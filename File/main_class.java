import java.io.*;

class file{
	private File f=null;
	private FileInputStream fis=null;
	private FileOutputStream fos=null;
	private DataInputStream dis=null;
	private DataOutputStream dos=null;
	private InputStream is=null;
	private OutputStream os=null;
	file(String filename){
		try{
			f=new java.io.File(filename);
			if(!f.exists()){
				f.createNewFile();
			}
			fis=new FileInputStream(f);
			fos=new FileOutputStream(f);
			dis=new DataInputStream(fis);
			dos=new DataOutputStream(fos);
			is=(InputStream)fis;
			os=(OutputStream)fos;
		}
		catch(Exception e){
			System.out.println("Error in open file "+e.getMessage());
		}
	}
	public boolean write(String s) throws IOException{
		boolean result=false;
		if(dos!=null){
			dos.writeUTF(s);
			os.write(("\n"+s).getBytes());
			dos.flush();
			System.out.println("Writeln into file");
			result=true;
		}
		else {
			System.out.println("Error in write to file");
			result=false;
		}
		return result;
	}
	public String read() throws IOException{
		String result="";
		byte[] buffer=new byte[255];
		int read_count=0;
		if(dis.markSupported()){
			dis.reset();
		}
		//dis.reset();
		//is.reset();
		//fis.reset();
		while((read_count=dis.read(buffer))!=-1){
			result=result+(new String(buffer,0,read_count));
		}
		return result;
	}
}
public class main_class {
	
	public static void main(String args[]){
		try{
			file f=new file("c://1.txt");
			f.write("hello");
			System.out.println("read_form_file:"+f.read());
		}
		catch(Exception e){
			System.out.println("Error in work with file\n"+e.getMessage());
		}
		System.out.println("OK");
	}
}
