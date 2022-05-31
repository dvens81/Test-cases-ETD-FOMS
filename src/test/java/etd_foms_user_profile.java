import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class etd_foms_user_profile {

    private WebDriverWait wait;
    private WebDriver driver;

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));

    }

    boolean isElementPresent (WebDriver driver, By locator) {
        return driver.findElements(locator).size() == 0;
    }

    @Test
    public void myFirstTest() throws InterruptedException {

        // Авторизация

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

        driver.findElement(By.cssSelector(".user-profile__icon")).click();
        TimeUnit.MILLISECONDS.sleep(400);
        driver.findElement(By.cssSelector(".MuiMenu-list li:first-child .dropdown__item")).click();
        TimeUnit.MILLISECONDS.sleep(400);

        driver.findElement(By.cssSelector(".avatar")).click();

        if (isElementPresent(driver, By.cssSelector("input[type=file]"))) {
            driver.findElement(By.cssSelector(".dialog-buttons button:nth-child(2)")).click();
            driver.findElement(By.cssSelector(".app > div:last-child .dialog-buttons button:first-child")).click();
            driver.findElement(By.cssSelector(".avatar")).click();

        }

        WebElement input_file = driver.findElement(By.cssSelector("input[type=file]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='block';", input_file);
        input_file.sendKeys("C:/Download/Avatar вложения/avatar-2.jpg");
        driver.findElement(By.cssSelector("[type=submit]")).click();

    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
