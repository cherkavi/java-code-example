package jabber.gui.panel_recieve;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;

/** панель по логгированию принятых сообщений */
public class PanelRecieve extends JPanel{
	private final static long serialVersionUID=1L;
	
	/** панель по логгированию принятых сообщений */
	public PanelRecieve(String title){
		if(title!=null){
			this.setBorder(javax.swing.BorderFactory.createTitledBorder(title));
		}
		initComponents();
	}
	
	/** текстовое поле, которое содержит тесты принятых сообщений */
	private JTextArea textArea;
	
	private void initComponents(){
		this.setLayout(new GridLayout(1,1));
		textArea=new JTextArea();
		this.add(textArea);
	}
	
	/** очистить панель */
	public void clearHistory(){
		this.textArea.setText("");
	}
	
	/** добавить в панель вывода данных историю */
	public void addHistory(String recipient, String text){
		if(text!=null){
			if(recipient!=null){
				this.textArea.insert(recipient+" : "+text+"\n", 0);
			}else{
				this.textArea.insert(text+"\n", 0);
			}
		}
	}
	
}
