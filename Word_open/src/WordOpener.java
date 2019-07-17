import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;


public class WordOpener {
	
	public static void main(String[] args) throws FileNotFoundException, IOException{
		HWPFDocument document=new HWPFDocument(new FileInputStream("resource/specification.doc"));
		WordExtractor extractor=new WordExtractor(document);
		
		String tableCasco=StringUtils.substringBetween(extractor.getText(), "Контракт Каско", "Контракт ОСАГО");
		// System.out.println("Casco: "+tableCasco);
		System.out.println("==========================");
		List<String[]> rowCasco=getRowSplitColumn(tableCasco);
		for(int counter=0;counter<rowCasco.size();counter++){
			for(int index=0;index<rowCasco.get(counter).length;index++){
				System.out.print(rowCasco.get(counter)[index].replace(  (char)13, ' ' ));
				System.out.print(" |:| ");
			}
			System.out.println("<<< \n");
		}
		
		System.out.println("-------------");
		String tableOsago=StringUtils.substringAfter(extractor.getText(), "Контракт ОСАГО");
		// System.out.println("Osago:"+tableOsago);
		System.out.println("-------------");
		
		/*
		// the text with random word break 
		TextPieceTable table=document.getTextTable();
		System.out.println("CpMin: "+table.getCpMin());
		List<TextPiece> list=(List<TextPiece>)table.getTextPieces();
		for(int counter=0;counter<list.size();counter++){
			System.out.println("    ---- "+counter+" ---- ");
			PieceDescriptor descriptor=list.get(counter).getPieceDescriptor();
			System.out.println("Descriptor: "+descriptor.isUnicode());
			StringBuffer buffer=list.get(counter).getStringBuffer();
			System.out.println("Buffer:"+buffer.toString());
			System.out.println("   ------------------  ");
		}*/
	}

	private static List<String[]> getRowSplitColumn(String text){
		List<String> rows=getRowsFromTable(text);
		ArrayList<String[]> returnValue=new ArrayList<String[]>();
		for(String row:rows){
			returnValue.add(row.split(""+((char)7)));
		}
		return returnValue;
	}
	
	private static List<String> getRowsFromTable(String text){
		String delimeter=""+((char)7)+""+((char)7);
		System.out.println("Delimeter:"+delimeter);
		String[] lines=text.split(delimeter);
		return Arrays.asList(lines);
	}
	
}
