<%@ page language="java" import="java.util.Date,java.io.*,translit.*,java.net.URLDecoder" contentType="text/html; charset=WINDOWS-1251"
    pageEncoding="WINDOWS-1251"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>
<body>
	<table width=600>
		<!--  source -->
		<tr>
			<td>введите русский текст:</td>
		</tr>
		<tr>
			<td>
				<form action="/Transliteration/index.jsp">
					<table>
						<tr>
							<td><textarea rows="10" cols="40" name="text_source"><% if(request.getParameter("text_source")!=null){
								String value=request.getParameter("text_source");
								try{
									System.out.println(">>>"+URLDecoder.decode(value,"WINDOWS-1251"));	
								}catch(Exception ex){
									System.out.println("Exception:"+ex.getMessage());
								}
								
								
						        BufferedReader reader=new BufferedReader(new InputStreamReader(new ByteArrayInputStream(value.getBytes()),"WINDOWS-1251"));
						        value=reader.readLine();
								System.out.println(value);	
								out.println(value);
								};%></textarea>
							</td>
						</tr>
						<tr>
							<td>
								<input type="submit" value="translit" /> 
							</td>
						</tr>
					</table>
				</form>
				
			</td>
		</tr>
		<!--  destination -->
		<tr>
			<td>translit:</td>
		</tr>
		<tr>
			<td>
				<textarea rows="10" cols="40"><%
					if(request.getParameter("text_source")!=null){
						System.out.println(request.getParameter("text_source"));
							out.println(translit.Translit.toTranslit(request.getParameter("text_source")));
						   };
				%></textarea>
			</td>
		</tr>
	</table>


</body>
</html>