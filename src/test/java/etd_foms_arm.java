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

// АРМ пользователя

// Проверки:
// 1) Проверка "Напечатать отчет"
// 2) Проверка "Написать в техподдержку". Прикрепление файла "Проверка требований к АРМ.docx"
// 3) Проверка корректности заполнения окна с подсказкой "Рекомендуемые версии браузеров"
// 4) Проверка корректности заполнения окна КриптоПро ЭЦП Browser плагин с подсказкой "Рекомендованные версии"

public class etd_foms_arm {

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

        driver.findElement(By.cssSelector(".user-profile__icon")).click();
        TimeUnit.MILLISECONDS.sleep(400);
        driver.findElement(By.cssSelector(".MuiMenu-list li:nth-child(2) .dropdown__item")).click();
        TimeUnit.MILLISECONDS.sleep(1000);

        // Проверка "Напечатать отчет"

        driver.findElement(By.cssSelector(".css-1t62lt9 button:first-child")).click();

        // При нажатии на кнопку "Напечатать отчет", при успешном скачивании файла, в DOM появляется элемент с локатором ".MuiTouchRipple-child"
        // Если такой элемент не появляется - кнопка не кликабельна, скачивание файла не успешно
        WebElement print = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".MuiTouchRipple-child")));
        String printText = print.getAttribute("outerHTML");
        System.out.println("Элемент в DOM присутствует: " + printText);

        // Проверка "Написать в техподдержку". Прикрепление файла "Проверка требований к АРМ.docx"

        String fileIssue = "Проверка требований к АРМ.docx";
        driver.findElement(By.cssSelector(".css-1t62lt9 button:last-child")).click();
        TimeUnit.MILLISECONDS.sleep(2000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".files-item")));
        String fileText = driver.findElement(By.cssSelector(".files-item__name")).getText();
        System.out.println("Прикрепленный файл: " + fileText);
        Assert.assertEquals(fileText, fileIssue);
        driver.findElement(By.cssSelector(".MuiDialogActions-spacing button:last-child")).click();

        // Проверка корректности заполнения окна с подсказкой "Рекомендуемые версии браузеров"

        driver.findElement(By.cssSelector(".armcheck_body > div:nth-child(2) > div:first-child > div > div:last-child > div:first-child > div:last-child tr:nth-child(4) p")).click();
        TimeUnit.MILLISECONDS.sleep(1000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[role=dialog]")));

        String h1 = "Рекомендуемые версии браузеров";
        String li1 = "Chromium GOST Версия 86.0.4240.111 и выше";
        String li2 = "Интернет- браузер «Яндекс.Браузер»";

        String h1_Text = driver.findElement(By.cssSelector("[role=dialog] h6")).getText();
        System.out.println("Заголовок окна с подсказкой \"Рекомендуемые версии браузеров\": " + h1_Text);
        Assert.assertEquals(h1, h1_Text);

        String li1_Text = driver.findElement(By.cssSelector("[role=dialog] ul li:first-child")).getText();
        System.out.println("1) " + li1_Text);
        String li2_Text = driver.findElement(By.cssSelector("[role=dialog] ul li:last-child")).getText();
        System.out.println("2) " + li2_Text);
        Assert.assertEquals(li1, li1_Text);
        Assert.assertEquals(li2, li2_Text);
        driver.findElement(By.cssSelector("[role=dialog] button")).click();
        TimeUnit.MILLISECONDS.sleep(1000);

        // Проверка корректности заполнения окна КриптоПро ЭЦП Browser плагин с подсказкой "Рекомендованные версии"

        driver.findElement(By.cssSelector(".armcheck_body > div:nth-child(2) > div:first-child > div > div:last-child > div:nth-child(2) p")).click();
        TimeUnit.MILLISECONDS.sleep(1000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[role=dialog]")));

        String h2 = "Необходима установка дополнительных плагинов";
        String plugins = "Должны быть установлены: Плагин КриптоПро ЭЦП Browser plug -in версия 2.0 и выше; " +
                "Расширение «CryptoPro Extension for CAdES Browser Plug-in» для браузеров «Яндекс.Браузер» или «Chromium GOST Версия 86.0.4240.111» и выше.";

        String h2_Text = driver.findElement(By.cssSelector("[role=dialog] h6")).getText();
        System.out.println("Заголовок окна с подсказкой \"Необходима установка дополнительных плагинов\": " + h2_Text);
        Assert.assertEquals(h2, h2_Text);

        String pluginsText = driver.findElement(By.cssSelector(".arm_check-plugins-result")).getText();
        System.out.println(pluginsText);
        Assert.assertEquals(plugins, pluginsText);

    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
