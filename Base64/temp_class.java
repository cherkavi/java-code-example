package package_main;


class base64{
	private String code_string="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
	private String get_binary_string(byte decode_char){
		String s1=Integer.toBinaryString((int)decode_char);
		while(s1.length()<8){
			s1="0"+s1;
		}
		return s1;
	}
	private String encode(byte char_from_string){
		String s1=this.get_binary_string(char_from_string).substring(0,6);
		String s2=this.get_binary_string(char_from_string).substring(6, 8)+"0000";
		String result=" ";
		//result=String.valueOf(this.code_string.charAt(Integer.parseInt(s1, 2)));
		result=""+this.code_string.charAt(Integer.parseInt(s1, 2));
		result=result+this.code_string.charAt(Integer.parseInt(s2, 2));
		return result+"==";
	}
	private String encode(byte char_1,byte char_2){
		String main=this.get_binary_string(char_1)+this.get_binary_string(char_2);
		String s1=main.substring(0, 6);
		String s2=main.substring(6,12);
		String s3=main.substring(12,16)+"00";
		String result="";
		result=""+this.code_string.charAt(Integer.parseInt(s1, 2));
		result=result+this.code_string.charAt(Integer.parseInt(s2, 2));
		result=result+this.code_string.charAt(Integer.parseInt(s3, 2));
		return result+"=";
	}
	private String encode(byte char_1,byte char_2,byte char_3){
		String main=this.get_binary_string(char_1)+this.get_binary_string(char_2)+this.get_binary_string(char_3);
		String s1=main.substring(0, 6);
		String s2=main.substring(6,12);
		String s3=main.substring(12,18);
		String s4=main.substring(18,24);
		String result="";
		result=""+this.code_string.charAt(Integer.parseInt(s1, 2));
		result=result+this.code_string.charAt(Integer.parseInt(s2, 2));
		result=result+this.code_string.charAt(Integer.parseInt(s3, 2));
		result=result+this.code_string.charAt(Integer.parseInt(s4, 2));
		return result;
	}
	public String encode(String s){
		String result="";
		int length_string=(int)(s.length()/3);
		System.out.println("length:"+length_string);
		for(int i=1;i<=length_string;i++){
			System.out.println( s.charAt((i-1)*3)+":"+s.charAt((i-1)*3+1)+":"+s.charAt((i-1)*3+2));
			result=result+this.encode((byte)s.charAt((i-1)*3),(byte)s.charAt((i-1)*3+1),(byte)s.charAt((i-1)*3+2));
		}
		if(s.length()!=(length_string*3)){
			if(s.length()==length_string*3+2){
				result=result+this.encode((byte)s.charAt(s.length()-2),(byte)s.charAt(s.length()-1));
			}
			else {
				result=result+this.encode((byte)s.charAt(s.length()-1));
			}
		}
		return result;
	}
	private boolean is_correct_symbols(String s){
		boolean result=true;
		iterator:for(int i=0;i<s.length();i++){
			if(this.code_string.indexOf(""+s.charAt(i))<0){
				result=false;
				break iterator;
			}
		}
		return result;
	}
	public String decode(String s){
		String temp_s;
		String result="";
		if(this.is_correct_symbols(s)){
			// data is correct
			while(s.charAt(s.length()-1)!='='){
				
				s=s.substring(1,s.length()-2);
			}
			System.out.println(">>>"+s);
		}
		else{
			// data is incorrect
			result="";
		}
		return result;
	}
}
public class main_class {
	public static void main(String[] args){
		try{
			base64 temp=new base64();
			System.out.println(">>>"+temp.encode("hel"));
			temp.decode("Ajjjj");
		}
		catch(Exception e){
			System.out.println("error:\n"+e.getMessage());
		}
	}
}
