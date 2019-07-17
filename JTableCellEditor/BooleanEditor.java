/** объект, который представляет из себя редактор для поля JCheckBox */
class BooleanEditor extends AbstractCellEditor implements TableCellEditor{
	private static final long serialVersionUID=1L;
	private JCheckBox checkBox=new JCheckBox();
	{
		this.checkBox.setHorizontalAlignment(SwingConstants.CENTER);
		this.checkBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonClicked();
			}
		});
	}
	/** была нажата кнопка выбора - остановить редактирование */
	private void onButtonClicked(){
		this.stopCellEditing();
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, 
												 Object value,
												 boolean isSelected, 
												 int row, 
												 int column) {
		if(value instanceof Boolean){
			this.checkBox.setSelected((Boolean)value);
			return this.checkBox;
		}else{
			System.err.println("Boolean Editor is not for 'null' values");
			return null;
		}
	}
	
	@Override
	public Object getCellEditorValue() {
		return this.checkBox.isSelected();
	}
	
}
