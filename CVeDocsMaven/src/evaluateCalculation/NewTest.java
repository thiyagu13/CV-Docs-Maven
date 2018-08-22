package evaluateCalculation;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Utils;
import com.mysql.jdbc.Connection;

public class NewTest {
	
	static String tenant_id=Constant.tenant_id;
	
	
  @Test
  public void f() throws ClassNotFoundException, SQLException, IOException {
	  
	  L0forPatch(169,"Ep1");
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
					/*//get active id for getting health value
					ResultSet getactiveID = stmt.executeQuery("SELECT * FROM evaluate_product_active_ingredient_relation where evaluate_product_id='" + prodname_id + "'");
					List<Integer> active = new ArrayList<>(); // store multiple equipment id
				    while (getactiveID.next()) 
				    {
				    	active.add(getactiveID.getInt(2)); // get health based value
				    }*/
					
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
									System.out.println("Both enabled");
									doseL0 = (float) (Safety_Factor * Active_Concen * (percentageAbsorbtion/100) * minDailyDoseperPatch * minNoOFPatchesWornatTime * 0.001);
									System.out.println("wihtou Min Daily Dose: "+Safety_Factor);
									System.out.println("Active_Concen: "+Active_Concen);
									System.out.println("percentageAbsorbtion: "+percentageAbsorbtion);
									System.out.println("minDailyDoseperPatch: "+minDailyDoseperPatch);
									System.out.println("minNoOFPatchesWornatTime: "+minNoOFPatchesWornatTime);
									System.out.println("wihtou Min Daily Dose: "+doseL0);
									if(doseL0==0)
									{
										System.out.println("min_daily_dose: "+min_daily_dose);
										doseL0 = (float) (min_daily_dose * 0.001);
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
							L0 = (float) (min_daily_dose * 0.001);
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
	} //closing topical L0
}
