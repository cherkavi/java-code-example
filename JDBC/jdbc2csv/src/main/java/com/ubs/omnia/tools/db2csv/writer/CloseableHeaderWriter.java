package com.ubs.omnia.tools.db2csv.writer;

import com.ubs.omnia.tools.db2csv.common.Closeable;
import com.ubs.omnia.tools.db2csv.exception.GenericWriterException;

public interface CloseableHeaderWriter<T> extends Closeable{
	
	void writeHeader(T headers) throws GenericWriterException;
	
	void writeNext(T values) throws GenericWriterException;
	
	void close() throws GenericWriterException;

}
