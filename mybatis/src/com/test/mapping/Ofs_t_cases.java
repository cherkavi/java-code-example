package com.test.mapping;

import org.w3c.dom.Document;

public class Ofs_t_cases {
	private int id;
	private String domain; 
	private String title;
	
	private Document payload;

	public Ofs_t_cases(){
	}
	public Ofs_t_cases(int id){
		this.id=id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Document getPayload() {
		return payload;
	}
	public void setPayload(Document payload) {
		this.payload = payload;
	}
	@Override
	public String toString() {
		return "Ofs_t_cases [id=" + id + ", domain=" + domain + ", title="
				+ title + "]";
	}
	
}
