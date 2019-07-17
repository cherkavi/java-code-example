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
			c.setDoInput(true);// установить возможность чтения=true
			c.setDoOutput(false);// установить возможность записи=false 
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
			BufferedInputStream bis=new BufferedInputStream(c.getInputStream());
			byte[] buffer=new byte[4096];
			int byte_count=0;
			while((byte_count=bis.read(buffer))!=-1){
				String s=new String(buffer,0,byte_count);
				System.out.println(s);
			}
			bis.close();
		}
		catch(Exception e){
			System.out.println(" Detected exception into method <urlconnection_to_console>"+e.getMessage());
		}
		return result;
	}
	static boolean socket_to_file(String path_to_socket,int port,String path_to_file){
		boolean result=false;
		Socket s=null;
		try{
			System.out.println("socket");
			s=new Socket(path_to_socket,port);
			InputStream is=s.getInputStream();
			OutputStream os=s.getOutputStream();
			BufferedInputStream bis=new BufferedInputStream(is);
			BufferedOutputStream bos=new BufferedOutputStream(os);
			byte[] buffer=new byte[4096];
			String out_string_2=new String("GET 1.php \n\n");
			bos.write(out_string_2.getBytes());
			bos.flush();
			int read_count=0;
			while((read_count=bis.read(buffer))!=-1){
				String out_string=new String(buffer,0,read_count);
				System.out.println(">>>"+out_string);
			}
			bis.close();
			bis.close();
			is.close();
			os.close();
		}
		catch(Exception e){
			System.out.println(" Error : "+e.getMessage());
		}
		return result;
	}
	static boolean String_to_file(String filename,String text){
		boolean result=false;
		try{
			FileOutputStream f=new FileOutputStream(filename); 		
			f.write(text.getBytes());
			f.flush();
			f.close();
			result=true;
		}
		catch(IOException e){
			System.out.println(" Error in open file:"+e.getMessage());
		}
		return result;
	}
	static boolean URL_to_file(String url_path,String filename){
		boolean result=false;
		URL site=null;
		FileOutputStream fos=null;
		InputStream is=null;
		try{
			site=new URL(url_path);
			is=site.openStream();
			fos=new FileOutputStream(filename);
			byte[] buffer=new byte[1024];
			int read_byte=0;
			while((read_byte=is.read(buffer))!=-1){
				fos.write(buffer,0,read_byte);
			}
		}
		catch(Exception e){
			
		}
		finally{
			if(fos!=null){
				try{
					fos.close();
				}
				catch(IOException e){
					System.out.println("Error in closing file "+filename);
				}
			}
			if(is!=null){
				try{
					is.close();
				}
				catch(IOException e){
					System.out.println("Error in close stream of site"+url_path);
				}
			}
		}
		return result;
	}
}
public class connect_to_internet {
	public static void main(String args[]){
		//net.url_to_file("http://127.0.0.1","c:\\3.txt");
		//net.file_to_file("c:\\3.txt", "c:\\4.txt");
		//net.file_to_console("c:\\4.txt");
		//net.urlconnection_to_console("http://127.0.0.1");
		//net.socket_to_file("http://127.0.0.1", 80, "c:\1.txt");
		net.String_to_file("c:\\1.txt", "Hello");
		net.URL_to_file("http://www.ya.ru", "c:\\ya_ru.html");
	}
}

