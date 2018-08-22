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

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.experimental.max.MaxCore;
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Utils;
import com.mysql.jdbc.Connection;

public class EvaluateEquipmentPrefTransfer {
	
	static String tenant_id= Constant.tenant_id;
			
	/*@Test
	public void test() throws ClassNotFoundException, SQLException, IOException, InterruptedException 
	{
		String CurrenProductName="S1";
		Integer equipmentID= 39231;
		float LowestoneExpectedL3= 10;
		float getLowestL1value= 15;
		
		
		EqPrefrentialTransfer(CurrenProductName,equipmentID,LowestoneExpectedL3,getLowestL1value);
	}*/
	/*
		public static double EqPrefrentialTransfer(String currentproductName, Integer EquipID,float LowestExpectedL3, float LowestExpectedL1) throws SQLException, ClassNotFoundException, IOException {
			//database connection
			Connection connection = Utils.db_connect();
			Statement stmt = (Statement) connection.createStatement();
			float AmountofDiscardedProduct, NoofDiscardedPackagingUnits, PreferentialTransfer = 0,preferential_transfer_surface_area=0;
			
			 Integer preferentialtransferoption=0,primarypackaging=0;
			 ResultSet PFTransfer = stmt.executeQuery("Select preferential_transfer_surface_area,preferential_transfer_option,primary_packaging from equipment where id= '" + EquipID + "' && tenant_id='"+tenant_id+"'"); // get product name id
	    	 while(PFTransfer.next()) {  preferential_transfer_surface_area = PFTransfer.getFloat(1); preferentialtransferoption = PFTransfer.getInt(2); primarypackaging = PFTransfer.getInt(3);
	    	 }
			
	    	 Integer CProdID,producttype = 0;
	    	 float productdose = 0,totalamountpercontainer = 0;
	    	 ResultSet product = stmt.executeQuery("Select id,product_type,product_dose,total_amount_per_container FROM evaluate_product where name='"+currentproductName+"' && tenant_id='"+tenant_id+"'");
	    	 while(product.next()) {  CProdID = product.getInt(1); producttype = product.getInt(2); productdose = product.getFloat(3); totalamountpercontainer = product.getFloat(4);
	    	 }
	    	 
	    	if(preferentialtransferoption==1 && primarypackaging==2) // preferentialtransferoption Yes and primarypackaging yes
	    	{
	    		//Amount of discarded product = L3 x SA/L1
	    		//AmountofDiscardedProduct
	    		PreferentialTransfer = (LowestExpectedL3 * preferential_transfer_surface_area )/ LowestExpectedL1;
	    	}
	    
	    	if(preferentialtransferoption==1 && primarypackaging==1) // preferentialtransferoption Yes and primarypackaging yes
	    	{
	    	 
	    		// No of discarded packaging units = (L3 x SA)  /   (L1 x AP)
	    		//NoofDiscardedPackagingUnits
	    		if(producttype==5) // if solid
	    		{
	    			PreferentialTransfer = (LowestExpectedL3 * preferential_transfer_surface_area )/ (LowestExpectedL1*productdose);	 
	    		}
	    		if(producttype==7 || producttype==1) // Patch & Cleaning Agent
	    		{
	    		 
	    		}
	    		if(producttype==4 ||producttype==3 ||producttype==2 ||producttype==6)
	    		{
	    			PreferentialTransfer = (LowestExpectedL3 * preferential_transfer_surface_area )/ (LowestExpectedL1*totalamountpercontainer) ;
	    		}
	    	}
	    	if(preferentialtransferoption==2) // preferentialtransferoption Yes and primarypackaging yes
	    	{
	    		//PreferentialTransfer = Float.valueOf("NA");
	    		PreferentialTransfer =1;
	    	}
		
			connection.close();	
			System.out.println("PreferentialTransfer: "+PreferentialTransfer);
		return PreferentialTransfer;
		} //closing calculate_P1_active1_L0
			*/
	
		
	
}