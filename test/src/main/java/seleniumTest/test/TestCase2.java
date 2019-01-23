package seleniumTest.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class TestCase2 {
	WebDriver webDriver;
	
	@Parameters({"url","driverType"})
	@BeforeTest
	public void setWebdriver(String url,String driverType) {
		if ("chrome".equals(driverType)) {
			System.setProperty("webdriver.chrome.driver", "C:\\\\Program Files (x86)\\\\Google\\\\Chrome\\\\Application\\\\chromedriver.exe");
			webDriver = new ChromeDriver();
		}else {
			System.out.println("暂时不支持该浏览器!");
		}
		webDriver.get("https://www.baidu.com");
		webDriver.manage().window().maximize();
	}
	
	@Test
	public void runWebdriver() {
		webDriver.findElement(By.cssSelector("input#kw")).clear();
		webDriver.findElement(By.cssSelector("input#kw")).sendKeys("testng");
		webDriver.findElement(By.cssSelector("input#su")).click();
	}
	
	@AfterClass
	public void closeWebdriver() throws InterruptedException {
		Thread.sleep(5000);
		webDriver.quit();
	}
	
}
