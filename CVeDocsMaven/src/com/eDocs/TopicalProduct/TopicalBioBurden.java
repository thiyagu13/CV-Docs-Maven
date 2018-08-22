package com.eDocs.TopicalProduct;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import com.eDocs.CommonValidation.NumericValidation;
import com.eDocs.Utils.Constant;
import com.eDocs.Utils.RepositoryParser;
import com.eDocs.Utils.Utils;

import jxl.write.WriteException;

public class TopicalBioBurden {
	
	private RepositoryParser parser;
	public static WebDriver driver = Constant.driver;
	String testcaseSheetName = "TopicalTC";
  
	/*@Test(priority=36)
	public void NumericEmptyTest() throws InterruptedException, WriteException, IOException
	{
		WebElement Submit = driver.findElement(By.name("next"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericEmpty(testcaseSheetName,Submit,38);
	}*/
	
	/*@Test(priority=43)
	public void bioburdenZEROTest() throws InterruptedException, WriteException, IOException
	{
		System.out.println("test: "+driver.findElement(By.name("bioburdenContribution")).isDisplayed());
		if(driver.findElement(By.name("bioburdenContribution")).isDisplayed()==true)
  		{
			WebElement numericField = driver.findElement(By.name("bioburdenContribution"));
			NumericValidation getnumericField = new NumericValidation();
			getnumericField.NumericZERO(testcaseSheetName,numericField,52);
  		}
		else 
  		{
  			XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
  			XSSFSheet sheet = workbook.getSheet(testcaseSheetName);
  			XSSFCell status = sheet.getRow(52).getCell(7);
			status.setCellValue("No BioBurden"); 
			Utils.writeTooutputFile(workbook); 
  		}
	}*/
	
	
	@Test(priority=44)
	public void bioburdenMaxLengthTest() throws InterruptedException, WriteException, IOException
	{
		System.out.println("Size: "+driver.findElement(By.cssSelector(".btn.blue-btn.waves-effect.continue-btn.next.gotoSolid_next2")).getText());
		if(driver.findElement(By.cssSelector(".btn.blue-btn.waves-effect.continue-btn.next.gotoSolid_next2")).getText().equalsIgnoreCase("Next"))
  		{
			driver.findElement(By.cssSelector(".btn.blue-btn.waves-effect.continue-btn.next.gotoSolid_next2")).click();
			Thread.sleep(1500);
			System.out.println("ggdgd:"+driver.findElements(By.className("notify-msg")).size());
			if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Enter Bioburden Contribution Value between 0 - 99"))
			{
				if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				}
				WebElement numericField = driver.findElement(By.name("bioburdenContribution"));
				NumericValidation getnumericField = new NumericValidation();
				getnumericField.NumericMaxLength(testcaseSheetName,numericField,53);
				numericField.clear();
			}
  		}
		else 
	  	{
			System.out.println("else loop");
	  		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
	  		XSSFSheet sheet = workbook.getSheet(testcaseSheetName);
	  		XSSFCell status = sheet.getRow(53).getCell(7);
			status.setCellValue("No BioBurden"); //Print status in excel
			Utils.writeTooutputFile(workbook); // write output to the workbook
	  	}
	}
	
	@Test(priority=45)
	public void bioburdenAlphabetsTest() throws InterruptedException, WriteException, IOException
	{
		if(driver.findElement(By.cssSelector(".btn.blue-btn.waves-effect.continue-btn.next.gotoSolid_next2")).getText().equalsIgnoreCase("Next"))
  		{
			driver.findElement(By.cssSelector(".btn.blue-btn.waves-effect.continue-btn.next.gotoSolid_next2")).click();
			Thread.sleep(1000);
			if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Enter Bioburden Contribution Value between 0 - 99"))
			{
				if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				}
				WebElement numericField = driver.findElement(By.name("bioburdenContribution"));
				NumericValidation getnumericField = new NumericValidation();
				getnumericField.numericFieldAlphabetsCheck(testcaseSheetName,numericField,54);
				numericField.clear();
			}
  		}
		else 
	  	{
	  		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
	  		XSSFSheet sheet = workbook.getSheet(testcaseSheetName);
	  		XSSFCell status = sheet.getRow(54).getCell(7);
			status.setCellValue("No BioBurden"); //Print status in excel
			Utils.writeTooutputFile(workbook); // write output to the workbook
	  	}
	}
	
	@Test(priority=46)
	public void bioburdenSpaceTest() throws InterruptedException, WriteException, IOException
	{
		if(driver.findElement(By.cssSelector(".btn.blue-btn.waves-effect.continue-btn.next.gotoSolid_next2")).getText().equalsIgnoreCase("Next"))
  		{
			driver.findElement(By.cssSelector(".btn.blue-btn.waves-effect.continue-btn.next.gotoSolid_next2")).click();
			Thread.sleep(1000);
			if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Enter Bioburden Contribution Value between 0 - 99"))
			{
				if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				}
				WebElement numericField = driver.findElement(By.name("bioburdenContribution"));
				NumericValidation getnumericField = new NumericValidation();
				getnumericField.numericSpaceCheck(testcaseSheetName,numericField,55);
				numericField.clear();
			}
  		}
		else 
	  	{
	  		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
	  		XSSFSheet sheet = workbook.getSheet(testcaseSheetName);
	  		XSSFCell status = sheet.getRow(55).getCell(7);
			status.setCellValue("No BioBurden"); //Print status in excel
			Utils.writeTooutputFile(workbook); // write output to the workbook
	  	}
	}
	
	@Test(priority=47)
	public void bioburdenMultiDecimalTest() throws InterruptedException, WriteException, IOException
	{
		if(driver.findElement(By.cssSelector(".btn.blue-btn.waves-effect.continue-btn.next.gotoSolid_next2")).getText().equalsIgnoreCase("Next"))
  		{
			driver.findElement(By.cssSelector(".btn.blue-btn.waves-effect.continue-btn.next.gotoSolid_next2")).click();
			Thread.sleep(1000);
			if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Enter Bioburden Contribution Value between 0 - 99"))
			{
				if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				}
				WebElement numericField = driver.findElement(By.name("bioburdenContribution"));
				NumericValidation getnumericField = new NumericValidation();
				getnumericField.numericMultiDecimal(testcaseSheetName,numericField,56);
				numericField.clear();
				numericField.sendKeys("20");
			}
  		}
		else 
	  	{
	  		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
	  		XSSFSheet sheet = workbook.getSheet(testcaseSheetName);
	  		XSSFCell status = sheet.getRow(56).getCell(7);
			status.setCellValue("No BioBurden"); //Print status in excel
			Utils.writeTooutputFile(workbook); // write output to the workbook
	  	}
		
		if(driver.findElement(By.cssSelector(".btn.blue-btn.waves-effect.continue-btn.next.gotoSolid_next2")).getText().equalsIgnoreCase("Next"))
  		{
		//Click Next
		Thread.sleep(500);
		WebElement Submit = driver.findElement(By.name("next"));
		Submit.click();
		Thread.sleep(1000);
		Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
		for(int k=1;k<1000;k++)
		{
			j.add(k);
		}
		WebElement getName = driver.findElement(By.name("productName"));
		String getEqName = getName.getAttribute("value");
		
		Thread.sleep(500);
		if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Product '"+getEqName+"' already exists!"))
		{
			String getduplicatename = driver.findElement(By.className("notify-msg")).getText();
			driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
		
		Thread.sleep(500);
		if(getduplicatename.equalsIgnoreCase("Product '"+getEqName+"' already exists!"))
		{
			for(Integer i:j)
			{
				getName.clear();
				Thread.sleep(200);
				getName.sendKeys(getEqName+i);
				Thread.sleep(500);
				Submit.click();
				Thread.sleep(500);
				if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Product '"+getEqName+i+"' already exists!"))
				{
					String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
					if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Product '"+getEqName+i+"' already exists!"))
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
		
	}
  	
	}
	
	
	
	
	
	
	
	
	
	
}

