package terminal_client.gui;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.EventObject;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import terminal.transfer.PercentReport;

/** данный класс отвечает за отображение текущих процессов, а так же выполненных процессов
 * отображение в виде таблицы объектов в процессе и выполненных объектов 
 * */
public class ProcessLogger extends JPanel implements PercentReport{
	/** */
	private static final long serialVersionUID = 1L;
	/** List of element's */
	private ArrayList<Element> field_element=new ArrayList<Element>();
	/** таблица, содержащая историю общения с сервером */
	private JTable field_progress_table;
	
	/** */
	private JPopupMenu field_popup;
	
	private void debug(String information){
		System.out.print(this.getClass().getName());
		System.out.print(" DEBUG ");
		System.out.println(information);
	}

	@SuppressWarnings("unused")
	private void error(String information){
		System.out.print(this.getClass().getName());
		System.out.print(" ERROR ");
		System.out.println(information);
	}
	
	public ProcessLogger(){
		super();
		initComponents();
	}

	
	private void initComponents(){
		System.out.println("initComponents");
		this.setBorder(javax.swing.BorderFactory.createTitledBorder("Задачи"));
		this.setLayout(new GridLayout(1,1));
		this.field_progress_table=new JTable();
		this.field_progress_table.setRowHeight(25);
		this.field_progress_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.field_progress_table.setModel(new ElementTableModel(this.field_element));
		this.field_progress_table.setDefaultRenderer(Element.class, new ElementRenderer());
		//this.field_progress_table.setDefaultEditor(Element.class, new ElementTableEditor(this.field_element));
		this.field_progress_table.setTableHeader(null);
		this.add(new JScrollPane(this.field_progress_table));
		
		//this.field_progress_table.setDefaultEditor(columnClass, editor)
		//this.field_progress_table.setDefaultRenderer(arg0, arg1)
		this.field_popup=new JPopupMenu();
		JMenuItem menu_delete=new JMenuItem("Delete");
		JMenuItem menu_delete_all=new JMenuItem("Clear");
		this.field_popup.add(menu_delete);
		this.field_popup.addSeparator();
		this.field_popup.add(menu_delete_all);
		// add listener's
		this.field_progress_table.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent event) {
				if(event.getButton()==MouseEvent.BUTTON3){
					ProcessLogger.this.field_popup.show(ProcessLogger.this.field_progress_table,event.getX(), event.getY());
				}
			}
		});
		menu_delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ProcessLogger.this.delete_element_from_table();
			}
		});
		menu_delete_all.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ProcessLogger.this.delete_all_element_from_table();
			}
		});
	}
	
	private void delete_element_from_table(){
		if(this.field_progress_table.getSelectedRow()>=0){
			try{
				Element element=(Element)this.field_progress_table.getValueAt(this.field_progress_table.getSelectedRow(), 0);
				//element.getUniqueId()
				int index_for_delete=this.getElementIndexByUniqueName(element.getUniqueId());
				if(index_for_delete>=0){
					this.deleteElement(index_for_delete);
					this.updateVisualElements();
				}
			}catch(Exception ex){
				
			}
		}
	}
	
	private void delete_all_element_from_table(){
		this.field_element.clear();
		this.updateVisualElements();
	}
	
	/** оповещение о событии новом или изменении выполняемого */
	@Override
	public void getPercentReport(String unique_id, String name, int percent) {
		/*this.field_logger.append("Unique_id="+unique_id+"    Name:"+name+"   Percent:"+percent);
		this.field_logger.append("\n");*/
		debug("getPercentReport: "+unique_id+"     name:"+name+"   percent:"+percent);
		this.setProcessPercent(unique_id, name, percent);
	}
	
	/** получение индекса элемента по его уникальному имени 
	 * @param unique_name - уникальное имя процесса
	 * @return -1, если элемента не существует в списке ( или индекс элемента в )
	 * 
	 * */
	public int getElementIndexByUniqueName(String unique_name){
		int return_value=(-1);
		for(int counter=0;counter<this.field_element.size();counter++){
			try{
				if(this.field_element.get(counter).getUniqueId().equals(unique_name)){
					return_value=counter;
					break;
				}
			}catch(NullPointerException ex){
			}
		}
		return return_value;
	}
	
	/** получение элемента по указанному номеру 
	 * @param unique_name уникальное имя для элемента 
	 * @return возвращает null, если элемент не найден ( или же возвращает сам элемент )
	 * */
	public Element getElementByUniqueIndex(String unique_name){
		Element return_value=null;
		int index=this.getElementIndexByUniqueName(unique_name);
		if(index>=0){
			return_value=this.getElementByIndex(index);
		}
		return return_value;
	}
	
	/** получение элемента по его индексу 
	 * @return null если индекс выходит за пределы 
	 * */
	public Element getElementByIndex(int index){
		if((index>=0)&&(index<this.field_element.size())){
			return this.field_element.get(index);
		}else{
			return null;
		}
	}
	
	/** создание элемента по уникльному имени */
	private void createElement(String unique_id, 
						       String caption, 
						       int value){
		// проверка на создание элемента
		int element_count=this.getElementIndexByUniqueName(unique_id);
		synchronized(this.field_element){
			if(element_count>=0){
				debug("create element. update");
				updateElement(element_count,value);
				this.updateVisualElements();
			}else{
				debug("create element. create");
				Element element=new Element(unique_id, caption,value);
				this.field_element.add(0, element);
				this.updateVisualElements();
			}
		}
	}
	
	
	/** обновление визуального значения элемента 
	 * @param index - индекс элемента на панели
	 * @param value - значение процентов, которое нужно поставить этому элементу
	 * */
	private void updateElement(int index, int value){
		if(index>=0){
			this.field_element.get(index).setPercent(value);
			this.field_element.get(index).getProgressBar().repaint();
			this.updateVisualElements();
		}
	}
	
	/** обновить визуальное содержимое панели */
	private void updateVisualElements(){
		debug("updateVisualElements");
		this.field_progress_table.revalidate();
		this.field_progress_table.repaint();
	}
	
	/** установка значения для элемента по его уникальному имени <br>
	 * или создание элемента по его уникальному имени  
	 * @param unique_name уникальный идентификатор элемента 
	 * @param caption заголовок отображаемой задачи
	 * @param value значение процента
	 * */
	private void setProcessPercent(String unique_name, 
			                      String caption, 
			                      int value){
		int element_count=this.getElementIndexByUniqueName(unique_name);
		if(element_count<0){
			debug(" create element:"+element_count+"   percent:"+value);
			createElement(unique_name, caption, value);
		}else{
			debug(" update element:"+element_count+"   percent:"+value);
			updateElement(element_count, value);
		}
	}
	
	/** удалить элемент по уникальному индексу */
	public void deleteElement(int index){
		if((index>=0)&&(index<this.field_element.size())){
			debug(" remove element by index");
			this.field_element.remove(index);
			this.updateVisualElements();
		}
	}
	
	/** удалить элемент по уникальному имени */
	public void deleteElement(String unique_name){
		debug("remove element by unique_name:"+unique_name);
		deleteElement(this.getElementIndexByUniqueName(unique_name));
	}
	
	/** проверить задачу на ожидание от сервера 
	 * @param unique_name - уникальное имя задачи
	 * @param random_length - длинна случайных чисел в наименовании уникальной задачи 
	 * @return возвращает true, если процесс находится в состоянии ожидания ответа от сервера
	 * или возвращает false, если процесса нет, если процесс выполнен на 100 %, если процесс не выполнился
	 * */
	public boolean isInAction(String unique_name,int random_length){
		boolean return_value=false;
		synchronized(this.field_element){
			// пробежаться по всем элементам
			for(int counter=0;counter<this.field_element.size();counter++){
				// выделить активные элементы
				if(this.field_element.get(counter).isInAction()){
					// сравнить имя
					if(this.field_element.get(counter).getUniqueId().length()==unique_name.length()){
						if(this.field_element.get(counter).getUniqueId().substring(0,this.field_element.get(counter).getUniqueId().length()-random_length).equals(
								                             unique_name.substring(0,unique_name.length()-random_length))){
							return_value=true;
						}
					}
				}
			}
		}
		return return_value;
	}
}

// --------------------------------------------------------------------------------------------------------------------
/** класс для содержания элементов, ответственных за отображение текущих задач */
class Element{
	private String field_unique_id;
	private String field_caption;
	int field_current_process=0;
	private Component field_progressbar=new JProgressBar(JProgressBar.HORIZONTAL,0,100);
	private JPanel field_progressbar_panel=new JPanel(new GridLayout(1,1));
	
	/** получить уникальный индекс */
	public String getUniqueId(){
		return this.field_unique_id;
	}
	
	/** получить заголовок для данного элемента */
	public String getCaption(){
		return this.field_caption;
	}

	/** установить значение(процент выполнения) для элемента */
	public void setPercent(int value){
		if(value==(-1)){
			JLabel field_label=new JLabel("error",JLabel.CENTER);
			field_label.setForeground(Color.RED);
			this.field_progressbar=field_label;
			this.field_progressbar_panel.remove(0);
			this.field_progressbar_panel.add(field_label);
			this.field_progressbar_panel.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED));
			
		}if(value==(100)){
			JLabel field_label=new JLabel("ok",JLabel.CENTER);
			field_label.setForeground(Color.BLUE);
			this.field_progressbar=field_label;
			this.field_progressbar_panel.remove(0);
			this.field_progressbar_panel.add(field_label);
			this.field_progressbar_panel.setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLUE));
		}else{
			this.field_current_process=value;
			if(this.field_progressbar instanceof JProgressBar){
				((JProgressBar)this.field_progressbar).setValue(value);
			}
		}
	}
	
	/**
	 * @param unique_id уникальный идентификатор 
	 * @param name отображаемое значение 
	 */
	public Element(String unique_id, 
				   String caption){
		this(unique_id, caption,0);
	}
	
	/**
	 * @param unique_id уникальный идентификатор 
	 * @param name отображаемое значение
	 * @param percent процент, на который данное действие выполнено 
	 */
	public Element(String unique_id, 
			       String caption,
			       int percent ){
		this.field_unique_id=unique_id;
		this.field_caption=caption;
		this.field_current_process=percent;
		this.field_progressbar_panel.add(this.field_progressbar);
		this.field_progressbar_panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(7, 7, 7, 7));
		this.setPercent(percent);
	}
	
	/** */
	public boolean isInAction(){
		boolean return_value=true;
		if(this.field_current_process>=100){
			return_value=false;
		};
		if(this.field_current_process<0){
			return_value=false;
		}
		return return_value;
	}
	
	/**
	 * получить визуальный компонент - ProgressBar
	 */
	public Component getProgressBar(){
		return this.field_progressbar_panel;
	}
}

// ------------------------------------------------------------------------------------------------------------------------
/** класс, который является моделью таблицы*/
class ElementTableModel extends AbstractTableModel{
	/** */
	private static final long serialVersionUID = 1L;
	/** ссылка на объект, который содержит все элементы для отображения */
	private ArrayList<Element> field_element_list;
	
	public ElementTableModel(ArrayList<Element> list){
		this.field_element_list=list;
	}
	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public int getRowCount() {
		if(this.field_element_list!=null){
			return this.field_element_list.size();
		}else{
			return 0;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(rowIndex<this.field_element_list.size()){
			return this.field_element_list.get(rowIndex);
		}else{
			return null;
		}
	}
	// для всех типов возвращаем класс Element
	public Class<?> getColumnClass(int column_index){
		return Element.class;
	}
	// все ячейки являются редактируемыми 
	public boolean isCellEditable(int rowIndex, int columnIndex){
		return true;
	}
}

//  ---------------------------------------------------------------------------
/** класс, который является прорисовщиком элементов */
class ElementRenderer implements TableCellRenderer{
	
	public ElementRenderer(){
	}
	@Override
	public Component getTableCellRendererComponent(JTable table, 
												   Object value,
												   boolean isSelected, 
												   boolean hasFocus, 
												   int row, 
												   int column) {
		final Element element;
		if(value instanceof Element){
			element=(Element)value;
			JPanel return_value=new JPanel(new GridLayout(1,2));
			{
				if(hasFocus||isSelected){
					return_value.setBorder(javax.swing.BorderFactory.createLineBorder(Color.blue));
				}else{
					//return_value.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED));
				}
				JLabel field_label;
				if((hasFocus)||(isSelected)){
					field_label=new JLabel("<html><i>"+element.getCaption()+"</i></html>",JLabel.CENTER);
				}else{
					field_label=new JLabel(element.getCaption(),JLabel.LEFT);
				}
				return_value.add(field_label);
				return_value.add(element.getProgressBar());
			}
			return return_value;
		}else{
			return new JPanel();
		}
	}
	
}
// --------------------------------------
/** редактор по компонентам */

class ElementTableEditor implements TableCellEditor{

	private ArrayList<CellEditorListener> field_listener=new ArrayList<CellEditorListener>();
	
	public ElementTableEditor(){
		
	}

	
	/** вернуть редактор для компонентов */
	@Override
	public Component getTableCellEditorComponent(JTable table, 
												 Object value,
												 boolean isSelected, 
												 int row, 
												 int column) {
		if(value instanceof Element){

			JPanel return_value=new JPanel();
/*			return_value.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED));
			JLabel field_label;
			//Element element=(Element)value;
			field_label=new JLabel("<html><i>"+element.getCaption()+"</i></html>",JLabel.CENTER);
			return_value.add(field_label,BorderLayout.NORTH);
			return_value.add(element.getProgressBar(),BorderLayout.CENTER);
*/			
			return return_value;
		}else{
			return null;
		}
	}
	
	/** получить отредактированное изменение */
	@Override
	public Object getCellEditorValue() {
		return null;
	}

	
	@Override
	public void addCellEditorListener(CellEditorListener l) {
		this.field_listener.add(l);
		
	}

	@Override
	public void removeCellEditorListener(CellEditorListener l) {
		this.field_listener.remove(l);
	}

	@Override
	public boolean shouldSelectCell(EventObject anEvent) {
		return true;
	}

	@Override
	public void cancelCellEditing() {}
	
	//метод вызывается когда таблица спрашивает завершено ли редатирование поля
	@Override
	public boolean stopCellEditing() {
		return false;
	}
	
	@Override
	public boolean isCellEditable(EventObject anEvent) {
		return true;
	}
	
}

