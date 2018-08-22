package com.eDocs.Equipment;

import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
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

public class EquipmentRiskAssessment {
	
	private RepositoryParser parser;
	public static WebDriver driver = Constant.driver;
	String testcaseSheetName = "EquipmentTC";
	
  	@Test (priority=36)
  	public void  RiskLocationEmptyTest() throws IOException, WriteException, InterruptedException
  	{
  		
  		System.out.println("Risk Size: "+ driver.findElements(By.id("submitLocationAssessment")).size());
  		if(driver.findElements(By.id("submitLocationAssessment")).size()!=0)
  		{
  			Thread.sleep(500);
  			driver.findElement(By.id("addLocation")).click();
  			Thread.sleep(500);
  			AlphaNumericValidation textField = new AlphaNumericValidation();
  	  		WebElement alphanumericField = driver.findElement(By.id("location1"));
  			WebElement Submit = driver.findElement(By.id("submitLocationAssessment"));
  	  		textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,Submit,46);
  		}
  		else 
  		{
  			XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
  			XSSFSheet sheet = workbook.getSheet("EquipmentTC");
  			XSSFCell status = sheet.getRow(46).getCell(7);
			status.setCellValue("No Risk Assessment"); //Print status in excel
			Utils.writeTooutputFile(workbook); // write output to the workbook
  		}
  		
  	}
  	
  	
  	@Test (priority=37)
  	public void  RiskLocationMaxLengthTest() throws IOException, WriteException, InterruptedException
  	{
  		
  		System.out.println("Risk Size: "+ driver.findElements(By.id("submitLocationAssessment")).size());
  		if(driver.findElements(By.id("submitLocationAssessment")).size()!=0)
  		{
  			Thread.sleep(500);
  	  		if(driver.findElements(By.id("location1")).size()!=0)
  	  		{
  	  			AlphaNumericValidation textField = new AlphaNumericValidation();
  	  			WebElement alphanumericField = driver.findElement(By.id("location1"));
  	  			textField.MaxLengthCheck(testcaseSheetName,alphanumericField,47);
  	  		}else {
  	  			driver.findElement(By.id("addLocation")).click();
  	  			Thread.sleep(500);
  	  			AlphaNumericValidation textField = new AlphaNumericValidation();
  	  			WebElement alphanumericField = driver.findElement(By.id("location1"));
  	  			textField.MaxLengthCheck(testcaseSheetName,alphanumericField,47);
  	  		}
  	  	Thread.sleep(1000);
  		driver.findElement(By.id("checkbox1")).click(); // Click Check box
  		Thread.sleep(1000);
  		driver.findElement(By.id("submitLocationAssessment")).click(); // Next button in risk assessment tab
  		}
  		
  		else 
  		{
  			XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
  			XSSFSheet sheet = workbook.getSheet("EquipmentTC");
  			XSSFCell status = sheet.getRow(47).getCell(7);
			status.setCellValue("No Risk Assessment"); //Print status in excel
			Utils.writeTooutputFile(workbook); // write output to the workbook
  		}
  		
  		
  	}
  	
	
	
}

