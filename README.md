# Selenium-based test for the Trading Dashboard web app

## Notes on Selenium

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
