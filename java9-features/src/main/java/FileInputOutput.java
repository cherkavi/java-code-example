import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileInputOutput {
	
    public static void main(String[] args) throws IOException {
    	System.out.println(" >>> create temp file with predefined content <<< ");
    	Path tempFile = createTempFile("this is a \n content of the \n temp file ");
    	
    	System.out.println(" >>> read content of the file <<< ");
    	readContentToConsole(tempFile);
    	
    	System.out.println(" >>> close all resources into 'try' block <<< ");
    	Path tempFile2 = writeIntoAnotherTempfile(new FileInputStream(tempFile.toFile()));

    	System.out.println(" >>> transferTo content <<< ");
    	transferContent(tempFile2.toFile());	
    }    
    
    private static Path writeIntoAnotherTempfile(InputStream inputStream) throws IOException {
    	Path destination = Files.createTempFile("test", "null");
    	try(inputStream; OutputStream outputStream=new FileOutputStream(destination.toFile())){
    		inputStream.transferTo(outputStream);
    	}
    	return destination;
    }
    
    private static void readContentToConsole(Path tempFile) throws IOException {
    	Files.readAllLines(tempFile)
    		.forEach(System.out::println);
    }
    
    @SuppressWarnings("resource")
	private static void transferContent(File inputFile) throws FileNotFoundException, IOException {
    	new FileInputStream(inputFile)
    	.transferTo(new OutputStream() {
			@Override
			public void write(int arg0) throws IOException {
				System.out.print((char)arg0);
			}
    	});
    }

	private static Path createTempFile(String content) throws IOException {
    	Path tempFile = Files.createTempFile("test", "test");
    	Files.write(tempFile, content.getBytes());
    	return tempFile;
    }
	
}

