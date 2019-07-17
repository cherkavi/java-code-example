package com.cherkashyn.vitalii.office.excel;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.cherkashyn.vitalii.office.exception.OfficeException;

public class ExcelTest {
	
	private final static String TEST_EXCEL_PATH="C:\\scarab_images\\tatran-price.xls";
	@Test
	public void testWrite() throws OfficeException{
		ExcelRW excel=new ExcelRW(TEST_EXCEL_PATH);
		System.out.println("Read:"+excel.read(0, 0));
		excel.write(5, 5, new SimpleDateFormat().format(new Date()));
		excel.close();
	}
}
