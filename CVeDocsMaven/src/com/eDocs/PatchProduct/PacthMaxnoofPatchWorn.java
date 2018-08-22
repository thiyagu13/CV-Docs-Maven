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

public class PacthMaxnoofPatchWorn {
	
	private RepositoryParser parser;
	public static WebDriver driver = Constant.driver;
	String testcaseSheetName = "PatchTC";
  
	/*@Test(priority=26)
	public void NumericEmptyTest() throws InterruptedException, WriteException, IOException
	{
		WebElement Submit = driver.findElement(By.name("next"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericEmpty(testcaseSheetName,Submit,26);
	}*/
	
	@Test(priority=32)
	public void maxPachWornSFZEROTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("patchesWorn"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericZERO(testcaseSheetName,numericField,33);
	}
	
	@Test(priority=33)
	public void maxPachWornMaxLengthTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("patchesWorn"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.NumericMaxLength(testcaseSheetName,numericField,34);
	}
	
	@Test(priority=34)
	public void maxPachWornAlphabetsTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("patchesWorn"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericFieldAlphabetsCheck(testcaseSheetName,numericField,35);
	}
	
	@Test(priority=35)
	public void maxPachWornSpaceTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("patchesWorn"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericSpaceCheck(testcaseSheetName,numericField,36);
	}

	@Test(priority=36)
	public void maxPachWornMultiDecimalTest() throws InterruptedException, WriteException, IOException
	{
		WebElement numericField = driver.findElement(By.name("patchesWorn"));
		NumericValidation getnumericField = new NumericValidation();
		getnumericField.numericMultiDecimal(testcaseSheetName,numericField,37);
		numericField.clear();
		numericField.sendKeys("50");
		numericField.sendKeys(Keys.TAB);
		Thread.sleep(500);	
	}
  	
	
	
}

