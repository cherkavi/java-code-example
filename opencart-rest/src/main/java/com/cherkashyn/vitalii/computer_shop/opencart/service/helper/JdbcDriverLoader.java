package com.cherkashyn.vitalii.computer_shop.opencart.service.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
public class JdbcDriverLoader {
	
	@Autowired
	DriverManagerDataSource dataSource;
	
	public void init() throws ClassNotFoundException{
		Class.forName(dataSource.getDriverClassName());
	}

}
