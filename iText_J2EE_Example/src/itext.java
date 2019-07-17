

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Servlet implementation class itext
 */
public class itext extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doService(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doService(request, response);
	}

	private SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy HH:mm:sss");
	protected void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			// создать документ
			Document document = new Document();
			// создать тело дл€ записи данных 
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// св€зать документ и тело дл€ записи данных
			PdfWriter.getInstance(document, baos);
			// открыть документ 
			document.open();
			// произвести необходимые манипул€ции
			document.add(new Paragraph(sdf.format(new java.util.Date())));
			// закрыть документ - зафиксировать все изменени€
			document.close();
			
			// указать пользователю (броузеру) на ответ
			response.setContentType("application/pdf");
			// указать пользователю (броузеру) на размер ответа 
			response.setContentLength(baos.size());
			// получить объект дл€ записи данных
			ServletOutputStream out = response.getOutputStream();
			// вылить файл пользователю (броузеру)
			baos.writeTo(out);
			out.flush();		
		}catch(DocumentException ex){
			PrintWriter pw=response.getWriter();
			pw.println("<html>");
			pw.println("	<head>");
			pw.println("	<title>Error</title>");
			pw.println("	</head>");
			pw.println("	<body>"+ex.getMessage()+"</body>");
			pw.println("</html>");
			pw.close();
		}
	}
}
