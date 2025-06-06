package testcase;

import base.BaseLogin;
import base.DriverCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.Commons;
import utilities.TestData;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import static base.BaseLogin.login;
import static base.DriverCreator.driver;

public class IdTypeTest extends BaseLogin {

    String idTpye;
    @Test(priority = 1)
    void idTypeCreation() throws IOException, InterruptedException {
        login();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        String idType = testData.getIdType();
        Commons.click(driver, By.xpath(locators.getProperty("registry_configuration")));
        Commons.click(driver, By.xpath(locators.getProperty("id_type")));
        Commons.click(driver, By.xpath(locators.getProperty("create_button")));
        Commons.enter(driver, By.xpath(locators.getProperty("configurations_data_input")), idType);
        Commons.click(driver, By.xpath(locators.getProperty("id_type_save_button")));
        String tableXPath = locators.getProperty("idtype_table");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryFound = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, idType);
        Assert.assertTrue(entryFound, "Expected entry with text '" + idType + "' not found");
    }

    @Test(priority = 2, dependsOnMethods = {"idTypeCreation"})
    void idTypeUpdation() throws IOException, InterruptedException {
        login();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        String idType = testData.getIdType();
        String idTypeUpdated = testData.getIdTypeUpdated();
        Commons.click(driver, By.xpath(locators.getProperty("registry_configuration")));
        Commons.click(driver, By.xpath(locators.getProperty("id_type")));
        String tableXPath = locators.getProperty("idtype_table");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryFound = Commons.clickEntryInPaginatedTable(driver, tableXPath, idType);
        Assert.assertTrue(entryFound, "Expected entry with text '" + idType + "' not found");
        Commons.enter(driver,By.xpath(locators.getProperty("configurations_data_input")),idTypeUpdated);
        Commons.click(driver,By.xpath(locators.getProperty("save_update")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryUpdateFound = Commons.clickEntryInPaginatedTable(driver, tableXPath, idTypeUpdated);
        Assert.assertTrue(entryUpdateFound, "Expected entry with text '" + idTypeUpdated + "' not found");
    }

    @Test(priority = 3, dependsOnMethods = {"idTypeUpdation"})
    void idTypeDeletion() throws IOException, InterruptedException {
        login();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        String idTypeUpdated = testData.getIdTypeUpdated();
        Commons.click(driver, By.xpath(locators.getProperty("registry_configuration")));
        Commons.click(driver, By.xpath(locators.getProperty("id_type")));
        String tableXPath = locators.getProperty("idtype_table");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryFound = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, idTypeUpdated);
        Assert.assertTrue(entryFound, "Expected entry with text '" + idTypeUpdated + "' not found");
        String rowCheckboxXPath = "//tr[td[contains(text(),'" + idTypeUpdated + "')]]//input[@type='checkbox']";
        Commons.click(driver, By.xpath(rowCheckboxXPath));
        Commons.click(driver,By.xpath(locators.getProperty("actions")));
        Commons.click(driver,By.xpath(locators.getProperty("delete")));
        Commons.click(driver,By.xpath(locators.getProperty("delete_confirmation")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(tableXPath)));
        boolean entryStillExists = Commons.isEntryPresentInPaginatedTable(driver, tableXPath, idTypeUpdated);
        Assert.assertFalse(entryStillExists, "Entry with text '" + idTypeUpdated + "' should be deleted but still exists.");
    }
}

