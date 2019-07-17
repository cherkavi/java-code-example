<%@ page language="java" 
		 contentType="text/html; charset=UTF-8"
     	 pageEncoding="UTF-8"
     	 import="bc.data_terminal.editor.database.*,bc.data_terminal.editor.utility.*,org.apache.log4j.*"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<body onload="terminal_select()" bgcolor="Silver">
	<script type='text/javascript' src='/XML_Editor/dwr/interface/ExchangeTerminalTask.js'></script>
	<script type='text/javascript' src='/XML_Editor/dwr/engine.js'></script>
	
	<link rel="stylesheet" href="modal/windowfiles/dhtmlwindow.css" type="text/css" />
	<script type="text/javascript" src="modal/windowfiles/dhtmlwindow.js"></script>
	<link rel="stylesheet" href="modal/modalfiles/modal.css" type="text/css" />
	<script type="text/javascript" src="modal/modalfiles/modal.js"></script>

	
	<script type="text/javascript">
		var function_terminal_select="function_terminal_select";
		var function_show_insert="function_show_insert"
		var function_insert="function_insert"; 
		var function_delete="function_delete";
		
		<%-- элемент для удаления --%>
		var delete_element;
		<%-- модальное окно для подтверждения удаления данных--%>
		var modal_window_delete;
		
		<%-- флаг подтверждения удаления данных --%>
		var modal_window_delete_commit=false;
		function set_modal_window_delete_commit(value){
			modal_window_delete_commit=value;
		}

		<%-- элемент, который содержит Pattern для выбора --%>
		var select_element_pattern;
		<%-- модальное окно для отображения вставки нового значения--%>		
		var modal_window_insert;
		var modal_window_insert_commit=false;
		var modal_window_insert_task_id="";
		var modal_window_insert_file_id="";
		
		
		<%-- модальное окно для редактирования данных--%>
		<%-- модальное окно для создания данных --%>
		
		<%-- произошла смена терминала, необходимо вывести по данному терминалу всю таблицу из задач --%>
		function terminal_select(){
			if(getCurrentTerminal()!=""){
				var data=new Object();
				data.key=function_terminal_select;
				<%--  user id --%>
				data.value=getCurrentTerminal();
				<%-- table task pattern --%>
				data.value2="table_task_pattern";
				ExchangeTerminalTask.sendXmlToServer(data,get_transport_from_server);
			}else{
				var element=document.getElementById("span_task_pattern");
				element.innerHTML="";
			}
		}

		<%-- объект, который получает ответ от сервера --%>
		function get_transport_from_server(response){
			if(response){
				if(response.key){
					if(response.key=="alert"){
						alert(response.value);
					}
					<%-- выбран новый терминал  --%>
					if(response.key==function_terminal_select){
						var element=document.getElementById("span_task_pattern");
						element.innerHTML=response.value;
					}
					<%-- выбран новый терминал  --%>
					if(response.key==function_insert){
						var element=document.getElementById("span_task_pattern");
						element.innerHTML=response.value;
					}
					
					<%-- отобразить меню для ввода нового значения --%>
					if(response.key==function_show_insert){
						select_element_pattern=document.createElement("SELECT");
						select_element_pattern.innerHTML=response.value;
						modal_window_insert=dhtmlmodal.open("modal_window_insert", "iframe", "modal/modalfiles/modal_window_insert.html", "Insert value", "width=400px,height=120px,center=1,resize=0,scrolling=0", "recal");
						modal_window_insert.onclose=modal_window_insert_return;																							
					}
					<%-- отобразить задачи-файлы после удаления одной записи (задачи-файла) --%>
					if(response.key==function_delete){
						var element=document.getElementById("span_task_pattern");
						element.innerHTML=response.value;
					}
				
				}
			}
		}
		

		function get_select_element_pattern(){
			return select_element_pattern;
		}

		function set_modal_window_insert_commit(value,task_id, file_id){
			modal_window_insert_commit=value;
			modal_window_insert_task_id=task_id;
			modal_window_insert_file_id=file_id;
		}
		
		function modal_window_insert_return(){
			if(modal_window_insert_commit==true){
				var data=new Object();
				data.key=function_insert;
				data.value=getCurrentTerminal();
				data.value2=modal_window_insert_file_id;
				data.value3=modal_window_insert_task_id;
				ExchangeTerminalTask.sendXmlToServer(data,get_transport_from_server);
			}else{
			}
			modal_window_insert_commit=false;
			modal_window_insert_task_id="";
			modal_window_insert_file_id="";
			return true;
		}


		<%-- получить выделенного в данный момент пользователя --%>
		function getCurrentTerminal(){
			var select_element=document.getElementById("terminal_select");
			return select_element.options[select_element.selectedIndex].value;
		}

		<%-- добвить еще одно соответствие TaskName - Pattern для данного пользователя --%>
		function add_new_task_pattern(){
			var data=new Object();
			data.key=function_show_insert;
			data.value=getCurrentTerminal();
			ExchangeTerminalTask.sendXmlToServer(data,get_transport_from_server);
		}


		<%-- удалить элемент Task-Pattern --%>
		function delete_task_pattern(element){
			<%-- set global variable for delete--%>
			delete_element=get_parent_table_by_element(element);
			modal_window_delete=dhtmlmodal.open("modal_window_delete", "iframe", "modal/modalfiles/modal_window_delete.html", "Warning", "width=180px,height=80px,center=1,resize=0,scrolling=0", "recal");
			//modal_window_delete.onclose=modal_window_close_return; 
			modal_window_delete.onclose=modal_window_delete_return;
		}
		<%-- функция для удаления данных --%>
		function modal_window_delete_return(){
			if(modal_window_delete_commit==true){
				var data=new Object();
				data.key=function_delete;
				<%--  user id --%>
				data.value=getCurrentTerminal();
				<%-- table task pattern --%>
				data.value2=delete_element.getAttribute("id_file");
				data.value3=delete_element.getAttribute("id_task");
				ExchangeTerminalTask.sendXmlToServer(data,get_transport_from_server);

				
				var parent_table=get_parent_table_for_table(delete_element);
				parent_table.childNodes.item(0).removeChild(delete_element.parentNode.parentNode);
				delete_element=null;
			}else{
									
			}
			return true;
		}


		<%-- получить таблицу, которая содержит в себе все элементы-таблицы (имя1, имя2, кнопка Edit, кнопка Delete)--%>
		function get_parent_table_for_table(table_element){
			//document.getElementById("").childNodes.item()
			//document.getElementById("").removeChild()
			return table_element.parentNode.parentNode.parentNode.parentNode;
		}
		
		<%-- получить таблицу, которая содержит данный элемент (кнопка удалить, кнопка редактировать )--%>
		function get_parent_table_by_element(element){
			return element.parentNode.parentNode.parentNode.parentNode;
		}
	</script>	
	<%-- visual element's --%>

	<%-- combobox of terminals --%>
	<% 
		out.println("<SELECT ID=\"terminal_select\" onchange=\"terminal_select()\">");
		out.println(toHtmlConverter.convertMapToSelectBodyWithEmpty(DBFunction.getUsers()));
		out.println("</SELECT>");
	%>
	<%-- table of task by terminal --%>
	<span id="span_task_pattern">
	</span>


</body>

</html>