package com.test.store;

import java.io.IOException;
import java.sql.SQLException;

public interface XmlStore {
	void clearData() throws SQLException;
	long writeXml(String xmlBody) throws SQLException, IOException;
	
}
