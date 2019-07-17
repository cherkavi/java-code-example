package com.cherkashyn.vitalii.jobtask.csvreader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.cherkashyn.vitalii.jobtask.csvreader.consumer.Analyzer;
import com.cherkashyn.vitalii.jobtask.csvreader.consumer.AnalyzerAmountOfOrder;
import com.cherkashyn.vitalii.jobtask.csvreader.consumer.AnalyzerAverageNumberOfItems;
import com.cherkashyn.vitalii.jobtask.csvreader.consumer.AnalyzerSum;
import com.cherkashyn.vitalii.jobtask.csvreader.exception.SourceException;
import com.cherkashyn.vitalii.jobtask.csvreader.source.CsvReader;
import com.cherkashyn.vitalii.jobtask.csvreader.source.domain.Column;
import com.cherkashyn.vitalii.jobtask.csvreader.source.domain.ColumnDate;
import com.cherkashyn.vitalii.jobtask.csvreader.source.domain.ColumnFloat;
import com.cherkashyn.vitalii.jobtask.csvreader.source.domain.ColumnInteger;
import com.cherkashyn.vitalii.jobtask.csvreader.source.domain.ColumnString;
import com.cherkashyn.vitalii.jobtask.csvreader.source.domain.Row;

/**
 * Unit test for simple App.
 */
public class AppTest {

	@Test
	public void fullCycleTest() throws SourceException, IOException {
		// given
		Column<?>[] columns = new Column<?>[] {
				// - day (format YYYYMMDD)
				new ColumnDate("day", "yyyyMMdd")
				// - country_id -> 3 chars (ISO)
				, new ColumnString("country_id")
				// - order_id -> integer
				, new ColumnInteger("order_id")
				// - product_category_id -> integer
				, new ColumnInteger("product_category_id")
				// - product_id -> integer
				, new ColumnInteger("product_id")
				// - quantity -> integer
				, new ColumnInteger("quantity")
				// - price per item -> decimal number
				, new ColumnFloat("price_per_item")

		};
		File csvFile = new File(
				Thread.currentThread().getContextClassLoader().getResource("real-example.csv").getFile());

		Analyzer[] analyzers=new Analyzer[]{
				new AnalyzerSum("total number of items sold:", new ColumnInteger("quantity"))
				,new AnalyzerAmountOfOrder(new ColumnInteger("order_id"))
				,new AnalyzerAverageNumberOfItems(new ColumnInteger("order_id"), new ColumnInteger("quantity"))
		};

		App.parseFile(csvFile, 1, columns, analyzers);

		String result1=analyzers[0].getResult();
		String result2=analyzers[1].getResult();
		String result3=analyzers[2].getResult();

		// then
		Assert.assertNotNull(result1);
		Assert.assertEquals(result1, "total number of items sold:14");
		Assert.assertEquals(result2, "total amount of orders:2");		
		Assert.assertEquals(result3, "average number of items per order:7.0");		
	}

}
