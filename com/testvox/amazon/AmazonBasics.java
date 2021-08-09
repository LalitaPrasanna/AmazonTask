package com.testvox.amazon;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.junit.Assert;  
         
/**
 * 
 * <p>This class will execute the given automation test cases on Amazon web page</p>
 *
 */

public class AmazonBasics extends BasicSteps {
	String productName = "Amazon Basics 8-Sheet Capacity, Cross-Cut Paper and Credit Card Shredder, 4.1 Gallon";
	
	/**
	 * <p>This method will execute the flow of the test script </p>
	 */
	public void execute() {
		WebDriver driver = null;
		try {
			driver = intializeWebDriver();
			executeTestSteps(driver);
		} catch (Exception e) {
			System.out.println("Error while executing the test case ");
			e.printStackTrace();
		} finally {
			// Close the Browser.
			if (driver != null)
				driver.close();
		}		
	}

	/**
	 * <p>This method executes all the test steps in the given test case scenario</p>
	 * @param driver An instance of WebDriver class
	 */
	public void executeTestSteps(WebDriver driver) {
		
		//verifying the cart value immediately after navigating to the site
		String initialCartValue = verifyCartItemsCount(driver);
		Assert.assertEquals(initialCartValue,"0");
		printToConsole(1,"Pass", "Intially cart is empty");
		
		
		//Go to Amazon Basics 
		String amazonBasicsXpath = "//*[contains(text(),'AmazonBasics')]";
		boolean amazonBasics  = driver.findElement(By.xpath(amazonBasicsXpath)).isDisplayed();
		Assert.assertTrue(amazonBasics);
		printToConsole(1,"Pass", "Amazon Basics is found on Home page");
		
		
		//2. Click on 'See more' link for 'AmazonBasics' on the home page
		String seeMore = "//a[contains(@href,'amazonbasics')]";
		boolean seeMoreValue = driver.findElement(By.xpath(seeMore)).isDisplayed();
		Assert.assertTrue(seeMoreValue);
		driver.findElement(By.xpath(seeMore)).click();
		printToConsole(2,"Pass", "See more on Amazon Basics is clicked");
		
		
		//3. Verify that results for "amazon basics" is displayed on the top of the page (below the menu bar)
		String amazonBasicsPageXpath =  "//span[contains(text(),'amazonbasics')]";
		boolean resultAmazonBasics = driver.findElement(By.xpath(amazonBasicsPageXpath)).isDisplayed();
		Assert.assertTrue(resultAmazonBasics);
		printToConsole(3,"Pass", "Amazon Basics page is loaded");
		
		
		//4. Select 'Our Brands' check box from "From Our BRands" section on the left of the page
		String ourBrandsCheckBoxXpath = "//*[@id=\"p_n_feature_forty-seven_browse-bin/21180942011\"]/span/a/div/label/i";
		WebElement ourBrandsCheckBox = driver.findElement(By.xpath(ourBrandsCheckBoxXpath));
		boolean checkBox = ourBrandsCheckBox.isDisplayed();
		Assert.assertTrue(checkBox);
		ourBrandsCheckBox.click();
		printToConsole(4,"Pass", "Our Brands checkbox is clicked");

				
		//5. Select "AmazonBasics 8-sheet capacity,Cross-cut paper and Credit card Shedder, 4.1 Galln"
		//10. Verify cart shows count as '1'.
		int itemSize = 0;
		String productXpath = "//span[contains(text(),'Amazon Basics 8-Sheet Capacity, Cross-Cut Paper and Credit Card Shredder, 4.1 Gallon')]";
		String nextXpath = "//a[contains(text(),'Next')]";
		while(driver.findElement(By.xpath(nextXpath)).isDisplayed()) {
			itemSize=driver.findElements(By.xpath(productXpath)).size();
			if(itemSize==1) {
				printToConsole(5,"Pass", "Product mentioned is found");
				WebElement product = driver.findElement(By.xpath(productXpath));
				Assert.assertTrue(product.getText().contains("Amazon Basics 8-Sheet Capacity"));
				product.click();
				productPage(driver);
				addItem(driver);
				verifyCartItem(driver);
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.MILLISECONDS);
				String laterCartValue = verifyCartItemsCount(driver);
//				WebElement laterCart = driver.findElement(By.id("nav-cart-count"));
//				String laterCartValue = laterCart.getText();
				if(laterCartValue.equals("1")) {
					Assert.assertEquals(laterCartValue, "1");
					printToConsole(10,"Pass", "Count on Cart is verfied after adding item and it is 1");
				}else
					printToConsole(10,"Fail", "count on cart is not equal to 1");
				break;
			}else {
				driver.findElement(By.xpath(nextXpath)).click();
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			}
			
		}
				
	}
	

	
	private String verifyCartItemsCount(WebDriver driver) {
		WebElement cart = driver.findElement(By.id("nav-cart-count"));//nav-cart-count-container
		String cartValue = cart.getText();
		return cartValue;	
	}
	
	/**
	 * <p>This method verifies the product page</p>
	 * @param driver: An instance of webdriver 
	 */
	
	//6. VErify that the product page is displayed
	//7. Verify the '8 Sheet' is selected under 'Size' section on the product page
	private void productPage(WebDriver driver) {
		String pageTitle = driver.findElement(By.id("productTitle")).getText();
		Assert.assertEquals(pageTitle.trim(), productName);
		printToConsole(6,"Pass", "Product page is displayed");
		String productSize = driver.findElement(By.className("selection")).getText();
		Assert.assertEquals(productSize.trim(), "8 Sheet"); 
		printToConsole(7,"Pass", "Product size is verified and it is 8 Sheet");
	}
	
	/**
	 * <p>This method adds the item to the cart and check if it gets added</p>
	 * @param driver
	 */
	
	//8. Click on 'Add to cart" button
	private void addItem(WebDriver driver) {
		WebElement addToCartButton = driver.findElement(By.id("add-to-cart-button"));
		addToCartButton.click();
		String newPage = driver.getTitle();
		Assert.assertTrue(newPage.trim().contains(productName));
		printToConsole(8,"Pass", "Product is added to cart");
	}
	
	/**
	 * <p>This method verifies the added item on the cart</p>
	 * @param driver:An instance of webdriver
	 */
	
	//9. Verify item added to cart
	private void verifyCartItem(WebDriver driver) {
		WebElement cartButton = driver.findElement(By.id("attach-sidesheet-view-cart-button"));
		cartButton.click();
		WebElement itemInCart = driver.findElement(By.className("a-truncate-cut"));
		itemInCart.getText();
		Assert.assertEquals(itemInCart.getText(),productName );
		printToConsole(9, "Pass", "Item in the cart is verified");
	}
}
