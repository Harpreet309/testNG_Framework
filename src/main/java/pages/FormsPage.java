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
        Thread.sleep(2000); // to load ads
        uti.removeAds(driver);
        logInfo("Navigated to form URL: " + url);
        logPass("Opened Practice Form successfully ✅");
    }

    public void fillForm(String firstName, String lastName, String email, String mobileNumber, String subjects, String currentAddress ) throws InterruptedException {
        String filePath = System.getProperty("user.dir") + ConfigReader.get("image");
        actions.log(Status.INFO, "Navigating to URL: " + ConfigReader.get("formUrl"));
        actions.click(practiceForm);
        actions.scrollIntoView(this.subjects);

        actions.sendKeys(this.firstName, firstName);
        actions.sendKeys(this.lastName, lastName);
        actions.sendKeys(this.email, email);

        actions.click(male);
        actions.sendKeys(this.mobileNumber, mobileNumber);

        actions.click(dateOfBirth);
        actions.selectBy(month, "November");
        actions.selectBy(year, "1994");
        actions.click(date);

        actions.log(Status.INFO, "Selecting subjects: " + subjects);
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
        actions.click(submitButton);
        actions.log(Status.PASS, "Form filled successfully ✅");
    }

    public boolean assertForm(String firstName, String lastName, String email, String mobileNumber, String subjects, String currentAddress, String successMessage) throws InterruptedException {
        String filePath = System.getProperty("user.dir") + ConfigReader.get("image");
        String fileName = new File(filePath).getName();
        Assert.assertEquals(successMessage, formSubmissionTitle.getText().trim());
        Assert.assertEquals(firstName + " " + lastName, getStudentName.getText().trim());
        Assert.assertEquals(email, getStudentEmail.getText().trim());
        Assert.assertEquals(mobileNumber, getMobile.getText().trim());
        Assert.assertEquals(currentAddress, getAddress.getText().trim());
        Thread.sleep(2000); // to slow up the execution
//        Assert.assertEquals(actions.makeArray(subjects), actions.makeArray(getSubjects.getText()));
        Assert.assertEquals(getHobbies.getText().trim(), "Sports");
        Assert.assertEquals(getPicture.getText().trim(), fileName);
        Assert.assertEquals(getStateAndCity.getText().trim(), "NCR Delhi");
        Assert.assertEquals(getDateOfBirth.getText().trim(), "17 November,1994");
        return true;
    }
}