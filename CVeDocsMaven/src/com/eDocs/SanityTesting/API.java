package com.eDocs.SanityTesting;

import static org.junit.Assert.assertArrayEquals;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Message;
import com.eDocs.Utils.RepositoryParser;
import com.eDocs.Utils.Utils;

public class API {
  
		
			private RepositoryParser parser;
			private WebDriver driver = Constant.driver;;
			public String password = Constant.sitepassword;
		
			//Datas for create API
			static String ActiveIngredientNameCREATE = "Test API";
			static String ActiveIDCREATE = "Active ID for create";
			static String SolubilityinWaterCREATE = "1";
			static String ChangeControlNumberCREATE  = "Change cntrl create 111";
			static String HBELValueCREATE  = "1.32";
			static String noofCarbonCREATE = "1";
			static String activeMolarCREATE  = "2";
			static String organicCarbonCREATE  = "3";
			
			//Datas for Edit API
			static String ActiveIDEDIT = "Active ID for edit";
			static String SolubilityinWaterEDIT = "2";
			static String ChangeControlNumberEDIT  = "Change cntrl edit 222";
			static String HBELValueEDIT  = "1.40";
			static String EditComments = "Edit all the API details";
			static String noofCarbonEDIT = "2";
			static String activeMolarEDIT  = "3";
			static String organicCarbonEDIT  = "4";
			
			//Delete Datas for API
			static String changeControlDELETE="Delete single API";
			
			//Multi Delete Data for API
			static String multiDeleteSearchData="Test API";
			
		
			
			@BeforeClass
			public void setUp() throws IOException  
			{
				//driver = new FirefoxDriver();
				driver.get(Constant.URL);
				parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\Product.properties");
			}
		
			@Test(priority=1)
			public void Login() throws InterruptedException
			{
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
		  			Thread.sleep(1000);
		  			driver.findElement(By.id("forcelogin")).click();
		  		}
		  		Thread.sleep(1500);
			}
				
			
			@Test(priority=35,invocationCount=2)
			public void CreateAPI() throws InterruptedException, SQLException, ClassNotFoundException, IOException
			{
				Thread.sleep(2000);
				driver.get(Constant.URL+"/active-ingredients");
				parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\Product.properties");
				Thread.sleep(1000);
				driver.findElement(By.id("addApi")).click();
				Thread.sleep(1000);
				String Name = ActiveIngredientNameCREATE;
				WebElement APIName = driver.findElement(parser.getbjectLocator("ActiveIngredientName"));
				APIName.sendKeys(Name);
				Thread.sleep(500);
				
				driver.findElement(parser.getbjectLocator("ActiveID")).sendKeys(ActiveIDCREATE);
				Thread.sleep(1500);
				//select HBEL Term
				driver.findElement(parser.getbjectLocator("ActiveID")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
				Thread.sleep(1000);
				driver.findElement(parser.getbjectLocator("ActiveID")).sendKeys(Keys.TAB,Keys.TAB,Keys.ENTER,Keys.ENTER);
				Thread.sleep(1000);
				driver.findElement(By.id("hbelValue1")).sendKeys(HBELValueCREATE); 
				Thread.sleep(500);
				driver.findElement(By.name("numberofCarbonAtoms")).sendKeys(noofCarbonCREATE);
				Thread.sleep(500);
				//scroll down
				driver.findElement(By.name("numberofCarbonAtoms")).sendKeys(Keys.TAB,Keys.TAB,Keys.TAB);
				Thread.sleep(500);
				driver.findElement(By.name("activeMolarMass")).sendKeys(activeMolarCREATE);
				driver.findElement(By.name("activeMolarMass")).sendKeys(Keys.TAB);
				Thread.sleep(500);	
				
				if(driver.findElements(parser.getbjectLocator("SolubilityinWater")).size()!=0)
				{
					driver.findElement(parser.getbjectLocator("SolubilityinWater")).sendKeys(SolubilityinWaterCREATE);
					Thread.sleep(500);
				}else if(driver.findElements(By.id("activeIngredientsolubilityWaterSelect")).size()!=0)
				{
					driver.findElement(By.name("organicCarbon")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
					Thread.sleep(500);
				}
				
				//driver.findElement(parser.getbjectLocator("APIChangeControlNumber")).sendKeys(ChangeControlNumberCREATE);
				//Thread.sleep(500);
				
				WebElement submit = driver.findElement(parser.getbjectLocator("APIsubmit"));
				submit.click();
				Thread.sleep(500);
				
				
				//if duplicate equipment name
				if( driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Active Ingredient '"+Name+"' already exists!"))
				{
					String getduplicatename = driver.findElement(By.className("notify-msg")).getText();
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				
				Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
				for(int k=5;k<1000;k++)
				{
					j.add(k);
				}
				
				Thread.sleep(500);
				if(getduplicatename.equalsIgnoreCase("Active Ingredient '"+Name+"' already exists!"))
				{
					for(Integer i:j)
					{
						driver.findElement(parser.getbjectLocator("ActiveIngredientName")).clear();
						driver.findElement(parser.getbjectLocator("ActiveIngredientName")).sendKeys(Name+i);
						Thread.sleep(500);
						driver.findElement(parser.getbjectLocator("APIsubmit")).click();
						Thread.sleep(500);
						if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Active Ingredient '"+Name+i+"' already exists!"))
						{
							String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
							System.out.println("Name duplicate: "+nameduplicate);
							driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
							if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Active Ingredient '"+Name+i+"' already exists!"))
							{
								continue;
							}
						}
								System.out.println("Not duplicate so break the loop");
								break;
						}
					}
				}// closing if loop duplicate equipment
				
				
				Thread.sleep(1000);
				String createAPI = driver.findElement(By.className("notify-msg")).getText();
				Assert.assertEquals(createAPI,Message.productAPICREATE);
				
				if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				}
				Thread.sleep(500);
			} // closing create API method
			
			
			
			
			
			@Test(priority=36)
			public void EditAPI() throws InterruptedException, SQLException, ClassNotFoundException
			{
				Thread.sleep(1000);
				driver.findElement(By.id("dLabel")).click();
				Thread.sleep(500);
				//driver.findElement(By.className("dropdown-item")).sendKeys(Keys.ENTER);
				driver.findElement(By.linkText("Edit")).click();
				Thread.sleep(300);
				String getactiverID = driver.findElement(parser.getbjectLocator("ActiveID")).getAttribute("value"); //verify text presented in the edit
				Assert.assertEquals(getactiverID, ActiveIDCREATE);
				Thread.sleep(300);
				driver.findElement(parser.getbjectLocator("ActiveID")).clear();
				Thread.sleep(300);
				driver.findElement(parser.getbjectLocator("ActiveID")).sendKeys(ActiveIDEDIT);
				Thread.sleep(1000);
				
				String getHBELvalue = driver.findElement(By.id("hbelValue1")).getAttribute("value");
				Thread.sleep(500);
				Assert.assertEquals(getHBELvalue, HBELValueCREATE); // verify HBEL value
				driver.findElement(By.id("hbelValue1")).clear();
				Thread.sleep(300);
				driver.findElement(By.id("hbelValue1")).sendKeys(HBELValueEDIT);
				Thread.sleep(500);
				
				//No of Carbon
				String noofCarbon = driver.findElement(By.name("numberofCarbonAtoms")).getAttribute("value");
				Thread.sleep(500);
				Assert.assertEquals(noofCarbon, noofCarbonCREATE); 
				driver.findElement(By.name("numberofCarbonAtoms")).clear();
				driver.findElement(By.name("numberofCarbonAtoms")).sendKeys(noofCarbonEDIT);
				Thread.sleep(500);
				
				//active molar mass
				String activemolar = driver.findElement(By.name("activeMolarMass")).getAttribute("value");
				Thread.sleep(500);
				Assert.assertEquals(activemolar, activeMolarCREATE);
				driver.findElement(By.name("activeMolarMass")).clear();
				driver.findElement(By.name("activeMolarMass")).sendKeys(activeMolarEDIT);
				Thread.sleep(500);	
			
				
				if(driver.findElements(parser.getbjectLocator("SolubilityinWater")).size()!=0)
				{
					String getsolubility = driver.findElement(parser.getbjectLocator("SolubilityinWater")).getAttribute("value");
					Thread.sleep(300);
					Assert.assertEquals(getsolubility, SolubilityinWaterCREATE);
					driver.findElement(parser.getbjectLocator("SolubilityinWater")).clear();
					driver.findElement(parser.getbjectLocator("SolubilityinWater")).sendKeys(SolubilityinWaterEDIT);
					Thread.sleep(500);
				}
				
				//driver.findElement(parser.getbjectLocator("APIChangeControlNumber")).clear();
				//Thread.sleep(500);
				//driver.findElement(parser.getbjectLocator("APIChangeControlNumber")).sendKeys(ChangeControlNumberEDIT);
				//Thread.sleep(500);
				
				WebElement submit = driver.findElement(parser.getbjectLocator("APIsubmit"));
				submit.click();
				Thread.sleep(1000);
				
				driver.findElement(By.id("comments")).sendKeys(EditComments);
				Thread.sleep(500);
				System.out.println("password: "+password);
				driver.findElement(By.id("comments")).sendKeys(Keys.SHIFT,Keys.TAB,password);
				Thread.sleep(500);
				driver.findElement(By.id("ackSubmit")).click();
				
				Thread.sleep(1000);
				String EditAPI = driver.findElement(By.className("notify-msg")).getText();
				System.out.println(EditAPI);
				Assert.assertEquals(EditAPI,Message.productAPIEDIT);
				
				if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				}
				Thread.sleep(500);
			}
			
			
			
			
			
			@Test(priority=37)
			public void SingleDeleteAPI() throws InterruptedException, IOException
			{
				Thread.sleep(2000);
				driver.get(Constant.URL+"/active-ingredients");
				Thread.sleep(1000);
				driver.findElement(By.id("dLabel")).click();
				Thread.sleep(500);
				//driver.findElement(By.xpath(".//*[@id='dropdownactionDoc']/li[3]/a")).click(); // Click edit equipment button
				Thread.sleep(1000);
				//driver.findElement(By.xpath(".//*[@id='openAckModal']")).click();//click Yes in popup
				driver.findElement(By.linkText("Delete")).click();
				Thread.sleep(500);
				driver.findElement(By.name("ackChangeControlNo")).sendKeys(changeControlDELETE);
				Thread.sleep(500);
				driver.findElement(By.name("ackChangeControlNo")).sendKeys(Keys.TAB +password);
				Thread.sleep(500);
				driver.findElement(By.id("comments")).sendKeys("Delete single equipment");
				Thread.sleep(500);
				driver.findElement(By.xpath(".//*[@id='dynamicModal']/div[3]/div/button[2]")).click();
				Thread.sleep(1500);
				String deletemsg = driver.findElement(By.className("notify-msg")).getText(); // get deleted esuccess message
				Assert.assertEquals(deletemsg,Message.productAPIDELETE);
				String className = this.getClass().getName(); // get current class name - for screenshot
				String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
				Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
				if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				}
				Thread.sleep(600);
			}
			
			
			
			
			@Test(priority=38)
			public void MultiDeleteAPI() throws InterruptedException, IOException
			{
				Thread.sleep(2000);
				driver.findElement(By.id("searchDataTable")).sendKeys(ActiveIngredientNameCREATE);
				Thread.sleep(1000);
				driver.findElement(By.id("example-select-all")).click();
				Thread.sleep(1000);
				driver.findElement(By.id("deleteSelectedActive")).click(); // multi delete
				Thread.sleep(500);
				//driver.findElement(By.xpath(".//*[@id='openAckModal']")).click();//click Yes in popup
				//Thread.sleep(500);
				driver.findElement(By.name("ackChangeControlNo")).sendKeys(changeControlDELETE);
				Thread.sleep(500);
				//driver.findElement(By.id("ackChangeControlNo")).sendKeys(Keys.TAB +password);
				driver.findElement(By.name("password")).sendKeys(password);
				Thread.sleep(500);
				driver.findElement(By.id("comments")).sendKeys("Delete single equipment");
				Thread.sleep(500);
				driver.findElement(By.xpath(".//*[@id='dynamicModal']/div[3]/div/button[2]")).click();
				Thread.sleep(1000);
				String deletemsg = driver.findElement(By.className("notify-msg")).getText(); // get deleted esuccess message
				Assert.assertEquals(deletemsg,Message.productAPIDELETE);
				String className = this.getClass().getName(); // get current class name - for screenshot
				String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
				Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
				if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				}
				Thread.sleep(600);
			}
			
			
			@Test(priority=39)
			public void CreateAPIforProduct() throws InterruptedException, SQLException, ClassNotFoundException, IOException
			{
				Thread.sleep(1000);
				driver.get(Constant.URL+"/active-ingredients");
				//Thread.sleep(2000);
				//driver.get("http://192.168.1.111:8090/active-ingredients");
				parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\Product.properties");
				Thread.sleep(1000);
				driver.findElement(By.id("addApi")).click();
				Thread.sleep(1000);
				String Name = ActiveIngredientNameCREATE;
				WebElement APIName = driver.findElement(parser.getbjectLocator("ActiveIngredientName"));
				APIName.sendKeys(Name);
				Thread.sleep(1000);
				driver.findElement(parser.getbjectLocator("ActiveID")).sendKeys(ActiveIDCREATE);
				Thread.sleep(1000);
				//select HBEL Term
				driver.findElement(parser.getbjectLocator("ActiveID")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
				Thread.sleep(1000);
				driver.findElement(parser.getbjectLocator("ActiveID")).sendKeys(Keys.TAB,Keys.TAB,Keys.ENTER,Keys.ENTER);
				Thread.sleep(1000);
				driver.findElement(By.id("hbelValue1")).sendKeys(HBELValueCREATE); 
				Thread.sleep(500);
				driver.findElement(By.name("numberofCarbonAtoms")).sendKeys(noofCarbonCREATE);
				Thread.sleep(500);
				//scroll down
				driver.findElement(By.name("numberofCarbonAtoms")).sendKeys(Keys.TAB,Keys.TAB,Keys.TAB);
				Thread.sleep(500);
				driver.findElement(By.name("activeMolarMass")).sendKeys(activeMolarCREATE);
				driver.findElement(By.name("activeMolarMass")).sendKeys(Keys.TAB);
				Thread.sleep(500);	
				
				if(driver.findElements(parser.getbjectLocator("SolubilityinWater")).size()!=0)
				{
					driver.findElement(parser.getbjectLocator("SolubilityinWater")).sendKeys(SolubilityinWaterCREATE);
					Thread.sleep(500);
				}else if(driver.findElements(By.id("activeIngredientsolubilityWaterSelect")).size()!=0)
				{
					driver.findElement(By.name("organicCarbon")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
					Thread.sleep(500);
				}
				
				WebElement submit = driver.findElement(parser.getbjectLocator("APIsubmit"));
				submit.click();
				Thread.sleep(500);
				
				
				//if duplicate equipment name
				if( driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Active Ingredient '"+Name+"' already exists!"))
				{
					String getduplicatename = driver.findElement(By.className("notify-msg")).getText();
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				
				Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
				for(int k=5;k<1000;k++)
				{
					j.add(k);
				}
				
				Thread.sleep(500);
				if(getduplicatename.equals("Active Ingredient '"+Name+"' already exists!"))
				{
					for(Integer i:j)
					{
						driver.findElement(parser.getbjectLocator("ActiveIngredientName")).clear();
						driver.findElement(parser.getbjectLocator("ActiveIngredientName")).sendKeys(Name+i);
						Thread.sleep(500);
						driver.findElement(parser.getbjectLocator("APIsubmit")).click();
						Thread.sleep(500);
						if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Active Ingredient '"+Name+i+"' already exists!"))
						{
							String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
							System.out.println("Name duplicate: "+nameduplicate);
							driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
							if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Active Ingredient '"+Name+i+"' already exists!"))
							{
								continue;
							}
						}
								System.out.println("Not duplicate so break the loop");
								break;
						}
					}
				}// closing if loop duplicate equipment
				
				
				Thread.sleep(1000);
				String createAPI = driver.findElement(By.className("notify-msg")).getText();
				Assert.assertEquals(createAPI,Message.productAPICREATE);
				
				if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				}
				Thread.sleep(500);
			} // closing create API method
			
			
			
				
			
			
			/*@Test(priority=6)
			public void ExportAPI() throws Exception
			{
				Utils.ExportPDF(driver);
			}*/
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
}

