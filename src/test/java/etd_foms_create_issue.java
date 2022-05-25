import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        //driver.manage().timeouts().pageLoadTimeout(6, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Duration.ofSeconds(1));

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
        //driver.findElement(By.cssSelector("[data-value=РМП]")).click(); // Не работает связка селениум и хром в части кодировки кириллицы
        driver.findElement(By.cssSelector(".MuiList-root li:nth-child(5)")).click();
        driver.findElement(By.cssSelector(".MuiGrid-grid-md-7 div:nth-child(3) [role=button]")).click();
        driver.findElement(By.cssSelector("[role=listbox] li:first-child")).click();
        driver.findElement(By.cssSelector("input[name=summary]")).sendKeys("Test, test 123 !");
        driver.findElement(By.cssSelector("textarea")).
                sendKeys("test 00935300000000000 FEDERAL STATE BUDGETARY INSTITUTION \"V.A. ALMAZOV NATIONAL MEDICAL RESEARCH CENTER\" " +
                        "OF THE MINISTRY OF HEALTH OF THE RUSSIAN FEDERATION test");

    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}

