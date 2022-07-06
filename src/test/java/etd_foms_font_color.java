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

// Проверка доработатки шрифтов и цвета в ЕТД согласно макету figma.com UI_KIT
// https://www.figma.com/file/O0z2FQspwRfNSfPypw1Uvz/UI_KIT.-v.-1.0?node-id=299%3A53875
//Шрифт и цвет шапки - ок
//Шрифт заголовков виджетов
//Шрифт вкладок
//Шрифт навигации
//Шрифт "хлебных крошек"

public class etd_foms_font_color {

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

        // Шрифт и цвет шапки

        String fontKit = "RobotoMedium";
        String colorKit = "rgba(0, 101, 177, 1)";

        // Шрифт заголовка
        String textLogoTitle = driver.findElement(By.cssSelector(".logo__title")).getText();

        String fontLogoTitle = driver.findElement(By.cssSelector(".logo__title")).getCssValue("font");
        System.out.println("Шапка. Текст элемента заголовка : " + textLogoTitle);
        System.out.println("Шапка. Шрифт элемента заголовка: " + fontLogoTitle);

        String[] words = fontLogoTitle.split(" ");
        for (String word : words) {
            //System.out.println(word);

            if (word.equals("RobotoMedium,")) {
                fontLogoTitle = fontKit;
                System.out.println("Шапка. Шрифт элемента заголовка совпадает со шрифтом, согласно макету: " + fontLogoTitle);
            }
        }
        Assert.assertEquals(fontLogoTitle, fontKit);

        // Цвет заголовка
        String colorTitle = driver.findElement(By.cssSelector(".logo__title")).getCssValue("color");
        System.out.println("Шапка. Цвет элемента заголовка: " + colorTitle);
        Assert.assertEquals(colorTitle, colorKit);
        System.out.println("Шапка. Цвет элемента заголовка совпадает с цветом, согласно макету: " + colorKit);

        // Шрифт пользователя
        String textLogoUser = driver.findElement(By.cssSelector(".user-select button")).getText();

        String fontLogoUser = driver.findElement(By.cssSelector(".user-select button")).getCssValue("font");
        System.out.println("Шапка. Текст элемента пользователя: " + textLogoUser);
        System.out.println("Шапка. Шрифт элемента пользователя: " + fontLogoUser);

        String[] words2 = fontLogoUser.split(" ");
        for (String word : words2) {
            //System.out.println(word);

            if (word.equals("RobotoMedium,")) {
                fontLogoUser = fontKit;
                System.out.println("Шапка. Шрифт элемента пользователя совпадает со шрифтом, согласно макету: " + fontLogoUser);
            }
        }
        Assert.assertEquals(fontLogoUser, fontKit);

        // Цвет пользователя
        String colorUser = driver.findElement(By.cssSelector(".user-select button")).getCssValue("color");
        System.out.println("Шапка. Цвет элемента пользователя: " + colorUser);
        Assert.assertEquals(colorUser, colorKit);
        System.out.println("Шапка. Цвет элемента пользователя совпадает с цветом, согласно макету: " + colorKit);

    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
