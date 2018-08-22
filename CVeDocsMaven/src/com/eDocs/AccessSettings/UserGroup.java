package com.eDocs.AccessSettings;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Utils;

public class UserGroup {
	
	
	private WebDriver driver;
	String baseURL = "http://192.168.1.111:8090/login";

	@Parameters({ "browser" })
	@BeforeTest
	public void openBrowser(String browser) throws InterruptedException {
		try {
			if (browser.equalsIgnoreCase("Firefox")) {	// Firefox browser execution
				driver = new FirefoxDriver();
			} else if (browser.equalsIgnoreCase("chrome")) { // chrome browser execution
				System.setProperty("webdriver.chrome.driver",
						"C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\chromedriver.exe");
				driver = new ChromeDriver();
			} /*else if (browser.equalsIgnoreCase("IE")) {
				System.setProperty("webdriver.ie.driver",
						"D:/IEDriverServer.exe");
				driver = new InternetExplorerDriver();
			}*/
		
		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		}
		driver.navigate().to(baseURL);
		Thread.sleep(1000);
		// Login
		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.id("password")).sendKeys("123456");
		Thread.sleep(3000);
		driver.findElement(By.id("loginsubmit")).click();
		Thread.sleep(1000);
		
	}
	
	
	@Test(priority=1)
	public void groupName() throws InterruptedException, IOException {
		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH);
		XSSFSheet sheet = workbook.getSheet("UserGroup");
		Thread.sleep(500);
		driver.get("http://192.168.1.111:8090/group-policy");
		Thread.sleep(500);
		driver.findElement(By.id("addGroupPolicy")).click();;
		Thread.sleep(500);
		driver.findElement(By.id("saveUserGroup")).click();
		Thread.sleep(500);
  		
		String emptyname_expec = sheet.getRow(6).getCell(6).getStringCellValue(); // get expected value from excel
		String emptyname_actual = driver.findElement(By.id(".//*[@id='grpPage']/div[12]/div/span")).getText();
		
		Thread.sleep(500);
		XSSFCell emptyname_actual_print = sheet.getRow(6).getCell(8); //Print actual result in the excel cell
		emptyname_actual_print.setCellValue("It shows error msg as: "+emptyname_actual);
		
		if(emptyname_expec.equalsIgnoreCase(emptyname_actual))
		{
			XSSFCell emptyname_status = sheet.getRow(6).getCell(9);
			emptyname_status.setCellValue("Pass"); //Print status in excel
			emptyname_status.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
		}else
		{
			XSSFCell emptyname_status = sheet.getRow(6).getCell(7);
			emptyname_status.setCellValue("Fail"); //Print status in excel
			emptyname_status.setCellStyle(Utils.style(workbook, "Fail")); //for print red font
			//Utils.writeTooutputFile(workbook); // write output to the workbook
		//	Assert.fail("Empty field validation not working");
		}
		Utils.writeTooutputFile(workbook); // write output to the workbook
		
		String className = this.getClass().getName(); // get current class name - for screenshot
		String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
		Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
	}


	
	
	
	/*
	@AfterTest
	public void closeBrowser() {
		driver.quit();
	}
	
	*/
	
	
}
