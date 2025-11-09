package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtil {

    public static String takeScreenshot(WebDriver driver, String name) {
        try {
            driver.manage().window().maximize();
            ((JavascriptExecutor) driver).executeScript("window.focus();");
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String destDir = "target/ExtentReports/screenshots/";
            Files.createDirectories(Paths.get(destDir));
            String dest = destDir + name + "_" + timestamp + ".png";
            Files.copy(src.toPath(), Paths.get(dest));
            return dest;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}