package com.cherkashyn.vitaliy.bpmn.utility;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;

public class InputStreamIterator implements Iterable<Pair<String, InputStream>>{
	private final static Logger logger=Logger.getLogger(InputStreamIterator.class.getName());
	private String dir;
	private File[] files;
	
	public InputStreamIterator(String pathToDirectory) throws IOException{
		this(pathToDirectory, BPMN_FILEFILTER);
	}

	public InputStreamIterator(String pathToDirectory, FileFilter fileFilter) throws IOException{
		this.dir=pathToDirectory;
		this.init(fileFilter);
	}
	
	protected void init(FileFilter fileFilter) throws IOException {
		File dirFile=new File(StringUtils.trim(this.dir));
		this.dir=dirFile.getAbsolutePath();
		if(dirFile.isDirectory()){
			files=dirFile.listFiles(fileFilter);
		}else{
			throw new IOException(MessageFormat.format("check Directory:{0} for existing",this.dir));
		}
	}
	
	public String getPath(){
		return this.dir;
	}
	
	@Override
	public Iterator<Pair<String,InputStream>> iterator()  {
		List<Pair<String,InputStream>> returnValue=new ArrayList<Pair<String,InputStream>>();
		for(File file:this.files){
			try{
				returnValue.add(new ImmutablePair<String, InputStream>(file.getName(), new FileInputStream(file)));
			}catch(IOException ex){
				logger.error("Error, when try to open file ", ex);
			}
		}
		return returnValue.iterator();
	}

	
	public static FileFilter BPMN_FILEFILTER=new FileFilter(){
		private String suffix="bpmn20.xml";
		@Override
		public boolean accept(File file) {
			return (file.isFile() && StringUtils.endsWith(file.getName(), suffix));
		}
	};
}
