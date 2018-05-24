package com.crud.tasks.com.crud.tasks.web;

import com.kodilla.testing2.config.WebDriverConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class CrudAppTestingApp {

    public static final String XPATH_INPUT = "//html/body/main/section/form/fieldset/input";
    public static final String XPATH_TEXTAREA = "//html/body/main/section/form/fieldset[2]/textarea";
    public static final String XPATH_SELECT = "//div[contains(@class, \"tasks-container\")]/form/div/fieldset/select[1]";
    public static final String XPATH_WAIT_FOR = "//select[1]";
    public static final String XPATH_ADD_TASK = "//html/body/main/section/form/fieldset[3]/button";

    public static void main(String[] args){
        WebDriver driver = WebDriverConfig.getDriver(WebDriverConfig.CHROME);
        driver.get("https://rundna.github.io/");

        WebElement searchField = driver.findElement(By.xpath(XPATH_INPUT));
        searchField.sendKeys("New robotic task");

        WebElement textareaField=driver.findElement(By.xpath(XPATH_TEXTAREA));
        textareaField.sendKeys("Robotic content2");

        while (!driver.findElement(By.xpath(XPATH_WAIT_FOR)).isDisplayed());

        WebElement addTask = driver.findElement(By.xpath(XPATH_ADD_TASK));
        //addTask.isSelected();
        addTask.submit();
        WebElement selectCombo=driver.findElement(By.xpath(XPATH_SELECT));
        Select selectBoard = new Select(selectCombo);
        selectBoard.selectByIndex(1);


    }

}
