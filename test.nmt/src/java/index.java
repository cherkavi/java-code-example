/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import database.Connector;
import java.io.*;
import database.Utility;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import report.Jasper;

/**
 *
 * @author First
 */
public class index extends HttpServlet {
    //Utility field_data=null;
    Connector field_data=null;
    java.sql.Connection field_connection=null;
    Logger field_logger;
    String field_path_to_pattern=null;
    String field_path_to_file=null;
    String[] field_criteria=new String[]{"CARD_SERIAL_NUMBER","NT_ICC","CD_CARD1"};
    
    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        field_logger=Logger.getRootLogger();
        field_logger.setLevel(Level.DEBUG);
        //field_data=new Utility();
        try{
            field_data=new Connector(this.getServletContext().getInitParameter("database_name"));
            //field_data.Connect("test2");
            
            /*if(field_data.fillBase()==false){
                throw new SQLException("DB not pervaded");
            }*/
            this.field_connection=field_data.getConnection();
             
        }catch(SQLException ex){
            field_logger.error("Mistake on connecting to database or data in database");
        }
        
    }
    public void destroy(){
        if(this.field_connection!=null){
        /*    try{
                this.field_data.shutdown();
            }catch(Exception ex){};
         */
            try{
                this.field_connection.close();
            }catch(Exception ex){}
        }
    }
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            if(this.field_connection!=null){
                // database is connected
                if(request.getParameter("exp")!=null){
                    // return report
                    boolean user_got_page=false;
                    if(request.getParameter("exp").equals("pdf")){
                        field_logger.debug("get pdf:");
                        user_got_page=this.outReport(request, response,"pdf");
                    }
                    if(request.getParameter("exp").equals("xls")){
                        field_logger.debug("get xls:");
                        user_got_page=this.outReport(request, response,"xls");
                        user_got_page=true;
                    }
                    if(request.getParameter("exp").equals("rtf")){
                        field_logger.debug("get doc:");
                        user_got_page=this.outReport(request, response,"rtf");
                        user_got_page=true;
                    }
                    if(user_got_page==false){
                        field_logger.debug("unknow file type:"+request.getParameter("exp"));
                        RequestDispatcher dispatcher=this.getServletContext().getRequestDispatcher("/Error");
                        request.setAttribute("Information", "Request error");
                        dispatcher.forward(request, response);
                    }
                }else{
                    // return start page
                    PrintWriter out = response.getWriter();
                    out.println(this.getStartPage());
                }
            }else{
                // error in connected to database
                field_logger.debug("error in connecting to database");
                RequestDispatcher dispatcher=this.getServletContext().getRequestDispatcher("/Error");
                request.setAttribute("Information", "Mistake in connection to database");
                dispatcher.forward(request, response);
            }

        } finally { 
            PrintWriter out = response.getWriter();
            out.close();
        }
    } 

    /**
     * получить имя критерия по последним цифрам в строке
     */
    private String getCriteriatFromID(String id,String name){
        String return_value="";
        if((id!=null)&&(!id.equals(""))&&(name!=null)&&(!name.equals(""))){
            try{
                field_logger.debug("===index:"+name.substring(id.length(),name.length()));
                return_value=this.field_criteria[Integer.parseInt(name.substring(id.length(),name.length()))];
            }catch(Exception ex){
                field_logger.error("Error in detect criteria:"+name);
            }
            
        }
        return return_value;
    }
    
    /** 
     *  получить критерии отбора в виде строк, обрамленных двойными кавычками с запятыми между ними
     * @return
     */
    private String getCriteriaDelimeterComma(){
        StringBuffer return_value=new StringBuffer();
        for(int counter=0;counter<this.field_criteria.length;counter++){
            return_value.append("\""+this.field_criteria[counter]+"\"");
            if(counter!=(this.field_criteria.length-1)){
                return_value.append(",");
            }
        }
        return return_value.toString();
    }
    
    private String read_file(String path_to_file){
        StringBuffer return_value=new StringBuffer();
        File f=new File(path_to_file);
        if(f.exists()){
            try{
                BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                String current_line;
                while((current_line=br.readLine())!=null){
                    return_value.append(current_line);
                    return_value.append("\n");
                }
            }catch(Exception ex){
                field_logger.error("read file:"+path_to_file+"    Exception:"+ex.getMessage());
            }
        }
        return return_value.toString();
    }
    
    /**
     * 
     * @return start page
     */
    private String getStartPage(){
        StringBuffer return_value=new StringBuffer();
        return_value.append("<html>\n");
        return_value.append("<head>\n");
        return_value.append("<title>View</title>\n");  
        return_value.append("    <script language=\"javascript\">\n");
        return_value.append("        var field_values=new Array("+getCriteriaDelimeterComma()+"); \n");

        return_value.append("        var combobox_name=\"criteria\";\n");
        return_value.append("        var combobox_parent_name=\"combobox_parent\";\n");
        return_value.append("        var table_name=\"table\";\n");
        return_value.append("        var table_parent_name=\"form\";\n");
        return_value.append("        var form_element_prefix=\"index_\";\n");
        return_value.append("    </script>\n");

        return_value.append("<script language=\"javascript\" >\n");
        return_value.append( read_file(this.getServletContext().getInitParameter("path_to_script")+"script.js"));
        return_value.append("</script>\n");

        return_value.append("</head>\n");
        /*return_value.append("<body>\n");
        return_value.append("<h1> Choice your report</h1>\n");
        return_value.append("<a href=\"index?exp=pdf\">pdf</a><br>\n");
        return_value.append("<a href=\"index?exp=xls\">xls</a><br>\n");
        return_value.append("<a href=\"index?exp=rtf\">rtf</a><br>\n");
        return_value.append("</body>\n");*/
        return_value.append("<body onload=\"load_combobox_criteria();\" id=\"body\">\n");
        return_value.append("    <input type=button value=\"add criteria\" id=\"button_add\" onclick=\"button_add_click()\"/>\n");
        return_value.append("    <span id=\"combobox_parent\">\n");
        return_value.append("        <select id=\"criteria\">\n");
        return_value.append("        </select>\n");
        return_value.append("    </span>\n");
        return_value.append("    <br />\n");
        return_value.append("    <form action=\"index\" id=\"form\" method=\"post\">\n");
        return_value.append("        <table id=\"table\">\n");
        return_value.append("        </table>\n");
        return_value.append("        <span id=\"manager\">\n");
        return_value.append("	    <input type=\"radio\" name=\"exp\" value=\"pdf\" checked/> pdf<br />\n");
        return_value.append("	    <input type=\"radio\" name=\"exp\" value=\"rtf\" /> rtf<br />\n");
        return_value.append("	    <input type=\"radio\" name=\"exp\" value=\"xls\" /> xls<br />\n");
        return_value.append("	    </span>\n");
        return_value.append("	    <input type=\"submit\" value=\"get_file\"/>\n");
        return_value.append("	</form>\n");
        return_value.append("    <span id=\"console\"></span>\n");
        return_value.append("</body>\n");
        return_value.append("</html>\n");
        return return_value.toString();
    }
    /**
     * Метод, который выливает файл пользователю в броузер
     * @param buffer
     * @param servlet_out
     * @throws java.io.IOException
     */
    private void fileToStream(BufferedInputStream buffer,ServletOutputStream servlet_out) throws IOException{
        byte[] read_bytes=new byte[1000];
        int byte_count=0;
        while( (byte_count=buffer.read(read_bytes))!=-1){
            servlet_out.write(read_bytes,0,byte_count);
        }
    }
    /**
     * output PDF file
     * @param request
     * @param response
     * @return true if user has got file
     */
    private boolean outReport(HttpServletRequest request,HttpServletResponse response,String type){
        boolean return_value=false;
        BufferedInputStream buffer=null;
        ServletOutputStream servlet_out=null;
        try{
            String path_to_file=this.getPathToFile();
            int jasper_type=0;
            if(type.equals("pdf")){
                path_to_file+=".pdf";
                jasper_type=Jasper.TYPE_PDF;
                response.setContentType("application/pdf");
                response.addHeader("Content-Disposition", "attachment; filename=report.pdf");
            }
            if(type.equals("rtf")){
                path_to_file+=".rtf";
                jasper_type=Jasper.TYPE_RTF;
                response.setContentType("application/rtf");
                response.addHeader("Content-Disposition", "attachment; filename=report.rtf");
            }
            if(type.equals("xls")){
                path_to_file+=".xls";
                jasper_type=Jasper.TYPE_XLS;
                response.setContentType("application/xls");
                response.addHeader("Content-Disposition", "attachment; filename=report.xls");
            }
            
            field_logger.debug("create query");
            ResultSet result_set=this.getResultSet(request);
            field_logger.debug("create report");
            File export_file=new File(path_to_file);
            if(Jasper.createReport(this.getPatternPath(), result_set, path_to_file,jasper_type)==true){
                field_logger.debug("report compile and created");
            }else{
                field_logger.error("report Error created");
            }
            field_logger.debug("report to user");
            response.setContentLength((int)export_file.length());
            field_logger.debug("output file:"+path_to_file+"  length:"+export_file.length());
            buffer=new BufferedInputStream(new FileInputStream(export_file));    
            servlet_out=response.getOutputStream();
            this.fileToStream(buffer, servlet_out);
            field_logger.debug("export done");
            // close Statement
            result_set.getStatement().close();
            return_value=true;
        }catch(IOException ex){
            field_logger.error("export IOException:"+ex.getMessage());
        }catch(Exception ex){
            field_logger.error("export Exception:"+ex.getMessage());
        }
        finally{
            if(buffer!=null){
                try{
                    buffer.close();
                }catch(Exception ex){
                    field_logger.error("PDF export buffer close error");
                }
            }
            if(servlet_out!=null){
                try{
                    servlet_out.close();
                }catch(Exception ex){
                    field_logger.error("PDF export servlet_stream close error");
                }
            }
        }
        return return_value;
    }
    /**
     * возвращает на основании параметров запроса HttpServletReaquest ResultSet из базы данных
     * @param request
     * @return
     */
    private ResultSet getResultSet(HttpServletRequest request) throws SQLException{
        StringBuffer where_string=new StringBuffer();
        HashMap where=new HashMap();
        Enumeration param=request.getParameterNames();
        String key_current=null;
        while(param.hasMoreElements()){
            key_current=((String)param.nextElement());
            if(!getCriteriatFromID("index_",key_current).equals("")){
                field_logger.debug(getCriteriatFromID("index_",key_current));
                field_logger.debug(": Name:"+key_current+"  Value:"+request.getParameter(key_current));
                where.put(getCriteriatFromID("index_",key_current), 
                          request.getParameter(key_current));
            }
        }
        
        Set parameters_key=null;
        Iterator key_iterator=null;
        if(where.size()>0){
            where_string.append("\n WHERE \n");
            key_iterator=where.keySet().iterator();
            int predel=0;
            while(key_iterator.hasNext()){
                predel++;
                key_current=(String)key_iterator.next();
                where_string.append(key_current+" LIKE '%"+((String)where.get(key_current)).toUpperCase()+"%' ");
                if(predel!=where.size()){
                    where_string.append(" AND ");
                }
            }
        }
        field_logger.debug("Query:"+where_string.toString());
        return field_data.getResultSet("SELECT * FROM D_CLUB_CARD "+where_string.toString());
    }
    /**
     * @return возвращает путь к имени файла (без расширения) для вывода данных
     */
    private String getPathToFile(){
        if(this.field_path_to_file==null){
            this.field_path_to_file=this.getServletContext().getInitParameter("path_to_file");
        }
        return this.field_path_to_file;
    }
    
    /**
     * @return возвращает путь к шаблону для JasperReport
     */
    private String getPatternPath(){
        if(this.field_path_to_pattern==null){
            this.field_path_to_pattern=this.getServletContext().getInitParameter("path_to_pattern");
        }
        return this.field_path_to_pattern;
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
    * Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
    * Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
    * Returns a short description of the servlet.
    */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
