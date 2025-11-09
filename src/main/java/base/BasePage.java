package base;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import utils.ElementActions;
import utils.ScreenshotUtil;
import utils.utilities;

public abstract class BasePage {
    protected WebDriver driver;
    protected ExtentTest test;
    protected utilities uti;
    protected ElementActions actions;
    protected ScreenshotUtil sc;

    public BasePage(WebDriver driver, ExtentTest test) {
        this.driver = driver;
        this.test = test;
        this.uti = new utilities();
        this.actions = new ElementActions(driver, test);
        PageFactory.initElements(driver, this);
    }


    public void logInfo(String message) {
        if (test != null) test.info(message);
    }

    public void logPass(String message) {
        if (test != null) test.pass(message);
    }

    public void logFail(String message) {
        if (test != null) test.fail(message);
    }
}
