package com.eDocs.SanityTesting;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Message;
import com.eDocs.Utils.RepositoryParser;
import com.eDocs.Utils.Utils;

public class UniversalSettings {
	
	
			private RepositoryParser parser;
			private WebDriver driver = Constant.driver;
			public String password = Constant.sitepassword;
			
			public String GneralPreference = "http://192.168.1.45:8092/general-preferences";
			public String SiteMap = "http://192.168.1.45:8092/site-map";
			public String LimitTerminology = "http://192.168.1.45:8092/limit-terminology";
			public String SamplingLocation = "http://192.168.1.45:8092/sampling-locations";
			public String ResidueLimit = "http://192.168.1.45:8092/residue-limit";
			public String microbilaLimit = "http://192.168.1.45:8092/microbial-limit";
			public String productgrouping = "http://192.168.1.45:8092/product-grouping";
			
			@BeforeClass
			public void setUp() throws IOException  
			{
				//driver = new FirefoxDriver();
				driver.get(Constant.URL);
				parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\Product.properties");
			}
		
			@Test(priority=1)
			//@Test
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

				
				
				@Test(priority=2)
			//@Test
				public void ResidueLimitSAVE() throws InterruptedException
				{
					driver.get(Constant.URL+"/residue-limit");
					Thread.sleep(500);
					//Basis Limit
					System.out.println(driver.findElement(By.id("basis3")).isSelected());
					if(driver.findElement(By.id("basis3")).isSelected()==false)
					{
						Thread.sleep(500);
						driver.findElement(By.id("basis3")).click();
					}
					Thread.sleep(500);
					//DefaultLimit
					if(driver.findElement(By.id("default1")).isSelected()==false)
					{
						Thread.sleep(500);
						driver.findElement(By.id("default1")).click();
					}
					driver.findElement(By.id("default1")).sendKeys(Keys.TAB,Keys.TAB);
					Thread.sleep(500);
					//Basis of Limit Determination
					if(driver.findElement(By.id("limitBasedonLowestL0")).isSelected()==false)
					{
						Thread.sleep(500);
						driver.findElement(By.id("limitBasedonLowestL0")).click();
					}
					//select Basis of L3 Determination
					Thread.sleep(500);
					driver.findElement(By.id("limitBasedonLowestL0")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
					Thread.sleep(500);
					//scroll
					driver.findElement(By.id("limitBasedonLowestL0")).sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB);
					Thread.sleep(500);
					//Surface Area to be used in Limit per unit area determination 
					driver.findElement(By.id("defaultsa1")).click();
					Thread.sleep(1000);
					
					//Qualification Run Sampling Method
					driver.findElement(By.id("defaultsa1")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ARROW_DOWN,Keys.ENTER);
					Thread.sleep(500);
					// Sampling Type
					driver.findElement(By.id("defaultsa1")).sendKeys(Keys.TAB,Keys.TAB,Keys.ENTER,Keys.ARROW_DOWN,Keys.ENTER);
					Thread.sleep(500);
					// Routine Monitoring
					driver.findElement(By.id("defaultsa1")).sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.ENTER,Keys.ARROW_DOWN,Keys.ENTER);
					Thread.sleep(500);
					
					//scroll Down
					driver.findElement(By.id("defaultsa1")).sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB);
					Thread.sleep(500);
					
					//Swab Area Option
					if(driver.findElement(By.id("surfaceAreaSampled1")).isSelected()==true)
					{
						Thread.sleep(500);
						System.out.println("area: "+driver.findElement(By.id("valueForSameProductsForSurfaceAreaSampled1")).getAttribute("value"));
						if(driver.findElement(By.id("valueForSameProductsForSurfaceAreaSampled1")).getAttribute("value").equalsIgnoreCase(""))
						{
							Thread.sleep(500);
							driver.findElement(By.id("valueForSameProductsForSurfaceAreaSampled1")).clear();
							driver.findElement(By.id("valueForSameProductsForSurfaceAreaSampled1")).sendKeys("60");
						}
					}else
					{
						Thread.sleep(500);
						System.out.println("area: "+driver.findElement(By.id("valueForDefinedEachForSurfaceAreaSampled2")).getText());
						System.out.println("area: "+driver.findElement(By.id("valueForDefinedEachForSurfaceAreaSampled2")).getAttribute("value"));
						if(driver.findElement(By.id("valueForDefinedEachForSurfaceAreaSampled2")).getAttribute("value").equalsIgnoreCase(""))
						{
							Thread.sleep(500);
							driver.findElement(By.id("valueForDefinedEachForSurfaceAreaSampled2")).clear();
							driver.findElement(By.id("valueForDefinedEachForSurfaceAreaSampled2")).sendKeys("60");
						}
					}
					Thread.sleep(500);
					
					//Swab Amount
					if(driver.findElement(By.id("desorption1")).isSelected()==true)
					{
						Thread.sleep(500);
						System.out.println(driver.findElement(By.id("valueForSameProductsForSolventUsed1")).getAttribute("value"));
						if(driver.findElement(By.id("valueForSameProductsForSolventUsed1")).getAttribute("value").equalsIgnoreCase(""))
						{
							Thread.sleep(500);
							driver.findElement(By.id("valueForSameProductsForSolventUsed1")).clear();
							driver.findElement(By.id("valueForSameProductsForSolventUsed1")).sendKeys("60");
						}
					}else
					{
						Thread.sleep(500);
						System.out.println("amount"+driver.findElement(By.id("valueForDefinedEachForSolventUsed2")).getText());
						System.out.println("amount"+driver.findElement(By.id("valueForDefinedEachForSolventUsed2")).getAttribute("value"));
						if(driver.findElement(By.id("valueForDefinedEachForSolventUsed2")).getAttribute("value").equalsIgnoreCase(""))
						{
							Thread.sleep(500);
							driver.findElement(By.id("valueForDefinedEachForSolventUsed2")).clear();
							driver.findElement(By.id("valueForDefinedEachForSolventUsed2")).sendKeys("60");
						}
					}
					Thread.sleep(500);
					
					//Select Rinse Sampling
					driver.findElement(By.id("valueForDefinedEachForSolventUsed2")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
					Thread.sleep(500);
					
				//Rinse Volume
					if(driver.findElement(By.id("rinseVolume1")).isSelected()==true)
					{
						Thread.sleep(500);
						System.out.println(driver.findElement(By.id("sameForAllEquipValueForRinseVolume1")).getAttribute("value").isEmpty());
						if(driver.findElement(By.id("sameForAllEquipValueForRinseVolume1")).getAttribute("value").isEmpty())
						{
							Thread.sleep(500);
							driver.findElement(By.id("sameForAllEquipValueForRinseVolume1")).clear();
							driver.findElement(By.id("sameForAllEquipValueForRinseVolume1")).sendKeys("60");
						}
					}else
					{
						Thread.sleep(500);
						System.out.println(driver.findElement(By.id("definedForEachEquipValueForRinseVolume2")).getAttribute("value").isEmpty());
						if(driver.findElement(By.id("definedForEachEquipValueForRinseVolume2")).getAttribute("value").isEmpty())
						{
							Thread.sleep(500);
							driver.findElement(By.id("definedForEachEquipValueForRinseVolume2")).clear();
							driver.findElement(By.id("definedForEachEquipValueForRinseVolume2")).sendKeys("60");
						}
					}
					
					Thread.sleep(500);
					driver.findElement(By.id("saveResidueLimitDetails")).click();
					Thread.sleep(1000);
					
					//Acknowledge
					if(driver.findElements(By.className("notify-msg")).size()!=0)
						{
						String SuccessMessage = driver.findElement(By.className("notify-msg")).getText();
						System.out.println("SuccessMessage: "+SuccessMessage);
						if(SuccessMessage.equalsIgnoreCase("No changes made."))
						{
							if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
							{
								driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
							}
						}
					}
						else {
							Thread.sleep(1500);
							System.out.println("else");
							driver.findElement(By.name("password")).sendKeys(password);
							Thread.sleep(500);
							driver.findElement(By.name("comments")).sendKeys("Test comments");
							Thread.sleep(500);
							driver.findElement(By.id("ackSubmit")).click();
							Thread.sleep(1500);
							Assert.assertEquals(driver.findElement(By.className("notify-msg")).getText(),Message.residueLimitUpdate);
							if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
							{
								driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
							}
							Thread.sleep(500);
						}
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					
				  }
				
				
	
				
				@Test(priority=3)
			//@Test
				public void GeneralSettings() throws InterruptedException
				{
					driver.get(Constant.URL+"/general-preferences");
					Thread.sleep(1000);
					//Click Save
					driver.findElement(By.id("saveGeneralOption")).click();
					Thread.sleep(1500);
					
					//Acknowledge
					if(driver.findElements(By.className("notify-msg")).size()!=0)
						{
						String SuccessMessage = driver.findElement(By.className("notify-msg")).getText();
						System.out.println("SuccessMessage: "+SuccessMessage);
						if(SuccessMessage.equalsIgnoreCase("No changes made."))
						{
							if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
							{
								driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
							}
						}
					}
						else {
							Thread.sleep(1500);
							System.out.println("else");
							driver.findElement(By.name("password")).sendKeys(password);
							Thread.sleep(500);
							driver.findElement(By.name("comments")).sendKeys("Test comments");
							Thread.sleep(500);
							driver.findElement(By.id("ackSubmit")).click();
							Thread.sleep(1500);
							Assert.assertEquals(driver.findElement(By.className("notify-msg")).getText(),Message.generalPreferenceSave);
							if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
							{
								driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
							}
							Thread.sleep(500);
						}
					
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					Thread.sleep(500);
				  }
				
				
				
				@Test(priority=4)
			//@Test
				public void SiteMap() throws InterruptedException
				{
					driver.get(Constant.URL+"/site-map");
					Thread.sleep(6000);
					//image Upload
					WebElement selection = driver.findElement(By.id("uploadSiteImages"));
					Select getimageSelection = new Select(selection);
					WebElement option = getimageSelection.getFirstSelectedOption(); 
					String getSelectedImageOption = option.getText();
					Thread.sleep(500);
					System.out.println("getSelectedImageOption: "+getSelectedImageOption);
					if(getSelectedImageOption.contains("Select Option"))
					{
						Thread.sleep(500);
						driver.findElement(By.xpath(".//*[@id='optionDetails']/div/div/div/div[1]/span/span[1]/span/span[2]")).click();
						driver.findElement(By.xpath(".//*[@id='optionDetails']/div/div/div/div[1]/span/span[1]/span/span[2]")).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
						Thread.sleep(500);
						//add pin
						driver.findElement(By.id("addPinName")).click();
						Thread.sleep(500);
						//locatin name
						driver.findElement(By.xpath(".//*[@id='marker-num1']/td[2]/input[2]")).sendKeys("location1");
						Thread.sleep(500);
						driver.findElement(By.id("saveLocation")).click();
						Thread.sleep(1500);
						
						//Acknowledge
						if(driver.findElements(By.className("notify-msg")).size()!=0)
						{
							String SuccessMessage = driver.findElement(By.className("notify-msg")).getText();
							System.out.println("SuccessMessage: "+SuccessMessage);
							if(SuccessMessage.equalsIgnoreCase(Message.siteMapSave))
							{
								Assert.assertEquals(SuccessMessage,Message.siteMapSave);
								if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
								{
									driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
								}
								
							}
						}
							else {
								Thread.sleep(1500);
								System.out.println("else");
								driver.findElement(By.name("password")).sendKeys(password);
								Thread.sleep(500);
								driver.findElement(By.name("comments")).sendKeys("Test comments");
								Thread.sleep(500);
								driver.findElement(By.id("ackSubmit")).click();
								Thread.sleep(1500);
								Assert.assertEquals(driver.findElement(By.className("notify-msg")).getText(),Message.siteMapSave);
								if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
								{
									driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
								}
								Thread.sleep(500);
							}
						
						//Assert.assertEquals(SuccessMessage,Message.siteMapSave);
						//if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
						//{
						//	driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
						//}
					}
					
					Thread.sleep(500);
					
						//if(driver.findElements(By.className("no")).size()!=0)
						//{
						//	if(driver.findElement(By.className("no")).getText().equalsIgnoreCase("1."))
						//	{
						//		Thread.sleep(500);
						//		System.out.println("--->:"+driver.findElement(By.xpath(".//*[@id='marker-num1']/td[2]/input[2]")).getAttribute("value"));
						//		if(driver.findElement(By.xpath(".//*[@id='marker-num1']/td[2]/input[2]")).getAttribute("value").equalsIgnoreCase(""))
							//	{
								//	driver.findElement(By.xpath(".//*[@id='marker-num1']/td[2]/input[2]")).sendKeys("Chennai");
							//	}
							//}
						//}
						//	else
						//	{
							//	Thread.sleep(500);
							//	driver.findElement(By.cssSelector(".waves-effect")).click();
							//	Thread.sleep(500);
							//	if(driver.findElement(By.className("no")).getText().equalsIgnoreCase("1."))
							//	{
								//	Thread.sleep(500);
								//	System.out.println("=-------");
								//	System.out.println(driver.findElement(By.xpath(".//*[@id='marker-num1']/td[2]/input[2]")).getText());
								//	if(driver.findElement(By.xpath(".//*[@id='marker-num1']/td[2]/input[2]")).getText().equalsIgnoreCase(""))
								//	{
								//		driver.findElement(By.xpath(".//*[@id='marker-num1']/td[2]/input[2]")).sendKeys("Chennai");
								//	}
							//	}
								
							//}
							//Thread.sleep(500);
							String className = this.getClass().getName(); // get current class name - for screenshot
							String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
							Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
							Thread.sleep(500);
				}
				
				
					
				
				
				@Test(priority=6)
			//@Test
				public void SampleLocation() throws InterruptedException
				{
					Thread.sleep(1000);
					driver.get(Constant.URL+"/sampling-locations");
					Thread.sleep(1000);
					WebElement selection = driver.findElement(By.id("samplingType"));
					Select getsamplingSelection = new Select(selection);
					String getSelectedOption="";
					try {
						WebElement option = getsamplingSelection.getFirstSelectedOption();
						getSelectedOption = option.getText();
					} catch(Exception e)
					{
						System.out.println("No Sampling Type has been selected");
					}
					Thread.sleep(500);
					System.out.println("getSelectedImageOption: "+getSelectedOption);
					if(getSelectedOption.equals(""))
					{
						driver.get(Constant.URL+"/sampling-locations");
						Thread.sleep(1000);
						driver.findElement(By.id("listMOC")).click();
						Thread.sleep(500);
						driver.findElement(By.id("saveMoc")).click(); // Click Save in MOC tab
						Thread.sleep(1000);
						String msg = driver.findElement(By.className("notify-msg")).getText();
						Thread.sleep(500);
					if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
					{
						driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
					}
						System.out.println("Msg: "+msg);
					if(!msg.equalsIgnoreCase("Material Of Construction cannot be empty") || 
							msg.equalsIgnoreCase(Message.MOCSave))
					{
						Assert.assertEquals(msg,Message.MOCSave);
						System.out.println("Msg--->"+msg);
						String className = this.getClass().getName(); // get current class name - for screenshot
						String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
						Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
						if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
						{
							//driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
						}
						Thread.sleep(1000);
					}
					else
					{
						Thread.sleep(500);
						driver.findElement(By.id("addMOC")).click();
						Thread.sleep(500);
						driver.findElement(By.id("locationName1")).clear();
						driver.findElement(By.id("locationName1")).sendKeys("Chennai");
						Thread.sleep(500);
						driver.findElement(By.id("saveMoc")).click();
						Thread.sleep(1000);
						String SuccessMessage = driver.findElement(By.className("notify-msg")).getText();
						Assert.assertEquals(SuccessMessage,Message.MOCSave);
						String className = this.getClass().getName(); // get current class name - for screenshot
						String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
						Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
						if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
						{
							driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
						}
						Thread.sleep(1000);
					}
					
					Thread.sleep(2000);
					driver.findElement(By.xpath(".//*[@id='limitTerminologyDefinition']/div/div/div/div[2]/div/div/span/span[1]/span/span[2]")).click();
					driver.findElement(By.xpath(".//*[@id='limitTerminologyDefinition']/div/div/div/div[2]/div/div/span/span[1]/span/span[2]")).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
					Thread.sleep(500);
					Thread.sleep(1000);
					System.out.println("xpath: "+driver.findElement(By.xpath(".//*[@id='user_role']/span/span[1]/span/ul")).getText());
					
					if(driver.findElement(By.xpath(".//*[@id='user_role']/span/span[1]/span/ul")).getText().equalsIgnoreCase(""))
					{
						driver.findElement(By.id("saveSamplingLocation")).click();
						Thread.sleep(2000);
						if(driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Please select Criteria"))
						{
							if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
							{
								driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
							}
							Thread.sleep(500);
							driver.findElement(By.id("trigger-add-role")).click();
							Thread.sleep(300);
							driver.findElement(By.id("criteria-name")).sendKeys(Keys.SHIFT,Keys.TAB,Keys.TAB,Keys.TAB,Keys.ENTER,Keys.ARROW_DOWN,Keys.CONTROL,Keys.ARROW_DOWN,Keys.ENTER);
							Thread.sleep(300);
							driver.findElement(By.id("trigger-cancel-role")).click();
							Thread.sleep(1000);
						}
					}
					
					Thread.sleep(500);
					driver.findElement(By.id("saveSamplingLocation")).click();
					Thread.sleep(500);
					
					if(driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Please select Rating"))
					{
						if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
						{
							driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
						}
						Thread.sleep(500);
						driver.findElement(By.id("trigger-add-role")).click();
						Thread.sleep(300);
						driver.findElement(By.id("criteria-name")).sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.ENTER,Keys.ENTER);
						Thread.sleep(300);
						driver.findElement(By.id("trigger-cancel-role")).click();
						Thread.sleep(1000);
						driver.findElement(By.id("saveSamplingLocation")).click();
					}
					
					//Acknowledge
					Thread.sleep(1000);
					if(driver.findElements(By.className("notify-msg")).size()!=0)
					{
					String SuccessMessage = driver.findElement(By.className("notify-msg")).getText();
					System.out.println("SuccessMessage: "+SuccessMessage);
					if(SuccessMessage.equalsIgnoreCase("No changes made."))
					{
						if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
						{
							driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
						}
					}
				}
					else {
						System.out.println("else");
						Thread.sleep(1500);
						driver.findElement(By.name("password")).sendKeys(password);
						Thread.sleep(500);
						driver.findElement(By.name("comments")).sendKeys("Test comments");
						Thread.sleep(500);
						driver.findElement(By.xpath(".//*[@id='ackSubmit']")).click();
						Thread.sleep(1000);
						Assert.assertEquals(driver.findElement(By.className("notify-msg")).getText(),Message.samplinglocationSave);
					}
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					Thread.sleep(500);
				}
			}
				
				
				
				@Test(priority=7)
			//@Test
				public void productgrouping() throws InterruptedException 
				{
					Thread.sleep(1000);
					driver.get(Constant.URL+"/product-grouping");
					Thread.sleep(1000);
					
					WebElement selection = driver.findElement(By.id("groupingCriteria"));
					Select getcriteriaSelection = new Select(selection);
					String getSelectedOption="";
					try
					{
						WebElement option = getcriteriaSelection.getFirstSelectedOption(); 
						getSelectedOption = option.getText();
					} catch (Exception e)
					{
						System.out.println("no option selected");
					}
					Thread.sleep(1000);
					System.out.println("getSelectedImageOption: "+getSelectedOption);
				if(getSelectedOption.equals(""))
				{
					driver.findElement(By.xpath(".//*[@id='optionDetails']/div/div/div/div[1]/div[1]/div/span/span[1]/span/ul")).click();
					driver.findElement(By.xpath(".//*[@id='optionDetails']/div/div/div/div[1]/div[1]/div/span/span[1]/span/ul")).sendKeys(Keys.ENTER);
					Thread.sleep(1500);
					driver.findElement(By.id("saveProductGroupingCriteria")).click();
					Thread.sleep(1500);
					
					//Acknowledge
					Thread.sleep(1000);
					if(driver.findElements(By.className("notify-msg")).size()!=0)
					{
					String SuccessMessage = driver.findElement(By.className("notify-msg")).getText();
					System.out.println("SuccessMessage: "+SuccessMessage);
					if(SuccessMessage.equalsIgnoreCase(Message.groupingSave))
					{
						Assert.assertEquals(SuccessMessage,Message.groupingSave);
						if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
						{
							driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
						}
					}
				}
					else {
						System.out.println("else");
						Thread.sleep(1500);
						driver.findElement(By.name("password")).sendKeys(password);
						Thread.sleep(500);
						driver.findElement(By.name("comments")).sendKeys("Test comments");
						Thread.sleep(500);
						driver.findElement(By.xpath(".//*[@id='ackSubmit']")).click();
						Thread.sleep(1000);
						Assert.assertEquals(driver.findElement(By.className("notify-msg")).getText(),Message.groupingSave);
					}
				}
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					Thread.sleep(500);
				}
					
				
				
				
				
				@Test(priority=8)
			//@Test
				public void microbialLimit() throws InterruptedException 
				{
					Thread.sleep(1000);
					driver.get(Constant.URL+"/microbial-limit");
					Thread.sleep(1000);
					
					WebElement selection = driver.findElement(By.id("limitsSetupOption"));
					Select getcriteriaSelection = new Select(selection);
					WebElement option = getcriteriaSelection.getFirstSelectedOption(); 
					String getSelectedOption = option.getText();
					Thread.sleep(1000);
					System.out.println("getSelectedImageOption: "+getSelectedOption);
				if(getSelectedOption.equals("Select Limit"))
				{
					//Limit Set up	
					driver.findElement(By.xpath(".//*[@id='microbialLimitForm']/div/div/div/div[2]/div/div/span/span[1]/span/span[2]")).click();
					driver.findElement(By.xpath(".//*[@id='microbialLimitForm']/div/div/div/div[2]/div/div/span/span[1]/span/span[2]")).sendKeys(Keys.ENTER);
					Thread.sleep(500);
				
					//Bioburden Sampling technique	
					driver.findElement(By.xpath(".//*[@id='optionBioburden']/div[2]/div/div/span/span[1]/span/span[2]")).click();
					driver.findElement(By.xpath(".//*[@id='optionBioburden']/div[2]/div/div/span/span[1]/span/span[2]")).sendKeys(Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ENTER);
					Thread.sleep(1000);
				
					//Select Surface Sample
					if(driver.findElement(By.id("surfaceSample3")).isSelected()==false)
					{
						driver.findElement(By.id("surfaceSample3")).click();
					}
					Thread.sleep(500);
					if(driver.findElement(By.id("surfaceSampleCalculateLimit1")).isSelected()==false)
					{
						driver.findElement(By.id("surfaceSampleCalculateLimit1")).click();
					}
					//Scroll Down
					Thread.sleep(500);
					driver.findElement(By.id("surfaceSample3")).sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB);
					Thread.sleep(500);
					
					//Select Rinse Sample
					if(driver.findElement(By.id("rinseSample3")).isSelected()==false)
					{
						driver.findElement(By.id("rinseSample3")).click();
					}
					Thread.sleep(500);
					if(driver.findElement(By.id("rinseSampleCalculateLimit1")).isSelected()==false)
					{
						driver.findElement(By.id("rinseSampleCalculateLimit1")).click();
					}
					//Scroll Down
					Thread.sleep(500);
					driver.findElement(By.id("rinseSample3")).sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB);
					Thread.sleep(500);
					
					//varFactorMicroEnum
					driver.findElement(By.id("varFactorMicroEnum")).sendKeys("30");
					Thread.sleep(1000);
					
					driver.findElement(By.id("saveMicrobialLimit")).click();
					Thread.sleep(1000);
					
					//Acknowledge
					if(driver.findElements(By.className("notify-msg")).size()!=0)
					{
						String SuccessMessage = driver.findElement(By.className("notify-msg")).getText();
						System.out.println("SuccessMessage: "+SuccessMessage);
						if(SuccessMessage.equalsIgnoreCase(Message.microbialSave))
						{
							Assert.assertEquals(SuccessMessage,Message.microbialSave);
							if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
							{
								driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
							}
						}
					}
					else {
						System.out.println("else");
						Thread.sleep(1500);
						driver.findElement(By.name("password")).sendKeys(password);
						Thread.sleep(500);
						driver.findElement(By.name("comments")).sendKeys("Test comments");
						Thread.sleep(500);
						driver.findElement(By.xpath(".//*[@id='ackSubmit']")).click();
						Thread.sleep(1000);
						Assert.assertEquals(driver.findElement(By.className("notify-msg")).getText(),Message.microbialSave);
					}
				}
				
					//String SuccessMessage = driver.findElement(By.className("notify-msg")).getText();
					//Assert.assertEquals(SuccessMessage,Message.microbialSave);
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					//if(driver.findElements(By.cssSelector(".close.custom-notify-close")).size()!=0)
				//	{
					//	driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
					//}
					Thread.sleep(500);
				//}
			}
				
				
				
				
				
	
}
