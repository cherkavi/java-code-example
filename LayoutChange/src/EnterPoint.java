import javax.swing.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
/** 
 * класс, который отображает возможность 
 * */
public class EnterPoint extends JFrame {
	private final static long serialVersionUID=1L;
	
	public static void main(String[] args){
		new EnterPoint(4);
	}
	
	private PanelControl panelControl;
	private int elementCount;
	
	public EnterPoint(int elementCount){
		super("Изменение на лету менеджеров расположения элементов");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.elementCount=elementCount;
		initComponents();
		this.setSize(300,200);
		this.setVisible(true);
	}
	
	private void initComponents(){
		// create component's
		panelControl=new PanelControl(elementCount);
		JButton buttonGridLayout=new JButton("GridLayout");
		JButton buttonFlowLayout=new JButton("FlowLayout");
		JButton buttonBorderLayout=new JButton("BorderLayout");
		// add listener's
		buttonGridLayout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonGrid();
			}
		});
		buttonFlowLayout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonFlow();
			}
		});
		buttonBorderLayout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonBorder();
			}
		});
		// placing
		JPanel panel_main=new JPanel(new BorderLayout());
		panel_main.add(buttonGridLayout,BorderLayout.WEST);
		panel_main.add(buttonFlowLayout,BorderLayout.EAST);
		panel_main.add(buttonBorderLayout,BorderLayout.SOUTH);
		panel_main.add(panelControl,BorderLayout.CENTER);
		this.getContentPane().add(panel_main);
	}
	
	private void onButtonGrid(){
		panelControl.setManagerLayout(new GridLayout(elementCount,1));
	}
	private void onButtonFlow(){
		panelControl.setManagerLayout(new FlowLayout());
	}
	private void onButtonBorder(){
		System.out.println("BorderLayout");
		panelControl.setManagerLayout(new BorderLayout());
	}
}
