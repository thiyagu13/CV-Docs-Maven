package com.eDocs.SanityTesting;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.RepositoryParser;
import com.eDocs.Utils.Utils;

public class SecuritySettings {
		
	private RepositoryParser parser;
	private WebDriver driver = Constant.driver;;
	 static String username = "trajan";
	 static String password = "trajan";
	
	
			@BeforeClass
			public void setUp() throws IOException  
			{
				//driver = new FirefoxDriver();
				driver.get("http://192.168.1.45:8092");
				parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\SecuritySettings.properties");
			}
		
			@Test(priority=1)
			public void Login() throws InterruptedException
			{
				String uname =username;
				String pword =password;
				//Lets see how we can find the first name field
				WebElement username = driver.findElement(By.id("username"));
				WebElement password = driver.findElement(By.id("password"));
				username.sendKeys(uname);
				Thread.sleep(500);
				password.sendKeys(pword);
				Thread.sleep(500);
				driver.findElement(By.id("loginsubmit")).click();
				Thread.sleep(500);
				driver.get("http://192.168.1.45:8092/security-policy");
			}
		
	
			@Test(priority=3)
			public void SecuritySettingSave() throws InterruptedException
			{
				Thread.sleep(500);
				//password aging
				WebElement passwordAging = driver.findElement(parser.getbjectLocator("PasswordAging"));
				Select SelectpasswordAging = new Select(passwordAging);
				SelectpasswordAging.selectByVisibleText("30 Days");
				Thread.sleep(500);
				
				//PasswordLength 
				WebElement PasswordLength = driver.findElement(parser.getbjectLocator("PasswordLength"));
				Select SelectPasswordLength = new Select(PasswordLength);
				SelectPasswordLength.selectByValue("6");
				Thread.sleep(500);
				
				//PasswordHistory 
				WebElement PasswordHistory = driver.findElement(parser.getbjectLocator("PasswordHistory"));
				Select SelectPasswordHistory = new Select(PasswordHistory);
				SelectPasswordHistory.selectByValue("2");
				Thread.sleep(500);
				
				//Invalid Login attempts 
				WebElement InvalidLoginattempts = driver.findElement(parser.getbjectLocator("InvalidLoginattempts"));
				Select SelectInvalidLoginattempts = new Select(InvalidLoginattempts);
				SelectInvalidLoginattempts.selectByValue("10");
				Thread.sleep(500);
				
				//Session Timeout 
				WebElement SessionTimeout = driver.findElement(parser.getbjectLocator("SessionTimeout"));
				Select SelectSessionTimeout = new Select(SessionTimeout);
				SelectSessionTimeout.selectByValue("45");
				Thread.sleep(500);
				
				driver.findElement(By.id("saveOption")).click();
				Thread.sleep(1000);
				
				String Success = null;
				if(driver.findElements(By.className("notify-msg")).size()!=0)
				{
					Success = driver.findElement(By.className("notify-msg")).getText();
				}
				Assert.assertEquals(Success,"Security policy updated successfully");
				String className = this.getClass().getName(); // get current class name - for screenshot
				String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
				Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
				if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
				}
				Thread.sleep(500);
			}
		
			
			
			
			
			@Test(priority=4)
			public void CheckPasswordLength() throws InterruptedException
			{
				Thread.sleep(1000);
				driver.get("http://192.168.1.45:8092/changeUserPassword");
				Thread.sleep(1000);
				//Current Password
				driver.findElement(By.id("password")).sendKeys(password);
				Thread.sleep(500);
				driver.findElement(By.id("newPassword")).sendKeys("123");
				Thread.sleep(500);
				driver.findElement(By.id("resetPassword")).sendKeys("123");
				Thread.sleep(500);
				driver.findElement(By.id("changePasswordSave")).click();
				Thread.sleep(1000);
				
				String Success = null;
				if(driver.findElements(By.className("notify-msg")).size()!=0)
				{
					Success = driver.findElement(By.className("notify-msg")).getText();
				}
				Assert.assertEquals(Success,"Minimum password length is 6 characters!");
				String className = this.getClass().getName(); // get current class name - for screenshot
				String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
				Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
				if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
				}
				Thread.sleep(500);
				
			}
			
			
			@Test(priority=5)
			public void CheckPasswordHistory() throws InterruptedException
			{
				Thread.sleep(1000);
				driver.get("http://192.168.1.45:8092/changeUserPassword");
				Thread.sleep(1000);
				//Current Password
				driver.findElement(By.id("password")).sendKeys(password);
				Thread.sleep(500);
				driver.findElement(By.id("newPassword")).sendKeys(password);
				Thread.sleep(500);
				driver.findElement(By.id("resetPassword")).sendKeys(password);
				Thread.sleep(500);
				driver.findElement(By.id("changePasswordSave")).click();
				Thread.sleep(1000);
				
				String Success = null;
				if(driver.findElements(By.className("notify-msg")).size()!=0)
				{
					Success = driver.findElement(By.className("notify-msg")).getText();
				}
				Assert.assertEquals(Success,"This password has been used previously. Please try a different password");
				String className = this.getClass().getName(); // get current class name - for screenshot
				String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
				Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
				if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
				}
				Thread.sleep(500);
			}
			
			
			@Test(priority=5)
			public void CheckInvalidLoginAttempts() throws InterruptedException
			{
				//Thread.sleep(1000);
				//driver.get("http://192.168.1.45:8092/changeUserPassword");
				Thread.sleep(1000);
				Actions logout = new Actions(driver);
				logout.moveToElement(driver.findElement(By.xpath(".//*[@id='securePolicy']/nav/div/button"))).click().build().perform();
				Thread.sleep(1000);
				driver.findElement(By.linkText("Logout")).click();
				Thread.sleep(2000);
				String Success = null;
				if(driver.findElements(By.className("notify-msg")).size()!=0)
				{
					Success = driver.findElement(By.className("notify-msg")).getText();
				}
				Assert.assertEquals(Success,"Logged out successfully");
				if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
				}
				Thread.sleep(500);
				
				String uname =username;
				String pword =password;
				//Lets see how we can find the first name field
				WebElement username = driver.findElement(By.id("username"));
				WebElement password = driver.findElement(By.id("password"));
				username.sendKeys(uname);
				Thread.sleep(500);
				password.sendKeys("gddfg");
				Thread.sleep(500);
				driver.findElement(By.id("loginsubmit")).click();
				Thread.sleep(2000);
				String invalid = null;
				if(driver.findElements(By.className("notify-msg")).size()!=0)
				{
					invalid = driver.findElement(By.className("notify-msg")).getText();
				}
				Assert.assertEquals(invalid,"Invalid credentials! 9 more attempts are remaining.");
				if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
				}
				Thread.sleep(500);
				
				String className = this.getClass().getName(); // get current class name - for screenshot
				String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
				Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
			}
			
			
			
			
			/*@Test(priority=6)
			public void CheckPasswordAging() throws InterruptedException
			{
				Thread.sleep(1000);
				
				
				
				
				
				
			}*/
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			/*@Test(priority=4)
			public void InvalidLoginAttempt() throws InterruptedException
			{
				Thread.sleep(500);
				driver.get("http://192.168.1.45:8092/change-password");
				Thread.sleep(500);
				driver.findElement(By.id("password")).sendKeys(password);
				Thread.sleep(500);
				driver.findElement(By.id("newPassword")).sendKeys("123");
				Thread.sleep(500);
				driver.findElement(By.id("resetPassword")).sendKeys("123");
				Thread.sleep(500);
				driver.findElement(By.id("changePasswordSave")).click();
				Thread.sleep(1000);
				String Success = null;
				if(driver.findElements(By.className("notify-msg")).size()!=0)
				{
					Success = driver.findElement(By.className("notify-msg")).getText();
				}
				Assert.assertEquals(Success,"Security policy updated successfully");
				String className = this.getClass().getName(); // get current class name - for screenshot
				String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
				Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
				if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
				}
				Thread.sleep(500);
				
			}
			*/
			
			/*@Test(priority=6)
			public void ExportSecuritySettings() throws Exception
			{
				Utils.ExportPDF(driver);
			}*/
			
			
	
			
	
	
}
