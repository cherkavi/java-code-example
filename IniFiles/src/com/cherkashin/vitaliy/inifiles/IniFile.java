/**
 * Пакет, который работает с файлами у которых формат подобен Ini файлу
 */
package com.cherkashin.vitaliy.inifiles;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
/**
 * класс, который позволяет работать с файлом как с Ini Файлом - аналог в Delphi
 */
public class IniFile {
    private String field_path_to_file;
    private StringBuffer field_stringbuffer;
    private HashMap field_body;
    /** открытие Ini файла, если файл не существует, то его необходимо создать */
    public IniFile(String path_to_file) {
        this.field_path_to_file=path_to_file;
        this.field_stringbuffer=new StringBuffer();
        this.field_body=new HashMap();
        if(this.file_exists(this.field_path_to_file)){
            // файл найден
            this.read_file_to_StringBuffer(this.field_stringbuffer,this.field_path_to_file);
            this.from_StringBuffer_to_HashMap(this.field_stringbuffer,this.field_body);
        }else {
            // файл не найден
        }
    }
    /**
     * @return возвращает значение True если секция существует
     * @param section_name имя секции
     */
    public boolean isSectionExists(String section_name){
        Set key_set=this.field_body.keySet();
        Iterator iterator=key_set.iterator();
        boolean return_value=false;
        while(iterator.hasNext()){
            if( ((String)iterator.next()).equalsIgnoreCase(section_name)){
                return_value=true;
                break;
            };
        };
        return return_value;
    }
    /**
     * @return возвращает значение True если секция и ключ найдены
     * @param section_name имя секции
     * @param key_name имя ключа
     */
    public boolean isKeyExists(String section_name,String key_name){
        boolean return_value=false;
        Set section_set=this.field_body.keySet();
        Iterator section_iterator=section_set.iterator();
        label_section:
        while(section_iterator.hasNext()){
            Object section_next=section_iterator.next();
            if( ((String)section_next).equalsIgnoreCase(section_name)){
                // секция найдена, поиск в секции необходимого 
                HashMap key_hashmap=(HashMap)this.field_body.get(section_next);
                Set key_set=key_hashmap.keySet();
                Iterator key_iterator=key_set.iterator();
                label_key:
                while(key_iterator.hasNext()){
                    if( ((String)key_iterator.next()).equalsIgnoreCase(key_name)){
                        return_value=true;
                        break label_section;
                    }
                };
            };
        };
        return return_value;
    }
    /**
     * @return возвращает значение параметра по секции и ключу, возвращает ("") если последовательность не найдена
     * @param section_name
     * @param key_name
     * @param default_value
     */
    public String getValue(String section_name,String key_name,String default_value){
        String return_value=default_value;
        Set section_set=this.field_body.keySet();
        Iterator section_iterator=section_set.iterator();
        label_section:
        while(section_iterator.hasNext()){
            Object section_next=section_iterator.next();
            if( ((String)section_next).equalsIgnoreCase(section_name)){
                // секция найдена, поиск в секции необходимого 
                HashMap key_hashmap=(HashMap)this.field_body.get(section_next);
                Set key_set=key_hashmap.keySet();
                Iterator key_iterator=key_set.iterator();
                label_key:
                while(key_iterator.hasNext()){
                    Object key_next=key_iterator.next();
                    if( ((String)key_next).equalsIgnoreCase(key_name)){
                        return_value=(String)key_hashmap.get(key_next);
                        break label_section;
                    }
                };
            };
        };
        return return_value;
    }
    /**
     * @return возвращает значение параметра по секции и ключу, возвращает ("") если последовательность не найдена
     * @param section_name
     * @param key_name
     */
    public String getValue(String section_name,String key_name){
        return this.getValue(section_name,key_name,"");
    }
    
    
    /**
     * устанавливает значение параметра по секции и ключу
     * @param section_name имя секции
     * @param key_name имя ключа
     * @param value значение
     */
    public void setValue(String section_name,String key_name,String value){
        Set section_set=this.field_body.keySet();
        Iterator section_iterator=section_set.iterator();
        label_global:
        while(true){
            label_section:
            while(section_iterator.hasNext()){
                Object section_next=section_iterator.next();
                if( ((String)section_next).equalsIgnoreCase(section_name)){
                    // секция найдена, поиск в секции необходимого 
                    HashMap key_hashmap=(HashMap)this.field_body.get(section_next);
                    Set key_set=key_hashmap.keySet();
                    Iterator key_iterator=key_set.iterator();
                    label_key:
                    while(key_iterator.hasNext()){
                        Object key_next=key_iterator.next();
                        if( ((String)key_next).equalsIgnoreCase(key_name)){
                            // попадаем сюда, если найдена и секция и ключ
                            key_hashmap.put(key_next,value);
                            break label_global;
                        }
                    };
                    // попадаем сюда, если секция найдена, но ключа и значения нет
                    key_hashmap.put(key_name,value);
                    break label_global;
                };
            };
            // попадаем сюда, если не найдена секция
            // создаем дополнительную секцию и ключ со значением в ней
            HashMap temp_hashmap=new HashMap();
            temp_hashmap.put(key_name,value);
            this.field_body.put(section_name,temp_hashmap);
            break;
        }
    }
           
    /**  проверка файла на существование 
     * @param path_to_file путь к файлу
     * @return возвращает положительное значение если файл существует и отрицательное, если файл не существует
     */
    private boolean file_exists(String path_to_file){
        java.io.File file=new java.io.File(path_to_file);
        return file.exists();
    }
    /** прочитать файл полностью в объект StringBuffer
     *  в любом случае очищает объект source
     *  @source StringBuffer куда будет помещена вся информация о файле
     *  @path_to_file путь к файлу из которого будут считаны все данные
     *  @return возвращает отрицательный результат, если произошла какая-либо ошибка при чтении
     */
    private boolean read_file_to_StringBuffer(StringBuffer source,String path_to_file){
        boolean return_value=false;
        source.setLength(0);
        try{
            FileInputStream inputstream=new FileInputStream(path_to_file);
            //DataInputStream dis=new DataInputStream(inputstream);
            //while(source.length()!=(source.append(dis.readUTF()).length()));
            int value=0;
            while((value=inputstream.read())!=(-1)){
                source.append((char)value);
            }
            //dis.close();
            inputstream.close();
            return_value=true;
        }catch(Exception e){
            System.out.println("Ошибка при чтении данных: "+e.getMessage());
        }
        return return_value;
    }
    /**
     * Сохранить данные в Ini Файл
     * @param path_to_file
     */
    private boolean save_HashMap_to_file(String path_to_file,String section_prefix,String section_suffix,String key_value_delimeter){
        boolean return_value=false;
        StringBuffer temp_stringbuffer=new StringBuffer();
        Set key_section=this.field_body.keySet();
        Iterator iterator_section=key_section.iterator();
        while(iterator_section.hasNext()){
            Object section=iterator_section.next();
            // запись в буфер имени секции
            temp_stringbuffer.append(section_prefix+(String)section+section_suffix+"\n");
            HashMap value_hash_map=(HashMap)this.field_body.get(section);
            // перебрать все значения для данной секции
            Set key_value=value_hash_map.keySet();
            Iterator iterator_value=key_value.iterator();
            while(iterator_value.hasNext()){
                Object key=iterator_value.next();
                // записать значение ключа и значения для ключа
                temp_stringbuffer.append((String)key+key_value_delimeter+(String)value_hash_map.get(key)+"\n");
            }
            // добавить в конце пустую строку
            temp_stringbuffer.append("\n");
        }
        // Записать данные в файл
        try{
            FileOutputStream fos=new FileOutputStream(path_to_file);
            fos.write(temp_stringbuffer.toString().getBytes());
            fos.close();
            return_value=true;
        }catch(Exception e){
            System.out.println("Error in save data to INI file:"+e.getMessage());
            return_value=false;
        }
        return return_value;
    }
    
    /**
     * обновить содержимое файла
     */
    public boolean Update(){
        return this.save_HashMap_to_file(this.field_path_to_file,"[","]","=");
    }
    
    /** сохранить в  HashMap данные из StringBuffer
     * @param stringbuffer источник, откуда берутся данных
     * @param hashmap карта, куда будут помещены в качестве ключей имена секций, а в качестве значений - тело секции
     * @return возвращает false если вдруг произошла ошибка
     */
    private boolean from_StringBuffer_to_HashMap(StringBuffer stringbuffer,HashMap hashmap){
        boolean return_value=false;
        try{
            String section_prefix="[";
            String section_suffix="]";
            String body=stringbuffer.toString();
            ArrayList arraylist=new ArrayList();
            String new_string="\n";
            StringBuffer section=new StringBuffer();
            int length_of_new_string="\n".length();
            int position_old=(-1);
            int position_new=(-1);
            hashmap.clear();
            // деление всего StringBuffer на строки, которые помещены в ArrayList
            while(true){
                position_new=body.indexOf(new_string,position_old+length_of_new_string);
                //System.out.println("position old="+position_old+"   position_new="+position_new);
                if(position_new<0){
                    if((position_old+length_of_new_string)<(body.length()-1)){
                        //System.out.println(position_old+length_of_new_string+"<>"+body.length()+"  >>> ADDING"+body.substring(position_old+length_of_new_string,body.length()));
                        arraylist.add(body.substring(position_old+length_of_new_string,body.length()));
                    }
                    break;
                }else{
                    arraylist.add(body.substring(position_old+length_of_new_string,position_new).trim());
                    position_old=position_new;
                }
            }
            // печать объекта ArrayList
            /*for(int counter=0;counter<arraylist.size();counter++){
                System.out.println(counter+":"+arraylist.get(counter));
            }*/
            // поиск в строках имен секций и распределение этих секций в HashMap (key=section_name,value=string)
            // создаем из файла Ini объект типа HashMap у которого ключи - секции, 
            // а тело секции еще один HashMap, у которого ключи - имена переменных, а значения - значения этих переменных
            String section_name="";
            for(int counter=0;counter<arraylist.size();counter++){
                if(is_section( (String)arraylist.get(counter),section_prefix,section_suffix)){
                    section_name=get_section_name((String)arraylist.get(counter),section_prefix,section_suffix);
                }else{
                    if(section_name.trim().equals("")){
                        //System.out.println("section name is empty");
                    }else{
                        if(hashmap.get(section_name)!=null){
                            //System.out.println("adding "+section_name);
                            HashMap temp_hashmap=(HashMap)hashmap.get(section_name);
                            if(((String)arraylist.get(counter)!=null)&&(!((String)arraylist.get(counter)).equals(""))){
                                temp_hashmap.put(this.get_key_from_string((String)arraylist.get(counter)),
                                                 this.get_value_from_string((String)arraylist.get(counter)));
                            }
                            hashmap.put(section_name,
                                        temp_hashmap);
                        }else{
                            //System.out.println("create "+section_name+"   >>   "+hashmap.get(section_name));
                            HashMap temp_hashmap=new HashMap();
                            if(((String)arraylist.get(counter)!=null)&&(!((String)arraylist.get(counter)).equals(""))){
                                temp_hashmap.put(this.get_key_from_string((String)arraylist.get(counter)),
                                                 this.get_value_from_string((String)arraylist.get(counter)));
                            }
                            hashmap.put(section_name,
                                        temp_hashmap);
                        }
                    }
                }
            }
            // распечатать данные
            /*Set key_set=hashmap.keySet();
            Iterator key_set_iterator=key_set.iterator();
            while(key_set_iterator.hasNext()){
                Object next_value=key_set_iterator.next();
                System.out.println((String)next_value+" : ");
                this.print_hashmap("             ",(HashMap)hashmap.get(next_value));
            }*/
            return_value=true;
            
        }catch(Exception e){
            System.out.println("Ошибка во время попытки преобразования StringBuffer в HashMap"+e.getMessage());
        }
        return return_value;
    }
    /**   метод, который определяет, является ли данное значение именем секции
     * и если является - тогда нужно взять ее имя
     */
    private boolean is_section(String value,String prefix,String suffix) {
        if((value.indexOf(prefix)==0)&&(value.endsWith(suffix))){
            return true;
        }else {
            return false;
        }
    }
    /** получение имени секции из строки 
     * @param value строка с именем секции
     * @param prefix строка, которая является маркером начала имени секции, по умолчанию "["
     * @param suffix строка, которая является маркером конца имени секции, по умолчанию "]"
     */
    private String get_section_name(String value,String prefix,String suffix) {
        return value.substring(prefix.length(),value.length()-suffix.length());
    }
    /** получение из строки вида key_name=key_value блока key
     * @param value строка, которая содержит значение
     * @return возвращает значение <key> - до знака "="
     */
    private String get_key_from_string(String value){
        String return_value="";
        if((value==null)||(value.length()==0)){
            return "";
        }else {
            int position_of_equals=value.indexOf("=");
            if(position_of_equals<0)position_of_equals=value.length()-1;// нет символа =
            return_value=value.substring(0,position_of_equals);
        }
        //System.out.println("source:"+value+"     key:"+return_value);
        return return_value;
    }
    /** получение из строки вида key_name=key_value блока value
     * @param value строка, которая содержит значение
     * @return возвращает значение <value> - после знака "="
     */
    private String get_value_from_string(String value){
        String return_value="";
        if((value==null)||(value.length()==0)){
            return_value="";
        }else {
            int position_of_equals=value.indexOf("=");
            if(position_of_equals<0){
                return_value=""; // нет символа "=""
            }else{
                return_value=value.substring(position_of_equals+1);
            }
        }
        //System.out.println("source:"+value+"     value:"+return_value);
        return return_value;
    }
    /** отпечатать hashmap c преамбулой */
    private void print_hashmap(String preambule,HashMap hashmap){
       if(hashmap!=null){
            Set key_set=hashmap.keySet();
            Iterator key_set_iterator=key_set.iterator();
            while(key_set_iterator.hasNext()){
                Object next_value=key_set_iterator.next();
                System.out.println(preambule+(String)next_value+" : "+(String)hashmap.get(next_value));
            }
       }
    }

    /** сохранить в файл все что есть в буфере
     * @param source источник StringBuffer из которого нужно производить запись в файл
     * @param path_to_file путь к файлу, в который нужно произвести сохранение
     * @return true в случае успеха
     */
/*    public boolean save_StringBuffer_to_file(StringBuffer source,String path_to_file){
        boolean return_value=false;
        try{
            java.io.FileOutputStream fos=new java.io.FileOutputStream(path_to_file);
            //java.io.DataOutputStream dos=new java.io.DataOutputStream(fos);
            //dos.writeUTF(source.toString());
            fos.write(source.toString().getBytes());
            //dos.close();
            fos.close();
        }catch(Exception e){
            System.out.println("произошла ошибка при попытке записи данных в ini файл"+e.getMessage());
        }
        return return_value;
    }*/
    
}













