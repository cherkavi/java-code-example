package com.cherkashyn.vitalii.tools.visio.recognizer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;

public class FaceRecognizer {

	public static void main(String[] args) throws IOException{
		System.out.println("begin");

		new FaceRecognizer("/home/technik/soft/opencv/opencv-2.4.9").recognize("/home/technik/whores_images/", new File("/home/technik/whores.csv")); 
		
		System.out.println("-end-");
	}
	private final static String NEW_LINE="\n";
	
	private final String PATH;
	private final CascadeClassifier frontalFace;
	private final CascadeClassifier frontalDefault;
	private final CascadeClassifier frontalAlt;
	private final CascadeClassifier frontalAlt2;
	private final CascadeClassifier frontalAltTree;
	private final CascadeClassifier eyes;
	
	
	public FaceRecognizer(String pathToOpenCv){
		PATH=pathToOpenCv;
		loadLibrary();
		frontalFace = new CascadeClassifier(PATH+"/data/lbpcascades/lbpcascade_frontalface.xml");
		frontalDefault = new CascadeClassifier(PATH+"/data/haarcascades/haarcascade_frontalface_default.xml");
		frontalAlt = new CascadeClassifier(PATH+"/data/haarcascades/haarcascade_frontalface_alt.xml");
		frontalAlt2 = new CascadeClassifier(PATH+"/data/haarcascades/haarcascade_frontalface_alt2.xml");
		frontalAltTree = new CascadeClassifier(PATH+"/data/haarcascades/haarcascade_frontalface_alt_tree.xml");
		eyes = new CascadeClassifier(PATH+"/data/haarcascades/haarcascade_eye.xml");
		
	}
	
	
	private void recognize(String path, File outputFile) throws IOException {
		System.out.println("\nRunning DetectFaceDemo");
		BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)));
		File file=new File(path);
		// header
		StringBuilder resultString=new StringBuilder();
		resultString.append("FileName");
		resultString.append(" ; frontalFace");
		resultString.append(" ; frontalDefault");
		resultString.append(" ; frontalAlt");
		resultString.append(" ; frontalAlt2");
		resultString.append(" ; frontalAltTree");
		resultString.append(" ; eyes");
		resultString.append(" ; ");
		writer.write(resultString.toString());
		writer.write(NEW_LINE);
		writer.flush();
		
		for(String eachFile: file.list()){
		    // lines
			Mat image = Highgui.imread(path+eachFile);
			resultString=new StringBuilder();
			resultString.append(eachFile);
			resultString.append(" ; ");
			resultString.append(result(frontalFace, image));
			resultString.append(" ; ");
			resultString.append(result(frontalDefault, image));
			resultString.append(" ; ");
			resultString.append(result(frontalAlt, image));
			resultString.append(" ; ");
			resultString.append(result(frontalAlt2, image));
			resultString.append(" ; ");
			resultString.append(result(frontalAltTree, image));
			resultString.append(" ; ");
			resultString.append(result(eyes, image));
			resultString.append(" ; ");

			writer.write(resultString.toString());
			writer.write(NEW_LINE);
			writer.flush();
		}
		
		writer.close();
	}
	
	private static int result(CascadeClassifier classifier, Mat image){
	    MatOfRect result = new MatOfRect();
	    classifier.detectMultiScale(image, result);
	    return result.toArray().length;
	}

	

	private void loadLibrary(){
		// System.setProperty("ocvLibDir","/home/technik/soft/opencv/opencv-2.4.9/lib");
		// System.setProperty("java.library.path", System.getProperty("java.library.path")+":/home/technik/soft/opencv/opencv-2.4.9/lib");
		// System.setProperty("java.library.path","/home/technik/soft/opencv/opencv-2.4.9/lib");
		// System.out.println("Library: "+System.getProperty("java.library.path"));
		// System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		// System.loadLibrary("opencv_java249");
		System.load(PATH+"/lib/lib"+Core.NATIVE_LIBRARY_NAME+".so");
		
	}
}
