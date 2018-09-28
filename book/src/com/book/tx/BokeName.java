package com.book.tx;

public class BokeName {

	private String name;
	private String url;
	private String content;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "BokeName [name=" + name + ", url=" + url + ", content=" + content + "]";
	}
	
	
}
