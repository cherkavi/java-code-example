package gui;

import java.util.ArrayList;



public class EnterPoint {
	public static void main(String[] args){
		try{
			ArrayList<String> forReplace=new ArrayList<String>();
			forReplace.add("<b><i>for replace</i></b>");
			
			(new HtmlReplacer("c:\\temp.html",
					          "WINDOWS-1251",
					          "value",
					          forReplace
			                  )).printResultToWriter(System.out);
		}catch(Exception ex){
			System.out.println("Exception:"+ex.getMessage());
		}
		
	}
}


/*
<html>
	<head>
		<title> this is head</title>
	</head>
	<body>
		<b>this is bold text</b>
		<br>
		<value/>
		<br>
		<br>
		<h1> this is text again </h1>
		<br>
		<value/>
		<br>
	</body>
</html>

*/