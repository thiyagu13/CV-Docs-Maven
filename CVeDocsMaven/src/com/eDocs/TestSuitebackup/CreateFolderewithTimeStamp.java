package com.eDocs.TestSuitebackup;


import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CreateFolderewithTimeStamp {

    /*public static void main(String[] args)
    {
    // below- to run TestNG.xml in the main method itself  
      TestNG testng = new TestNG();
   // Create a list of String 
      List<String> suites= new ArrayList<String>();
      suites.add("C:\\Users\\Easy solutions\\git\\eDocs\\eResidue_CV_eDocs_Automation\\testng.xml");//path to xml..
    //  suites.add("c:/tests/testng2.xml");  - Add multiple TestNG xml as you needed
      testng.setTestSuites(suites);
      testng.run(); // above - to run TestNG.xml in the main method itself
      
      CreateFileWithTimeStamp("Test Suite Report ");
    }*/
    
   @Parameters({"Test Report"}) // pass name & value to the parameter in testNG.xml file
   @Test 
   //Create a new file
    public static void CreateFileWithTimeStamp(String filename) {
        //get current project path
    	    	
    	
    	try {
        String filePath = "C:\\selenium\\eResidue_CV_eDocs\\Output"; 
       // project default path
       /* String filpath = System.getProperty("user.dir");*/
        //create a new file with Time Stamp	
        File file = new File(filePath + "\\" + filename	+ GetCurrentTimeStamp().replace(":", "_").replace(".", "_"));
			

			if (!file.exists()) {
				file.mkdir(); // create new folder
				File Source_xlsx_path = new File("C:\\selenium\\eResidue_CV_eDocs\\eResidue_eDocs_TestCase.xlsx");
				// updated testcase.xlsx copy to newly created folder
				FileUtils.copyFileToDirectory(Source_xlsx_path, new File(file.getAbsolutePath()));
				
				// Updated screenshot copy to newly created folder
				File Screenshot_path = new File("C:\\selenium\\eResidue_CV_eDocs\\screenshots All TC");
				FileUtils.copyDirectoryToDirectory(Screenshot_path, new File(file.getAbsolutePath()));
				
				// Updated html file copy to newly created folder
				File Html_path = new File("C:\\selenium\\eResidue_CV_eDocs\\test-output\\index" + ".html");
				FileUtils.copyFileToDirectory(Html_path, new File(file.getAbsolutePath()));
				
				
				
				System.out.println("Dest Path" + file.getAbsolutePath());
				System.out.println("Source path" + Source_xlsx_path);
				System.out.println("File is created; file name is "	+ file.getName());
				
			} else {

				System.out.println("File already exist " + file.getName());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
      

	// Get current system time
    public static String GetCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss.SSS"); // dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
    // Get Current Host Name
    public static String GetCurrentTestHostName() throws UnknownHostException {
        InetAddress localMachine = InetAddress.getLocalHost();
        String hostName = localMachine.getHostName();
        return hostName;
    }

    // Get Current User Name
    public static String GetCurrentTestUserName() {
        return System.getProperty("user.name");
    }

   }
    