package com.test.ui;

import static org.junit.Assert.*;

import com.test.common.Server;
import com.test.util.Generator;
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
    static  private String appURL = Server.getAppFrontEnd().getAppURL();

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver","drivers/chromedriver");
        browser = new ChromeDriver();
    }

    private void create_a_new_order(final WebDriver browser, final String side, long quantity, long price) {
        browser.findElement(By.id("quantity")).sendKeys(String.valueOf(quantity));
        browser.findElement(By.id("price")).sendKeys(String.valueOf(price));
        WebElement sendBtn = browser.findElement(By.id("btn_send_order"));
        sendBtn.click();
    }

    @Test
    public void new_order_creation() {

        browser.get(appURL);

        final Double orderPrice = Generator.getOrderPrice();
        final Long price = Math.round(orderPrice);
        final String orderPriceString = price.toString();

        // Send a new order
        create_a_new_order(browser, "S", 10000, price);

        // Verify that new order got created
        WebDriverWait wait = new WebDriverWait(browser, 5);

        final WebElement orders = browser.findElement(By.xpath("//*[@id=\"orders\"]"));
        wait.until(ExpectedConditions.textToBePresentInElement(orders, orderPriceString));

        final WebElement lastOrder = browser.findElement(By.xpath("//*[@id=\"orders\"]/tbody[2]/tr"));
        final WebElement lastOrderPrice = lastOrder.findElement(By.id("order_price"));

        System.out.println(String.format("New order: %s", lastOrder.getText()));
        assertEquals(orderPriceString, lastOrderPrice.getText());
    }

    @Test
    public void order_cancellation() {

        browser.get(appURL);

        final Double orderPrice = Generator.getOrderPrice();
        final Long price = Math.round(orderPrice);
        final String orderPriceString = price.toString();

        // Send a new order
        create_a_new_order(browser, "S", 10000, price);

        // Verify that new order got created
        WebDriverWait wait = new WebDriverWait(browser, 5);

        final WebElement orders = browser.findElement(By.xpath("//*[@id=\"orders\"]"));
        wait.until(ExpectedConditions.textToBePresentInElement(orders, orderPriceString));

        final WebElement lastOrder = browser.findElement(By.xpath("//*[@id=\"orders\"]/tbody[2]/tr"));
        final WebElement lastOrderPrice = lastOrder.findElement(By.id("order_price"));

        System.out.println(String.format("New order: %s", lastOrder.getText()));
        assertEquals(lastOrderPrice.getText(), orderPriceString);

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
