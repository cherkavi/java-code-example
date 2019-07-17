package com.cherkashyn.vitalii.parsers;

import java.io.FileOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.cherkashyn.vitalii.office.excel.ExcelRW;
import com.cherkashyn.vitalii.office.exception.OfficeException;
import com.cherkashyn.vitalii.parsers.spare_parts.model.PositionModel;
import com.cherkashyn.vitalii.parsers.spare_parts.model.SourceRecord;
import com.cherkashyn.vitalii.parsers.spare_parts.store.ScarabPriceExcelWalker;

public class ScarabParser {
	
	private static void log(String message){
		System.out.println(message);
	}
	
	public static void main(String[] args) throws OfficeException, InterruptedException{
		String pathToFile="C:\\scarab_images\\tatran-price2.xls";
		log(" - BEGIN - ");
		ScarabPriceExcelWalker walker=new ScarabPriceExcelWalker(pathToFile);
		log("walk through all records");
		int rowCounter=0;
		int rowScarabCounter=0;
		while(true){
			SourceRecord currentRecord=walker.readCurrentRecord();
			if(currentRecord.getNumber()!=null){
				rowCounter++;
				log("check for already parsed");
				if(currentRecord.getResultFirstElement()==null){
					log("check for number from scarab");
					if(isKatalogNumber(currentRecord.getNumber())){
						log("try to parse:"+currentRecord.getNumber());
						// check for null
						// save position
						// save images
						PositionModel model=new PositionModel();
						model.setKodCatalog(currentRecord.getNumber());
						// walker.writeToCurrentRecord(model);
						rowScarabCounter++;
					}else{
						log("another number, not for Scarab ");
					}
				}else{
					log("already processed");
				}
			}
			// Thread.currentThread().wait(2000);
			log("move to next row");
			if(walker.moveToNextRow()==false){
				break;
			}
		}
		walker.close();
		log(" Results: "+rowScarabCounter+"/"+rowCounter);
		log(" - END -");
	}

	private static boolean isKatalogNumber(String number) {
		// 452330657907
		if( (number.length()==12) && (StringUtils.isNumeric(number)) ){
			return true;
		}else{
			return false;
		}
	}
	
	private static void printToFile(String pathToFile, PositionModel model){
		FileOutputStream fos=new FileOutputStream(pathToFile);
		fos.
		IOUtils.writeLines(lines, "\n", output);
		try{
			
		}catch(IOException ex){
			
		}
	}
}
