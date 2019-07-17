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
<body body bgcolor="Silver" >
	<%
		Logger.getRootLogger().setLevel(Level.DEBUG);
		
		if(!Logger.getRootLogger().getAllAppenders().hasMoreElements()){
			BasicConfigurator.configure();
		}else{
			Logger.getRootLogger().removeAllAppenders();
			BasicConfigurator.configure();
		}
	%>
	<script type='text/javascript' src='/XML_Editor/dwr/interface/ExchangeFieldsPattern.js'></script>
	<script type='text/javascript' src='/XML_Editor/dwr/engine.js'></script>
	<script type="text/javascript">
		var function_pattern_copy="function_pattern_copy";
		var function_pattern_open="function_pattern_open";
		var function_pattern_create="function_pattern_create";
		var function_save_pattern="function_save_pattern";
		var function_delete_pattern="function_delete_pattern";
		var function_load_task="function_load_task";
		
		<%-- возвращает true, если указаныый элемент содержит указанного потомка --%>
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
					<%-- загрузить данные в Task_Select --%>
					if(response.key==function_load_task){
						var element=document.getElementById("task_select");
						element.innerHTML=response.value;
					}
					<%-- копирование шаблона --%>
					if(response.key==function_pattern_copy){
						var element=document.getElementById("pattern_select");
						element.innerHTML=response.value;
						document.getElementById("button_copy_pattern").setAttribute("disabled","disabled");
						document.getElementById("text_copy_pattern").setAttribute("disabled","disabled");
						document.getElementById("button_delete_pattern").setAttribute("disabled","disabled");
						document.getElementById("button_open_pattern").setAttribute("value","create");
						document.getElementById("button_open_pattern").setAttribute("iscreate","iscreate");
						document.getElementById("create_caption").removeAttribute("disabled");
						document.getElementById("create_text").removeAttribute("disabled");
					}
					<%-- открытие шаблона --%>
					if(response.key==function_pattern_open){
						document.getElementById("xml_body").innerHTML=response.value;		
						document.getElementById("task_select").innerHTML=response.value2;
						alert("OK");				
					}
					<%-- создание шаблона --%>
					if(response.key==function_pattern_create){
						document.getElementById("xml_body").innerHTML=response.value;
						document.getElementById("task_select").innerHTML=response.value2;
						alert("OK");
					}
					<%-- удаление шаблона --%>
					if(response.key==function_delete_pattern){
						var element=document.getElementById("pattern_select");
						element.innerHTML=response.value;
						document.getElementById("button_copy_pattern").setAttribute("disabled","disabled");
						document.getElementById("text_copy_pattern").setAttribute("disabled","disabled");
						document.getElementById("button_delete_pattern").setAttribute("disabled","disabled");
						document.getElementById("button_open_pattern").setAttribute("value","create");
						document.getElementById("button_open_pattern").setAttribute("iscreate","iscreate");
						document.getElementById("create_caption").removeAttribute("disabled");
						document.getElementById("create_text").removeAttribute("disabled");
						document.getElementById("xml_body").innerHTML="";
						load_task();
						alert("deleted");
					}
					<%-- сохранение шаблона --%>
					if(response.key==function_save_pattern){
						var element=document.getElementById("pattern_select");
						element.innerHTML=response.value;
						document.getElementById("button_copy_pattern").setAttribute("disabled","disabled");
						document.getElementById("text_copy_pattern").setAttribute("disabled","disabled");
						document.getElementById("button_delete_pattern").setAttribute("disabled","disabled");
						document.getElementById("button_open_pattern").setAttribute("value","create");
						document.getElementById("button_open_pattern").setAttribute("iscreate","iscreate");
						document.getElementById("create_caption").removeAttribute("disabled");
						document.getElementById("create_text").removeAttribute("disabled");
						document.getElementById("xml_body").innerHTML="";
						load_task();
					}
					if(response.key=="alert"){
						alert(response.value)
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

		function trim(stringToTrim) {
			return stringToTrim.replace(/^\s+|\s+$/g,"");
		}
		
		<%-- сохранить отредактированный XML на сервере --%>
		function on_save_form(){
			var name_of_pattern=document.getElementById("create_text").value;
			name_of_pattern=trim(name_of_pattern);
			// check for empty string
			if((name_of_pattern=="")&&(document.getElementById("button_open_pattern").getAttribute("iscreate")=="")){
				alert("input name for save new pattern");				
			}else{
				// check for repeat value, when create pattern
				if(isOptionPresent("pattern_select",name_of_pattern)&&(document.getElementById("button_open_pattern").getAttribute("iscreate")=="")){
					alert("change pattern name - it present into list of pattern");
				}else{
					// send to Server
					var data_element=document.getElementById("xml_body");
					var data=new Object();
					data.key=function_save_pattern;
					var is_create=document.getElementById("button_open_pattern").getAttribute("iscreate");
					if((is_create=="iscreate")||(is_create="")){
						// edit exists
						data.value="iscreate";
						data.value2=document.getElementById("create_text").value;
						data.value3=getHtmlText(data_element);
						var task_select=document.getElementById("task_select");
						data.value4=task_select.options[task_select.selectedIndex].value
					}else{
						// create new
						data.value="edit";
						var select_element=document.getElementById("pattern_select");
						data.value2=select_element.options[select_element.selectedIndex].value;
						data.value3=getHtmlText(data_element);
						var task_select=document.getElementById("task_select");
						data.value4=task_select.options[task_select.selectedIndex].value
					}
					ExchangeFieldsPattern.sendXmlToServer(data,get_transport_from_server);
				}
			}
		}


		function isOptionPresent(select_element_id, value_for_compare){
			var return_value=false;
			var select=document.getElementById(select_element_id);
			var option_list=select.getElementsByTagName("OPTION");
			for(var counter=0;counter<option_list.length;counter++){
				if(option_list.item(counter).getAttribute("value")==value_for_compare){
					return_value=true;
					break;
				}
			}
			return return_value;
		}
		
		function load_task(){
			var data=new Object();
			data.key=function_load_task;
			data.value="";
			ExchangeFieldsPattern.sendXmlToServer(data,get_transport_from_server);
		}
		
		<%-- по выбранному пользователю загрузить XML файл из сервера --%>
		function pattern_select(){
			var select_element=document.getElementById("pattern_select");
			if(select_element.options[select_element.selectedIndex].value==""){
				// only create pattern
				document.getElementById("button_copy_pattern").setAttribute("disabled","disabled");
				document.getElementById("text_copy_pattern").setAttribute("disabled","disabled");
				document.getElementById("button_delete_pattern").setAttribute("disabled","disabled");
				document.getElementById("button_open_pattern").setAttribute("value","create");
				document.getElementById("button_open_pattern").setAttribute("iscreate","iscreate");
				document.getElementById("create_caption").removeAttribute("disabled");
				document.getElementById("create_text").removeAttribute("disabled");
				load_task();
			}else{
				// open or copy pattern
				document.getElementById("button_copy_pattern").removeAttribute("disabled");
				document.getElementById("text_copy_pattern").removeAttribute("disabled");
				document.getElementById("button_delete_pattern").removeAttribute("disabled");
				document.getElementById("button_open_pattern").setAttribute("value","open");
				document.getElementById("button_open_pattern").removeAttribute("iscreate");
				document.getElementById("create_caption").setAttribute("disabled","disabled");
				document.getElementById("create_text").setAttribute("disabled","disabled");
				open_pattern();
			}
		}
		
		<%-- реакция на нажатие кнопки копирования шаблона --%>
		function copy_pattern(){
			var select_element=document.getElementById("pattern_select");
			var data=new Object();
			data.key=function_pattern_copy;
			data.value=select_element.options[select_element.selectedIndex].value;
			data.value2=document.getElementById("text_copy_pattern").value;
			ExchangeFieldsPattern.sendXmlToServer(data,get_transport_from_server);
		}

		function delete_pattern(){
			var select_element=document.getElementById("pattern_select");
			var data=new Object();
			data.key=function_delete_pattern;
			data.value=select_element.options[select_element.selectedIndex].value;
			ExchangeFieldsPattern.sendXmlToServer(data,get_transport_from_server);
		}
		
		<%-- функция открытия выделенного шаблона или создания нового --%>
		function open_pattern(){
			if(!(document.getElementById("button_open_pattern").getAttribute("iscreate")==null)){
				var select_element=document.getElementById("pattern_select");
				var data=new Object();
				data.key=function_pattern_create;
				data.value=document.getElementById("create_text").value;
				ExchangeFieldsPattern.sendXmlToServer(data,get_transport_from_server);
			}else{
				var select_element=document.getElementById("pattern_select");
				var data=new Object();
				data.key=function_pattern_open;
				data.value=select_element.options[select_element.selectedIndex].value;
				ExchangeFieldsPattern.sendXmlToServer(data,get_transport_from_server);
			}
		}

		<%-- поставить таблицу в начало --%>
		function table_first(element){
			var consist_table=get_consist_table(element);
			var table_parent=get_table_parent();
			if(table_parent.hasChildNodes()){
				var first_child=table_parent.childNodes.item(0);
				//table_parent.removeChild(consist_table);
				table_parent.insertBefore(consist_table,first_child);
			}
		}

		<%-- получить элемент перед заданным элементом--%>
		function get_element_before(tag_name,element){
			var parent_element=element.parentNode;
			if(parent_element.hasChildNodes()){
				var child_before=null;
				var table_list=parent_element.getElementsByTagName(tag_name);
				
				for(var counter=0;counter<table_list.length;counter++){
					if(table_list.item(counter)==element){
						return child_before;
					}
					child_before=table_list.item(counter);
				}
				return null;
			}else{
				return null;
			}
		}

		<%-- получить элемент после заданного элемента --%>
		function get_element_after(tag_name,element){
			var parent_element=element.parentNode;
			if(parent_element.hasChildNodes()){
				var child_after=null;
				var child_after_after=null;
				var table_list=parent_element.getElementsByTagName(tag_name);
				
				for(var counter=table_list.length;counter>=0;counter--){
					if(table_list.item(counter)==element){
						return child_after_after;
					}
					child_after_after=child_after;
					child_after=table_list.item(counter);
				}
				return null;
			}else{
				return null;
			}
		}

		<%-- передвинуть таблицу вверх --%>
		function table_up(element){
			var consist_table=get_consist_table(element);
			var table_parent=get_table_parent();
			
			var table_before=get_element_before("TABLE",consist_table);
			if(table_before!=null){
				table_parent.removeChild(consist_table);
				table_parent.insertBefore(consist_table,table_before);
			}
		}

		<%-- передвинуть таблицу вниз --%>
		function table_down(element){
			var consist_table=get_consist_table(element);
			var table_parent=get_table_parent();
			
			var table_after=get_element_after("TABLE",consist_table);
			if(table_after!=null){
				table_parent.removeChild(consist_table);
				table_parent.insertBefore(consist_table,table_after);
			}else{
				table_parent.removeChild(consist_table);
				table_parent.appendChild(consist_table);
			}
		}

		<%-- переместить таблицу в самый низ --%>
		function table_last(element){
			var consist_table=get_consist_table(element);
			var table_parent=get_table_parent();
			table_parent.removeChild(consist_table);
			table_parent.appendChild(consist_table);
		}

		

		function get_consist_table(element){
			return element.parentNode.parentNode.parentNode.parentNode;
		}

		function get_table_parent(){
			return document.getElementById("fields");
		//	var elements=document.getElementsByTagName("tr");
			
		}
		
	</script>
	<table>
		<tr>
			<td>
				<span id="span_client_select">
					<%
						out.println("<SELECT ID=\"pattern_select\" onchange=\"pattern_select()\">");
						out.println(toHtmlConverter.convertMapToSelectBodyWithEmpty(DBFunction.getFilesPattern()));
						out.println("</SELECT>");
					%>
				</span>
			</td>
			<td>
				<input type="button" id="button_copy_pattern" value="Copy pattern to new" onclick="copy_pattern()" disabled="disabled" >
			</td>
			<td>
				<input type="text" id="text_copy_pattern" size=20 value="" disabled="disabled" >
			</td>
			<td>
				<input type="button" id="button_delete_pattern" size=20 value="delete pattern" disabled="disabled" onclick="delete_pattern()" >
			</td>
		</tr>
		<tr>
			<td>
				<input type="button" id="button_open_pattern" onclick="open_pattern()" value="create_pattern" iscreate >
			</td>
			<td>
				<font id="create_caption"> name of new pattern</font>
			</td>
			<td>
				<input id="create_text" type="text" value="" >
			</td>
		</tr>
		<tr>
			<td>
				Associated File and Task:
			</td>
			<td>
				<select id="task_select">
				<% 
					out.println(toHtmlConverter.convertMapToSelectBody(DBFunction.getTaskAll()));
				%>
				</select>
			</td>
		</tr>
    </table>
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