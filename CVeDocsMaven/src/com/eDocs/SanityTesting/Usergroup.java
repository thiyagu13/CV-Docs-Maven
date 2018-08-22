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
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.RepositoryParser;
import com.eDocs.Utils.Utils;

public class Usergroup {
  
	
	
			
			private RepositoryParser parser;
			private WebDriver driver = Constant.driver;
			public String password = "123456";
		
			//Datas for create User
			static String userGroupNameCREATE = "SeleniumGroup";
			static String userGroupDescCREATE = "Create user group";
			static String GroupPermCREATE = "Admin";
			static String moduleCREATE = "Site Information";
			static String changeControlCREATE  = "CreateUser 111";
			
			//Datas for Edit User
			static String userGroupNameEDIT = "SeleniumGroup";
			static String userGroupDescEDIT = "Edit user group";
			static String GroupPermEDIT = "Approve";
			static String moduleEDIT = "Documents";
			static String changeControlEDIT  = "CreateUser 111";
			
			//Multi Delete Data for user
			static String multiDeleteSearchData="SeleniumGroup";
			
			
			
			@BeforeClass
			public void setUp() throws IOException  
			{
				//driver = new FirefoxDriver();
				driver.get("http://192.168.1.111:8090");
				parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\UserGroup.properties");
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
				Thread.sleep(1000);
				driver.get("http://192.168.1.111:8090/group-policy");
			}
				
			
			
		//	@Test(priority=3,invocationCount=2)
			@Test(priority=3)
			public void CreateUSERGROUP() throws InterruptedException, SQLException, ClassNotFoundException, IOException
			{
				Thread.sleep(2000);
				//driver.get("http://192.168.1.45:8092/group-policy");
				//parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\UserGroup.properties");
				//Thread.sleep(1000);
				driver.findElement(By.id("addGroupPolicy")).click();
				Thread.sleep(1000);
				String Name = userGroupNameCREATE;
				WebElement userName = driver.findElement(parser.getbjectLocator("UserGroupName"));
				userName.sendKeys(Name);
				Thread.sleep(500);
				
				Thread.sleep(500);
				driver.findElement(parser.getbjectLocator("GroupDescription")).sendKeys(userGroupDescCREATE);
				Thread.sleep(500);
				
				driver.findElement(parser.getbjectLocator("GroupChangeControlNo")).sendKeys(changeControlCREATE);
				Thread.sleep(500);
				
				driver.findElement(By.id("add_policy_type")).click();
				Thread.sleep(500);
				driver.findElement(By.id("add_policy_type")).click();
				Thread.sleep(500);
				driver.findElement(By.className("remove-row-icon")).click(); //delete one row
				Thread.sleep(100);
				driver.findElement(By.className("grpmodname")).click();
				driver.findElement(By.className("grpmodname")).sendKeys("Site Information",Keys.ENTER);
				Thread.sleep(1000);
				//driver.findElement(By.className("grpsite")).click();
				//driver.findElement(By.className("grpsite")).sendKeys(Keys.ENTER);
				//Thread.sleep(1000);
				driver.findElement(By.id("grppermission1")).click();
				driver.findElement(By.id("grppermission1")).sendKeys("Admin",Keys.ENTER);
				Thread.sleep(1000);
				
				WebElement submit = driver.findElement(parser.getbjectLocator("CreateGroupSubmit"));
				submit.click();
				Thread.sleep(1000);
				
				System.out.println(driver.findElement(By.className("notify-msg")).getText());
				//if duplicate equipment name
				if( driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Group name already exists"))
				{
					String getduplicatename = driver.findElement(By.className("notify-msg")).getText();
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				
				Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
				for(int k=1;k<1000;k++)
				{
					j.add(k);
				}
				
				Thread.sleep(500);
				if(getduplicatename.equalsIgnoreCase("Group name already exists"))
				{
					for(Integer i:j)
					{
						driver.findElement(parser.getbjectLocator("UserGroupName")).clear();
						driver.findElement(parser.getbjectLocator("UserGroupName")).sendKeys(Name+i);
						Thread.sleep(500);
						driver.findElement(parser.getbjectLocator("CreateGroupSubmit")).click();
						Thread.sleep(500);
						if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Group name already exists"))
						{
							String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
							System.out.println("Name duplicate: "+nameduplicate);
							driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
							if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Group name already exists"))
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
				Assert.assertEquals(successMsg,"Group has been added successfully");
				
				if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				}
				Thread.sleep(500);
			} // closing create User method
			
	

			
			@Test(priority=4)
			public void EditUserGroup() throws InterruptedException, SQLException, ClassNotFoundException
			{
				Thread.sleep(2000);
				driver.findElement(By.id("dLabel")).click();
				Thread.sleep(500);
				//driver.findElement(By.className("dropdown-item")).sendKeys(Keys.ENTER);
				driver.findElement(By.linkText("Edit")).click();
				
				Thread.sleep(500);
				String getgrpDesc = driver.findElement(parser.getbjectLocator("GroupDescription")).getAttribute("value");
				Assert.assertEquals(getgrpDesc, userGroupDescCREATE);
				driver.findElement(parser.getbjectLocator("GroupDescription")).clear();
				driver.findElement(parser.getbjectLocator("GroupDescription")).sendKeys(userGroupDescEDIT);
				Thread.sleep(1000);
				
				
				//Module name
				/*WebElement groupmodname  = driver.findElement(By.xpath(".//*[@id='marker-num1']/td[2]/span/span[1]/span")); // Select ADE
				String selected = groupmodname.getText().substring(1);
				Assert.assertEquals(selected, moduleCREATE);
				driver.findElement(By.className("grpmodname")).click();
				driver.findElement(By.className("grpmodname")).sendKeys(moduleEDIT,Keys.ENTER);*/
				
				//site name
				//WebElement grpsite  = driver.findElement(By.className("grpsite")); // Select ADE
				//Select Selectgrpsite  = new Select(grpsite);
				//WebElement getsiteoption = Selectgrpsite.getFirstSelectedOption();
				//String selectedsite = getsiteoption.getText();
				//Assert.assertEquals(selectedsite, SiteCREATE);
				
				//site name
				Thread.sleep(1000);
				
				
				WebElement modulename = driver.findElement(By.id("controlNo1"));
				Select selectmodulename = new Select(modulename);
				WebElement option = selectmodulename.getFirstSelectedOption(); 
				String getOption = option.getText();
				System.out.println("getOption: "+getOption);
				//WebElement grppermission  = driver.findElement(By.xpath(".//*[@id='marker-num1']/td[2]/span/span[1]/span")); // Select ADE
				//String selectedPerm = grppermission.getText().substring(1);
				//System.out.println("selectedPerm "+selectedPerm);
				Assert.assertEquals(getOption, moduleCREATE);
				Thread.sleep(1000);
				driver.findElement(By.className("grpmodname")).sendKeys(moduleEDIT,Keys.ENTER);
				
				//driver.findElement(By.xpath(".//*[@id='marker-num1']/td[4]/span/span[1]/span")).click();
				//Thread.sleep(500);
				Thread.sleep(1000);
				WebElement permission = driver.findElement(By.id("grppermission1"));
				Select selectpermission = new Select(permission);
				WebElement option1 = selectpermission.getFirstSelectedOption(); 
				String getOption1 = option1.getText();
				System.out.println("getimageOption1 "+getOption1);
				Assert.assertEquals(getOption1, GroupPermCREATE);
				driver.findElement(By.id("grppermission1")).sendKeys("Approve",Keys.ENTER);
				
				/*driver.findElement(By.xpath(".//*[@id='marker-num1']/td[4]/span/span[1]/span")).sendKeys(Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ARROW_DOWN);
				Thread.sleep(500);
				//GroupPermEDIT
				driver.findElement(By.xpath(".//*[@id='marker-num1']/td[4]/span/span[1]/span")).sendKeys(Keys.ENTER);
				driver.findElement(By.xpath(".//*[@id='marker-num1']/td[4]/span/span[1]/span")).sendKeys(Keys.TAB);
				Thread.sleep(1000);
				*/
				
				Thread.sleep(1000);
				
				WebElement submit = driver.findElement(By.id("saveUserGroup"));
				submit.click();
				Thread.sleep(1000);
				
				driver.findElement(By.name("password")).sendKeys(password);
				Thread.sleep(500);
				
				driver.findElement(By.id("comments")).sendKeys("Edit user group");
				Thread.sleep(500);
				//driver.findElement(By.id("edtcomments")).sendKeys(Keys.SHIFT,Keys.TAB,password);
				
				driver.findElement(By.id("ackSubmit")).click();
				
				Thread.sleep(2000);
				String successMsg = null;
				if(driver.findElements(By.className("notify-msg")).size()!=0)
				{
					successMsg = driver.findElement(By.className("notify-msg")).getText();
				}
				Assert.assertEquals(successMsg,"Group has been updated successfully");
				
				if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				}
				Thread.sleep(500);
			}
			
	
			
			
			
			@Test(priority=5)
			public void SingleDeleteUserGroup() throws InterruptedException, IOException
			{
				Thread.sleep(2000);
				driver.findElement(By.id("dLabel")).click();
				Thread.sleep(500);
				//driver.findElement(By.className("dropdown-item")).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
				driver.findElement(By.linkText("Delete")).click();
				Thread.sleep(500);
				//driver.findElement(By.xpath(".//*[@id='dropdownactionDoc']/li[3]/a")).click(); // Click edit equipment button
				//Thread.sleep(1000);
			//	driver.findElement(By.xpath(".//*[@id='openAckModal']")).click();//click Yes in popup
				//Thread.sleep(500);
				driver.findElement(By.name("ackChangeControlNo")).sendKeys("111");
				Thread.sleep(500);
				//driver.findElement(By.name("ackChangeControlNo")).sendKeys(Keys.TAB +password);
				driver.findElement(By.name("password")).sendKeys(password);
				Thread.sleep(500);
				driver.findElement(By.id("comments")).sendKeys("Delete single user group");
				Thread.sleep(1000);
				driver.findElement(By.xpath(".//*[@id='dynamicModal']/div[3]/div/button[2]")).click();
				Thread.sleep(2000);
				
				String successMsg = null;
				if(driver.findElements(By.className("notify-msg")).size()!=0)
				{
					successMsg = driver.findElement(By.className("notify-msg")).getText();
				}
				Assert.assertEquals(successMsg,"User Group was deleted successfully");
				
				String className = this.getClass().getName(); // get current class name - for screenshot
				String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
				Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
				if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				}
				Thread.sleep(600);
			}
			
			
			
		/*	@Test(priority=6)
			public void MultiDeleteUserGroup() throws InterruptedException, IOException
			{
				Thread.sleep(2000);
				driver.findElement(By.id("searchEquipment")).sendKeys(multiDeleteSearchData);
				Thread.sleep(1000);
				driver.findElement(By.id("example-select-all")).click();
				Thread.sleep(2000);
				driver.findElement(By.id("deleteSelectedEquipment")).click(); // multi delete
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
				Assert.assertEquals(successMsg,"User Group deleted successfully");
				
				String className = this.getClass().getName(); // get current class name - for screenshot
				String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
				Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
				if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				}
				Thread.sleep(600);
			}
			
			
			@Test(priority=7)
			public void CreateUSERGROUPforPolicy() throws InterruptedException, SQLException, ClassNotFoundException, IOException
			{
				Thread.sleep(2000);
				//driver.get("http://192.168.1.45:8092/users");
				//parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\UserGroup.properties");
				//Thread.sleep(1000);
				driver.findElement(By.id("example-select-all")).click(); // un check mutli check box
				Thread.sleep(1000);
				driver.findElement(By.id("addGroupPolicy")).click();
				Thread.sleep(1000);
				String Name = userGroupNameCREATE;
				WebElement userName = driver.findElement(parser.getbjectLocator("UserGroupName"));
				userName.sendKeys(Name);
				Thread.sleep(500);
				
				Thread.sleep(500);
				driver.findElement(parser.getbjectLocator("GroupDescription")).sendKeys(userGroupDescCREATE);
				Thread.sleep(500);
				
				driver.findElement(parser.getbjectLocator("GroupChangeControlNo")).sendKeys(changeControlCREATE);
				Thread.sleep(500);
				
				driver.findElement(By.id("add_policy_type")).click();
				Thread.sleep(500);
				driver.findElement(By.id("add_policy_type")).click();
				Thread.sleep(500);
				driver.findElement(By.className("remove-row-icon")).click(); //delete one row
				Thread.sleep(500);
				driver.findElement(By.className("grpmodname")).click();
				driver.findElement(By.className("grpmodname")).sendKeys("Site Information",Keys.ENTER);
				Thread.sleep(1000);
				driver.findElement(By.className("grpsite")).click();
				driver.findElement(By.className("grpsite")).sendKeys(Keys.ENTER);
				Thread.sleep(1000);
				driver.findElement(By.className("grppermission")).click();
				driver.findElement(By.className("grppermission")).sendKeys("Admin",Keys.ENTER);
				Thread.sleep(1000);
				
				WebElement submit = driver.findElement(parser.getbjectLocator("CreateGroupSubmit"));
				submit.click();
				Thread.sleep(1000);
				
				
				//if duplicate equipment name
				if( driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Group name already exists"))
				{
					String getduplicatename = driver.findElement(By.className("notify-msg")).getText();
					driver.findElement(By.className(".grey-text.custom-notify-close")).click();
				
				Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
				for(int k=1;k<1000;k++)
				{
					j.add(k);
				}
				
				Thread.sleep(500);
				if(getduplicatename.equalsIgnoreCase("Group name already exists"))
				{
					for(Integer i:j)
					{
						driver.findElement(parser.getbjectLocator("UserGroupName")).clear();
						driver.findElement(parser.getbjectLocator("UserGroupName")).sendKeys(Name+i);
						Thread.sleep(500);
						driver.findElement(parser.getbjectLocator("CreateGroupSubmit")).click();
						Thread.sleep(500);
						if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Group name already exists"))
						{
							String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
							System.out.println("Name duplicate: "+nameduplicate);
							driver.findElement(By.className(".grey-text.custom-notify-close")).click();
							if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Group name already exists"))
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
				Assert.assertEquals(successMsg,"Group has been added successfully");
				
				if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				}
				Thread.sleep(500);
			} // closing create User method
			
	
	*/
			/*@Test(priority=6)
			public void ExportUserGroup() throws Exception
			{
				Utils.ExportPDF(driver);
			}*/
	
	
	
	
	
}
