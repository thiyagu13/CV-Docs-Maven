package com.eDocs.Utility;

import java.sql.SQLException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Message;
import com.eDocs.Utils.Utils;

public class UtilityDelete {
	
	public static WebDriver driver = Constant.driver;
	public String password = Constant.sitepassword;
	
	@Test(priority=9)
	//@Test
	public void DeleteUtility() throws InterruptedException
	{
		Thread.sleep(2000);
		driver.get(Constant.URL+"/utility");
		Thread.sleep(1000);
		driver.findElement(By.id("dLabel")).click();
		Thread.sleep(500);
		//driver.findElement(By.xpath(".//*[@id='dropdownactionDoc']/li[3]/a")).click(); // Click edit equipment button
		Thread.sleep(1000);
		//driver.findElement(By.xpath(".//*[@id='openAckModal']")).click();//click Yes in popup
		driver.findElement(By.linkText("Delete")).click();
		Thread.sleep(500);
		driver.findElement(By.name("ackChangeControlNo")).sendKeys("Ch11");
		Thread.sleep(500);
		driver.findElement(By.name("ackChangeControlNo")).sendKeys(Keys.TAB +password);
		Thread.sleep(500);
		driver.findElement(By.id("comments")).sendKeys("Delete single equipment");
		Thread.sleep(500);
		driver.findElement(By.xpath(".//*[@id='dynamicModal']/div[3]/div/button[2]")).click();
		Thread.sleep(1500);
		String deletemsg = driver.findElement(By.className("notify-msg")).getText(); // get deleted esuccess message
		Assert.assertEquals(deletemsg,Message.utilityDELETE);
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
