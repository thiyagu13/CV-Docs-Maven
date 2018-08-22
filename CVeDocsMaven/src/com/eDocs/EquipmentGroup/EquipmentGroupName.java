package com.eDocs.EquipmentGroup;

import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.eDocs.CommonValidation.AlphaNumericValidation;
import com.eDocs.Utils.Constant;
import com.eDocs.Utils.RepositoryParser;
import jxl.write.WriteException;

public class EquipmentGroupName {
	
	private RepositoryParser parser;
	public static WebDriver driver = Constant.driver;
	String testcaseSheetName = "EquipmentGROUPTC";
	
	/*@BeforeClass
	public void setUp() throws IOException  
	{
		driver.get(Constant.URL);
		parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\Equipment.properties");
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
		driver.get(Constant.URL+"/equipment-group");
		Thread.sleep(1500);
		driver.findElement(By.id("addGroup")).click(); 
  		Thread.sleep(1500);
  		//WebElement alphanumericField = driver.findElement(By.name("name"));
  		//alphanumericField.sendKeys("test");
	}
	*/
  
  	@Test (priority=52)
  	public void  nameEmpty() throws IOException, WriteException, InterruptedException
  	{
  		Thread.sleep(500);
  		driver.get(Constant.URL+"/equipment-group");
  		Thread.sleep(500);
  		driver.findElement(By.id("addGroup")).click(); 
  		Thread.sleep(1500);
  		parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\Equipment.properties");
  		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.id("next-worstCase"));
		
  		textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,Submit,6);
  	}
  	
  	@Test (priority=53)
  	public void  nameMaxFieldLength() throws IOException, WriteException, InterruptedException
  	{	
  		WebElement alphanumericField = driver.findElement(By.name("name"));
  		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.MaxLengthCheck(testcaseSheetName,alphanumericField,8);
	}
  	
  	@Test (priority=54)
  	public void  nameSpecialChar() throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
  	{
  		WebElement alphanumericField = driver.findElement(By.name("name"));
  		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.nameSpecialCharCheck(testcaseSheetName,alphanumericField,9);
  	}
  	
  	@Test (priority=55)
  	public void  nameBeginingSpaceCheck() throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
  	{
  		WebElement alphanumericField = driver.findElement(By.name("name"));
  		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.nameBeginingSpaceCheck(testcaseSheetName,alphanumericField,10);
  	}
	
  /*	@Test (priority=5)
  	public void  nameDuplicate() throws IOException, WriteException, InterruptedException
  	{
  		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		XSSFSheet sheet =  workbook.getSheet("EquipmentTC");
		
  		String duplicate = sheet.getRow(7).getCell(4).getStringCellValue(); // get name from excel
  		WebElement name = driver.findElement(By.name("name"));
  		Thread.sleep(500);
  		name.clear();
  		Thread.sleep(500);
  		name.sendKeys(duplicate); // send data to input field
  		Thread.sleep(1000);
  		
  		//Data for other fields
  		driver.findElement(By.id("surfaceArea")).sendKeys("10000");
  		driver.findElement(By.id("minBatch")).sendKeys("100");
  		Select preferential = new Select(driver.findElement(By.id("preferentialTransferOption")));
  		preferential.selectByValue("2");
  		Select cleaningSOPNo = new Select(driver.findElement(By.id("cleaningSOPNo")));
  		cleaningSOPNo.selectByValue("1");
  		Select cleaningProcessType = new Select(driver.findElement(By.id("cleaningProcessType")));
  		cleaningProcessType.selectByValue("2");
  		//
  		
  		
  		WebElement savebtn = driver.findElement(By.id("saveEquipment"));
  		savebtn.click();
		
  		String expectedMSG = sheet.getRow(7).getCell(5).getStringCellValue(); // get expected value from excel
		String actualMSG = null;
		Thread.sleep(1000);
			if(driver.findElements(By.className("notify-msg")).size()!=0)
			{
				actualMSG = driver.findElement(By.className("notify-msg")).getText();
				String className = this.getClass().getName(); // get current class name - for screenshot
				String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
				Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
				driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
			}
			XSSFCell actualMSGprint = sheet.getRow(7).getCell(6); //Print actual result in the excel cell
			actualMSGprint.setCellValue(actualMSG);
		
			if(expectedMSG.equalsIgnoreCase(actualMSG))
			{
				XSSFCell status = sheet.getRow(7).getCell(7);
				status.setCellValue("Pass"); //Print status in excel
				status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
			}else
			{
				XSSFCell status = sheet.getRow(7).getCell(7);
				status.setCellValue("Fail"); //Print status in excel
				status.setCellStyle(Utils.style(workbook, "Fail")); //for print red font
			}
		Utils.writeTooutputFile(workbook); // write output to the workbook
		}
  	*/
  	
	/*@Test (priority=5)
	public void  nameBeginingSpaceCheck() throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
	{
		
	}*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

