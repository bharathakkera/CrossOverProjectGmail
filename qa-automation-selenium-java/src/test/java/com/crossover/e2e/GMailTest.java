package com.crossover.e2e;

import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;


public class GMailTest extends TestCase {
    private WebDriver driver;
    private Properties properties = new Properties();

    public void setUp() throws Exception {
        
        properties.load(new FileReader(new File("src/test/resources/test.properties")));
        //Dont Change below line. Set this value in test.properties file incase you need to change it..
        System.setProperty("webdriver.chrome.driver",properties.getProperty("webdriver.chrome.driver") );
        driver = new ChromeDriver();
        driver.manage().window().maximize();

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

	/*
	 * public void tearDown() throws Exception { driver.quit(); }
	 */

    /*
     * Please focus on completing the task
     * 
     */
    @Test
    public void testSendEmail() throws Exception {
        driver.get("https://mail.google.com/");
       
        
        WebElement userElement = driver.findElement(By.id("identifierId"));
        userElement.sendKeys(properties.getProperty("username"));

        driver.findElement(By.id("identifierNext")).click();

        Thread.sleep(1000);

        WebElement passwordElement = driver.findElement(By.name("password"));
        passwordElement.sendKeys(properties.getProperty("password"));
        driver.findElement(By.id("passwordNext")).click();

        Thread.sleep(1000);

        //WebElement composeElement = driver.findElement(By.xpath("//*[@role='button' and (.)='COMPSE']"));
        WebElement composeElement = driver.findElement(By.xpath("//div[@class='T-I T-I-KE L3']"));
        composeElement.click();

        driver.findElement(By.name("to")).clear();
        driver.findElement(By.xpath("//textarea[@name='to']"))
        	.sendKeys(String.format("%s@gmail.com", properties.getProperty("username")));
        Thread.sleep(1000);
     
        // emailSubject and emailbody to be used in this unit test.
        String emailSubject = properties.getProperty("email.subject");
        String emailBody = properties.getProperty("email.body"); 
        
        driver.findElement(By.name("subjectbox")).sendKeys(emailSubject);
        Thread.sleep(1000);
        
        driver.findElement(By.xpath("//div[@aria-label='Message Body']")).sendKeys(emailBody);
        Thread.sleep(1000);
        
        driver.findElement(By.xpath("//*[@role='button' and text()='Send']")).click();
        Thread.sleep(10000);
        
       
        List<WebElement> unreademail = driver.findElements(By.xpath("//span[@class='bog']"));
    
        
        
        // real logic starts here
        for(int i=0;i<unreademail.size();i++){
        	if(unreademail.get(i).isDisplayed()==true){
             // now verify if you have got mail form a specific subject
       
        		System.out.println(unreademail.get(i).getText());
        		if(unreademail.get(i).getText().contains(emailSubject)){
        			System.out.println("Yes we have got mail form " + emailSubject);
        			break;
        		}else{
        			System.out.println("No mail form " + emailSubject);
        		}
        	}
        
        }
             
        
}
}
