package com.eDocs.API;

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
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.eDocs.CommonValidation.NumericValidation;
import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Message;
import com.eDocs.Utils.RepositoryParser;
import com.eDocs.Utils.Utils;

import jxl.write.WriteException;

public class NonMandatoryFields {
	
	private RepositoryParser parser;
	public static WebDriver driver = Constant.driver;
	String testcaseSheetName = "ProductTC";
  
	//Start Number of carbon actoms in active
	@Test(priority=18)
	public void NoofCarbonZEROTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("numberofCarbonAtoms"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericZERO(testcaseSheetName,numericField,27);
	}
	
	@Test(priority=19)
	public void NoofCarbonMaxLengthTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("numberofCarbonAtoms"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericMaxLength(testcaseSheetName,numericField,28);
	}
	
	@Test(priority=20)
	public void NoofCarbonAlphabetsTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("numberofCarbonAtoms"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericFieldAlphabetsCheck(testcaseSheetName,numericField,29);
	}
	
	@Test(priority=21)
	public void NoofCarbonSpaceTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("numberofCarbonAtoms"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericSpaceCheck(testcaseSheetName,numericField,30);
	}
	
	@Test(priority=22)
	public void NoofCarbonMultiDecimalTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("numberofCarbonAtoms"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericMultiDecimal(testcaseSheetName,numericField,31);
	}
  	
  	//end Number of carbon actoms in active
	
	//start Active Molar Mass
	@Test(priority=23)
	public void activeMolarZEROTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("activeMolarMass"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericZERO(testcaseSheetName,numericField,33);
	}
	
	@Test(priority=24)
	public void activeMolarMaxLengthTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("activeMolarMass"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericMaxLength(testcaseSheetName,numericField,34);
	}
	
	@Test(priority=25)
	public void activeMolarAlphabetsTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("activeMolarMass"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericFieldAlphabetsCheck(testcaseSheetName,numericField,35);
	}
	
	@Test(priority=26)
	public void activeMolarSpaceTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("activeMolarMass"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericSpaceCheck(testcaseSheetName,numericField,36);
	}
	
	@Test(priority=27)
	public void activeMolarMultiDecimalTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("activeMolarMass"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericMultiDecimal(testcaseSheetName,numericField,37);
		numericField.sendKeys(Keys.TAB,Keys.TAB,Keys.TAB);
	}
	
	//End Active Molar Mass
	//Start Organic Carbon (by molecular weight)
	/*@Test(priority=28)
	public void organicCarbonZEROTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("organicCarbon"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericZERO(testcaseSheetName,numericField,39);
	}
	
	@Test(priority=29)
	public void organicCarbonMaxLengthTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("organicCarbon"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericMaxLength(testcaseSheetName,numericField,40);
	}
	
	@Test(priority=30)
	public void organicCarbonAlphabetsTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("organicCarbon"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericFieldAlphabetsCheck(testcaseSheetName,numericField,41);
	}
	
	@Test(priority=31)
	public void organicCarbonSpaceTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("organicCarbon"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericSpaceCheck(testcaseSheetName,numericField,42);
	}
	
	@Test(priority=32)
	public void organicCarbonMultiDecimalTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("organicCarbon"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericMultiDecimal(testcaseSheetName,numericField,43);
	}*/
	//End Organic Carbon (by molecular weight)
	
	
	
	// Start Solubility in Water
	@Test(priority=33)
	public void solubilityWaterZEROTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("solubilityInWater"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericZERO(testcaseSheetName,numericField,45);
	}
	
	@Test(priority=34)
	public void solubilityWaterMaxLengthTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("solubilityInWater"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericMaxLength(testcaseSheetName,numericField,46);
	}
	
	@Test(priority=35)
	public void solubilityWaterAlphabetsTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("solubilityInWater"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericFieldAlphabetsCheck(testcaseSheetName,numericField,47);
	}
	
	@Test(priority=36)
	public void solubilityWaterSpaceTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("solubilityInWater"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericSpaceCheck(testcaseSheetName,numericField,48);
	}
	
	@Test(priority=37)
	public void solubilityWaterMultiDecimalTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("solubilityInWater"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericMultiDecimal(testcaseSheetName,numericField,49);
	}
	//end Solubility in Water 
	
	@Test(priority=38)
	public void APISAVE() throws InterruptedException, WriteException, IOException
	{
		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		XSSFSheet sheet = workbook.getSheet(testcaseSheetName);
		Thread.sleep(500);
		WebElement submit = driver.findElement(By.id("saveActiveIngredient")); //submitEquipmentSamplingDetails
		submit.click();
		Thread.sleep(200);
	
			WebElement getName = driver.findElement(By.name("name"));
			String getEqName = getName.getAttribute("value");
			Thread.sleep(500);
			if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Active Ingredient '"+getEqName+"' already exists!"))
			{
				String getduplicatename = driver.findElement(By.className("notify-msg")).getText();
				driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
				for(int k=5;k<1000;k++)
				{
					j.add(k);
				}
			Thread.sleep(500);
			if(getduplicatename.equalsIgnoreCase("Active Ingredient '"+getEqName+"' already exists!"))
			{
				for(Integer i:j)
				{
					getName.clear();
					Thread.sleep(200);
					getName.sendKeys(getEqName+i);
					Thread.sleep(500);
					submit.click();
					Thread.sleep(500);
					if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Active Ingredient '"+getEqName+i+"' already exists!"))
					{
						driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
						if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Active Ingredient '"+getEqName+i+"' already exists!"))
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
			String actualMSG = null;
			String emptyExpected = sheet.getRow(50).getCell(5).getStringCellValue();
			if(driver.findElements(By.className("notify-msg")).size()!=0)
			{
				actualMSG = driver.findElement(By.className("notify-msg")).getText();
			}
			if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
			{
				driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
			}
			
			if(emptyExpected.equalsIgnoreCase(actualMSG))
			{
				XSSFCell status = sheet.getRow(50).getCell(7);
				status.setCellValue("Pass"); //Print status in excel
				status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
			}else
			{
				XSSFCell status = sheet.getRow(50).getCell(7);
				status.setCellValue("Fail"); //Print status in excel
				status.setCellStyle(Utils.style(workbook, "Fail")); //for print red font
			}
			Thread.sleep(1000);
			Utils.writeTooutputFile(workbook); 
	}
	
	
	
}

