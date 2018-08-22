package com.eDocs.SanityTesting;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Message;
import com.eDocs.Utils.RepositoryParser;
import com.eDocs.Utils.Utils;

public class grid {
  
			//public static RemoteWebDriver driver;
			
			private RepositoryParser parser;
			private WebDriver driver = Constant.driver;
			//WebDriver driver;
			public String password = Constant.sitepassword;
		
			//Datas for create User
			static String utilityNameCREATE = "SeleniumUtility";
			static int locationNameCREATE = 1;
			static String sopNumberCREATE = "1";
			static String qualificationDocCREATE  = "CIP";
			static String dateofLastQuaCREATE  = "04/04/1989";
			static String changeControlCREATE  = "CreateUtil 111";
			static String SerialNo = "111";
			
			//Datas for Edit User
			static int locationNameEDIT = 1;
			static String sopNumberEDIT = "2";
			static String qualificationDocEDIT  = "COP";
			static String dateofLastQuaEDIT  = "05/04/1989";
			
			//Multi Delete Data for user
			static String multiDeleteSearchData="SeleniumUtility";
			
			
			String baseUrl;
			@Parameters({ "browser","portNo"})
			@BeforeTest()
			public void setUp(String browser, String portNo) throws IOException  
			{
				if(portNo.equals("5567"))
				{
					//driver = Browser.getDriver(browser);
					//driver.get(Constant.URL);
					baseUrl= Constant.URL;
					parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\utility.properties");
					DesiredCapabilities capability = DesiredCapabilities.chrome();
					//capability.setCapability("marionette", true);
					capability.setBrowserName(browser);
					capability.setPlatform(Platform.ANY);
					driver = new RemoteWebDriver(new URL("http://192.168.1.45:5567/wd/hub"),capability);
					driver.get(baseUrl);
				}
				
				if(portNo.equals("5566"))
				{
					//driver = Browser.getDriver(browser);
					//driver.get(Constant.URL);
					baseUrl= Constant.URL;
					parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\utility.properties");
					DesiredCapabilities capability = DesiredCapabilities.chrome();
					//capability.setCapability("marionette", true);
					capability.setBrowserName(browser);
					capability.setPlatform(Platform.ANY);
					driver = new RemoteWebDriver(new URL("http://192.168.1.26:5566/wd/hub"),capability);
					driver.get(baseUrl);
				}
			}
		
			
			@Test(priority=1)
			public void Login() throws InterruptedException, IOException
			{
				//Lets see how we can find the first name field
				Thread.sleep(1000);
				WebElement username = driver.findElement(By.id("username"));
				WebElement password = driver.findElement(By.id("password"));
				username.sendKeys(Constant.siteusername);
				Thread.sleep(500);
				password.sendKeys(Constant.sitepassword);
				Thread.sleep(500);
				driver.findElement(By.id("loginsubmit")).click();
				Thread.sleep(1000);
		  		if (driver.findElements(By.id("forcelogin")).size()!=0)
		  		{
		  			// Force Login
		  			//Thread.sleep(1000);
		  			driver.findElement(By.id("forcelogin")).click();
		  		}
		  		Thread.sleep(1000);
				driver.get(Constant.URL+"/utility");
				//Thread.sleep(500);
			}
		

				
			
			@Test(priority=12,invocationCount=2)
			public void CreateUtility() throws Exception
			{
				Thread.sleep(2000);
				driver.get(Constant.URL+"/utility");
				parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\utility.properties");
				Thread.sleep(1000);
				driver.findElement(By.id("addUtility")).click();
				Thread.sleep(1000);
				String Name = utilityNameCREATE;
				WebElement userName = driver.findElement(parser.getbjectLocator("UtilityName"));
				userName.sendKeys(Name);
				Thread.sleep(500);
				
				//Location Name
				userName.sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
				Thread.sleep(500);
				
				//Serial No
				WebElement serialNO = driver.findElement(By.name("serialOrAssetNo"));
				serialNO.sendKeys(SerialNo);
				Thread.sleep(500);
				//SOP Number 
				userName.sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.ENTER,Keys.ENTER);
				Thread.sleep(500);
				
				//Qualification Documents 	 
				userName.sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.ENTER,Keys.ENTER);
				Thread.sleep(500);
				
				WebElement submit = driver.findElement(parser.getbjectLocator("UtilitySubmit"));
				submit.click();
				Thread.sleep(1000);
				
				
				Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
				for(int k=5;k<1000;k++)
				{
					j.add(k);
				}
				
				if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Utility '"+Name+"' already exists!"))
				{
					String getduplicatename = driver.findElement(By.className("notify-msg")).getText();
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				
				Thread.sleep(500);
				if(getduplicatename.equalsIgnoreCase("Utility '"+Name+"' already exists!"))
				{
					for(Integer i:j)
					{
						//driver.findElement(parser.getbjectLocator("ActiveIngredientName")).clear();
						userName.clear();
						Thread.sleep(200);
						userName.sendKeys(Name+i);
						//driver.findElement(parser.getbjectLocator("ActiveIngredientName")).sendKeys(Name+i);
						Thread.sleep(500);
						submit.click();
						//driver.findElement(parser.getbjectLocator("APIsubmit")).click();
						Thread.sleep(500);
						if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Utility '"+Name+i+"' already exists!"))
						{
							String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
							System.out.println("Name duplicate: "+nameduplicate);
							driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
							if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Utility '"+Name+i+"' already exists!"))
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
				if( driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Equipment Identification Number '"+SerialNo+"' already exists!"))
				{
					String getduplicateID = driver.findElement(By.className("notify-msg")).getText();
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				System.out.println("getduplicatename: "+getduplicateID);
				if(getduplicateID.equalsIgnoreCase("Equipment Identification Number '"+SerialNo+"' already exists!"))
				{
					for(Integer i:j)
					{
						serialNO.clear();
						Thread.sleep(200);
						serialNO.sendKeys(SerialNo+i);
						Thread.sleep(500);
						//driver.findElement(parser.getbjectLocator("APIsubmit")).click();
						submit.click();
						Thread.sleep(500);
						if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Equipment Identification Number '"+SerialNo+i+"' already exists!"))
						{
							String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
							driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
							if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Equipment Identification Number '"+SerialNo+i+"' already exists!"))
							{
								continue;
							}
						}
								System.out.println("Not duplicate so break the loop");
								break;
						}
				}
				
			}
				
				//custom loop
				//WebElement submitbutton = driver.findElement(parser.getbjectLocator("UtilitySubmit")); //submitEquipmentSamplingDetails
				Thread.sleep(500);
				//System.out.println("submitbutton: "+submitbutton);
			if(driver.findElement(By.id("saveCustomDetails")).isDisplayed())
			{
				System.out.println("Text: "+driver.findElement(By.id("saveCustomDetails")).getText());
				//if(driver.findElement(By.id("saveCustomDetails")).getText().equalsIgnoreCase("Submit"))
				//{
				//	System.out.println("No Custom loop");
				//	driver.findElement(By.id("saveCustomDetails")).click();
				//	Thread.sleep(500);
					
				//}else
				//{
					//submitbutton.click();
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
							driver.findElement(By.id("saveCustomDetails")).click();
							
				//}
			}
				
				Thread.sleep(1500);
				String successMsg = null;
				if(driver.findElements(By.className("notify-msg")).size()!=0)
				{
					successMsg = driver.findElement(By.className("notify-msg")).getText();
				}
				Assert.assertEquals(successMsg,Message.utilityCREATE);
				
				if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				}
				Thread.sleep(500);
			} // closing create User method
			
	
			
			@Test(priority=13)
			public void EditUtility() throws Exception
			{
				Thread.sleep(2000);
				driver.findElement(By.id("dLabel")).click();
				Thread.sleep(500);
				//driver.findElement(By.className("dropdown-item")).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
				//driver.findElement(By.className("dropdown-item")).sendKeys(Keys.ENTER);
				driver.findElement(By.linkText("Edit")).click();
				//Thread.sleep(300);
				//Location Name
				Thread.sleep(500);
				WebElement getlocationName = driver.findElement(parser.getbjectLocator("UtilityLocationName"));
				Select Selectlocation  = new Select(getlocationName);
				WebElement locationoption = Selectlocation.getFirstSelectedOption();
				String selectedlocation = locationoption.getText();
				if(selectedlocation.equals(""))
				{
					throw new Exception();
				}
					
				//Assert.assertEquals(selectedlocation, locationNameCREATE);
				//Selectlocation.selectByIndex(locationNameEDIT);
				Thread.sleep(500);
				
				//SOP Number
				WebElement SOPNo = driver.findElement(parser.getbjectLocator("UtilitySOPNumber"));
				Select Selectsop  = new Select(SOPNo);
				WebElement sopoption = Selectsop.getFirstSelectedOption();
				String selectedSOP = sopoption.getText();
				//Assert.assertEquals(selectedSOP, sopNumberCREATE);
				if(selectedSOP.equals(""))
				{
					throw new Exception();
				}
				Thread.sleep(500);
				
				//Qualification Documents 
				WebElement QaDoc = driver.findElement(parser.getbjectLocator("QualificationDocuments"));
				Select SelectQaDoc  = new Select(QaDoc);
				WebElement QaDocoption = SelectQaDoc.getFirstSelectedOption();
				String selectedQADoc = QaDocoption.getText();
				//Assert.assertEquals(selectedQADoc, qualificationDocCREATE);
				//SelectQaDoc.selectByVisibleText(qualificationDocEDIT);
				if(selectedQADoc.equals(""))
				{
					throw new Exception();
				}
				Thread.sleep(500);
				
				//Get Date
				String getDate = driver.findElement(parser.getbjectLocator("DateofLastQualification")).getAttribute("value");
				if(getDate.equals(""))
				{
					throw new Exception();
				}
				
				//Assert.assertEquals(getDate, dateofLastQuaCREATE);
				//driver.findElement(parser.getbjectLocator("DateofLastQualification")).clear();
				//driver.findElement(parser.getbjectLocator("DateofLastQualification")).sendKeys(dateofLastQuaEDIT);
				Thread.sleep(500);
				
				//WebElement submit = driver.findElement(parser.getbjectLocator("UtilitySubmit"));
				WebElement submit = driver.findElement(By.id("utilitySubmit"));
				submit.click();
				Thread.sleep(1000);
				
				if(driver.findElement(By.id("saveCustomDetails")).isDisplayed())
				{
					//if(driver.findElement(By.id("saveCustomDetails")).getText().equalsIgnoreCase("Submit"))
					//{
					//	System.out.println("No Custom loop");
					//	driver.findElement(By.id("saveCustomDetails")).click();
					//	Thread.sleep(500);
					//}else
					//{
						//submitbutton.click();
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
								driver.findElement(By.id("saveCustomDetails")).click();
								Thread.sleep(1000);
					//}
				}
				
				driver.findElement(By.name("comments")).sendKeys("Edit utility");
				Thread.sleep(500);
				driver.findElement(By.name("password")).sendKeys(password);
				//driver.findElement(By.name("comments")).sendKeys(Keys.SHIFT,Keys.TAB,password);
				Thread.sleep(500);
				driver.findElement(By.id("ackSubmit")).click();
				
				Thread.sleep(2000);
				String successMsg = null;
				if(driver.findElements(By.className("notify-msg")).size()!=0)
				{
					successMsg = driver.findElement(By.className("notify-msg")).getText();
				}
				Assert.assertEquals(successMsg,Message.utilityEDIT);
				
				if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				}
				Thread.sleep(500);
			}
			
			
	
			
			
			
			@Test(priority=14)
			public void SingleDeleteUtility() throws InterruptedException, IOException
			{
				Thread.sleep(2000);
				driver.findElement(By.id("dLabel")).click();
				Thread.sleep(500);
				//driver.findElement(By.className("dropdown-item")).sendKeys(Keys.ENTER);
				//driver.findElement(By.className("dropdown-item")).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
				driver.findElement(By.linkText("Delete")).click();
				
				Thread.sleep(1000);
				//driver.findElement(By.xpath(".//*[@id='dropdownactionDoc']/li[3]/a")).click(); // Click edit equipment button
				//Thread.sleep(1000);
				//driver.findElement(By.xpath(".//*[@id='openAckModal']")).click();//click Yes in popup
				//Thread.sleep(500);
				driver.findElement(By.name("ackChangeControlNo")).sendKeys("111");
				Thread.sleep(500);
				//driver.findElement(By.name("ackChangeControlNo")).sendKeys(Keys.TAB +password);
				driver.findElement(By.name("password")).sendKeys(password);
				Thread.sleep(500);
				driver.findElement(By.id("comments")).sendKeys("Delete single user");
				Thread.sleep(500);
				//driver.findElement(By.xpath(".//*[@id='dynamicModal']/div[3]/div/button[2]")).click();
				driver.findElement(By.id("comments")).sendKeys(Keys.TAB,Keys.TAB,Keys.ENTER);
				Thread.sleep(2000);
				
				String successMsg = null;
				if(driver.findElements(By.className("notify-msg")).size()!=0)
				{
					successMsg = driver.findElement(By.className("notify-msg")).getText();
				}
				Assert.assertEquals(successMsg,Message.utilityDELETE);
				
				String className = this.getClass().getName(); // get current class name - for screenshot
				String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
				Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
				if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				}
				Thread.sleep(600);
			}
			
			
			/*
			@Test(priority=15)
			public void ExportUtility() throws Exception
			{
				Thread.sleep(1000);
				//driver.findElement(By.id("searchDataTable")).clear();
				Utils.ExportPDF(driver);
			}*/
			
			@Test(priority=16)
			public void MultiDeleteUtility() throws Exception
			{
				Thread.sleep(2000);
				driver.findElement(By.id("searchDataTable")).clear();
				driver.findElement(By.id("searchDataTable")).sendKeys(multiDeleteSearchData);
				Thread.sleep(1000);
				driver.findElement(By.id("example-select-all")).click();
				Thread.sleep(2000);
				driver.findElement(By.id("deleteSelectedUtility")).click(); // multi delete
				Thread.sleep(500);
				//driver.findElement(By.xpath(".//*[@id='openAckModal']")).click();//click Yes in popup
				//Thread.sleep(500);
				driver.findElement(By.name("ackChangeControlNo")).sendKeys("222");
				Thread.sleep(500);
				driver.findElement(By.name("ackChangeControlNo")).sendKeys(Keys.TAB +password);
				Thread.sleep(500);
				driver.findElement(By.id("comments")).sendKeys("Delete Mulitple Utility");
				Thread.sleep(500);
				driver.findElement(By.id("comments")).sendKeys(Keys.TAB,Keys.TAB,Keys.ENTER);
				//driver.findElement(By.cssSelector("btn.blue-btn.red.waves-effect.deleteData")).click();
				Thread.sleep(2000);
				
				String successMsg = null;
				if(driver.findElements(By.className("notify-msg")).size()!=0)
				{
					successMsg = driver.findElement(By.className("notify-msg")).getText();
				}
				Assert.assertEquals(successMsg,Message.utilityDELETE);
				
				String className = this.getClass().getName(); // get current class name - for screenshot
				String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
				Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
				if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				}
				Thread.sleep(600);
				
			}
			
			
			
			@Test(priority=17)
			public void CreateUtilityforEquip() throws Exception
			{
				Thread.sleep(2000);
				//driver.get("http://192.168.1.45:8092/utility");
				///parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\utility.properties");
				//Thread.sleep(1000);
				driver.findElement(By.id("addUtility")).click();
				Thread.sleep(1000);
				String Name = utilityNameCREATE;
				WebElement userName = driver.findElement(parser.getbjectLocator("UtilityName"));
				userName.sendKeys(Name);
				Thread.sleep(500);
				
				//Location Name
				//WebElement UtilityLocationName  = driver.findElement(parser.getbjectLocator("UtilityLocationName"));
				//Select SelecttoUtilityLocationName  = new Select(UtilityLocationName);
				//SelecttoUtilityLocationName.selectByIndex(locationNameCREATE);
				userName.sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
				Thread.sleep(500);
				
				//Serial No
				WebElement serialNO = driver.findElement(By.name("serialOrAssetNo"));
				serialNO.sendKeys(SerialNo);
				Thread.sleep(500);
				//SOP Number 
				//WebElement UtilitySOPNumber  = driver.findElement(parser.getbjectLocator("UtilitySOPNumber"));
				//Select SelectUtilitySOPNumber  = new Select(UtilitySOPNumber);
				//SelectUtilitySOPNumber.selectByValue(sopNumberCREATE);
				userName.sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.ENTER,Keys.ENTER);
				Thread.sleep(500);
				
				//Qualification Documents 	 
				//WebElement QualificationDocuments  = driver.findElement(parser.getbjectLocator("QualificationDocuments"));
				//Select SelectQualificationDocuments  = new Select(QualificationDocuments);
				//SelectQualificationDocuments.selectByVisibleText(qualificationDocCREATE);
				userName.sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.ENTER,Keys.ENTER);
				Thread.sleep(500);
				
				//driver.findElement(parser.getbjectLocator("DateofLastQualification")).sendKeys(dateofLastQuaCREATE);
				//Thread.sleep(500);
				
				WebElement submit = driver.findElement(parser.getbjectLocator("UtilitySubmit"));
				submit.click();
				Thread.sleep(1000);
				
				
				Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
				for(int k=5;k<1000;k++)
				{
					j.add(k);
				}
				
				if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Utility '"+Name+"' already exists!"))
				{
					String getduplicatename = driver.findElement(By.className("notify-msg")).getText();
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				
				Thread.sleep(500);
				if(getduplicatename.equalsIgnoreCase("Utility '"+Name+"' already exists!"))
				{
					for(Integer i:j)
					{
						//driver.findElement(parser.getbjectLocator("ActiveIngredientName")).clear();
						userName.clear();
						Thread.sleep(200);
						userName.sendKeys(Name+i);
						//driver.findElement(parser.getbjectLocator("ActiveIngredientName")).sendKeys(Name+i);
						Thread.sleep(500);
						submit.click();
						//driver.findElement(parser.getbjectLocator("APIsubmit")).click();
						Thread.sleep(500);
						if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Utility '"+Name+i+"' already exists!"))
						{
							String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
							System.out.println("Name duplicate: "+nameduplicate);
							driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
							if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Utility '"+Name+i+"' already exists!"))
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
				if( driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Equipment Identification Number '"+SerialNo+"' already exists!"))
				{
					String getduplicateID = driver.findElement(By.className("notify-msg")).getText();
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				System.out.println("getduplicatename: "+getduplicateID);
				if(getduplicateID.equalsIgnoreCase("Equipment Identification Number '"+SerialNo+"' already exists!"))
				{
					for(Integer i:j)
					{
						serialNO.clear();
						Thread.sleep(200);
						serialNO.sendKeys(SerialNo+i);
						Thread.sleep(500);
						//driver.findElement(parser.getbjectLocator("APIsubmit")).click();
						submit.click();
						Thread.sleep(500);
						if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Equipment Identification Number '"+SerialNo+i+"' already exists!"))
						{
							String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
							driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
							if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Equipment Identification Number '"+SerialNo+i+"' already exists!"))
							{
								continue;
							}
						}
								System.out.println("Not duplicate so break the loop");
								break;
						}
				}
				
			}
				
				//custom loop
				//WebElement submitbutton = driver.findElement(parser.getbjectLocator("UtilitySubmit")); //submitEquipmentSamplingDetails
				Thread.sleep(500);
				//System.out.println("submitbutton: "+submitbutton);
			if(driver.findElement(By.id("saveCustomDetails")).isDisplayed())
			{
				System.out.println("Text: "+driver.findElement(By.id("saveCustomDetails")).getText());
				//if(driver.findElement(By.id("saveCustomDetails")).getText().equalsIgnoreCase("Submit"))
				//{
				//	System.out.println("No Custom loop");
				//	driver.findElement(By.id("saveCustomDetails")).click();
				//	Thread.sleep(500);
					
				//}else
				//{
					//submitbutton.click();
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
							driver.findElement(By.id("saveCustomDetails")).click();
							
				//}
			}
				
				Thread.sleep(1500);
				String successMsg = null;
				if(driver.findElements(By.className("notify-msg")).size()!=0)
				{
					successMsg = driver.findElement(By.className("notify-msg")).getText();
				}
				Assert.assertEquals(successMsg,Message.utilityCREATE);
				
				if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				}
				Thread.sleep(500);
			} // closing create User method
			
			
			
			
		/*	
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
