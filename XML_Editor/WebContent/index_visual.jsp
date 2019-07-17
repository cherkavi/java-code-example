<%@ page language="java" 
         contentType="text/html; charset=UTF-8"
    	 pageEncoding="UTF-8" 
    	 import="bc.data_terminal.editor.database.*,bc.data_terminal.editor.utility.*,org.apache.log4j.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.apache.log4j.BasicConfigurator"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body bgcolor="Silver" onload="select_user(document.getElementById('client_id'))">
	<%
		BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(Level.DEBUG);
	%>
	<script type='text/javascript' src='/XML_Editor/dwr/interface/ExchangeVisual.js'></script>
	<script type='text/javascript' src='/XML_Editor/dwr/engine.js'></script>
	<script type="text/javascript">

		<%-- по выбранному пользователю загрузить XML файл из сервера --%>
		function select_user(select_element){
			var data=new Object();
			data.key=select_element.id;
			data.value=select_element.options[select_element.selectedIndex].value;
			ExchangeVisual.sendXmlToServer(data,get_transport_from_server);
			//document.getElementById("button_save");
		}

		<%-- объект, который получает ответ от сервера --%>
		function get_transport_from_server(response){
			if(response){
				if(response.key){
					if(response.key=="xml_body"){
						document.getElementById(response.key).innerHTML=response.value;
					}
					if(response.key=="alert"){
						alert(response.value)
					}
				}
			}
		}

		
		<%-- установить выделенность для подэлементов --%>
		function span_set_checked(span_element,value){
			var nodes=span_element.childNodes;
			for(var counter=0;counter<nodes.length;counter++){
				var node=nodes.item(counter);
				if((node)&&(node.tagName)){
					if(node.tagName=="INPUT"){
						node.checked=value;
					}
					if(node.tagName=="SPAN"){
						span_set_checked(node,value);
					}
				}
			}
		}

		<%-- проверить все подэлементы из указанного на снятие выделенности и снять эту выделенность с заголовка группы --%>
		function check_brother(element){
			var parent_node=element.parentNode;
			var brother_elements=parent_node.childNodes;
			var input_counter=0;
			var check_counter=0;
			for(var counter=0;counter<brother_elements.length;counter++){
				var node=brother_elements.item(counter);
				if((node)&&(node.tagName)){
					var node_tag_name=node.nodeName;
					if(node_tag_name == "INPUT"){
						if(node.checked){
							check_counter++;
						}else{
							//
						}
						input_counter++;
					}
				}
			}
			if((input_counter>0)&&(check_counter==0)){
				set_input_brother_checked(parent_node,false);
			}else{
				set_input_brother_checked(parent_node,true);
			}
		} 

		<%-- функция задания значения для вышестоящего INPUT (на этом же уровне) элемента --%>
		function set_input_brother_checked(element, value){
			var parent_element=element.parentNode;
			if(parent_element){
				var nodes=parent_element.childNodes;
				var input_element;
				for(var counter=0;counter<nodes.length;counter++){
					if(nodes.item(counter).nodeName=="INPUT"){
						input_element=nodes.item(counter);
					}
					if(nodes.item(counter)==element){
						if(input_element){
							input_element.checked=value;
							break;
						}
					}
				}
			}
		}
		
		<%-- установить для группы выделенность --%>
		function check_root(element){
			check_brother(element);
			var span_body=document.getElementById("body_"+element.id);
			if(element.checked){
				span_set_checked(span_body,true);
			}else{
				span_set_checked(span_body,false);
			}
		}

		function getAllAttributes(element){
			var return_value=" ";
			/*
			var attrs=element.attributes;
			for(var counter=0;counter<attrs.length;counter++){
				return_value=return_value+"   "+attrs.item(counter).nodeName+"=\""+attrs.item(counter).nodeValue+"\"";
			}*/
			
			try{
				return_value=return_value+"id=\""+element.id+"\" ";
			}catch(Exception){};
			try{
				return_value=return_value+"checked=\""+element.checked+"\" ";	
			}catch(Exception){};
			try{
				return_value=return_value+"task_id=\""+element.getAttribute("task_id")+"\" ";	
			}catch(Exception){};
			
			return return_value;
		}

		function getHtmlText(element){
			var return_value="";
			if(element){
				try{
					return_value=return_value+"<"+element.localName+"   "+getAllAttributes(element)+">";
					if(element.hasChildNodes()){
						var nodes=element.childNodes;
						for(var counter=0;counter<nodes.length;counter++){
							return_value=return_value+getHtmlText(nodes.item(counter));
						}					
					}
					return_value=return_value+"</"+element.localName+">";
				}catch(exception){
				}
			}
			return return_value;
		}

		<%-- сохранить отредактированный XML на сервере --%>
		function on_save_form(){
			var select_element=document.getElementById("xml_body");
			var data=new Object();
			data.key=select_element.id;
			//data.value=select_element.innerHTML;
			var user_id=user_element=document.getElementById("client_id");
			data.value=user_id.options[user_id.selectedIndex].value;
			data.value2=getHtmlText(select_element);
			ExchangeVisual.sendXmlToServer(data,get_transport_from_server);
		}
		
	</script>
	<span id="client_select">
		<%
			out.println("<SELECT ID=\"client_id\" onchange=\"select_user(this)\">");
			out.println(toHtmlConverter.convertMapToSelectBody(DBFunction.getUsers()));
			out.println("</SELECT>");
		%>
	</span>
	
	<br>
	<hr>
	<span id="xml_body">
	</span>
	<hr>
	<input type="button" id="button_save" onclick="on_save_form()" value="Save">
</body>
</html>