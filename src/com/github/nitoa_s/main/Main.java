package com.github.nitoa_s.main;

import com.github.nitoa_s.selenium.Selenium;

public class Main {
	public static void main(String[] args) {
		Selenium selenium = new Selenium(true);
		selenium.start();
		selenium.SetBrowserUrl("https://www.yahoo.co.jp/");
		selenium.getHtml();
	}
}
