package listener;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ExtentManager;
import utils.ScreenshotUtil;

import java.io.File;
import java.lang.reflect.Field;

public class TestListener implements ITestListener {
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(
                result.getTestClass().getRealClass().getSimpleName() + " :: " + result.getMethod().getMethodName()
        );
        testThread.set(extentTest);

        // Assign to BaseTest.test
        Object instance = result.getInstance();
        try {
            Field f = instance.getClass().getSuperclass().getDeclaredField("test");
            f.setAccessible(true);
            f.set(instance, extentTest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        testThread.get().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = testThread.get();
        test.log(Status.FAIL, result.getThrowable());

        Object testClass = result.getInstance();
        try {
            Field f = testClass.getClass().getSuperclass().getDeclaredField("driver");
            f.setAccessible(true);
            Object driverObj = f.get(testClass);

            if (driverObj instanceof WebDriver) {
                WebDriver driver = (WebDriver) driverObj;
                String relativePath = ScreenshotUtil.takeScreenshot(driver, result.getMethod().getMethodName());

                if (relativePath != null) {
                    // ✅ Proper clickable thumbnail inside Extent report
                    test.fail("Screenshot on failure:",
                            MediaEntityBuilder.createScreenCaptureFromPath(relativePath).build());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        testThread.get().log(Status.SKIP, "Test skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    public static ExtentTest getTest() {
        return testThread.get();
    }

}