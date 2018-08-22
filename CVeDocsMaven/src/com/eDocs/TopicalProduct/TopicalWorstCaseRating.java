package com.eDocs.TopicalProduct;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.eDocs.CommonValidation.NumericValidation;
import com.eDocs.Utils.Constant;

import jxl.write.WriteException;

public class TopicalWorstCaseRating {
	
	public static WebDriver driver = Constant.driver;
	public String password = Constant.sitepassword;
	String testcaseSheetName = "TopicalTC";
	
	
	@Test(priority=69)
	public void worstcaseRatingEmptyTest() throws InterruptedException, WriteException, IOException
	{
		Thread.sleep(1000);
		System.out.println("test");
		WebElement Submit = driver.findElement(By.cssSelector(".btn.blue-btn.continue-btn.waves-effect.gotoSolid_next4"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericEmpty(testcaseSheetName,Submit,81);
	}
	
	
	@Test(priority=70)
	public void worstcaseRatingSelection() throws InterruptedException
	{
		for(int i=1;i<10;i++)
		{
			Thread.sleep(200);
			if(driver.findElements(By.name("listCriteria"+i+"")).size()!=0)
			{
				Thread.sleep(200);
				WebElement grpCriteria = driver.findElement(By.xpath(".//*[@id='worstCaseRating']/div[1]/div/div["+i+"]/span/span[1]/span/span[2]"));
				grpCriteria.click();
				grpCriteria.sendKeys(Keys.ENTER);
				Thread.sleep(200);
			}else
			{
				break;
			}
		}
		driver.findElement(By.cssSelector(".btn.blue-btn.continue-btn.waves-effect.gotoSolid_next4")).click(); 
		Thread.sleep(500);
	}
}
