package com.eDocs.EquipmentTrain;

import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.eDocs.CommonValidation.AlphaNumericValidation;
import com.eDocs.Utils.Constant;
import com.eDocs.Utils.RepositoryParser;
import jxl.write.WriteException;

public class EquipmentTrainName {

	private RepositoryParser parser;
	public static WebDriver driver = Constant.driver;
	String testcaseSheetName = "EquipmentTRAINTC";
/*
	@BeforeClass
	public void setUp() throws IOException {
		driver.get(Constant.URL);
		parser = new RepositoryParser(
				"C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\Equipment.properties");
	}

	@Test(priority = 1)
	public void Login() throws InterruptedException {
		Thread.sleep(1000);
		WebElement username = driver.findElement(By.id("username"));
		WebElement password = driver.findElement(By.id("password"));
		username.sendKeys(Constant.siteusername);
		Thread.sleep(500);
		password.sendKeys(Constant.sitepassword);
		Thread.sleep(500);
		driver.findElement(By.id("loginsubmit")).click();
		Thread.sleep(1000);
		if (driver.findElements(By.id("forcelogin")).size() != 0) {
			// Force Login
			Thread.sleep(1000);
			driver.findElement(By.id("forcelogin")).click();
		}
		Thread.sleep(1500);
		driver.get(Constant.URL + "/equipment-train");
		Thread.sleep(1500);
		driver.findElement(By.id("addTrain")).click();
		Thread.sleep(1500);
		// WebElement alphanumericField = driver.findElement(By.name("name"));
		// alphanumericField.sendKeys("test");
	}
*/
	@Test(priority = 66)
	public void nameEmpty() throws IOException, WriteException, InterruptedException {
		parser = new RepositoryParser(
				"C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\Equipment.properties");
		
		Thread.sleep(500);
		driver.get(Constant.URL + "/equipment-train");
		Thread.sleep(500);
		driver.findElement(By.id("addTrain")).click();
		Thread.sleep(500);
		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.id("saveEquipmentTrainDetails"));
		textField.AlphaNumericEmpty(testcaseSheetName, Constant.EXCEL_PATH_Result, Submit, 6);
	}

	@Test(priority = 67)
	public void nameMaxFieldLength() throws IOException, WriteException, InterruptedException {
		WebElement alphanumericField = driver.findElement(By.name("name"));
		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.MaxLengthCheck(testcaseSheetName, alphanumericField, 8);
	}

	@Test(priority = 68)
	public void nameSpecialChar() throws IOException, WriteException, InterruptedException // check mandatory symbol and
																							// error msg
	{
		WebElement alphanumericField = driver.findElement(By.name("name"));
		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.nameSpecialCharCheck(testcaseSheetName, alphanumericField, 9);
	}

	@Test(priority = 69)
	public void nameBeginingSpaceCheck() throws IOException, WriteException, InterruptedException // check mandatory
																									// symbol and error
																									// msg
	{
		WebElement alphanumericField = driver.findElement(By.name("name"));
		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.nameBeginingSpaceCheck(testcaseSheetName, alphanumericField, 10);
	}

}
