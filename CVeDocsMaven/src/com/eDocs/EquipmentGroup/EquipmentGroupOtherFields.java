package com.eDocs.EquipmentGroup;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.eDocs.CommonValidation.AlphaNumericValidation;
import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Message;
import com.eDocs.Utils.RepositoryParser;
import com.eDocs.Utils.Utils;

import jxl.write.WriteException;

public class EquipmentGroupOtherFields {
	
	private RepositoryParser parser;
	public static WebDriver driver = Constant.driver;
	public String password = Constant.sitepassword;
	String testcaseSheetName = "EquipmentGROUPTC";
	
  
  	@Test (priority=56)
  	public void  Emptyfeature() throws IOException, WriteException, InterruptedException
  	{
  		//Thread.sleep(500);
  		//driver.findElement(By.name("name")).sendKeys("test");
  		// Select Criteria
  		Thread.sleep(500);
  		driver.findElement(By.id("bracketingOption-2")).click();
  		Thread.sleep(500);
  		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.id("next-worstCase"));
  		textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,Submit,11);
  		//Select Feature
  		driver.findElement(By.name("name")).sendKeys(Keys.TAB,Keys.TAB,Keys.ENTER,Keys.ENTER);
  	}
  	
  	
	@Test (priority=57)
  	public void  EmptyEquipmentSelection() throws IOException, WriteException, InterruptedException
  	{
  		Thread.sleep(500);
  		//driver.findElement(By.id("bracketingCriteria")).sendKeys(Keys.ENTER);
  		//Thread.sleep(500);
  		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.id("next-worstCase"));
  		textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,Submit,12);
  	}
	
	
  	@Test (priority=58)
  	public void  singleEquipmentSelection() throws IOException, WriteException, InterruptedException
  	{
  		// Select single equipment
  		Thread.sleep(500);
  		driver.findElement(By.name("comment")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
  		Thread.sleep(500);
  		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.id("next-worstCase"));
  		textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,Submit,13);
  		Thread.sleep(500);
  		//Select multiple equipment to go next step
  		driver.findElement(By.name("comment")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ARROW_DOWN,Keys.ENTER);
  		Submit.click();
  		Thread.sleep(1000);

		Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
		for(int k=5;k<1000;k++)
		{
			j.add(k);
		}
		WebElement getName = driver.findElement(By.name("name"));
		String getgrpName = getName.getAttribute("value");
		Thread.sleep(500);
		if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Group '"+getgrpName+"' already exists!"))
		{
			String getduplicatename = driver.findElement(By.className("notify-msg")).getText();
			driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
		
		Thread.sleep(500);
		if(getduplicatename.equalsIgnoreCase("Group '"+getgrpName+"' already exists!"))
		{
			for(Integer i:j)
			{
				//driver.findElement(parser.getbjectLocator("ActiveIngredientName")).clear();
				getName.clear();
				Thread.sleep(200);
				getName.sendKeys(getgrpName+i);
				//driver.findElement(parser.getbjectLocator("ActiveIngredientName")).sendKeys(Name+i);
				Thread.sleep(500);
				Submit.click();
				//driver.findElement(parser.getbjectLocator("APIsubmit")).click();
				Thread.sleep(500);
				if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Group '"+getgrpName+i+"' already exists!"))
				{
					String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
					System.out.println("Name duplicate: "+nameduplicate);
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
					if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Group '"+getgrpName+i+"' already exists!"))
					{
						continue;
					}
				}
						System.out.println("Not duplicate so break the loop");
						break;
				}
			}
		}
		Thread.sleep(1000);
  		driver.findElement(By.id("worstCase-step")).click();
  		driver.findElement(By.id("worstCase-step")).sendKeys(Keys.ENTER);
  		
	}
  	
  	@Test (priority=59)
  	public void  EmptyNoofRuns() throws IOException, WriteException, InterruptedException
  	{
  		Thread.sleep(500);
  		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.id("saveEquipmentGroup"));
  		textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,Submit,14);
  		Thread.sleep(500);
  		driver.findElement(By.id("wcderesult")).sendKeys(Keys.SHIFT,Keys.TAB,Keys.TAB,Keys.ENTER,Keys.ENTER);
  		//driver.findElement(By.name("runs")).sendKeys(Keys.ENTER,Keys.ENTER);
  		Thread.sleep(500);
  	}
  	
  	@Test (priority=60)
  	public void  EmptyProtocolDocID() throws IOException, WriteException, InterruptedException
  	{
  		Thread.sleep(500);
  		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.id("saveEquipmentGroup"));
  		textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,Submit,16);
  		Thread.sleep(500);
  		//Select protocol document id
  		driver.findElement(By.id("wcderesult")).sendKeys(Keys.SHIFT,Keys.TAB,Keys.ENTER,Keys.ENTER);
  		Thread.sleep(500);
  	}
  	
  	@Test (priority=61)
  	public void  reportIDMaxLength() throws IOException, WriteException, InterruptedException
  	{
  		Thread.sleep(500);
  		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement reportID = driver.findElement(By.xpath(".//*[@id='wcderesult']"));
  		textField.MaxLengthCheck(testcaseSheetName, reportID, 17);
  		Thread.sleep(500);
  	}
  	
  	
	@Test (priority=62)
  	public void  worstcaseProductEmpty() throws IOException, WriteException, InterruptedException
  	{
  		Thread.sleep(500);
  		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement submit = driver.findElement(By.id("saveEquipmentGroup"));
  		textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,submit,18);
  		Thread.sleep(500);
  	}
	
  	@Test (priority=63)
  	public void  LessthanONENoofRuns() throws IOException, WriteException, InterruptedException
  	{
  		//Select worstcase product
  		Thread.sleep(500);
  		driver.findElement(By.id("wcderesult")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
  		Thread.sleep(500);
  		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.id("saveEquipmentGroup"));
  		textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,Submit,15);
  	}
  	
  	
  	@Test (priority=64)
  	public void  saveEquipmentGroup() throws IOException, WriteException, InterruptedException
  	{
  		//Select worstcase product
  		Thread.sleep(500);
  		driver.findElement(By.id("wcderesult")).sendKeys(Keys.SHIFT,Keys.TAB,Keys.TAB,Keys.ENTER,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ENTER);
  		Thread.sleep(500);
  		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.id("saveEquipmentGroup"));
  		textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,Submit,19);
  	}
}

