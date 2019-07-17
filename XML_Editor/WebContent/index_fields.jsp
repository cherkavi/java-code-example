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
<body body bgcolor="Silver" onload="select_user(document.getElementById('client_select'))">
	<%
		Logger.getRootLogger().setLevel(Level.DEBUG);
		
		if(!Logger.getRootLogger().getAllAppenders().hasMoreElements()){
			BasicConfigurator.configure();
		}else{
			Logger.getRootLogger().removeAllAppenders();
			BasicConfigurator.configure();
		}
	%>
	<script type='text/javascript' src='/XML_Editor/dwr/interface/ExchangeFields.js'></script>
	<script type='text/javascript' src='/XML_Editor/dwr/engine.js'></script>
	<script type="text/javascript">
		var name_client_task="client_task";
		var function_user_change="user_change";
		var function_task_change="task_change";
		var function_create_field="create_field";
		var unique_field_id=1000;
		
		<%-- по выбранному пользователю загрузить XML файл из сервера --%>
		function select_user(select_element){
			var element=document.getElementById("span_task_select");
			if(is_child_by_tag_exists(element,"SELECT")==false){
				// element Task not exitsts
				// create element
				var client_select=document.createElement("SELECT");
				client_select.setAttribute("id",name_client_task);
				client_select.setAttribute("onChange","task_change()");
				element.appendChild(client_select);
			}
			// fill new task
			var data=new Object();
			data.key=function_user_change;
			data.value=select_element.options[select_element.selectedIndex].value;
			ExchangeFields.sendXmlToServer(data,get_transport_from_server);
		}

		function task_change(){
			var data=new Object();
			data.key="task_change";
			var user_name=document.getElementById("client_select").options[document.getElementById("client_select").selectedIndex].value;
			var user_task=document.getElementById(name_client_task).options[document.getElementById(name_client_task).selectedIndex].value;
			data.value=user_name;
			data.value2=user_task;
			ExchangeFields.sendXmlToServer(data,get_transport_from_server);
		}
		
		function is_child_by_tag_exists(element,child_tag){
			var return_value=false;
			var nodes=element.childNodes;
			for(var counter=0;counter<nodes.length;counter++){
				if(nodes.item(counter).nodeName==child_tag){
					return_value=true;
				}
			}
			return return_value;
		}
		
		<%-- объект, который получает ответ от сервера --%>
		function get_transport_from_server(response){
			if(response){
				if(response.key){
					if(response.key=="xml_body"){
						document.getElementById(response.key).innerHTML=response.value;
					}
					if(response.key==function_user_change){
						document.getElementById(name_client_task).innerHTML=response.value;
						task_change();
					}
					if(response.key=="alert"){
						alert(response.value)
					}
					if(response.key==function_create_field){
						var table_fields=document.getElementById("fields");
						var child_element=document.createElement("TR");
						child_element.innerHTML=response.value;
						table_fields.appendChild(child_element);						
					}
				}
			}
		}
		
		<%-- получить указанные аттрибуты из элемента в виде текста --%>
		function getAllAttributes(element){
			var return_value="";
			/*
			var attrs=element.attributes;
			for(var counter=0;counter<attrs.length;counter++){
				return_value=return_value+"   "+attrs.item(counter).nodeName+"=\""+attrs.item(counter).nodeValue+"\"";
			}*/
			
			try{
				if(element.id){
					return_value=return_value+"id=\""+element.id+"\" ";
				}
			}catch(Exception){};

			try{
				if(element.checked){
					return_value=return_value+"checked=\""+element.checked+"\" ";	
				}
			}catch(Exception){};

			try{
				if(element.selected){
					return_value=return_value+"selected=\""+element.selected+"\" ";	
				}
			}catch(Exception){};

			try{
				if(element.value){
					return_value=return_value+"value=\""+element.value+"\"";
				}
			}catch(Exception){};
			
			return return_value;
		}

		<%-- получить HTML код по указанному элементу --%>
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

		<%-- удалить элемент из документа по уникальному ID--%>
		function delete_element(unique_id){
			var element=document.getElementById(unique_id);
			var parent=element.parentNode;
			if(parent){
				parent.removeChild(element);
			}
		}

		<%-- удалить родительский элемент указанного документа --%>
		function delete_parent_element(unique_id){
			var element=document.getElementById(unique_id);
			var parent=element.parentNode;
			if(parent){
				var parent_parent=parent.parentNode;
				parent_parent.removeChild(parent);
			}
		}
		<%-- создать элемент Fields --%>
		function add_field_element(){
			var data=new Object();
			data.key=function_create_field;
			unique_field_id=unique_field_id-1;
			data.value=unique_field_id;
			ExchangeFields.sendXmlToServer(data,get_transport_from_server);
		}

		<%-- сохранить отредактированный XML на сервере --%>
		function on_save_form(){
			var select_element=document.getElementById("xml_body");
			var data=new Object();
			data.key=select_element.id;
			//data.value=select_element.innerHTML;
			data.value=getHtmlText(select_element);
			var user_name=document.getElementById("client_select").options[document.getElementById("client_select").selectedIndex].value;
			var user_task=document.getElementById(name_client_task).options[document.getElementById(name_client_task).selectedIndex].value;
			data.value2=user_name;
			data.value3=user_task;	
			ExchangeFields.sendXmlToServer(data,get_transport_from_server);
		}
		
	</script>

	<span id="span_client_select">
		<%
			out.println("<SELECT ID=\"client_select\" onchange=\"select_user(this)\">");
			out.println(toHtmlConverter.convertMapToSelectBody(DBFunction.getUsers()));
			out.println("</SELECT>");
		%>
	</span>
	<br>
	<span id="span_task_select">
				
	</span>	
	<br>
	<hr>
	<span id="xml_body">
	</span>
	<hr>
	<input type="button" onclick="on_save_form()" value="Save">
</body>
</html>