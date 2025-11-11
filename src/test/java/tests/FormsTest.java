package tests;

import base.BaseTest;
import models.FormData;
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
                formData.getFirstName(),
                formData.getLastName(),
                formData.getEmail(),
                formData.getMobileNumber(),
                formData.getSubjects(),
                formData.getCurrentAddress()
        );

        test.pass("Form filled successfully.");
    }

    @Test(dependsOnMethods = "fillFormTest", priority = 2)
    public void assertFormTest() throws IOException, InterruptedException {
        test.info("Validating submitted form data...");
        FormsPage formsPage = new FormsPage(driver, test);

        boolean result = formsPage.assertForm(
                formData.getFirstName(),
                formData.getLastName(),
                formData.getEmail(),
                formData.getMobileNumber(),
                formData.getSubjects(),
                formData.getCurrentAddress(),
                formData.getGender(),
                formData.getHobbies(),
                formData.getState(),
                formData.getCity(),
                formData.getDateOfBirth(),
                formData.getSuccessMessage()
        );

        Assert.assertTrue(result, "Form validation failed!");
        test.pass("Form data validated successfully.");
    }
}