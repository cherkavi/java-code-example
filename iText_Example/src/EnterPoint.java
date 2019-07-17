import java.io.FileOutputStream;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BarcodeEAN;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;


/** пример формирование PDF документа */
public class EnterPoint {
	
	// http://itextdocs.lowagie.com/tutorial/
	public static void main(String[] args){
		printSimpleTextToPDF("c:\\HelloWorld.pdf");
		
	}
	
	private static void printSimpleTextToPDF(String pathToFile){
		try{
			
			Document document=new Document();
			document.setMargins(10, 10, 10, 10);
			document.addTitle("Hello World example");
			document.addAuthor("Bruno Lowagie");
			document.addSubject("This example explains how to add metadata.");
			document.addKeywords("iText, Hello World, step 3, metadata");
			document.addCreator("My program using iText");

			PdfWriter writer=PdfWriter.getInstance(document, new FileOutputStream(pathToFile));
			document.open();
			document.add(new Paragraph("Hello World"));
			document.add(new Chunk(Chunk.NEWLINE));
			document.add(new Chunk(Chunk.NEWLINE));
			document.add(new Chunk(Chunk.NEWLINE));
			
			PdfContentByte cb = writer.getDirectContent();
			BarcodeEAN codeEAN = new BarcodeEAN();
			codeEAN.setCodeType(BarcodeEAN.EAN13);
			codeEAN.setCode("9780201615883");
			Image imageEAN = codeEAN.createImageWithBarcode(cb, null, null);
			document.add(new Phrase(new Chunk(imageEAN, 0, 0)));			
			document.close();
			
		}catch(Exception ex){
			System.err.println("Exception: "+ex.getMessage());
		}
	}
}
