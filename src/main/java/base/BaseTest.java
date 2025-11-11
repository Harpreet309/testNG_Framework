package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import factory.DriverFactory;
import listener.TestListener;
import models.FormData;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import utils.ExtentManager;
import utils.excel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BaseTest {

    protected WebDriver driver;
    protected ExtentReports extent;
    protected ExtentTest test;

    protected static FormData formData;
    protected static excel data = new excel();

    @BeforeSuite(alwaysRun = true)
    public void loadTestData() throws IOException {
        formData = data.getFormData();
        System.out.println("✅ Test data preloaded: " + formData);
    }

    @BeforeSuite(alwaysRun = true)
    public void setupReport() {
        // ✅ Initialize ExtentReports once for the entire suite
        extent = ExtentManager.getInstance();

        // ✅ Optional cleanup: delete old screenshots to keep folder clean
        try {
            Path screenshotsDir = Paths.get("target/ExtentReports/screenshots/");
            if (Files.exists(screenshotsDir)) {
                Files.walk(screenshotsDir)
                        .map(Path::toFile)
                        .forEach(File::delete);
                System.out.println("🧹 Old screenshots cleaned up successfully.");
            }
        } catch (IOException e) {
            System.err.println("⚠️ Unable to clean screenshots directory: " + e.getMessage());
        }
    }

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        boolean usesSelenium = !(this.getClass().getSimpleName().toLowerCase().contains("db")
                || this.getClass().getSimpleName().toLowerCase().contains("api"));

        try {
            if (usesSelenium) {
                driver = DriverFactory.driverInitialization();
                System.out.println("✅ WebDriver started for: " + this.getClass().getSimpleName());
            } else {
                System.out.println("ℹ️ No WebDriver required for: " + this.getClass().getSimpleName());
            }
        } catch (Exception e) {
            System.err.println("⚠️ WebDriver initialization failed for: " + this.getClass().getSimpleName());
            e.printStackTrace();
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void assignExtentTest() {
        // ✅ Assign the ExtentTest created by the listener for this test method
        test = TestListener.getTest();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("🧹 WebDriver closed for: " + this.getClass().getSimpleName());
        }
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownReport() {
        if (extent != null) {
            extent.flush();
            System.out.println("📘 Extent report flushed successfully.");
        }
    }
}