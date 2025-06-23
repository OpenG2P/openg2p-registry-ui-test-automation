package base;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import utilities.ScreenshotUtil;
import utilities.TestData;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DriverCreator {
    private  final Logger logger = LoggerFactory.getLogger(DriverCreator.class);

    public  Properties properties = new Properties();
    public  Properties locators = new Properties();
    public  Properties headless = new Properties();
    public  FileReader fileReader1;
    public  FileReader fileReader2;
    public  FileReader fileReader3;
    public  TestData testData;

    @BeforeClass
    public void importTestData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        testData = objectMapper.readValue(new File("testconfigs/testdata/testdata.json"), TestData.class);
    }

    @BeforeMethod
    public void setup() throws IOException {
        logger.info("Setup action initiated");

        if (properties.isEmpty()) {
            fileReader1 = new FileReader("testconfigs/configfile/config.properties");
            fileReader2 = new FileReader("src/main/resources/configfiles/locators.properties");
            fileReader3 = new FileReader("testconfigs/configfile/headless.properties");
            properties.load(fileReader1);
            locators.load(fileReader2);
            headless.load(fileReader3);
        }

        WebDriver driver;

        if (properties.getProperty("browser").equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            boolean isHeadless = headless.getProperty("headless").equalsIgnoreCase("true");
            if (isHeadless) {
                options.addArguments("--headless");
                options.addArguments("--window-size=1920,1080");
                options.addArguments("--force-device-scale-factor=1");
                options.addArguments("--disable-gpu");
            }

            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);

            if (isHeadless) {
                driver.manage().window().setSize(new Dimension(1920, 1080));
            } else {
                driver.manage().window().maximize();
            }

        } else if (properties.getProperty("browser").equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            if (headless.getProperty("headless").equalsIgnoreCase("true")) {
                options.addArguments("--headless");
            }

            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver(options);
            driver.manage().window().maximize();

        } else if (properties.getProperty("browser").equalsIgnoreCase("edge")) {
            EdgeOptions options = new EdgeOptions();
            if (headless.getProperty("headless").equalsIgnoreCase("true")) {
                options.addArguments("--headless");
            }

            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver(options);
            driver.manage().window().maximize();

        } else {
            throw new IllegalArgumentException("Unsupported browser: " + properties.getProperty("browser"));
        }

        DriverManager.setDriver(driver);
        driver.get(properties.getProperty("openg2purl"));
        logger.info("Driver has been created successfully");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        WebDriver driver = DriverManager.getDriver();
        if (result.getStatus() == ITestResult.FAILURE) {
            ScreenshotUtil.attachScreenshotToAllure(driver, result.getName());
        }

        DriverManager.quitDriver();
    }
}
