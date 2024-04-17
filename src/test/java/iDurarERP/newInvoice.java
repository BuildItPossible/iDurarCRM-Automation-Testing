package iDurarERP;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class newInvoice {
    private WebDriver driver;
    int itemIndex = 0;
    int totalItems = dataProvider.newInvoice().length;

    @BeforeClass
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

    @Test(priority = 1)
    public void navigateToPage() {
        // Wait for the Menu link to be clickable
        WebElement categoryLink = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.linkText("Invoices")));
        // Go to Companies page
        categoryLink.click();
    }

    @Test(priority = 2)
    public void addInvoiceDetails(ITestContext context){
        WebElement addButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div/main/main/div[1]/div/span/div/div[3]/button")));
        addButton.click();
        // Wait for the drawer to open
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("#root > div > div > main > main > div.ant-spin-nested-loading.css-16v3ahg > div > form > div:nth-child(7) > div:nth-child(1) > div:nth-child(1) > div > div > div > div > div > button > span:nth-child(2)")));
        // Find the "Pending" option and click on it
        driver.findElement(By.xpath("/html/body/div[1]/div/div/main/main/div[3]/div/form/div[1]/div[5]/div/div/div[2]/div/div/div/div/span[2]")).click();
        driver.findElement(By.cssSelector(".ant-select-item-option[title='Pending']")).click();

        driver.findElement(By.id("expiredDate")).click();
        driver.findElement(By.xpath("//div[@class='ant-picker-cell-inner'][text()='25']")).click();
        driver.findElement(By.xpath("/html/body/div[1]/div/div/main/main/div[3]/div/form/div[1]/div[1]/div/div/div[2]/div/div/div/div/span[1]/input")).sendKeys("Test Name", Keys.ENTER);

    }

    @Test(priority = 3, dataProvider = "InvoiceData", dataProviderClass = dataProvider.class ) //
    public void addInvoiceItems(String itemName, String itemDesc, String qnt, String price){

        driver.findElement(By.id("items_" + itemIndex + "_itemName")).sendKeys(itemName);
        driver.findElement(By.id("items_" + itemIndex + "_description")).sendKeys(itemDesc);
        driver.findElement(By.id("items_" + itemIndex + "_quantity")).sendKeys(qnt);
        driver.findElement(By.id("items_" + itemIndex + "_price")).sendKeys(price);
        itemIndex++;
        if (itemIndex < totalItems){
            driver.findElement(By.cssSelector("#root > div > div > main > main > div.ant-spin-nested-loading.css-16v3ahg > div > form > div.ant-form-item.css-16v3ahg > div > div > div > div > button")).click();
        }else {
            driver.findElement(By.id("rc_select_12")).click();
            driver.findElement(By.cssSelector("body > div:nth-child(6) > div > div > div.rc-virtual-list > div > div > div > div.ant-select-item.ant-select-item-option.ant-select-item-option-active > div > span")).click();
            driver.findElement(By.xpath("/html/body/div[1]/div/div/main/main/div[3]/div/form/div[12]/div[1]/div[1]/div/div/div/div/div/button")).click();
        }
    }


    @AfterClass
    public void completion() {
        // Close the WebDriver
//        driver.quit();
    }
}
