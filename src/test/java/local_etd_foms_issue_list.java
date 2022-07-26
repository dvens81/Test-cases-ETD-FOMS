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
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class local_etd_foms_issue_list {

    private WebDriver driver;
    private WebDriverWait wait;

    private void pause() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(8000);
    }

    boolean isElementPresent1(WebDriver driver, By locator) {
        return driver.findElements(locator).size() == 1;
    }

    boolean isElementPresent2(WebDriver driver, By locator) {
        return driver.findElements(locator).size() == 2;
    }

    boolean isElementPresent9(WebDriver driver, By locator) {
        return driver.findElements(locator).size() >= 9;
    }

    private void checkStatus(String textStatus) {
        List<WebElement> paginationList = driver.findElements(By.cssSelector(".pagination span:not([ng-if='$table.pagination.page + 2 < $table.pagination.lastPage && $table.pagination.lastPage > 4'])"));
        int paginationNumberMax = 0;

        for (int i = 0; i < paginationList.size(); i++) {
            String getPaginationText = paginationList.get(i).getText();
            int paginationNumber = Integer.parseInt(getPaginationText);

            if (paginationNumber > paginationNumberMax) {
                paginationNumberMax = paginationNumber;
            }
        }

        for (int i = 0; i <= paginationNumberMax - 1; i++) {
            List<WebElement> list = driver.findElements(By.cssSelector(".table__content td:nth-child(6)"));
            for (int j = 0; j < list.size(); j++) {
                String getTextList = list.get(j).getText();
                Assert.assertEquals(textStatus, getTextList);
            }
            driver.findElement(By.cssSelector(".ion-chevron-right")).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".table__content td:nth-child(6)")));
        }
    }


    @Before
    public void start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    @Test
    public void test() throws InterruptedException {

        // Авторизация
        driver.get("http://black:8080/");
        TimeUnit.MILLISECONDS.sleep(10000);

        driver.findElement(By.cssSelector(".header__support-dropdown .btn__label")).click();
        TimeUnit.MILLISECONDS.sleep(400);
        driver.findElement(By.cssSelector(".header__support-dropdown a:last-child")).click();
        TimeUnit.MILLISECONDS.sleep(4000);

//        // Проверка чекбоксов обращений
//
//        driver.findElement(By.cssSelector(".table__body .custom-checkbox")).click();
//        wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(".chosen"), 1));
//        Assert.assertTrue(isElementPresent1(driver, By.cssSelector(".chosen")));
//
//        driver.findElement(By.cssSelector(".table__body tr:nth-child(2) .custom-checkbox")).click();
//        wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(".chosen"), 2));
//        Assert.assertTrue(isElementPresent2(driver, By.cssSelector(".chosen")));
//
//        driver.findElement(By.cssSelector(".table__filters .custom-checkbox")).click();
//        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".chosen")));
//        Assert.assertTrue(isElementPresent9(driver, By.cssSelector(".chosen")));
//
//        // Проверка "Напечатать отчет"
//
//        driver.findElement(By.cssSelector("[ng-click='printIssues()']")).click();
//
//        // При нажатии на кнопку "Печать", при успешном скачивании файла, в DOM меняются стили у элементов
//        String print1 = "[style='display: block;']";
//        String print2 = "[style='visibility: visible;']";
//        // Если стили элементов не изменяются - кнопка не кликабельна, скачивание файла не успешно
//        WebElement locator1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(print1)));
//        //System.out.println("print1: " + locator1.getAttribute("outerHTML"));
//        Assert.assertTrue(isElementPresent1(driver, By.cssSelector(print1)));
//
//        WebElement locator2 = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(print2)));
//        //System.out.println("print2: " + locator2.getAttribute("outerHTML"));
//        Assert.assertTrue(isElementPresent1(driver, By.cssSelector(print2)));
//
//        // Проверка поиска обращения по теме
//
//        String findText = "Не открылся реестр 404";
//        driver.findElement(By.cssSelector("input[name=search_summary]")).sendKeys(findText);
//        String getTextIssue = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".table__content td:nth-child(5)"))).getText();
//        Assert.assertEquals(findText, getTextIssue);
//        driver.findElement(By.cssSelector("input[name=search_summary]")).clear();

        // Проверка фильтра по статусам. Проверка статуса для каждого обращения на каждой странице pagination
        // Проверка неактивности кнопки "Отправить", при нажатии на "Ответить на запрос данных" и "Вернуть в работу" из статусов "Запрос данных" и "Приёмка" соответственно

//        driver.get("http://black:8080/#/app/issues");
//        TimeUnit.MILLISECONDS.sleep(7000);

//        driver.findElement(By.cssSelector(".table__filters th:nth-child(6)")).click();
//        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".table__filters th:nth-child(6) .dropdown-menu__item")));
//
//        // Открыто
//        String textStatusOpen = "Открыто";
//        driver.findElement(By.cssSelector(".table__filters th:nth-child(6) a:nth-child(1) span")).click();
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".table__content td:nth-child(6)")));
//
//        checkStatus(textStatusOpen);
//
//        driver.get("http://black:8080/#/app/issues");
//        pause();
//
//        driver.findElement(By.cssSelector(".table__filters th:nth-child(6)")).click();
//        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".table__filters th:nth-child(6) .dropdown-menu__item")));
//
//        // Запрос данных
//        String textStatusRequest = "Запрос данных";
//        driver.findElement(By.cssSelector(".table__filters th:nth-child(6) a:nth-child(2) span")).click();
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".table__content td:nth-child(6)")));
//
//        checkStatus(textStatusRequest);
//
//        driver.get("http://black:8080/#/app/issues");
//        pause();
//
//        // Ответить на запрос данных
//        driver.findElement(By.cssSelector(".table__filters th:nth-child(6)")).click();
//        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".table__filters th:nth-child(6) .dropdown-menu__item")));
//
//        driver.findElement(By.cssSelector(".table__filters th:nth-child(6) a:nth-child(2) span")).click();
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".table__content td:nth-child(6)")));
//
//        driver.findElement(By.cssSelector(".table__body .custom-checkbox")).click();
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[ng-if='!checkFKIssues'] a:first-child"))).click();
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".modal-content")));
//        Assert.assertTrue(isElementPresent1(driver, By.cssSelector(".modal-footer [disabled=disabled]")));
//
//        driver.get("http://black:8080/#/app/issues");
//        pause();
//
//        driver.findElement(By.cssSelector(".table__filters th:nth-child(6)")).click();
//        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".table__filters th:nth-child(6) .dropdown-menu__item")));
//
//        // Анализ
//        String textStatusAnalysis = "Анализ";
//        driver.findElement(By.cssSelector(".table__filters th:nth-child(6) a:nth-child(3) span")).click();
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".table__content td:nth-child(6)")));
//
//        checkStatus(textStatusAnalysis);
//
//        driver.get("http://black:8080/#/app/issues");
//        pause();
//
//        driver.findElement(By.cssSelector(".table__filters th:nth-child(6)")).click();
//        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".table__filters th:nth-child(6) .dropdown-menu__item")));
//
//        // Приёмка
//        String textStatusAccept = "Приёмка";
//        driver.findElement(By.cssSelector(".table__filters th:nth-child(6) a:nth-child(4) span")).click();
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".table__content td:nth-child(6)")));
//
//        checkStatus(textStatusAccept);
//
//        driver.get("http://black:8080/#/app/issues");
//        pause();
//
//        // Вернуть в работу
//        driver.findElement(By.cssSelector(".table__filters th:nth-child(6)")).click();
//        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".table__filters th:nth-child(6) .dropdown-menu__item")));
//
//        driver.findElement(By.cssSelector(".table__filters th:nth-child(6) a:nth-child(4) span")).click();
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".table__content td:nth-child(6)")));
//
//        driver.findElement(By.cssSelector(".table__body .custom-checkbox")).click();
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[ng-if='!checkFKIssues'] a:last-child"))).click();
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".modal-content")));
//        Assert.assertTrue(isElementPresent1(driver, By.cssSelector(".modal-footer [disabled=disabled]")));
//
//        driver.get("http://black:8080/#/app/issues");
//        pause();
//
//        driver.findElement(By.cssSelector(".table__filters th:nth-child(6)")).click();
//        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".table__filters th:nth-child(6) .dropdown-menu__item")));
//
//        // Проверка фильтра по статусам. Выбор нескольких статусов одновременно. Проверка статуса каждого обращения на каждой странице pagination
//
//        String textStatusMultiSelectRequest = "Запрос данных";
//        String textStatusMultiSelectAccept = "Приёмка";
//        driver.findElement(By.cssSelector(".table__filters th:nth-child(6) a:nth-child(2) span")).click();
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".table__content td:nth-child(6)")));
//        driver.findElement(By.cssSelector(".table__filters th:nth-child(6) a:nth-child(4) span")).click();
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".table__content td:nth-child(6)")));
//
//        List<WebElement> paginationList = driver.findElements(By.cssSelector(".pagination span:not([ng-if='$table.pagination.page + 2 < $table.pagination.lastPage && $table.pagination.lastPage > 4'])"));
//        int paginationNumberMax = 0;
//
//        for (int i = 0; i < paginationList.size(); i++) {
//            String getPaginationText = paginationList.get(i).getText();
//            int paginationNumber = Integer.parseInt(getPaginationText);
//
//            if (paginationNumber > paginationNumberMax) {
//                paginationNumberMax = paginationNumber;
//            }
//        }
//
//        for (int i = 0; i <= paginationNumberMax - 1; i++) {
//            List<WebElement> list = driver.findElements(By.cssSelector(".table__content td:nth-child(6)"));
//            for (int j = 0; j < list.size(); j++) {
//                String getTextList = list.get(j).getText();
//                Assert.assertTrue(Objects.equals(getTextList, textStatusMultiSelectRequest) || Objects.equals(getTextList, textStatusMultiSelectAccept));
//            }
//            driver.findElement(By.cssSelector(".ion-chevron-right")).click();
//            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".table__content td:nth-child(6)")));
//        }
//        driver.get("http://black:8080/#/app/issues");
//        pause();
//
//        // Проверка "Закрытые обращения". Проверка статуса "Закрыто" для каждого обращения на каждой странице pagination
//
//        driver.findElement(By.cssSelector(".align-items-center label.custom-checkbox")).click();
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".table__content td:nth-child(6)")));
//        String textStatusClose = "Закрыто";
//
//        checkStatus(textStatusClose);

        // Проверка фильтра по датам "Создано". Фильтрация созданных обращений по Диапазону и Месяцу.
        // Сортировка обращений по номерам в выбранном диапазоне/месяце.

        // Фильтрация по Диапазону
        driver.findElement(By.cssSelector(".table__filters th:nth-child(3) [ng-click='$dropdown.open($event);']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".table__filters th:nth-child(3) [ng-click='$dropdown.open($event);']._open")));
        driver.findElement(By.cssSelector(".table__filters th:nth-child(3) a:first-child")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".table__filters th:nth-child(3) ._right")));
        driver.findElement(By.cssSelector(".table__filters th:nth-child(3) input[title]")).sendKeys("13.07.2020/19.07.2020");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".table__content td:nth-child(2)")));
        driver.findElement(By.cssSelector(".table__head-row th:nth-child(2) .column-title")).click();
        TimeUnit.MILLISECONDS.sleep(400);

//        // Фильтрация по Месяцу
//        driver.findElement(By.cssSelector(".table__filters th:nth-child(3) [ng-click='$dropdown.open($event);']")).click();
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".table__filters th:nth-child(3) [ng-click='$dropdown.open($event);']._open")));
//        driver.findElement(By.cssSelector(".table__filters th:nth-child(3) a:last-child")).click();
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".table__filters th:nth-child(3) ._right")));
//        driver.findElement(By.cssSelector(".table__filters th:nth-child(3) input[title]")).sendKeys("Май 2020");
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".table__content td:nth-child(2)")));
//        driver.findElement(By.cssSelector(".table__head-row th:nth-child(2) .column-title")).click();
//        TimeUnit.MILLISECONDS.sleep(400);

    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }


}
