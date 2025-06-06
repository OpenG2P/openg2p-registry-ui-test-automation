package listeners;

import base.DriverCreator;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.ScreenshotUtil;

public class TestListener implements ITestListener {

    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("FAILED: {}", result.getName());
        WebDriver driver = DriverCreator.driver;

        if (driver != null) {
            ScreenshotUtil.attachScreenshotToAllure(driver, result.getName());
            logger.error("Screenshot attached to Allure report");
        } else {
            logger.error("Driver is null; cannot capture screenshot");
        }
    }

}
