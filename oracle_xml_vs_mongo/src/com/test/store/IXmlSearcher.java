package com.test.store;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IXmlSearcher {
	List<String> readXml(Connection connection, 
						 String xpath, 
						 String condition) throws SQLException;
	void printList(List<String> list);
}
