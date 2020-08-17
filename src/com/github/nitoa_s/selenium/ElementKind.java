package com.github.nitoa_s.selenium;

public enum ElementKind {
	CLASS("class"),
	ID("id"),
	NAME("name"),
	XPATH("xpath"),
	LINK_TEXT("linkText"),
	TEXT("text"),
	;
	private String kind;
	
	private ElementKind(String kind) {
		this.kind = kind;
	}
	
	public String getKind() {
		return kind;
	}
}
