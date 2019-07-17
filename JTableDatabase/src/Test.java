import javax.swing.*;

import swing_table.DatabaseTableWrap;

import connector.Connector;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Test extends JFrame {
	private final static long serialVersionUID=1L;
	public static void main(String[] args){
		new Test();
	}
	
	private Connection connection;
	private DatabaseTableWrap table;
	private JToggleButton buttonUpdate;
	public Test(){
		super();
		this.setSize(400,300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.connection=Connector.getConnection("SYSDBA","masterkey","111");
		System.out.println("Connection:"+this.connection);
		try{
			this.initComponents();
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, ex.getMessage(),"Create Error",JOptionPane.ERROR_MESSAGE);
		}
		this.setVisible(true);
	}
	
	private void initComponents() throws Exception {
		// create component's
		JPanel panelMain=new JPanel(new BorderLayout());
		JButton buttonClose=new JButton("close");
		buttonUpdate=new JToggleButton("update");
		table=new DatabaseTableWrap(connection.prepareStatement("select ul1,dm1, lt1, naiu, viddil from viddil_js where ul1=? and dm1=? and lt1=? and naiu=?"),
				new String[]{"код ул.","дом","бкв.","улица","отдел"}, 
				new int[]{30,50,50,200,30},
				connection.createStatement().executeQuery("select ul1,dm1,lt1,naiu from viddil_js")
				);

		// add listener's
		buttonClose.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonClose();
			}
		});
		buttonUpdate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonUpdate();
			}
		});
		table.getTable().addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton()==MouseEvent.BUTTON3){
					onTableRightClick();
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
		// placing
		panelMain.add(buttonUpdate,BorderLayout.NORTH);
		panelMain.add(new JScrollPane(table.getTable()),BorderLayout.CENTER);
		panelMain.add(buttonClose,BorderLayout.SOUTH);
		this.getContentPane().add(panelMain);
	}
	
	/** реакция на закрытие окна */
	private void onButtonClose(){
		this.dispose();
	}

	/** реакция на обновление данных в таблице */
	private void onButtonUpdate(){
		try{
			if(this.buttonUpdate.isSelected()){
				this.table.updateData(connection.createStatement().executeQuery("select ul1,dm1,lt1,naiu from viddil_js where ul1<1000"));
			}else{
				this.table.updateData(connection.createStatement().executeQuery("select ul1,dm1,lt1,naiu from viddil_js"));
			}
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, "Data is not update","Error",JOptionPane.ERROR_MESSAGE);
		}
		
	}

	/** реакция на нажатие клавиши на таблице */
	private void onTableRightClick(){
		//System.out.println(this.table.getSelectedValues());
		System.out.println(this.table.getSelectedColumnValues(0));
	}

	@Override
	protected void finalize() throws Throwable {
		Connector.closeConnection(this.connection);
		super.finalize();
	}
}
