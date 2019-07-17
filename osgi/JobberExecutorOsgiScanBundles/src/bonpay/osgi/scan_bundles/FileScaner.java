package bonpay.osgi.scan_bundles;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/** объект, который сканирует каталог на возможное появление-исчезновение Bundle и соответственно устанавливаем/деинсталирует Bundle */
public class FileScaner extends Thread{
	/** путь, по которому происходит сканирование файлов на наличие новых или же удаление установленных */
	private String pathForScan;
	/** контекст OSGi с которым происходит общение */
	private BundleContext context;
	/** время засыпания потока, перед очередным поиском данных */
	private long delayScan;
	/** системный разделитель файлов */
	private String separator=System.getProperty("file.separator");
	/** каталог в котором будет производиться постоянный поиск объектов */
	private File directory;
	/** текущий список файлов, из которых прочитаны Bundle и проинсталлированы */
	private ArrayList<String> listOfFileName=new ArrayList<String>();
	/** соответствующий списку файлов список Bundles */
	private ArrayList<Bundle> listOfBundle=new ArrayList<Bundle>();
	/** логгер */
	private Logger logger=Logger.getLogger(this.getClass());
	
	private void debug(Object value){
		logger.debug(value);
		// System.out.println("Debug FileScaner#"+value);
	}

	private void error(Object value){
		logger.error(value);
		// System.out.println("Error FileScaner#"+value);
	}
	
	public FileScaner(String pathForScan, BundleContext context, long delayScan){
		this.pathForScan=pathForScan.trim();
		this.delayScan=delayScan;
		if(this.pathForScan.endsWith(separator)){
			// path is ends for separator
			this.pathForScan=this.pathForScan.substring(0,this.pathForScan.length()-separator.length());
		}
		this.directory=new File(this.pathForScan);
		if(this.directory.isDirectory()==false){
			error("Path is not directory:"+this.pathForScan);
			this.directory=null;
		}
		this.context=context;
		this.start();
	}
	
	@Override
	public void run(){
		while(this.isInterrupted()==false){
			if(this.directory!=null){
				// сканирование всех файлов в каталоге
				File[] files=this.directory.listFiles();
				// найти все новые файлы
				for(int counter=0;counter<files.length;counter++){
					if(files[counter].isFile()){
						String fileName=files[counter].getName();
						// String fileNameAbsolute=files[counter].getAbsolutePath();
						if(listOfFileName.indexOf(fileName)<0){
							// файл новый - попытаться установить 
							String pathToBundle=this.pathForScan+this.separator+fileName;
							try{
								debug("Attempt to install bundle: "+pathToBundle);
								//Bundle bundle=this.context.installBundle("file: "+fileNameAbsolute);
								// INFO not working : this.context.installBundle(fileName);this.context.installBundle("file: "+fileName);
								Bundle bundle=this.context.installBundle(fileName,new FileInputStream(pathToBundle));
								this.listOfBundle.add(bundle);
								this.listOfFileName.add(fileName);
								debug("Attempt to start bundle: "+pathToBundle);
								bundle.start();
								debug("start ok:"+bundle.getState());
							}catch(Exception ex){
								error("Bundle exception: "+ex.getMessage());
								ex.printStackTrace();
							}
						}
					}else{
						// file is directory 
					}
				}
				// найти все удаленные файлы
				int counter=this.listOfBundle.size()-1;
				while(counter>=0){
					// деинсталлировать удаленные
					if(this.getPositionIntoArray(files, this.listOfFileName.get(counter))<0){
						// файл удален - деинсталлировать
						try{
							this.listOfBundle.get(counter).uninstall();
							this.listOfBundle.remove(counter);
							String fileRemove=this.listOfFileName.remove(counter);
							debug("Bundle is Uninstalled: "+fileRemove);
						}catch(Exception ex){
							error("Uninstall files Exception: "+ex.getMessage());
						}
					}
					counter--;
				}
					
			}else{
				error(" FileScanner Error: ");
			}
			try{
				Thread.sleep(this.delayScan);
			}catch(InterruptedException ie){};
		}
	}
	
	/** получить позицию элемента в массиве по имени файла 
	 * @param files - файлы, в именах которых нужно проводить поиск 
	 * @param findName - искомое имя файла, которое нужно найти в files 
	 * @return позицию элемента, либо же -1
	 */
	private int getPositionIntoArray(File[] files, String findName){
		int returnValue=(-1);
		for(int counter=0;counter<files.length;counter++){
			if(files[counter].getName().equals(findName)){
				returnValue=counter;
				break;
			}
		}
		return returnValue;
	}
}
