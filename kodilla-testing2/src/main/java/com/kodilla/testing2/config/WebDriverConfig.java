package com.kodilla.testing2.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.opera.OperaDriver;

public class WebDriverConfig {
    public final static String FIREFOX = "FIREFOX_DRIVER";
    public final static String CHROME = "CHROME_DRIVER";
    public final static String OPERA = "OPERA_DRIVER";

    public static WebDriver getDriver(final String driver) {

        System.setProperty("webdriver.chrome.driver", "c:\\Selenium-drivers\\Chrome\\chromedriver.exe");
        System.setProperty("webdriver.opera.driver", "c:\\Selenium-drivers\\Opera\\operadriver.exe");

        if (driver.equals(CHROME)) {
            return new ChromeDriver();
        } else if (driver.equals(OPERA)) {
            return new OperaDriver();
        } else {
            return null;
        }
    }
}



