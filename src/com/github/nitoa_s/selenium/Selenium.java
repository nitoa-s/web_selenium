package com.github.nitoa_s.selenium;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;




public class Selenium {
	private boolean canAnalysis = false;
	private ChromeOptions options = new ChromeOptions();
	private WebDriver driver;
	
	public Selenium(boolean canAnalysis) {
		this.canAnalysis = canAnalysis;
		final String os = getOS();
		if( os.startsWith("windows") ) {
			System.setProperty("webdriver.chrome.driver", "exe/chromedriver.exe");
		} else if( os.startsWith("mac") ) {
			System.setProperty("webdriver.chrome.driver", "exe/chromedriver");
		}
	}
	
	private String getOS() {
		return System.getProperty("os.name").toLowerCase();
	}
	
	public void start() {
		driver=new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void SetBrowserUrl(String url) {
		driver.get(url);
	}

	public void back() {
		driver.navigate().back();
	}

	public void forward() {
		driver.navigate().forward();
	}

	public void reload() {
		driver.navigate().refresh();
	}

	private WebElement getElement(ElementKind kind, String kindValue) {
		switch(kind) {
		case CLASS:return driver.findElement(By.className(kindValue));
		case ID:return driver.findElement(By.id(kindValue));
		case NAME:return driver.findElement(By.name(kindValue));
		case XPATH:return driver.findElement(By.xpath(kindValue));
		case LINK_TEXT:return driver.findElement(By.linkText(kindValue));
		case TEXT:return driver.findElement(By.xpath("//*[text()=\""+kindValue+"\"]"));
		default:return driver.findElement(By.xpath("//*[@"+kind+"=\""+kindValue+"\"]"));
		}
	}
	
	private ArrayList<WebElement> getElements(ElementKind kind, String kindValue) {
		switch(kind) {
		case CLASS:return (ArrayList<WebElement>) driver.findElements(By.className(kindValue));
		case ID:return (ArrayList<WebElement>) driver.findElements(By.id(kindValue));
		case NAME:return (ArrayList<WebElement>) driver.findElements(By.name(kindValue));
		case XPATH:return (ArrayList<WebElement>) driver.findElements(By.xpath(kindValue));
		case LINK_TEXT:return (ArrayList<WebElement>) driver.findElements(By.linkText(kindValue));
		case TEXT:return (ArrayList<WebElement>) driver.findElements(By.xpath("//*[text()=\""+kindValue+"\"]"));
		default:return null;
		}
	}

	public void setInputText(ElementKind kind, String kindValue, String value) {
		WebElement element = getElement(kind, kindValue);
		if(element==null)return;
		element.clear();
		element.sendKeys(value);
	}

	public void click(ElementKind kind, String kindValue) {
		WebElement element = getElement(kind, kindValue);
		if(element==null)return;
		element.click();
	}

	public void click(ElementKind kind, String kindValue, String tag) {
		for(WebElement element:getElements(kind, kindValue))
			if(element.getTagName().equals(tag))
				element.click();
	}

	public void submit(ElementKind kind, String kindValue) {
		WebElement element = getElement(kind, kindValue);
		if(element==null)return;
		element.submit();
	}

	public String getUrl() {return driver.getCurrentUrl();}
	public String getTitle() {return driver.getTitle();}
	public String getSource() {return driver.getPageSource();}

	public static void main(String[] args) {
		Selenium selenium = new Selenium(false);
		selenium.start();
		selenium.SetBrowserUrl("https://www.yahoo.co.jp/");
		System.out.println(selenium.getSource());
	}

}
