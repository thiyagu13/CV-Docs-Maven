package com.eDocs.result;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import com.eDocs.Utils.Constant;
import com.eDocs.Utils.Utils;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

@SuppressWarnings("unused")
public class LocalScreenshot extends TestListenerAdapter {

    String Seperator=System.getProperty("file.separator");

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = null;
        Reporter.setCurrentTestResult(result);
        String sTestClassName=result.getTestClass().getRealClass().getSimpleName();
        System.out.println("Test class name ..."+sTestClassName);
        String sFileName=result.getName();
        System.out.println("Test method name ..."+sFileName);
        
        driver= Utils.getDriverDetails(result.getTestClass().getName());
        String imagePathInHost="file://"+System.getProperty("user.dir")+Seperator+"screenshots"+Seperator+"LatestResults"+Seperator+sTestClassName+Seperator+captureScreenshot(driver,sTestClassName,sFileName);
        System.out.println("Image path...."+imagePathInHost);
        Reporter.log("<a href='" + imagePathInHost + "'> <img width='100' height='100' src='" + imagePathInHost + "'> </a>");
        Reporter.setCurrentTestResult(null);

    }

    public String captureScreenshot(WebDriver driver,String sTestClassName,String sFileName)
    {
        sFileName=sFileName+".png";
        try
        {
            File file = new File("screenshots" + Seperator + "LatestResults");
            if (!file.exists())
            {
                System.out.println("File created somewhere" + file);
                file.mkdir();
            }

            File sourceFile = ((TakesScreenshot) Constant.driver).getScreenshotAs(OutputType.FILE);
            File targetFile = new File("screenshots" + Seperator + "LatestResults" + Seperator + sTestClassName, sFileName);
            FileUtils.copyFile(sourceFile, targetFile);
            return sFileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}