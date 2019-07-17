package com.test.hadoop.client;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocalFileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;

public class DfsSimpleCommands {
	
	public static void main(String[] args) throws IOException{
		System.out.println("-- begin --");
		// FileSystem fileSystem=new DistributedFileSystem();
		Configuration configuration=new Configuration();
		String hdfsUrl="hdfs://172.17.0.2:7180";
		configuration.set("fs.default.name", hdfsUrl);
		configuration.set("fs.defaultFS",hdfsUrl);
		// conf.set(key, "hdfs://host:port");  // where key="fs.default.name"|"fs.defaultFS"
		
		FileSystem fileSystem=FileSystem.get(configuration);
		FileStatus[] statuses=fileSystem.listStatus(new Path("/","/home/technik"));
		for(FileStatus status:statuses){
			System.out.println(status.getPath());
		}
		System.out.println("--- end ---");
	}
	
}
