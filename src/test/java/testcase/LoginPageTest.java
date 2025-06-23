package testcase;

import base.BaseLogin;
import base.DriverManager;
import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilities.Commons;

import java.io.IOException;
import java.time.Duration;

@Listeners(TestListener.class)
public class LoginPageTest extends BaseLogin {

    private WebDriverWait getWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    private boolean isElementPresent(By locator) {
        WebDriver driver = DriverManager.getDriver();
        return driver.findElements(locator).size() > 0;
    }

    @Test(priority = 1)
    void resetPassword() throws IOException, InterruptedException {
        WebDriver driver = DriverManager.getDriver();
        loginPage();

        for (String email : testData.getEmail()) {
            WebDriverWait wait = getWait(driver);
            Commons.click(driver, By.linkText(locators.getProperty("reset_link")));
            Commons.enter(driver, By.xpath(locators.getProperty("reset_email_field")), email);
            Commons.click(driver, By.xpath(locators.getProperty("confirm_email_button")));

            By successMsgLocator = By.xpath(locators.getProperty("confirmation_message"));
            By errorMsgLocator = By.cssSelector(locators.getProperty("reset_password_error_message"));

            try {
                wait.until(ExpectedConditions.or(
                        ExpectedConditions.visibilityOfElementLocated(successMsgLocator),
                        ExpectedConditions.visibilityOfElementLocated(errorMsgLocator)
                ));

                if (isElementPresent(successMsgLocator)) {
                    String successMessage = driver.findElement(successMsgLocator).getText().trim();
                    Assert.assertEquals(successMessage, "Password reset instructions sent to your email", "Success message mismatch.");
                } else {
                    String errorMessage = driver.findElement(errorMsgLocator).getText().trim();
                    Assert.assertEquals(errorMessage, "Incorrect email. Please enter the registered email address.", "Error message mismatch.");
                }
            } catch (Exception e) {
                Assert.fail("Neither success nor error message appeared within timeout for email: " + email, e);
            }

            Commons.click(driver, By.linkText(locators.getProperty("back_to_login")));
        }
    }

    @Test(priority = 2)
    void loginTest() throws IOException, InterruptedException {
        WebDriver driver = DriverManager.getDriver();
        WebDriverWait wait = getWait(driver);
        loginPage();

        for (String email : testData.getEmail()) {
            Commons.enter(driver, By.id(locators.getProperty("username_field")), email);
            Commons.enter(driver, By.id(locators.getProperty("password_field")), testData.getPassword());
            Commons.click(driver, By.xpath(locators.getProperty("login_button")));

            try {
                wait.until(ExpectedConditions.or(
                        ExpectedConditions.presenceOfElementLocated(By.xpath(locators.getProperty("Registry"))),
                        ExpectedConditions.presenceOfElementLocated(By.xpath(locators.getProperty("login_error_message")))
                ));

                if (isElementPresent(By.xpath(locators.getProperty("Registry")))) {
                    WebElement registryButton = driver.findElement(By.xpath(locators.getProperty("group_create_button")));
                    Assert.assertNotNull(registryButton, "Valid login: 'group_create_button' not found");
                    Commons.click(driver, By.xpath(locators.getProperty("logout_dropdown")));
                    Commons.click(driver, By.xpath(locators.getProperty("logout")));
                    loginPage(); // Ensure fresh state for next iteration
                } else {
                    WebElement errorElement = driver.findElement(By.xpath(locators.getProperty("login_error_message")));
                    String errorMessage = errorElement.getText().trim();
                    Assert.assertEquals(errorMessage, "Login failed due to Invalid credentials !", "Error message mismatch for invalid credentials.");
                }

            } catch (Exception e) {
                Assert.fail("Login result not displayed in time for email: " + email, e);
            }
        }
    }
}
