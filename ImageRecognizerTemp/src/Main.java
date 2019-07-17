import javaanpr.configurator.Configurator;
import javaanpr.imageanalysis.CarSnapshot;
import javaanpr.intelligence.Intelligence;


/** класс, который демонстирует возможность чтения/распознания номерных знаков с автомобилей */
public class Main {
	public static void main(String[] args){
		String pathToFile="D:\\java_lib\\ImageRecognize\\snapshots\\test_001.jpg";
		String pathToDirectory="D:\\eclipse_workspace\\ImageRecognizerTemp\\resources";
		if(args.length>0){
			if(args.length==1){
				pathToFile=args[0];
			}else{
				pathToFile=args[1];
				pathToDirectory=args[0];
			}
			Configurator configurator = new Configurator(pathToDirectory);
			try{
				Intelligence systemLogic=new Intelligence(false,configurator);
				CarSnapshot car=new CarSnapshot(pathToFile);
		        try {
		        	long time=System.currentTimeMillis();
		        	long time2=0;
		        	System.out.println(systemLogic.recognize(car));
		        	time2=System.currentTimeMillis();
		        	System.out.println("TimeMillis:"+(time2-time));
		        	//System.out.println("Recongnize:"+systemLogic.recognize(car));
		        } catch (Exception ex) {
		        	System.out.println("Recognize Exception: "+ex.getMessage());
		        }
			}catch(Exception ex){
				System.out.println("Exception: "+ex.getMessage());
			}
		}else{
			System.out.println("Program launch: \n 1:   java -jar ImageRecongnizer <path To Image> \n 2:   java -jar ImageRecongnizer <path to resource directory> <path To Image>");
		}
	}
}
