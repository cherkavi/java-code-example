package com.cherkashyn.vitalii.tools.database.diff.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class SqlFilterTest {
	private final static String LINE_DELIMITER="\n";

	static StringBuilder testFile=new StringBuilder();
	static {
	testFile.append("222c222");
	testFile.append(LINE_DELIMITER);
	testFile.append("< ) ENGINE=MyISAM AUTO_INCREMENT=518 DEFAULT CHARSET=utf8 COLLATE=utf8_bin PACK_KEYS=0;");
	testFile.append(LINE_DELIMITER);
	testFile.append("---");
	testFile.append(LINE_DELIMITER);
	testFile.append("> ) ENGINE=MyISAM AUTO_INCREMENT=519 DEFAULT CHARSET=utf8 COLLATE=utf8_bin PACK_KEYS=0;");
	testFile.append(LINE_DELIMITER);
	testFile.append("741a742");
	testFile.append(LINE_DELIMITER);
	testFile.append("> INSERT INTO `Point` (`id`, `name`, `svg_id`, `photo`, `address`, `phone`, `url`, `video_file_name`, `video_file_time`, `description`) VALUES (518,'test point','test','','','','','','','');");
	testFile.append(LINE_DELIMITER);
	testFile.append("2958c2959");
	testFile.append(LINE_DELIMITER);
	testFile.append("< -- Dump completed on 2014-04-24  8:55:14");
	testFile.append(LINE_DELIMITER);
	testFile.append("---");
	testFile.append(LINE_DELIMITER);
	testFile.append("> -- Dump completed on 2014-04-24  8:55:41");
	testFile.append(LINE_DELIMITER);
	}
	
	@Test
	public void sqlFilterTest() throws IOException{
		// given
		List<String> lines=readLines(testFile);
		// when
		List<String> result=SqlFilter.filter(lines);
		// then
		Assert.assertEquals(1, result.size());
	}

	private List<String> readLines(StringBuilder testFile2) throws IOException {
		BufferedReader reader=new BufferedReader(new StringReader(testFile2.toString()));
		List<String> returnValue=new ArrayList<String>();
		String nextLine=null;
		while((nextLine=reader.readLine())!=null){
			returnValue.add(nextLine);
		}
		return returnValue;
	}
	
}

