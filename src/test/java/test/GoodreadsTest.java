package test;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class GoodreadsTest {

    public AndroidDriver<MobileElement> driver;
    public WebDriverWait wait;

    /***
     * Declare Locator of elements on the Login page
     */
    public final By loginLink = By.id("com.goodreads:id/sign_in_button");
    public final By txtEmail = By.id("com.goodreads:id/email");
    public final By txtPass = By.id("com.goodreads:id/password");
    public final By btnLogin = By.id("com.goodreads:id/login_button");
    public final By txtWelcome = By.id("com.goodreads:id/title");

    public final By actionSearch = By.id("com.goodreads:id/action_search");
    public final By txtSearch = By.id("com.goodreads:id/search_text");

    public final By resultMessage = By.id("com.goodreads:id/results_message");
    public final By wantToRead_Unchecked = By.id("com.goodreads:id/wtr_unshelvedStatus");
    public final By wantToRead_Checked = By.id("com.goodreads:id/wtr_shelvedStatus");

    @BeforeMethod
    public void setup() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "Pixel 4 API 30");
        caps.setCapability("udid", "emulator-5554");
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "11.0");
        caps.setCapability("skipUnlock", "true");
        caps.setCapability("appPackage", "com.goodreads");
        caps.setCapability("appActivity", "com.goodreads.kindle.ui.activity.SplashActivity");
        caps.setCapability("noReset", "false");
        driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        wait = new WebDriverWait(driver, 5000);
    }

    @Test
    public void basicTest() throws InterruptedException {
        // Click "Log in" link
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginLink)).click();

        // Enter email and password
        WebElement txtEmailElement = wait.until(ExpectedConditions.visibilityOfElementLocated(txtEmail));
        txtEmailElement.click();
        txtEmailElement.sendKeys("kimdung726@gmail.com");
        WebElement txtPassElement = wait.until(ExpectedConditions.visibilityOfElementLocated(txtPass));
        txtPassElement.click();
        txtPassElement.sendKeys("demopass123456789");

        // Click Log in button
        WebElement btnLoginElement = wait.until(ExpectedConditions.visibilityOfElementLocated(btnLogin));
        btnLoginElement.click();

        // Check user log in successfully
        WebElement txtWelcomeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(txtWelcome));
        String welcomeMessage = txtWelcomeElement.getText();
        Assert.assertEquals(welcomeMessage, "Get started with Goodreads");

        // Search book
        wait.until(ExpectedConditions.visibilityOfElementLocated(actionSearch)).click();

        // Enter a keyword
        WebElement searchTbElement = wait.until(ExpectedConditions.visibilityOfElementLocated(txtSearch));
        searchTbElement.click();
        searchTbElement.sendKeys("Child");

        driver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", "Go"));

        // Get book
        wait.until(ExpectedConditions.visibilityOfElementLocated(resultMessage));
        wait.until(ExpectedConditions.visibilityOfElementLocated(wantToRead_Unchecked)).click();

        WebElement checkedElement = driver.findElement(wantToRead_Checked);
        String status = checkedElement.getAttribute("content-desc");

        boolean markedAsRead = false;
        if (status.contains("selected")) {
            markedAsRead = true;
        }

        // Verify mark as read
        Assert.assertTrue(markedAsRead);
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}
