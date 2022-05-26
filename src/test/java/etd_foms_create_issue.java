import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class etd_foms_create_issue {

    // ".alert-dialog-content__link" - локатор всплывающего уведомления об успешном создании обращения

    private WebDriverWait wait;
    private WebDriver driver;

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        //driver.manage().timeouts().pageLoadTimeout(6, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));

    }

//    boolean isElementPresent(WebDriver driver, By locator) {
//        return driver.findElements(locator).size() > 0;
//    }

    @Test
    public void myFirstTest() throws InterruptedException {
        driver.get("https://d-customer-balancer-iam-proxy-01.foms.novalocal/etd-front/");
        driver.findElement(By.cssSelector("#details-button")).click();
        driver.findElement(By.cssSelector("#proceed-link")).click();
        driver.findElement(By.cssSelector("#details-button")).click();
        driver.findElement(By.cssSelector("#proceed-link")).click();
        driver.findElement(By.cssSelector("#zocial-esia")).click();
        driver.findElement(By.cssSelector("#login")).sendKeys("ajurba@mail.ru");
        driver.findElement(By.cssSelector("#password")).sendKeys("Zaxscdvfbg321-");
        driver.findElement(By.cssSelector("#loginByPwdButton .ui-button-text")).click();
        driver.findElement(By.cssSelector("button:nth-child(2) .name")).click();
        TimeUnit.MILLISECONDS.sleep(4000);

        driver.findElement(By.cssSelector(".support-button__icon")).click();
        TimeUnit.MILLISECONDS.sleep(400);
        driver.findElement(By.cssSelector(".MuiList-root span:first-child")).click();
        TimeUnit.MILLISECONDS.sleep(400);
        driver.findElement(By.cssSelector(".MuiOutlinedInput-root [role=button]")).click();
        driver.findElement(By.cssSelector("[data-value=РМП]")).click();
        driver.findElement(By.cssSelector(".MuiGrid-grid-md-7 div:nth-child(3) [role=button]")).click();
        driver.findElement(By.cssSelector("[role=listbox] li:first-child")).click();
        driver.findElement(By.cssSelector("input[name=summary]")).sendKeys("Тест, тест 123 !");
        WebElement textarea = driver.findElement(By.cssSelector(".field-container_nativeTextarea textarea"));
        textarea.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        textarea.sendKeys(Keys.chord(Keys.DELETE));
        textarea.sendKeys("Не открывается \"Реестр входящих документов\"");
        WebElement input_file = driver.findElement(By.cssSelector("input[type=file]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='block';", input_file);
        input_file.sendKeys("C:/Download/Screen Recorder/foto.jpeg");

    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}

