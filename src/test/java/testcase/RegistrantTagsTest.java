package testcase;

import base.BaseLogin;
import base.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.Commons;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class RegistrantTagsTest extends BaseLogin {

    @Test(priority = 1)
    void registrantTagCreation() throws IOException, InterruptedException {
        WebDriver driver = DriverManager.getDriver();
        login();
        Properties locators = getLocators();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        String registrantTag = testData.getRegistrantTag();

        Commons.click(driver, By.xpath(locators.getProperty("registry_configuration")));
        Commons.click(driver, By.xpath(locators.getProperty("registrant_tags")));
        Commons.click(driver, By.xpath(locators.getProperty("create_button")));
        Commons.enter(driver, By.xpath(locators.getProperty("configurations_data_input")), registrantTag);
        Commons.click(driver, By.xpath(locators.getProperty("save_button")));

        String tableXPath = locators.getProperty("registrantTag_table");
        By newEntry = By.xpath("//tr[td[contains(text(),'" + registrantTag + "')]]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(newEntry));

        boolean entryFound = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, registrantTag);
        Assert.assertTrue(entryFound, "Expected entry with text '" + registrantTag + "' not found");
    }

    @Test(priority = 2, dependsOnMethods = {"registrantTagCreation"})
    void registrantTagUpdation() throws IOException, InterruptedException {
        WebDriver driver = DriverManager.getDriver();
        login();
        Properties locators = getLocators();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        String registrantTag = testData.getRegistrantTag();
        String registrantTagUpdated = testData.getRegistrantTagUpdated();

        Commons.click(driver, By.xpath(locators.getProperty("registry_configuration")));
        Commons.click(driver, By.xpath(locators.getProperty("registrant_tags")));

        String tableXPath = locators.getProperty("registrantTag_table");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryFound = Commons.clickEntryInPaginatedTable(driver, tableXPath, registrantTag);
        Assert.assertTrue(entryFound, "Expected entry with text '" + registrantTag + "' not found");

        Commons.clearAndEnter(driver, By.xpath(locators.getProperty("configurations_data_input")), registrantTagUpdated);
        Commons.click(driver, By.xpath(locators.getProperty("save_update")));

        By updatedEntry = By.xpath("//tr[td[contains(text(),'" + registrantTagUpdated + "')]]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(updatedEntry));

        boolean entryUpdateFound = Commons.clickEntryInPaginatedTable(driver, tableXPath, registrantTagUpdated);
        Assert.assertTrue(entryUpdateFound, "Expected entry with text '" + registrantTagUpdated + "' not found");
    }

    @Test(priority = 3, dependsOnMethods = {"registrantTagUpdation"})
    void registrantTagDeletion() throws IOException, InterruptedException {
        WebDriver driver = DriverManager.getDriver();
        login();
        Properties locators = getLocators();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        String registrantTagUpdated = testData.getRegistrantTagUpdated();

        Commons.click(driver, By.xpath(locators.getProperty("registry_configuration")));
        Commons.click(driver, By.xpath(locators.getProperty("registrant_tags")));

        String tableXPath = locators.getProperty("registrantTag_table");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryFound = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, registrantTagUpdated);
        Assert.assertTrue(entryFound, "Expected entry with text '" + registrantTagUpdated + "' not found");

        String rowCheckboxXPath = "//tr[td[contains(text(),'" + registrantTagUpdated + "')]]//input[@type='checkbox']";
        Commons.click(driver, By.xpath(rowCheckboxXPath));
        Commons.click(driver, By.xpath(locators.getProperty("actions")));
        Commons.click(driver, By.xpath(locators.getProperty("delete")));
        Commons.click(driver, By.xpath(locators.getProperty("delete_confirmation")));

        By deletedEntry = By.xpath("//tr[td[contains(text(),'" + registrantTagUpdated + "')]]");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(deletedEntry));

        boolean entryStillExists = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, registrantTagUpdated);
        Assert.assertFalse(entryStillExists, "Entry with text '" + registrantTagUpdated + "' should be deleted but still exists.");
    }
}
