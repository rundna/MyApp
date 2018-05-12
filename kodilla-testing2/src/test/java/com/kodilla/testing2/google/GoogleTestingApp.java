package com.kodilla.testing2.google;

import com.kodilla.testing2.config.WebDriverConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GoogleTestingApp {
    public static final String SEARCHFIELD = "gh-ac";

    public static void main(String[] args) {
        //System.getProperty("webdriver.gecko.driver","c:\\Selenium-drivers\\Opera\\geckodriver.exe");
        //System.getProperty("webdriver.chrome.driver","c:\\Selenium-drivers\\Chrome\\chromedriver.exe");
        //System.getProperty("webdriver.opera.driver","c:\\Selenium-drivers\\Opera\\operadriver.exe");

        WebDriver driver = WebDriverConfig.getDriver(WebDriverConfig.CHROME);
        driver.get("https://www.ebay.com");

        WebElement searchField=driver.findElement(By.id(SEARCHFIELD));
        searchField.sendKeys("Laptop");
        searchField.submit();

        //OperaOptions opera = new OperaOptions();
        //final OperaOptions operaOptions = operaOptions.setBinary("C:\Program Files (x86)\Opera Next\53.0.2907.31\opera.exe");

    }


}
