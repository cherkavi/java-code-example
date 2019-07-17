import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.FontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;


public class TestFreeChart {
	public static void main(String[] args){
		// create a dataset...
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Category 1", 43.2);
		dataset.setValue("Category 2", 27.9);
		dataset.setValue("Category 3", 79.5);
		// create a chart...
		JFreeChart chart = ChartFactory.createPieChart(
				"Sample Pie Chart",
				dataset,
				true, // legend?
				true, // tooltips?
				false // URLs?
		);
		
		
		// create and display a frame...
		// ChartFrame frame = new ChartFrame("First", chart);
		// frame.pack();
		// frame.setVisible(true);
		
		// output to JPEG File
		try{
			OutputStream out=new FileOutputStream("c:\\out.jpg");
			ChartUtilities.writeChartAsJPEG(out, 1, chart, 400, 300);
			out.flush();
			out.close();
		}catch(Exception ex){
			System.err.println("Write to file Exception:"+ex.getMessage());
		}

		// output to PDF
		try{
			OutputStream out=new FileOutputStream("c:\\out.pdf");
			writeChartAsPDF(out, chart, 400, 300, new DefaultFontMapper());
			out.flush();
			out.close();
		}catch(Exception ex){
			System.err.println("Write to file Exception:"+ex.getMessage());
		}
		
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		System.out.println("Done:"+sdf.format(new Date()));
	}
	
	public static void writeChartAsPDF( OutputStream out,
										JFreeChart chart,
										int width,
										int height,
										FontMapper mapper) throws IOException {
		Rectangle pagesize = new Rectangle(width, height);
		Document document = new Document(pagesize, 50, 50, 50, 50);
		try {
			PdfWriter writer = PdfWriter.getInstance(document, out);
			document.addAuthor("place for Author output");
			document.addSubject("Subject");
			document.open();
			PdfContentByte cb = writer.getDirectContent();
			PdfTemplate tp = cb.createTemplate(width, height);
			Graphics2D g2 = tp.createGraphics(width, height, mapper);
			Rectangle2D r2D = new Rectangle2D.Double(0, 0, width, height);
			chart.draw(g2, r2D);
			g2.dispose();
			cb.addTemplate(tp, 0, 0);
		}
		catch (DocumentException de) {
			System.err.println(de.getMessage());
		}
		document.close();
	}
}
