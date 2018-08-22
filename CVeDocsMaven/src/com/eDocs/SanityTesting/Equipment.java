package com.eDocs.SanityTesting;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
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

public class Equipment {
  
				private RepositoryParser parser;
				private WebDriver driver = Constant.driver;
				public String password = Constant.sitepassword;
				static String EquipmentName ="Test Equipment";
				
				//Create Equipment Data's
				static String EquipmentModelCREATE ="Eq Create Model1";
				static String EquipmentSerialNoCREATE ="Eq Create 111";
				static String EquipmentManufacturerCREATE ="Eq Create Manu1";
				static String EquipmentSFCREATE ="10000";
				static String EquipmentSFDataSourceCREATE ="Eq Create Data Source";
				static String EquipmentMinBatchCREATE ="10";
				static String EquipmentQuaDocIDCREATE ="Eq Qua ID Create";
				static String EquipmentCleaningInfoCREATE ="Create Euipment";
				static String EquipmentPreferentialTransferCREATE ="Yes";
				static String EquipmentPreferentialTransferSFCREATE ="100";
				static String EquipmentPrimaryPackagingeOptionCREATE ="Yes";
				static String EquipmentSamplingLocationCREATE ="Chennai";
				static String EquipmentCleaningProcessTypeCREATE ="2";

				static String EquipmentSegmentNameCREATE="Seg name1";
				static String EquipmentSegmentSFCREATE="100";
				
				

				//Edit Equipment Data's
				static String EquipmentModelEDIT ="Eq Edit Model";
				static String EquipmentSerialNoEDIT ="Eq Edit 222";
				static String EquipmentManufacturerEDIT ="Eq Edit Manu";
				static String EquipmentSFEDIT ="20000";
				static String EquipmentSFDataSourceEDIT ="Eq Edit Data Source";
				static String EquipmentMinBatchEDIT ="15";
				static String EquipmentQuaDocIDEDIT ="Eq Qua ID Edit";
				static String EquipmentCleaningInfoEDIT ="Edit Euipment";
				static String EquipmentPreferentialTransferEDIT ="No";
				static String EquipmentSegmentNameEDIT="Seg name2";
				static String EquipmentSegmentSFEDIT="200";
				static String EquipmentCleaningProcessTypeEDT ="4";
				
				
				
				
				@BeforeClass
				public void setUp() throws IOException  
				{
					//driver = new FirefoxDriver();
					//driver.get("http://192.168.1.111:8090");
					driver.get(Constant.URL);
					parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\Equipment.properties");
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
					driver.get(Constant.URL+"/equipment");
					Thread.sleep(1500);
					//driver.findElement(By.id("addEquipment")).click(); 
			  		//Thread.sleep(1500);
			  		//WebElement alphanumericField = driver.findElement(By.name("name"));
			  		//alphanumericField.sendKeys("test");
				}
		
				@Test(priority=18)
				public void CreateEquipmentwithRiskAssessment() throws InterruptedException, SQLException, ClassNotFoundException, IOException
				{
					parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\Equipment.properties");
					//Thread.sleep(5000);
					Thread.sleep(2000);
					//SampleLocationRiskAssessment();
					
					driver.get(Constant.URL+"/equipment");
					//driver.navigate().refresh();
					Thread.sleep(2000);
					driver.findElement(parser.getbjectLocator("CreateEquipment")).click(); // Click create equipment button
					Thread.sleep(1000);
					String equipmentName = EquipmentName;
					WebElement eqName = driver.findElement(parser.getbjectLocator("EquipmentName")); //Equipment Name field
					eqName.sendKeys(equipmentName);
					Thread.sleep(500);
					//WebElement locationdropdown = driver.findElement(By.name("location"));
					//Select location = new Select(locationdropdown);
					//location.selectByIndex(1);
					driver.findElement(By.name("name")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("Model")).sendKeys(EquipmentModelCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("AssetNo/SerialNo")).sendKeys(EquipmentSerialNoCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("Manufacturer")).sendKeys(EquipmentManufacturerCREATE);
					Thread.sleep(500);
					//Scroll down the page
					driver.findElement(parser.getbjectLocator("Manufacturer")).sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB);
					Thread.sleep(500);
					driver.findElement(By.id("surfaceArea")).sendKeys(EquipmentSFCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("SurfaceAreaDataSource")).sendKeys(EquipmentSFDataSourceCREATE);
					Thread.sleep(500);
					
					driver.findElement(parser.getbjectLocator("SurfaceAreaDataSource")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
					//WebElement preferentialTransfer = driver.findElement(parser.getbjectLocator("CanPreferentialTransferofResidueOccurwiththisEquipment?"));
					//Select preferentialTransferType = new Select(preferentialTransfer);
					//preferentialTransferType.selectByVisibleText(EquipmentPreferentialTransferCREATE);
					Thread.sleep(500);
					
					driver.findElement(By.id("preferentialTransferSurfaceArea")).sendKeys(EquipmentPreferentialTransferSFCREATE);
					Thread.sleep(500);

					driver.findElement(By.id("preferentialTransferSurfaceArea")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
					//WebElement PrimaryPackaging = driver.findElement(By.id("primaryPackaging"));
					//Select SelectPrimaryPackaging = new Select(PrimaryPackaging);
					//SelectPrimaryPackaging.selectByVisibleText(EquipmentPrimaryPackagingeOptionCREATE);
					Thread.sleep(500);
					//scroll down
					driver.findElement(By.id("preferentialTransferSurfaceArea")).sendKeys(Keys.TAB,Keys.TAB);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("MinimumBatchSize")).sendKeys(EquipmentMinBatchCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("MinimumBatchSize")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
					Thread.sleep(500);
					//Cleaning SOP
					driver.findElement(parser.getbjectLocator("MinimumBatchSize")).sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.ENTER,Keys.ENTER);
					//WebElement SOP = driver.findElement(parser.getbjectLocator("CleaningSOPNo"));
					//Select CleaningSOP = new Select(SOP);
					//CleaningSOP.selectByIndex(1);
					Thread.sleep(500);
					//scroll down the page
					driver.findElement(parser.getbjectLocator("MinimumBatchSize")).sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB);
					Thread.sleep(500);
					//cleaing proceess type
					driver.findElement(parser.getbjectLocator("OtherCleaningRelevantInformation")).sendKeys(Keys.SHIFT,Keys.TAB,Keys.ENTER,Keys.ARROW_DOWN,Keys.ENTER);
					//WebElement processType = driver.findElement(parser.getbjectLocator("CleaningProcessType"));
					//Select CleaningProcessType = new Select(processType);
					//CleaningProcessType.selectByValue(EquipmentCleaningProcessTypeCREATE);
					Thread.sleep(500);
					
					driver.findElement(parser.getbjectLocator("OtherCleaningRelevantInformation")).sendKeys(EquipmentCleaningInfoCREATE);
					Thread.sleep(1000);
					//driver.findElement(parser.getbjectLocator("NextButton")).click();
					WebElement submit = driver.findElement(By.id("saveEquipment"));
					submit.click();
					Thread.sleep(500);
					
					
					Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
					for(int k=5;k<1000;k++)
					{
						j.add(k);
					}
					
					if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Equipment '"+equipmentName+"' already exists!"))
					{
						String getduplicatename = driver.findElement(By.className("notify-msg")).getText();
						driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
					
					Thread.sleep(500);
					if(getduplicatename.equalsIgnoreCase("Equipment '"+equipmentName+"' already exists!"))
					{
						for(Integer i:j)
						{
							//driver.findElement(parser.getbjectLocator("ActiveIngredientName")).clear();
							eqName.clear();
							Thread.sleep(200);
							eqName.sendKeys(equipmentName+i);
							//driver.findElement(parser.getbjectLocator("ActiveIngredientName")).sendKeys(Name+i);
							Thread.sleep(500);
							submit.click();
							//driver.findElement(parser.getbjectLocator("APIsubmit")).click();
							Thread.sleep(500);
							if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Equipment '"+equipmentName+i+"' already exists!"))
							{
								String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
								System.out.println("Name duplicate: "+nameduplicate);
								driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
								if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Equipment '"+equipmentName+i+"' already exists!"))
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
					if( driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Equipment Identification Number '"+EquipmentSerialNoCREATE+"' already exists!"))
					{
						String getduplicateID = driver.findElement(By.className("notify-msg")).getText();
						driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
					System.out.println("getduplicatename: "+getduplicateID);
					if(getduplicateID.equalsIgnoreCase("Equipment Identification Number '"+EquipmentSerialNoCREATE+"' already exists!"))
					{
						for(Integer i:j)
						{
							driver.findElement(parser.getbjectLocator("AssetNo/SerialNo")).clear();
							Thread.sleep(200);
							driver.findElement(parser.getbjectLocator("AssetNo/SerialNo")).sendKeys(EquipmentSerialNoCREATE+i);
							Thread.sleep(500);
							//driver.findElement(parser.getbjectLocator("APIsubmit")).click();
							submit.click();
							Thread.sleep(500);
							if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Equipment Identification Number '"+EquipmentSerialNoCREATE+i+"' already exists!"))
							{
								String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
								System.out.println("Name duplicate: "+nameduplicate);
								driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
								if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Equipment Identification Number '"+EquipmentSerialNoCREATE+i+"' already exists!"))
								{
									continue;
								}
							}
									System.out.println("Not duplicate so break the loop");
									break;
							}
					}
					
				}
					
					Thread.sleep(3000);
					System.out.println("submitLocationAssessment: "+driver.findElements(parser.getbjectLocator("submitLocationAssessment")).size());
					if(driver.findElements(parser.getbjectLocator("submitLocationAssessment")).size()!=0)
					{
					//Location Risk Assessment
					Thread.sleep(1000);
					driver.findElement(parser.getbjectLocator("AddLocation")).click();
					driver.findElement(parser.getbjectLocator("AddLocation")).click();
					Thread.sleep(500);
					driver.findElement(By.id("checkbox1")).click();
					Thread.sleep(500);
					driver.findElement(By.id("checkbox2")).click();
					Thread.sleep(500);
					driver.findElement(By.id("location1")).sendKeys(EquipmentSamplingLocationCREATE);
					Thread.sleep(500);
					driver.findElement(By.id("location2")).sendKeys(EquipmentSamplingLocationCREATE);
					Thread.sleep(500);
					//Enter Locations
			        Thread.sleep(500);
			        driver.findElement(By.id("location1")).sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB);
			        //Delete row
			        Thread.sleep(1000);
			        driver.findElement(By.cssSelector(".remove-row-icon")).click();
			        Thread.sleep(1000);
			        driver.findElement(By.id("location1")).sendKeys(EquipmentSamplingLocationCREATE);
			        Thread.sleep(500);
			       driver.findElement(By.cssSelector(".remove-row-icon")).sendKeys(Keys.SHIFT,Keys.TAB,Keys.SHIFT,Keys.TAB,Keys.SHIFT,Keys.TAB,Keys.SHIFT,Keys.TAB,Keys.SHIFT,Keys.TAB,Keys.SHIFT,Keys.TAB,Keys.SHIFT,Keys.TAB,Keys.SHIFT,Keys.TAB,Keys.SHIFT,Keys.TAB);
			       Thread.sleep(1000);
					
					driver.findElement(parser.getbjectLocator("submitLocationAssessment")).click(); // submit location assessment
					//Location Risk Assessment -End
					}
					
					
					Thread.sleep(1000);
					//Do you want to upload images?
					driver.findElement(By.xpath(".//*[@id='needUloadImages']/div/div/span/span[1]/span/span[2]")).click();
					driver.findElement(By.xpath(".//*[@id='needUloadImages']/div/div/span/span[1]/span/span[2]")).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
					//WebElement uploadsamplingimage = driver.findElement(parser.getbjectLocator("Doyouwanttouploadimages?"));
					//Select YesorNo = new Select(uploadsamplingimage);
					//YesorNo.selectByIndex(2); // select No option
					Thread.sleep(1000);
					//upload image
					//driver.findElement(By.xpath(".//*[@id='upload-images']/div/div/input")).sendKeys("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\Test Data\\equipTrain.jpg");
					//Thread.sleep(1000);
					//add location
					//driver.findElement(By.xpath(".//*[@id='addPinName']")).click();
					//click add location pin
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("AddLocationButton")).click(); 
					//LocationName
					Thread.sleep(500);
					System.out.println("locationName1:" +driver.findElement(By.id("locationName1")).getAttribute("type"));
					if(driver.findElement(By.id("locationName1")).getAttribute("type").contains("text")) 
					{
						Thread.sleep(500);
						driver.findElement(By.id("locationName1")).clear();
						driver.findElement(By.id("locationName1")).sendKeys("Chennai");
						Thread.sleep(500);
						//select MOC
						driver.findElement(By.id("locationName1")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
						Thread.sleep(500);
					}
					Thread.sleep(1000);
					//MOC Selection
					System.out.println("-->"+driver.findElement(By.id("locationName1")).getAttribute("type"));
					if(driver.findElement(By.id("locationName1")).getAttribute("type").contains("select"))
					{
						driver.findElement(By.xpath(".//*[@id='marker-num1']/div[2]/span/span[1]/span")).click();
						driver.findElement(By.xpath(".//*[@id='marker-num1']/div[2]/span/span[1]/span")).sendKeys(Keys.ENTER);
						Thread.sleep(1000);
						driver.findElement(By.xpath(".//*[@id='marker-num1']/div[3]/span/span[1]/span")).click();
						driver.findElement(By.xpath(".//*[@id='marker-num1']/div[3]/span/span[1]/span")).sendKeys(Keys.ENTER);
					}
					
					//select sampling method and test
					driver.findElement(By.xpath(".//*[@id='marker-num1']/div[4]/span/span[1]/span")).click();
					driver.findElement(By.xpath(".//*[@id='marker-num1']/div[4]/span/span[1]/span")).sendKeys(Keys.ENTER);
					
				//	WebElement Sampling = driver.findElement(By.id("mocy1"));
					//Select SelectSampling = new Select(Sampling);
					//SelectSampling.selectByValue("1");
					
					
					//save image
					//driver.findElement(By.xpath(".//*[@id='preview-image']/div/div[2]/img")).click(); //click to save uploaded image
					//Thread.sleep(1000);
					Thread.sleep(1000);
					WebElement samplingbutton = driver.findElement(parser.getbjectLocator("samplingDetailsNextButton")); //submitEquipmentSamplingDetails
					if(samplingbutton.getText().equalsIgnoreCase("Submit"))
					{
						System.out.println("No Custom loop");
						driver.findElement(parser.getbjectLocator("samplingDetailsNextButton")).click();
						Thread.sleep(2000);
					}
					
					if(samplingbutton.getText().equalsIgnoreCase("Next"))
					{
						Thread.sleep(1000);
						driver.findElement(parser.getbjectLocator("samplingDetailsNextButton")).click();
						Thread.sleep(1000);
						System.out.println("----->"+driver.findElement(By.id("addSegment")).getText());
						System.out.println("----->size"+driver.findElements(By.id("next-gotoeqpSamplingCustom")).size());
						
						if(driver.findElement(By.id("addSegment")).getText().equalsIgnoreCase("Add Segment"))
						{
							Thread.sleep(1000);
							driver.findElement(By.id("addSegment")).click();
							Thread.sleep(500);
							driver.findElement(By.id("segmentName1")).sendKeys(EquipmentSegmentNameCREATE);
							Thread.sleep(500);
							driver.findElement(By.id("segSurfaceArea1")).sendKeys(EquipmentSegmentSFCREATE);
							Thread.sleep(500);
							//select segment locations
							driver.findElement(By.id("segSurfaceArea1")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
							WebElement Seglocation = driver.findElement(By.id("select1"));
							Select SelectLocation = new Select(Seglocation);
							SelectLocation.selectByIndex(0);
							Thread.sleep(1000);
							
							WebElement segSubmit = driver.findElement(By.id("next-gotoeqpSamplingCustom")); //submitEquipmentSamplingDetails
							System.out.println("================"+segSubmit.getText());
						//	System.out.println("================"+driver.findElement(By.cssSelector("#next-gotoeqpSamplingCustom")).getText());
							
							
							if(segSubmit.getText().equalsIgnoreCase("Submit"))
							{
								System.out.println("SegLoop: No Custom loop");
								segSubmit.click();
								Thread.sleep(2000);
							}
							else // custom loop after segment info
							{
								segSubmit.click();
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
										Thread.sleep(2000);
							}
							
						}
						else // custom loop after sampling info
						{
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
									Thread.sleep(2000);
						}
					}
					
					String createEquipment = driver.findElement(By.className("notify-msg")).getText();
					System.out.println("createEquipment "+createEquipment);
					//String createEquipment = driver.findElement(By.className("notify-msg")).getText();
					Assert.assertEquals(createEquipment,Message.equipmentCREATE);
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
					{
						//String createEquipment = driver.findElement(By.className("notify-msg")).getText();
						//System.out.println("createEquipment "+createEquipment);
						//String createEquipment = driver.findElement(By.className("notify-msg")).getText();
						//Assert.assertEquals(createEquipment,"Equipment saved successfully");
						driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
					}
					Thread.sleep(500);
					
				} // closing Create Equipment method
				
				
			
			
				@Test(priority=19)
				public void EditEquipmentwithRiskAssessment() throws Exception
				{
					//Thread.sleep(31000);
					Thread.sleep(4000);
					//driver.findElement(parser.getbjectLocator("EquipmentAction")).click(); // Click action icon
					driver.findElement(By.id("dLabel")).click();
					Thread.sleep(500);
					//driver.findElement(By.xpath(".//*[@id='datatable']/tbody/tr[1]/td[10]/div/ul/li[3]/a")).click();
					//driver.findElement(By.className("dropdown-item")).sendKeys(Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ENTER);
					driver.findElement(By.linkText("Edit")).click();
					Thread.sleep(500);
					WebElement locationdropdown = driver.findElement(parser.getbjectLocator("EquipmentLocation"));
					Select location = new Select(locationdropdown);
					location.selectByIndex(1);
					Thread.sleep(500);
					
					String getEqmodel = driver.findElement(parser.getbjectLocator("Model")).getAttribute("value"); //verify text presented in the edit
					Assert.assertEquals(getEqmodel,EquipmentModelCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("Model")).clear();
					driver.findElement(parser.getbjectLocator("Model")).sendKeys(EquipmentModelEDIT);
					Thread.sleep(500);
					
					String getSerialNo = driver.findElement(parser.getbjectLocator("AssetNo/SerialNo")).getAttribute("value"); //verify text presented in the edit
					//Assert.assertEquals(getSerialNo,EquipmentSerialNoCREATE);
					if(getSerialNo.equalsIgnoreCase(""))
					{
						new Exception();
					}
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("AssetNo/SerialNo")).clear();
					driver.findElement(parser.getbjectLocator("AssetNo/SerialNo")).sendKeys(EquipmentSerialNoEDIT);
					Thread.sleep(300);
					
					String getmanufacturing = driver.findElement(parser.getbjectLocator("Manufacturer")).getAttribute("value"); //verify text presented in the edit
					Assert.assertEquals(getmanufacturing,EquipmentManufacturerCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("Manufacturer")).clear();
					driver.findElement(parser.getbjectLocator("Manufacturer")).sendKeys(EquipmentManufacturerEDIT);
					Thread.sleep(500);
					
					String geteqSF = driver.findElement(By.id("surfaceArea")).getAttribute("value"); //verify text presented in the edit
					Assert.assertEquals(geteqSF,EquipmentSFCREATE);
					Thread.sleep(500);
					driver.findElement(By.id("surfaceArea")).clear();
					driver.findElement(By.id("surfaceArea")).sendKeys(EquipmentSFEDIT);
					Thread.sleep(500);
					
					String getDASF = driver.findElement(parser.getbjectLocator("SurfaceAreaDataSource")).getAttribute("value"); //verify text presented in the edit
					Assert.assertEquals(getDASF,EquipmentSFDataSourceCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("SurfaceAreaDataSource")).clear();
					driver.findElement(parser.getbjectLocator("SurfaceAreaDataSource")).sendKeys(EquipmentSFDataSourceEDIT);
					Thread.sleep(500);
					
					WebElement preferentialTransfer = driver.findElement(parser.getbjectLocator("CanPreferentialTransferofResidueOccurwiththisEquipment?"));
					Select preferentialTransferType = new Select(preferentialTransfer);
					WebElement option = preferentialTransferType.getFirstSelectedOption(); 
					String getpreTransfer = option.getText();
					Assert.assertEquals(getpreTransfer,EquipmentPreferentialTransferCREATE);
					//preferentialTransferType.selectByVisibleText(EquipmentPreferentialTransferEDIT);
					driver.findElement(parser.getbjectLocator("SurfaceAreaDataSource")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ARROW_DOWN,Keys.ENTER);
					Thread.sleep(500);
					
					String getMinBatch = driver.findElement(parser.getbjectLocator("MinimumBatchSize")).getAttribute("value"); //verify text presented in the edit
					Assert.assertEquals(getMinBatch,EquipmentMinBatchCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("MinimumBatchSize")).clear();
					driver.findElement(parser.getbjectLocator("MinimumBatchSize")).sendKeys(EquipmentMinBatchEDIT);
					Thread.sleep(500);
					
				//	String getQAID = driver.findElement(parser.getbjectLocator("QualificationDocumentId")).getAttribute("value"); //verify text presented in the edit
					//Assert.assertEquals(getQAID,EquipmentQuaDocIDCREATE);
				//	Thread.sleep(500);
				//	driver.findElement(parser.getbjectLocator("QualificationDocumentId")).clear();
				//	driver.findElement(parser.getbjectLocator("QualificationDocumentId")).sendKeys(EquipmentQuaDocIDCREATE);
					Thread.sleep(500);
					
					WebElement SOP = driver.findElement(parser.getbjectLocator("CleaningSOPNo"));
					Select CleaningSOP = new Select(SOP);
					//CleaningSOP.selectByIndex(1);
					WebElement getSOP = CleaningSOP.getFirstSelectedOption(); 
					String getSOPText = getSOP.getText();
					//Assert.assertEquals(getSOPText,EquipmentPreferentialTransferCREATE);
					System.out.println("getSOPText: "+getSOPText);
					if(getSOPText.equalsIgnoreCase(""))
					{
						throw new Exception();
					}
					Thread.sleep(500);
					WebElement processType = driver.findElement(parser.getbjectLocator("CleaningProcessType"));
					Select CleaningProcessType = new Select(processType);
					//CleaningProcessType.selectByValue(EquipmentCleaningProcessTypeEDT);
					WebElement getCleaningProcessType = CleaningProcessType.getFirstSelectedOption(); 
					String getCleaningProcessTypeText = getCleaningProcessType.getText();
					System.out.println("getCleaningProcessTypeText: "+getCleaningProcessTypeText);
					Thread.sleep(500);
					if(getCleaningProcessTypeText.equalsIgnoreCase(""))
					{
						throw new Exception();
					}
					
					String getothercleaningInfo = driver.findElement(parser.getbjectLocator("OtherCleaningRelevantInformation")).getAttribute("value"); //verify text presented in the edit
					Assert.assertEquals(getothercleaningInfo,EquipmentCleaningInfoCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("OtherCleaningRelevantInformation")).clear();
					driver.findElement(parser.getbjectLocator("OtherCleaningRelevantInformation")).sendKeys(EquipmentCleaningInfoEDIT);
					Thread.sleep(1000);
					
					//driver.findElement(parser.getbjectLocator("NextButton")).click();
					driver.findElement(By.id("saveEquipment")).click();
					Thread.sleep(1000);
					
					System.out.println("submitLocationAssessment: "+driver.findElements(parser.getbjectLocator("submitLocationAssessment")).size());
					if(driver.findElements(parser.getbjectLocator("submitLocationAssessment")).size()!=0)
					{
					//Location Risk Assessment
					Thread.sleep(1000);
					driver.findElement(parser.getbjectLocator("submitLocationAssessment")).click(); // submit location assessment
					//Location Risk Assessment -End
					}
					
					Thread.sleep(3000);
					//Do you want to upload images?
					WebElement uploadsamplingimage = driver.findElement(parser.getbjectLocator("Doyouwanttouploadimages?"));
					Select YesorNo = new Select(uploadsamplingimage);
				//	YesorNo.selectByIndex(2); // select Yes option
					Thread.sleep(1000);
					
					WebElement Sampling = driver.findElement(By.id("mocy1"));
					Select SelectSampling = new Select(Sampling);
					WebElement selected = SelectSampling.getFirstSelectedOption();
					String getSelected = selected.getText();
					System.out.println("getSelected--->"+getSelected);
					//Assert.assertEquals(getSelected,"Swab Surface Area");
					
					
					Thread.sleep(1000);
					System.out.println("locationName1:" +driver.findElement(By.id("locationName1")).getAttribute("type"));
					if(driver.findElement(By.id("locationName1")).getAttribute("type").equals("text")) 
					{
						Thread.sleep(500);
						driver.findElement(By.id("locationName1")).clear();
						driver.findElement(By.id("locationName1")).sendKeys("Chennai");
					}
					
					
					Thread.sleep(1000);
					WebElement samplingbutton = driver.findElement(parser.getbjectLocator("samplingDetailsNextButton")); //submitEquipmentSamplingDetails
					
					if(samplingbutton.getText().equals("Submit"))
					{
						System.out.println("No Custom loop");
						driver.findElement(parser.getbjectLocator("samplingDetailsNextButton")).click();
						Thread.sleep(2000);
						driver.findElement(By.id("comments")).sendKeys("Comments");
						Thread.sleep(500);
						driver.findElement(By.name("password")).clear();
						Thread.sleep(500);
						driver.findElement(By.id("ackSubmit")).click();
						Thread.sleep(500);
						driver.findElement(By.name("password")).sendKeys(password);
						Thread.sleep(500);
						driver.findElement(By.id("ackSubmit")).click();
						
					}
					//
					if(samplingbutton.getText().equalsIgnoreCase("Next"))
					{
						Thread.sleep(1000);
						driver.findElement(parser.getbjectLocator("samplingDetailsNextButton")).click();
						Thread.sleep(1000);
						System.out.println("----->"+driver.findElement(By.id("addSegment")).getText());
						System.out.println("----->size"+driver.findElements(By.id("next-gotoeqpSamplingCustom")).size());
						
						if(driver.findElement(By.id("addSegment")).getText().equalsIgnoreCase("Add Segment"))
						{
							Thread.sleep(1000);
							System.out.println(driver.findElements(By.id("segmentName1")).size());
							if(driver.findElements(By.id("segmentName1")).size()==0)
							{
								driver.findElement(By.id("addSegment")).click();	
								Thread.sleep(500);
								driver.findElement(By.id("segmentName1")).clear();
								driver.findElement(By.id("segmentName1")).sendKeys(EquipmentSegmentNameCREATE);
								Thread.sleep(500);
								driver.findElement(By.id("sugSurfaceArea1")).clear();
								driver.findElement(By.id("sugSurfaceArea1")).sendKeys(EquipmentSegmentSFCREATE);
								Thread.sleep(500);
								WebElement Seglocation = driver.findElement(By.id("select1"));
								Select SelectLocation = new Select(Seglocation);
								//SelectLocation.selectByIndex(0);
								WebElement getseglocation = SelectLocation.getFirstSelectedOption(); 
								String getseglocationText = getseglocation.getText();
								System.out.println("getseglocationText: "+getseglocationText);
								Thread.sleep(500);
								if(getseglocationText.equalsIgnoreCase(""))
								{
									driver.findElement(By.id("sugSurfaceArea1")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
								}
								Thread.sleep(1000);
							}
							String getsegmentName = driver.findElement(By.id("segmentName1")).getAttribute("value"); //verify text presented in the edit
							Assert.assertEquals(getsegmentName,EquipmentSegmentNameCREATE);
							Thread.sleep(500);
							driver.findElement(By.id("segmentName1")).clear();
							driver.findElement(By.id("segmentName1")).sendKeys(EquipmentSegmentNameEDIT);
							Thread.sleep(500);

							String getsegmentSF= driver.findElement(By.id("sugSurfaceArea1")).getAttribute("value"); //verify text presented in the edit
							Assert.assertEquals(getsegmentSF,EquipmentSegmentSFCREATE);
							Thread.sleep(500);
							driver.findElement(By.id("sugSurfaceArea1")).clear();
							driver.findElement(By.id("sugSurfaceArea1")).sendKeys(EquipmentSegmentSFEDIT);
							Thread.sleep(500);
							
							WebElement segSubmit = driver.findElement(By.id("next-gotoeqpSamplingCustom")); //submitEquipmentSamplingDetails
							System.out.println("================"+segSubmit.getText());
						//	System.out.println("================"+driver.findElement(By.cssSelector("#next-gotoeqpSamplingCustom")).getText());
							
							
							if(segSubmit.getText().equalsIgnoreCase("Submit"))
							{
								System.out.println("SegLoop: No Custom loop");
								segSubmit.click();
								Thread.sleep(1000);
								//driver.findElement(By.id("comments")).click();
								//Thread.sleep(500);
								driver.findElement(By.id("comments")).sendKeys("comments");
								Thread.sleep(500);
								driver.findElement(By.name("password")).clear();
								Thread.sleep(500);
								driver.findElement(By.id("ackSubmit")).click();
								Thread.sleep(500);
								driver.findElement(By.name("password")).sendKeys(password);
								Thread.sleep(500);
								driver.findElement(By.id("ackSubmit")).click();
								Thread.sleep(2000);
							}
							else // custom loop after segment info
							{
								segSubmit.click();
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
										//driver.findElement(By.id("comments")).click();
										//Thread.sleep(500);
										driver.findElement(By.id("comments")).sendKeys("Comments");
										Thread.sleep(500);
										driver.findElement(By.name("password")).clear();
										Thread.sleep(500);
										driver.findElement(By.id("ackSubmit")).click();
										Thread.sleep(500);
										driver.findElement(By.name("password")).sendKeys(password);
										Thread.sleep(500);
										driver.findElement(By.id("ackSubmit")).click();
										Thread.sleep(2000);
							}
							
						}
						else // custom loop after sampling info
						{
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
									Thread.sleep(500);
									driver.findElement(By.id("comments")).sendKeys("Comments");
									Thread.sleep(500);
									driver.findElement(By.name("password")).clear();
									Thread.sleep(500);
									driver.findElement(By.id("ackSubmit")).click();
									Thread.sleep(500);
									driver.findElement(By.name("password")).sendKeys(password);
									Thread.sleep(500);
									driver.findElement(By.id("ackSubmit")).click();
									Thread.sleep(2000);
						}
					}
					//
					Thread.sleep(2000);
					String EditEquipment = driver.findElement(By.className("notify-msg")).getText(); 
					Assert.assertEquals(EditEquipment,Message.equipmentEDIT);
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
					{
						driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
					}
					Thread.sleep(500);
				} // closing Edit Equipment method

				
						
				@Test(priority=20)
				public void DeleteEquipmentRiskAssessment() throws InterruptedException, SQLException, ClassNotFoundException
				{
					Thread.sleep(1000);
					driver.get(Constant.URL+"/equipment");
					//driver.findElement(parser.getbjectLocator("EquipmentAction")).click(); // Click action icon
					driver.findElement(By.id("dLabel")).click();
					Thread.sleep(500);
					//driver.findElement(By.xpath(".//*[@id='datatable']/tbody/tr[1]/td[10]/div/ul/li[4]/a")).click(); // click delete button
					//driver.findElement(By.className("dropdown-item")).sendKeys(Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ENTER);
					driver.findElement(By.linkText("Delete")).click();
					Thread.sleep(500);
					//driver.findElement(By.xpath(".//*[@id='openAckModal']")).click();//click Yes in popup
					//Thread.sleep(500);
					driver.findElement(By.name("ackChangeControlNo")).sendKeys("111");
					Thread.sleep(500);
					//driver.findElement(By.name("ackChangeControlNo")).sendKeys(Keys.TAB +password);
					driver.findElement(By.name("password")).sendKeys(password);
					Thread.sleep(500);
					driver.findElement(By.id("comments")).sendKeys("Delete single equipment");
					Thread.sleep(500);
					//driver.findElement(By.id("ackSubmit")).click();
					//driver.findElement(By.xpath(".//*[@id='dynamicModal']/div[3]/div/button[2]")).click();
					driver.findElement(By.id("comments")).sendKeys(Keys.TAB,Keys.TAB,Keys.ENTER);
					Thread.sleep(1500);
					String deletemsg = driver.findElement(By.className("notify-msg")).getText(); // get deleted esuccess message
					Assert.assertEquals(deletemsg,Message.equipmentDELETE);
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
					{
						driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
					}
					Thread.sleep(500);
				}
				
				
				//@Test(priority=12,invocationCount=2)
				@Test(priority=21)
				public void CreateEquipmentwithSamplingSiteType() throws InterruptedException, SQLException, ClassNotFoundException, IOException
				{
					parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\Equipment.properties");
					//Thread.sleep(5000);
					Thread.sleep(2000);
					SampleLocationSamplingSiteType();
					
					driver.get(Constant.URL+"/equipment");
					//driver.navigate().refresh();
					Thread.sleep(2000);
					driver.findElement(parser.getbjectLocator("CreateEquipment")).click(); // Click create equipment button
					Thread.sleep(1000);
					String equipmentName = EquipmentName;
					WebElement eqName = driver.findElement(parser.getbjectLocator("EquipmentName")); //Equipment Name field
					eqName.sendKeys(equipmentName);
					Thread.sleep(500);
					//WebElement locationdropdown = driver.findElement(By.name("location"));
					//Select location = new Select(locationdropdown);
					//location.selectByIndex(1);
					driver.findElement(By.name("name")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("Model")).sendKeys(EquipmentModelCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("AssetNo/SerialNo")).sendKeys(EquipmentSerialNoCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("Manufacturer")).sendKeys(EquipmentManufacturerCREATE);
					Thread.sleep(500);
					//Scroll down the page
					driver.findElement(parser.getbjectLocator("Manufacturer")).sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB);
					Thread.sleep(500);
					driver.findElement(By.id("surfaceArea")).sendKeys(EquipmentSFCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("SurfaceAreaDataSource")).sendKeys(EquipmentSFDataSourceCREATE);
					Thread.sleep(500);
					
					driver.findElement(parser.getbjectLocator("SurfaceAreaDataSource")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
					//WebElement preferentialTransfer = driver.findElement(parser.getbjectLocator("CanPreferentialTransferofResidueOccurwiththisEquipment?"));
					//Select preferentialTransferType = new Select(preferentialTransfer);
					//preferentialTransferType.selectByVisibleText(EquipmentPreferentialTransferCREATE);
					Thread.sleep(500);
					
					driver.findElement(By.id("preferentialTransferSurfaceArea")).sendKeys(EquipmentPreferentialTransferSFCREATE);
					Thread.sleep(500);

					driver.findElement(By.id("preferentialTransferSurfaceArea")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
					//WebElement PrimaryPackaging = driver.findElement(By.id("primaryPackaging"));
					//Select SelectPrimaryPackaging = new Select(PrimaryPackaging);
					//SelectPrimaryPackaging.selectByVisibleText(EquipmentPrimaryPackagingeOptionCREATE);
					Thread.sleep(500);
					//scroll down
					driver.findElement(By.id("preferentialTransferSurfaceArea")).sendKeys(Keys.TAB,Keys.TAB);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("MinimumBatchSize")).sendKeys(EquipmentMinBatchCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("MinimumBatchSize")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
					Thread.sleep(500);
					//Cleaning SOP
					driver.findElement(parser.getbjectLocator("MinimumBatchSize")).sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.ENTER,Keys.ENTER);
					//WebElement SOP = driver.findElement(parser.getbjectLocator("CleaningSOPNo"));
					//Select CleaningSOP = new Select(SOP);
					//CleaningSOP.selectByIndex(1);
					Thread.sleep(500);
					//scroll down the page
					driver.findElement(parser.getbjectLocator("MinimumBatchSize")).sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB);
					Thread.sleep(500);
					//cleaing proceess type
					driver.findElement(parser.getbjectLocator("OtherCleaningRelevantInformation")).sendKeys(Keys.SHIFT,Keys.TAB,Keys.ENTER,Keys.ARROW_DOWN,Keys.ENTER);
					//WebElement processType = driver.findElement(parser.getbjectLocator("CleaningProcessType"));
					//Select CleaningProcessType = new Select(processType);
					//CleaningProcessType.selectByValue(EquipmentCleaningProcessTypeCREATE);
					Thread.sleep(500);
					
					driver.findElement(parser.getbjectLocator("OtherCleaningRelevantInformation")).sendKeys(EquipmentCleaningInfoCREATE);
					Thread.sleep(1000);
					//driver.findElement(parser.getbjectLocator("NextButton")).click();
					WebElement submit = driver.findElement(By.id("saveEquipment"));
					submit.click();
					Thread.sleep(500);
					
					Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
					for(int k=5;k<1000;k++)
					{
						j.add(k);
					}
					
					if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Equipment '"+equipmentName+"' already exists!"))
					{
						String getduplicatename = driver.findElement(By.className("notify-msg")).getText();
						driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
					
					Thread.sleep(500);
					if(getduplicatename.equalsIgnoreCase("Equipment '"+equipmentName+"' already exists!"))
					{
						for(Integer i:j)
						{
							//driver.findElement(parser.getbjectLocator("ActiveIngredientName")).clear();
							eqName.clear();
							Thread.sleep(200);
							eqName.sendKeys(equipmentName+i);
							//driver.findElement(parser.getbjectLocator("ActiveIngredientName")).sendKeys(Name+i);
							Thread.sleep(500);
							submit.click();
							//driver.findElement(parser.getbjectLocator("APIsubmit")).click();
							Thread.sleep(500);
							if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Equipment '"+equipmentName+i+"' already exists!"))
							{
								String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
								System.out.println("Name duplicate: "+nameduplicate);
								driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
								if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Equipment '"+equipmentName+i+"' already exists!"))
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
					if( driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Asset Number / Serial Number '"+EquipmentSerialNoCREATE+"' already exists!"))
					{
						String getduplicateID = driver.findElement(By.className("notify-msg")).getText();
						driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
					System.out.println("getduplicatename: "+getduplicateID);
					if(getduplicateID.equalsIgnoreCase("Asset Number / Serial Number '"+EquipmentSerialNoCREATE+"' already exists!"))
					{
						for(Integer i:j)
						{
							driver.findElement(parser.getbjectLocator("AssetNo/SerialNo")).clear();
							Thread.sleep(200);
							driver.findElement(parser.getbjectLocator("AssetNo/SerialNo")).sendKeys(EquipmentSerialNoCREATE+i);
							Thread.sleep(500);
							//driver.findElement(parser.getbjectLocator("APIsubmit")).click();
							submit.click();
							Thread.sleep(500);
							if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Asset Number / Serial Number '"+EquipmentSerialNoCREATE+i+"' already exists!"))
							{
								String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
								System.out.println("Name duplicate: "+nameduplicate);
								driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
								if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Asset Number / Serial Number '"+EquipmentSerialNoCREATE+i+"' already exists!"))
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
					System.out.println("submitLocationAssessment: "+driver.findElements(parser.getbjectLocator("submitLocationAssessment")).size());
					if(driver.findElements(parser.getbjectLocator("submitLocationAssessment")).size()!=0)
					{
					//Location Risk Assessment
					Thread.sleep(1000);
					driver.findElement(parser.getbjectLocator("AddLocation")).click();
					driver.findElement(parser.getbjectLocator("AddLocation")).click();
					Thread.sleep(500);
					driver.findElement(By.id("checkbox1")).click();
					Thread.sleep(500);
					driver.findElement(By.id("checkbox2")).click();
					Thread.sleep(500);
					driver.findElement(By.id("location1")).sendKeys(EquipmentSamplingLocationCREATE);
					Thread.sleep(500);
					driver.findElement(By.id("location2")).sendKeys(EquipmentSamplingLocationCREATE);
					Thread.sleep(500);
					//Enter Locations
			        Thread.sleep(500);
			        driver.findElement(By.id("location1")).sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB);
			        //Delete row
			        Thread.sleep(1000);
			        driver.findElement(By.cssSelector(".remove-row-icon")).click();
			        Thread.sleep(1000);
			        driver.findElement(By.id("location1")).sendKeys(EquipmentSamplingLocationCREATE);
			        Thread.sleep(500);
			       driver.findElement(By.cssSelector(".remove-row-icon")).sendKeys(Keys.SHIFT,Keys.TAB,Keys.SHIFT,Keys.TAB,Keys.SHIFT,Keys.TAB,Keys.SHIFT,Keys.TAB,Keys.SHIFT,Keys.TAB,Keys.SHIFT,Keys.TAB,Keys.SHIFT,Keys.TAB,Keys.SHIFT,Keys.TAB,Keys.SHIFT,Keys.TAB);
			       Thread.sleep(1000);
					
					driver.findElement(parser.getbjectLocator("submitLocationAssessment")).click(); // submit location assessment
					//Location Risk Assessment -End
					}
					
					
					Thread.sleep(1000);
					//Do you want to upload images?
					driver.findElement(By.xpath(".//*[@id='needUloadImages']/div/div/span/span[1]/span/span[2]")).click();
					driver.findElement(By.xpath(".//*[@id='needUloadImages']/div/div/span/span[1]/span/span[2]")).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
					//WebElement uploadsamplingimage = driver.findElement(parser.getbjectLocator("Doyouwanttouploadimages?"));
					//Select YesorNo = new Select(uploadsamplingimage);
					//YesorNo.selectByIndex(2); // select No option
					Thread.sleep(1000);
					//upload image
					//driver.findElement(By.xpath(".//*[@id='upload-images']/div/div/input")).sendKeys("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\Test Data\\equipTrain.jpg");
					//Thread.sleep(1000);
					//add location
					//driver.findElement(By.xpath(".//*[@id='addPinName']")).click();
					//click add location pin
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("AddLocationButton")).click(); 
					//LocationName
					Thread.sleep(500);
					System.out.println("locationName1:" +driver.findElement(By.id("locationName1")).getAttribute("type"));
					if(driver.findElement(By.id("locationName1")).getAttribute("type").contains("text")) 
					{
						Thread.sleep(500);
						driver.findElement(By.id("locationName1")).clear();
						driver.findElement(By.id("locationName1")).sendKeys("Chennai");
						Thread.sleep(500);
						//select MOC
						driver.findElement(By.id("locationName1")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
						Thread.sleep(500);
						//Select Sampling Type
						driver.findElement(By.id("locationName1")).sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.ENTER,Keys.ENTER);
						
					}
					Thread.sleep(1000);
					//MOC Selection
					System.out.println("-->"+driver.findElement(By.id("locationName1")).getAttribute("type"));
					if(driver.findElement(By.id("locationName1")).getAttribute("type").contains("select"))
					{
						driver.findElement(By.xpath(".//*[@id='marker-num1']/div[2]/span/span[1]/span")).click();
						driver.findElement(By.xpath(".//*[@id='marker-num1']/div[2]/span/span[1]/span")).sendKeys(Keys.ENTER);
						Thread.sleep(1000);
						driver.findElement(By.xpath(".//*[@id='marker-num1']/div[3]/span/span[1]/span")).click();
						driver.findElement(By.xpath(".//*[@id='marker-num1']/div[3]/span/span[1]/span")).sendKeys(Keys.ENTER);
					}
					
					//select sampling method and test
					driver.findElement(By.xpath(".//*[@id='marker-num1']/div[4]/span/span[1]/span")).click();
					driver.findElement(By.xpath(".//*[@id='marker-num1']/div[4]/span/span[1]/span")).sendKeys(Keys.ENTER);
					
				//	WebElement Sampling = driver.findElement(By.id("mocy1"));
					//Select SelectSampling = new Select(Sampling);
					//SelectSampling.selectByValue("1");
					
					
					
					
					//save image
					//driver.findElement(By.xpath(".//*[@id='preview-image']/div/div[2]/img")).click(); //click to save uploaded image
					//Thread.sleep(1000);
					Thread.sleep(1000);
					WebElement samplingbutton = driver.findElement(By.id("submitEquipmentSamplingDetails")); //submitEquipmentSamplingDetails
					System.out.println("samplingbutton: "+samplingbutton);
					if(samplingbutton.getText().equalsIgnoreCase("Submit"))
					{
						System.out.println("No Custom loop");
						driver.findElement(By.id("submitEquipmentSamplingDetails")).click();
						Thread.sleep(2000);
					}
					
					if(samplingbutton.getText().equalsIgnoreCase("Next"))
					{
						Thread.sleep(1000);
						driver.findElement(By.id("submitEquipmentSamplingDetails")).click();
						Thread.sleep(1000);
						System.out.println("----->"+driver.findElement(By.id("addSegment")).getText());
						System.out.println("----->size"+driver.findElements(By.id("next-gotoeqpSamplingCustom")).size());
						
						if(driver.findElement(By.id("addSegment")).getText().equalsIgnoreCase("Add Segment"))
						{
							Thread.sleep(1000);
							driver.findElement(By.id("addSegment")).click();
							Thread.sleep(500);
							driver.findElement(By.id("segmentName1")).sendKeys(EquipmentSegmentNameCREATE);
							Thread.sleep(500);
							driver.findElement(By.id("segSurfaceArea1")).sendKeys(EquipmentSegmentSFCREATE);
							Thread.sleep(500);
							//select segment locations
							driver.findElement(By.id("segSurfaceArea1")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
							WebElement Seglocation = driver.findElement(By.id("select1"));
							Select SelectLocation = new Select(Seglocation);
							SelectLocation.selectByIndex(0);
							Thread.sleep(1000);
							
							WebElement segSubmit = driver.findElement(By.id("next-gotoeqpSamplingCustom")); //submitEquipmentSamplingDetails
							System.out.println("================"+segSubmit.getText());
						//	System.out.println("================"+driver.findElement(By.cssSelector("#next-gotoeqpSamplingCustom")).getText());
							
							
							if(segSubmit.getText().equalsIgnoreCase("Submit"))
							{
								System.out.println("SegLoop: No Custom loop");
								segSubmit.click();
								Thread.sleep(2000);
							}
							else // custom loop after segment info
							{
								segSubmit.click();
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
										Thread.sleep(2000);
							}
							
						}
						else // custom loop after sampling info
						{
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
									Thread.sleep(2000);
						}
					}
					String createEquipment = driver.findElement(By.className("notify-msg")).getText();
					System.out.println("createEquipment "+createEquipment);
					//String createEquipment = driver.findElement(By.className("notify-msg")).getText();
					Assert.assertEquals(createEquipment,Message.equipmentCREATE);
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
					{
						//String createEquipment = driver.findElement(By.className("notify-msg")).getText();
						//System.out.println("createEquipment "+createEquipment);
						//String createEquipment = driver.findElement(By.className("notify-msg")).getText();
						//Assert.assertEquals(createEquipment,"Equipment saved successfully");
						driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
					}
					Thread.sleep(500);
					
				} // closing Create Equipment method
			
				
				@Test(priority=22)
				public void EditEquipmentwithSamplingSiteType() throws Exception
				{
					//Thread.sleep(31000);
					Thread.sleep(6000);
					//driver.findElement(parser.getbjectLocator("EquipmentAction")).click(); // Click action icon
					driver.findElement(By.id("dLabel")).click();
					Thread.sleep(500);
					//driver.findElement(By.xpath(".//*[@id='datatable']/tbody/tr[1]/td[10]/div/ul/li[3]/a")).click();
					//driver.findElement(By.className("dropdown-item")).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
					driver.findElement(By.linkText("Edit")).click();
					Thread.sleep(500);
					WebElement locationdropdown = driver.findElement(parser.getbjectLocator("EquipmentLocation"));
					Select location = new Select(locationdropdown);
					location.selectByIndex(1);
					Thread.sleep(500);
					
					String getEqmodel = driver.findElement(parser.getbjectLocator("Model")).getAttribute("value"); //verify text presented in the edit
					Assert.assertEquals(getEqmodel,EquipmentModelCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("Model")).clear();
					driver.findElement(parser.getbjectLocator("Model")).sendKeys(EquipmentModelEDIT);
					Thread.sleep(500);
					
					String getSerialNo = driver.findElement(parser.getbjectLocator("AssetNo/SerialNo")).getAttribute("value"); //verify text presented in the edit
					if(getSerialNo.equalsIgnoreCase(""))
					{
						new Exception();
					}
					//Assert.assertEquals(getSerialNo,EquipmentSerialNoCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("AssetNo/SerialNo")).clear();
					driver.findElement(parser.getbjectLocator("AssetNo/SerialNo")).sendKeys(EquipmentSerialNoEDIT);
					Thread.sleep(300);
					
					String getmanufacturing = driver.findElement(parser.getbjectLocator("Manufacturer")).getAttribute("value"); //verify text presented in the edit
					Assert.assertEquals(getmanufacturing,EquipmentManufacturerCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("Manufacturer")).clear();
					driver.findElement(parser.getbjectLocator("Manufacturer")).sendKeys(EquipmentManufacturerEDIT);
					Thread.sleep(500);
					
					String geteqSF = driver.findElement(By.id("surfaceArea")).getAttribute("value"); //verify text presented in the edit
					Assert.assertEquals(geteqSF,EquipmentSFCREATE);
					Thread.sleep(500);
					driver.findElement(By.id("surfaceArea")).clear();
					driver.findElement(By.id("surfaceArea")).sendKeys(EquipmentSFEDIT);
					Thread.sleep(500);
					
					String getDASF = driver.findElement(parser.getbjectLocator("SurfaceAreaDataSource")).getAttribute("value"); //verify text presented in the edit
					Assert.assertEquals(getDASF,EquipmentSFDataSourceCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("SurfaceAreaDataSource")).clear();
					driver.findElement(parser.getbjectLocator("SurfaceAreaDataSource")).sendKeys(EquipmentSFDataSourceEDIT);
					Thread.sleep(500);
					
					WebElement preferentialTransfer = driver.findElement(parser.getbjectLocator("CanPreferentialTransferofResidueOccurwiththisEquipment?"));
					Select preferentialTransferType = new Select(preferentialTransfer);
					WebElement option = preferentialTransferType.getFirstSelectedOption(); 
					String getpreTransfer = option.getText();
					Assert.assertEquals(getpreTransfer,EquipmentPreferentialTransferCREATE);
					driver.findElement(parser.getbjectLocator("SurfaceAreaDataSource")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ARROW_DOWN,Keys.ENTER);
					//preferentialTransferType.selectByVisibleText(EquipmentPreferentialTransferEDIT);
					Thread.sleep(500);
					
					String getMinBatch = driver.findElement(parser.getbjectLocator("MinimumBatchSize")).getAttribute("value"); //verify text presented in the edit
					Assert.assertEquals(getMinBatch,EquipmentMinBatchCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("MinimumBatchSize")).clear();
					driver.findElement(parser.getbjectLocator("MinimumBatchSize")).sendKeys(EquipmentMinBatchEDIT);
					Thread.sleep(500);
					
					//String getQAID = driver.findElement(parser.getbjectLocator("QualificationDocumentId")).getAttribute("value"); //verify text presented in the edit
					//Assert.assertEquals(getQAID,EquipmentQuaDocIDCREATE);
					//Thread.sleep(500);
					//driver.findElement(parser.getbjectLocator("QualificationDocumentId")).clear();
					//driver.findElement(parser.getbjectLocator("QualificationDocumentId")).sendKeys(EquipmentQuaDocIDCREATE);
					//Thread.sleep(500);
					
					WebElement SOP = driver.findElement(parser.getbjectLocator("CleaningSOPNo"));
					Select CleaningSOP = new Select(SOP);
					//CleaningSOP.selectByIndex(1);
					WebElement getSOP = CleaningSOP.getFirstSelectedOption(); 
					String getSOPText = getSOP.getText();
					//Assert.assertEquals(getSOPText,EquipmentPreferentialTransferCREATE);
					System.out.println("getSOPText: "+getSOPText);
					if(getSOPText.equalsIgnoreCase(""))
					{
						throw new Exception();
					}
					Thread.sleep(500);
					WebElement processType = driver.findElement(parser.getbjectLocator("CleaningProcessType"));
					Select CleaningProcessType = new Select(processType);
					//CleaningProcessType.selectByValue(EquipmentCleaningProcessTypeEDT);
					WebElement getCleaningProcessType = CleaningProcessType.getFirstSelectedOption(); 
					String getCleaningProcessTypeText = getCleaningProcessType.getText();
					System.out.println("getCleaningProcessTypeText: "+getCleaningProcessTypeText);
					Thread.sleep(500);
					if(getCleaningProcessTypeText.equalsIgnoreCase(""))
					{
						throw new Exception();
					}
					
					//CleaningSOP.selectByIndex(1);
					Thread.sleep(500);
					//CleaningProcessType.selectByValue(EquipmentCleaningProcessTypeEDT);
					driver.findElement(parser.getbjectLocator("MinimumBatchSize")).sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.ENTER,Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ENTER);
					Thread.sleep(500);
					
					String getothercleaningInfo = driver.findElement(parser.getbjectLocator("OtherCleaningRelevantInformation")).getAttribute("value"); //verify text presented in the edit
					Assert.assertEquals(getothercleaningInfo,EquipmentCleaningInfoCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("OtherCleaningRelevantInformation")).clear();
					driver.findElement(parser.getbjectLocator("OtherCleaningRelevantInformation")).sendKeys(EquipmentCleaningInfoEDIT);
					Thread.sleep(1000);
					
					//driver.findElement(parser.getbjectLocator("NextButton")).click();
					driver.findElement(By.id("saveEquipment")).click();
					Thread.sleep(1000);
					
					System.out.println("submitLocationAssessment: "+driver.findElements(parser.getbjectLocator("submitLocationAssessment")).size());
					if(driver.findElements(parser.getbjectLocator("submitLocationAssessment")).size()!=0)
					{
						//Location Risk Assessment
						Thread.sleep(1000);
						driver.findElement(parser.getbjectLocator("submitLocationAssessment")).click(); // submit location assessment
						//Location Risk Assessment -End
					}
					
					Thread.sleep(3000);
					//Do you want to upload images?
					//WebElement uploadsamplingimage = driver.findElement(parser.getbjectLocator("Doyouwanttouploadimages?"));
					//Select YesorNo = new Select(uploadsamplingimage);
					//YesorNo.selectByValue("2"); // select No option
					//Thread.sleep(1000);
					
					//addPinName
				//	driver.findElement(By.id("addPinName")).click();
					WebElement Sampling = driver.findElement(By.id("mocy1"));
					Select SelectSampling = new Select(Sampling);
					WebElement selected = SelectSampling.getFirstSelectedOption();
					String getSelected = selected.getText();
					System.out.println("getSelected--->"+getSelected);
					if(getSelected.equalsIgnoreCase("")) {
						new Exception();
					}
					
					Thread.sleep(1000);
					System.out.println("locationName1:" +driver.findElement(By.id("locationName1")).getAttribute("type"));
					if(driver.findElement(By.id("locationName1")).getAttribute("type").equals("text")) 
					{
						Thread.sleep(500);
						driver.findElement(By.id("locationName1")).clear();
						driver.findElement(By.id("locationName1")).sendKeys("Chennai");
						Thread.sleep(500);
						WebElement moc = driver.findElement(By.id("moc1"));
						Select selectMOC = new Select(moc);
						//CleaningProcessType.selectByValue(EquipmentCleaningProcessTypeEDT);
						WebElement getMOC = selectMOC.getFirstSelectedOption(); 
						String getMOCText = getMOC.getText();
						System.out.println("getMOCText: "+getMOCText);
						Thread.sleep(500);
						if(getMOCText.equalsIgnoreCase(""))
						{
							throw new Exception();
						}
						Thread.sleep(500);
						//Sampling Type check whther selected or not
						WebElement sampleType = driver.findElement(By.id("mocz1"));
						Select selectsampleType = new Select(sampleType);
						//CleaningProcessType.selectByValue(EquipmentCleaningProcessTypeEDT);
						WebElement getsampleType = selectsampleType.getFirstSelectedOption(); 
						String getsampleTypeText = getsampleType.getText();
						System.out.println("getsampleTypeText: "+getsampleTypeText);
						Thread.sleep(500);
						if(getsampleTypeText.equalsIgnoreCase(""))
						{
							throw new Exception();
						}
						//Sampling and test method - check wehter selected or not
						WebElement sampleTest = driver.findElement(By.id("mocy1"));
						Select selectsampleTest = new Select(sampleTest);
						WebElement getsampleTest = selectsampleTest.getFirstSelectedOption(); 
						String getsampleTestText = getsampleTest.getText();
						System.out.println("getsampleTestText: "+getsampleTestText);
						Thread.sleep(500);
						if(getsampleTestText.equalsIgnoreCase(""))
						{
							throw new Exception();
						}
					}
					
					
					Thread.sleep(1000);
					WebElement samplingbutton = driver.findElement(By.id("submitEquipmentSamplingDetails")); //submitEquipmentSamplingDetails
					
					if(samplingbutton.getText().equals("Submit"))
					{
						System.out.println("No Custom loop");
						driver.findElement(By.id("submitEquipmentSamplingDetails")).click();
						Thread.sleep(2000);
						Thread.sleep(500);
						driver.findElement(By.id("comments")).sendKeys("Comments");
						Thread.sleep(500);
						driver.findElement(By.name("password")).clear();
						Thread.sleep(500);
						driver.findElement(By.id("ackSubmit")).click();
						Thread.sleep(500);
						driver.findElement(By.name("password")).sendKeys(password);
						Thread.sleep(500);
						driver.findElement(By.id("ackSubmit")).click();
						Thread.sleep(2000);
						
					}
					//
					if(samplingbutton.getText().equalsIgnoreCase("Next"))
					{
						Thread.sleep(1000);
						driver.findElement(By.id("submitEquipmentSamplingDetails")).click();
						Thread.sleep(1000);
						System.out.println("----->"+driver.findElement(By.id("addSegment")).getText());
						System.out.println("----->size"+driver.findElements(By.id("next-gotoeqpSamplingCustom")).size());
						
						if(driver.findElement(By.id("addSegment")).getText().equalsIgnoreCase("Add Segment"))
						{
							Thread.sleep(1000);
							System.out.println(driver.findElements(By.id("segmentName1")).size());
							if(driver.findElements(By.id("segmentName1")).size()==0)
							{
								driver.findElement(By.id("addSegment")).click();	
								Thread.sleep(500);
								driver.findElement(By.id("segmentName1")).clear();
								driver.findElement(By.id("segmentName1")).sendKeys(EquipmentSegmentNameCREATE);
								Thread.sleep(500);
								driver.findElement(By.id("sugSurfaceArea1")).clear();
								driver.findElement(By.id("sugSurfaceArea1")).sendKeys(EquipmentSegmentSFCREATE);
								Thread.sleep(500);
								WebElement Seglocation = driver.findElement(By.id("select1"));
								Select SelectLocation = new Select(Seglocation);
								SelectLocation.selectByIndex(0);
								Thread.sleep(1000);
							}
							String getsegmentName = driver.findElement(By.id("segmentName1")).getAttribute("value"); //verify text presented in the edit
							Assert.assertEquals(getsegmentName,EquipmentSegmentNameCREATE);
							Thread.sleep(500);
							driver.findElement(By.id("segmentName1")).clear();
							driver.findElement(By.id("segmentName1")).sendKeys(EquipmentSegmentNameEDIT);
							Thread.sleep(500);

							String getsegmentSF= driver.findElement(By.id("sugSurfaceArea1")).getAttribute("value"); //verify text presented in the edit
							Assert.assertEquals(getsegmentSF,EquipmentSegmentSFCREATE);
							Thread.sleep(500);
							driver.findElement(By.id("sugSurfaceArea1")).clear();
							driver.findElement(By.id("sugSurfaceArea1")).sendKeys(EquipmentSegmentSFEDIT);
							Thread.sleep(500);
							
							WebElement segSubmit = driver.findElement(By.id("next-gotoeqpSamplingCustom")); //submitEquipmentSamplingDetails
							System.out.println("================"+segSubmit.getText());
						//	System.out.println("================"+driver.findElement(By.cssSelector("#next-gotoeqpSamplingCustom")).getText());
							
							
							if(segSubmit.getText().equalsIgnoreCase("Submit"))
							{
								System.out.println("SegLoop: No Custom loop");
								segSubmit.click();
								Thread.sleep(1000);
								driver.findElement(By.id("comments")).click();
								Thread.sleep(500);
								driver.findElement(By.id("comments")).sendKeys("Comments");
								Thread.sleep(500);
								driver.findElement(By.name("password")).clear();
								Thread.sleep(500);
								driver.findElement(By.id("ackSubmit")).click();
								Thread.sleep(500);
								driver.findElement(By.name("password")).sendKeys(password);
								Thread.sleep(500);
								driver.findElement(By.id("ackSubmit")).click();
								Thread.sleep(2000);
							}
							else // custom loop after segment info
							{
								segSubmit.click();
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
										Thread.sleep(500);
										driver.findElement(By.id("comments")).sendKeys("Comments");
										Thread.sleep(500);
										driver.findElement(By.name("password")).clear();
										Thread.sleep(500);
										driver.findElement(By.id("ackSubmit")).click();
										Thread.sleep(500);
										driver.findElement(By.name("password")).sendKeys(password);
										Thread.sleep(500);
										driver.findElement(By.id("ackSubmit")).click();
										Thread.sleep(2000);
							}
							
						}
						else // custom loop after sampling info
						{
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
									driver.findElement(By.id("comments")).sendKeys("Comments");
									Thread.sleep(500);
									driver.findElement(By.name("password")).clear();
									Thread.sleep(500);
									driver.findElement(By.id("ackSubmit")).click();
									Thread.sleep(500);
									driver.findElement(By.name("password")).sendKeys(password);
									Thread.sleep(500);
									driver.findElement(By.id("ackSubmit")).click();
									Thread.sleep(2000);
						}
					}
					//
					Thread.sleep(2000);
					String EditEquipment = driver.findElement(By.className("notify-msg")).getText(); 
					Assert.assertEquals(EditEquipment,Message.equipmentEDIT);
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
					{
						driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
					}
					Thread.sleep(500);
				} // closing Edit Equipment method
				
				
				
				
				@Test(priority=23)
				public void SingleDeleteEquipmentSamplingSiteType() throws InterruptedException, SQLException, ClassNotFoundException
				{
					Thread.sleep(1000);
					driver.get(Constant.URL+"/equipment");
					//driver.findElement(parser.getbjectLocator("EquipmentAction")).click(); // Click action icon
					driver.findElement(By.id("dLabel")).click();
					Thread.sleep(500);
					//driver.findElement(By.xpath(".//*[@id='datatable']/tbody/tr[1]/td[10]/div/ul/li[4]/a")).click(); // click delete button
					//driver.findElement(By.className("dropdown-item")).sendKeys(Keys.ARROW_DOWN,Keys.ARROW_DOWN,Keys.ENTER);
					driver.findElement(By.linkText("Delete")).click();
					Thread.sleep(500);
					//driver.findElement(By.xpath(".//*[@id='openAckModal']")).click();//click Yes in popup
					//Thread.sleep(500);
					driver.findElement(By.name("ackChangeControlNo")).sendKeys("111");
					Thread.sleep(500);
					//driver.findElement(By.name("ackChangeControlNo")).sendKeys(Keys.TAB +password);
					driver.findElement(By.name("password")).sendKeys(password);
					Thread.sleep(500);
					driver.findElement(By.id("comments")).sendKeys("Delete single equipment");
					Thread.sleep(500);
					//driver.findElement(By.id("ackSubmit")).click();
					//driver.findElement(By.xpath(".//*[@id='dynamicModal']/div[3]/div/button[2]")).click();
					driver.findElement(By.id("comments")).sendKeys(Keys.TAB,Keys.TAB,Keys.ENTER);
					Thread.sleep(1500);
					String deletemsg = driver.findElement(By.className("notify-msg")).getText(); // get deleted esuccess message
					Assert.assertEquals(deletemsg,Message.equipmentDELETE);
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
					{
						driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
					}
					Thread.sleep(500);
				}
				
				
				@Test(priority=24)
				public void MultiDeleteEquipment() throws Exception
				{
					Thread.sleep(500);
					driver.findElement(By.id("searchDataTable")).sendKeys("test Equipment");
					Thread.sleep(500);
					driver.findElement(By.id("example-select-all")).click();
					Thread.sleep(1500);
					driver.findElement(By.id("deleteSelectedEquipment")).click(); // multi delete
					Thread.sleep(1000);
					//driver.findElement(By.id("deleteSelectedEquipment")).sendKeys(Keys.ENTER);
					//driver.findElement(By.xpath(".//*[@id='openAckModal']")).click();//click Yes in popup
					//Thread.sleep(500);
					driver.findElement(By.name("ackChangeControlNo")).sendKeys("111");
					Thread.sleep(500);
					//driver.findElement(By.id("ackChangeControlNo")).sendKeys(Keys.TAB +password);
					driver.findElement(By.name("password")).sendKeys(password);
					Thread.sleep(500);
					driver.findElement(By.id("comments")).sendKeys("Delete Multi equipment");
					Thread.sleep(500);
					//driver.findElement(By.id("ackSubmit")).click();
					driver.findElement(By.id("comments")).sendKeys(Keys.TAB,Keys.TAB,Keys.ENTER);
					Thread.sleep(1000);
					String deletemsg = driver.findElement(By.className("notify-msg")).getText(); // get deleted esuccess message
					Assert.assertEquals(deletemsg,Message.equipmentDELETE);
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
					{
						driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
					}
					Thread.sleep(500);
				}
				
				
				
				@Test(priority=25)
				public void CreateEquipmentforCreatingGroup() throws InterruptedException, SQLException, ClassNotFoundException, IOException
				{
					parser = new RepositoryParser("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\UI Map\\Equipment.properties");
					//Thread.sleep(5000);
					Thread.sleep(2000);
					SampleLocationSamplingSiteType();
					
					driver.get(Constant.URL+"/equipment");
					//driver.navigate().refresh();
					Thread.sleep(2000);
					driver.findElement(parser.getbjectLocator("CreateEquipment")).click(); // Click create equipment button
					Thread.sleep(1000);
					String equipmentName = EquipmentName;
					WebElement eqName = driver.findElement(parser.getbjectLocator("EquipmentName")); //Equipment Name field
					eqName.sendKeys(equipmentName);
					Thread.sleep(500);
					//WebElement locationdropdown = driver.findElement(By.name("location"));
					//Select location = new Select(locationdropdown);
					//location.selectByIndex(1);
					driver.findElement(By.name("name")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("Model")).sendKeys(EquipmentModelCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("AssetNo/SerialNo")).sendKeys(EquipmentSerialNoCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("Manufacturer")).sendKeys(EquipmentManufacturerCREATE);
					Thread.sleep(500);
					//Scroll down the page
					driver.findElement(parser.getbjectLocator("Manufacturer")).sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB);
					Thread.sleep(500);
					driver.findElement(By.id("surfaceArea")).sendKeys(EquipmentSFCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("SurfaceAreaDataSource")).sendKeys(EquipmentSFDataSourceCREATE);
					Thread.sleep(500);
					
					driver.findElement(parser.getbjectLocator("SurfaceAreaDataSource")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
					//WebElement preferentialTransfer = driver.findElement(parser.getbjectLocator("CanPreferentialTransferofResidueOccurwiththisEquipment?"));
					//Select preferentialTransferType = new Select(preferentialTransfer);
					//preferentialTransferType.selectByVisibleText(EquipmentPreferentialTransferCREATE);
					Thread.sleep(500);
					
					driver.findElement(By.id("preferentialTransferSurfaceArea")).sendKeys(EquipmentPreferentialTransferSFCREATE);
					Thread.sleep(500);

					driver.findElement(By.id("preferentialTransferSurfaceArea")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
					//WebElement PrimaryPackaging = driver.findElement(By.id("primaryPackaging"));
					//Select SelectPrimaryPackaging = new Select(PrimaryPackaging);
					//SelectPrimaryPackaging.selectByVisibleText(EquipmentPrimaryPackagingeOptionCREATE);
					Thread.sleep(500);
					//scroll down
					driver.findElement(By.id("preferentialTransferSurfaceArea")).sendKeys(Keys.TAB,Keys.TAB);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("MinimumBatchSize")).sendKeys(EquipmentMinBatchCREATE);
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("MinimumBatchSize")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
					Thread.sleep(500);
					//Cleaning SOP
					driver.findElement(parser.getbjectLocator("MinimumBatchSize")).sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.ENTER,Keys.ENTER);
					//WebElement SOP = driver.findElement(parser.getbjectLocator("CleaningSOPNo"));
					//Select CleaningSOP = new Select(SOP);
					//CleaningSOP.selectByIndex(1);
					Thread.sleep(500);
					//scroll down the page
					driver.findElement(parser.getbjectLocator("MinimumBatchSize")).sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB);
					Thread.sleep(500);
					//cleaing proceess type
					driver.findElement(parser.getbjectLocator("OtherCleaningRelevantInformation")).sendKeys(Keys.SHIFT,Keys.TAB,Keys.ENTER,Keys.ARROW_DOWN,Keys.ENTER);
					//WebElement processType = driver.findElement(parser.getbjectLocator("CleaningProcessType"));
					//Select CleaningProcessType = new Select(processType);
					//CleaningProcessType.selectByValue(EquipmentCleaningProcessTypeCREATE);
					Thread.sleep(500);
					
					driver.findElement(parser.getbjectLocator("OtherCleaningRelevantInformation")).sendKeys(EquipmentCleaningInfoCREATE);
					Thread.sleep(1000);
					//driver.findElement(parser.getbjectLocator("NextButton")).click();
					WebElement submit = driver.findElement(By.id("saveEquipment"));
					submit.click();
					Thread.sleep(500);
					
					
					Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
					for(int k=5;k<1000;k++)
					{
						j.add(k);
					}
					
					if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Equipment '"+equipmentName+"' already exists!"))
					{
						String getduplicatename = driver.findElement(By.className("notify-msg")).getText();
						driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
					
					Thread.sleep(500);
					if(getduplicatename.equalsIgnoreCase("Equipment '"+equipmentName+"' already exists!"))
					{
						for(Integer i:j)
						{
							//driver.findElement(parser.getbjectLocator("ActiveIngredientName")).clear();
							eqName.clear();
							Thread.sleep(200);
							eqName.sendKeys(equipmentName+i);
							//driver.findElement(parser.getbjectLocator("ActiveIngredientName")).sendKeys(Name+i);
							Thread.sleep(500);
							submit.click();
							//driver.findElement(parser.getbjectLocator("APIsubmit")).click();
							Thread.sleep(500);
							if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Equipment '"+equipmentName+i+"' already exists!"))
							{
								String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
								System.out.println("Name duplicate: "+nameduplicate);
								driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
								if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Equipment '"+equipmentName+i+"' already exists!"))
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
					if( driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Asset Number / Serial Number '"+EquipmentSerialNoCREATE+"' already exists!"))
					{
						String getduplicateID = driver.findElement(By.className("notify-msg")).getText();
						driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
					System.out.println("getduplicatename: "+getduplicateID);
					if(getduplicateID.equalsIgnoreCase("Asset Number / Serial Number '"+EquipmentSerialNoCREATE+"' already exists!"))
					{
						for(Integer i:j)
						{
							driver.findElement(parser.getbjectLocator("AssetNo/SerialNo")).clear();
							Thread.sleep(200);
							driver.findElement(parser.getbjectLocator("AssetNo/SerialNo")).sendKeys(EquipmentSerialNoCREATE+i);
							Thread.sleep(500);
							//driver.findElement(parser.getbjectLocator("APIsubmit")).click();
							submit.click();
							Thread.sleep(500);
							if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Asset Number / Serial Number '"+EquipmentSerialNoCREATE+i+"' already exists!"))
							{
								String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
								System.out.println("Name duplicate: "+nameduplicate);
								driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
								if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Asset Number / Serial Number '"+EquipmentSerialNoCREATE+i+"' already exists!"))
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
					System.out.println("submitLocationAssessment: "+driver.findElements(parser.getbjectLocator("submitLocationAssessment")).size());
					if(driver.findElements(parser.getbjectLocator("submitLocationAssessment")).size()!=0)
					{
					//Location Risk Assessment
					Thread.sleep(1000);
					driver.findElement(parser.getbjectLocator("AddLocation")).click();
					driver.findElement(parser.getbjectLocator("AddLocation")).click();
					Thread.sleep(500);
					driver.findElement(By.id("checkbox1")).click();
					Thread.sleep(500);
					driver.findElement(By.id("checkbox2")).click();
					Thread.sleep(500);
					driver.findElement(By.id("location1")).sendKeys(EquipmentSamplingLocationCREATE);
					Thread.sleep(500);
					driver.findElement(By.id("location2")).sendKeys(EquipmentSamplingLocationCREATE);
					Thread.sleep(500);
					//Enter Locations
			        Thread.sleep(500);
			        driver.findElement(By.id("location1")).sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB);
			        //Delete row
			        Thread.sleep(1000);
			        driver.findElement(By.cssSelector(".remove-row-icon")).click();
			        Thread.sleep(1000);
			        driver.findElement(By.id("location1")).sendKeys(EquipmentSamplingLocationCREATE);
			        Thread.sleep(500);
			       driver.findElement(By.cssSelector(".remove-row-icon")).sendKeys(Keys.SHIFT,Keys.TAB,Keys.SHIFT,Keys.TAB,Keys.SHIFT,Keys.TAB,Keys.SHIFT,Keys.TAB,Keys.SHIFT,Keys.TAB,Keys.SHIFT,Keys.TAB,Keys.SHIFT,Keys.TAB,Keys.SHIFT,Keys.TAB,Keys.SHIFT,Keys.TAB);
			       Thread.sleep(1000);
					
					driver.findElement(parser.getbjectLocator("submitLocationAssessment")).click(); // submit location assessment
					//Location Risk Assessment -End
					}
					
					
					Thread.sleep(1000);
					//Do you want to upload images?
					driver.findElement(By.xpath(".//*[@id='needUloadImages']/div/div/span/span[1]/span/span[2]")).click();
					driver.findElement(By.xpath(".//*[@id='needUloadImages']/div/div/span/span[1]/span/span[2]")).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
					//WebElement uploadsamplingimage = driver.findElement(parser.getbjectLocator("Doyouwanttouploadimages?"));
					//Select YesorNo = new Select(uploadsamplingimage);
					//YesorNo.selectByIndex(2); // select No option
					Thread.sleep(1000);
					//upload image
					//driver.findElement(By.xpath(".//*[@id='upload-images']/div/div/input")).sendKeys("C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\src\\Test Data\\equipTrain.jpg");
					//Thread.sleep(1000);
					//add location
					//driver.findElement(By.xpath(".//*[@id='addPinName']")).click();
					//click add location pin
					Thread.sleep(500);
					driver.findElement(parser.getbjectLocator("AddLocationButton")).click(); 
					//LocationName
					Thread.sleep(500);
					System.out.println("locationName1:" +driver.findElement(By.id("locationName1")).getAttribute("type"));
					if(driver.findElement(By.id("locationName1")).getAttribute("type").contains("text")) 
					{
						Thread.sleep(500);
						driver.findElement(By.id("locationName1")).clear();
						driver.findElement(By.id("locationName1")).sendKeys("Chennai");
						Thread.sleep(500);
						//select MOC
						driver.findElement(By.id("locationName1")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
						Thread.sleep(500);
						//Select Sampling Type
						driver.findElement(By.id("locationName1")).sendKeys(Keys.TAB,Keys.TAB,Keys.TAB,Keys.ENTER,Keys.ENTER);
						
					}
					Thread.sleep(1000);
					//MOC Selection
					System.out.println("-->"+driver.findElement(By.id("locationName1")).getAttribute("type"));
					if(driver.findElement(By.id("locationName1")).getAttribute("type").contains("select"))
					{
						driver.findElement(By.xpath(".//*[@id='marker-num1']/div[2]/span/span[1]/span")).click();
						driver.findElement(By.xpath(".//*[@id='marker-num1']/div[2]/span/span[1]/span")).sendKeys(Keys.ENTER);
						Thread.sleep(1000);
						driver.findElement(By.xpath(".//*[@id='marker-num1']/div[3]/span/span[1]/span")).click();
						driver.findElement(By.xpath(".//*[@id='marker-num1']/div[3]/span/span[1]/span")).sendKeys(Keys.ENTER);
					}
					
					//select sampling method and test
					driver.findElement(By.xpath(".//*[@id='marker-num1']/div[4]/span/span[1]/span")).click();
					driver.findElement(By.xpath(".//*[@id='marker-num1']/div[4]/span/span[1]/span")).sendKeys(Keys.ENTER);
					
				//	WebElement Sampling = driver.findElement(By.id("mocy1"));
					//Select SelectSampling = new Select(Sampling);
					//SelectSampling.selectByValue("1");
					
					
					
					
					//save image
					//driver.findElement(By.xpath(".//*[@id='preview-image']/div/div[2]/img")).click(); //click to save uploaded image
					//Thread.sleep(1000);
					Thread.sleep(1000);
					WebElement samplingbutton = driver.findElement(By.id("submitEquipmentSamplingDetails")); //submitEquipmentSamplingDetails
					System.out.println("samplingbutton: "+samplingbutton);
					if(samplingbutton.getText().equalsIgnoreCase("Submit"))
					{
						System.out.println("No Custom loop");
						driver.findElement(By.id("submitEquipmentSamplingDetails")).click();
						Thread.sleep(2000);
					}
					
					if(samplingbutton.getText().equalsIgnoreCase("Next"))
					{
						Thread.sleep(1000);
						driver.findElement(By.id("submitEquipmentSamplingDetails")).click();
						Thread.sleep(1000);
						System.out.println("----->"+driver.findElement(By.id("addSegment")).getText());
						System.out.println("----->size"+driver.findElements(By.id("next-gotoeqpSamplingCustom")).size());
						
						if(driver.findElement(By.id("addSegment")).getText().equalsIgnoreCase("Add Segment"))
						{
							Thread.sleep(1000);
							driver.findElement(By.id("addSegment")).click();
							Thread.sleep(500);
							driver.findElement(By.id("segmentName1")).sendKeys(EquipmentSegmentNameCREATE);
							Thread.sleep(500);
							driver.findElement(By.id("segSurfaceArea1")).sendKeys(EquipmentSegmentSFCREATE);
							Thread.sleep(500);
							//select segment locations
							driver.findElement(By.id("segSurfaceArea1")).sendKeys(Keys.TAB,Keys.ENTER,Keys.ENTER);
							WebElement Seglocation = driver.findElement(By.id("select1"));
							Select SelectLocation = new Select(Seglocation);
							SelectLocation.selectByIndex(0);
							Thread.sleep(1000);
							
							WebElement segSubmit = driver.findElement(By.id("next-gotoeqpSamplingCustom")); //submitEquipmentSamplingDetails
							System.out.println("================"+segSubmit.getText());
						//	System.out.println("================"+driver.findElement(By.cssSelector("#next-gotoeqpSamplingCustom")).getText());
							
							
							if(segSubmit.getText().equalsIgnoreCase("Submit"))
							{
								System.out.println("SegLoop: No Custom loop");
								segSubmit.click();
								Thread.sleep(2000);
							}
							else // custom loop after segment info
							{
								segSubmit.click();
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
										Thread.sleep(2000);
							}
							
						}
						else // custom loop after sampling info
						{
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
									Thread.sleep(2000);
						}
					}
					
					String createEquipment = driver.findElement(By.className("notify-msg")).getText();
					System.out.println("createEquipment "+createEquipment);
					//String createEquipment = driver.findElement(By.className("notify-msg")).getText();
					Assert.assertEquals(createEquipment,Message.equipmentCREATE);
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
					{
						//String createEquipment = driver.findElement(By.className("notify-msg")).getText();
						//System.out.println("createEquipment "+createEquipment);
						//String createEquipment = driver.findElement(By.className("notify-msg")).getText();
						//Assert.assertEquals(createEquipment,"Equipment saved successfully");
						driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
					}
					Thread.sleep(500);
					
				} // closing Create Equipment method
				
				
				
				@Test(priority=26,invocationCount=5)
				public void CopyEquipment() throws InterruptedException, SQLException, ClassNotFoundException
				{
					//driver.navigate().refresh();
					driver.get(Constant.URL+"/equipment");
					Thread.sleep(2000);
					//driver.findElement(parser.getbjectLocator("EquipmentAction")).click(); // Click action icon
					driver.findElement(By.id("dLabel")).click();
					Thread.sleep(500);
					//driver.findElement(By.xpath(".//*[@id='datatable']/tbody/tr[1]/td[10]/div/ul/li[2]/a")).click(); // click delete button
					//driver.findElement(By.className("dropdown-item")).sendKeys(Keys.ENTER);
					driver.findElement(By.linkText("Copy")).click();
					Thread.sleep(500);
					
					String equipmentName = "Test Equipment";
					WebElement eqName = driver.findElement(By.id("nameForCopy")); //Equipment copy Name field
					eqName.sendKeys(equipmentName);
					
					Thread.sleep(500);
					//driver.findElement(By.xpath(".//*[@id='dynamicModal']/div[3]/div/button[2]")).click();
					driver.findElement(By.cssSelector(".btn.blue-btn.waves-effect.copyData")).click();
					Thread.sleep(3000);
					
					String successMSG = null;
					if(driver.findElements(By.cssSelector(".btn.blue-btn.waves-effect.copyData")).size()!=0)
					{
						String getduplicatename = driver.findElement(By.className("notify-msg")).getText();
						driver.findElement(By.className("custom-notify-close")).click();
						if(getduplicatename.equalsIgnoreCase(Message.equipmentCOPY))
						{
							successMSG = getduplicatename;
						}
						
					Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
					for(int k=25;k<1000;k++)
					{
						j.add(k);
					}
					
					Thread.sleep(500);
					if(getduplicatename.equals("Equipment '"+equipmentName+"' already exists!"))
					{
						System.out.println("loop");
						for(Integer i:j)
						{
							driver.findElement(By.id("nameForCopy")).clear();
							driver.findElement(By.id("nameForCopy")).sendKeys(equipmentName+i);
							Thread.sleep(500);
							driver.findElement(By.cssSelector(".btn.blue-btn.waves-effect.copyData")).click();
							Thread.sleep(3000);
							String nameduplicate = driver.findElement(By.className("notify-msg")).getText();
							if(driver.findElements(By.className("notify-msg")).size()!=0 
									&& driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Equipment '"+equipmentName+i+"' already exists!"))
							{
								System.out.println("nameduplicate: "+nameduplicate);
								if(nameduplicate.equalsIgnoreCase(Message.equipmentCOPY))
								{
									successMSG = nameduplicate;
									System.out.println("For Loop: "+successMSG);
								}
								driver.findElement(By.className("custom-notify-close")).click();
								if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Equipment '"+equipmentName+i+"' already exists!"))
								{
									continue;
								}
							}
									System.out.println("Not duplicate so break the loop");
									if(nameduplicate.equalsIgnoreCase(Message.equipmentCOPY))
									{
										successMSG = nameduplicate;
										System.out.println("For Loop: "+successMSG);
									}
									break;
							}
						}
					} 
					Thread.sleep(3000);
					//successMSG = driver.findElement(By.className("notify-msg")).getText();
					System.out.println("MSG: "+successMSG);
					Assert.assertEquals(successMSG,Message.equipmentCOPY);
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					//if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
					//{
					//	driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
					//}
					Thread.sleep(500);
				}
				
				
				
				
			/*	
				@Test(priority=18)
				public void ExportEquipment() throws Exception
				{
					Utils.ExportPDF(driver);
				}
				
				*/
				
				
				
				
				/*@AfterClass
				public void tearDown()
				{
					driver.quit();
				}*/
				
	
				
				public void SampleLocationRiskAssessment() throws InterruptedException
				{
					Thread.sleep(500);
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
					if(!msg.equalsIgnoreCase("Material Of Construction cannot be empty")&& 
							msg.equalsIgnoreCase(Message.MOCSave))
					{
						//Thread.sleep(500);
						//driver.findElement(By.id("saveMoc")).click();
						//Thread.sleep(1000);
						//String SuccessMessage = driver.findElement(By.className("notify-msg")).getText();
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
					//WebElement Risk = driver.findElement(By.name("samplingType"));
					//Select RiskAssement = new Select(Risk);
					//Risk.click();
					//Select criteria fro Risk	
					//driver.findElement(By.id("trigger-add-role")).click();
					//Thread.sleep(500);
					
					//driver.findElement(By.id("criteria-name")).sendKeys(Keys.SHIFT,Keys.TAB,Keys.TAB,Keys.TAB,Keys.TAB,Keys.ARROW_DOWN,Keys.ENTER);
					driver.findElement(By.xpath(".//*[@id='limitTerminologyDefinition']/div/div/div/div[2]/div/div/span/span[1]/span/span[2]")).click();
					driver.findElement(By.xpath(".//*[@id='limitTerminologyDefinition']/div/div/div/div[2]/div/div/span/span[1]/span/span[2]")).sendKeys(Keys.ARROW_DOWN,Keys.ENTER);
					Thread.sleep(500);
					//RiskAssement.selectByValue("2");
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
							//WebElement sampling = driver.findElement(By.id("sampling-location"));
							//sampling.click();
							//Select s= new Select(sampling);
							//s.selectByValue("1");
							//s.selectByValue("2");
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
					
					//WebElement rating = driver.findElement(By.id("rating"));
					//Select SelectRating = new Select(rating);
					//WebElement option = SelectRating.getFirstSelectedOption();
					//String getvalue = option.getText();
					//System.out.println("getpreTransfer"+getvalue);
					//if(getvalue.equalsIgnoreCase("Select"))
					//{
						//SelectRating.selectByValue("1");
						//Thread.sleep(1000);
					//}
					
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
					/*if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
					{
						driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
					}*/
					Thread.sleep(500);
				}
				
				
				
				
				public void SampleLocationSamplingSiteType() throws InterruptedException
				{
					Thread.sleep(500);
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
					if(!msg.equalsIgnoreCase("Material Of Construction cannot be empty")&& 
							msg.equalsIgnoreCase(Message.MOCSave))
					{
						//Thread.sleep(500);
						//driver.findElement(By.id("saveMoc")).click();
						//Thread.sleep(1000);
						//String SuccessMessage = driver.findElement(By.className("notify-msg")).getText();
						Assert.assertEquals(msg,Message.MOCSave);
						String className = this.getClass().getName(); // get current class name - for screenshot
						String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
						Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
						if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
						{
							//driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
						}
						Thread.sleep(1000);
						System.out.println("-------->");
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
					
					//Select RiskAssement = new Select(Risk);
					//RiskAssement.selectByVisibleText("Based on Sampling Site Type");
					Thread.sleep(1000);
					System.out.println("Test");
					
					driver.findElement(By.xpath(".//*[@id='limitTerminologyDefinition']/div/div/div/div[2]/div/div/span/span[1]/span/span[2]")).click();
					driver.findElement(By.xpath(".//*[@id='limitTerminologyDefinition']/div/div/div/div[2]/div/div/span/span[1]/span/span[2]")).sendKeys(Keys.ARROW_UP,Keys.ENTER);
					Thread.sleep(1000);
					
					//listLocation
					driver.findElement(By.id("listLocation")).click();
					Thread.sleep(1000);
					WebElement samplinglocation = driver.findElement(By.id("saveLoc"));
					samplinglocation.click();
					Thread.sleep(500);
					String samplingMSG = driver.findElement(By.className("notify-msg")).getText();
					Thread.sleep(1000);
					System.out.println("Sampling msg: "+samplingMSG);
					if(!samplingMSG.equalsIgnoreCase("Sampling Location Type cannot be empty")&& 
							samplingMSG.equalsIgnoreCase(Message.samplinglocationTypeSave))
					{
						Assert.assertEquals(samplingMSG,Message.samplinglocationTypeSave);
						String className = this.getClass().getName(); // get current class name - for screenshot
						String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
						Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
						System.out.println("Loop");
						if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
						{
							driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
						}
						Thread.sleep(1000);
						
					}
					else
					{
						Thread.sleep(500);
						driver.findElement(By.id("addLOC")).click();
						Thread.sleep(500);
						driver.findElement(By.id("locationName11")).clear();
						driver.findElement(By.id("locationName11")).sendKeys("Bottom");
						Thread.sleep(500);
						driver.findElement(By.id("saveLoc")).click();
						Thread.sleep(1000);
						String SuccessMessage = driver.findElement(By.className("notify-msg")).getText();
						Assert.assertEquals(SuccessMessage,Message.samplinglocationTypeSave);
						String className = this.getClass().getName(); // get current class name - for screenshot
						String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
						Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
						if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
						{
							driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
						}
						Thread.sleep(1000);
					}
					
					driver.findElement(By.id("saveSamplingLocation")).click();
					Thread.sleep(2000);
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
						driver.findElement(By.xpath(".//*[@id='ackSubmit']")).click();
						Thread.sleep(1000);
						Assert.assertEquals(driver.findElement(By.className("notify-msg")).getText(),Message.samplinglocationSave);
					}
				
					String className = this.getClass().getName(); // get current class name - for screenshot
					String Currentmethdname = new Object(){}.getClass().getEnclosingMethod().getName(); // get current method name - for screenshot
					Utils.captureScreenshot_eachClass(driver,Currentmethdname,className); // Capture Screenshot with current method name
					/*if(driver.findElements(By.cssSelector(".grey-text.custom-notify-close")).size()!=0)
					{
						driver.findElement(By.cssSelector(".grey-text.custom-notify-close")).click();
					}*/
					Thread.sleep(500);
				}
	
}
