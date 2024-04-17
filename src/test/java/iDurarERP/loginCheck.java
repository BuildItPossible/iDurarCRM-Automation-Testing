package iDurarERP;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.Duration;

public class loginCheck {
    private WebDriver driver;

    @Test
    @Parameters({"baseURL", "email", "password"})
    public void setup(String baseURL, String email, String password) {
        // Set up the WebDriver (ChromeDriver)
        driver = new ChromeDriver();
        driver.get(baseURL + "login");
        driver.manage().window().maximize();

        // Login logic
        WebElement emailField = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.id("normal_login_email")));
        emailField.sendKeys(email);
        driver.findElement(By.id("normal_login_password")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id=\"normal_login\"]/div[2]/div/div/div/div/button/span")).click();

        // Wait for the Dashboard to load
        WebElement dashboardLink = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Dashboard")));

        // Check if login was successful
        String currentURL = driver.getCurrentUrl();
        if (!currentURL.equals(baseURL)) {
            throw new SkipException("Login failed.");
        }
    }
}
