package insurance_test;

import insurance_test.exists.AxiomaFields.XmlOutputVarType;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.util.ArrayUtil;

public class MsWordSpecificationSource implements ISpecificationSource{
	private String pathToFile=null;
	
	public MsWordSpecificationSource(String pathDocument) throws Exception {
		this.pathToFile=pathDocument;
	}
	
	/** get all records from source  */
	public List<Record> getCascoRecords() throws Exception{
		// open document 
		HWPFDocument document=new HWPFDocument(new FileInputStream(pathToFile));
		WordExtractor extractor=new WordExtractor(document);
		// read tables 
		String tableCasco=StringUtils.substringBetween(extractor.getText(), "Контракт Каско", "Контракт ОСАГО");
		List<String[]> rowCasco=getRowSplitColumn(tableCasco, true, 5);
		// parse records
		return convertToRecords(rowCasco);
	}

	private List<Record> convertToRecords(List<String[]> rowCasco) {
		ArrayList<Record> returnValue=new ArrayList<Record>(rowCasco.size());
		for(String[] values:rowCasco){
			int startIndex=0;
			if(StringUtils.trimToNull(values[0])==null){
				startIndex=1;
			}
			returnValue.add(getRecordFromArray(values, startIndex));
		}
		return returnValue;
	}

	private final static String RequiredYes="Да";
	// private final static String RequiredNo="Нет";
	
	private Record getRecordFromArray(String[] array, int startIndex) {
		try{
			// System.out.println(Arrays.toString(array));
			Record returnValue=new Record();
			returnValue.setName(array[startIndex].trim());
			returnValue.setXmlTag(array[startIndex+1].trim());
			returnValue.setRequired( RequiredYes.equalsIgnoreCase(StringUtils.trimToEmpty(array[startIndex+2])) );
			returnValue.setDataType(XmlOutputVarType.valueOf(array[startIndex+3].trim()));
			return returnValue;
		}catch(Exception ex){
			System.out.println("ParseError: "+getFromArray(array,startIndex+1)+"  "+getFromArray(array,startIndex+2)+"  "+getFromArray(array,startIndex+3));
			System.out.println("ParseRecord Exception:"+ex.getMessage());
			ex.printStackTrace(System.out);
			return null;
		}
	}
	
	private String getFromArray(String[] array, int index){
		if((array!=null)&&(array.length>index)){
			return array[index];
		}else {
			return null;
		}
	}
	
	
	public List<Record> getOsagoRecords() throws Exception{
		// open document 
		HWPFDocument document=new HWPFDocument(new FileInputStream(pathToFile));
		WordExtractor extractor=new WordExtractor(document);
		// read tables 
		String tableCasco=StringUtils.substringAfter(extractor.getText(), "Контракт ОСАГО");
		List<String[]> rowCasco=getRowSplitColumn(tableCasco, true ,5 );
		// parse records
		return convertToRecords(rowCasco);
	}
	
	private List<String[]> getRowSplitColumn(String text, boolean removeFirst, int columnCount){
		columnCount++;
		ArrayList<String[]> returnValue=new ArrayList<String[]>();
		String[] dirtyArray=text.split(""+((char)7));
		int index=0;
		while(index<dirtyArray.length){
			if( ((index+1)%columnCount)==0){
				returnValue.add((String[])ArrayUtils.subarray(dirtyArray, index-(columnCount), index));
			}
			index++;
		}
		if(removeFirst && (returnValue.size()>0)){
			returnValue.remove(0);
		}
		return returnValue;
	}


}
