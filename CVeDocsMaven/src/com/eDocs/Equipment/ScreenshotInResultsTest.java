package com.eDocs.Equipment;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.eDocs.Utils.Constant;


public class ScreenshotInResultsTest {

     WebDriver driver = Constant.driver;
    public Logger log = Logger.getLogger("eResidue");
    
 
    @Test
    public void openFacebook(){
        driver.get("http://facebook.com");
        System.out.println("Facebook Opened ..");
        log.debug("Opened FB");
        Assert.fail("Test was failed");

    }

    @Test
    public void openGoogle(){
        driver.get("http://google.com");
        System.out.println("Google Opened ..");
        log.debug("Opened FB");
        Assert.fail("Test was failed");

    }

 
         /*@AfterClass
         public void quitDriver(){
         if(gerDriverObjMap().get(getClass().getName())!=null){
            gerDriverObjMap().get(getClass().getName()).quit();
            gerDriverObjMap().remove(getClass().getName());
            System.out.println("driver for class : "+ getClass().getName() + "is closed");
         }
  		 */   
}