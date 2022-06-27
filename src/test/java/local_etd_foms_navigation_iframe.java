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

// Прокликивает последовательно все реестры подсистемы РМП. Переход в реестр/iframe. Проверка корректного открытия реестров/iframe.

public class local_etd_foms_navigation_iframe {

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
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".navigation-tree__item-name")));

        List<WebElement> menu = driver.findElements(By.cssSelector(".navigation-tree__item-name"));

        for (int i = 0; i < menu.size(); i++) {
            menu = driver.findElements(By.cssSelector(".navigation-tree__item-name"));
            String s = menu.get(i).getAttribute("textContent");
            System.out.println("textContent = " + s);

            if (s.equals("Тест")) {
                menu.get(i).click();
                break;
            }

        }

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".navigation-second__right-items [ng-bind='$item.name']")));

        List<WebElement> links = driver.findElements(By.cssSelector(".navigation-second__right-items [ng-bind='$item.name']"));
        for (int i = 0; i < links.size(); i++) {
            links = driver.findElements(By.cssSelector(".navigation-second__right-items [ng-bind='$item.name']"));
            links.get(i).click();
            TimeUnit.MILLISECONDS.sleep(10000);
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector(".frameContainer iframe")));
            Assert.assertTrue(isElementPresent(driver, By.cssSelector("header #logo_holder_outer")));
            driver.switchTo().defaultContent();
            driver.findElement(By.cssSelector(".navigation-top__item-close")).click();


        }

    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
