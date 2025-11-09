package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.FormsPage;
import utils.excel;

import java.io.IOException;

@Listeners(listener.TestListener.class)
public class FormsTest extends BaseTest {

    excel data = new excel();

    @Test(priority = 1)
    public void fillFormTest() throws IOException, InterruptedException {
        test.info("Filling out form...");
        FormsPage formsPage = new FormsPage(driver, test);

        formsPage.openForm();
        formsPage.fillForm(
                data.getData("First Name").get(1),
                data.getData("Last Name").get(1),
                data.getData("Email").get(1),
                data.getData("Mobile Number").get(1),
                data.getData("Subjects").get(1),
                data.getData("Current Address").get(1)
        );

        test.pass("✅ Form filled successfully.");
    }

    @Test(dependsOnMethods = "fillFormTest", priority = 2)
    public void assertFormTest() throws IOException, InterruptedException {
        test.info("Validating submitted form data...");
        FormsPage formsPage = new FormsPage(driver, test);

        boolean result = formsPage.assertForm(
                data.getData("First Name").get(1),
                data.getData("Last Name").get(1),
                data.getData("Email").get(1),
                data.getData("Mobile Number").get(1),
                data.getData("Subjects").get(1),
                data.getData("Current Address").get(1),
                "Thanks for submitting the form"
        );

        Assert.assertTrue(result, "Form validation failed!");
        test.pass("✅ Form data validated successfully.");
    }
}