package com.eDocs.Equipment;

import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.eDocs.CommonValidation.AlphaNumericValidation;
import com.eDocs.Utils.Constant;
import com.eDocs.Utils.RepositoryParser;
import com.eDocs.Utils.Utils;

import jxl.write.WriteException;

public class EquipmentSamplingInfo {
	
	private RepositoryParser parser;
	public static WebDriver driver = Constant.driver;
	String testcaseSheetName = "EquipmentTC";
	
	public static void writeExcel(int row) throws IOException
	{
		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		XSSFSheet sheet = workbook.getSheet("EquipmentTC");
		
		XSSFCell status = sheet.getRow(row).getCell(7);
		status.setCellValue("DropDown"); //Print status in excel
		Utils.writeTooutputFile(workbook); // write output to the workbook
	}
	
  
  	@Test (priority=38)
  	public void  SamplingLocationEmptyPinTest() throws IOException, WriteException, InterruptedException
  	{
  		Thread.sleep(1000);
  		//Upload Image
  		/*Select UploadIMGYesorNo = new Select(driver.findElement(By.name("imageFlag")));
  		UploadIMGYesorNo.selectByValue("2");*/
  		driver.findElement(By.xpath(".//*[@id='needUloadImages']/div/div/span/span[1]/span/span[2]")).click();
  		Thread.sleep(300);
  		driver.findElement(By.xpath(".//*[@id='needUloadImages']/div/div/span/span[1]/span/span[2]")).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
  		Thread.sleep(1000);
  		//driver.findElement(By.id("addPinName")).click();
  		//Thread.sleep(500);
  		//driver.findElement(By.id("submitEquipmentSamplingDetails")).click();
  		//Thread.sleep(1000);
  		AlphaNumericValidation textField = new AlphaNumericValidation();
  	  //	WebElement alphanumericField = driver.findElement(By.id("locationName1"));
  		WebElement Submit = driver.findElement(By.id("submitEquipmentSamplingDetails"));
  	  	textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,Submit,50);
  	}
  	
  //	WebElement SamplingSubmit;
  	@Test (priority=39)
  	public void  SamplingInfoSamplingMethodEmptyTest() throws IOException, WriteException, InterruptedException
  	{
  		Thread.sleep(1000);
  		//Upload Image
  		//Select UploadIMGYesorNo = new Select(driver.findElement(By.id("uploadSampleTypeImage")));
  		//UploadIMGYesorNo.selectByVisibleText("No");
  		//Thread.sleep(1000);
  		driver.findElement(By.id("addPinName")).click();
  		Thread.sleep(500);
  		
  		//Select MOC = new Select(driver.findElement(By.id("moc1")));
  		//MOC.selectByIndex(1);
  		// if Sampling Type in univ settings - location name will be text box
  		WebElement Submit = driver.findElement(By.id("submitEquipmentSamplingDetails"));
  		WebElement alphanumericField = driver.findElement(By.id("locationName1"));
  		if(alphanumericField.getAttribute("type").contains("select"))
  		{
  			Thread.sleep(500);
  	  		AlphaNumericValidation textField = new AlphaNumericValidation();
  	  	  	//WebElement moc = driver.findElement(By.id("mocy1"));
  	  	  	textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,Submit,53);
  	  	  	writeExcel(51);
  	  	  	writeExcel(52);
  		}else
  		{
  			//Location field Empty Check
  			Thread.sleep(500);
  	  		AlphaNumericValidation textField = new AlphaNumericValidation();
  	  	  	textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,Submit,51);
  	  	  	//Location field Max Length Check
  	  	  	Thread.sleep(500);
	  		AlphaNumericValidation textField1 = new AlphaNumericValidation();
	  	  	textField1.MaxLengthCheck(testcaseSheetName,alphanumericField, 52);
	  	  	//Sampling method empty Check
	  	  	Thread.sleep(500);
	  		AlphaNumericValidation textField2 = new AlphaNumericValidation();
	  	  	//WebElement moc = driver.findElement(By.id("mocy1"));
	  	  	textField2.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,Submit,53);
  		}
  	  	Thread.sleep(1000);
  	  	//Select Sampling Method
  	  	System.out.println("Test1");
  	  	System.out.println("alphanumericField.getTagName(): "+alphanumericField.getAttribute("type"));
  	  		if(alphanumericField.getAttribute("type").equalsIgnoreCase("text"))
  	  		{
  	  			System.out.println("Text");
  	  			Thread.sleep(500);
  	  			alphanumericField.sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.ENTER,Keys.ENTER);
  	  		}
  	  		else
  	  		{
  	  		System.out.println("select");
  	  			driver.findElement(By.xpath(".//*[@id='marker-num1']/div[4]/span/span[1]/span/ul/li/input")).click();
  	  			Thread.sleep(500);
  	  			driver.findElement(By.xpath(".//*[@id='marker-num1']/div[4]/span/span[1]/span/ul/li/input")).sendKeys(Keys.ENTER);
  	  		}
  	  		
  	  		/*
  	  		driver.findElement(By.xpath(".//*[@id='marker-num1']/div[5]/span/span[1]/span/ul")).click();
	  		Thread.sleep(500);
	  		driver.findElement(By.xpath(".//*[@id='marker-num1']/div[5]/span/span[1]/span/ul")).sendKeys(Keys.ENTER);*/
  	  	
  	  	
  	  	 /* 
  	  	WebElement Sampling = driver.findElement(By.id("mocy1"));
		Select SelectSampling = new Select(Sampling);
		SelectSampling.selectByValue("1");*/
		Thread.sleep(500);
  	}
  	
  	
	@Test (priority=40)
  	public void  SamplingInfoMOCEmptyTest() throws IOException, WriteException, InterruptedException
  	{
			WebElement Submit = driver.findElement(By.id("submitEquipmentSamplingDetails"));
			AlphaNumericValidation textField = new AlphaNumericValidation();
	  	  	textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,Submit,54);
	  	  	//Select MOC
	  	  if(driver.findElement(By.id("locationName1")).getAttribute("type").contains("text")) 
			{
				//select MOC
	  		  	Thread.sleep(500);
				driver.findElement(By.id("locationName1")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
				Thread.sleep(500);
			}
			//MOC Selection
			System.out.println("-->"+driver.findElement(By.id("locationName1")).getAttribute("type"));
			if(driver.findElement(By.id("locationName1")).getAttribute("type").contains("select"))
			{
				Thread.sleep(1000);
				driver.findElement(By.xpath(".//*[@id='marker-num1']/div[3]/span/span[1]/span")).click();
				driver.findElement(By.xpath(".//*[@id='marker-num1']/div[3]/span/span[1]/span")).sendKeys(Keys.ENTER);
			}
  	}
  	
  	
  	
  	
  	
  	
}

