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

public class GenderTypeTest extends BaseLogin {

    @Test(priority = 1)
    void genderTypeCreation() throws IOException, InterruptedException {
        WebDriver driver = DriverManager.getDriver();
        Properties locators = getLocators();
        login();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String gender = testData.getGender();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("registry_configuration"))));
        Commons.click(driver, By.xpath(locators.getProperty("registry_configuration")));
        Commons.click(driver, By.xpath(locators.getProperty("gender_type")));
        Commons.click(driver, By.xpath(locators.getProperty("create_button")));

        Commons.enter(driver, By.xpath(locators.getProperty("gender_type_data_input_code")), gender);
        Commons.enter(driver, By.xpath(locators.getProperty("gender_type_data_input_value")), gender);
        Commons.click(driver, By.xpath(locators.getProperty("save_button")));

        String tableXPath = locators.getProperty("gender_table");
        By newEntry = By.xpath("//tr[td[contains(text(),'" + gender + "')]]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(newEntry));

        boolean entryFound = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, gender);
        Assert.assertTrue(entryFound, "Expected entry with text '" + gender + "' not found");
    }

    @Test(priority = 2, dependsOnMethods = {"genderTypeCreation"})
    void genderTypeUpdation() throws IOException, InterruptedException {
        WebDriver driver = DriverManager.getDriver();
        Properties locators = getLocators();
        login();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String gender = testData.getGender();
        String genderUpdated = testData.getGenderUpdated();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("registry_configuration"))));
        Commons.click(driver, By.xpath(locators.getProperty("registry_configuration")));
        Commons.click(driver, By.xpath(locators.getProperty("gender_type")));

        String tableXPath = locators.getProperty("gender_table");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));

        boolean entryFound = Commons.clickEntryInPaginatedTable(driver, tableXPath, gender);
        Assert.assertTrue(entryFound, "Expected entry with text '" + gender + "' not found");

        Commons.clearAndEnter(driver, By.xpath(locators.getProperty("gender_type_data_input_code")), genderUpdated);
        Commons.clearAndEnter(driver, By.xpath(locators.getProperty("gender_type_data_input_value")), genderUpdated);
        Commons.click(driver, By.xpath(locators.getProperty("save_update")));

        By updatedEntry = By.xpath("//tr[td[contains(text(),'" + genderUpdated + "')]]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(updatedEntry));

        boolean entryUpdateFound = Commons.clickEntryInPaginatedTable(driver, tableXPath, genderUpdated);
        Assert.assertTrue(entryUpdateFound, "Expected entry with text '" + genderUpdated + "' not found");
    }

    @Test(priority = 3, dependsOnMethods = {"genderTypeUpdation"})
    void genderTypeDeletion() throws IOException, InterruptedException {
        WebDriver driver = DriverManager.getDriver();
        Properties locators = getLocators();
        login();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String genderUpdated = testData.getGenderUpdated();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locators.getProperty("registry_configuration"))));
        Commons.click(driver, By.xpath(locators.getProperty("registry_configuration")));
        Commons.click(driver, By.xpath(locators.getProperty("gender_type")));

        String tableXPath = locators.getProperty("gender_table");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));

        boolean entryFound = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, genderUpdated);
        Assert.assertTrue(entryFound, "Expected entry with text '" + genderUpdated + "' not found");

        String rowCheckboxXPath = "//tr[td[contains(text(),'" + genderUpdated + "')]]//input[@type='checkbox']";
        Commons.click(driver, By.xpath(rowCheckboxXPath));
        Commons.click(driver, By.xpath(locators.getProperty("actions")));
        Commons.click(driver, By.xpath(locators.getProperty("delete")));
        Commons.click(driver, By.xpath(locators.getProperty("delete_confirmation")));

        By deletedEntry = By.xpath("//tr[td[contains(text(),'" + genderUpdated + "')]]");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(deletedEntry));

        boolean entryStillExists = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, genderUpdated);
        Assert.assertFalse(entryStillExists, "Entry with text '" + genderUpdated + "' should be deleted but still exists.");
    }
}
