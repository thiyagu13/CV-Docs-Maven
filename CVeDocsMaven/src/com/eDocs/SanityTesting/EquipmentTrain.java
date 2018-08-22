package com.eDocs.SanityTesting;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

public class EquipmentTrain {
  
	
			private RepositoryParser parser;
			private WebDriver driver = Constant.driver;
			public String password = Constant.sitepassword;
			static String EquipmenttrainName= "Selenium Train";
			static String TrainMultiDeleteSEARCH="Test Train";
			static String IdentifyEquipment="Test Equipment";
			//Create Equipment Train
			static String TrainChnageContorlNoCREATE="111";
			static String TrainChnageContorlNoEDIT="222";
			
			static String TrainRinseVolumeCREATE="10";
			static String uploadImageOptionCREATE="No";
			static String trainLocationCREATE="Chennai";
			static String trainRationaleCREATE="Rationale Creat";
			
			//Edit Equipment Train
			static String TrainRinseVolumeEDIT="30";
			static String uploadImageOptionEDIT="No";
			static String trainLocationEDIT="BangChennai";
			static String trainRationaleEDIT="Rationale Edit";
			
			
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
		  		//driver.get(Constant.URL+"/equipment-train");
			}
				
			
			@Test(priority=31,invocationCount=2)
			public void CreateEquipmentTrain() throws InterruptedException, SQLException, ClassNotFoundException, IOException
			{
				parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\Equipment.properties");
				Thread.sleep(1000);
				driver.get(Constant.URL+"/equipment-train");
				Thread.sleep(1000);
				driver.findElement(parser.getbjectLocator("createTrain")).click(); // Click create equipment button
				Thread.sleep(1000);
				String trainName = EquipmenttrainName;
				driver.findElement(parser.getbjectLocator("EquipmentTrainName")).sendKeys(trainName);; //Equipment Name field
				Thread.sleep(500);
				
				WebElement IdentifuEquip = driver.findElement(By.className("select2-search__field"));
				IdentifuEquip.sendKeys(IdentifyEquipment);
				Thread.sleep(300);
				IdentifuEquip.sendKeys(Keys.ENTER);
				Thread.sleep(300);
				String s = driver.findElement(By.name("groupEquipment")).getText();
				System.out.println(s);
				String getselectedEquipName = driver.findElement(By.className("select2-selection__rendered")).getText();
				System.out.println(getselectedEquipName);
				IdentifuEquip.sendKeys(IdentifyEquipment);
				Thread.sleep(500);
				IdentifuEquip.sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
		        Thread.sleep(500);
		        //driver.findElement(By.className("select2-selection__rendered")).getText().substring(1); //get total selectd equipments
		       
				List<String> equipments = new ArrayList<>();
				equipments.add(getselectedEquipName);
				//equipments.add(driver.findElement(By.className("select2-selection__rendered")).getText().substring(1));
				//equipments.add(driver.findElement(By.cssSelector("ul.select2-selection__rendered>li:nth-child(1)")).getText().substring(1));
				//System.out.println("equipments"+equipments);
		        
		        
		        //driver.findElement(By.id("equiptreeMultiple")).click();
		        //driver.findElement(By.id("equiptreeMultiple")).sendKeys(Keys.ENTER);
		        //Select s = new Select(driver.findElement(By.id("equiptreeMultiple")));
		        //s.selectByIndex(0);
		        
				Thread.sleep(1000);
				driver.findElement(parser.getbjectLocator("TrainChangeControlNo")).sendKeys(TrainChnageContorlNoCREATE);
				
				Thread.sleep(500);
				driver.findElement(By.id("saveEquipmentTrainDetails")).click();
				Thread.sleep(1000);
				
				//if duplicate equipment name
				if( driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Train '"+trainName+"' already exists!"))
				{
					String getduplicatename = driver.findElement(By.className("notify-msg")).getText();
					driver.findElement(By.className("custom-notify-close")).click();
				
				Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
				for(int k=5;k<1000;k++)
				{
					j.add(k);
				}
				
				Thread.sleep(500);
				if(getduplicatename.equalsIgnoreCase("Train '"+trainName+"' already exists!"))
				{
					System.out.println("loop");
					for(Integer i:j)
					{
						driver.findElement(parser.getbjectLocator("EquipmentTrainName")).clear();
						driver.findElement(parser.getbjectLocator("EquipmentTrainName")).sendKeys(trainName+i);
						Thread.sleep(500);
						driver.findElement(By.id("saveEquipmentTrainDetails")).click();
						Thread.sleep(500);
						if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Train '"+trainName+i+"' already exists!"))
						{
							String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
							System.out.println("Name duplicate: "+nameduplicate);
							driver.findElement(By.className("custom-notify-close")).click();
							if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Train '"+trainName+i+"' already exists!"))
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
				
				
				//if duplicate equipment set
				if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Duplicate equipment set") )
				{
						driver.findElement(By.className("custom-notify-close")).click();
						WebElement IdentifuEquipment = driver.findElement(By.className("select2-search__field"));
						IdentifuEquipment.sendKeys(IdentifyEquipment);
						Thread.sleep(500);
						IdentifuEquipment.sendKeys(Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ENTER);
						Thread.sleep(500);
						driver.findElement(By.id("saveEquipmentTrainDetails")).click();
						Thread.sleep(500);
						if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Duplicate equipment set"))
						{
							driver.findElement(By.className("custom-notify-close")).click();
							WebElement IdentifuDupliEquipment = driver.findElement(By.className("select2-search__field"));
							IdentifuDupliEquipment.sendKeys(IdentifyEquipment);
							Thread.sleep(500);
							IdentifuDupliEquipment.sendKeys(Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ENTER);
							Thread.sleep(500);
							driver.findElement(By.id("saveEquipmentTrainDetails")).click();
							Thread.sleep(500);
						}
					
				}	// closing if loop duplicate equipment set
				
				

				
				//Location information
				Thread.sleep(500);
				//driver.findElement(By.id("rinseVolume")).clear();
				//driver.findElement(By.id("rinseVolume")).sendKeys(TrainRinseVolumeCREATE);
				//Thread.sleep(1000);
				
				//WebElement uploadimage = driver.findElement(parser.getbjectLocator("TrainDoyouwanttouploadimages?"));
				//Select YesorNo = new Select(uploadimage);
				//YesorNo.selectByVisibleText(uploadImageOptionCREATE);
				//upload image NO
				driver.findElement(By.id("saveEquipmentTrainSamplingDetails")).sendKeys(Keys.SHIFT,Keys.TAB,Keys.TAB,Keys.TAB,Keys.ENTER,Keys.ARROW_DOWN,Keys.ENTER);
				Thread.sleep(500);
				//upload image if yes
				//driver.findElement(By.xpath(".//*[@id='upload-images']/div/div/div/div/input")).sendKeys("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\Test Data\\equipTrain.jpg");
				//Thread.sleep(1000);
				
				//add location
				//driver.findElement(By.cssSelector(".btn.orange-btn.waves-effect.mr-20")).click();
				driver.findElement(By.id("addPinName")).click();
				Thread.sleep(500);
				driver.findElement(parser.getbjectLocator("TrainLocationName")).sendKeys(trainLocationCREATE);
				Thread.sleep(500);
				//Select MOC
				driver.findElement(parser.getbjectLocator("TrainLocationName")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
				Thread.sleep(500);
				//WebElement MOC = driver.findElement(parser.getbjectLocator("TrainMoc")); //MOC
				//Select selectMOC = new Select(MOC);
				//selectMOC.selectByIndex(1);
				
				//Rationale
				driver.findElement(parser.getbjectLocator("TrainRationale")).sendKeys(trainRationaleCREATE);
				Thread.sleep(500);
				
				//save image if upload yes
				//driver.findElement(By.xpath(".//*[@id='preview-image']/div/div[2]/img")).click(); //click to save uploaded image
				//Thread.sleep(1000);
				
				WebElement samplingbutton = driver.findElement(parser.getbjectLocator("TrainSubmitbutton")); //submitEquipmentSamplingDetails
				Thread.sleep(1000);
				if(samplingbutton.getText().equalsIgnoreCase("Submit"))
				{
					System.out.println("No Custom loop");
					driver.findElement(parser.getbjectLocator("TrainSubmitbutton")).click();
					Thread.sleep(500);
					
				}else
				{
					samplingbutton.click();
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
							
				}
				Thread.sleep(4000);
				String createEquipmentTrain = driver.findElement(By.className("notify-msg")).getText();
				System.out.println(createEquipmentTrain);
				Assert.assertEquals(createEquipmentTrain,Message.equipementTrainCREATE);
				String className = this.getClass().getName(); // get current class name - for screenshot
				String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
				Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
				if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				}
				Thread.sleep(600);
			} // closing Create Equipment Train method
		
	
			
			@Test(priority=32)
			public void EditEquipmentTrain() throws InterruptedException, SQLException, ClassNotFoundException, IOException
			{
				Thread.sleep(1000);
				driver.findElement(By.id("dLabel")).click();
				Thread.sleep(500);
				//driver.findElement(By.xpath(".//*[@id='datatable']/tbody/tr[1]/td[8]/div/ul/li[2]/a")).click(); // Click edit equipment button
				driver.findElement(By.className("dropdown-item")).sendKeys(Keys.ENTER);
				Thread.sleep(500);
				//driver.findElement(parser.getbjectLocator("TrainChangeControlNo")).clear();
				//driver.findElement(parser.getbjectLocator("TrainChangeControlNo")).sendKeys("234456");
				
				Thread.sleep(500);
				driver.findElement(By.id("saveEquipmentTrainDetails")).click();
				Thread.sleep(500);
				
				//String getTrainvolume = driver.findElement(By.id("rinseVolume")).getAttribute("value"); //verify text presented in the edit
				//Assert.assertEquals(getTrainvolume,TrainRinseVolumeCREATE);
				//Thread.sleep(500);
				//driver.findElement(By.id("rinseVolume")).sendKeys(TrainRinseVolumeEDIT);
				
				//WebElement s = driver.findElement(By.xpath(".//*[@id='upload-images']/div/div/div/div/input"));
				//System.out.println("s "+s.getText());
				//System.out.println("SRC: "+s.getAttribute("src"));	
				Thread.sleep(1500);
				WebElement uploadimage = driver.findElement(parser.getbjectLocator("TrainDoyouwanttouploadimages?"));
				Select YesorNo = new Select(uploadimage);
				WebElement option = YesorNo.getFirstSelectedOption(); 
				String getimageOption = option.getText();
				System.out.println("getproducttype "+getimageOption);
				Assert.assertEquals(getimageOption,uploadImageOptionCREATE);
				Thread.sleep(500);
				
				
				//add location
				Thread.sleep(500);
				String getTrainLocation = driver.findElement(By.id("locationName1")).getAttribute("value"); //verify text presented in the edit
				Assert.assertEquals(getTrainLocation,trainLocationCREATE);
				Thread.sleep(500);
				driver.findElement(By.id("locationName1")).clear();
				Thread.sleep(500);
				driver.findElement(By.id("locationName1")).sendKeys(trainLocationEDIT);
				Thread.sleep(500);
				
				//WebElement moc = driver.findElement(By.xpath(".//*[@id='moc1']"));
				//Select SelectMOC = new Select(moc);
				//SelectMOC.selectByIndex(1);
				
				//String getRationale = driver.findElement(By.name("rationale1")).getAttribute("value"); //verify text presented in the edit
				//Assert.assertEquals(getRationale,trainRationaleCREATE);
				//Thread.sleep(500);
				//driver.findElement(By.name("rationale1")).clear();
				//Thread.sleep(500);
				//driver.findElement(By.name("rationale1")).sendKeys(trainRationaleEDIT);
				//Thread.sleep(500);
				
				WebElement samplingbutton = driver.findElement(parser.getbjectLocator("TrainSubmitbutton")); //submitEquipmentSamplingDetails
				Thread.sleep(200);
				if(samplingbutton.getText().equalsIgnoreCase("Submit"))
				{
					System.out.println("No Custom loop");
					driver.findElement(parser.getbjectLocator("TrainSubmitbutton")).click();
					Thread.sleep(2000);
					driver.findElement(By.id("comments")).click();
					Thread.sleep(500);
					driver.findElement(By.id("comments")).sendKeys(Keys.SHIFT,Keys.TAB,password);
					Thread.sleep(500);
					driver.findElement(By.id("ackSubmit")).click();
					
				}else
				{
					samplingbutton.click();
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
				Thread.sleep(6000);
				String EditEquipmentTrain = driver.findElement(By.className("notify-msg")).getText();
				System.out.println(EditEquipmentTrain);
				Assert.assertEquals(EditEquipmentTrain,Message.equipmentTrainEDIT);
				
				String className = this.getClass().getName(); // get current class name - for screenshot
				String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
				Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
				if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				}
				Thread.sleep(600);
			} // closing Edit Equipment Train method
			
			
			
			
			@Test(priority=33)
			public void SingleDeleteEquipmentTrain() throws InterruptedException, IOException
			{
				Thread.sleep(2000);
				driver.get(Constant.URL+"/equipment-train");
				Thread.sleep(1000);
				driver.findElement(By.id("dLabel")).click();
				Thread.sleep(500);
				//driver.findElement(By.xpath(".//*[@id='datatable']/tbody/tr[1]/td[8]/div/ul/li[3]/a")).click(); // Click edit equipment button
				Thread.sleep(1000);
				//driver.findElement(By.xpath(".//*[@id='openAckModal']")).click();//click Yes in popup
				driver.findElement(By.linkText("Delete")).click();
				Thread.sleep(500);
				driver.findElement(By.name("ackChangeControlNo")).sendKeys("111");
				Thread.sleep(500);
				driver.findElement(By.name("ackChangeControlNo")).sendKeys(Keys.TAB +password);
				Thread.sleep(500);
				driver.findElement(By.id("comments")).sendKeys("Delete single equipment");
				Thread.sleep(500);
				//driver.findElement(By.xpath(".//*[@id='dynamicModal']/div[3]/div/button[2]")).click();
				driver.findElement(By.id("comments")).sendKeys(Keys.TAB,Keys.TAB,Keys.ENTER); //Submit
				Thread.sleep(1500);
				String deletemsg = driver.findElement(By.className("notify-msg")).getText(); // get deleted esuccess message
				Assert.assertEquals(deletemsg,Message.equipmentTrainDELETE);
				String className = this.getClass().getName(); // get current class name - for screenshot
				String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
				Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
				if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
				{
					driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
				}
				Thread.sleep(600);
			}
			

			@Test(priority=34)
			public void MultiDeleteEquipmentTrain() throws InterruptedException, IOException
			{
				Thread.sleep(2000);
				driver.get(Constant.URL+"/equipment-train");
				Thread.sleep(1000);
				driver.findElement(By.id("searchDataTable")).sendKeys(EquipmenttrainName);
				Thread.sleep(1000);
				driver.findElement(By.id("example-select-all")).click();
				Thread.sleep(1000);
				driver.findElement(By.id("deleteSelectedEquipmentTrain")).click(); // multi delete
				//driver.findElement(By.id("deleteSelectedEquipmentGroup")).sendKeys(Keys.ENTER); // multi delete
				//Thread.sleep(500);
				//driver.findElement(By.xpath(".//*[@id='openAckModal']")).click();//click Yes in popup
				Thread.sleep(500);
				driver.findElement(By.name("ackChangeControlNo")).sendKeys("111");
				Thread.sleep(500);
				driver.findElement(By.name("ackChangeControlNo")).sendKeys(Keys.TAB +password);
				Thread.sleep(500);
				driver.findElement(By.id("comments")).sendKeys("Delete multiple equipment");
				Thread.sleep(500);
				//driver.findElement(By.id("ackSubmit")).click();
				driver.findElement(By.id("comments")).sendKeys(Keys.TAB,Keys.TAB,Keys.ENTER); //Submit
				Thread.sleep(1500);
				String deletemsg = driver.findElement(By.className("notify-msg")).getText(); // get deleted esuccess message
				Assert.assertEquals(deletemsg,Message.equipmentTrainDELETE);
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
			public void ExportEquipmentTrain() throws Exception
			{
				Utils.ExportPDF(driver);
			}*/
			
			
			
			
			
			
			
	
	
}
