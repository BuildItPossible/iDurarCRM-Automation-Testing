package iDurarERP;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class Companies {
    private WebDriver driver;

    @BeforeClass
    public void setup() {
        // Set up the WebDriver (ChromeDriver)
        driver = new ChromeDriver();
        String baseURL = "https://cloud.idurarapp.com/";
        driver.get(baseURL + "login");
        driver.manage().window().maximize();

        // Login logic
        WebElement emailField = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.id("normal_login_email")));
        emailField.sendKeys("taedyn.tai@milkgitter.com");
        driver.findElement(By.id("normal_login_password")).sendKeys("123456");
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

    @Test(priority = 1)
    public void navigateToPage() {
        System.out.println("Starting to test Companies Module...");

        // Wait for the Companies link to be clickable
        WebElement companiesLink = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.linkText("Companies")));
        System.out.println("Going to " + companiesLink.getText() + " Page...");

        // Go to Companies page
        companiesLink.click();
    }

    @Test(priority = 2, dataProvider = "CompanyData", dataProviderClass = dataProvider.class, enabled = false)
    public void addNewCompany(String name, String country, String phone, String email, String website) {
        WebElement addButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div/main/main/div[1]/div/span/div/div[3]/button")));
        addButton.click();
        System.out.println("Opening Drawer");
        // Wait for the drawer to open
        WebElement drawerItem = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".ant-col:nth-child(1) > .ant-spin-nested-loading:nth-child(1) #name")));
         driver.findElement(By.cssSelector(".ant-col:nth-child(1) > .ant-spin-nested-loading:nth-child(1) #name")).sendKeys(name);
        driver.findElement(By.cssSelector(".ant-col:nth-child(1) > .ant-spin-nested-loading:nth-child(1) #country")).sendKeys(country, Keys.ENTER);
        driver.findElement(By.cssSelector(".ant-col:nth-child(1) > .ant-spin-nested-loading:nth-child(1) #phone")).sendKeys(phone);
        driver.findElement(By.cssSelector(".ant-col:nth-child(1) > .ant-spin-nested-loading:nth-child(1) #email")).sendKeys(email);
        driver.findElement(By.cssSelector(".ant-col:nth-child(1) > .ant-spin-nested-loading:nth-child(1) #website")).sendKeys(website);
        driver.findElement(By.cssSelector("body > div:nth-child(3) > div > div.ant-drawer-content-wrapper > div > div.ant-drawer-body > div > div.collapseBox > div.BottomCollapseBox > div > div > div > div > div > form > div.ant-form-item.css-16v3ahg > div > div > div > div > button")).click();
        boolean changed = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ant-col:nth-child(1) > .ant-spin-nested-loading:nth-child(1) #name")));
        if (changed){
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
            driver.findElement(By.xpath("//button[@class='ant-drawer-close']")).click();
        } else {
            SoftAssert error = new SoftAssert();
            error.assertEquals("Failed", "Failed to add Company");
        }
    }

    @AfterClass
    public void completion() {
        // Close the WebDriver
        driver.quit();
    }
}