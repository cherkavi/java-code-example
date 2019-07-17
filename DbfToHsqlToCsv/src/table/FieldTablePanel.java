package table;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.*;

import common.Field;

import java.awt.event.*;

/** панель, которая содержит таблицу с полями и кнопками управления этими полями */
public class FieldTablePanel extends JPanel{
	private final static long serialVersionUID=1L;
	private JTable table;
	private FieldTableModel model;
	
	public FieldTablePanel(){
		super();
		initComponents();
	}
	
	/** инициализация компонентов */
	private void initComponents(){
		this.setLayout(new BorderLayout());
		// create
		this.table=new JTable();
		this.table.setDefaultRenderer(Object.class, new FieldTableCellRenderer());
		this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.table.setDefaultEditor(Object.class, new FieldTableCellEditor());
		this.add(new JScrollPane(this.table),BorderLayout.CENTER);
		JButton buttonUp=new JButton("UP");
		buttonUp.setToolTipText("CTRL-UP");
		JButton buttonDown=new JButton("Down");
		buttonDown.setToolTipText("CTRL-DOWN");
		// add listener's
		buttonUp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				onButtonUp();
			}
		});
		buttonDown.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				onButtonDown();
			}
		});
		this.table.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				if((e.getKeyCode()==KeyEvent.VK_UP)&& ((e.getModifiers()&KeyEvent.CTRL_MASK)>0) ){
					int selectedrow=FieldTablePanel.this.table.getSelectedRow();
					onButtonUp();
					FieldTablePanel.this.table.getSelectionModel().setSelectionInterval(selectedrow, selectedrow);
				}
				if((e.getKeyCode()==KeyEvent.VK_DOWN)&& ((e.getModifiers()&KeyEvent.CTRL_MASK)>0) ){
					int selectedrow=FieldTablePanel.this.table.getSelectedRow();
					onButtonDown();
					FieldTablePanel.this.table.getSelectionModel().setSelectionInterval(selectedrow, selectedrow);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
		// placing
		JPanel panelManager=new JPanel(new GridLayout(2,1));
		panelManager.add(buttonUp);
		panelManager.add(buttonDown);

		this.add(this.table);
		//this.add(panelManager,BorderLayout.EAST);
	}
	
	public void setTableModel(FieldTableModel model){
		this.model=model;
		this.table.setModel(model);
	}
	
	/** выделенное переместить вверх */
	public void selectionMoveUp(){
		
		if(this.model!=null){
			this.model.moveUp(this.getSelection());
		}
		
		
	}

	/** выделенное переместить вниз */
	public void selectionMoveDown(){
		if(this.model!=null){
			this.model.moveDown(this.getSelection());
		}
	}
	
	private Field getSelection(){
		try{
			return (Field)this.table.getValueAt(this.table.getSelectedRow(), 0);
		}catch(Exception ex){
			return null;
		}
		
	}
	
	private void onButtonUp(){
		int selectedrow=FieldTablePanel.this.table.getSelectedRow();
		selectionMoveUp();
		FieldTablePanel.this.table.getSelectionModel().setSelectionInterval(selectedrow, selectedrow);
		this.table.getParent().invalidate();
		this.table.invalidate();
	}
	
	private void onButtonDown(){
		selectionMoveDown();
		this.table.getParent().invalidate();
		this.table.invalidate();
	}
	
	/** получить SQL запрос на основании расположения полей в таблице и возможного условия
	 * @param tableName - имя таблицы в базе данных 
	 * @param whereString - если равен null - ничего не добавляется в часть WHERE
	 * @param isDistinct - нужно ли отбирать только уникальные записи 
	 * */
	public String getSqlQueryByFieldsPosition(String tableName, String whereString, boolean isDistinct){
		return this.model.getQueryByData(tableName, whereString,isDistinct);
	}
	
	/** получить кол-во полей из модели */
	public int getFieldCount(){
		return this.model.getSelectedFieldCount();
	}
	/** получить имя поля из модели */
	public String getFieldName(int row){
		try{
			return ((Field)this.model.getValueAt(row, 0)).getSqlFieldName();
		}catch(Exception ex){
			return "";
		}
	}
	
	public String getSelectedFieldName(int index){
		return this.model.getSelectedFieldName(index); 
	}
}
