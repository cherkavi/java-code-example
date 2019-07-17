package com.cherkashyn.vitalii.tools.file;

import java.io.File;


import com.cherkashyn.vitalii.tools.file.processor.FindBasketProcessor;
import com.cherkashyn.vitalii.tools.file.processor.MoveFolderProcessor;
import com.cherkashyn.vitalii.tools.file.processor.ParentIdentifierProcessor;
import com.cherkashyn.vitalii.tools.file.processor.PersistBasketProcessor;
import com.cherkashyn.vitalii.tools.persist.PersistException;
import com.cherkashyn.vitalii.tools.persist.PersistServiceImpl;

public class ScanDirectoryManager {

	/*
select pf.guid, m.barcode, f.name
from PARENT_FOLDER pf
inner join MARKER m on m.id_parent_folder=pf.id
inner join FILE f on f.id_marker=m.id

delete from FILE;
delete from MARKER;
delete from PARENT_FOLDER;
	 */
	
	public static void main(String[] arg) throws PersistException {
		// /tmp/source /tmp/destination /tmp/destinationError jdbc:mysql://localhost:3306/dir_scan root root com.mysql.jdbc.Driver
		if (arg.length < 7) {
			System.err
					.println("need to have two arguments: <source folder> <destination folder> <destination error folder> <url> <user> <password> <className>");
			System.exit(1);
		}
		String sourceFolder = arg[0];
		String destinationFolder = arg[1];
		String destinationErrorFolder = arg[2];
		String url = arg[3];
		String user = arg[4];
		String password = arg[5];
		String className = arg[6];

		new ScanDirectory(new File(sourceFolder), 
						  new File(destinationErrorFolder), 
						  2, 
						  new FindBasketProcessor( 0, 0, 50, 20 ),
						  new ParentIdentifierProcessor(),
						  new PersistBasketProcessor(new PersistServiceImpl(url, user,password, className)), 
						  new MoveFolderProcessor(new File(destinationFolder))
		).start();
	}

}
