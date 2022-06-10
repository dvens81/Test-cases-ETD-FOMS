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
import java.util.concurrent.TimeUnit;

// Виджеты. Избранное. Добавление и удаление реестров.

public class etd_foms_favourites {

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

    @Test
    public void myFirstTest() throws InterruptedException {
        driver.get("http://black:8080/");

        // Проверяем, если есть реестры в Избранном, то удаляем
        int count = driver.findElements(By.cssSelector("._widget-favorites li [ng-bind='$item.name']")).size();

        if (count > 0) {
            driver.findElement(By.cssSelector("[ng-href='/#/menu']")).click();
            driver.findElement(By.cssSelector("[ng-if='$navigation.hasFavorites()'] span")).click();

            String numbersLocator = "[title='Убрать из избранного']";
            int favourites = driver.findElements(By.cssSelector(numbersLocator)).size();

            while (favourites > 0) {
                driver.findElement(By.cssSelector(numbersLocator)).click();
                wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(numbersLocator), favourites - 1));
                favourites = driver.findElements(By.cssSelector(numbersLocator)).size();

            }
            driver.get("http://black:8080/");

        }


    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
