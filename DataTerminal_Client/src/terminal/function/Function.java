package terminal.function;

import terminal.transfer.ExchangeServer;
import terminal.transfer.PercentReport;
import terminal.transport.Transport;
import java.util.zip.*;
import java.io.*;

/** данный класс является базовым для всех функций*/
public abstract class Function implements Runnable{
	/** полный путь к сервлету, который принимает входящие запросы и возвращает ответы */
	private String field_path_to_servlet="http://localhost:8080/DataTerminal_Server/TerminalServer";
	/** объект, которого нужно оповещать о ходе процесса выполнения задачи */
	private PercentReport field_percent_report=null;
	/** уникальное имя процесса для возврата значения в процент объекта выполнения */
	private String field_unique_id;
	/** Caption для данного действия*/
	private String field_caption;
	
	/** Logger DEBUG */
	protected void debug(String information){
		System.out.print(this.getClass().getName());
		System.out.print(" DEBUG: ");
	    System.out.println(information);
	}
	/** Logger ERROR */
	protected void error(String information){
		System.out.print(this.getClass().getName());
		System.out.print(" ERROR: ");
	    System.out.println(information);
	}
	
    /** заархивировать указаныый файл в поток байт
     * @param path_to_file путь к файлу
     * @return возвращает byte[] 
     */
    public byte[] getZipBytesFromFile(String path_to_file){
        byte[] return_value=null;
        try{
            File file_source=new File(path_to_file);
            if(file_source.exists()){
                BufferedInputStream origin = null;
                ByteArrayOutputStream dest=new ByteArrayOutputStream();
                ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
                //out.setMethod(ZipOutputStream.DEFLATED);
                byte data[] = new byte[2048];
                FileInputStream file = new FileInputStream(path_to_file);
                origin = new BufferedInputStream(file, 2048);
                // положить в Zip архив имя файла, или его полный путь 
                ZipEntry entry = new ZipEntry(file_source.getName());
                out.putNextEntry(entry);
                int count;
                while((count = origin.read(data, 0, 2048)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
                out.close();
                dest.close();
                return_value=dest.toByteArray();
            }else{
                debug("fileToArchiv: file not found");
            }
        }catch(Exception ex){
            error("getZipBytesFromFile:"+ex.getMessage());
        }
        return return_value;
    }
    
    /** разархивировать поток байт в виде Zip архива в файл на диске 
     * @param data массив байт формата Zip
     * @param path_to_file имя файла для сохранения из архива
     * @return true если файл успешно сохранен
     */
    public boolean getFileFromZipBytes(byte[] data,String path_to_file){
        boolean return_value=false;
        try{
            // получить поток 
            ByteArrayInputStream fis=new ByteArrayInputStream(data);
            // получить zip поток
            ZipInputStream zis=new ZipInputStream(new BufferedInputStream(fis));
            // получение очередной сущности
            ZipEntry entry;
            BufferedOutputStream dest;
            while((entry=zis.getNextEntry())!=null){
                if(entry.isDirectory()){
                    debug("this is directory:"+entry.getName());
                }else{
                    debug("this is file:"+entry.getName());
                    int count;
                    byte buffer[] = new byte[2048];
                    // write the files to the disk
                    FileOutputStream fos = new FileOutputStream(path_to_file);
                    dest = new BufferedOutputStream(fos, 2048);
                    while ((count = zis.read(buffer, 0, 2048)) != -1) {
                       dest.write(buffer, 0, count);
                    }
                    dest.flush();
                    dest.close();
                }
            }
            zis.close();
            return_value=true;
        }catch(Exception ex){
            error("getFileFromZipBytes Exception:"+ex.getMessage());
        }
        return return_value;
    }
	

	/**
	 * @param object_for_answer - объект, которому нужно передать результат выполнения
	 * @param object_for_process - объект, которому нужно передать процент выполнения задачи  (null - не оповещаем )
	 * @param unique_id - уникальное имя для данного процесса  ( для объекта object_for_process)
	 * @param caption - caption для отображения пользователю имени 
	 */
	public Function(PercentReport object_for_process,
			        String unique_id,
			        String caption){
		this(null,
			 object_for_process,
			 unique_id,
			 caption);
	}

	
	/**
	 * @param path_to_server полный путь к серверу
	 * @param object_for_answer - объект, которому нужно передать результат выполнения
	 * @param object_for_process - объект, которому нужно передать процент выполнения задачи  (null - не оповещаем )
	 * @param unique_id - уникальное имя для данного процесса ( для объекта object_for_process)  
	 */
	public Function(String path_to_server,
			        PercentReport object_for_process,
			        String unique_id,
			        String caption ){
		debug("Function: constructor");
		if((path_to_server!=null)&&(!path_to_server.equals(""))){
			this.field_path_to_servlet=path_to_server;
		}
		this.field_percent_report=object_for_process;
		this.field_unique_id=unique_id;
		this.field_caption=caption;
		
	}
	

	/** стартовать выполнение и указать: ожидать ли окончания выполнения или не ожидать 
	 * @param wait_for_done: <br>
	 * <li>true </li>- ожидать когда потом выполнится
	 * <li>false </li> не ожидать результатов выполнения 
	 * */
	public void start(boolean wait_for_done){
		try{
			Thread thread_main=new Thread(this);
			thread_main.start();
			if(wait_for_done==true){
				thread_main.join();
			}
		}catch(Exception ex){
			if(this.field_percent_report!=null){
				// вернуть значение (-1) - ошибка выполнения
				this.field_percent_report.getPercentReport(this.field_unique_id, this.field_caption, -1);
			}
		}
	}
	
	/** старотовать выполнение */
	public void start(){
		start(false);
	}
	
	
	
	/** вывести процент выполнения слушателю 
	 * @param value - процент, который нужно отобразить пользователю   
	 */
	protected void notifyReportPercent(int value){
		if(this.field_percent_report!=null){
			synchronized(this.field_percent_report){
				this.field_percent_report.getPercentReport(field_unique_id, this.field_caption, value);
			}
		}
	}
	
	/** вернуть полный путь к удаленному сервлету - серверу информационного обмена */
	public String getServletURL(){
		return this.field_path_to_servlet;
	}
	
	/** объект, который отвечает за отображение хода выполнения */
	public PercentReport getPercentReport(){
		return this.field_percent_report;
	}
	
	/** уникальный идентификатор для текущей задачи */
	public String getUniqueId(){
		return this.field_unique_id;
	}
	
	/** текст для пользователя (имя функции )*/
	public String getCaption(){
		return this.field_caption;
	}
	/** создать объект, который будет послан на сервер в качестве первого запроса */
	abstract protected Transport getFirstTransport();

	/**  первая посылка серверу пакета с информацией(getFirstTransport) о запроса действия (может включать дополнительные данные )) 
	 * @return возвращает ответ сервера на указанный запрос
	 * */
	protected Transport sendFirstTask(){
		debug("sendFirstTask");
		ExchangeServer server=new ExchangeServer(this.field_path_to_servlet);
		Transport request=getFirstTransport();
		debug("process"); 
		return server.Connect(request);
	}
}
