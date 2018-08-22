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

public class ProductMaxDose {
	
	private RepositoryParser parser;
	public static WebDriver driver = Constant.driver;
	String testcaseSheetName = "ProductTC";
  
	/*@Test(priority=13)
	public void NumericEmptyTest() throws InterruptedException, WriteException, IOException
	{
		WebElement Submit = driver.findElement(By.name("next"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericEmpty(Submit,18);
	}*/
	
	@Test(priority=25)
	public void NumericZEROTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("maxNoOfDosesInput"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericZERO(testcaseSheetName,numericField,33);
	}
	
	@Test(priority=26)
	public void NumericMaxLengthTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("maxNoOfDosesInput"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericMaxLength(testcaseSheetName,numericField,34);
	}
	
	@Test(priority=27)
	public void numericFieldAlphabetsCheckTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("maxNoOfDosesInput"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericFieldAlphabetsCheck(testcaseSheetName,numericField,35);
	}
	
	@Test(priority=28)
	public void numericSpaceCheckTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("maxNoOfDosesInput"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericSpaceCheck(testcaseSheetName,numericField,36);
	}
	
	@Test(priority=29)
	public void numericMultiDecimalTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("maxNoOfDosesInput"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericMultiDecimal(testcaseSheetName,numericField,37);
		numericField.clear();
		numericField.sendKeys("3");
		numericField.sendKeys(Keys.TAB);
		Thread.sleep(300);
		WebElement MaxDD = driver.findElement(By.name("maxDailyDoseInput"));
		String maxValue = MaxDD.getAttribute("value");
		System.out.println("Max DD: "+maxValue);
		if(maxValue.equalsIgnoreCase(""))
		{
			driver.findElement(By.name("productDoseInput")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ARROW_DOWN,Keys.ENTER);
		}else
		{
			System.out.println("Max Daily Dose already Calculated");
		}
		
		
	}
  	
	
	
	
}

