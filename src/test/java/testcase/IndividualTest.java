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
public class IndividualTest extends BaseLogin {

    private String getIndividualFullName(String family, String given, String additional) {
        return family + ", " + given + " " + additional;
    }

    @Test(priority = 1)
    void individualCreation() throws IOException, InterruptedException {
        WebDriver driver = DriverManager.getDriver();
        Properties locators = getLocators();
        login();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String familyName = testData.getFamilyName();
        String givenName = testData.getGivenName();
        String additionalName = testData.getAdditionalName();
        String fullName = getIndividualFullName(familyName, givenName, additionalName);

        Commons.click(driver, By.xpath(locators.getProperty("individuals")));
        Commons.click(driver, By.xpath(locators.getProperty("create_button")));
        Commons.enter(driver, By.id(locators.getProperty("family_name")), familyName);
        Commons.enter(driver, By.id(locators.getProperty("given_name")), givenName);
        Commons.enter(driver, By.id(locators.getProperty("additional_name")), additionalName);
        Commons.dropDownByValue(driver, By.id(locators.getProperty("tags")), testData.getTags());
        Commons.enter(driver, By.id(locators.getProperty("address")), testData.getAddress());
        Commons.enter(driver, By.id(locators.getProperty("email")), testData.getIndividualEmail());
        Commons.dropDownByValue(driver, By.id(locators.getProperty("district_dropdown")), testData.getDistrict());
        Commons.dropDownByValue(driver, By.id(locators.getProperty("region_dropdown")), testData.getRegion());
        Commons.enter(driver, By.id(locators.getProperty("birth_place")), testData.getAddress());
        Commons.enter(driver, By.id(locators.getProperty("date_of_birth")), testData.getDOB());
        Commons.enter(driver, By.id(locators.getProperty("civil_status")), testData.getCivilStatus());
        Commons.enter(driver, By.id(locators.getProperty("occupation")), testData.getOccupation());
        Commons.enter(driver, By.id(locators.getProperty("income")), testData.getIncome());
        Commons.dropDownByValue(driver, By.id(locators.getProperty("gender_dropdown")), testData.getGender());
        Commons.click(driver, By.xpath(locators.getProperty("save")));
        Commons.click(driver, By.xpath(locators.getProperty("individuals")));

        String tableXPath = locators.getProperty("table");
        By newEntry = By.xpath("//tr[td[contains(text(),'" + fullName + "')]]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(newEntry));
        boolean entryFound = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, fullName);
        Assert.assertTrue(entryFound, "Expected entry with text '" + fullName + "' not found");
    }

    @Test(priority = 2, dependsOnMethods = {"individualCreation"})
    void individualUpdation() throws IOException, InterruptedException {
        WebDriver driver = DriverManager.getDriver();
        Properties locators = getLocators();
        login();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String familyName = testData.getFamilyName();
        String givenName = testData.getGivenName();
        String additionalName = testData.getAdditionalName();
        String givenNameUpdated = testData.getGivenNameUpdated();

        String fullName = getIndividualFullName(familyName, givenName, additionalName);
        String updatedFullName = getIndividualFullName(familyName, givenNameUpdated, additionalName);

        Commons.click(driver, By.xpath(locators.getProperty("individuals")));
        String tableXPath = locators.getProperty("table");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));

        boolean entryClicked = Commons.clickEntryInPaginatedTable(driver, tableXPath, fullName);
        Assert.assertTrue(entryClicked, "Expected entry '" + fullName + "' not found and clicked.");

        Commons.enter(driver, By.id(locators.getProperty("given_name")), givenNameUpdated);
        Commons.click(driver, By.xpath(locators.getProperty("save")));
        Commons.click(driver, By.xpath(locators.getProperty("individuals")));

        By updatedRow = By.xpath("//tr[td[contains(text(),'" + updatedFullName + "')]]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(updatedRow));

        boolean entryFound = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, updatedFullName);
        Assert.assertTrue(entryFound, "Expected entry with text '" + updatedFullName + "' not found");
    }

    @Test(priority = 3, dependsOnMethods = {"individualUpdation"})
    void individualDeletion() throws IOException, InterruptedException {
        WebDriver driver = DriverManager.getDriver();
        Properties locators = getLocators();
        login();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String familyName = testData.getFamilyName();
        String givenNameUpdated = testData.getGivenNameUpdated();
        String additionalName = testData.getAdditionalName();
        String updatedFullName = getIndividualFullName(familyName, givenNameUpdated, additionalName);

        Commons.click(driver, By.xpath(locators.getProperty("individuals")));
        String tableXPath = locators.getProperty("table");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));

        boolean entryFound = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, updatedFullName);
        Assert.assertTrue(entryFound, "Expected entry with text '" + updatedFullName + "' not found");

        String rowCheckboxXPath = "//tr[td[contains(text(),'" + updatedFullName + "')]]//input[@type='checkbox']";
        Commons.click(driver, By.xpath(rowCheckboxXPath));
        Commons.click(driver, By.xpath(locators.getProperty("actions")));
        Commons.click(driver, By.xpath(locators.getProperty("delete")));
        Commons.click(driver, By.xpath(locators.getProperty("delete_confirmation")));

        By deletedEntry = By.xpath("//tr[td[contains(text(),'" + updatedFullName + "')]]");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(deletedEntry));

        boolean entryStillExists = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, updatedFullName);
        Assert.assertFalse(entryStillExists, "Entry with text '" + updatedFullName + "' should be deleted but still exists.");
    }
}
