package com.eDocs.microbialCalculation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Utils;
import com.mysql.jdbc.Connection;
import com.eDocs.residueCalculation.ResidueCalculationwithCampaign;
import com.eDocs.residueCalculation.SurfaceAreaValue;

public class MicrobialCalculation {
	
	//static String currentproductname = "P11";
//	static String nextproductname = "P11";
	/*String productName1 ="P11";
	String productName2 ="P22";
	String productName3 ="P33";
	String productName4 ="P44";*/
	//WebDriver driver;
	static String tenant_id= Constant.tenant_id;
	
	
	@Test
	public void test() throws ClassNotFoundException, SQLException, IOException, InterruptedException
	{/*
		
		//XSSFWorkbook workbook;
		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH); 
		XSSFSheet sheet = workbook.getSheet("microbial_calculation_result");
		
		driver = new FirefoxDriver();
		// Open the application
		driver.get("http://192.168.1.111:8090/login");
		Thread.sleep(1000);
		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.id("password")).sendKeys("123456");
		Thread.sleep(3000);
		driver.findElement(By.id("loginsubmit")).click();
		Thread.sleep(1000);
		driver.get("http://192.168.1.111:8090/products");
		Thread.sleep(1000);
		driver.findElement(By.id("searchEquipment")).sendKeys("P11");
		Thread.sleep(1000);
		driver.findElement(By.xpath(".//*[@id='dLabel']/i")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(".//*[@id='datatable']/tbody/tr[1]/td[9]/div/ul/li[2]/a")).click(); //click calculation window
		Thread.sleep(1000);
		driver.findElement(By.xpath(".//*[@id='calculateProductForm']/div[2]/div[2]/div/div/label")).click(); // click manual calculation option
		Thread.sleep(1000);
		String title = "Test Calculation";
		driver.findElement(By.name("title")).sendKeys(title);//title of calculation
		Thread.sleep(1000);
		WebElement limitselection = driver.findElement(By.id("limitSelection")); // select residue limit
		Select limit = new Select(limitselection);
		limit.selectByIndex(2);
		Thread.sleep(1000);
		WebElement selectproduct = driver.findElement(By.xpath(".//*[@id='routeAdminDiv']/div/div/span/span[1]/span/ul/li/input"));

		int row =8;
		for(int i = 0;i<=100;i++)
		{
			String getproductname = sheet.getRow(row).getCell(2).getStringCellValue(); //get product name list from excel (for using calculation)
			System.out.println("getproductname"+getproductname);
			if(!StringUtils.isEmpty(getproductname))
			{
				selectproduct.sendKeys(getproductname);
				Thread.sleep(300);
				selectproduct.sendKeys(Keys.ENTER);
				Thread.sleep(300);
				row++;
			}else {
				break;
			}
		}
		driver.findElement(By.id("changeControlNo")).sendKeys("123");
		
		
		
		Select select = new Select(driver.findElement(By.id("productGroups"))); // get seleced products from the dropdown
		List<WebElement> option = select.getAllSelectedOptions();

		Set <String> selectedproducts = new HashSet<>();
		for(WebElement iterateselectedproducts: option)
		{
			 selectedproducts.add(iterateselectedproducts.getText());
		}
		
		System.out.println("selectedproducts-->"+selectedproducts);
		
		driver.findElement(By.id("saveCalculateProduct")).click(); // Click calculation submit button
		
		//get duplication calculation
		String gettitle = driver.findElement(By.xpath("html/body/div[18]/div/span")).getText();
		Thread.sleep(500);
		driver.findElement(By.xpath("html/body/div[18]/div/button")).click();
		
		Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
		for(int k=45;k<1000;k++)
		{
			j.add(k);
		}
		
		List<Integer> iteratecaltitle = new ArrayList<>();
		iteratecaltitle.addAll(j);
		
		System.out.println("iteratecaltitle-->"+iteratecaltitle);
		if(gettitle.equals("Calculation '"+title+"' already exists!"))
		{
			for(Integer i:iteratecaltitle)
			{
			WebElement caltitle1 = driver.findElement(By.name("title"));//title of calculation
			caltitle1.clear();
			caltitle1.sendKeys(title+i);
			driver.findElement(By.id("saveCalculateProduct")).click(); // Click calculation submit button
			Thread.sleep(500);
			try
			{
				if(driver.findElement(By.xpath("html/body/div[18]/div/span"))!=null)
				{
					
					System.out.println(driver.findElement(By.xpath("html/body/div[18]/div/span"))==null);
					String title_duplicate = driver.findElement(By.xpath("html/body/div[18]/div/span")).getText();
					System.out.println("title_duplicate: "+title_duplicate);
					driver.findElement(By.xpath("html/body/div[18]/div/button")).click(); //close the duplicate validation alert
					if(title_duplicate.equals("Calculation '"+title+i+"' already exists!"))
					{
						iteratecaltitle.lastIndexOf(i);
						System.out.println("Remove---->"+iteratecaltitle.lastIndexOf(i));
						System.out.println("iteratecaltitle " +iteratecaltitle);
						System.out.println("duplicate");
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
		Thread.sleep(300);
		List<String>  currentproductlist = new ArrayList<>(); //store product list
  		currentproductlist.addAll(selectedproducts);
  		
		List<String>  nextproductlist = new ArrayList<>(); //store product list
		nextproductlist.addAll(selectedproducts);*/
		Set<String> selectedproducts = new HashSet<>();
		//selectedproducts.add("L5");
		//selectedproducts.add("L6");
		selectedproducts.add("S1");	
		selectedproducts.add("S2");
		selectedproducts.add("S3");	
		
		List<String>  currentproductlist = new ArrayList<>(); //store product list
  		currentproductlist.addAll(selectedproducts);
  		
		List<String>  nextproductlist = new ArrayList<>(); //store product list
		nextproductlist.addAll(selectedproducts);
		
		microbialcalculation(currentproductlist, nextproductlist);
		//writeTooutputFile(workbook); // write output into work sheet
	}
	
	
		static float bioburdenL1,bioburdenL3,bioburdenL4ContactPlateORSwab,L4Rinse,EndoToxinResult;
		public static void microbialcalculation(List<String> currentproduct,List<String> nextproduct) throws SQLException, ClassNotFoundException, IOException
		{
			XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH); 
			XSSFSheet sheet = workbook.getSheet("Microbial_Calculation");
			//database connection
			Connection connection = Utils.db_connect();
			Statement stmt = (Statement) connection.createStatement();
			
		  	ResultSet microbialoption = stmt.executeQuery("SELECT limits_setup_option,bioburden_limit_option,surface_sample_option,surface_sample_calculate_compare_default,sampling_method_option,contact_plate_surface_area,other_surface_sample_calculate_compare_default_value,other_surface_sample_calculate_compare_default_value_unit,preset_apply_default_limit_for_surface,other_default_limit_value_for_surface_value_unit,other_default_limit_value_for_surface_value,default_endotoxin_limit,default_rinse_limit_nmt FROM microbial_limit where tenant_id='"+tenant_id+"'");
		  	
		  	int microLimitOption=0,bioburden_limit_option=0,surfaceSampleOption=0,surfaceSampleCalculateCompareDefault=0,
		  			sampling_method_option=0,other_surface_sample_calculate_compare_default_value_unit = 0,other_default_limit_value_for_surface_value_unit=0,default_endotoxin_limit=0,default_rinse_limit_nmt=0;
		  	float contact_plate_surface_area=0,other_surface_sample_calculate_compare_default_value = 0,preset_apply_default_limit_for_surface=0,other_default_limit_value_for_surface_value=0;
		  	
		  		while (microbialoption.next())  //check microbial option whether bioburden or both
				{
		  			microLimitOption = microbialoption.getInt(1);
		  			bioburden_limit_option = microbialoption.getInt(2);
		  			surfaceSampleOption = microbialoption.getInt(3);
		  			surfaceSampleCalculateCompareDefault = microbialoption.getInt(4);
		  			sampling_method_option = microbialoption.getInt(5);
		  			contact_plate_surface_area = microbialoption.getFloat(6);
		  			other_surface_sample_calculate_compare_default_value = microbialoption.getFloat(7);
		  			
		  			other_surface_sample_calculate_compare_default_value_unit = microbialoption.getInt(8);
		  			preset_apply_default_limit_for_surface = microbialoption.getInt(9);
		  			other_default_limit_value_for_surface_value_unit = microbialoption.getInt(10);
		  			other_default_limit_value_for_surface_value = microbialoption.getInt(11);
		  			default_endotoxin_limit = microbialoption.getInt(12);
		  			default_rinse_limit_nmt = microbialoption.getInt(13);
			}
			
		  	List<String>  currentproductlist = new ArrayList<>();
		  	currentproductlist.addAll(currentproduct);
		  		
		  	List<String>  nextproductlist = new ArrayList<>();
		  	nextproductlist.addAll(nextproduct);
	  		
		  		
		 int row=41,surface=7,surfaceontact=8,printCurrentpname=3,printNextpname=4, l4Rinserow =41,trainrow=41;
		 // Manual or Campaign mode
		 String CalculationType = sheet.getRow(39).getCell(0).getStringCellValue();
		 for(String currentproductname:currentproductlist)
		 {
		  			Cell currentprodname = sheet.getRow(row).getCell(printCurrentpname);
			  		currentprodname.setCellValue(currentproductname); 
			  		
			  		List<Float> LowestBioburdenL3 = new ArrayList<Float>(); 	
			  		int nextproductID = 0;
			  		
			  		int CurrentproductType = 0;
					// get current product type
					ResultSet getproductType = stmt.executeQuery("Select product_type from product where name = '"+ currentproductname + "' && tenant_id='" + tenant_id + "'"); // get product name id
					while (getproductType.next()) {	CurrentproductType = getproductType.getInt(1);	}
				if(CurrentproductType==2)
				{
					//Skip if diluent as current product
				}
				else
				{
			  		
		  for(String nextproductname:nextproductlist)
		  {
			  	 if(currentproductname.equalsIgnoreCase(nextproductname) && CalculationType.equalsIgnoreCase("Manual"))
				 {
					 // skip if manual, Same product result  
				 } if(!currentproductname.equalsIgnoreCase(nextproductname) && CalculationType.equalsIgnoreCase("Manual")||
						  currentproductname.equalsIgnoreCase(nextproductname) && CalculationType.equalsIgnoreCase("Campaign")||
						  !currentproductname.equalsIgnoreCase(nextproductname) && CalculationType.equalsIgnoreCase("Campaign"))
				 {
			  
			  	ResultSet getcurrentProductID = stmt.executeQuery("SELECT * FROM product where name ='"+nextproductname+"' && tenant_id='"+tenant_id+"'");
				while (getcurrentProductID.next())  //check surface or both
				{	
					nextproductID = getcurrentProductID.getInt(1);
				}
		  		//if only Surface Limit Selected Selected
		  		if(microLimitOption==1 || microLimitOption==3) //bioburden(1) or endotoxin(2) or both(3)
		  		{
		  			if(bioburden_limit_option==1 || bioburden_limit_option==3)  //Surface(1) or rinse(2) or both(3)
		  			{
		  				if(surfaceSampleOption==3) //Calculate Limit and compare against the default limit 
		  				{
		  					//Compare Surface Value 1 CFU/sq.cm with calculated  
		  					if(surfaceSampleCalculateCompareDefault==1)
		  					{
		  						if(BioburdensurfaceLimit(currentproductname,nextproductname)<1)// Compare calculated value and default value 1 CFU/sq.cm 
		  						{
		  							bioburdenL3 = BioburdensurfaceLimit(currentproductname,nextproductname);
		  							//Find Contact Plate or Swab
		  							if(sampling_method_option==1)
		  							{
		  								bioburdenL4ContactPlateORSwab = bioburdenL3 * contact_plate_surface_area;
		  							}
		  							if(sampling_method_option==2)
		  							{
		  								bioburdenL4ContactPlateORSwab = bioburdenL3 * contact_plate_surface_area;
		  							}
		  						}
		  						else// Compare calculated value and default value 1 CFU/sq.cm 
		  						{
		  							System.out.println("1");
		  							bioburdenL3 = 1;
		  							//Find Contact Plate or Swab
		  							if(sampling_method_option==1)
		  							{
		  								bioburdenL4ContactPlateORSwab = bioburdenL3 * contact_plate_surface_area;
		  							}
		  							if(sampling_method_option==2)
		  							{
		  								bioburdenL4ContactPlateORSwab = bioburdenL3 * contact_plate_surface_area;
		  							}
		  						}
		  					}
		  					
		  				//Other - //Compare Surface Value other CFU/sq.cm with calculated  
		  				if(surfaceSampleCalculateCompareDefault==2 && other_surface_sample_calculate_compare_default_value_unit==1)
		  				{
		  					// other value
		  					if(BioburdensurfaceLimit(currentproductname,nextproductname)<other_surface_sample_calculate_compare_default_value)// Compare calculated value and default value 1 CFU/sq.cm 
	  						{
	  							bioburdenL3 = BioburdensurfaceLimit(currentproductname,nextproductname);
	  							//Find Contact Plate or Swab
	  							if(sampling_method_option==1)
	  							{
	  								bioburdenL4ContactPlateORSwab = bioburdenL3 * contact_plate_surface_area;
	  							}
	  							if(sampling_method_option==2)
	  							{
	  								bioburdenL4ContactPlateORSwab = bioburdenL3 * contact_plate_surface_area;
	  							}
	  							System.out.println("bioburdenL3: "+bioburdenL3);
	  							System.out.println("bioburdenL4ContactPlateORSwab: "+bioburdenL4ContactPlateORSwab);
	  						}
	  						else// Compare calculated value and default value 1 CFU/sq.cm 
	  						{
	  							bioburdenL3 = other_surface_sample_calculate_compare_default_value;
	  							//Find Contact Plate or Swab
	  							if(sampling_method_option==1)
	  							{
	  								bioburdenL4ContactPlateORSwab = bioburdenL3 * contact_plate_surface_area;
	  							}
	  							if(sampling_method_option==2)
	  							{
	  								bioburdenL4ContactPlateORSwab = bioburdenL3 * contact_plate_surface_area;
	  							}
	  						}
		  						
		  				}
		  				
		  				float lowestSFContactORSwab;
		  				// Compare calculated contact plate value with default value
		  				if(surfaceSampleCalculateCompareDefault==2 && other_surface_sample_calculate_compare_default_value_unit==2
		  						|| surfaceSampleCalculateCompareDefault==2 && other_surface_sample_calculate_compare_default_value_unit==3)
		  				{
		  					bioburdenL3 = BioburdensurfaceLimit(currentproductname,nextproductname);
		  					lowestSFContactORSwab = bioburdenL3 * contact_plate_surface_area;
		  					
		  					if(lowestSFContactORSwab<other_surface_sample_calculate_compare_default_value)
		  					{
		  						bioburdenL4ContactPlateORSwab = lowestSFContactORSwab;
		  					}
		  					if(lowestSFContactORSwab>other_surface_sample_calculate_compare_default_value)
		  					{
		  						bioburdenL4ContactPlateORSwab = other_surface_sample_calculate_compare_default_value;
		  								
		  					}
		  				}
		  			}	
		  				
		  			//	Surface - Apply Default Limit
		  			if(surfaceSampleOption==2)
		  			{
		  				//Use default value 1CFU/sq.cm
		  				if(preset_apply_default_limit_for_surface==1)
		  				{
		  					bioburdenL3 = 1;
		  					//Find Contact Plate or Swab
  							if(sampling_method_option==1)
  							{
  								bioburdenL4ContactPlateORSwab = bioburdenL3 * contact_plate_surface_area;
  							}
  							if(sampling_method_option==2)
  							{
  								bioburdenL4ContactPlateORSwab = bioburdenL3 * contact_plate_surface_area;
  							}
		  				}
		  				
		  				if(preset_apply_default_limit_for_surface==2)
		  				{
		  					
		  					if(other_default_limit_value_for_surface_value_unit==1)
		  					{
		  						bioburdenL3 = other_default_limit_value_for_surface_value;
		  						//Find Contact Plate or Swab
	  							if(sampling_method_option==1)
	  							{
	  								bioburdenL4ContactPlateORSwab = bioburdenL3 * contact_plate_surface_area; // Default surface
	  							}
	  							if(sampling_method_option==2)
	  							{
	  								bioburdenL4ContactPlateORSwab = bioburdenL3 * contact_plate_surface_area;// Default surface
	  							}
		  					}
		  					
		  					if(other_default_limit_value_for_surface_value_unit==2) // Default Contact plate Value
		  					{
		  							bioburdenL3 = BioburdensurfaceLimit(currentproductname,nextproductname);
		  							bioburdenL4ContactPlateORSwab = other_default_limit_value_for_surface_value;
		  					}
		  					
		  					if(other_default_limit_value_for_surface_value_unit==3) // Default Swab Value
		  					{
		  							bioburdenL3 = BioburdensurfaceLimit(currentproductname,nextproductname);
		  							bioburdenL4ContactPlateORSwab = other_default_limit_value_for_surface_value;
		  					}
		  				}
		  			}
		  		}
		  			else if(bioburden_limit_option==2)
		  			{
		  				bioburdenL3 = BioburdensurfaceLimit(currentproductname,nextproductname);
		  			}
		  	}
		  		
		  		
		  		
		  		//if endotoxin result
		  		if(microLimitOption==2 || microLimitOption==3) //bioburden(1) or endotoxin(2) or both(3)
		  		{
		  			if(default_endotoxin_limit==1)
		  			{
		  				EndoToxinResult = (float) 0.25;
		  			}
		  			if(default_endotoxin_limit==2)
	  			{
	  				EndoToxinResult = default_rinse_limit_nmt;
	  			}
		  		}
		  		
	  		Cell nextprodname = sheet.getRow(row).getCell(printNextpname); 
	  		nextprodname.setCellValue(nextproductname); 
	  		//Print expected result
	  		Cell SurfaceLimit = sheet.getRow(row).getCell(surface); 
	  		SurfaceLimit.setCellValue(bioburdenL3); 
    		Cell SurfaceLimitContorswab = sheet.getRow(row).getCell(surfaceontact);
    		SurfaceLimitContorswab.setCellValue(bioburdenL4ContactPlateORSwab); 
    		LowestBioburdenL3.add(bioburdenL3);// Get all the L3 in the list
    		//Actual L3 result
    		actualL3Result(currentproductname, nextproductID,workbook, stmt, sheet, row,bioburdenL3,bioburdenL4ContactPlateORSwab);
    		row++;
		}
	}  	 // closing next product list for loop
		  
		 
		   		//Lowest value from iteration
		   		float LowestBioburdenL3fromAll = Collections.min(LowestBioburdenL3);
		   		System.out.println("Lowest L3: "+LowestBioburdenL3fromAll);		  
		   		// Get actual L3 and contact plate result from database
		   		
		 //Equipment based L4 rinse bioburden (Expected and actual Result)
		  		bioburdenL4RinseEquipment(workbook,sheet, currentproductname,l4Rinserow,LowestBioburdenL3fromAll);
		   		l4Rinserow++;
		   		row++;
		   		l4Rinserow++; // space between each product rinse
		   		
		   		
		  //Equipment Train based L4 rinse bioburden (Expected and actual Result)
		   		bioburdenL4cRinseTrain(workbook, sheet, stmt, currentproductname, trainrow, LowestBioburdenL3fromAll);		
		   		trainrow++;
		  
		}// Closing if else loop - if current product not diluent   		
		   		
	 } // closing current product list for loop
		
		
		 
		  	connection.close();	  		
		  	writeTooutputFile(workbook); // write output into work sheet
		}
		
		
		
	
	
		
		//actual result L3
		public static void actualL3Result(String currentproductname,int nextproductID, XSSFWorkbook workbook,Statement stmt,XSSFSheet sheet,
				int row,float L3SurfaceLimit,float L3SurfacelimitContactORSwab ) throws SQLException
			{
				int currentproductID=0;
	    		//get current product id
	    		ResultSet getcurrentProductID = stmt.executeQuery("SELECT * FROM product where name ='"+currentproductname+"' && tenant_id='"+tenant_id+"'");
				while (getcurrentProductID.next())  //check surface or both
				{	
					currentproductID = getcurrentProductID.getInt(1);
				}
				System.out.println("Current: "+currentproductID);
				System.out.println("Next: "+nextproductID);
	    		
				float actualSurfaceLimit=0,actualSurfaceContactPlateSwab=0;
	    		//ResultSet bioburdensurfaceResult = stmt.executeQuery("SELECT * FROM microbial_bioburden_results where product_id="+currentproductID+" and next_product_ids="+nextproductID+" and rinse_or_surface="+1+" && tenant_id='"+tenant_id+"'");
				ResultSet bioburdensurfaceResult = stmt.executeQuery("SELECT * FROM microbial_bioburden_results where product_id="+currentproductID+" and next_product_ids="+nextproductID+" && tenant_id='"+tenant_id+"'");
				while (bioburdensurfaceResult.next())  //check surface or both
				{	
					actualSurfaceLimit = bioburdensurfaceResult.getFloat(2);
					actualSurfaceContactPlateSwab = bioburdensurfaceResult.getFloat(3);
				}
				int actualsurfacelimitColumn =11,actualsurfacelimitContantPlateColumn=12;
				
				if(actualSurfaceLimit!=0) //print actual surface limit value in excel
				{
				Cell actualSurfaceLimitresult = sheet.getRow(row).getCell(actualsurfacelimitColumn); 
				actualSurfaceLimitresult.setCellValue(actualSurfaceLimit);
				}else
				{
					Cell actualSurfaceLimitresult = sheet.getRow(row).getCell(actualsurfacelimitColumn); 
					actualSurfaceLimitresult.setCellValue("");
				}
				
				if(actualSurfaceContactPlateSwab!=0) //print actual surface limit contact plate/swab value in excel
				{
					Cell actulasurfaceLimitContorswabResult = sheet.getRow(row).getCell(actualsurfacelimitContantPlateColumn);
		    		actulasurfaceLimitContorswabResult.setCellValue(actualSurfaceContactPlateSwab);
				}else
				{
					Cell actulasurfaceLimitContorswabResult = sheet.getRow(row).getCell(actualsurfacelimitContantPlateColumn);
		    		actulasurfaceLimitContorswabResult.setCellValue("");
				}
	    		
	    		int surfacestatus=13;
	    		if(actualSurfaceLimit!=0)// if actual surface value not zero
	    		{
	    			if(Utils.toOptimizeDecimalPlacesRoundedOff(L3SurfaceLimit).equals(Utils.toOptimizeDecimalPlacesRoundedOff(actualSurfaceLimit))) //verify expected surface and actual surface limit value
	    			{
	    			Cell surfacestatusresult = sheet.getRow(row).getCell(surfacestatus);
	    			surfacestatusresult.setCellValue("Pass");
	    			surfacestatusresult.setCellStyle(Utils.style(workbook, "Pass"));
	    			}else {
	    			Cell surfacestatusresult = sheet.getRow(row).getCell(surfacestatus);
	    			surfacestatusresult.setCellValue("Fail");
	    			surfacestatusresult.setCellStyle(Utils.style(workbook, "Fail"));
	    			}
	    			
	    		}
	    		int surfacecontactstatus=14;
	    		if(actualSurfaceContactPlateSwab!=0)//if actual surface contact value not zero
	    		{
	    			if(Utils.toOptimizeDecimalPlacesRoundedOff(L3SurfacelimitContactORSwab).equals(Utils.toOptimizeDecimalPlacesRoundedOff(actualSurfaceContactPlateSwab))) //verify expected surface and actual surface limit value
	    			{
	    			Cell surfacestatusresult = sheet.getRow(row).getCell(surfacecontactstatus);
	    			surfacestatusresult.setCellValue("Pass");
	    			surfacestatusresult.setCellStyle(Utils.style(workbook, "Pass"));
	    			}else {
	    			Cell surfacestatusresult = sheet.getRow(row).getCell(surfacecontactstatus);
	    			surfacestatusresult.setCellValue("Fail");
	    			surfacestatusresult.setCellStyle(Utils.style(workbook, "Fail"));
	    			}
	    			
	    		}
			}
		
		
	
	//Equipment Expected L4c
	public static void bioburdenL4RinseEquipment(XSSFWorkbook workbook,XSSFSheet sheet,String CurrenProductName,int L4Row,float LowestBioburdenL3fromAll) throws ClassNotFoundException, SQLException, IOException
	{
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();
		String sampling_methodOption = null;
		int RinseSampling = 0;
		ResultSet residueLimit = stmt.executeQuery("Select sampling_method,rinse_sampling_option from residue_limit where tenant_id='" + tenant_id + "'"); // get product name
		while(residueLimit.next())
		{
			sampling_methodOption = residueLimit.getString(1);
			RinseSampling = residueLimit.getInt(2);
		}
		
		for (Integer equipmentID : ResidueCalculationwithCampaign.getEquipment(CurrenProductName)) // get id from set
		{
			System.out.println("--------->"+equipmentID);
			Cell ActiveName = sheet.getRow(L4Row).getCell(16);
			ActiveName.setCellValue(CurrenProductName); // print active name into excel
			ResultSet EquipID = stmt.executeQuery("Select name,surface_area,swab_area,swab_amount,rinse_volume from equipment where id='"+ equipmentID + "' && tenant_id='" + tenant_id + "'"); // get product name
									
			String eqname = null;
			double SFArea = 0,rinsevolume = 0,bioburdenExpectedL4cRinse;
			while (EquipID.next()) 
			{ 
				eqname = EquipID.getString(1); // get name from database
				SFArea = EquipID.getFloat(2); // get SF value from database
				rinsevolume = EquipID.getFloat(5); // get rinse volume from database
			}
				// equipment rinse volume()
				if (ResidueCalculationwithCampaign.eqRinseVolume() == 0) // check rinset from univ setting or each equipment
				{
					bioburdenExpectedL4cRinse = (LowestBioburdenL3fromAll * SFArea) / (rinsevolume * 1000);
					Cell equipRinse = sheet.getRow(L4Row).getCell(20);
					equipRinse.setCellValue(rinsevolume); 
															
				} else 
				{
					bioburdenExpectedL4cRinse = (LowestBioburdenL3fromAll * SFArea) / (ResidueCalculationwithCampaign.eqRinseVolume() * 1000);
					Cell equipRinse = sheet.getRow(L4Row).getCell(20);
					equipRinse.setCellValue(ResidueCalculationwithCampaign.eqRinseVolume()); // print all the equipment rinse
				}
				// Print Expected L4c value
				Cell equipName = sheet.getRow(L4Row).getCell(17);
				equipName.setCellValue(eqname); 
				Cell equipSF = sheet.getRow(L4Row).getCell(18);
				equipSF.setCellValue(SFArea); 
				
				float ExpectedL4RiseEquipment = 0;
				//To Do comapre L4rinse value with default value
				ResultSet microbialoption = stmt.executeQuery("SELECT limits_setup_option,bioburden_limit_option,rinse_sample_option,rinse_sample_calculate_compare_default,other_rinse_sample_calculate_compare_default_unit,other_rinse_sample_calculate_compare_default_value,preset_apply_default_limit_for_rinse,other_default_limit_for_rinse_unit,other_default_limit_for_rinse_value FROM microbial_limit where tenant_id='"+tenant_id+"'");
			  	
			  	int microLimitOption=0,bioburden_limit_option=0,rinseSampleOption=0,rinseSampleCalculateCompareDefault=0,
			  			other_rinse_sample_calculate_compare_default_unit=0,preset_apply_default_limit_for_rinse=0,other_default_limit_for_rinse_unit=0;
			  	float other_rinse_sample_calculate_compare_default_value=0,other_default_limit_for_rinse_value=0;
			  		while (microbialoption.next())  //check microbial option whether bioburden or both
					{
			  			microLimitOption = microbialoption.getInt(1);
			  			bioburden_limit_option = microbialoption.getInt(2);
			  			rinseSampleOption = microbialoption.getInt(3);
			  			rinseSampleCalculateCompareDefault = microbialoption.getInt(4);
			  			other_rinse_sample_calculate_compare_default_unit = microbialoption.getInt(5);
			  			other_rinse_sample_calculate_compare_default_value = microbialoption.getFloat(6);
			  			preset_apply_default_limit_for_rinse= microbialoption.getInt(7);
			  			other_default_limit_for_rinse_unit= microbialoption.getInt(8);
			  			other_default_limit_for_rinse_value= microbialoption.getFloat(9);
				}
			  		
			  		if(microLimitOption==1 || microLimitOption==3) //bioburden(1) or endotoxin(2) or both(3)
			  		{
			  			if(bioburden_limit_option==2 || bioburden_limit_option==3)  //Surface(1) or rinse(2) or both(3)
			  			{
			  				if(rinseSampleOption==2) //Rinse - default(2) 
			  				{
			  					if(preset_apply_default_limit_for_rinse==1) //rinse default value 1
			  					{
			  						ExpectedL4RiseEquipment = (float) 0.1; //(NMT 1 CFU/10mL)
			  						System.out.println("ExpectedL4RiseEquipment--: "+ExpectedL4RiseEquipment);
			  					}
			  					if(preset_apply_default_limit_for_rinse==2) //rinse default value 2
			  					{
			  						ExpectedL4RiseEquipment = 100;
			  					}
			  					if(preset_apply_default_limit_for_rinse==3) //rinse other default value 
			  					{
			  						//Unit based
			  						if(other_default_limit_for_rinse_unit==1)
			  						{
			  							ExpectedL4RiseEquipment = other_default_limit_for_rinse_value;	
			  						}
			  						if(other_default_limit_for_rinse_unit==2)
			  						{
			  							ExpectedL4RiseEquipment = (other_default_limit_for_rinse_value/10);	
			  						}
			  						if(other_default_limit_for_rinse_unit==3)
			  						{
			  							ExpectedL4RiseEquipment = (other_default_limit_for_rinse_value/100);	
			  						}
			  					}	
			  				}
			  				
			  			if(rinseSampleOption==3) //Rinse - Compare default & no default(3) 
		  				{
			  					if(rinseSampleCalculateCompareDefault==1) //compare default 1 value with calculated value
			  					{
			  						if(bioburdenExpectedL4cRinse<0.1)
			  						{
			  							ExpectedL4RiseEquipment = (float) bioburdenExpectedL4cRinse;
			  						}else
			  						{
			  							ExpectedL4RiseEquipment = (float)0.1;
			  						}
			  					}
			  					if(rinseSampleCalculateCompareDefault==2) //compare default 100 value with calculated value
			  					{
			  						if(bioburdenExpectedL4cRinse<100)
			  						{
			  							ExpectedL4RiseEquipment = (float) bioburdenExpectedL4cRinse;
			  						}else
			  						{
			  							ExpectedL4RiseEquipment = 100;
			  						}
			  					}
			  					if(rinseSampleCalculateCompareDefault==3) //compare default other value with calculated value
			  					{
			  						if(other_rinse_sample_calculate_compare_default_unit==1) // if default rinse unit is CFU/mL
			  						{
			  							if(bioburdenExpectedL4cRinse<other_rinse_sample_calculate_compare_default_value)
			  							{
			  								ExpectedL4RiseEquipment = (float) bioburdenExpectedL4cRinse;
			  							}else
			  							{
			  								ExpectedL4RiseEquipment = other_rinse_sample_calculate_compare_default_value;
			  							}
			  						}
			  						if(other_rinse_sample_calculate_compare_default_unit==2) // if default rinse unit is CFU/10mL
			  						{
			  							if(bioburdenExpectedL4cRinse<(other_rinse_sample_calculate_compare_default_value/10))
			  							{
			  								ExpectedL4RiseEquipment = (float) bioburdenExpectedL4cRinse;
			  							}else
			  							{
			  								ExpectedL4RiseEquipment = (other_rinse_sample_calculate_compare_default_value/10);
			  							}
			  						}
			  						if(other_rinse_sample_calculate_compare_default_unit==3) // if default rinse unit is CFU/10mL
			  						{
			  							if(bioburdenExpectedL4cRinse<(other_rinse_sample_calculate_compare_default_value/100))
			  							{
			  								ExpectedL4RiseEquipment = (float) bioburdenExpectedL4cRinse;
			  							}else
			  							{
			  								ExpectedL4RiseEquipment = (other_rinse_sample_calculate_compare_default_value/100);
			  							}
			  						}
			  					}
			  					
		  				}
			  		}
			  	}
				//End - To Do comapre with default value
				
				
				// check whether rinse enabled in universal settings
				if (sampling_methodOption.equals("1,2") && RinseSampling == 1 ||sampling_methodOption.equals("1,2,3") && RinseSampling == 1) // if rinse as indiv
				{
					System.out.println("ExpectedL4RiseEquipment: "+ExpectedL4RiseEquipment);
					Cell L4cEquip = sheet.getRow(L4Row).getCell(21);
					L4cEquip.setCellValue(ExpectedL4RiseEquipment);
				}

				
			//get current product ID
			int getCurrentprodID = 0;
			ResultSet getcurrentProdID = stmt.executeQuery("SELECT * FROM product where name='"	+ CurrenProductName + "' && tenant_id='" + tenant_id+ "'");
			while (getcurrentProdID.next()) 
			{
				getCurrentprodID = getcurrentProdID.getInt(1);
			}
		
				
			// Actual Result for L4a, L4b, L4c
			float bioburdenActualL4Rinse = 0;
			ResultSet ActualequipResult = stmt.executeQuery("SELECT l4c FROM microbial_bioburden_equipment_results where product_id='"
							+ getCurrentprodID + "' && equipment_id='" + equipmentID + "' && tenant_id='" + tenant_id+ "'");
			while (ActualequipResult.next()) {
				bioburdenActualL4Rinse = ActualequipResult.getFloat(1);
			}

			if (sampling_methodOption.equals("1,2") && RinseSampling == 1|| sampling_methodOption.equals("1,2,3") && RinseSampling == 1) // if rinse enabled in
			{
				if (bioburdenActualL4Rinse != 0) {
					Cell L4cEquipactual = sheet.getRow(L4Row).getCell(22);// cell to print L4b value
					L4cEquipactual.setCellValue(bioburdenActualL4Rinse); // print all the equipment surface area(used in the product) in excel
				} else {
					Cell L4cEquipactual = sheet.getRow(L4Row).getCell(22);// cell to print L4b value
					L4cEquipactual.setCellValue("NA");
				}
			}
			/*if (sampling_methodOption.equals("1,2") && RinseSampling == 1) // if rinse enabled in sampling
			{
				// check expected L4c and actual L4c
				double EL4c = sheet.getRow(L4Row).getCell(21).getNumericCellValue();
				double AL4c = sheet.getRow(L4Row).getCell(22).getNumericCellValue();
					
				if (Utils.toOptimizeDecimalPlacesRoundedOff(EL4c).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4c))) {
					Cell verify_result = sheet.getRow(L4Row).getCell(23);
					verify_result.setCellValue("Pass");
					verify_result.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
				} else {
					Cell verify_result = sheet.getRow(L4Row).getCell(23);
					verify_result.setCellValue("Fail");
					verify_result.setCellStyle(Utils.style(workbook, "Fail")); // for print red font
				}
			}*/
			L4Row++;
		} // closing for equipment ID loop
		connection.close();	  		
	}
	
	
	
  // Get adjusted Bsp value for each current product
  public static float adjustedBSP(String currentproductname) throws SQLException, ClassNotFoundException 
  {
	//database connection
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();// Create Statement Object
	  ResultSet microbialoption = stmt.executeQuery("SELECT limits_setup_option FROM microbial_limit where tenant_id='"+tenant_id+"'");
	  	int microLimitOption = 0,bioburderLimit = 0,surfacelimitoption=0,
	  			factorMicroEnumeraion=0,rinseoption=0;
	  	float adjusted_BSP = 0,productBSP=0,bioburdenContribution=0;
		while (microbialoption.next())  //check microbial option whether bioburder or both
		{	
		microLimitOption = microbialoption.getInt(1);
		}
			if(microLimitOption==1 || microLimitOption==3)
			{
				ResultSet bioburderLimitOption = stmt.executeQuery("SELECT var_factor_micro_enum,bioburden_limit_option,surface_sample_option,rinse_sample_option,default_endotoxin_limit FROM microbial_limit where tenant_id='"+tenant_id+"'");
				while (bioburderLimitOption.next())  //check surface or both
				{	
					//bioburdenContribution = bioburderLimitOption.getInt(1);
					factorMicroEnumeraion = bioburderLimitOption.getInt(1);
					bioburderLimit = bioburderLimitOption.getInt(2);
					surfacelimitoption = bioburderLimitOption.getInt(3);
					rinseoption = bioburderLimitOption.getInt(4);
					//endotoxinDefaultvalueOption = bioburderLimitOption.getInt(5);
				}
				//get basp value rom product
				ResultSet bspfromProduct = stmt.executeQuery("SELECT bioburden_spec,bioburden_contribution FROM product where name='"+currentproductname+"' && tenant_id='"+tenant_id+"'");
				while (bspfromProduct.next())  //check surface or both
				{	
					productBSP = bspfromProduct.getFloat(1);
					bioburdenContribution = bspfromProduct.getFloat(2);
				}
				if(bioburderLimit == 1 || bioburderLimit==2 || bioburderLimit ==3  ) // check surface or rinse or both
				{
					if(surfacelimitoption ==1 || surfacelimitoption==3 || rinseoption ==1 || rinseoption==3) //check surface limit whther default or not default or both
					{
					float bioburden = 100-bioburdenContribution;
					adjusted_BSP = productBSP * ((bioburden/100) / factorMicroEnumeraion);
					//Adjusted_BSP = BSP x (100-Bioburden contribution)% / factor
					
					}
				}
			}
			System.out.println("Adjusted BSP: "+adjusted_BSP);
			connection.close();
			return adjusted_BSP;
	}
  
  
  		//find L3 bioburden surface limit value
  		public static float BioburdensurfaceLimit(String currentproductname,String nextproductname) throws ClassNotFoundException, SQLException
  		{
  			float L3SurfaceBioburden = 0,minBatchofNextprod=0;
  			int surfaceArea = 0;
  			Connection connection = Utils.db_connect();
  			Statement stmt = (Statement) connection.createStatement();// Create Statement 
  			
  			ResultSet Product = stmt.executeQuery("SELECT min_batch_size FROM product where name='"+nextproductname+"' && tenant_id='"+tenant_id+"'");
			while (Product.next())  //check surface or both
			{	
				minBatchofNextprod = Product.getFloat(1);
			}
			// Check whther Shared or Lowest
			ResultSet residueLimit = stmt.executeQuery("SELECT l3_surface_area_option,sampling_method FROM residue_limit where tenant_id='"+tenant_id+"'");
			while(residueLimit.next())
			{
				surfaceArea = residueLimit.getInt(1);
			}
			
			if(surfaceArea==0)
			{
				L3SurfaceBioburden = (adjustedBSP(currentproductname) * minBatchofNextprod * 1000) / SurfaceAreaValue.actualSharedbetween2(currentproductname, nextproductname);
			}else
			{
				L3SurfaceBioburden = (adjustedBSP(currentproductname) * minBatchofNextprod * 1000) / SurfaceAreaValue.lowestTrainbetween2(currentproductname, nextproductname);
			}
			connection.close();
			return L3SurfaceBioburden;
  		}
  
  		
  	/*//find L3 bioburden surface limit value
  		public static float BioburdensurfaceLimit(String currentproductname,String nextproductname) throws ClassNotFoundException, SQLException
  		{
  			float L3SurfaceBioburden = 0,minBatchofNextprod=0;
  			
  			Connection connection = Utils.db_connect();
  			Statement stmt = (Statement) connection.createStatement();// Create Statement 
  			
  			ResultSet Product = stmt.executeQuery("SELECT min_batch_size FROM product where name='"+nextproductname+"' && tenant_id='"+tenant_id+"'");
			while (Product.next())  //check surface or both
			{	
				minBatchofNextprod = Product.getFloat(1);
			}
			L3SurfaceBioburden = (adjustedBSP(currentproductname) * minBatchofNextprod * 1000) / SurfaceAreaValue.sameProductSF(nextproductname);
			connection.close();
			return L3SurfaceBioburden;
  		}*/
  
  	//To get L3surfacelimit - Contact plate or swab
  		public static float surfaceLimitContactPlateORSwab(String currentproductname,String nextproductname) throws ClassNotFoundException, SQLException
  		{
  			Connection connection = Utils.db_connect();
  			Statement stmt = (Statement) connection.createStatement();// Create Statement Object
  			
  			 ResultSet SwabSurfaceLimit = stmt.executeQuery("SELECT contact_plate_surface_area,bioburden_limit_option FROM microbial_limit where tenant_id='"+tenant_id+"'");
  			 float contactPlateORSwab = 0,SurfaceLimitContactORSwab = 0;
  			 int bioburdenoption = 0;
  				while (SwabSurfaceLimit.next())  //check microbial option whether bioburder or both
  				{	
  				contactPlateORSwab = SwabSurfaceLimit.getInt(1);
  				bioburdenoption = SwabSurfaceLimit.getInt(2);
  				}
  				if(bioburdenoption == 1 || bioburdenoption ==3  ) // check surface or rinse or both
				{
  					SurfaceLimitContactORSwab = BioburdensurfaceLimit(currentproductname,nextproductname) * contactPlateORSwab;
				}
  				connection.close();
			return SurfaceLimitContactORSwab;
  		}
  
  
  	//To get L3surfacelimit - Contact plate or swab
  		public static float rinseLimit(String currentproductname, String nextproductname,float L3SurfaceLimit)   throws ClassNotFoundException, SQLException
  		{
  			float L3RinseBioburden;
  			Connection connection = Utils.db_connect();
  			Statement stmt = (Statement) connection.createStatement();// Create Statement Object
  			 ResultSet product = stmt.executeQuery("SELECT total_rinse_volume FROM product where name='"+currentproductname+"' && tenant_id='"+tenant_id+"'");
 			 float rinsevolumeofcurrentproduct = 0;
 				while (product.next())  //check microbial option whether bioburder or both
 				{	
 				rinsevolumeofcurrentproduct = product.getFloat(1);
 				}
 				//get minimum batch of next product
 				ResultSet Product = stmt.executeQuery("SELECT min_batch_size FROM product where name='"+nextproductname+"' && tenant_id='"+tenant_id+"'");
 	  			float minBatchofNextprod=0;
 				while (Product.next())  //check surface or both
 				{	
 					minBatchofNextprod = Product.getFloat(1);
 				}
 				
 				if(L3SurfaceLimit!=0)
 					{
 					L3RinseBioburden  = (L3SurfaceLimit * SurfaceAreaValue.sameProductSF(nextproductname)) / (rinsevolumeofcurrentproduct * 1000);
 					}else
 					{
 						L3RinseBioburden  = (adjustedBSP(currentproductname) * minBatchofNextprod * 1000)/ (rinsevolumeofcurrentproduct * 1000);
 					}
 				connection.close();
				return L3RinseBioburden;
				
  		}
  		
  		
  		

  		
  		public static void bioburdenL4cRinseTrain(XSSFWorkbook workbook,XSSFSheet sheet,Statement stmt,String CurrenProductName,int TrainRow,float LowestBioburdenL3fromAll) throws SQLException, ClassNotFoundException
  		{
  			String sampling_methodOption = null;
  			int RinseSampling=0;
  			ResultSet residueLimit = stmt.executeQuery("Select sampling_method,rinse_sampling_option from residue_limit where tenant_id='" + tenant_id + "'"); // get product name
  			while(residueLimit.next())
  			{
  				sampling_methodOption = residueLimit.getString(1);
  				RinseSampling = residueLimit.getInt(2);
  			}
  			
			// getEquipmentTrain(CurrenProductName,LowestoneExpectedL3);
			List<Object> setlist = new ArrayList<>();
			List<Float> equipSetTotalSF = new ArrayList<>();
			List<Object> equipSetNamelist = new ArrayList<>();
			
			int currentprodID = 0,currentproductsetcount = 0;
			ResultSet getCPID = stmt.executeQuery("SELECT id,set_count FROM product where name='"+ CurrenProductName + "' && tenant_id='" + tenant_id	+ "'"); // get product name id
			while (getCPID.next()) 
			{
				currentprodID = getCPID.getInt(1);
				currentproductsetcount = getCPID.getInt(2);
			}
			
			
			for (int i = 1; i <= currentproductsetcount; i++) {
				List<Integer> currentequipmentID = new ArrayList<>();

				// check if only equipmnet used in the product
				ResultSet getequipfromset = stmt.executeQuery("SELECT equipment_id FROM product_equipment_set_equipments where product_id='"+ currentprodID + "' && set_id ='" + i + "' && tenant_id='" + tenant_id	+ "'"); // get product name id
				while (getequipfromset.next()) {
					System.out.println("ony equipment selected");
					currentequipmentID.add(getequipfromset.getInt(1));
				}
				// check if only equipment group used in the product -current product

				List<Integer> eqgroupIDs = new ArrayList<>(); // if equipment group means - use the
																
				// List<Integer> equipmentgroup = new ArrayList<>();
				ResultSet getequipgrpfromset = stmt.executeQuery("SELECT group_id FROM product_equipment_set_groups where product_id="+ currentprodID + " && set_id =" + i + " && tenant_id='" + tenant_id + "'"); 
				while (getequipgrpfromset.next()) 
				{
					System.out.println("ony equipment group selected");
					eqgroupIDs.add(getequipgrpfromset.getInt(1)); // get group ID
				}
				for (int id : eqgroupIDs) // iterate group id one by one
				{
					int equipmentusedcount = 0;
					ResultSet geteqcountfromgrpID = stmt.executeQuery(
							"SELECT equipment_used_count FROM product_equipment_set_groups where product_id="
									+ currentprodID + " && group_id=" + id + " && tenant_id='" + tenant_id
									+ "'"); // get product name id
					while (geteqcountfromgrpID.next()) {
						equipmentusedcount = geteqcountfromgrpID.getInt(1);
					}
					ResultSet geteqfromgrpID = stmt.executeQuery(
							"SELECT equipment_id FROM equipment_group_relation where group_id=" + id
									+ " && tenant_id='" + tenant_id + "' order by sorted_id limit "
									+ equipmentusedcount + ""); // get product name id
					while (geteqfromgrpID.next()) {
						currentequipmentID.add(geteqfromgrpID.getInt(1));
					}
				}

				// end: check if only equipment group used in the product -current product
				// check if only equipment train used in the product -current product
				int gettrainID = 0;
				ResultSet getequiptrainIDfromset = stmt.executeQuery("SELECT train_id FROM product_equipment_set_train where product_id=" + currentprodID+ " && set_id =" + i + " && tenant_id='" + tenant_id + "'"); // get
				while (getequiptrainIDfromset.next()) 
				{
					System.out.println("ony equipment train selected");
					gettrainID = getequiptrainIDfromset.getInt(1);
				}
				// if train used only equipmeans used the below query
				ResultSet eqfromtrain = stmt.executeQuery("SELECT equipment_id FROM equipment_train_equipments where train_id="+ gettrainID + " && tenant_id='" + tenant_id + "'"); // get product name
				while (eqfromtrain.next()) 
				{
					currentequipmentID.add(eqfromtrain.getInt(1));
				}
				// if train used group means - use the below query
				Set<Integer> groupIDs = new HashSet<>();
				ResultSet eqfromtraingroup = stmt.executeQuery("SELECT group_id FROM equipment_train_group where train_id="	+ gettrainID + " && tenant_id='" + tenant_id + "'"); // get product name
				while (eqfromtraingroup.next()) 
				{
					groupIDs.add(eqfromtraingroup.getInt(1));
				}
				for (int id : groupIDs) // iterate group id one by one (from train)
				{
					int equipmentusedcount = 0;
					ResultSet geteqcountfromgrpID = stmt.executeQuery(
							"SELECT equipment_used_count FROM equipment_train_group where train_id="
									+ gettrainID + " && group_id=" + id + " && tenant_id='" + tenant_id
									+ "'"); // get product name id
					while (geteqcountfromgrpID.next()) {
						equipmentusedcount = geteqcountfromgrpID.getInt(1);
					}
					System.out.println("Train group count" + equipmentusedcount);
					ResultSet geteqfromgrpID = stmt.executeQuery(
							"SELECT equipment_id FROM equipment_group_relation where group_id=" + id
									+ " && tenant_id='" + tenant_id + "' order by sorted_id limit "
									+ equipmentusedcount + ""); // get product name id
					while (geteqfromgrpID.next()) {
						currentequipmentID.add(geteqfromgrpID.getInt(1));
					}
				}

				// end: check if only equipment train used in the product -current product
				List<String> eqnamelist = new ArrayList<>();
				float surfaceArea = 0, equipmentTotalSF = 0;
				for (Integer eqid : currentequipmentID) {
					// ------------->if equipment reused in equipment train
					Integer equipreusedID = 0, equipment_used_count = 0;
					ResultSet equipreused = stmt.executeQuery("SELECT equipment_id,equipment_used_count FROM train_equipment_count where train_id="+ gettrainID + " && equipment_id=" + eqid + " && tenant_id='"+ tenant_id + "'"); // get product name id
					if (equipreused != null) 
					{
						while (equipreused.next()) 
						{
							equipreusedID = equipreused.getInt(1);
							equipment_used_count = equipreused.getInt(2);
						}
						System.out.println("equipment_used_count" + equipment_used_count);
						// get eqiupment surface area (for reused equipment)

						float equipSF = 0;
						String equipreusedName = null;
						ResultSet equipreusedSf = stmt.executeQuery("SELECT surface_area,name FROM equipment where id="	+ equipreusedID + " && tenant_id='" + tenant_id + "'"); // get
						while (equipreusedSf.next()) 
						{
							equipSF = equipreusedSf.getFloat(1);
							equipreusedName = equipreusedSf.getString(2);
						}
						equipmentTotalSF = equipSF * equipment_used_count;
						System.out.println(" ------>equipment reused-" + equipmentTotalSF);
					} // <------------------ending if equipment reused in equipment train

					ResultSet getequipdetails = stmt.executeQuery("SELECT name,surface_area FROM equipment where id=" + eqid+ " && tenant_id='" + tenant_id + "'");
					while (getequipdetails.next()) 
					{
						if (equipment_used_count == 0) 
						{
							eqnamelist.add(getequipdetails.getString(1));
						} else {
							eqnamelist.add(getequipdetails.getString(1) + "("+ (equipment_used_count + 1) + ")");
						}
						surfaceArea = (surfaceArea + getequipdetails.getFloat(2) + equipmentTotalSF);
					}

				}
				System.out.println("Equipment Name Setlist-> " + eqnamelist);

				setlist.add(currentequipmentID);
				equipSetTotalSF.add(surfaceArea);
				equipSetNamelist.add(eqnamelist);
				System.out.println("Equipment Set List: " + setlist);
				System.out.println("Equipment Set equipSetTotalSF: " + equipSetTotalSF);
				System.out.println("Equipment Set equipSetNamelist: " + equipSetNamelist);

				// Get Train rinse volume for each set
				Integer trainID = null;
				ResultSet set = stmt.executeQuery("SELECT train_id FROM product_equipment_set_train where product_id=" + currentprodID+ " && set_id=" + i + " && tenant_id='" + tenant_id + "'");
				while (set.next()) 
				{
					trainID = set.getInt(1);
				}
				float TrainRinsevolume = 0;
				ResultSet eqtrain = stmt.executeQuery("SELECT rinse_volume FROM equipment_train where id=" + trainID+ " && tenant_id='" + tenant_id + "'");
				while (eqtrain.next()) 
				{
					TrainRinsevolume = eqtrain.getFloat(1);
				}

				// equipment rinse volume()
				float L4cTrain;
				String space = " ";
				if (ResidueCalculationwithCampaign.eqRinseVolume() == 0) // check rinset from univ setting or each equipment
				{
					L4cTrain = (LowestBioburdenL3fromAll * surfaceArea) / (TrainRinsevolume * 1000);
					Cell productname = sheet.getRow(TrainRow).getCell(25);
					productname.setCellValue(CurrenProductName); // print product name into

					String TrainequipmentName = "";
					for (String list : eqnamelist) {
						String comma = ",";
						TrainequipmentName = TrainequipmentName + list + comma;
					}
					Cell eqlist = sheet.getRow(TrainRow).getCell(26); // print set list in each cell
					eqlist.setCellValue(TrainequipmentName);

					Cell equipRinse = sheet.getRow(TrainRow).getCell(27);
					equipRinse.setCellValue(TrainRinsevolume);

					Cell trainL4c = sheet.getRow(TrainRow).getCell(28);
					trainL4c.setCellValue(L4cTrain);
				} else {
					L4cTrain = (float) ((LowestBioburdenL3fromAll * surfaceArea) / (ResidueCalculationwithCampaign.eqRinseVolume() * 1000));
					System.out.println("Train row"+TrainRow);
					Cell productname = sheet.getRow(TrainRow).getCell(25);
					productname.setCellValue(CurrenProductName); // print product name into
																				// excel

					String TrainequipmentName = "";
					for (String list : eqnamelist) {
						String comma = ",";
						TrainequipmentName = TrainequipmentName + list + comma;
					}
					Cell eqlist = sheet.getRow(TrainRow).getCell(26); // print set list in each cell
					eqlist.setCellValue(TrainequipmentName);

					Cell equipRinse = sheet.getRow(TrainRow).getCell(27);
					equipRinse.setCellValue(ResidueCalculationwithCampaign.eqRinseVolume());

					Cell trainL4c = sheet.getRow(TrainRow).getCell(28);
					trainL4c.setCellValue(L4cTrain);
				}

				// L4c Train Result (Opening Actual actual L4c Train Result)
				float actualTrainL4c = 0;
				ResultSet actualTrainresult = stmt.executeQuery("SELECT l4c FROM microbial_bioburden_equipment_results where product_id= "+ currentprodID + " && train_id="+ trainID + " && tenant_id='" + tenant_id + "'");
				while (actualTrainresult.next()) 
				{
					actualTrainL4c = actualTrainresult.getFloat(1);
				}

				if (actualTrainL4c == 0) {
					Cell actualL4c = sheet.getRow(TrainRow).getCell(29);
					actualL4c.setCellValue("NA");
				} else {
					Cell actualL4c = sheet.getRow(TrainRow).getCell(29);
					actualL4c.setCellValue(actualTrainL4c);
				}

				// L4c Train Result status
				if (sampling_methodOption.equals("1,2") && RinseSampling == 2) // if rinse enabled in
																				// sampling
				{
					// check expected L4c and actual L4c
					if (Utils.toOptimizeDecimalPlacesRoundedOff(L4cTrain).equals(Utils.toOptimizeDecimalPlacesRoundedOff(actualTrainL4c))) {
						Cell verify_result = sheet.getRow(TrainRow).getCell(30);
						verify_result.setCellValue("Pass");
						verify_result.setCellStyle(Utils.style(workbook, "Pass")); // for print green
																					// font
					} else {
						Cell verify_result = sheet.getRow(TrainRow).getCell(30);
						verify_result.setCellValue("Fail");
						verify_result.setCellStyle(Utils.style(workbook, "Fail")); // for print red font
					}
				} // closing L4c Train result Status
				TrainRow++;
			} // Closing no of set count presented in the current product for loop
			TrainRow++;
			// Ending actual result L4c
  		}
  		
  		
  		
  		
  		
  	// Write output and close workbook
  		public static void writeTooutputFile(Workbook workbook) throws IOException {
  			try {
  				FileOutputStream outFile = new FileOutputStream(new File(Constant.EXCEL_PATH_Result));
  				workbook.write(outFile);
  				outFile.close();
  			} catch (Exception e) {
  				throw e;
  			}
  		}
  		
  
	
  }
  
  
  
  
