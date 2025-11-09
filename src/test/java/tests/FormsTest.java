package tests;

import base.BaseTest;
import com.aventstack.extentreports.ExtentTest;
import listener.TestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.FormsPage;
import utils.excel;

import java.io.IOException;

@Listeners(listener.TestListener.class)
public class FormsTest extends BaseTest {

    @Test
    public void fillFormTest() throws IOException, InterruptedException {
        ExtentTest test = TestListener.getTest();
        FormsPage formsPage = new FormsPage(driver, test);
        excel data = new excel();
        test.info("Starting fillFormTest");
        formsPage.openForm();
        formsPage.fillForm(data.getData("First Name").get(1), data.getData("Last Name").get(1), data.getData("Email").get(1),
                data.getData("Mobile Number").get(1), data.getData("Subjects").get(1), data.getData("Current Address").get(1));
        test.pass("Form submission test executed successfully ✅");
    }

    @Test(dependsOnMethods = {"fillFormTest"})
    public void assertFormTest() throws IOException, InterruptedException {
        ExtentTest test = TestListener.getTest();
        FormsPage formsPage = new FormsPage(driver, test);
        excel data = new excel();
        test.info("validating fillFormTest");
        boolean testResult = formsPage.assertForm(data.getData("First Name").get(1), data.getData("Last Name").get(1), data.getData("Email").get(1),
                data.getData("Mobile Number").get(1), data.getData("Subjects").get(1), data.getData("Current Address").get(1), "Thanks for submitting the form");
        Assert.assertTrue(testResult, "Form submission test executed successfully with correct inputs!!");
        test.pass("Form submission test executed successfully with correct inputs ✅");
    }
}