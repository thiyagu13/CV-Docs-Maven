package com.eDocs.residueCalculation;

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
import org.apache.poi.ss.usermodel.DataFormatter;
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
public class ResidueCalculation {
	
	public boolean no_default = false;
	public double default_l1_val;
	public double default_l1l3_l1;
	public double default_l1l3_l3;
	public double default_l3_val,default_l1_val1;
	public boolean default_l3 = false;
	public boolean default_l1 = false;
	public boolean default_l1_l3 = false;
	
		
	static String tenant_id= Constant.tenant_id;
	public int limitDetermination() throws ClassNotFoundException, SQLException
	{
		int LimitDeterminationOption = 0;
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();// Create Statement Object
		ResultSet LimitDetermination = stmt.executeQuery("SELECT basis_of_limit_determination FROM residue_limit where tenant_id='"+tenant_id+"'");
		while (LimitDetermination.next()) 
		{
			LimitDeterminationOption = LimitDetermination.getInt(1);
		}
		System.out.println("LimitDeterminationOption-->"+LimitDeterminationOption);
		connection.close();
		return LimitDeterminationOption;
	}

	
	//default limit option
	public void defaultValueSet(String CurrenProductName) throws ClassNotFoundException, SQLException, IOException, InterruptedException	{
		
		if(Default.defaultmethod().equalsIgnoreCase("No_Default")) {
			no_default = true;
			System.out.println("no_default  "+no_default);
		}else if(Default.defaultmethod().contains("Default_L1@@")) {
			String[] a = Default.defaultmethod().split("@@");
			default_l1_val = Double.parseDouble(a[1]);
			default_l1 = true;
			System.out.println("default_l1_val---->"+default_l1_val+"---->default_l1---->"+default_l1);
			
			System.out.println("limitDetermination-->"+limitDetermination());
			if(limitDetermination()==1) //grouping approach true and default l1 - it will apply
			{
				if(Default.groupingApproachDefaultL1(CurrenProductName)==true)
				{
					default_l1_val = default_l1_val / Default.getMaxDose(CurrenProductName);
					System.out.println("default_l1_val---->"+default_l1_val);
				}
			}
			
			
		}else if(Default.defaultmethod().contains("Default_L3@@")) {
			String[] a = Default.defaultmethod().split("@@");
			default_l3_val = Double.parseDouble(a[1]);
			default_l3 = true;
			System.out.println("default_l3_val---->"+default_l3_val+"---->default_l3---->"+default_l3);
			
		}else if(Default.defaultmethod().contains("Default_L1L3@@")) {
			System.out.println("Entry 4");
			String[] a = Default.defaultmethod().split("@@");
			default_l1l3_l1 = Double.parseDouble(a[1]);
			default_l1l3_l3 = Double.parseDouble(a[2]);
			default_l1_l3 = true;
			System.out.println("limitDetermination-->"+limitDetermination());
			if(limitDetermination()==1) //grouping approach true and default l1 - it will apply
			{
				if(Default.groupingApproachDefaultL1(CurrenProductName)==true)
				{
					default_l1l3_l1 = default_l1l3_l1 / Default.getMaxDose(CurrenProductName);
					System.out.println("default_l1l3_l1---->"+default_l1l3_l1);
				}
			}
			System.out.println("4: default_l1_val---->"+default_l1l3_l1+"---->default_l3---->"+default_l1l3_l3 +"Default L1 and L3 option: "+default_l1_l3);
		}
	}
	
	
	//Get swab area value from universal settings
	public double swabArea() throws ClassNotFoundException, SQLException {
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();// Create Statement Object
		ResultSet residueLimit = stmt.executeQuery("SELECT definded_for_each_equip_loc_for_surface_area_sampled_flag,value_for_same_products_for_surface_area_sampled FROM residue_limit where tenant_id='"+tenant_id+"'");
		while (residueLimit.next()) 
		{	
		int definded_for_each_equip_loc_for_surface_area_sampled_flag = residueLimit.getInt(1);
		if(definded_for_each_equip_loc_for_surface_area_sampled_flag==0)
		{
			swabSurfaceArea = residueLimit.getFloat(2);
		}
		}
		connection.close();
		return swabSurfaceArea;
	}
	
	//Get swab amount value from universal settings
	public double swabAmount() throws ClassNotFoundException, SQLException {
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();// Create Statement Object
		ResultSet residueLimit = stmt.executeQuery("SELECT definded_for_each_equip_loc_for_solvent_used_flag,value_for_same_products_for_solvent_used FROM residue_limit where tenant_id='"+tenant_id+"'");
		while (residueLimit.next()) 
		{
			int definded_for_each_equip_loc_for_solvent_used_flag = residueLimit.getInt(1);
		if(definded_for_each_equip_loc_for_solvent_used_flag==0)
		{
			swabAmount = residueLimit.getFloat(2);
		}
		}
		connection.close();
		return swabAmount;
		
	}
	String sampling_methodOption;
	int RinseSampling;
	//Get equipment rinse volume from universal settings
	public double eqRinseVolume() throws ClassNotFoundException, SQLException {//Get value from universal settings
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();// Create Statement Object
		ResultSet residueLimit = stmt.executeQuery("SELECT defined_for_each_equip_or_train_loc_flag,sampling_method,rinse_sampling_option,same_for_all_equip_value_for_rinse_volume FROM residue_limit where tenant_id='"+tenant_id+"'");
		while (residueLimit.next())																										  
		{	
			int defined_for_each_equip_or_train_loc_flag = residueLimit.getInt(1);
			sampling_methodOption = residueLimit.getString(2);
			RinseSampling = residueLimit.getInt(3);
			if(defined_for_each_equip_or_train_loc_flag==0)
			{
				rinsevolume = residueLimit.getFloat(4); 
			}
		}
		connection.close();
		return rinsevolume;
	}
	
	
//	WebDriver driver;
	
	@Test
	public void test() throws ClassNotFoundException, SQLException, IOException, InterruptedException
	{
		/*	
		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH); 
		XSSFSheet sheet = workbook.getSheet("ResidueCalculation");
		
		driver = new FirefoxDriver();
		// Open the application
		driver.get("http://192.168.1.45:8091/login");
		Thread.sleep(1000);
		driver.findElement(By.id("username")).sendKeys("admin");
		driver.findElement(By.id("password")).sendKeys("123456");
		Thread.sleep(3000);
		driver.findElement(By.id("loginsubmit")).click();
		Thread.sleep(1000);
		driver.get("http://192.168.1.45:8091/products");
		Thread.sleep(1000);
		driver.findElement(By.id("searchEquipment")).sendKeys("Test P1");
		Thread.sleep(1000);
		driver.findElement(By.xpath(".//*[@id='dLabel']/i")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(".//*[@id='datatable']/tbody/tr[1]/td[9]/div/ul/li[2]/a")).click(); //click calculation window
		Thread.sleep(1000);
		driver.findElement(By.xpath(".//*[@id='calculateProductForm']/div[2]/div[2]/div/div/label")).click(); // click manual calculation option
		Thread.sleep(1000);
		String title = "Test Calculation63";
		driver.findElement(By.name("title")).sendKeys(title);//title of calculation
		Thread.sleep(2000);
		WebElement limitselection = driver.findElement(By.id("limitSelection")); // select residue limit
		Select limit = new Select(limitselection);
		System.out.println("-----------");
		limit.selectByIndex(1);
		String getproductname1 = sheet.getRow(41).getCell(1).getStringCellValue();
		System.out.println("Name"+getproductname1);
		Thread.sleep(1000);
		
		WebElement selectproduct = driver.findElement(By.xpath(".//*[@id='routeAdminDiv']/div/div/span/span[1]/span/ul/li/input"));
		//WebElement selectproduct = driver.findElement(By.name("selectedProducts"));
		
		int row =41;
		for(int i = 0;i<=1000;i++)
		{
			String getproductname = sheet.getRow(row).getCell(1).getStringCellValue(); //get product name list from excel (for using calculation)
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
		//String gettitle = driver.findElement(By.className("notify-msg")).getText();
		Thread.sleep(500);
		//driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
		
		Set<Integer> j = new HashSet<>(); //to store no of digits for iterate calculation title
		for(int k=75;k<1000;k++)
		{
			j.add(k);
		}
		
		List<Integer> iteratecaltitle = new ArrayList<>();
		iteratecaltitle.addAll(j);
		
		System.out.println("iteratecaltitle-->"+iteratecaltitle);
		if(driver.findElements(By.className("notify-msg")).size()!=0 && driver.findElement(By.className("notify-msg")).getText().equalsIgnoreCase("Calculation '"+title+"' already exists!"))
		{
			for(Integer i:iteratecaltitle)
			{
			driver.findElement(By.cssSelector(".close.custom-notify-close")).click();
			WebElement caltitle1 = driver.findElement(By.name("title"));//title of calculation
			caltitle1.clear();
			caltitle1.sendKeys(title+i);
			driver.findElement(By.id("saveCalculateProduct")).click(); // Click calculation submit button
			Thread.sleep(500);
			try
			{
				//if(driver.findElement(By.xpath("html/body/div[18]/div/span"))!=null)
				System.out.println("Size----->"+driver.findElements(By.id("saveCalculateProduct")).size());
				if(driver.findElements(By.id("saveCalculateProduct")).size()!=0)
				{
					String title_duplicate = driver.findElement(By.className("notify-msg")).getText();
					System.out.println("title_duplicate: "+title_duplicate);
					driver.findElement(By.cssSelector(".close.custom-notify-close")).click(); //close the duplicate validation alert
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
  		
  		System.out.println("currentproductlist "+currentproductlist);
		List<String>  nextproductlist = new ArrayList<>(); //store product list
		nextproductlist.addAll(selectedproducts);
		System.out.println("nextproductlist "+nextproductlist);*/
		
		Set<String> selectedproducts = new HashSet<>();
		selectedproducts.add("Topical1");
		selectedproducts.add("Topical2");
		//selectedproducts.add("Inhalant");
	//	selectedproducts.add("S4");
		
		List<String>  currentproductlist = new ArrayList<>(); //store product list
  		currentproductlist.addAll(selectedproducts);
  		
		List<String>  nextproductlist = new ArrayList<>(); //store product list
		nextproductlist.addAll(selectedproducts);
		
		Thread.sleep(1000);
		//System.out.println("currentproductlist   "+currentproductlist);
		//System.out.println("nextproductlist   "+nextproductlist);
		calculation(currentproductlist, nextproductlist);
		
		//writeTooutputFile(workbook); // write output into work sheet
		Thread.sleep(10000);
		//driver.close();
	}
	
	
	//Solid,Liquid,Inhalant,Patch,Topical	
	
	double value_L1,value_L2,value_L3,Solid_Total_surface_area,maxDD,minBatch,Solid_Expec_Value_L2,Solid_Expec_Value_L3, Solid_Expec_Value_L4a, Solid_Expec_Value_L4b,Solid_Expec_Value_L1,swabSurfaceArea,swabAmount,rinsevolume
			,L4cEquipment;
	float L4cTrain=0;

	public void calculation(List<String> CurrenProduct,List<String> Nextprod) throws IOException, InterruptedException, SQLException, ClassNotFoundException 
	{
		
		System.out.println("CurrenProduct   "+CurrenProduct);
		System.out.println("Nextprod   "+Nextprod);
		
		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH); 
		XSSFSheet sheet = workbook.getSheet("ResidueCalculation");
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();// Create Statement Object
				//next product id // Execute the SQL Query. Store results in Result Set // While Loop to iterate through all data and print results
				int nextProdID=0;
								
				//Current product list
				List<String>  currentproductlist = new ArrayList<>();
		  		currentproductlist.addAll(CurrenProduct);
		  		//Next product list
		  		List<String>  nextproductlist = new ArrayList<>();
		  		nextproductlist.addAll(Nextprod);
	  		
				//List<Float> LowestExpectL3 = new ArrayList<>();
				int row=41,column=9; //excel row and column		
				int L4Row =41;int TrainRow = 41;
				Thread.sleep(10000);
		for(String CurrenProductName : currentproductlist) // Current product list
		{
				System.out.println("CurrenProductName-->"+CurrenProductName);
			
				int getprodID = 0,currentproductsetcount = 0,grouping_criteria_option=0,CurrentproductType=0;
				String cprodname = null,activename = null;
				
				//Get  Current product details
						ResultSet productID = stmt.executeQuery("Select id,name,grouping_criteria_option,set_count,product_type from product where name = '" + CurrenProductName + "' && tenant_id='"+tenant_id+"'"); // get product name id
							while (productID.next()) 
							{
								getprodID = productID.getInt(1);
								cprodname = productID.getString(2); // get name id from product table
								grouping_criteria_option= productID.getInt(3);
								currentproductsetcount = productID.getInt(4); 
								CurrentproductType = productID.getInt(5);
							} // closing for productID while loop 
							
				 // Get Actual Limit Result from db
			    System.out.println("Get current product ID: "+getprodID);
			    ResultSet prod_active_relation = stmt.executeQuery("SELECT * FROM product_active_ingredient_relation where product_id='" + getprodID + "' && tenant_id='"+tenant_id+"'");
				//get active multiple active id
				List<Integer> activelist = new ArrayList<>(); // get active list from above query
				while (prod_active_relation.next()) 
				{
				activelist.add(prod_active_relation.getInt(2));
				}
			
	if(limitDetermination()==2)//Start:  Lowest based on lowest amongst all actives within a product
	{
		for(Integer activeID:activelist) // iterate active presented in the current product
		{
					System.out.println("Active List----> "+activelist);
					// get active name ,prod name and print in excel
					 ResultSet active = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+ activeID + "' && tenant_id='"+tenant_id+"'");	 
						 while (active.next()) 
						 {
							// String activename = active.getString(2);
							  activename = active.getString(2);
							 if(limitDetermination()==2)
							 {
								 String space = " ";
								 //prodname+=space+activename; //print product with active name
								 System.out.println("Äctive name ------->"+cprodname+space+activename);
								 Cell ActiveName = sheet.getRow(row).getCell(3);
								 ActiveName.setCellValue(cprodname+space+activename); // print active name into excel
							 }else 
							 {
								 System.out.println("prodname name ------->"+cprodname);
								 Cell prodName1 = sheet.getRow(row).getCell(3);
								 prodName1.setCellValue(cprodname); // print product name into excel
							 }
						 }
				
				defaultValueSet(CurrenProductName);
				List<Float> LowestExpectL3 = new ArrayList<>();
				List<Float> LowestActualL3 = new ArrayList<>(); 
				
		for(String NextprodName : nextproductlist)  // Next product list
		{
			String LimitcalculationType = sheet.getRow(39).getCell(0).getStringCellValue();
			
			//if(CurrenProductName.equals(NextprodName) || LimitcalculationType.equalsIgnoreCase("Manual") ||CurrentproductType==2) // if same product (e.g P1 ->P1)
		if(CurrenProductName.equals(NextprodName) ||CurrentproductType==2 || LimitcalculationType.equalsIgnoreCase("Campaign")) 
		{
			//TO DO
			
				//value_L1 = 
				//System.out.println("-------------->Same Product");
				
		}else { // if other product (e.g P1 ->P2)
				
				System.out.println("prodName"+NextprodName);
				String nprodname = null;	
				ResultSet productdata = stmt.executeQuery("Select id,name,max_daily_dose,min_batch_size from product where name ='"+NextprodName+"' && tenant_id='"+tenant_id+"' "); // get next prod name from excel and find out in db
				while (productdata.next()) {	nextProdID = productdata.getInt(1); nprodname = productdata.getString(2); maxDD = productdata.getFloat(3); minBatch = productdata.getFloat(4);   }

				
				String productType = sheet.getRow(39).getCell(1).getStringCellValue();
				System.out.println("productType--->"+productType);
				if(productType.equals("Solid")|| productType.equals("Liquid")||productType.equals("Inhalant"))
				{
					System.out.println("activeID--->"+activeID);
					value_L1 = L0.L0forSOLID(activeID, CurrenProductName) / maxDD;
					Cell Solid_expec_Value_L0_print = sheet.getRow(row).getCell(5); 
					Solid_expec_Value_L0_print.setCellValue(L0.L0forSOLID(activeID,CurrenProductName));
				}
				
				//if product is Transdermal Patch
				if(productType.equals("Patch"))
				{
					System.out.println("activeID--->"+activeID);
					value_L1 = (L0.L0forPatch(activeID, CurrenProductName)*1000) / maxDD;
					Cell Solid_expec_Value_L0_print = sheet.getRow(row).getCell(5); 
					Solid_expec_Value_L0_print.setCellValue(L0.L0forPatch(activeID,CurrenProductName));
				}
				//if product is Topical - Option2
				if(productType.equals("Topical") && grouping_criteria_option==2)
				{
					System.out.println("activeID--->"+activeID);
					value_L1 = (L0.L0forTOPICALoption2(activeID, CurrenProductName)*1000) / maxDD;
					Cell Solid_expec_Value_L0_print = sheet.getRow(row).getCell(5); 
					Solid_expec_Value_L0_print.setCellValue(L0.L0forTOPICALoption2(activeID,CurrenProductName));
				}
				//if product is Topical - Option1
				if(productType.equals("Topical") && grouping_criteria_option==1)
				{
					System.out.println("activeID--->"+activeID);
					value_L1 = L0.L0forTOPICALoption1(activeID, CurrenProductName) / maxDD;
					Cell Solid_expec_Value_L0_print = sheet.getRow(row).getCell(5); 
					Solid_expec_Value_L0_print.setCellValue("NA");
				}
				
				/*if(limitDetermination()==2)
				{
					System.out.println("activeID--->"+activeID);
					value_L1 = L0.L0forSOLID(activeID, CurrenProductName) / maxDD;
					Cell Solid_expec_Value_L0_print = sheet.getRow(row).getCell(5); 
					Solid_expec_Value_L0_print.setCellValue(L0.L0forSOLID(activeID,CurrenProductName)); // print expected L0 result into excel
				}
				if(limitDetermination()==1)
				{
					System.out.println("grouping approach");
					value_L1 = L0.groupingApproach_L0(CurrenProductName) / maxDD;
					Cell Solid_expec_Value_L0_print = sheet.getRow(row).getCell(5); 
					Solid_expec_Value_L0_print.setCellValue(L0.groupingApproach_L0(CurrenProductName)); // print expected L0 result into excel
				}*/
				value_L2 = value_L1 * minBatch * 1000 ; // Calculated L2 Value
				
				//find surface area option in residue limit whether shared or lowest
				int sharedORLowest=0;
				ResultSet surfaceAreaOption = stmt.executeQuery("Select l3_surface_area_option from residue_limit where tenant_id='"+tenant_id+"'"); // get equipment id
			    while (surfaceAreaOption.next()) 
			    {
			      sharedORLowest =surfaceAreaOption.getInt(1);
			    }
			    if(sharedORLowest==0)
			    {
			    	System.out.println("SF shread");
			    	Solid_Total_surface_area =  SurfaceAreaValue.actualSharedbetween2(CurrenProductName,NextprodName); // Calculated L3 for actual shared
			    	//Cell printsurfaceareaUsed = sheet.getRow(28).getCell(column); 
			    	//printsurfaceareaUsed.setCellValue(SurfaceAreaValue.actualSharedbetween2(CurrenProductName,NextprodName)); // print surface area
			    }else
			    {
			    	System.out.println("SF lowest");
			       	Solid_Total_surface_area =  SurfaceAreaValue.lowestTrainbetween2(CurrenProductName,NextprodName); // Calculated L3 for lowest train between two
			     	//Cell printsurfaceareaUsed = sheet.getRow(28).getCell(column); 
			    	//printsurfaceareaUsed.setCellValue(Solid_Total_surface_area);
			    }
			    value_L3 = value_L2 / Solid_Total_surface_area;
				
		if(no_default) // No Default limit
		{	
			no_defaultMethod();
		}
		if(default_l1) // Default limit for L1 value
		{
			defaultL1Method();
		}
		if(default_l3) // Default limit for L3 value
		{
			defaultL3Method();
		}
		if(default_l1_l3)// Default limit for L1 and L3
		{	
			defaultL1L3Method();
		} 
		Cell nextprodname = sheet.getRow(row).getCell(4); 
		nextprodname.setCellValue(nprodname ); // print next product name
		Cell Solid_expec_Value_L1_print = sheet.getRow(row).getCell(6); 
		Solid_expec_Value_L1_print.setCellValue(Solid_Expec_Value_L1 ); // print expected L0 result into excel
		Cell Solid_expec_Value_L2_print = sheet.getRow(row).getCell(7); 
		Solid_expec_Value_L2_print.setCellValue(Solid_Expec_Value_L2); // print expected L2 result into excel
		Cell Solid_expec_Value_L3_print = sheet.getRow(row).getCell(8); 
		Solid_expec_Value_L3_print.setCellValue(Solid_Expec_Value_L3); // print expected L3 result into excel
		System.out.println("Expected L1: "+Solid_Expec_Value_L1);
		System.out.println("Expected L2: "+Solid_Expec_Value_L2);
		System.out.println("Expected L3: "+Solid_Expec_Value_L3);
		LowestExpectL3.add((float) Solid_Expec_Value_L3); // get all Expected L3
				
		// Get Actual Result from DB
			 System.out.println("Prouct id: "+getprodID +" Active id: "+activeID); // current product id and active id
			
			 int actualnextProdID = 0;
			 int actualresultrow = 41; 
				 float ActualL0Result = 0,ActualL1Result = 0,ActualL2Result = 0,ActualL3Result = 0;
				 ResultSet nextproductdata = stmt.executeQuery("Select * from product where name ='"+NextprodName+"' && tenant_id='"+tenant_id+"' "); // get next prod name from excel and find out in db
					while (nextproductdata.next()) {	actualnextProdID = nextproductdata.getInt(1);    }
					
					System.out.println("actualnextProdID"+actualnextProdID);
					ResultSet prod_cal = stmt.executeQuery("SELECT l0,l1,l2,l3 FROM product_calculation where product_id= '" + getprodID + "'&& next_prod_ids='"+  actualnextProdID+ "' && active_ingredient_id='"+activeID+ "' && tenant_id='"+tenant_id+"'");
					//While Loop to iterate through all data and print results
					while (prod_cal.next()) {
						 ActualL0Result = prod_cal.getFloat(1); ActualL1Result = prod_cal.getFloat(2); ActualL2Result = prod_cal.getFloat(3); ActualL3Result = prod_cal.getFloat(4);
											}
					// Print Actual result to excel
					if(ActualL0Result==0)
							{
							Cell print_actual_L0 = sheet.getRow(row).getCell(9); 
							print_actual_L0.setCellValue("NA"); // print actual L0 result into excel
							}else {
							Cell print_actual_L0 = sheet.getRow(row).getCell(9); 
							print_actual_L0.setCellValue(ActualL0Result); // print actual L0 result into excel
							}			
					if(ActualL1Result==0)
					{
						Cell print_actual_L1 = sheet.getRow(row).getCell(10); 
						print_actual_L1.setCellValue("NA"); // print actual L1 result into excel
					}else {
						Cell print_actual_L1 = sheet.getRow(row).getCell(10); 
						print_actual_L1.setCellValue(ActualL1Result); // print actual L1 result into excel
					}		
					if(ActualL2Result==0)
					{
						Cell print_actual_L2 = sheet.getRow(row).getCell(11); 
						print_actual_L2.setCellValue("NA"); // print actual L2 result into excel
					}else {
						Cell print_actual_L2 = sheet.getRow(row).getCell(11); 
						print_actual_L2.setCellValue(ActualL2Result); // print actual L2 result into excel
					}
					if(ActualL3Result==0)
					{
						System.out.println("Zero");
						Cell print_actual_L3 = sheet.getRow(row).getCell(12); 
						print_actual_L3.setCellValue("NA"); // print actual L3 result into excel
					}else {
						System.out.println("Not Zero");
						Cell print_actual_L3 = sheet.getRow(row).getCell(12); 
						print_actual_L3.setCellValue(ActualL3Result); // print actual L3 result into excel
					}
					if(ActualL3Result!=0) {
					LowestActualL3.add((float) ActualL3Result);	
					}
				
				if(ActualL3Result==0) // this condition for if actual result not lowest(if zero)
				{
					System.out.println("No Result");
				}
				else 
				{
					System.out.println("Expected: "+Solid_Expec_Value_L3);
					System.out.println("Actual: "+ActualL3Result);
					
					if(Utils.toOptimizeDecimalPlacesRoundedOff(Solid_Expec_Value_L3).equals(Utils.toOptimizeDecimalPlacesRoundedOff(ActualL3Result)))
					{
						Cell printlowestL3 = sheet.getRow(row).getCell(13);
						printlowestL3.setCellValue("Pass");
						printlowestL3.setCellStyle(Utils.style(workbook, "Pass")); // for print green font				
					}else
					{
						Cell printlowestL3 = sheet.getRow(row).getCell(13);
						printlowestL3.setCellValue("Fail");
						printlowestL3.setCellStyle(Utils.style(workbook, "Fail")); // for print red font
					}
				}
					
						
		row++;	
		column++;
		} //closing else loop (other product result loop)	
		}//closing next product iteration
		
		 
		//Expected Lowest L3 for current product iteration 
		float LowestoneExpectedL3 = Collections.min(LowestExpectL3);
		System.out.println("Expected Lowest ExpectL3: "+LowestoneExpectedL3);
		/*
		//Actual Lowest L3 for current product iteration
		float LowestoneActualResult = Collections.min(LowestActualL3);
		System.out.println("LowestoneActualResult: "+LowestoneActualResult);*/
			
		float SFArea = 0,rinsevolume=0,swabarea = 0,swabamount=0;
		String eqname = null;
	    
		    System.out.println("Current product Equipment ID--------->"+getEquipment(CurrenProductName));
		    
		    for (Integer equipmentID:getEquipment(CurrenProductName)) //get id from set
		    {
		    	String space =" ";
		    	Cell ActiveName = sheet.getRow(L4Row).getCell(16);
				ActiveName.setCellValue(cprodname+space+activename); // print active name into excel
		    	System.out.println("getprodID--->"+getprodID);
		    	ResultSet EquipID = stmt.executeQuery("Select name,surface_area,swab_area,swab_amount,rinse_volume from equipment where id= '" + equipmentID + "' && tenant_id='"+tenant_id+"'"); // get product name id
		    		// print
		    	 while(EquipID.next()) {  // print name and sf value from equipment table
		    	 eqname = EquipID.getString(1); // get name from database
				 SFArea = EquipID.getFloat(2); // get SF value from database
				 swabarea = EquipID.getFloat(3); // get SF value from database
				 swabamount = EquipID.getFloat(4);
				 rinsevolume = EquipID.getFloat(5); // get SF value from database
				
				 
				 System.out.println("swabArea-->"+swabArea());
				 if(swabArea()==0) //check swab area from uni setting or each equipment
				 {
					 Solid_Expec_Value_L4a =  LowestoneExpectedL3 * swabarea;
					 Cell equipswab = sheet.getRow(L4Row).getCell(19);//cell to print swab amount
					 equipswab.setCellValue(swabarea); 
					 System.out.println("Solid_Expec_Value_L4a: "+Solid_Expec_Value_L4a);
				 }else {
					 Solid_Expec_Value_L4a =  LowestoneExpectedL3 * swabArea();
					 Cell equipswab = sheet.getRow(L4Row).getCell(19);//cell to print swab amount
					 equipswab.setCellValue(swabArea()); 
					 System.out.println("Solid_Expec_Value_L4a" +Solid_Expec_Value_L4a);
					 }
				 //swab amount
				 if(swabAmount()==0) //check swab amount from univ setting or each equipment
				 {
					 Solid_Expec_Value_L4b =  Solid_Expec_Value_L4a/ swabamount;
					 Cell equipRinse = sheet.getRow(L4Row).getCell(20);//cell to print swab amount
					 equipRinse.setCellValue(swabamount); 
					 System.out.println("Solid_Expec_Value_L4b: "+Solid_Expec_Value_L4b);
				 }else {
					 Solid_Expec_Value_L4b =  Solid_Expec_Value_L4a/ swabAmount();
					 Cell equipRinse = sheet.getRow(L4Row).getCell(20);//cell to print swab amount
					 equipRinse.setCellValue(swabAmount()); 
					 System.out.println("Solid_Expec_Value_L4b: "+Solid_Expec_Value_L4b);
				 }
				// equipment rinse volume()
				 if(eqRinseVolume()==0) //check rinset from univ setting or each equipment
				 {
					 L4cEquipment = (LowestoneExpectedL3 * SFArea) /(rinsevolume * 1000);//Calculate L4c equipment value
					 Cell equipRinse = sheet.getRow(L4Row).getCell(21);//cell to print equipment rinse volume
					 equipRinse.setCellValue(rinsevolume); // print all the equipment rinse volume(used in the product) in excel
				 }else {
					 L4cEquipment = (LowestoneExpectedL3 * SFArea) /(eqRinseVolume() * 1000);//Calculate L4c equipment value
					 Cell equipRinse = sheet.getRow(L4Row).getCell(21);//cell to print equipment rinse volume 
					 equipRinse.setCellValue(eqRinseVolume()); // print all the equipment rinse volume(used in the product) in excel
					 System.out.println("L4cEquipment"+L4cEquipment);
				 }
		    	 // Print Expected L4a, L4b, L4c value
		    	 	Cell equipName = sheet.getRow(L4Row).getCell(17);//cell to print name 
		    		equipName.setCellValue(eqname); // print equipment name(used in the product) in excel
		    		Cell equipSF = sheet.getRow(L4Row).getCell(18);//cell to print equipment surface area 
		    		equipSF.setCellValue(SFArea); // print all the equipment surface area(used in the product) in excel
		    		Cell L4aEquip = sheet.getRow(L4Row).getCell(22);//cell to print L4a value 
		    		L4aEquip.setCellValue(Solid_Expec_Value_L4a); // print all the equipment surface area(used in the product) in excel
		    		Cell L4bEquip = sheet.getRow(L4Row).getCell(23);//cell to print L4b value
		    		L4bEquip.setCellValue(Solid_Expec_Value_L4b); // print all the equipment surface area(used in the product) in excel
		    		
		    		// check whether rinse enabled in universal settings
		    		System.out.println("sampling_methodOption"+sampling_methodOption);
		    		System.out.println("RinseSampling"+RinseSampling);
		    			if(sampling_methodOption.equals("1,2") && RinseSampling==1) // if rinse enabled in sampling
		    			{
		    				Cell L4cEquip = sheet.getRow(L4Row).getCell(24);
		    				L4cEquip.setCellValue(L4cEquipment); 
		    			}
		    			
		    }//closing ExpectedequipResult while loop 
		    			
		    	 
		    	 
		    	 
		 // Actual Result for L4a, L4b, L4c
		    			float Ac_L4a = 0,Ac_L4b = 0,Ac_L4c = 0;
		    					ResultSet ActualequipResult = stmt.executeQuery("SELECT l4a,l4b,l4c FROM product_calculation_equipment_results where product_id= '" + getprodID + "' && active_ingredient_id='"+  activeID+ "' && equipment_id='"+equipmentID+"' && tenant_id='"+tenant_id+"'");
		    		while (ActualequipResult.next()) 
		    		{
		    					 Ac_L4a = ActualequipResult.getFloat(1); 
		    					 Ac_L4b = ActualequipResult.getFloat(2);
		    					 Ac_L4c = ActualequipResult.getFloat(3);
		    			    System.out.println("L4a "+Ac_L4a+" L4b "+Ac_L4a+" L4c "+Ac_L4c);
		    				
		    			    if(Ac_L4a!=0)
		    			    {
		    			    	Cell L4aEquipactual = sheet.getRow(L4Row).getCell(25);//cell to print L4a value 
		    			    	L4aEquipactual.setCellValue(Ac_L4a); // print all the equipment surface area(used in the product) in excel
		    			    }else
		    			    {
		    			    	Cell L4aEquipactual = sheet.getRow(L4Row).getCell(25);//cell to print L4a value 
		    			    	L4aEquipactual.setCellValue("NA"); 
		    			    }
		    			    
		    			    if(Ac_L4b!=0)
		    			    {
		    			    	Cell L4bEquipactual = sheet.getRow(L4Row).getCell(26);//cell to print L4b value
		    			    	L4bEquipactual.setCellValue(Ac_L4b); // print all the equipment surface area(used in the product) in excel
		    			    }else
		    			    {
		    			    	Cell L4bEquipactual = sheet.getRow(L4Row).getCell(26);//cell to print L4b value
		    			    	L4bEquipactual.setCellValue("NA"); 
		    			    }
		    			    
		    	    		if(sampling_methodOption.equals("1,2")&& RinseSampling==1) // if rinse enabled in sampling
		        			{
		    	    			if(Ac_L4c!=0)
			    			    {
		    	    				Cell L4cEquipactual = sheet.getRow(L4Row).getCell(27);//cell to print L4b value
		    	    				L4cEquipactual.setCellValue(Ac_L4c); // print all the equipment surface area(used in the product) in excel
			    			    }
		    	    			else
		    	    			{
		    	    				Cell L4cEquipactual = sheet.getRow(L4Row).getCell(27);//cell to print L4b value
		    	    				L4cEquipactual.setCellValue("NA");
		    	    			}
		    			    }
		    	    		/*if(sampling_methodOption.equals("1,2") && RinseSampling==2) // if rinse enabled in sampling
			    			{
		  
		    	    			Cell L4cEquipactual = sheet.getRow(L4Row).getCell(27);//cell to print L4b value
		        				L4cEquipactual.setCellValue("NA");		        				
			    			}*/
		        			/*else 
		        			{
		        				Cell L4cEquipactual = sheet.getRow(L4Row).getCell(27);//cell to print L4b value
		        				L4cEquipactual.setCellValue("NA"); // print all the equipment surface area(used in the product) in excel 
		        			}*/
		    		}//closing ActualequipResult while loop  		
		    					
		    		if(sampling_methodOption.equals("1,2")&& RinseSampling==1) // if rinse enabled in sampling
        			{
		    					// check expected L4a,L4b,L4c and actual L4a,L4b,L4c
		    			
		    			
		    					double EL4a = sheet.getRow(L4Row).getCell(22).getNumericCellValue();
		    					double EL4b = sheet.getRow(L4Row).getCell(23).getNumericCellValue();
		    					double EL4c = sheet.getRow(L4Row).getCell(24).getNumericCellValue();
		    					double AL4a = sheet.getRow(L4Row).getCell(25).getNumericCellValue();
		    					double AL4b = sheet.getRow(L4Row).getCell(26).getNumericCellValue();
		    					double AL4c = sheet.getRow(L4Row).getCell(27).getNumericCellValue();
		    					System.out.println("---------------------------------------------------------------------------------------");
		    					System.out.println(Utils.toOptimizeDecimalPlacesRoundedOff(EL4a));
		    					System.out.println(Utils.toOptimizeDecimalPlacesRoundedOff(EL4b));
		    					System.out.println(Utils.toOptimizeDecimalPlacesRoundedOff(EL4c));
		    					System.out.println(Utils.toOptimizeDecimalPlacesRoundedOff(AL4a));
		    					System.out.println(Utils.toOptimizeDecimalPlacesRoundedOff(AL4b));
		    					System.out.println(Utils.toOptimizeDecimalPlacesRoundedOff(AL4c));
		    					
		    					if(Utils.toOptimizeDecimalPlacesRoundedOff(EL4a).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4a)) && 
		    							Utils.toOptimizeDecimalPlacesRoundedOff(EL4b).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4b)) &&
		    							Utils.toOptimizeDecimalPlacesRoundedOff(EL4c).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4c)))
		    					{
		    						Cell verify_result = sheet.getRow(L4Row).getCell(28);
		    						verify_result.setCellValue("Pass");
		    						verify_result.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
		    					}else
		    					{	
		    						Cell verify_result = sheet.getRow(L4Row).getCell(28);
		    						verify_result.setCellValue("Fail");
		    						verify_result.setCellStyle(Utils.style(workbook, "Fail")); // for print red font
		    					}
        			}
		    		if(sampling_methodOption.equals("1") || (sampling_methodOption.equals("1,2")&& RinseSampling==2)) // if rinse enabled in sampling
        			{
		    					// check expected L4a,L4b,L4c and actual L4a,L4b,L4c 	
		    					double EL4a = sheet.getRow(L4Row).getCell(22).getNumericCellValue();
		    					double EL4b = sheet.getRow(L4Row).getCell(23).getNumericCellValue();	    					
		    					double AL4a = sheet.getRow(L4Row).getCell(25).getNumericCellValue();
		    					double AL4b = sheet.getRow(L4Row).getCell(26).getNumericCellValue();		    					
		    					if(Utils.toOptimizeDecimalPlacesRoundedOff(EL4a).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4a)) && 
		    							Utils.toOptimizeDecimalPlacesRoundedOff(EL4b).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4b)))
		    					{
		    						Cell verify_result = sheet.getRow(L4Row).getCell(28);
		    						verify_result.setCellValue("Pass");
		    						verify_result.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
		    					}else
		    					{	
		    						Cell verify_result = sheet.getRow(L4Row).getCell(28);
		    						verify_result.setCellValue("Fail");
		    						verify_result.setCellStyle(Utils.style(workbook, "Fail")); // for print red font
		    					}
        			}	
		    					
		    	L4Row++;
		    	} //closing for equipment ID loop
		       L4Row++; // Leave one row for each product
		        
		  
		       
		       if(sampling_methodOption.equals("1,2") && RinseSampling==2) // if rinse enabled in sampling
   			{
   			
   				//getEquipmentTrain(CurrenProductName,LowestoneExpectedL3);		    	
   		        List<Object> setlist = new ArrayList<>();
   		        List<Float> equipSetTotalSF = new ArrayList<>();
   		        List<Object> equipSetNamelist = new ArrayList<>();
   		        
   		        	
   		        
   		        for (int i = 1; i <= currentproductsetcount; i++) 
   		        { 
   		        	List<Integer> currentequipmentID = new ArrayList<>();
   		        	 
   		 //check if only equipmnet used in the product
   		            ResultSet getequipfromset = stmt.executeQuery("SELECT equipment_id FROM product_equipment_set_equipments where product_id='" + getprodID + "' && set_id ='" + i + "' && tenant_id='"+tenant_id+"'"); // get product name id
   		            while (getequipfromset.next()) 
   		            {
   		                System.out.println("ony equipment selected");
   		                currentequipmentID.add(getequipfromset.getInt(1));
   		            }
   		 //check if only equipment group used in the product -current product
   		           
   		            List<Integer> eqgroupIDs = new ArrayList<>(); // if equipment  group means - use the below query
   		            // List<Integer> equipmentgroup = new ArrayList<>();
   		            ResultSet getequipgrpfromset = stmt.executeQuery("SELECT group_id FROM product_equipment_set_groups where product_id=" + getprodID + " && set_id =" + i + " && tenant_id='"+tenant_id+"'"); // get product name id
   		            while (getequipgrpfromset.next()) {
   		                System.out.println("ony equipment group selected");
   		                eqgroupIDs.add(getequipgrpfromset.getInt(1)); // get group ID
   		            }
   		            for (int id : eqgroupIDs) // iterate group id one by one 
   		            {
   		                int equipmentusedcount = 0;
   		                ResultSet geteqcountfromgrpID = stmt.executeQuery("SELECT equipment_used_count FROM product_equipment_set_groups where product_id=" + getprodID + " && group_id=" + id + " && tenant_id='"+tenant_id+"'"); // get product name id
   		                while (geteqcountfromgrpID.next()) 
   		                {
   		                    equipmentusedcount = geteqcountfromgrpID.getInt(1);
   		                }
   		                ResultSet geteqfromgrpID = stmt.executeQuery("SELECT equipment_id FROM equipment_group_relation where group_id=" + id + " && tenant_id='"+tenant_id+"' order by sorted_id limit " + equipmentusedcount + ""); // get product name id
   		                while (geteqfromgrpID.next()) 
   		                {
   		                    currentequipmentID.add(geteqfromgrpID.getInt(1));
   		                }
   		                //  currentequipmentID.addAll(equipmentgroup);
   		            }
   		            
   		//end: check if only equipment group used in the product -current product
   		//check if only equipment train used in the product -current product
   		            int gettrainID = 0;
   		            ResultSet getequiptrainIDfromset = stmt.executeQuery("SELECT train_id FROM product_equipment_set_train where product_id=" + getprodID + " && set_id =" + i + " && tenant_id='"+tenant_id+"'"); // get product name id
   		            while (getequiptrainIDfromset.next()) {
   		                System.out.println("ony equipment train selected");
   		                gettrainID = getequiptrainIDfromset.getInt(1);
   		            }
   		            // if train used only equipmeans used the below query
   		            ResultSet eqfromtrain = stmt.executeQuery("SELECT equipment_id FROM equipment_train_equipments where train_id=" + gettrainID + " && tenant_id='"+tenant_id+"'"); // get product name id
   		            while (eqfromtrain.next()) {
   		                currentequipmentID.add(eqfromtrain.getInt(1));
   		            }
   		            // if train used group means - use the below query
   		            Set<Integer> groupIDs = new HashSet<>();
   		            ResultSet eqfromtraingroup = stmt.executeQuery("SELECT group_id FROM equipment_train_group where train_id=" + gettrainID + " && tenant_id='"+tenant_id+"'"); // get product name id
   		            while (eqfromtraingroup.next()) {
   		                groupIDs.add(eqfromtraingroup.getInt(1));
   		            }
   		            for (int id : groupIDs) // iterate group id one by one (from train)
   		            {
   		                //Set<Integer> equipID = new HashSet();
   		                int equipmentusedcount = 0;
   		                ResultSet geteqcountfromgrpID = stmt.executeQuery("SELECT equipment_used_count FROM equipment_train_group where train_id="+gettrainID+" && group_id=" + id + " && tenant_id='"+tenant_id+"'"); // get product name id
   		                while (geteqcountfromgrpID.next()) {
   		                    equipmentusedcount = geteqcountfromgrpID.getInt(1);
   		                }
   		                System.out.println("Train group count"+equipmentusedcount);
   		                ResultSet geteqfromgrpID = stmt.executeQuery("SELECT equipment_id FROM equipment_group_relation where group_id=" + id + " && tenant_id='"+tenant_id+"' order by sorted_id limit " + equipmentusedcount + ""); // get product name id
   		                while (geteqfromgrpID.next()) {
   		                    currentequipmentID.add(geteqfromgrpID.getInt(1));
   		                }
   		            }
   		            
   		//end: check if only equipment train used in the product -current product 
   		           
   		            List<String>  eqnamelist = new ArrayList<>();  		          
   	            	float surfaceArea = 0,equipmentTotalSF=0;
   		            for(Integer eqid:currentequipmentID)
   		            {
   		            	//------------->if equipment reused in equipment train
   		                Integer equipreusedID=0,equipment_used_count=0;   
   		                ResultSet equipreused = stmt.executeQuery("SELECT equipment_id,equipment_used_count FROM train_equipment_count where train_id=" + gettrainID + " && equipment_id="+eqid+" && tenant_id='"+tenant_id+"'"); // get product name id
   		                if(equipreused!=null)
   		                {
   		                	while (equipreused.next()) 
   		                	{
   		                		equipreusedID = equipreused.getInt(1);
   		                		equipment_used_count = equipreused.getInt(2);
   		                		// currentequipmentID.add(equipreused.getInt(2));
   		                	}
   		                	System.out.println("equipment_used_count"+equipment_used_count);
   		                //get eqiupment surface area (for reused equipment)
   		                	
   		                	float equipSF=0;
   		                	String equipreusedName =null;
   		                	ResultSet equipreusedSf = stmt.executeQuery("SELECT surface_area,name FROM equipment where id=" + equipreusedID + " && tenant_id='"+tenant_id+"'"); // get product name id
   		                	while (equipreusedSf.next()) 
   		                	{
   		                		equipSF = equipreusedSf.getFloat(1);
   		                		equipreusedName = equipreusedSf.getString(2);	                	  		                		
   		                	}
   		                
   		                equipmentTotalSF = equipSF * equipment_used_count;
   		                System.out.println(" ------>equipment reused-"+equipmentTotalSF);	
   		                } //<------------------ending if equipment reused in equipment train
   		                
   		            	
   		            	ResultSet getequipdetails = stmt.executeQuery("SELECT name,surface_area FROM equipment where id="+eqid+" && tenant_id='"+tenant_id+"'");
   		            	while(getequipdetails.next())
   		            	{
   		            		if(equipment_used_count==0)
   		                	{
   		            			eqnamelist.add(getequipdetails.getString(1));
   		                	}else 
   		                	{
   		                		eqnamelist.add(getequipdetails.getString(1)+"("+(equipment_used_count+1)+")");	
   		                	}
   		            		surfaceArea = (surfaceArea + getequipdetails.getFloat(2) + equipmentTotalSF);
   		            	}
   		            	
   		            }
   		            System.out.println("Equipment Name Setlist-> "+eqnamelist);
   		            
   		           // Cell eqnamesetlist = sheet.getRow(TrainRow).getCell(32); //print train equipments in single cell
					//eqnamesetlist.setCellValue(eqnamelist); 
					
   		            setlist.add(currentequipmentID);
   		            equipSetTotalSF.add(surfaceArea);
   		            equipSetNamelist.add(eqnamelist);
   		            System.out.println("Equipment Set List: "+setlist);
   		            System.out.println("Equipment Set equipSetTotalSF: "+equipSetTotalSF);
   		            System.out.println("Equipment Set equipSetNamelist: "+equipSetNamelist);
   		            
   		           
   			        // Get Train rinse volume for each set
   			        		Integer trainID = null ;
   			        	    ResultSet set = stmt.executeQuery("SELECT train_id FROM product_equipment_set_train where product_id=" + getprodID + " && set_id="+i+" && tenant_id='"+tenant_id+"'");
   				         	while(set.next())
   				         	{
   				         		trainID = set.getInt(1);       		
   				         	}
   			      
   				        float TrainRinsevolume=0;
   			 	        ResultSet eqtrain = stmt.executeQuery("SELECT rinse_volume FROM equipment_train where id="+trainID+" && tenant_id='"+tenant_id+"'");
   			         	while(eqtrain.next())
   			         	{
   			         		TrainRinsevolume = eqtrain.getFloat(1);       		
   			         	}
   			      
   			         	//Calculation getrinsevolume = new Calculation();
   			         	//getrinsevolume.eqRinseVolume();hjk
   			         	
   			         	//System.out.println("getrinsevolume.eqRinseVolume() "+getrinsevolume.eqRinseVolume());
   			         // equipment rinse volume()
   			         	String space =" ";
   						 if(eqRinseVolume()==0) //check rinset from univ setting or each equipment
   						 {
   							 L4cTrain = (LowestoneExpectedL3 *  surfaceArea) / (TrainRinsevolume * 1000) ;
   							 Cell productname = sheet.getRow(TrainRow).getCell(30);
   				    		 productname.setCellValue(cprodname+space+activename); // print product name into excel
   							   				    								
							 String TrainequipmentName ="" ;							 
					         	for(String list:eqnamelist)
					         	{
					         		String comma =",";
					         		TrainequipmentName = TrainequipmentName +list+comma;
					         	}					         
							 Cell eqlist = sheet.getRow(TrainRow).getCell(31); //print set list in each cell
							 eqlist.setCellValue(TrainequipmentName); 	
							 
   							 Cell equipRinse = sheet.getRow(TrainRow).getCell(32);
  							 equipRinse.setCellValue(TrainRinsevolume); 
  							 
   							 Cell trainL4c = sheet.getRow(TrainRow).getCell(33);
   							 trainL4c.setCellValue(L4cTrain); 		  							
   						 }else {
   							 L4cTrain = (float) ((LowestoneExpectedL3 *  surfaceArea) / (eqRinseVolume() * 1000)) ;
   							 Cell productname = sheet.getRow(TrainRow).getCell(30);
   				    		 productname.setCellValue(cprodname+space+activename); // print product name into excel
   									
							 String TrainequipmentName ="" ;							
					         	for(String list:eqnamelist)
					         	{
					         		String comma =",";
					         		TrainequipmentName = TrainequipmentName +list+comma;
					         	}					         
							 Cell eqlist = sheet.getRow(TrainRow).getCell(31); //print set list in each cell
							 eqlist.setCellValue(TrainequipmentName); 	
							 
   							 Cell equipRinse = sheet.getRow(TrainRow).getCell(32);
   							 equipRinse.setCellValue(eqRinseVolume()); 
   							 
   							 Cell trainL4c = sheet.getRow(TrainRow).getCell(33);
   							 trainL4c.setCellValue(L4cTrain); 		   							 
   						 }
   						 
   		       
   		        
//L4c Train Result (Opening Actual actual L4c Train Result)
 		       
   			 	    float actualTrainL4c = 0;
   			 	    System.out.println("============================================");
   			 	    System.out.println(getprodID);
   			 	System.out.println(activeID);
   			 System.out.println(trainID);
   			System.out.println(tenant_id);
   			    	ResultSet actualTrainresult = stmt.executeQuery("SELECT l4c FROM product_calculation_equipment_results where product_id= "+getprodID+" && active_ingredient_id="+activeID+"  && train_id="+trainID+" && tenant_id='"+tenant_id+"'");
   			       	while(actualTrainresult.next())
   			      	{
   			       		actualTrainL4c = actualTrainresult.getFloat(1);       		
   			       	}	
   			    		
   			       	if(actualTrainL4c==0)
   			       	{
   			       		Cell actualL4c = sheet.getRow(TrainRow).getCell(34);
   			       		actualL4c.setCellValue("NA"); 
   			       	}else
   			       	{
   			       		Cell actualL4c = sheet.getRow(TrainRow).getCell(34);
			       		actualL4c.setCellValue(actualTrainL4c); 
   			       	}
   			    	
   				
   			       	//L4c Train Result status
   			    if(sampling_methodOption.equals("1,2")&& RinseSampling==2) // if rinse enabled in sampling
     			{
		    					// check expected L4c and actual L4c 	
		    					
		    					if(Utils.toOptimizeDecimalPlacesRoundedOff(L4cTrain).equals(Utils.toOptimizeDecimalPlacesRoundedOff(actualTrainL4c)))
		    					{
		    						Cell verify_result = sheet.getRow(TrainRow).getCell(35);
		    						verify_result.setCellValue("Pass");
		    						verify_result.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
		    					}else
		    					{	
		    						Cell verify_result = sheet.getRow(TrainRow).getCell(35);
		    						verify_result.setCellValue("Fail");
		    						verify_result.setCellStyle(Utils.style(workbook, "Fail")); // for print red font
		    					}
     			} // closing L4c Train result Status
   			    
   			 
		 	       TrainRow++;
		 	      System.out.println("loop end------------------>");
   		        
   		     } // Closing no of set count presented in the current product for loop
   		        TrainRow++;
   		        System.out.println("Train end------------------>");
   		        
  // Ending actual result L4c     
   			} // L4c Train Result (closing Expected actual L4c Train Result)		           
		       
		       
		       
		       
				} // Closing current product active iteration
		
	}//End:  Lowest based on lowest amongst all actives within a product	
		
		
	

	if(limitDetermination()==1) // Start grouping approach
	{
				Cell prodName1 = sheet.getRow(row).getCell(3); // print current product name
				prodName1.setCellValue(cprodname); 	
		
				defaultValueSet(CurrenProductName);
				List<Float> LowestExpectL3 = new ArrayList<>();
				List<Float> LowestActualL3 = new ArrayList<>(); 
				
		for(String NextprodName : nextproductlist)  // Next product list
		{
		
			if(CurrenProductName.equals(NextprodName) || CurrentproductType==2) // if same product (e.g P1 ->P1)
			{
				System.out.println("-------------->Same Product");
				
			}else { // if other product (e.g P1 ->P2)
				
				System.out.println("prodName"+NextprodName);
				String nprodname = null; 	
				ResultSet productdata = stmt.executeQuery("Select id,name,max_daily_dose,min_batch_size from product where name ='"+NextprodName+"' && tenant_id='"+tenant_id+"' "); // get next prod name from excel and find out in db
				while (productdata.next()) {	nextProdID = productdata.getInt(1); nprodname = productdata.getString(2); maxDD = productdata.getFloat(3); minBatch = productdata.getFloat(4);   }
				
				
				
				String productType = sheet.getRow(39).getCell(1).getStringCellValue();
				System.out.println("productType--->"+productType);
				
				if(productType.equals("Solid")|| productType.equals("Liquid")||productType.equals("Inhalant"))
				{
					value_L1 = L0.groupingApproach_L0forSolid(CurrenProductName,row,sheet) / maxDD;
					Cell Solid_expec_Value_L0_print = sheet.getRow(row).getCell(5); 
					Solid_expec_Value_L0_print.setCellValue(L0.groupingApproach_L0forSolid(CurrenProductName,row,sheet));
				}
				//if product is Transdermal Patch
				if(productType.equals("Patch"))
				{
					value_L1 = (L0.groupingApproach_L0forPatch(CurrenProductName,row,sheet)*1000) / maxDD;
					Cell Solid_expec_Value_L0_print = sheet.getRow(row).getCell(5); 
					Solid_expec_Value_L0_print.setCellValue(L0.groupingApproach_L0forPatch(CurrenProductName,row,sheet));
				}
				
				//if product is Topical
				if(productType.equals("Topical"))
				{
					value_L1 = (L0.groupingApproach_L0forTOPICAL(CurrenProductName)*1000) / maxDD;
					Cell Solid_expec_Value_L0_print = sheet.getRow(row).getCell(5); 
					Solid_expec_Value_L0_print.setCellValue(L0.groupingApproach_L0forTOPICAL(CurrenProductName));
				}
				
				
				//value_L1 = L0.groupingApproach_L0(CurrenProductName) / maxDD;
				//Cell Solid_expec_Value_L0_print = sheet.getRow(row).getCell(5); 
				//Solid_expec_Value_L0_print.setCellValue(L0.groupingApproach_L0(CurrenProductName)); // print expected L0 result into excel
				//}
				value_L2 = value_L1 * minBatch * 1000 ; // Calculated L2 Value
				
				//find surface area option in residue limit whether shared or lowest
				int sharedORLowest=0;
				ResultSet surfaceAreaOption = stmt.executeQuery("Select l3_surface_area_option from residue_limit where tenant_id='"+tenant_id+"'"); // get equipment id
			    while (surfaceAreaOption.next()) 
			    {
			      sharedORLowest =surfaceAreaOption.getInt(1);
			    }
			    if(sharedORLowest==0)
			    {
			    	System.out.println("SF shread");
			    	Solid_Total_surface_area =  SurfaceAreaValue.actualSharedbetween2(CurrenProductName,NextprodName); // Calculated L3 for actual shared
			    	
			    }else
			    {
			    	System.out.println("SF lowest");
			       	Solid_Total_surface_area =  SurfaceAreaValue.lowestTrainbetween2(CurrenProductName,NextprodName); // Calculated L3 for lowest train between two
			     	
			    }
			    value_L3 = value_L2 / Solid_Total_surface_area;
				
		if(no_default) // No Default limit
		{	
			no_defaultMethod();
		}
		if(default_l1) // Default limit for L1 value
		{
			defaultL1Method();
		}
		if(default_l3) // Default limit for L3 value
		{
			defaultL3Method();
		}
		if(default_l1_l3)// Default limit for L1 and L3
		{	
			defaultL1L3Method();
		} 
		
		Cell nextprodname = sheet.getRow(row).getCell(4); 
		nextprodname.setCellValue(nprodname ); // print next product name
		Cell Solid_expec_Value_L1_print = sheet.getRow(row).getCell(6); 
		Solid_expec_Value_L1_print.setCellValue(Solid_Expec_Value_L1 ); // print expected L0 result into excel
		Cell Solid_expec_Value_L2_print = sheet.getRow(row).getCell(7); 
		Solid_expec_Value_L2_print.setCellValue(Solid_Expec_Value_L2); // print expected L2 result into excel
		Cell Solid_expec_Value_L3_print = sheet.getRow(row).getCell(8); 
		Solid_expec_Value_L3_print.setCellValue(Solid_Expec_Value_L3); // print expected L3 result into excel
		System.out.println("Expected L1: "+Solid_Expec_Value_L1);
		System.out.println("Expected L2: "+Solid_Expec_Value_L2);
		System.out.println("Expected L3: "+Solid_Expec_Value_L3);
		LowestExpectL3.add((float) Solid_Expec_Value_L3); // get all Expected L3
				
		// Get Actual Result from DB
			
			 int actualnextProdID = 0;
			 int actualresultrow = 41; 
				 float ActualL0Result = 0,ActualL1Result = 0,ActualL2Result = 0,ActualL3Result = 0;
				 ResultSet nextproductdata = stmt.executeQuery("Select * from product where name ='"+NextprodName+"' && tenant_id='"+tenant_id+"'"); // get next prod name from excel and find out in db
					while (nextproductdata.next()) {	actualnextProdID = nextproductdata.getInt(1);    }
					
					System.out.println("actualnextProdID"+actualnextProdID);
					ResultSet prod_cal = stmt.executeQuery("SELECT l0,l1,l2,l3 FROM product_calculation where product_id= '" + getprodID + "'&& next_prod_ids='"+  actualnextProdID+"' && tenant_id='"+tenant_id+"'"); /*"' && active_ingredient_id='"+activeID+ "'");*/
					//While Loop to iterate through all data and print results
					while (prod_cal.next()) {
						 ActualL0Result = prod_cal.getFloat(1); ActualL1Result = prod_cal.getFloat(2); ActualL2Result = prod_cal.getFloat(3); ActualL3Result = prod_cal.getFloat(4);
											}
					// Print Actual result to excel
					if(ActualL0Result==0)
							{
							Cell print_actual_L0 = sheet.getRow(row).getCell(9); 
							print_actual_L0.setCellValue("NA"); // print actual L0 result into excel
							}else {
							Cell print_actual_L0 = sheet.getRow(row).getCell(9); 
							print_actual_L0.setCellValue(ActualL0Result); // print actual L0 result into excel
							}			
					if(ActualL1Result==0)
					{
						Cell print_actual_L1 = sheet.getRow(row).getCell(10); 
						print_actual_L1.setCellValue("NA"); // print actual L1 result into excel
					}else {
						Cell print_actual_L1 = sheet.getRow(row).getCell(10); 
						print_actual_L1.setCellValue(ActualL1Result); // print actual L1 result into excel
					}		
					if(ActualL2Result==0)
					{
						Cell print_actual_L2 = sheet.getRow(row).getCell(11); 
						print_actual_L2.setCellValue("NA"); // print actual L2 result into excel
					}else {
						Cell print_actual_L2 = sheet.getRow(row).getCell(11); 
						print_actual_L2.setCellValue(ActualL2Result); // print actual L2 result into excel
					}
					if(ActualL3Result==0)
					{
						System.out.println("Zero");
						Cell print_actual_L3 = sheet.getRow(row).getCell(12); 
						print_actual_L3.setCellValue("NA"); // print actual L3 result into excel
					}else {
						System.out.println("Not Zero");
						Cell print_actual_L3 = sheet.getRow(row).getCell(12); 
						print_actual_L3.setCellValue(ActualL3Result); // print actual L3 result into excel
					}
					if(ActualL3Result!=0) {
					LowestActualL3.add((float) ActualL3Result);	
					}
				
				if(ActualL3Result==0) // this condition for if actual result not lowest(if zero)
				{
					System.out.println("No Result");
				}
				else 
				{
					if(Utils.toOptimizeDecimalPlacesRoundedOff(Solid_Expec_Value_L3).equals(Utils.toOptimizeDecimalPlacesRoundedOff(ActualL3Result)))
					{
						Cell printlowestL3 = sheet.getRow(row).getCell(13);
						printlowestL3.setCellValue("Pass");
						printlowestL3.setCellStyle(Utils.style(workbook, "Pass")); // for print green font				
					}else
					{
						Cell printlowestL3 = sheet.getRow(row).getCell(13);
						printlowestL3.setCellValue("Fail");
						printlowestL3.setCellStyle(Utils.style(workbook, "Fail")); // for print red font
					}
				}
					
						
		row++;	
		column++;
		} //closing else loop (other product result loop)	
		}//closing next product iteration
		
		 
		//Expected Lowest L3 for current product iteration 
		float LowestoneExpectedL3 = Collections.min(LowestExpectL3);
		System.out.println("Expected Lowest ExpectL3: "+LowestoneExpectedL3);
		/*
		//Actual Lowest L3 for current product iteration
		float LowestoneActualResult = Collections.min(LowestActualL3);
		System.out.println("LowestoneActualResult: "+LowestoneActualResult);*/
			
		float SFArea = 0,rinsevolume=0,swabarea = 0,swabamount=0;
		String eqname = null;
	    
		    System.out.println("Current product Equipment ID--------->"+getEquipment(CurrenProductName));
		    
		    for (Integer equipmentID:getEquipment(CurrenProductName)) //get id from set
		    {
		    	String space =" ";
		    	//Cell ActiveName = sheet.getRow(L4Row).getCell(16);
				//ActiveName.setCellValue(prodname+space+activename); // print active name into excel
				Cell currentpname = sheet.getRow(L4Row).getCell(16);
				currentpname.setCellValue(cprodname); // print product name into excel
				 
		    	System.out.println("getprodID--->"+getprodID);
		    	ResultSet EquipID = stmt.executeQuery("Select name,surface_area,swab_area,swab_amount,rinse_volume from equipment where id= '" + equipmentID + "' && tenant_id='"+tenant_id+"'"); // get product name id
		    		// print
		    	 while(EquipID.next()) {  // print name and sf value from equipment table
		    	 eqname = EquipID.getString(1); // get name from database
				 SFArea = EquipID.getFloat(2); // get SF value from database
				 swabarea = EquipID.getFloat(3); 
				 swabamount = EquipID.getFloat(4);
				 rinsevolume = EquipID.getFloat(5);
				 
				 System.out.println("swabArea-->"+swabArea());
				 if(swabArea()==0) //check swab area from uni setting or each equipment
				 {
					 Solid_Expec_Value_L4a =  LowestoneExpectedL3 * swabarea;
					 Cell equipswab = sheet.getRow(L4Row).getCell(19);//cell to print swab amount
					 equipswab.setCellValue(swabarea); 
					 System.out.println("Solid_Expec_Value_L4a: "+Solid_Expec_Value_L4a);
				 }else {
					 Solid_Expec_Value_L4a =  LowestoneExpectedL3 * swabArea();
					 Cell equipswab = sheet.getRow(L4Row).getCell(19);//cell to print swab amount
					 equipswab.setCellValue(swabArea()); 
					 System.out.println("Solid_Expec_Value_L4a" +Solid_Expec_Value_L4a);
					 }
				 //swab amount
				 if(swabAmount()==0) //check swab amount from univ setting or each equipment
				 {
					 Solid_Expec_Value_L4b =  Solid_Expec_Value_L4a/ swabamount;
					 Cell equipRinse = sheet.getRow(L4Row).getCell(20);//cell to print swab amount
					 equipRinse.setCellValue(swabamount); 
					 System.out.println("Solid_Expec_Value_L4b: "+Solid_Expec_Value_L4b);
				 }else {
					 Solid_Expec_Value_L4b =  Solid_Expec_Value_L4a/ swabAmount();
					 Cell equipRinse = sheet.getRow(L4Row).getCell(20);//cell to print swab amount
					 equipRinse.setCellValue(swabAmount()); 
					 System.out.println("Solid_Expec_Value_L4b: "+Solid_Expec_Value_L4b);
				 }
				// equipment rinse volume()
				 if(eqRinseVolume()==0) //check rinset from univ setting or each equipment
				 {
					 L4cEquipment = (LowestoneExpectedL3 * SFArea) /(rinsevolume * 1000);//Calculate L4c equipment value
					 Cell equipRinse = sheet.getRow(L4Row).getCell(21);//cell to print equipment rinse volume
					 equipRinse.setCellValue(rinsevolume); // print all the equipment rinse volume(used in the product) in excel
				 }else {
					 L4cEquipment = (LowestoneExpectedL3 * SFArea) /(eqRinseVolume() * 1000);//Calculate L4c equipment value
					 Cell equipRinse = sheet.getRow(L4Row).getCell(21);//cell to print equipment rinse volume 
					 equipRinse.setCellValue(eqRinseVolume()); // print all the equipment rinse volume(used in the product) in excel
					 System.out.println("L4cEquipment"+L4cEquipment);
				 }
		    	 // Print Expected L4a, L4b, L4c value
		    	 	Cell equipName = sheet.getRow(L4Row).getCell(17);//cell to print name 
		    		equipName.setCellValue(eqname); // print equipment name(used in the product) in excel
		    		Cell equipSF = sheet.getRow(L4Row).getCell(18);//cell to print equipment surface area 
		    		equipSF.setCellValue(SFArea); // print all the equipment surface area(used in the product) in excel
		    		Cell L4aEquip = sheet.getRow(L4Row).getCell(22);//cell to print L4a value 
		    		L4aEquip.setCellValue(Solid_Expec_Value_L4a); // print all the equipment surface area(used in the product) in excel
		    		Cell L4bEquip = sheet.getRow(L4Row).getCell(23);//cell to print L4b value
		    		L4bEquip.setCellValue(Solid_Expec_Value_L4b); // print all the equipment surface area(used in the product) in excel
		    		
		    		// check whether rinse enabled in universal settings
		    		System.out.println("sampling_methodOption"+sampling_methodOption);
		    		System.out.println("RinseSampling"+RinseSampling);
		    			if(sampling_methodOption.equals("1,2") && RinseSampling==1) // if rinse enabled in sampling
		    			{
		    				Cell L4cEquip = sheet.getRow(L4Row).getCell(24);
		    				L4cEquip.setCellValue(L4cEquipment); 
		    			}
		    			
		    }//closing ExpectedequipResult while loop 
		    			
		    	 
		    	 
		    	 
		 // Actual Result for L4a, L4b, L4c
		    			float Ac_L4a = 0,Ac_L4b = 0,Ac_L4c = 0;
		    					//ResultSet ActualequipResult = stmt.executeQuery("SELECT * FROM product_calculation_equipment_results where product_id= '" + getprodID + "' && active_ingredient_id='"+  activeID+ "' && equipment_id='"+equipmentID+"'");
		    					ResultSet ActualequipResult = stmt.executeQuery("SELECT l4a,l4b,l4c FROM product_calculation_equipment_results where product_id= '" + getprodID + "'&& equipment_id='"+equipmentID+"' && tenant_id='"+tenant_id+"'");
		    		while (ActualequipResult.next()) 
		    		{
		    					 Ac_L4a = ActualequipResult.getFloat(1); 
		    					 Ac_L4b = ActualequipResult.getFloat(2);
		    					 Ac_L4c = ActualequipResult.getFloat(3);
		    			    System.out.println("L4a "+Ac_L4a+" L4b "+Ac_L4a+" L4c "+Ac_L4c);
		    				
		    			    if(Ac_L4a!=0)
		    			    {
		    			    	Cell L4aEquipactual = sheet.getRow(L4Row).getCell(25);//cell to print L4a value 
		    			    	L4aEquipactual.setCellValue(Ac_L4a); // print all the equipment surface area(used in the product) in excel
		    			    }else
		    			    {
		    			    	Cell L4aEquipactual = sheet.getRow(L4Row).getCell(25);//cell to print L4a value 
		    			    	L4aEquipactual.setCellValue("NA"); 
		    			    }
		    			    
		    			    if(Ac_L4b!=0)
		    			    {
		    			    	Cell L4bEquipactual = sheet.getRow(L4Row).getCell(26);//cell to print L4b value
		    			    	L4bEquipactual.setCellValue(Ac_L4b); // print all the equipment surface area(used in the product) in excel
		    			    }else
		    			    {
		    			    	Cell L4bEquipactual = sheet.getRow(L4Row).getCell(26);//cell to print L4b value
		    			    	L4bEquipactual.setCellValue("NA"); 
		    			    }
		    			    
		    	    		if(sampling_methodOption.equals("1,2")&& RinseSampling==1) // if rinse enabled in sampling
		        			{
		    	    			if(Ac_L4c!=0)
			    			    {
		    	    				Cell L4cEquipactual = sheet.getRow(L4Row).getCell(27);//cell to print L4b value
		    	    				L4cEquipactual.setCellValue(Ac_L4c); // print all the equipment surface area(used in the product) in excel
			    			    }
		    	    			else
		    	    			{
		    	    				Cell L4cEquipactual = sheet.getRow(L4Row).getCell(27);//cell to print L4b value
		    	    				L4cEquipactual.setCellValue("NA");
		    	    			}
		    			    }
		    	    		/*if(sampling_methodOption.equals("1,2") && RinseSampling==2) // if rinse enabled in sampling
			    			{
		  
		    	    			Cell L4cEquipactual = sheet.getRow(L4Row).getCell(27);//cell to print L4b value
		        				L4cEquipactual.setCellValue("NA");		        				
			    			}*/
		        			/*else 
		        			{
		        				Cell L4cEquipactual = sheet.getRow(L4Row).getCell(27);//cell to print L4b value
		        				L4cEquipactual.setCellValue("NA"); // print all the equipment surface area(used in the product) in excel 
		        			}*/
		    		}//closing ActualequipResult while loop  		
		    					
		    		if(sampling_methodOption.equals("1,2")&& RinseSampling==1) // if rinse enabled in sampling
        			{
		    					// check expected L4a,L4b,L4c and actual L4a,L4b,L4c 	
		    					double EL4a = sheet.getRow(L4Row).getCell(22).getNumericCellValue();
		    					double EL4b = sheet.getRow(L4Row).getCell(23).getNumericCellValue();
		    					double EL4c = sheet.getRow(L4Row).getCell(24).getNumericCellValue();
		    					double AL4a = sheet.getRow(L4Row).getCell(25).getNumericCellValue();
		    					double AL4b = sheet.getRow(L4Row).getCell(26).getNumericCellValue();
		    					double AL4c = sheet.getRow(L4Row).getCell(27).getNumericCellValue();
		    					if(Utils.toOptimizeDecimalPlacesRoundedOff(EL4a).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4a)) && 
		    							Utils.toOptimizeDecimalPlacesRoundedOff(EL4b).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4b)) &&
		    							Utils.toOptimizeDecimalPlacesRoundedOff(EL4c).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4c)))
		    					{
		    						Cell verify_result = sheet.getRow(L4Row).getCell(28);
		    						verify_result.setCellValue("Pass");
		    						verify_result.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
		    					}else
		    					{	
		    						Cell verify_result = sheet.getRow(L4Row).getCell(28);
		    						verify_result.setCellValue("Fail");
		    						verify_result.setCellStyle(Utils.style(workbook, "Fail")); // for print red font
		    					}
        			}
		    		if(sampling_methodOption.equals("1") || (sampling_methodOption.equals("1,2")&& RinseSampling==2)) // if rinse enabled in sampling
        			{
		    					// check expected L4a,L4b,L4c and actual L4a,L4b,L4c 	
		    					double EL4a = sheet.getRow(L4Row).getCell(22).getNumericCellValue();
		    					double EL4b = sheet.getRow(L4Row).getCell(23).getNumericCellValue();	    					
		    					double AL4a = sheet.getRow(L4Row).getCell(25).getNumericCellValue();
		    					double AL4b = sheet.getRow(L4Row).getCell(26).getNumericCellValue();		    					
		    					if(Utils.toOptimizeDecimalPlacesRoundedOff(EL4a).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4a)) && 
		    							Utils.toOptimizeDecimalPlacesRoundedOff(EL4b).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4b)))
		    					{
		    						Cell verify_result = sheet.getRow(L4Row).getCell(28);
		    						verify_result.setCellValue("Pass");
		    						verify_result.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
		    					}else
		    					{	
		    						Cell verify_result = sheet.getRow(L4Row).getCell(28);
		    						verify_result.setCellValue("Fail");
		    						verify_result.setCellStyle(Utils.style(workbook, "Fail")); // for print red font
		    					}
        			}	
		    					
		    	L4Row++;
		    	} //closing for equipment ID loop
		       L4Row++; // Leave one row for each product
		        
		  
		       
		       if(sampling_methodOption.equals("1,2") && RinseSampling==2) // if rinse enabled in sampling
   				{
   			
   				//getEquipmentTrain(CurrenProductName,LowestoneExpectedL3);		    	
   		        List<Object> setlist = new ArrayList<>();
   		        List<Float> equipSetTotalSF = new ArrayList<>();
   		        List<Object> equipSetNamelist = new ArrayList<>();
   		        
   		        	
   		        
   		        for (int i = 1; i <= currentproductsetcount; i++) 
   		        { 
   		        	List<Integer> currentequipmentID = new ArrayList<>();
   		        	 
   		 //check if only equipmnet used in the product
   		            ResultSet getequipfromset = stmt.executeQuery("SELECT equipment_id FROM product_equipment_set_equipments where product_id='" + getprodID + "' && set_id ='" + i + "' && tenant_id='"+tenant_id+"'"); // get product name id
   		            while (getequipfromset.next()) 
   		            {
   		                System.out.println("ony equipment selected");
   		                currentequipmentID.add(getequipfromset.getInt(1));
   		            }
   		 //check if only equipment group used in the product -current product
   		           
   		            List<Integer> eqgroupIDs = new ArrayList<>(); // if equipment  group means - use the below query
   		            // List<Integer> equipmentgroup = new ArrayList<>();
   		            ResultSet getequipgrpfromset = stmt.executeQuery("SELECT group_id FROM product_equipment_set_groups where product_id=" + getprodID + " && set_id =" + i + " && tenant_id='"+tenant_id+"'"); // get product name id
   		            while (getequipgrpfromset.next()) {
   		                System.out.println("ony equipment group selected");
   		                eqgroupIDs.add(getequipgrpfromset.getInt(1)); // get group ID
   		            }
   		            for (int id : eqgroupIDs) // iterate group id one by one 
   		            {
   		                int equipmentusedcount = 0;
   		                ResultSet geteqcountfromgrpID = stmt.executeQuery("SELECT equipment_used_count FROM product_equipment_set_groups where product_id=" + getprodID + " && group_id=" + id + " && tenant_id='"+tenant_id+"'"); // get product name id
   		                while (geteqcountfromgrpID.next()) 
   		                {
   		                    equipmentusedcount = geteqcountfromgrpID.getInt(1);
   		                }
   		                ResultSet geteqfromgrpID = stmt.executeQuery("SELECT equipment_id FROM equipment_group_relation where group_id=" + id + " && tenant_id='"+tenant_id+"' order by sorted_id limit " + equipmentusedcount + ""); // get product name id
   		                while (geteqfromgrpID.next()) 
   		                {
   		                    currentequipmentID.add(geteqfromgrpID.getInt(1));
   		                }
   		                //  currentequipmentID.addAll(equipmentgroup);
   		            }
   		            
   		//end: check if only equipment group used in the product -current product
   		//check if only equipment train used in the product -current product
   		            int gettrainID = 0;
   		            ResultSet getequiptrainIDfromset = stmt.executeQuery("SELECT product_equipment_set_train FROM product_equipment_set_train where product_id=" + getprodID + " && set_id =" + i + " && tenant_id='"+tenant_id+"'"); // get product name id
   		            while (getequiptrainIDfromset.next()) {
   		                System.out.println("ony equipment train selected");
   		                gettrainID = getequiptrainIDfromset.getInt(1);
   		            }
   		            // if train used only equipmeans used the below query
   		            ResultSet eqfromtrain = stmt.executeQuery("SELECT equipment_id FROM equipment_train_equipments where train_id=" + gettrainID + " && tenant_id='"+tenant_id+"'"); // get product name id
   		            while (eqfromtrain.next()) {
   		                currentequipmentID.add(eqfromtrain.getInt(1));
   		            }
   		            // if train used group means - use the below query
   		            Set<Integer> groupIDs = new HashSet<>();
   		            ResultSet eqfromtraingroup = stmt.executeQuery("SELECT group_id FROM equipment_train_group where train_id=" + gettrainID + " && tenant_id='"+tenant_id+"'"); // get product name id
   		            while (eqfromtraingroup.next()) {
   		                groupIDs.add(eqfromtraingroup.getInt(1));
   		            }
   		            for (int id : groupIDs) // iterate group id one by one (from train)
   		            {
   		                //Set<Integer> equipID = new HashSet();
   		                int equipmentusedcount = 0;
   		                ResultSet geteqcountfromgrpID = stmt.executeQuery("SELECT equipment_used_count FROM equipment_train_group where train_id=" + gettrainID + " && group_id=" + id + " && tenant_id='"+tenant_id+"'"); // get product name id
   		                while (geteqcountfromgrpID.next()) {
   		                    equipmentusedcount = geteqcountfromgrpID.getInt(1);
   		                }
   		                System.out.println("Train group count"+equipmentusedcount);
   		                ResultSet geteqfromgrpID = stmt.executeQuery("SELECT equipment_id FROM equipment_group_relation where group_id=" + id + " && tenant_id='"+tenant_id+"' order by sorted_id limit " + equipmentusedcount + ""); // get product name id
   		                while (geteqfromgrpID.next()) {
   		                    currentequipmentID.add(geteqfromgrpID.getInt(1));
   		                }
   		            }
   		            
   		//end: check if only equipment train used in the product -current product 
   		           
   		            List<String>  eqnamelist = new ArrayList<>();  		          
   	            	float surfaceArea = 0,equipmentTotalSF=0;
   		            for(Integer eqid:currentequipmentID)
   		            {
   		            	//------------->if equipment reused in equipment train
   		                Integer equipreusedID=0,equipment_used_count=0;   
   		                ResultSet equipreused = stmt.executeQuery("SELECT equipment_id,equipment_used_count FROM train_equipment_count where train_id=" + gettrainID + " && equipment_id="+eqid+" && tenant_id='"+tenant_id+"'"); // get product name id
   		                if(equipreused!=null)
   		                {
   		                	while (equipreused.next()) 
   		                	{
   		                		equipreusedID = equipreused.getInt(1);
   		                		equipment_used_count = equipreused.getInt(2);
   		                		// currentequipmentID.add(equipreused.getInt(2));
   		                	}
   		                	System.out.println("equipment_used_count"+equipment_used_count);
   		                //get eqiupment surface area (for reused equipment)
   		                	
   		                	float equipSF=0;
   		                	String equipreusedName =null;
   		                	ResultSet equipreusedSf = stmt.executeQuery("SELECT surface_area,name FROM equipment where id=" + equipreusedID + " && tenant_id='"+tenant_id+"'"); // get product name id
   		                	while (equipreusedSf.next()) 
   		                	{
   		                		equipSF = equipreusedSf.getFloat(1);
   		                		equipreusedName = equipreusedSf.getString(2);	                	  		                		
   		                	}
   		                
   		                equipmentTotalSF = equipSF * equipment_used_count;
   		                System.out.println(" ------>equipment reused-"+equipmentTotalSF);	
   		                } //<------------------ending if equipment reused in equipment train
   		                
   		            	
   		            	ResultSet getequipdetails = stmt.executeQuery("SELECT name,surface_area FROM equipment where id="+eqid+" && tenant_id='"+tenant_id+"'");
   		            	while(getequipdetails.next())
   		            	{
   		            		if(equipment_used_count==0)
   		                	{
   		            			eqnamelist.add(getequipdetails.getString(1));
   		                	}else 
   		                	{
   		                		eqnamelist.add(getequipdetails.getString(1)+"("+(equipment_used_count+1)+")");	
   		                	}
   		            		surfaceArea = (surfaceArea + getequipdetails.getFloat(2) + equipmentTotalSF);
   		            	}
   		            	
   		            }
   		            System.out.println("Equipment Name Setlist-> "+eqnamelist);
   		            
   		           // Cell eqnamesetlist = sheet.getRow(TrainRow).getCell(32); //print train equipments in single cell
					//eqnamesetlist.setCellValue(eqnamelist); 
					
   		            setlist.add(currentequipmentID);
   		            equipSetTotalSF.add(surfaceArea);
   		            equipSetNamelist.add(eqnamelist);
   		            System.out.println("Equipment Set List: "+setlist);
   		            System.out.println("Equipment Set equipSetTotalSF: "+equipSetTotalSF);
   		            System.out.println("Equipment Set equipSetNamelist: "+equipSetNamelist);
   		            
   		           
   			        // Get Train rinse volume for each set
   			        		Integer trainID = null ;
   			        	    ResultSet set = stmt.executeQuery("SELECT train_id FROM product_equipment_set_train where product_id=" +getprodID+ " && set_id="+i+" && tenant_id='"+tenant_id+"'");
   				         	while(set.next())
   				         	{
   				         		trainID = set.getInt(1);       		
   				         	}
   			      
   				        float TrainRinsevolume=0;
   			 	        ResultSet eqtrain = stmt.executeQuery("SELECT rinse_volume FROM equipment_train where id="+trainID+" && tenant_id='"+tenant_id+"'");
   			         	while(eqtrain.next())
   			         	{
   			         		TrainRinsevolume = eqtrain.getFloat(1);       		
   			         	}
   			      
   			         	//Calculation getrinsevolume = new Calculation();
   			         	//getrinsevolume.eqRinseVolume();hjk
   			         	
   			         	//System.out.println("getrinsevolume.eqRinseVolume() "+getrinsevolume.eqRinseVolume());
   			         // equipment rinse volume()
   			         	String space =" ";
   						 if(eqRinseVolume()==0) //check rinset from univ setting or each equipment
   						 {
   							 L4cTrain = (LowestoneExpectedL3 *  surfaceArea) / (TrainRinsevolume * 1000) ;
   							 Cell productname = sheet.getRow(TrainRow).getCell(30);
   				    		 productname.setCellValue(cprodname); // print product name into excel
   							   				    		
   				    		 System.out.println("==========>eqnamelist"+eqnamelist);
							 String TrainequipmentName ="" ;							 
					         	for(String list:eqnamelist)
					         	{
					         		String comma =",";
					         		TrainequipmentName = TrainequipmentName +list+comma;
					         	}					         
							 Cell eqlist = sheet.getRow(TrainRow).getCell(31); //print set list in each cell
							 eqlist.setCellValue(TrainequipmentName); 	
							 
   							 Cell equipRinse = sheet.getRow(TrainRow).getCell(32);
  							 equipRinse.setCellValue(TrainRinsevolume); 
  							 
   							 Cell trainL4c = sheet.getRow(TrainRow).getCell(33);
   							 trainL4c.setCellValue(L4cTrain); 		  							
   						 }else {
   							 L4cTrain = (float) ((LowestoneExpectedL3 *  surfaceArea) / (eqRinseVolume() * 1000)) ;
   							 Cell productname = sheet.getRow(TrainRow).getCell(30);
   				    		 productname.setCellValue(cprodname); // print product name into excel
   									
							 String TrainequipmentName ="" ;							
					         	for(String list:eqnamelist)
					         	{
					         		String comma =",";
					         		TrainequipmentName = TrainequipmentName +list+comma;
					         	}					         
							 Cell eqlist = sheet.getRow(TrainRow).getCell(31); //print set list in each cell
							 eqlist.setCellValue(TrainequipmentName); 	
							 
   							 Cell equipRinse = sheet.getRow(TrainRow).getCell(32);
   							 equipRinse.setCellValue(eqRinseVolume()); 
   							 
   							 Cell trainL4c = sheet.getRow(TrainRow).getCell(33);
   							 trainL4c.setCellValue(L4cTrain); 		   							 
   						 }
   						 
   		       
   		        
//L4c Train Result (Opening Actual actual L4c Train Result)
 		       
   			 	    float actualTrainL4c = 0;
   			    	//ResultSet actualTrainresult = stmt.executeQuery("SELECT l4c FROM product_calculation_equipment_results where product_id= "+getprodID+" && active_ingredient_id="+activeID+"  && train_id="+trainID+"");
   			 	ResultSet actualTrainresult = stmt.executeQuery("SELECT l4c FROM product_calculation_equipment_results where product_id= "+getprodID+" && train_id="+trainID+" && tenant_id='"+tenant_id+"'");
   			       	while(actualTrainresult.next())
   			      	{
   			       		actualTrainL4c = actualTrainresult.getFloat(1);       		
   			       	}	
   			    		
   			       	if(actualTrainL4c==0)
   			       	{
   			       		Cell actualL4c = sheet.getRow(TrainRow).getCell(34);
   			       		actualL4c.setCellValue("NA"); 
   			       	}else
   			       	{
   			       		Cell actualL4c = sheet.getRow(TrainRow).getCell(34);
			       		actualL4c.setCellValue(actualTrainL4c); 
   			       	}
   			    	
   				
   			       	//L4c Train Result status
   			    if(sampling_methodOption.equals("1,2")&& RinseSampling==2) // if rinse enabled in sampling
     			{
		    					// check expected L4c and actual L4c 	
		    					
		    					if(Utils.toOptimizeDecimalPlacesRoundedOff(L4cTrain).equals(Utils.toOptimizeDecimalPlacesRoundedOff(actualTrainL4c)))
		    					{
		    						Cell verify_result = sheet.getRow(TrainRow).getCell(35);
		    						verify_result.setCellValue("Pass");
		    						verify_result.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
		    					}else
		    					{	
		    						Cell verify_result = sheet.getRow(TrainRow).getCell(35);
		    						verify_result.setCellValue("Fail");
		    						verify_result.setCellStyle(Utils.style(workbook, "Fail")); // for print red font
		    					}
     			} // closing L4c Train result Status
   			    
   			 
		 	       TrainRow++;
		 	      System.out.println("loop end------------------>");
   		        
   		     } // Closing no of set count presented in the current product for loop
   		        TrainRow++;
   		        System.out.println("Train end------------------>");
   		        
  // Ending actual result L4c     
   			} // L4c Train Result (closing Expected actual L4c Train Result)		           
		       
		       
		       
		       
			//	} // Closing current product active iteration
		
	}//End:  Lowest based on lowest amongst all actives within a product	
	/*<------------------------------------>	*/ // grouping approach
	
	
	
	
} // Closing current product iteration
			
		writeTooutputFile(workbook); // write output into work sheet
		connection.close();
		Thread.sleep(800);
		
		}// closing P1A1 calculation

	
	
	
	
	
	
	// method for when default limit option: no default option
	public void no_defaultMethod() throws ClassNotFoundException, SQLException
	{  
		System.out.println("No default true");
		Solid_Expec_Value_L1 = value_L1;
		Solid_Expec_Value_L2 = value_L2; // Calculated L2 Value 
		Solid_Expec_Value_L3 = value_L3; // Calculated L3 value
	}

	// method for when default limit option: Default L1
	public void defaultL1Method() throws ClassNotFoundException, SQLException
	{
		System.out.println("Defaut L1 Value true");
		if(value_L1<default_l1_val) // Low L1 value and high default l1 value
		{	// Formula for L2 
			Solid_Expec_Value_L1 = value_L1;
			Solid_Expec_Value_L2 = value_L2; // Roundoff
			Solid_Expec_Value_L3 = value_L3; // Roundoff
		}else {	// high L1 value and low default l1 value
			System.out.println("use Default L1 Value: " +default_l1_val);
			Solid_Expec_Value_L1 = default_l1_val;
			Solid_Expec_Value_L2 = default_l1_val * minBatch * 1000 ; // Calculated L2 Value for same product
			Solid_Expec_Value_L3 = Solid_Expec_Value_L2 / Solid_Total_surface_area; // Calculated L3 value
			 }
	}
	// method for when default limit option: Default L3
	public void defaultL3Method() throws ClassNotFoundException, SQLException
	{

		if(value_L3<default_l3_val) // Low L3 value and high default l3 value
		{
			Solid_Expec_Value_L1 = value_L1;
			Solid_Expec_Value_L2 = value_L2 ; // Calculated L2 Value for same product
			Solid_Expec_Value_L3 = value_L3; // Calculated L3 value
		}
		else {	// high L3 value and low default l3 value
			Solid_Expec_Value_L1 = value_L1;
			Solid_Expec_Value_L2 = value_L2; // Calculated L2 Value for same product
			Solid_Expec_Value_L3 = default_l3_val; // Calculated L3 value
		}
	}
	
	// method for when default limit option: Default L1 and L3
	public void defaultL1L3Method() throws ClassNotFoundException, SQLException
	{
		if(value_L1>default_l1l3_l1)
			{	// Formula for L2 
				Solid_Expec_Value_L1 = default_l1l3_l1;
				Solid_Expec_Value_L2  = default_l1l3_l1 * minBatch * 1000 ; // Calculated L2 Value for same product
				double valL3 = Solid_Expec_Value_L2 / Solid_Total_surface_area; // Calculated L3 value using default L1 value
				if(valL3<default_l1l3_l3)
				{
					Solid_Expec_Value_L3 = valL3;
				} else
				{	//double L3 = default_l1l3_l3 / Solid_Total_surface_area;
					Solid_Expec_Value_L3 = default_l1l3_l3; 
				}
			}
		if(value_L1<default_l1l3_l1)
		{		// Formula for L2 
			Solid_Expec_Value_L1 = value_L1;
			Solid_Expec_Value_L2 = value_L2; // Calculated L2 Value for same product
			double valL3 = Solid_Expec_Value_L2 / Solid_Total_surface_area;
			if(valL3<default_l1l3_l3)
			{   
				Solid_Expec_Value_L3 = value_L3;
			} else
			{	//double L3 = default_l1l3_l3 / Solid_Total_surface_area;
				Solid_Expec_Value_L3 = default_l1l3_l3; 
			}
		}		
	}
	
	
	
	//get current product equipment ID
	public static Set<Integer> getEquipment(String CurrenProductName) throws SQLException, ClassNotFoundException  
	{
        int currentproductID = 0, currentproductsetcount = 0;
        //database connection
        Connection connection = Utils.db_connect();
        Statement stmt = connection.createStatement();
        
        //current product equipment set
        // List<Integer> Currentsetcount = new ArrayList<>();
        ResultSet currentprod = stmt.executeQuery("SELECT id,set_count FROM product where name='" + CurrenProductName + "' && tenant_id='"+tenant_id+"'"); // get product name id
        while (currentprod.next()) {
            currentproductID = currentprod.getInt(1);
            currentproductsetcount = currentprod.getInt(2);
        }
        Set<Integer> currentequipmentID = new HashSet<>();
        for (int i = 1; i <= currentproductsetcount; i++) 
        { 
 //check if only equipmnet used in the product
            ResultSet getequipfromset = stmt.executeQuery("SELECT equipment_id FROM product_equipment_set_equipments where product_id='" + currentproductID + "' && set_id ='" + i + "' && tenant_id='"+tenant_id+"'"); // get product name id
            while (getequipfromset.next()) 
            {
                System.out.println("ony equipment selected");
                currentequipmentID.add(getequipfromset.getInt(1));
            }
 //check if only equipment group used in the product -current product
           
            List<Integer> eqgroupIDs = new ArrayList<>(); // if equipment  group means - use the below query
            // List<Integer> equipmentgroup = new ArrayList<>();
            ResultSet getequipgrpfromset = stmt.executeQuery("SELECT group_id FROM product_equipment_set_groups where product_id=" + currentproductID + " && set_id =" + i + " && tenant_id='"+tenant_id+"'"); // get product name id
            while (getequipgrpfromset.next()) {
                System.out.println("ony equipment group selected");
                eqgroupIDs.add(getequipgrpfromset.getInt(1)); // get group ID
            }
            for (int id : eqgroupIDs) // iterate group id one by one 
            {
                int equipmentusedcount = 0;
                ResultSet geteqcountfromgrpID = stmt.executeQuery("SELECT equipment_used_count FROM product_equipment_set_groups where product_id=" + currentproductID + " && group_id=" + id + " && tenant_id='"+tenant_id+"'"); // get product name id
                while (geteqcountfromgrpID.next()) 
                {
                    equipmentusedcount = geteqcountfromgrpID.getInt(1);
                }
                ResultSet geteqfromgrpID = stmt.executeQuery("SELECT equipment_id FROM equipment_group_relation where group_id=" + id + " && tenant_id='"+tenant_id+"' order by sorted_id limit " + equipmentusedcount + ""); // get product name id
                while (geteqfromgrpID.next()) 
                {
                    currentequipmentID.add(geteqfromgrpID.getInt(1));
                }
                //  currentequipmentID.addAll(equipmentgroup);
            }
            
//end: check if only equipment group used in the product -current product
//check if only equipment train used in the product -current product
            int gettrainID = 0;
            ResultSet getequiptrainIDfromset = stmt.executeQuery("SELECT train_id FROM product_equipment_set_train where product_id=" + currentproductID + " && set_id =" + i + " && tenant_id='"+tenant_id+"'"); // get product name id
            while (getequiptrainIDfromset.next()) {
                System.out.println("ony equipment train selected");
                gettrainID = getequiptrainIDfromset.getInt(1);
            }
            // if train used only equipmeans used the below query
            ResultSet eqfromtrain = stmt.executeQuery("SELECT equipment_id FROM equipment_train_equipments where train_id=" + gettrainID + " && tenant_id='"+tenant_id+"'"); // get product name id
            while (eqfromtrain.next()) {
                currentequipmentID.add(eqfromtrain.getInt(1));
            }
            // if train used group means - use the below query
            Set<Integer> groupIDs = new HashSet<>();
            ResultSet eqfromtraingroup = stmt.executeQuery("SELECT group_id FROM equipment_train_group where train_id=" + gettrainID + " && tenant_id='"+tenant_id+"'"); // get product name id
            while (eqfromtraingroup.next()) {
                groupIDs.add(eqfromtraingroup.getInt(1));
            }
            for (int id : groupIDs) // iterate group id one by one (from train)
            {
                //Set<Integer> equipID = new HashSet();
                int equipmentusedcount = 0;
                ResultSet geteqcountfromgrpID = stmt.executeQuery("SELECT equipment_used_count FROM equipment_train_group where train_id=" + gettrainID + " && group_id=" + id + " && tenant_id='"+tenant_id+"'"); // get product name id
                while (geteqcountfromgrpID.next()) {
                    equipmentusedcount = geteqcountfromgrpID.getInt(1);
                }
                System.out.println("Train group count"+equipmentusedcount);
                ResultSet geteqfromgrpID = stmt.executeQuery("SELECT equipment_id FROM equipment_group_relation where group_id=" + id + " && tenant_id='"+tenant_id+"' order by sorted_id limit " + equipmentusedcount + ""); // get product name id
                while (geteqfromgrpID.next()) {
                    currentequipmentID.add(geteqfromgrpID.getInt(1));
                }
            }
            
//end: check if only equipment train used in the product -current product    
        }

        System.out.println("currentequipmentID-->"+currentequipmentID);
        connection.close();
        return currentequipmentID;
        
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
	
	// Method for getting the lowest (minimum) value
	  public static double getMin(double[] inputArray){ 
	    double minValue = inputArray[0]; 
	    for(int i=1;i<inputArray.length;i++){ 
	      if(inputArray[i] < minValue){ 
	        minValue = inputArray[i]; 
	      } 
	    } 
	    return minValue; 
	  } 
	  
	 
}
