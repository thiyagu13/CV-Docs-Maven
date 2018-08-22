package com.eDocs.TopicalProduct;

import java.io.IOException;
import org.openqa.selenium.By;
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

public class TopicalName {
	
	private RepositoryParser parser;
	public static WebDriver driver = Constant.driver;
	String testcaseSheetName = "TopicalTC";
	
/*	@BeforeClass
	public void setUp() throws IOException  
	{
		driver.get(Constant.URL);
		parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\Product.properties");
	}

	@Test(priority=1)
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
		driver.get(Constant.URL+"/products");
		Thread.sleep(1500);
		driver.findElement(By.id("addProduct")).click(); 
  		Thread.sleep(1500);
  		//
  		WebElement type = driver.findElement(By.xpath(".//*[@id='productSpec']/div[1]/div/div/span/span[1]/span/span[2]"));
		type.click();
		type.sendKeys(Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ENTER);
		Thread.sleep(500);
		driver.findElement(By.name("productName")).sendKeys("f sf s");
	}
	*/
  
  	@Test (priority=1)
  	public void  nameEmpty() throws IOException, WriteException, InterruptedException
  	{
  		Thread.sleep(1000);
  		driver.get(Constant.URL+"/products");
		Thread.sleep(1500);
		driver.findElement(By.id("addProduct")).click(); 
  		Thread.sleep(1000);
  		WebElement type = driver.findElement(By.xpath(".//*[@id='productSpec']/div[1]/div/div/span/span[1]/span/span[2]"));
		type.click();
		type.sendKeys(Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ENTER);
		Thread.sleep(500);
  		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.name("next"));
  		textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH,Submit,6);
  	}
  	/*
  	@Test (priority=2)
  	public void  nameMaxFieldLength() throws IOException, WriteException, InterruptedException
  	{	
  		WebElement alphanumericField = driver.findElement(By.name("productName"));
  		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.MaxLengthCheck(testcaseSheetName,alphanumericField,8);
	}
  	
  	@Test (priority=3)
  	public void  nameSpecialChar() throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
  	{
  		WebElement alphanumericField = driver.findElement(By.name("productName"));
  		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.nameSpecialCharCheck(testcaseSheetName,alphanumericField,9);
  	}
  	*/
  	@Test (priority=4)
  	public void  nameBeginingSpaceCheck() throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
  	{
  		WebElement alphanumericField = driver.findElement(By.name("productName"));
  		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.nameBeginingSpaceCheck(testcaseSheetName,alphanumericField,10);
  	}
	
  	
  
  	
	@Test (priority=5)
  	public void  productIDMaxLength() throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
  	{
		WebElement alphanumericField = driver.findElement(By.name("productID"));
		AlphaNumericValidation getAlphanumericField = new AlphaNumericValidation();
		getAlphanumericField.MaxLengthCheck(testcaseSheetName,alphanumericField, 12);
		alphanumericField.clear();
		alphanumericField.sendKeys("Prod 111");
  	}
	
	@Test (priority=6)
  	public void  packagingEmpty() throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
  	{
		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.name("next"));
  		textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,Submit,13);
  	}
	
	@Test (priority=7)
  	public void  packagingCREATEEmpty() throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
  	{
		Thread.sleep(500);
		driver.findElement(By.cssSelector(".icon.add-icon.mr-10.addDosageForm")).click();
		Thread.sleep(500);
		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.xpath(".//*[@id='addNewOtherDosageForm']/div/div/span/a[1]"));
  		textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,Submit,14);
  	}
	
	@Test (priority=8)
  	public void  packagingDuplicate() throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
  	{
		Thread.sleep(500);
		//select already created one
		driver.findElement(By.name("newOtherDosageForm")).sendKeys("Bottle");
		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.xpath(".//*[@id='addNewOtherDosageForm']/div/div/span/a[1]"));
  		textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,Submit,15);
  	}
	
	@Test (priority=9)
  	public void  packagingCREATEMaxLength() throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
  	{
		Thread.sleep(500);
		WebElement alphanumericField = driver.findElement(By.name("newOtherDosageForm"));
		alphanumericField.clear();
		AlphaNumericValidation getAlphanumericField = new AlphaNumericValidation();
		getAlphanumericField.MaxLengthCheck(testcaseSheetName,alphanumericField, 16);
  	}
	
	@Test (priority=10)
  	public void  packgingCREATESuccess() throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
  	{
		Thread.sleep(500);
		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.xpath(".//*[@id='addNewOtherDosageForm']/div/div/span/a[1]"));
  		textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,Submit,17);
  	}
	
		
		@Test (priority=11)
	  	public void  packagingDeleteSuccess() throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
	  	{
			Thread.sleep(500);
			//Select Finished Dosage Form
			driver.findElement(By.name("productID")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ENTER);
			Thread.sleep(500);
			AlphaNumericValidation textField = new AlphaNumericValidation();
			WebElement Submit = driver.findElement(By.id("deleteOtherDosage"));
	  		textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,Submit,18);
	  		//Select Finished Dosage Form
			driver.findElement(By.name("productID")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
	  	}	
		
		//Start total amount per container
		
		@Test(priority=12)
		public void NumericEmptyTest() throws InterruptedException, WriteException, IOException
		{
			WebElement Submit = driver.findElement(By.name("next"));
			NumericValidation getnumericField = new NumericValidation();
			getnumericField.NumericEmpty(testcaseSheetName,Submit,19);
		}
		
		@Test(priority=13)
		public void NumericZEROTest() throws InterruptedException, WriteException, IOException
		{
			WebElement numericField = driver.findElement(By.name("otherTotalAmountPerContainer"));
			NumericValidation getnumericField = new NumericValidation();
			getnumericField.NumericZERO(testcaseSheetName,numericField,20);
		}
		
		@Test(priority=14)
		public void NumericMaxLengthTest() throws InterruptedException, WriteException, IOException
		{
			WebElement numericField = driver.findElement(By.name("otherTotalAmountPerContainer"));
			NumericValidation getnumericField = new NumericValidation();
			getnumericField.NumericMaxLength(testcaseSheetName,numericField,21);
		}
		
		@Test(priority=15)
		public void numericFieldAlphabetsCheckTest() throws InterruptedException, WriteException, IOException
		{
			WebElement numericField = driver.findElement(By.name("otherTotalAmountPerContainer"));
			NumericValidation getnumericField = new NumericValidation();
			getnumericField.numericFieldAlphabetsCheck(testcaseSheetName,numericField,22);
		}
		
		@Test(priority=16)
		public void numericSpaceCheckTest() throws InterruptedException, WriteException, IOException
		{
			WebElement numericField = driver.findElement(By.name("otherTotalAmountPerContainer"));
			NumericValidation getnumericField = new NumericValidation();
			getnumericField.numericSpaceCheck(testcaseSheetName,numericField,23);
		}
		
		@Test(priority=17)
		public void numericMultiDecimalTest() throws InterruptedException, WriteException, IOException
		{
			WebElement numericField = driver.findElement(By.name("otherTotalAmountPerContainer"));
			NumericValidation getnumericField = new NumericValidation();
			getnumericField.numericMultiDecimal(testcaseSheetName,numericField,24);
		}
	  	
		//end total amount per container
		
		
	@Test (priority=18)
  	public void  ActiveEmpty() throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
  	{
		Thread.sleep(500);
		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.name("next"));
  		textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,Submit,25);
  		//Select API
  		driver.findElement(By.name("inputExc0")).sendKeys(Keys.SHIFT,Keys.TAB,Keys.ENTER,Keys.ENTER);
  	}
	
	@Test (priority=19)
  	public void  groupingCriteriaEmpty() throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
  	{
		Thread.sleep(500);
		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.name("next"));
  		textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,Submit,25);
  		//Select API
  		driver.findElement(By.name("inputExc0")).sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB);
  		driver.findElement(By.id("systemicAbsorption")).click();
  	}
	
}

