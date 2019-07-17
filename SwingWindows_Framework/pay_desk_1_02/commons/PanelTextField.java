package pay_desk.commons;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** панель, которая содержит необходимый набор функционала для отображения текста и кнопку с редактированием */
public class PanelTextField extends JPanel{
	private final static long serialVersionUID=1L;
	private JTextField textField;
	
	/** панель, которая содержит необходимый набор функционала для отображения текста и кнопку с редактированием */
	public PanelTextField(String title){
		initComponents(title);
	}
	
	/** инициализация компонентов */
	private void initComponents(String title){
		this.setLayout(new BorderLayout());
		this.setBorder(javax.swing.BorderFactory.createTitledBorder(title));
		textField=new JTextField();
		this.add(textField);
	}
	
	public void setText(String value){
		this.textField.setText(value);
	}

	public String getText(){
		return this.textField.getText();
	}

}
