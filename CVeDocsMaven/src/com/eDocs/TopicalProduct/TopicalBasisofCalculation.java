package com.eDocs.TopicalProduct;

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

public class TopicalBasisofCalculation {
	
	private RepositoryParser parser;
	public static WebDriver driver = Constant.driver;
	String testcaseSheetName = "TopicalTC";
  
	//Minimum Daily Dose Start
	@Test(priority=48)
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
		getnumericField.NumericEmpty(testcaseSheetName,Submit,57);
	}
	
	@Test(priority=49)
	public void MinDDZEROTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("minimumDailyDose"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericZERO(testcaseSheetName,numericField,58);
	}
	
	@Test(priority=50)
	public void MinDDMaxLengthTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("minimumDailyDose"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericMaxLength(testcaseSheetName,numericField,59);
	}
	
	@Test(priority=51)
	public void MinDDAlphabetsTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("minimumDailyDose"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericFieldAlphabetsCheck(testcaseSheetName,numericField,60);
	}
	
	@Test(priority=52)
	public void MinDDSpaceTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("minimumDailyDose"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericSpaceCheck(testcaseSheetName,numericField,61);
	}
	
	@Test(priority=53)
	public void MinDDMultiDecimalTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("minimumDailyDose"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericMultiDecimal(testcaseSheetName,numericField,62);
	}
  	
	//Minimum Daily Dose End
	
	//Minimum Amount Applied Start
	
	@Test(priority=54)
	public void minAmountAppZEROTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("topicalMinimumAmountApplied"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericZERO(testcaseSheetName,numericField,64);
	}
	
	@Test(priority=55)
	public void minAmountAppMaxLengthTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("topicalMinimumAmountApplied"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericMaxLength(testcaseSheetName,numericField,65);
	}
	
	@Test(priority=56)
	public void minAmountAppAlphabetsTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("topicalMinimumAmountApplied"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericFieldAlphabetsCheck(testcaseSheetName,numericField,66);
	}
	
	@Test(priority=57)
	public void minAmountAppSpaceTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("topicalMinimumAmountApplied"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericSpaceCheck(testcaseSheetName,numericField,67);
	}
	
	@Test(priority=58)
	public void minAmountAppMultiDecimalTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("topicalMinimumAmountApplied"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericMultiDecimal(testcaseSheetName,numericField,68);
	}
	//minAmountApp End
	
	
	//min body sf start
	@Test(priority=59)
	public void DoseofActiveZEROTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("topicalMinimumBodySurfaceArea"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericZERO(testcaseSheetName,numericField,70);
	}
	
	@Test(priority=60)
	public void DoseofActiveMaxLengthTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("topicalMinimumBodySurfaceArea"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericMaxLength(testcaseSheetName,numericField,71);
	}
	
	@Test(priority=61)
	public void DoseofActiveAlphabetsTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("topicalMinimumBodySurfaceArea"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericFieldAlphabetsCheck(testcaseSheetName,numericField,72);
	}
	
	@Test(priority=62)
	public void DoseofActiveSpaceTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("topicalMinimumBodySurfaceArea"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericSpaceCheck(testcaseSheetName,numericField,73);
	}
	
	@Test(priority=63)
	public void DoseofActiveMultiDecimalTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("topicalMinimumBodySurfaceArea"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericMultiDecimal(testcaseSheetName,numericField,74);
	}
	//min body sf end
	
	//active Concen start
	@Test(priority=64)
	public void activeConcenZEROTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("activeConcentration"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericZERO(testcaseSheetName,numericField,76);
	}
	
	@Test(priority=65)
	public void activeConcenMaxLengthTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("activeConcentration"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericMaxLength(testcaseSheetName,numericField,77);
	}
	
	@Test(priority=66)
	public void activeConcenAlphabetsTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("activeConcentration"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericFieldAlphabetsCheck(testcaseSheetName,numericField,78);
	}
	
	@Test(priority=67)
	public void activeConcenSpaceTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("activeConcentration"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericSpaceCheck(testcaseSheetName,numericField,79);
	}
	
	@Test(priority=68)
	public void activeConcenMultiDecimalTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("activeConcentration"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericMultiDecimal(testcaseSheetName,numericField,80);
		Thread.sleep(500);
		numericField.sendKeys(Keys.TAB,Keys.TAB);
		Thread.sleep(500);
		//Click next button
		driver.findElement(By.cssSelector(".btn.blue-btn.continue-btn.waves-effect.gotoSolid_next3")).click();
		Thread.sleep(500);
	}
	//active Concen end
	
	
}

