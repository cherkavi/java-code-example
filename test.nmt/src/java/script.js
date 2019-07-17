        function button_add_click(){
            var table=document.getElementById(table_name);
            var combobox=document.getElementById(combobox_name);
            var delete_text;
            var delete_index;
            var counter=0;
            // combobox exists ?
            if(combobox!=null){
                // remove selected child
                for(counter=0;counter<combobox.childNodes.length;counter++){
                    if(combobox.childNodes[counter].selected){
                        delete_text=combobox.childNodes[counter].value;
                        delete_index=get_element_index(field_values,delete_text);
                        //out("text:"+delete_text+"   index:"+delete_index);
                        combobox.removeChild(combobox.childNodes[counter]);
                        combobox=document.getElementById(combobox_name);
                        //out(combobox.childNodes.length);
                        // combobox is empty ?
                        if(combobox.childNodes.length==1){
                            //combobox.setAttribute("Visible","false");
                            // remove combobox from 
                            remove_element_from_parent(document.getElementById(combobox_parent_name),combobox);
                        }
                        //out(delete_text);
                        break;
                    }
                }
                //create row into table
                if(table==null){
                    // create table with header
                    var table=document.createElement(table_name);
                    table.id=table_name;
                    var table_row=document.createElement("tr");
                    var table_header_1=document.createElement("th");
                    var table_header_2=document.createElement("th");
                    table_header_1.innerHTML="<center>description</center>";
                    table_row.appendChild(table_header_1);
                    table_header_2.innerHTML="<center>manager</center>";
                    table_row.appendChild(table_header_2);
                    table.appendChild(table_row);
                }
                // create header
                var table=document.getElementById(table_name);
                var table_row=document.createElement("tr");
                table_row.id="row_"+delete_index;
                var table_header_1=document.createElement("td");
                var table_header_2=document.createElement("td");
                table_header_1.innerHTML="<center>"+delete_text+"</center><br/><input type='text' size=10 id='"+form_element_prefix+delete_index+"' name='"+form_element_prefix+delete_index+"'/>";
                table_row.appendChild(table_header_1);
                table_header_2.innerHTML="<input type='button' value='delete' onclick='delete_table_row(\"row_"+delete_index+"\")'/>";
                table_row.appendChild(table_header_2);
                table.appendChild(table_row);
            }else{
                // check for scan resource or check for test
            }
        }

        function delete_table_row(row_name){
            // get index from array
            var element_index=row_name.substring("row_".length,row_name.length);
            //out("element_name:"+row_name+"   element_index:"+element_index);
            // find element and delete row into table
            var counter=0;
            var table=document.getElementById(table_name);
            var find_element=document.getElementById(row_name);
            for(counter=0;counter<table.childNodes.length;counter++){
                if(table.childNodes[counter]==find_element){
                    table.removeChild(find_element);
                    break;
                }
            }
            // adding element into combobox
            var combobox=document.getElementById(combobox_name);
                // create combobox ?
            if(combobox==null){
                var parent_element=document.getElementById(combobox_parent_name);
                var combobox=document.createElement("select");
                combobox.id=combobox_name;
                parent_element.appendChild(combobox);
            }
                // add element
            combobox=document.getElementById(combobox_name);
            var option_new=document.createElement("option");
            //out("element_index:"+element_index+"   Value:"+field_values[element_index]);
            var option_text=document.createTextNode(field_values[element_index]);
            option_new.appendChild(option_text);
            combobox.appendChild(option_new);
        }
                
        // get index of element from array
        function get_element_index(array,element){
            var return_value=-1;
            var counter=0;
            if(array!=null){
                for(counter=0;counter<array.length;counter++){
                    if(array[counter]==element){
                        return_value=counter;
                        break;
                    }
                }
            }
            return return_value;
        }
        
        // delete element_for_delete from parent
        function remove_element_from_parent(parent,element_for_delete){
            var counter=0;
            for(counter=0;counter<parent.childNodes.length;counter++){
                if(parent.childNodes[counter]==element_for_delete){
                    parent.removeChild(element_for_delete);
                    break;
                }
            }
        }
        
        function load_combobox_criteria(){
            var combobox=document.getElementById(combobox_name);
            var counter=0;
            var option_new;
            var option_text
            for (counter=0;counter<field_values.length;counter++){
                option_new=document.createElement("option");
                option_text=document.createTextNode(field_values[counter]);
                option_new.appendChild(option_text);
                combobox.appendChild(option_new);
            }
        }
        
        var console_name="console";
        function out(value){
            var element=document.getElementById(console_name);
            if(element!=null){
                // console is found
		        if((value==null)||(value=="")||(value==" ")||(value=="\n")){
                	var element_child=document.createElement("br");
                	element.appendChild(element_child);
		        }else{
                	var element_child=document.createElement("div");
                	element.appendChild(element_child);
                	element_child.appendChild(document.createTextNode(value));
		        }
            }else{
                // console not found
            }
        }
