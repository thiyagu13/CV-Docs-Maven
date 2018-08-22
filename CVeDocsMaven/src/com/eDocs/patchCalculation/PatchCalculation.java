package com.eDocs.patchCalculation;

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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Utils;
import com.eDocs.residueCalculation.Default;
import com.eDocs.residueCalculation.SurfaceAreaValue;
import com.mysql.jdbc.Connection;
public class PatchCalculation {
	
	public boolean no_default = false;
	public double default_l1_val;
	public double default_l1l3_l1;
	public double default_l1l3_l3;
	public double default_l3_val,default_l1_val1;
	public boolean default_l3 = false;
	public boolean default_l1 = false;
	public boolean default_l1_l3 = false;
	
	
	
	
	
	
	
	public int limitDetermination() throws ClassNotFoundException, SQLException
	{
		int LimitDeterminationOption = 0;
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();// Create Statement Object
		ResultSet LimitDetermination = stmt.executeQuery("SELECT basis_of_limit_determination FROM residue_limit");
		while (LimitDetermination.next()) 
		{
			LimitDeterminationOption = LimitDetermination.getInt(1);
		}
		System.out.println("LimitDeterminationOption-->"+LimitDeterminationOption);
		connection.close();
		return LimitDeterminationOption;
	}

	
	//default limit option
	public void defaultValueSet(String CurrenProductName) throws ClassNotFoundException, SQLException	{
		if(Default.defaultmethod().equalsIgnoreCase("No_Default")) {
			no_default = true;
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
		ResultSet residueLimit = stmt.executeQuery("SELECT definded_for_each_equip_loc_for_surface_area_sampled_flag,value_for_same_products_for_surface_area_sampled FROM residue_limit");
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
	//todo
	//Get swab amount value from universal settings
	public double swabAmount() throws ClassNotFoundException, SQLException {
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();// Create Statement Object
		ResultSet residueLimit = stmt.executeQuery("SELECT definded_for_each_equip_loc_for_solvent_used_flag,value_for_same_products_for_solvent_used FROM residue_limit");
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
		ResultSet residueLimit = stmt.executeQuery("SELECT defined_for_each_equip_or_train_loc_flag,sampling_method,rinse_sampling_option,same_for_all_equip_value_for_rinse_volume FROM residue_limit");
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
	
	
	
	
	
	double value_L1,value_L2,value_L3,Solid_Total_surface_area,maxDD,minBatch,Solid_Expec_Value_L2,Solid_Expec_Value_L3, Solid_Expec_Value_L4a, Solid_Expec_Value_L4b,Solid_Expec_Value_L1,swabSurfaceArea,swabAmount,rinsevolume
			,L4cEquipment;

	
	/*public static String productName1 = "patch";
	public static String productName2 = "Transdermal Patch";
	public static String productName3 = "Patch1";
	public static String productName4 = "Patch2";*/
	
	@Test(priority=1) // Current ProductName with First Active
	@Parameters({"productName1","productName2","productName3","productName4"})
	public void P1_Active1_patch(String productName1,String productName2,String productName3,String productName4) throws IOException, InterruptedException, SQLException, ClassNotFoundException 
	{
		String CurrenProductName = productName1; // current product name
		defaultValueSet(CurrenProductName);
		//XSSFWorkbook workbook;
		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result); 
		XSSFSheet sheet = workbook.getSheet("Patch_Calculation");
		Connection connection = Utils.db_connect();
		Statement stmt = connection.createStatement();// Create Statement Object
				//next product id // Execute the SQL Query. Store results in Result Set // While Loop to iterate through all data and print results
				int nextProdID=0;
				
				 Set<String> productList = new HashSet<>(); 
				 productList.add(productName2);
				 productList.add(productName3);
				 productList.add(productName4);
				List<Float> LowestExpectL3 = new ArrayList<>();
				int row=42,column=9; //excel row and column
				
				for(String NextprodName : productList)
				{
				String nprodname = null;	
				System.out.println("prodName"+NextprodName);
				ResultSet productdata = stmt.executeQuery("Select * from product where name ='"+NextprodName+"' "); // get next prod name from excel and find out in db
				while (productdata.next()) {	nextProdID = productdata.getInt(1); nprodname = productdata.getString(2); maxDD = productdata.getFloat(8); minBatch = productdata.getFloat(9);   }

				
				
				
				if(limitDetermination()==2)
				{
				System.out.println("Lowest Limit");
				value_L1 = L0forpatch.calculate_P1_active1_L0(productName1, productName2, productName3, productName4) / maxDD;
				Cell Solid_expec_Value_L0_print = sheet.getRow(row).getCell(3); 
				Solid_expec_Value_L0_print.setCellValue(L0forpatch.calculate_P1_active1_L0(productName1, productName2, productName3, productName4)); // print expected L0 result into excel
				}
				if(limitDetermination()==1)
				{
				System.out.println("grouping approach");
				value_L1 = L0forpatch.groupingApproach_L0_p11(CurrenProductName) / maxDD;
				Cell Solid_expec_Value_L0_print = sheet.getRow(row).getCell(3); 
				Solid_expec_Value_L0_print.setCellValue(L0forpatch.groupingApproach_L0_p11(CurrenProductName)); // print expected L0 result into excel
				}
				value_L2 = value_L1 * minBatch * 1000 ; // Calculated L2 Value
				
				//find surface area option in residue limit whether shared or lowest
				int sharedORLowest=0;
				ResultSet surfaceAreaOption = stmt.executeQuery("Select * from residue_limit"); // get equipment id
			    while (surfaceAreaOption.next()) {
			      sharedORLowest =surfaceAreaOption.getInt(15);
			    	}
			    if(sharedORLowest==0)
			    {
			    	Solid_Total_surface_area =  SurfaceAreaValue.actualSharedbetween2(CurrenProductName,NextprodName); // Calculated L3 for actual shared
			    	Cell printsurfaceareaUsed = sheet.getRow(28).getCell(column); 
			    	printsurfaceareaUsed.setCellValue(SurfaceAreaValue.actualSharedbetween2(CurrenProductName,NextprodName)); // print surface area
			    }else
			    {
			    	Solid_Total_surface_area =  SurfaceAreaValue.lowestTrainbetween2(CurrenProductName,NextprodName); // Calculated L3 for lowest train between two
			    	Cell printsurfaceareaUsed = sheet.getRow(28).getCell(column); 
			    	printsurfaceareaUsed.setCellValue(SurfaceAreaValue.lowestTrainbetween2(CurrenProductName,NextprodName));
			    }
			    column++;
			    
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
		Cell nextprodname = sheet.getRow(row).getCell(2); 
		nextprodname.setCellValue(nprodname ); // print next product name
		Cell Solid_expec_Value_L1_print = sheet.getRow(row).getCell(4); 
		Solid_expec_Value_L1_print.setCellValue(Solid_Expec_Value_L1 ); // print expected L0 result into excel
		Cell Solid_expec_Value_L2_print = sheet.getRow(row).getCell(5); 
		Solid_expec_Value_L2_print.setCellValue(Solid_Expec_Value_L2); // print expected L2 result into excel
		Cell Solid_expec_Value_L3_print = sheet.getRow(row).getCell(6); 
		Solid_expec_Value_L3_print.setCellValue(Solid_Expec_Value_L3); // print expected L3 result into excel
		System.out.println("Expected L1: "+Solid_Expec_Value_L1);
		System.out.println("Expected L2: "+Solid_Expec_Value_L2);
		System.out.println("Expected L3: "+Solid_Expec_Value_L3);
		LowestExpectL3.add((float) Solid_Expec_Value_L3);
		row++;
		column++;
		}
			
		System.out.println("LowestExpectL3"+LowestExpectL3);
		float LowestoneExpectedL3 = Collections.min(LowestExpectL3);
		System.out.println("Min" +LowestoneExpectedL3);
		Cell Solid_expec_Value_L3_print = sheet.getRow(41).getCell(7); //print lowest L3 from iteration
		Solid_expec_Value_L3_print.setCellValue(LowestoneExpectedL3); // print expected L3 result into excel
				
				
		int getprodID = 0;
		float SFArea = 0,rinsevolume=0,swabarea = 0,swabamount=0;
		String eqname = null,prodname = null;
		
		//Get no of used Equipment in the Current product
		ResultSet productID = stmt.executeQuery("Select * from product where name = '" + CurrenProductName + "'"); // get product name id
			while (productID.next()) 
			{
				getprodID = productID.getInt(1);
				prodname = productID.getString(2); // get name id from product table
			} // closing for productID while loop 
			//equipment set id
			ResultSet productSetEqID = stmt.executeQuery("Select * from product_equipment_set_equipments where product_id='" + getprodID + "'"); // get equipment id
			Set<Integer> set = new HashSet<>(); // store multiple equipment id
		    while (productSetEqID.next()) {
		      set.add(productSetEqID.getInt(4));
		    	}
		    int i =81; //increment cell to print result
		    
		    for (Integer equipmentID:set) //get id from set
		    {
		    ResultSet EquipID = stmt.executeQuery("Select * from equipment where id= '" + equipmentID + "'"); // get product name id
		    		// print
		    	while(EquipID.next()) {  // print name and sf value from equipment table
		    	 eqname = EquipID.getString(9); // get name from database
				 SFArea = EquipID.getFloat(13); // get SF value from database
				 rinsevolume = EquipID.getFloat(17); // get SF value from database
				 swabarea = EquipID.getFloat(15); // get SF value from database
				 swabamount = EquipID.getFloat(16);
				 
				 if(swabArea()==0) //check swab area from uni setting or each equipment
				 {
					 Solid_Expec_Value_L4a =  LowestoneExpectedL3 * swabarea;
					 Cell equipRinse = sheet.getRow(i).getCell(3);//cell to print swab amount
					 equipRinse.setCellValue(swabarea); 
				 }else {
					 Solid_Expec_Value_L4a =  LowestoneExpectedL3 * swabArea();
					 Cell equipRinse = sheet.getRow(i).getCell(3);//cell to print swab amount
					 equipRinse.setCellValue(swabArea()); 
					 System.out.println("Solid_Expec_Value_L4a" +Solid_Expec_Value_L4a);
					 System.out.println("lowestL3"+LowestoneExpectedL3);
					 System.out.println("swabArea" +swabArea());
				 }
				 //swab amount
				 if(swabAmount()==0) //check swab amount from univ setting or each equipment
				 {
					 Solid_Expec_Value_L4b =  Solid_Expec_Value_L4a/ swabamount;
					 Cell equipRinse = sheet.getRow(i).getCell(4);//cell to print swab amount
					 equipRinse.setCellValue(swabamount); 
				 }else {
					 Solid_Expec_Value_L4b =  Solid_Expec_Value_L4a/ swabAmount();
					 Cell equipRinse = sheet.getRow(i).getCell(4);//cell to print swab amount
					 equipRinse.setCellValue(swabAmount()); 
				 }
				// equipment rinse volume()
				 if(eqRinseVolume()==0) //check rinset from univ setting or each equipment
				 {
					 L4cEquipment = (LowestoneExpectedL3 * SFArea) /(rinsevolume * 1000);//Calculate L4c equipment value
					 Cell equipRinse = sheet.getRow(i).getCell(5);//cell to print equipment rinse volume
					 equipRinse.setCellValue(rinsevolume); // print all the equipment rinse volume(used in the product) in excel
				 }else {
					 L4cEquipment = (LowestoneExpectedL3 * SFArea) /(eqRinseVolume() * 1000);//Calculate L4c equipment value
					 Cell equipRinse = sheet.getRow(i).getCell(5);//cell to print equipment rinse volume 
					 equipRinse.setCellValue(eqRinseVolume()); // print all the equipment rinse volume(used in the product) in excel
					 System.out.println("L4cEquipment"+L4cEquipment);
				 }
		    	 // Print Expected L4a, L4b, L4c value
		    	 	Cell equipName = sheet.getRow(i).getCell(1);//cell to print name 
		    		equipName.setCellValue(eqname); // print equipment name(used in the product) in excel
		    		Cell equipSF = sheet.getRow(i).getCell(2);//cell to print equipment surface area 
		    		equipSF.setCellValue(SFArea); // print all the equipment surface area(used in the product) in excel
		    		Cell L4aEquip = sheet.getRow(i).getCell(6);//cell to print L4a value 
		    		L4aEquip.setCellValue(Solid_Expec_Value_L4a); // print all the equipment surface area(used in the product) in excel
		    		Cell L4bEquip = sheet.getRow(i).getCell(7);//cell to print L4b value
		    		L4bEquip.setCellValue(Solid_Expec_Value_L4b); // print all the equipment surface area(used in the product) in excel
		    		
		    		// check whether rinse enabled in universal settings
		    		System.out.println("sampling_methodOption"+sampling_methodOption);
		    		System.out.println("RinseSampling"+RinseSampling);
		    			if(sampling_methodOption.equals("1,2") && RinseSampling==1) // if rinse enabled in sampling
		    			{
		    				Cell L4cEquip = sheet.getRow(i).getCell(8);
		    				L4cEquip.setCellValue(L4cEquipment); 
		    			}
		    			else {
		    				Cell L4cEquip = sheet.getRow(i).getCell(8);
		    				L4cEquip.setCellValue(0); 
		    				}
				 i++;
		    	} //closing equipmentID while loop 
		    	} //closing for loop
		    
		    // Get Actual Limit Result from db
		    System.out.println("Actual result productid: "+getprodID);
		    ResultSet prod_active_relation = stmt.executeQuery("SELECT * FROM product_active_ingredient_relation where product_id='" + getprodID + "'");
			//get active multiple active id
			List<Integer> activelist = new ArrayList<>(); // get active list from above query
			while (prod_active_relation.next()) 
			{
			activelist.add(prod_active_relation.getInt(2));
			}
				 // get active name ,prod name and print in excel
				 ResultSet active = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+ activelist.get(0) + "'");
				 {
					 while (active.next()) 
					 {
						 if(limitDetermination()==2)
						 {
						 String activename = active.getString(2);
						 //print active name to excel
						 System.out.println("ÄCtive name ------->"+activename);
						 Cell NameofActive = sheet.getRow(79).getCell(2);
						 NameofActive.setCellValue(activename); // print active name into excel
						 Cell ActiveName = sheet.getRow(41).getCell(1);
						 ActiveName.setCellValue(activename); // print active name into excel
						 }else {
							 System.out.println(" lowest loop");
							 
							 //print active name to excel
							 Cell prodName = sheet.getRow(79).getCell(2);
							 prodName.setCellValue(prodname); // print active name into excel
							 Cell prodName1 = sheet.getRow(41).getCell(1);
							 prodName1.setCellValue(prodname); // print active name into excel
						 }
					 }
				 }
				 //get product id
				 System.out.println("Prouct id: "+getprodID +" Active id: "+activelist.get(0));
				 //current product id 
				 List<Float> LowestActualL3 = new ArrayList<>(); 
				 int actualnextProdID = 0;
				 int actualresultrow = 42;
				 for(String NextprodNameactualresult : productList)
					{ 
					 float ActualL0Result = 1,ActualL1Result = 1,ActualL2Result = 1,ActualL3Result = 1;
					 ResultSet productdata = stmt.executeQuery("Select * from product where name ='"+NextprodNameactualresult+"' "); // get next prod name from excel and find out in db
						while (productdata.next()) {	actualnextProdID = productdata.getInt(1);    }
						
						System.out.println("actualnextProdID"+actualnextProdID);
						ResultSet prod_cal = stmt.executeQuery("SELECT * FROM product_calculation where product_id= '" + getprodID + "'&& next_prod_ids='"+  actualnextProdID+ "' && active_ingredient_id='"+  activelist.get(0)+ "'");
						//While Loop to iterate through all data and print results
						while (prod_cal.next()) {
							 ActualL0Result = prod_cal.getFloat(3); ActualL1Result = prod_cal.getFloat(4); ActualL2Result = prod_cal.getFloat(5); ActualL3Result = prod_cal.getFloat(6);
												}
						
						System.out.println("ActualL0Result: "+ActualL0Result+"ActualL1Result: "+ActualL1Result+"ActualL2Result: "+ActualL2Result+ "ActualL3Result: "+ActualL3Result);
						// Print Actual result to excel
						if(ActualL0Result==0)
								{
								Cell print_actual_L0 = sheet.getRow(actualresultrow).getCell(8); 
								print_actual_L0.setCellValue("NA"); // print actual L0 result into excel
								}else {
								Cell print_actual_L0 = sheet.getRow(actualresultrow).getCell(8); 
								print_actual_L0.setCellValue(ActualL0Result); // print actual L0 result into excel
								}			
						if(ActualL1Result==0)
						{
							Cell print_actual_L1 = sheet.getRow(actualresultrow).getCell(9); 
							print_actual_L1.setCellValue("NA"); // print actual L1 result into excel
						}else {
							Cell print_actual_L1 = sheet.getRow(actualresultrow).getCell(9); 
							print_actual_L1.setCellValue(ActualL1Result); // print actual L1 result into excel
						}		
						if(ActualL2Result==0)
						{
							Cell print_actual_L2 = sheet.getRow(actualresultrow).getCell(10); 
							print_actual_L2.setCellValue("NA"); // print actual L2 result into excel
						}else {
							Cell print_actual_L2 = sheet.getRow(actualresultrow).getCell(10); 
							print_actual_L2.setCellValue(ActualL2Result); // print actual L2 result into excel
						}
						if(ActualL3Result==0)
						{
							System.out.println("Zero");
							Cell print_actual_L3 = sheet.getRow(actualresultrow).getCell(11); 
							print_actual_L3.setCellValue("NA"); // print actual L3 result into excel
						}else {
							System.out.println("Not Zero");
							Cell print_actual_L3 = sheet.getRow(actualresultrow).getCell(11); 
							print_actual_L3.setCellValue(ActualL3Result); // print actual L3 result into excel
						}
						if(ActualL3Result!=0) {
						LowestActualL3.add((float) ActualL3Result);	
						}
						System.out.println("ActualL3Result"+ActualL3Result);
						actualresultrow++;
					}			
				
				 // Find minimum value from the actual results
					float LowestoneActualResult = Collections.min(LowestActualL3);
					System.out.println("LowestActualL3"+LowestActualL3);
					
					Cell printL3_result = sheet.getRow(41).getCell(12);
					printL3_result.setCellValue(LowestoneActualResult);
						
				//Compare both expected and actual result - applied round off for comparison
				if(Utils.toOptimizeDecimalPlacesRoundedOff(LowestoneActualResult).equals(Utils.toOptimizeDecimalPlacesRoundedOff(LowestoneExpectedL3)) )
				{
					Cell printlowestL3 = sheet.getRow(41).getCell(13);
					printlowestL3.setCellValue("Pass");
					printlowestL3.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
				}else
				{	Cell printlowestL3 = sheet.getRow(41).getCell(13);
					printlowestL3.setCellValue("Fail");
					printlowestL3.setCellStyle(Utils.style(workbook, "Fail")); // for print red font
				}
				
			//Get Actual L4a L4b L4c
			int j =81;
			float Ac_L4a = 0,Ac_L4b = 0,Ac_L4c = 0;
			for (Integer equipmentIDList:set) //get id from set
				   {
					System.out.println("equipmentIDList"+equipmentIDList);
					ResultSet ActualequipResult = stmt.executeQuery("SELECT * FROM product_calculation_equipment_results where product_id= '" + getprodID + "' && active_ingredient_id='"+  activelist.get(0)+ "' && equipment_id='"+equipmentIDList+"'");
					while (ActualequipResult.next()) {
					 Ac_L4a = ActualequipResult.getFloat(5); 
					 Ac_L4b = ActualequipResult.getFloat(6);
					 Ac_L4c = ActualequipResult.getFloat(7);
				Cell L4aEquip = sheet.getRow(j).getCell(9);//cell to print L4a value 
	    		L4aEquip.setCellValue(Ac_L4a); // print all the equipment surface area(used in the product) in excel
	    		Cell L4bEquip = sheet.getRow(j).getCell(10);//cell to print L4b value
	    		L4bEquip.setCellValue(Ac_L4b); // print all the equipment surface area(used in the product) in excel
	    		if(sampling_methodOption.equals("1,2")&& RinseSampling==1) // if rinse enabled in sampling
    			{
    				Cell L4cEquip = sheet.getRow(j).getCell(11);//cell to print L4b value
    	    		L4cEquip.setCellValue(Ac_L4c); // print all the equipment surface area(used in the product) in excel
    			}
    			else {
    				Cell L4cEquip = sheet.getRow(j).getCell(11);//cell to print L4b value
    	    		L4cEquip.setCellValue(0); // print all the equipment surface area(used in the product) in excel 
    				}
					}
					j++;
				 }
			
			// check expected L4a,L4b,L4c and actual L4a,L4b,L4c 
			int k= 81;
			for(Integer ss:set)
			{
			double EL4a = sheet.getRow(k).getCell(6).getNumericCellValue();
			double EL4b = sheet.getRow(k).getCell(7).getNumericCellValue();
			double EL4c = sheet.getRow(k).getCell(8).getNumericCellValue();
			double AL4a = sheet.getRow(k).getCell(9).getNumericCellValue();
			double AL4b = sheet.getRow(k).getCell(10).getNumericCellValue();
			double AL4c = sheet.getRow(k).getCell(11).getNumericCellValue();
			if(Utils.toOptimizeDecimalPlacesRoundedOff(EL4a).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4a)) && 
					Utils.toOptimizeDecimalPlacesRoundedOff(EL4b).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4b)) &&
					Utils.toOptimizeDecimalPlacesRoundedOff(EL4c).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4c)))
			{
				Cell verify_result = sheet.getRow(k).getCell(12);
				verify_result.setCellValue("Pass");
				verify_result.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
			}else
			{	
				Cell verify_result = sheet.getRow(k).getCell(12);
				verify_result.setCellValue("Fail");
				verify_result.setCellStyle(Utils.style(workbook, "Fail")); // for print red font
			}
			k++;
			}
		
		writeTooutputFile(workbook); // write output into work sheet
		connection.close();
		Thread.sleep(800);
		}// closing P1A1 calculation

	
	
	@Test(priority=2) // Product P1 Active1 to P2
	@Parameters({"productName1","productName2","productName3","productName4"})
	public void P1_Active2_patch(String productName1,String productName2,String productName3,String productName4) throws IOException, InterruptedException, SQLException, ClassNotFoundException 
	{
		String CurrenProductName = productName1;
		defaultValueSet(CurrenProductName);
		//XSSFWorkbook workbook;
		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result); 
		XSSFSheet sheet = workbook.getSheet("Patch_Calculation");
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();// Create Statement Object
				//next product id // Execute the SQL Query. Store results in Result Set // While Loop to iterate through all data and print results
				int nextProdID=0;
				
				 Set<String> productList = new HashSet<>(); 
				 productList.add(productName2);
				 productList.add(productName3);
				 productList.add(productName4);
				List<Float> LowestExpectL3 = new ArrayList<>();
				int row=46,column=9; //excel row and column
				
				for(String NextprodName : productList)
				{
					String nprodname = null;
					System.out.println("prodName"+NextprodName);
				ResultSet productdata = stmt.executeQuery("Select * from product where name ='"+NextprodName+"' "); // get next prod name from excel and find out in db
				while (productdata.next()) {	nextProdID = productdata.getInt(1); nprodname = productdata.getString(2); maxDD = productdata.getFloat(8); minBatch = productdata.getFloat(9);   }

				if(limitDetermination()==2)
				{
				System.out.println("Lowest Limit");
				value_L1 = L0forpatch.calculate_P1_active2_L0(productName1, productName2, productName3, productName4) / maxDD;
				Cell Solid_expec_Value_L0_print = sheet.getRow(row).getCell(3); 
				Solid_expec_Value_L0_print.setCellValue(L0forpatch.calculate_P1_active2_L0(productName1, productName2, productName3, productName4)); // print expected L0 result into excel
				
				}
				if(limitDetermination()==1)
				{
				System.out.println("grouping approach");
				value_L1 = L0forpatch.groupingApproach_L0_p11(CurrenProductName) / maxDD;
				Cell Solid_expec_Value_L0_print = sheet.getRow(row).getCell(3); 
				Solid_expec_Value_L0_print.setCellValue(L0forpatch.groupingApproach_L0_p11(CurrenProductName) ); // print expected L0 result into excel
				
				}
				value_L2 = value_L1 * minBatch * 1000 ; // Calculated L2 Value
				//Solid_Total_surface_area = sheet.getRow(28).getCell(column).getNumericCellValue(); 
				//value_L3 = value_L2 / Solid_Total_surface_area; // Calculated L3 value
				int sharedORLowest=0;
				ResultSet surfaceAreaOption = stmt.executeQuery("Select * from residue_limit"); // get equipment id
			    while (surfaceAreaOption.next()) {
			      sharedORLowest =surfaceAreaOption.getInt(15);
			    	}
			    if(sharedORLowest==0)
			    {
			    	Solid_Total_surface_area =  SurfaceAreaValue.actualSharedbetween2(CurrenProductName,NextprodName); // Calculated L3 for actual shared
			    }else
			    {
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
		Cell nextprodname = sheet.getRow(row).getCell(2); 
		nextprodname.setCellValue(nprodname ); // print next product name
		Cell Solid_expec_Value_L1_print = sheet.getRow(row).getCell(4); 
		Solid_expec_Value_L1_print.setCellValue(Solid_Expec_Value_L1 ); // print expected L0 result into excel
		Cell Solid_expec_Value_L2_print = sheet.getRow(row).getCell(5); 
		Solid_expec_Value_L2_print.setCellValue(Solid_Expec_Value_L2); // print expected L2 result into excel
		Cell Solid_expec_Value_L3_print = sheet.getRow(row).getCell(6); 
		Solid_expec_Value_L3_print.setCellValue(Solid_Expec_Value_L3); // print expected L3 result into excel
		System.out.println("Expected L1: "+Solid_Expec_Value_L1);
		System.out.println("Expected L2: "+Solid_Expec_Value_L2);
		System.out.println("Expected L3: "+Solid_Expec_Value_L3);
		LowestExpectL3.add((float) Solid_Expec_Value_L3);
		row++;
		column++;
		}
			
		System.out.println("LowestExpectL3"+LowestExpectL3);
		float LowestoneExpectedL3 = Collections.min(LowestExpectL3);
		System.out.println("Min" +LowestoneExpectedL3);
		Cell Solid_expec_Value_L3_print = sheet.getRow(45).getCell(7); //print lowest L3 from iteration
		Solid_expec_Value_L3_print.setCellValue(LowestoneExpectedL3); // print expected L3 result into excel
				
				
		int getprodID = 0;
		float SFArea = 0,rinsevolume=0,swabarea = 0,swabamount=0;
		String eqname = null,prodname = null;
		
		//Get no of used Equipment in the Current product
		ResultSet productID = stmt.executeQuery("Select * from product where name = '" + CurrenProductName + "'"); // get product name id
			while (productID.next()) 
			{
				getprodID = productID.getInt(1);
				prodname = productID.getString(2); // get name id from product table
			} // closing for productID while loop 
			//equipment set id
			ResultSet productSetEqID = stmt.executeQuery("Select * from product_equipment_set_equipments where product_id='" + getprodID + "'"); // get equipment id
			Set<Integer> set = new HashSet<>(); // store multiple equipment id
		    while (productSetEqID.next()) {
		      set.add(productSetEqID.getInt(4));
		    	}
		    int i =81; //increment cell to print result
		    
		    for (Integer equipmentID:set) //get id from set
		    {
		    ResultSet EquipID = stmt.executeQuery("Select * from equipment where id= '" + equipmentID + "'"); // get product name id
		    		// print
		    	while(EquipID.next()) {  // print name and sf value from equipment table
		    	 eqname = EquipID.getString(9); // get name from database
				 SFArea = EquipID.getFloat(13); // get SF value from database
				 rinsevolume = EquipID.getFloat(17); // get SF value from database
				 swabarea = EquipID.getFloat(15); // get SF value from database
				 swabamount = EquipID.getFloat(16);
				 
				 if(swabArea()==0) //check swab area from uni setting or each equipment
				 {
					 Solid_Expec_Value_L4a =  LowestoneExpectedL3 * swabarea;
					 Cell equipRinse = sheet.getRow(i).getCell(16);//cell to print swab amount
					 equipRinse.setCellValue(swabarea); 
				 }else {
					 Solid_Expec_Value_L4a =  LowestoneExpectedL3 * swabArea();
					 Cell equipRinse = sheet.getRow(i).getCell(16);//cell to print swab amount
					 equipRinse.setCellValue(swabArea()); 
					 System.out.println("Solid_Expec_Value_L4a" +Solid_Expec_Value_L4a);
					 System.out.println("lowestL3"+LowestoneExpectedL3);
					 System.out.println("swabArea" +swabArea());
				 }
				 //swab amount
				 if(swabAmount()==0) //check swab amount from univ setting or each equipment
				 {
					 Solid_Expec_Value_L4b =  Solid_Expec_Value_L4a/ swabamount;
					 Cell equipRinse = sheet.getRow(i).getCell(17);//cell to print swab amount
					 equipRinse.setCellValue(swabamount); 
				 }else {
					 Solid_Expec_Value_L4b =  Solid_Expec_Value_L4a/ swabAmount();
					 Cell equipRinse = sheet.getRow(i).getCell(17);//cell to print swab amount
					 equipRinse.setCellValue(swabAmount()); 
				 }
				// equipment rinse volume()
				 if(eqRinseVolume()==0) //check rinset from univ setting or each equipment
				 {
					 L4cEquipment = (LowestoneExpectedL3 * SFArea) /(rinsevolume * 1000);//Calculate L4c equipment value
					 Cell equipRinse = sheet.getRow(i).getCell(18);//cell to print equipment rinse volume
					 equipRinse.setCellValue(rinsevolume); // print all the equipment rinse volume(used in the product) in excel
				 }else {
					 L4cEquipment = (LowestoneExpectedL3 * SFArea) /(eqRinseVolume() * 1000);//Calculate L4c equipment value
					 Cell equipRinse = sheet.getRow(i).getCell(18);//cell to print equipment rinse volume 
					 equipRinse.setCellValue(eqRinseVolume()); // print all the equipment rinse volume(used in the product) in excel
					 System.out.println("L4cEquipment"+L4cEquipment);
				 }
		    	 // Print Expected L4a, L4b, L4c value
		    	 	Cell equipName = sheet.getRow(i).getCell(14);//cell to print name 
		    		equipName.setCellValue(eqname); // print equipment name(used in the product) in excel
		    		Cell equipSF = sheet.getRow(i).getCell(15);//cell to print equipment surface area 
		    		equipSF.setCellValue(SFArea); // print all the equipment surface area(used in the product) in excel
		    		Cell L4aEquip = sheet.getRow(i).getCell(19);//cell to print L4a value 
		    		L4aEquip.setCellValue(Solid_Expec_Value_L4a); // print all the equipment surface area(used in the product) in excel
		    		Cell L4bEquip = sheet.getRow(i).getCell(20);//cell to print L4b value
		    		L4bEquip.setCellValue(Solid_Expec_Value_L4b); // print all the equipment surface area(used in the product) in excel
		    		
		    		// check whether rinse enabled in universal settings
		    		System.out.println("sampling_methodOption"+sampling_methodOption);
		    		System.out.println("RinseSampling"+RinseSampling);
		    			if(sampling_methodOption.equals("1,2") && RinseSampling==1) // if rinse enabled in sampling
		    			{
		    				Cell L4cEquip = sheet.getRow(i).getCell(21);
		    				L4cEquip.setCellValue(L4cEquipment); 
		    			}
		    			else {
		    				Cell L4cEquip = sheet.getRow(i).getCell(21);
		    				L4cEquip.setCellValue(0); 
		    				}
				 i++;
		    	} //closing equipmentID while loop 
		    	} //closing for loop
		    
		   
		    	
		    // Get Actual Limit Result from db
		    System.out.println("Actual result productid: "+getprodID);
		    ResultSet prod_active_relation = stmt.executeQuery("SELECT * FROM product_active_ingredient_relation where product_id='" + getprodID + "'");
			//get active multiple active id
			List<Integer> activelist = new ArrayList<>(); // get active list from above query
			while (prod_active_relation.next()) 
			{
			activelist.add(prod_active_relation.getInt(2));
			}
		  	
				 // get active name ,prod name and print in excel
				 ResultSet active = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+ activelist.get(1) + "'");
				 {
					 while (active.next()) 
					 {
						 if(limitDetermination()==2)
						 {
						 String activename = active.getString(2);
						 //print active name to excel
						 Cell NameofActive = sheet.getRow(79).getCell(15);
						 NameofActive.setCellValue(activename); // print active name into excel
						 Cell ActiveName = sheet.getRow(45).getCell(1);
						 ActiveName.setCellValue(activename); // print active name into excel
						 }else {
							 System.out.println(" lowest loop");
							 //print active name to excel
							 Cell NameofActive = sheet.getRow(79).getCell(15);
							 NameofActive.setCellValue(prodname); // print active name into excel
							 Cell ActiveName = sheet.getRow(45).getCell(1);
							 ActiveName.setCellValue(prodname); // print active name into excel
						 }
					 }
				 }
				 
				 //get product id
				 System.out.println("Prouct id: "+getprodID +" Active id: "+activelist.get(1));
				 //current product id 
				 List<Float> LowestActualL3 = new ArrayList<>(); 
				 int actualnextProdID = 0,actualresultrow = 46;
				 for(String NextprodNameactualresult : productList)
					{ 
					 float ActualL0Result = 1,ActualL1Result = 1,ActualL2Result = 1,ActualL3Result = 1;
					 ResultSet productdata = stmt.executeQuery("Select * from product where name ='"+NextprodNameactualresult+"' "); // get next prod name from excel and find out in db
						while (productdata.next()) {	actualnextProdID = productdata.getInt(1);    }
						
						System.out.println("actualnextProdID"+actualnextProdID);
						ResultSet prod_cal = stmt.executeQuery("SELECT * FROM product_calculation where product_id= '" + getprodID + "'&& next_prod_ids='"+  actualnextProdID+ "' && active_ingredient_id='"+  activelist.get(1)+ "'");
						//While Loop to iterate through all data and print results
						while (prod_cal.next()) {
							 ActualL0Result = prod_cal.getFloat(3); ActualL1Result = prod_cal.getFloat(4); ActualL2Result = prod_cal.getFloat(5); ActualL3Result = prod_cal.getFloat(6);
												}
						
						// Print Actual result to excel
						if(ActualL0Result==0)
								{
								Cell print_actual_L0 = sheet.getRow(actualresultrow).getCell(8); 
								print_actual_L0.setCellValue("NA"); // print actual L0 result into excel
								}else {
								Cell print_actual_L0 = sheet.getRow(actualresultrow).getCell(8); 
								print_actual_L0.setCellValue(ActualL0Result); // print actual L0 result into excel
								}			
						if(ActualL1Result==0)
						{
							Cell print_actual_L1 = sheet.getRow(actualresultrow).getCell(9); 
							print_actual_L1.setCellValue("NA"); // print actual L1 result into excel
						}else {
							Cell print_actual_L1 = sheet.getRow(actualresultrow).getCell(9); 
							print_actual_L1.setCellValue(ActualL1Result); // print actual L1 result into excel
						}		
						if(ActualL2Result==0)
						{
							Cell print_actual_L2 = sheet.getRow(actualresultrow).getCell(10); 
							print_actual_L2.setCellValue("NA"); // print actual L2 result into excel
						}else {
							Cell print_actual_L2 = sheet.getRow(actualresultrow).getCell(10); 
							print_actual_L2.setCellValue(ActualL2Result); // print actual L2 result into excel
						}
						if(ActualL3Result==0)
						{
							System.out.println("Zero");
							Cell print_actual_L3 = sheet.getRow(actualresultrow).getCell(11); 
							print_actual_L3.setCellValue("NA"); // print actual L3 result into excel
						}else {
							System.out.println("Not Zero");
							Cell print_actual_L3 = sheet.getRow(actualresultrow).getCell(11); 
							print_actual_L3.setCellValue(ActualL3Result); // print actual L3 result into excel
						}
						if(ActualL3Result!=0) {
						LowestActualL3.add((float) ActualL3Result);	
						}
						System.out.println("ActualL3Result"+ActualL3Result);
						actualresultrow++;
						
					}			
				
				 // Find minimum value from the actual results
					float LowestoneActualResult = Collections.min(LowestActualL3);
					System.out.println("LowestActualL3"+LowestActualL3);
					
					Cell printL3_result = sheet.getRow(45).getCell(12);
					printL3_result.setCellValue(LowestoneActualResult);
						
				//Compare both expected and actual result - applied round off for comparison
				if(Utils.toOptimizeDecimalPlacesRoundedOff(LowestoneActualResult).equals(Utils.toOptimizeDecimalPlacesRoundedOff(LowestoneExpectedL3)) )
				{
					Cell printlowestL3 = sheet.getRow(45).getCell(13);
					printlowestL3.setCellValue("Pass");
					printlowestL3.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
				}else
				{	Cell printlowestL3 = sheet.getRow(45).getCell(13);
					printlowestL3.setCellValue("Fail");
					printlowestL3.setCellStyle(Utils.style(workbook, "Fail")); // for print red font
				}
				
				
			//Get Actual L4a L4b L4c
			int j =81;
			float Ac_L4a = 0,Ac_L4b = 0,Ac_L4c = 0;
			for (Integer equipmentIDList:set) //get id from set
				   {
					System.out.println("equipmentIDList"+equipmentIDList);
					ResultSet ActualequipResult = stmt.executeQuery("SELECT * FROM product_calculation_equipment_results where product_id= '" + getprodID + "' && active_ingredient_id='"+  activelist.get(1)+ "' && equipment_id='"+equipmentIDList+"'");
					while (ActualequipResult.next()) {
					 Ac_L4a = ActualequipResult.getFloat(5); 
					 Ac_L4b = ActualequipResult.getFloat(6);
					 Ac_L4c = ActualequipResult.getFloat(7);
				Cell L4aEquip = sheet.getRow(j).getCell(22);//cell to print L4a value 
	    		L4aEquip.setCellValue(Ac_L4a); // print all the equipment surface area(used in the product) in excel
	    		Cell L4bEquip = sheet.getRow(j).getCell(23);//cell to print L4b value
	    		L4bEquip.setCellValue(Ac_L4b); // print all the equipment surface area(used in the product) in excel
	    		if(sampling_methodOption.equals("1,2")&& RinseSampling==1) // if rinse enabled in sampling
    			{
    				Cell L4cEquip = sheet.getRow(j).getCell(24);//cell to print L4b value
    	    		L4cEquip.setCellValue(Ac_L4c); // print all the equipment surface area(used in the product) in excel
    			}
    			else {
    				Cell L4cEquip = sheet.getRow(j).getCell(24);//cell to print L4b value
    	    		L4cEquip.setCellValue(0); // print all the equipment surface area(used in the product) in excel 
    				}
					}
					j++;
				 }
			
			// check expected L4a,L4b,L4c and actual L4a,L4b,L4c 
			int k= 81;
			for(Integer ss:set)
			{
			double EL4a = sheet.getRow(k).getCell(19).getNumericCellValue();
			double EL4b = sheet.getRow(k).getCell(20).getNumericCellValue();
			double EL4c = sheet.getRow(k).getCell(21).getNumericCellValue();
			double AL4a = sheet.getRow(k).getCell(22).getNumericCellValue();
			double AL4b = sheet.getRow(k).getCell(23).getNumericCellValue();
			double AL4c = sheet.getRow(k).getCell(24).getNumericCellValue();
			if(Utils.toOptimizeDecimalPlacesRoundedOff(EL4a).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4a)) && 
					Utils.toOptimizeDecimalPlacesRoundedOff(EL4b).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4b)) &&
					Utils.toOptimizeDecimalPlacesRoundedOff(EL4c).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4c)))
			{
				Cell verify_result = sheet.getRow(k).getCell(25);
				verify_result.setCellValue("Pass");
				verify_result.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
			}else
			{	
				Cell verify_result = sheet.getRow(k).getCell(25);
				verify_result.setCellValue("Fail");
				verify_result.setCellStyle(Utils.style(workbook, "Fail")); // for print red font
			}
			k++;
			}
		
		writeTooutputFile(workbook); // write output into work sheet
		connection.close();
		Thread.sleep(800);
		}// closing P1A1 calculation

	
	
	

	@Test(priority=3) // Current ProductName with First Active
	@Parameters({"productName1","productName2","productName3","productName4"})
	public void P2_Active1_patch(String productName1,String productName2,String productName3,String productName4) throws IOException, InterruptedException, SQLException, ClassNotFoundException 
	{
		 String CurrenProductName= productName2;
		defaultValueSet(CurrenProductName);
		//XSSFWorkbook workbook;
		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result); 
		XSSFSheet sheet = workbook.getSheet("Patch_Calculation");
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();// Create Statement Object
				//next product id // Execute the SQL Query. Store results in Result Set // While Loop to iterate through all data and print results
				int nextProdID=0;
				
				 Set<String> productList = new HashSet<>(); 
				 productList.add(productName1);
				 productList.add(productName3);
				 productList.add(productName4);
				List<Float> LowestExpectL3 = new ArrayList<>();
				int row=50,column=9; //excel row and column
				
				for(String NextprodName : productList)
				{
				String nprodname = null;
				System.out.println("prodName"+NextprodName);
				ResultSet productdata = stmt.executeQuery("Select * from product where name ='"+NextprodName+"' "); // get next prod name from excel and find out in db
				while (productdata.next()) {	nextProdID = productdata.getInt(1); nprodname =productdata.getString(2); maxDD = productdata.getFloat(8); minBatch = productdata.getFloat(9);   }

				if(limitDetermination()==2)
				{
				System.out.println("Lowest Limit");
				value_L1 = L0forpatch.calculate_P2_active1_L0(productName1, productName2, productName3, productName4) / maxDD;
				Cell Solid_expec_Value_L0_print = sheet.getRow(row).getCell(3); 
				Solid_expec_Value_L0_print.setCellValue(L0forpatch.calculate_P2_active1_L0(productName1, productName2, productName3, productName4)); // print expected L0 result into excel
				}
				if(limitDetermination()==1)
				{
				System.out.println("grouping approach");
				value_L1 = L0forpatch.groupingApproach_L0_p11(CurrenProductName) / maxDD;
				Cell Solid_expec_Value_L0_print = sheet.getRow(row).getCell(3); 
				Solid_expec_Value_L0_print.setCellValue(L0forpatch.groupingApproach_L0_p11(CurrenProductName)); // print expected L0 result into excel
				}
				value_L2 = value_L1 * minBatch * 1000 ; // Calculated L2 Value
				//Solid_Total_surface_area = sheet.getRow(30).getCell(column).getNumericCellValue(); 
				int sharedORLowest=0;
				ResultSet surfaceAreaOption = stmt.executeQuery("Select * from residue_limit"); // get equipment id
			    while (surfaceAreaOption.next()) {
			      sharedORLowest =surfaceAreaOption.getInt(15);
			    	}
			    if(sharedORLowest==0)
			    {
			    	Solid_Total_surface_area =  SurfaceAreaValue.actualSharedbetween2(CurrenProductName,NextprodName); // Calculated L3 for actual shared
			    	Cell printsurfaceareaUsed = sheet.getRow(30).getCell(column); 
			    	printsurfaceareaUsed.setCellValue(SurfaceAreaValue.actualSharedbetween2(CurrenProductName,NextprodName) ); // print surface area
			    }else
			    {
			    	Solid_Total_surface_area =  SurfaceAreaValue.lowestTrainbetween2(CurrenProductName,NextprodName); // Calculated L3 for lowest train between two
			    	Cell printsurfaceareaUsed = sheet.getRow(28).getCell(column); 
			    	printsurfaceareaUsed.setCellValue(SurfaceAreaValue.lowestTrainbetween2(CurrenProductName,NextprodName) ); // print surface area
			    }
				column++;
			    value_L3 = value_L2/Solid_Total_surface_area;
			    
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
		Cell nextprodname = sheet.getRow(row).getCell(2); 
		nextprodname.setCellValue(nprodname ); // print next product name
		Cell Solid_expec_Value_L1_print = sheet.getRow(row).getCell(4); 
		Solid_expec_Value_L1_print.setCellValue(Solid_Expec_Value_L1 ); // print expected L0 result into excel
		Cell Solid_expec_Value_L2_print = sheet.getRow(row).getCell(5); 
		Solid_expec_Value_L2_print.setCellValue(Solid_Expec_Value_L2); // print expected L2 result into excel
		Cell Solid_expec_Value_L3_print = sheet.getRow(row).getCell(6); 
		Solid_expec_Value_L3_print.setCellValue(Solid_Expec_Value_L3); // print expected L3 result into excel
		System.out.println("Expected L1: "+Solid_Expec_Value_L1);
		System.out.println("Expected L2: "+Solid_Expec_Value_L2);
		System.out.println("Expected L3: "+Solid_Expec_Value_L3);
		LowestExpectL3.add((float) Solid_Expec_Value_L3);
		row++;
		column++;
		}
			
		System.out.println("LowestExpectL3"+LowestExpectL3);
		float LowestoneExpectedL3 = Collections.min(LowestExpectL3);
		System.out.println("Min" +LowestoneExpectedL3);
		Cell Solid_expec_Value_L3_print = sheet.getRow(49).getCell(7); //print lowest L3 from iteration
		Solid_expec_Value_L3_print.setCellValue(LowestoneExpectedL3); // print expected L3 result into excel
				
				
		int getprodID = 0;
		float SFArea = 0,rinsevolume=0,swabarea = 0,swabamount=0;
		String eqname = null,prodname = null;
		
		//Get no of used Equipment in the Current product
		ResultSet productID = stmt.executeQuery("Select * from product where name = '" + CurrenProductName + "'"); // get product name id
			while (productID.next()) 
			{
				getprodID = productID.getInt(1);
				prodname = productID.getString(2); // get name id from product table
			} // closing for productID while loop 
			//equipment set id
			ResultSet productSetEqID = stmt.executeQuery("Select * from product_equipment_set_equipments where product_id='" + getprodID + "'"); // get equipment id
			Set<Integer> set = new HashSet<>(); // store multiple equipment id
		    while (productSetEqID.next()) {
		      set.add(productSetEqID.getInt(4));
		    	}
		    int i =93; //increment cell to print result
		    
		    for (Integer equipmentID:set) //get id from set
		    {
		    ResultSet EquipID = stmt.executeQuery("Select * from equipment where id= '" + equipmentID + "'"); // get product name id
		    		// print
		    	while(EquipID.next()) {  // print name and sf value from equipment table
		    	 eqname = EquipID.getString(9); // get name from database
				 SFArea = EquipID.getFloat(13); // get SF value from database
				 rinsevolume = EquipID.getFloat(17); // get SF value from database
				 swabarea = EquipID.getFloat(15); // get SF value from database
				 swabamount = EquipID.getFloat(16);
				 
				 if(swabArea()==0) //check swab area from uni setting or each equipment
				 {
					 Solid_Expec_Value_L4a =  LowestoneExpectedL3 * swabarea;
					 Cell equipRinse = sheet.getRow(i).getCell(3);//cell to print swab amount
					 equipRinse.setCellValue(swabarea); 
				 }else {
					 Solid_Expec_Value_L4a =  LowestoneExpectedL3 * swabArea();
					 Cell equipRinse = sheet.getRow(i).getCell(3);//cell to print swab amount
					 equipRinse.setCellValue(swabArea()); 
					 System.out.println("Solid_Expec_Value_L4a" +Solid_Expec_Value_L4a);
					 System.out.println("lowestL3"+LowestoneExpectedL3);
					 System.out.println("swabArea" +swabArea());
				 }
				 //swab amount
				 if(swabAmount()==0) //check swab amount from univ setting or each equipment
				 {
					 Solid_Expec_Value_L4b =  Solid_Expec_Value_L4a/ swabamount;
					 Cell equipRinse = sheet.getRow(i).getCell(4);//cell to print swab amount
					 equipRinse.setCellValue(swabamount); 
				 }else {
					 Solid_Expec_Value_L4b =  Solid_Expec_Value_L4a/ swabAmount();
					 Cell equipRinse = sheet.getRow(i).getCell(4);//cell to print swab amount
					 equipRinse.setCellValue(swabAmount()); 
				 }
				// equipment rinse volume()
				 if(eqRinseVolume()==0) //check rinset from univ setting or each equipment
				 {
					 L4cEquipment = (LowestoneExpectedL3 * SFArea) /(rinsevolume * 1000);//Calculate L4c equipment value
					 Cell equipRinse = sheet.getRow(i).getCell(5);//cell to print equipment rinse volume
					 equipRinse.setCellValue(rinsevolume); // print all the equipment rinse volume(used in the product) in excel
				 }else {
					 L4cEquipment = (LowestoneExpectedL3 * SFArea) /(eqRinseVolume() * 1000);//Calculate L4c equipment value
					 Cell equipRinse = sheet.getRow(i).getCell(5);//cell to print equipment rinse volume 
					 equipRinse.setCellValue(eqRinseVolume()); // print all the equipment rinse volume(used in the product) in excel
					 System.out.println("L4cEquipment"+L4cEquipment);
				 }
		    	 // Print Expected L4a, L4b, L4c value
		    	 	Cell equipName = sheet.getRow(i).getCell(1);//cell to print name 
		    		equipName.setCellValue(eqname); // print equipment name(used in the product) in excel
		    		Cell equipSF = sheet.getRow(i).getCell(2);//cell to print equipment surface area 
		    		equipSF.setCellValue(SFArea); // print all the equipment surface area(used in the product) in excel
		    		Cell L4aEquip = sheet.getRow(i).getCell(6);//cell to print L4a value 
		    		L4aEquip.setCellValue(Solid_Expec_Value_L4a); // print all the equipment surface area(used in the product) in excel
		    		Cell L4bEquip = sheet.getRow(i).getCell(7);//cell to print L4b value
		    		L4bEquip.setCellValue(Solid_Expec_Value_L4b); // print all the equipment surface area(used in the product) in excel
		    		
		    		// check whether rinse enabled in universal settings
		    		System.out.println("sampling_methodOption"+sampling_methodOption);
		    		System.out.println("RinseSampling"+RinseSampling);
		    			if(sampling_methodOption.equals("1,2") && RinseSampling==1) // if rinse enabled in sampling
		    			{
		    				Cell L4cEquip = sheet.getRow(i).getCell(8);
		    				L4cEquip.setCellValue(L4cEquipment); 
		    			}
		    			else {
		    				Cell L4cEquip = sheet.getRow(i).getCell(8);
		    				L4cEquip.setCellValue(0); 
		    				}
				 i++;
		    	} //closing equipmentID while loop 
		    	} //closing for loop
		    
		    // Get Actual Limit Result from db
		    System.out.println("Actual result productid: "+getprodID);
		    ResultSet prod_active_relation = stmt.executeQuery("SELECT * FROM product_active_ingredient_relation where product_id='" + getprodID + "'");
			//get active multiple active id
			List<Integer> activelist = new ArrayList<>(); // get active list from above query
			while (prod_active_relation.next()) 
			{
			activelist.add(prod_active_relation.getInt(2));
			}
				 // get active name ,prod name and print in excel
				 ResultSet active = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+ activelist.get(0) + "'");
				 {
					 while (active.next()) 
					 {
						 if(limitDetermination()==2)
						 {
						 String activename = active.getString(2);
						 //print active name to excel
						 Cell NameofActive = sheet.getRow(91).getCell(2);
						 NameofActive.setCellValue(activename); // print active name into excel
						 Cell ActiveName = sheet.getRow(49).getCell(1);
						 ActiveName.setCellValue(activename); // print active name into excel
						 }else {
							 System.out.println(" lowest loop");
							 //print active name to excel
							 Cell NameofActive = sheet.getRow(91).getCell(2);
							 NameofActive.setCellValue(prodname); // print active name into excel
							 Cell ActiveName = sheet.getRow(49).getCell(1);
							 ActiveName.setCellValue(prodname); // print active name into excel
						 }
					 }
				 }
				 //get product id
				 System.out.println("Prouct id: "+getprodID +" Active id: "+activelist.get(0));
				 //current product id 
				 List<Float> LowestActualL3 = new ArrayList<>(); 
				 int actualnextProdID = 0;
				 int actualresultrow = 50;
				 for(String NextprodNameactualresult : productList)
					{ 
					 float ActualL0Result = 1,ActualL1Result = 1,ActualL2Result = 1,ActualL3Result = 1;
					 ResultSet productdata = stmt.executeQuery("Select * from product where name ='"+NextprodNameactualresult+"' "); // get next prod name from excel and find out in db
						while (productdata.next()) {	actualnextProdID = productdata.getInt(1);    }
						
						System.out.println("actualnextProdID"+actualnextProdID);
						ResultSet prod_cal = stmt.executeQuery("SELECT * FROM product_calculation where product_id= '" + getprodID + "'&& next_prod_ids='"+  actualnextProdID+ "' && active_ingredient_id='"+  activelist.get(0)+ "'");
						//While Loop to iterate through all data and print results
						while (prod_cal.next()) {
							 ActualL0Result = prod_cal.getFloat(3); ActualL1Result = prod_cal.getFloat(4); ActualL2Result = prod_cal.getFloat(5); ActualL3Result = prod_cal.getFloat(6);
												}
						// Print Actual result to excel
						if(ActualL0Result==0)
								{
								Cell print_actual_L0 = sheet.getRow(actualresultrow).getCell(8); 
								print_actual_L0.setCellValue("NA"); // print actual L0 result into excel
								}else {
								Cell print_actual_L0 = sheet.getRow(actualresultrow).getCell(8); 
								print_actual_L0.setCellValue(ActualL0Result); // print actual L0 result into excel
								}			
						if(ActualL1Result==0)
						{
							Cell print_actual_L1 = sheet.getRow(actualresultrow).getCell(9); 
							print_actual_L1.setCellValue("NA"); // print actual L1 result into excel
						}else {
							Cell print_actual_L1 = sheet.getRow(actualresultrow).getCell(9); 
							print_actual_L1.setCellValue(ActualL1Result); // print actual L1 result into excel
						}		
						if(ActualL2Result==0)
						{
							Cell print_actual_L2 = sheet.getRow(actualresultrow).getCell(10); 
							print_actual_L2.setCellValue("NA"); // print actual L2 result into excel
						}else {
							Cell print_actual_L2 = sheet.getRow(actualresultrow).getCell(10); 
							print_actual_L2.setCellValue(ActualL2Result); // print actual L2 result into excel
						}
						if(ActualL3Result==0)
						{
							System.out.println("Zero");
							Cell print_actual_L3 = sheet.getRow(actualresultrow).getCell(11); 
							print_actual_L3.setCellValue("NA"); // print actual L3 result into excel
						}else {
							System.out.println("Not Zero");
							Cell print_actual_L3 = sheet.getRow(actualresultrow).getCell(11); 
							print_actual_L3.setCellValue(ActualL3Result); // print actual L3 result into excel
						}
						if(ActualL3Result!=0) {
						LowestActualL3.add((float) ActualL3Result);	
						}
						System.out.println("ActualL3Result"+ActualL3Result);
						actualresultrow++;
					}			
				
				 // Find minimum value from the actual results
					float LowestoneActualResult = Collections.min(LowestActualL3);
					System.out.println("LowestActualL3"+LowestActualL3);
					
					Cell printL3_result = sheet.getRow(49).getCell(12);
					printL3_result.setCellValue(LowestoneActualResult);
						
				//Compare both expected and actual result - applied round off for comparison
				if(Utils.toOptimizeDecimalPlacesRoundedOff(LowestoneActualResult).equals(Utils.toOptimizeDecimalPlacesRoundedOff(LowestoneExpectedL3)) )
				{
					Cell printlowestL3 = sheet.getRow(49).getCell(13);
					printlowestL3.setCellValue("Pass");
					printlowestL3.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
				}else
				{	Cell printlowestL3 = sheet.getRow(49).getCell(13);
					printlowestL3.setCellValue("Fail");
					printlowestL3.setCellStyle(Utils.style(workbook, "Fail")); // for print red font
				}
				
			//Get Actual L4a L4b L4c
			int j =93;
			float Ac_L4a = 0,Ac_L4b = 0,Ac_L4c = 0;
			for (Integer equipmentIDList:set) //get id from set
				   {
					System.out.println("equipmentIDList"+equipmentIDList);
					ResultSet ActualequipResult = stmt.executeQuery("SELECT * FROM product_calculation_equipment_results where product_id= '" + getprodID + "' && active_ingredient_id='"+  activelist.get(0)+ "' && equipment_id='"+equipmentIDList+"'");
					while (ActualequipResult.next()) {
					 Ac_L4a = ActualequipResult.getFloat(5); 
					 Ac_L4b = ActualequipResult.getFloat(6);
					 Ac_L4c = ActualequipResult.getFloat(7);
				Cell L4aEquip = sheet.getRow(j).getCell(9);//cell to print L4a value 
	    		L4aEquip.setCellValue(Ac_L4a); // print all the equipment surface area(used in the product) in excel
	    		Cell L4bEquip = sheet.getRow(j).getCell(10);//cell to print L4b value
	    		L4bEquip.setCellValue(Ac_L4b); // print all the equipment surface area(used in the product) in excel
	    		if(sampling_methodOption.equals("1,2")&& RinseSampling==1) // if rinse enabled in sampling
    			{
    				Cell L4cEquip = sheet.getRow(j).getCell(11);//cell to print L4b value
    	    		L4cEquip.setCellValue(Ac_L4c); // print all the equipment surface area(used in the product) in excel
    			}
    			else {
    				Cell L4cEquip = sheet.getRow(j).getCell(11);//cell to print L4b value
    	    		L4cEquip.setCellValue(0); // print all the equipment surface area(used in the product) in excel 
    				}
					}
					j++;
				 }
			
			// check expected L4a,L4b,L4c and actual L4a,L4b,L4c 
			int k= 93;
			for(Integer ss:set)
			{
			double EL4a = sheet.getRow(k).getCell(6).getNumericCellValue();
			double EL4b = sheet.getRow(k).getCell(7).getNumericCellValue();
			double EL4c = sheet.getRow(k).getCell(8).getNumericCellValue();
			double AL4a = sheet.getRow(k).getCell(9).getNumericCellValue();
			double AL4b = sheet.getRow(k).getCell(10).getNumericCellValue();
			double AL4c = sheet.getRow(k).getCell(11).getNumericCellValue();
			if(Utils.toOptimizeDecimalPlacesRoundedOff(EL4a).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4a)) && 
					Utils.toOptimizeDecimalPlacesRoundedOff(EL4b).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4b)) &&
					Utils.toOptimizeDecimalPlacesRoundedOff(EL4c).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4c)))
			{
				Cell verify_result = sheet.getRow(k).getCell(12);
				verify_result.setCellValue("Pass");
				verify_result.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
			}else
			{	
				Cell verify_result = sheet.getRow(k).getCell(12);
				verify_result.setCellValue("Fail");
				verify_result.setCellStyle(Utils.style(workbook, "Fail")); // for print red font
			}
			k++;
			}
		
		writeTooutputFile(workbook); // write output into work sheet
		connection.close();
		Thread.sleep(800);
		}// closing P1A1 calculation

	
	
	

	@Test(priority=4) // current productname P2 with second active
	@Parameters({"productName1","productName2","productName3","productName4"})
	public void P2_Active2_patch(String productName1,String productName2,String productName3,String productName4) throws IOException, InterruptedException, SQLException, ClassNotFoundException 
	{
		 String CurrenProductName = productName2; 
		defaultValueSet(CurrenProductName);
		//XSSFWorkbook workbook;
		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result); 
		XSSFSheet sheet = workbook.getSheet("Patch_Calculation");
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();// Create Statement Object
				//next product id // Execute the SQL Query. Store results in Result Set // While Loop to iterate through all data and print results
				int nextProdID=0;
				
				 Set<String> productList = new HashSet<>(); 
				 productList.add(productName1);
				 productList.add(productName3);
				 productList.add(productName4);
				List<Float> LowestExpectL3 = new ArrayList<>();
				int row=54,column=9; //excel row and column
				
				for(String NextprodName : productList)
				{
					String nprodname=null;
					System.out.println("prodName"+NextprodName);
				ResultSet productdata = stmt.executeQuery("Select * from product where name ='"+NextprodName+"' "); // get next prod name from excel and find out in db
				while (productdata.next()) {	nextProdID = productdata.getInt(1); nprodname =productdata.getString(2); maxDD = productdata.getFloat(8); minBatch = productdata.getFloat(9);   }

				if(limitDetermination()==2)
				{
				System.out.println("Lowest Limit");
				value_L1 = L0forpatch.calculate_P2_active2_L0(productName1, productName2, productName3, productName4) / maxDD;
				Cell Solid_expec_Value_L0_print = sheet.getRow(row).getCell(3); 
				Solid_expec_Value_L0_print.setCellValue(L0forpatch.calculate_P2_active2_L0(productName1, productName2, productName3, productName4)); // print expected L0 result into excel
				
				}
				if(limitDetermination()==1)
				{
				System.out.println("grouping approach");
				value_L1 = L0forpatch.groupingApproach_L0_p11(CurrenProductName)  / maxDD;
				Cell Solid_expec_Value_L0_print = sheet.getRow(row).getCell(3); 
				Solid_expec_Value_L0_print.setCellValue(L0forpatch.groupingApproach_L0_p11(CurrenProductName)  ); // print expected L0 result into excel
				
				}
				value_L2 = value_L1 * minBatch * 1000 ; // Calculated L2 Value
				int sharedORLowest=0;
				ResultSet surfaceAreaOption = stmt.executeQuery("Select * from residue_limit"); // get equipment id
			    while (surfaceAreaOption.next()) {
			      sharedORLowest =surfaceAreaOption.getInt(15);
			    	}
			    if(sharedORLowest==0)
			    {
			    	Solid_Total_surface_area =  SurfaceAreaValue.actualSharedbetween2(CurrenProductName,NextprodName); // Calculated L3 for actual shared
			    }else
			    {
			    	Solid_Total_surface_area =  SurfaceAreaValue.lowestTrainbetween2(CurrenProductName,NextprodName); // Calculated L3 for lowest train between two
			    }
				
			    value_L3 = value_L2/Solid_Total_surface_area;
			    
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
		Cell nextprodname = sheet.getRow(row).getCell(2); 
		nextprodname.setCellValue(nprodname ); // print next product name
		Cell Solid_expec_Value_L1_print = sheet.getRow(row).getCell(4); 
		Solid_expec_Value_L1_print.setCellValue(Solid_Expec_Value_L1 ); // print expected L0 result into excel
		Cell Solid_expec_Value_L2_print = sheet.getRow(row).getCell(5); 
		Solid_expec_Value_L2_print.setCellValue(Solid_Expec_Value_L2); // print expected L2 result into excel
		Cell Solid_expec_Value_L3_print = sheet.getRow(row).getCell(6); 
		Solid_expec_Value_L3_print.setCellValue(Solid_Expec_Value_L3); // print expected L3 result into excel
		System.out.println("Expected L1: "+Solid_Expec_Value_L1);
		System.out.println("Expected L2: "+Solid_Expec_Value_L2);
		System.out.println("Expected L3: "+Solid_Expec_Value_L3);
		LowestExpectL3.add((float) Solid_Expec_Value_L3);
		row++;
		column++;
		}
			
		System.out.println("LowestExpectL3"+LowestExpectL3);
		float LowestoneExpectedL3 = Collections.min(LowestExpectL3);
		System.out.println("Min" +LowestoneExpectedL3);
		Cell Solid_expec_Value_L3_print = sheet.getRow(53).getCell(7); //print lowest L3 from iteration
		Solid_expec_Value_L3_print.setCellValue(LowestoneExpectedL3); // print expected L3 result into excel
				
				
		int getprodID = 0;
		float SFArea = 0,rinsevolume=0,swabarea = 0,swabamount=0;
		String eqname = null,prodname = null;
		
		//Get no of used Equipment in the Current product
		ResultSet productID = stmt.executeQuery("Select * from product where name = '" + CurrenProductName + "'"); // get product name id
			while (productID.next()) 
			{
				getprodID = productID.getInt(1);
				prodname = productID.getString(2); // get name id from product table
			} // closing for productID while loop 
			//equipment set id
			ResultSet productSetEqID = stmt.executeQuery("Select * from product_equipment_set_equipments where product_id='" + getprodID + "'"); // get equipment id
			Set<Integer> set = new HashSet<>(); // store multiple equipment id
		    while (productSetEqID.next()) {
		      set.add(productSetEqID.getInt(4));
		    	}
		    int i =93; //increment cell to print result
		    
		    for (Integer equipmentID:set) //get id from set
		    {
		    ResultSet EquipID = stmt.executeQuery("Select * from equipment where id= '" + equipmentID + "'"); // get product name id
		    		// print
		    	while(EquipID.next()) {  // print name and sf value from equipment table
		    	 eqname = EquipID.getString(9); // get name from database
				 SFArea = EquipID.getFloat(13); // get SF value from database
				 rinsevolume = EquipID.getFloat(17); // get SF value from database
				 swabarea = EquipID.getFloat(15); // get SF value from database
				 swabamount = EquipID.getFloat(16);
				 
				 if(swabArea()==0) //check swab area from uni setting or each equipment
				 {
					 Solid_Expec_Value_L4a =  LowestoneExpectedL3 * swabarea;
					 Cell equipRinse = sheet.getRow(i).getCell(16);//cell to print swab amount
					 equipRinse.setCellValue(swabarea); 
				 }else {
					 Solid_Expec_Value_L4a =  LowestoneExpectedL3 * swabArea();
					 Cell equipRinse = sheet.getRow(i).getCell(16);//cell to print swab amount
					 equipRinse.setCellValue(swabArea()); 
					 System.out.println("Solid_Expec_Value_L4a" +Solid_Expec_Value_L4a);
					 System.out.println("lowestL3"+LowestoneExpectedL3);
					 System.out.println("swabArea" +swabArea());
				 }
				 //swab amount
				 if(swabAmount()==0) //check swab amount from univ setting or each equipment
				 {
					 Solid_Expec_Value_L4b =  Solid_Expec_Value_L4a/ swabamount;
					 Cell equipRinse = sheet.getRow(i).getCell(17);//cell to print swab amount
					 equipRinse.setCellValue(swabamount); 
				 }else {
					 Solid_Expec_Value_L4b =  Solid_Expec_Value_L4a/ swabAmount();
					 Cell equipRinse = sheet.getRow(i).getCell(17);//cell to print swab amount
					 equipRinse.setCellValue(swabAmount()); 
				 }
				// equipment rinse volume()
				 if(eqRinseVolume()==0) //check rinset from univ setting or each equipment
				 {
					 L4cEquipment = (LowestoneExpectedL3 * SFArea) /(rinsevolume * 1000);//Calculate L4c equipment value
					 Cell equipRinse = sheet.getRow(i).getCell(18);//cell to print equipment rinse volume
					 equipRinse.setCellValue(rinsevolume); // print all the equipment rinse volume(used in the product) in excel
				 }else {
					 L4cEquipment = (LowestoneExpectedL3 * SFArea) /(eqRinseVolume() * 1000);//Calculate L4c equipment value
					 Cell equipRinse = sheet.getRow(i).getCell(18);//cell to print equipment rinse volume 
					 equipRinse.setCellValue(eqRinseVolume()); // print all the equipment rinse volume(used in the product) in excel
					 System.out.println("L4cEquipment"+L4cEquipment);
				 }
		    	 // Print Expected L4a, L4b, L4c value
		    	 	Cell equipName = sheet.getRow(i).getCell(14);//cell to print name 
		    		equipName.setCellValue(eqname); // print equipment name(used in the product) in excel
		    		Cell equipSF = sheet.getRow(i).getCell(15);//cell to print equipment surface area 
		    		equipSF.setCellValue(SFArea); // print all the equipment surface area(used in the product) in excel
		    		Cell L4aEquip = sheet.getRow(i).getCell(19);//cell to print L4a value 
		    		L4aEquip.setCellValue(Solid_Expec_Value_L4a); // print all the equipment surface area(used in the product) in excel
		    		Cell L4bEquip = sheet.getRow(i).getCell(20);//cell to print L4b value
		    		L4bEquip.setCellValue(Solid_Expec_Value_L4b); // print all the equipment surface area(used in the product) in excel
		    		
		    		// check whether rinse enabled in universal settings
		    		System.out.println("sampling_methodOption"+sampling_methodOption);
		    		System.out.println("RinseSampling"+RinseSampling);
		    			if(sampling_methodOption.equals("1,2") && RinseSampling==1) // if rinse enabled in sampling
		    			{
		    				Cell L4cEquip = sheet.getRow(i).getCell(21);
		    				L4cEquip.setCellValue(L4cEquipment); 
		    			}
		    			else {
		    				Cell L4cEquip = sheet.getRow(i).getCell(21);
		    				L4cEquip.setCellValue(0); 
		    				}
				 i++;
		    	} //closing equipmentID while loop 
		    	} //closing for loop
		    
		   
		    	
		    // Get Actual Limit Result from db
		    System.out.println("Actual result productid: "+getprodID);
		    ResultSet prod_active_relation = stmt.executeQuery("SELECT * FROM product_active_ingredient_relation where product_id='" + getprodID + "'");
			//get active multiple active id
			List<Integer> activelist = new ArrayList<>(); // get active list from above query
			while (prod_active_relation.next()) 
			{
			activelist.add(prod_active_relation.getInt(2));
			}
		  	
				 // get active name ,prod name and print in excel
				 ResultSet active = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+ activelist.get(1) + "'");
				 {
					 while (active.next()) 
					 {
						 if(limitDetermination()==2)
						 {
						 String activename = active.getString(2);
						 //print active name to excel
						 Cell NameofActive = sheet.getRow(91).getCell(15);
						 NameofActive.setCellValue(activename); // print active name into excel
						 Cell ActiveName = sheet.getRow(53).getCell(1);
						 ActiveName.setCellValue(activename); // print active name into excel
						 }else {
							 System.out.println(" lowest loop");
							 //print active name to excel
							 Cell NameofActive = sheet.getRow(91).getCell(15);
							 NameofActive.setCellValue(prodname); // print active name into excel
							 Cell ActiveName = sheet.getRow(53).getCell(1);
							 ActiveName.setCellValue(prodname); // print active name into excel
						 }
					 }
				 }
				 
				 //get product id
				 System.out.println("Prouct id: "+getprodID +" Active id: "+activelist.get(1));
				 //current product id 
				 List<Float> LowestActualL3 = new ArrayList<>(); 
				 int actualnextProdID = 0,actualresultrow = 54;
				 for(String NextprodNameactualresult : productList)
					{ 
					 float ActualL0Result = 1,ActualL1Result = 1,ActualL2Result = 1,ActualL3Result = 1;
					 ResultSet productdata = stmt.executeQuery("Select * from product where name ='"+NextprodNameactualresult+"' "); // get next prod name from excel and find out in db
						while (productdata.next()) {	actualnextProdID = productdata.getInt(1);    }
						
						System.out.println("actualnextProdID"+actualnextProdID);
						ResultSet prod_cal = stmt.executeQuery("SELECT * FROM product_calculation where product_id= '" + getprodID + "'&& next_prod_ids='"+  actualnextProdID+ "' && active_ingredient_id='"+  activelist.get(1)+ "'");
						//While Loop to iterate through all data and print results
						while (prod_cal.next()) {
							 ActualL0Result = prod_cal.getFloat(3); ActualL1Result = prod_cal.getFloat(4); ActualL2Result = prod_cal.getFloat(5); ActualL3Result = prod_cal.getFloat(6);
												}
						
						// Print Actual result to excel
						if(ActualL0Result==0)
								{
								Cell print_actual_L0 = sheet.getRow(actualresultrow).getCell(8); 
								print_actual_L0.setCellValue("NA"); // print actual L0 result into excel
								}else {
								Cell print_actual_L0 = sheet.getRow(actualresultrow).getCell(8); 
								print_actual_L0.setCellValue(ActualL0Result); // print actual L0 result into excel
								}			
						if(ActualL1Result==0)
						{
							Cell print_actual_L1 = sheet.getRow(actualresultrow).getCell(9); 
							print_actual_L1.setCellValue("NA"); // print actual L1 result into excel
						}else {
							Cell print_actual_L1 = sheet.getRow(actualresultrow).getCell(9); 
							print_actual_L1.setCellValue(ActualL1Result); // print actual L1 result into excel
						}		
						if(ActualL2Result==0)
						{
							Cell print_actual_L2 = sheet.getRow(actualresultrow).getCell(10); 
							print_actual_L2.setCellValue("NA"); // print actual L2 result into excel
						}else {
							Cell print_actual_L2 = sheet.getRow(actualresultrow).getCell(10); 
							print_actual_L2.setCellValue(ActualL2Result); // print actual L2 result into excel
						}
						if(ActualL3Result==0)
						{
							System.out.println("Zero");
							Cell print_actual_L3 = sheet.getRow(actualresultrow).getCell(11); 
							print_actual_L3.setCellValue("NA"); // print actual L3 result into excel
						}else {
							System.out.println("Not Zero");
							Cell print_actual_L3 = sheet.getRow(actualresultrow).getCell(11); 
							print_actual_L3.setCellValue(ActualL3Result); // print actual L3 result into excel
						}
						if(ActualL3Result!=0) {
						LowestActualL3.add((float) ActualL3Result);	
						}
						System.out.println("ActualL3Result"+ActualL3Result);
						actualresultrow++;
						
					}			
				
				 // Find minimum value from the actual results
					float LowestoneActualResult = Collections.min(LowestActualL3);
					System.out.println("LowestActualL3"+LowestActualL3);
					
					Cell printL3_result = sheet.getRow(53).getCell(12);
					printL3_result.setCellValue(LowestoneActualResult);
						
				//Compare both expected and actual result - applied round off for comparison
				if(Utils.toOptimizeDecimalPlacesRoundedOff(LowestoneActualResult).equals(Utils.toOptimizeDecimalPlacesRoundedOff(LowestoneExpectedL3)) )
				{
					Cell printlowestL3 = sheet.getRow(53).getCell(13);
					printlowestL3.setCellValue("Pass");
					printlowestL3.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
				}else
				{	Cell printlowestL3 = sheet.getRow(53).getCell(13);
					printlowestL3.setCellValue("Fail");
					printlowestL3.setCellStyle(Utils.style(workbook, "Fail")); // for print red font
				}
				
				
			//Get Actual L4a L4b L4c
			int j =93;
			float Ac_L4a = 0,Ac_L4b = 0,Ac_L4c = 0;
			for (Integer equipmentIDList:set) //get id from set
				   {
					System.out.println("equipmentIDList"+equipmentIDList);
					ResultSet ActualequipResult = stmt.executeQuery("SELECT * FROM product_calculation_equipment_results where product_id= '" + getprodID + "' && active_ingredient_id='"+  activelist.get(1)+ "' && equipment_id='"+equipmentIDList+"'");
					while (ActualequipResult.next()) {
					 Ac_L4a = ActualequipResult.getFloat(5); 
					 Ac_L4b = ActualequipResult.getFloat(6);
					 Ac_L4c = ActualequipResult.getFloat(7);
				Cell L4aEquip = sheet.getRow(j).getCell(22);//cell to print L4a value 
	    		L4aEquip.setCellValue(Ac_L4a); // print all the equipment surface area(used in the product) in excel
	    		Cell L4bEquip = sheet.getRow(j).getCell(23);//cell to print L4b value
	    		L4bEquip.setCellValue(Ac_L4b); // print all the equipment surface area(used in the product) in excel
	    		if(sampling_methodOption.equals("1,2")&& RinseSampling==1) // if rinse enabled in sampling
    			{
    				Cell L4cEquip = sheet.getRow(j).getCell(24);//cell to print L4b value
    	    		L4cEquip.setCellValue(Ac_L4c); // print all the equipment surface area(used in the product) in excel
    			}
    			else {
    				Cell L4cEquip = sheet.getRow(j).getCell(24);//cell to print L4b value
    	    		L4cEquip.setCellValue(0); // print all the equipment surface area(used in the product) in excel 
    				}
					}
					j++;
				 }
			
			// check expected L4a,L4b,L4c and actual L4a,L4b,L4c 
			int k= 93;
			for(Integer ss:set)
			{
			double EL4a = sheet.getRow(k).getCell(19).getNumericCellValue();
			double EL4b = sheet.getRow(k).getCell(20).getNumericCellValue();
			double EL4c = sheet.getRow(k).getCell(21).getNumericCellValue();
			double AL4a = sheet.getRow(k).getCell(22).getNumericCellValue();
			double AL4b = sheet.getRow(k).getCell(23).getNumericCellValue();
			double AL4c = sheet.getRow(k).getCell(24).getNumericCellValue();
			if(Utils.toOptimizeDecimalPlacesRoundedOff(EL4a).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4a)) && 
					Utils.toOptimizeDecimalPlacesRoundedOff(EL4b).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4b)) &&
					Utils.toOptimizeDecimalPlacesRoundedOff(EL4c).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4c)))
			{
				Cell verify_result = sheet.getRow(k).getCell(25);
				verify_result.setCellValue("Pass");
				verify_result.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
			}else
			{	
				Cell verify_result = sheet.getRow(k).getCell(25);
				verify_result.setCellValue("Fail");
				verify_result.setCellStyle(Utils.style(workbook, "Fail")); // for print red font
			}
			k++;
			}
		
		writeTooutputFile(workbook); // write output into work sheet
		connection.close();
		Thread.sleep(800);
		}// closing P1A1 calculation

	
	@Test(priority=5) // Current ProductName with First Active
	@Parameters({"productName1","productName2","productName3","productName4"})
	public void P3_Active1_patch(String productName1,String productName2,String productName3,String productName4) throws IOException, InterruptedException, SQLException, ClassNotFoundException 
	{
		 String CurrenProductName = productName3;
		 defaultValueSet(CurrenProductName);
		//XSSFWorkbook workbook;
		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result); 
		XSSFSheet sheet = workbook.getSheet("Patch_Calculation");
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();// Create Statement Object
				//next product id // Execute the SQL Query. Store results in Result Set // While Loop to iterate through all data and print results
				int nextProdID=0;
				
				 Set<String> productList = new HashSet<>(); 
				 productList.add(productName1);
				 productList.add(productName2);
				 productList.add(productName4);
				List<Float> LowestExpectL3 = new ArrayList<>();
				int row=58,column=9; //excel row and column
				
				for(String NextprodName : productList)
				{
					String nprodname =null;
				System.out.println("prodName"+NextprodName);
				ResultSet productdata = stmt.executeQuery("Select * from product where name ='"+NextprodName+"' "); // get next prod name from excel and find out in db
				while (productdata.next()) {	nextProdID = productdata.getInt(1); nprodname=productdata.getString(2);  maxDD = productdata.getFloat(8); minBatch = productdata.getFloat(9);   }

				if(limitDetermination()==2)
				{
				System.out.println("Lowest Limit");
				value_L1 = L0forpatch.calculate_P3_active1_L0(productName1, productName2, productName3, productName4) / maxDD;
				Cell Solid_expec_Value_L0_print = sheet.getRow(row).getCell(3); 
				Solid_expec_Value_L0_print.setCellValue(L0forpatch.calculate_P3_active1_L0(productName1, productName2, productName3, productName4)); // print expected L0 result into excel
				}
				if(limitDetermination()==1)
				{
				System.out.println("grouping approach");
				value_L1 = L0forpatch.groupingApproach_L0_p11(CurrenProductName) / maxDD;
				Cell Solid_expec_Value_L0_print = sheet.getRow(row).getCell(3); 
				Solid_expec_Value_L0_print.setCellValue(L0forpatch.groupingApproach_L0_p11(CurrenProductName)); // print expected L0 result into excel
				}
				value_L2 = value_L1 * minBatch * 1000 ; // Calculated L2 Value
				int sharedORLowest=0;
				ResultSet surfaceAreaOption = stmt.executeQuery("Select * from residue_limit"); //find surface area selection
			    while (surfaceAreaOption.next()) {
			      sharedORLowest =surfaceAreaOption.getInt(15);
			    	}
			    if(sharedORLowest==0)
			    {
			    	Solid_Total_surface_area =  SurfaceAreaValue.actualSharedbetween2(CurrenProductName,NextprodName); // Calculated L3 for actual shared
			    	Cell printsurfaceareaUsed = sheet.getRow(32).getCell(column); 
			    	printsurfaceareaUsed.setCellValue(SurfaceAreaValue.actualSharedbetween2(CurrenProductName,NextprodName));
			    }else
			    {
			    	Solid_Total_surface_area =  SurfaceAreaValue.lowestTrainbetween2(CurrenProductName,NextprodName); // Calculated L3 for lowest train between two
			    	Cell printsurfaceareaUsed = sheet.getRow(32).getCell(column); 
			    	printsurfaceareaUsed.setCellValue(SurfaceAreaValue.lowestTrainbetween2(CurrenProductName,NextprodName));
			    }
			    column++;
			    value_L3 = value_L2/Solid_Total_surface_area;
				
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
		Cell nextprodname = sheet.getRow(row).getCell(2); 
		nextprodname.setCellValue(nprodname ); // print next product name
		Cell Solid_expec_Value_L1_print = sheet.getRow(row).getCell(4); 
		Solid_expec_Value_L1_print.setCellValue(Solid_Expec_Value_L1 ); // print expected L0 result into excel
		Cell Solid_expec_Value_L2_print = sheet.getRow(row).getCell(5); 
		Solid_expec_Value_L2_print.setCellValue(Solid_Expec_Value_L2); // print expected L2 result into excel
		Cell Solid_expec_Value_L3_print = sheet.getRow(row).getCell(6); 
		Solid_expec_Value_L3_print.setCellValue(Solid_Expec_Value_L3); // print expected L3 result into excel
		System.out.println("Expected L1: "+Solid_Expec_Value_L1);
		System.out.println("Expected L2: "+Solid_Expec_Value_L2);
		System.out.println("Expected L3: "+Solid_Expec_Value_L3);
		LowestExpectL3.add((float) Solid_Expec_Value_L3);
		row++;
		column++;
		}
			
		System.out.println("LowestExpectL3"+LowestExpectL3);
		float LowestoneExpectedL3 = Collections.min(LowestExpectL3);
		System.out.println("Min" +LowestoneExpectedL3);
		Cell Solid_expec_Value_L3_print = sheet.getRow(57).getCell(7); //print lowest L3 from iteration
		Solid_expec_Value_L3_print.setCellValue(LowestoneExpectedL3); // print expected L3 result into excel
				
				
		int getprodID = 0;
		float SFArea = 0,rinsevolume=0,swabarea = 0,swabamount=0;
		String eqname = null,prodname = null;
		
		//Get no of used Equipment in the Current product
		ResultSet productID = stmt.executeQuery("Select * from product where name = '" + CurrenProductName + "'"); // get product name id
			while (productID.next()) 
			{
				getprodID = productID.getInt(1);
				prodname = productID.getString(2); // get name id from product table
			} // closing for productID while loop 
			//equipment set id
			ResultSet productSetEqID = stmt.executeQuery("Select * from product_equipment_set_equipments where product_id='" + getprodID + "'"); // get equipment id
			Set<Integer> set = new HashSet<>(); // store multiple equipment id
		    while (productSetEqID.next()) {
		      set.add(productSetEqID.getInt(4));
		    	}
		    
		    int i =105; //increment cell to print result
		    
		    for (Integer equipmentID:set) //get id from set
		    {
		    ResultSet EquipID = stmt.executeQuery("Select * from equipment where id= '" + equipmentID + "'"); // get product name id
		    		// print
		    	while(EquipID.next()) {  // print name and sf value from equipment table
		    	 eqname = EquipID.getString(9); // get name from database
				 SFArea = EquipID.getFloat(13); // get SF value from database
				 rinsevolume = EquipID.getFloat(17); // get SF value from database
				 swabarea = EquipID.getFloat(15); // get SF value from database
				 swabamount = EquipID.getFloat(16);
				 
				 if(swabArea()==0) //check swab area from uni setting or each equipment
				 {
					 Solid_Expec_Value_L4a =  LowestoneExpectedL3 * swabarea;
					 Cell equipRinse = sheet.getRow(i).getCell(3);//cell to print swab amount
					 equipRinse.setCellValue(swabarea); 
				 }else {
					 Solid_Expec_Value_L4a =  LowestoneExpectedL3 * swabArea();
					 Cell equipRinse = sheet.getRow(i).getCell(3);//cell to print swab amount
					 equipRinse.setCellValue(swabArea()); 
					 System.out.println("Solid_Expec_Value_L4a" +Solid_Expec_Value_L4a);
					 System.out.println("lowestL3"+LowestoneExpectedL3);
					 System.out.println("swabArea" +swabArea());
				 }
				 //swab amount
				 if(swabAmount()==0) //check swab amount from univ setting or each equipment
				 {
					 Solid_Expec_Value_L4b =  Solid_Expec_Value_L4a/ swabamount;
					 Cell equipRinse = sheet.getRow(i).getCell(4);//cell to print swab amount
					 equipRinse.setCellValue(swabamount); 
				 }else {
					 Solid_Expec_Value_L4b =  Solid_Expec_Value_L4a/ swabAmount();
					 Cell equipRinse = sheet.getRow(i).getCell(4);//cell to print swab amount
					 equipRinse.setCellValue(swabAmount()); 
				 }
				// equipment rinse volume()
				 if(eqRinseVolume()==0) //check rinset from univ setting or each equipment
				 {
					 L4cEquipment = (LowestoneExpectedL3 * SFArea) /(rinsevolume * 1000);//Calculate L4c equipment value
					 Cell equipRinse = sheet.getRow(i).getCell(5);//cell to print equipment rinse volume
					 equipRinse.setCellValue(rinsevolume); // print all the equipment rinse volume(used in the product) in excel
				 }else {
					 L4cEquipment = (LowestoneExpectedL3 * SFArea) /(eqRinseVolume() * 1000);//Calculate L4c equipment value
					 Cell equipRinse = sheet.getRow(i).getCell(5);//cell to print equipment rinse volume 
					 equipRinse.setCellValue(eqRinseVolume()); // print all the equipment rinse volume(used in the product) in excel
					 System.out.println("L4cEquipment"+L4cEquipment);
				 }
		    	 // Print Expected L4a, L4b, L4c value
		    	 	Cell equipName = sheet.getRow(i).getCell(1);//cell to print name 
		    		equipName.setCellValue(eqname); // print equipment name(used in the product) in excel
		    		Cell equipSF = sheet.getRow(i).getCell(2);//cell to print equipment surface area 
		    		equipSF.setCellValue(SFArea); // print all the equipment surface area(used in the product) in excel
		    		Cell L4aEquip = sheet.getRow(i).getCell(6);//cell to print L4a value 
		    		L4aEquip.setCellValue(Solid_Expec_Value_L4a); // print all the equipment surface area(used in the product) in excel
		    		Cell L4bEquip = sheet.getRow(i).getCell(7);//cell to print L4b value
		    		L4bEquip.setCellValue(Solid_Expec_Value_L4b); // print all the equipment surface area(used in the product) in excel
		    		
		    		// check whether rinse enabled in universal settings
		    		System.out.println("sampling_methodOption"+sampling_methodOption);
		    		System.out.println("RinseSampling"+RinseSampling);
		    			if(sampling_methodOption.equals("1,2") && RinseSampling==1) // if rinse enabled in sampling
		    			{
		    				Cell L4cEquip = sheet.getRow(i).getCell(8);
		    				L4cEquip.setCellValue(L4cEquipment); 
		    			}
		    			else {
		    				Cell L4cEquip = sheet.getRow(i).getCell(8);
		    				L4cEquip.setCellValue(0); 
		    				}
				 i++;
		    	} //closing equipmentID while loop 
		    	} //closing for loop
		    
		    // Get Actual Limit Result from db
		    System.out.println("Actual result productid: "+getprodID);
		    ResultSet prod_active_relation = stmt.executeQuery("SELECT * FROM product_active_ingredient_relation where product_id='" + getprodID + "'");
			//get active multiple active id
			List<Integer> activelist = new ArrayList<>(); // get active list from above query
			while (prod_active_relation.next()) 
			{
			activelist.add(prod_active_relation.getInt(2));
			}
				 // get active name ,prod name and print in excel
				 ResultSet active = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+ activelist.get(0) + "'");
				 {
					 while (active.next()) 
					 {
						 if(limitDetermination()==2)
						 {
						 String activename = active.getString(2);
						 //print active name to excel
						 Cell NameofActive = sheet.getRow(103).getCell(2);
						 NameofActive.setCellValue(activename); // print active name into excel
						 Cell ActiveName = sheet.getRow(57).getCell(1);
						 ActiveName.setCellValue(activename); // print active name into excel
						 }else {
							 System.out.println(" lowest loop");
							 //print active name to excel
							 Cell NameofActive = sheet.getRow(103).getCell(2);
							 NameofActive.setCellValue(prodname); // print active name into excel
							 Cell ActiveName = sheet.getRow(57).getCell(1);
							 ActiveName.setCellValue(prodname); // print active name into excel
						 }
					 }
				 }
				 //get product id
				 System.out.println("Prouct id: "+getprodID +" Active id: "+activelist.get(0));
				 //current product id 
				 List<Float> LowestActualL3 = new ArrayList<>(); 
				 int actualnextProdID = 0;
				 int actualresultrow = 58;
				 for(String NextprodNameactualresult : productList)
					{ 
					 float ActualL0Result = 1,ActualL1Result = 1,ActualL2Result = 1,ActualL3Result = 1;
					 ResultSet productdata = stmt.executeQuery("Select * from product where name ='"+NextprodNameactualresult+"' "); // get next prod name from excel and find out in db
						while (productdata.next()) {	actualnextProdID = productdata.getInt(1);    }
						
						System.out.println("actualnextProdID"+actualnextProdID);
						ResultSet prod_cal = stmt.executeQuery("SELECT * FROM product_calculation where product_id= '" + getprodID + "'&& next_prod_ids='"+  actualnextProdID+ "' && active_ingredient_id='"+  activelist.get(0)+ "'");
						//While Loop to iterate through all data and print results
						while (prod_cal.next()) {
							 ActualL0Result = prod_cal.getFloat(3); ActualL1Result = prod_cal.getFloat(4); ActualL2Result = prod_cal.getFloat(5); ActualL3Result = prod_cal.getFloat(6);
												}
						// Print Actual result to excel
						if(ActualL0Result==0)
								{
								Cell print_actual_L0 = sheet.getRow(actualresultrow).getCell(8); 
								print_actual_L0.setCellValue("NA"); // print actual L0 result into excel
								}else {
								Cell print_actual_L0 = sheet.getRow(actualresultrow).getCell(8); 
								print_actual_L0.setCellValue(ActualL0Result); // print actual L0 result into excel
								}			
						if(ActualL1Result==0)
						{
							Cell print_actual_L1 = sheet.getRow(actualresultrow).getCell(9); 
							print_actual_L1.setCellValue("NA"); // print actual L1 result into excel
						}else {
							Cell print_actual_L1 = sheet.getRow(actualresultrow).getCell(9); 
							print_actual_L1.setCellValue(ActualL1Result); // print actual L1 result into excel
						}		
						if(ActualL2Result==0)
						{
							Cell print_actual_L2 = sheet.getRow(actualresultrow).getCell(10); 
							print_actual_L2.setCellValue("NA"); // print actual L2 result into excel
						}else {
							Cell print_actual_L2 = sheet.getRow(actualresultrow).getCell(10); 
							print_actual_L2.setCellValue(ActualL2Result); // print actual L2 result into excel
						}
						if(ActualL3Result==0)
						{
							System.out.println("Zero");
							Cell print_actual_L3 = sheet.getRow(actualresultrow).getCell(11); 
							print_actual_L3.setCellValue("NA"); // print actual L3 result into excel
						}else {
							System.out.println("Not Zero");
							Cell print_actual_L3 = sheet.getRow(actualresultrow).getCell(11); 
							print_actual_L3.setCellValue(ActualL3Result); // print actual L3 result into excel
						}
						if(ActualL3Result!=0) {
						LowestActualL3.add((float) ActualL3Result);	
						}
						System.out.println("ActualL3Result"+ActualL3Result);
						actualresultrow++;
					}			
				
				 // Find minimum value from the actual results
					float LowestoneActualResult = Collections.min(LowestActualL3);
					System.out.println("LowestActualL3"+LowestActualL3);
					
					Cell printL3_result = sheet.getRow(57).getCell(12);
					printL3_result.setCellValue(LowestoneActualResult);
						
				//Compare both expected and actual result - applied round off for comparison
				if(Utils.toOptimizeDecimalPlacesRoundedOff(LowestoneActualResult).equals(Utils.toOptimizeDecimalPlacesRoundedOff(LowestoneExpectedL3)) )
				{
					Cell printlowestL3 = sheet.getRow(57).getCell(13);
					printlowestL3.setCellValue("Pass");
					printlowestL3.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
				}else
				{	Cell printlowestL3 = sheet.getRow(57).getCell(13);
					printlowestL3.setCellValue("Fail");
					printlowestL3.setCellStyle(Utils.style(workbook, "Fail")); // for print red font
				}
				
			//Get Actual L4a L4b L4c
			int j =105;
			float Ac_L4a = 0,Ac_L4b = 0,Ac_L4c = 0;
			for (Integer equipmentIDList:set) //get id from set
				   {
					System.out.println("equipmentIDList"+equipmentIDList);
					ResultSet ActualequipResult = stmt.executeQuery("SELECT * FROM product_calculation_equipment_results where product_id= '" + getprodID + "' && active_ingredient_id='"+  activelist.get(0)+ "' && equipment_id='"+equipmentIDList+"'");
					while (ActualequipResult.next()) {
					 Ac_L4a = ActualequipResult.getFloat(5); 
					 Ac_L4b = ActualequipResult.getFloat(6);
					 Ac_L4c = ActualequipResult.getFloat(7);
				Cell L4aEquip = sheet.getRow(j).getCell(9);//cell to print L4a value 
	    		L4aEquip.setCellValue(Ac_L4a); // print all the equipment surface area(used in the product) in excel
	    		Cell L4bEquip = sheet.getRow(j).getCell(10);//cell to print L4b value
	    		L4bEquip.setCellValue(Ac_L4b); // print all the equipment surface area(used in the product) in excel
	    		if(sampling_methodOption.equals("1,2")&& RinseSampling==1) // if rinse enabled in sampling
    			{
    				Cell L4cEquip = sheet.getRow(j).getCell(11);//cell to print L4b value
    	    		L4cEquip.setCellValue(Ac_L4c); // print all the equipment surface area(used in the product) in excel
    			}
    			else {
    				Cell L4cEquip = sheet.getRow(j).getCell(11);//cell to print L4b value
    	    		L4cEquip.setCellValue(0); // print all the equipment surface area(used in the product) in excel 
    				}
					}
					j++;
				 }
			
			// check expected L4a,L4b,L4c and actual L4a,L4b,L4c 
			int k= 105;
			for(Integer ss:set)
			{
			double EL4a = sheet.getRow(k).getCell(6).getNumericCellValue();
			double EL4b = sheet.getRow(k).getCell(7).getNumericCellValue();
			double EL4c = sheet.getRow(k).getCell(8).getNumericCellValue();
			double AL4a = sheet.getRow(k).getCell(9).getNumericCellValue();
			double AL4b = sheet.getRow(k).getCell(10).getNumericCellValue();
			double AL4c = sheet.getRow(k).getCell(11).getNumericCellValue();
			if(Utils.toOptimizeDecimalPlacesRoundedOff(EL4a).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4a)) && 
					Utils.toOptimizeDecimalPlacesRoundedOff(EL4b).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4b)) &&
					Utils.toOptimizeDecimalPlacesRoundedOff(EL4c).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4c)))
			{
				Cell verify_result = sheet.getRow(k).getCell(12);
				verify_result.setCellValue("Pass");
				verify_result.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
			}else
			{	
				Cell verify_result = sheet.getRow(k).getCell(12);
				verify_result.setCellValue("Fail");
				verify_result.setCellStyle(Utils.style(workbook, "Fail")); // for print red font
			}
			k++;
			}
		
		writeTooutputFile(workbook); // write output into work sheet
		connection.close();
		Thread.sleep(800);
		}// closing P1A1 calculation

	
	

	@Test(priority=6) // current productname P2 with second active
	@Parameters({"productName1","productName2","productName3","productName4"})
	public void P3_Active2_patch(String productName1,String productName2,String productName3,String productName4) throws IOException, InterruptedException, SQLException, ClassNotFoundException 
	{
		 String CurrenProductName= productName3;
		defaultValueSet(CurrenProductName);
		//XSSFWorkbook workbook;
		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result); 
		XSSFSheet sheet = workbook.getSheet("Patch_Calculation");
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();// Create Statement Object
				//next product id // Execute the SQL Query. Store results in Result Set // While Loop to iterate through all data and print results
					int nextProdID=0;
				
				 Set<String> productList = new HashSet<>(); 
				 productList.add(productName1);
				 productList.add(productName2);
				 productList.add(productName4);
				List<Float> LowestExpectL3 = new ArrayList<>();
				int row=62,column=9; //excel row and column
				
				for(String NextprodName : productList)
				{
					String nprodname =null;
					System.out.println("prodName"+NextprodName);
				ResultSet productdata = stmt.executeQuery("Select * from product where name ='"+NextprodName+"' "); // get next prod name from excel and find out in db
				while (productdata.next()) {	nextProdID = productdata.getInt(1); nprodname = productdata.getString(2); maxDD = productdata.getFloat(8); minBatch = productdata.getFloat(9);   }

				if(limitDetermination()==2)
				{
				System.out.println("Lowest Limit");
				value_L1 = L0forpatch.calculate_P3_active2_L0(productName1, productName2, productName3, productName4) / maxDD;
				Cell Solid_expec_Value_L0_print = sheet.getRow(row).getCell(3); 
				Solid_expec_Value_L0_print.setCellValue(L0forpatch.calculate_P3_active2_L0(productName1, productName2, productName3, productName4)); // print expected L0 result into excel
				
				}
				if(limitDetermination()==1)
				{
				System.out.println("grouping approach");
				value_L1 = L0forpatch.groupingApproach_L0_p11(CurrenProductName) / maxDD;
				Cell Solid_expec_Value_L0_print = sheet.getRow(row).getCell(3); 
				Solid_expec_Value_L0_print.setCellValue(L0forpatch.groupingApproach_L0_p11(CurrenProductName)); // print expected L0 result into excel
				
				}
				value_L2 = value_L1 * minBatch * 1000 ; // Calculated L2 Value
				int sharedORLowest=0;
				ResultSet surfaceAreaOption = stmt.executeQuery("Select * from residue_limit"); //find surface area selection
			    while (surfaceAreaOption.next()) {
			      sharedORLowest =surfaceAreaOption.getInt(15);
			    	}
			    if(sharedORLowest==0)
			    {
			    	Solid_Total_surface_area =  SurfaceAreaValue.actualSharedbetween2(CurrenProductName,NextprodName); // Calculated L3 for actual shared
			    }else
			    {
			    	Solid_Total_surface_area =  SurfaceAreaValue.lowestTrainbetween2(CurrenProductName,NextprodName); // Calculated L3 for lowest train between two
			    }
				
			    value_L3 = value_L2/Solid_Total_surface_area;
			    
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
		Cell nextprodname = sheet.getRow(row).getCell(2); 
		nextprodname.setCellValue(nprodname ); // print next product name
		Cell Solid_expec_Value_L1_print = sheet.getRow(row).getCell(4); 
		Solid_expec_Value_L1_print.setCellValue(Solid_Expec_Value_L1 ); // print expected L0 result into excel
		Cell Solid_expec_Value_L2_print = sheet.getRow(row).getCell(5); 
		Solid_expec_Value_L2_print.setCellValue(Solid_Expec_Value_L2); // print expected L2 result into excel
		Cell Solid_expec_Value_L3_print = sheet.getRow(row).getCell(6); 
		Solid_expec_Value_L3_print.setCellValue(Solid_Expec_Value_L3); // print expected L3 result into excel
		System.out.println("Expected L1: "+Solid_Expec_Value_L1);
		System.out.println("Expected L2: "+Solid_Expec_Value_L2);
		System.out.println("Expected L3: "+Solid_Expec_Value_L3);
		LowestExpectL3.add((float) Solid_Expec_Value_L3);
		row++;
		column++;
		}
			
		System.out.println("LowestExpectL3"+LowestExpectL3);
		float LowestoneExpectedL3 = Collections.min(LowestExpectL3);
		System.out.println("Min" +LowestoneExpectedL3);
		Cell Solid_expec_Value_L3_print = sheet.getRow(61).getCell(7); //print lowest L3 from iteration
		Solid_expec_Value_L3_print.setCellValue(LowestoneExpectedL3); // print expected L3 result into excel
				
				
		int getprodID = 0;
		float SFArea = 0,rinsevolume=0,swabarea = 0,swabamount=0;
		String eqname = null,prodname = null;
		
		//Get no of used Equipment in the Current product
		ResultSet productID = stmt.executeQuery("Select * from product where name = '" + CurrenProductName + "'"); // get product name id
			while (productID.next()) 
			{
				getprodID = productID.getInt(1);
				prodname = productID.getString(2); // get name id from product table
			} // closing for productID while loop 
			//equipment set id
			ResultSet productSetEqID = stmt.executeQuery("Select * from product_equipment_set_equipments where product_id='" + getprodID + "'"); // get equipment id
			Set<Integer> set = new HashSet<>(); // store multiple equipment id
		    while (productSetEqID.next()) {
		      set.add(productSetEqID.getInt(4));
		    	}
		    int i =105; //increment cell to print result
		    
		    for (Integer equipmentID:set) //get id from set
		    {
		    ResultSet EquipID = stmt.executeQuery("Select * from equipment where id= '" + equipmentID + "'"); // get product name id
		    		// print
		    	while(EquipID.next()) {  // print name and sf value from equipment table
		    	 eqname = EquipID.getString(9); // get name from database
				 SFArea = EquipID.getFloat(13); // get SF value from database
				 rinsevolume = EquipID.getFloat(17); // get SF value from database
				 swabarea = EquipID.getFloat(15); // get SF value from database
				 swabamount = EquipID.getFloat(16);
				 
				 if(swabArea()==0) //check swab area from uni setting or each equipment
				 {
					 Solid_Expec_Value_L4a =  LowestoneExpectedL3 * swabarea;
					 Cell equipRinse = sheet.getRow(i).getCell(16);//cell to print swab amount
					 equipRinse.setCellValue(swabarea); 
				 }else {
					 Solid_Expec_Value_L4a =  LowestoneExpectedL3 * swabArea();
					 Cell equipRinse = sheet.getRow(i).getCell(16);//cell to print swab amount
					 equipRinse.setCellValue(swabArea()); 
					 System.out.println("Solid_Expec_Value_L4a" +Solid_Expec_Value_L4a);
					 System.out.println("lowestL3"+LowestoneExpectedL3);
					 System.out.println("swabArea" +swabArea());
				 }
				 //swab amount
				 if(swabAmount()==0) //check swab amount from univ setting or each equipment
				 {
					 Solid_Expec_Value_L4b =  Solid_Expec_Value_L4a/ swabamount;
					 Cell equipRinse = sheet.getRow(i).getCell(17);//cell to print swab amount
					 equipRinse.setCellValue(swabamount); 
				 }else {
					 Solid_Expec_Value_L4b =  Solid_Expec_Value_L4a/ swabAmount();
					 Cell equipRinse = sheet.getRow(i).getCell(17);//cell to print swab amount
					 equipRinse.setCellValue(swabAmount()); 
				 }
				// equipment rinse volume()
				 if(eqRinseVolume()==0) //check rinset from univ setting or each equipment
				 {
					 L4cEquipment = (LowestoneExpectedL3 * SFArea) /(rinsevolume * 1000);//Calculate L4c equipment value
					 Cell equipRinse = sheet.getRow(i).getCell(18);//cell to print equipment rinse volume
					 equipRinse.setCellValue(rinsevolume); // print all the equipment rinse volume(used in the product) in excel
				 }else {
					 L4cEquipment = (LowestoneExpectedL3 * SFArea) /(eqRinseVolume() * 1000);//Calculate L4c equipment value
					 Cell equipRinse = sheet.getRow(i).getCell(18);//cell to print equipment rinse volume 
					 equipRinse.setCellValue(eqRinseVolume()); // print all the equipment rinse volume(used in the product) in excel
					 System.out.println("L4cEquipment"+L4cEquipment);
				 }
		    	 // Print Expected L4a, L4b, L4c value
		    	 	Cell equipName = sheet.getRow(i).getCell(14);//cell to print name 
		    		equipName.setCellValue(eqname); // print equipment name(used in the product) in excel
		    		Cell equipSF = sheet.getRow(i).getCell(15);//cell to print equipment surface area 
		    		equipSF.setCellValue(SFArea); // print all the equipment surface area(used in the product) in excel
		    		Cell L4aEquip = sheet.getRow(i).getCell(19);//cell to print L4a value 
		    		L4aEquip.setCellValue(Solid_Expec_Value_L4a); // print all the equipment surface area(used in the product) in excel
		    		Cell L4bEquip = sheet.getRow(i).getCell(20);//cell to print L4b value
		    		L4bEquip.setCellValue(Solid_Expec_Value_L4b); // print all the equipment surface area(used in the product) in excel
		    		
		    		// check whether rinse enabled in universal settings
		    		System.out.println("sampling_methodOption"+sampling_methodOption);
		    		System.out.println("RinseSampling"+RinseSampling);
		    			if(sampling_methodOption.equals("1,2") && RinseSampling==1) // if rinse enabled in sampling
		    			{
		    				Cell L4cEquip = sheet.getRow(i).getCell(21);
		    				L4cEquip.setCellValue(L4cEquipment); 
		    			}
		    			else {
		    				Cell L4cEquip = sheet.getRow(i).getCell(21);
		    				L4cEquip.setCellValue(0); 
		    				}
				 i++;
		    	} //closing equipmentID while loop 
		    	} //closing for loop
		    
		   
		    	
		    // Get Actual Limit Result from db
		    System.out.println("Actual result productid: "+getprodID);
		    ResultSet prod_active_relation = stmt.executeQuery("SELECT * FROM product_active_ingredient_relation where product_id='" + getprodID + "'");
			//get active multiple active id
			List<Integer> activelist = new ArrayList<>(); // get active list from above query
			while (prod_active_relation.next()) 
			{
			activelist.add(prod_active_relation.getInt(2));
			}
		  	
				 // get active name ,prod name and print in excel
				 ResultSet active = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+ activelist.get(1) + "'");
				 {
					 while (active.next()) 
					 {
						 if(limitDetermination()==2)
						 {
						 String activename = active.getString(2);
						 //print active name to excel
						 Cell NameofActive = sheet.getRow(103).getCell(15);
						 NameofActive.setCellValue(activename); // print active name into excel
						 Cell ActiveName = sheet.getRow(61).getCell(1);
						 ActiveName.setCellValue(activename); // print active name into excel
						 }else {
							 System.out.println(" lowest loop");
							 //print active name to excel
							 Cell NameofActive = sheet.getRow(103).getCell(15);
							 NameofActive.setCellValue(prodname); // print active name into excel
							 Cell ActiveName = sheet.getRow(61).getCell(1);
							 ActiveName.setCellValue(prodname); // print active name into excel
						 }
					 }
				 }
				 
				 //get product id
				 System.out.println("Prouct id: "+getprodID +" Active id: "+activelist.get(1));
				 //current product id 
				 List<Float> LowestActualL3 = new ArrayList<>(); 
				 int actualnextProdID = 0,actualresultrow = 62;
				 for(String NextprodNameactualresult : productList)
					{ 
					 float ActualL0Result = 1,ActualL1Result = 1,ActualL2Result = 1,ActualL3Result = 1;
					 ResultSet productdata = stmt.executeQuery("Select * from product where name ='"+NextprodNameactualresult+"' "); // get next prod name from excel and find out in db
						while (productdata.next()) {	actualnextProdID = productdata.getInt(1);    }
						
						System.out.println("actualnextProdID"+actualnextProdID);
						ResultSet prod_cal = stmt.executeQuery("SELECT * FROM product_calculation where product_id= '" + getprodID + "'&& next_prod_ids='"+  actualnextProdID+ "' && active_ingredient_id='"+  activelist.get(1)+ "'");
						//While Loop to iterate through all data and print results
						while (prod_cal.next()) {
							 ActualL0Result = prod_cal.getFloat(3); ActualL1Result = prod_cal.getFloat(4); ActualL2Result = prod_cal.getFloat(5); ActualL3Result = prod_cal.getFloat(6);
												}
						
						// Print Actual result to excel
						if(ActualL0Result==0)
								{
								Cell print_actual_L0 = sheet.getRow(actualresultrow).getCell(8); 
								print_actual_L0.setCellValue("NA"); // print actual L0 result into excel
								}else {
								Cell print_actual_L0 = sheet.getRow(actualresultrow).getCell(8); 
								print_actual_L0.setCellValue(ActualL0Result); // print actual L0 result into excel
								}			
						if(ActualL1Result==0)
						{
							Cell print_actual_L1 = sheet.getRow(actualresultrow).getCell(9); 
							print_actual_L1.setCellValue("NA"); // print actual L1 result into excel
						}else {
							Cell print_actual_L1 = sheet.getRow(actualresultrow).getCell(9); 
							print_actual_L1.setCellValue(ActualL1Result); // print actual L1 result into excel
						}		
						if(ActualL2Result==0)
						{
							Cell print_actual_L2 = sheet.getRow(actualresultrow).getCell(10); 
							print_actual_L2.setCellValue("NA"); // print actual L2 result into excel
						}else {
							Cell print_actual_L2 = sheet.getRow(actualresultrow).getCell(10); 
							print_actual_L2.setCellValue(ActualL2Result); // print actual L2 result into excel
						}
						if(ActualL3Result==0)
						{
							System.out.println("Zero");
							Cell print_actual_L3 = sheet.getRow(actualresultrow).getCell(11); 
							print_actual_L3.setCellValue("NA"); // print actual L3 result into excel
						}else {
							System.out.println("Not Zero");
							Cell print_actual_L3 = sheet.getRow(actualresultrow).getCell(11); 
							print_actual_L3.setCellValue(ActualL3Result); // print actual L3 result into excel
						}
						if(ActualL3Result!=0) {
						LowestActualL3.add((float) ActualL3Result);	
						}
						System.out.println("ActualL3Result"+ActualL3Result);
						actualresultrow++;
						
					}			
				
				 // Find minimum value from the actual results
					float LowestoneActualResult = Collections.min(LowestActualL3);
					System.out.println("LowestActualL3"+LowestActualL3);
					
					Cell printL3_result = sheet.getRow(61).getCell(12);
					printL3_result.setCellValue(LowestoneActualResult);
						
				//Compare both expected and actual result - applied round off for comparison
				if(Utils.toOptimizeDecimalPlacesRoundedOff(LowestoneActualResult).equals(Utils.toOptimizeDecimalPlacesRoundedOff(LowestoneExpectedL3)) )
				{
					Cell printlowestL3 = sheet.getRow(61).getCell(13);
					printlowestL3.setCellValue("Pass");
					printlowestL3.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
				}else
				{	Cell printlowestL3 = sheet.getRow(61).getCell(13);
					printlowestL3.setCellValue("Fail");
					printlowestL3.setCellStyle(Utils.style(workbook, "Fail")); // for print red font
				}
				
				
			//Get Actual L4a L4b L4c
			int j =105;
			float Ac_L4a = 0,Ac_L4b = 0,Ac_L4c = 0;
			for (Integer equipmentIDList:set) //get id from set
				   {
					System.out.println("equipmentIDList"+equipmentIDList);
					ResultSet ActualequipResult = stmt.executeQuery("SELECT * FROM product_calculation_equipment_results where product_id= '" + getprodID + "' && active_ingredient_id='"+  activelist.get(1)+ "' && equipment_id='"+equipmentIDList+"'");
					while (ActualequipResult.next()) {
					 Ac_L4a = ActualequipResult.getFloat(5); 
					 Ac_L4b = ActualequipResult.getFloat(6);
					 Ac_L4c = ActualequipResult.getFloat(7);
				Cell L4aEquip = sheet.getRow(j).getCell(22);//cell to print L4a value 
	    		L4aEquip.setCellValue(Ac_L4a); // print all the equipment surface area(used in the product) in excel
	    		Cell L4bEquip = sheet.getRow(j).getCell(23);//cell to print L4b value
	    		L4bEquip.setCellValue(Ac_L4b); // print all the equipment surface area(used in the product) in excel
	    		if(sampling_methodOption.equals("1,2")&& RinseSampling==1) // if rinse enabled in sampling
    			{
    				Cell L4cEquip = sheet.getRow(j).getCell(24);//cell to print L4b value
    	    		L4cEquip.setCellValue(Ac_L4c); // print all the equipment surface area(used in the product) in excel
    			}
    			else {
    				Cell L4cEquip = sheet.getRow(j).getCell(24);//cell to print L4b value
    	    		L4cEquip.setCellValue(0); // print all the equipment surface area(used in the product) in excel 
    				}
					}
					j++;
				 }
			
			// check expected L4a,L4b,L4c and actual L4a,L4b,L4c 
			int k= 105;
			for(Integer ss:set)
			{
			double EL4a = sheet.getRow(k).getCell(19).getNumericCellValue();
			double EL4b = sheet.getRow(k).getCell(20).getNumericCellValue();
			double EL4c = sheet.getRow(k).getCell(21).getNumericCellValue();
			double AL4a = sheet.getRow(k).getCell(22).getNumericCellValue();
			double AL4b = sheet.getRow(k).getCell(23).getNumericCellValue();
			double AL4c = sheet.getRow(k).getCell(24).getNumericCellValue();
			if(Utils.toOptimizeDecimalPlacesRoundedOff(EL4a).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4a)) && 
					Utils.toOptimizeDecimalPlacesRoundedOff(EL4b).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4b)) &&
					Utils.toOptimizeDecimalPlacesRoundedOff(EL4c).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4c)))
			{
				Cell verify_result = sheet.getRow(k).getCell(25);
				verify_result.setCellValue("Pass");
				verify_result.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
			}else
			{	
				Cell verify_result = sheet.getRow(k).getCell(25);
				verify_result.setCellValue("Fail");
				verify_result.setCellStyle(Utils.style(workbook, "Fail")); // for print red font
			}
			k++;
			}
		
		writeTooutputFile(workbook); // write output into work sheet
		connection.close();
		Thread.sleep(800);
		}

	
	

	@Test(priority=7) // Current ProductName with First Active
	@Parameters({"productName1","productName2","productName3","productName4"})
	public void P4_Active1_patch(String productName1,String productName2,String productName3,String productName4) throws IOException, InterruptedException, SQLException, ClassNotFoundException 
	{
		 String CurrenProductName = productName4;
		defaultValueSet(CurrenProductName);
		//XSSFWorkbook workbook;
		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result); 
		XSSFSheet sheet = workbook.getSheet("Patch_Calculation");
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();// Create Statement Object
				//next product id // Execute the SQL Query. Store results in Result Set // While Loop to iterate through all data and print results
				int nextProdID=0;
				
				 Set<String> productList = new HashSet<>(); 
				 productList.add(productName1);
				 productList.add(productName2);
				 productList.add(productName3);
				List<Float> LowestExpectL3 = new ArrayList<>();
				int row=66,column=9; //excel row and column
				
				System.out.println("-----------> p44");
				for(String NextprodName : productList)
				{
					String nprodname= null;
				System.out.println("prodName"+NextprodName);
				ResultSet productdata = stmt.executeQuery("Select * from product where name ='"+NextprodName+"' "); // get next prod name from excel and find out in db
				while (productdata.next()) {	nextProdID = productdata.getInt(1); nprodname =productdata.getString(2); maxDD = productdata.getFloat(8); minBatch = productdata.getFloat(9);   }

				if(limitDetermination()==2)
				{
				System.out.println("Lowest Limit");
				value_L1 = L0forpatch.calculate_P4_active1_L0(productName1, productName2, productName3, productName4) / maxDD;
				Cell Solid_expec_Value_L0_print = sheet.getRow(row).getCell(3); 
				Solid_expec_Value_L0_print.setCellValue(L0forpatch.calculate_P4_active1_L0(productName1, productName2, productName3, productName4)); // print expected L0 result into excel
				}
				if(limitDetermination()==1)
				{
				System.out.println("grouping approach");
				value_L1 = L0forpatch.groupingApproach_L0_p11(CurrenProductName) / maxDD;
				Cell Solid_expec_Value_L0_print = sheet.getRow(row).getCell(3); 
				Solid_expec_Value_L0_print.setCellValue(L0forpatch.groupingApproach_L0_p11(CurrenProductName)); // print expected L0 result into excel
				}
				value_L2 = value_L1 * minBatch * 1000 ; // Calculated L2 Value
				int sharedORLowest=0;
				ResultSet surfaceAreaOption = stmt.executeQuery("Select * from residue_limit"); //find surface area selection
			    while (surfaceAreaOption.next()) {
			      sharedORLowest =surfaceAreaOption.getInt(15);
			    	}
			    if(sharedORLowest==0)
			    {
			    	Solid_Total_surface_area =  SurfaceAreaValue.actualSharedbetween2(CurrenProductName,NextprodName); // Calculated L3 for actual shared
			    	Cell printsurfaceareaUsed = sheet.getRow(34).getCell(column); 
			    	printsurfaceareaUsed.setCellValue(SurfaceAreaValue.actualSharedbetween2(CurrenProductName,NextprodName));
			    }else
			    {
			    	Solid_Total_surface_area =  SurfaceAreaValue.lowestTrainbetween2(CurrenProductName,NextprodName); // Calculated L3 for lowest train between two
			    	Cell printsurfaceareaUsed = sheet.getRow(34).getCell(column); 
			    	printsurfaceareaUsed.setCellValue(SurfaceAreaValue.lowestTrainbetween2(CurrenProductName,NextprodName));
			    }
			    value_L3 = value_L2/Solid_Total_surface_area;
				
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
		Cell nextprodname = sheet.getRow(row).getCell(2); 
		nextprodname.setCellValue(nprodname ); // print next product name
		Cell Solid_expec_Value_L1_print = sheet.getRow(row).getCell(4); 
		Solid_expec_Value_L1_print.setCellValue(Solid_Expec_Value_L1 ); // print expected L0 result into excel
		Cell Solid_expec_Value_L2_print = sheet.getRow(row).getCell(5); 
		Solid_expec_Value_L2_print.setCellValue(Solid_Expec_Value_L2); // print expected L2 result into excel
		Cell Solid_expec_Value_L3_print = sheet.getRow(row).getCell(6); 
		Solid_expec_Value_L3_print.setCellValue(Solid_Expec_Value_L3); // print expected L3 result into excel
		System.out.println("Expected L1: "+Solid_Expec_Value_L1);
		System.out.println("Expected L2: "+Solid_Expec_Value_L2);
		System.out.println("Expected L3: "+Solid_Expec_Value_L3);
		LowestExpectL3.add((float) Solid_Expec_Value_L3);
		row++;
		column++;
		}
			
		System.out.println("LowestExpectL3"+LowestExpectL3);
		float LowestoneExpectedL3 = Collections.min(LowestExpectL3);
		System.out.println("Min" +LowestoneExpectedL3);
		Cell Solid_expec_Value_L3_print = sheet.getRow(65).getCell(7); //print lowest L3 from iteration
		Solid_expec_Value_L3_print.setCellValue(LowestoneExpectedL3); // print expected L3 result into excel
				
				
		int getprodID = 0;
		float SFArea = 0,rinsevolume=0,swabarea = 0,swabamount=0;
		String eqname = null,prodname = null;
		
		//Get no of used Equipment in the Current product
		ResultSet productID = stmt.executeQuery("Select * from product where name = '" + CurrenProductName + "'"); // get product name id
			while (productID.next()) 
			{
				getprodID = productID.getInt(1);
				prodname = productID.getString(2); // get name id from product table
			} // closing for productID while loop 
			//equipment set id
			ResultSet productSetEqID = stmt.executeQuery("Select * from product_equipment_set_equipments where product_id='" + getprodID + "'"); // get equipment id
			Set<Integer> set = new HashSet<>(); // store multiple equipment id
		    while (productSetEqID.next()) {
		      set.add(productSetEqID.getInt(4));
		    	}
		    
		    int i =117; //increment cell to print result
		    
		    for (Integer equipmentID:set) //get id from set
		    {
		    ResultSet EquipID = stmt.executeQuery("Select * from equipment where id= '" + equipmentID + "'"); // get product name id
		    		// print
		    	while(EquipID.next()) {  // print name and sf value from equipment table
		    	 eqname = EquipID.getString(9); // get name from database
				 SFArea = EquipID.getFloat(13); // get SF value from database
				 rinsevolume = EquipID.getFloat(17); // get SF value from database
				 swabarea = EquipID.getFloat(15); // get SF value from database
				 swabamount = EquipID.getFloat(16);
				 
				 if(swabArea()==0) //check swab area from uni setting or each equipment
				 {
					 Solid_Expec_Value_L4a =  LowestoneExpectedL3 * swabarea;
					 Cell equipRinse = sheet.getRow(i).getCell(3);//cell to print swab amount
					 equipRinse.setCellValue(swabarea); 
				 }else {
					 Solid_Expec_Value_L4a =  LowestoneExpectedL3 * swabArea();
					 Cell equipRinse = sheet.getRow(i).getCell(3);//cell to print swab amount
					 equipRinse.setCellValue(swabArea()); 
					 System.out.println("Solid_Expec_Value_L4a" +Solid_Expec_Value_L4a);
					 System.out.println("lowestL3"+LowestoneExpectedL3);
					 System.out.println("swabArea" +swabArea());
				 }
				 //swab amount
				 if(swabAmount()==0) //check swab amount from univ setting or each equipment
				 {
					 Solid_Expec_Value_L4b =  Solid_Expec_Value_L4a/ swabamount;
					 Cell equipRinse = sheet.getRow(i).getCell(4);//cell to print swab amount
					 equipRinse.setCellValue(swabamount); 
				 }else {
					 Solid_Expec_Value_L4b =  Solid_Expec_Value_L4a/ swabAmount();
					 Cell equipRinse = sheet.getRow(i).getCell(4);//cell to print swab amount
					 equipRinse.setCellValue(swabAmount()); 
				 }
				// equipment rinse volume()
				 if(eqRinseVolume()==0) //check rinset from univ setting or each equipment
				 {
					 L4cEquipment = (LowestoneExpectedL3 * SFArea) /(rinsevolume * 1000);//Calculate L4c equipment value
					 Cell equipRinse = sheet.getRow(i).getCell(5);//cell to print equipment rinse volume
					 equipRinse.setCellValue(rinsevolume); // print all the equipment rinse volume(used in the product) in excel
				 }else {
					 L4cEquipment = (LowestoneExpectedL3 * SFArea) /(eqRinseVolume() * 1000);//Calculate L4c equipment value
					 Cell equipRinse = sheet.getRow(i).getCell(5);//cell to print equipment rinse volume 
					 equipRinse.setCellValue(eqRinseVolume()); // print all the equipment rinse volume(used in the product) in excel
					 System.out.println("L4cEquipment"+L4cEquipment);
				 }
		    	 // Print Expected L4a, L4b, L4c value
		    	 	Cell equipName = sheet.getRow(i).getCell(1);//cell to print name 
		    		equipName.setCellValue(eqname); // print equipment name(used in the product) in excel
		    		Cell equipSF = sheet.getRow(i).getCell(2);//cell to print equipment surface area 
		    		equipSF.setCellValue(SFArea); // print all the equipment surface area(used in the product) in excel
		    		Cell L4aEquip = sheet.getRow(i).getCell(6);//cell to print L4a value 
		    		L4aEquip.setCellValue(Solid_Expec_Value_L4a); // print all the equipment surface area(used in the product) in excel
		    		Cell L4bEquip = sheet.getRow(i).getCell(7);//cell to print L4b value
		    		L4bEquip.setCellValue(Solid_Expec_Value_L4b); // print all the equipment surface area(used in the product) in excel
		    		
		    		// check whether rinse enabled in universal settings
		    		System.out.println("sampling_methodOption"+sampling_methodOption);
		    		System.out.println("RinseSampling"+RinseSampling);
		    			if(sampling_methodOption.equals("1,2") && RinseSampling==1) // if rinse enabled in sampling
		    			{
		    				Cell L4cEquip = sheet.getRow(i).getCell(8);
		    				L4cEquip.setCellValue(L4cEquipment); 
		    			}
		    			else {
		    				Cell L4cEquip = sheet.getRow(i).getCell(8);
		    				L4cEquip.setCellValue(0); 
		    				}
				 i++;
		    	} //closing equipmentID while loop 
		    	} //closing for loop
		    
		    // Get Actual Limit Result from db
		    System.out.println("Actual result productid: "+getprodID);
		    ResultSet prod_active_relation = stmt.executeQuery("SELECT * FROM product_active_ingredient_relation where product_id='" + getprodID + "'");
			//get active multiple active id
			List<Integer> activelist = new ArrayList<>(); // get active list from above query
			while (prod_active_relation.next()) 
			{
			activelist.add(prod_active_relation.getInt(2));
			}
				 // get active name ,prod name and print in excel
				 ResultSet active = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+ activelist.get(0) + "'");
				 {
					 while (active.next()) 
					 {
						 if(limitDetermination()==2)
						 {
						 String activename = active.getString(2);
						 //print active name to excel
						 Cell NameofActive = sheet.getRow(115).getCell(2);
						 NameofActive.setCellValue(activename); // print active name into excel
						 Cell ActiveName = sheet.getRow(65).getCell(1);
						 ActiveName.setCellValue(activename); // print active name into excel
						 }else {
							 System.out.println(" lowest loop");
							 //print active name to excel
							 Cell NameofActive = sheet.getRow(115).getCell(2);
							 NameofActive.setCellValue(prodname); // print active name into excel
							 Cell ActiveName = sheet.getRow(65).getCell(1);
							 ActiveName.setCellValue(prodname); // print active name into excel
						 }
					 }
				 }
				 //get product id
				 System.out.println("Prouct id: "+getprodID +" Active id: "+activelist.get(0));
				 //current product id 
				 List<Float> LowestActualL3 = new ArrayList<>(); 
				 int actualnextProdID = 0;
				 int actualresultrow = 66;
				 for(String NextprodNameactualresult : productList)
					{ 
					 float ActualL0Result = 1,ActualL1Result = 1,ActualL2Result = 1,ActualL3Result = 1;
					 ResultSet productdata = stmt.executeQuery("Select * from product where name ='"+NextprodNameactualresult+"' "); // get next prod name from excel and find out in db
						while (productdata.next()) {	actualnextProdID = productdata.getInt(1);    }
						
						System.out.println("actualnextProdID"+actualnextProdID);
						ResultSet prod_cal = stmt.executeQuery("SELECT * FROM product_calculation where product_id= '" + getprodID + "'&& next_prod_ids='"+  actualnextProdID+ "' && active_ingredient_id='"+  activelist.get(0)+ "'");
						//While Loop to iterate through all data and print results
						while (prod_cal.next()) {
							 ActualL0Result = prod_cal.getFloat(3); ActualL1Result = prod_cal.getFloat(4); ActualL2Result = prod_cal.getFloat(5); ActualL3Result = prod_cal.getFloat(6);
												}
						// Print Actual result to excel
						if(ActualL0Result==0)
								{
								Cell print_actual_L0 = sheet.getRow(actualresultrow).getCell(8); 
								print_actual_L0.setCellValue("NA"); // print actual L0 result into excel
								}else {
								Cell print_actual_L0 = sheet.getRow(actualresultrow).getCell(8); 
								print_actual_L0.setCellValue(ActualL0Result); // print actual L0 result into excel
								}			
						if(ActualL1Result==0)
						{
							Cell print_actual_L1 = sheet.getRow(actualresultrow).getCell(9); 
							print_actual_L1.setCellValue("NA"); // print actual L1 result into excel
						}else {
							Cell print_actual_L1 = sheet.getRow(actualresultrow).getCell(9); 
							print_actual_L1.setCellValue(ActualL1Result); // print actual L1 result into excel
						}		
						if(ActualL2Result==0)
						{
							Cell print_actual_L2 = sheet.getRow(actualresultrow).getCell(10); 
							print_actual_L2.setCellValue("NA"); // print actual L2 result into excel
						}else {
							Cell print_actual_L2 = sheet.getRow(actualresultrow).getCell(10); 
							print_actual_L2.setCellValue(ActualL2Result); // print actual L2 result into excel
						}
						if(ActualL3Result==0)
						{
							System.out.println("Zero");
							Cell print_actual_L3 = sheet.getRow(actualresultrow).getCell(11); 
							print_actual_L3.setCellValue("NA"); // print actual L3 result into excel
						}else {
							System.out.println("Not Zero");
							Cell print_actual_L3 = sheet.getRow(actualresultrow).getCell(11); 
							print_actual_L3.setCellValue(ActualL3Result); // print actual L3 result into excel
						}
						if(ActualL3Result!=0) {
						LowestActualL3.add((float) ActualL3Result);	
						}
						System.out.println("ActualL3Result"+ActualL3Result);
						actualresultrow++;
					}			
				
				 // Find minimum value from the actual results
					float LowestoneActualResult = Collections.min(LowestActualL3);
					System.out.println("LowestActualL3"+LowestActualL3);
					
					Cell printL3_result = sheet.getRow(65).getCell(12);
					printL3_result.setCellValue(LowestoneActualResult);
						
				//Compare both expected and actual result - applied round off for comparison
				if(Utils.toOptimizeDecimalPlacesRoundedOff(LowestoneActualResult).equals(Utils.toOptimizeDecimalPlacesRoundedOff(LowestoneExpectedL3)) )
				{
					Cell printlowestL3 = sheet.getRow(65).getCell(13);
					printlowestL3.setCellValue("Pass");
					printlowestL3.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
				}else
				{	Cell printlowestL3 = sheet.getRow(65).getCell(13);
					printlowestL3.setCellValue("Fail");
					printlowestL3.setCellStyle(Utils.style(workbook, "Fail")); // for print red font
				}
				
			//Get Actual L4a L4b L4c
			int j =117;
			float Ac_L4a = 0,Ac_L4b = 0,Ac_L4c = 0;
			for (Integer equipmentIDList:set) //get id from set
				   {
					System.out.println("equipmentIDList"+equipmentIDList);
					ResultSet ActualequipResult = stmt.executeQuery("SELECT * FROM product_calculation_equipment_results where product_id= '" + getprodID + "' && active_ingredient_id='"+  activelist.get(0)+ "' && equipment_id='"+equipmentIDList+"'");
					while (ActualequipResult.next()) {
					 Ac_L4a = ActualequipResult.getFloat(5); 
					 Ac_L4b = ActualequipResult.getFloat(6);
					 Ac_L4c = ActualequipResult.getFloat(7);
				Cell L4aEquip = sheet.getRow(j).getCell(9);//cell to print L4a value 
	    		L4aEquip.setCellValue(Ac_L4a); // print all the equipment surface area(used in the product) in excel
	    		Cell L4bEquip = sheet.getRow(j).getCell(10);//cell to print L4b value
	    		L4bEquip.setCellValue(Ac_L4b); // print all the equipment surface area(used in the product) in excel
	    		if(sampling_methodOption.equals("1,2")&& RinseSampling==1) // if rinse enabled in sampling
    			{
    				Cell L4cEquip = sheet.getRow(j).getCell(11);//cell to print L4b value
    	    		L4cEquip.setCellValue(Ac_L4c); // print all the equipment surface area(used in the product) in excel
    			}
    			else {
    				Cell L4cEquip = sheet.getRow(j).getCell(11);//cell to print L4b value
    	    		L4cEquip.setCellValue(0); // print all the equipment surface area(used in the product) in excel 
    				}
					}
					j++;
				 }
			
			// check expected L4a,L4b,L4c and actual L4a,L4b,L4c 
			int k= 117;
			for(Integer ss:set)
			{
			double EL4a = sheet.getRow(k).getCell(6).getNumericCellValue();
			double EL4b = sheet.getRow(k).getCell(7).getNumericCellValue();
			double EL4c = sheet.getRow(k).getCell(8).getNumericCellValue();
			double AL4a = sheet.getRow(k).getCell(9).getNumericCellValue();
			double AL4b = sheet.getRow(k).getCell(10).getNumericCellValue();
			double AL4c = sheet.getRow(k).getCell(11).getNumericCellValue();
			if(Utils.toOptimizeDecimalPlacesRoundedOff(EL4a).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4a)) && 
					Utils.toOptimizeDecimalPlacesRoundedOff(EL4b).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4b)) &&
					Utils.toOptimizeDecimalPlacesRoundedOff(EL4c).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4c)))
			{
				Cell verify_result = sheet.getRow(k).getCell(12);
				verify_result.setCellValue("Pass");
				verify_result.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
			}else
			{	
				Cell verify_result = sheet.getRow(k).getCell(12);
				verify_result.setCellValue("Fail");
				verify_result.setCellStyle(Utils.style(workbook, "Fail")); // for print red font
			}
			k++;
			}
		
		writeTooutputFile(workbook); // write output into work sheet
		connection.close();
		Thread.sleep(800);
		}// closing P1A1 calculation

	
	
	

	@Test(priority=8) // current productname P2 with second active
	@Parameters({"productName1","productName2","productName3","productName4"})
	public void P4_Active2_patch(String productName1,String productName2,String productName3,String productName4) throws IOException, InterruptedException, SQLException, ClassNotFoundException 
	{
		String CurrenProductName = productName4;
		defaultValueSet(CurrenProductName);
		//XSSFWorkbook workbook;
		XSSFWorkbook workbook = Utils.getExcelSheet(Constant.EXCEL_PATH_Result); 
		XSSFSheet sheet = workbook.getSheet("Patch_Calculation");
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();// Create Statement Object
				//next product id // Execute the SQL Query. Store results in Result Set // While Loop to iterate through all data and print results
				int nextProdID=0;
				
				 Set<String> productList = new HashSet<>(); 
				 productList.add(productName1);
				 productList.add(productName2);
				 productList.add(productName3);
				List<Float> LowestExpectL3 = new ArrayList<>();
				int row=70,column=9; //excel row and column
				
				for(String NextprodName : productList)
				{
					String nprodname =null;
					System.out.println("prodName"+NextprodName);
				ResultSet productdata = stmt.executeQuery("Select * from product where name ='"+NextprodName+"' "); // get next prod name from excel and find out in db
				while (productdata.next()) {	nextProdID = productdata.getInt(1); nprodname =productdata.getString(2); maxDD = productdata.getFloat(8); minBatch = productdata.getFloat(9);   }

				if(limitDetermination()==2)
				{
				System.out.println("Lowest Limit");
				value_L1 = L0forpatch.calculate_P4_active2_L0(productName1, productName2, productName3, productName4) / maxDD;
				Cell Solid_expec_Value_L0_print = sheet.getRow(row).getCell(3); 
				Solid_expec_Value_L0_print.setCellValue(L0forpatch.calculate_P4_active2_L0(productName1, productName2, productName3, productName4)); // print expected L0 result into excel
				
				}
				if(limitDetermination()==1)
				{
				System.out.println("grouping approach");
				value_L1 = L0forpatch.groupingApproach_L0_p11(CurrenProductName) / maxDD;
				Cell Solid_expec_Value_L0_print = sheet.getRow(row).getCell(3); 
				Solid_expec_Value_L0_print.setCellValue(L0forpatch.groupingApproach_L0_p11(CurrenProductName)); // print expected L0 result into excel
				
				}
				value_L2 = value_L1 * minBatch * 1000 ; // Calculated L2 Value
				int sharedORLowest=0;
				ResultSet surfaceAreaOption = stmt.executeQuery("Select * from residue_limit"); //find surface area selection
			    while (surfaceAreaOption.next()) {
			      sharedORLowest =surfaceAreaOption.getInt(15);
			    	}
			    if(sharedORLowest==0)
			    {
			    	Solid_Total_surface_area =  SurfaceAreaValue.actualSharedbetween2(CurrenProductName,NextprodName); // Calculated L3 for actual shared
			    }else
			    {
			    	Solid_Total_surface_area =  SurfaceAreaValue.lowestTrainbetween2(CurrenProductName,NextprodName); // Calculated L3 for lowest train between two
			    }
			    
			    value_L3 = value_L2/Solid_Total_surface_area;
				
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
		Cell nextprodname = sheet.getRow(row).getCell(2); 
		nextprodname.setCellValue(nprodname ); // print next product name
		Cell Solid_expec_Value_L1_print = sheet.getRow(row).getCell(4); 
		Solid_expec_Value_L1_print.setCellValue(Solid_Expec_Value_L1 ); // print expected L0 result into excel
		Cell Solid_expec_Value_L2_print = sheet.getRow(row).getCell(5); 
		Solid_expec_Value_L2_print.setCellValue(Solid_Expec_Value_L2); // print expected L2 result into excel
		Cell Solid_expec_Value_L3_print = sheet.getRow(row).getCell(6); 
		Solid_expec_Value_L3_print.setCellValue(Solid_Expec_Value_L3); // print expected L3 result into excel
		System.out.println("Expected L1: "+Solid_Expec_Value_L1);
		System.out.println("Expected L2: "+Solid_Expec_Value_L2);
		System.out.println("Expected L3: "+Solid_Expec_Value_L3);
		LowestExpectL3.add((float) Solid_Expec_Value_L3);
		row++;
		column++;
		}
			
		System.out.println("LowestExpectL3"+LowestExpectL3);
		float LowestoneExpectedL3 = Collections.min(LowestExpectL3);
		System.out.println("Min" +LowestoneExpectedL3);
		Cell Solid_expec_Value_L3_print = sheet.getRow(69).getCell(7); //print lowest L3 from iteration
		Solid_expec_Value_L3_print.setCellValue(LowestoneExpectedL3); // print expected L3 result into excel
				
				
		int getprodID = 0;
		float SFArea = 0,rinsevolume=0,swabarea = 0,swabamount=0;
		String eqname = null,prodname = null;
		
		//Get no of used Equipment in the Current product
		ResultSet productID = stmt.executeQuery("Select * from product where name = '" + CurrenProductName + "'"); // get product name id
			while (productID.next()) 
			{
				getprodID = productID.getInt(1);
				prodname = productID.getString(2); // get name id from product table
			} // closing for productID while loop 
			//equipment set id
			ResultSet productSetEqID = stmt.executeQuery("Select * from product_equipment_set_equipments where product_id='" + getprodID + "'"); // get equipment id
			Set<Integer> set = new HashSet<>(); // store multiple equipment id
		    while (productSetEqID.next()) {
		      set.add(productSetEqID.getInt(4));
		    	}
		    int i =117; //increment cell to print result
		    
		    for (Integer equipmentID:set) //get id from set
		    {
		    ResultSet EquipID = stmt.executeQuery("Select * from equipment where id= '" + equipmentID + "'"); // get product name id
		    		// print
		    	while(EquipID.next()) {  // print name and sf value from equipment table
		    	 eqname = EquipID.getString(9); // get name from database
				 SFArea = EquipID.getFloat(13); // get SF value from database
				 rinsevolume = EquipID.getFloat(17); // get SF value from database
				 swabarea = EquipID.getFloat(15); // get SF value from database
				 swabamount = EquipID.getFloat(16);
				 
				 if(swabArea()==0) //check swab area from uni setting or each equipment
				 {
					 Solid_Expec_Value_L4a =  LowestoneExpectedL3 * swabarea;
					 Cell equipRinse = sheet.getRow(i).getCell(16);//cell to print swab amount
					 equipRinse.setCellValue(swabarea); 
				 }else {
					 Solid_Expec_Value_L4a =  LowestoneExpectedL3 * swabArea();
					 Cell equipRinse = sheet.getRow(i).getCell(16);//cell to print swab amount
					 equipRinse.setCellValue(swabArea()); 
					 System.out.println("Solid_Expec_Value_L4a" +Solid_Expec_Value_L4a);
					 System.out.println("lowestL3"+LowestoneExpectedL3);
					 System.out.println("swabArea" +swabArea());
				 }
				 //swab amount
				 if(swabAmount()==0) //check swab amount from univ setting or each equipment
				 {
					 Solid_Expec_Value_L4b =  Solid_Expec_Value_L4a/ swabamount;
					 Cell equipRinse = sheet.getRow(i).getCell(17);//cell to print swab amount
					 equipRinse.setCellValue(swabamount); 
				 }else {
					 Solid_Expec_Value_L4b =  Solid_Expec_Value_L4a/ swabAmount();
					 Cell equipRinse = sheet.getRow(i).getCell(17);//cell to print swab amount
					 equipRinse.setCellValue(swabAmount()); 
				 }
				// equipment rinse volume()
				 if(eqRinseVolume()==0) //check rinset from univ setting or each equipment
				 {
					 L4cEquipment = (LowestoneExpectedL3 * SFArea) /(rinsevolume * 1000);//Calculate L4c equipment value
					 Cell equipRinse = sheet.getRow(i).getCell(18);//cell to print equipment rinse volume
					 equipRinse.setCellValue(rinsevolume); // print all the equipment rinse volume(used in the product) in excel
				 }else {
					 L4cEquipment = (LowestoneExpectedL3 * SFArea) /(eqRinseVolume() * 1000);//Calculate L4c equipment value
					 Cell equipRinse = sheet.getRow(i).getCell(18);//cell to print equipment rinse volume 
					 equipRinse.setCellValue(eqRinseVolume()); // print all the equipment rinse volume(used in the product) in excel
					 System.out.println("L4cEquipment"+L4cEquipment);
				 }
		    	 // Print Expected L4a, L4b, L4c value
		    	 	Cell equipName = sheet.getRow(i).getCell(14);//cell to print name 
		    		equipName.setCellValue(eqname); // print equipment name(used in the product) in excel
		    		Cell equipSF = sheet.getRow(i).getCell(15);//cell to print equipment surface area 
		    		equipSF.setCellValue(SFArea); // print all the equipment surface area(used in the product) in excel
		    		Cell L4aEquip = sheet.getRow(i).getCell(19);//cell to print L4a value 
		    		L4aEquip.setCellValue(Solid_Expec_Value_L4a); // print all the equipment surface area(used in the product) in excel
		    		Cell L4bEquip = sheet.getRow(i).getCell(20);//cell to print L4b value
		    		L4bEquip.setCellValue(Solid_Expec_Value_L4b); // print all the equipment surface area(used in the product) in excel
		    		
		    		// check whether rinse enabled in universal settings
		    		System.out.println("sampling_methodOption"+sampling_methodOption);
		    		System.out.println("RinseSampling"+RinseSampling);
		    			if(sampling_methodOption.equals("1,2") && RinseSampling==1) // if rinse enabled in sampling
		    			{
		    				Cell L4cEquip = sheet.getRow(i).getCell(21);
		    				L4cEquip.setCellValue(L4cEquipment); 
		    			}
		    			else {
		    				Cell L4cEquip = sheet.getRow(i).getCell(21);
		    				L4cEquip.setCellValue(0); 
		    				}
				 i++;
		    	} //closing equipmentID while loop 
		    	} //closing for loop
		    
		   
		    	
		    // Get Actual Limit Result from db
		    System.out.println("Actual result productid: "+getprodID);
		    ResultSet prod_active_relation = stmt.executeQuery("SELECT * FROM product_active_ingredient_relation where product_id='" + getprodID + "'");
			//get active multiple active id
			List<Integer> activelist = new ArrayList<>(); // get active list from above query
			while (prod_active_relation.next()) 
			{
			activelist.add(prod_active_relation.getInt(2));
			}
		  	
				 // get active name ,prod name and print in excel
				 ResultSet active = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+ activelist.get(1) + "'");
				 {
					 while (active.next()) 
					 {
						 if(limitDetermination()==2)
						 {
						 String activename = active.getString(2);
						 //print active name to excel
						 Cell NameofActive = sheet.getRow(115).getCell(15);
						 NameofActive.setCellValue(activename); // print active name into excel
						 Cell ActiveName = sheet.getRow(69).getCell(1);
						 ActiveName.setCellValue(activename); // print active name into excel
						 }else {
							 System.out.println(" lowest loop");
							 //print active name to excel
							 Cell NameofActive = sheet.getRow(115).getCell(15);
							 NameofActive.setCellValue(prodname); // print active name into excel
							 Cell ActiveName = sheet.getRow(69).getCell(1);
							 ActiveName.setCellValue(prodname); // print active name into excel
						 }
					 }
				 }
				 
				 //get product id
				 System.out.println("Prouct id: "+getprodID +" Active id: "+activelist.get(1));
				 //current product id 
				 List<Float> LowestActualL3 = new ArrayList<>(); 
				 int actualnextProdID = 0,actualresultrow = 70;
				 for(String NextprodNameactualresult : productList)
					{ 
					 float ActualL0Result = 1,ActualL1Result = 1,ActualL2Result = 1,ActualL3Result = 1;
					 ResultSet productdata = stmt.executeQuery("Select * from product where name ='"+NextprodNameactualresult+"' "); // get next prod name from excel and find out in db
						while (productdata.next()) {	actualnextProdID = productdata.getInt(1);    }
						
						System.out.println("actualnextProdID"+actualnextProdID);
						ResultSet prod_cal = stmt.executeQuery("SELECT * FROM product_calculation where product_id= '" + getprodID + "'&& next_prod_ids='"+  actualnextProdID+ "' && active_ingredient_id='"+  activelist.get(1)+ "'");
						//While Loop to iterate through all data and print results
						while (prod_cal.next()) {
							 ActualL0Result = prod_cal.getFloat(3); ActualL1Result = prod_cal.getFloat(4); ActualL2Result = prod_cal.getFloat(5); ActualL3Result = prod_cal.getFloat(6);
												}
						
						// Print Actual result to excel
						if(ActualL0Result==0)
								{
								Cell print_actual_L0 = sheet.getRow(actualresultrow).getCell(8); 
								print_actual_L0.setCellValue("NA"); // print actual L0 result into excel
								}else {
								Cell print_actual_L0 = sheet.getRow(actualresultrow).getCell(8); 
								print_actual_L0.setCellValue(ActualL0Result); // print actual L0 result into excel
								}			
						if(ActualL1Result==0)
						{
							Cell print_actual_L1 = sheet.getRow(actualresultrow).getCell(9); 
							print_actual_L1.setCellValue("NA"); // print actual L1 result into excel
						}else {
							Cell print_actual_L1 = sheet.getRow(actualresultrow).getCell(9); 
							print_actual_L1.setCellValue(ActualL1Result); // print actual L1 result into excel
						}		
						if(ActualL2Result==0)
						{
							Cell print_actual_L2 = sheet.getRow(actualresultrow).getCell(10); 
							print_actual_L2.setCellValue("NA"); // print actual L2 result into excel
						}else {
							Cell print_actual_L2 = sheet.getRow(actualresultrow).getCell(10); 
							print_actual_L2.setCellValue(ActualL2Result); // print actual L2 result into excel
						}
						if(ActualL3Result==0)
						{
							System.out.println("Zero");
							Cell print_actual_L3 = sheet.getRow(actualresultrow).getCell(11); 
							print_actual_L3.setCellValue("NA"); // print actual L3 result into excel
						}else {
							System.out.println("Not Zero");
							Cell print_actual_L3 = sheet.getRow(actualresultrow).getCell(11); 
							print_actual_L3.setCellValue(ActualL3Result); // print actual L3 result into excel
						}
						if(ActualL3Result!=0) {
						LowestActualL3.add((float) ActualL3Result);	
						}
						System.out.println("ActualL3Result"+ActualL3Result);
						actualresultrow++;
						
					}			
				
				 // Find minimum value from the actual results
					float LowestoneActualResult = Collections.min(LowestActualL3);
					System.out.println("LowestActualL3"+LowestActualL3);
					
					Cell printL3_result = sheet.getRow(69).getCell(12);
					printL3_result.setCellValue(LowestoneActualResult);
						
				//Compare both expected and actual result - applied round off for comparison
				if(Utils.toOptimizeDecimalPlacesRoundedOff(LowestoneActualResult).equals(Utils.toOptimizeDecimalPlacesRoundedOff(LowestoneExpectedL3)) )
				{
					Cell printlowestL3 = sheet.getRow(69).getCell(13);
					printlowestL3.setCellValue("Pass");
					printlowestL3.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
				}else
				{	Cell printlowestL3 = sheet.getRow(69).getCell(13);
					printlowestL3.setCellValue("Fail");
					printlowestL3.setCellStyle(Utils.style(workbook, "Fail")); // for print red font
				}
				
				
			//Get Actual L4a L4b L4c
			int j =117;
			float Ac_L4a = 0,Ac_L4b = 0,Ac_L4c = 0;
			for (Integer equipmentIDList:set) //get id from set
				   {
					System.out.println("equipmentIDList"+equipmentIDList);
					ResultSet ActualequipResult = stmt.executeQuery("SELECT * FROM product_calculation_equipment_results where product_id= '" + getprodID + "' && active_ingredient_id='"+  activelist.get(1)+ "' && equipment_id='"+equipmentIDList+"'");
					while (ActualequipResult.next()) {
					 Ac_L4a = ActualequipResult.getFloat(5); 
					 Ac_L4b = ActualequipResult.getFloat(6);
					 Ac_L4c = ActualequipResult.getFloat(7);
				Cell L4aEquip = sheet.getRow(j).getCell(22);//cell to print L4a value 
	    		L4aEquip.setCellValue(Ac_L4a); // print all the equipment surface area(used in the product) in excel
	    		Cell L4bEquip = sheet.getRow(j).getCell(23);//cell to print L4b value
	    		L4bEquip.setCellValue(Ac_L4b); // print all the equipment surface area(used in the product) in excel
	    		if(sampling_methodOption.equals("1,2")&& RinseSampling==1) // if rinse enabled in sampling
    			{
    				Cell L4cEquip = sheet.getRow(j).getCell(24);//cell to print L4b value
    	    		L4cEquip.setCellValue(Ac_L4c); // print all the equipment surface area(used in the product) in excel
    			}
    			else {
    				Cell L4cEquip = sheet.getRow(j).getCell(24);//cell to print L4b value
    	    		L4cEquip.setCellValue(0); // print all the equipment surface area(used in the product) in excel 
    				}
					}
					j++;
				 }
			
			// check expected L4a,L4b,L4c and actual L4a,L4b,L4c 
			int k= 117;
			for(Integer ss:set)
			{
			double EL4a = sheet.getRow(k).getCell(19).getNumericCellValue();
			double EL4b = sheet.getRow(k).getCell(20).getNumericCellValue();
			double EL4c = sheet.getRow(k).getCell(21).getNumericCellValue();
			double AL4a = sheet.getRow(k).getCell(22).getNumericCellValue();
			double AL4b = sheet.getRow(k).getCell(23).getNumericCellValue();
			double AL4c = sheet.getRow(k).getCell(24).getNumericCellValue();
			if(Utils.toOptimizeDecimalPlacesRoundedOff(EL4a).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4a)) && 
					Utils.toOptimizeDecimalPlacesRoundedOff(EL4b).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4b)) &&
					Utils.toOptimizeDecimalPlacesRoundedOff(EL4c).equals(Utils.toOptimizeDecimalPlacesRoundedOff(AL4c)))
			{
				Cell verify_result = sheet.getRow(k).getCell(25);
				verify_result.setCellValue("Pass");
				verify_result.setCellStyle(Utils.style(workbook, "Pass")); // for print green font
			}else
			{	
				Cell verify_result = sheet.getRow(k).getCell(25);
				verify_result.setCellValue("Fail");
				verify_result.setCellStyle(Utils.style(workbook, "Fail")); // for print red font
			}
			k++;
			}
		
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
				if(Solid_Expec_Value_L3<default_l1l3_l3)
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
			if(Solid_Expec_Value_L3<default_l1l3_l3)
			{   
				Solid_Expec_Value_L3 = value_L3;
			} else
			{	//double L3 = default_l1l3_l3 / Solid_Total_surface_area;
				Solid_Expec_Value_L3 = default_l1l3_l3; 
			}
		}		
	}
	
	
	
	
	
	
	// Write output and close workbook
	public void writeTooutputFile(Workbook workbook) throws IOException {
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
