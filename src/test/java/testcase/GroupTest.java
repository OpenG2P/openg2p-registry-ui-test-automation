package testcase;

import base.BaseLogin;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.Commons;

import java.io.IOException;
import java.time.Duration;

public class GroupTest extends BaseLogin {

    @Test(priority = 1)
    void groupCreation() throws IOException, InterruptedException {
        login();
            String groupName = testData.getGroupName();
            Commons.click(driver, By.xpath(locators.getProperty("group")));
            Commons.click(driver, By.xpath(locators.getProperty("new_button")));
            Commons.enter(driver, By.xpath(locators.getProperty("group_name_field")), groupName);
            Commons.dropDownByValue(driver, By.id(locators.getProperty("tags")), testData.getTags());
            Commons.dropDownByValue(driver, By.id(locators.getProperty("kind")), testData.getGroupMembershipKind());
            Commons.click(driver, By.xpath(locators.getProperty("contact_details")));
            Commons.enter(driver, By.id(locators.getProperty("address")), testData.getAddress());
            Commons.dropDownByValue(driver, By.id(locators.getProperty("region_dropdown")), testData.getRegion());
//        Commons.click(driver, By.xpath(locators.getProperty("add_a_line")));
//        Commons.enter(driver, By.xpath(locators.getProperty("phone_number")),"");
//        Commons.click(driver, By.xpath(locators.getProperty("save&close")));
            Commons.click(driver, By.xpath(locators.getProperty("save")));
            Commons.click(driver, By.xpath(locators.getProperty("groups")));
            String tableXPath = locators.getProperty("group_table");
            boolean entryFound = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, groupName);
            Assert.assertTrue(entryFound, "Expected entry with text '" + groupName + "' not found");


    }

    @Test(priority = 2, dependsOnMethods = {"groupCreation"})
    void groupUpdation() throws IOException, InterruptedException {
        login();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        String groupName = testData.getGroupName();
        String groupNameUpdated = testData.getGroupNameUpdated();
        Commons.click(driver, By.xpath(locators.getProperty("group")));
        String tableXPath = locators.getProperty("group_table");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryClicked = Commons.clickEntryInPaginatedTable(driver, tableXPath, groupName);
        Assert.assertTrue(entryClicked, "Expected entry '" + groupName + "' not found and clicked.");
        Commons.clearAndEnter(driver,By.xpath(locators.getProperty("group_name_field")),groupNameUpdated);
        Commons.click(driver,By.xpath(locators.getProperty("save")));
        Commons.click(driver, By.xpath(locators.getProperty("groups")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryFound = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, groupNameUpdated);
        Assert.assertTrue(entryFound, "Expected entry with text '" + groupNameUpdated + "' not found");

    }

    @Test(priority = 3,dependsOnMethods = {"groupUpdation"})
    void groupDeletion() throws IOException, InterruptedException {
        login();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        String groupNameUpdated = testData.getGroupNameUpdated();
        Commons.click(driver, By.xpath(locators.getProperty("group")));
        String tableXPath = locators.getProperty("group_table");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryFound = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, groupNameUpdated);
        Assert.assertTrue(entryFound, "Expected entry with text '" + groupNameUpdated + "' not found");
        String rowCheckboxXPath = "//tr[td[contains(text(),'" + groupNameUpdated + "')]]//input[@type='checkbox']";
        Commons.click(driver, By.xpath(rowCheckboxXPath));
        Commons.click(driver,By.xpath(locators.getProperty("actions")));
        Commons.click(driver,By.xpath(locators.getProperty("delete")));
        Commons.click(driver,By.xpath(locators.getProperty("delete_confirmation")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryStillExists = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, groupNameUpdated);
        Assert.assertFalse(entryStillExists, "Entry with text '" + groupNameUpdated + "' should be deleted but still exists.");

    }
}
