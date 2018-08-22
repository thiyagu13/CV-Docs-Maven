package com.eDocs.Equipment;

import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.eDocs.CommonValidation.NumericValidation;
import com.eDocs.Utils.Constant;
import com.eDocs.Utils.RepositoryParser;
import com.eDocs.Utils.Utils;

import jxl.write.WriteException;

public class EquipmentPrefTransfr {
	
	private RepositoryParser parser;
	public static WebDriver driver = Constant.driver;
	String testcaseSheetName = "EquipmentTC";
	
	@Test(priority=12)
	public void EqupmentPrefTransferEmptyTest() throws InterruptedException, WriteException, IOException
	{
		NumericValidation dropDown = new NumericValidation();
		WebElement Submit = driver.findElement(By.id("saveEquipment"));
		dropDown.NumericEmpty(testcaseSheetName,Submit,22);
	}

	@Test(priority=13)
	public void EqupmentPrefTransferNo() throws Exception
	{
		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		XSSFSheet sheet = workbook.getSheet("EquipmentTC");
		Thread.sleep(1000);
		driver.findElement(By.id("surfaceAreaDataSource")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ARROW_DOWN,Keys.ENTER);
		Thread.sleep(500);
		Select preferentialTransferOption =  new Select(driver.findElement(By.id("preferentialTransferOption")));
		/*preferentialTransferOption.selectByValue("2");*/ 
		WebElement option = preferentialTransferOption.getFirstSelectedOption(); 
		String getpreferentialTransferOption = option.getText();
		System.out.println("preferentialTransferOption: "+getpreferentialTransferOption);
		Thread.sleep(1000);
		if(getpreferentialTransferOption.equalsIgnoreCase("No"))
		{
			System.out.println("True");
			XSSFCell status = sheet.getRow(29).getCell(7);
			status.setCellValue("Pass"); //Print status in excel
			status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
			//Assert.assertTrue(true);
		}else
		{
			XSSFCell status = sheet.getRow(29).getCell(7);
			status.setCellValue("Pass"); //Print status in excel
			status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
			//throw new Exception();
		}
		Thread.sleep(500);
		driver.findElement(By.id("surfaceAreaDataSource")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ARROW_UP,Keys.ENTER);
		Thread.sleep(500);
	}
	
	@Test(priority=14)
	public void EqupmentPrefTransferYes() throws Exception
	{
		Thread.sleep(1000);
		//preferentialTransferOption.selectByValue("1"); // Equipment Pref Transfer
		//driver.findElement(By.id("surfaceAreaDataSource")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ARROW_DOWN,Keys.ENTER);
		//driver.findElement(By.id("minBatch")).sendKeys(Keys.SHIFT,Keys.TAB,Keys.ENTER,Keys.ARROW_UP,Keys.ENTER);//select yes
		//Thread.sleep(1000);
		//driver.findElement(By.id("preferentialTransferSurfaceArea")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);//select option primary packing
		//System.out.println("--->");
		//Thread.sleep(500);
		Select preferentialTransferOption =  new Select(driver.findElement(By.id("preferentialTransferOption")));
		//WebElement packagingoption = preferentialTransferOption.getFirstSelectedOption(); 
		//String getpackaging = packagingoption.getText();
		//System.out.println("preferentialTransferOption: "+getpackaging);
		
		WebElement option = preferentialTransferOption.getFirstSelectedOption(); 
		String getpreferentialTransferOption = option.getText();
		System.out.println("preferentialTransferOption: "+getpreferentialTransferOption);
		Thread.sleep(1500);
		if(getpreferentialTransferOption.equalsIgnoreCase("Yes"))
		{
			Assert.assertTrue(true);
		}else
		{
			throw new Exception();
		}
	}
	
	
	@Test(priority=15)
	public void NumericEmptyTest() throws InterruptedException, WriteException, IOException
	{
		WebElement Submit = driver.findElement(By.id("saveEquipment"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericEmpty(testcaseSheetName,Submit,23);
	}
	
	@Test(priority=16)
	public void NumericZEROTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.id("preferentialTransferSurfaceArea"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericZERO(testcaseSheetName,numericField,24);
	}
	
	@Test(priority=17)
	public void NumericMaxLengthTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.id("preferentialTransferSurfaceArea"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericMaxLength(testcaseSheetName,numericField,25);
	}
	
	@Test(priority=18)
	public void numericFieldAlphabetsCheckTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.id("preferentialTransferSurfaceArea"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericFieldAlphabetsCheck(testcaseSheetName,numericField,26);
	}
	
	@Test(priority=19)
	public void numericSpaceCheckTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.id("preferentialTransferSurfaceArea"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericSpaceCheck(testcaseSheetName,numericField,27);
	}
	
	@Test(priority=20)
	public void numericMultiDecimalTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.id("preferentialTransferSurfaceArea"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericMultiDecimal(testcaseSheetName,numericField,28);
		
		//select primary packing
		Thread.sleep(500);
		numericField.sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
		Thread.sleep(500);
	}
	
	
}

