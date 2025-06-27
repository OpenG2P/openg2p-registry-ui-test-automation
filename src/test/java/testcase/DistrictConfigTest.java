package testcase;

import base.BaseLogin;
import base.DriverManager;
import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
public class DistrictConfigTest extends BaseLogin {

    @Test(priority = 1)
    void districtConfigCreation() throws IOException, InterruptedException {
        WebDriver driver = DriverManager.getDriver();
        login();
        Properties locators = getLocators();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String district = getTestData().getDistrict();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("registry_configuration"))));
        Commons.click(driver, By.xpath(locators.getProperty("registry_configuration")));
        Commons.click(driver, By.xpath(locators.getProperty("district_config")));
        Commons.click(driver, By.xpath(locators.getProperty("create_button")));
        Commons.enter(driver, By.xpath(locators.getProperty("district_input_field")), district);
        Commons.click(driver, By.xpath(locators.getProperty("save_button")));

        String tableXPath = locators.getProperty("table");
        By newEntry = By.xpath("//tr[td[contains(text(),'" + district + "')]]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(newEntry));

        boolean entryFound = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, district);
        Assert.assertTrue(entryFound, "Expected entry with text '" + district + "' not found");
    }

    @Test(priority = 2, dependsOnMethods = {"districtConfigCreation"})
    void districtConfigUpdation() throws IOException, InterruptedException {
        WebDriver driver = DriverManager.getDriver();
        login();
        Properties locators = getLocators();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String district = getTestData().getDistrict();
        String districtUpdated = getTestData().getDistrictUpdated();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("registry_configuration"))));
        Commons.click(driver, By.xpath(locators.getProperty("registry_configuration")));
        Commons.click(driver, By.xpath(locators.getProperty("district_config")));
        String tableXPath = locators.getProperty("table");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryFound = Commons.clickEntryInPaginatedTable(driver, tableXPath, district);
        Assert.assertTrue(entryFound, "Expected entry with text '" + district + "' not found");

        Commons.clearAndEnter(driver, By.xpath(locators.getProperty("district_input_field")), districtUpdated);
        Commons.click(driver, By.xpath(locators.getProperty("save_update")));

        By updatedEntry = By.xpath("//tr[td[contains(text(),'" + districtUpdated + "')]]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(updatedEntry));

        boolean entryUpdateFound = Commons.clickEntryInPaginatedTable(driver, tableXPath, districtUpdated);
        Assert.assertTrue(entryUpdateFound, "Expected entry with text '" + districtUpdated + "' not found");
    }

    @Test(priority = 3, dependsOnMethods = {"districtConfigUpdation"})
    void districtConfigDeletion() throws IOException, InterruptedException {
        WebDriver driver = DriverManager.getDriver();
        login();
        Properties locators = getLocators();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String districtUpdated = getTestData().getDistrictUpdated();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("registry_configuration"))));
        Commons.click(driver, By.xpath(locators.getProperty("registry_configuration")));
        Commons.click(driver, By.xpath(locators.getProperty("district_config")));
        String tableXPath = locators.getProperty("table");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryFound = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, districtUpdated);
        Assert.assertTrue(entryFound, "Expected entry with text '" + districtUpdated + "' not found");

        String rowCheckboxXPath = "//tr[td[contains(text(),'" + districtUpdated + "')]]//input[@type='checkbox']";
        Commons.click(driver, By.xpath(rowCheckboxXPath));
        Commons.click(driver, By.xpath(locators.getProperty("actions")));
        Commons.click(driver, By.xpath(locators.getProperty("delete")));
        Commons.click(driver, By.xpath(locators.getProperty("delete_confirmation")));

        By deletedEntry = By.xpath("//tr[td[contains(text(),'" + districtUpdated + "')]]");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(deletedEntry));

        boolean entryStillExists = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, districtUpdated);
        Assert.assertFalse(entryStillExists, "Entry with text '" + districtUpdated + "' should be deleted but still exists.");
    }
}
