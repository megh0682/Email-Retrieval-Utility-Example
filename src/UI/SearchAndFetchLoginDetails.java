package UI;
import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;

import com.thoughtworks.selenium.webdriven.commands.*;
import com.thoughtworks.selenium.webdriven.*;
import com.thoughtworks.selenium.SeleneseTestBase;
import com.thoughtworks.selenium.Wait;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
import com.thoughtworks.selenium.Selenium;

import java.util.List;

@SuppressWarnings({ "unused", "deprecation" })
public class SearchAndFetchLoginDetails {
	
	WebDriver driver = new FirefoxDriver();
	//WebDriverBackedSelenium selenium; used when we need the old scripts written in RC to be compatible with webdriver
	String baseURL = "https://accounts.google.com/ServiceLogin?#identifier";
	

	@Before		
	public void setUp() throws Exception {
		driver.get(baseURL);
		//driver.manage().deleteAllCookies();
		driver.findElement(By.id("Email")).sendKeys("shah.iyer@gmail.com");
		driver.findElement(By.id("next")).click();	
		WebElement myDynamicElement = (new WebDriverWait(driver, 10))
		  .until(ExpectedConditions.presenceOfElementLocated(By.id("Passwd")));
		WebElement myPassword = driver.findElement(By.id("Passwd"));
		myPassword.sendKeys("VedAni1982*");
		driver.findElement(By.id("signIn")).click();
	    		
	}
	
	@Test
	public void testSearchAndFetchLoginDetails() throws Exception {
		
		driver.navigate().to("https://mail.google.com/mail/u/0/#inbox");
		try{
		(new WebDriverWait(driver, 10))
			  .until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='gbqfq']")));	
		driver.findElement(By.xpath(".//*[@id='gbqfq']")).sendKeys("subject:(*ITELearn*) login");
		driver.findElement(By.xpath(".//*[@id='gbqfb']")).click();
		(new WebDriverWait(driver, 60))
		  .until(ExpectedConditions.presenceOfElementLocated(By.className("F cf zt")));	
		List<WebElement> tr_collection=driver.findElements(By.className("F cf zt"));
		System.out.println("NUMBER OF Emails="+tr_collection.size());
		if(!tr_collection.isEmpty() && tr_collection.size() != 1 )
		for(WebElement trElement : tr_collection)
		{
			List<WebElement> td_collection=trElement.findElements(By.xpath("td"));
			 if(!td_collection.isEmpty() && td_collection.size() != 1 ){  
	                for(WebElement tdElement : td_collection)
	                {
	                        if((tdElement.getText()).toUpperCase()== "USERNAME")
	                        {
	                        	System.out.println(tdElement.getText());
	                        	System.out.println(trElement.getText());
	                        }
	                }
	                      
			 }                   	                
		}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	@After
	public void tearDown() throws Exception {
	driver.close();
	}
	
	
	

}
