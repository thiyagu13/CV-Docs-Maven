package com.eDocs.SanityTesting;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.eDocs.Utils.Utils;

public class ChangeControl {
  
	
	
			
		//	private RepositoryParser parser;
			//private WebDriver driver = Constant.driver;
			private WebDriver driver;
			public String password = "123456";
		
			
			//Datas for create ChangeControl
			static String changeControlCREATE  = "Selenium 1111";
			static String summaryCREATE = "Summary";
			static String descriptionCREATE = "Description of change Control";
			static String rationaleCREATE = "Rationale";
			static String equipmentName = "sel";
			
			//Datas for Edit changeControl
			static String summaryEDIT = "edit Summary";
			static String descriptionEDIT = "Edit Description of change Control";
			static String rationaleEDIT = "Edit Rationale";
			
			
			//Multi Delete Data for user
			static String multiDeleteSearchData="Selenium 1111";
			
			
			
			@BeforeClass
			public void setUp() throws IOException  
			{
				//Create FireFox Profile object
				FirefoxProfile profile = new FirefoxProfile();
		 
				//Set Location to store files after downloading.
				profile.setPreference("browser.download.dir", "D:\\WebDriverDownloads");
				profile.setPreference("browser.download.folderList", 2);
		 
				//Set Preference to not show file download confirmation dialogue using MIME types Of different file extension types.
				profile.setPreference("browser.helperApps.neverAsk.saveToDisk", 
				    "application/pdf;"); 
		 
				profile.setPreference( "browser.download.manager.showWhenStarting", false );
				profile.setPreference( "pdfjs.disabled", true );
		 
				//Pass FProfile parameter In webdriver to use preferences to download file.
				 FirefoxOptions options = new FirefoxOptions();
				    options.setProfile(profile);
				driver = new FirefoxDriver(options);  
				driver.get("http://192.168.1.45:8092");
				//parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\utility.properties");
			}
		
			@Test(priority=1)
			public void Login() throws InterruptedException
			{
				//Lets see how we can find the first name field
				WebElement username = driver.findElement(By.id("username"));
				WebElement password = driver.findElement(By.id("password"));
				username.sendKeys("thiyagu1");
				Thread.sleep(500);
				password.sendKeys("123456");
				Thread.sleep(500);
				driver.findElement(By.id("loginsubmit")).click();
				Thread.sleep(500);
				driver.get("http://192.168.1.45:8092/changecontrol");
			}
				
			
			
			
			@Test(priority=2,invocationCount=2)
			public void CreateChangeControl() throws InterruptedException, SQLException, ClassNotFoundException, IOException
			{
				Thread.sleep(2000);
				//driver.get("http://192.168.1.45:8092/utility");
				///parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\utility.properties");
				//Thread.sleep(1000);
				driver.findElement(By.id("addControl")).click();
				Thread.sleep(1000);
				String Name = changeControlCREATE;
				WebElement userName = driver.findElement(By.id("changeControlNo"));
				userName.sendKeys(Name);
				Thread.sleep(500);
				
				
				driver.findElement(By.id("ctrlSummary")).sendKeys(summaryCREATE);
				Thread.sleep(500);
				
				driver.findElement(By.id("ctrlDesc")).sendKeys(descriptionCREATE);
				Thread.sleep(500);
				
				driver.findElement(By.id("ctrlRationale")).sendKeys(rationaleCREATE);
				Thread.sleep(500);
				
				//Module Name
			//	WebElement moduleName  = driver.findElement(By.id("modulesInvolved"));
			//	Select SelectmoduleName  = new Select(moduleName);
			//	SelectmoduleName.selectByVisibleText("Site Information");
				driver.findElement(By.id("modulesInvolved")).sendKeys("site",Keys.ENTER);
				driver.findElement(By.id("modulesInvolved")).sendKeys(Keys.TAB);
				Thread.sleep(1000);
				
				//SubModule Name
				//WebElement submoduleName  = driver.findElement(By.id("subModulesInvolved"));
				//Select SelectsubmoduleName  = new Select(submoduleName);
				//SelectsubmoduleName.selectByVisibleText("Equipment");
				driver.findElement(By.id("subModulesInvolved")).sendKeys("equip",Keys.ENTER);
				driver.findElement(By.id("subModulesInvolved")).sendKeys(Keys.TAB);
				Thread.sleep(1000);
				
				driver.findElement(By.id("newRecordName1")).sendKeys(equipmentName);
				Thread.sleep(500);
				
				
				WebElement submit = driver.findElement(By.id("first-lvl"));
				submit.click();
				Thread.sleep(1000);
				
				//if duplicate equipment name
				if( driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Change Control Number '"+Name+"' already exists!"))
				{
					String getduplicatename = driver.findElement(By.className("notify-msg")).getText();
					driver.findElement(By.className("custom-notify-close")).click();
				
				Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
				for(int k=1;k<1000;k++)
				{
					j.add(k);
				}
				
				Thread.sleep(500);
				if(getduplicatename.equalsIgnoreCase("Change Control Number '"+Name+"' already exists!"))
				{
					for(Integer i:j)
					{
						driver.findElement(By.id("changeControlNo")).clear();
						driver.findElement(By.id("changeControlNo")).sendKeys(Name+i);
						Thread.sleep(500);
						driver.findElement(By.id("first-lvl")).click();
						Thread.sleep(500);
						if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Change Control Number '"+Name+"' already exists!"))
						{
							String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
							System.out.println("Name duplicate: "+nameduplicate);
							driver.findElement(By.className("custom-notify-close")).click();
							if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Change Control Number '"+Name+"' already exists!"))
							{
								continue;
							}
						}
								System.out.println("Not duplicate so break the loop");
								break;
						}
					}
				}// closing if loop duplicate equipment
				
				Thread.sleep(2000);
				String successMsg = null;
				if(driver.findElements(By.className("notify-msg")).size()!=0)
				{
					successMsg = driver.findElement(By.className("notify-msg")).getText();
				}
				Assert.assertEquals(successMsg,"Change Control saved successfully");
				
				if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
				}
				Thread.sleep(500);
			} // closing create User method
			
	
			
			@Test(priority=3)
			public void EditChangeControl() throws InterruptedException, SQLException, ClassNotFoundException
			{
				Thread.sleep(2000);
				driver.findElement(By.id("dLabel")).click();
				Thread.sleep(500);
				//driver.findElement(By.className("dropdown-item")).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
				driver.findElement(By.className("dropdown-item")).sendKeys(Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ENTER);
				//Thread.sleep(300);
				
				String getsummary = driver.findElement(By.id("ctrlSummary")).getAttribute("value");
				Assert.assertEquals(getsummary, summaryCREATE);
				driver.findElement(By.id("ctrlSummary")).clear();
				driver.findElement(By.id("ctrlSummary")).sendKeys(summaryEDIT);
				Thread.sleep(500);
				 
				String getDesc = driver.findElement(By.id("ctrlDesc")).getAttribute("value");
				Assert.assertEquals(getDesc, descriptionCREATE);
				driver.findElement(By.id("ctrlDesc")).clear();
				driver.findElement(By.id("ctrlDesc")).sendKeys(descriptionEDIT);
				Thread.sleep(500);
				
				String getRationale = driver.findElement(By.id("ctrlRationale")).getAttribute("value");
				Assert.assertEquals(getRationale, rationaleCREATE);
				driver.findElement(By.id("ctrlRationale")).clear();
				driver.findElement(By.id("ctrlRationale")).sendKeys(rationaleEDIT);
				Thread.sleep(500);
				
				//Module Name
				Thread.sleep(500);
				WebElement modulename = driver.findElement(By.id("modulesInvolved"));
				Select Selectmodulename  = new Select(modulename);
				WebElement getoption = Selectmodulename.getFirstSelectedOption();
				String selectedModule = getoption.getText();
				Assert.assertEquals(selectedModule, "Site Information");
				Thread.sleep(500);
				
				//SubModule Name
				Thread.sleep(500);
				WebElement submodulename = driver.findElement(By.id("subModulesInvolved"));
				Select Selectsubmodulename  = new Select(submodulename);
				WebElement getsubmodoption = Selectsubmodulename.getFirstSelectedOption();
				String selectedsubModule = getsubmodoption.getText();
				Assert.assertEquals(selectedsubModule, "Equipment");
				Thread.sleep(500);
				
				String equipname = driver.findElement(By.id("newRecordName1")).getAttribute("value");
				Assert.assertEquals(equipname, equipname);
				Thread.sleep(500);
				
				WebElement submit = driver.findElement(By.id("first-lvl"));
				submit.click();
				Thread.sleep(1000);
				
				driver.findElement(By.name("comments")).sendKeys("Edit utility");
				Thread.sleep(500);
				driver.findElement(By.name("comments")).sendKeys(Keys.SHIFT,Keys.TAB,password);
				Thread.sleep(500);
				driver.findElement(By.id("ackSubmit")).click();
				
				Thread.sleep(2000);
				String successMsg = null;
				if(driver.findElements(By.className("notify-msg")).size()!=0)
				{
					successMsg = driver.findElement(By.className("notify-msg")).getText();
				}
				Assert.assertEquals(successMsg,"Utility updated successfully");
				
				if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
				}
				Thread.sleep(500);
			}
			
			
	
				
			
			@Test(priority=4)
			public void SingleDeleteChangeControl() throws InterruptedException, IOException
			{
				Thread.sleep(2000);
				driver.findElement(By.id("dLabel")).click();
				Thread.sleep(500);
				//driver.findElement(By.className("dropdown-item")).sendKeys(Keys.ENTER);
				driver.findElement(By.className("dropdown-item")).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
				
				Thread.sleep(1000);
				//driver.findElement(By.xpath(".//*[@id='dropdownactionDoc']/li[3]/a")).click(); // Click edit equipment button
				//Thread.sleep(1000);
				driver.findElement(By.xpath(".//*[@id='openAckModal']")).click();//click Yes in popup
				Thread.sleep(500);
				driver.findElement(By.id("ackChangeControlNo")).sendKeys("111");
				Thread.sleep(500);
				driver.findElement(By.id("ackChangeControlNo")).sendKeys(Keys.TAB +password);
				Thread.sleep(500);
				driver.findElement(By.id("comments")).sendKeys("Delete single user");
				Thread.sleep(500);
				driver.findElement(By.id("ackSubmit")).click();
				Thread.sleep(2000);
				
				String successMsg = null;
				if(driver.findElements(By.className("notify-msg")).size()!=0)
				{
					successMsg = driver.findElement(By.className("notify-msg")).getText();
				}
				Assert.assertEquals(successMsg,"change Request deleted successfully");
				
				String className = this.getClass().getName(); // get current class name - for screenshot
				String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
				Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
				if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
				}
				Thread.sleep(600);
			}
			
			
			
			
			@Test(priority=5)
			public void MultiDeleteChangeControl() throws InterruptedException, IOException
			{
				Thread.sleep(2000);
				driver.findElement(By.id("searchEquipment")).sendKeys(multiDeleteSearchData);
				Thread.sleep(1000);
				driver.findElement(By.id("example-select-all")).click();
				Thread.sleep(2000);
				driver.findElement(By.id("deleteSelectedUtility")).click(); // multi delete
				Thread.sleep(500);
				driver.findElement(By.xpath(".//*[@id='openAckModal']")).click();//click Yes in popup
				Thread.sleep(500);
				driver.findElement(By.id("ackChangeControlNo")).sendKeys("222");
				Thread.sleep(500);
				driver.findElement(By.id("ackChangeControlNo")).sendKeys(Keys.TAB +password);
				Thread.sleep(500);
				driver.findElement(By.id("comments")).sendKeys("Delete multple user");
				Thread.sleep(500);
				driver.findElement(By.id("ackSubmit")).click();
				Thread.sleep(2000);
				
				String successMsg = null;
				if(driver.findElements(By.className("notify-msg")).size()!=0)
				{
					successMsg = driver.findElement(By.className("notify-msg")).getText();
				}
				Assert.assertEquals(successMsg,"change Request deleted successfully");
				
				String className = this.getClass().getName(); // get current class name - for screenshot
				String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
				Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
				if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
				}
				Thread.sleep(600);
			}
			
			
	
			/*
			
			
			@Test(priority=6)
			public void ExportUtility() throws Exception
			{
				Utils.ExportPDF(driver);
			}
			
			
			
			@Test(priority=7)
			public void viewUtility() throws Exception
			{
				Thread.sleep(1000);
				driver.findElement(By.className("eqpNameTag")).click();
				Thread.sleep(1000);
				String name = driver.findElement(parser.getbjectLocator("UtilityName")).getText();
				System.out.println("Name: "+name);
				if(org.apache.commons.lang3.StringUtils.isEmpty(name)) {
					throw new Exception("Utility Name is Empty");
				}
				//Location Name
				Thread.sleep(500);
				WebElement getlocationName = driver.findElement(parser.getbjectLocator("UtilityLocationName"));
				Select Selectlocation  = new Select(getlocationName);
				WebElement locationoption = Selectlocation.getFirstSelectedOption();
				String selectedlocation = locationoption.getText();
				System.out.println("Location Name: "+selectedlocation);
				Thread.sleep(500);
				
				//SOP Number
				WebElement SOPNo = driver.findElement(parser.getbjectLocator("UtilitySOPNumber"));
				Select Selectsop  = new Select(SOPNo);
				WebElement sopoption = Selectsop.getFirstSelectedOption();
				String selectedSOP = sopoption.getText();
				System.out.println("SOP Number: "+selectedSOP);
				Assert.assertEquals(selectedSOP,sopNumberCREATE);
				Thread.sleep(500);
				
				//Qualification Documents 
				WebElement QaDoc = driver.findElement(parser.getbjectLocator("QualificationDocuments"));
				Select SelectQaDoc  = new Select(QaDoc);
				WebElement QaDocoption = SelectQaDoc.getFirstSelectedOption();
				String selectedQADoc = QaDocoption.getText();
				System.out.println("Qualification Documents : "+selectedQADoc);
				Assert.assertEquals(selectedQADoc,qualificationDocCREATE);
				Thread.sleep(500);
				
				String LastQuaDate = driver.findElement(parser.getbjectLocator("DateofLastQualification")).getText();
				System.out.println("Date of Last Qualification : "+LastQuaDate);
				if(org.apache.commons.lang3.StringUtils.isEmpty(LastQuaDate)) {
					throw new Exception("Date of Last Qualification field is Empty");
				}
				Assert.assertEquals(LastQuaDate,dateofLastQuaCREATE);
				Thread.sleep(500);
				driver.findElement(By.className("cancel-btn")).click();
			}
			
			*/
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
	
}
