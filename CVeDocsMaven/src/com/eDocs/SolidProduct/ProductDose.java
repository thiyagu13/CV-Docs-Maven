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

public class ProductDose {
	
	private RepositoryParser parser;
	public static WebDriver driver = Constant.driver;
	String testcaseSheetName = "ProductTC";
	
  
	@Test(priority=13)
	public void productDoseEmpty() throws InterruptedException, WriteException, IOException
	{
		WebElement Submit = driver.findElement(By.name("next"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericEmpty(testcaseSheetName,Submit,20);
	}
	
	@Test(priority=14)
	public void productDoseZERO() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("productDoseInput"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericZERO(testcaseSheetName,numericField,21);
	}
	
	@Test(priority=15)
	public void productDoseMaxLength() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("productDoseInput"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericMaxLength(testcaseSheetName,numericField,22);
	}
	
	@Test(priority=16)
	public void productDoseAlphabetsTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("productDoseInput"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericFieldAlphabetsCheck(testcaseSheetName,numericField,23);
	}
	
	@Test(priority=17)
	public void productDoseSpaceTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("productDoseInput"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericSpaceCheck(testcaseSheetName,numericField,24);
	}
	
	@Test(priority=18)
	public void productDoseMultiDecimalTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("productDoseInput"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericMultiDecimal(testcaseSheetName,numericField,25);
		numericField.clear();
		numericField.sendKeys("30");
	}
  	
	
	
}

