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

public class ProductMaxDailyDose {
	
	private RepositoryParser parser;
	public static WebDriver driver = Constant.driver;
	String testcaseSheetName = "ProductTC";
  
	@Test(priority=19)
	public void NumericEmptyTest() throws InterruptedException, WriteException, IOException
	{
		WebElement Submit = driver.findElement(By.name("next"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericEmpty(testcaseSheetName,Submit,26);
	}
	
	@Test(priority=20)
	public void NumericZEROTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("maxDailyDoseInput"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericZERO(testcaseSheetName,numericField,27);
	}
	
	@Test(priority=21)
	public void NumericMaxLengthTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("maxDailyDoseInput"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericMaxLength(testcaseSheetName,numericField,28);
	}
	
	@Test(priority=22)
	public void numericFieldAlphabetsCheckTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("maxDailyDoseInput"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericFieldAlphabetsCheck(testcaseSheetName,numericField,29);
	}
	
	@Test(priority=23)
	public void numericSpaceCheckTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("maxDailyDoseInput"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericSpaceCheck(testcaseSheetName,numericField,30);
	}
	
	@Test(priority=24)
	public void numericMultiDecimalTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("maxDailyDoseInput"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericMultiDecimal(testcaseSheetName,numericField,31);
		numericField.clear();
		numericField.sendKeys("50");
	}
  	
	
	
}

