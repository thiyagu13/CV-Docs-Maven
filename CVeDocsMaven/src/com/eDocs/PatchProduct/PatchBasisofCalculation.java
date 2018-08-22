package com.eDocs.PatchProduct;

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

public class PatchBasisofCalculation {
	
	private RepositoryParser parser;
	public static WebDriver driver = Constant.driver;
	String testcaseSheetName = "PatchTC";
  
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
		getnumericField.NumericEmpty(testcaseSheetName,Submit,50);
	}
	
	@Test(priority=49)
	public void MinDDZEROTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("minimumDailyDose"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericZERO(testcaseSheetName,numericField,51);
	}
	
	@Test(priority=50)
	public void MinDDMaxLengthTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("minimumDailyDose"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericMaxLength(testcaseSheetName,numericField,52);
	}
	
	@Test(priority=51)
	public void MinDDAlphabetsTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("minimumDailyDose"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericFieldAlphabetsCheck(testcaseSheetName,numericField,53);
	}
	
	@Test(priority=52)
	public void MinDDSpaceTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("minimumDailyDose"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericSpaceCheck(testcaseSheetName,numericField,54);
	}
	
	@Test(priority=53)
	public void MinDDMultiDecimalTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("minimumDailyDose"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericMultiDecimal(testcaseSheetName,numericField,55);
	}
  	
	//Minimum Daily Dose End
	
	//Active Concen Applied Start
	@Test(priority=54)
	public void activeConcenZEROTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("activeConcentration"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericZERO(testcaseSheetName,numericField,57);
	}
	
	@Test(priority=55)
	public void activeConcenMaxLengthTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("activeConcentration"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericMaxLength(testcaseSheetName,numericField,58);
	}
	
	@Test(priority=56)
	public void activeConcenAlphabetsTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("activeConcentration"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericFieldAlphabetsCheck(testcaseSheetName,numericField,59);
	}
	
	@Test(priority=57)
	public void activeConcenSpaceTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("activeConcentration"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericSpaceCheck(testcaseSheetName,numericField,60);
	}
	
	@Test(priority=58)
	public void activeConcenMultiDecimalTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("activeConcentration"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericMultiDecimal(testcaseSheetName,numericField,61);
	}
	//activeConcen End
	
	
	//min daily dose per patch start
	@Test(priority=59)
	public void DoseofActiveZEROTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("doseOfActive"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericZERO(testcaseSheetName,numericField,63);
	}
	
	@Test(priority=60)
	public void DoseofActiveMaxLengthTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("doseOfActive"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericMaxLength(testcaseSheetName,numericField,64);
	}
	
	@Test(priority=61)
	public void DoseofActiveAlphabetsTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("doseOfActive"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericFieldAlphabetsCheck(testcaseSheetName,numericField,65);
	}
	
	@Test(priority=62)
	public void DoseofActiveSpaceTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("doseOfActive"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericSpaceCheck(testcaseSheetName,numericField,66);
	}
	
	@Test(priority=63)
	public void DoseofActiveMultiDecimalTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("doseOfActive"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericMultiDecimal(testcaseSheetName,numericField,67);
	}
	//min daily dose per patch end
	
	
	//Min no of paches worn start
	@Test(priority=64)
	public void minPatchWornZEROTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("minNoOfDoses"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericZERO(testcaseSheetName,numericField,69);
	}
	
	@Test(priority=65)
	public void minPatchWornMaxLengthTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("minNoOfDoses"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericMaxLength(testcaseSheetName,numericField,70);
	}
	
	@Test(priority=66)
	public void minPatchWornAlphabetsTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("minNoOfDoses"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericFieldAlphabetsCheck(testcaseSheetName,numericField,71);
	}
	
	@Test(priority=67)
	public void minPatchWornSpaceTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("minNoOfDoses"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericSpaceCheck(testcaseSheetName,numericField,72);
	}
	
	@Test(priority=68)
	public void minPatchWornMultiDecimalTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("minNoOfDoses"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericMultiDecimal(testcaseSheetName,numericField,73);
		Thread.sleep(500);
		numericField.sendKeys(Keys.TAB,Keys.TAB);
		Thread.sleep(500);
		//Click next button
		driver.findElement(By.cssSelector(".btn.blue-btn.continue-btn.waves-effect.gotoSolid_next3")).click();
		Thread.sleep(500);
	}
	//active Concen end
	
	
}

