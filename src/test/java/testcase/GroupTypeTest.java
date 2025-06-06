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

public class GroupTypeTest extends BaseLogin {
    @Test(priority = 1)
    void groupTypeCreation() throws IOException, InterruptedException {
        login();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        String groupType = testData.getGroupType();
        Commons.click(driver, By.xpath(locators.getProperty("registry_configuration")));
        Commons.click(driver,By.xpath(locators.getProperty("group_type")));
        Commons.click(driver,By.xpath(locators.getProperty("create_button")));
        Commons.enter(driver,By.xpath(locators.getProperty("group_type_data_input")),groupType);
        Commons.click(driver,By.xpath(locators.getProperty("save_group_type")));
        String tableXPath = locators.getProperty("groupType_table");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryFound = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, groupType);
        Assert.assertTrue(entryFound, "Expected entry with text '" + groupType + "' not found");
    }

   @Test(priority = 2, dependsOnMethods = {"groupTypeCreation"})
   void groupTypeUpdation() throws IOException, InterruptedException {
       login();
       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
       String groupType = testData.getGroupType();
       String groupTypeUpdated = testData.getGroupTypeUpdated();
       Commons.click(driver, By.xpath(locators.getProperty("registry_configuration")));
       Commons.click(driver,By.xpath(locators.getProperty("group_type")));
       String tableXPath = locators.getProperty("group_membership_kind_table");
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
       boolean entryFound = Commons.clickEntryInPaginatedTable(driver, tableXPath, groupType);
       Assert.assertTrue(entryFound, "Expected entry with text '" + groupType + "' not found");
       Commons.enter(driver,By.xpath(locators.getProperty("group_type_data_input")),groupTypeUpdated);
       Commons.click(driver,By.xpath(locators.getProperty("save_update")));
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
       boolean entryUpdateFound = Commons.clickEntryInPaginatedTable(driver, tableXPath, groupTypeUpdated);
       Assert.assertTrue(entryUpdateFound, "Expected entry with text '" + groupTypeUpdated + "' not found");
   }

   @Test(priority = 3,dependsOnMethods = {"groupTypeUpdation"})
    void groupTypeDeletion() throws IOException, InterruptedException {
       login();
       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
       String groupTypeUpdated = testData.getGroupTypeUpdated();
       Commons.click(driver, By.xpath(locators.getProperty("registry_configuration")));
       Commons.click(driver,By.xpath(locators.getProperty("group_type")));
       String tableXPath = locators.getProperty("group_membership_kind_table");
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
       boolean entryFound = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, groupTypeUpdated);
       Assert.assertTrue(entryFound, "Expected entry with text '" + groupTypeUpdated + "' not found");
       String rowCheckboxXPath = "//tr[td[contains(text(),'" + groupTypeUpdated + "')]]//input[@type='checkbox']";
       Commons.click(driver, By.xpath(rowCheckboxXPath));
       Commons.click(driver,By.xpath(locators.getProperty("actions")));
       Commons.click(driver,By.xpath(locators.getProperty("delete")));
       Commons.click(driver,By.xpath(locators.getProperty("delete_confirmation")));
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
       boolean entryStillExists = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, groupTypeUpdated);
       Assert.assertFalse(entryStillExists, "Entry with text '" + groupTypeUpdated + "' should be deleted but still exists.");
   }
}
