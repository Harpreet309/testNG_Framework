package base;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import utils.ElementActions;
import utils.ScreenshotUtil;
import utils.utilities;

/**
 * Base class for all Page Object classes.
 * Handles driver setup, Extent logging, utilities, and page initialization.
 */
public abstract class BasePage {

    protected WebDriver driver;
    protected ExtentTest test;
    protected utilities uti;
    protected ElementActions actions;
    protected JavascriptExecutor js;

    public BasePage(WebDriver driver, ExtentTest test) {
        this.driver = driver;
        this.test = test;
        this.uti = new utilities();
        this.actions = new ElementActions(driver, test); // ✅ no recursion now
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    // --- Screenshot Utility ---
    protected void captureScreenshot(String name) {
        try {
            ScreenshotUtil.takeScreenshot(driver, name);
            System.out.println("📸 Screenshot captured: " + name);
        } catch (Exception e) {
            System.err.println("⚠️ Failed to capture screenshot: " + e.getMessage());
        }
    }

    // --- Logging Helpers ---
    protected void logInfo(String message) {
        if (test != null) test.info(message);
    }

    protected void logPass(String message) {
        if (test != null) test.pass(message);
    }

    protected void logFail(String message) {
        if (test != null) test.fail(message);
    }
}