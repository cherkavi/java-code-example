/*
 * Main.java
 *
 * Created on 29 лютого 2008, 14:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package mail_recieve_and_send;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 *
 * @author Technik
 */
public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    public static void send(){
	
        boolean_package result_send_mail=new boolean_package(false);
        string_package trace=new string_package("");
        String source="technik7job@rambler.ru";
        String destination="technik7@rambler.ru";
        String temp_value=String.valueOf((new java.util.Random()).nextLong());
        StringBuffer header=new StringBuffer(); 
        Date date=new Date();
        SimpleDateFormat simple_date_format=new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z",Locale.US);
        header.append("Date:"+simple_date_format.format(date)+"\r\n");
        header.append("From: Technik <"+source+">\r\n");
        header.append("X-Mailer: The Bat! (v2.01) Educational");
        header.append("Reply-To: "+source+"\r\n");
        header.append("X-Priority: 1 (Highest)\r\n");
        simple_date_format=new SimpleDateFormat("yyyyMMddHHmmss",Locale.US);
        header.append("Message-ID: <"+temp_value+"."+simple_date_format.format(date)+"@rambler.ru>\r\n");
        header.append("To: "+destination+"\r\n");
        header.append("MIME-Version: 1.0\r\n");
        header.append("Content-Type: text/plain; charset=us-ascii\r\n");
        header.append("Content-Transfer-Encoding: 7bit\r\n");
        //System.out.println(header.toString());

        new send_mail("technik7_job",
                       "sokol7",
                       "smtp.online.ua", 
                       25,
                       "technik7_job@online.ua",
                       "technik7@rambler.ru", 
                       "hello from technik",
                       result_send_mail,
                       trace);
	if(result_send_mail.getValue()){
            //System.out.println("mail sended:\n"+trace.getValue());
            System.out.println("mail sended:\n");
        }
	else{
             System.out.println("error sended mail");
         }
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        send();
    }
}
