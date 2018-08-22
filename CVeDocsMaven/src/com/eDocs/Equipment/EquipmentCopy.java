package com.eDocs.Equipment;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Message;
import com.eDocs.Utils.Utils;

public class EquipmentCopy {
	
	public static WebDriver driver = Constant.driver;
	public String password = Constant.sitepassword;
	
	@Test(priority=51,invocationCount=3)
	public void CopyEquipment() throws InterruptedException, SQLException, ClassNotFoundException, IOException
	{
		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result);
		XSSFSheet sheet = workbook.getSheet("EquipmentTC");
		//driver.navigate().refresh();
		driver.get(Constant.URL+"/equipment");
		Thread.sleep(1000);
		//driver.findElement(parser.getbjectLocator("EquipmentAction")).click(); // Click action icon
		driver.findElement(By.id("dLabel")).click();
		Thread.sleep(500);
		//driver.findElement(By.xpath(".//*[@id='datatable']/tbody/tr[1]/td[10]/div/ul/li[2]/a")).click(); // click delete button
		//driver.findElement(By.className("dropdown-item")).sendKeys(Keys.ENTER);
		driver.findElement(By.linkText("Copy")).click();
		Thread.sleep(500);
		
		
		String equipmentName = sheet.getRow(10).getCell(4).getStringCellValue(); // get equip name from excel
		//String equipmentName = "Test Equipment";
		WebElement eqName = driver.findElement(By.id("nameForCopy")); //Equipment copy Name field
		eqName.sendKeys(equipmentName);
		
		Thread.sleep(500);
		//driver.findElement(By.xpath(".//*[@id='dynamicModal']/div[3]/div/button[2]")).click();
		driver.findElement(By.cssSelector(".btn.blue-btn.waves-effect.copyData")).click();
		Thread.sleep(2000);
		
		String successMSG = null;
		if(driver.findElements(By.cssSelector(".btn.blue-btn.waves-effect.copyData")).size()!=0)
		{
			String getduplicatename = driver.findElement(By.className("notify-msg")).getText();
			driver.findElement(By.className("custom-notify-close")).click();
			if(getduplicatename.equalsIgnoreCase(Message.equipmentCOPY))
			{
				successMSG = getduplicatename;
			}
			
		Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
		for(int k=25;k<1000;k++)
		{
			j.add(k);
		}
		
		Thread.sleep(500);
		if(getduplicatename.equals("Equipment '"+equipmentName+"' already exists!"))
		{
			System.out.println("loop");
			for(Integer i:j)
			{
				driver.findElement(By.id("nameForCopy")).clear();
				driver.findElement(By.id("nameForCopy")).sendKeys(equipmentName+i);
				Thread.sleep(500);
				driver.findElement(By.cssSelector(".btn.blue-btn.waves-effect.copyData")).click();
				Thread.sleep(2000);
				String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
				if(driver.findElements(By.className("notify-msg")).size()!=0 
						&& driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Equipment '"+equipmentName+i+"' already exists!"))
				{
					System.out.println("nameduplicate: "+nameduplicate);
					if(nameduplicate.equalsIgnoreCase(Message.equipmentCOPY))
					{
						successMSG = nameduplicate;
						System.out.println("For Loop: "+successMSG);
					}
					driver.findElement(By.className("custom-notify-close")).click();
					if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Equipment '"+equipmentName+i+"' already exists!"))
					{
						continue;
					}
				}
						System.out.println("Not duplicate so break the loop");
						if(nameduplicate.equalsIgnoreCase(Message.equipmentCOPY))
						{
							successMSG = nameduplicate;
							System.out.println("For Loop: "+successMSG);
						}
						break;
				}
			}
		} 
		Thread.sleep(2000);
		//successMSG = driver.findElement(By.className("notify-msg")).getText();
		System.out.println("MSG: "+successMSG);
		Assert.assertEquals(successMSG,Message.equipmentCOPY);
		String className = this.getClass().getName(); // get current class name - for screenshot
		String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
		Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
		//if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
		//{
		//	driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
		//}
		Utils.writeTooutputFile(workbook); 
		Thread.sleep(500);
	}
}
