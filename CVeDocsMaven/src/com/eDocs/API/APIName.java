package com.eDocs.API;

import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByCssSelector;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.eDocs.CommonValidation.AlphaNumericValidation;
import com.eDocs.CommonValidation.NumericValidation;
import com.eDocs.Utils.Constant;
import com.eDocs.Utils.RepositoryParser;
import jxl.write.WriteException;

public class APIName {
	
	private RepositoryParser parser;
	public static WebDriver driver = Constant.driver;
	String testcaseSheetName = "APITC";
	
	/*@BeforeClass
	public void setUp() throws IOException  
	{
		driver.get(Constant.URL);
		parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\Product.properties");
	}

	@Test(priority=0)
	public void Login() throws InterruptedException
	{
		Thread.sleep(1000);
		WebElement username = driver.findElement(By.id("username"));
		WebElement password = driver.findElement(By.id("password"));
		username.sendKeys(Constant.siteusername);
		Thread.sleep(500);
		password.sendKeys(Constant.sitepassword);
		Thread.sleep(500);
		driver.findElement(By.id("loginsubmit")).click();
		Thread.sleep(1000);
  		if (driver.findElements(By.id("forcelogin")).size()!=0)
  		{
  			// Force Login
  			Thread.sleep(1000);
  			driver.findElement(By.id("forcelogin")).click();
  		}
  		Thread.sleep(1500);
	}*/
	
  
  	@Test (priority=1)
  	public void  nameEmpty() throws IOException, WriteException, InterruptedException
  	{
  		Thread.sleep(1000);
  		driver.get(Constant.URL+"/active-ingredients");
		Thread.sleep(1500);
		driver.findElement(By.id("addApi")).click(); 
  		Thread.sleep(1500);
  		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.id("saveActiveIngredient"));
  		textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,Submit,6);
  	}
  	
  	@Test (priority=2)
  	public void  nameMaxFieldLength() throws IOException, WriteException, InterruptedException
  	{	
  		WebElement alphanumericField = driver.findElement(By.name("name"));
  		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.MaxLengthCheck(testcaseSheetName,alphanumericField,8);
	}
  	
  	@Test (priority=3)
  	public void  nameSpecialChar() throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
  	{
  		WebElement alphanumericField = driver.findElement(By.name("name"));
  		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.nameSpecialCharCheck(testcaseSheetName,alphanumericField,9);
  	}
  	
  	@Test (priority=4)
  	public void  nameBeginingSpaceCheck() throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
  	{
  		WebElement alphanumericField = driver.findElement(By.name("name"));
  		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.nameBeginingSpaceCheck(testcaseSheetName,alphanumericField,10);
  	}
	
  	
  	
	@Test (priority=5)
  	public void  productIDMaxLength() throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
  	{
		WebElement alphanumericField = driver.findElement(By.name("activeId"));
		AlphaNumericValidation getAlphanumericField = new AlphaNumericValidation();
		getAlphanumericField.MaxLengthCheck(testcaseSheetName,alphanumericField, 12);
		alphanumericField.clear();
		alphanumericField.sendKeys("API 111");
  	}
	
	@Test (priority=6)
  	public void  routeofAdminEmpty() throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
  	{
		driver.findElement(By.name("activeId")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.id("saveActiveIngredient"));
  		textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,Submit,13);
  	}
	
	@Test (priority=7)
  	public void  routeofAdminCREATEmpty() throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
  	{
		Thread.sleep(500);
		driver.findElement(By.id("trigger-add-roa")).click();
		Thread.sleep(500);
		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.id("submit-roa"));
  		textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,Submit,14);
  	}
	
	@Test (priority=8)
  	public void  routeofAdminDuplicate() throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
  	{
		Thread.sleep(500);
		//select already created one
		driver.findElement(By.id("newRoA")).sendKeys("Any");
		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.id("submit-roa"));
  		textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,Submit,15);
  	}
	
	@Test (priority=9)
  	public void  routeofAdminCREATEMaxLength() throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
  	{
		Thread.sleep(500);
		WebElement alphanumericField = driver.findElement(By.id("newRoA"));
		alphanumericField.clear();
		AlphaNumericValidation getAlphanumericField = new AlphaNumericValidation();
		getAlphanumericField.MaxLengthCheck(testcaseSheetName,alphanumericField, 16);
  	}
	
	@Test (priority=10)
  	public void  routeofAdminCREATESuccess() throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
  	{
		Thread.sleep(500);
		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.id("submit-roa"));
  		textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,Submit,17);
  	}
	
		
		@Test (priority=11)
	  	public void  routeofAdminDeleteSuccess() throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
	  	{
			Thread.sleep(500);
			//Select Finished Dosage Form
			driver.findElement(By.id("activeIngredientId")).sendKeys(Keys.TAB,Keys.TAB,"Lorem",Keys.ENTER);
			Thread.sleep(500);
			AlphaNumericValidation textField = new AlphaNumericValidation();
			WebElement Submit = driver.findElement(By.id("deleteRoA"));
	  		textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,Submit,18);
	  		//Select Finished Dosage Form
			driver.findElement(By.id("activeIngredientId")).sendKeys(Keys.TAB,Keys.TAB,Keys.ENTER,Keys.ENTER);
	  	}	
	
}

