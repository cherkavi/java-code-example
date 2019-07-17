import java.rmi.*;
public class list_naming{
	public static void main(String[] args){
	try{
	    String port="";	
	    if(args.length>=1){
	    	System.out.println("print args:"+args[0]);
		port=args[0];
	    }else{
		port="2001";
	    }
	    System.out.println("getting naming");
	    // получить на данном хосте и на данном порту данные обо всех запущенных сервисах
	    String[] naming_list=java.rmi.Naming.list("//localhost:"+port);
            for(int i=0;i<naming_list.length;i++){
                System.out.println(i+":"+naming_list[i]);
            }
        }catch(Exception e){
            System.out.println("exception: "+e.getMessage());
        }
	}
			
}