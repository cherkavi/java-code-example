package com.cherkashyn.vitalii.jobtask.csvreader;

import java.io.File;
import java.io.IOException;

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

/**
 * parsing CSV file
 */
public class App {
    
	public static void main( String[] args ) throws SourceException, IOException{
    	File sourceFile=parseArguments(args);
    	Analyzer[] analyzers=getAnalyzers();
    	Column<?>[] columns=getColumns();
    	int skipLines=1;
    	
    	parseFile(sourceFile, skipLines, columns, analyzers);
		printResults(analyzers);
    }
	
	static void parseFile(File sourceFile, int skipLines, Column<?>[] columns, Analyzer[] analyzers) throws SourceException, IOException{
		try(CsvReader reader=new CsvReader(sourceFile, skipLines, columns)){
			while(reader.hasNext()){
				Analyzer.walkThrough(analyzers, reader.next());
			}
		}
	}

	private static void printResults(Analyzer[] analyzers) {
		for(Analyzer eachAnalyzer:analyzers){
			System.out.println(eachAnalyzer.getResult());
		}
	}

	private static Column<?>[] getColumns() {
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
		return columns;
	}

	private static Analyzer[] getAnalyzers() {
		Analyzer[] analyzers=new Analyzer[]{
				new AnalyzerSum("total number of items sold:", new ColumnInteger("quantity"))
				,new AnalyzerAmountOfOrder(new ColumnInteger("order_id"))
				,new AnalyzerAverageNumberOfItems(new ColumnInteger("order_id"), new ColumnInteger("quantity"))
		};
		return analyzers;
	}

	private static File parseArguments(String[] args) {
    	if(args.length<1){
    		System.err.println("path to file should be specified");
    		System.exit(1);
    	}
    	File file=new File(args[0]);
    	if(!file.exists()){
    		System.err.println("path to file should be specified");
    		System.exit(1);
    	}
    	return file;
	}
}
