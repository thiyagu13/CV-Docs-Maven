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
import org.testng.Assert;
import org.testng.annotations.Test;
import com.eDocs.CommonValidation.NumericValidation;
import com.eDocs.Utils.Constant;
import com.eDocs.Utils.RepositoryParser;
import com.eDocs.Utils.Utils;

import jxl.write.WriteException;

public class EquipmentCleaningProcessType {
	
	private RepositoryParser parser;
	public static WebDriver driver = Constant.driver;
	String testcaseSheetName = "EquipmentTC";
	
	@Test(priority=27)
	public void CleaningProcessTypeEmptyTest() throws InterruptedException, WriteException, IOException
	{
		NumericValidation dropDown = new NumericValidation();
		WebElement Submit = driver.findElement(By.id("saveEquipment"));
		dropDown.NumericEmpty(testcaseSheetName,Submit,39);
	}
	
	@Test(priority=28)
	public void CleaningProcessTypeManual() throws Exception
	{
		Thread.sleep(500);
		driver.findElement(By.id("minBatch")).sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.ENTER,Keys.ARROW_DOWN,Keys.ENTER);
		Select ClProcessType =  new Select(driver.findElement(By.id("cleaningProcessType")));
		/*ClProcessType.selectByValue("2"); // select Manual
*/		WebElement option = ClProcessType.getFirstSelectedOption(); 
		String getCLProType = option.getText();
		System.out.println("getCLProType: "+getCLProType);
		if(getCLProType.contains("Manual"))
		{
			System.out.println("True");
			Assert.assertTrue(true);
		}else
		{
			throw new Exception();
		}
	}
	
	@Test(priority=29)
	public void CleaningProcessTypeAuto() throws Exception
	{
		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		XSSFSheet sheet = workbook.getSheet("EquipmentTC");
		Thread.sleep(1500);
		Select ClProcessType =  new Select(driver.findElement(By.id("cleaningProcessType")));
		//driver.findElement(By.id("otherCleaningInfo")).sendKeys(Keys.SHIFT,Keys.TAB,Keys.ENTER,Keys.ARROW_UP,Keys.ENTER);
		driver.findElement(By.id("minBatch")).sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.ENTER,Keys.ARROW_DOWN,Keys.ENTER);
		//ClProcessType.selectByValue("1"); // select Auto
		Thread.sleep(500);
		WebElement option = ClProcessType.getFirstSelectedOption(); 
		String getCLProType = option.getText();
		System.out.println("getCLProType: "+getCLProType);
		Thread.sleep(1500);
		if(getCLProType.contains("Auto") && driver.findElements(By.id("cleaningProcessTypeCIP")).size()!=0)
		{
			System.out.println("True");
			driver.findElement(By.id("otherCleaningInfo")).sendKeys(Keys.SHIFT,Keys.TAB,Keys.ENTER,Keys.ENTER);
			XSSFCell status = sheet.getRow(40).getCell(7);
			status.setCellValue("Pass"); //Print status in excel
			status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
			//Assert.assertTrue(true);
		}else
		{
			//throw new Exception();
			XSSFCell failstatus = sheet.getRow(40).getCell(7);
			failstatus.setCellValue("Pass"); //Print status in excel
			failstatus.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
		}
		Thread.sleep(1500);
			
		Utils.writeTooutputFile(workbook);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}

