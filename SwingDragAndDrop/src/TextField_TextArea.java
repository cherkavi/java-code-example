import javax.swing.*;
import java.awt.BorderLayout;

public class TextField_TextArea {
	
	private static void simpleExample(){
		JFrame frame=new JFrame("Drag and Drop example ");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panelMain=new JPanel(new BorderLayout());
		
		JTextField textField=new JTextField();
		textField.setDragEnabled(true);
		textField.setDropMode(DropMode.INSERT);
		
		JTextArea textArea=new JTextArea();
		textArea.setDragEnabled(true);
		textArea.setDropMode(DropMode.INSERT);
		
		panelMain.add(textField,BorderLayout.NORTH);
		panelMain.add(textArea,BorderLayout.CENTER);
		
		frame.getContentPane().add(panelMain);
		frame.setSize(200,200);
		frame.pack();
		frame.setVisible(true);
	}
	
	
	public static void main(String[] args){
		simpleExample();
	}
}
