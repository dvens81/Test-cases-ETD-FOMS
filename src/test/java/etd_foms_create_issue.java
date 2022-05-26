import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class etd_foms_create_issue {

    private WebDriverWait wait;
    private WebDriver driver;

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        //driver.manage().timeouts().pageLoadTimeout(6, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));

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

        // Создание обращения

        driver.findElement(By.cssSelector(".support-button__icon")).click();
        TimeUnit.MILLISECONDS.sleep(400);
        driver.findElement(By.cssSelector(".MuiList-root span:first-child")).click();
        TimeUnit.MILLISECONDS.sleep(400);
        driver.findElement(By.cssSelector(".MuiOutlinedInput-root [role=button]")).click();
        driver.findElement(By.cssSelector("[data-value=РМП]")).click();
        driver.findElement(By.cssSelector(".MuiGrid-grid-md-7 div:nth-child(3) [role=button]")).click();
        driver.findElement(By.cssSelector("[role=listbox] li:first-child")).click();
        String topic_issue = "Тест, тест 123 !";
        driver.findElement(By.cssSelector("input[name=summary]")).sendKeys(topic_issue);
        WebElement textarea = driver.findElement(By.cssSelector(".field-container_nativeTextarea textarea"));
        textarea.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        textarea.sendKeys(Keys.chord(Keys.DELETE));
        String description_issue = "Не открывается \"Реестр входящих документов\"";
        textarea.sendKeys(description_issue);
        driver.findElement(By.cssSelector("[placeholder='example@email.com']")).sendKeys("test@otr.ru");
        driver.findElement(By.cssSelector(".MuiAccordionDetails-root > div:nth-child(1) button")).click();
        WebElement phone = driver.findElement(By.cssSelector("[name=phone-0]"));
        phone.sendKeys(Keys.chord(Keys.HOME));
        phone.sendKeys("9876543210");
        driver.findElement(By.cssSelector(".MuiAccordionDetails-root > div:nth-child(2) button")).click();

        WebElement input_file = driver.findElement(By.cssSelector("input[type=file]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='block';", input_file);
        input_file.sendKeys("C:/Download/Screen Recorder/foto.jpeg");

        driver.findElement(By.cssSelector(".MuiDialogActions-spacing button:nth-child(1)")).click();

        WebElement issue = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".alert-dialog-content__link")));
        String issue_number_create = issue.getText();
        String issue_number_create_registr = issue_number_create.toLowerCase();
        System.out.println("Номер созданного обращения: " + issue_number_create_registr);
        issue.click();

        // Проверки

        String issue_number_tab = driver.findElement(By.cssSelector(".customTab__label")).getText();
        String issue_number_tab_registr = issue_number_tab.toLowerCase();
        System.out.println("Номер обращения на вкладке: " + issue_number_tab_registr);
        Assert.assertEquals(issue_number_create_registr, issue_number_tab_registr);

        String topic_issue_card = driver.findElement(By.cssSelector(".issue-details__summary")).getText();
        System.out.println("Тема в форме обращения: " + topic_issue);
        System.out.println("Тема в карточке обращения: " + topic_issue_card);
        Assert.assertEquals(topic_issue, topic_issue_card);

        String description_issue_card = driver.findElement(By.cssSelector(".issue-details__description")).getText();
        System.out.println("Описание в форме создания обращения: " + description_issue);
        System.out.println("Описание в карточке обращения: " + description_issue_card);
        Assert.assertEquals(description_issue, description_issue_card);

        String userComment = "Тест комментария 123 !";
        driver.findElement(By.cssSelector("#userComment")).sendKeys(userComment);
        driver.findElement(By.cssSelector("[type=submit]")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".alert-dialog-content")));
        String userCommentIssueActivities = driver.findElement(By.cssSelector(".issue-activities__messages > div:first-child div > span")).getText();
        System.out.println("Введенный комментарий в карточке обращения: " + userComment);
        System.out.println("Отображаемый комментарий в поле Активности по обращению: " + userCommentIssueActivities);
        Assert.assertEquals(userComment, userCommentIssueActivities);

    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}

