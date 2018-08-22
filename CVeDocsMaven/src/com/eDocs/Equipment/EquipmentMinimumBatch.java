package com.eDocs.Equipment;

import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import com.eDocs.CommonValidation.NumericValidation;
import com.eDocs.Utils.Constant;
import com.eDocs.Utils.RepositoryParser;
import jxl.write.WriteException;

public class EquipmentMinimumBatch {
	
	private RepositoryParser parser;
	public static WebDriver driver = Constant.driver;
	String testcaseSheetName = "EquipmentTC";
	
  
	@Test(priority=21)
	public void NumericEmptyTest() throws InterruptedException, WriteException, IOException
	{
		// For min BAtch Empty check - Pref transer to be filled (Bcz previous field)
		//Select prefTransfer =  new Select(driver.findElement(By.id("preferentialTransferOption")));
		//prefTransfer.selectByValue("2"); // select no
		NumericValidation getnumericField = new NumericValidation();
		WebElement Submit = driver.findElement(By.id("saveEquipment"));
		getnumericField.NumericEmpty(testcaseSheetName,Submit,31);
	}
	
	@Test(priority=22)
	public void NumericZEROTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.id("minBatch"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericZERO(testcaseSheetName,numericField,32);
	}
	
	@Test(priority=23)
	public void NumericMaxLengthTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.id("minBatch"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericMaxLength(testcaseSheetName,numericField,33);
	}
	
	@Test(priority=24)
	public void numericFieldAlphabetsCheckTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.id("minBatch"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericFieldAlphabetsCheck(testcaseSheetName,numericField,34);
	}
	
	@Test(priority=25)
	public void numericSpaceCheckTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.id("minBatch"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericSpaceCheck(testcaseSheetName,numericField,35);
	}
	
	@Test(priority=26)
	public void numericMultiDecimalTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.id("minBatch"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericMultiDecimal(testcaseSheetName,numericField,36);
	}
	
	
	
	
	
}

