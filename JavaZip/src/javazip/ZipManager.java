/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javazip;
import java.util.zip.*;
import java.io.*;
/**
 * Класс для архивирования и разархивирования данных 
 * @author cherkashinv
 */
public class ZipManager {
    
    private void debug(String information){
        System.out.print(this.getClass().getName());
        System.out.print(" DEBUG ");
        System.out.println(information);
    }
    private void error(String information){
        System.out.print(this.getClass().getName());
        System.out.print(" ERROR ");
        System.out.println(information);
    }
    
    public static void main(String args[]){
        System.out.println("begin");
        String field_path_to_file="D:\\temp.html ";
        String field_path_to_new_file="D:\\temp2.html ";
        
        ZipManager manager=new ZipManager();
        byte[] array_of_byte=manager.getZipBytesFromFile(field_path_to_file);
        System.out.println("Array of byte length: "+array_of_byte.length);
        manager.getFileFromZipBytes(array_of_byte, field_path_to_new_file);
        System.out.println("end");
    }
    
    /** 
     * разархивировать указанный файл
     * @param path_to_archiv полный путь к файлу архива
     * @param path_to_directory полный путь к каталогу, в который нужно разархивировать данный файл
     */
    public boolean fileFromArchiv(String path_to_archiv, String path_to_directory){
        boolean return_value=false;
        debug("fileFromArchiv: begin");
        try{
            // получить поток 
            FileInputStream fis=new FileInputStream(path_to_archiv);
            // получить zip поток
            ZipInputStream zis=new ZipInputStream(new BufferedInputStream(fis));
            debug("Zip file:"+path_to_archiv+"   Directory:"+path_to_directory);
            // получение очередной сущности
            ZipEntry entry;
            BufferedOutputStream dest;
            while((entry=zis.getNextEntry())!=null){
                if(entry.isDirectory()){
                    debug("Entry is directory:"+entry.getName());
                }else{
                    debug("Entry is file:"+entry.getName());
                    int count;
                    byte data[] = new byte[2048];
                    // write the files to the disk
                    FileOutputStream fos = new FileOutputStream(path_to_directory+entry.getName());
                    dest = new BufferedOutputStream(fos, 2048);
                    while ((count = zis.read(data, 0, 2048)) != -1) {
                       dest.write(data, 0, count);
                    }
                    dest.flush();
                    dest.close();
                }
            }
            zis.close();
            return_value=true;
        }catch(Exception ex){
            debug("Exception: fileFromArchiv: "+ex.getMessage());
        }
        debug("fileFromArchiv: end");
        return return_value;
    }
    
    
    /** заархивировать указанный файл 
     * @param path_to_file путь к файлу для архивирования
     * @param path_to_archiv путь к файлу в который нужно архивировать 
     */
    public boolean fileToArchiv(String absolute_path_to_file,String absolute_path_to_archiv){
        boolean return_value=false;
        try{
            File file_source=new File(absolute_path_to_file);
            if(file_source.exists()){
                BufferedInputStream origin = null;
                FileOutputStream dest = new FileOutputStream(absolute_path_to_archiv);
                ZipOutputStream out = new ZipOutputStream(new 
                BufferedOutputStream(dest));
                //out.setMethod(ZipOutputStream.DEFLATED);
                byte data[] = new byte[2048];
                // get a list of files from current directory
                //File f = new File(".");
                //String files[] = f.list();
                //for (int i=0; i<files.length; i++) {
                //System.out.println("Adding: "+files[i]);
                // --- body ---
                FileInputStream file = new FileInputStream(absolute_path_to_file);
                origin = new BufferedInputStream(file, 2048);
                // положить в Zip архив имя файла, или его полный путь 
                ZipEntry entry = new ZipEntry(file_source.getName());
                out.putNextEntry(entry);
                int count;
                while((count = origin.read(data, 0, 2048)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
                //}
                out.close();
                return_value=true;
            }else{
                debug("fileToArchiv: file not found");
            }
        }catch(Exception ex){
            error("fileToArchiv Exception:"+ex.getMessage());
        }
        return return_value;
    }
    

    /** заархивировать указаныый файл в поток байт
     * @param path_to_file путь к файлу
     * @return возвращает byte[] 
     */
    public byte[] getZipBytesFromFile(String path_to_file){
        byte[] return_value=null;
        try{
            File file_source=new File(path_to_file);
            if(file_source.exists()){
                BufferedInputStream origin = null;
                ByteArrayOutputStream dest=new ByteArrayOutputStream();
                ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
                //out.setMethod(ZipOutputStream.DEFLATED);
                byte data[] = new byte[2048];
                FileInputStream file = new FileInputStream(path_to_file);
                origin = new BufferedInputStream(file, 2048);
                // положить в Zip архив имя файла, или его полный путь 
                ZipEntry entry = new ZipEntry(file_source.getName());
                out.putNextEntry(entry);
                int count;
                while((count = origin.read(data, 0, 2048)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
                out.close();
                dest.close();
                return_value=dest.toByteArray();
            }else{
                debug("fileToArchiv: file not found");
            }
        }catch(Exception ex){
            error("getZipBytesFromFile:"+ex.getMessage());
        }
        return return_value;
    }

    /** разархивировать поток байт в виде Zip архива в файл на диске 
     * @param data массив байт формата Zip
     * @param path_to_file имя файла для сохранения из архива
     * @return true если файл успешно сохранен
     */
    public boolean getFileFromZipBytes(byte[] data,String path_to_file){
        boolean return_value=false;
        try{
            // получить поток 
            ByteArrayInputStream fis=new ByteArrayInputStream(data);
            // получить zip поток
            ZipInputStream zis=new ZipInputStream(new BufferedInputStream(fis));
            // получение очередной сущности
            ZipEntry entry;
            BufferedOutputStream dest;
            while((entry=zis.getNextEntry())!=null){
                if(entry.isDirectory()){
                    debug("this is directory:"+entry.getName());
                }else{
                    debug("this is file:"+entry.getName());
                    int count;
                    byte buffer[] = new byte[2048];
                    // write the files to the disk
                    FileOutputStream fos = new FileOutputStream(path_to_file);
                    dest = new BufferedOutputStream(fos, 2048);
                    while ((count = zis.read(buffer, 0, 2048)) != -1) {
                       dest.write(buffer, 0, count);
                    }
                    dest.flush();
                    dest.close();
                }
            }
            zis.close();
            return_value=true;
        }catch(Exception ex){
            error("getFileFromZipBytes Exception:"+ex.getMessage());
        }
        return return_value;
    }
}
