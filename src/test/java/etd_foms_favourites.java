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
        driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));

    }

    boolean isElementPresent(WebDriver driver, By locator) {
        return driver.findElements(locator).size() == 0;
    }

    @Test
    public void myFirstTest() throws InterruptedException {
        driver.get("http://black:8080/");

        // Проверяем, если есть реестры в блоке "Избранное" на главной странице, то удаляем их
        int count = driver.findElements(By.cssSelector("._widget-favorites li [ng-bind='$item.name']")).size();

        if (count > 0) {
            driver.findElement(By.cssSelector("[ng-href='/#/menu']")).click();
            driver.findElement(By.cssSelector("[ng-if='$navigation.hasFavorites()'] span")).click();

            String numbersLocator = "[title='Убрать из избранного']";
            int favouritesDel = driver.findElements(By.cssSelector(numbersLocator)).size();

            System.out.println("Количество удаленных реестров из блока \"Избранное\" на главной странице: " + favouritesDel);
            System.out.println("--------------------------");

            while (favouritesDel > 0) {
                driver.findElement(By.cssSelector(numbersLocator)).click();
                wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(numbersLocator), favouritesDel - 1));
                favouritesDel = driver.findElements(By.cssSelector(numbersLocator)).size();

            }
            driver.get("http://black:8080/");

        }

        // Добавление реестров в избранное
        List<WebElement> menu = driver.findElements(By.cssSelector(".navigation-tree__item-name"));

        for (int i = 0; i < menu.size(); i++) {
            menu = driver.findElements(By.cssSelector(".navigation-tree__item-name"));
            String s = menu.get(i).getAttribute("textContent");
            //System.out.println("textContent = " + s);

            if (s.equals("Тест")) {
                menu.get(i).click();
                break;
            }

        }

        List<WebElement> favouritesAdd = driver.findElements(By.cssSelector("[title='Добавить в избранное']"));

        for (int i = 0; i < favouritesAdd.size(); i++) {
            favouritesAdd.get(i).click();
            TimeUnit.MILLISECONDS.sleep(500);

        }
        TimeUnit.MILLISECONDS.sleep(1500);
        driver.get("http://black:8080/");

        // Проверяем, что количество добавленных реестров совпадает с количеством реестров, отображаемых на главной странице в блоке "Избранное"
        List<WebElement> favouritesMenu = driver.findElements(By.cssSelector("._widget-favorites li [ng-bind='$item.name']"));

        System.out.println("Количество добавленных реестров: " + favouritesAdd.size());
        System.out.println("Количество реестров, отображаемых на главной странице в блоке \"Избранное\": " + favouritesMenu.size());
        System.out.println("--------------------------");

        Assert.assertTrue(favouritesAdd.size() == favouritesMenu.size());

        // Проверяем, что количество реестров, отображаемых на главной странице в блоке "Избранное", совпадает с количеством реестров в виджете "Избранное"
        driver.findElement(By.cssSelector("[ng-href='/#/menu']")).click();
        driver.findElement(By.cssSelector("[ng-if='$navigation.hasFavorites()'] span")).click();

        String numbersLocator = "[title='Убрать из избранного']";
        int favouritesDel = driver.findElements(By.cssSelector(numbersLocator)).size();

        System.out.println("Количество реестров, отображаемых на главной странице в блоке \"Избранное\": " + favouritesMenu.size());
        System.out.println("Количество реестров в виджете \"Избранное\": " + favouritesDel);
        System.out.println("--------------------------");

        Assert.assertTrue(favouritesDel == favouritesMenu.size());

        // Удаляем все реестры из избранного. Проверяем, что на главной странице отсутствуют реестры в блоке "Избранное"
        while (favouritesDel > 0) {
            driver.findElement(By.cssSelector(numbersLocator)).click();
            wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(numbersLocator), favouritesDel - 1));
            favouritesDel = driver.findElements(By.cssSelector(numbersLocator)).size();

        }
        driver.get("http://black:8080/");

        int count2 = driver.findElements(By.cssSelector("._widget-favorites li [ng-bind='$item.name']")).size();
        System.out.println("На главной странице отсутствуют реестры в блоке \"Избранное\": " + count2);

        Assert.assertTrue(isElementPresent(driver, By.cssSelector("._widget-favorites li [ng-bind='$item.name']")));


    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
