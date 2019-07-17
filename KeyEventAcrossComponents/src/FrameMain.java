import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FrameMain extends JFrame implements KeyListener{
	/** */
	private static final long serialVersionUID = 1L;

	private void debug(String information){
		System.out.println("DEBUG: "+information);
	}
	
	private void error(String information){
		System.out.println("ERROR: "+information);
	}
	
	public static void main(String[] args){
		//Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new FrameMain("Frame main");
            }
        });
	}
	
	
	private JTextField field_edit_main;
	
	public FrameMain(String caption){
		super(caption);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(400,400);
		this.getContentPane().add(initComponents());
		this.getRootPane().addKeyListener(this);
		
		// add listener before all event
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher(){
			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				if(e.getID()==KeyEvent.KEY_RELEASED){
					if(e.getKeyCode()==KeyEvent.VK_ENTER){
						System.out.println(">>>>");
					}
					System.out.println("dispatchKeyEvent:"+e.getKeyChar());
				}
				// try changing this return value				
				return true;
			}
		});

		// add listener after all event
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventPostProcessor(new KeyEventPostProcessor(){
			@Override
			public boolean postProcessKeyEvent(KeyEvent e) {
				if(e.getID()==KeyEvent.KEY_TYPED){
					System.out.println("postProcessKeyEvent:"+e.getKeyChar());
				}
				return false;
			}
		});
		
		this.pack();
		this.setVisible(true);
		
	}
	
	private JPanel initComponents(){
		JPanel return_value=new JPanel(new BorderLayout());
		JLabel label=new JLabel("this is example for KeyListener across another component's");
		return_value.add(label,BorderLayout.NORTH);
		
		JPanel panel_edit=new JPanel(new BorderLayout());
		panel_edit.setBorder(javax.swing.BorderFactory.createTitledBorder("another text"));
		JTextField field_edit=new JTextField();
		field_edit.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
		
		panel_edit.add(field_edit,BorderLayout.NORTH);
		JButton decorate_button=new JButton("decorate button");
		decorate_button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(FrameMain.this, "show, how get KeyEvent");
			}
		});
		panel_edit.add(decorate_button,BorderLayout.CENTER);
		return_value.add(panel_edit,BorderLayout.CENTER);
		
		JPanel panel_edit_main=new JPanel(new BorderLayout());
		panel_edit_main.setBorder(javax.swing.BorderFactory.createTitledBorder("text main"));
		field_edit_main=new JTextField();
		panel_edit_main.add(field_edit_main, BorderLayout.SOUTH);
		return_value.add(panel_edit_main,BorderLayout.SOUTH);
		return return_value;
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
		debug("KeyTyped:"+e.getKeyChar());
	}
}

