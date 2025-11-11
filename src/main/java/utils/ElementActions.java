package utils;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Utility class to handle Selenium element-level interactions safely.
 * Does NOT extend BasePage — avoids recursive dependency.
 */
public class ElementActions {

    private final WebDriver driver;
    private final ExtentTest test;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;

    public ElementActions(WebDriver driver, ExtentTest test) {
        this.driver = driver;
        this.test = test;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }

    public void sendKeys(WebElement element, String input) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).sendKeys(input);
        log("Entered value: " + input);
    }

    public void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        log("Clicked element: " + element.toString());
    }

    public void selectByText(WebElement element, String input) {
        new Select(element).selectByVisibleText(input);
        log("Selected: " + input);
    }

    public void scrollIntoView(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        log("Scrolled into view: " + element.toString());
    }

    public void jsClick(WebElement element) {
        js.executeScript("arguments[0].click();", element);
        log("JS clicked element: " + element.toString());
    }

    public void waitForVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void selectSubjects(WebElement subjectsElement, String input) throws InterruptedException {
        for (String subject : input.trim().split(",")) {
            subjectsElement.sendKeys(subject.trim());
            Thread.sleep(200);
            subjectsElement.sendKeys(Keys.ENTER);
        }
        log("Subjects selected: " + input);
    }

    public void performAction(String actionType, WebElement source, WebElement target) {
        Actions action = new Actions(driver);
        switch (actionType.toLowerCase()) {
            case "sortable":
                action.clickAndHold(source).moveToElement(target).release().perform();
                break;
            case "draganddrop":
                action.dragAndDrop(source, target).perform();
                break;
            case "select":
                action.click(target).perform();
                break;
            case "resizeable":
                action.clickAndHold(target).moveByOffset(150, 100).release().perform();
                break;
            case "draggable":
                action.dragAndDropBy(source, 300, 0).perform();
                break;
            case "scrollto":
                action.scrollToElement(target).perform();
                break;
            default:
                throw new IllegalArgumentException("❌ Invalid action type: " + actionType);
        }
        log("Performed action: " + actionType);
    }

    public void waitFor(long milliseconds) throws InterruptedException {
        Thread.sleep(milliseconds);
        log("Waited for " + milliseconds + " ms");
    }

    public String[] makeArray(String input) {
        return input.trim().split(",");
    }

    private void log(String message) {
        if (test != null) test.log(com.aventstack.extentreports.Status.INFO, message);
    }
}