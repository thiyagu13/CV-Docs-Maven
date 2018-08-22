package com.eDocs.microbialCalculation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.eDocs.Utils.Utils;
import com.mysql.jdbc.Connection;

public class L0ProductTypes {
  
	/*
	 CA - 1
	Diluent -2
	inhalant -3
	liquid -4
	solid -5
	topical -6
	patch -7
	 	 */
	
	String currentproductname = "CA TEST";
	@Test
	public void exeL0() throws ClassNotFoundException, SQLException 
	{
		L0(currentproductname);
		
	}
	
	
	
		public void L0(String currentproductname) throws ClassNotFoundException, SQLException {
			Connection connection = Utils.db_connect();	
			Statement stmt = (Statement) connection.createStatement();// Create Statement Object
			//get product type for current product
			ResultSet getproducttype = stmt.executeQuery("SELECT id,product_type FROM product where name='"+currentproductname+"'");
			int producttype = 0,productID=0;
			while (getproducttype.next()) 
			{	
				productID  = getproducttype.getInt(1);
				producttype = getproducttype.getInt(2);
			}
			
			ResultSet prod_basis_relation_id = stmt.executeQuery("SELECT * FROM product_basis_of_calculation_relation where product_id='" + productID + "'");
			//get active multiple active id
			List<Integer> activelist = new ArrayList<>(); // get active list from above query
			while (prod_basis_relation_id.next()) 
			{
			activelist.add(prod_basis_relation_id.getInt(2));
			}
		  	System.out.println("First Active:" +activelist.get(0));// get 1st id
			ResultSet basisOfcalc = stmt.executeQuery("SELECT * FROM product_basis_of_calculation where id ='" + activelist.get(0) + "'");
			while (basisOfcalc.next()) 
				{
				float Safety_Factor = basisOfcalc.getFloat(10);
				float Active_Concen = basisOfcalc.getFloat(6);
				float Dose_of_active = basisOfcalc.getFloat(7);
				float min_no_of_dose = basisOfcalc.getFloat(8);
				}
			
			System.out.println("producttype: "+producttype);
			switch(producttype){
			
				case 1:System.out.println("Cleaning agent");break;
				case 2:System.out.println("Diluent");break;
				case 3:System.out.println("Inhalant");break;
				case 4:System.out.println("Liquid");break;
				case 5:	System.out.println("Solid");
						
						break;
				case 6:System.out.println("Topical");break;
				case 7:System.out.println("Transdermal Patch");break;
				
				
			}
			
			
			connection.close();	
		}
	
	
	
	
	
}
