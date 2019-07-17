/*
 * Main.java
 *
 * Created on 17 травн€ 2008, 18:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package http_to_file;
import java.net.*;
import java.io.*;
/**
 *
 * @author Technik
 */
public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    public static void common(){
        String path_to_url="http://www.kpiservice.com.ua/category/tabled/4884/";
        String path_to_file="c:/out.data";
        try{
            System.out.println(">>> getting url");
            URL current_url=new URL(path_to_url);
            System.out.println(">>> getting InputStream");
            InputStream input_stream=current_url.openStream();
            System.out.println(">>> getting InputStreamReader");
            InputStreamReader input_stream_reader=new InputStreamReader(input_stream);
            System.out.println(">>> getting BufferedReader");
            BufferedReader buffered_reader=new BufferedReader(input_stream_reader);
            System.out.println(">>> getting File");
            FileWriter file_writer=new FileWriter(path_to_file);
            
            String line;
            System.out.println(">>>begin read from URL");
            while( (line=buffered_reader.readLine())!=null){
                file_writer.write(line+"\n");
                System.out.print(".");
            };
            System.out.println("\n>>>end write to file");
            file_writer.close();
            buffered_reader.close();
            System.out.println("end");
        }catch(Exception ex){
            System.out.println("Exception :"+ex.getMessage());
        }
    }
    public static void HTTP_GET(String path,String key,String value){
        try{
            System.out.println("получить соединение");
            HttpURLConnection connection=(HttpURLConnection)(new URL(path)).openConnection();
            System.out.println("установить параметры соединени€");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            System.out.println("ѕолучить поток ввода");
            OutputStream output_stream=connection.getOutputStream();
            System.out.println("«аписать параметры");
            //output_stream.write((URLEncoder.encode("action","UTF-8")+"="+URLEncoder.encode("loadInfo","UTF-8")).getBytes());
            //output_stream.write(("&"+URLEncoder.encode(key,"UTF-8")+"="+URLEncoder.encode(value,"UTF-8")).getBytes());
            output_stream.write((("action")+"="+"loadInfo").getBytes());
            output_stream.write(("&"+key.replaceAll(" ","%20")+"="+value.replaceAll(" ","%20")).getBytes());
            output_stream.flush();
            System.out.println("открыть соединение");
            connection.connect();
            System.out.println("получить input_stream"+connection.getResponseMessage());
            InputStream input_stream=connection.getInputStream();
            System.out.println("получить input_stream_reader");
            InputStreamReader input_stream_reader=new InputStreamReader(input_stream);
            System.out.println("получить buffered_reader");
            BufferedReader buffered_reader=new BufferedReader(input_stream_reader);
            String line="";
            System.out.println("READED:BEGIN");
            while((line=buffered_reader.readLine())!=null){
                System.out.println(line);
            }
            System.out.println("READED:END");
        }catch(Exception ex){
            System.out.println("GET Exception:"+ex.getMessage());
        }
    }
    public static void HTTP_to_File(String url_path,String key, String value,String file_path){
        try{
            System.out.println("получить соединение");
            HttpURLConnection connection=(HttpURLConnection)(new URL(url_path)).openConnection();
            System.out.println("установить параметры соединени€");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            System.out.println("ѕолучить поток ввода");
            OutputStream output_stream=connection.getOutputStream();
            System.out.println("«аписать параметры");
            //output_stream.write((URLEncoder.encode("action","UTF-8")+"="+URLEncoder.encode("loadInfo","UTF-8")).getBytes());
            //output_stream.write(("&"+URLEncoder.encode(key,"UTF-8")+"="+URLEncoder.encode(value,"UTF-8")).getBytes());
            //output_stream.write((("action")+"="+"loadInfo").getBytes());
            output_stream.write((key.replaceAll(" ","%20")+"="+value.replaceAll(" ","%20")).getBytes());
            //output_stream.flush();
            System.out.println("открыть соединение");
            connection.connect();
            System.out.println("получить input_stream"+connection.getResponseMessage());
            InputStream input_stream=connection.getInputStream();
            File file=new File(file_path);
            output_stream=new FileOutputStream(file);
            byte[] buffer=new byte[1000];
            int readed=0;
            while((readed=input_stream.read(buffer))>0){
                output_stream.write(buffer,0,readed);
            }
            output_stream.flush();
            input_stream.close();
            output_stream.close();
            System.out.println("READED:END");
        }catch(Exception ex){
            System.out.println("HTTP_to_File Exception:"+ex.getMessage());
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //HTTP_GET("http://www.kpiservice.com.ua//includes//load.ajax.php","id","   534");
        HTTP_to_File("http://www.kpiservice.com.ua//image_lab.php","pic_id","%20%20%202ZE","c:\\temp\\out.gif");
    }
    
}
