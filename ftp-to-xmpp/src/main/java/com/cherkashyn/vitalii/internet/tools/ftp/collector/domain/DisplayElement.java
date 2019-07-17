package com.cherkashyn.vitalii.internet.tools.ftp.collector.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DisplayElement {
	private final String ip;
	private final String name;
	private final String phone;
	private final Date createDate;
	private final String subDomain;
	private final static SimpleDateFormat SDF=new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	
	public DisplayElement(String ip, String name, String phone) {
		this(ip, name, phone, new Date(), null);
	}

	public DisplayElement(String ip, String name, String phone, Date createdAt) {
		this(ip, name, phone, createdAt, null);
	}
	

	public DisplayElement(String ip, String name, String phone,
			Date createDate, String subDomain) {
		super();
		this.ip = ip;
		this.name = name;
		this.phone = phone;
		this.createDate = createDate;
		this.subDomain = subDomain;
	}

	public String getIp() {
		return ip;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getSubDomain() {
		return subDomain;
	}

	@Override
	public String toString() {
		return "ip=" + ip 
				+ ", subDomain=" + subDomain
				+ ", createDate=" + SDF.format(createDate) 
				+ ", name=" + name 
				+ ", phone="+ phone 
				;
	}
	
	
	
}
