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

// Проверка доработки шрифтов и цвета в ЕТД согласно макету figma.com UI_KIT
// https://www.figma.com/file/O0z2FQspwRfNSfPypw1Uvz/UI_KIT.-v.-1.0?node-id=299%3A53875
//
// Проверки:
// 1) Шрифт и цвет текста шапки
// 2) Шрифт заголовков виджетов
// 3) Шрифт навигации. Подсистем и реестров
// 4) Шрифт вкладок
// 5) Шрифт "хлебных крошек"

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

    public void fontRoboto(String font, String fontKit, String text) {
        String[] words = font.split(" ");

        for (String word : words) {

            if (word.equals(fontKit + ",")) {
                font = fontKit;
                System.out.println(text + font);
            }
        }
        Assert.assertEquals(font, fontKit);
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

        // Проверка настройки "Показывать навигационную цепочку"

        driver.findElement(By.cssSelector(".user-profile__icon")).click();
        TimeUnit.MILLISECONDS.sleep(400);
        driver.findElement(By.cssSelector(".MuiMenu-list li:nth-child(3) .dropdown__item")).click();
        TimeUnit.MILLISECONDS.sleep(400);
        driver.findElement(By.cssSelector("#showBreadcrumbs")).click();
        TimeUnit.MILLISECONDS.sleep(1000);

        if (isElementPresent(driver, By.cssSelector("#showBreadcrumbs[value=false]"))) {
            driver.findElement(By.cssSelector("#showBreadcrumbs[value=false]")).click();
            TimeUnit.MILLISECONDS.sleep(1000);
        }
        driver.findElement(By.cssSelector("[data-testid=HomeIcon]")).click();

        // Шрифт и цвет шапки

        String fontTitleKit = "RobotoMedium";
        String colorKit = "rgba(0, 101, 177, 1)";

        // Шрифт заголовка
        String textLogoTitle = driver.findElement(By.cssSelector(".logo__title")).getText();
        String fontLogoTitle = driver.findElement(By.cssSelector(".logo__title")).getCssValue("font");

        System.out.println("Шапка. Текст элемента заголовка : " + textLogoTitle);
        System.out.println("Шапка. Шрифт элемента заголовка: " + fontLogoTitle);

        String titleEquals = "Шапка. Шрифт элемента заголовка совпадает со шрифтом, согласно макету: ";
        fontRoboto(fontLogoTitle, fontTitleKit, titleEquals);

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

        String userEquals = "Шапка. Шрифт элемента пользователя совпадает со шрифтом, согласно макету: ";
        fontRoboto(fontLogoUser, fontTitleKit, userEquals);

        // Цвет пользователя
        String colorUser = driver.findElement(By.cssSelector(".user-select button")).getCssValue("color");
        System.out.println("Шапка. Цвет элемента пользователя: " + colorUser);
        Assert.assertEquals(colorUser, colorKit);
        System.out.println("Шапка. Цвет элемента пользователя совпадает с цветом, согласно макету: " + colorKit);

        //Шрифт заголовков виджетов

        String fontWidgetKit = "Roboto";

        List<WebElement> widgets = driver.findElements(By.cssSelector(".widget-title"));

        for (int i = 0; i < widgets.size(); i++) {

            // Шрифт виджета
            String textWidget = widgets.get(i).getText();
            String fontWidget = widgets.get(i).getCssValue("font");

            System.out.println("Виджеты. Текст элемента: " + textWidget);
            System.out.println("Виджеты. Шрифт элемента: " + fontWidget);

            String widgetEquals = "Виджеты. Шрифт элемента виджета совпадает со шрифтом, согласно макету: ";
            fontRoboto(fontWidget, fontWidgetKit, widgetEquals);
        }

        //Шрифт навигации

        List<WebElement> menu = driver.findElements(By.cssSelector(".subsystem-widget"));

        for (int i = 0; i < menu.size(); i++) {
            menu = driver.findElements(By.cssSelector(".subsystem-widget"));
            String s = menu.get(i).getAttribute("textContent");

            if (s.equals("РМП")) {
                menu.get(i).click();
                break;
            }
        }

        //Шрифт подсистем
        String fontMenuKit = "RobotoMedium";

        String textMenu = driver.findElement(By.cssSelector(".MuiTypography-h6")).getText();
        String fontMenu = driver.findElement(By.cssSelector(".MuiTypography-h6")).getCssValue("font-family");

        System.out.println("Меню навигации. Текст элемента меню: " + textMenu);
        System.out.println("Меню навигации. Шрифт элемента меню: " + fontMenu);

        String menuEquals = "Меню навигации. Шрифт элемента меню совпадает со шрифтом, согласно макету: ";
        fontRoboto(fontMenu, fontMenuKit, menuEquals);

        //Шрифт реестров
        String fontRegistryKit = "Roboto";

        String textRegistry = driver.findElement(By.cssSelector(".bookmark__text")).getText();
        String fontRegistry = driver.findElement(By.cssSelector(".bookmark__text")).getCssValue("font-family");

        System.out.println("Меню навигации. Текст элемента реестра: " + textRegistry);
        System.out.println("Меню навигации. Шрифт элемента реестра: " + fontRegistry);

        String registryEquals = "Меню навигации. Шрифт элемента реестра совпадает со шрифтом, согласно макету: ";
        fontRoboto(fontRegistry, fontRegistryKit, registryEquals);

        //Шрифт вкладок

        driver.findElement(By.cssSelector(".bookmark__text")).click();
        TimeUnit.MILLISECONDS.sleep(10000);

        String fontTabKit = "RobotoMedium";

        String textTab = driver.findElement(By.cssSelector(".customTab__label")).getText();
        String fontTab = driver.findElement(By.cssSelector(".customTab__label")).getCssValue("font");

        System.out.println("Вкладки. Текст элемента вкладки: " + textTab);
        System.out.println("Вкладки. Шрифт элемента вкладки: " + fontTab);

        String tabEquals = "Вкладки. Шрифт элемента вкладки совпадает со шрифтом, согласно макету: ";
        fontRoboto(fontTab, fontTabKit, tabEquals);

        //Шрифт "хлебных крошек"

        String fontBreadcrumbsKit = "Roboto";

        String textBreadcrumbs = driver.findElement(By.cssSelector(".breadcrumbs__item_last")).getText();
        String fontBreadcrumbs = driver.findElement(By.cssSelector(".breadcrumbs__item_last")).getCssValue("font-family");

        System.out.println("Хлебные крошки. Текст элемента цепочки: " + textBreadcrumbs);
        System.out.println("Хлебные крошки. Шрифт элемента цепочки: " + fontBreadcrumbs);

        String breadcrumbsEquals = "Хлебные крошки. Шрифт элемента цепочки совпадает со шрифтом, согласно макету: ";
        fontRoboto(fontBreadcrumbs, fontBreadcrumbsKit, breadcrumbsEquals);

    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
