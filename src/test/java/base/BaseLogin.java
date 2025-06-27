package base;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BaseLogin extends DriverCreator {

    public void loginPage() throws IOException {
        WebDriver driver = DriverManager.getDriver();
        Properties locators = getLocators();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement socialRegistry = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(locators.getProperty("social_registry"))));
        scrollAndClick(driver, socialRegistry);

        WebElement socialRegistryAdmin = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(locators.getProperty("social_registry_admin"))));
        scrollAndClick(driver, socialRegistryAdmin);
    }

    public void login() throws IOException {
        WebDriver driver = DriverManager.getDriver();
        Properties config = getConfig();
        Properties locators = getLocators();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement socialRegistry = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(locators.getProperty("social_registry"))));
        scrollAndClick(driver, socialRegistry);

        WebElement socialRegistryAdmin = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(locators.getProperty("social_registry_admin"))));
        scrollAndClick(driver, socialRegistryAdmin);

        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(
                By.name(locators.getProperty("username_field"))));
        usernameField.clear();
        usernameField.sendKeys(config.getProperty("username"));

        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(
                By.id(locators.getProperty("password_field"))));
        passwordField.clear();
        passwordField.sendKeys(config.getProperty("password"));

        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(locators.getProperty("login_button"))));
        loginButton.click();
    }

    private void scrollAndClick(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        js.executeScript("document.querySelectorAll('a[target=\"_blank\"]').forEach(el => el.setAttribute('target', '_self'));");
        js.executeScript("arguments[0].click();", element);
    }
}
