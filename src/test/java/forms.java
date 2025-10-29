import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.time.Duration;

public class forms extends baseDriver {
    utilities uti = new utilities();
    excel data = new excel();

    public forms() {
        driver = driverInitialization();
    }

    public static void main(String[] args) throws IOException {
    new forms().fillForm();
    }

    public void fillForm() throws IOException {
        driver.get("https://demoqa.com/");
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        uti.removeAds(driver);
        WebElement practiceForm = driver.findElement(By.xpath("//span[text()='Practice Form']"));
        practiceForm.click();

        WebElement firstName = driver.findElement(By.xpath("//input[@placeholder='First Name']"));
        WebElement lastName = driver.findElement(By.xpath("//input[@placeholder='Last Name']"));
        WebElement email = driver.findElement(By.id("userEmail"));
        WebElement male = driver.findElement(By.xpath("//input[@value='Male']"));
        WebElement mobileNumber = driver.findElement(By.xpath("//input[@placeholder='Mobile Number']"));
        WebElement dateOfBirth = driver.findElement(By.xpath("//input[@id='dateOfBirthInput']"));
        WebElement subjects = driver.findElement(By.xpath("//input[@id='subjectsInput']"));
        WebElement sportsHobby = driver.findElement(By.id("hobbies-checkbox-1"));
        WebElement pictureUpload = driver.findElement(By.id("uploadPicture"));
        WebElement currentAddress = driver.findElement(By.id("currentAddress"));
        firstName.sendKeys(data.getData("First Name").get(1));
        lastName.sendKeys(data.getData("Last Name").get(1));
        email.sendKeys(data.getData("Email").get(1));
        male.click();
        mobileNumber.sendKeys(data.getData("Mobile Number").get(1));
        dateOfBirth.sendKeys(data.getData("DOB").get(1));
        subjects.sendKeys(data.getData("Subjects").get(1));
        sportsHobby.click();
        currentAddress.sendKeys(data.getData("Current Address").get(1));

    }





}
