package com.eDocs.SolidProduct;

import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import com.eDocs.CommonValidation.NumericValidation;
import com.eDocs.Utils.Constant;
import com.eDocs.Utils.RepositoryParser;
import jxl.write.WriteException;

public class ProductBasisofCalculation {
	
	private RepositoryParser parser;
	public static WebDriver driver = Constant.driver;
	String testcaseSheetName = "ProductTC";
  
	//Minimum Daily Dose Start
	@Test(priority=42)
	public void MinDDEmptyTest() throws InterruptedException, WriteException, IOException
	{
		Thread.sleep(500);
		if(driver.findElement(By.cssSelector(".filled-in.healthCheck")).isSelected()==false)
		{
			driver.findElement(By.cssSelector(".filled-in.healthCheck")).click();	
		}
		if(driver.findElement(By.cssSelector(".filled-in.doseCheck")).isSelected()==false)
		{
			driver.findElement(By.cssSelector(".filled-in.doseCheck")).click();	
		}
		WebElement Submit = driver.findElement(By.cssSelector(".btn.blue-btn.continue-btn.waves-effect.gotoSolid_next3"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericEmpty(testcaseSheetName,Submit,50);
	}
	
	@Test(priority=43)
	public void MinDDZEROTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("minimumDailyDose"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericZERO(testcaseSheetName,numericField,51);
	}
	
	@Test(priority=44)
	public void MinDDMaxLengthTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("minimumDailyDose"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericMaxLength(testcaseSheetName,numericField,52);
	}
	
	@Test(priority=45)
	public void MinDDAlphabetsTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("minimumDailyDose"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericFieldAlphabetsCheck(testcaseSheetName,numericField,53);
	}
	
	@Test(priority=46)
	public void MinDDSpaceTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("minimumDailyDose"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericSpaceCheck(testcaseSheetName,numericField,54);
	}
	
	@Test(priority=47)
	public void MinDDMultiDecimalTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("minimumDailyDose"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericMultiDecimal(testcaseSheetName,numericField,55);
	}
  	
	//Minimum Daily Dose End
	
	//Active Concentration Start
	
	@Test(priority=48)
	public void ActiveConcenZEROTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("activeConcentration"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericZERO(testcaseSheetName,numericField,57);
	}
	
	@Test(priority=49)
	public void ActiveConcenMaxLengthTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("activeConcentration"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericMaxLength(testcaseSheetName,numericField,58);
	}
	
	@Test(priority=50)
	public void ActiveConcenAlphabetsTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("activeConcentration"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericFieldAlphabetsCheck(testcaseSheetName,numericField,59);
	}
	
	@Test(priority=51)
	public void ActiveConcenSpaceTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("activeConcentration"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericSpaceCheck(testcaseSheetName,numericField,60);
	}
	
	@Test(priority=52)
	public void ActiveConcenMultiDecimalTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("activeConcentration"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericMultiDecimal(testcaseSheetName,numericField,61);
	}
	//Active Concentration End
	
	
	//Dose of Active start
	@Test(priority=53)
	public void DoseofActiveZEROTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("doseOfActive"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericZERO(testcaseSheetName,numericField,63);
	}
	
	@Test(priority=54)
	public void DoseofActiveMaxLengthTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("doseOfActive"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericMaxLength(testcaseSheetName,numericField,64);
	}
	
	@Test(priority=55)
	public void DoseofActiveAlphabetsTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("doseOfActive"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericFieldAlphabetsCheck(testcaseSheetName,numericField,65);
	}
	
	@Test(priority=56)
	public void DoseofActiveSpaceTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("doseOfActive"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericSpaceCheck(testcaseSheetName,numericField,66);
	}
	
	@Test(priority=57)
	public void DoseofActiveMultiDecimalTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("doseOfActive"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericMultiDecimal(testcaseSheetName,numericField,67);
	}
	//Dose of Active end
	
	//Minimum no of Doses start
	@Test(priority=58)
	public void MinnoofDoseZEROTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("minNoOfDoses"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericZERO(testcaseSheetName,numericField,69);
	}
	
	@Test(priority=59)
	public void MinnoofDoseMaxLengthTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("minNoOfDoses"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericMaxLength(testcaseSheetName,numericField,70);
	}
	
	@Test(priority=60)
	public void MinnoofDoseAlphabetsTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("minNoOfDoses"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericFieldAlphabetsCheck(testcaseSheetName,numericField,71);
	}
	
	@Test(priority=61)
	public void MinnoofDoseSpaceTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("minNoOfDoses"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericSpaceCheck(testcaseSheetName,numericField,72);
	}
	
	@Test(priority=62)
	public void MinnoofDoseMultiDecimalTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("minNoOfDoses"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericMultiDecimal(testcaseSheetName,numericField,73);
		Thread.sleep(1000);
		numericField.sendKeys(Keys.TAB,Keys.TAB);
		Thread.sleep(500);
		//Click next button
		driver.findElement(By.cssSelector(".btn.blue-btn.continue-btn.waves-effect.gotoSolid_next3")).click();
		Thread.sleep(500);
	}
	//Minimum no of Doses end
	
	
}

