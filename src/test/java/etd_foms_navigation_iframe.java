import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

// Прокликивает последовательно все реестры подсистемы РМП. Переход в реестр/iframe. Проверка корректного открытия реестров/iframe.
// доделать когда будет доступ к стендам

public class etd_foms_navigation_iframe {

    private WebDriverWait wait;
    private WebDriver driver;

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

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

        List<WebElement> links = driver.findElements(By.cssSelector(".navigation-second__right-items [ng-bind='$item.name']"));
        for (int i = 0; i < links.size(); i++) {
            links.get(i).click();
            WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("iframe")));
            driver.switchTo().frame(iframe);
            driver.switchTo().defaultContent();

        }


    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
