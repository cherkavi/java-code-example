

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Test
 */
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Test() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request,response);
	}

	
	private void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		{
			System.out.println("out parameter's:begin");
			Enumeration enumeration=request.getParameterNames();
			while(enumeration.hasMoreElements()){
				System.out.println(enumeration.nextElement());
			}
			System.out.println("out parameter's:end");
		}
		Enumeration enumeration=request.getParameterNames();
		PrintWriter printWriter=response.getWriter();
		printWriter.write("<html>\n");
		printWriter.write("<head>\n");
		printWriter.write("</head>\n");
		
		printWriter.write("<body>\n");
		printWriter.write("<table>");
		while(enumeration.hasMoreElements()){
			printWriter.write("<tr>");
			printWriter.write("<td>");
			printWriter.write(enumeration.nextElement().toString());
			printWriter.write("</td>");
			printWriter.write("</tr>");
		}
		printWriter.write("</table>");
		printWriter.write("</body>\n");
		printWriter.write("</html>\n");
	}
}
