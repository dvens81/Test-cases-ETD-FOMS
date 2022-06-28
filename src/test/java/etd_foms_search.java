import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

// Поиск по меню. Поиск реестров. Переход в подсистему/подменю по результатам поиска. Открытие реестра по результатам поиска
// Проверки:
// 1) При переходе в подсистему/подменю, по результатам поиска, присутствует искомый реестр
// 2) Проверяем корректное открытие реестра по результатам поиска

public class etd_foms_search {

    private WebDriverWait wait;
    private WebDriver driver;

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    }

    boolean isElementPresent(WebDriver driver, By locator) {
        return driver.findElements(locator).size() > 0;
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

        driver.findElement(By.cssSelector("[data-testid=MenuIcon]")).click();
        String registry = "Реестр оплат";
        driver.findElement(By.cssSelector("input[type=text]")).sendKeys(registry);
        TimeUnit.MILLISECONDS.sleep(400);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".breadcrumbs__item"))).click();

        // Проверяем, что, при переходе в подсистему/подменю, по результатам поиска, присутствует искомый реестр
        List<WebElement> list = driver.findElements(By.cssSelector(".system-item"));

        String findRegistry = "";

        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i).getAttribute("textContent");

            if (s.equals(registry)) {
                findRegistry = s;
                break;

            }

        }

        Assert.assertEquals(findRegistry, registry);

        driver.findElement(By.cssSelector("input[type=text]")).sendKeys(registry);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".bookmark__text_hovered"))).click();
        TimeUnit.MILLISECONDS.sleep(10000);

        // Проверяем корректное открытие реестра по результатам поиска
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector("iframe")));
        Assert.assertTrue(isElementPresent(driver, By.cssSelector(".ant-breadcrumb")));

    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
