package com.cherkashyn.vitalii.jobtask.csvreader.source;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.cherkashyn.vitalii.jobtask.csvreader.exception.SourceException;
import com.cherkashyn.vitalii.jobtask.csvreader.source.domain.Column;
import com.cherkashyn.vitalii.jobtask.csvreader.source.domain.ColumnDate;
import com.cherkashyn.vitalii.jobtask.csvreader.source.domain.ColumnFloat;
import com.cherkashyn.vitalii.jobtask.csvreader.source.domain.ColumnInteger;
import com.cherkashyn.vitalii.jobtask.csvreader.source.domain.ColumnString;
import com.cherkashyn.vitalii.jobtask.csvreader.source.domain.Row;

import junit.framework.Assert;

public class CsvReaderTest {
	@Test
	public void testReadCsvFile() throws SourceException, IOException{
		Column<?>[] columns=new Column<?>[]{
//			    - day (format YYYYMMDD)
				 new ColumnDate("day", "yyyyMMdd")
//					- country_id -> 3 chars (ISO)
				,new ColumnString("country_id")
//				- order_id -> integer
				,new ColumnInteger("order_id")
//				- product_category_id -> integer
				,new ColumnInteger("product_category_id")
//				- product_id -> integer
				,new ColumnInteger("product_id")
//				- quantity -> integer
				,new ColumnInteger("quantity")
//				- price per item -> decimal number
				,new ColumnFloat("price_per_item")
				
		};
		int expectedRowCount=4;
		File csvFile=new File(Thread.currentThread().getContextClassLoader().getResource("real-example.csv").getFile());
		
		// when
		List<Row> listOfRow=new ArrayList<Row>(expectedRowCount);
		try(CsvReader reader=new CsvReader(csvFile, 1, columns)){
			while(reader.hasNext()){
				listOfRow.add(reader.next());
			}
		}
		
		// then
		Assert.assertEquals(expectedRowCount, listOfRow.size());
		Row row=listOfRow.get(0);
		// TODO check each data by type 
		Assert.assertNotNull(row.getData()[0]);
		Assert.assertNotNull(row.getData()[1]);
		Assert.assertNotNull(row.getData()[2]);
		Assert.assertNotNull(row.getData()[3]);
		Assert.assertNotNull(row.getData()[4]);
		Assert.assertNotNull(row.getData()[5]);
		Assert.assertNotNull(row.getData()[6]);
	}
}
