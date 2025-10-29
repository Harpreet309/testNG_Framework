import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StandAloneTest extends baseDriver {
    private final utilities uti = new utilities();
    private static final List<String> actionsList = Arrays.asList("Sortable", "Selectable", "Resizable", "Droppable", "Dragabble");

    public StandAloneTest() {
        driver = driverInitialization();
    }

    public static void main(String[] args) throws InterruptedException {
        StandAloneTest test = new StandAloneTest();
        test.navigateToInteractions();
        test.assertActionsList(actionsList);
        test.sortable();
        test.Selectable();
        test.resizeAble();
        test.Droppable();
        test.dragabble();
        test.quitDriver();
    }

    public void navigateToInteractions() throws InterruptedException {
        driver.get("https://demoqa.com/");
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        uti.removeAds(driver);
        WebElement interactions = driver.findElement(By.xpath("//h5[text()='Interactions']"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(interactions));
        js.executeScript("document.getElementById('fixedban')?.remove();");
        js.executeScript("arguments[0].scrollIntoView({behavior:'smooth',block:'center'});", interactions);
        js.executeScript("arguments[0].click();", interactions);
        Thread.sleep(1000);
    }

    public void assertActionsList(List<String> actionsList) {
        List<WebElement> element = driver.findElements(By.xpath("//div[text()='Interactions']//ancestor::div[@class='element-group']//li//span"));
        List<String> actualList = element.stream().map(e -> e.getText().trim()).collect(Collectors.toList());
        System.out.println(actualList);
        Assert.assertEquals(actionsList, actualList);
    }

    public void sortable() throws InterruptedException {
        WebElement sortable = driver.findElement(By.xpath("//span[text()='Sortable']"));
        sortable.click();
        List<String> beforeSort = driver.findElements(By.xpath("//div[contains(@class, 'vertical-list')]//div")).stream().map(e -> e.getText().trim()).collect(Collectors.toList());
        WebElement source = driver.findElement(By.xpath("//div[contains(@class, 'vertical-list')]//div[text()='One']"));
        WebElement target = driver.findElement(By.xpath("//div[contains(@class, 'vertical-list')]//div[text()='Four']"));
        uti.actionsMethods("sortable", driver, source, target);
        List<String> afterSort = driver.findElements(By.xpath("//div[contains(@class, 'vertical-list')]//div")).stream().map(e -> e.getText().trim()).collect(Collectors.toList());
        Assert.assertNotEquals(beforeSort, afterSort);
    }

    public void Selectable() throws InterruptedException {
        WebElement selectable = driver.findElement(By.xpath("//span[text()='Selectable']"));
        selectable.click();
        WebElement scrollToEnd = driver.findElement(By.xpath("//*[text()='Book Store Application']"));
        uti.actionsMethods("scrollTo", driver, selectable, scrollToEnd);
        List<WebElement> optionsToSelect = driver.findElements(By.xpath("//ul[@id='verticalListContainer']//li[contains(@class, 'mt-2 list-group-item list-group-item-action')]"));
        for (WebElement element : optionsToSelect) {
            uti.actionsMethods("select", driver, selectable, element);
        }
        Assert.assertTrue(driver.findElements(By.xpath("//ul[@id='verticalListContainer']//li[contains(@class, 'mt-2 list-group-item list-group-item-action')]")).isEmpty());
    }

    public void resizeAble() throws InterruptedException {
        WebElement resizeAble = driver.findElement(By.xpath("//span[text()='Resizable']"));
        WebElement scrollToEnd = driver.findElement(By.xpath("//*[text()='Book Store Application']"));
        uti.actionsMethods("scrollTo", driver, resizeAble, scrollToEnd);
        resizeAble.click();
        WebElement source = driver.findElement(By.xpath("//*[@id='resizableBoxWithRestriction']/span"));
        WebElement resizeableBox = driver.findElement(By.xpath("//*[@id='resizableBoxWithRestriction']"));
        Dimension beforeDragging = resizeableBox.getSize();
        System.out.println("Before resizing: " + beforeDragging);
        Thread.sleep(2000);
        uti.actionsMethods("scrollTo", driver, resizeAble, scrollToEnd);
        Thread.sleep(2000);
        uti.actionsMethods("resizeable", driver, source, resizeAble);
        Dimension afterDragging = resizeableBox.getSize();
        Thread.sleep(2000);
        System.out.println("Before resizing: " + afterDragging);
        Assert.assertTrue(afterDragging.getWidth() > beforeDragging.getWidth(), "Width did not increase after resizing!");
        Assert.assertTrue(afterDragging.getHeight() > beforeDragging.getHeight(), "Height did not increase after resizing!");
    }

    public void Droppable() throws InterruptedException {
        WebElement droppable = driver.findElement(By.xpath("//span[text()='Droppable']"));
        droppable.click();
        WebElement scrollToEnd = driver.findElement(By.xpath("//*[text()='Book Store Application']"));
        uti.actionsMethods("scrollTo", driver, droppable, scrollToEnd);
        WebElement source = driver.findElement(By.cssSelector("div#draggable"));
        WebElement target = driver.findElement(By.cssSelector("div#droppable"));
        uti.actionsMethods("dragAndDrop", driver, source, target);
        Thread.sleep(3000);
        WebElement dropped = driver.findElement(By.xpath("//p[text()='Dropped!']"));
        Assert.assertTrue(dropped.isDisplayed());
    }

    public void dragabble() throws InterruptedException {
        WebElement scrollToEnd = driver.findElement(By.xpath("//*[text()='Book Store Application']"));
        WebElement dragabble = driver.findElement(By.xpath("//span[text()='Dragabble']"));
        uti.actionsMethods("scrollTo", driver, dragabble, scrollToEnd);
        dragabble.click();
        WebElement source = driver.findElement(By.id("dragBox"));
        Thread.sleep(1000);
        Point before = source.getLocation();
        uti.actionsMethods("dragabble", driver, source, dragabble);
        Thread.sleep(1000);
        Point after = source.getLocation();
        Assert.assertTrue(after.getX() > before.getX(), "Box did not move right!");
    }
    
}
