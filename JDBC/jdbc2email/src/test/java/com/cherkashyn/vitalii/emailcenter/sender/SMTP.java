package com.cherkashyn.vitalii.emailcenter.sender;

public enum SMTP {
	yandex( "smtp.yandex.com", 465, "service@yandex.com", "password"),
	mail_ru("smtp.mail.ru", 465, "service@mail.ru", "password");
	
	private final String server;
	private final int port;
	private final String user;
	private final String password;

	SMTP(String server, int port, String user, String password){
		this.server=server;
		this.port=port;
		this.user=user;
		this.password=password;
	}

	public String getServer() {
		return server;
	}

	public int getPort() {
		return port;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}
	
}

