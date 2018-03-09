package com.amazon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AmazonTest {
	
	WebDriver driver;
	
	
	@BeforeTest
	public void TestInit() {
		System.setProperty("webdriver.chrome.driver","C:\\Users\\Dev\\Downloads\\chromedriver_win32\\chromedriver.exe");
		
		driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
	}
	
	@Test(priority=1, description="searches for pendrive with filters. Brand: Sandisk, Price Range: Rs. 300 – Rs. 1000, Avg Customer Review: 4 and up.")
	public void TestScenario_01() throws InterruptedException {

		// get to AUT 
		
		driver.get("https://www.amazon.in/");
		
		// searches pen drive
		
		WebElement searchTextBox = driver.findElement(By.id("twotabsearchtextbox"));

		
		searchTextBox.sendKeys("pen drive");
		
		searchTextBox.submit();
		

		// Setting Price Range: Rs. 300 – Rs. 1000

		
		WebElement minPriceRange = driver.findElement(By.xpath("//input[@type='text'][@placeholder='Min']"));
		
		WebElement maxPriceRange = driver.findElement(By.xpath("//input[@type='text'][@placeholder='Max']"));
		
		WebElement submitPriceRange = driver.findElement(By.xpath("//span[@class=\"a-button-inner\"]/input[@value='Go']"));
		
		minPriceRange.sendKeys("300");
		
		maxPriceRange.sendKeys("1000");
		
		submitPriceRange.click();

		

		// Setting Avg Customer Review: 4 and up.
		
		
		
		WebElement fourStarsAndUp = driver.findElement(By.xpath("//h4[text()='Avg. Customer Review']//following::ul[1]/div[1]/li[1]"));
		Thread.sleep(5000);
		fourStarsAndUp.click();
		

		//setting Brand to SanDisk
		Thread.sleep(5000);
		driver.findElement(By.xpath("//text()[.='SanDisk']/ancestor::label[1]")).click();
		
	}

		// B. Verify the user can add pen drive with the lowest price from the first
		
		// page of search results of above test case.
	
	@Test(priority=2, description="adds pen drive with the lowest price from the first page of search results of above test case.")
	public void TestScenario_02() throws InterruptedException {
		
		List<Integer> priceList = new ArrayList<>();
		
		Thread.sleep(1000);
		
		List<WebElement> price = driver.findElements(By.xpath("//span[@class='a-size-base a-color-price s-price a-text-bold']"));
		
		for (WebElement x : price) {
		
			String y = x.getAttribute("innerHTML");
			
			y = y.replace("<span class=\"currencyINR\">&nbsp;&nbsp;</span>", "");
			
			if (y.contains(",")) {
			
				y = y.replace(",", "");
			
			}
			
			priceList.add(Integer.parseInt(y));
		
		}
		
		Collections.sort(priceList);
		
		// current window
		
		String resultWindow = driver.getWindowHandle();
		
		System.out.println("result window: " + resultWindow);
		
		WebElement cheapestPenDrive = driver.findElement(By.xpath("//span[contains(text()," + priceList.get(0) + ")]"));
		
		cheapestPenDrive.click();
		
	}
		
		// C. Verify the user is prompted to Login when the user tries to check out the
		
		// product added in above test case.
	
	@Test(priority=3, description="verify that user is prompted to Login when the user tries to check out the product added in above test case.")
	public void TestScenario_03() throws InterruptedException {
		
		Set<String> windowhandles = driver.getWindowHandles();
		
		for (String s : windowhandles) {
		
			System.out.println(s);
			
			driver.switchTo().window(s);
		
		}
		
		WebElement buyNowButton = driver.findElement(By.xpath("//input[@id=\"buy-now-button\"]"));
		
		buyNowButton.click();
		
		Thread.sleep(1000);
		
		windowhandles = driver.getWindowHandles();
		
		for (String s : windowhandles) {
			
			driver.switchTo().window(s);
		
		}
		
		Assert.assertEquals(driver.getTitle(), "Amazon Sign In");

	}

}
