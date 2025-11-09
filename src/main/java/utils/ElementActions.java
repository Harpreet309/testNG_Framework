package utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class ElementActions {
     private final WebDriver driver;
     private final WebDriverWait wait;
     private final JavascriptExecutor js;
     private final ExtentTest test;

    public ElementActions(WebDriver driver, ExtentTest test){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // created here
        this.js = (JavascriptExecutor) driver;
        this.test = test;
    }

    public void log(Status status, String message) {
        if (test != null)
            test.log(status, message);
        else
            System.out.println(status + ": " + message);
    }

    public void sendKeys(WebElement element, String input) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).sendKeys(input);
        log(Status.INFO, "Entered value: " + input);
    }

    public void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        log(Status.INFO, "Clicked element: " + element.toString());
    }

    public void selectBy(WebElement element, String input) {
        Select select = new Select(element);
        select.selectByVisibleText(input);
        log(Status.INFO, "Selected: " + input);
    }

    public void scrollIntoView(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void jsClick(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }

    public void waitForVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForClickAbility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void selectSubjects(WebElement subjectsElement, String input) throws InterruptedException {
        String[] subject = input.trim().split(",");
        for(String sub: subject) {
            subjectsElement.sendKeys(sub.trim());
            Thread.sleep(200);
            subjectsElement.sendKeys(Keys.ENTER);
        }
    }

    public String[] makeArray(String input) {
        return input.split(",");
    }

    public void performAction(String actionType, WebElement source, WebElement target) {
        Actions action = new Actions(driver);

        switch (actionType) {
            case "sortable":
                action.clickAndHold(source).moveToElement(target).release().perform();
                break;

            case "dragAndDrop":
                action.dragAndDrop(source, target).build().perform();
                break;

            case "select":
                action.click(target).perform();
                break;

            case "resizeable":
                action.clickAndHold(target).moveByOffset(150, 100).release().perform();
                break;

            case "dragabble":
                action.dragAndDropBy(source, 300, 0).perform();
                break;

            case "scrollTo":
                action.scrollToElement(target).perform();
                break;

            default:
                throw new RuntimeException("Invalid action type: " + actionType);
        }

        log(Status.INFO, "Performed action: " + actionType);
    }
}
