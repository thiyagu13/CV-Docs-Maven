package com.eDocs.TopicalProduct;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Utils;

public class TopicalDelete {
	
	public static WebDriver driver = Constant.driver;
	public String password = Constant.sitepassword;
	
	@Test(priority=74)
	public void DeleteProduct() throws InterruptedException
	{
		Thread.sleep(2000);
		driver.findElement(By.id("dLabel")).click();
		Thread.sleep(500);
		driver.findElement(By.linkText("Delete")).click();
		Thread.sleep(500);
		driver.findElement(By.name("ackChangeControlNo")).sendKeys("111");
		Thread.sleep(500);
		driver.findElement(By.name("ackChangeControlNo")).sendKeys(Keys.TAB +password);
		Thread.sleep(500);
		driver.findElement(By.id("comments")).sendKeys("Delete single equipment");
		Thread.sleep(500);
		//driver.findElement(By.id("comments")).sendKeys(Keys.SHIFT,Keys.TAB +password);
		//Thread.sleep(500);
		driver.findElement(By.id("comments")).sendKeys(Keys.TAB,Keys.TAB,Keys.ENTER);
		//driver.findElement(By.xpath(".//*[@id='dynamicModal']/div[3]/div/button[2]")).click();
		Thread.sleep(2000);
		String deletemsg = driver.findElement(By.className("notify-msg")).getText(); // get deleted esuccess message
		Assert.assertEquals(deletemsg,"Product was deleted successfully");
		String className = this.getClass().getName(); // get current class name - for screenshot
		String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
		Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
		if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
		{
			driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
		}
		Thread.sleep(600);
	}
}
