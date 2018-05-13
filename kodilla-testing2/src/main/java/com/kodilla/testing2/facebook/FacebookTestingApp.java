package com.kodilla.testing2.facebook;

import com.kodilla.testing2.config.WebDriverConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class FacebookTestingApp {

    public static final String XPATH_BIRTHDAY = "//div[contains(@class,\"_5k_5\")]/span/span/select[1]";
    public static final String XPATH_BIRTHMONTH = "//div[contains(@class,\"_5k_5\")]/span/span/select[2]";
    public static final String XPATH_BIRTHYEAR = "//div[contains(@class,\"_5k_5\")]/span/span/select[3]";


    public static final String XPATH_SELECT1 = "//select[1]";
    public static final String XPATH_SELECT2 = "//select[2]";
    public static final String XPATH_SELECT3 = "//select[3]";

    public static final String XPATH_ADD_TASK = "//html/body/main/section/form/fieldset[3]/button";

    public static void main(String[] args) {
        WebDriver driver = WebDriverConfig.getDriver(WebDriverConfig.CHROME);
        driver.get("https://www.facebook.com");

        //WebElement searchField = driver.findElement(By.xpath(XPATH_INPUT));
        //searchField.sendKeys("New robotic task");

        //WebElement textareaField = driver.findElement(By.xpath(XPATH_TEXTAREA));
        //textareaField.sendKeys("Robotic content2");

       // WebElement birthDay = driver.findElement(By.xpath(XPATH_BIRTHDAY));
        //birthDay.click();

        //while (!driver.findElement(By.xpath(XPATH_BIRTHDAY)).isDisplayed()) ;
        WebElement selectDay=driver.findElement(By.xpath(XPATH_BIRTHDAY));
        WebElement selectMonth=driver.findElement(By.xpath(XPATH_BIRTHMONTH));
        WebElement selectYear=driver.findElement(By.xpath(XPATH_BIRTHYEAR));
        Select selectFDay = new Select(selectDay);
        selectFDay.selectByValue("20");
        Select selectFMonth = new Select(selectMonth);
        selectFMonth.selectByIndex(6);
        //selectMonth.submit();
        Select selectFYear = new Select(selectYear);
        selectFYear.selectByValue("2006");
        //selectYear.submit();


    }
}

