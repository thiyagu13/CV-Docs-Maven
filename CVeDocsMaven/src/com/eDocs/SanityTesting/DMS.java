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

public class DMS {
  
		
			private RepositoryParser parser;
			private WebDriver driver = Constant.driver;;
			public String password = Constant.sitepassword;
		
			//Datas for create DMS
			static String dmsNameCREATE = "Test DMS";
			static String ChangeControlNumberCREATE  = "Change cntrl create 111";
			static String documentPath = "C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\Test Data\\Cleaning-Validation-Manual.pdf";
			//static String documentPath = "Cleaning-Validation-Manual.pdf";
			static String documentIDCREATE  = "DMS Doc 111";
			
			
			//Datas for Edit DMS

			
			//Delete Datas for DMS
			static String changeControlDELETE="Delete single API";
			
			//Multi Delete Data for DMS
			static String multiDeleteSearchData="Test API";
			
			
			
		/*	@BeforeClass
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
			}*/
				
			
			@Test(priority=9)
			public void CreateDMSforValidationDocument() throws InterruptedException, SQLException, ClassNotFoundException, IOException
			{
				Thread.sleep(2000);
				driver.get(Constant.URL+"/documents");
				//parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\Product.properties");
				Thread.sleep(1000);
				driver.findElement(By.id("addFileModalbtn")).click();
				Thread.sleep(1000);
				String Name = dmsNameCREATE;
				WebElement nameField = driver.findElement(By.name("add_file_doc_name"));
				nameField.sendKeys(Name);
				Thread.sleep(1000);
				//Document Type
				nameField.sendKeys(Keys.TAB,Keys.ENTER,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ENTER);
				Thread.sleep(1000);
				//Document ID
				driver.findElement(By.name("add_file_doc_id")).sendKeys(documentIDCREATE);
				Thread.sleep(500);
				//Module
				driver.findElement(By.name("add_file_doc_id")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ENTER);
				Thread.sleep(500);
				//Upload Document
				driver.findElement(By.name("addfileDocUpload")).sendKeys(documentPath);
				Thread.sleep(1000);
				
				Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
				for(int k=5;k<1000;k++)
				{
					j.add(k);
				}
				
				//Submit Button
				WebElement submit = driver.findElement(By.id("addFileType"));
				submit.click();
				Thread.sleep(1500);
				
				//if duplicate equipment name
				if( driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Document with same Name already exists"))
				{
					String getduplicatename = driver.findElement(By.className("notify-msg")).getText();
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				
				Thread.sleep(500);
				if(getduplicatename.equalsIgnoreCase("Document with same Name already exists"))
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
				
				Thread.sleep(1000);
				String createAPI = driver.findElement(By.className("notify-msg")).getText();
				Assert.assertEquals(createAPI,Message.dmsCREATE);
				
				if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				}
				Thread.sleep(500);
		} 
			
			
			@Test(priority=10,invocationCount=2)
			public void CreateDMSforSOP() throws InterruptedException, SQLException, ClassNotFoundException, IOException
			{
				Thread.sleep(2000);
				driver.findElement(By.id("addFileModalbtn")).click();
				Thread.sleep(1000);
				String Name = dmsNameCREATE;
				WebElement nameField = driver.findElement(By.name("add_file_doc_name"));
				nameField.sendKeys(Name);
				Thread.sleep(1000);
				//Document Type
				nameField.sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
				Thread.sleep(1000);
				//Document ID
				driver.findElement(By.name("add_file_doc_id")).sendKeys(documentIDCREATE);
				Thread.sleep(500);
				//Module
				driver.findElement(By.name("add_file_doc_id")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ENTER);
				Thread.sleep(500);
				//Upload Document
				driver.findElement(By.name("addfileDocUpload")).sendKeys(documentPath);
				Thread.sleep(1000);
				//Submit Button
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
				if(getduplicatename.equalsIgnoreCase("Document with same Name already exists"))
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
				
				Thread.sleep(1000);
				String createAPI = driver.findElement(By.className("notify-msg")).getText();
				Assert.assertEquals(createAPI,Message.dmsCREATE);
				
				if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				}
				Thread.sleep(500);
		} 
			
			
			@Test(priority=11)
			public void DeleteDMS() throws InterruptedException, IOException
			{
				Thread.sleep(2000);
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
				driver.findElement(By.id("comments")).sendKeys(Keys.TAB,Keys.TAB,Keys.ENTER);
				Thread.sleep(1500);
				String deletemsg = driver.findElement(By.className("notify-msg")).getText(); // get deleted esuccess message
				Assert.assertEquals(deletemsg,Message.dmsDELETE);
				String className = this.getClass().getName(); // get current class name - for screenshot
				String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
				Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
				if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				}
				Thread.sleep(600);
			}
			
}

