package bonpay.osgi.scan_bundles;

import java.io.FileInputStream;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class ScanBundlesActivator implements BundleActivator{
	static{
		// Logger activator 
		// String className="bonpay.osgi.scan_bundles";
		// Logger.getLogger(className).setLevel(Level.DEBUG);
		// Logger.getLogger(className).addAppender(new ConsoleAppender(new PatternLayout()));
	}
	
	private Logger logger=Logger.getLogger(this.getClass());
	private FileScaner fileScaner;
	
	private final static String propertiesPath="scan.path";
	private final static String propertiesDelay="scan.delay";
	@Override
	public void start(BundleContext context) throws Exception {
		Properties properties=new Properties();
		try{
			properties.load(new FileInputStream("ScanBundles.properties"));
			String path=properties.getProperty(propertiesPath);
			if(path==null){
				path="executors";
			};
			int delay=0;
			try{
				Integer.parseInt(properties.getProperty(propertiesDelay));
			}catch(Exception ex){};
			if(delay==0){
				delay=5000;
			}
			this.fileScaner=new FileScaner(path,context, delay);
		}catch(Exception ex){
			logger.error("ScanBundleActivator Exception:"+ex.getMessage());
			this.fileScaner=new FileScaner("executors",context, 5000);
		}
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		this.fileScaner.interrupt();
	}

}
