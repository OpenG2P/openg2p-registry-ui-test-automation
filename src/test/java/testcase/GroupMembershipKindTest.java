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
public class GroupMembershipKindTest extends BaseLogin {

    @Test(priority = 1)
    void groupMembershipKindCreation() throws IOException, InterruptedException {
        WebDriver driver = DriverManager.getDriver();
        Properties locators = getLocators();
        login();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String groupMembershipKind = getTestData().getGroupMembershipKind();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("registry_configuration"))));
        Commons.click(driver, By.xpath(locators.getProperty("registry_configuration")));
        Commons.click(driver, By.xpath(locators.getProperty("group_membership_kind")));
        Commons.click(driver, By.xpath(locators.getProperty("create_button")));
        Commons.enter(driver, By.xpath(locators.getProperty("group_membership_kind_data_input")), groupMembershipKind);
        Commons.click(driver, By.xpath(locators.getProperty("save_button")));

        String tableXPath = locators.getProperty("table");
        By newEntry = By.xpath("//tr[td[contains(text(),'" + groupMembershipKind + "')]]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(newEntry));
        boolean entryFound = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, groupMembershipKind);
        Assert.assertTrue(entryFound, "Expected entry with text '" + groupMembershipKind + "' not found");
    }

    @Test(priority = 2, dependsOnMethods = {"groupMembershipKindCreation"})
    void groupMembershipKindUpdation() throws IOException, InterruptedException {
        WebDriver driver = DriverManager.getDriver();
        Properties locators = getLocators();
        login();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String groupMembershipKind = getTestData().getGroupMembershipKind();
        String groupMembershipKindUpdated = getTestData().getGroupMembershipKindUpdated();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("registry_configuration"))));
        Commons.click(driver, By.xpath(locators.getProperty("registry_configuration")));
        Commons.click(driver, By.xpath(locators.getProperty("group_membership_kind")));

        String tableXPath = locators.getProperty("table");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));

        boolean entryFound = Commons.clickEntryInPaginatedTable(driver, tableXPath, groupMembershipKind);
        Assert.assertTrue(entryFound, "Expected entry with text '" + groupMembershipKind + "' not found");

        Commons.clearAndEnter(driver, By.xpath(locators.getProperty("group_membership_kind_data_input")), groupMembershipKindUpdated);
        Commons.click(driver, By.xpath(locators.getProperty("save_update")));

        By updatedEntry = By.xpath("//tr[td[contains(text(),'" + groupMembershipKindUpdated + "')]]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(updatedEntry));

        boolean entryUpdateFound = Commons.clickEntryInPaginatedTable(driver, tableXPath, groupMembershipKindUpdated);
        Assert.assertTrue(entryUpdateFound, "Expected entry with text '" + groupMembershipKindUpdated + "' not found");
    }

    @Test(priority = 3, dependsOnMethods = {"groupMembershipKindUpdation"})
    void groupMembershipKindDeletion() throws IOException, InterruptedException {
        WebDriver driver = DriverManager.getDriver();
        Properties locators = getLocators();
        login();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String groupMembershipKindUpdated = getTestData().getGroupMembershipKindUpdated();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("registry_configuration"))));
        Commons.click(driver, By.xpath(locators.getProperty("registry_configuration")));
        Commons.click(driver, By.xpath(locators.getProperty("group_membership_kind")));

        String tableXPath = locators.getProperty("table");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));

        boolean entryFound = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, groupMembershipKindUpdated);
        Assert.assertTrue(entryFound, "Expected entry with text '" + groupMembershipKindUpdated + "' not found");

        String rowCheckboxXPath = "//tr[td[contains(text(),'" + groupMembershipKindUpdated + "')]]//input[@type='checkbox']";
        Commons.click(driver, By.xpath(rowCheckboxXPath));
        Commons.click(driver, By.xpath(locators.getProperty("actions")));
        Commons.click(driver, By.xpath(locators.getProperty("delete")));
        Commons.click(driver, By.xpath(locators.getProperty("delete_confirmation")));

        By deletedEntry = By.xpath("//tr[td[contains(text(),'" + groupMembershipKindUpdated + "')]]");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(deletedEntry));

        boolean entryStillExists = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, groupMembershipKindUpdated);
        Assert.assertFalse(entryStillExists, "Entry with text '" + groupMembershipKindUpdated + "' should be deleted but still exists.");
    }
}
