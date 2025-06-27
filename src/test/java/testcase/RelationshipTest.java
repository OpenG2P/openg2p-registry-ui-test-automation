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
public class RelationshipTest extends BaseLogin {

    @Test(priority = 1)
    void relationshipsCreation() throws IOException, InterruptedException {
        WebDriver driver = DriverManager.getDriver();
        login();
        Properties locators = getLocators();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String relation = testData.getRelationName();
        String relationInverse = testData.getRelationInverseName();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("registry_configuration"))));
        Commons.click(driver, By.xpath(locators.getProperty("registry_configuration")));
        Commons.click(driver, By.xpath(locators.getProperty("relationship")));
        Commons.click(driver, By.xpath(locators.getProperty("create_button")));
        Commons.enter(driver, By.xpath(locators.getProperty("relationship_name_data_input")), relation);
        Commons.enter(driver, By.xpath(locators.getProperty("relationship_inverse_name_data_input")), relationInverse);
        Commons.click(driver, By.xpath(locators.getProperty("relationship_bidirectional_checkbox")));
        Commons.dropDownByValue(driver, By.xpath(locators.getProperty("source_partner_type_dropdown")), "Group");
        Commons.dropDownByValue(driver, By.xpath(locators.getProperty("destination_partner_type_dropdown")), "Individual");
        Commons.click(driver, By.xpath(locators.getProperty("save_relationship")));

        String tableXPath = locators.getProperty("table");
        By newEntry = By.xpath("//tr[td[contains(text(),'" + relation + "')]]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(newEntry));

        boolean entryFound = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, relation);
        Assert.assertTrue(entryFound, "Expected entry with text '" + relation + "' not found");
    }

    @Test(priority = 2, dependsOnMethods = {"relationshipsCreation"})
    void relationshipUpdation() throws IOException, InterruptedException {
        WebDriver driver = DriverManager.getDriver();
        login();
        Properties locators = getLocators();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String relation = testData.getRelationName();
        String relationUpdated = testData.getRelationNameUpdated();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("registry_configuration"))));
        Commons.click(driver, By.xpath(locators.getProperty("registry_configuration")));
        Commons.click(driver, By.xpath(locators.getProperty("relationship")));

        String tableXPath = locators.getProperty("table");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));

        boolean entryFound = Commons.clickEntryInPaginatedTable(driver, tableXPath, relation);
        Assert.assertTrue(entryFound, "Expected entry with text '" + relation + "' not found");

        Commons.clearAndEnter(driver, By.xpath(locators.getProperty("relationship_name_data_input")), relationUpdated);
        Commons.click(driver, By.xpath(locators.getProperty("save_update")));

        By updatedEntry = By.xpath("//tr[td[contains(text(),'" + relationUpdated + "')]]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(updatedEntry));

        boolean entryUpdateFound = Commons.clickEntryInPaginatedTable(driver, tableXPath, relationUpdated);
        Assert.assertTrue(entryUpdateFound, "Expected entry with text '" + relationUpdated + "' not found");
    }

    @Test(priority = 3, dependsOnMethods = {"relationshipUpdation"})
    void relationshipDeletion() throws IOException, InterruptedException {
        WebDriver driver = DriverManager.getDriver();
        login();
        Properties locators = getLocators();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String relationUpdated = testData.getRelationNameUpdated();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("registry_configuration"))));
        Commons.click(driver, By.xpath(locators.getProperty("registry_configuration")));
        Commons.click(driver, By.xpath(locators.getProperty("relationship")));

        String tableXPath = locators.getProperty("table");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));

        boolean entryFound = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, relationUpdated);
        Assert.assertTrue(entryFound, "Expected entry with text '" + relationUpdated + "' not found");

        String rowCheckboxXPath = "//tr[td[contains(text(),'" + relationUpdated + "')]]//input[@type='checkbox']";
        Commons.click(driver, By.xpath(rowCheckboxXPath));
        Commons.click(driver, By.xpath(locators.getProperty("actions")));
        Commons.click(driver, By.xpath(locators.getProperty("delete")));
        Commons.click(driver, By.xpath(locators.getProperty("delete_confirmation")));

        By deletedEntry = By.xpath("//tr[td[contains(text(),'" + relationUpdated + "')]]");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(deletedEntry));

        boolean entryStillExists = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, relationUpdated);
        Assert.assertFalse(entryStillExists, "Entry with text '" + relationUpdated + "' should be deleted but still exists.");
    }
}
