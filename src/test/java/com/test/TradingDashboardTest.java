package com.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TradingDashboardTest {

    private WebDriver browser;
    static  String appURL = "http://localhost:5000";

    static String getCurrentTimestamp() {

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        return sdf.format(new Date());
    }

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver","drivers/chromedriver");
        browser = new ChromeDriver();
    }

    private void create_a_new_order(final WebDriver browser, final String side, double quantity, double price, final String notes) {
        browser.findElement(By.id("side")).sendKeys(side);
        browser.findElement(By.id("quantity")).sendKeys(String.valueOf(quantity));
        browser.findElement(By.id("price")).sendKeys(String.valueOf(price));
        browser.findElement(By.id("notes")).sendKeys(notes);
        WebElement sendBtn = browser.findElement(By.id("btn_send_order"));
        sendBtn.click();
    }

    @Test
    public void new_order_creation() {

        browser.get(appURL);

        final String orderNotes = getCurrentTimestamp();

        // Send a new order
        create_a_new_order(browser, "S", 10000, 7800, orderNotes);

        // Verify that new order got created
        WebDriverWait wait = new WebDriverWait(browser, 5);
        final WebElement lastOrder = browser.findElement(By.xpath("//*[@id=\"orders\"]/tr[2]"));
        wait.until(ExpectedConditions.textToBePresentInElement(lastOrder, orderNotes));

        System.out.println(String.format("New order: %s", lastOrder.getText()));
        assertEquals(lastOrder.findElement(By.id("order_notes")).getText(), orderNotes);
    }

    @Test
    public void order_cancellation() {

        browser.get(appURL);


        final String orderNotes = getCurrentTimestamp();

        // Send a new order
        create_a_new_order(browser, "S", 10000, 7800, orderNotes);

        // Verify that new order got created
        WebDriverWait wait = new WebDriverWait(browser, 5);
        final WebElement lastOrder = browser.findElement(By.xpath("//*[@id=\"orders\"]/tr[2]"));
        wait.until(ExpectedConditions.textToBePresentInElement(lastOrder, orderNotes));

        System.out.println(String.format("New order: %s", lastOrder.getText()));
        assertEquals(lastOrder.findElement(By.id("order_notes")).getText(), orderNotes);

        JavascriptExecutor executor = (JavascriptExecutor)browser;
        executor.executeScript("document.body.style.zoom = '0.5'");

        // cancel the newly created order
        WebElement cancelBtn = lastOrder.findElement(By.id("order_cancel"));

        Actions actions = new Actions(browser);
        actions.moveToElement(cancelBtn).perform();

        // NOTE: neither Click nor RETURN work as Cancel is wrapped/hidden in a div, using JS click instead
        executor.executeScript("arguments[0].click();", cancelBtn);
        //cancelBtn.click();
        //cancelBtn.sendKeys(Keys.RETURN);

        WebElement status = lastOrder.findElement(By.id("order_status"));

        // verify that order status get updated accordingly
        final String cancelledStatus = "C";
        wait.until(ExpectedConditions.textToBePresentInElement(status, cancelledStatus));
        assertEquals(status.getText(), cancelledStatus);
    }

    @After
    public void after() {
        browser.close();
    }

}
