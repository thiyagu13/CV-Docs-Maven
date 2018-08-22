package com.eDocs.Utils;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Browser {
	
	public static RemoteWebDriver getDriver(String browser) throws MalformedURLException 
	{
		return new RemoteWebDriver(new URL("http://192.168.1.45:4444/wd/hub"), getBrowserCapabilities(browser));
	}
	
	
	private static DesiredCapabilities getBrowserCapabilities(String browserType) throws MalformedURLException {
		switch (browserType) {
		case "firefox":
			System.out.println("Opening firefox driver");
			return DesiredCapabilities.firefox();
		case "chrome":
			System.out.println("Opening chrome driver");
			System.setProperty("webdriver.chrome.driver","C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\chromedriver.exe");
			return DesiredCapabilities.chrome();
		case "IE":
			System.out.println("Opening IE driver");
			return DesiredCapabilities.internetExplorer();
		default:
			System.out.println("browser : " + browserType + " is invalid, Launching Firefox as browser of choice..");
			return DesiredCapabilities.firefox();
		}
	}
}