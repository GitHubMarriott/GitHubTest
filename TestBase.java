 package com.company.composite.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

//import com.company.util.Sleeper;
import com.company.util.WebEventListener;

/*Notes to get folder path:	String absolutePath = new File(".").getAbsolutePath();
	System.out.println(absolutePath);// Shows you the path of your Project Folder
	int last = absolutePath.length()-1;
	absolutePath = absolutePath.substring(0, last);//Remove the dot at the end of path
	System.out.println(absolutePath);
	String filePath =  "MyFolderInsideEclipseProject\\file.txt";//You know this
	System.out.println(absolutePath + filePath);//Get the full path.
*/

public class TestBase {

	public static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;
	public static WebDriver driver;

	private String testEnvironment = "";
	public static Properties prop;

	public static String driverPath = System.getProperty("user.dir");
	// public static String browserName = prop.getProperty("browser");
	
	static long millies = 20;

	public TestBase() {
		try {
			prop = new Properties();

			FileInputStream ip = new FileInputStream(System. getProperty("user.dir") + "//src//main//java//"
					+ "com//company//enviormentInfo//Config.property");
			System.out.println(ip);
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	public static void initialization() {	
		 String browserName = prop.getProperty("browser");
		if (browserName.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					"C:\\Users\\Priyanka\\OneDrive\\chromedriver_win32\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		 else if(browserName.equals("FireFox")){
		 System.setProperty("webdriver.gecko.driver",
				 "C:\\Users\\Priyanka Tiwari\\Desktop\\workspace_selenium\\selenium-framwork\\drivers\\firefox\\geckodriver.exe");
		 driver = new FirefoxDriver(); 
		 
		}
		 else if(browserName.equals("i.e.")){
			 System.setProperty("webdriver.ie.driver",
					 "C:\\Users\\Priyanka Tiwari\\Desktop\\workspace_selenium\\selenium-framwork\\drivers\\internet explorer\\IEDriverServer.exe");
			 driver = new InternetExplorerDriver();; 
			 
		 }
		
		e_driver = new EventFiringWebDriver(driver);
		eventListener = new WebEventListener();
		e_driver.register(eventListener);
		driver = e_driver;
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		/*
		 * Sleeper.PageLoadTimeout(driver,millies,TimeUnit.SECONDS);
		 * Sleeper.sleepImplicitly(driver, millies, TimeUnit.SECONDS);
		 */
   
		

		driver.get(prop.getProperty("Endpoint2"));
	}
	
	  public void quit()
	  { 
		  System.out.println("Quiting the Driver");
		  
	  driver.quit(); 
	  }
	  
	  public static void GridTest() throws MalformedURLException{
		  DesiredCapabilities cap = new DesiredCapabilities();
		  cap.setBrowserName(prop.getProperty("browser"));
	      cap.setPlatform(Platform.WINDOWS);
	      
	      ChromeOptions options = new ChromeOptions();
	      options.merge(cap); 
		  
	      String hubUrl = "http://192.168.0.104:4444/wd/hub";
	      WebDriver driver = new RemoteWebDriver(new URL(hubUrl),options);
	  }
	 
} 
