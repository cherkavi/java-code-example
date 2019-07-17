package compare_xls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import compare_xls.correctors.ReplaceCorrector;
import compare_xls.settings.ExcelSettings;
import compare_xls.source.ExcelFileSource;
import compare_xls.source.IGridSource;
import compare_xls.strategy.CompareCorrespondingValues;
import compare_xls.strategy.CompareFindDoubles;
import compare_xls.strategy.CompareOriginalExists;
import compare_xls.strategy.CompareSymmetricReversDecorator;
import compare_xls.strategy.CompareResult;
import compare_xls.strategy.ICompare;

public class ExcelComparator {
	public static void main(String[] args) throws Exception {
		BasicConfigurator.configure();
		Logger logger=Logger.getLogger(ExcelComparator.class);
		
		String pathToOriginal="/home/vcherkashin/Downloads/email_cardif_CAR_AUG-2011.xls"; // email_cardif_PUL_31-16-08_slyota.xls
		// file from ProjectOffice
		// String pathToControl="/home/vcherkashin/Tasks/Task_CFIT-438/check_2011_08_30/created_reports/Financing_reestr_back-office_26.08.2011.xls";
		// file from SQL Production
		String pathToControl="/home/vcherkashin/Downloads/email_cardif_PUL_6-9.xls";
		
		// open file
		IGridSource original=new ExcelFileSource(pathToOriginal);
		original.setSettings(getSettingsForOriginal());
		IGridSource control=new ExcelFileSource(pathToControl);
		control.setSettings(getSettingsForControl());

		// CompareOriginalExists
		// compare two files for equals of cells 
		// ICompare[] comparers=new ICompare[]{new CompareCorrespondingValues(new ReplaceCorrector(10, "", "0.0"))};
		// compare two files for not included values ( exists in one file but not exists in another )
		// ICompare[] comparers=new ICompare[]{ new CompareOriginalExists(), new CompareSymmetricReversDecorator(new CompareOriginalExists()) };
		// compare for repeat values 
		ICompare[] comparers=new ICompare[]{ new CompareFindDoubles(), new CompareSymmetricReversDecorator(new CompareFindDoubles()) };
		for(int counter=0;counter<comparers.length;counter++){
			logger.debug("Compare begin: "+comparers[counter]);
			printCompareResult(comparers[counter].compare(original, control));
			logger.debug("Compare -end-: "+comparers[counter]);
		}
		
	}
	
	private static void printCompareResult(List<CompareResult> list) {
		if(list!=null){
			for(int index=0;index<list.size();index++){
				System.out.println( (index+1)+"/"+list.size()+list.get(index));
			}
		}
	}

	
	/**
	 * get settings for Original file 
	 * @return
	 */
	private static ExcelSettings getSettingsForOriginal(){
		ExcelSettings returnValue=new ExcelSettings();
		returnValue.setSheetNumber(0);
		returnValue.setStartRow(1);
		returnValue.setPrimaryKeyColumn(3);// [0..)
		Map<Integer, Integer> compareValues=new HashMap<Integer, Integer>();
		compareValues.put(0, 1);
		// compareValues.put(1,2); financing date
		compareValues.put(2,3);
		compareValues.put(3,4);
		compareValues.put(4,5);
		compareValues.put(5,6);
		compareValues.put(6,7);
		compareValues.put(7,8);
		// compareValues.put(8,9);
		compareValues.put(9,10);
		// compareValues.put(10,11); // amount of comission for partner
		compareValues.put(11,12);
		// compareValues.put(12,13); // in_dossier.amount_of_loan
		compareValues.put(13,14);
		compareValues.put(14,15); // bank name 
		compareValues.put(15,16);
		compareValues.put(16,17); // trade point name 
		compareValues.put(17,18);
		// compareValues.put(18,19); // status_of_dossier 
		compareValues.put(19,20);
		returnValue.setCompare(compareValues);
		return returnValue;
	}

	/** 
	 * get Settings for Controlling file  
	 * @return
	 */
	private static ExcelSettings getSettingsForControl(){
		ExcelSettings returnValue=new ExcelSettings();
		returnValue.setSheetNumber(0);
		returnValue.setStartRow(1);
		returnValue.setPrimaryKeyColumn(2);
		returnValue.setCompare(null);
		return returnValue;
	}
	
}
