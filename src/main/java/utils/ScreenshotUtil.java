package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtil {

    public static String takeScreenshot(WebDriver driver, String name) {
        try {
            // Ensure the browser window is visible
            driver.manage().window().maximize();
            ((JavascriptExecutor) driver).executeScript("window.focus();");

            // Capture screenshot
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

            String testClassName = "UnknownTest";
            for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
                if (element.getClassName().contains("tests.")) {
                    testClassName = element.getClassName().substring(element.getClassName().lastIndexOf('.') + 1);
                    break;
                }
            }

            //Build directory path using class name
            String destDir = "target/ExtentReports/screenshots/" + testClassName + "/";
            Files.createDirectories(Paths.get(destDir));

            //File name with timestamp
            String fileName = name + "_" + timestamp + ".png";
            Path destPath = Paths.get(destDir + fileName);

            // Save the screenshot
            Files.copy(src.toPath(), destPath);
            System.out.println("Screenshot saved at: " + destPath);
            return "screenshots/" + testClassName + "/" + fileName;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}