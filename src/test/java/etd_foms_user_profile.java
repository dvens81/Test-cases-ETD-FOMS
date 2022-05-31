import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class etd_foms_user_profile {

    private WebDriverWait wait;
    private WebDriver driver;

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    }

    boolean isElementPresent (WebDriver driver, By locator) {
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

        driver.findElement(By.cssSelector(".user-profile__icon")).click();
        TimeUnit.MILLISECONDS.sleep(400);
        driver.findElement(By.cssSelector(".MuiMenu-list li:first-child .dropdown__item")).click();
        TimeUnit.MILLISECONDS.sleep(400);

        driver.findElement(By.cssSelector(".avatar")).click();

        // Проверка наличия аватарки. Если аватарка есть - удаляем. Проверяем, что удаление выполнилось корректно

        if (isElementPresent(driver, By.cssSelector("input[type=file]"))) {
            driver.findElement(By.cssSelector(".dialog-buttons button:nth-child(2)")).click();
            driver.findElement(By.cssSelector(".app > div:last-child .dialog-buttons button:first-child")).click();
            WebElement alertDialog = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".alert-dialog-content")));
            String alertDialogText = alertDialog.getText();
            System.out.println(alertDialogText);
            Assert.assertEquals(alertDialogText, "Фотография успешно удалена");
            driver.findElement(By.cssSelector(".avatar")).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type=file]")));

        }

        // Загрузка новой аватарки. Проверяем, что загрузка выполнилась корректно

        // jpg
        WebElement input_file_jpg = driver.findElement(By.cssSelector("input[type=file]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='block';", input_file_jpg);
        input_file_jpg.sendKeys("C:/Download/Avatar вложения/avatar-2.jpg");
        driver.findElement(By.cssSelector("[type=submit]")).click();

        WebElement alertDialog_jpg = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".alert-dialog-content")));
        String alertDialogText_jpg = alertDialog_jpg.getText();
        System.out.println(alertDialogText_jpg + " .jpg");
        Assert.assertEquals(alertDialogText_jpg, "Фотография успешно загружена");
        TimeUnit.MILLISECONDS.sleep(1000);

        driver.findElement(By.cssSelector(".avatar")).click();
        driver.findElement(By.cssSelector(".dialog-buttons button:nth-child(2)")).click();
        driver.findElement(By.cssSelector(".app > div:last-child .dialog-buttons button:first-child")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".alert-dialog-content")));
        driver.findElement(By.cssSelector(".avatar")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type=file]")));

        // png
        WebElement input_file_png = driver.findElement(By.cssSelector("input[type=file]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='block';", input_file_png);
        input_file_png.sendKeys("C:/Download/Avatar вложения/management.png");
        driver.findElement(By.cssSelector("[type=submit]")).click();

        WebElement alertDialog_png = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".alert-dialog-content")));
        String alertDialogText_png = alertDialog_png.getText();
        System.out.println(alertDialogText_png + " .png");
        Assert.assertEquals(alertDialogText_png, "Фотография успешно загружена");
        TimeUnit.MILLISECONDS.sleep(1000);

        driver.findElement(By.cssSelector(".avatar")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".avatar-actions-dialog__image")));
        driver.findElement(By.cssSelector(".dialog-buttons button:nth-child(2)")).click();
        driver.findElement(By.cssSelector(".app > div:last-child .dialog-buttons button:first-child")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".alert-dialog-content")));
        driver.findElement(By.cssSelector(".avatar")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type=file]")));

        // gif
        WebElement input_file_gif = driver.findElement(By.cssSelector("input[type=file]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='block';", input_file_gif);
        input_file_gif.sendKeys("C:/Download/Avatar вложения/avatar18.gif");
        driver.findElement(By.cssSelector("[type=submit]")).click();

        WebElement alertDialog_gif = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".alert-dialog-content")));
        String alertDialogText_gif = alertDialog_gif.getText();
        System.out.println(alertDialogText_gif + " .gif");
        Assert.assertEquals(alertDialogText_gif, "Фотография успешно загружена");

    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
