package com.eDocs.Users;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.eDocs.CommonValidation.AlphaNumericValidation;
import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Utils;

import jxl.write.WriteException;

public class UserCreate {

	public static WebDriver driver = Constant.driver;
	String testcaseSheetName = "UserTC";

	@BeforeClass
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
	}

	@Test(priority = 1)
	public void nameEmpty() throws IOException, WriteException, InterruptedException 
	{
		Thread.sleep(1000);
		driver.get(Constant.URL + "/users");
		Thread.sleep(1500);
		driver.findElement(By.id("adduser")).click();
		Thread.sleep(1500);
		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.id("createNewUser"));
		textField.AlphaNumericEmpty(testcaseSheetName, Constant.EXCEL_PATH, Submit, 6);
	}

	@Test(priority = 2)
	public void nameMaxFieldLength() throws IOException, WriteException, InterruptedException 
	{
		WebElement alphanumericField = driver.findElement(By.name("username"));
		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.MaxLengthCheck(testcaseSheetName, alphanumericField, 8);
	}

	@Test(priority = 3)
	public void nameSpecialChar() throws IOException, WriteException, InterruptedException 
	{
		WebElement alphanumericField = driver.findElement(By.name("username"));
		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.nameSpecialCharCheck(testcaseSheetName, alphanumericField, 9);
	}

	@Test(priority = 4)
	public void nameBeginingSpaceCheck() throws IOException, WriteException, InterruptedException 
	{
		WebElement alphanumericField = driver.findElement(By.name("username"));
		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.nameBeginingSpaceCheck(testcaseSheetName, alphanumericField, 10);
	}
	// end  Username

	//Employee ID start
	@Test(priority = 6)
	public void employeeIDEmpty() throws IOException, WriteException, InterruptedException 
	{
		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.id("createNewUser"));
		textField.AlphaNumericEmpty(testcaseSheetName, Constant.EXCEL_PATH_Result, Submit, 11);
	}
	
	@Test(priority = 7)
	public void employeeIDMaxFieldLength() throws IOException, WriteException, InterruptedException {
		WebElement alphanumericField = driver.findElement(By.name("empId"));
		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.MaxLengthCheck(testcaseSheetName, alphanumericField, 12);
		alphanumericField.clear();
	}
	@Test(priority = 8)
	public void employeeIDSpaceatBegining() throws IOException, WriteException, InterruptedException 
	{
		WebElement alphanumericField = driver.findElement(By.name("empId"));
		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.nameBeginingSpaceCheck(testcaseSheetName, alphanumericField, 13);
		alphanumericField.sendKeys(Keys.TAB);
	}
	
	//Employee ID End
	
	//Start FirstName
	@Test(priority = 9)
	public void firstNameEmpty() throws IOException, WriteException, InterruptedException 
	{
		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.id("createNewUser"));
		textField.AlphaNumericEmpty(testcaseSheetName, Constant.EXCEL_PATH_Result, Submit, 14);
	}
	
	@Test(priority = 10)
	public void firstNameMaxFieldLength() throws IOException, WriteException, InterruptedException {
		WebElement alphanumericField = driver.findElement(By.name("fName"));
		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.MaxLengthCheck(testcaseSheetName, alphanumericField, 15);
		alphanumericField.clear();
	}
	@Test(priority = 11)
	public void firstNameSpaceatBegining() throws IOException, WriteException, InterruptedException 
	{
		WebElement alphanumericField = driver.findElement(By.name("fName"));
		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.nameBeginingSpaceCheck(testcaseSheetName, alphanumericField, 16);
		alphanumericField.sendKeys(Keys.TAB);
	}
	// End First Name
	
	
	//Start lastName
		@Test(priority = 12)
		public void lastNameEmpty() throws IOException, WriteException, InterruptedException 
		{
			AlphaNumericValidation textField = new AlphaNumericValidation();
			WebElement Submit = driver.findElement(By.id("createNewUser"));
			textField.AlphaNumericEmpty(testcaseSheetName, Constant.EXCEL_PATH_Result, Submit, 17);
		}
		
		@Test(priority = 13)
		public void lastNameMaxFieldLength() throws IOException, WriteException, InterruptedException {
			WebElement alphanumericField = driver.findElement(By.name("lName"));
			AlphaNumericValidation textField = new AlphaNumericValidation();
			textField.MaxLengthCheck(testcaseSheetName, alphanumericField, 18);
			alphanumericField.clear();
		}
		@Test(priority = 14)
		public void lastNameSpaceatBegining() throws IOException, WriteException, InterruptedException 
		{
			WebElement alphanumericField = driver.findElement(By.name("lName"));
			AlphaNumericValidation textField = new AlphaNumericValidation();
			textField.nameBeginingSpaceCheck(testcaseSheetName, alphanumericField, 19);
			alphanumericField.sendKeys(Keys.TAB);
		}
		// End last Name
		
	
		
		//Start Telephone
				@Test(priority = 15)
				public void telePhEmpty() throws IOException, WriteException, InterruptedException 
				{
					AlphaNumericValidation textField = new AlphaNumericValidation();
					WebElement Submit = driver.findElement(By.id("createNewUser"));
					textField.AlphaNumericEmpty(testcaseSheetName, Constant.EXCEL_PATH_Result, Submit, 20);
				}
				
				@Test(priority = 16)
				public void telePhMaxFieldLength() throws IOException, WriteException, InterruptedException {
					WebElement alphanumericField = driver.findElement(By.name("landline"));
					AlphaNumericValidation textField = new AlphaNumericValidation();
					textField.MaxLengthCheck(testcaseSheetName, alphanumericField, 21);
					alphanumericField.clear();
				}
				@Test(priority = 17)
				public void telePhSpaceatBegining() throws IOException, WriteException, InterruptedException 
				{
					WebElement alphanumericField = driver.findElement(By.name("landline"));
					AlphaNumericValidation textField = new AlphaNumericValidation();
					textField.nameBeginingSpaceCheck(testcaseSheetName, alphanumericField, 22);
					alphanumericField.sendKeys(Keys.TAB);
				}
		// End Telephone
				
	
		// Start Email
				@Test(priority = 18)
				public void emailEmpty() throws IOException, WriteException, InterruptedException 
				{
					AlphaNumericValidation textField = new AlphaNumericValidation();
					WebElement Submit = driver.findElement(By.id("createNewUser"));
					textField.AlphaNumericEmpty(testcaseSheetName, Constant.EXCEL_PATH_Result, Submit, 23);
				}
				@Test(priority = 19)
				public void emailMaxFieldLength() throws IOException, WriteException, InterruptedException {
					WebElement alphanumericField = driver.findElement(By.name("email"));
					AlphaNumericValidation textField = new AlphaNumericValidation();
					textField.MaxLengthCheck(testcaseSheetName, alphanumericField, 24);
					alphanumericField.clear();
				}
				@Test(priority = 20)
				public void emailInvalid() throws IOException, WriteException, InterruptedException 
				{
					WebElement alphanumericField = driver.findElement(By.name("email"));
					AlphaNumericValidation textField = new AlphaNumericValidation();
					textField.nameBeginingSpaceCheck(testcaseSheetName, alphanumericField, 25);
					alphanumericField.sendKeys(Keys.TAB);
				}
				@Test(priority = 21)
				public void emailInvalidwithspace() throws IOException, WriteException, InterruptedException 
				{
					WebElement alphanumericField = driver.findElement(By.name("email"));
					AlphaNumericValidation textField = new AlphaNumericValidation();
					textField.nameBeginingSpaceCheck(testcaseSheetName, alphanumericField, 26);
					alphanumericField.sendKeys(Keys.TAB);
				}
				@Test(priority = 22)
				public void emailInvalidwrongDomains() throws IOException, WriteException, InterruptedException 
				{
					WebElement alphanumericField = driver.findElement(By.name("email"));
					AlphaNumericValidation textField = new AlphaNumericValidation();
					textField.nameBeginingSpaceCheck(testcaseSheetName, alphanumericField, 27);
					alphanumericField.sendKeys(Keys.TAB);
				}
				@Test(priority = 23)
				public void emailSpaceatBegining() throws IOException, WriteException, InterruptedException 
				{
					WebElement alphanumericField = driver.findElement(By.name("email"));
					AlphaNumericValidation textField = new AlphaNumericValidation();
					textField.nameBeginingSpaceCheck(testcaseSheetName, alphanumericField, 28);
					alphanumericField.clear();
					alphanumericField.sendKeys("test@easy.com");
					//alphanumericField.sendKeys(Keys.TAB);
				}
				
		// End Email
				
				
				/*@Test(priority = 25)
				public void departmentEmpty() throws IOException, WriteException, InterruptedException 
				{
					AlphaNumericValidation textField = new AlphaNumericValidation();
					WebElement Submit = driver.findElement(By.name("next"));
					textField.AlphaNumericEmpty(testcaseSheetName, Constant.EXCEL_PATH_Result, Submit, 29);
				}*/
				
	@Test(priority = 26)
	public void departmentCreateEmpty() throws IOException, WriteException, InterruptedException 
	{
		driver.findElement(By.id("trigger-add-role")).click(); // Click plus button
		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.xpath(".//*[@id='add_role']/div[2]/span/a[1]"));
		textField.AlphaNumericEmpty(testcaseSheetName, Constant.EXCEL_PATH_Result, Submit, 30);
	}
	
	@Test(priority = 27)
	public void departmentCreateMaxFieldLength() throws IOException, WriteException, InterruptedException {
		WebElement alphanumericField = driver.findElement(By.name("manfact_name"));
		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.MaxLengthCheck(testcaseSheetName, alphanumericField, 31);
	}
	
	/*@Test(priority = 28)
	public void  packagingDuplicate() throws IOException, WriteException, InterruptedException // check mandatory symbol and error msg
  	{
		Thread.sleep(500);
		driver.findElement(By.name("manfact_name")).sendKeys("Qualification Assurance");
		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.cssSelector(".icon.add-icon.m-x-10"));
  		textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,Submit,32);
  	}	*/
	
	@Test(priority = 29)
	public void departmentCreateSuccess() throws IOException, WriteException, InterruptedException 
	{
		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.cssSelector(".icon.add-icon.m-x-10"));
		textField.AlphaNumericEmpty(testcaseSheetName, Constant.EXCEL_PATH_Result, Submit, 33);
	}
	
	@Test(priority = 30)
	public void departmentdeleteSuccess() throws IOException, WriteException, InterruptedException 
	{
		driver.findElement(By.name("email")).sendKeys(Keys.TAB,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ENTER,Keys.ENTER);
		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.id("deleteDepartment"));
		textField.AlphaNumericEmpty(testcaseSheetName, Constant.EXCEL_PATH_Result, Submit, 34);
		//select department
		driver.findElement(By.name("email")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
	}
	
	@Test(priority = 31)
	public void groupEmptyTest() throws IOException, WriteException, InterruptedException 
	{
		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.id("createNewUser"));
		textField.AlphaNumericEmpty(testcaseSheetName, Constant.EXCEL_PATH_Result, Submit, 35);
		//select group
		driver.findElement(By.name("email")).sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.ENTER,Keys.ENTER);
	}
	
	@Test(priority = 32)
	public void userCreate() throws IOException, WriteException, InterruptedException 
	{
		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		XSSFSheet sheet = workbook.getSheet(testcaseSheetName);

		WebElement nameField = driver.findElement(By.name("username"));
		String Name = nameField.getAttribute("value");

		WebElement document = driver.findElement(By.name("empId"));
		String documentIDCREATE = document.getAttribute("value");

		WebElement submit = driver.findElement(By.id("createNewUser"));
		submit.click();
		Thread.sleep(1500);
			
		Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
		for(int k=5;k<1000;k++)
		{
			j.add(k);
		}
		
		//if duplicate equipment name
		if( driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Username already exists"))
		{
			String getduplicatename = driver.findElement(By.className("notify-msg")).getText();
			driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
		
		Thread.sleep(500);
		if(getduplicatename.equals("Username already exists"))
		{
			for(Integer i:j)
			{
				nameField.clear();
				Thread.sleep(200);
				nameField.sendKeys(Name+i);
				Thread.sleep(500);
				submit.click();
				Thread.sleep(500);
				if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Username already exists"))
				{
					String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
					System.out.println("Name duplicate: "+nameduplicate);
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
					if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Username already exists"))
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
		if( driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("EmployeeID already exists"))
		{
			String getduplicateID = driver.findElement(By.className("notify-msg")).getText();
			driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
		System.out.println("getduplicatename: "+getduplicateID);
		if(getduplicateID.equalsIgnoreCase("EmployeeIDD already exists"))
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
				if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("EmployeeID already exists"))
				{
					String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
					System.out.println("Name duplicate: "+nameduplicate);
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
					if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("EmployeeID already exists"))
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
		String emptyExpected = sheet.getRow(36).getCell(5).getStringCellValue();
		if (driver.findElements(By.className("notify-msg")).size() != 0) 
		{
			actualMSG = driver.findElement(By.className("notify-msg")).getText();
		}
		if (driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size() != 0) 
		{
			driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
		}

		if (emptyExpected.equalsIgnoreCase(actualMSG)) {
			XSSFCell status = sheet.getRow(36).getCell(7);
			status.setCellValue("Pass"); // Print status in excel
			status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
		} else {
			XSSFCell status = sheet.getRow(36).getCell(7);
			status.setCellValue("Fail"); // Print status in excel
			status.setCellStyle(Utils.style(workbook, "Fail")); // for print red font
		}
		Thread.sleep(1000);
		Utils.writeTooutputFile(workbook);
	}

}
