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

// Виджеты. Избранное. Добавление и удаление реестров.
// Проверки:
// 1) количество добавленных реестров совпадает с количеством реестров, отображаемых на главной странице в блоке "Избранное"
// 2) количество реестров, отображаемых на главной странице в блоке "Избранное", совпадает с количеством реестров в виджете "Избранное"
// 3) после удаления всех реестров из избранного, на главной странице отсутствуют реестры в блоке "Избранное"

public class etd_foms_favourites {

    private WebDriverWait wait;
    private WebDriver driver;

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));

    }

    boolean isElementPresent(WebDriver driver, By locator) {
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

        // Проверяем, если есть реестры в блоке "Избранное" на главной странице, то удаляем их
        int count = driver.findElements(By.cssSelector(".bookmark__text")).size();

        if (count > 0) {

            String numbersLocator = ".favorite-icon-mini_active";
            int favouritesDel = driver.findElements(By.cssSelector(numbersLocator)).size();

            System.out.println("Количество удаленных реестров из блока \"Избранное\" на главной странице: " + favouritesDel);
            System.out.println("--------------------------");

            while (favouritesDel > 0) {
                driver.findElement(By.cssSelector(numbersLocator)).click();
                wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(numbersLocator), favouritesDel - 1));
                favouritesDel = driver.findElements(By.cssSelector(numbersLocator)).size();

            }

        }

        // Добавление реестров в избранное
        List<WebElement> menu = driver.findElements(By.cssSelector(".subsystem-widget"));

        for (int i = 0; i < menu.size(); i++) {
            menu = driver.findElements(By.cssSelector(".subsystem-widget"));
            String s = menu.get(i).getAttribute("textContent");

            if (s.equals("РМП")) {
                menu.get(i).click();
                break;
            }

        }

        List<WebElement> favouritesAdd = driver.findElements(By.cssSelector(".subSystemStack__items [data-testid=StarIcon]"));
        int favouritesAddNumbers = 3;

        for (int i = 0; i < favouritesAddNumbers; i++) {
            favouritesAdd.get(i).click();
            TimeUnit.MILLISECONDS.sleep(500);

        }
        TimeUnit.MILLISECONDS.sleep(1500);
        driver.findElement(By.cssSelector("[data-testid=HomeIcon]")).click();

        // Проверяем, что количество добавленных реестров совпадает с количеством реестров, отображаемых на главной странице в блоке "Избранное"
        List<WebElement> favouritesMenu = driver.findElements(By.cssSelector(".bookmark__text"));

        System.out.println("Количество добавленных реестров: " + favouritesAddNumbers);
        System.out.println("Количество реестров, отображаемых на главной странице в блоке \"Избранное\": " + favouritesMenu.size());
        System.out.println("--------------------------");

        Assert.assertTrue(favouritesAddNumbers == favouritesMenu.size());

        // Проверяем, что количество реестров, отображаемых на главной странице в блоке "Избранное", совпадает с количеством реестров в виджете "Избранное"
        driver.findElement(By.cssSelector("[data-testid=StarIcon]")).click();

        String numbersLocator = ".subSystemStack__items .favorite-icon-mini_active";
        int favouritesDel = driver.findElements(By.cssSelector(numbersLocator)).size();

        System.out.println("Количество реестров, отображаемых на главной странице в блоке \"Избранное\": " + favouritesMenu.size());
        System.out.println("Количество реестров в виджете \"Избранное\": " + favouritesDel);
        System.out.println("--------------------------");
        System.out.println("Количество удаленных реестров из виджета \"Избранное\": " + favouritesDel);

        Assert.assertTrue(favouritesDel == favouritesMenu.size());

        // Удаляем все реестры из избранного. Проверяем, что на главной странице отсутствуют реестры в блоке "Избранное"
        while (favouritesDel > 0) {
            driver.findElement(By.cssSelector(numbersLocator)).click();
            wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(numbersLocator), favouritesDel - 1));
            favouritesDel = driver.findElements(By.cssSelector(numbersLocator)).size();

        }
        driver.findElement(By.cssSelector("[data-testid=HomeIcon]")).click();

        int count2 = driver.findElements(By.cssSelector(".bookmark__text")).size();
        System.out.println("На главной странице отсутствуют реестры в блоке \"Избранное\": " + count2);

        Assert.assertTrue(isElementPresent(driver, By.cssSelector(".bookmark__text")));


    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}