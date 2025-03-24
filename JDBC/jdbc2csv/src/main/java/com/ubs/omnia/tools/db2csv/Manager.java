package com.ubs.omnia.tools.db2csv;

import java.util.List;

import com.ubs.omnia.tools.db2csv.common.Closeable;
import com.ubs.omnia.tools.db2csv.exception.GenericConverterException;
import com.ubs.omnia.tools.db2csv.reader.CloseableHeaderIterator;
import com.ubs.omnia.tools.db2csv.reader.DbReader;
import com.ubs.omnia.tools.db2csv.settings.Parameters;
import com.ubs.omnia.tools.db2csv.writer.CloseableHeaderWriter;
import com.ubs.omnia.tools.db2csv.writer.FileWriter;

/**
 * converter from source ( DB ) to destination ( CSV )
 */
public class Manager 
{
    public static void main( String[] args ) throws GenericConverterException
    {
    	Parameters parameters=readParameters(args);
    	checkParameters(parameters);
    	
    	DbReader reader=new DbReader(parameters);
    	
    	FileWriter writer=new FileWriter(parameters);

    	try{
    		new Manager().execute(parameters, reader, writer);
    	}finally{
    		// close
    		quiteClose(reader);
    		quiteClose(writer);
    	}
    	
    	
    }
    
    public void execute(Parameters parameters, CloseableHeaderIterator<List<String>> reader, CloseableHeaderWriter<List<String>> writer) throws GenericConverterException{
    	// write headers
    	List<String> headers=reader.getHeaders();
    	writer.writeHeader(headers);
    	
    	List<String> next=null;
    	// write iterations
    	while( (next=reader.next())!=null ){
    		writer.writeNext(next);
    	}
    	
    }
    
    
    private static void quiteClose(Closeable target) {
    	try{
    		target.close();
    	}catch(GenericConverterException ex){
    	}
    }

    /**
     * check parameters for valid values;
     * @param parameters
     */
	private static void checkParameters(Parameters parameters) {
		// TODO Auto-generated method stub
		
	}

	private static Parameters readParameters(String[] args) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
