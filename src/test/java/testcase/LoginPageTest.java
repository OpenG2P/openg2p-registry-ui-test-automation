package testcase;

import base.BaseLogin;
import base.DriverCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.Commons;
import utilities.TestData;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import static base.DriverCreator.driver;

public class LoginPageTest extends BaseLogin {


    @Test(priority = 1)
    void resetPassword() throws InterruptedException, IOException {
        loginPage();
        for (String email : testData.getEmail()) {
            Commons.click(driver, By.linkText(locators.getProperty("reset_link")));
            Commons.enter(driver, By.xpath(locators.getProperty("reset_email_field")), email);
            Commons.click(driver, By.xpath(locators.getProperty("confirm_email_button")));
            Thread.sleep(3000);
            if (isElementPresent(By.xpath(locators.getProperty("confirmation_message")))) {
                String successMessage = driver.findElement(By.xpath(locators.getProperty("confirmation_message"))).getText();
                Assert.assertEquals(successMessage, "Password reset instructions sent to your email");
            } else if (isElementPresent(By.cssSelector(locators.getProperty("reset_password_error_message")))) {
                String errorMessage = driver.findElement(By.cssSelector(locators.getProperty("reset_password_error_message"))).getText();
                Assert.assertEquals(errorMessage, "Incorrect email. Please enter the registered email address.");
            } else {
                Assert.fail("Neither success nor error message was found after attempting to reset password.");
            }
            Commons.click(driver, By.linkText(locators.getProperty("back_to_login")));
        }
    }


    @Test(priority = 2)
    void loginTest() throws InterruptedException, IOException {
        loginPage();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        for (String email : testData.getEmail()) {
            Commons.enter(driver, By.id(locators.getProperty("username_field")), email);
            Commons.enter(driver, By.id(locators.getProperty("password_field")), testData.getPassword());
            Commons.click(driver, By.xpath(locators.getProperty("login_button")));
            Thread.sleep(2000);
            if (isElementPresent(By.xpath(locators.getProperty("Registry")))) {
                WebElement registry = driver.findElement(By.xpath(locators.getProperty("group_create_button")));
                Assert.assertNotNull(registry, "Assertion failed in valid login scenario");
                Commons.click(driver, By.xpath(locators.getProperty("logout_dropdown")));
                Commons.click(driver,By.xpath(locators.getProperty("logout")));
            } else {
                WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(locators.getProperty("login_error_message"))));
                String errorMessage = errorElement.getText();
                Assert.assertEquals(errorMessage, "Login failed due to Invalid credentials !", "Error message mismatch for invalid credentials.");
            }
        }
    }




    private boolean isElementPresent(By locator) {
        return driver.findElements(locator).size() > 0;
    }

}
