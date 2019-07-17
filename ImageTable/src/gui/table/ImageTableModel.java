package gui.table;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class ImageTableModel implements TableModel{
	private Logger field_logger=Logger.getLogger(this.getClass().getName());
	{
		field_logger.setLevel(Level.INFO);
	}
	private Repaint field_component_for_repaint;
	/** объект, который хранит все текущие изображения */
	private ArrayList<RowElement> field_data=new ArrayList<RowElement>();
	private static final long serialVersionUID = 1L;
	/** генератор случайных чисел */
	java.util.Random field_random=new java.util.Random();
	/** длинна уникального идентификатора */
	int field_unique_length=5;
	/** симоволы для генерации уникального номера */
	private static char[] symbols=new char[]{'0','1','2','3','4','5','6','7','8','9','0','A','B','C','D','E','F'};
	
	/** модель таблицы, которая содержит визуальные элементы изображений */
	public ImageTableModel(Repaint repaint){
		this.field_component_for_repaint=repaint;
	}
	/** добавить элемент в таблицу */
	public void addElement(String ... path_to_image){
		field_logger.debug("addElement:begin");
		String unique_identifier=this.getUniqueIdentifier();
		ImageIdentifier[] identifiers=new ImageIdentifier[path_to_image.length];
		for(int counter=0;counter<path_to_image.length;counter++){
			identifiers[counter]=new ImageIdentifier(path_to_image[counter]);
		}
		this.field_data.add(new RowElement(unique_identifier,identifiers));
		repaint_table();
		field_logger.debug("addElement:end");
	}
	
	/** удалить элемент из таблицы */
	public void deleteElement(String unique_identifier){
		field_logger.debug("deleteElement:begin");
		int index_for_delete=this.getIndexByUniqueIdentifier(unique_identifier);
		if(index_for_delete>=0){
			field_logger.debug("index for delete:"+index_for_delete);
			this.field_data.remove(index_for_delete);
			repaint_table();
		}else{
			this.field_logger.error("index not found");
		}
		field_logger.debug("deleteElement:end");
	}

	/** repaint table for visible changing */
	private void repaint_table(){
		this.field_component_for_repaint.repaintVisualElements();
	}
	
	/**
	 * return index of RowElement into  ArrayList by unique_idetifier
	 * @param unique_identifier уникальный идентификатор 
	 * @return индекс иникального идентификатора
	 */
	private int getIndexByUniqueIdentifier(String unique_identifier){
		for(int counter=0;counter<field_data.size();counter++){
			if(field_data.get(counter).isUniqueIdentifierEquals(unique_identifier)){
				return counter;
			}
		}
		return -1;
	}
	
	/** генератор уникального идентификатора в контексте модели данных*/
	private String getUniqueIdentifier(){
		String return_value=null;
		boolean generator_error=true;
		// проверка на уникальность в контексте модели
		while(generator_error){
			return_value=createUniqueIdentifier();
			generator_error=(this.getIndexByUniqueIdentifier(return_value)>=0);
		}
		field_logger.debug("Unique identifier:"+return_value);
		return return_value;
	}
	
	/** создание уникального идентификатора */
	private String createUniqueIdentifier(){
		StringBuffer return_value=new StringBuffer();
		for(int counter=0;counter<this.field_unique_length;counter++){
			return_value.append(symbols[this.field_random.nextInt(symbols.length)]);
		}
		return return_value.toString();
	}
	
	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		return this.field_data.size();
	}

	@Override
	public Object getValueAt(int indexRow, int indexColumn) {
		if((indexColumn<2)&&(indexRow<this.field_data.size())){
			if(indexColumn==0){
				return this.field_data.get(indexRow).getUniqueIdentifier();
			}else{
				return this.field_data.get(indexRow);
			}
		}else{
			return null;
		}
	}


	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if(columnIndex==1){
			return RowElement.class;
		}else{
			return String.class;
		}
	}

	@Override
	public String getColumnName(int columnIndex) {
		if(columnIndex<2){
			if(columnIndex==0){
				return "UniqueIndex";
			}else{
				return "RowElement";
			}
		}else{
			return null;
		}
		
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {return false;}
	@Override
	public void removeTableModelListener(TableModelListener l) {}
	@Override
	public void addTableModelListener(TableModelListener l) {}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {}
}



