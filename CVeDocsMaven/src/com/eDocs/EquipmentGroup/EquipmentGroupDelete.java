package com.eDocs.EquipmentGroup;

import java.io.IOException;
import java.sql.SQLException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Message;
import com.eDocs.Utils.Utils;

public class EquipmentGroupDelete {
	
	public static WebDriver driver = Constant.driver;
	public String password = Constant.sitepassword;
	
	@Test(priority=65)
	public void SingleDeleteEquipmentGroup() throws InterruptedException, IOException
	{
		Thread.sleep(3000);
		driver.get(Constant.URL+"/equipment-group");
		driver.findElement(By.id("dLabel")).click();
		Thread.sleep(1000);
		//driver.findElement(By.xpath(".//*[@id='datatable']/tbody/tr[1]/td[8]/div/ul/li[3]/a")).click(); // Click edit equipment button
		//driver.findElement(By.className("dropdown-item")).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
		driver.findElement(By.linkText("Delete")).click();
		Thread.sleep(1000);
		//driver.findElement(By.xpath(".//*[@id='openAckModal']")).click();//click Yes in popup
		//Thread.sleep(500);
		driver.findElement(By.name("ackChangeControlNo")).sendKeys("111");
		Thread.sleep(500);
		driver.findElement(By.name("ackChangeControlNo")).sendKeys(Keys.TAB +password);
		Thread.sleep(500);
		driver.findElement(By.id("comments")).sendKeys("Delete single equipment");
		Thread.sleep(500);
		driver.findElement(By.xpath(".//*[@id='dynamicModal']/div[3]/div/button[2]")).click();
		Thread.sleep(1500);
		String deletemsg = driver.findElement(By.className("notify-msg")).getText(); // get deleted esuccess message
		Assert.assertEquals(deletemsg,Message.equipmentGroupDELETE);
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
