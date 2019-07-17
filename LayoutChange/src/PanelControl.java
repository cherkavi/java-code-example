import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.*;
import java.awt.event.*;

/** панель, у которой будет изменяться менеджер расположения */
public class PanelControl extends JPanel{
	private static final long serialVersionUID=1L;
	@SuppressWarnings("unused")
	private int elementCount=4;
	
	/**
	 * панель, у которой будет изменяться менеджер расположения  
	 * @param elementCount - кол-во элементов, которые будут расположены на панели 
	 */
	public PanelControl(int elementCount){
		super();
		initComponents(elementCount);
	}
	
	public void setManagerLayout(LayoutManager layout){
		this.setLayout(layout);
		this.revalidate();
		this.repaint();
	}
	
	private void initComponents(int count){
		this.setLayout(new GridLayout(1,count));
		for(int counter=0;counter<count;counter++){
			JButton button=new JButton(Integer.toString(counter));
			final int value=counter;
			button.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					System.out.println(":"+value+": ");
				}
			});
			this.add(button);
		}
	}
}
