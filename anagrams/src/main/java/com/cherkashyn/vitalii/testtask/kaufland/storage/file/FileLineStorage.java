package com.cherkashyn.vitalii.testtask.kaufland.storage.file;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.IOUtils;

import com.cherkashyn.vitalii.testtask.kaufland.exception.StorageException;
import com.cherkashyn.vitalii.testtask.kaufland.exception.StorageRuntimeException;
import com.cherkashyn.vitalii.testtask.kaufland.storage.CloseableIterator;
import com.cherkashyn.vitalii.testtask.kaufland.storage.LineStorage;

public class FileLineStorage implements LineStorage, Closeable{
	private final File file;
	private PrintWriter writer=null;
	
	public FileLineStorage(File file){
		this.file=file;
	}
	
	private PrintWriter createWriter() throws FileNotFoundException{
		return new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.file)), true);
	}
	
	@Override
	public void addLine(String value) throws StorageException {
		if(this.writer==null){
			try {
				this.writer=createWriter();
			} catch (FileNotFoundException e) {
				throw new StorageException("can't add line into file:"+file.getAbsolutePath(),e);
			}
		}
		writer.println(value);
	}

	@Override
	public CloseableIterator<String> getIterator() throws RuntimeException{
		if(this.writer!=null){
			this.writer.close();
			this.writer=null;
		}
		try {
			return new FileCloseableIterator(copyFileToTemp(this.file));
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			throw new StorageRuntimeException("can't read source file:"+this.file.getAbsolutePath(), e);
		}
	}

	private static File copyFileToTemp(File fileSource) {
		File tempFile;
		InputStream input=null;
		OutputStream output=null;
		try{
			tempFile=File.createTempFile(fileSource.getName(), null);
			input=new FileInputStream(fileSource);
			output=new FileOutputStream(tempFile);
			
			IOUtils.copy(input, output);
			output.flush();
		}catch(IOException ex){
			throw new StorageRuntimeException("can't create temp file ");
		}finally{
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}
		return tempFile;
	}

	@Override
	public void close()  {
		if(this.writer==null){
			return;
		}
		this.writer.close();
		this.writer=null;
	}

	class FileCloseableIterator implements CloseableIterator<String>{
		private final File file;
		private String nextLine=null;
		private BufferedReader reader=null;
		
		private boolean needToCheckNextLine=false;
		private boolean hasNext=false;
		
		public FileCloseableIterator(File file) throws UnsupportedEncodingException, FileNotFoundException {
			this.file=file;
			this.reader=new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			needToCheckNextLine=true;
			hasNext=false;
		}
		
		@Override
		public boolean hasNext() throws StorageRuntimeException{
			if(!this.needToCheckNextLine){
				return hasNext;
			}
			this.needToCheckNextLine=false;
			try {
				this.nextLine=this.reader.readLine();
			} catch (IOException e) {
				throw new StorageRuntimeException("can't read next line from file:"+this.file.getAbsolutePath(), e);
			}
			this.hasNext=this.nextLine!=null;
			return this.hasNext;
		}

		@Override
		public String next() {
			if(!this.needToCheckNextLine){
				this.needToCheckNextLine=true;
				return this.nextLine;
			}
			this.hasNext();
			return this.nextLine;
		}

		@Override
		public void close() throws IOException {
			IOUtils.closeQuietly(reader);
			if(this.file.delete()==false){
				this.file.deleteOnExit();
			}
		}
		
	}
}
