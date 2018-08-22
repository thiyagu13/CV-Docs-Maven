package com.eDocs.Equipment;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.eDocs.CommonValidation.AlphaNumericValidation;
import com.eDocs.Utils.Constant;
import com.eDocs.Utils.RepositoryParser;
import com.eDocs.Utils.Utils;

import jxl.write.WriteException;

public class EquipmentNonMandatoryFields {
	
	private RepositoryParser parser;
	public static WebDriver driver = Constant.driver;
	String testcaseSheetName = "EquipmentTC";
	
	
	@Test(priority=30)
	public void ModelMaxLengthTest() throws InterruptedException, WriteException, IOException
	{
		WebElement alphanumericField = driver.findElement(By.id("model"));
		AlphaNumericValidation getAlphanumericField = new AlphaNumericValidation();
		getAlphanumericField.MaxLengthCheck(testcaseSheetName,alphanumericField, 12);
	}
	
	@Test(priority=31)
	public void SerialNoMaxLengthTest() throws InterruptedException, WriteException, IOException
	{
		WebElement alphanumericField = driver.findElement(By.id("serialOrAssetNo"));
		AlphaNumericValidation getAlphanumericField = new AlphaNumericValidation();
		getAlphanumericField.MaxLengthCheck(testcaseSheetName,alphanumericField, 13);
		//serial no
		Thread.sleep(500);
		alphanumericField.clear();
		alphanumericField.sendKeys("111");
		Thread.sleep(500);
	}
	
	@Test(priority=32)
	public void ManufacturerMaxLengthTest() throws InterruptedException, WriteException, IOException
	{
		WebElement alphanumericField = driver.findElement(By.id("manufacturer"));
		AlphaNumericValidation getAlphanumericField = new AlphaNumericValidation();
		getAlphanumericField.MaxLengthCheck(testcaseSheetName,alphanumericField, 14);
	}
	
	@Test(priority=33)
	public void SurfaceDataSourceMaxLengthTest() throws InterruptedException, WriteException, IOException
	{
		WebElement alphanumericField = driver.findElement(By.id("surfaceAreaDataSource"));
		AlphaNumericValidation getAlphanumericField = new AlphaNumericValidation();
		getAlphanumericField.MaxLengthCheck(testcaseSheetName,alphanumericField, 21);
	}
	
	/*@Test(priority=34)
	public void QualificationDocIDMaxLengthTest() throws InterruptedException, WriteException, IOException
	{
		WebElement alphanumericField = driver.findElement(By.id("qualificationDocId"));
		AlphaNumericValidation getAlphanumericField = new AlphaNumericValidation();
		getAlphanumericField.MaxLengthCheck(testcaseSheetName,alphanumericField, 37);
	}*/
	
	@Test(priority=35)
	public void OtherCleaningInfoMaxLengthTest() throws InterruptedException, WriteException, IOException
	{
		
		WebElement alphanumericField = driver.findElement(By.id("otherCleaningInfo"));
		AlphaNumericValidation getAlphanumericField = new AlphaNumericValidation();
		getAlphanumericField.MaxLengthCheck(testcaseSheetName,alphanumericField, 41);
		Thread.sleep(1000);
		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		XSSFSheet sheet = workbook.getSheet("EquipmentTC");
		Thread.sleep(1000);
		//Click Submit button in first Tab to move on next page
		System.out.println("Submit");
		Thread.sleep(1500);
		driver.findElement(By.id("surfaceArea")).clear();
		Thread.sleep(500);
		driver.findElement(By.id("surfaceArea")).sendKeys("1000");
		Thread.sleep(500);
		WebElement Submit = driver.findElement(By.id("saveEquipment"));
		Submit.click();
		Thread.sleep(1000);
		Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
		for(int k=5;k<1000;k++)
		{
			j.add(k);
		}
		WebElement getName = driver.findElement(By.name("name"));
		String getEqName = getName.getAttribute("value");
		
		WebElement SerialNO = driver.findElement(By.name("serialOrAssetNo"));
		String getSerialNo = SerialNO.getAttribute("value");
		
		Thread.sleep(500);
		if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Equipment '"+getEqName+"' already exists!"))
		{
			String getduplicatename = driver.findElement(By.className("notify-msg")).getText();
			driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
		
		Thread.sleep(500);
		if(getduplicatename.equalsIgnoreCase("Equipment '"+getEqName+"' already exists!"))
		{
			for(Integer i:j)
			{
				//driver.findElement(parser.getbjectLocator("ActiveIngredientName")).clear();
				getName.clear();
				Thread.sleep(200);
				getName.sendKeys(getEqName+i);
				//driver.findElement(parser.getbjectLocator("ActiveIngredientName")).sendKeys(Name+i);
				Thread.sleep(500);
				Submit.click();
				//driver.findElement(parser.getbjectLocator("APIsubmit")).click();
				Thread.sleep(500);
				if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Equipment '"+getEqName+i+"' already exists!"))
				{
					String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
					System.out.println("Name duplicate: "+nameduplicate);
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
					if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Equipment '"+getEqName+i+"' already exists!"))
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
		
		
		//Serial no already exists 
		if( driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Equipment Identification Number '"+getSerialNo+"' already exists!"))
		{
			String getduplicateID = driver.findElement(By.className("notify-msg")).getText();
			driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
		System.out.println("getduplicatename: "+getduplicateID);
		if(getduplicateID.equalsIgnoreCase("Equipment Identification Number '"+getSerialNo+"' already exists!"))
		{
			for(Integer i:j)
			{
				SerialNO.clear();
				Thread.sleep(200);
				SerialNO.sendKeys(getSerialNo+i);
				Thread.sleep(500);
				//driver.findElement(parser.getbjectLocator("APIsubmit")).click();
				Submit.click();
				Thread.sleep(500);
				if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Equipment Identification Number '"+getSerialNo+i+"' already exists!"))
				{
					String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
					System.out.println("Name duplicate: "+nameduplicate);
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
					if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Equipment Identification Number '"+getSerialNo+i+"' already exists!"))
					{
						continue;
					}
				}
						System.out.println("Not duplicate so break the loop");
						break;
				}
		}
		
	}
		Utils.writeTooutputFile(workbook); // write output to the workbook
	}
	
	
	
	
}

