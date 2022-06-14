import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

// Поиск по меню. Поиск реестров. Переход в подсистему/подменю по результатам поиска. Открытие реестра по результатам поиска
// Проверки:
// 1) При переходе в подсистему/подменю, по результатам поиска, присутствуют искомые реестры
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
        driver.get("http://black:8080/");

        driver.findElement(By.cssSelector("[ng-href='/#/menu']")).click();
        driver.findElement(By.cssSelector("input[type=text]")).sendKeys("сайт 1");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".bc__item-link"))).click();

        // Проверяем, что, при переходе в подсистему/подменю, по результатам поиска, присутствуют искомые реестры
        isElementPresent(driver, By.cssSelector("[ng-click='$navigation.tree.itemSelect($item, $event)']"));

        driver.findElement(By.cssSelector("input[type=text]")).sendKeys("сайт 1");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[ng-repeat='$item in $navigation.getItems() | " +
                "orderBy: $navigation.tree.extend.orderFoldersAndItems track by $index '] [ng-bind='$item.name']"))).click();
        TimeUnit.MILLISECONDS.sleep(10000);

        // Проверяем корректное открытие реестра по результатам поиска
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector(".frameContainer iframe")));
        Assert.assertTrue(isElementPresent(driver, By.cssSelector("header #logo_holder_outer")));

          }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
