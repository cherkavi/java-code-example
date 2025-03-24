package com.ubs.omnia.tools.db2csv.reader;

import com.ubs.omnia.tools.db2csv.common.Closeable;
import com.ubs.omnia.tools.db2csv.exception.GenericReaderException;

public interface CloseableHeaderIterator<T> extends Closeable{
	
	T getHeaders() throws GenericReaderException;
	
	T next() throws GenericReaderException;

	void close() throws GenericReaderException;
	
}
