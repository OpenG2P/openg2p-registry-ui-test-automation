package utilities;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtil {

    public static void attachScreenshotToAllure(WebDriver driver, String testName) {
        try {
            byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);


            Allure.getLifecycle().addAttachment(
                    testName + "_screenshot",
                    "image/png",
                    ".png",
                    screenshotBytes
            );

            System.out.println("✅ Screenshot attached for test: " + testName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
