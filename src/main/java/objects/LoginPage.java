package objects;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage {

    public LoginPage() {
    }

    /***
     * Declare Locator of elements on the Login page
     */
    private final By loginLink = By.id("com.goodreads:id/sign_in_button");
    private final By txtEmail = By.id("com.goodreads:id/email");
    private final By txtPass = By.id("com.goodreads:id/password");
    private final By loginBtn = By.id("com.goodreads:id/login_button");
    private final By txtWelcome = By.id("com.goodreads:id/title");


    @Step("Login application")
    public void login() {
    }

}
