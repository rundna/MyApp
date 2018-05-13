package com.kodilla.testing2.crudapp;

import com.kodilla.testing2.config.WebDriverConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class CrudAppTestSuite {

    private static final String BASE_URL = "https://rundna.github.io/";
    private WebDriver driver;
    private Random generator;

//    private static final String XPATH_TASK_NAME = "//form[contains(@action, \"createTask\")]/fieldset[1]/input";
//    //private static final String XPATH_TASK_CONTENT = "//form[contains(@action, \"createTask\")]fieldset[2]/textarea";
//    private static final String XPATH_TASK_CONTENT = "//form[contains(@action, \"createTask\")]//textarea[1]";
//    //private static final String XPATH_TASK_CONTENT = "//html/body/main/section[1]/form/fieldset[2]/textarea";
//    //private static final String XPATH_ADD_BUTTON = "//form[contains(@action, \"createTask\")]fieldset[3]/button";
//    private static final String XPATH_ADD_BUTTON = "//form[contains(@action, \"createTask\")]fieldset[3]/button";
//    //private static final String XPATH_ADD_BUTTON = "//form[contains(@action, \"createTask\")]//button[1]";



    @Before
    public void initTests(){
        driver = WebDriverConfig.getDriver(WebDriverConfig.CHROME);
        driver.get(BASE_URL);
        generator = new Random();
    }

    @After
    public void cleanUpAfterTest(){
        driver.close();
    }


    public String createCrudAppTestTask() throws InterruptedException{
        final String XPATH_TASK_NAME = "//form[contains(@action, \"createTask\")]//input[1]";
        final String XPATH_TASK_CONTENT = "//form[contains(@action, \"createTask\")]//textarea[1]";
        final String XPATH_ADD_BUTTON = "//form[contains(@action, \"createTask\")]//button[1]";


        String taskName = "Task number "+generator.nextInt(100000);
        String taskContent = taskName + " content";

        WebElement name=driver.findElement(By.xpath(XPATH_TASK_NAME));
        name.sendKeys(taskName);

        WebElement content = driver.findElement(By.xpath(XPATH_TASK_CONTENT));
        content.sendKeys(taskContent);

        WebElement addButton = driver.findElement(By.xpath(XPATH_ADD_BUTTON));
        addButton.click();
        Thread.sleep(2000);
        return taskName;
    }

    public void deleteCrudAppTestTask(String taskName) throws InterruptedException{
        driver.navigate().refresh();

        while(!driver.findElement(By.xpath("//select[1]")).isDisplayed());

        driver.findElements(By.xpath("//form[@class=\"datatable__row\"]")).stream()
                .filter(anyForm-> anyForm.findElement(By.xpath(".//p[@class=\"datatable__field-value\"]"))
                        .getText().equals(taskName))
                .forEach(theForm->{
                    WebElement deleteButton =
                            theForm.findElement(By.xpath(".//button[4]"));
                    deleteButton.click();
                });
        //Thread.sleep(2000);

    }

    private void sendTestTaskToTrello(String taskName) throws InterruptedException{
        driver.navigate().refresh();

        while(!driver.findElement(By.xpath("//select[1]")).isDisplayed());

        driver.findElements(By.xpath("//form[@class=\"datatable__row\"]")).stream()
                .filter(anyForm-> anyForm.findElement(By.xpath(".//p[@class=\"datatable__field-value\"]"))
                        .getText().equals(taskName))
                .forEach(theForm->{
                    WebElement selectElement = theForm.findElement(By.xpath("//select[1]"));
                    Select select = new Select(selectElement);
                    select.selectByIndex(1);
                    WebElement selectElement2 = theForm.findElement(By.xpath("//select[2]"));
                    Select select2 = new Select(selectElement2);
                    select2.selectByIndex(1);

                    WebElement buttonCreateCard =
                            theForm.findElement(By.xpath(".//button[contains(@class,\"card-creation\")]"));
                    buttonCreateCard.click();
                });
        Thread.sleep(5000);

    }


    @Test
    public void shouldCreateTrelloCard() throws InterruptedException {
        String taskName = createCrudAppTestTask();
        sendTestTaskToTrello(taskName);
        deleteCrudAppTestTask(taskName);
        assertTrue(checkTaskExistsInTrello(taskName));
    }

        private boolean checkTaskExistsInTrello(String taskName) throws InterruptedException {
            final String TRELLO_URL = "https://trello.com/login";
            boolean result = false;
            WebDriver driverTrello = WebDriverConfig.getDriver(WebDriverConfig.CHROME);
            driverTrello.get(TRELLO_URL);

            driverTrello.findElement(By.id("user")).sendKeys("");
            driverTrello.findElement(By.id("password")).sendKeys("");
            driverTrello.findElement(By.id("login")).submit();

            Thread.sleep(2000);

            driverTrello.findElements(By.xpath("a@class=\"board-title\"]")).stream()
                    .filter(aHref -> aHref.findElements(By.xpath(".//span[@title=\"Kodilla Application\"]")).size()>0)
                    .forEach(aHref->aHref.click());

            Thread.sleep(2000);

            result = driverTrello.findElements(By.xpath("//span")).stream()
                    .filter(theSpan->theSpan.getText().contains(taskName))
                    .collect(Collectors.toList())
                    .size()>0;

            driverTrello.close();

            return result;






    }
}
