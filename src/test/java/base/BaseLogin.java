package base;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.Set;

public class BaseLogin extends DriverCreator{


    public static Properties properties = new Properties();

    public static Properties locators = new Properties();

    public static FileReader fileReader1;
    public static FileReader fileReader2;

    public static void login() throws IOException, InterruptedException {

        fileReader1 = new FileReader("testconfigs/configfile/config.properties");
        fileReader2 = new FileReader("src/main/resources/configfiles/locators.properties");
        properties.load(fileReader1);
        locators.load(fileReader2);

        WebElement socialRegistry = driver.findElement(By.xpath(locators.getProperty("social_registry")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", socialRegistry);
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelectorAll('a[target=\"_blank\"]').forEach(el => el.setAttribute('target', '_self'));");
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", socialRegistry);

        WebElement socialRegistryAdmin = driver.findElement(By.xpath(locators.getProperty("social_registry_admin")));
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelectorAll('a[target=\"_blank\"]').forEach(el => el.setAttribute('target', '_self'));");
        socialRegistryAdmin.click();
        driver.findElement(By.name(locators.getProperty("username_field"))).sendKeys(properties.getProperty("username"));
        driver.findElement(By.id(locators.getProperty("password_field"))).sendKeys(properties.getProperty("password"));
        driver.findElement(By.xpath(locators.getProperty("login_button"))).click();

    }


    public static void loginPage() throws IOException, InterruptedException {
        fileReader1 = new FileReader("testconfigs/configfile/config.properties");
        fileReader2 = new FileReader("src/main/resources/configfiles/locators.properties");
        properties.load(fileReader1);
        locators.load(fileReader2);

        WebElement socialRegistry = driver.findElement(By.xpath(locators.getProperty("social_registry")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", socialRegistry);
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelectorAll('a[target=\"_blank\"]').forEach(el => el.setAttribute('target', '_self'));");
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", socialRegistry);

        WebElement socialRegistryAdmin = driver.findElement(By.xpath(locators.getProperty("social_registry_admin")));
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelectorAll('a[target=\"_blank\"]').forEach(el => el.setAttribute('target', '_self'));");
        socialRegistryAdmin.click();
    }
}



