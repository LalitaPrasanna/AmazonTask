package com.testvox.amazon;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import junit.framework.Assert;

public class BasicSteps {	

	 /**
	  * <p>This method intializes the web driver.</p>
	  */
		public WebDriver intializeWebDriver() {
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\LENOVO\\chromedriver_win32\\chromedriver.exe");
			WebDriver driver = new ChromeDriver();

			// Puts an Implicit wait, Will wait for 30 seconds before throwing exception
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

			//1. Navigate to www.amazon.com
			driver.get("https://www.amazon.com/");
			String title = driver.getTitle();
			System.out.println("Title is :"+title);
			Assert.assertTrue(title.contains("Amazon.com"));

			// Maximize the browser
			driver.manage().window().maximize();

			return driver;
		}
		
		/**
		 * <p>This method does the print on system console.</p>
		 */
		public void printToConsole(int stepNum, String testStatus,  String line) {
			
			System.out.println("************************************************");
			System.out.println("Test Step: "+stepNum);
			System.out.println("Result "+testStatus);
			System.out.println(line);
			System.out.println("************************************************\n\n");
			
		}
		
}
