/*
 * TableModel_to_ODF.java
 *
 * Created on 16 квітня 2008, 8:52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package html_table_decode;
import com.sun.star.beans.*;
import com.sun.star.sheet.XSpreadsheet;
import com.sun.star.sheet.XSpreadsheetDocument;
import com.sun.star.sheet.XSpreadsheetView;
import com.sun.star.sheet.XSpreadsheets;
import com.sun.star.table.XCell;
import com.sun.star.util.*;
import com.sun.star.io.*;
import com.sun.star.connection.*;
import com.sun.star.lang.*;
import com.sun.star.text.*;
import com.sun.star.frame.*;
import com.sun.star.container.*;
import com.sun.star.view.XPrintable;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XInterface;
import com.sun.star.uno.XNamingService;
import com.sun.star.uno.XComponentContext;
import javax.swing.table.TableModel;
import ooo.connector.BootstrapSocketConnector;
/**
 * Класс для экспортирования TableModel в Формат OpenOffice CALC
 * @author Technik
 */
public class TableModel_to_ODF {
    private XComponentContext xRemoteContext = null;
    private XMultiComponentFactory xRemoteServiceManager = null;
    private XURLTransformer xTransformer = null;
    private XComponentLoader xComponentLoader = null;
    private XDesktop xDesktop=null;
    private XSpreadsheet xSpreadsheet=null;
    
    /** массив из чисел int - номера столбцов, которые можно выводить*/
    private int[] field_valid_column=null;
    /** массив из заголовков*/
    private String[] field_column_header;
    
    /** создать объект, и присоединиться к OpenOffice
     * @throw если соединение с OpenOffice прошло неудачно
     */
    public TableModel_to_ODF() throws Exception {
        connect();
    }
    /** устновить столбцы для вывода
     * @param value - массив из номеров столбцов для вывода данных
     */
    public void set_valid_column(int[] value){
        this.field_valid_column=value;
    }
    /** массив из заголовков для вывода данных*/
    public void set_column_header(String[] value){
        this.field_column_header=value;
    }
    /** метод-коннектор с Open Office*/
    private void connect() throws Exception {
        //xRemoteContext = com.sun.star.comp.helper.Bootstrap.bootstrap();
        String oooExeFolder = "C:/Program Files/OpenOffice.org 2.0.2/program/";
        //String oooExeFolder = "%OPEN_OFFICE_PROGRAM%"; // not emplements
        xRemoteContext = BootstrapSocketConnector.bootstrap(oooExeFolder);
        
        xRemoteServiceManager = xRemoteContext.getServiceManager();
    
        //
        Object transformer = xRemoteServiceManager.createInstanceWithContext("com.sun.star.util.URLTransformer", xRemoteContext );
        xTransformer = (XURLTransformer)UnoRuntime.queryInterface(XURLTransformer.class, transformer);
    
        //     Desktop
        Object desktop = (XInterface) xRemoteServiceManager.createInstanceWithContext("com.sun.star.frame.Desktop", xRemoteContext);
        xDesktop = (XDesktop)UnoRuntime.queryInterface(XDesktop.class, desktop);
    
        //             
        xComponentLoader = (XComponentLoader)UnoRuntime.queryInterface(XComponentLoader.class, desktop);        
    }
    private boolean is_value_in_array(int value,int[] array){
        boolean return_value=false;
        if(array==null){
            return_value=false;
        }else{
            for(int counter=0;counter<array.length;counter++){
                if(value==array[counter]){
                    return_value=true;
                    break;
                }
            }
        };
        return return_value;
    }
    private void model_to_calc(TableModel table_model,XSpreadsheet sheet) throws Exception{
        XCell cell=null;
        /** кол-во строк в модели*/
        int row_count=table_model.getRowCount();
        /** счетчик строк в модели*/
        int row_counter=0;
        /** порядковый номер строки для вывода в CALC*/
        int out_row_counter=0;
        /** кол-во столбцов в модели*/
        int column_count=table_model.getColumnCount();
        /** счетчик колонок в модели*/
        int column_counter=0;
        /** порядоквый номер столбца для вывода в CALC*/
        int out_column_counter=0;
        
        // вывод заголовка
        if((this.field_column_header!=null)&&(this.field_column_header.length>0)){
            // проверка на вывод только указанных колонок
            if((this.field_valid_column!=null)&&(this.field_valid_column.length>0)){
                while(column_counter<column_count){
                    if(this.is_value_in_array(column_counter,this.field_valid_column)){
                        cell=sheet.getCellByPosition(out_column_counter,0);
                        cell.setFormula(this.field_column_header[out_column_counter]);
                        out_column_counter++;
                    }
                    column_counter++;
                }
            }else{
                for(int counter=0;counter<this.field_column_header.length;counter++){
                    cell=sheet.getCellByPosition(counter,0);
                    cell.setFormula(this.field_column_header[counter]);
                }
            }
            out_row_counter=1;
        }
        // вывод данных
        while(row_counter<row_count){
            column_counter=0;
            out_column_counter=0;
            while(column_counter<column_count){
                if(this.field_valid_column==null){
                    cell=sheet.getCellByPosition(out_column_counter,out_row_counter);
                    cell.setFormula((String)table_model.getValueAt(row_counter,column_counter));
                    out_column_counter++;
                }else{
                    if(this.is_value_in_array(column_counter,this.field_valid_column)){
                        cell=sheet.getCellByPosition(out_column_counter,out_row_counter);
                        cell.setFormula((String)table_model.getValueAt(row_counter,column_counter));
                        out_column_counter++;
                    }else{
                        //System.out.println("данные не должны быть выведены");
                    }
                }
                column_counter++;
            }
            out_row_counter++;
            row_counter++;
        }
    }
    /** 
     * метод, который выводит данные из TableModel на лист CALC
     * @param table_model модель для таблицы, откуда нужно брать данные (String)
     * @sheet_name имя листа, который будет вставлен в документ
     */
    public boolean to_calc(TableModel table_model,String sheet_name ){
        boolean return_value=false;
        try {
            if((sheet_name==null)||(sheet_name.trim()=="")){
                sheet_name="out";
            }
            // query the XComponentLoader interface from the desktop
            XComponentLoader xComponentLoader = (XComponentLoader)UnoRuntime.queryInterface(XComponentLoader.class, xDesktop);
            // create empty array of PropertyValue structs, needed for loadComponentFromURL
            PropertyValue[] loadProps = new PropertyValue[0];
            // load new calc file
            XComponent xSpreadsheetComponent = xComponentLoader.loadComponentFromURL("private:factory/scalc", "_blank", 0, loadProps);
            // query its XSpreadsheetDocument interface, we want to use getSheets()
            XSpreadsheetDocument xSpreadsheetDocument = (XSpreadsheetDocument)UnoRuntime.queryInterface(XSpreadsheetDocument.class, xSpreadsheetComponent);
            // use getSheets to get spreadsheets container
            XSpreadsheets xSpreadsheets = xSpreadsheetDocument.getSheets();
            //insert new sheet at position 0 and get it by name, then query its XSpreadsheet interface
            xSpreadsheets.insertNewByName(sheet_name, (short)0);
            Object sheet = xSpreadsheets.getByName(sheet_name);
            xSpreadsheet = (XSpreadsheet)UnoRuntime.queryInterface(XSpreadsheet.class, sheet);

            // use XSpreadsheet interface to get the cell A1 at position 0,0 and enter 21 as value
            //XCell xCell = xSpreadsheet.getCellByPosition(0, 0);
            //xCell.setValue(21);
            // sum up the two cells
            //xCell = xSpreadsheet.getCellByPosition(0, 2);
            //xCell.setFormula("=sum(A1:A2)");
            this.model_to_calc(table_model,xSpreadsheet);
            // we want to access the cell property CellStyle, so query the cell's XPropertySet interface 
            //XPropertySet xCellProps = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, xCell);
            // assign the cell style "Result" to our formula, which is available out of the box
            //xCellProps.setPropertyValue("CellStyle", "Result");
            // we want to make our new sheet the current sheet, so we need to ask the model
            // for the controller: first query the XModel interface from our spreadsheet component
            XModel xSpreadsheetModel = (XModel)UnoRuntime.queryInterface(XModel.class, xSpreadsheetComponent);
            // then get the current controller from the model
            XController xSpreadsheetController = xSpreadsheetModel.getCurrentController();
            // get the XSpreadsheetView interface from the controller, we want to call its method
            // setActiveSheet
            XSpreadsheetView xSpreadsheetView = (XSpreadsheetView)UnoRuntime.queryInterface(XSpreadsheetView.class, xSpreadsheetController);
            // make our newly inserted sheet the active sheet using setActiveSheet
            xSpreadsheetView.setActiveSheet(xSpreadsheet);   

            /*   
             * printDocument(currentDocument) -         
             * saveDocument (currentDocument, props) -     
             * saveAsDocument (currentDocument, sURL, props) -       
             */
            //String[] cmds = {"Print"};
            //executeCommands(cmds);
            /*closeDocument(currentDocument, false);
            XCloseable c = (XCloseable)UnoRuntime.queryInterface(XCloseable.class, currentDocument);
            while(true){
                try {
                    c.close(false);
                    break;
                } catch (com.sun.star.util.CloseVetoException e) {
                }            
            }
            Thread.sleep(200);*/
            return_value=true;
        }catch(Exception e) {
            System.out.println(" Exception "+e.getMessage());
        }
        return return_value;
    }
}
