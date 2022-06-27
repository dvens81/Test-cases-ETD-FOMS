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

public class etd_foms_navigation_iframe {

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

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".subsystem-widget")));

        List<WebElement> menu = driver.findElements(By.cssSelector(".subsystem-widget"));

        for (int i = 0; i < menu.size(); i++) {
            menu = driver.findElements(By.cssSelector(".subsystem-widget"));
            String s = menu.get(i).getAttribute("textContent");
            System.out.println("textContent = " + s);

            if (s.equals("РМП")) {
                menu.get(i).click();
                break;
            }

        }

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".system-item-content")));

        List<WebElement> links = driver.findElements(By.cssSelector(".system-item-content"));
        for (int i = 0; i < links.size(); i++) {
            links = driver.findElements(By.cssSelector(".system-item-content"));
            links.get(i).click();
            TimeUnit.MILLISECONDS.sleep(10000);
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector("iframe")));
            Assert.assertTrue(isElementPresent(driver, By.cssSelector(".ant-breadcrumb")));
            driver.switchTo().defaultContent();
            driver.findElement(By.cssSelector("[data-testid=CloseIcon]")).click();

            List<WebElement> subsystems = driver.findElements(By.cssSelector(".sub-system-sidebar__menu ul h6"));
            for (int j = 0; j < subsystems.size(); j++) {
                subsystems = driver.findElements(By.cssSelector(".sub-system-sidebar__menu ul h6"));
                String sub = subsystems.get(j).getAttribute("textContent");

                if (sub.equals("РМП")) {
                    subsystems.get(j).click();
                    break;
                }

            }


        }

    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
