import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

// Прокликивает последовательно все подсистемы меню навигации слева и проверяет, что реестры расположены в алфавитном порядке

public class etd_foms_navigation_sort {

    private EventFiringWebDriver driver;
    private WebDriverWait wait;

    public static class MyListener extends AbstractWebDriverEventListener {
        @Override
        public void beforeFindBy(By by, WebElement element, WebDriver driver) {
            //System.out.println(by);
        }

        @Override
        public void afterFindBy(By by, WebElement element, WebDriver driver) {
            //System.out.println(by + " found");
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
        wait = new WebDriverWait(driver, Duration.ofSeconds(1));

    }

    boolean isElementPresent(WebDriver driver, By locator) {
        return driver.findElements(locator).size() > 0;
    }

    @Test
    public void myFirstTest() {
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

        driver.findElement(By.cssSelector(".menu-nav [data-testid=MenuIcon]")).click();

        List<WebElement> menu = driver.findElements(By.cssSelector(".treeItem-level-0 h6"));

        ArrayList<String> sub_menu_List = new ArrayList<>();

        for (int i = 0; i < menu.size(); i++) {
            menu = driver.findElements(By.cssSelector(".treeItem-level-0 h6"));
            menu.get(i).click();

            List<WebElement> sub_menu = driver.findElements(By.cssSelector(".bookmark__text"));

            for (int j = 0; j < sub_menu.size(); j++) {
                String attr_sub_menu = sub_menu.get(j).getAttribute("textContent");
                String attr_sub_menu_registr = attr_sub_menu.toLowerCase();
                sub_menu_List.add(j, attr_sub_menu_registr);

            }
            System.out.println("sub_menu_List " + sub_menu_List);

            ArrayList<String> sub_menu_sort_List = new ArrayList<>();
            for (int j = 0; j < sub_menu_List.size(); j++) {
                sub_menu_sort_List.add(j, sub_menu_List.get(j));
            }

            Collections.sort(sub_menu_sort_List);
            System.out.println("sub_menu_sort_List " + sub_menu_sort_List);
            Assert.assertEquals(sub_menu_List, sub_menu_sort_List);
            sub_menu_List.clear();
            sub_menu_sort_List.clear();

            if (isElementPresent(driver, By.cssSelector("[data-testid=ExpandMoreIcon]"))) {
                driver.findElement(By.cssSelector("[data-testid=ExpandMoreIcon]")).click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".MuiCollapse-wrapperInner .treeItem-level-1 .treeItem-node h6")));
            }


        }

    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
