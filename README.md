# Automated functional/integration test suite for Trading Simulator and Trading Dashboard Web App

## Hot to build and run automated test suite

Prerequisite: Chrome broser installed.

```
git clone https://github.com/dmitryaleks/TradingSimulatorTest.git
mvn test
```

## Tests for Trading Simulator API

Theses tests are facilitated by REST Assured.

## Front-end tests

Theese tests are done using Selenium.

Selenium allows automated testing of web apps using a browser driver (E.g. Chrome).

It opens a browser in an automated test mode and drives the test by manipulating inputs and making assertions on observed changes in DOM elements of the page under test.

### Project Setup

Download Selenium Standalone Server (JAR) from:
<https://www.seleniumhq.org/download>

Download Selenium Client & WebDriver Language Bindings for Java from:
<https://www.seleniumhq.org/download>

Get the latest JUnit (E.g. 4.10) from:
<https://sourceforge.net/projects/junit>

### Download and configure "chromedriver" for Linux

<https://chromedriver.storage.googleapis.com/2.44/chromedriver_linux64.zip>

Unzip it to some location under the project.

Point Selenium at it inside the test:
```java
System.setProperty("webdriver.chrome.driver","/home/dmitry/IdeaProjects/SeleniumTest/drivers/chromedriver");
WebDriver browser = new ChromeDriver();
```

### Make a JUnit-based test

[Example test](https://github.com/dmi3aleks/WebAppTester/blob/master/src/com/test/TradingDashboardTest.java)


### Finding DOM elements

Elements can be found either by "id" or by the XPath.


Example of finding an element by id (this is a preferred way):
```java
WebElement header = browser.findElement(By.id("in"));
assertTrue((header.isDisplayed()));
```

Example of finding an element by XPath (this is less maintainable):
```java
browser.findElement(By.xpath("(//*[@id=\"in\"])[1]")).sendKeys("S");
browser.findElement(By.xpath("(//*[@id=\"in\"])[2]")).sendKeys("10000");
browser.findElement(By.xpath("(//*[@id=\"in\"])[3]")).sendKeys("7800");
```

### Actions

Common actions are:
  * sendKeys: inputs text into an element;
  * getText:  extracts text from an element.
