package base;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.edge.*;
import org.openqa.selenium.firefox.*;
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

    private final Logger logger = LoggerFactory.getLogger(DriverCreator.class);

    protected static final ThreadLocal<TestData> threadTestData = new ThreadLocal<>();
    protected static final ThreadLocal<Properties> threadProperties = ThreadLocal.withInitial(Properties::new);
    protected static final ThreadLocal<Properties> threadLocators = ThreadLocal.withInitial(Properties::new);
    protected static final ThreadLocal<Properties> threadHeadless = ThreadLocal.withInitial(Properties::new);

    @BeforeClass
    public void importTestData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        TestData data = objectMapper.readValue(new File("testconfigs/testdata/testdata.json"), TestData.class);
        threadTestData.set(data);
    }

    @BeforeMethod
    public void setup() throws IOException {
        logger.info("Setup action initiated");

        Properties properties = new Properties();
        Properties locators = new Properties();
        Properties headless = new Properties();

        try (
                FileReader fileReader1 = new FileReader("testconfigs/configfile/config.properties");
                FileReader fileReader2 = new FileReader("src/main/resources/configfiles/locators.properties");
                FileReader fileReader3 = new FileReader("testconfigs/configfile/headless.properties")
        ) {
            properties.load(fileReader1);
            locators.load(fileReader2);
            headless.load(fileReader3);
        }

        threadProperties.set(properties);
        threadLocators.set(locators);
        threadHeadless.set(headless);

        WebDriver driver;

        String browser = properties.getProperty("browser");
        boolean isHeadless = headless.getProperty("headless").equalsIgnoreCase("true");

        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (isHeadless) {
                    chromeOptions.addArguments("--headless=new", "--window-size=1920,1080", "--disable-gpu");
                }
                chromeOptions.addArguments("--incognito");
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (isHeadless) {
                    firefoxOptions.addArguments("--headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (isHeadless) {
                    edgeOptions.addArguments("--headless");
                }
                driver = new EdgeDriver(edgeOptions);
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        if (isHeadless) {
            driver.manage().window().setSize(new Dimension(1920, 1080));
        } else {
            driver.manage().window().maximize();
        }

        DriverManager.setDriver(driver);
        driver.get(properties.getProperty("openg2purl"));
        logger.info("Driver created and navigated to base URL");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        WebDriver driver = DriverManager.getDriver();
        if (result.getStatus() == ITestResult.FAILURE) {
            ScreenshotUtil.attachScreenshotToAllure(driver, result.getName());
        }
        DriverManager.quitDriver();
    }

    protected TestData getTestData() {
        return threadTestData.get();
    }

    protected Properties getConfig() {
        return threadProperties.get();
    }

    protected Properties getLocators() {
        return threadLocators.get();
    }
}
