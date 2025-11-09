package factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import utils.ConfigReader;

import java.time.Duration;

public class DriverFactory {

    public static WebDriver driverInitialization() {
        String browser = ConfigReader.get("browser");
        if (browser == null || browser.isEmpty() || browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-infobars");
            options.addArguments("--disable-extensions");
            options.addArguments("--start-maximized");
            WebDriver driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(
                    ConfigReader.getInt("implicitlyWait", 10)));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(
                    ConfigReader.getInt("pageLoadTimeout", 30)));
            driver.manage().window().maximize();
            return driver;
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            WebDriver driver = new FirefoxDriver();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(
                    ConfigReader.getInt("implicitlyWait", 10)));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(
                    ConfigReader.getInt("pageLoadTimeout", 30)));
            driver.manage().window().maximize();
            return driver;
        }

        throw new RuntimeException("Unsupported browser: " + browser);
    }
}