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

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Utils;
import com.mysql.jdbc.Connection;

public class SurfaceAreaValue {
	
	
	static String tenant_id= Constant.tenant_id;
/*
    public static String productName1 = "P11";
    public static String productName2 = "P22";
    public static String productName3 = "P33";
    public static String productName4 = "P44";*/
   /* @Test
    public void test() throws ClassNotFoundException, SQLException, IOException {
    
    	String currentproductname ="Topical2";
    	String nextproductname = "Topical1";
    		 actualSharedbetween2(currentproductname,nextproductname);
    }*/
	
			
    //Current to next   - Actual shared SF between two products
    public static float actualSharedbetween2(String currentproductname, String nextproductname) throws SQLException, ClassNotFoundException {
        int currentproductID = 0, nextproductID = 0, currentproductsetcount = 0, nextproductsetcount = 0;
        //database connection
        Connection connection = Utils.db_connect();
        Statement stmt = connection.createStatement();
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
        //find total surface area between two products
        List<Float> TotalactualshreadList = new ArrayList<>();
        for (int i = 1; i <= currentproductsetcount; i++) {
            List<Integer> currentequipmentID = new ArrayList<>();
            //check if only equipmnet used in the product
            ResultSet getequipfromset = stmt.executeQuery("SELECT equipment_id FROM product_equipment_set_equipments where product_id='" + currentproductID + "' && set_id ='" + i + "' && tenant_id='"+tenant_id+"'"); // get product name id
            while (getequipfromset.next()) {
                System.out.println("ony equipment selected");
                currentequipmentID.add(getequipfromset.getInt(1));
            }
            //check if only equipment group used in the product -current product
            // if equipment  group means - use the below query
            List<Integer> eqgroupIDs = new ArrayList<>();
            // List<Integer> equipmentgroup = new ArrayList<>();
            ResultSet getequipgrpfromset = stmt.executeQuery("SELECT group_id FROM product_equipment_set_groups where product_id=" + currentproductID + " && set_id =" + i + " && tenant_id='"+tenant_id+"'"); // get product name id
            while (getequipgrpfromset.next()) {
                System.out.println("ony equipment group selected");
                eqgroupIDs.add(getequipgrpfromset.getInt(1));
            }
            for (int id : eqgroupIDs) // iterate group id one by one (from train)
            {
                int equipmentusedcount = 0;
                ResultSet geteqcountfromgrpID = stmt.executeQuery("SELECT equipment_used_count FROM product_equipment_set_groups where product_id=" + currentproductID + " && group_id=" + id + " && tenant_id='"+tenant_id+"'"); // get product name id
                while (geteqcountfromgrpID.next()) {
                    equipmentusedcount = geteqcountfromgrpID.getInt(1);
                }
                ResultSet geteqfromgrpID = stmt.executeQuery("SELECT equipment_id FROM equipment_group_relation where group_id=" + id + " && tenant_id='"+tenant_id+"' order by sorted_id limit " + equipmentusedcount + ""); // get product name id
                while (geteqfromgrpID.next()) {
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
            // if train used only equip means used the below query
            ResultSet eqfromtrain = stmt.executeQuery("SELECT equipment_id FROM equipment_train_equipments where train_id=" + gettrainID + " && tenant_id='"+tenant_id+"'"); // get product name id
            while (eqfromtrain.next()) {
                currentequipmentID.add(eqfromtrain.getInt(1));
            }
            // equipment reused in the train
            List<Integer>  EqNoOfReusedIDS = new ArrayList<>();  		          
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
	                	}
	                	System.out.println("equipment_used_count"+equipment_used_count);
	                	for(int j=1;j<=equipment_used_count;j++)
	 	                {
	                			EqNoOfReusedIDS.add(equipreusedID);
	 	                }
	                } //<------------------ending if equipment reused in equipment train
	            }
	            currentequipmentID.addAll(EqNoOfReusedIDS);
            //equipment reused in the train - endloop
            
            
            
            
            // if train used group means - use the below query
            Set<Integer> groupIDs = new HashSet<>();
            ResultSet eqfromtraingroup = stmt.executeQuery("SELECT * FROM equipment_train_group where train_id=" + gettrainID + " && tenant_id='"+tenant_id+"'"); // get product name id
            while (eqfromtraingroup.next()) {
                groupIDs.add(eqfromtraingroup.getInt(2));
            }
            for (int id : groupIDs) // iterate group id one by one (from train)
            {
                //Set<Integer> equipID = new HashSet();
                int equipmentusedcount = 0;
                ResultSet geteqcountfromgrpID = stmt.executeQuery("SELECT * FROM equipment_train_group where train_id="+ gettrainID+" && group_id=" + id + " && tenant_id='"+tenant_id+"'"); // get product name id
                while (geteqcountfromgrpID.next()) {
                    equipmentusedcount = geteqcountfromgrpID.getInt(3);
                }
                ResultSet geteqfromgrpID = stmt.executeQuery("SELECT * FROM equipment_group_relation where group_id=" + id + " && tenant_id='"+tenant_id+"' order by sorted_id limit " + equipmentusedcount + ""); // get product name id
                while (geteqfromgrpID.next()) {
                    currentequipmentID.add(geteqfromgrpID.getInt(2));
                }
            }
            //end: check if only equipment train used in the product -current product
            List<Float> actualshread = new ArrayList<>();
     //Next product equipment set and total surface area
            for (int j = 1; j <= nextproductsetcount; j++) {
                List<Integer> nextequipmentIDs = new ArrayList<>();
                //check if only equipment used in the product -Next product
                ResultSet getequipIDprodset = stmt.executeQuery("SELECT * FROM product_equipment_set_equipments where product_id='" + nextproductID + "' && set_id ='" + j + "' && tenant_id='"+tenant_id+"'"); // get product name id
                while (getequipIDprodset.next()) {
                    nextequipmentIDs.add(getequipIDprodset.getInt(4));
                }
     //check if only equipment group used in the product -Next product
                // if equipment  group means - use the below query
                List<Integer> eqgroupIDsofNext = new ArrayList<>();
                // List<Integer> equipmentgroupofNext = new ArrayList<>();
                ResultSet getequipgrpfromsetNextprod = stmt.executeQuery("SELECT * FROM product_equipment_set_groups where product_id=" + nextproductID + " && set_id =" + j + " && tenant_id='"+tenant_id+"'"); // get product name id
                while (getequipgrpfromsetNextprod.next()) {
                    System.out.println("ony equipment group selected");
                    eqgroupIDsofNext.add(getequipgrpfromsetNextprod.getInt(4));
                }
                for (int id : eqgroupIDsofNext) // iterate group id one by one (from train)
                {
                    int equipmentusedcount = 0;
                    ResultSet geteqcountfromgrpID = stmt.executeQuery("SELECT * FROM product_equipment_set_groups where product_id=" + nextproductID + " && group_id=" + id + " && tenant_id='"+tenant_id+"'"); // get product name id
                    while (geteqcountfromgrpID.next()) {
                        equipmentusedcount = geteqcountfromgrpID.getInt(5);
                    }
                    ResultSet geteqfromgrpID = stmt.executeQuery("SELECT * FROM equipment_group_relation where group_id=" + id + " && tenant_id='"+tenant_id+"' order by sorted_id limit "+ equipmentusedcount +""); // get product name id
                    while (geteqfromgrpID.next()) {
                        nextequipmentIDs.add(geteqfromgrpID.getInt(2));
                    }
                    // nextequipmentIDs.addAll(equipmentgroupofNext);
                }
                //End: check if only equipment group used in the product -Next product
//check if only equipment train used in the product -Next product
                int nextprodtrainID = 0;
                ResultSet nextgetequiptrainIDfromset = stmt.executeQuery("SELECT * FROM product_equipment_set_train where product_id=" + nextproductID + " && set_id =" + j + " && tenant_id='"+tenant_id+"'"); // get product name id
                while (nextgetequiptrainIDfromset.next()) {
                    System.out.println("ony equipment train selected");
                    nextprodtrainID = nextgetequiptrainIDfromset.getInt(4);
                }
// if train used only equip means used the below query
                ResultSet eqfromtrainnextprod = stmt.executeQuery("SELECT * FROM equipment_train_equipments where train_id=" + nextprodtrainID + " && tenant_id='"+tenant_id+"'"); // get product name id
                while (eqfromtrainnextprod.next()) {
                    nextequipmentIDs.add(eqfromtrainnextprod.getInt(2));
                }
             // equipment reused in the train
                List<Integer>  EqNoOfReusedIDSNext = new ArrayList<>();  		          
    	            for(Integer eqid:nextequipmentIDs)
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
    	                	for(int k=1;k<=equipment_used_count;k++)
    	 	                {
    	                		EqNoOfReusedIDSNext.add(equipreusedID);
    	 	                }
    	                } //<------------------ending if equipment reused in equipment train
    	            }
    	            nextequipmentIDs.addAll(EqNoOfReusedIDSNext);
                //equipment reused in the train - endloop
    	            
 // if train used group means - use the below query
                Set<Integer> nextprodgroupIDs = new HashSet<>();
                ResultSet eqfromtraingroupNextProd = stmt.executeQuery("SELECT group_id FROM equipment_train_group where train_id=" + nextprodtrainID + " && tenant_id='"+tenant_id+"'"); // get product name id
                while (eqfromtraingroupNextProd.next()) {
                    nextprodgroupIDs.add(eqfromtraingroupNextProd.getInt(1));
                }
                for (int ids : nextprodgroupIDs) // iterate group id one by one (from train)
                {
                    //Set<Integer> equipID = new HashSet();
                    int equipmentusedcount = 0;
                    ResultSet geteqcountfromgrpID = stmt.executeQuery("SELECT equipment_used_count FROM equipment_train_group where train_id="+nextprodtrainID+" && group_id=" + ids + " && tenant_id='"+tenant_id+"'"); // get product name id
                    while (geteqcountfromgrpID.next()) {
                        equipmentusedcount = geteqcountfromgrpID.getInt(1);
                    }
                    ResultSet geteqfromgrpID = stmt.executeQuery("SELECT * FROM equipment_group_relation where group_id=" + ids + " && tenant_id='"+tenant_id+"' order by sorted_id limit " + equipmentusedcount + ""); // get product name id
                    while (geteqfromgrpID.next()) {
                        nextequipmentIDs.add(geteqfromgrpID.getInt(2));
                    }
                }
                //end: check if only equipment train used in the product -current product
                System.out.println("Current equipmentID" + currentequipmentID);
                System.out.println("Next equipmentID" + nextequipmentIDs);
               // nextequipmentIDs.retainAll(currentequipmentID); // get common id between current and next
               // System.out.println("Common--->: " + nextequipmentIDs);
                
               System.out.println("Final Result:"+CollectionUtils.intersection(currentequipmentID, nextequipmentIDs));
                
                float equipTotalSharedSF = 0;
                for (int sharedID : CollectionUtils.intersection(currentequipmentID, nextequipmentIDs)) //calculate shared total SF
                {
                	System.out.println("sharedID "+sharedID);
                    ResultSet eqSF = stmt.executeQuery("SELECT surface_area FROM equipment where id='" + sharedID + "' && tenant_id='"+tenant_id+"'"); // get product name id
                    while (eqSF.next()) {
                        equipTotalSharedSF = equipTotalSharedSF + eqSF.getFloat(1);
                    }
                }
                actualshread.add(equipTotalSharedSF);
            }
            TotalactualshreadList.addAll(actualshread);
        }
        System.out.println("TotalactualshreadList: "+TotalactualshreadList);
        float actualsharedbetween2 = Collections.max(TotalactualshreadList);
        System.out.println("Maximum shared SF value:" + actualsharedbetween2);
        connection.close();
        return actualsharedbetween2;
    }

    //Current to next  -lowest train SF between two products
    public static float lowestTrainbetween2(String currentproductname, String nextproductname) throws SQLException, ClassNotFoundException {
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

    
    public static float sameProductSF(String currentproductname) throws SQLException, ClassNotFoundException {
        int currentproductID = 0, currentproductsetcount = 0;
        //database connection
        Connection connection = Utils.db_connect();
        Statement stmt = connection.createStatement();
        
        System.out.println("currentproductname-->" +currentproductname);
        
//current product equipment set
        List<Integer> Currentsetcount = new ArrayList<>();
        ResultSet currentprod = stmt.executeQuery("SELECT id,set_count FROM product where name='" + currentproductname + "' && tenant_id='"+tenant_id+"'"); // get product name id
        while (currentprod.next()) {
            currentproductID = currentprod.getInt(1);
            Currentsetcount.add(currentprod.getInt(2));
            currentproductsetcount = currentprod.getInt(2);
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
        float lowestTrainbetween2 = Collections.max(currnetProdeqSettotalSF);
        System.out.println("Largest value is : " + lowestTrainbetween2);
        connection.close();
        return lowestTrainbetween2;
    }
    
    
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
