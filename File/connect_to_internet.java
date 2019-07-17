
import java.net.*;
import java.io.*;
import java.util.Date;

class net{
	static boolean url_to_file(String url_path,String filename){
		InputStream in=null;
		OutputStream os=null;
		boolean result=false;
		// создать объекты для получения потоков
		try{
			URL url=new URL(url_path);
			in=url.openStream();
			os=new FileOutputStream(filename);
			byte[] buffer=new byte[4096];
			int read_bytes=0;
			while((read_bytes=in.read(buffer))!=-1){
				os.write(buffer, 0, read_bytes);
			}
			result=true;
		}
		catch(Exception e){
			System.out.println("Detected Error:"+e.getMessage());
		}
		finally{
			try{
				if(in!=null){
					in.close();
				}
				if(os!=null){
					os.close();
				}
			}
			catch(Exception e){
				System.out.println("Detected Error when closing Input and Output Stream");
			}
		}
		return result;
	}
	static boolean file_to_file(String source,String destination){
		InputStream source_stream=null;
		OutputStream destination_stream=null;
		boolean result=false;
		try{
			source_stream=new FileInputStream(source);
			destination_stream=new FileOutputStream(destination);
			byte[] buffer=new byte[4096];
			int read_bytes=0;
			while((read_bytes=source_stream.read(buffer))!=-1){
				destination_stream.write(buffer,0,read_bytes);
			}
			result=true;
		}
		catch(Exception e){
			System.out.println("Detected Error: "+e.getMessage());
			result=false;
		}
		finally{
			try{
				if(source_stream!=null){
					source_stream.close();
				}
				if(destination_stream!=null){
					destination_stream.close();
				}
			}
			catch(Exception e){
				System.out.println("Error during closing");
			}
		}
		return result;
	}
	static boolean file_to_console(String source){
		boolean result=false;
		FileInputStream fis=null;
		FileOutputStream fos=null;
		try{
			fis=new FileInputStream(source);
			fos=new FileOutputStream(source+"_temp");
			
			byte[] buffer=new byte[4096];
			int quentity_byte=0;
			while((quentity_byte=fis.read(buffer))!=-1){
				String s=new String(buffer,0,quentity_byte);
				System.out.println(s);
			}
			result=true;
		}
		catch(Exception e){
			System.out.println("Detected Error into method <file_to_console> ");
			result=false;
		}
		finally{
			try{
				if(fis!=null){
					fis.close();
				};
				if(fos!=null){
					fos.close();
				}
			}
			catch(Exception e){
				System.out.println("Detected Error when closing files");
			}
		}
		return result;
	}
	
	static boolean urlconnection_to_console(String url_path){
		boolean result=false;
		URLConnection c=null;
		try{
			c=(new URL(url_path)).openConnection();
			c.connect();
			System.out.println("Тип содержимого: "+c.getContentType());
			System.out.println("Кодировка: "+c.getContentEncoding());
			System.out.println("Размер содержимого: "+c.getContentLength());
			System.out.println("Дата: "+(new Date(c.getDate())));
			System.out.println("Дата последнего изменения: "+(new Date(c.getLastModified())));
			System.out.println("Срок годности: "+(new Date(c.getExpiration())));
			// если это HTTP соединение
			if(c instanceof HttpURLConnection){
				HttpURLConnection h=(HttpURLConnection)c;
				System.out.println("Метод запроса: "+h.getRequestMethod());
				System.out.println("Сообщение ответа: "+h.getResponseMessage());
				System.out.println("Код ответа: "+h.getResponseCode());
			}
		}
		catch(Exception e){
			System.out.println(" Detected exception into method <urlconnection_to_console>");
		}
		
		return result;
	}
}
public class connect_to_internet {
	public static void main(String args[]){
		net.url_to_file("http://127.0.0.1","c:\\3.txt");
		net.file_to_file("c:\\3.txt", "c:\\4.txt");
		net.file_to_console("c:\\4.txt");
		net.urlconnection_to_console("http://127.0.0.1");
	}
}

