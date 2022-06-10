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
// Проверка: количество добавленных реестров в избранное равно количеству отображаемых реестров в виджете Избранное

public class etd_foms_favourites {

    private WebDriverWait wait;
    private WebDriver driver;

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    }

    boolean isElementPresent(WebDriver driver, By locator) {
        return driver.findElements(locator).size() == 0;
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
            int favouritesDel = driver.findElements(By.cssSelector(numbersLocator)).size();

            while (favouritesDel > 0) {
                driver.findElement(By.cssSelector(numbersLocator)).click();
                wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(numbersLocator), favouritesDel - 1));
                favouritesDel = driver.findElements(By.cssSelector(numbersLocator)).size();

            }
            driver.get("http://black:8080/");

        }

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

        List<WebElement> favouritesAdd = driver.findElements(By.cssSelector("[title='Добавить в избранное']"));

        for (int i = 0; i < favouritesAdd.size(); i++) {
            favouritesAdd.get(i).click();

        }
        driver.get("http://black:8080/");

        List<WebElement> favouritesMenu = driver.findElements(By.cssSelector("._widget-favorites li [ng-bind='$item.name']"));

        // Проверяем, что количество добавленных реестров, совпадает с колличеством реестров, отображаемых на главной странице в блоке "Избранное"
        Assert.assertTrue(favouritesAdd.size() == favouritesMenu.size());

        driver.findElement(By.cssSelector("[ng-href='/#/menu']")).click();
        driver.findElement(By.cssSelector("[ng-if='$navigation.hasFavorites()'] span")).click();

        String numbersLocator = "[title='Убрать из избранного']";
        int favouritesDel = driver.findElements(By.cssSelector(numbersLocator)).size();

        // Проверяем, что количество реестров, отображаемых на главной странице в блоке "Избранное", совпадает с колличеством реестров в виджете "Избранное"
        Assert.assertTrue(favouritesDel == favouritesMenu.size());

        while (favouritesDel > 0) {
            driver.findElement(By.cssSelector(numbersLocator)).click();
            wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(numbersLocator), favouritesDel - 1));
            favouritesDel = driver.findElements(By.cssSelector(numbersLocator)).size();

        }
        driver.get("http://black:8080/");

        // Проверяем, что на главной странице отсутствуют реестры в блоке "Избранное"
        Assert.assertTrue(isElementPresent(driver, By.cssSelector("._widget-favorites li [ng-bind='$item.name']")));


    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
