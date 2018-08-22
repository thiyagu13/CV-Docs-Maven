package com.eDocs.patchCalculation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.poi.ss.usermodel.Workbook;
import org.testng.annotations.Parameters;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Utils;
import com.mysql.jdbc.Connection;

public class L0forpatch {
	

	/*public static String productName1 = Constant.productName1;
	public static String productName2 = Constant.productName2;
	public static String productName3 = Constant.productName3;
	public static String productName4 = Constant.productName4;*/
	
	
	@Parameters({"productName1","productName2","productName3","productName4"})
	//current product productName1 with first API
	public static double calculate_P1_active1_L0(String productName1,String productName2,String productName3,String productName4) throws SQLException, ClassNotFoundException, IOException {
			double L0 = 0,doseL0=0;
			float Safety_Factor = 0, Active_Concen = 0, percentageAbsorbtion = 0,minDailyDoseperPatch = 0,minNoOFPatchesWornatTime = 0;
			int Basislimitoption=0;
			//database connection
			Connection connection = Utils.db_connect();
			Statement stmt = connection.createStatement();
			// get current product name id from product table // for finding dose based and health flag
			ResultSet getprodname_id = stmt.executeQuery("SELECT id,percentage_absorption FROM product where name = '" + productName1 + "'");// Execute the SQL Query to find prod id from product table
			int prodname_id = 0;
			while (getprodname_id.next()) 
				{ 
				prodname_id = getprodname_id.getInt(1); // get name id from product table
				percentageAbsorbtion = getprodname_id.getFloat(2); // get name id from product table
				}
				System.out.println("name id: " + prodname_id);
				ResultSet prod_basis_relation_id = stmt.executeQuery("SELECT * FROM product_basis_of_calculation_relation where product_id='" + prodname_id + "'");
					//get active multiple active id
					List<Integer> activelist = new ArrayList<>(); // get active list from above query
					while (prod_basis_relation_id.next()) 
					{
					activelist.add(prod_basis_relation_id.getInt(2));
					}
					
					
				  	System.out.println("First Active:" +activelist.get(0));// get 1st id
					ResultSet basisOfcalc = stmt.executeQuery("SELECT other_safety_factor,active_concentration,min_daily_dose_per_patch,min_no_of_patches_worn_at_one_time FROM product_basis_of_calculation where id =" + activelist.get(0) + "");
					while (basisOfcalc.next()) 
						{
						Safety_Factor = basisOfcalc.getFloat(1);
						Active_Concen = basisOfcalc.getFloat(2);
						minDailyDoseperPatch = basisOfcalc.getFloat(3);
						minNoOFPatchesWornatTime = basisOfcalc.getFloat(4);
						}
						//get active id for getting health value
						ResultSet getactiveID = stmt.executeQuery("SELECT * FROM product_active_ingredient_relation where product_id='" + prodname_id + "'");
						List<Integer> active = new ArrayList<>(); // store multiple equipment id
					    while (getactiveID.next()) 
					    {
					    	active.add(getactiveID.getInt(2)); // get health based value
					    }
						
					    ResultSet residuelimit = stmt.executeQuery("SELECT * FROM residue_limit");
					    while (residuelimit.next()) 
						{
					    Basislimitoption = residuelimit.getInt(2);
						}
					    
					    //find health value for each active
					    float health = 0;
					    //get health based L0 from database
						ResultSet Active = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+active.get(0)+ "'");
						while (Active.next()) 
						{
							health = Active.getFloat(12);
							L0 = health;
						}
						
					    // When dose and health flag is true in basis of calculation table
						if (Basislimitoption== 3) {
										System.out.println("Both enabled");
										doseL0 = Safety_Factor * Active_Concen * percentageAbsorbtion * minDailyDoseperPatch * minNoOFPatchesWornatTime * 0.001;
										
											if (health <= doseL0) // compare both dose and health
											{
												L0 = health;
											}else
											{
												L0=doseL0;
											}
										System.out.println("Print lowest b/w health & dose L0: "+L0);
										return L0; // getting lowest L0 b/w 2
						} // for finding dose based and health flag
						
						if (Basislimitoption == 1) 
						{
							System.out.println("Dose enabled and health disabled");
								
							L0 = Safety_Factor * Active_Concen * percentageAbsorbtion * minDailyDoseperPatch * minNoOFPatchesWornatTime * 0.001;
							System.out.println("Print Dose based L0" +L0);
							return L0; // getting does L0 
						} 
						
						if (Basislimitoption== 2) 
						{
							System.out.println("Dose disabled and health enabled");
							// get health based L0 from database
								L0 = health;
							System.out.println("Print health L0: "+L0);
							return L0;
						}
						connection.close();
			return L0; // return that L0 in this method
		} //closing calculate_P1_active1_L0

	
	
	//current product productname1 with second API
	@Parameters({"productName1","productName2","productName3","productName4"})
	public static double calculate_P1_active2_L0(String productName1,String productName2,String productName3,String productName4) throws SQLException, ClassNotFoundException, IOException {
		double L0 = 0,doseL0=0;
		float Safety_Factor = 0, Active_Concen = 0, percentageAbsorbtion = 0,minDailyDoseperPatch = 0,minNoOFPatchesWornatTime = 0;
		int Basislimitoption=0;
		//database connection
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();
		// get current product name id from product table // for finding dose based and health flag
		ResultSet getprodname_id = stmt.executeQuery("SELECT id,percentage_absorption FROM product where name = '" + productName1 + "'");// Execute the SQL Query to find prod id from product table
		int prodname_id = 0;
		while (getprodname_id.next()) 
			{ 
			prodname_id = getprodname_id.getInt(1); // get name id from product table
			percentageAbsorbtion = getprodname_id.getFloat(2); // get name id from product table
			}
			System.out.println("name id: " + prodname_id);
			ResultSet prod_basis_relation_id = stmt.executeQuery("SELECT * FROM product_basis_of_calculation_relation where product_id='" + prodname_id + "'");
				//get active multiple active id
				List<Integer> activelist = new ArrayList<>(); // get active list from above query
				while (prod_basis_relation_id.next()) 
				{
				activelist.add(prod_basis_relation_id.getInt(2));
				}
			  	System.out.println("First Active:" +activelist.get(1));// get 1st id
				ResultSet basisOfcalc = stmt.executeQuery("SELECT other_safety_factor,active_concentration,min_daily_dose_per_patch,min_no_ofpatches_worn_at_one_time FROM product_basis_of_calculation where id ='" + activelist.get(1) + "'");
				while (basisOfcalc.next()) 
					{
					Safety_Factor = basisOfcalc.getFloat(1);
					Active_Concen = basisOfcalc.getFloat(2);
					minDailyDoseperPatch = basisOfcalc.getFloat(3);
					minNoOFPatchesWornatTime = basisOfcalc.getFloat(4);
					}
					//get active id for getting health value
					ResultSet getactiveID = stmt.executeQuery("SELECT * FROM product_active_ingredient_relation where product_id='" + prodname_id + "'");
					List<Integer> active = new ArrayList<>(); // store multiple equipment id
				    while (getactiveID.next()) 
				    {
				    	active.add(getactiveID.getInt(2)); // get health based value
				    }
					
				    ResultSet residuelimit = stmt.executeQuery("SELECT * FROM residue_limit");
				    while (residuelimit.next()) 
					{
				    Basislimitoption = residuelimit.getInt(2);
					}
				    
				    //find health value for each active
				    float health = 0;
				    //get health based L0 from database
					ResultSet Active = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+active.get(1)+ "'");
					while (Active.next()) 
					{
						health = Active.getFloat(12);
						L0 = health;
					}
					
				    // When dose and health flag is true in basis of calculation table
					if (Basislimitoption== 3) {
									System.out.println("Both enabled");
									doseL0 = Safety_Factor * Active_Concen * percentageAbsorbtion * minDailyDoseperPatch * minNoOFPatchesWornatTime * 0.001;
									
										if (health <= doseL0) // compare both dose and health
										{
											L0 = health;
										}else
										{
											L0=doseL0;
										}
									System.out.println("Print lowest b/w health & dose L0: "+L0);
									return L0; // getting lowest L0 b/w 2
					} // for finding dose based and health flag
					
					if (Basislimitoption == 1) 
					{
						System.out.println("Dose enabled and health disabled");
							
						L0 = Safety_Factor * Active_Concen * percentageAbsorbtion * minDailyDoseperPatch * minNoOFPatchesWornatTime * 0.001;
						System.out.println("Print Dose based L0" +L0);
						return L0; // getting does L0 
					} 
					
					if (Basislimitoption== 2) 
					{
						System.out.println("Dose disabled and health enabled");
						// get health based L0 from database
							L0 = health;
						System.out.println("Print health L0: "+L0);
						return L0;
					}
					connection.close();
		return L0; // return that L0 in this method
	} //closing calculate_P1_active1_L0

	
	
	//current product productName2 with first API
	@Parameters({"productName1","productName2","productName3","productName4"})
		public static double calculate_P2_active1_L0(String productName1,String productName2,String productName3,String productName4) throws SQLException, ClassNotFoundException, IOException {
		double L0 = 0,doseL0=0;
		float Safety_Factor = 0, Active_Concen = 0, percentageAbsorbtion = 0,minDailyDoseperPatch = 0,minNoOFPatchesWornatTime = 0;
		int Basislimitoption=0;
		//database connection
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();
		// get current product name id from product table // for finding dose based and health flag
		ResultSet getprodname_id = stmt.executeQuery("SELECT id,percentage_absorption FROM product where name = '" + productName2 + "'");// Execute the SQL Query to find prod id from product table
		int prodname_id = 0;
		while (getprodname_id.next()) 
			{ 
			prodname_id = getprodname_id.getInt(1); // get name id from product table
			percentageAbsorbtion = getprodname_id.getFloat(2); // get name id from product table
			}
			System.out.println("name id: " + prodname_id);
			ResultSet prod_basis_relation_id = stmt.executeQuery("SELECT * FROM product_basis_of_calculation_relation where product_id='" + prodname_id + "'");
				//get active multiple active id
				List<Integer> activelist = new ArrayList<>(); // get active list from above query
				while (prod_basis_relation_id.next()) 
				{
				activelist.add(prod_basis_relation_id.getInt(2));
				}
			  	System.out.println("First Active:" +activelist.get(0));// get 1st id
				ResultSet basisOfcalc = stmt.executeQuery("SELECT other_safety_factor,active_concentration,min_daily_dose_per_patch,min_no_ofpatches_worn_at_one_time FROM product_basis_of_calculation where id ='" + activelist.get(0) + "'");
				while (basisOfcalc.next()) 
					{
					Safety_Factor = basisOfcalc.getFloat(1);
					Active_Concen = basisOfcalc.getFloat(2);
					minDailyDoseperPatch = basisOfcalc.getFloat(3);
					minNoOFPatchesWornatTime = basisOfcalc.getFloat(4);
					}
					//get active id for getting health value
					ResultSet getactiveID = stmt.executeQuery("SELECT * FROM product_active_ingredient_relation where product_id='" + prodname_id + "'");
					List<Integer> active = new ArrayList<>(); // store multiple equipment id
				    while (getactiveID.next()) 
				    {
				    	active.add(getactiveID.getInt(2)); // get health based value
				    }
					
				    ResultSet residuelimit = stmt.executeQuery("SELECT * FROM residue_limit");
				    while (residuelimit.next()) 
					{
				    Basislimitoption = residuelimit.getInt(2);
					}
				    
				    //find health value for each active
				    float health = 0;
				    //get health based L0 from database
					ResultSet Active = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+active.get(0)+ "'");
					while (Active.next()) 
					{
						health = Active.getFloat(12);
						L0 = health;
					}
					
				    // When dose and health flag is true in basis of calculation table
					if (Basislimitoption== 3) {
									System.out.println("Both enabled");
									doseL0 = Safety_Factor * Active_Concen * percentageAbsorbtion * minDailyDoseperPatch * minNoOFPatchesWornatTime * 0.001;
									
										if (health <= doseL0) // compare both dose and health
										{
											L0 = health;
										}else
										{
											L0=doseL0;
										}
									System.out.println("Print lowest b/w health & dose L0: "+L0);
									return L0; // getting lowest L0 b/w 2
					} // for finding dose based and health flag
					
					if (Basislimitoption == 1) 
					{
						System.out.println("Dose enabled and health disabled");
							
						L0 = Safety_Factor * Active_Concen * percentageAbsorbtion * minDailyDoseperPatch * minNoOFPatchesWornatTime * 0.001;
						System.out.println("Print Dose based L0" +L0);
						return L0; // getting does L0 
					} 
					
					if (Basislimitoption== 2) 
					{
						System.out.println("Dose disabled and health enabled");
						// get health based L0 from database
							L0 = health;
						System.out.println("Print health L0: "+L0);
						return L0;
					}
					connection.close();
		return L0; // return that L0 in this method
			} //closing calculate_P1_active1_L0
	
	
	
	
	//current product productname2 with second API
	@Parameters({"productName1","productName2","productName3","productName4"})
		public static double calculate_P2_active2_L0(String productName1,String productName2,String productName3,String productName4) throws SQLException, ClassNotFoundException, IOException {
		double L0 = 0,doseL0=0;
		float Safety_Factor = 0, Active_Concen = 0, percentageAbsorbtion = 0,minDailyDoseperPatch = 0,minNoOFPatchesWornatTime = 0;
		int Basislimitoption=0;
		//database connection
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();
		// get current product name id from product table // for finding dose based and health flag
		ResultSet getprodname_id = stmt.executeQuery("SELECT id,percentage_absorption FROM product where name = '" + productName2 + "'");// Execute the SQL Query to find prod id from product table
		int prodname_id = 0;
		while (getprodname_id.next()) 
			{ 
			prodname_id = getprodname_id.getInt(1); // get name id from product table
			percentageAbsorbtion = getprodname_id.getFloat(2); // get name id from product table
			}
			System.out.println("name id: " + prodname_id);
			ResultSet prod_basis_relation_id = stmt.executeQuery("SELECT * FROM product_basis_of_calculation_relation where product_id='" + prodname_id + "'");
				//get active multiple active id
				List<Integer> activelist = new ArrayList<>(); // get active list from above query
				while (prod_basis_relation_id.next()) 
				{
				activelist.add(prod_basis_relation_id.getInt(2));
				}
			  	System.out.println("First Active:" +activelist.get(1));// get 1st id
				ResultSet basisOfcalc = stmt.executeQuery("SELECT other_safety_factor,active_concentration,min_daily_dose_per_patch,min_no_ofpatches_worn_at_one_time FROM product_basis_of_calculation where id ='" + activelist.get(1) + "'");
				while (basisOfcalc.next()) 
					{
					Safety_Factor = basisOfcalc.getFloat(1);
					Active_Concen = basisOfcalc.getFloat(2);
					minDailyDoseperPatch = basisOfcalc.getFloat(3);
					minNoOFPatchesWornatTime = basisOfcalc.getFloat(4);
					}
					//get active id for getting health value
					ResultSet getactiveID = stmt.executeQuery("SELECT * FROM product_active_ingredient_relation where product_id='" + prodname_id + "'");
					List<Integer> active = new ArrayList<>(); // store multiple equipment id
				    while (getactiveID.next()) 
				    {
				    	active.add(getactiveID.getInt(2)); // get health based value
				    }
					
				    ResultSet residuelimit = stmt.executeQuery("SELECT * FROM residue_limit");
				    while (residuelimit.next()) 
					{
				    Basislimitoption = residuelimit.getInt(2);
					}
				    
				    //find health value for each active
				    float health = 0;
				    //get health based L0 from database
					ResultSet Active = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+active.get(1)+ "'");
					while (Active.next()) 
					{
						health = Active.getFloat(12);
						L0 = health;
					}
					
				    // When dose and health flag is true in basis of calculation table
					if (Basislimitoption== 3) {
									System.out.println("Both enabled");
									doseL0 = Safety_Factor * Active_Concen * percentageAbsorbtion * minDailyDoseperPatch * minNoOFPatchesWornatTime * 0.001;
									
										if (health <= doseL0) // compare both dose and health
										{
											L0 = health;
										}else
										{
											L0=doseL0;
										}
									System.out.println("Print lowest b/w health & dose L0: "+L0);
									return L0; // getting lowest L0 b/w 2
					} // for finding dose based and health flag
					
					if (Basislimitoption == 1) 
					{
						System.out.println("Dose enabled and health disabled");
							
						L0 = Safety_Factor * Active_Concen * percentageAbsorbtion * minDailyDoseperPatch * minNoOFPatchesWornatTime * 0.001;
						System.out.println("Print Dose based L0" +L0);
						return L0; // getting does L0 
					} 
					
					if (Basislimitoption== 2) 
					{
						System.out.println("Dose disabled and health enabled");
						// get health based L0 from database
							L0 = health;
						System.out.println("Print health L0: "+L0);
						return L0;
					}
					connection.close();
		return L0; // return that L0 in this method
		} //closing calculate_P1_active1_L0
	
	
	
	
		//current product productName3 with first API
	@Parameters({"productName1","productName2","productName3","productName4"})
			public static double calculate_P3_active1_L0(String productName1,String productName2,String productName3,String productName4) throws SQLException, ClassNotFoundException, IOException {
		double L0 = 0,doseL0=0;
		float Safety_Factor = 0, Active_Concen = 0, percentageAbsorbtion = 0,minDailyDoseperPatch = 0,minNoOFPatchesWornatTime = 0;
		int Basislimitoption=0;
		//database connection
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();
		// get current product name id from product table // for finding dose based and health flag
		ResultSet getprodname_id = stmt.executeQuery("SELECT id,percentage_absorption FROM product where name = '" + productName3 + "'");// Execute the SQL Query to find prod id from product table
		int prodname_id = 0;
		while (getprodname_id.next()) 
			{ 
			prodname_id = getprodname_id.getInt(1); // get name id from product table
			percentageAbsorbtion = getprodname_id.getFloat(2); // get name id from product table
			}
			System.out.println("name id: " + prodname_id);
			ResultSet prod_basis_relation_id = stmt.executeQuery("SELECT * FROM product_basis_of_calculation_relation where product_id='" + prodname_id + "'");
				//get active multiple active id
				List<Integer> activelist = new ArrayList<>(); // get active list from above query
				while (prod_basis_relation_id.next()) 
				{
				activelist.add(prod_basis_relation_id.getInt(2));
				}
			  	System.out.println("First Active:" +activelist.get(0));// get 1st id
				ResultSet basisOfcalc = stmt.executeQuery("SELECT other_safety_factor,active_concentration,min_daily_dose_per_patch,min_no_ofpatches_worn_at_one_time FROM product_basis_of_calculation where id ='" + activelist.get(0) + "'");
				while (basisOfcalc.next()) 
					{
					Safety_Factor = basisOfcalc.getFloat(1);
					Active_Concen = basisOfcalc.getFloat(2);
					minDailyDoseperPatch = basisOfcalc.getFloat(3);
					minNoOFPatchesWornatTime = basisOfcalc.getFloat(4);
					}
					//get active id for getting health value
					ResultSet getactiveID = stmt.executeQuery("SELECT * FROM product_active_ingredient_relation where product_id='" + prodname_id + "'");
					List<Integer> active = new ArrayList<>(); // store multiple equipment id
				    while (getactiveID.next()) 
				    {
				    	active.add(getactiveID.getInt(2)); // get health based value
				    }
					
				    ResultSet residuelimit = stmt.executeQuery("SELECT * FROM residue_limit");
				    while (residuelimit.next()) 
					{
				    Basislimitoption = residuelimit.getInt(2);
					}
				    
				    //find health value for each active
				    float health = 0;
				    //get health based L0 from database
					ResultSet Active = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+active.get(0)+ "'");
					while (Active.next()) 
					{
						health = Active.getFloat(12);
						L0 = health;
					}
					
				    // When dose and health flag is true in basis of calculation table
					if (Basislimitoption== 3) {
									System.out.println("Both enabled");
									doseL0 = Safety_Factor * Active_Concen * percentageAbsorbtion * minDailyDoseperPatch * minNoOFPatchesWornatTime * 0.001;
									
										if (health <= doseL0) // compare both dose and health
										{
											L0 = health;
										}else
										{
											L0=doseL0;
										}
									System.out.println("Print lowest b/w health & dose L0: "+L0);
									return L0; // getting lowest L0 b/w 2
					} // for finding dose based and health flag
					
					if (Basislimitoption == 1) 
					{
						System.out.println("Dose enabled and health disabled");
							
						L0 = Safety_Factor * Active_Concen * percentageAbsorbtion * minDailyDoseperPatch * minNoOFPatchesWornatTime * 0.001;
						System.out.println("Print Dose based L0" +L0);
						return L0; // getting does L0 
					} 
					
					if (Basislimitoption== 2) 
					{
						System.out.println("Dose disabled and health enabled");
						// get health based L0 from database
							L0 = health;
						System.out.println("Print health L0: "+L0);
						return L0;
					}
					connection.close();
		return L0; // return that L0 in this method
				} //closing calculate_P1_active1_L0
		
		
		
		
		
			//current product productname3 with second API
			@Parameters({"productName1","productName2","productName3","productName4"})
			public static double calculate_P3_active2_L0(String productName1,String productName2,String productName3,String productName4) throws SQLException, ClassNotFoundException, IOException {
				double L0 = 0,doseL0=0;
				float Safety_Factor = 0, Active_Concen = 0, percentageAbsorbtion = 0,minDailyDoseperPatch = 0,minNoOFPatchesWornatTime = 0;
				int Basislimitoption=0;
				//database connection
				Connection connection = Utils.db_connect();
				Statement stmt = (Statement) connection.createStatement();
				// get current product name id from product table // for finding dose based and health flag
				ResultSet getprodname_id = stmt.executeQuery("SELECT id,percentage_absorption FROM product where name = '" + productName3 + "'");// Execute the SQL Query to find prod id from product table
				int prodname_id = 0;
				while (getprodname_id.next()) 
					{ 
					prodname_id = getprodname_id.getInt(1); // get name id from product table
					percentageAbsorbtion = getprodname_id.getFloat(2); // get name id from product table
					}
					System.out.println("name id: " + prodname_id);
					ResultSet prod_basis_relation_id = stmt.executeQuery("SELECT * FROM product_basis_of_calculation_relation where product_id='" + prodname_id + "'");
						//get active multiple active id
						List<Integer> activelist = new ArrayList<>(); // get active list from above query
						while (prod_basis_relation_id.next()) 
						{
						activelist.add(prod_basis_relation_id.getInt(2));
						}
					  	System.out.println("First Active:" +activelist.get(0));// get 1st id
						ResultSet basisOfcalc = stmt.executeQuery("SELECT other_safety_factor,active_concentration,min_daily_dose_per_patch,min_no_ofpatches_worn_at_one_time FROM product_basis_of_calculation where id ='" + activelist.get(1) + "'");
						while (basisOfcalc.next()) 
							{
							Safety_Factor = basisOfcalc.getFloat(1);
							Active_Concen = basisOfcalc.getFloat(2);
							minDailyDoseperPatch = basisOfcalc.getFloat(3);
							minNoOFPatchesWornatTime = basisOfcalc.getFloat(4);
							}
							//get active id for getting health value
							ResultSet getactiveID = stmt.executeQuery("SELECT * FROM product_active_ingredient_relation where product_id='" + prodname_id + "'");
							List<Integer> active = new ArrayList<>(); // store multiple equipment id
						    while (getactiveID.next()) 
						    {
						    	active.add(getactiveID.getInt(2)); // get health based value
						    }
							
						    ResultSet residuelimit = stmt.executeQuery("SELECT * FROM residue_limit");
						    while (residuelimit.next()) 
							{
						    Basislimitoption = residuelimit.getInt(2);
							}
						    
						    //find health value for each active
						    float health = 0;
						    //get health based L0 from database
							ResultSet Active = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+active.get(1)+ "'");
							while (Active.next()) 
							{
								health = Active.getFloat(12);
								L0 = health;
							}
							
						    // When dose and health flag is true in basis of calculation table
							if (Basislimitoption== 3) {
											System.out.println("Both enabled");
											doseL0 = Safety_Factor * Active_Concen * percentageAbsorbtion * minDailyDoseperPatch * minNoOFPatchesWornatTime * 0.001;
											
												if (health <= doseL0) // compare both dose and health
												{
													L0 = health;
												}else
												{
													L0=doseL0;
												}
											System.out.println("Print lowest b/w health & dose L0: "+L0);
											return L0; // getting lowest L0 b/w 2
							} // for finding dose based and health flag
							
							if (Basislimitoption == 1) 
							{
								System.out.println("Dose enabled and health disabled");
									
								L0 = Safety_Factor * Active_Concen * percentageAbsorbtion * minDailyDoseperPatch * minNoOFPatchesWornatTime * 0.001;
								System.out.println("Print Dose based L0" +L0);
								return L0; // getting does L0 
							} 
							
							if (Basislimitoption== 2) 
							{
								System.out.println("Dose disabled and health enabled");
								// get health based L0 from database
									L0 = health;
								System.out.println("Print health L0: "+L0);
								return L0;
							}
							connection.close();
				return L0; // return that L0 in this method
			} //closing calculate_P1_active1_L0
		
		
	
	

			//current product productName4 with first API
			@Parameters({"productName1","productName2","productName3","productName4"})
				public static double calculate_P4_active1_L0(String productName1,String productName2,String productName3,String productName4) throws SQLException, ClassNotFoundException, IOException {
				double L0 = 0,doseL0=0;
				float Safety_Factor = 0, Active_Concen = 0, percentageAbsorbtion = 0,minDailyDoseperPatch = 0,minNoOFPatchesWornatTime = 0;
				int Basislimitoption=0;
				//database connection
				Connection connection = Utils.db_connect();
				Statement stmt = (Statement) connection.createStatement();
				// get current product name id from product table // for finding dose based and health flag
				ResultSet getprodname_id = stmt.executeQuery("SELECT id,percentage_absorption FROM product where name = '" + productName4 + "'");// Execute the SQL Query to find prod id from product table
				int prodname_id = 0;
				while (getprodname_id.next()) 
					{ 
					prodname_id = getprodname_id.getInt(1); // get name id from product table
					percentageAbsorbtion = getprodname_id.getFloat(2); // get name id from product table
					}
					System.out.println("name id: " + prodname_id);
					ResultSet prod_basis_relation_id = stmt.executeQuery("SELECT * FROM product_basis_of_calculation_relation where product_id='" + prodname_id + "'");
						//get active multiple active id
						List<Integer> activelist = new ArrayList<>(); // get active list from above query
						while (prod_basis_relation_id.next()) 
						{
						activelist.add(prod_basis_relation_id.getInt(2));
						}
					  	System.out.println("First Active:" +activelist.get(0));// get 1st id
						ResultSet basisOfcalc = stmt.executeQuery("SELECT other_safety_factor,active_concentration,min_daily_dose_per_patch,min_no_ofpatches_worn_at_one_time FROM product_basis_of_calculation where id ='" + activelist.get(0) + "'");
						while (basisOfcalc.next()) 
							{
							Safety_Factor = basisOfcalc.getFloat(1);
							Active_Concen = basisOfcalc.getFloat(2);
							minDailyDoseperPatch = basisOfcalc.getFloat(3);
							minNoOFPatchesWornatTime = basisOfcalc.getFloat(4);
							}
							//get active id for getting health value
							ResultSet getactiveID = stmt.executeQuery("SELECT * FROM product_active_ingredient_relation where product_id='" + prodname_id + "'");
							List<Integer> active = new ArrayList<>(); // store multiple equipment id
						    while (getactiveID.next()) 
						    {
						    	active.add(getactiveID.getInt(2)); // get health based value
						    }
							
						    ResultSet residuelimit = stmt.executeQuery("SELECT * FROM residue_limit");
						    while (residuelimit.next()) 
							{
						    Basislimitoption = residuelimit.getInt(2);
							}
						    
						    //find health value for each active
						    float health = 0;
						    //get health based L0 from database
							ResultSet Active = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+active.get(0)+ "'");
							while (Active.next()) 
							{
								health = Active.getFloat(12);
								L0 = health;
							}
							
						    // When dose and health flag is true in basis of calculation table
							if (Basislimitoption== 3) {
											System.out.println("Both enabled");
											doseL0 = Safety_Factor * Active_Concen * percentageAbsorbtion * minDailyDoseperPatch * minNoOFPatchesWornatTime * 0.001;
											
												if (health <= doseL0) // compare both dose and health
												{
													L0 = health;
												}else
												{
													L0=doseL0;
												}
											System.out.println("Print lowest b/w health & dose L0: "+L0);
											return L0; // getting lowest L0 b/w 2
							} // for finding dose based and health flag
							
							if (Basislimitoption == 1) 
							{
								System.out.println("Dose enabled and health disabled");
									
								L0 = Safety_Factor * Active_Concen * percentageAbsorbtion * minDailyDoseperPatch * minNoOFPatchesWornatTime * 0.001;
								System.out.println("Print Dose based L0" +L0);
								return L0; // getting does L0 
							} 
							
							if (Basislimitoption== 2) 
							{
								System.out.println("Dose disabled and health enabled");
								// get health based L0 from database
									L0 = health;
								System.out.println("Print health L0: "+L0);
								return L0;
							}
							connection.close();
				return L0; // return that L0 in this method
					} //closing calculate_P1_active1_L0
			
			
			
			
			
				//current product productname4 with second API
			@Parameters({"productName1","productName2","productName3","productName4"})
				public static double calculate_P4_active2_L0(String productName1,String productName2,String productName3,String productName4) throws SQLException, ClassNotFoundException, IOException {
				double L0 = 0,doseL0=0;
				float Safety_Factor = 0, Active_Concen = 0, percentageAbsorbtion = 0,minDailyDoseperPatch = 0,minNoOFPatchesWornatTime = 0;
				int Basislimitoption=0;
				//database connection
				Connection connection = Utils.db_connect();
				Statement stmt = (Statement) connection.createStatement();
				// get current product name id from product table // for finding dose based and health flag
				ResultSet getprodname_id = stmt.executeQuery("SELECT id,percentage_absorption FROM product where name = '" + productName4 + "'");// Execute the SQL Query to find prod id from product table
				int prodname_id = 0;
				while (getprodname_id.next()) 
					{ 
					prodname_id = getprodname_id.getInt(1); // get name id from product table
					percentageAbsorbtion = getprodname_id.getFloat(2); // get name id from product table
					}
					System.out.println("name id: " + prodname_id);
					ResultSet prod_basis_relation_id = stmt.executeQuery("SELECT * FROM product_basis_of_calculation_relation where product_id='" + prodname_id + "'");
						//get active multiple active id
						List<Integer> activelist = new ArrayList<>(); // get active list from above query
						while (prod_basis_relation_id.next()) 
						{
						activelist.add(prod_basis_relation_id.getInt(2));
						}
					  	System.out.println("First Active:" +activelist.get(1));// get 1st id
						ResultSet basisOfcalc = stmt.executeQuery("SELECT other_safety_factor,active_concentration,min_daily_dose_per_patch,min_no_ofpatches_worn_at_one_time FROM product_basis_of_calculation where id ='" + activelist.get(1) + "'");
						while (basisOfcalc.next()) 
							{
							Safety_Factor = basisOfcalc.getFloat(1);
							Active_Concen = basisOfcalc.getFloat(2);
							minDailyDoseperPatch = basisOfcalc.getFloat(3);
							minNoOFPatchesWornatTime = basisOfcalc.getFloat(4);
							}
							//get active id for getting health value
							ResultSet getactiveID = stmt.executeQuery("SELECT * FROM product_active_ingredient_relation where product_id='" + prodname_id + "'");
							List<Integer> active = new ArrayList<>(); // store multiple equipment id
						    while (getactiveID.next()) 
						    {
						    	active.add(getactiveID.getInt(2)); // get health based value
						    }
							
						    ResultSet residuelimit = stmt.executeQuery("SELECT * FROM residue_limit");
						    while (residuelimit.next()) 
							{
						    Basislimitoption = residuelimit.getInt(2);
							}
						    
						    //find health value for each active
						    float health = 0;
						    //get health based L0 from database
							ResultSet Active = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+active.get(1)+ "'");
							while (Active.next()) 
							{
								health = Active.getFloat(12);
								L0 = health;
							}
							
						    // When dose and health flag is true in basis of calculation table
							if (Basislimitoption== 3) {
											System.out.println("Both enabled");
											doseL0 = Safety_Factor * Active_Concen * percentageAbsorbtion * minDailyDoseperPatch * minNoOFPatchesWornatTime * 0.001;
											
												if (health <= doseL0) // compare both dose and health
												{
													L0 = health;
												}else
												{
													L0=doseL0;
												}
											System.out.println("Print lowest b/w health & dose L0: "+L0);
											return L0; // getting lowest L0 b/w 2
							} // for finding dose based and health flag
							
							if (Basislimitoption == 1) 
							{
								System.out.println("Dose enabled and health disabled");
									
								L0 = Safety_Factor * Active_Concen * percentageAbsorbtion * minDailyDoseperPatch * minNoOFPatchesWornatTime * 0.001;
								System.out.println("Print Dose based L0" +L0);
								return L0; // getting does L0 
							} 
							
							if (Basislimitoption== 2) 
							{
								System.out.println("Dose disabled and health enabled");
								// get health based L0 from database
									L0 = health;
								System.out.println("Print health L0: "+L0);
								return L0;
							}
							connection.close();
				return L0; // return that L0 in this method
				} //closing calculate_P1_active1_L0
			
			
	
	
	
	
	
	//if grouping approach
			//@Parameters({"productName1","productName2","productName3","productName4"})
			public static double groupingApproach_L0_p11(String currentproductname) throws IOException, ClassNotFoundException, SQLException 
			{
				double L0 = 0, Safety_Factor = 0, Active_Concen = 0, minDailyDoseperPatch = 0, minNoOFPatchesWornatTime = 0, percentageAbsorbtion = 0,doseL0=0,healthL0 = 0;
				int Basislimitoption = 0;
				//database connection
				Connection connection = Utils.db_connect();
				Statement stmt = (Statement) connection.createStatement();
				ResultSet getprodname_id = stmt.executeQuery("SELECT * FROM product where name = '" + currentproductname + "'");// Execute the SQL Query to find prod id from product table
				int prodname_id = 0, lowestsolubilityID = 0,lowestADEID=0;
				//Get product id 
				while (getprodname_id.next()) {
					prodname_id = getprodname_id.getInt(1); // get name id from product table
					percentageAbsorbtion = getprodname_id.getFloat(6); // get frequency from product table
					}
					System.out.println("name id: " + prodname_id);
					//get active id
					ResultSet getactiveID = stmt.executeQuery("SELECT * FROM product_active_ingredient_relation where product_id='" + prodname_id + "'");
						List<Integer> active = new ArrayList<>(); // store multiple equipment id
				    	while (getactiveID.next()) 
				    	{
				    	active.add(getactiveID.getInt(2)); // get health based value
				    	}
				    
				    //get lowest solubility within api from product
				    List<Float> Solubilities = new ArrayList<>(); // store multiple equipment id
				    	for(int activeID:active)
				    	{
				    		ResultSet getallActive = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+activeID+ "'");
				    		while(getallActive.next())
				    		{
				    			Solubilities.add((float) getallActive.getFloat(9)); // get health based value
				    			System.out.println("solubilityinWater" +Solubilities + "Active:"+activeID);
				    		}
				    	}
				    	float minsolubility = Collections.min(Solubilities); // get minimum value from awithin active
				    	
				    
				    // find minimum solubility active id
				    for(int listofactiveID:active)
				    {
				    ResultSet getActive = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+listofactiveID+ "' and solubility_in_water= '"+minsolubility+ "'");
				    while(getActive.next())
				    {
				    	lowestsolubilityID =getActive.getInt(1); // get health based value
				    	System.out.println("Lowest solubility active id: "+lowestsolubilityID);
				    }
				    } // end - get lowest solubility within api from product
				    
				    
				  //get lowest ADE within api from product
				    List<Float> ade = new ArrayList<>(); // store multiple equipment id
				    	for(int activeID:active)
				    	{
				    		ResultSet getallActive = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+activeID+ "'");
				    		while(getallActive.next())
				    		{
				    			ade.add((float) getallActive.getFloat(12)); // get health based value
				    			System.out.println("ADE" +ade + "Active:"+activeID);
				    		}
				    	}
				    	float minade = Collections.min(ade); // get minimum value from awithin active
				    	System.out.println("Min ADE" +minade);
				    	
				    
				    // find minimum solubility active id
				    for(int listofactiveID:active)
				    {
				    ResultSet getActive = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+listofactiveID+ "' and lowest_route_of_admin_value LIKE '"+minade+ "'");
				    while(getActive.next())
				    {
				    	lowestADEID =getActive.getInt(1); // get health based value
				    	System.out.println("Lowest ADE active id: "+lowestADEID);
				    }
				    } // end - get lowest solubility within api from product
				    
				    
				    // get values using lowest active id
					ResultSet basisOfcalc = stmt.executeQuery("SELECT other_safety_factor,active_concentration,min_daily_dose_per_patch,min_no_ofpatches_worn_at_one_time FROM product_basis_of_calculation where active_ingredient_id ='" + lowestsolubilityID + "'");
						while (basisOfcalc.next()) 
						{
							//dose_based_flag = basisOfcalc.getInt(5);
							//health_based_flag = basisOfcalc.getInt(11); 
							Safety_Factor = basisOfcalc.getFloat(10);
							Active_Concen = basisOfcalc.getFloat(6);
							minDailyDoseperPatch = basisOfcalc.getFloat(7);
							minNoOFPatchesWornatTime = basisOfcalc.getFloat(8);
						} 
						
						ResultSet residuelimit = stmt.executeQuery("SELECT * FROM residue_limit");
					    while (residuelimit.next()) 
						{
					    Basislimitoption = residuelimit.getInt(2);
						}
					    System.out.println("Basislimitoption"+Basislimitoption);
					    
					    //Basis of limit option if dose or lowest between two
							if (Basislimitoption==1 || Basislimitoption==3) {
									System.out.println("Dose enabled and health disabled");
									// get dose based information
									/*ResultSet dosebaseddata = stmt.executeQuery("SELECT * FROM product_basis_of_calculation where active_ingredient_id ='" + lowestsolubilityID + "'");
									//System.out.println("activelist.get(0)" +activelist.get(0));
									System.out.println("lowestsolubilityID" +lowestsolubilityID);
									// While Loop to iterate through all data and print results
									while (dosebaseddata.next())
									{
										Safety_Factor = dosebaseddata.getFloat(10);
										Active_Concen = dosebaseddata.getFloat(6);
										Dose_of_active = dosebaseddata.getFloat(7);
										min_no_of_dose = dosebaseddata.getFloat(8);
									}*/
											
									doseL0 = Safety_Factor * Active_Concen * percentageAbsorbtion * minDailyDoseperPatch * minNoOFPatchesWornatTime * 0.001;
										
									System.out.println("Print Dose based L0" +doseL0);
							} // closing for loop
							
							//Basis of limit option if health or lowest between two
							if (Basislimitoption==2 || Basislimitoption==3) 
							{
								System.out.println("Dose disabled and health enabled");
								System.out.println("lowestsolubilityID"+lowestsolubilityID);
								System.out.println("lowestADEID"+lowestADEID);
								if(lowestADEID == lowestsolubilityID)
								{
									System.out.println(" same");
								// get health based L0 from database
								ResultSet Active = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+lowestsolubilityID+ "'");
								while (Active.next()) 
								{
									float health = Active.getFloat(12);
									healthL0 = health;
								}
								}else
								{
									System.out.println("Not same");
									float lowestADEDose = 0,lowestsolubilityDose = 0;
									ResultSet LowestPDEactive = stmt.executeQuery("SELECT * FROM product_basis_of_calculation where active_ingredient_id LIKE '"+lowestADEID+ "'");
									//TO DO
									while(LowestPDEactive.next())
									{
										lowestADEDose = LowestPDEactive.getFloat(7);
										System.out.println("lowestADEDose"+lowestADEDose);
									}
									ResultSet Lowestsolubilityactive = stmt.executeQuery("SELECT * FROM product_basis_of_calculation where active_ingredient_id LIKE '"+lowestsolubilityID+ "'");
									while(Lowestsolubilityactive.next())
									{
										lowestsolubilityDose = Lowestsolubilityactive.getFloat(7);
										System.out.println("lowestsolubilityDose"+lowestsolubilityDose);
									}
									healthL0 = ((minade/lowestADEDose) *lowestsolubilityDose);
								}
							}
							
							// get final L0 value
							if(doseL0==0)
							{
								L0 = healthL0;
							}
							if(healthL0==0)
							{
								L0 = doseL0;
							}
							if(healthL0!=0 && doseL0!=0)
							{
								L0 = Math.min(doseL0,healthL0);
							}
							System.out.println("Print dose L0: "+doseL0);
							System.out.println("Print health L0: "+healthL0);
							System.out.println("Print  L0: "+L0);
							connection.close();
				return L0; // return that L0 in this method
			} 
			
	
			
			/*
			
	
			//if grouping approach - product2
			@Parameters({"productName1","productName2","productName3","productName4"})
			public static double groupingApproach_L0_p22(String productName1,String productName2,String productName3,String productName4) throws IOException, ClassNotFoundException, SQLException 
			{
				double L0 = 0, Safety_Factor = 0, Active_Concen = 0, Dose_of_active = 0, Product_Dose = 0, min_no_of_dose = 0,frequency = 0,doseL0=0,healthL0 = 0;
				int Basislimitoption = 0;
				//database connection
				Connection connection = Utils.db_connect();
				Statement stmt = (Statement) connection.createStatement();
				ResultSet getprodname_id = stmt.executeQuery("SELECT * FROM product where name = '" + productName2 + "'");// Execute the SQL Query to find prod id from product table
				int prodname_id = 0, lowestsolubilityID = 0,lowestADEID=0;
				//Get product id 
				while (getprodname_id.next()) {
					prodname_id = getprodname_id.getInt(1); // get name id from product table
					frequency = getprodname_id.getFloat(6); // get frequency from product table
					Product_Dose = getprodname_id.getFloat(5); //// get product dose from product table
					}
					System.out.println("name id: " + prodname_id);
					//get active id
					ResultSet getactiveID = stmt.executeQuery("SELECT * FROM product_active_ingredient_relation where product_id='" + prodname_id + "'");
						List<Integer> active = new ArrayList<>(); // store multiple equipment id
				    	while (getactiveID.next()) 
				    	{
				    	active.add(getactiveID.getInt(2)); // get health based value
				    	}
				    
				    //get lowest solubility within api from product
				    List<Float> Solubilities = new ArrayList<>(); // store multiple equipment id
				    	for(int activeID:active)
				    	{
				    		ResultSet getallActive = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+activeID+ "'");
				    		while(getallActive.next())
				    		{
				    			Solubilities.add((float) getallActive.getFloat(9)); // get health based value
				    			System.out.println("solubilityinWater" +Solubilities + "Active:"+activeID);
				    		}
				    	}
				    	float minsolubility = Collections.min(Solubilities); // get minimum value from awithin active
				    	
				    
				    // find minimum solubility active id
				    for(int listofactiveID:active)
				    {
				    ResultSet getActive = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+listofactiveID+ "' and solubility_in_water= '"+minsolubility+ "'");
				    while(getActive.next())
				    {
				    	lowestsolubilityID =getActive.getInt(1); // get health based value
				    	System.out.println("Lowest solubility active id: "+lowestsolubilityID);
				    }
				    } // end - get lowest solubility within api from product
				    
				    
				  //get lowest ADE within api from product
				    List<Float> ade = new ArrayList<>(); // store multiple equipment id
				    	for(int activeID:active)
				    	{
				    		ResultSet getallActive = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+activeID+ "'");
				    		while(getallActive.next())
				    		{
				    			ade.add((float) getallActive.getFloat(12)); // get health based value
				    			System.out.println("ADE" +ade + "Active:"+activeID);
				    		}
				    	}
				    	float minade = Collections.min(ade); // get minimum value from awithin active
				    	System.out.println("Min ADE" +minade);
				    	
				    
				    // find minimum solubility active id
				    for(int listofactiveID:active)
				    {
				    ResultSet getActive = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+listofactiveID+ "' and lowest_route_of_admin_value LIKE '"+minade+ "'");
				    while(getActive.next())
				    {
				    	lowestADEID =getActive.getInt(1); // get health based value
				    	System.out.println("Lowest ADE active id: "+lowestADEID);
				    }
				    } // end - get lowest solubility within api from product
				    
				    
				    // get values using lowest active id
					ResultSet basisOfcalc = stmt.executeQuery("SELECT * FROM product_basis_of_calculation where active_ingredient_id ='" + lowestsolubilityID + "'");
						while (basisOfcalc.next()) 
						{
							//dose_based_flag = basisOfcalc.getInt(5);
							//health_based_flag = basisOfcalc.getInt(11); 
							Safety_Factor = basisOfcalc.getFloat(10);
							Active_Concen = basisOfcalc.getFloat(6);
							Dose_of_active = basisOfcalc.getFloat(7);
							min_no_of_dose = basisOfcalc.getFloat(8);
						} 
						
						ResultSet residuelimit = stmt.executeQuery("SELECT * FROM residue_limit");
					    while (residuelimit.next()) 
						{
					    Basislimitoption = residuelimit.getInt(2);
						}
					    System.out.println("Basislimitoption"+Basislimitoption);
					    
					    //Basis of limit option if dose or lowest between two
							if (Basislimitoption==1 || Basislimitoption==3) {
									System.out.println("Dose enabled and health disabled");
									// get dose based information
									ResultSet dosebaseddata = stmt.executeQuery("SELECT * FROM product_basis_of_calculation where active_ingredient_id ='" + lowestsolubilityID + "'");
									//System.out.println("activelist.get(0)" +activelist.get(0));
									System.out.println("lowestsolubilityID" +lowestsolubilityID);
									// While Loop to iterate through all data and print results
									while (dosebaseddata.next())
									{
										Safety_Factor = dosebaseddata.getFloat(10);
										Active_Concen = dosebaseddata.getFloat(6);
										Dose_of_active = dosebaseddata.getFloat(7);
										min_no_of_dose = dosebaseddata.getFloat(8);
									}
											if (Dose_of_active == 0) { // if dose of active is null
												doseL0 = Safety_Factor * Active_Concen * Product_Dose* (min_no_of_dose / frequency);
											} else { // if dose of active not null
												doseL0 = Safety_Factor * Dose_of_active * (min_no_of_dose / frequency);
											}
										System.out.println("Print Dose based L0" +doseL0);
							} // closing for loop
							
							//Basis of limit option if health or lowest between two
							if (Basislimitoption==2 || Basislimitoption==3) 
							{
								System.out.println("Dose disabled and health enabled");
								System.out.println("lowestsolubilityID"+lowestsolubilityID);
								System.out.println("lowestADEID"+lowestADEID);
								if(lowestADEID == lowestsolubilityID)
								{
									System.out.println(" same");
								// get health based L0 from database
								ResultSet Active = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+lowestsolubilityID+ "'");
								while (Active.next()) 
								{
									float health = Active.getFloat(12);
									healthL0 = health;
								}
								}else
								{
									System.out.println("Not same");
									float lowestADEDose = 0,lowestsolubilityDose = 0;
									ResultSet LowestPDEactive = stmt.executeQuery("SELECT * FROM product_basis_of_calculation where active_ingredient_id LIKE '"+lowestADEID+ "'");
									//TO DO
									while(LowestPDEactive.next())
									{
										lowestADEDose = LowestPDEactive.getFloat(7);
										System.out.println("lowestADEDose"+lowestADEDose);
									}
									ResultSet Lowestsolubilityactive = stmt.executeQuery("SELECT * FROM product_basis_of_calculation where active_ingredient_id LIKE '"+lowestsolubilityID+ "'");
									while(Lowestsolubilityactive.next())
									{
										lowestsolubilityDose = Lowestsolubilityactive.getFloat(7);
										System.out.println("lowestsolubilityDose"+lowestsolubilityDose);
									}
									healthL0 = ((minade/lowestADEDose) *lowestsolubilityDose);
								}
							}
							
							// get final L0 value
							if(doseL0==0)
							{
								L0 = healthL0;
							}
							if(healthL0==0)
							{
								L0 = doseL0;
							}
							if(healthL0!=0 && doseL0!=0)
							{
								L0 = Math.min(doseL0,healthL0);
							}
							System.out.println("Print dose L0: "+doseL0);
							System.out.println("Print health L0: "+healthL0);
							System.out.println("Print  L0: "+L0);
							connection.close();
				return L0; // return that L0 in this method
			} 
	
			
			//if grouping approach - product2
			@Parameters({"productName1","productName2","productName3","productName4"})
			public static double groupingApproach_L0_p33(String productName1,String productName2,String productName3,String productName4) throws IOException, ClassNotFoundException, SQLException 
			{
				double L0 = 0, Safety_Factor = 0, Active_Concen = 0, Dose_of_active = 0, Product_Dose = 0, min_no_of_dose = 0,frequency = 0,doseL0=0,healthL0 = 0;
				int Basislimitoption = 0;
				//database connection
				Connection connection = Utils.db_connect();
				Statement stmt = (Statement) connection.createStatement();
				ResultSet getprodname_id = stmt.executeQuery("SELECT * FROM product where name = '" + productName3 + "'");// Execute the SQL Query to find prod id from product table
				int prodname_id = 0, lowestsolubilityID = 0,lowestADEID=0;
				//Get product id 
				while (getprodname_id.next()) {
					prodname_id = getprodname_id.getInt(1); // get name id from product table
					frequency = getprodname_id.getFloat(6); // get frequency from product table
					Product_Dose = getprodname_id.getFloat(5); //// get product dose from product table
					}
					System.out.println("name id: " + prodname_id);
					//get active id
					ResultSet getactiveID = stmt.executeQuery("SELECT * FROM product_active_ingredient_relation where product_id='" + prodname_id + "'");
						List<Integer> active = new ArrayList<>(); // store multiple equipment id
				    	while (getactiveID.next()) 
				    	{
				    	active.add(getactiveID.getInt(2)); // get health based value
				    	}
				    
				    //get lowest solubility within api from product
				    List<Float> Solubilities = new ArrayList<>(); // store multiple equipment id
				    	for(int activeID:active)
				    	{
				    		ResultSet getallActive = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+activeID+ "'");
				    		while(getallActive.next())
				    		{
				    			Solubilities.add((float) getallActive.getFloat(9)); // get health based value
				    			System.out.println("solubilityinWater" +Solubilities + "Active:"+activeID);
				    		}
				    	}
				    	float minsolubility = Collections.min(Solubilities); // get minimum value from awithin active
				    	
				    
				    // find minimum solubility active id
				    for(int listofactiveID:active)
				    {
				    ResultSet getActive = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+listofactiveID+ "' and solubility_in_water= '"+minsolubility+ "'");
				    while(getActive.next())
				    {
				    	lowestsolubilityID =getActive.getInt(1); // get health based value
				    	System.out.println("Lowest solubility active id: "+lowestsolubilityID);
				    }
				    } // end - get lowest solubility within api from product
				    
				    
				  //get lowest ADE within api from product
				    List<Float> ade = new ArrayList<>(); // store multiple equipment id
				    	for(int activeID:active)
				    	{
				    		ResultSet getallActive = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+activeID+ "'");
				    		while(getallActive.next())
				    		{
				    			ade.add((float) getallActive.getFloat(12)); // get health based value
				    			System.out.println("ADE" +ade + "Active:"+activeID);
				    		}
				    	}
				    	float minade = Collections.min(ade); // get minimum value from awithin active
				    	System.out.println("Min ADE" +minade);
				    	
				    
				    // find minimum solubility active id
				    for(int listofactiveID:active)
				    {
				    ResultSet getActive = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+listofactiveID+ "' and lowest_route_of_admin_value LIKE '"+minade+ "'");
				    while(getActive.next())
				    {
				    	lowestADEID =getActive.getInt(1); // get health based value
				    	System.out.println("Lowest ADE active id: "+lowestADEID);
				    }
				    } // end - get lowest solubility within api from product
				    
				    
				    // get values using lowest active id
					ResultSet basisOfcalc = stmt.executeQuery("SELECT * FROM product_basis_of_calculation where active_ingredient_id ='" + lowestsolubilityID + "'");
						while (basisOfcalc.next()) 
						{
							//dose_based_flag = basisOfcalc.getInt(5);
							//health_based_flag = basisOfcalc.getInt(11); 
							Safety_Factor = basisOfcalc.getFloat(10);
							Active_Concen = basisOfcalc.getFloat(6);
							Dose_of_active = basisOfcalc.getFloat(7);
							min_no_of_dose = basisOfcalc.getFloat(8);
						} 
						
						ResultSet residuelimit = stmt.executeQuery("SELECT * FROM residue_limit");
					    while (residuelimit.next()) 
						{
					    Basislimitoption = residuelimit.getInt(2);
						}
					    System.out.println("Basislimitoption"+Basislimitoption);
					    
					    //Basis of limit option if dose or lowest between two
							if (Basislimitoption==1 || Basislimitoption==3) {
									System.out.println("Dose enabled and health disabled");
									// get dose based information
									ResultSet dosebaseddata = stmt.executeQuery("SELECT * FROM product_basis_of_calculation where active_ingredient_id ='" + lowestsolubilityID + "'");
									//System.out.println("activelist.get(0)" +activelist.get(0));
									System.out.println("lowestsolubilityID" +lowestsolubilityID);
									// While Loop to iterate through all data and print results
									while (dosebaseddata.next())
									{
										Safety_Factor = dosebaseddata.getFloat(10);
										Active_Concen = dosebaseddata.getFloat(6);
										Dose_of_active = dosebaseddata.getFloat(7);
										min_no_of_dose = dosebaseddata.getFloat(8);
									}
											if (Dose_of_active == 0) { // if dose of active is null
												doseL0 = Safety_Factor * Active_Concen * Product_Dose* (min_no_of_dose / frequency);
											} else { // if dose of active not null
												doseL0 = Safety_Factor * Dose_of_active * (min_no_of_dose / frequency);
											}
										System.out.println("Print Dose based L0" +doseL0);
							} // closing for loop
							
							//Basis of limit option if health or lowest between two
							if (Basislimitoption==2 || Basislimitoption==3) 
							{
								System.out.println("Dose disabled and health enabled");
								System.out.println("lowestsolubilityID"+lowestsolubilityID);
								System.out.println("lowestADEID"+lowestADEID);
								if(lowestADEID == lowestsolubilityID)
								{
									System.out.println(" same");
								// get health based L0 from database
								ResultSet Active = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+lowestsolubilityID+ "'");
								while (Active.next()) 
								{
									float health = Active.getFloat(12);
									healthL0 = health;
								}
								}else
								{
									System.out.println("Not same");
									float lowestADEDose = 0,lowestsolubilityDose = 0;
									ResultSet LowestPDEactive = stmt.executeQuery("SELECT * FROM product_basis_of_calculation where active_ingredient_id LIKE '"+lowestADEID+ "'");
									//TO DO
									while(LowestPDEactive.next())
									{
										lowestADEDose = LowestPDEactive.getFloat(7);
										System.out.println("lowestADEDose"+lowestADEDose);
									}
									ResultSet Lowestsolubilityactive = stmt.executeQuery("SELECT * FROM product_basis_of_calculation where active_ingredient_id LIKE '"+lowestsolubilityID+ "'");
									while(Lowestsolubilityactive.next())
									{
										lowestsolubilityDose = Lowestsolubilityactive.getFloat(7);
										System.out.println("lowestsolubilityDose"+lowestsolubilityDose);
									}
									healthL0 = ((minade/lowestADEDose) *lowestsolubilityDose);
								}
							}
							
							// get final L0 value
							if(doseL0==0)
							{
								L0 = healthL0;
							}
							if(healthL0==0)
							{
								L0 = doseL0;
							}
							if(healthL0!=0 && doseL0!=0)
							{
								L0 = Math.min(doseL0,healthL0);
							}
							System.out.println("Print dose L0: "+doseL0);
							System.out.println("Print health L0: "+healthL0);
							System.out.println("Print  L0: "+L0);
							connection.close();
				return L0; // return that L0 in this method
			} 
	
	
			//if grouping approach - product4
			@Parameters({"productName1","productName2","productName3","productName4"})
			public static double groupingApproach_L0_p44(String productName1,String productName2,String productName3,String productName4) throws IOException, ClassNotFoundException, SQLException 
			{
				double L0 = 0, Safety_Factor = 0, Active_Concen = 0, Dose_of_active = 0, Product_Dose = 0, min_no_of_dose = 0,frequency = 0,doseL0=0,healthL0 = 0;
				int Basislimitoption = 0;
				//database connection
				Connection connection = Utils.db_connect();
				Statement stmt = (Statement) connection.createStatement();
				ResultSet getprodname_id = stmt.executeQuery("SELECT * FROM product where name = '" + productName4 + "'");// Execute the SQL Query to find prod id from product table
				int prodname_id = 0, lowestsolubilityID = 0,lowestADEID=0;
				//Get product id 
				while (getprodname_id.next()) {
					prodname_id = getprodname_id.getInt(1); // get name id from product table
					frequency = getprodname_id.getFloat(6); // get frequency from product table
					Product_Dose = getprodname_id.getFloat(5); //// get product dose from product table
					}
					System.out.println("name id: " + prodname_id);
					//get active id
					ResultSet getactiveID = stmt.executeQuery("SELECT * FROM product_active_ingredient_relation where product_id='" + prodname_id + "'");
						List<Integer> active = new ArrayList<>(); // store multiple equipment id
				    	while (getactiveID.next()) 
				    	{
				    	active.add(getactiveID.getInt(2)); // get health based value
				    	}
				    
				    //get lowest solubility within api from product
				    List<Float> Solubilities = new ArrayList<>(); // store multiple equipment id
				    	for(int activeID:active)
				    	{
				    		ResultSet getallActive = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+activeID+ "'");
				    		while(getallActive.next())
				    		{
				    			Solubilities.add((float) getallActive.getFloat(9)); // get health based value
				    			System.out.println("solubilityinWater" +Solubilities + "Active:"+activeID);
				    		}
				    	}
				    	float minsolubility = Collections.min(Solubilities); // get minimum value from awithin active
				    	
				    
				    // find minimum solubility active id
				    for(int listofactiveID:active)
				    {
				    ResultSet getActive = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+listofactiveID+ "' and solubility_in_water= '"+minsolubility+ "'");
				    while(getActive.next())
				    {
				    	lowestsolubilityID =getActive.getInt(1); // get health based value
				    	System.out.println("Lowest solubility active id: "+lowestsolubilityID);
				    }
				    } // end - get lowest solubility within api from product
				    
				    
				  //get lowest ADE within api from product
				    List<Float> ade = new ArrayList<>(); // store multiple equipment id
				    	for(int activeID:active)
				    	{
				    		ResultSet getallActive = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+activeID+ "'");
				    		while(getallActive.next())
				    		{
				    			ade.add((float) getallActive.getFloat(12)); // get health based value
				    			System.out.println("ADE" +ade + "Active:"+activeID);
				    		}
				    	}
				    	float minade = Collections.min(ade); // get minimum value from awithin active
				    	System.out.println("Min ADE" +minade);
				    	
				    
				    // find minimum solubility active id
				    for(int listofactiveID:active)
				    {
				    ResultSet getActive = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+listofactiveID+ "' and lowest_route_of_admin_value LIKE '"+minade+ "'");
				    while(getActive.next())
				    {
				    	lowestADEID =getActive.getInt(1); // get health based value
				    	System.out.println("Lowest ADE active id: "+lowestADEID);
				    }
				    } // end - get lowest solubility within api from product
				    
				    
				    // get values using lowest active id
					ResultSet basisOfcalc = stmt.executeQuery("SELECT * FROM product_basis_of_calculation where active_ingredient_id ='" + lowestsolubilityID + "'");
						while (basisOfcalc.next()) 
						{
							//dose_based_flag = basisOfcalc.getInt(5);
							//health_based_flag = basisOfcalc.getInt(11); 
							Safety_Factor = basisOfcalc.getFloat(10);
							Active_Concen = basisOfcalc.getFloat(6);
							Dose_of_active = basisOfcalc.getFloat(7);
							min_no_of_dose = basisOfcalc.getFloat(8);
						} 
						
						ResultSet residuelimit = stmt.executeQuery("SELECT * FROM residue_limit");
					    while (residuelimit.next()) 
						{
					    Basislimitoption = residuelimit.getInt(2);
						}
					    System.out.println("Basislimitoption"+Basislimitoption);
					    
					    //Basis of limit option if dose or lowest between two
							if (Basislimitoption==1 || Basislimitoption==3) {
									System.out.println("Dose enabled and health disabled");
									// get dose based information
									ResultSet dosebaseddata = stmt.executeQuery("SELECT * FROM product_basis_of_calculation where active_ingredient_id ='" + lowestsolubilityID + "'");
									//System.out.println("activelist.get(0)" +activelist.get(0));
									System.out.println("lowestsolubilityID" +lowestsolubilityID);
									// While Loop to iterate through all data and print results
									while (dosebaseddata.next())
									{
										Safety_Factor = dosebaseddata.getFloat(10);
										Active_Concen = dosebaseddata.getFloat(6);
										Dose_of_active = dosebaseddata.getFloat(7);
										min_no_of_dose = dosebaseddata.getFloat(8);
									}
											if (Dose_of_active == 0) { // if dose of active is null
												doseL0 = Safety_Factor * Active_Concen * Product_Dose* (min_no_of_dose / frequency);
											} else { // if dose of active not null
												doseL0 = Safety_Factor * Dose_of_active * (min_no_of_dose / frequency);
											}
										System.out.println("Print Dose based L0" +doseL0);
							} // closing for loop
							
							//Basis of limit option if health or lowest between two
							if (Basislimitoption==2 || Basislimitoption==3) 
							{
								System.out.println("Dose disabled and health enabled");
								System.out.println("lowestsolubilityID"+lowestsolubilityID);
								System.out.println("lowestADEID"+lowestADEID);
								if(lowestADEID == lowestsolubilityID)
								{
									System.out.println(" same");
								// get health based L0 from database
								ResultSet Active = stmt.executeQuery("SELECT * FROM product_active_ingredient where id = '"+lowestsolubilityID+ "'");
								while (Active.next()) 
								{
									float health = Active.getFloat(12);
									healthL0 = health;
								}
								}else
								{
									System.out.println("Not same");
									float lowestADEDose = 0,lowestsolubilityDose = 0;
									ResultSet LowestPDEactive = stmt.executeQuery("SELECT * FROM product_basis_of_calculation where active_ingredient_id LIKE '"+lowestADEID+ "'");
									//TO DO
									while(LowestPDEactive.next())
									{
										lowestADEDose = LowestPDEactive.getFloat(7);
										System.out.println("lowestADEDose"+lowestADEDose);
									}
									ResultSet Lowestsolubilityactive = stmt.executeQuery("SELECT * FROM product_basis_of_calculation where active_ingredient_id LIKE '"+lowestsolubilityID+ "'");
									while(Lowestsolubilityactive.next())
									{
										lowestsolubilityDose = Lowestsolubilityactive.getFloat(7);
										System.out.println("lowestsolubilityDose"+lowestsolubilityDose);
									}
									healthL0 = ((minade/lowestADEDose) *lowestsolubilityDose);
								}
							}
							
							// get final L0 value
							if(doseL0==0)
							{
								L0 = healthL0;
							}
							if(healthL0==0)
							{
								L0 = doseL0;
							}
							if(healthL0!=0 && doseL0!=0)
							{
								L0 = Math.min(doseL0,healthL0);
							}
							System.out.println("Print dose L0: "+doseL0);
							System.out.println("Print health L0: "+healthL0);
							System.out.println("Print  L0: "+L0);
							connection.close();
				return L0; // return that L0 in this method
			} 		
			
			*/
			
			
			
			
			
			
			
			
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
