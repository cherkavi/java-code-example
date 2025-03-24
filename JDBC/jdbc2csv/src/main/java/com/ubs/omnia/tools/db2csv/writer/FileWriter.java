package com.ubs.omnia.tools.db2csv.writer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.ubs.omnia.tools.db2csv.exception.GenericWriterException;
import com.ubs.omnia.tools.db2csv.settings.Parameters;

public class FileWriter  implements CloseableHeaderWriter<List<String>>{
	private final Parameters parameters;
	private final File tempFile;
	private CsvFileWriter writer;
	
	private final static String TEMP_FILE_PREFIX=FileWriter.class.getName();
	private final static String TEMP_FILE_SUFIX=FileWriter.class.getName();
	
	
	public FileWriter(Parameters parameters) throws GenericWriterException{
		this(parameters, OpenCsvWriter.class);
	}
	
	public FileWriter(Parameters parameters, Class<? extends CsvFileWriter> csvWriterClass) throws GenericWriterException{
		this.parameters=parameters;
		// create temp file
		try {
			tempFile=File.createTempFile(TEMP_FILE_PREFIX, TEMP_FILE_SUFIX);
		} catch (IOException e1) {
			throw new GenericWriterException("can't create temp file for writing ");
		}
		// create CSV writer
		try {
			if(StringUtils.trimToNull(parameters.getDelimiter())!=null){
				writer = createWriter(csvWriterClass, new java.io.FileWriter(tempFile), parameters.getDelimiter());
			}else{
				writer = createWriter(csvWriterClass, new java.io.FileWriter(tempFile));
			}
		} catch (NoSuchMethodException ex){
			throw new GenericWriterException("can't find constructor of Class: "+csvWriterClass.getName());
		} catch( SecurityException ex){
			throw new GenericWriterException("can't access (security) to Class: "+csvWriterClass.getName());
		} catch(InstantiationException ex){
			throw new GenericWriterException("can't instance of class:: "+csvWriterClass.getName());
		} catch(IllegalAccessException ex){
			throw new GenericWriterException("can't access to class/constructor: "+csvWriterClass.getName());
		} catch(IllegalArgumentException ex){
			throw new GenericWriterException("can't find constructor(FileWriter) of Class: "+csvWriterClass.getName());
		} catch(InvocationTargetException ex){
			throw new GenericWriterException("can't create instance: "+csvWriterClass.getName());
		} catch(IOException ex){
			throw new GenericWriterException("can't create CSV writer for file: "+tempFile.getAbsolutePath());
		}
	}

	private CsvFileWriter createWriter(
			Class<? extends CsvFileWriter> csvWriterClass,
			java.io.FileWriter fileWriter, String delimiter) 
					throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException 
	{
		Constructor<? extends CsvFileWriter> constructor=csvWriterClass.getConstructor(java.io.FileWriter.class, String.class);
		return constructor.newInstance(fileWriter, delimiter);
	}

	private CsvFileWriter createWriter(Class<? extends CsvFileWriter> csvWriterClass, java.io.FileWriter fileWriter) 
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Constructor<? extends CsvFileWriter> constructor=csvWriterClass.getConstructor(java.io.FileWriter.class);
		return constructor.newInstance(fileWriter);
	}

	@Override
	public void writeHeader(List<String> headers) throws GenericWriterException {
		writeNext(headers);
	}

	@Override
	public void writeNext(List<String> values) throws GenericWriterException {
		try{
			this.writer.write(values.toArray(new String[values.size()]));
		}catch(IOException ex){
			throw new GenericWriterException(ex);
		}
	}

	@Override
	public void close() throws GenericWriterException {
		try {
			writer.flush();
			writer.close();
		} catch (IOException e) {
			throw new GenericWriterException("can't close destination file ");
		}
		
		copyFromTempToDestination();
		
		tempFile.delete();
	}

	@SuppressWarnings("resource")
	private void copyFromTempToDestination() throws GenericWriterException{
		InputStream input;
		try {
				input = new FileInputStream(tempFile);
		} catch (FileNotFoundException e) {
			throw new GenericWriterException("can't find temp file: "+tempFile.getAbsolutePath(), e);
		}
		
		OutputStream output;
		try {
			output = new FileOutputStream(parameters.getOutputUrl());
		} catch (FileNotFoundException e) {
			throw new GenericWriterException("can't create output file: "+parameters.getOutputUrl(), e);
		}
		
		try {
			IOUtils.copy(input, output);
		} catch (IOException e) {
			throw new GenericWriterException(MessageFormat.format("exception during copy from {0} to {1} : ",tempFile.getAbsoluteFile(), parameters.getOutputUrl()), e);
		}
		
		IOUtils.closeQuietly(input);
		IOUtils.closeQuietly(output);
	}
	
	
}
