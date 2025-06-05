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

public class GroupMembershipKindTest extends BaseLogin {

    @Test(priority = 1)
    void groupMembershipKindCreation() throws IOException, InterruptedException {
        login();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        String groupMembershipKind = testData.getGroupMembershipKind();
        Commons.click(driver, By.xpath(locators.getProperty("registry_configuration")));
        Commons.click(driver,By.xpath(locators.getProperty("group_membership_kind")));
        Commons.click(driver,By.xpath(locators.getProperty("create_button")));
        Commons.enter(driver,By.xpath(locators.getProperty("group_membership_kind_data_input")),groupMembershipKind);
        Commons.click(driver,By.xpath(locators.getProperty("save_group_membership_kind")));
        String tableXPath = locators.getProperty("group_membership_kind_table");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryFound = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, groupMembershipKind);
        Assert.assertTrue(entryFound, "Expected entry with text '" + groupMembershipKind + "' not found");
    }


    @Test(priority = 2, dependsOnMethods = {"groupMembershipKindCreation"})
    void groupMembershipKindUpdation() throws IOException, InterruptedException {
        login();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        String groupMembershipKindUpdated = testData.getGroupMembershipKindUpdated();
        String groupMembershipKind = testData.getGroupMembershipKind();
        Commons.click(driver, By.xpath(locators.getProperty("registry_configuration")));
        Commons.click(driver,By.xpath(locators.getProperty("group_membership_kind")));
        String tableXPath = locators.getProperty("group_membership_kind_table");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryFound = Commons.clickEntryInPaginatedTable(driver, tableXPath, groupMembershipKind);
        Assert.assertTrue(entryFound, "Expected entry with text '" + groupMembershipKind + "' not found");
        Commons.enter(driver,By.xpath(locators.getProperty("group_membership_kind_data_input")),groupMembershipKindUpdated);
        Commons.click(driver,By.xpath(locators.getProperty("save_update")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryUpdateFound = Commons.clickEntryInPaginatedTable(driver, tableXPath, groupMembershipKindUpdated);
        Assert.assertTrue(entryUpdateFound, "Expected entry with text '" + groupMembershipKindUpdated + "' not found");

    }

    @Test(priority = 3, dependsOnMethods = {"groupMembershipKindUpdation"})
    void groupMembershipKindDeletion() throws IOException, InterruptedException {
        login();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        String groupMembershipKindUpdated = testData.getGroupMembershipKindUpdated();
        Commons.click(driver, By.xpath(locators.getProperty("registry_configuration")));
        Commons.click(driver,By.xpath(locators.getProperty("group_membership_kind")));
        String tableXPath = locators.getProperty("group_membership_kind_table");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryFound = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, groupMembershipKindUpdated);
        Assert.assertTrue(entryFound, "Expected entry with text '" + groupMembershipKindUpdated + "' not found");
        String rowCheckboxXPath = "//tr[td[contains(text(),'" + groupMembershipKindUpdated + "')]]//input[@type='checkbox']";
        Commons.click(driver, By.xpath(rowCheckboxXPath));
        Commons.click(driver,By.xpath(locators.getProperty("actions")));
        Commons.click(driver,By.xpath(locators.getProperty("delete")));
        Commons.click(driver,By.xpath(locators.getProperty("delete_confirmation")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryStillExists = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, groupMembershipKindUpdated);
        Assert.assertFalse(entryStillExists, "Entry with text '" + groupMembershipKindUpdated + "' should be deleted but still exists.");
    }
}
