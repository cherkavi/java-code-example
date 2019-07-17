import com.csvreader.*; 

public class Example {
	public static void main(String[] args){
		String path_to_file="c:\\temp.csv";
		write(path_to_file);
		read(path_to_file);
		
	}
	
	
	private static void write(String path_to_file){
		try{
			com.csvreader.CsvWriter writer=new com.csvreader.CsvWriter(path_to_file);
			writer.writeRecord(new String[]{"hello","#from","file\"CSV\""});
			writer.writeComment("hello");
			writer.writeRecord(new String[]{"hello2","from2","file2\"CSV\""});
			writer.flush();
			writer.close();
		}catch(Exception ex){
			System.out.println("Error write:"+ex.getMessage());
		}
	}
	
	private static void read(String path_to_file){
		System.out.println("READ");
		try{
			System.out.println("begin");
			com.csvreader.CsvReader reader=new com.csvreader.CsvReader(path_to_file);
			
/*			System.out.println("read header");
			reader.readHeaders();
			for(int counter=0;counter<reader.getHeaderCount();counter++){
				System.out.println(counter+":"+reader.getHeader(counter));
			}
*/			
			System.out.println("read data");
			reader.setComment('#');
			reader.setUseComments(true);
			while(reader.readRecord()){
				for(int counter=0;counter<reader.getColumnCount();counter++){
					System.out.print(reader.get(counter));
					System.out.print("  -|-  ");
				}
				System.out.println("");
			}
			reader.close();
			System.out.println("end");
		}catch(Exception ex){
			System.out.println("Error in read CSV:"+ex.getMessage());
		}
		
	}
}
