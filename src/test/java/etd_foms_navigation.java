import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

// Прокликивает последовательно все пункты меню навигации слева, включая вложенные пункты.
// Для каждой страницы проверяет наличие заголовка (элемента с тэгом h1)

public class etd_foms_navigation {

    private EventFiringWebDriver driver;
    private WebDriverWait wait;

    public static class MyListener extends AbstractWebDriverEventListener {
        @Override
        public void beforeFindBy(By by, WebElement element, WebDriver driver) {
            System.out.println(by);
        }

        @Override
        public void afterFindBy(By by, WebElement element, WebDriver driver) {
            System.out.println(by + " found");
        }
    }

    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new EventFiringWebDriver(new ChromeDriver(options));
        driver.register(new MyListener());
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        //driver.manage().timeouts().pageLoadTimeout(6, TimeUnit.SECONDS);
        //wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    boolean isElementPresent(WebDriver driver, By locator) {
        return driver.findElements(locator).size() > 0;
    }

    @Test
    public void myFirstTest() throws InterruptedException {
        driver.get("https://d-customer-balancer-iam-proxy-01.foms.novalocal/etd-front/"); //https://d-customer-balancer-iam-proxy-02.foms.novalocal/etd-front
        driver.findElement(By.cssSelector("#details-button")).click();
        driver.findElement(By.cssSelector("#proceed-link")).click();
        driver.findElement(By.cssSelector("#details-button")).click();
        driver.findElement(By.cssSelector("#proceed-link")).click();
        driver.findElement(By.cssSelector("#zocial-esia")).click();
        driver.findElement(By.cssSelector("#login")).sendKeys("ajurba@mail.ru");
        driver.findElement(By.cssSelector("#password")).sendKeys("Zaxscdvfbg321-");
        driver.findElement(By.cssSelector("#loginByPwdButton .ui-button-text")).click();
        driver.findElement(By.cssSelector("button:nth-child(2) .name")).click();

        driver.findElement(By.cssSelector(".menu-nav [data-testid=MenuIcon]")).click();

        List<WebElement> menu = driver.findElements(By.cssSelector(".treeItem-level-0 h6"));

        for (int i = 0; i < menu.size(); i++) {
            menu = driver.findElements(By.cssSelector(".treeItem-level-0 h6"));
            menu.get(i).click();
            TimeUnit.MILLISECONDS.sleep(500);

            int sub_menu = driver.findElements(By.cssSelector(".MuiCollapse-wrapperInner .treeItem-level-1 .treeItem-node h6")).size();

            if (sub_menu > 0) {
                List<WebElement> menu_sub = driver.findElements(By.cssSelector(".MuiCollapse-wrapperInner .treeItem-level-1 .treeItem-node h6"));

                for (int j = 0; j < menu_sub.size(); j++) {
                    //menu_sub = driver.findElements(By.cssSelector(".MuiCollapse-wrapperInner .treeItem-level-1 .treeItem-node h6"));
                    menu_sub.get(j).click();
                    TimeUnit.MILLISECONDS.sleep(500);

//                    List<WebElement> el = driver.findElements(By.cssSelector("li ul .MuiCollapse-root h6"));
//
//                    if (el.size() == 0) {
//                        wait.until(ExpectedConditions.invisibilityOfAllElements(el));
//                    }

                    int sub_menu2 = driver.findElements(By.cssSelector("li ul .MuiCollapse-root h6")).size();

                    if (sub_menu2 > 0) {
                        List<WebElement> menu_sub2 = driver.findElements(By.cssSelector("li ul .MuiCollapse-root h6"));

                        for (int k = 0; k < menu_sub2.size(); k++) {
                            //List<WebElement> elements = wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("li ul .MuiCollapse-root h6"), menu_sub2.size()));
                            //wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("li ul .MuiCollapse-root h6")));
                            //List<WebElement> sub2 = driver.findElements(By.cssSelector("li ul .MuiCollapse-root [aria-selected=false]"));
                            menu_sub2 = driver.findElements(By.cssSelector("li ul .MuiCollapse-root h6"));
                            menu_sub2.get(k).click();
                            //TimeUnit.SECONDS.sleep(2);
                            //wait.until(ExpectedConditions.stalenessOf(menu_sub2.get(k)));

                            //wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-selected=true]")));

                        }
                    }

                }

            }
            if (isElementPresent(driver, By.cssSelector("[data-testid=ExpandMoreIcon]"))) {
                driver.findElement(By.cssSelector("[data-testid=ExpandMoreIcon]")).click();
                TimeUnit.MILLISECONDS.sleep(500);
            }

        }

    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
