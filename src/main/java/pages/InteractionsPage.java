package pages;

import base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ConfigReader;

import java.util.List;
import java.util.stream.Collectors;

public class InteractionsPage extends BasePage {

    public InteractionsPage(WebDriver driver, com.aventstack.extentreports.ExtentTest test) {
        super(driver, test);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[text()='Sortable']")
    WebElement sortableTab;

    @FindBy(xpath = "//span[text()='Selectable']")
    WebElement selectableTab;

    @FindBy(xpath = "//span[text()='Resizable']")
    WebElement resizableTab;

    @FindBy(xpath = "//span[text()='Droppable']")
    WebElement droppableTab;

    @FindBy(xpath = "//span[text()='Dragabble']")
    WebElement draggableTab;

    @FindBy(xpath = "//div[contains(@class, 'vertical-list')]//div[text()='One']")
    WebElement sortableSource;

    @FindBy(xpath = "//div[contains(@class, 'vertical-list')]//div[text()='Four']")
    WebElement sortableTarget;

    @FindBy(xpath = "//ul[@id='verticalListContainer']//li")
    List<WebElement> selectableItems;

    @FindBy(xpath = "//*[@id='resizableBoxWithRestriction']/span")
    WebElement resizeHandle;

    @FindBy(xpath = "//*[@id='resizableBoxWithRestriction']")
    WebElement resizableBox;

    @FindBy(id = "draggable")
    WebElement dragSource;

    @FindBy(id = "droppable")
    WebElement dropTarget;

    @FindBy(id = "dragBox")
    WebElement dragBox;

    @FindBy(xpath = "//li[text()='Porta ac consectetur ac']")
    WebElement selectableValue;

    @FindBy(xpath = "//p[text()='Dropped!']")
    WebElement dropped;

    // ---------- Methods ----------
    public boolean openInteractionsPage() {
        String url = ConfigReader.get("baseUrl");
        driver.get(url);
        uti.removeAds(driver);

        logPass("Opened 'Interactions' section successfully ✅");
        return driver.getCurrentUrl().contains("interaction") || driver.getTitle().contains("ToolsQA");
    }

    public boolean verifyActionsList(List<String> expectedActions) {
        actions.scrollIntoView(draggableTab);
        List<WebElement> elements = driver.findElements(
                By.xpath("//div[text()='Interactions']//ancestor::div[@class='element-group']//li//span"));
        List<String> actualList = elements.stream().map(WebElement::getText).map(String::trim).collect(Collectors.toList());

        logInfo("Actual actions list: " + actualList);
        return actualList.equals(expectedActions);
    }

    public boolean performSortable() throws InterruptedException {
        actions.scrollIntoView(draggableTab);
        actions.click(sortableTab);

        List<String> beforeSort = driver.findElements(
                        By.xpath("//div[contains(@class, 'vertical-list')]//div"))
                .stream().map(WebElement::getText).collect(Collectors.toList());

        actions.waitForClickAbility(sortableSource);
        actions.scrollIntoView(sortableTarget);

        actions.performAction("sortable", sortableSource, sortableTarget);

        Thread.sleep(2000);

        List<String> afterSort = driver.findElements(
                        By.xpath("//div[contains(@class, 'vertical-list')]//div"))
                .stream().map(WebElement::getText).collect(Collectors.toList());

        boolean changed = !beforeSort.equals(afterSort);
        if (changed) logPass("Sortable verified successfully ✅");
        else logFail("Sortable failed ❌");
        return changed;
    }

    public boolean performSelectable() {
        actions.scrollIntoView(selectableTab);
        actions.click(selectableTab);
        actions.scrollIntoView(selectableValue);

        for (WebElement item : selectableItems) {
            actions.performAction("select", null, item);
        }

        boolean selected = !selectableItems.isEmpty();
        if (selected) logPass("Selectable items clicked successfully ✅");
        else logFail("Selectable failed ❌");
        return selected;
    }

    public boolean performResizable() {
        actions.scrollIntoView(resizableTab);
        actions.click(resizableTab);

        Dimension before = resizableBox.getSize();
        logInfo("Resizable box size before: " + before);

        actions.scrollIntoView(resizeHandle);
        actions.performAction("resizeable", null, resizeHandle);

        Dimension after = resizableBox.getSize();
        logInfo("Resizable box size after: " + after);

        boolean resized = after.getWidth() > before.getWidth() && after.getHeight() > before.getHeight();
        if (resized) logPass("Resizable verified successfully ✅");
        else logFail("Resizable failed ❌");
        return resized;
    }

    public boolean performDroppable() throws InterruptedException {
        actions.click(droppableTab);
        actions.scrollIntoView(draggableTab);
        Thread.sleep(1000);
        actions.performAction("dragAndDrop", dragSource, dropTarget);

        try {
            actions.waitForVisibility(dropped);
            boolean success = dropped.isDisplayed();
            if (success) logPass("Droppable verified successfully ✅");
            else logFail("Droppable failed ❌");
            return success;
        } catch (NoSuchElementException e) {
            logFail("Droppable failed ❌");
            return false;
        }
    }

    public boolean performDraggable() throws InterruptedException {
        actions.scrollIntoView(draggableTab);
        actions.click(draggableTab);
        Thread.sleep(1000);
        actions.scrollIntoView(draggableTab);

        Point before = dragBox.getLocation();
        Thread.sleep(2000);
        actions.performAction("dragabble", dragBox, null);
        Point after = dragBox.getLocation();

        boolean moved = after.getX() > before.getX();
        if (moved) logPass("Draggable verified successfully ✅");
        else logFail("Draggable failed ❌");
        return moved;
    }
}