package com.cherkashyn.vitalii.internet.tools.ftp.collector.storage;

import java.util.Arrays;
import java.util.List;

public class FtpDirectories {
	
	List<String> folders;

	public FtpDirectories(String ... folders) {
		this(Arrays.asList(folders));
	}
	
	public FtpDirectories(List<String> folders) {
		super();
		this.folders = folders;
	}

	public List<String> getFolders() {
		return folders;
	}
	
}
