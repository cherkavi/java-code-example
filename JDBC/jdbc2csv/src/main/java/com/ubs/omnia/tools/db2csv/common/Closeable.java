package com.ubs.omnia.tools.db2csv.common;

import com.ubs.omnia.tools.db2csv.exception.GenericConverterException;

public interface Closeable {
	
	void close() throws GenericConverterException;
}
