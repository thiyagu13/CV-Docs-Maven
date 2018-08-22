package com.eDocs.SanityTesting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

public class EquipmentGroup {
 
				//RepositoryParser parser = new RepositoryParser("");
				//static Utils WD = new Utils();	
				private RepositoryParser parser;
				private WebDriver driver = Constant.driver;
				public String password = Constant.sitepassword;
				static String EquipmentGroupName = "Test Group";
				static String groupIdentifyEquipment = "Test Equipment";
				
				//Create Group Data's
				static String groupCommentsCREATE = "Create equipment group";
				static String groupChangeControlCREATE = "Create equipment group";
				static String groupNoOfRunsCREATE = "3";
				static String groupProtocolDocCREATE = "equip Group protocol for create";
				static String groupReportIDCREATE = "equip Group report for create";
				
				//Edit Group Data's
				static String groupCommentsEDIT = "edit equipment group";
				static String groupChangeControlEDIT = "Edit equipment group";
				static String groupNoOfRunsEDIT = "4";
				static String groupProtocolDocEDIT = "equip Group protocol for edit";
				static String groupReportIDEDIT = "equip Group report for edit";
			/*	
				@BeforeClass
				public void setUp() throws IOException  
				{
					//driver = new FirefoxDriver();
					driver.get(Constant.URL+"/login");
					parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\Equipment.properties");
				}
			
				@Test(priority=2)
				public void Login() throws InterruptedException, IOException
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
					//Thread.sleep(500);
					//driver.get("http://192.168.1.111:8090/equipment-group");
			  		//driver.get(Constant.URL+"/equipment-group");
				}
	*/
	
				@Test(priority=27,invocationCount=2)
				public void CreateEquipmentGroup() throws InterruptedException, IOException
				{
					parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\Equipment.properties");
					Thread.sleep(2000);
					driver.get(Constant.URL+"/equipment-group");
					//driver.navigate().refresh();
					Thread.sleep(1000);
					driver.findElement(By.id("addGroup")).click(); // Click create equipment button
					Thread.sleep(500);
					String GroupName = EquipmentGroupName;
					WebElement eqGRPName = driver.findElement(parser.getbjectLocator("EquipmentGroupName")); //Equipment Name field
					eqGRPName.sendKeys(GroupName);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("GroupingCriteriaSimilar")).click();
					Thread.sleep(500);
			  		//Select Feature
			  		driver.findElement(By.name("name")).sendKeys(Keys.TAB,Keys.TAB,Keys.ENTER,Keys.ENTER);
			  		
					//WebElement selectcriteria= driver.findElement(parser.getbjectLocator("Feature"));
					//Select SelectGroupCriteria = new Select(selectcriteria);
					//SelectGroupCriteria.selectByIndex(1);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("GroupComments")).sendKeys(groupCommentsCREATE);
					Thread.sleep(500);
					
					
					WebElement IdentifuEquip = driver.findElement(By.className("select2-search__field"));
					IdentifuEquip.sendKeys(groupIdentifyEquipment);
					Thread.sleep(200);
					IdentifuEquip.sendKeys(Keys.ENTER);
					Thread.sleep(200);
					IdentifuEquip.sendKeys(groupIdentifyEquipment);
					Thread.sleep(200);
					IdentifuEquip.sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
			        Thread.sleep(500);
			       
					List<String> equipments = new ArrayList<>();
					equipments.add(driver.findElement(By.cssSelector("ul.select2-selection__rendered>li:nth-child(1)")).getText().substring(1));
					equipments.add(driver.findElement(By.cssSelector("ul.select2-selection__rendered>li:nth-child(2)")).getText().substring(1));
					
					System.out.println("-->equipments "+ equipments);
					/*for(String equipment:equipments)
					{
						Thread.sleep(500);
						WebElement selectEquipment = driver.findElement(parser.getbjectLocator("IdentifyEquipment"));
						selectEquipment.sendKeys(equipment);
						Thread.sleep(500);
						selectEquipment.sendKeys(Keys.ENTER);
						Thread.sleep(500);
						selectEquipment.click();
						Thread.sleep(500);
					}
					*/
					
					WebElement next = driver.findElement(parser.getbjectLocator("WorstcaseNextButton"));
					next.click();
					Thread.sleep(500);
					
	//Check name duplicate
					if(driver.findElements(By.className("notify-msg")).size()!=0)
					{
						String getduplicatename = driver.findElement(By.className("notify-msg")).getText();
						driver.findElement(By.className("custom-notify-close")).click();
					
					Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
					for(int k=5;k<1000;k++)
					{
						j.add(k);
					}
					
					Thread.sleep(500);
					if(getduplicatename.equalsIgnoreCase("Group '"+GroupName+"' already exists!"))
					{
						System.out.println("loop");
						for(Integer i:j)
						{
							eqGRPName.clear();
							eqGRPName.sendKeys(GroupName+i);
							Thread.sleep(500);
							next.click(); 
							Thread.sleep(500);
						try
						{
							if(driver.findElements(parser.getbjectLocator("WorstcaseNextButton")).size()!=0)
							{
								String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
								System.out.println("Name duplicate: "+nameduplicate);
								driver.findElement(By.className("custom-notify-close")).click();
								if(nameduplicate.equalsIgnoreCase("Equipment '"+GroupName+i+"' already exists!"))
								{
									continue;
								}
							}
						}
						
							catch(Exception e)
							{
									System.out.println("Not duplicate so break the loop");
									break;
							}
						
							}
						}
					}	
					
					//worst case equipment selection
					Thread.sleep(500);
					driver.findElement(By.id("worstCase-step")).click();
			  		driver.findElement(By.id("worstCase-step")).sendKeys(Keys.ENTER);
					//WebElement worstcase = driver.findElement(parser.getbjectLocator("WorstCaseDetermination"));
					//worstcase.click();
					//worstcase.sendKeys(equipments.get(0));
					//worstcase.sendKeys(Keys.ENTER);
					//worstcase.click();
					Thread.sleep(1000);	
					//no of runs
					driver.findElement(By.id("wcderesult")).sendKeys(Keys.SHIFT,Keys.TAB,Keys.TAB,Keys.ENTER,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ENTER);
					
					//WebElement noofruns = driver.findElement(parser.getbjectLocator("No.ofRuns"));
					//Select noofRunsforWorstcaseeq = new Select(noofruns);
					//noofRunsforWorstcaseeq.selectByVisibleText(groupNoOfRunsCREATE);
					Thread.sleep(500);
					//Select protocol document id
			  		driver.findElement(By.id("wcderesult")).sendKeys(Keys.SHIFT,Keys.TAB,Keys.ENTER,Keys.ENTER);
					//driver.findElement(parser.getbjectLocator("ProtocolDocID")).sendKeys(groupProtocolDocCREATE);//protocol id
			  		
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("ReportID")).sendKeys(groupReportIDCREATE);
					Thread.sleep(500);
					
					//Select worstcase product
			  		Thread.sleep(500);
			  		driver.findElement(By.id("wcderesult")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
					//WebElement worstcaseEQ = driver.findElement(parser.getbjectLocator("WorstCaseEquipment"));
					//Select worstquipment = new Select(worstcaseEQ);
					//worstquipment.selectByIndex(1);
					Thread.sleep(500);
					
					
					WebElement worstcasesubmit = driver.findElement(By.id("saveEquipmentGroup")); //submitEquipmentSamplingDetails
					
					if(worstcasesubmit.getText().equals("Submit"))
					{
						Thread.sleep(1000);
						worstcasesubmit.click();
					}else
					{
						worstcasesubmit.click();
						System.out.println("Custom loop");
						Thread.sleep(500);
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
					}
					Thread.sleep(2000);
					String createGroup = driver.findElement(By.className("notify-msg")).getText(); 
					Assert.assertEquals(createGroup,Message.equipementGroupCREATE);
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
					{
						driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
					}
					Thread.sleep(1000);
				} // closing create equipment group
				
	
				
				@Test(priority=28)
				public void EditEquipmentGroup() throws InterruptedException, IOException
				{
					//Thread.sleep(6000);
					Thread.sleep(3000);
					driver.findElement(By.id("dLabel")).click();
					Thread.sleep(500);
					//driver.findElement(By.xpath(".//*[@id='datatable']/tbody/tr[1]/td[8]/div/ul/li[2]/a")).click(); // Click edit equipment button
					//driver.findElement(By.className("dropdown-item")).sendKeys(Keys.ENTER);
					driver.findElement(By.linkText("Edit")).click();
					Thread.sleep(1000);
					driver.findElement(parser.getbjectLocator("GroupingCriteriaSimilar")).click();
					Thread.sleep(500);
					//WebElement selectcriteria= driver.findElement(parser.getbjectLocator("Feature"));
					//Select SelectGroupCriteria = new Select(selectcriteria);
					//SelectGroupCriteria.selectByIndex(2);
					//Thread.sleep(500);
					
					String getgroupComments = driver.findElement(parser.getbjectLocator("GroupComments")).getAttribute("value"); //verify text presented in the edit
					Assert.assertEquals(getgroupComments,groupCommentsCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("GroupComments")).clear();
					driver.findElement(parser.getbjectLocator("GroupComments")).sendKeys(groupCommentsEDIT);
					Thread.sleep(500);
					
					
					WebElement next = driver.findElement(parser.getbjectLocator("WorstcaseNextButton"));
					next.click();
					Thread.sleep(500);
					
					
					System.out.println(driver.findElements(parser.getbjectLocator("No.ofRuns")).size());
					if(driver.findElements(parser.getbjectLocator("No.ofRuns")).size()!=0)
					{
					//no of runs
						WebElement noofruns = driver.findElement(parser.getbjectLocator("No.ofRuns"));
						Select noofRunsforWorstcaseeq = new Select(noofruns);
						WebElement option = noofRunsforWorstcaseeq.getFirstSelectedOption(); 
						String getNoofRuns = option.getText();
						Assert.assertEquals(getNoofRuns,groupNoOfRunsCREATE);
						//noofRunsforWorstcaseeq.selectByVisibleText(groupNoOfRunsEDIT);
						driver.findElement(By.id("wcderesult")).sendKeys(Keys.SHIFT,Keys.TAB,Keys.TAB,Keys.ENTER,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ENTER);
						
						Thread.sleep(500);
						//Select protocol document id
				  		WebElement protocol = driver.findElement(parser.getbjectLocator("ProtocolDocID"));
						Select selectprotocol = new Select(protocol);
						WebElement getprotocol = selectprotocol.getFirstSelectedOption(); 
						String getprotocolText = getprotocol.getText();
						if(getprotocolText.equalsIgnoreCase(""))
						{
							new Exception();
						}
						//Report ID
						String getReportID = driver.findElement(parser.getbjectLocator("ReportID")).getAttribute("value"); //verify text presented in the edit
						Assert.assertEquals(getReportID,groupReportIDCREATE);
						Thread.sleep(500);
						driver.findElement(parser.getbjectLocator("ReportID")).clear();
						driver.findElement(parser.getbjectLocator("ReportID")).sendKeys(groupReportIDEDIT);
						Thread.sleep(500);
						
						//Worstcase equipment
						WebElement worstcaseEQ = driver.findElement(parser.getbjectLocator("WorstCaseEquipment"));
						Select worstquipment = new Select(worstcaseEQ);
						WebElement getworstquipment = worstquipment.getFirstSelectedOption(); 
						String getgetworstquipmentText = getworstquipment.getText();
						if(getgetworstquipmentText.equalsIgnoreCase(""))
						{
							new Exception();
						}
						Thread.sleep(500);
					}
					
					Thread.sleep(1000);
					WebElement worstcasesubmit = driver.findElement(By.id("saveEquipmentGroup")); //submitEquipmentSamplingDetails
					
					if(worstcasesubmit.getText().equals("Submit"))
					{
						worstcasesubmit.click();
						Thread.sleep(1000);
						driver.findElement(By.id("comments")).click();
						Thread.sleep(500);
						driver.findElement(By.id("comments")).sendKeys(Keys.SHIFT,Keys.TAB,password);
						Thread.sleep(500);
						driver.findElement(By.id("ackSubmit")).click();
						
						
					}else
					{
						worstcasesubmit.click();
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
								driver.findElement(By.id("comments")).click();
								Thread.sleep(500);
								driver.findElement(By.id("comments")).sendKeys(Keys.SHIFT,Keys.TAB,password);
								Thread.sleep(500);
								driver.findElement(By.id("ackSubmit")).click();
						}
					Thread.sleep(1000);
					String EditGroup = driver.findElement(By.className("notify-msg")).getText();
					Assert.assertEquals(EditGroup,Message.equipmentGroupEDIT);
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
					{
						driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
					}
					Thread.sleep(600);
				} // closing Edit equipment group
				
				
				
				
				@Test(priority=29)
				public void SingleDeleteEquipmentGroup() throws InterruptedException, IOException
				{
					Thread.sleep(3000);
					driver.get(Constant.URL+"/equipment-group");
					driver.findElement(By.id("dLabel")).click();
					Thread.sleep(1000);
					//driver.findElement(By.xpath(".//*[@id='datatable']/tbody/tr[1]/td[8]/div/ul/li[3]/a")).click(); // Click edit equipment button
					//driver.findElement(By.className("dropdown-item")).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
					driver.findElement(By.linkText("Delete")).click();
					Thread.sleep(1000);
					//driver.findElement(By.xpath(".//*[@id='openAckModal']")).click();//click Yes in popup
					//Thread.sleep(500);
					driver.findElement(By.name("ackChangeControlNo")).sendKeys("111");
					Thread.sleep(500);
					driver.findElement(By.name("ackChangeControlNo")).sendKeys(Keys.TAB +password);
					Thread.sleep(500);
					driver.findElement(By.id("comments")).sendKeys("Delete single equipment");
					Thread.sleep(500);
					driver.findElement(By.xpath(".//*[@id='dynamicModal']/div[3]/div/button[2]")).click();
					Thread.sleep(1500);
					String deletemsg = driver.findElement(By.className("notify-msg")).getText(); // get deleted esuccess message
					Assert.assertEquals(deletemsg,Message.equipmentGroupDELETE);
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
					{
						driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
					}
					Thread.sleep(600);
				}
				

				@Test(priority=30)
				public void MultiDeleteEquipmentGroup() throws InterruptedException, IOException
				{
					Thread.sleep(2000);
					driver.get(Constant.URL+"/equipment-group");
					driver.findElement(By.id("searchDataTable")).sendKeys(EquipmentGroupName);
					Thread.sleep(1000);
					driver.findElement(By.id("example-select-all")).click();
					Thread.sleep(1000);
					driver.findElement(By.id("deleteSelectedEquipmentGroup")).click(); // multi delete
					//driver.findElement(By.id("deleteSelectedEquipmentGroup")).sendKeys(Keys.ENTER); // multi delete
					//Thread.sleep(500);
					//driver.findElement(By.xpath(".//*[@id='openAckModal']")).click();//click Yes in popup
					Thread.sleep(500);
					driver.findElement(By.name("ackChangeControlNo")).sendKeys("111");
					Thread.sleep(500);
					driver.findElement(By.name("ackChangeControlNo")).sendKeys(Keys.TAB +password);
					Thread.sleep(500);
					driver.findElement(By.id("comments")).sendKeys("Delete single equipment");
					Thread.sleep(500);
					//driver.findElement(By.id("ackSubmit")).click();
					driver.findElement(By.cssSelector(".btn.blue-btn.red.waves-effect.deleteData")).click();
					Thread.sleep(1000);
					String deletemsg = driver.findElement(By.className("notify-msg")).getText(); // get deleted esuccess message
					Assert.assertEquals(deletemsg,Message.equipmentGroupDELETE);
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
					{
						driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
					}
					Thread.sleep(600);
					
				}
				
				
				
				/*@Test(priority=6)
				public void ExportEquipmentGroup() throws Exception
				{
					Utils.ExportPDF(driver);
				}*/
				
				
				
				
				
	
}
