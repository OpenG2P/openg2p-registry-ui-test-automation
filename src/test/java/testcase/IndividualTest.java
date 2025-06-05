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
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class IndividualTest extends BaseLogin {

    @Test(priority = 1)
    void individualCreation() throws IOException, InterruptedException {
        login();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        String familyName = testData.getFamilyName();
        String givenName = testData.getGivenName();
        String additionalName = testData.getAdditionalName();
        Commons.click(driver, By.xpath(locators.getProperty("individuals")));
        Commons.click(driver, By.xpath(locators.getProperty("new_button")));
        Commons.enter(driver, By.id(locators.getProperty("family_name")), familyName);
        Commons.enter(driver, By.id(locators.getProperty("given_name")), givenName);
        Commons.enter(driver, By.id(locators.getProperty("additional_name")), additionalName);
        Commons.dropDownByValue(driver, By.id(locators.getProperty("tags")), testData.getTags());
        Commons.enter(driver, By.id(locators.getProperty("address")), testData.getAddress());
        Commons.enter(driver, By.id(locators.getProperty("email")), testData.getIndividualEmail());
        Commons.dropDownByValue(driver, By.id(locators.getProperty("district_dropdown")),testData.getDistrict());
        Commons.dropDownByValue(driver, By.id(locators.getProperty("region_dropdown")), testData.getRegion());
        Commons.enter(driver,By.id(locators.getProperty("birth_place")), testData.getAddress());
        Commons.enter(driver,By.id(locators.getProperty("date_of_birth")),testData.getDOB());
        Commons.enter(driver,By.id(locators.getProperty("civil_status")), testData.getCivilStatus());
        Commons.enter(driver,By.id(locators.getProperty("occupation")),testData.getOccupation());
        Commons.enter(driver,By.id(locators.getProperty("income")),testData.getIncome());
        Commons.dropDownByValue(driver,By.id(locators.getProperty("gender_dropdown")), testData.getGender());
//        Commons.click(driver, By.xpath(locators.getProperty("add_a_line")));
//        Commons.enter(driver, By.xpath(locators.getProperty("phone_number")),"");
//        Commons.click(driver, By.xpath(locators.getProperty("save&close")));
        Commons.click(driver,By.xpath(locators.getProperty("save")));
        Commons.click(driver, By.xpath(locators.getProperty("individuals")));
        String individualName = familyName + ", " + givenName + " " + additionalName;
        String tableXPath = locators.getProperty("individual_table");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryFound = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, individualName);
        Assert.assertTrue(entryFound, "Expected entry with text '" + individualName + "' not found");

    }


    @Test(priority = 2, dependsOnMethods = {"individualCreation"})
    void individualUpdation() throws IOException, InterruptedException {
        login();
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(2));
        String familyName = testData.getFamilyName();
        String givenName = testData.getGivenName();
        String additionalName = testData.getAdditionalName();
        String givenNameUpdated = testData.getGivenNameUpdated();
        Commons.click(driver, By.xpath(locators.getProperty("individuals")));
        String individualName = familyName+ ", " + givenName + " " + additionalName;
        String tableXPath = locators.getProperty("individual_table");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryClicked = Commons.clickEntryInPaginatedTable(driver, tableXPath, individualName);
        Assert.assertTrue(entryClicked, "Expected entry '" + individualName + "' not found and clicked.");
        Commons.enter(driver, By.id(locators.getProperty("given_name")), givenNameUpdated);
        Commons.click(driver,By.xpath(locators.getProperty("save")));
        Commons.click(driver, By.xpath(locators.getProperty("individuals")));
        String individualNameUpdated = familyName+ ", " + givenNameUpdated + " " + additionalName;
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryFound = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, individualNameUpdated);
        Assert.assertTrue(entryFound, "Expected entry with text '" + individualNameUpdated + "' not found");

    }

    @Test(priority = 3, dependsOnMethods = {"individualUpdation"})
    void individualDeletion() throws IOException, InterruptedException {
        login();
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(2));
        String familyName = testData.getFamilyName();
        String additionalName = testData.getAdditionalName();
        String givenNameUpdated = testData.getGivenNameUpdated();
        Commons.click(driver, By.xpath(locators.getProperty("individuals")));
        String individualNameUpdated = familyName+ ", " + givenNameUpdated + " " + additionalName;
        String tableXPath = locators.getProperty("individual_table");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryFound = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, individualNameUpdated);
        Assert.assertTrue(entryFound, "Expected entry with text '" + individualNameUpdated + "' not found");
        String rowCheckboxXPath = "//tr[td[contains(text(),'" + individualNameUpdated + "')]]//input[@type='checkbox']";
        Commons.click(driver, By.xpath(rowCheckboxXPath));
        Commons.click(driver,By.xpath(locators.getProperty("actions")));
        Commons.click(driver,By.xpath(locators.getProperty("delete")));
        Commons.click(driver,By.xpath(locators.getProperty("delete_confirmation")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryStillExists = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, individualNameUpdated);
        Assert.assertFalse(entryStillExists, "Entry with text '" + individualNameUpdated + "' should be deleted but still exists.");
    }

}
