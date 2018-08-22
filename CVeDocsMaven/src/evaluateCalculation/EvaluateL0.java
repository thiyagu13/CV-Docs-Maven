package evaluateCalculation;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Utils;
import com.mysql.jdbc.Connection;

public class EvaluateL0 {
	

	static String tenant_id=Constant.tenant_id;
	public static double L0forSOLID(Integer activeID,String CurrenProductName) throws SQLException, ClassNotFoundException, IOException {
		float L0 = 0, healthL0=0, Safety_Factor = 0, Active_Concen = 0, Dose_of_active = 0, Product_Dose = 0, min_no_of_dose = 0,frequency = 0,min_daily_dose=0;
		int Basislimitoption=0;
		//database connection
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();
		// get current product name id FROM evaluate_product table // for finding dose based and health flag
		System.out.println("");
		ResultSet getprodname_id = stmt.executeQuery("SELECT id,dosage_interval,product_dose FROM evaluate_product where name ='"+CurrenProductName+"' && tenant_id='"+tenant_id+"'");// Execute the SQL Query to find prod id FROM evaluate_product table
		int prodname_id = 0;
		while (getprodname_id.next()) 
			{
			prodname_id = getprodname_id.getInt(1); // get name id FROM evaluate_product table
			frequency = getprodname_id.getFloat(2); // get frequency FROM evaluate_product table
			Product_Dose = getprodname_id.getFloat(3); //// get product dose FROM evaluate_product table
			}
			System.out.println("name id: " + prodname_id);
			ResultSet prod_basis_relation_id = stmt.executeQuery("SELECT * FROM evaluate_product_basis_of_calculation_relation where evaluate_product_id='" + prodname_id + "' && tenant_id='"+tenant_id+"'");
				//get active multiple active id
				List<Integer> BasisID = new ArrayList<>(); // get active list from above query
				while (prod_basis_relation_id.next()) 
				{
					BasisID.add(prod_basis_relation_id.getInt(2));
				}
			  	//System.out.println("First Active:" +activelist.get(0));// get 1st id
				System.out.println("BasisID"+BasisID);
				System.out.println("activeID"+activeID);
				for(Integer basis:BasisID) {
				ResultSet basisOfcalc = stmt.executeQuery("SELECT other_safety_factor,active_concentration,dose_of_active,min_num_of_dose,min_daily_dose FROM evaluate_product_basis_of_calculation where id ='"+basis+"' && evaluate_active_ingredient_id='"+activeID+"' && tenant_id='"+tenant_id+"'");
				while (basisOfcalc.next()) 
					{
					Safety_Factor = basisOfcalc.getFloat(1);
					Active_Concen = basisOfcalc.getFloat(2);
					Dose_of_active = basisOfcalc.getFloat(3);
					min_no_of_dose = basisOfcalc.getFloat(4);
					min_daily_dose = basisOfcalc.getFloat(5);
					}
				}
					//get active id for getting health value
				    ResultSet residuelimit = stmt.executeQuery("SELECT * FROM residue_limit where tenant_id='"+tenant_id+"'");
				    while (residuelimit.next()) 
					{
				    Basislimitoption = residuelimit.getInt(2);
					}
				    float health=0;
				    Integer HealthTerm = 0;
					float repiratoryVolume = 0;
					ResultSet Active = stmt.executeQuery("SELECT lowest_route_of_admin_value,health_based_term_id,respiratory_volume FROM evaluate_product_active_ingredient where id = '"+activeID+ "' && tenant_id='"+tenant_id+"'");
					while (Active.next()) 
					{
						 health = Active.getFloat(1);
						HealthTerm = Active.getInt(2);
						repiratoryVolume = Active.getFloat(3);
					}
						if(HealthTerm==4)
						{
							healthL0 = health *repiratoryVolume;
						}
						else
						{
							healthL0 = health;
						}
						
						float DoseL0=0;
						//Get Dose L0
						if (Dose_of_active == 0) { // if dose of active is null
							DoseL0 = Safety_Factor * Active_Concen * Product_Dose* (min_no_of_dose / frequency);
						} else { // if dose of active not null
							DoseL0 = Safety_Factor * Dose_of_active * (min_no_of_dose / frequency);
						}
						if(DoseL0==0)
						{
							DoseL0 = (float) (min_daily_dose *0.001);
						}
				    // When dose and health flag is true in basis of calculation table
					if (Basislimitoption== 3) {
						System.out.println("Both enabled");
						if(healthL0!=0 && DoseL0!=0)
						{
							if (healthL0 <= DoseL0) // compare both dose and health
							{
								L0 = healthL0;
							}
							else
							{
								L0= DoseL0;
							}
						}
						if(healthL0==0 && DoseL0!=0)
						{
							L0= DoseL0;
						}
						if(healthL0!=0 && DoseL0==0)
						{
							L0= healthL0;
						}
						
						
					
					System.out.println("Print lowest b/w health & dose L0: "+L0);
					return L0; // getting lowest L0 b/w 2
	} // for finding dose based and health flag
					
					if (Basislimitoption == 1) {
							// get dose based information
									if (Dose_of_active == 0) { // if dose of active is null
										L0 = Safety_Factor * Active_Concen * Product_Dose* (min_no_of_dose / frequency);
									} else { // if dose of active not null
										L0 = Safety_Factor * Dose_of_active * (min_no_of_dose / frequency);
									}
									if(L0==0)
									{
										L0 = (float) (min_daily_dose *0.001);
									}
								System.out.println("Print Dose based L0" +L0);
								return L0; // getting lowest L0 b/w 2
					} // closing 4th while - health based L0
					
					if (Basislimitoption== 2) 
					{
							L0 = healthL0;
						System.out.println("Print health L0: "+L0);
						return L0;
					}
					connection.close();
		return L0; // return that L0 in this method
	} //closing calculate_P1_active1_L0
	
	
	
	//if grouping approach
	//@Parameters({"productName1","productName2","productName3","productName4"})
	public static double groupingApproach_L0forSolid(String CurrenProductName) throws IOException, ClassNotFoundException, SQLException 
	{
		double L0 = 0, Safety_Factor = 0, Active_Concen = 0, Dose_of_active = 0, Product_Dose = 0, min_no_of_dose = 0,doseL0=0,healthL0 = 0,min_daily_dose=0;
		int Basislimitoption = 0,frequency = 0;
		//database connection
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();
		ResultSet getprodname_id = stmt.executeQuery("SELECT id,dosage_interval,product_dose FROM evaluate_product where name = '" + CurrenProductName + "' && tenant_id='"+tenant_id+"'");// Execute the SQL Query to find prod id FROM evaluate_product table
		int prodname_id = 0, lowestsolubilityID = 0,lowestADEID=0;
		//Get product id 
		while (getprodname_id.next()) {
			prodname_id = getprodname_id.getInt(1); // get name id FROM evaluate_product table
			frequency = getprodname_id.getInt(2); // get frequency FROM evaluate_product table
			Product_Dose = getprodname_id.getFloat(3); //// get product dose FROM evaluate_product table
			}
			System.out.println("name id: " + prodname_id);
			//get active id
			ResultSet getactiveID = stmt.executeQuery("SELECT * FROM evaluate_product_active_ingredient_relation where evaluate_product_id='" + prodname_id + "' && tenant_id='"+tenant_id+"'");
				List<Integer> active = new ArrayList<>(); // store multiple equipment id
		    	while (getactiveID.next()) 
		    	{
		    	active.add(getactiveID.getInt(2)); // get health based value
		    	}
		    
		    //get lowest solubility within api FROM evaluate_product
		    List<Float> Solubilities = new ArrayList<>(); // store multiple equipment id
		    	for(int activeID:active)
		    	{
		    		ResultSet getallActive = stmt.executeQuery("SELECT solubility_in_water FROM evaluate_product_active_ingredient where id = '"+activeID+ "' && tenant_id='"+tenant_id+"'");
		    		while(getallActive.next())
		    		{
		    			Solubilities.add((float) getallActive.getFloat(1)); // get health based value
		    			System.out.println("solubilityinWater" +Solubilities + "Active:"+activeID);
		    		}
		    	}
		    	float minsolubility = Collections.min(Solubilities); // get minimum value from awithin active
		    	
		    
		    // find minimum solubility active id
		    for(int listofactiveID:active)
		    {
		    ResultSet getActive = stmt.executeQuery("SELECT * FROM evaluate_product_active_ingredient where id = '"+listofactiveID+ "' and solubility_in_water='"+minsolubility+ "' or solubility_in_water LIKE '"+minsolubility+"' && tenant_id='"+tenant_id+"'");
		    while(getActive.next())
		    {
		    	lowestsolubilityID =getActive.getInt(1); // get health based value
		    }
		    } // end - get lowest solubility within api FROM evaluate_product
		    
		    
		  //get lowest ADE within api FROM evaluate_product
		    List<Float> ade = new ArrayList<>(); // store multiple equipment id
		    	for(int activeID:active)
		    	{
		    		Integer HealthTerm = 0;
				    float repiratoryVolume = 0;
		    		ResultSet getallActive = stmt.executeQuery("SELECT lowest_route_of_admin_value,health_based_term_id,respiratory_volume FROM evaluate_product_active_ingredient where id = '"+activeID+ "' && tenant_id='"+tenant_id+"'");
		    		while(getallActive.next())
		    		{
		    			if(HealthTerm==4) {
			    			ade.add((float) getallActive.getFloat(1) * repiratoryVolume); // get health based value
			    			System.out.println("ADE" +ade + "Active:"+activeID);
			    			}
			    			else {
			    				ade.add((float) getallActive.getFloat(1)); // get health based value
				    			System.out.println("ADE" +ade + "Active:"+activeID);
			    				}
		    		}
		    	}
		    	float minade = Collections.min(ade); // get minimum value from awithin active
		    	System.out.println("Min ADE" +minade);
		    	
		    
		    // find minimum solubility active id
		    for(int listofactiveID:active)
		    {
		    ResultSet getActive = stmt.executeQuery("SELECT * FROM evaluate_product_active_ingredient where id = '"+listofactiveID+ "' and lowest_route_of_admin_value LIKE '"+minade+ "' or lowest_route_of_admin_value='"+minade+"' && tenant_id='"+tenant_id+"'");
		    while(getActive.next())
		    {
		    	lowestADEID = getActive.getInt(1); // get health based value
		    	System.out.println("Lowest ADE active id: "+lowestADEID);
		    }
		    } // end - get lowest solubility within api FROM evaluate_product
		    
		    //Integer basisofcalID=0;
		    Set<Integer> basisofcalID = new HashSet<>();
		    ResultSet basisID = stmt.executeQuery("SELECT basis_of_calc_id FROM evaluate_product_basis_of_calculation_relation where evaluate_product_id = '"+prodname_id+ "' && tenant_id='"+tenant_id+"'");
		    while(basisID.next())
		    {
		    	basisofcalID.add(basisID.getInt(1)); // get health based value
		    }
		    
		    
		    
		    // get values using lowest active id
		   
		    for(Integer basID:basisofcalID) //get on basis of limit with active ingredient ID
		    {
		    	ResultSet basisOfcalc = stmt.executeQuery("SELECT other_safety_factor,active_concentration,dose_of_active,min_num_of_dose,min_daily_dose FROM evaluate_product_basis_of_calculation where id="+basID+" && evaluate_active_ingredient_id="+lowestsolubilityID+" && tenant_id='"+tenant_id+"'");
				while (basisOfcalc.next()) 
				{
					//dose_based_flag = basisOfcalc.getInt(5);
					//health_based_flag = basisOfcalc.getInt(11); 
					Safety_Factor = basisOfcalc.getFloat(1);
					Active_Concen = basisOfcalc.getFloat(2);
					Dose_of_active = basisOfcalc.getFloat(3);
					min_no_of_dose = basisOfcalc.getFloat(4);
					min_daily_dose = basisOfcalc.getFloat(5);
					System.out.println("Dose_of_active"+Dose_of_active);
				} 
		    }	
		    
				ResultSet residuelimit = stmt.executeQuery("SELECT l0_option FROM residue_limit where tenant_id='"+tenant_id+"'");
			    while (residuelimit.next()) 
				{
			    Basislimitoption = residuelimit.getInt(1);
				}
			    System.out.println("Basislimitoption"+Basislimitoption);
			    
			    //Basis of limit option if dose or lowest between two
					if (Basislimitoption==1 || Basislimitoption==3) {
							System.out.println("Dose enabled and health disabled");
							// get dose based information
							
									if (Dose_of_active == 0) { // if dose of active is null
										doseL0 = Safety_Factor * Active_Concen * Product_Dose* (min_no_of_dose / frequency);
									} else { // if dose of active not null
										doseL0 = Safety_Factor * Dose_of_active * (min_no_of_dose / frequency);
									}
									if(doseL0==0)
									{
										doseL0 = min_daily_dose * 0.001;
									}
									System.out.println("Min Daily Dose: "+doseL0);
								System.out.println("Print Dose based L0" +doseL0);
					} // closing for loop
					
					//Basis of limit option if health or lowest between two
					if (Basislimitoption==2 || Basislimitoption==3) 
					{
						Integer HealthTerm = 0;
					    float repiratoryVolume = 0;
						if(lowestADEID == lowestsolubilityID)
						{
							System.out.println(" same");
							// get health based L0 from database
							ResultSet Active = stmt.executeQuery("SELECT lowest_route_of_admin_value,health_based_term_id,respiratory_volume FROM evaluate_product_active_ingredient where id = '"+lowestsolubilityID+ "' && tenant_id='"+tenant_id+"'");
							while (Active.next()) 
							{
								float health = Active.getFloat(1);
								HealthTerm = Active.getInt(2);
								repiratoryVolume = Active.getFloat(3);
								if(HealthTerm==4)
								{
									healthL0 = health *repiratoryVolume;
								}
								else
								{
									healthL0 = health;
								}
							}
						}else
						{
							System.out.println("Not same");
							float lowestADEDose = 0,lowestsolubilityDose = 0;
							for(Integer basID:basisofcalID) //get on basis of limit with active ingredient ID
						    {
								ResultSet LowestPDEactive = stmt.executeQuery("SELECT dose_of_active FROM evaluate_product_basis_of_calculation where id ="+basID+" && evaluate_active_ingredient_id='"+lowestADEID+ "' && tenant_id='"+tenant_id+"'");
								//TO DO
								while(LowestPDEactive.next())
								{
									lowestADEDose = LowestPDEactive.getFloat(1);
									System.out.println("lowestADEDose"+lowestADEDose);
								}
						    
								ResultSet Lowestsolubilityactive = stmt.executeQuery("SELECT dose_of_active FROM evaluate_product_basis_of_calculation where id ="+basID+" && evaluate_active_ingredient_id='"+lowestsolubilityID+ "' && tenant_id='"+tenant_id+"'");
								while(Lowestsolubilityactive.next())
								{
									lowestsolubilityDose = Lowestsolubilityactive.getFloat(1);
									System.out.println("lowestsolubilityDose"+lowestsolubilityDose);
								}
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
	
	
	
	public static double L0forPatch(Integer activeID,String CurrenProductName) throws SQLException, ClassNotFoundException, IOException {
		float L0 = 0,doseL0=0;
		float Safety_Factor = 0, Active_Concen = 0, percentageAbsorbtion = 0,minDailyDoseperPatch = 0,minNoOFPatchesWornatTime = 0,min_daily_dose = 0;
		int Basislimitoption=0;
		//database connection
		Connection connection = Utils.db_connect();
		Statement stmt = connection.createStatement();
		// get current product name id FROM evaluate_product table // for finding dose based and health flag
		ResultSet getprodname_id = stmt.executeQuery("SELECT id,percentage_absorption FROM evaluate_product where name = '" + CurrenProductName + "' && tenant_id='"+tenant_id+"'");// Execute the SQL Query to find prod id FROM evaluate_product table
		int prodname_id = 0;
		while (getprodname_id.next()) 
			{ 
			prodname_id = getprodname_id.getInt(1); // get name id FROM evaluate_product table
			percentageAbsorbtion = getprodname_id.getFloat(2); // get name id FROM evaluate_product table
			}
			System.out.println("name id: " + prodname_id);
			ResultSet prod_basis_relation_id = stmt.executeQuery("SELECT * FROM evaluate_product_basis_of_calculation_relation where evaluate_product_id='" + prodname_id + "' && tenant_id='"+tenant_id+"'");
				//get active multiple basis of calculation ID
				List<Integer> basislist = new ArrayList<>(); // get active list from above query
				while (prod_basis_relation_id.next()) 
				{
					basislist.add(prod_basis_relation_id.getInt(2));
				}
				
				for(Integer basislistID:basislist)
				{
				ResultSet basisOfcalc = stmt.executeQuery("SELECT other_safety_factor,active_concentration,min_daily_dose_per_patch,min_no_of_patches_worn_at_one_time,min_daily_dose FROM evaluate_product_basis_of_calculation where id =" + basislistID + " && evaluate_active_ingredient_id="+activeID+" && tenant_id='"+tenant_id+"'");
				while (basisOfcalc.next()) 
					{
					Safety_Factor = basisOfcalc.getFloat(1);
					Active_Concen = basisOfcalc.getFloat(2);
					minDailyDoseperPatch = basisOfcalc.getFloat(3);
					minNoOFPatchesWornatTime = basisOfcalc.getFloat(4);
					min_daily_dose =  basisOfcalc.getFloat(5);
					}
				}
					
				    ResultSet residuelimit = stmt.executeQuery("SELECT l0_option FROM residue_limit where tenant_id='"+tenant_id+"'");
				    while (residuelimit.next()) 
					{
				    Basislimitoption = residuelimit.getInt(1);
					}
				    
				    //find health value for each active
				    float health = 0;
				    Integer HealthTerm = 0;
				    float repiratoryVolume = 0;
				    //get health based L0 from database
					ResultSet Active = stmt.executeQuery("SELECT lowest_route_of_admin_value,health_based_term_id,respiratory_volume FROM evaluate_product_active_ingredient where id = '"+activeID+ "' && tenant_id='"+tenant_id+"'");
					while (Active.next()) 
					{
						health = Active.getFloat(1);
						HealthTerm = Active.getInt(2);
						repiratoryVolume = Active.getFloat(3);
						if(HealthTerm==4)
						{
							L0 = health *repiratoryVolume;
						}
						else
						{
							L0 = health;
						}
					}
					if(health==0)
					{
						ResultSet Active1 = stmt.executeQuery("SELECT lowest_route_of_admin_value,health_based_term_id,respiratory_volume FROM product_active_ingredient where id = '"+activeID+ "' && tenant_id='"+tenant_id+"'");
						while (Active1.next()) 
						{
							health = Active1.getFloat(1);
							HealthTerm = Active1.getInt(2);
							repiratoryVolume = Active1.getFloat(3);
							if(HealthTerm==4)
							{
								L0 = health *repiratoryVolume;
							}
							else
							{
								L0 = health;
							}
						}
						
					}
					System.out.println("Health LO: "+health);
				    // When dose and health flag is true in basis of calculation table
					if (Basislimitoption== 3) {
									doseL0 = (float) (Safety_Factor * Active_Concen * (percentageAbsorbtion/100) * minDailyDoseperPatch * minNoOFPatchesWornatTime * 0.001);
									if(doseL0==0)
									{
										System.out.println("min_daily_dose: "+min_daily_dose);
										doseL0 = (float) min_daily_dose * Safety_Factor * (percentageAbsorbtion/100);
									}
									System.out.println("Min Daily Dose: "+doseL0);
									
									if (doseL0==0) // compare both dose and health
									{
										if(HealthTerm==4)
										{
											L0 = health *repiratoryVolume;
										}
										else
										{
											L0 = health;
										}
									}
									else if(health == 0)
									{
										L0=doseL0;
									}
									
									else if(health !=0 && doseL0 !=0)
									{
												if(health<=doseL0) 
												{
													if(HealthTerm==4)
													{
														L0 = health *repiratoryVolume;
													}
													else
													{
														L0 = health;
													}
												}
												else
												{
													L0=doseL0;
												}
									}	
									System.out.println("Print lowest b/w health & dose L0: "+L0);
									return L0; // getting lowest L0 b/w 2
					} // for finding dose based and health flag
					
					if (Basislimitoption == 1) 
					{
						System.out.println("Dose enabled and health disabled");
							
						L0 = (float) (Safety_Factor * Active_Concen * percentageAbsorbtion * minDailyDoseperPatch * minNoOFPatchesWornatTime * 0.001);
						if(L0==0)
						{
							L0 = (float) min_daily_dose * Safety_Factor * (percentageAbsorbtion/100);
						}
						System.out.println("Print Dose based L0: " +L0);
						return L0; // getting does L0 
					} 
					
					if (Basislimitoption== 2) 
					{
						System.out.println("Dose disabled and health enabled");
						// get health based L0 from database
						if(HealthTerm==4)
						{
							L0 = health *repiratoryVolume;
						}
						else
						{
							L0 = health;
						}
						System.out.println("Print health L0: "+L0);
						return L0;
					}
					connection.close();
		return L0; // return that L0 in this method
	} //closing calculate_P1_active1_L0
			
	
	
	
	public static double groupingApproach_L0forPatch(String CurrenProductName) throws IOException, ClassNotFoundException, SQLException 
	{
		float L0 = 0, Safety_Factor = 0, Active_Concen = 0, percentageAbsorbtion = 0, minNoOFPatchesWornatTime = 0, minDailyDoseperPatch = 0,doseL0=0,healthL0 = 0,min_daily_dose=0;
		int Basislimitoption = 0,frequency = 0;
		
		//database connection
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();
		ResultSet getprodname_id = stmt.executeQuery("SELECT id,percentage_absorption FROM evaluate_product where name = '" + CurrenProductName + "' && tenant_id='"+tenant_id+"'");// Execute the SQL Query to find prod id FROM evaluate_product table
		int prodname_id = 0, lowestsolubilityID = 0,lowestADEID=0;
		//Get product id 
		while (getprodname_id.next()) {
			prodname_id = getprodname_id.getInt(1); // get name id FROM evaluate_product table
			//frequency = getprodname_id.getInt(2); // get frequency FROM evaluate_product table
			percentageAbsorbtion = getprodname_id.getFloat(2); //// get product dose FROM evaluate_product table
			}
			System.out.println("name id: " + prodname_id);
			//get active id
			ResultSet getactiveID = stmt.executeQuery("SELECT * FROM evaluate_product_active_ingredient_relation where evaluate_product_id='" + prodname_id + "' && tenant_id='"+tenant_id+"'");
				List<Integer> active = new ArrayList<>(); // store multiple equipment id
		    	while (getactiveID.next()) 
		    	{
		    	active.add(getactiveID.getInt(2)); // get health based value
		    	}
		    
		    //get lowest solubility within api FROM evaluate_product
		    List<Float> Solubilities = new ArrayList<>(); // store multiple equipment id
		    	for(int activeID:active)
		    	{
		    		ResultSet getallActive = stmt.executeQuery("SELECT solubility_in_water FROM evaluate_product_active_ingredient where id = '"+activeID+ "' && tenant_id='"+tenant_id+"'");
		    		while(getallActive.next())
		    		{
		    			Solubilities.add((float) getallActive.getFloat(1)); // get health based value
		    			System.out.println("solubilityinWater" +Solubilities + "Active:"+activeID);
		    		}
		    	}
		    	float minsolubility = Collections.min(Solubilities); // get minimum value from awithin active
		    	
		    
		    // find minimum solubility active id
		    for(int listofactiveID:active)
		    {
		    ResultSet getActive = stmt.executeQuery("SELECT * FROM evaluate_product_active_ingredient where id = '"+listofactiveID+ "' and solubility_in_water='"+minsolubility+ "' or solubility_in_water LIKE '"+minsolubility+ "' && tenant_id='"+tenant_id+"'");
		    while(getActive.next())
		    {
		    	lowestsolubilityID =getActive.getInt(1); // get health based value
		    	System.out.println("Lowest solubility active id: "+lowestsolubilityID);
		    }
		    } // end - get lowest solubility within api FROM evaluate_product
		    
		    
		  //get lowest ADE within api FROM evaluate_product
		    List<Float> ade = new ArrayList<>(); // store multiple equipment id
		    	for(int activeID:active)
		    	{
		    		Integer HealthTerm = 0;
				    float repiratoryVolume = 0;
		    		ResultSet getallActive = stmt.executeQuery("SELECT lowest_route_of_admin_value,health_based_term_id,respiratory_volume FROM evaluate_product_active_ingredient where id = '"+activeID+ "' && tenant_id='"+tenant_id+"'");
		    		while(getallActive.next())
		    		{
		    			if(HealthTerm==4) {
			    			ade.add((float) getallActive.getFloat(1) * repiratoryVolume); // get health based value
			    			System.out.println("ADE" +ade + "Active:"+activeID);
			    			}
			    			else {
			    				ade.add((float) getallActive.getFloat(1)); // get health based value
				    			System.out.println("ADE" +ade + "Active:"+activeID);
			    				}
		    		}
		    	}
		    	float minade = Collections.min(ade); // get minimum value from awithin active
		    	System.out.println("Min ADE" +minade);
		    	
		    
		    // find minimum solubility active id
		    for(int listofactiveID:active)
		    {
		    	System.out.println("listofactiveID "+listofactiveID);
		    	ResultSet getActive = stmt.executeQuery("SELECT * FROM evaluate_product_active_ingredient where id = '"+listofactiveID+ "' && lowest_route_of_admin_value="+minade+" or lowest_route_of_admin_value LIKE "+minade+" && tenant_id='"+tenant_id+"'");
		    	while(getActive.next())
		    	{
		    		lowestADEID = getActive.getInt(1); // get health based value
		    		System.out.println("Lowest ADE active id: "+lowestADEID);
		    	}
		    } // end - get lowest solubility within api FROM evaluate_product
		    
		    //Integer basisofcalID=0;
		    Set<Integer> basisofcalID = new HashSet<>();
		    ResultSet basisID = stmt.executeQuery("SELECT basis_of_calc_id FROM evaluate_product_basis_of_calculation_relation where evaluate_product_id = '"+prodname_id+ "' && tenant_id='"+tenant_id+"'");
		    while(basisID.next())
		    {
		    	basisofcalID.add(basisID.getInt(1)); // get health based value
		    }
		    
		    
		    
		    // get values using lowest active id
		   
		    for(Integer basID:basisofcalID) //get on basis of limit with active ingredient ID
		    {
		    	ResultSet basisOfcalc = stmt.executeQuery("SELECT other_safety_factor,active_concentration,min_daily_dose_per_patch,min_no_of_patches_worn_at_one_time,min_daily_dose FROM evaluate_product_basis_of_calculation where id="+basID+" && evaluate_active_ingredient_id="+lowestsolubilityID+" && tenant_id='"+tenant_id+"'");
				while (basisOfcalc.next()) 
				{
					//dose_based_flag = basisOfcalc.getInt(5);
					//health_based_flag = basisOfcalc.getInt(11); 
					Safety_Factor = basisOfcalc.getFloat(1);
					Active_Concen = basisOfcalc.getFloat(2);
					minDailyDoseperPatch = basisOfcalc.getFloat(3);
					minNoOFPatchesWornatTime = basisOfcalc.getFloat(4);
					min_daily_dose = basisOfcalc.getFloat(5);
				} 
		    }	
		    
				ResultSet residuelimit = stmt.executeQuery("SELECT l0_option FROM residue_limit where tenant_id='"+tenant_id+"'");
			    while (residuelimit.next()) 
				{
			    Basislimitoption = residuelimit.getInt(1);
				}
			    System.out.println("Basislimitoption"+Basislimitoption);
			    
			    //Basis of limit option if dose or lowest between two
					if (Basislimitoption==1 || Basislimitoption==3) 
					{
							System.out.println("Dose enabled and health disabled");
							// get dose based information
							/*ResultSet dosebaseddata = stmt.executeQuery("SELECT * FROM evaluate_product_basis_of_calculation where id="+basisofcalID+" && evaluate_active_ingredient_id ='" + lowestsolubilityID + "'");
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
							
							doseL0 =  (float) (Safety_Factor * Active_Concen * (percentageAbsorbtion/100) * minDailyDoseperPatch * minNoOFPatchesWornatTime * 0.001);
							System.out.println("Print Dose based L0" +doseL0);
							if(doseL0==0)
							{
								doseL0 = (float) min_daily_dose * Safety_Factor * (percentageAbsorbtion/100);
							}
							System.out.println("Min Daily Dose: "+doseL0);
					} // closing for loop
					
					//Basis of limit option if health or lowest between two
					if (Basislimitoption==2 || Basislimitoption==3) 
					{
						Integer HealthTerm = 0;
					    float repiratoryVolume = 0;
						if(lowestADEID == lowestsolubilityID)
						{
							System.out.println(" same");
						// get health based L0 from database
						ResultSet Active = stmt.executeQuery("SELECT lowest_route_of_admin_value,health_based_term_id,respiratory_volume FROM evaluate_product_active_ingredient where id = '"+lowestsolubilityID+ "' && tenant_id='"+tenant_id+"'");
							while (Active.next()) 
							{
								float health = Active.getFloat(1);
								HealthTerm = Active.getInt(2);
								repiratoryVolume = Active.getFloat(3);
								if(HealthTerm==4)
								{
									healthL0 = health *repiratoryVolume;
								}
								else
								{
									healthL0 = health;
								}
							}
						}else
						{
							System.out.println("Not same");
							float lowestADEDose = 0,lowestsolubilityDose = 0;
							for(Integer basID:basisofcalID) //get on basis of limit with active ingredient ID
						    {
								ResultSet LowestPDEactive = stmt.executeQuery("SELECT min_daily_dose_per_patch FROM evaluate_product_basis_of_calculation where id ="+basID+" && evaluate_active_ingredient_id='"+lowestADEID+ "' && tenant_id='"+tenant_id+"'");
								//TO DO
								while(LowestPDEactive.next())
								{
									
									lowestADEDose = LowestPDEactive.getFloat(1);
									System.out.println("lowestADEDose"+lowestADEDose);
								}
						    
								ResultSet Lowestsolubilityactive = stmt.executeQuery("SELECT min_daily_dose_per_patch FROM evaluate_product_basis_of_calculation where id ="+basID+" && evaluate_active_ingredient_id='"+lowestsolubilityID+ "' && tenant_id='"+tenant_id+"'");
								while(Lowestsolubilityactive.next())
								{
									lowestsolubilityDose = Lowestsolubilityactive.getFloat(1);
									System.out.println("lowestsolubilityDose"+lowestsolubilityDose);
								}
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
	
	
	
	
	public static double L0forTOPICALoption2(Integer activeID,String CurrenProductName) throws SQLException, ClassNotFoundException, IOException {
		float L0 = 0,doseL0=0;
		float Safety_Factor = 0, Active_Concen = 0, minAmountApplied=0,
				minApplnFrequency=0,minBodySF=0,min_daily_dose=0;
		int Basislimitoption=0;
		//database connection
		Connection connection = Utils.db_connect();
		Statement stmt = connection.createStatement();
		// get current product name id FROM evaluate_product table // for finding dose based and health flag
		ResultSet getprodname_id = stmt.executeQuery("SELECT id FROM evaluate_product where name = '" + CurrenProductName + "' && tenant_id='"+tenant_id+"'");// Execute the SQL Query to find prod id FROM evaluate_product table
		int prodname_id = 0;
		while (getprodname_id.next()) 
			{ 
			prodname_id = getprodname_id.getInt(1); // get name id FROM evaluate_product table
			}
			System.out.println("name id: " + prodname_id);
			ResultSet prod_basis_relation_id = stmt.executeQuery("SELECT * FROM evaluate_product_basis_of_calculation_relation where evaluate_product_id='" + prodname_id + "' && tenant_id='"+tenant_id+"'");
				//get active multiple basis of calculation ID
				List<Integer> basislist = new ArrayList<>(); // get active list from above query
				while (prod_basis_relation_id.next()) 
				{
					basislist.add(prod_basis_relation_id.getInt(2));
					
				}
				
				System.out.println("basislist: "+basislist);
				for(Integer basislistID:basislist)
				{
				ResultSet basisOfcalc = stmt.executeQuery("SELECT other_safety_factor,active_concentration,min_amount_applied,min_daily_application_frequency,min_body_surface_area,min_daily_dose FROM evaluate_product_basis_of_calculation where id =" + basislistID + " && evaluate_active_ingredient_id="+activeID+" && tenant_id='"+tenant_id+"'");
				while (basisOfcalc.next()) 
					{
					Safety_Factor = basisOfcalc.getFloat(1);
					Active_Concen = basisOfcalc.getFloat(2);
					minAmountApplied= basisOfcalc.getFloat(3);
					minApplnFrequency =basisOfcalc.getFloat(4);
					minBodySF = basisOfcalc.getFloat(5);
					min_daily_dose = basisOfcalc.getFloat(6);
					System.out.println("min_daily_dose: "+min_daily_dose);
					}
					
				}
				    ResultSet residuelimit = stmt.executeQuery("SELECT l0_option FROM residue_limit where tenant_id='"+tenant_id+"'");
				    while (residuelimit.next()) 
					{
				    	Basislimitoption = residuelimit.getInt(1);
					}
				    
				    //find health value for each active
				    float health = 0;
				    float healthL0 = 0;
				    Integer HealthTerm = 0;
				    float repiratoryVolume = 0;
				    //get health based L0 from database
					ResultSet Active = stmt.executeQuery("SELECT lowest_route_of_admin_value,health_based_term_id,respiratory_volume FROM evaluate_product_active_ingredient where id = '"+activeID+ "' && tenant_id='"+tenant_id+"'");
					while (Active.next()) 
					{
						health = Active.getFloat(1);
						HealthTerm = Active.getInt(2);
						repiratoryVolume = Active.getFloat(3);
						if(HealthTerm==4)
						{
							healthL0 = health *repiratoryVolume;
						}
						else
						{
							healthL0 = health;
						}
						
					}
						System.out.println("health: "+health);
						if(healthL0==0)
						{
							 Integer HealthTerm1 = 0;
							    float repiratoryVolume1 = 0;
							ResultSet Active1 = stmt.executeQuery("SELECT lowest_route_of_admin_value,health_based_term_id,respiratory_volume FROM product_active_ingredient where id = '"+activeID+ "' && tenant_id='"+tenant_id+"'");
							while (Active1.next()) 
							{
							health = Active1.getFloat(1);
							HealthTerm1 = Active.getInt(2);
							repiratoryVolume1 = Active.getFloat(3);
							if(HealthTerm1==4)
							{
								healthL0 = health *repiratoryVolume1;
							}
							else
							{
								healthL0 = health;
							}
							}
						}
						System.out.println("health: "+health);
					
					
					
					System.out.println("Dose LO: "+doseL0);
					System.out.println("Health LO: "+health);
				    // When dose and health flag is true in basis of calculation table
					if (Basislimitoption== 3) {
									System.out.println("Both enabled");
									doseL0 = (float) (Safety_Factor * Active_Concen * minAmountApplied * minApplnFrequency * minBodySF * 0.001);
									if(doseL0==0)
									{
										doseL0 = (float) (min_daily_dose * 0.001);
									}
									System.out.println("Min Daily Dose: "+doseL0);
									
										if (doseL0==0) // compare both dose and health
										{
											L0 = healthL0;
										}
										else if(healthL0 == 0)
										{
											L0=doseL0;
										}
										
										else if(healthL0 !=0 && doseL0 !=0)
										{
													if(healthL0<=doseL0) 
													{
														L0 = healthL0;
													}
													else
													{
														L0=doseL0;
													}
										}
										
									System.out.println("Print lowest b/w health & dose L0: "+L0);
									return L0; // getting lowest L0 b/w 2
					} // for finding dose based and health flag
					
					if (Basislimitoption == 1) 
					{
						System.out.println("Dose enabled and health disabled");
							
						L0 = (float) (Safety_Factor * Active_Concen * minAmountApplied * minApplnFrequency * minBodySF * 0.001);
						if(L0==0)
						{
							L0 = (float) (min_daily_dose * 0.001);
						}
						System.out.println("Min Daily Dose: "+L0);
						System.out.println("Print Dose based L0: " +L0);
						return L0; // getting does L0 
					} 
					
					if (Basislimitoption== 2) 
					{
						System.out.println("Dose disabled and health enabled");
						// get health based L0 from database
							L0 = healthL0;
						System.out.println("Print health L0: "+L0);
						return L0;
					}
					connection.close();
		return L0; // return that L0 in this method
	} //closing topical L0
			
	
	public static double L0forTOPICALoption1(Integer activeID,String CurrenProductName) throws SQLException, ClassNotFoundException, IOException {
		float L0 = 0,doseL0=0;
		float Safety_Factor = 0, Active_Concen = 0, minAmountApplied=0,	minApplnFrequency=0,min_daily_dose=0;
		int Basislimitoption=0;
		//database connection
		Connection connection = Utils.db_connect();
		Statement stmt = connection.createStatement();
		// get current product name id FROM evaluate_product table // for finding dose based and health flag
		ResultSet getprodname_id = stmt.executeQuery("SELECT id,percentage_absorption FROM evaluate_product where name = '" + CurrenProductName + "' && tenant_id='"+tenant_id+"'");// Execute the SQL Query to find prod id FROM evaluate_product table
		int prodname_id = 0;
		while (getprodname_id.next()) 
			{ 
			prodname_id = getprodname_id.getInt(1); // get name id FROM evaluate_product table
			}
			System.out.println("name id: " + prodname_id);
			ResultSet prod_basis_relation_id = stmt.executeQuery("SELECT * FROM evaluate_product_basis_of_calculation_relation where evaluate_product_id='" + prodname_id + "' && tenant_id='"+tenant_id+"'");
				//get active multiple basis of calculation ID
				List<Integer> basislist = new ArrayList<>(); // get active list from above query
				while (prod_basis_relation_id.next()) 
				{
					basislist.add(prod_basis_relation_id.getInt(2));
				}
				
				for(Integer basislistID:basislist)
				{
				ResultSet basisOfcalc = stmt.executeQuery("SELECT other_safety_factor,active_concentration,min_amount_applied,min_daily_application_frequency,min_daily_dose FROM evaluate_product_basis_of_calculation where id =" + basislistID + " && evaluate_active_ingredient_id="+activeID+" && tenant_id='"+tenant_id+"'");
				while (basisOfcalc.next()) 
					{
					Safety_Factor = basisOfcalc.getFloat(1);
					Active_Concen = basisOfcalc.getFloat(2);
					minAmountApplied= basisOfcalc.getFloat(3);
					minApplnFrequency =basisOfcalc.getFloat(4);
					min_daily_dose = basisOfcalc.getFloat(5);
					}
				}
					
					
				    ResultSet residuelimit = stmt.executeQuery("SELECT l0_option FROM residue_limit where tenant_id='"+tenant_id+"'");
				    while (residuelimit.next()) 
					{
				    Basislimitoption = residuelimit.getInt(1);
					}
				    
				    //find health value for each active
				    float health = 0;
				    float healthL0 = 0;
				    Integer HealthTerm = 0;
				    float repiratoryVolume = 0;
				    //get health based L0 from database
					ResultSet Active = stmt.executeQuery("SELECT lowest_route_of_admin_value,health_based_term_id,respiratory_volume FROM evaluate_product_active_ingredient where id = '"+activeID+ "' && tenant_id='"+tenant_id+"'");
					while (Active.next()) 
					{
						health = Active.getFloat(1);
						HealthTerm = Active.getInt(2);
						repiratoryVolume = Active.getFloat(3);
						if(HealthTerm==4)
						{
							healthL0 = health *repiratoryVolume;
						}
						else
						{
							healthL0 = health;
						}
						
					}
						System.out.println("health: "+health);
						if(healthL0==0)
						{
							 Integer HealthTerm1 = 0;
							    float repiratoryVolume1 = 0;
							ResultSet Active1 = stmt.executeQuery("SELECT lowest_route_of_admin_value,health_based_term_id,respiratory_volume FROM product_active_ingredient where id = '"+activeID+ "' && tenant_id='"+tenant_id+"'");
							while (Active1.next()) 
							{
							health = Active1.getFloat(1);
							HealthTerm1 = Active.getInt(2);
							repiratoryVolume1 = Active.getFloat(3);
							if(HealthTerm1==4)
							{
								healthL0 = health *repiratoryVolume1;
							}
							else
							{
								healthL0 = health;
							}
							}
						}
						System.out.println("health: "+health);
					
					
					
				    // When dose and health flag is true in basis of calculation table
					if (Basislimitoption== 3) {
									System.out.println("Both enabled");
									doseL0 = (float) (Safety_Factor * Active_Concen * minAmountApplied * minApplnFrequency *0.001);
									System.out.println("Dose LO: "+doseL0);
									if(doseL0==0)
									{
										doseL0 = (float) (min_daily_dose *Safety_Factor);
									}
									System.out.println("Min Daily Dose: "+doseL0);
									
										if (doseL0==0) // compare both dose and health
										{
											L0 = healthL0;
										}
										else if(healthL0 == 0)
										{
											L0=doseL0;
										}
										
										else if(healthL0 !=0 && doseL0 !=0)
										{
													if(healthL0<=doseL0) 
													{
														L0 = healthL0;
													}
													else
													{
														L0=doseL0;
													}
										}
									System.out.println("Print lowest b/w health & dose L0: "+L0);
									return L0; // getting lowest L0 b/w 2
					} // for finding dose based and health flag
					
					if (Basislimitoption == 1) 
					{
						System.out.println("Dose enabled and health disabled");
							
						L0 = (float) (Safety_Factor * Active_Concen * minAmountApplied * minApplnFrequency * 0.001);
						if(L0==0)
						{
							L0 = (float) (min_daily_dose * Safety_Factor);
						}
						System.out.println("Min Daily Dose: "+L0);
						System.out.println("Print Dose based L0: " +L0);
						return L0; // getting does L0 
					} 
					
					if (Basislimitoption== 2) 
					{
						System.out.println("Dose disabled and health enabled");
						// get health based L0 from database
							L0 = healthL0;
						System.out.println("Print health L0: "+L0);
						return L0;
					}
					connection.close();
		return L0; // return that L0 in this method
	} //clsoing topical L0
			
	
	
	
	public static double groupingApproach_L0forTOPICAL(String CurrenProductName) throws IOException, ClassNotFoundException, SQLException 
	{
		float L0 = 0, doseL0=0,healthL0 = 0,Safety_Factor = 0, Active_Concen = 0,minAmountApplied=0,min_daily_dose=0,
				minApplnFrequency=0,minBodySF=1,max_amount_appled=0,max_ap_freq=0,max_Body_Sf=1;
		int Basislimitoption = 0,grouping_criteria_option=0;
		
		//database connection
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();
		ResultSet getprodname_id = stmt.executeQuery("SELECT id,max_amount_applied,max_daily_application_frequency,max_body_surface_area,grouping_criteria_option FROM evaluate_product where name = '" + CurrenProductName + "' && tenant_id='"+tenant_id+"'");// Execute the SQL Query to find prod id FROM evaluate_product table
		int prodname_id = 0, lowestsolubilityID = 0,lowestADEID=0;
		//Get product id 
		while (getprodname_id.next()) {
			prodname_id = getprodname_id.getInt(1); // get name id FROM evaluate_product table
			max_amount_appled = getprodname_id.getInt(2); // get name id FROM evaluate_product table
			max_ap_freq = getprodname_id.getInt(3); // get name id FROM evaluate_product table
			max_Body_Sf = getprodname_id.getInt(4); // get name id FROM evaluate_product table
			grouping_criteria_option =  getprodname_id.getInt(5); 
			}
			System.out.println("name id: " + prodname_id);
			//get active id
			ResultSet getactiveID = stmt.executeQuery("SELECT * FROM evaluate_product_active_ingredient_relation where evaluate_product_id='" + prodname_id + "' && tenant_id='"+tenant_id+"'");
				List<Integer> active = new ArrayList<>(); // store multiple equipment id
		    	while (getactiveID.next()) 
		    	{
		    	active.add(getactiveID.getInt(2)); // get health based value
		    	}
		    
		    //get lowest solubility within api FROM evaluate_product
		    List<Float> Solubilities = new ArrayList<>(); // store multiple equipment id
		    	for(int activeID:active)
		    	{
		    		ResultSet getallActive = stmt.executeQuery("SELECT solubility_in_water FROM evaluate_product_active_ingredient where id = '"+activeID+ "' && tenant_id='"+tenant_id+"'");
		    		while(getallActive.next())
		    		{
		    			Solubilities.add((float) getallActive.getFloat(1)); // get health based value
		    			System.out.println("solubilityinWater" +Solubilities + "Active:"+activeID);
		    		}
		    	}
		    	float minsolubility = Collections.min(Solubilities); // get minimum value from awithin active
		    	
		    
		    // find minimum solubility active id
		    for(int listofactiveID:active)
		    {
		    ResultSet getActive = stmt.executeQuery("SELECT * FROM evaluate_product_active_ingredient where id = '"+listofactiveID+ "' and solubility_in_water='"+minsolubility+ "' or solubility_in_water LIKE '"+minsolubility+ "' && tenant_id='"+tenant_id+"'");
		    while(getActive.next())
		    {
		    	lowestsolubilityID =getActive.getInt(1); // get health based value
		    	System.out.println("Lowest solubility active id: "+lowestsolubilityID);
		    }
		    } // end - get lowest solubility within api FROM evaluate_product
		    
		    
		  //get lowest ADE within api FROM evaluate_product
		    List<Float> ade = new ArrayList<>(); // store multiple equipment id
		    	for(int activeID:active)
		    	{
		    		Integer HealthTerm = 0;
				    float repiratoryVolume = 0;
		    		ResultSet getallActive = stmt.executeQuery("SELECT lowest_route_of_admin_value,health_based_term_id,respiratory_volume FROM evaluate_product_active_ingredient where id = '"+activeID+ "' && tenant_id='"+tenant_id+"'");
		    		while(getallActive.next())
		    		{
		    			if(HealthTerm==4) {
			    			ade.add((float) getallActive.getFloat(1) * repiratoryVolume); // get health based value
			    			System.out.println("ADE" +ade + "Active:"+activeID);
			    			}
			    			else {
			    				ade.add((float) getallActive.getFloat(1)); // get health based value
				    			System.out.println("ADE" +ade + "Active:"+activeID);
			    				}
		    		}
		    	}
		    	float minade = Collections.min(ade); // get minimum value from awithin active
		    	System.out.println("Min ADE" +minade);
		    	
		    
		    // find minimum solubility active id
		    for(int listofactiveID:active)
		    {
		    	System.out.println("listofactiveID "+listofactiveID);
		    	ResultSet getActive = stmt.executeQuery("SELECT * FROM evaluate_product_active_ingredient where id = '"+listofactiveID+ "' && lowest_route_of_admin_value="+minade+" or lowest_route_of_admin_value LIKE "+minade+" && tenant_id='"+tenant_id+"'");
		    	while(getActive.next())
		    	{
		    		lowestADEID = getActive.getInt(1); // get health based value
		    		System.out.println("Lowest ADE active id: "+lowestADEID);
		    	}
		    } // end - get lowest solubility within api FROM evaluate_product
		    
		    //Integer basisofcalID=0;
		    Set<Integer> basisofcalID = new HashSet<>();
		    ResultSet basisID = stmt.executeQuery("SELECT basis_of_calc_id FROM evaluate_product_basis_of_calculation_relation where evaluate_product_id = '"+prodname_id+ "' && tenant_id='"+tenant_id+"'");
		    while(basisID.next())
		    {
		    	basisofcalID.add(basisID.getInt(1)); // get health based value
		    }
		    
		    
		    
		    // get values using lowest active id
		   
		    for(Integer basID:basisofcalID) //get on basis of limit with active ingredient ID
		    {
		    	ResultSet basisOfcalc = stmt.executeQuery("SELECT other_safety_factor,active_concentration,min_amount_applied,min_daily_application_frequency,min_body_surface_area,min_daily_dose FROM evaluate_product_basis_of_calculation where id="+basID+" && evaluate_active_ingredient_id="+lowestsolubilityID+" && tenant_id='"+tenant_id+"'");
				while (basisOfcalc.next()) 
				{
					//dose_based_flag = basisOfcalc.getInt(5);
					//health_based_flag = basisOfcalc.getInt(11); 
					Safety_Factor = basisOfcalc.getFloat(1);
					Active_Concen = basisOfcalc.getFloat(2);
					minAmountApplied= basisOfcalc.getFloat(3);
					minApplnFrequency =basisOfcalc.getFloat(4);
					minBodySF = basisOfcalc.getFloat(5);
					min_daily_dose = basisOfcalc.getFloat(6);
				} 
		    }	
		    
				ResultSet residuelimit = stmt.executeQuery("SELECT l0_option FROM residue_limit where tenant_id='"+tenant_id+"'");
			    while (residuelimit.next()) 
				{
			    Basislimitoption = residuelimit.getInt(1);
				}
			    System.out.println("Basislimitoption"+Basislimitoption);
			    
			    //Basis of limit option if dose or lowest between two
					if (Basislimitoption==1 || Basislimitoption==3) 
					{
							System.out.println("Dose enabled and health disabled");
							// get dose based information
							doseL0 = (float) (Safety_Factor * Active_Concen * minAmountApplied * minApplnFrequency * minBodySF * 0.001);
							System.out.println("Print Dose based L0" +doseL0);
							if(doseL0==0)
							{
								doseL0 = (float) (min_daily_dose * 0.001);
							}
							System.out.println("Min Daily Dose: "+doseL0);
							
					} // closing for loop
					
					//Basis of limit option if health or lowest between two
					if (Basislimitoption==2 || Basislimitoption==3) 
					{
						System.out.println("Dose disabled and health enabled");
						System.out.println("lowestsolubilityID"+lowestsolubilityID);
						System.out.println("lowestADEID: "+lowestADEID);
						if(lowestADEID == lowestsolubilityID)
						{
							System.out.println(" same");
						// get health based L0 from database
							Integer HealthTerm = 0;
						    float repiratoryVolume = 0;
						ResultSet Active = stmt.executeQuery("SELECT lowest_route_of_admin_value,health_based_term_id,respiratory_volume FROM evaluate_product_active_ingredient where id = '"+lowestsolubilityID+ "' && tenant_id='"+tenant_id+"'");
							while (Active.next()) 
							{
								float health = Active.getFloat(1);
								HealthTerm = Active.getInt(2);
								repiratoryVolume = Active.getFloat(3);
								if(HealthTerm==4)
								{
									healthL0 = health *repiratoryVolume;
								}
								else
								{
									healthL0 = health;
								}
							}
						}else
						{
							System.out.println("Not same");
							float lowestADEDose=0,lowestADEminDose = 0,lowestADEminBodySF = 0,lowestsolubilityDose = 0,lowestsolubilityminAmount=0,lowestsolubilityminBodySF=0;
							for(Integer basID:basisofcalID) //get on basis of limit with active ingredient ID
						    {
								ResultSet LowestPDEactive = stmt.executeQuery("SELECT min_amount_applied,min_body_surface_area FROM evaluate_product_basis_of_calculation where id ="+basID+" && evaluate_active_ingredient_id='"+lowestADEID+ "' && tenant_id='"+tenant_id+"'");
								//TO DO
								while(LowestPDEactive.next())
								{
									
									lowestADEminDose = LowestPDEactive.getFloat(1);
									lowestADEminBodySF = LowestPDEactive.getFloat(2);
									if(grouping_criteria_option==2)
									{
										lowestADEDose = lowestADEminDose + lowestADEminBodySF;	
									}else
									{
										lowestADEDose = lowestADEminDose;
									}
									
									System.out.println("lowestADEDose"+lowestADEDose);
								}
						    
								ResultSet Lowestsolubilityactive = stmt.executeQuery("SELECT min_amount_applied,min_body_surface_area FROM evaluate_product_basis_of_calculation where id ="+basID+" && evaluate_active_ingredient_id='"+lowestsolubilityID+ "' && tenant_id='"+tenant_id+"'");
								while(Lowestsolubilityactive.next())
								{
									lowestsolubilityminAmount = Lowestsolubilityactive.getFloat(1);
									lowestsolubilityminBodySF = Lowestsolubilityactive.getFloat(2);
									if(grouping_criteria_option==2)
									{
										lowestsolubilityDose = lowestsolubilityminAmount + lowestsolubilityminBodySF;	
									}else
									{
										lowestsolubilityDose = lowestsolubilityminAmount;
									}
									
									System.out.println("lowestsolubilityDose"+lowestsolubilityDose);
								}
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
	
	
	
	public static double groupingApproach_L1forSolidSameProduct(String CurrenProductName) throws IOException, ClassNotFoundException, SQLException 
	{
		double L0 = 0,DoseL1=0, max_no_of_Dose=0,Safety_Factor = 0, Active_Concen = 0, Dose_of_active = 0, Product_Dose = 0, min_no_of_dose = 0,doseL0=0,healthL0 = 0,min_daily_dose=0;
		int Basislimitoption = 0,frequency = 0;
		//database connection
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();
		ResultSet getprodname_id = stmt.executeQuery("SELECT id,dosage_interval,product_dose,max_no_of_doses FROM evaluate_product where name = '" + CurrenProductName + "' && tenant_id='"+tenant_id+"'");// Execute the SQL Query to find prod id FROM evaluate_product table
		int prodname_id = 0, lowestsolubilityID = 0,lowestADEID=0;
		//Get product id 
		while (getprodname_id.next()) {
			prodname_id = getprodname_id.getInt(1); // get name id FROM evaluate_product table
			frequency = getprodname_id.getInt(2); // get frequency FROM evaluate_product table
			Product_Dose = getprodname_id.getFloat(3); //// get product dose FROM evaluate_product table
			max_no_of_Dose = getprodname_id.getFloat(4);
			}
			System.out.println("name id: " + prodname_id);
			//get active id
			ResultSet getactiveID = stmt.executeQuery("SELECT * FROM evaluate_product_active_ingredient_relation where evaluate_product_id='" + prodname_id + "' && tenant_id='"+tenant_id+"'");
				List<Integer> active = new ArrayList<>(); // store multiple equipment id
		    	while (getactiveID.next()) 
		    	{
		    	active.add(getactiveID.getInt(2)); // get health based value
		    	}
		    
		    //get lowest solubility within api FROM evaluate_product
		    List<Float> Solubilities = new ArrayList<>(); // store multiple equipment id
		    	for(int activeID:active)
		    	{
		    		ResultSet getallActive = stmt.executeQuery("SELECT solubility_in_water FROM evaluate_product_active_ingredient where id = '"+activeID+ "' && tenant_id='"+tenant_id+"'");
		    		while(getallActive.next())
		    		{
		    			Solubilities.add((float) getallActive.getFloat(1)); // get health based value
		    			System.out.println("solubilityinWater" +Solubilities + "Active:"+activeID);
		    		}
		    	}
		    	float minsolubility = Collections.min(Solubilities); // get minimum value from awithin active
		    	
		    
		    // find minimum solubility active id
		    for(int listofactiveID:active)
		    {
		    ResultSet getActive = stmt.executeQuery("SELECT * FROM evaluate_product_active_ingredient where id = '"+listofactiveID+ "' and solubility_in_water='"+minsolubility+ "' or solubility_in_water LIKE '"+minsolubility+"' && tenant_id='"+tenant_id+"'");
		    while(getActive.next())
		    {
		    	lowestsolubilityID =getActive.getInt(1); // get health based value
		    	System.out.println("Lowest solubility active id: "+lowestsolubilityID);
		    }
		    } // end - get lowest solubility within api FROM evaluate_product
		    
		    
		  //get lowest ADE within api FROM evaluate_product
		    List<Float> ade = new ArrayList<>(); // store multiple equipment id
		    	for(int activeID:active)
		    	{
		    		Integer HealthTerm = 0;
				    float repiratoryVolume = 0;
		    		ResultSet getallActive = stmt.executeQuery("SELECT lowest_route_of_admin_value,health_based_term_id,respiratory_volume FROM evaluate_product_active_ingredient where id = '"+activeID+ "' && tenant_id='"+tenant_id+"'");
		    		while(getallActive.next())
		    		{
		    			if(HealthTerm==4) {
			    			ade.add((float) getallActive.getFloat(1) * repiratoryVolume); // get health based value
			    			System.out.println("ADE" +ade + "Active:"+activeID);
			    			}
			    			else {
			    				ade.add((float) getallActive.getFloat(1)); // get health based value
				    			System.out.println("ADE" +ade + "Active:"+activeID);
			    				}
		    		}
		    	}
		    	float minade = Collections.min(ade); // get minimum value from awithin active
		    	System.out.println("Min ADE" +minade);
		    	
		    
		    // find minimum solubility active id
		    for(int listofactiveID:active)
		    {
		    ResultSet getActive = stmt.executeQuery("SELECT * FROM evaluate_product_active_ingredient where id = '"+listofactiveID+ "' and lowest_route_of_admin_value LIKE '"+minade+ "' or lowest_route_of_admin_value='"+minade+"' && tenant_id='"+tenant_id+"'");
		    while(getActive.next())
		    {
		    	lowestADEID = getActive.getInt(1); // get health based value
		    	System.out.println("Lowest ADE active id: "+lowestADEID);
		    }
		    } // end - get lowest solubility within api FROM evaluate_product
		    
		    //Integer basisofcalID=0;
		    Set<Integer> basisofcalID = new HashSet<>();
		    ResultSet basisID = stmt.executeQuery("SELECT basis_of_calc_id FROM evaluate_product_basis_of_calculation_relation where evaluate_product_id = '"+prodname_id+ "' && tenant_id='"+tenant_id+"'");
		    while(basisID.next())
		    {
		    	basisofcalID.add(basisID.getInt(1)); // get health based value
		    }
		    
		    
		    
		    // get values using lowest active id
		   
		    for(Integer basID:basisofcalID) //get on basis of limit with active ingredient ID
		    {
		    	ResultSet basisOfcalc = stmt.executeQuery("SELECT other_safety_factor,active_concentration,dose_of_active,min_num_of_dose,min_daily_dose FROM evaluate_product_basis_of_calculation where id="+basID+" && evaluate_active_ingredient_id="+lowestsolubilityID+" && tenant_id='"+tenant_id+"'");
				while (basisOfcalc.next()) 
				{
					//dose_based_flag = basisOfcalc.getInt(5);
					//health_based_flag = basisOfcalc.getInt(11); 
					Safety_Factor = basisOfcalc.getFloat(1);
					Active_Concen = basisOfcalc.getFloat(2);
					Dose_of_active = basisOfcalc.getFloat(3);
					min_no_of_dose = basisOfcalc.getFloat(4);
					min_daily_dose = basisOfcalc.getFloat(5);
					System.out.println("Dose_of_active"+Dose_of_active);
				} 
		    }	
		    
				ResultSet residuelimit = stmt.executeQuery("SELECT l0_option FROM residue_limit where tenant_id='"+tenant_id+"'");
			    while (residuelimit.next()) 
				{
			    Basislimitoption = residuelimit.getInt(1);
				}
			    System.out.println("Basislimitoption"+Basislimitoption);
			    
			    //Basis of limit option if dose or lowest between two
					if (Basislimitoption==1 || Basislimitoption==3) {
							System.out.println("Dose enabled and health disabled");
							// get dose based information
							
									if (Dose_of_active == 0) { // if dose of active is null
										doseL0 = Safety_Factor * Active_Concen * Product_Dose* (min_no_of_dose / frequency);
									} else { // if dose of active not null
										doseL0 = Safety_Factor * Dose_of_active * (min_no_of_dose / frequency);
									}
									if(doseL0==0)
									{
										doseL0 = min_daily_dose * 0.001;
									}
									System.out.println("Min Daily Dose: "+doseL0);
								System.out.println("Print Dose based L0" +doseL0);
								if(Active_Concen!=0)
								{
									DoseL1 = Safety_Factor * Active_Concen;
								}
								if (Dose_of_active!=0 && Product_Dose!=0) 
								{
									DoseL1 = Safety_Factor * Dose_of_active * Product_Dose;
								} 
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
							Integer HealthTerm = 0;
						    float repiratoryVolume = 0;
							// get health based L0 from database
							ResultSet Active = stmt.executeQuery("SELECT lowest_route_of_admin_value,health_based_term_id,respiratory_volume FROM evaluate_product_active_ingredient where id = '"+lowestsolubilityID+ "' && tenant_id='"+tenant_id+"'");
							while (Active.next()) 
							{
								float health = Active.getFloat(1);
								HealthTerm = Active.getInt(2);
								repiratoryVolume = Active.getFloat(3);
								if(HealthTerm==4)
								{
									healthL0 = health *repiratoryVolume;
								}
								else
								{
									healthL0 = health;
								}
							}
						}else
						{
							System.out.println("Not same");
							float lowestADEDose = 0,lowestsolubilityDose = 0;
							for(Integer basID:basisofcalID) //get on basis of limit with active ingredient ID
						    {
								ResultSet LowestPDEactive = stmt.executeQuery("SELECT dose_of_active FROM evaluate_product_basis_of_calculation where id ="+basID+" && evaluate_active_ingredient_id='"+lowestADEID+ "' && tenant_id='"+tenant_id+"'");
								//TO DO
								while(LowestPDEactive.next())
								{
									lowestADEDose = LowestPDEactive.getFloat(1);
									System.out.println("lowestADEDose"+lowestADEDose);
								}
						    
								ResultSet Lowestsolubilityactive = stmt.executeQuery("SELECT dose_of_active FROM evaluate_product_basis_of_calculation where id ="+basID+" && evaluate_active_ingredient_id='"+lowestsolubilityID+ "' && tenant_id='"+tenant_id+"'");
								while(Lowestsolubilityactive.next())
								{
									lowestsolubilityDose = Lowestsolubilityactive.getFloat(1);
									System.out.println("lowestsolubilityDose"+lowestsolubilityDose);
								}
						    }
							healthL0 = ((minade/lowestADEDose) *lowestsolubilityDose);
						}
					}
					float L1=0;
					// get final L0 value
					if(doseL0==0)
					{
						L1 = (float) (healthL0/(Product_Dose * (max_no_of_Dose/frequency)));
					}
					if(healthL0==0)
					{
						if(Active_Concen!=0)
						{
						L1 = (float) (Safety_Factor * Active_Concen);
						}
						else if(Product_Dose!=0 && Dose_of_active!=0)
						{
							L1 = (float) (Safety_Factor * Product_Dose * Dose_of_active);
						}
					}
					if(healthL0!=0 && doseL0!=0)
					{
						if(doseL0<healthL0)
						{
							L1= (float) (Safety_Factor * Active_Concen);
						}
						else
						{
							L1 = (float) (healthL0/(Product_Dose * (max_no_of_Dose/frequency)));
						}
					}
					System.out.println("Print dose L1: "+doseL0);
					System.out.println("Print health L0: "+healthL0);
					System.out.println("Print  L0: "+L1);
					connection.close();
		return L1; // return that L0 in this method
	} 
	
	
	
	public static double groupingApproach_L1forTOPICALSameProduct(String CurrenProductName) throws IOException, ClassNotFoundException, SQLException 
	{
		float L0 = 0, doseL0=0,healthL0 = 0,Safety_Factor = 0, Active_Concen = 0,minAmountApplied=0,min_daily_dose=0,
				minApplnFrequency=0,minBodySF=1,max_amount_appled=0,max_ap_freq=0,max_Body_Sf=1;
		int Basislimitoption = 0,grouping_criteria_option=0;
		
		//database connection
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();
		ResultSet getprodname_id = stmt.executeQuery("SELECT id,max_amount_applied,max_daily_application_frequency,max_body_surface_area,grouping_criteria_option FROM evaluate_product where name = '" + CurrenProductName + "' && tenant_id='"+tenant_id+"'");// Execute the SQL Query to find prod id FROM evaluate_product table
		int prodname_id = 0, lowestsolubilityID = 0,lowestADEID=0;
		//Get product id 
		while (getprodname_id.next()) {
			prodname_id = getprodname_id.getInt(1); // get name id FROM evaluate_product table
			max_amount_appled = getprodname_id.getInt(2); // get name id FROM evaluate_product table
			max_ap_freq = getprodname_id.getInt(3); // get name id FROM evaluate_product table
			max_Body_Sf = getprodname_id.getInt(4); // get name id FROM evaluate_product table
			grouping_criteria_option =  getprodname_id.getInt(5); 
			
			}
			System.out.println("name id: " + prodname_id);
			//get active id
			ResultSet getactiveID = stmt.executeQuery("SELECT * FROM evaluate_product_active_ingredient_relation where evaluate_product_id='" + prodname_id + "' && tenant_id='"+tenant_id+"'");
				List<Integer> active = new ArrayList<>(); // store multiple equipment id
		    	while (getactiveID.next()) 
		    	{
		    	active.add(getactiveID.getInt(2)); // get health based value
		    	}
		    
		    //get lowest solubility within api FROM evaluate_product
		    List<Float> Solubilities = new ArrayList<>(); // store multiple equipment id
		    	for(int activeID:active)
		    	{
		    		ResultSet getallActive = stmt.executeQuery("SELECT solubility_in_water FROM evaluate_product_active_ingredient where id = '"+activeID+ "' && tenant_id='"+tenant_id+"'");
		    		while(getallActive.next())
		    		{
		    			Solubilities.add((float) getallActive.getFloat(1)); // get health based value
		    			System.out.println("solubilityinWater" +Solubilities + "Active:"+activeID);
		    		}
		    	}
		    	float minsolubility = Collections.min(Solubilities); // get minimum value from awithin active
		    	
		    
		    // find minimum solubility active id
		    for(int listofactiveID:active)
		    {
		    ResultSet getActive = stmt.executeQuery("SELECT * FROM evaluate_product_active_ingredient where id = '"+listofactiveID+ "' and solubility_in_water='"+minsolubility+ "' or solubility_in_water LIKE '"+minsolubility+ "' && tenant_id='"+tenant_id+"'");
		    while(getActive.next())
		    {
		    	lowestsolubilityID =getActive.getInt(1); // get health based value
		    	System.out.println("Lowest solubility active id: "+lowestsolubilityID);
		    }
		    } // end - get lowest solubility within api FROM evaluate_product
		    
		    
		  //get lowest ADE within api FROM evaluate_product
		    List<Float> ade = new ArrayList<>(); // store multiple equipment id
		    	for(int activeID:active)
		    	{
		    		Integer HealthTerm = 0;
				    float repiratoryVolume = 0;
		    		ResultSet getallActive = stmt.executeQuery("SELECT lowest_route_of_admin_value,health_based_term_id,respiratory_volume FROM evaluate_product_active_ingredient where id = '"+activeID+ "' && tenant_id='"+tenant_id+"'");
		    		while(getallActive.next())
		    		{
		    			if(HealthTerm==4) {
			    			ade.add((float) getallActive.getFloat(1) * repiratoryVolume); // get health based value
			    			System.out.println("ADE" +ade + "Active:"+activeID);
			    			}
			    			else {
			    				ade.add((float) getallActive.getFloat(1)); // get health based value
				    			System.out.println("ADE" +ade + "Active:"+activeID);
			    				}
		    		}
		    	}
		    	float minade = Collections.min(ade); // get minimum value from awithin active
		    	System.out.println("Min ADE" +minade);
		    	
		    
		    // find minimum solubility active id
		    for(int listofactiveID:active)
		    {
		    	System.out.println("listofactiveID "+listofactiveID);
		    	ResultSet getActive = stmt.executeQuery("SELECT * FROM evaluate_product_active_ingredient where id = '"+listofactiveID+ "' && lowest_route_of_admin_value="+minade+" or lowest_route_of_admin_value LIKE "+minade+" && tenant_id='"+tenant_id+"'");
		    	while(getActive.next())
		    	{
		    		lowestADEID = getActive.getInt(1); // get health based value
		    		System.out.println("Lowest ADE active id: "+lowestADEID);
		    	}
		    } // end - get lowest solubility within api FROM evaluate_product
		    
		    //Integer basisofcalID=0;
		    Set<Integer> basisofcalID = new HashSet<>();
		    ResultSet basisID = stmt.executeQuery("SELECT basis_of_calc_id FROM evaluate_product_basis_of_calculation_relation where evaluate_product_id = '"+prodname_id+ "' && tenant_id='"+tenant_id+"'");
		    while(basisID.next())
		    {
		    	basisofcalID.add(basisID.getInt(1)); // get health based value
		    }
		    
		    
		    
		    // get values using lowest active id
		   
		    for(Integer basID:basisofcalID) //get on basis of limit with active ingredient ID
		    {
		    	ResultSet basisOfcalc = stmt.executeQuery("SELECT other_safety_factor,active_concentration,min_amount_applied,min_daily_application_frequency,min_body_surface_area,min_daily_dose FROM evaluate_product_basis_of_calculation where id="+basID+" && evaluate_active_ingredient_id="+lowestsolubilityID+" && tenant_id='"+tenant_id+"'");
				while (basisOfcalc.next()) 
				{
					//dose_based_flag = basisOfcalc.getInt(5);
					//health_based_flag = basisOfcalc.getInt(11); 
					Safety_Factor = basisOfcalc.getFloat(1);
					Active_Concen = basisOfcalc.getFloat(2);
					minAmountApplied= basisOfcalc.getFloat(3);
					minApplnFrequency =basisOfcalc.getFloat(4);
					minBodySF = basisOfcalc.getFloat(5);
					min_daily_dose = basisOfcalc.getFloat(6);
				} 
		    }	
		    
				ResultSet residuelimit = stmt.executeQuery("SELECT l0_option FROM residue_limit where tenant_id='"+tenant_id+"'");
			    while (residuelimit.next()) 
				{
			    Basislimitoption = residuelimit.getInt(1);
				}
			    System.out.println("Basislimitoption"+Basislimitoption);
			    
			    //Basis of limit option if dose or lowest between two
					if (Basislimitoption==1 || Basislimitoption==3) 
					{
							System.out.println("Dose enabled and health disabled");
							// get dose based information
							
							doseL0 = (float) (Safety_Factor * Active_Concen * minAmountApplied * minApplnFrequency * minBodySF * 0.001);
							System.out.println("Print Dose based L0" +doseL0);
							if(doseL0==0)
							{
								doseL0 = (float) (min_daily_dose * 0.001);
							}
							System.out.println("Min Daily Dose: "+doseL0);
							
					} // closing for loop
					
					//Basis of limit option if health or lowest between two
					if (Basislimitoption==2 || Basislimitoption==3) 
					{
						System.out.println("Dose disabled and health enabled");
						System.out.println("lowestsolubilityID"+lowestsolubilityID);
						System.out.println("lowestADEID: "+lowestADEID);
						if(lowestADEID == lowestsolubilityID)
						{
							System.out.println(" same");
							Integer HealthTerm = 0;
						    float repiratoryVolume = 0;
						// get health based L0 from database
						ResultSet Active = stmt.executeQuery("SELECT lowest_route_of_admin_value,health_based_term_id,respiratory_volume FROM evaluate_product_active_ingredient where id = '"+lowestsolubilityID+ "' && tenant_id='"+tenant_id+"'");
							while (Active.next()) 
							{
								float health = Active.getFloat(1);
								HealthTerm = Active.getInt(2);
								repiratoryVolume = Active.getFloat(3);
								if(HealthTerm==4)
								{
									healthL0 = health *repiratoryVolume;
								}
								else
								{
									healthL0 = health;
								}
							}
						}else
						{
							System.out.println("Not same");
							float lowestADEDose=0,lowestADEminDose = 0,lowestADEminBodySF = 0,lowestsolubilityDose = 0,lowestsolubilityminAmount=0,lowestsolubilityminBodySF=0;
							for(Integer basID:basisofcalID) //get on basis of limit with active ingredient ID
						    {
								ResultSet LowestPDEactive = stmt.executeQuery("SELECT min_amount_applied,min_body_surface_area FROM evaluate_product_basis_of_calculation where id ="+basID+" && evaluate_active_ingredient_id='"+lowestADEID+ "' && tenant_id='"+tenant_id+"'");
								//TO DO
								while(LowestPDEactive.next())
								{
									
									lowestADEminDose = LowestPDEactive.getFloat(1);
									lowestADEminBodySF = LowestPDEactive.getFloat(2);
									if(grouping_criteria_option==2)
									{
										lowestADEDose = lowestADEminDose + lowestADEminBodySF;	
									}else
									{
										lowestADEDose = lowestADEminDose;
									}
									
									System.out.println("lowestADEDose"+lowestADEDose);
								}
						    
								ResultSet Lowestsolubilityactive = stmt.executeQuery("SELECT min_amount_applied,min_body_surface_area FROM evaluate_product_basis_of_calculation where id ="+basID+" && evaluate_active_ingredient_id='"+lowestsolubilityID+ "' && tenant_id='"+tenant_id+"'");
								while(Lowestsolubilityactive.next())
								{
									lowestsolubilityminAmount = Lowestsolubilityactive.getFloat(1);
									lowestsolubilityminBodySF = Lowestsolubilityactive.getFloat(2);
									if(grouping_criteria_option==2)
									{
										lowestsolubilityDose = lowestsolubilityminAmount + lowestsolubilityminBodySF;	
									}else
									{
										lowestsolubilityDose = lowestsolubilityminAmount;
									}
									
									System.out.println("lowestsolubilityDose"+lowestsolubilityDose);
								}
						    }
							healthL0 = ((minade/lowestADEDose) *lowestsolubilityDose);
						}
					}
					float L1=0;
					// get final L0 value
					if(doseL0==0)
					{
						L1 = (healthL0*1000)/(max_amount_appled * max_ap_freq * max_Body_Sf);
					}
					if(healthL0==0)
					{
						L1 = Safety_Factor * Active_Concen;
					}
					if(healthL0!=0 && doseL0!=0)
					{
						if(doseL0<healthL0)
						{
							L1 = Safety_Factor * Active_Concen;
						}
						else if(doseL0>healthL0)
						{
							L1 = (healthL0*1000)/(max_amount_appled * max_ap_freq * max_Body_Sf);
						}
						
					}
					System.out.println("Print dose L0: "+doseL0);
					System.out.println("Print health L0: "+healthL0);
					System.out.println("Print  L1: "+L1);
					connection.close();
		return L1; // return that L0 in this method
	}
			
	
	
	
	public static double L0forCleaningAgent(String CurrenProductName) throws SQLException, ClassNotFoundException, IOException {
		float L0 = 0, doseL0=0,LD50= 0, ConversionFactor = 0, BodyWeight = 0, HealthL0 = 0;
		int Basislimitoption=0;
		//database connection
		Connection connection = Utils.db_connect();
		Statement stmt = (Statement) connection.createStatement();
		// get current product name id FROM evaluate_product table // for finding dose based and health flag
		ResultSet getprodname_id = stmt.executeQuery("SELECT id FROM evaluate_product where name ='"+CurrenProductName+"' && tenant_id='"+tenant_id+"'");// Execute the SQL Query to find prod id FROM evaluate_product table
		int prodname_id = 0;
		while (getprodname_id.next()) 
			{
			prodname_id = getprodname_id.getInt(1);
			}
			System.out.println("name id: " + prodname_id);
			ResultSet prod_basis_relation_id = stmt.executeQuery("SELECT * FROM evaluate_product_basis_of_calculation_relation where evaluate_product_id='" + prodname_id + "' && tenant_id='"+tenant_id+"'");
				//get active multiple active id
				List<Integer> BasisID = new ArrayList<>(); // get active list from above query
				while (prod_basis_relation_id.next()) 
				{
					BasisID.add(prod_basis_relation_id.getInt(2));
				}
			  	//System.out.println("First Active:" +activelist.get(0));// get 1st id
				System.out.println("BasisID"+BasisID);
				for(Integer basis:BasisID) {
				ResultSet basisOfcalc = stmt.executeQuery("SELECT ld50_value,conversion_factor,body_weight,cleaning_agent_health_based_value FROM evaluate_product_basis_of_calculation where id ='"+basis+"' && tenant_id='"+tenant_id+"'");
				while (basisOfcalc.next()) 
					{
					LD50 = basisOfcalc.getFloat(1);
					ConversionFactor = basisOfcalc.getFloat(2);
					BodyWeight = basisOfcalc.getFloat(3);
					HealthL0 = basisOfcalc.getFloat(4);
					}
				}
				    ResultSet residuelimit = stmt.executeQuery("SELECT * FROM residue_limit where tenant_id='"+tenant_id+"'");
				    while (residuelimit.next()) 
					{
				    Basislimitoption = residuelimit.getInt(2);
					}
				    // When dose and health flag is true in basis of calculation table
					if (Basislimitoption== 3) 
					{
										doseL0 = LD50 * ConversionFactor * BodyWeight;
										if(doseL0==0)
										{
											L0 = HealthL0;
										}
										if(HealthL0==0)
										{
											L0 = doseL0;
										}
										if(doseL0!=0 && HealthL0!=0)
										{
											if(doseL0>HealthL0)
											{
												L0 = HealthL0;
											}else
											{
												L0 = doseL0;
											}
										}
										
									return L0; // getting lowest L0 b/w 2
					}
					
					if (Basislimitoption == 1) 
					{
						System.out.println("Dose enabled and health disabled");
						L0 = LD50 * ConversionFactor * BodyWeight;
						System.out.println("Print Dose based L0" +L0);
						return L0;
					}
					
					if (Basislimitoption== 2) 
					{
						L0 = HealthL0;
						return L0;
					}
					connection.close();
		return L0; // return that L0 in this method
	} //closing calculate_P1_active1_L0
	
	
	
	
	// Write output and close workbook
	/*public static void writeTooutputFile(Workbook workbook) throws IOException {
		try {
			FileOutputStream outFile = new FileOutputStream(new File(Constant.EXCEL_PATH_Result));
			workbook.write(outFile);
			outFile.close();
		} catch (Exception e) {
			throw e;
		}
	}*/
}
