package com.cherkashin.vitaliy.rfid.web_server.client.view.edit.people_add;

import java.util.ArrayList;
import com.cherkashin.vitaliy.rfid.web_server.client.utility.ITableDataSelected;
import com.cherkashin.vitaliy.rfid.web_server.client.view.edit.Card;
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
public class TableCard extends GridPanel{
	/** columns into data for view */
	private final String[] columns=new String[]{"id","cardNumber","timeWrite"};
	private PeopleAdd_const constants=null;
	/** конвертация POJO в Object[][] */
	private Object[][] convertRowDataToArray(Card[] rowData){
		Object[][] returnValue=new Object[rowData.length][3];
		for(int counter=0;counter<rowData.length;counter++){
			returnValue[counter][0]=rowData[counter].getId();
			returnValue[counter][1]=rowData[counter].getCardNumber();
			returnValue[counter][2]=rowData[counter].getDate();
		}
		return returnValue;
	}
	
	/** обновить данные в таблице */
	public void updateModel(Card[] elements){
		this.getStore().setDataProxy(new MemoryProxy(this.convertRowDataToArray(elements)));
		this.getStore().reload();
	}
	
	private ArrayList<ITableDataSelected> listOfListener=new ArrayList<ITableDataSelected>();
	
	/** таблица с данными 
	 * @param constants - константы, который описывают поля 
	 * @param list - список слушателей 
	 */
	public TableCard(PeopleAdd_const constants, ITableDataSelected ... list){
		for(ITableDataSelected element:list){
			listOfListener.add(element);
		}
		this.constants=constants;
		RecordDef recordDef=new RecordDef(new FieldDef[]{
				new IntegerFieldDef(columns[0]),
				new StringFieldDef(columns[1]),
				new DateFieldDef(columns[2])
		});
		Store store=new Store(new ArrayReader(recordDef));
		// Grid.column
		ColumnConfig[] columnsConfig=new ColumnConfig[]{
				//new ColumnConfig(this.constants.columnId(),columns[0],30,true),
				new ColumnConfig(this.constants.columnCardNumber(),columns[1],140,true),
				new ColumnConfig(this.constants.columnDate(),columns[2],140,true)
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
				TableCard.this.notifyData(TableCard.this.getStore().getAt(rowIndex));
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
			listener.selectedValue(record.getAsString(columns[1]));
		}
	}
}
