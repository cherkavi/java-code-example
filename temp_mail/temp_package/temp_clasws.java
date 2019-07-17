package temp_package;
   class utility{
	   public static String convert(char ch){
		   String result=ch+"";
		   if(ch=='¸')result="yo"+"";
		   if(ch=='º')result="ye"+"";
		   if(ch=='¿')result="yi"+"";
		   if(ch=='À')result="A"+"";
		   if(ch=='Á')result="B"+"";
		   if(ch=='Â')result="V"+"";
		   if(ch=='Ã')result="G"+"";
		   if(ch=='Ä')result="D"+"";
		   if(ch=='Å')result="E"+"";
		   if(ch=='Æ')result="G"+"";
		   if(ch=='Ç')result="Z"+"";
		   if(ch=='È')result="I"+"";
		   if(ch=='É')result="Y"+"";
		   if(ch=='Ê')result="K"+"";
		   if(ch=='Ë')result="L"+"";
		   if(ch=='Ì')result="M"+"";
		   if(ch=='Í')result="N"+"";
		   if(ch=='Î')result="O"+"";
		   if(ch=='Ï')result="P"+"";
		   if(ch=='Ð')result="R"+"";
		   if(ch=='Ñ')result="S"+"";
		   if(ch=='Ò')result="T"+"";
		   if(ch=='Ó')result="U"+"";
		   if(ch=='Ô')result="F"+"";
		   if(ch=='Õ')result="H"+"";
		   if(ch=='Ö')result="C"+"";
		   if(ch=='×')result="CH"+"";
		   if(ch=='Ø')result="SH"+"";
		   if(ch=='Ù')result="SCH"+"";
		   if(ch=='Ú')result=""+"";
		   if(ch=='Û')result="Y"+"";
		   if(ch=='Ü')result=""+"";
		   if(ch=='Ý')result="E"+"";
		   if(ch=='Þ')result="YU"+"";
		   if(ch=='ß')result="YA"+"";
		   if(ch=='à')result="a"+"";
		   if(ch=='á')result="b"+"";
		   if(ch=='â')result="v"+"";
		   if(ch=='ã')result="g"+"";
		   if(ch=='ä')result="d"+"";
		   if(ch=='å')result="e"+"";
		   if(ch=='æ')result="g"+"";
		   if(ch=='ç')result="z"+"";
		   if(ch=='è')result="i"+"";
		   if(ch=='é')result="y"+"";
		   if(ch=='ê')result="k"+"";
		   if(ch=='ë')result="l"+"";
		   if(ch=='ì')result="m"+"";
		   if(ch=='í')result="n"+"";
		   if(ch=='î')result="o"+"";
		   if(ch=='ï')result="p"+"";
		   if(ch=='ð')result="r"+"";
		   if(ch=='ñ')result="s"+"";
		   if(ch=='ò')result="t"+"";
		   if(ch=='ó')result="u"+"";
		   if(ch=='ô')result="f"+"";
		   if(ch=='õ')result="h"+"";
		   if(ch=='ö')result="c"+"";
		   if(ch=='÷')result="ch"+"";
		   if(ch=='ø')result="sh"+"";
		   if(ch=='ù')result="sch"+"";
		   if(ch=='ú')result=""+"";
		   if(ch=='û')result="y"+"";
		   if(ch=='ü')result=""+"";
		   if(ch=='ý')result="e"+"";
		   if(ch=='þ')result="yu"+"";
		   if(ch=='ÿ')result="ya"+"";
		   if(ch=='¥')result="Ga"+"";
		   if(ch=='¨')result="E"+"";
		   if(ch=='ª')result="E"+"";
		   if(ch=='¯')result="YI"+"";
		   if(ch=='³')result="yi"+"";
		   if(ch=='´')result="ga"+"";
		   if(ch=='¥')result="Ga"+"";
		   if(ch=='¨')result="E"+"";
		   if(ch=='ª')result="E"+"";
		   return result;
	   }
	   public static String convert_to_asc(String s){
		   String result="";
		   for(int i=0;i<s.length();i++){
			   result=result+convert(s.charAt(i));
		   }
		   return result;
	   }
   }
   
   public class temp_clasws {
   public static void main(String args[]){
	   
	   System.out.println(utility.convert_to_asc("àáâãäåé"));
   }
}
