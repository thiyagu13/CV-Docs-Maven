package com.eDocs.Utility;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByCssSelector;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.eDocs.CommonValidation.AlphaNumericValidation;
import com.eDocs.CommonValidation.NumericValidation;
import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Message;
import com.eDocs.Utils.RepositoryParser;
import com.eDocs.Utils.Utils;

import jxl.write.WriteException;

public class UtilityCreate {

	private RepositoryParser parser;
	public static WebDriver driver = Constant.driver;
	String testcaseSheetName = "UtilityTC";

/*	@BeforeClass
	public void setUp() throws IOException {
		driver.get(Constant.URL);
		parser = new RepositoryParser(
				"C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\Product.properties");
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
	}*/

	@Test(priority = 1)
	//@Test
	public void nameEmpty() throws IOException, WriteException, InterruptedException {
		Thread.sleep(1000);
		driver.get(Constant.URL + "/utility");
		Thread.sleep(1500);
		driver.findElement(By.id("addUtility")).click();
		Thread.sleep(1500);
		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.id("utilitySubmit"));
		textField.AlphaNumericEmpty(testcaseSheetName, Constant.EXCEL_PATH, Submit, 6);
	}

	@Test(priority = 2)
	//@Test
	public void nameMaxFieldLength() throws IOException, WriteException, InterruptedException {
		WebElement alphanumericField = driver.findElement(By.name("name"));
		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.MaxLengthCheck(testcaseSheetName, alphanumericField, 8);
	}

	@Test(priority = 3)
	//@Test
	public void nameSpecialChar() throws IOException, WriteException, InterruptedException // check mandatory symbol and
																							// error msg
	{
		WebElement alphanumericField = driver.findElement(By.name("name"));
		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.nameSpecialCharCheck(testcaseSheetName, alphanumericField, 9);
	}

	@Test(priority = 4)
	//@Test
	public void nameBeginingSpaceCheck() throws IOException, WriteException, InterruptedException // check mandatory
																									// symbol and error
																									// msg
	{
		WebElement alphanumericField = driver.findElement(By.name("name"));
		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.nameBeginingSpaceCheck(testcaseSheetName, alphanumericField, 10);
	}
	// end name

	@Test(priority = 5)
	//@Test
	public void locationNameEmpty() throws IOException, WriteException, InterruptedException // check mandatory symbol
																								// and error msg
	{
		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.id("utilitySubmit"));
		textField.AlphaNumericEmpty(testcaseSheetName, Constant.EXCEL_PATH_Result, Submit, 11);
		// Select location
		WebElement alphanumericField = driver.findElement(By.name("name"));
		alphanumericField.sendKeys(Keys.TAB, Keys.ENTER, Keys.ENTER);
	}

	@Test(priority = 6)
	//@Test
	public void identificationNoEmpty() throws IOException, WriteException, InterruptedException // check mandatory
																								// symbol and error msg
	{
		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.id("utilitySubmit"));
		textField.AlphaNumericEmpty(testcaseSheetName, Constant.EXCEL_PATH_Result, Submit, 12);
	}

	@Test(priority = 7)
	//@Test
	public void identificationNoMaxLength() throws IOException, WriteException, InterruptedException 
	{
		Thread.sleep(500);
		WebElement alphanumericField = driver.findElement(By.name("serialOrAssetNo"));
		AlphaNumericValidation getAlphanumericField = new AlphaNumericValidation();
		getAlphanumericField.MaxLengthCheck(testcaseSheetName, alphanumericField, 13);
		alphanumericField.clear();
		alphanumericField.sendKeys("111");
		alphanumericField.sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
		alphanumericField.sendKeys(Keys.TAB,Keys.TAB,Keys.ENTER,Keys.ENTER);
	}

	@Test(priority = 8)
	//@Test
	public void utilitySave() throws IOException, WriteException, InterruptedException 
	{
		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		XSSFSheet sheet = workbook.getSheet(testcaseSheetName);

		WebElement Name = driver.findElement(By.name("name"));
		String getEqName = Name.getAttribute("value");

		WebElement serialNo = driver.findElement(By.name("serialOrAssetNo"));
		String getSerialNo = serialNo.getAttribute("value");

		WebElement submit = driver.findElement(By.id("UtilitySubmit"));
		submit.click();
		Thread.sleep(1000);

		Set<Integer> j = new HashSet<>(); // to store no of digits for iterate calculation title
		for (int k = 5; k < 1000; k++) {
			j.add(k);
		}

		if (driver.findElements(By.className("notify-msg")).size() != 0
				&& driver.findElement(By.className("notify-msg")).getText()
						.equalsIgnoreCase("Utility '" + getEqName + "' already exists!")) {
			String getduplicatename = driver.findElement(By.className("notify-msg")).getText();
			driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();

			Thread.sleep(500);
			if (getduplicatename.equalsIgnoreCase("Utility '" + getEqName + "' already exists!")) {
				for (Integer i : j) {
					Name.clear();
					Thread.sleep(200);
					Name.sendKeys(getEqName + i);
					Thread.sleep(500);
					submit.click();
					Thread.sleep(500);
					if (driver.findElements(By.className("notify-msg")).size() != 0
							&& driver.findElement(By.className("notify-msg")).getText()
									.equalsIgnoreCase("Utility '" + getEqName + i + "' already exists!")) {
						driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
						if (driver.findElements(By.className("notify-msg")).size() != 0
								&& driver.findElement(By.className("notify-msg")).getText()
										.equalsIgnoreCase("Utility '" + getEqName + i + "' already exists!")) {
							continue;
						}
					}
					System.out.println("Not duplicate so break the loop");
					break;
				}
			}
		}

		// if Duplicate Document ID
		// Document with same ID already exists
		if (driver.findElements(By.className("notify-msg")).size() != 0	&& driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Equipment Identification Number '" + getSerialNo + "' already exists!")) 
		{
			String getduplicateID = driver.findElement(By.className("notify-msg")).getText();
			driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
			System.out.println("getduplicatename: " + getduplicateID);
			
			Set<Integer> m = new HashSet<>(); // to store no of digits for iterate calculation title
			for (int k = 5; k < 1000; k++) 
			{
				m.add(k);
			}
			
			if (getduplicateID.equalsIgnoreCase("Equipment Identification Number '" + getSerialNo + "' already exists!")) 
			{
				for (Integer n : m) 
				{
					System.out.println("Test");
					driver.findElement(By.name("serialOrAssetNo")).clear();
					Thread.sleep(200);
					System.out.println(getSerialNo + n);
					driver.findElement(By.name("serialOrAssetNo")).sendKeys(getSerialNo + n);
					Thread.sleep(500);
					// driver.findElement(parser.getbjectLocator("APIsubmit")).click();
					submit.click();
					Thread.sleep(500);
					if (driver.findElements(By.className("notify-msg")).size() != 0
							&& driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase(
									"Equipment Identification Number '" + getSerialNo + n + "' already exists!")) {
						System.out.println("dfgdfg");
						String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
						driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
						if (driver.findElements(By.className("notify-msg")).size() != 0
								&& driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase(
										"Equipment Identification Number '" + getSerialNo + n + "' already exists!")) {
							continue;
						}
					}
					System.out.println("Not duplicate so break the loop");
					break;
				}
			}

		}

		// custom loop
		Thread.sleep(500);
		if (driver.findElement(By.id("saveCustomDetails")).isDisplayed()) {
			System.out.println("Text: " + driver.findElement(By.id("saveCustomDetails")).getText());
			System.out.println("Custom loop");
			Thread.sleep(1000);
			for (int i = 0; i < 6; i++) {
				System.out.println("i--->" + i);
				String custom = "customFieldInput_";
				Thread.sleep(500);
				if (driver.findElements(By.id(custom + i)).size() != 0) {
					Thread.sleep(1000);
					if (driver.findElement(By.id(custom + i)).getAttribute("type").equals("text")) {
						System.out.println("Text bx");
						Thread.sleep(500);
						driver.findElement(By.id(custom + i)).sendKeys("Test");
					}
					if (driver.findElement(By.id(custom + i)).getAttribute("type").equals("select-one")) {
						System.out.println("DropDown");
						Thread.sleep(500);
						WebElement select = driver.findElement(By.id(custom + i));
						Select selectcustom = new Select(select);
						selectcustom.selectByIndex(1);
					}
				}
			}
			driver.findElement(By.id("saveCustomDetails")).click();

		}

		String actualMSG = null;
		String emptyExpected = sheet.getRow(14).getCell(5).getStringCellValue();
		if (driver.findElements(By.className("notify-msg")).size() != 0) {
			actualMSG = driver.findElement(By.className("notify-msg")).getText();
		}
		if (driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size() != 0) {
			driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
		}

		if (emptyExpected.equalsIgnoreCase(actualMSG)) {
			XSSFCell status = sheet.getRow(14).getCell(7);
			status.setCellValue("Pass"); // Print status in excel
			status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
		} else {
			XSSFCell status = sheet.getRow(14).getCell(7);
			status.setCellValue("Fail"); // Print status in excel
			status.setCellStyle(Utils.style(workbook, "Fail")); // for print red font
		}
		Thread.sleep(1000);
		Utils.writeTooutputFile(workbook);
	}

}
