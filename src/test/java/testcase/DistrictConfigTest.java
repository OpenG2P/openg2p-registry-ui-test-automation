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

public class DistrictConfigTest extends BaseLogin {

    @Test(priority = 1)
    void districtConfigCreation() throws IOException, InterruptedException {
        login();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        String district = testData.getDistrict();
        Commons.click(driver, By.xpath(locators.getProperty("registry_configuration")));
        Commons.click(driver,By.xpath(locators.getProperty("district_config")));
        Commons.click(driver,By.xpath(locators.getProperty("create_button")));
        Commons.enter(driver,By.xpath(locators.getProperty("district_input_field")),district);
        Commons.click(driver,By.xpath(locators.getProperty("district_save")));
        String tableXPath = locators.getProperty("district_table");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryFound = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, district);
        Assert.assertTrue(entryFound, "Expected entry with text '" + district + "' not found");
    }

    @Test(priority = 2, dependsOnMethods = {"districtConfigCreation"})
    void districtConfigUpdation() throws IOException, InterruptedException {
        login();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        String district = testData.getDistrict();
        String districtUpdated = testData.getDistrictUpdated();
        Commons.click(driver, By.xpath(locators.getProperty("registry_configuration")));
        Commons.click(driver,By.xpath(locators.getProperty("district_config")));
        String tableXPath = locators.getProperty("district_table");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryFound = Commons.clickEntryInPaginatedTable(driver, tableXPath, district);
        Assert.assertTrue(entryFound, "Expected entry with text '" + district + "' not found");
        Commons.clearAndEnter(driver,By.xpath(locators.getProperty("district_input_field")),districtUpdated);
        Commons.click(driver,By.xpath(locators.getProperty("save_update")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryUpdateFound = Commons.clickEntryInPaginatedTable(driver, tableXPath, districtUpdated);
        Assert.assertTrue(entryUpdateFound, "Expected entry with text '" + districtUpdated + "' not found");
    }

    @Test(priority = 3,dependsOnMethods = {"districtConfigUpdation"})
    void districtConfigDeletion() throws IOException, InterruptedException {
        login();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        String districtUpdated = testData.getDistrictUpdated();
        Commons.click(driver, By.xpath(locators.getProperty("registry_configuration")));
        Commons.click(driver,By.xpath(locators.getProperty("district_config")));
        String tableXPath = locators.getProperty("district_table");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryFound = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, districtUpdated);
        Assert.assertTrue(entryFound, "Expected entry with text '" + districtUpdated + "' not found");
        String rowCheckboxXPath = "//tr[td[contains(text(),'" + districtUpdated + "')]]//input[@type='checkbox']";
        Commons.click(driver, By.xpath(rowCheckboxXPath));
        Commons.click(driver,By.xpath(locators.getProperty("actions")));
        Commons.click(driver,By.xpath(locators.getProperty("delete")));
        Commons.click(driver,By.xpath(locators.getProperty("delete_confirmation")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryStillExists = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, districtUpdated);
        Assert.assertFalse(entryStillExists, "Entry with text '" + districtUpdated + "' should be deleted but still exists.");
    }
}
