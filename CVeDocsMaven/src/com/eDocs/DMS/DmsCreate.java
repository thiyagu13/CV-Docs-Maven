package com.eDocs.DMS;

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

public class DmsCreate {

	public static WebDriver driver = Constant.driver;
	String testcaseSheetName = "DMSTC";
	static String documentPath = "C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\Test Data\\Cleaning-Validation-Manual.pdf";

	/*@BeforeClass
	public void setUp() throws IOException 
	{
		driver.get(Constant.URL);
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

	@Test(priority = 10)
	//@Test
	public void nameEmpty() throws IOException, WriteException, InterruptedException {
		Thread.sleep(1000);
		driver.get(Constant.URL + "/documents");
		Thread.sleep(1500);
		driver.findElement(By.id("addFileModalbtn")).click();
		Thread.sleep(1500);
		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.id("addFileType"));
		textField.AlphaNumericEmpty(testcaseSheetName, Constant.EXCEL_PATH_Result, Submit, 6);
	}

	@Test(priority = 11)
	//@Test
	public void nameMaxFieldLength() throws IOException, WriteException, InterruptedException {
		WebElement alphanumericField = driver.findElement(By.name("add_file_doc_name"));
		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.MaxLengthCheck(testcaseSheetName, alphanumericField, 8);
	}

	@Test(priority = 12)
	//@Test
	public void nameSpecialChar() throws IOException, WriteException, InterruptedException // check mandatory symbol and
																							// error msg
	{
		WebElement alphanumericField = driver.findElement(By.name("add_file_doc_name"));
		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.nameSpecialCharCheck(testcaseSheetName, alphanumericField, 9);
	}

	@Test(priority = 13)
	public void nameBeginingSpaceCheck() throws IOException, WriteException, InterruptedException 
	{
		WebElement alphanumericField = driver.findElement(By.name("add_file_doc_name"));
		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.nameBeginingSpaceCheck(testcaseSheetName, alphanumericField, 10);
	}
	// end name

	@Test(priority = 14)
	//@Test
	public void documentTypeEmpty() throws IOException, WriteException, InterruptedException 
	{
		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.id("addFileType"));
		textField.AlphaNumericEmpty(testcaseSheetName, Constant.EXCEL_PATH_Result, Submit, 11);
		// Select location
		WebElement alphanumericField = driver.findElement(By.name("add_file_doc_name"));
		alphanumericField.sendKeys(Keys.TAB, Keys.ENTER, Keys.ENTER);
	}

	@Test(priority = 15)
	//@Test
	public void documentIDEmpty() throws IOException, WriteException, InterruptedException 
	{
		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.id("addFileType"));
		textField.AlphaNumericEmpty(testcaseSheetName, Constant.EXCEL_PATH_Result, Submit, 12);
	}
	
	@Test(priority = 16)
	//@Test
	public void documentIDMaxFieldLength() throws IOException, WriteException, InterruptedException {
		WebElement alphanumericField = driver.findElement(By.name("add_file_doc_id"));
		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.MaxLengthCheck(testcaseSheetName, alphanumericField, 13);
		alphanumericField.clear();
	}
	@Test(priority = 17)
	//@Test
	public void ducumentIDSpaceatBegining() throws IOException, WriteException, InterruptedException 
	{
		WebElement alphanumericField = driver.findElement(By.name("add_file_doc_id"));
		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.nameBeginingSpaceCheck(testcaseSheetName, alphanumericField, 14);
		alphanumericField.sendKeys(Keys.TAB);
	}
	
	@Test(priority = 18)
	//@Test
	public void moduleEmpty() throws IOException, WriteException, InterruptedException 
	{
		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.id("addFileType"));
		textField.AlphaNumericEmpty(testcaseSheetName, Constant.EXCEL_PATH_Result, Submit, 15);
		// Select location
		WebElement alphanumericField = driver.findElement(By.name("add_file_doc_id"));
		alphanumericField.sendKeys(Keys.TAB, Keys.ENTER,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ENTER);
	}
	
	
	
	@Test(priority = 19)
	//@Test
	public void uploadDocEmpty() throws IOException, WriteException, InterruptedException 
	{
		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.id("addFileType"));
		textField.AlphaNumericEmpty(testcaseSheetName, Constant.EXCEL_PATH_Result, Submit, 16);
		//Upload Document
		driver.findElement(By.name("addfileDocUpload")).sendKeys(documentPath);
	}
	
	
	@Test(priority = 20)
	//@Test
	public void documentSave() throws IOException, WriteException, InterruptedException 
	{
		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		XSSFSheet sheet = workbook.getSheet(testcaseSheetName);

		WebElement nameField = driver.findElement(By.name("add_file_doc_name"));
		String Name = nameField.getAttribute("value");

		WebElement document = driver.findElement(By.name("add_file_doc_id"));
		String documentIDCREATE = document.getAttribute("value");

		WebElement submit = driver.findElement(By.id("addFileType"));
		submit.click();
		Thread.sleep(1500);
			
		Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
		for(int k=5;k<1000;k++)
		{
			j.add(k);
		}
		
		//if duplicate equipment name
		if( driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Document with same Name already exists"))
		{
			String getduplicatename = driver.findElement(By.className("notify-msg")).getText();
			driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
		
		Thread.sleep(500);
		if(getduplicatename.equals("Document with same Name already exists"))
		{
			for(Integer i:j)
			{
				//driver.findElement(parser.getbjectLocator("ActiveIngredientName")).clear();
				nameField.clear();
				Thread.sleep(200);
				nameField.sendKeys(Name+i);
				//driver.findElement(parser.getbjectLocator("ActiveIngredientName")).sendKeys(Name+i);
				Thread.sleep(500);
				submit.click();
				//driver.findElement(parser.getbjectLocator("APIsubmit")).click();
				Thread.sleep(500);
				if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Document with same Name already exists"))
				{
					String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
					System.out.println("Name duplicate: "+nameduplicate);
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
					if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Document with same Name already exists"))
					{
						continue;
					}
				}
						System.out.println("Not duplicate so break the loop");
						break;
				}
			}
		}
		
		//if Duplicate Document ID
		//Document with same ID already exists 
		if( driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Document with same ID already exists"))
		{
			String getduplicateID = driver.findElement(By.className("notify-msg")).getText();
			driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
		System.out.println("getduplicatename: "+getduplicateID);
		if(getduplicateID.equalsIgnoreCase("Document with same ID already exists"))
		{
			for(Integer i:j)
			{
				driver.findElement(By.name("add_file_doc_id")).clear();
				Thread.sleep(200);
				driver.findElement(By.name("add_file_doc_id")).sendKeys(documentIDCREATE+i);
				Thread.sleep(500);
				//driver.findElement(parser.getbjectLocator("APIsubmit")).click();
				submit.click();
				Thread.sleep(500);
				if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Document with same ID already exists"))
				{
					String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
					System.out.println("Name duplicate: "+nameduplicate);
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
					if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Document with same ID already exists"))
					{
						continue;
					}
				}
						System.out.println("Not duplicate so break the loop");
						break;
				}
		}
		
	}
	
		String actualMSG = null;
		String emptyExpected = sheet.getRow(17).getCell(5).getStringCellValue();
		if (driver.findElements(By.className("notify-msg")).size() != 0) 
		{
			actualMSG = driver.findElement(By.className("notify-msg")).getText();
		}
		if (driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size() != 0) 
		{
			driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
		}

		if (emptyExpected.equalsIgnoreCase(actualMSG)) {
			XSSFCell status = sheet.getRow(17).getCell(7);
			status.setCellValue("Pass"); // Print status in excel
			status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
		} else {
			XSSFCell status = sheet.getRow(17).getCell(7);
			status.setCellValue("Fail"); // Print status in excel
			status.setCellStyle(Utils.style(workbook, "Fail")); // for print red font
		}
		Thread.sleep(1000);
		Utils.writeTooutputFile(workbook);
	}

}
