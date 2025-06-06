package testcase;

import base.BaseLogin;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.Commons;

import java.io.IOException;
import java.time.Duration;

public class RegionTest extends BaseLogin {

    @Test(priority = 1)
    void regionCreation() throws IOException, InterruptedException {
        login();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        String region = testData.getRegion();
        Commons.click(driver, By.xpath(locators.getProperty("registry_configuration")));
        Commons.click(driver,By.xpath(locators.getProperty("region")));
        Commons.click(driver,By.xpath(locators.getProperty("create_button")));
        Commons.enter(driver,By.xpath(locators.getProperty("configurations_data_input")),region);
        Commons.click(driver,By.xpath(locators.getProperty("save_region")));
        String tableXPath = locators.getProperty("region_table");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryFound = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, region);
        Assert.assertTrue(entryFound, "Expected entry with text '" + region + "' not found");

    }

    @Test(priority = 2, dependsOnMethods = {"regionCreation"})
    void regionUpdation() throws IOException, InterruptedException {
        login();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        String region = testData.getRegion();
        String regionUpdated = testData.getRegionUpdated();
        Commons.click(driver, By.xpath(locators.getProperty("registry_configuration")));
        Commons.click(driver,By.xpath(locators.getProperty("region")));
        String tableXPath = locators.getProperty("region_table");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryFound = Commons.clickEntryInPaginatedTable(driver, tableXPath, region);
        Assert.assertTrue(entryFound, "Expected entry with text '" + region + "' not found");
        Commons.enter(driver,By.xpath(locators.getProperty("configurations_data_input")),regionUpdated);
        Commons.click(driver,By.xpath(locators.getProperty("save_update")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryUpdateFound = Commons.clickEntryInPaginatedTable(driver, tableXPath, regionUpdated);
        Assert.assertTrue(entryUpdateFound, "Expected entry with text '" + regionUpdated + "' not found");

    }

    @Test(priority = 3, dependsOnMethods = {"regionUpdation"})
    void regionDeletion() throws IOException, InterruptedException {
        login();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        String regionUpdated = testData.getRegionUpdated();
        Commons.click(driver, By.xpath(locators.getProperty("registry_configuration")));
        Commons.click(driver,By.xpath(locators.getProperty("region")));
        String tableXPath = locators.getProperty("region_table");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryFound = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, regionUpdated);
        Assert.assertTrue(entryFound, "Expected entry with text '" + regionUpdated + "' not found");
        String rowCheckboxXPath = "//tr[td[contains(text(),'" + regionUpdated + "')]]//input[@type='checkbox']";
        Commons.click(driver, By.xpath(rowCheckboxXPath));
        Commons.click(driver,By.xpath(locators.getProperty("actions")));
        Commons.click(driver,By.xpath(locators.getProperty("delete")));
        Commons.click(driver,By.xpath(locators.getProperty("delete_confirmation")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryStillExists = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, regionUpdated);
        Assert.assertFalse(entryStillExists, "Entry with text '" + regionUpdated + "' should be deleted but still exists.");
    }
}
