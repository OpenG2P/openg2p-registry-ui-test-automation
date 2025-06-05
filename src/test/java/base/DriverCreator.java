package base;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import utilities.TestData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DriverCreator {
    private static final Logger logger = LoggerFactory.getLogger(DriverCreator.class);
    public static WebDriver driver;
    public static Properties properties = new Properties();
    public static Properties locators = new Properties();
    public static Properties headless = new Properties();
    public static FileReader fileReader1;
    public static FileReader fileReader2;
    public  static  FileReader fileReader3;
    public static TestData testData;
    @BeforeClass
    public void importTestData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        testData = objectMapper.readValue(new File("testconfigs/testdata/testdata.json"), TestData.class);
    }

    @BeforeMethod
    public void setup() throws IOException {
        logger.info("setup action been intiated");
        if (driver == null) {
            fileReader1 = new FileReader("testconfigs/configfile/config.properties");
            fileReader2 = new FileReader("src/main/resources/configfiles/locators.properties");
            fileReader3 = new FileReader("testconfigs/configfile/headless.properties");
            properties.load(fileReader1);
            locators.load(fileReader2);
            headless.load(fileReader3);
        }

        if (properties.getProperty("browser").equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            if (headless.getProperty("headless").equalsIgnoreCase("true")) {
                options.addArguments("--headless");
            }
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);
        } else if (properties.getProperty("browser").equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            if (headless.getProperty("headless").equalsIgnoreCase("true")) {
                options.addArguments("--headless");
            }
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver(options);
        } else if (properties.getProperty("browser").equalsIgnoreCase("edge")) {
            EdgeOptions options = new EdgeOptions();
            if (headless.getProperty("headless").equalsIgnoreCase("true")) {
                options.addArguments("--headless");
            }
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver(options);
        }
        driver.get(properties.getProperty("openg2purl"));
        driver.manage().window().maximize();
        logger.info("driver has been created successfully");
    }

    @AfterMethod
    public void tearDown() {

        driver.close();
    }

}
