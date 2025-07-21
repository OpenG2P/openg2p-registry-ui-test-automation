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
import java.util.Properties;

@Listeners(TestListener.class)
public class RegistrationPortalUserTest extends BaseLogin {

    @Test(priority = 1)
    void registrationPortalUserCreation() throws IOException, InterruptedException {
        WebDriver driver = DriverManager.getDriver();
        login();
        Properties locators = getLocators();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String portalUserName = getTestData().getPortalUserName();
        String portalUserEmail = getTestData().getPortalUserEmail();
        Commons.click(driver, By.xpath(locators.getProperty("home_menu")));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("registration_portal_user_dropdown"))));
        Commons.click(driver,By.xpath(locators.getProperty("registration_portal_user_dropdown")));
        Commons.click(driver,By.xpath(locators.getProperty("new_button")));
        Commons.enter(driver, By.xpath(locators.getProperty("portal_username")),portalUserName);
        Commons.enter(driver,By.xpath(locators.getProperty("portal_user_email")),portalUserEmail);
        Commons.click(driver,By.xpath(locators.getProperty("save")));
        Commons.click(driver,By.xpath(locators.getProperty("registration_portal_user")));
        Commons.click(driver,By.xpath(locators.getProperty("list_view")));
        String tableXPath = locators.getProperty("table");
        By newEntry = By.xpath("//tr[td[contains(text(),'" + portalUserName + "')]]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(newEntry));
        boolean entryFound = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, portalUserName);
        Assert.assertTrue(entryFound, "Expected entry with text '" + portalUserName + "' not found");
    }

    @Test(priority = 2, dependsOnMethods = {"registrationPortalUserCreation"})
    void grantPortalAccess() throws IOException, InterruptedException {
        WebDriver driver = DriverManager.getDriver();
        login();
        Properties locators = getLocators();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String portalUserName = getTestData().getPortalUserName();
        Commons.click(driver, By.xpath(locators.getProperty("home_menu")));
        Commons.click(driver,By.xpath(locators.getProperty("registration_portal_user_dropdown")));
        Commons.click(driver,By.xpath(locators.getProperty("list_view")));

        String tableXPath = locators.getProperty("table");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryFound = Commons.clickEntryInPaginatedTable(driver, tableXPath, portalUserName);
        Assert.assertTrue(entryFound, "Expected entry with text '" + portalUserName + "' not found");
        Commons.click(driver, By.xpath(locators.getProperty("action_dropdown")));
        Commons.click(driver, By.xpath(locators.getProperty("grant_portal_access")));
        Commons.click(driver, By.xpath(locators.getProperty("grant_access")));
        WebElement revokeButton = driver.findElement(By.xpath(locators.getProperty("revoke_access")));
        Assert.assertTrue(revokeButton.isDisplayed(), "'Revoke Access' button is not visible after clicking 'Grant Access'.");

    }

    @Test(priority = 3, dependsOnMethods = {"registrationPortalUserCreation"})
    void revokePortalAccess() throws IOException, InterruptedException {
        WebDriver driver = DriverManager.getDriver();
        login();
        Properties locators = getLocators();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String portalUserName = getTestData().getPortalUserName();
        Commons.click(driver, By.xpath(locators.getProperty("home_menu")));
        Commons.click(driver,By.xpath(locators.getProperty("registration_portal_user_dropdown")));
        Commons.click(driver,By.xpath(locators.getProperty("list_view")));

        String tableXPath = locators.getProperty("table");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryFound = Commons.clickEntryInPaginatedTable(driver, tableXPath, portalUserName);
        Assert.assertTrue(entryFound, "Expected entry with text '" + portalUserName + "' not found");
        Commons.click(driver, By.xpath(locators.getProperty("action_dropdown")));
        Commons.click(driver, By.xpath(locators.getProperty("grant_portal_access")));
        Commons.click(driver, By.xpath(locators.getProperty("revoke_access")));
        WebElement grantButton = driver.findElement(By.xpath(locators.getProperty("grant_access")));
        Assert.assertTrue(grantButton.isDisplayed(), "'Grant Access' button is not visible after clicking 'Revoke Access'.");
    }

    @Test(priority = 4, dependsOnMethods = {"registrationPortalUserCreation"})
    void registrationPortalUserUpdation() throws IOException, InterruptedException {
        WebDriver driver = DriverManager.getDriver();
        login();
        Properties locators = getLocators();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String portalUserName = getTestData().getPortalUserName();
        String portalUserNameUpdated = getTestData().getPortalUserNameUpdated();
        Commons.click(driver, By.xpath(locators.getProperty("home_menu")));
        Commons.click(driver,By.xpath(locators.getProperty("registration_portal_user_dropdown")));
        Commons.click(driver,By.xpath(locators.getProperty("list_view")));

        String tableXPath = locators.getProperty("table");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryFound = Commons.clickEntryInPaginatedTable(driver, tableXPath, portalUserName);
        Assert.assertTrue(entryFound, "Expected entry with text '" + portalUserName + "' not found");

        Commons.enter(driver, By.xpath(locators.getProperty("portal_username")),portalUserNameUpdated);
        Commons.click(driver,By.xpath(locators.getProperty("save")));
        Commons.click(driver,By.xpath(locators.getProperty("registration_portal_user")));
        Commons.click(driver,By.xpath(locators.getProperty("list_view")));

        By updatedEntry = By.xpath("//tr[td[contains(text(),'" + portalUserNameUpdated + "')]]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(updatedEntry));
        boolean entryUpdateFound = Commons.clickEntryInPaginatedTable(driver, tableXPath, portalUserNameUpdated);
        Assert.assertTrue(entryUpdateFound, "Expected entry with text '" + portalUserNameUpdated + "' not found");
    }
}
