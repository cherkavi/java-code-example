package com.cherkashin.vitaliy.rfid.web_server.client.view.show.result;

import java.util.ArrayList;
import com.cherkashin.vitaliy.rfid.web_server.client.utility.ITableDataSelected;
import com.cherkashin.vitaliy.rfid.web_server.client.view.show.Result;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.data.MemoryProxy;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.data.ArrayReader;
import com.gwtext.client.data.DateFieldDef;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.IntegerFieldDef;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.event.GridCellListener;

/** таблица с данными */
public class ResultTable extends GridPanel{
	/** columns into data for view */
	private final String[] columns=new String[]{"id","name","surname","fatherName","cardNumber"};
	private Result_const constants=null;
	private ArrayList<ITableDataSelected> listOfListener=new ArrayList<ITableDataSelected>();

	/** обновить данные в таблице */
	public void updateModel(Result[] elements){
		this.getStore().setDataProxy(new MemoryProxy(this.convertRowDataToArray(elements)));
		this.getStore().reload();
	}

	/** конвертация POJO в Object[][] */
	private Object[][] convertRowDataToArray(Result[] rowData){
		Object[][] returnValue=new Object[rowData.length][5];
		for(int counter=0;counter<rowData.length;counter++){
			returnValue[counter][0]=rowData[counter].getDate();
			returnValue[counter][1]=rowData[counter].getUserName();
			returnValue[counter][2]=rowData[counter].getDateIn();
			returnValue[counter][3]=rowData[counter].getDateOut();
			returnValue[counter][4]=rowData[counter].getMinutes();
		}
		return returnValue;
	}

	/** таблица с данными 
	 * @param constants - константы, который описывают поля 
	 * @param list - список слушателей 
	 */
	public ResultTable(Result_const constants, ITableDataSelected ... list){
		for(ITableDataSelected element:list){
			listOfListener.add(element);
		}
		this.constants=constants;
		RecordDef recordDef=new RecordDef(new FieldDef[]{
										  new DateFieldDef(columns[0]),
										  new StringFieldDef(columns[1]),
										  new DateFieldDef(columns[2]),
										  new DateFieldDef(columns[3]),
										  new IntegerFieldDef(columns[4])
							 });
		Store store=new Store(new ArrayReader(recordDef));
		// Grid.column
		ColumnConfig[] columnsConfig=new ColumnConfig[]{
				new ColumnConfig(this.constants.columnDate(),columns[0],150,true),
				new ColumnConfig(this.constants.columnUserName(),columns[1],150,true),
				new ColumnConfig(this.constants.columnDateIn(),columns[2],200,false),
				new ColumnConfig(this.constants.columnDateOut(),columns[3],200,false),
				new ColumnConfig(this.constants.columnMinutes(),columns[4],50,false)
		};
		this.setColumnModel(new ColumnModel(columnsConfig));
		// this.addGridCellListener(new GridCellListener(){
		this.setStore(store);
		// this.setTitle(constants.panelTitle());
		this.setStore(store);
		//this.setWidth("100%");
		//this.setHeight("100%");
		this.setStripeRows(true);
		this.setAutoScroll(true);
		this.addGridCellListener(new GridCellListener(){
			@Override
			public void onCellClick(GridPanel grid, int rowIndex, int colIndex,
					EventObject e) {
				ResultTable.this.notifyData(ResultTable.this.getStore().getAt(rowIndex));
			}

			@Override
			public void onCellContextMenu(GridPanel grid, int rowIndex,
					int cellIndex, EventObject e) {
			}

			@Override
			public void onCellDblClick(GridPanel grid, int rowIndex,
					int colIndex, EventObject e) {
			}
		});
	}
	
	/** оповестить слушателей о выделении записей */
	private void notifyData(Record record){
		for(ITableDataSelected listener:this.listOfListener){
			try{
				String object=record.getAsString(columns[0]);
				listener.selectedValue(object);
			}catch(Exception ex){
				System.err.println("Exception: "+ex.getMessage());
			}
		}
	}
	
}
