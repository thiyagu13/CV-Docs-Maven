package com.eDocs.EquipmentTrain;

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
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.eDocs.CommonValidation.AlphaNumericValidation;
import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Message;
import com.eDocs.Utils.RepositoryParser;
import com.eDocs.Utils.Utils;

import jxl.write.WriteException;

public class EquipmentTrainSelectEquipment {

	private RepositoryParser parser;
	public static WebDriver driver = Constant.driver;
	String testcaseSheetName = "EquipmentTRAINTC";
	public String password = Constant.sitepassword;

	@Test(priority = 70)
	public void EquipmentEmpty() throws IOException, WriteException, InterruptedException {
		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.id("saveEquipmentTrainDetails"));
		textField.AlphaNumericEmpty(testcaseSheetName, Constant.EXCEL_PATH_Result, Submit, 11);
	}

	@Test(priority = 71)
	public void lessthanOneEquipment() throws IOException, WriteException, InterruptedException {
		Thread.sleep(500);
		driver.findElement(By.xpath(".//*[@id='fs1']/div[2]/div/div/div/span/span[1]/span")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath(".//*[@id='fs1']/div[2]/div/div/div/span/span[1]/span")).sendKeys(Keys.ENTER);
		Thread.sleep(500);
		AlphaNumericValidation textField = new AlphaNumericValidation();
		WebElement Submit = driver.findElement(By.id("saveEquipmentTrainDetails"));
		textField.AlphaNumericEmpty(testcaseSheetName, Constant.EXCEL_PATH_Result, Submit, 12);
		Thread.sleep(1000);
		driver.findElement(By.xpath(".//*[@id='fs1']/div[2]/div/div/div/span/span[1]/span")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath(".//*[@id='fs1']/div[2]/div/div/div/span/span[1]/span")).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
		Thread.sleep(500);
		Submit.click();
		
		Thread.sleep(1000);
		Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
		for(int k=5;k<1000;k++)
		{
			j.add(k);
		}
		WebElement getName = driver.findElement(By.name("name"));
		String gettrainName = getName.getAttribute("value");
		Thread.sleep(500);
		if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Train '"+gettrainName+"' already exists!"))
		{
			String getduplicatename = driver.findElement(By.className("notify-msg")).getText();
			driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
		
		Thread.sleep(500);
		if(getduplicatename.equalsIgnoreCase("Train '"+gettrainName+"' already exists!"))
		{
			for(Integer i:j)
			{
				//driver.findElement(parser.getbjectLocator("ActiveIngredientName")).clear();
				getName.clear();
				Thread.sleep(200);
				getName.sendKeys(gettrainName+i);
				//driver.findElement(parser.getbjectLocator("ActiveIngredientName")).sendKeys(Name+i);
				Thread.sleep(500);
				Submit.click();
				//driver.findElement(parser.getbjectLocator("APIsubmit")).click();
				Thread.sleep(500);
				if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Train '"+gettrainName+i+"' already exists!"))
				{
					String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
					System.out.println("Name duplicate: "+nameduplicate);
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
					if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Train '"+gettrainName+i+"' already exists!"))
					{
						continue;
					}
				}
						System.out.println("Not duplicate so break the loop");
						break;
				}
			}
		}
		Thread.sleep(1000);
		//if duplicate equipment set
		if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Duplicate equipment set") )
		{
				driver.findElement(By.className("custom-notify-close")).click();
				WebElement IdentifuEquipment = driver.findElement(By.className("select2-search__field"));
				IdentifuEquipment.click();
				Thread.sleep(500);
				IdentifuEquipment.sendKeys(Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ENTER);
				Thread.sleep(500);
				driver.findElement(By.id("saveEquipmentTrainDetails")).click();
				Thread.sleep(500);
				if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Duplicate equipment set"))
				{
					driver.findElement(By.className("custom-notify-close")).click();
					WebElement IdentifuDupliEquipment = driver.findElement(By.className("select2-search__field"));
					IdentifuDupliEquipment.click();
					Thread.sleep(500);
					IdentifuDupliEquipment.sendKeys(Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ENTER);
					Thread.sleep(500);
					driver.findElement(By.id("saveEquipmentTrainDetails")).click();
					Thread.sleep(500);
				}
		}	// closing if loop duplicate equipment set
		
	}	
		
	@Test(priority = 72)
	public void uploadImageEmpty() throws IOException, WriteException, InterruptedException {
		Thread.sleep(1000);
		WebElement Submit = driver.findElement(By.id("saveEquipmentTrainSamplingDetails"));
		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.AlphaNumericEmpty(testcaseSheetName, Constant.EXCEL_PATH_Result, Submit, 13);
	}	
	
	@Test(priority = 73)
	public void locationPinEmpty() throws IOException, WriteException, InterruptedException {
		Thread.sleep(1500);
		//Upload image set as NO
		driver.findElement(By.xpath(".//*[@id='needUloadImages']/span/span[1]/span/span[2]")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath(".//*[@id='needUloadImages']/span/span[1]/span/span[2]")).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
		Thread.sleep(500);
		WebElement Submit = driver.findElement(By.id("saveEquipmentTrainSamplingDetails"));
		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.AlphaNumericEmpty(testcaseSheetName, Constant.EXCEL_PATH_Result, Submit, 14);
	}
	
	@Test(priority = 74)
	public void locationNameEmpty() throws IOException, WriteException, InterruptedException {
		Thread.sleep(500);
		//Click 'Add Location' Pin
		driver.findElement(By.id("addPinName")).click();
		Thread.sleep(500);
		WebElement Submit = driver.findElement(By.id("saveEquipmentTrainSamplingDetails"));
		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.AlphaNumericEmpty(testcaseSheetName, Constant.EXCEL_PATH_Result, Submit, 15);
	}
	
	@Test(priority = 75)
	public void locationNameMaxLength() throws IOException, WriteException, InterruptedException {
		Thread.sleep(500);
		WebElement alphanumericField = driver.findElement(By.id("locationName1"));
		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.MaxLengthCheck(testcaseSheetName, alphanumericField, 16);
	}
	
	@Test(priority = 76)
	public void rationaleMaxLength() throws IOException, WriteException, InterruptedException {
		Thread.sleep(500);
		WebElement alphanumericField = driver.findElement(By.xpath(".//*[@id='rationale1']"));
		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.MaxLengthCheck(testcaseSheetName, alphanumericField, 17);
	}
	
	
	
	@Test(priority = 77)
	public void MOCEmptyTest() throws IOException, WriteException, InterruptedException
	{
		Thread.sleep(1000);
		WebElement Submit = driver.findElement(By.id("saveEquipmentTrainSamplingDetails"));
		AlphaNumericValidation textField = new AlphaNumericValidation();
		textField.AlphaNumericEmpty(testcaseSheetName, Constant.EXCEL_PATH_Result, Submit,18);
		Thread.sleep(1000);
		//Select MOC
		//TODO
		Thread.sleep(500);
		System.out.println("locationName1:" +driver.findElement(By.id("locationName1")).getAttribute("type"));
		if(driver.findElement(By.id("locationName1")).getAttribute("type").contains("text")) 
		{
			Thread.sleep(500);
			//select MOC
			driver.findElement(By.id("locationName1")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
			Thread.sleep(500);
		}
		Thread.sleep(1000);
		//MOC Selection
		if(driver.findElement(By.id("locationName1")).getAttribute("type").contains("select"))
		{
			Thread.sleep(1000);
			driver.findElement(By.xpath(".//*[@id='marker-num1']/div[3]/span/span[1]/span")).click();
			driver.findElement(By.xpath(".//*[@id='marker-num1']/div[3]/span/span[1]/span")).sendKeys(Keys.ENTER);
		}
	}
		
	
	@Test(priority = 78)
	public void trainSaveandNOChangeControl() throws IOException, WriteException, InterruptedException {
		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		XSSFSheet sheet = workbook.getSheet(testcaseSheetName);
		Thread.sleep(500);
		AlphaNumericValidation textField = new AlphaNumericValidation();
  		WebElement Submit = driver.findElement(By.id("saveEquipmentTrainSamplingDetails"));
  			if(Submit.getText().equalsIgnoreCase("Submit"))
			{
  				Thread.sleep(500);
				System.out.println("No Custom loop");
				Thread.sleep(500);
				Submit.click();
				String expectedMSG = sheet.getRow(19).getCell(5).getStringCellValue(); // get expected value from excel
				//textField.AlphaNumericEmpty(testcaseSheetName, Constant.EXCEL_PATH_Result, Submit,19);
				Thread.sleep(3000);
				String actualMSG=null;
				if(driver.findElements(By.className("notify-msg")).size()!=0)
				{
					actualMSG = driver.findElement(By.className("notify-msg")).getText();
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				}
				XSSFCell actualMSGprint = sheet.getRow(19).getCell(6); //Print actual result in the excel cell
				actualMSGprint.setCellValue(actualMSG);
				System.out.println("actualMSG----->"+actualMSG);
				
				if(expectedMSG.equalsIgnoreCase(actualMSG))
				{
					XSSFCell status = sheet.getRow(19).getCell(7);
					status.setCellValue("Pass"); //Print status in excel
					status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
				}else
				{
					XSSFCell status = sheet.getRow(19).getCell(7);
					status.setCellValue("Fail"); //Print status in excel
					status.setCellStyle(Utils.style(workbook, "Fail")); //for print red font
				}
				
			}
			else // custom loop  
			{
				Submit.click();
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
					Thread.sleep(500);
						//click save button in custom fields
						WebElement customLoopSubmit = driver.findElement(By.id("saveCustomDetails"));
						//textField.AlphaNumericEmpty(testcaseSheetName, Constant.EXCEL_PATH_Result, customLoopSubmit,19);
						Thread.sleep(2000);
						customLoopSubmit.click();
						String expectedMSG = sheet.getRow(19).getCell(5).getStringCellValue(); // get expected value from excel
						//textField.AlphaNumericEmpty(testcaseSheetName, Constant.EXCEL_PATH_Result, Submit,19);
						Thread.sleep(3000);
						String actualMSG=null;
						if(driver.findElements(By.className("notify-msg")).size()!=0)
						{
							actualMSG = driver.findElement(By.className("notify-msg")).getText();
							String className = this.getClass().getName(); // get current class name - for screenshot
							String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
							Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
							driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
						}
						XSSFCell actualMSGprint = sheet.getRow(19).getCell(6); //Print actual result in the excel cell
						actualMSGprint.setCellValue(actualMSG);
						System.out.println("actualMSG----->"+actualMSG);
						
						if(expectedMSG.equalsIgnoreCase(actualMSG))
						{
							XSSFCell status = sheet.getRow(19).getCell(7);
							status.setCellValue("Pass"); //Print status in excel
							status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
						}else
						{
							XSSFCell status = sheet.getRow(19).getCell(7);
							status.setCellValue("Fail"); //Print status in excel
							status.setCellStyle(Utils.style(workbook, "Fail")); //for print red font
						}
			}
  		// Get SuccessFull Message
  			Utils.writeTooutputFile(workbook); 
	}
	
	
	
	
	
}
