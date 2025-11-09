package tests;

import base.BaseTest;
import com.aventstack.extentreports.ExtentTest;
import listener.TestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.InteractionsPage;

import java.util.Arrays;
import java.util.List;

@Listeners(TestListener.class)
public class InteractionsTest extends BaseTest {

    @Test
    public void verifyAllInteractions() throws InterruptedException {
//        ExtentTest test = TestListener.getTest();
        test.info("Verifying interactions on UI...");
        InteractionsPage page = new InteractionsPage(driver, test);
        List<String> expectedActions = Arrays.asList("Sortable", "Selectable", "Resizable", "Droppable", "Dragabble");

        Assert.assertTrue(page.openInteractionsPage(), "❌ Failed to open Interactions page");
        Assert.assertTrue(page.verifyActionsList(expectedActions), "❌ Actions list mismatch");
        Assert.assertTrue(page.performSortable(), "❌ Sortable did not work correctly");
        Assert.assertTrue(page.performSelectable(), "❌ Selectable did not work correctly");
        Assert.assertTrue(page.performResizable(), "❌ Resizable did not work correctly");
        Assert.assertTrue(page.performDroppable(), "❌ Droppable did not work correctly");
        Assert.assertTrue(page.performDraggable(), "❌ Draggable did not work correctly");

        test.pass("✅ All UI interactions verified successfully.");
    }
}