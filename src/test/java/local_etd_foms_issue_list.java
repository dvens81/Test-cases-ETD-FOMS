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

public class local_etd_foms_issue_list {

    private WebDriver driver;
    private WebDriverWait wait;

    boolean isElementPresent1(WebDriver driver, By locator) {
        return driver.findElements(locator).size() == 1;
    }
    boolean isElementPresent2(WebDriver driver, By locator) {
        return driver.findElements(locator).size() == 2;
    }
    boolean isElementPresent9(WebDriver driver, By locator) {
        return driver.findElements(locator).size() >= 9;
    }


    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void test() throws InterruptedException {

        // Авторизация
        driver.get("http://black:8080/");
        TimeUnit.MILLISECONDS.sleep(8000);

        driver.findElement(By.cssSelector(".header__support-dropdown .btn__label")).click();
        TimeUnit.MILLISECONDS.sleep(400);
        driver.findElement(By.cssSelector(".header__support-dropdown a:last-child")).click();
        TimeUnit.MILLISECONDS.sleep(4000);

        // Проверка чекбоксов обращений

        driver.findElement(By.cssSelector(".table__body .custom-checkbox")).click();
        wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(".chosen"), 1));
        Assert.assertTrue(isElementPresent1(driver, By.cssSelector(".chosen")));

        driver.findElement(By.cssSelector(".table__body tr:nth-child(2) .custom-checkbox")).click();
        wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(".chosen"), 2));
        Assert.assertTrue(isElementPresent2(driver, By.cssSelector(".chosen")));

        driver.findElement(By.cssSelector(".table__filters .custom-checkbox")).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".chosen")));
        Assert.assertTrue(isElementPresent9(driver, By.cssSelector(".chosen")));

    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }


}
