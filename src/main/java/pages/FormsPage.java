package pages;

import base.BasePage;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import utils.ConfigReader;

import java.io.File;

public class FormsPage extends BasePage {
    public FormsPage(WebDriver driver, com.aventstack.extentreports.ExtentTest test) {
        super(driver, test);
    }

    // ---------- ELEMENTS -----------
    @FindBy(xpath = "//span[text()='Practice Form']")
    WebElement practiceForm;

    @FindBy(xpath = "//input[@placeholder='First Name']")
    WebElement firstName;

    @FindBy(xpath = "//input[@placeholder='Last Name']")
    WebElement lastName;

    @FindBy(id = "userEmail")
    WebElement email;

    @FindBy(xpath = "//label[normalize-space(text())='Male']")
    WebElement male;

    @FindBy(xpath = "//input[@placeholder='Mobile Number']")
    WebElement mobileNumber;

    @FindBy(id = "dateOfBirthInput")
    WebElement dateOfBirth;

    @FindBy(id = "subjectsInput")
    WebElement subjects;

    @FindBy(xpath = "//label[normalize-space(text())='Sports']")
    WebElement sportsHobby;

    @FindBy(id = "uploadPicture")
    WebElement pictureUpload;

    @FindBy(id = "currentAddress")
    WebElement currentAddress;

    @FindBy(xpath = "//div[contains(@id,'state')]")
    WebElement stateDropdown;

    @FindBy(xpath = "//div[contains(text(),'NCR')]")
    WebElement ncrOption;

    @FindBy(xpath = "//div[contains(@id,'city')]")
    WebElement cityDropdown;

    @FindBy(xpath = "//div[contains(text(),'Delhi')]")
    WebElement delhiOption;

    @FindBy(className = "react-datepicker__month-select")
    WebElement month;

    @FindBy(className = "react-datepicker__year-select")
    WebElement year;

    @FindBy(xpath = "//div[contains(@class,'react-datepicker__day') and text()='17']")
    WebElement date;

    @FindBy(id = "submit")
    WebElement submitButton;

    @FindBy(xpath = "//div[@class='modal-title h4']")
    WebElement formSubmissionTitle;

    @FindBy(xpath = "//td[text()='Student Name']//following-sibling::td")
    WebElement getStudentName;

    @FindBy(xpath = "//td[text()='Student Email']//following-sibling::td")
    WebElement getStudentEmail;

    @FindBy(xpath = "//td[text()='Gender']//following-sibling::td")
    WebElement getGender;

    @FindBy(xpath = "//td[text()='Mobile']//following-sibling::td")
    WebElement getMobile;

    @FindBy(xpath = "//td[text()='Date of Birth']//following-sibling::td")
    WebElement getDateOfBirth;

    @FindBy(xpath = "//td[text()='Subjects']//following-sibling::td")
    WebElement getSubjects;

    @FindBy(xpath = "//td[text()='Hobbies']//following-sibling::td")
    WebElement getHobbies;

    @FindBy(xpath = "//td[text()='Picture']//following-sibling::td")
    WebElement getPicture;

    @FindBy(xpath = "//td[text()='Address']//following-sibling::td")
    WebElement getAddress;

    @FindBy(xpath = "//td[text()='State and City']//following-sibling::td")
    WebElement getStateAndCity;

    public void openForm() throws InterruptedException {
        String url = ConfigReader.get("formUrl");
        driver.get(url);
        actions.waitFor(2000); // to load ads
        uti.removeAds(driver);
        logInfo("Navigated to form URL: " + url);
        captureScreenshot("Form_Page_Loaded");
        logPass("Opened Practice Form successfully");
    }

    public void fillForm(String firstName, String lastName, String email, String mobileNumber, String subjects, String currentAddress ) throws InterruptedException {
        String monthToSelect = "November";
        String yearToSelect = "1994";
        String filePath = System.getProperty("user.dir") + ConfigReader.get("image");
        logInfo("Navigating to URL: " + ConfigReader.get("formUrl"));
        actions.click(practiceForm);
        actions.scrollIntoView(this.subjects);

        actions.sendKeys(this.firstName, firstName);
        actions.sendKeys(this.lastName, lastName);
        actions.sendKeys(this.email, email);
        captureScreenshot("Form_Filled_Partial");

        actions.click(male);
        actions.sendKeys(this.mobileNumber, mobileNumber);

        actions.click(dateOfBirth);
        actions.selectByText(this.month, monthToSelect);
        actions.selectByText(this.year, yearToSelect);
        actions.click(date);

        logInfo("Selecting subjects: " + subjects);
        actions.selectSubjects(this.subjects, subjects);

        actions.click(sportsHobby);
        actions.sendKeys(pictureUpload, filePath);
        actions.sendKeys(this.currentAddress, currentAddress);

        actions.scrollIntoView(stateDropdown);
        actions.click(stateDropdown);
        actions.click(ncrOption);

        actions.scrollIntoView(cityDropdown);
        actions.click(cityDropdown);
        actions.click(delhiOption);
        captureScreenshot("Form_Filled_Complete");
        actions.click(submitButton);
        captureScreenshot("Form_Submitted");
        logPass("Form filled successfully");
    }

    public boolean assertForm(String firstName, String lastName, String email, String mobileNumber, String subjects, String currentAddress, String gender, String hobbies, String state, String city, String dob, String successMessage) throws InterruptedException {
        String stateAndCity = state + " " + city;
        String filePath = System.getProperty("user.dir") + ConfigReader.get("image");
        String fileName = new File(filePath).getName();
        Assert.assertEquals(formSubmissionTitle.getText().trim(), successMessage);
        Assert.assertEquals(getStudentName.getText().trim(), firstName + " " + lastName );
        Assert.assertEquals(getStudentEmail.getText().trim(), email);
        Assert.assertEquals(getMobile.getText().trim(), mobileNumber);
        Assert.assertEquals(getAddress.getText().trim(), currentAddress);
        actions.waitFor(2000); // to slow up the execution
        Assert.assertEquals(actions.makeArray(subjects), actions.makeArray(getSubjects.getText()));
        Assert.assertEquals(getHobbies.getText().trim(), hobbies);
        Assert.assertEquals(getPicture.getText().trim(), fileName);
        Assert.assertEquals(getStateAndCity.getText().trim(), stateAndCity);
        Assert.assertEquals(getDateOfBirth.getText().trim(), dob);
        Assert.assertEquals(getGender.getText().trim(), gender);
        captureScreenshot("Form_Validation_Complete");
        logPass("Form data validated successfully");
        return true;
    }
}