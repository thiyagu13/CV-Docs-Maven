package com.eDocs.CommonValidation;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.RepositoryParser;
import com.eDocs.Utils.Utils;

import jxl.write.WriteException;

public class NumericValidation {
 
	private RepositoryParser parser;
	public static WebDriver driver = Constant.driver;
	

	/*@BeforeClass
	public void setUp() throws IOException  
	{
		//driver = new FirefoxDriver();
		//driver.get("http://192.168.1.111:8090");
		driver.get("http://192.168.1.111:8090");
		parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\Equipment.properties");
	}

	@Test(priority=1)
	public void Login() throws InterruptedException
	{
		//Lets see how we can find the first name field
		Thread.sleep(1000);
		WebElement username = driver.findElement(By.id("username"));
		WebElement password = driver.findElement(By.id("password"));
		username.sendKeys("thiyagu1");
		Thread.sleep(500);
		password.sendKeys("123456");
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
		driver.get("http://192.168.1.111:8090/equipment");
		Thread.sleep(1500);
		driver.findElement(By.id("addEquipment")).click(); 
  		Thread.sleep(1500);
	}*/
		
		
		

  	//@Test (priority=2)
  	public void  NumericEmpty(String testcaseSheetName,WebElement sumit, int row) throws IOException, WriteException, InterruptedException
  	{
  		//XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
  		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		XSSFSheet sheet = workbook.getSheet(testcaseSheetName);
		Thread.sleep(500);
		//driver.findElement(By.name("name")).sendKeys("test");// for surface empty check
		//Thread.sleep(1000);
  		//WebElement savebtn = driver.findElement(By.id("saveEquipment"));
		sumit.click();
		Thread.sleep(500);
		String emptyExpected = sheet.getRow(row).getCell(5).getStringCellValue(); 
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
			XSSFCell actualMSGprint = sheet.getRow(row).getCell(6); //Print actual result in the excel cell
			actualMSGprint.setCellValue(actualMSG);
		
			if(emptyExpected.equalsIgnoreCase(actualMSG))
			{
				XSSFCell status = sheet.getRow(row).getCell(7);
				status.setCellValue("Pass"); //Print status in excel
				status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
			}else
			{
				XSSFCell status = sheet.getRow(row).getCell(7);
				status.setCellValue("Fail"); //Print status in excel
				status.setCellStyle(Utils.style(workbook, "Fail")); //for print red font
			}
		Utils.writeTooutputFile(workbook); // write output to the workbook
		
		
  		}
  	
  	
  //	@Test (priority=3)
  	public void  NumericZERO(String testcaseSheetName,WebElement numericField,int row) throws IOException, WriteException, InterruptedException
  	{
  		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
  		XSSFSheet sheet = workbook.getSheet(testcaseSheetName);
		Thread.sleep(500);
  		//WebElement numericField = driver.findElement(By.id("surfaceArea"));
  		//Thread.sleep(1000);
  		numericField.sendKeys("0");
  		Thread.sleep(500);
  		numericField.sendKeys(Keys.TAB);
  		//WebElement savebtn = driver.findElement(By.id("saveEquipment"));
  		//savebtn.click();
  		
  		String expectedMSG="";
		//String expectedMSG = sheet.getRow(row).getCell(5).getStringCellValue(); // get expected value from excel
		//String actualMSG = null;
		Thread.sleep(500);
		/*	if(driver.findElements(By.className("notify-msg")).size()!=0)
			{
				actualMSG = driver.findElement(By.className("notify-msg")).getText();
				String className = this.getClass().getName(); // get current class name - for screenshot
				String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
				Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
				driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
			}
		
			XSSFCell actualMSGprint = sheet.getRow(row).getCell(6); //Print actual result in the excel cell
			actualMSGprint.setCellValue(actualMSG);
		*/
			if(expectedMSG.equalsIgnoreCase(numericField.getAttribute("value")))
			{
				XSSFCell status = sheet.getRow(row).getCell(7);
				status.setCellValue("Pass"); //Print status in excel
				status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
			}else
			{
				XSSFCell status = sheet.getRow(row).getCell(7);
				status.setCellValue("Fail"); //Print status in excel
				status.setCellStyle(Utils.style(workbook, "Fail")); //for print red font
			}
		Utils.writeTooutputFile(workbook); // write output to the workbook
		
  		}
  	
  	
  	
  	//@Test (priority=4)
  	public void  NumericMaxLength(String testcaseSheetName,WebElement numericfield,int row) throws IOException, WriteException, InterruptedException
  	{
  		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		XSSFSheet sheet = workbook.getSheet(testcaseSheetName);
		
  		double maxLengthdata = sheet.getRow(row).getCell(4).getNumericCellValue(); 
  		//WebElement numericfield = driver.findElement(By.id("surfaceArea"));
  		Thread.sleep(500);
  		numericfield.clear();
  		Thread.sleep(500);
  		System.out.println(String.valueOf(maxLengthdata));
  		//numericfield.sendKeys(String.valueOf(maxLengthdata)); // send data to input field
  		numericfield.sendKeys("12345678");
  		Thread.sleep(500);
  		
		double  ExpectedLength = sheet.getRow(row).getCell(5).getNumericCellValue(); // get expected from excel
		double  Actuallength = numericfield.getAttribute("value").length(); // get expected length of the value present in the sf Text box
		System.out.println(numericfield.getAttribute("value"));
		System.out.println(Actuallength);

		XSSFCell maxLength_actual_print = sheet.getRow(row).getCell(6); //Print actual result in the excel cell
		maxLength_actual_print.setCellValue(Actuallength);
		
		if(ExpectedLength == Actuallength)
		{
			XSSFCell status = sheet.getRow(row).getCell(7);
			status.setCellValue("Pass"); //Print status in excel
			status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
		}else
		{
			XSSFCell status = sheet.getRow(row).getCell(7);
			status.setCellValue("Fail"); //Print status in excel
			status.setCellStyle(Utils.style(workbook, "Fail")); //for print red font
		}
	  
		Utils.writeTooutputFile(workbook); // write output to the workbook
		String className = this.getClass().getName(); // get current class name - for screenshot
		String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
		Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
  		}
  	
  	
  	
  	
  //	@Test (priority=5)
  	public void  numericFieldAlphabetsCheck(String testcaseSheetName,WebElement numericfield,int row) throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
  	{
  		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		XSSFSheet sheet = workbook.getSheet(testcaseSheetName);
		
  		String Data = sheet.getRow(row).getCell(4).getStringCellValue(); 
  		//WebElement numericfield = driver.findElement(By.id("surfaceArea"));
  		Thread.sleep(500);
  		numericfield.clear();
  		Thread.sleep(500);
  		numericfield.sendKeys(Data); // send data to input field
  		Thread.sleep(500);
  		
		String  ActualMSG = numericfield.getText(); // get expected length of the value present in the sf Text box
		System.out.println(ActualMSG);

		XSSFCell maxLength_actual_print = sheet.getRow(row).getCell(6); //Print actual result in the excel cell
		maxLength_actual_print.setCellValue(ActualMSG);
		
		if(ActualMSG.equals(""))
		{
			XSSFCell status = sheet.getRow(row).getCell(7);
			status.setCellValue("Pass"); //Print status in excel
			status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
		}else
		{
			XSSFCell status = sheet.getRow(row).getCell(7);
			status.setCellValue("Fail"); //Print status in excel
			status.setCellStyle(Utils.style(workbook, "Fail")); //for print red font
		}
	  
		Utils.writeTooutputFile(workbook); // write output to the workbook
		String className = this.getClass().getName(); // get current class name - for screenshot
		String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
		Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
  		}
  	
  	//@Test (priority=6)
  	public void  numericSpaceCheck(String testcaseSheetName,WebElement numericfield,int row) throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
  	{
  		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		XSSFSheet sheet = workbook.getSheet(testcaseSheetName);
		
  		String maxLengthdata = String.valueOf(sheet.getRow(row).getCell(4).getStringCellValue()); 
  		//WebElement numericfield = driver.findElement(By.id("surfaceArea"));
  		Thread.sleep(500);
  		numericfield.clear();
  		Thread.sleep(500);
  		numericfield.sendKeys(maxLengthdata); // send data to input field
  		Thread.sleep(500);
  		
		double  ExpectedLength = sheet.getRow(row).getCell(5).getNumericCellValue(); // get expected from excel
		double  Actuallength = Double.valueOf(numericfield.getAttribute("value")); // get expected length of the value present in the sf Text box
		System.out.println(Actuallength);

		XSSFCell maxLength_actual_print = sheet.getRow(row).getCell(6); //Print actual result in the excel cell
		maxLength_actual_print.setCellValue(Actuallength);
		
		if(ExpectedLength == Actuallength)
		{
			XSSFCell status = sheet.getRow(row).getCell(7);
			status.setCellValue("Pass"); //Print status in excel
			status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
		}else
		{
			XSSFCell status = sheet.getRow(row).getCell(7);
			status.setCellValue("Fail"); //Print status in excel
			status.setCellStyle(Utils.style(workbook, "Fail")); //for print red font
		}
	  
		Utils.writeTooutputFile(workbook); // write output to the workbook
		String className = this.getClass().getName(); // get current class name - for screenshot
		String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
		Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
  		}
  	
	
  //	@Test (priority=7)
  	public void  numericMultiDecimal(String testcaseSheetName,WebElement numericfield, int row) throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
  	{
  		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		XSSFSheet sheet = workbook.getSheet(testcaseSheetName);
		
		String maxLengthdata = String.valueOf(sheet.getRow(row).getCell(4).getStringCellValue());
  		//WebElement numericfield = driver.findElement(By.id("surfaceArea"));
  		Thread.sleep(500);
  		numericfield.clear();
  		Thread.sleep(500);
  		numericfield.sendKeys(String.valueOf(maxLengthdata)); // send data to input field
  		Thread.sleep(500);
  		
		double  ExpectedLength = sheet.getRow(row).getCell(5).getNumericCellValue(); // get expected from excel
		double  Actuallength = Double.valueOf(numericfield.getAttribute("value")); // get expected length of the value present in the sf Text box
		System.out.println(Actuallength);

		XSSFCell maxLength_actual_print = sheet.getRow(row).getCell(6); //Print actual result in the excel cell
		maxLength_actual_print.setCellValue(Actuallength);
		
		if(ExpectedLength == Actuallength)
		{
			XSSFCell status = sheet.getRow(row).getCell(7);
			status.setCellValue("Pass"); //Print status in excel
			status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
		}else
		{
			XSSFCell status = sheet.getRow(row).getCell(7);
			status.setCellValue("Fail"); //Print status in excel
			status.setCellStyle(Utils.style(workbook, "Fail")); //for print red font
		}
	  
		Utils.writeTooutputFile(workbook); // write output to the workbook
		String className = this.getClass().getName(); // get current class name - for screenshot
		String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
		Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
  		}
	
	
	
}
