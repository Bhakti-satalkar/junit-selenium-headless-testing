package com.lambdatest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class Headless {
    String username = System.getenv("LT_USERNAME") == null ? "Your LT Username" : System.getenv("LT_USERNAME");
    String accessKey = System.getenv("LT_ACCESS_KEY") == null ? "Your LT AccessKey" : System.getenv("LT_ACCESS_KEY");
    public static RemoteWebDriver driver = null;
    public String gridURL = "@hub.lambdatest.com/wd/hub";
    public String status = "failed";

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("version", "latest");
        capabilities.setCapability("platform", "Windows 10"); // If this cap isn't specified, it will just get the any
                                                              // available one
        capabilities.setCapability("build", "Junit Testing Example");
        capabilities.setCapability("name", "Headless Test");
        capabilities.setCapability("plugin", "git-junit");

       // Chrome flag for headless using chrome options
       ChromeOptions options = new ChromeOptions();

       options.addArguments("--no-sandbox");
       options.addArguments("--headless"); // headless flag for chrome
       options.addArguments("disable-gpu");

       capabilities.setCapability(ChromeOptions.CAPABILITY, options);

       /*
        * At lambdatest you can execute headless tests via `headless` capability on
        * chrome, firefox and microsoft edge browser
        */

       // lambdatest Headless capability
       // caps.setCapability("headless",true);

        try {
            driver = new RemoteWebDriver(new URL("https://" + username + ":" + accessKey + gridURL), capabilities);
        } catch (MalformedURLException e) {
            System.out.println("Invalid grid URL");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testSimple() throws Exception {
        try {
            String spanText;
            System.out.println("Loading Url");
    
            driver.get("https://lambdatest.github.io/sample-todo-app/");
    
            System.out.println("Checking Box");
            driver.findElement(By.name("li1")).click();
    
            System.out.println("Checking Another Box");
            driver.findElement(By.name("li2")).click();
    
            System.out.println("Checking Box");
            driver.findElement(By.name("li3")).click();
    
            System.out.println("Checking Another Box");
            driver.findElement(By.name("li4")).click();
    
            driver.findElement(By.id("sampletodotext")).sendKeys(" List Item 6");
            driver.findElement(By.id("addbutton")).click();
    
            driver.findElement(By.id("sampletodotext")).sendKeys(" List Item 7");
            driver.findElement(By.id("addbutton")).click();
    
            driver.findElement(By.id("sampletodotext")).sendKeys(" List Item 8");
            driver.findElement(By.id("addbutton")).click();
    
            System.out.println("Checking Another Box");
            driver.findElement(By.name("li1")).click();
    
            System.out.println("Checking Another Box");
            driver.findElement(By.name("li3")).click();
    
            System.out.println("Checking Another Box");
            driver.findElement(By.name("li7")).click();
    
            System.out.println("Checking Another Box");
            driver.findElement(By.name("li8")).click();
            Thread.sleep(300);
    
            System.out.println("Entering Text");
            driver.findElement(By.id("sampletodotext")).sendKeys("Get Taste of Lambda and Stick to It");
    
            driver.findElement(By.id("addbutton")).click();
    
            System.out.println("Checking Another Box");
            driver.findElement(By.name("li9")).click();
    
            // Let's also assert that the todo we added is present in the list.
    
            spanText = driver.findElementByXPath("/html/body/div/div/div/ul/li[9]/span").getText();
            Assert.assertEquals("Get Taste of Lambda and Stick to It", spanText);
            status = "passed";
            Thread.sleep(150);
    
            System.out.println("TestFinished");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @After
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.executeScript("lambda-status=" + status);
            driver.quit();
        }
    }
}
