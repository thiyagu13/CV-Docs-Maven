package com.eDocs.residueCalculation;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Utils;
import com.mysql.jdbc.Connection;

public class NewTest1 {
	static String tenant_id=Constant.tenant_id;
			
	@Test
	public void test() throws ClassNotFoundException, SQLException, IOException, InterruptedException 
	{
		String currentproductname= "P1";
		String nextproductname= "P3";
		lowestSF(currentproductname,nextproductname);
	}
	
	public static double lowestSF(String currentproductname, String nextproductname) throws IOException, ClassNotFoundException, SQLException  
	{
        int currentproductID = 0, nextproductID = 0, currentproductsetcount = 0, nextproductsetcount = 0;
        //database connection
        Connection connection = Utils.db_connect();
        Statement stmt = connection.createStatement();
        
       System.out.println("currentproductname-->" +currentproductname);
        System.out.println("nextproductname-->" +nextproductname);
        
//current product equipment set
        List<Integer> Currentsetcount = new ArrayList<>();
        ResultSet currentprod = stmt.executeQuery("SELECT id,set_count FROM product where name='" + currentproductname + "' && tenant_id='"+tenant_id+"'"); // get product name id
        while (currentprod.next()) {
            currentproductID = currentprod.getInt(1);
            Currentsetcount.add(currentprod.getInt(2));
            currentproductsetcount = currentprod.getInt(2);
        }
//next product equipment set
        List<Integer> Nextsetcount = new ArrayList<>();
        ResultSet nextprod = stmt.executeQuery("SELECT id,set_count FROM product where name='" + nextproductname + "' && tenant_id='"+tenant_id+"'"); // get product name id
        while (nextprod.next()) {
            nextproductID = nextprod.getInt(1);
            Nextsetcount.add(nextprod.getInt(2));
            nextproductsetcount = nextprod.getInt(2);
        }
//Current product equipment set and total surface area
        List<Float> currnetProdeqSettotalSF = new ArrayList<>();
        List<Integer> equipmentgroup = new ArrayList<>();
        for (int i = 1; i <= currentproductsetcount; i++) {
            List<Integer> equipments = new ArrayList<>();
            //check if only equipment used in the product -current product
            ResultSet getequipfromset = stmt.executeQuery("SELECT * FROM product_equipment_set_equipments where product_id='" + currentproductID + "' && set_id ='" + i + "' && tenant_id='"+tenant_id+"'"); // get product name id
            while (getequipfromset.next()) {
                equipments.add(getequipfromset.getInt(4));
            }
            //End: check if only equipment used in the product -current product
            //check if only equipment group used in the product -current product
            // if equipment  group means - use the below query
            List<Integer> eqgroupIDs = new ArrayList<>();
            ResultSet getequipgrpfromset = stmt.executeQuery("SELECT * FROM product_equipment_set_groups where product_id=" + currentproductID + " && set_id =" + i + " && tenant_id='"+tenant_id+"'"); // get product name id
            while (getequipgrpfromset.next()) {
                System.out.println("ony equipment group selected");
                eqgroupIDs.add(getequipgrpfromset.getInt(4));
            }
            for (int id : eqgroupIDs) // iterate group id one by one (from train)
            {
                int equipmentusedcount = 0;
                ResultSet geteqcountfromgrpID = stmt.executeQuery("SELECT * FROM product_equipment_set_groups where product_id=" + currentproductID + " && group_id=" + id + " && tenant_id='"+tenant_id+"'"); // get product name id
                while (geteqcountfromgrpID.next()) {
                    equipmentusedcount = geteqcountfromgrpID.getInt(5);
                }
                ResultSet geteqfromgrpID = stmt.executeQuery("SELECT * FROM equipment_group_relation where group_id=" + id + " && tenant_id='"+tenant_id+"' order by sorted_id limit " + equipmentusedcount + ""); // get product name id
                while (geteqfromgrpID.next()) {
                    equipmentgroup.add(geteqfromgrpID.getInt(2));
                }
                equipments.addAll(equipmentgroup);
            }
            //end: check if only equipment group used in the product -current product
//check if only equipment train used in the product -current product
            int gettrainID = 0;
            ResultSet getequiptrainIDfromset = stmt.executeQuery("SELECT * FROM product_equipment_set_train where product_id=" + currentproductID + " && set_id =" + i + " && tenant_id='"+tenant_id+"'"); // get product name id
            while (getequiptrainIDfromset.next()) {
                System.out.println("ony equipment train selected");
                gettrainID = getequiptrainIDfromset.getInt(4);
            }
//if train used only equipmeans used the below query
            ResultSet eqfromtrain = stmt.executeQuery("SELECT * FROM equipment_train_equipments where train_id=" + gettrainID + " && tenant_id='"+tenant_id+"'"); // get product name id
            while (eqfromtrain.next()) {
                equipments.add(eqfromtrain.getInt(2));
            }
            //
            // equipment reused in the train
            List<Integer>  EqNoOfReusedIDS = new ArrayList<>();  		          
	            for(Integer eqid:equipments)
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
	                	}
	                	System.out.println("equipment_used_count"+equipment_used_count);
	                	for(int j=1;j<=equipment_used_count;j++)
	 	                {
	                			EqNoOfReusedIDS.add(equipreusedID);
	 	                }
	                } //<------------------ending if equipment reused in equipment train
	            }
	            equipments.addAll(EqNoOfReusedIDS);
            //equipment reused in the train - endloop
            //
            
//if train used group means - use the below query
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
                ResultSet geteqfromgrpID = stmt.executeQuery("SELECT * FROM equipment_group_relation where group_id=" + id + " && tenant_id='"+tenant_id+"' order by sorted_id limit " + equipmentusedcount + ""); // get product name id
                while (geteqfromgrpID.next()) {
                    equipments.add(geteqfromgrpID.getInt(2));
                }
            }
            //end: check if only equipment train used in the product -current product
            System.out.println("Current equipments--->"+equipments);
            
            float equiptotalSF = 0;
            for (int geteqID : equipments) //get equipment surface area
            {
                ResultSet eqSF = stmt.executeQuery("SELECT surface_area FROM equipment where id='" + geteqID + "' && tenant_id='"+tenant_id+"'"); // get product name id
                while (eqSF.next()) {
                    equiptotalSF = equiptotalSF + eqSF.getFloat(1);
                }
            }
            currnetProdeqSettotalSF.add(equiptotalSF);
        }
        
        System.out.println("Current prouct Total SF:"+currnetProdeqSettotalSF);

        
        
        List<Float> nextProdeqSettotalSF = new ArrayList<>();
        List<Integer> equipmentgroupNextProd = new ArrayList<>();
        //Next product equipment set and total surface area
        for (int i = 1; i <= nextproductsetcount; i++) {
            List<Integer> equipments = new ArrayList<>();
            System.out.println("Next Prod Total: "+nextproductID);
            System.out.println("i: "+i);
            ResultSet getequipfromset = stmt.executeQuery("SELECT * FROM product_equipment_set_equipments where product_id='" + nextproductID + "' && set_id ='" + i + "' && tenant_id='"+tenant_id+"'"); // get product name id
            while (getequipfromset.next()) {
                equipments.add(getequipfromset.getInt(4));
            }
            //check if only equipment group used in the product -Next product
            // if train used group means - use the below query
            List<Integer> eqgroupIDs = new ArrayList<>();
            ResultSet getequipgrpfromset = stmt.executeQuery("SELECT * FROM product_equipment_set_groups where product_id=" + nextproductID + " && set_id =" + i + " && tenant_id='"+tenant_id+"'"); // get product name id
            while (getequipgrpfromset.next()) {
                System.out.println("ony equipment group selected");
                eqgroupIDs.add(getequipgrpfromset.getInt(4));
            }
            for (int id : eqgroupIDs) // iterate group id one by one (from train)
            {
                int equipmentusedcount = 0;
                ResultSet geteqcountfromgrpID = stmt.executeQuery("SELECT * FROM product_equipment_set_groups where product_id=" + nextproductID + " && group_id=" + id + " && tenant_id='"+tenant_id+"'"); // get product name id
                while (geteqcountfromgrpID.next()) {
                    equipmentusedcount = geteqcountfromgrpID.getInt(5);
                }
                ResultSet geteqfromgrpID = stmt.executeQuery("SELECT * FROM equipment_group_relation where group_id=" + id + " && tenant_id='"+tenant_id+"' order by sorted_id limit " + equipmentusedcount + ""); // get product name id
                while (geteqfromgrpID.next()) {
                    equipmentgroupNextProd.add(geteqfromgrpID.getInt(2));
                }
                equipments.addAll(equipmentgroupNextProd);
            }
            //End: check if only equipment group used in the product -Next product
            //check if only equipment train used in the product -current product
            int nextprodtrainID = 0;
            ResultSet nextgetequiptrainIDfromset = stmt.executeQuery("SELECT * FROM product_equipment_set_train where product_id=" + nextproductID + " && set_id =" + i + " && tenant_id='"+tenant_id+"'"); // get product name id
            while (nextgetequiptrainIDfromset.next()) {
                System.out.println("ony equipment train selected");
                nextprodtrainID = nextgetequiptrainIDfromset.getInt(4);
            }
            // if train used only equip means used the below query
            ResultSet eqfromtrainnextprod = stmt.executeQuery("SELECT * FROM equipment_train_equipments where train_id=" + nextprodtrainID + " && tenant_id='"+tenant_id+"'"); // get product name id
            while (eqfromtrainnextprod.next()) {
                equipments.add(eqfromtrainnextprod.getInt(2));
            }
            
            //
            // equipment reused in the train
            List<Integer>  EqNoOfReusedIDS = new ArrayList<>();  		          
	            for(Integer eqid:equipments)
	            {
	            	//------------->if equipment reused in equipment train
	                Integer equipreusedID=0,equipment_used_count=0;   
	                ResultSet equipreused = stmt.executeQuery("SELECT equipment_id,equipment_used_count FROM train_equipment_count where train_id=" + nextprodtrainID + " && equipment_id="+eqid+" && tenant_id='"+tenant_id+"'"); // get product name id
	                if(equipreused!=null)
	                {
	                	while (equipreused.next()) 
	                	{
	                		equipreusedID = equipreused.getInt(1);
	                		equipment_used_count = equipreused.getInt(2);
	                	}
	                	System.out.println("equipment_used_count"+equipment_used_count);
	                	for(int j=1;j<=equipment_used_count;j++)
	 	                {
	                			EqNoOfReusedIDS.add(equipreusedID);
	 	                }
	                } //<------------------ending if equipment reused in equipment train
	            }
	            equipments.addAll(EqNoOfReusedIDS);
            //equipment reused in the train - endloop
            
            //
            
            
            
            // if train used group means - use the below query
            Set<Integer> nextprodgroupIDs = new HashSet<>();
            ResultSet eqfromtraingroupNextProd = stmt.executeQuery("SELECT * FROM equipment_train_group where train_id=" + nextprodtrainID + " && tenant_id='"+tenant_id+"'"); // get product name id
            while (eqfromtraingroupNextProd.next()) {
                nextprodgroupIDs.add(eqfromtraingroupNextProd.getInt(2));
            }
            for (int ids : nextprodgroupIDs) // iterate group id one by one (from train)
            {
                //Set<Integer> equipID = new HashSet();
                int equipmentusedcount = 0;
                ResultSet geteqcountfromgrpID = stmt.executeQuery("SELECT * FROM equipment_train_group where train_id="+nextprodtrainID+" && group_id=" + ids + " && tenant_id='"+tenant_id+"'"); // get product name id
                while (geteqcountfromgrpID.next()) {
                    equipmentusedcount = geteqcountfromgrpID.getInt(3);
                }
                ResultSet geteqfromgrpID = stmt.executeQuery("SELECT * FROM equipment_group_relation where group_id=" + ids + " && tenant_id='"+tenant_id+"' order by sorted_id limit " + equipmentusedcount + ""); // get product name id
                while (geteqfromgrpID.next()) {
                    equipments.add(geteqfromgrpID.getInt(2));
                }
            }
            //end: check if only equipment train used in the product -current product
            
            System.out.println("Equipment id of Next: "+equipments);
            
            float equiptotalSF = 0;
            for (int geteqID : equipments) //get equipment surface area
            {
                ResultSet eqSF = stmt.executeQuery("SELECT surface_area FROM equipment where id='" + geteqID + "' && tenant_id='"+tenant_id+"'"); // get product name id
                while (eqSF.next()) {
                    equiptotalSF = equiptotalSF + eqSF.getFloat(1);
                    
                }
            }
            nextProdeqSettotalSF.add((float) equiptotalSF);
        }
        System.out.println("nextProdeqSettotalSF-- " + nextProdeqSettotalSF);
        ArrayList<Float> Lowestvalue = new ArrayList<>(); //storing comparison output
        for (float currentTest : currnetProdeqSettotalSF) {
            for (float nextTest : nextProdeqSettotalSF) {
                Lowestvalue.add(Float.compare(currentTest, nextTest) < 0 ? currentTest : nextTest);
            }
        }
        System.out.println("All Lowest comparison value:"+Lowestvalue);
        float lowestTrainbetween2 = Collections.max(Lowestvalue);
        System.out.println("Largest value is : " + lowestTrainbetween2);
        connection.close();
        return lowestTrainbetween2;
    } 
	

	 
		
	
}