package com.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TradingDashboardTest {

    private WebDriver browser;

    static String getCurrentTimestamp() {

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        return sdf.format(new Date());
    }

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver","/home/dmitry/IdeaProjects/SeleniumTest/drivers/chromedriver");
        browser = new ChromeDriver();
    }

    @Test
    public void new_order_creation() {

        browser.get("http://localhost:9000");
        WebElement header = browser.findElement(By.id("in"));
        assertTrue((header.isDisplayed()));

        final String orderNotes = getCurrentTimestamp();

        // Send a new order
        browser.findElement(By.xpath("(//*[@id=\"in\"])[1]")).sendKeys("S");
        browser.findElement(By.xpath("(//*[@id=\"in\"])[2]")).sendKeys("10000");
        browser.findElement(By.xpath("(//*[@id=\"in\"])[3]")).sendKeys("7800");
        browser.findElement(By.xpath("(//*[@id=\"in\"])[4]")).sendKeys(orderNotes);
        browser.findElement(By.xpath("//*[@id=\"btn\"]")).click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        // Verify that new order got created
        final String lastOrder = browser.findElement(By.xpath("//*[@id=\"orders\"]/tr[2]")).getText();
        System.out.println(String.format("New order: %s", lastOrder));

        assertTrue(lastOrder.contains(orderNotes));
    }

    @After
    public void after() {
        browser.close();
    }

}