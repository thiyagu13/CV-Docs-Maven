package com.eDocs.Equipment;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import com.eDocs.Utils.Message;
import com.eDocs.Utils.RepositoryParser;
import com.eDocs.Utils.Utils;

import jxl.write.WriteException;

public class EquipmentSegment {
	
	private RepositoryParser parser;
	public static WebDriver driver = Constant.driver;
	String testcaseSheetName = "EquipmentTC";
	
	
	
	public static void writeExcel(int row) throws IOException
	{
		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		XSSFSheet sheet = workbook.getSheet("EquipmentTC");
		
		XSSFCell status = sheet.getRow(row).getCell(7);
		status.setCellValue("No Segment"); //Print status in excel
		Utils.writeTooutputFile(workbook); // write output to the workbook
	}
  
  	@Test (priority=40)
  	public void  segmentNamePinEmptyOREquipSaveTest() throws IOException, WriteException, InterruptedException
  	{
  		WebElement samplingSubmit = driver.findElement(By.id("submitEquipmentSamplingDetails"));
  		System.out.println("samplingSubmit: "+samplingSubmit.getText());
  		if(samplingSubmit.getText().equalsIgnoreCase("Submit"))
  		{
  			Thread.sleep(500);
  			AlphaNumericValidation textField = new AlphaNumericValidation();
  			textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,samplingSubmit,67);
  		}
  		else
  		{
  			if(driver.findElements(By.id("next-gotoeqpSamplingCustom")).size()!=0)
  			{
  				Thread.sleep(500);
  				samplingSubmit.click();
  				Thread.sleep(500);
  				WebElement Submit = driver.findElement(By.id("next-gotoeqpSamplingCustom"));
  				AlphaNumericValidation textField = new AlphaNumericValidation();
  				textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,Submit,56);
  			}
  		}
  	}
  	
  	@Test (priority=41)
  	public void  segmentNameEmptyTest() throws IOException, WriteException, InterruptedException
  	{
  		Thread.sleep(1000);
  		System.out.println("Size: "+driver.findElements(By.id("next-gotoeqpSamplingCustom")).size());
  		//if(driver.findElements(By.id("next-gotoeqpSamplingCustom")).size()!=0)
  		if(driver.findElement(By.id("next-gotoeqpSamplingCustom")).getText().equalsIgnoreCase("Next")
  				|| driver.findElement(By.id("next-gotoeqpSamplingCustom")).getText().equalsIgnoreCase("Submit"))
  		{
  				Thread.sleep(500);
				driver.findElement(By.id("addSegment")).click();// add Segment Pin
  				WebElement segmentSubmit = driver.findElement(By.id("next-gotoeqpSamplingCustom"));
	  			Thread.sleep(500);
	  			AlphaNumericValidation textField = new AlphaNumericValidation();
	  			textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result,segmentSubmit,57);
  		}
  		else
  		{
  			writeExcel(57);
  		}
  		/*else 
  		{
  			XSSFCell status = sheet.getRow(57).getCell(7);
			status.setCellValue("No Segment"); //Print status in excel
  		}*/
  		//Utils.writeTooutputFile(workbook); // write output to the workbook
  	}
  	
	@Test (priority=42)
  	public void  segmentNameMaxLengthTest() throws IOException, WriteException, InterruptedException
  	{
		//XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		//XSSFSheet sheet = workbook.getSheet("EquipmentTC");
  		Thread.sleep(500);
  		if(driver.findElement(By.id("next-gotoeqpSamplingCustom")).getText().equalsIgnoreCase("Next")
  				|| driver.findElement(By.id("next-gotoeqpSamplingCustom")).getText().equalsIgnoreCase("Submit"))
  		{
  				Thread.sleep(500);
  				WebElement alphanumericField = driver.findElement(By.id("segmentName1"));
	  			Thread.sleep(500);
	  			AlphaNumericValidation textField = new AlphaNumericValidation();
	  			textField.MaxLengthCheck(testcaseSheetName,alphanumericField, 58);
  		}
  		else
  		{
  	  			writeExcel(58);
  		}
  		/*else 
  		{
  			XSSFCell status = sheet.getRow(58).getCell(7);
			status.setCellValue("No Segment"); //Print status in excel
  		}
  		Utils.writeTooutputFile(workbook); // write output to the workbook
*/  	}
	
	//TO DO Numeric field
	@Test (priority=43)
  	public void  segmentSurfaceAreaEmptyTest() throws IOException, WriteException, InterruptedException
  	{
		//XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		//XSSFSheet sheet = workbook.getSheet("EquipmentTC");
  		Thread.sleep(500);
  		if(driver.findElement(By.id("next-gotoeqpSamplingCustom")).getText().equalsIgnoreCase("Next")
  				|| driver.findElement(By.id("next-gotoeqpSamplingCustom")).getText().equalsIgnoreCase("Submit"))
  		{
  				Thread.sleep(500);
  				WebElement submit = driver.findElement(By.id("next-gotoeqpSamplingCustom"));
	  			Thread.sleep(500);
	  			NumericValidation textField = new NumericValidation();
	  			textField.NumericEmpty(testcaseSheetName,submit, 59);
  		}
  		else
  		{
  			writeExcel(59);
  		}
/*  		else 
  		{
  			XSSFCell status = sheet.getRow(59).getCell(7);
			status.setCellValue("No Segment"); //Print status in excel
  		}*/
  		//Utils.writeTooutputFile(workbook); // write output to the workbook
  	}
	
	@Test (priority=44)
  	public void  segmentSurfaceAreaZeroTest() throws IOException, WriteException, InterruptedException
  	{
		//XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		//XSSFSheet sheet = workbook.getSheet("EquipmentTC");
  		Thread.sleep(500);
  		if(driver.findElement(By.id("next-gotoeqpSamplingCustom")).getText().equalsIgnoreCase("Next")
  				|| driver.findElement(By.id("next-gotoeqpSamplingCustom")).getText().equalsIgnoreCase("Submit"))
  		{
  				Thread.sleep(500);
  				WebElement numericField = driver.findElement(By.id("segSurfaceArea1"));
	  			Thread.sleep(500);
	  			NumericValidation textField = new NumericValidation();
	  			textField.NumericZERO(testcaseSheetName,numericField, 60);
  		}
  		else
  		{
  			writeExcel(60);
  		}
  		/*else 
  		{
  			XSSFCell status = sheet.getRow(60).getCell(7);
			status.setCellValue("No Segment"); //Print status in excel
  		}*/
  		//Utils.writeTooutputFile(workbook); // write output to the workbook
  	}
	
	@Test (priority=45)
  	public void  segmentSurfaceAreaMaxLengthTest() throws IOException, WriteException, InterruptedException
  	{
		//XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		//XSSFSheet sheet = workbook.getSheet("EquipmentTC");
  		Thread.sleep(500);
  		if(driver.findElement(By.id("next-gotoeqpSamplingCustom")).getText().equalsIgnoreCase("Next")
  				|| driver.findElement(By.id("next-gotoeqpSamplingCustom")).getText().equalsIgnoreCase("Submit"))
  		{
  				Thread.sleep(500);
  				WebElement numericField = driver.findElement(By.id("segSurfaceArea1"));
	  			Thread.sleep(500);
	  			NumericValidation textField = new NumericValidation();
	  			textField.NumericMaxLength(testcaseSheetName,numericField, 61);
  		}
  		else
  		{
  			writeExcel(61);
  		}
  		/*else 
  		{
  			XSSFCell status = sheet.getRow(61).getCell(7);
			status.setCellValue("No Segment"); //Print status in excel
  		}*/
  		//Utils.writeTooutputFile(workbook); // write output to the workbook
  	}
	
	@Test (priority=46)
  	public void  segmentSurfaceAreaInvalidCharTest() throws IOException, WriteException, InterruptedException
  	{
		//XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		//XSSFSheet sheet = workbook.getSheet("EquipmentTC");
  		Thread.sleep(500);
  		if(driver.findElement(By.id("next-gotoeqpSamplingCustom")).getText().equalsIgnoreCase("Next")
  				|| driver.findElement(By.id("next-gotoeqpSamplingCustom")).getText().equalsIgnoreCase("Submit"))
  		{
  				Thread.sleep(500);
  				WebElement numericField = driver.findElement(By.id("segSurfaceArea1"));
	  			Thread.sleep(500);
	  			NumericValidation textField = new NumericValidation();
	  			textField.numericFieldAlphabetsCheck(testcaseSheetName,numericField, 62);
  		}
  		else
  		{
  			writeExcel(62);
  		}
  		/*else 
  		{
  			XSSFCell status = sheet.getRow(62).getCell(7);
			status.setCellValue("No Segment"); //Print status in excel
  		}*/
  		//Utils.writeTooutputFile(workbook); // write output to the workbook
  	}
	
	@Test (priority=47)
  	public void  segmentSurfaceAreaSpaceTest() throws IOException, WriteException, InterruptedException
  	{
		//XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		//XSSFSheet sheet = workbook.getSheet("EquipmentTC");
  		Thread.sleep(500);
  		if(driver.findElement(By.id("next-gotoeqpSamplingCustom")).getText().equalsIgnoreCase("Next")
  				|| driver.findElement(By.id("next-gotoeqpSamplingCustom")).getText().equalsIgnoreCase("Submit"))
  		{
  				Thread.sleep(500);
  				WebElement numericField = driver.findElement(By.id("segSurfaceArea1"));
	  			Thread.sleep(500);
	  			NumericValidation textField = new NumericValidation();
	  			textField.numericSpaceCheck(testcaseSheetName,numericField, 63);
  		}
  		else
  		{
  			writeExcel(63);
  		}
  		/*else 
  		{
  			XSSFCell status = sheet.getRow(63).getCell(7);
			status.setCellValue("No Segment"); //Print status in excel
  		}*/
  		//Utils.writeTooutputFile(workbook); // write output to the workbook
  	}
	
	@Test (priority=48)
  	public void  segmentSurfaceAreaMultiDecimalTest() throws IOException, WriteException, InterruptedException
  	{
		//XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		//XSSFSheet sheet = workbook.getSheet("EquipmentTC");
  		Thread.sleep(500);
  		if(driver.findElement(By.id("next-gotoeqpSamplingCustom")).getText().equalsIgnoreCase("Next")
  				|| driver.findElement(By.id("next-gotoeqpSamplingCustom")).getText().equalsIgnoreCase("Submit"))
  		{
  				Thread.sleep(500);
  				WebElement numericField = driver.findElement(By.id("segSurfaceArea1"));
	  			Thread.sleep(500);
	  			NumericValidation textField = new NumericValidation();
	  			textField.numericMultiDecimal(testcaseSheetName,numericField, 64);
  		}
  		else
  		{
  			writeExcel(64);
  		}
  		/*else 
  		{
  			XSSFCell status = sheet.getRow(64).getCell(7);
			status.setCellValue("No Segment"); //Print status in excel
  		}*/
  		//Utils.writeTooutputFile(workbook); // write output to the workbook
  	}
	
	@Test (priority=49)
  	public void  segmentlocationTest() throws IOException, WriteException, InterruptedException
  	{
		//XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		//XSSFSheet sheet = workbook.getSheet("EquipmentTC");
  		Thread.sleep(500);
  		if(driver.findElement(By.id("next-gotoeqpSamplingCustom")).getText().equalsIgnoreCase("Next")
  				|| driver.findElement(By.id("next-gotoeqpSamplingCustom")).getText().equalsIgnoreCase("Submit"))
  		{
  				Thread.sleep(500);
  				WebElement submit = driver.findElement(By.id("next-gotoeqpSamplingCustom"));
	  			Thread.sleep(500);
	  			AlphaNumericValidation textField = new AlphaNumericValidation();
	  			textField.AlphaNumericEmpty(testcaseSheetName,Constant.EXCEL_PATH_Result, submit, 65);
	  			Thread.sleep(500);
	  			//WebElement Seglocation = driver.findElement(By.id("select1"));
				//Select SelectLocation = new Select(Seglocation);
				//SelectLocation.selectByIndex(0);
	  			driver.findElement(By.id("segSurfaceArea1")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);;
	  			Thread.sleep(500);
  		}
  		else
  		{
  			writeExcel(65);
  		}
  		/*else 
  		{
  			XSSFCell status = sheet.getRow(65).getCell(7);
			status.setCellValue("No Segment"); //Print status in excel
  		}*/
  		//Utils.writeTooutputFile(workbook); // write output to the workbook
  		
  	}
	
	@Test (priority=50)
  	public void  equipmentIFCustomFieldorSAVETest() throws IOException, WriteException, InterruptedException
  	{
		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		XSSFSheet sheet = workbook.getSheet("EquipmentTC");
  		Thread.sleep(500);
  		if(driver.findElement(By.id("next-gotoeqpSamplingCustom")).getText().equalsIgnoreCase("Next")
  				|| driver.findElement(By.id("next-gotoeqpSamplingCustom")).getText().equalsIgnoreCase("Submit"))
  		{
  			Thread.sleep(500);
  			
  			WebElement segSubmit = driver.findElement(By.id("next-gotoeqpSamplingCustom"));
  			if(segSubmit.getText().equalsIgnoreCase("Submit"))
			{
  				Thread.sleep(500);
				System.out.println("SegLoop: No Custom loop");
				segSubmit.click();
				Thread.sleep(2000);
			}
			else // custom loop after segment info
			{
				segSubmit.click();
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
						driver.findElement(By.id("saveCustomDetails")).click();
						Thread.sleep(2000);
			}
  		}
  		// Get SuccessFull Message
  		String actualMSG = null;
  			String expectedMSG = sheet.getRow(67).getCell(5).getStringCellValue();
  		if(driver.findElements(By.className("notify-msg")).size()!=0)
		{
			actualMSG = driver.findElement(By.className("notify-msg")).getText();
			String className = this.getClass().getName(); // get current class name - for screenshot
			String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
			Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
			driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
		}
  		
  		if(actualMSG!=null)
  		{
  			XSSFCell actualMSGprint = sheet.getRow(67).getCell(6); //Print actual result in the excel cell
  			actualMSGprint.setCellValue(actualMSG);
  		}else {
  			actualMSG = sheet.getRow(67).getCell(6).getStringCellValue();
  		}
		
		if(expectedMSG.equalsIgnoreCase(actualMSG))
		{
			XSSFCell status = sheet.getRow(67).getCell(7);
			status.setCellValue("Pass"); //Print status in excel
			status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
		}else
		{
			XSSFCell status = sheet.getRow(67).getCell(7);
			status.setCellValue("Fail"); //Print status in excel
			status.setCellStyle(Utils.style(workbook, "Fail")); //for print red font
		}
		Utils.writeTooutputFile(workbook); // write output to the workbook
		
  	}
}

