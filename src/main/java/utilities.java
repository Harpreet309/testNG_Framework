import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class utilities {

    public void removeAds(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelectorAll('[id*=\"ad\"],[class*=\"ad\"],[id*=\"banner\"],[class*=\"banner\"],iframe,#fixedban').forEach(e=>e.remove());"
        );
    }

    public void actionsMethods(String input, WebDriver driver, WebElement source, WebElement target) throws InterruptedException {
        Actions action = new Actions(driver);
        switch (input) {
            case "sortable":
                action.
                        clickAndHold(source)
                        .moveToElement(target)
                        .release()
                        .perform();
                break;

            case "select":
                action
                        .click(target)
                        .perform();
                break;

            case "resizeable":
                action
                        .clickAndHold(source)
                        .moveByOffset(150, 100)
                        .release()
                        .perform();
                break;

            case "dragAndDrop":
                action
                        .clickAndHold(source)
                        .moveToElement(target)
                        .release()
                        .build()
                        .perform();
                break;

            case "dragabble":
                action
                        .dragAndDropBy(source, 300, 0)
                        .perform();
                break;

            case "scrollTo":
                action
                        .scrollToElement(target)
                        .perform();
                break;

                default:
                    throw new RuntimeException("Invalid Input");



        }
    }
}
