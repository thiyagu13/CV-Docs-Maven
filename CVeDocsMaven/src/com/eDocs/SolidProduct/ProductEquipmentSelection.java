package com.eDocs.SolidProduct;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.eDocs.CommonValidation.AlphaNumericValidation;
import com.eDocs.CommonValidation.NumericValidation;
import com.eDocs.Utils.Constant;

import jxl.write.WriteException;

public class ProductEquipmentSelection {
	
	public static WebDriver driver = Constant.driver;
	public String password = Constant.sitepassword;
	String testcaseSheetName = "ProductTC";
	
	@Test(priority=65)
	public void EquipmentSetSelectionOptionEmpty() throws InterruptedException, WriteException, IOException
	{
		WebElement Submit = driver.findElement(By.cssSelector(".btn.blue-btn.continue-btn.waves-effect.gotoSolid_next5"));
		AlphaNumericValidation textField = new AlphaNumericValidation();
  		textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,Submit,75);
		//NumericValidation getnumericField = new NumericValidation();
		//getnumericField.NumericEmpty(testcaseSheetName,Submit,75);
	}
	
	@Test(priority=66)
	public void EquipmentSetEmpty() throws InterruptedException, WriteException, IOException
	{
		driver.findElement(By.id("selection2")).click(); 
		Thread.sleep(500);
		WebElement Submit = driver.findElement(By.cssSelector(".btn.blue-btn.continue-btn.waves-effect.gotoSolid_next5"));
		AlphaNumericValidation textField = new AlphaNumericValidation();
  		textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,Submit,76);
	}
	
	@Test(priority=67)
	public void productSave() throws InterruptedException, WriteException, IOException
	{
		Thread.sleep(500);
		driver.findElement(By.id("selection2")).sendKeys(Keys.TAB,Keys.TAB,Keys.ENTER,"Eq selene",Keys.ENTER);
		Thread.sleep(500);
		WebElement submit = driver.findElement(By.cssSelector(".btn.blue-btn.continue-btn.waves-effect.gotoSolid_next5")); //submitEquipmentSamplingDetails
		Thread.sleep(200);
		System.out.println("---:"+submit.getText());
		if(submit.getText().equalsIgnoreCase("Submit"))
		{
			System.out.println("No Custom loop");
			WebElement s = driver.findElement(By.cssSelector(".btn.blue-btn.continue-btn.waves-effect.gotoSolid_next5"));
			Thread.sleep(500);
			NumericValidation getnumericField = new NumericValidation();
			getnumericField.NumericEmpty(testcaseSheetName,s,77);
		}else
		{
			submit.click();
			System.out.println("Custom loop");
			Thread.sleep(1000);
			for(int i=0;i<6;i++)
			{
				System.out.println("i--->"+i);
				String custom ="customFieldInput_";
				Thread.sleep(500);
				if(driver.findElements(By.id(custom+i)).size()!=0)
				{
					Thread.sleep(1000);
					if(driver.findElement(By.id(custom+i)).getAttribute("type").equals("text"))
					{
						System.out.println("Text bx");
						Thread.sleep(500);
						driver.findElement(By.id(custom+i)).sendKeys("Test");
					}
					if(driver.findElement(By.id(custom+i)).getAttribute("type").equals("select-one"))
					{
						System.out.println("DropDown");
						Thread.sleep(500);
						WebElement select = driver.findElement(By.id(custom+i));
						Select selectcustom = new Select(select);
						selectcustom.selectByIndex(1); 
					}
				}
			}
					//click save button in custom fields
					WebElement ss = driver.findElement(By.cssSelector(".btn.btn-default.continue-btn.waves-effect.saveSolidLiquidInhalant"));
					NumericValidation getnumericField = new NumericValidation();
					getnumericField.NumericEmpty(testcaseSheetName,ss,77);
		}
		Thread.sleep(1000);
	}
	
	
	
	
	
}
