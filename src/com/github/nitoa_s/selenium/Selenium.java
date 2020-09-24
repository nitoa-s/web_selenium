package com.github.nitoa_s.selenium;

import java.util.logging.Level;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import com.github.nitoa_s.log.LogFile;
import com.github.nitoa_s.nlp.datastructure.tree.TreeStructure;
import com.github.nitoa_s.nlp.nlp.programming.html.HtmlAnalysis;
import com.github.nitoa_s.nlp.nlp.programming.html.HtmlTag;




public class Selenium {
	private static final LogFile logFile = new LogFile("selenium", "log/Selenium.log");
	private WebDriver driver;
	private ChromeOptions options = new ChromeOptions();
	private TreeStructure<HtmlTag> html;
	private boolean analysis;
	private WebElement iframe;
	public Selenium(boolean analysis) {
		this.analysis = analysis;
		System.setProperty("webdriver.chrome.driver", "exe/chromedriver.exe");
//		else if (OS.isMac())System.setProperty("webdriver.chrome.driver", "exe/mac/chromedriver");
	}

	public void start() {
		driver=new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		logFile.setLog(Level.INFO, "selenium起動");
	}

	public void addUserAgent(String agent) {
		options.addArguments(agent);
	}

	public void setSecretMode() {
		options.addArguments("incognito");
	}
	public void SetBrowserUrl(String url) {
		driver.get(url);
		logFile.setLog(Level.INFO, driver.getCurrentUrl()+"にアクセス");
		if(analysis)html=HtmlAnalysis.analysis(getSource());
	}

	public void setHeadless() {
		options.addArguments("--headless", "--disable-gpu");
	}

	public void back() {
		driver.navigate().back();
		logFile.setLog(Level.INFO, driver.getCurrentUrl()+"にアクセス");
	}

	public void forward() {
		driver.navigate().forward();
		logFile.setLog(Level.INFO, driver.getCurrentUrl()+"にアクセス");
	}

	public void reload() {
		driver.navigate().refresh();
		logFile.setLog(Level.INFO, driver.getCurrentUrl()+"を更新");
		if(analysis)html=HtmlAnalysis.analysis(getSource());
	}

	private WebElement getElement(String kind, String kindValue) {
		switch(kind) {
		case "class":return driver.findElement(By.className(kindValue));
		case "id":return driver.findElement(By.id(kindValue));
		case "name":return driver.findElement(By.name(kindValue));
		case "xpath":return driver.findElement(By.xpath(kindValue));
		case "linkText":return driver.findElement(By.linkText(kindValue));
		case "text":return driver.findElement(By.xpath("//*[text()=\""+kindValue+"\"]"));
		default:return driver.findElement(By.xpath("//*[@"+kind+"=\""+kindValue+"\"]"));
		}
	}

	private ArrayList<WebElement> getElements(String kind, String kindValue) {
		switch(kind) {
		case "class":return (ArrayList<WebElement>) driver.findElements(By.className(kindValue));
		case "id":return (ArrayList<WebElement>) driver.findElements(By.id(kindValue));
		case "name":return (ArrayList<WebElement>) driver.findElements(By.name(kindValue));
		case "xpath":return (ArrayList<WebElement>) driver.findElements(By.xpath(kindValue));
		case "linkText":return (ArrayList<WebElement>) driver.findElements(By.linkText(kindValue));
		case "text":return (ArrayList<WebElement>) driver.findElements(By.xpath("//*[text()=\""+kindValue+"\"]"));
		default:return null;
		}
	}

	public void setPullDownSelect(String kind, String kindValue, String value) {
		WebElement element = getElement(kind, kindValue);
		if(element==null)return;
		new Select(element).selectByVisibleText(value);
	}

	public void setInputText(String kind, String kindValue, String value) {
		WebElement element = getElement(kind, kindValue);
		if(element==null)return;
		element.clear();
		element.sendKeys(value);
	}
	public void click(String kind, String kindValue) {
		WebElement element = getElement(kind, kindValue);
		if(element==null)return;
		element.click();
	}

	public void click(String kind, String kindValue, String tag) {
		for(WebElement element:getElements(kind, kindValue))
			if(element.getTagName().equals(tag))
				element.click();
	}

	public void submit(String kind, String kindValue) {
		WebElement element = getElement(kind, kindValue);
		if(element==null)return;
		element.submit();
	}

	public String getContentValue(String kind, String kindValue, int index) {
		ArrayList<WebElement> elements = getElements(kind, kindValue);
		return elements.get(index - 1).getText();
	}
	public boolean isDisplayed(String kind, String kindValue) {
		WebElement element = getElement(kind, kindValue);
		if(element==null)return false;
		else return element.isDisplayed();
	}

	public void switchIframe(String kind, String kindValue) {
		WebElement element = getElement(kind, kindValue);
		if(!element.getTagName().equals("iframe"))return;
		driver.switchTo().frame(element);
		iframe = element;
	}

	public void backFromIframe() {
		driver.switchTo().defaultContent();
	}

	public void searchTag(String kind, String kindValue) {
		ArrayList<WebElement> elements = getElements(kind, kindValue);
		for(WebElement element:elements)
			System.out.println(element.getTagName());
	}

	public WebElement getSearchTag(String kind, String kindValue, int orderNum) {
		ArrayList<WebElement> elements = getElements(kind, kindValue);
		if(elements.size()>= orderNum)
			return elements.get(orderNum - 1);
		else
			return null;
	}

	public WebElement getSearchTag(String kind, String kindValue) {
		return getSearchTag(kind, kindValue, 1);
	}

	public int getSearchTagCount(String kind, String kindValue) {
		return getElements(kind, kindValue).size();
	}

	public WebElement getActiveElement() {return driver.switchTo().activeElement();}
	public boolean isIframe() {
		if(iframe == null)return false;
		else return true;
	}

	public Set<Cookie> getCookies(){
		return driver.manage().getCookies();
	}

	public void close() {driver.close();}
	public void quit() {driver.quit();}
	public String getUrl() {return driver.getCurrentUrl();}
	public String getTitle() {return driver.getTitle();}
	public String getSource() {return driver.getPageSource();}
	public void getHtml() {html.Print();}
	public ArrayList<HtmlTag> searchTagData(String tagName) {
		ArrayList<HtmlTag> searchTagData = new ArrayList<HtmlTag>(1);
		for(HtmlTag tag: html.getAllValues()) if( tag.getTagName().equals(tagName) ) searchTagData.add(tag);
		return searchTagData;
	}

}
