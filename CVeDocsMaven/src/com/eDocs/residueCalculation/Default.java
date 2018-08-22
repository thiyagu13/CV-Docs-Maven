package com.eDocs.residueCalculation;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Utils;
import com.mysql.jdbc.Connection;

public class Default {
	public static WebDriver driver;
	
	static String tenant_id=Constant.tenant_id;
	/*public static String UniversalSettings() throws IOException, InterruptedException,ClassNotFoundException {
		//System.setProperty("webdriver.Chrome.driver","C:\\selenium\\Testing\\chromedriver.exe");
		System.setProperty("webdriver.gecko.driver","C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\geckodriver.exe");
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();

		capabilities.setCapability("marionette", true);
		
		driver = new FirefoxDriver(capabilities);
		// Open the application
		driver.get("http://192.168.1.111:8090/login");
		Thread.sleep(1000);
		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.id("password")).sendKeys("123456");
		Thread.sleep(3000);
		driver.findElement(By.id("loginsubmit")).click();
		Thread.sleep(1000);
		driver.get("http://192.168.1.111:8090/residue-limit");
		
		//driver.switchTo().alert().accept();
		if (driver.getTitle().equalsIgnoreCase("Report Tracker - eResidue") == false) {
			Thread.sleep(1000);
			String pop = driver.getWindowHandle();
			driver.switchTo().window(pop);
			Thread.sleep(1000);
			driver.findElement(By.xpath(".//*[@id='loginForm']/div[3]/div/input[2]")).click();
		}
		// ForceLogin
		if (driver.findElement(By.className("top-message")).getText()
				.equalsIgnoreCase("Invalid credentials!")) {
			System.out.println(driver.findElement(By.className("top-message"))
					.getText());
		} else {
			// Force Login
			if (driver.getTitle().equalsIgnoreCase("Report Tracker - eResidue") == false) {
				Thread.sleep(1000);
				String pop = driver.getWindowHandle();
				driver.switchTo().window(pop);
				Thread.sleep(1000);
				driver.findElement(By.id("forcelogin")).click();
			}
		}
		Thread.sleep(1000);
		
		// mouse over on Settings menu
		WebElement f = driver.findElement(By.xpath(".//*[@id='settings']"));
		Actions a1 = new Actions(driver);
		Thread.sleep(300);
		a1.moveToElement(f).perform();
		Thread.sleep(1000);
		// select Universal Settings option
		driver.findElement(By.linkText("Universal Settings")).click();
		Thread.sleep(500);
		System.out.println(driver.getTitle());
		Thread.sleep(500);
		// select Limit Definition Tab
		driver.findElement(By.linkText("Limit Definition")).click();
	
		defaultmethod();
		//System.out.println("defaultmethod option selected---->"+defaultmethod());
		//Thread.sleep(10000);
		//driver.close();
		return defaultmethod();
	}
		public static String defaultmethod()
		{
			
		for (int i = 1; i <=4; i++) 
		{
			WebElement radiobutton = driver.findElement(By.id("default"+i));
			String str2;
			if (radiobutton.isSelected()) 
			{
				str2 = radiobutton.getAttribute("Value"); 
				
				//First Radio button is selected
				if(str2.equals("1")){
					return "No_Default";
				}
				
				//Second Radio button is selected
				if(str2.equals("2"))
				{
					//System.out.println("'Use a default value for L1' is selected =---->");
					WebElement radiobutton2 = driver.findElement(By.id("defaultL1Default"));
					{
						if (radiobutton2.isSelected()) 
						{
						String de_L1 = radiobutton2.getAttribute("Value"); 
						String de_L1_unitCon = Double.toString(Double.parseDouble(de_L1) * 0.001); // For unit conversion (ppm to mg/g)
						return "Default_L1@@"+de_L1_unitCon;
						}
					else{
						String de_L1 = driver.findElement(By.id("defautL1OthersValue")).getAttribute("value");
						String de_L1_unitCon = Double.toString(Double.parseDouble(de_L1) * 0.001); // For unit conversion (ppm to mg/g)
						return "Default_L1@@"+de_L1_unitCon;
						}
					}
					
				}
				
				//Third Radio button is selected
				if(str2.equals("3"))
				{
					WebElement radiobutton3 = driver.findElement(By.id("defaultL3Default"));
					{
						if (radiobutton3.isSelected()) 
						{
						String de_L3 = radiobutton3.getAttribute("Value"); 
						System.out.println("Use a default value for L3="+de_L3+" mg/sq.cm ");
						return "Default_L3@@"+de_L3;
						}
					else{
						String de_L3 = driver.findElement(By.id("defautL3OthersValue")).getAttribute("value");
						System.out.println("Use a default value for L3="+de_L3+"mg/sq.cm (other)");
						return "Default_L3@@"+de_L3;
						}
					}
				}
				
				//Fourth Radio button is selected
				if(str2.equals("4"))
				{
					WebElement defaultL1 = driver.findElement(By.id("defaultBothL1Default"));
					WebElement defaultL3 = driver.findElement(By.id("defaultBothL3Default"));
					WebElement defaultL1_other_option = driver.findElement(By.id("defaultBothL1Other"));
					WebElement defaultL1_other_value = driver.findElement(By.id("defautBothL1OthersValue"));
					WebElement defaultL3_other_option = driver.findElement(By.id("defaultBothL3Other"));
					WebElement defaultL3_other_value = driver.findElement(By.id("defautBothL3OthersValue"));
					{
						if (defaultL1.isSelected() &&defaultL3.isSelected()) // default L1=10 ppm and Default L3 = 0.004 mg/sq.cm
						{
						String de_L1 = defaultL1.getAttribute("Value"); 
						System.out.println("Use a default value for L1="+de_L1+"ppm");
						String de_L1_Conv = Double.toString(Double.parseDouble(de_L1) * 0.001); // For unit conversion (ppm to mg/g)
						
						String de_L1_L3 = defaultL3.getAttribute("Value"); 
						System.out.println("Use a default value for L3="+de_L1_L3+"mg/sq.cm "); // Default L3 value
						return "Default_L1L3@@"+de_L1_Conv +"@@"+de_L1_L3 ;
						
						}
						if (defaultL1.isSelected() &&defaultL3_other_option.isSelected() ) // default L1=10 ppm and Default other L3 value
						{
						String de_L1 = defaultL1.getAttribute("Value"); 
						System.out.println("Use a default value for L1="+de_L1+"ppm");
						String de_L1_Conv = Double.toString(Double.parseDouble(de_L1) * 0.001); // For unit conversion (ppm to mg/g)
						
						String de_L1_L3 = defaultL3_other_value.getAttribute("Value"); 
						System.out.println("Use a default value for L3="+de_L1_L3+"mg/sq.cm "); // Default L3 value
						return "Default_L1L3@@"+de_L1_Conv +"@@"+de_L1_L3 ;
						}
						if (defaultL1_other_option.isSelected() &&defaultL3_other_option.isSelected() ) // default other L1 value and Default other L3 value
						{
						String de_L1 = defaultL1_other_value.getAttribute("Value"); 
						System.out.println("Use a default value for L1="+de_L1+"ppm");
						String de_L1_Conv = Double.toString(Double.parseDouble(de_L1) * 0.001); // For unit conversion (ppm to mg/g)
						
						String de_L1_L3 = defaultL3_other_value.getAttribute("Value"); 
						System.out.println("Use a default value for L3="+de_L1_L3+"mg/sq.cm "); // Default L3 value
						return "Default_L1L3@@"+de_L1_Conv +"@@"+de_L1_L3 ;
						}
						if (defaultL1_other_option.isSelected() &&defaultL3.isSelected() ) // default other L1 Value and Default other L3 = 0.004 mg/sq.cm
						{
						String de_L1 = defaultL1_other_value.getAttribute("Value"); 
						System.out.println("Use a default value for L1="+de_L1+"ppm");
						String de_L1_Conv = Double.toString(Double.parseDouble(de_L1) * 0.001); // For unit conversion (ppm to mg/g)
						
						String de_L1_L3 = defaultL3.getAttribute("Value"); 
						System.out.println("Use a default value for L3="+de_L1_L3+"mg/sq.cm "); // Default L3 value
						return "Default_L1L3@@"+de_L1_Conv +"@@"+de_L1_L3 ;
						}
					}
				}
			}
			} 

		return null;
		}*/
		
	
public static String defaultmethod() throws SQLException, ClassNotFoundException
		{
				
		//database connection
			Connection connection = Utils.db_connect();
			Statement stmt = (Statement) connection.createStatement();
			Integer defaultLimitOption=0,l1_default_flag=0,l3_default_flag = 0;
			float l1_other_value = 0,l3_other_value=0;
			ResultSet defaultLimit = stmt.executeQuery("SELECT l1_and_l3_option,l1_default_flag,l1_other_value,l3_default_flag,l3_other_value FROM residue_limit where tenant_id='"+tenant_id+"'");
			while (defaultLimit.next()) 
			{
				 defaultLimitOption = defaultLimit.getInt(1);
				 l1_default_flag = defaultLimit.getInt(2); // use Default value for L1 (option- default or other)
				 l1_other_value = defaultLimit.getFloat(3); // use Default value for L1 value (both default & other value stored)
				 l3_default_flag = defaultLimit.getInt(4); // use Default value for L3 (option- default or other)
				 l3_other_value = defaultLimit.getFloat(5); // use Default value for L3 value (both default & other value stored)
			}
	
	
	
	
		for (int i = 1; i <=defaultLimitOption; i++) 
		{			
				//No Default used. Use calculated value
				if(defaultLimitOption==1)
				{
					return "No_Default";
				}
				
				//Use a Default value for L1
				if(defaultLimitOption==2)
				{					
					//{
						if (l1_default_flag==1) // L1 default value(10ppm)
						{
						 // For unit conversion (ppm to mg/g)
						float de_L1_unitCon = (float) (l1_other_value * 0.001);
						System.out.println("de_L1_unitCon"+de_L1_unitCon);
						return "Default_L1@@"+de_L1_unitCon;
						
						}
						if (l1_default_flag==0) // L1 default other value
						{
						//String de_L1 = driver.findElement(By.id("defautL1OthersValue")).getAttribute("value");
						float de_L1_unitCon = (float) (l1_other_value * 0.001);
						//String de_L1_unitCon = Double.toString(l1_other_value * 0.001); // For unit conversion (ppm to mg/g)
						System.out.println("de_L1_unitCon"+de_L1_unitCon);
						return "Default_L1@@"+de_L1_unitCon;
					
						}											
				}
				
				//Third Radio button is selected
				if(defaultLimitOption==3)
				{					
						if (l3_default_flag==1) // L3 default value 
						{						
							float de_L3 = l3_other_value;
							System.out.println("Use a default value for L3="+de_L3+" mg/sq.cm ");
							return "Default_L3@@"+de_L3;
						}
						if (l3_default_flag==0) // L3 default other value
						{						
							float de_L3 = l3_other_value;
							System.out.println("Use a default value for L3="+de_L3+"mg/sq.cm (other)");
							return "Default_L3@@"+de_L3;
						}					
				}
				
				//Fourth Radio button is selected
				if(defaultLimitOption==4)
				{					
						float de_L1_Conv = (float) (l1_other_value * 0.001); 						
						System.out.println("Use a default value for L1="+de_L1_Conv+"ppm");						
						
						float de_L1_L3 = (float) (l3_other_value ); 						  
						System.out.println("Use a default value for L3="+de_L1_L3+"mg/sq.cm "); // Default L3 value
						return "Default_L1L3@@"+de_L1_Conv +"@@"+de_L1_L3 ;											
				}
			
			} // closing for loop
		connection.close();
		return null;
		}
		
		
		
		public static float getMaxDose(String CurrenProductName) throws ClassNotFoundException, SQLException
		{
			//database connection
			Connection connection = Utils.db_connect();
			Statement stmt = (Statement) connection.createStatement();
			ResultSet getprodname_id = stmt.executeQuery("SELECT * FROM product where name = '" + CurrenProductName + "' && tenant_id='"+tenant_id+"'");// Execute the SQL Query to find prod id from product table
			//Get product id 
			int prodname_id = 0;
			while (getprodname_id.next()) 
				{
				prodname_id = getprodname_id.getInt(1); // get name id from product table
				}
			//Get highest dose of active value
		    Set<Integer> basisofcalIDs  = new HashSet<>();
		    ResultSet basisofcalID = stmt.executeQuery("SELECT basis_of_calc_id FROM product_basis_of_calculation_relation where product_id = "+prodname_id+" && tenant_id='"+tenant_id+"'");
		    while(basisofcalID.next())
		    {
		    	basisofcalIDs.add(basisofcalID.getInt(1)); // get health based value
		    }
		    
		    List<Float> getdoseofActive  = new ArrayList<>();
		    for(int basisofcal:basisofcalIDs)
		    {
		    	ResultSet doseofActive = stmt.executeQuery("SELECT dose_of_active FROM product_basis_of_calculation where id = "+basisofcal+ " && tenant_id='"+tenant_id+"'");
			    while(doseofActive.next())
			    {
			    	getdoseofActive.add(doseofActive.getFloat(1)); // get health based value
			    }
		    }
		    System.out.println("getdoseofActive" +getdoseofActive);
		    
		    Float getmaxDoseofActive = Collections.max(getdoseofActive);
		    System.out.println("getminDoseofActive" +getmaxDoseofActive);
		    
		    connection.close();
			return getmaxDoseofActive;
		}
		
		
		
		public static boolean groupingApproachDefaultL1(String CurrenProductName) throws ClassNotFoundException, SQLException
		{
			//database connection
			Connection connection = Utils.db_connect();
			Statement stmt = (Statement) connection.createStatement();
			ResultSet getprodname_id = stmt.executeQuery("SELECT * FROM product where name = '" + CurrenProductName + "' && tenant_id='"+tenant_id+"'");// Execute the SQL Query to find prod id from product table
			int prodname_id = 0, lowestsolubilityID = 0,lowestADEID=0;
			//Get product id 
			while (getprodname_id.next()) 
				{
				prodname_id = getprodname_id.getInt(1); // get name id from product table
				}
				//get active id
				ResultSet getactiveID = stmt.executeQuery("SELECT active_ingredient_id FROM product_active_ingredient_relation where product_id='" + prodname_id + "' && tenant_id='"+tenant_id+"'");
				List<Integer> active = new ArrayList<>(); // store multiple equipment id
			    	while (getactiveID.next()) 
			    	{
			    	active.add(getactiveID.getInt(1)); // get health based value
			    	}
			    //get lowest solubility within api from product
			    List<Float> Solubilities = new ArrayList<>(); // store multiple equipment id
			    List<Float> ADE = new ArrayList<>(); // store multiple equipment id
			    	for(int activeID:active)
			    	{
			    		ResultSet getallActive = stmt.executeQuery("SELECT solubility_in_water,lowest_route_of_admin_value FROM product_active_ingredient where id = '"+activeID+ "' && tenant_id='"+tenant_id+"'");
			    		while(getallActive.next())
			    		{
			    			Solubilities.add((float) getallActive.getFloat(1)); // get solibility value
			    			ADE.add((float) getallActive.getFloat(2)); // get health based value
			    		}
			    	}
			    float minsolubility = Collections.min(Solubilities); // get minimum value from awithin active
			    float minADE = Collections.min(ADE); // get minimum value from awithin active
			    	
			    // find minimum Solubility active id
			    for(int listofactiveID:active)
			    {
			    ResultSet getActive = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = "+listofactiveID+ " && solubility_in_water LIKE "+minsolubility+ " or solubility_in_water="+minsolubility+" && tenant_id='"+tenant_id+"'");
			    while(getActive.next())
			    {
			    	System.out.println("pass");
			    	lowestsolubilityID =getActive.getInt(1); // get health based value
			    }
			    } // end - get lowest solubility within api from product
			    System.out.println("Lowest Solubility active id: "+lowestsolubilityID);
			    
			    // find minimum ADE active id
			    for(int listofactiveID:active)
			    {
			    ResultSet getActive = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = "+listofactiveID+ " && lowest_route_of_admin_value LIKE "+minADE+" or lowest_route_of_admin_value="+minADE+" && tenant_id='"+tenant_id+"'");
			    while(getActive.next())
			    {
			    	System.out.println("pass");
			    	lowestADEID =getActive.getInt(1); // get health based value
			    }
			    } // end - get lowest solubility within api from product
			    System.out.println("Lowest ADE active id: "+lowestADEID);
			
			    
			    connection.close();
			    if(lowestsolubilityID==lowestADEID)
			    {
			    	return true;
			    }else {
			    	return false;
			    }
			  
		}
		
		
	}

