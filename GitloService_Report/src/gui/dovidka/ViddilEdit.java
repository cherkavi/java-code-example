package gui.dovidka;
import gui.Position;
import gui.swing_table.DatabaseTableWrap;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

/** панель для редактирования данных из таблицы VIDDIL_JS */
public class ViddilEdit extends JPanel{
	private static final long serialVersionUID=1L;
	private JDialog parent;
	private DatabaseTableWrap table=null;
	private Connection connection;
	
	public ViddilEdit(JDialog parent,Connection connection){
		super();
		this.parent=parent;
		this.connection=connection;
		initComponents();
	}
	
	private void initComponents(){
		this.setLayout(new BorderLayout());
		JButton buttonClose=new JButton("Закончить редактирование данных ");
		this.add(buttonClose,BorderLayout.SOUTH);
		buttonClose.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonClose();
			}
		});
		try{
			this.table=new DatabaseTableWrap(connection.prepareStatement("select naiu, dm1, lt1, viddil from viddil_js where ul1=? and dm1=? and lt1=? and naiu=?"),
					new String[]{"улица","дом","бкв.","отдел"}, 
					new int[]{200,50,50,70},
					connection.createStatement().executeQuery("select ul1,dm1,lt1,naiu from viddil_js")
					);
			this.add(new JScrollPane(this.table.getTable()));
			this.table.getTable().addMouseListener(new MouseListener(){
				@Override
				public void mouseClicked(MouseEvent event) {
					if((event.getButton()==MouseEvent.BUTTON1)&&(event.getClickCount()>1)){
						ViddilEdit.this.onEditViddil();
					}
				}
				@Override
				public void mouseEntered(MouseEvent arg0) {
				}
				@Override
				public void mouseExited(MouseEvent arg0) {
				}
				@Override
				public void mousePressed(MouseEvent arg0) {
				}
				@Override
				public void mouseReleased(MouseEvent arg0) {
				}
				
			});
		}catch(Exception ex){
			System.err.println("ViddilEdit#initComponents: Exception:"+ex.getMessage());
		}
		
	}
	
	private void onButtonClose(){
		this.parent.dispose();
	}
	
	private void onEditViddil(){
		JDialog dialog=new JDialog(this.parent);
		ArrayList<ArrayList<Object>> selectedValue=this.table.getSelectedValues();
		ViddilReturnValue modalResult=new ViddilReturnValue();
		Integer selectedViddil=null;
		if (selectedValue.get(0).get(3)==null){
			selectedViddil=null;
		}else{
			selectedViddil=((Double)selectedValue.get(0).get(3)).intValue();
		}
		dialog.add(
					new ViddilEditSelector(dialog,
										   modalResult,
										   this.getAddressFromSelected(selectedValue),
										   selectedViddil
										   )
					);
		dialog.setModal(true);
		dialog.setModalityType(ModalityType.DOCUMENT_MODAL);
		dialog.setTitle("Выбор отделения");
		Position.setDialogToCenterBySize(dialog, 300, 200);
		dialog.setVisible(true);
		if(modalResult.isSelected()){
			System.out.println("Update");
			//connection.prepareStatement("select naiu, dm1, lt1, viddil from viddil_js where ul1=? and dm1=? and lt1=? and naiu=?"),
			ArrayList<ArrayList<Object>> list=this.table.getSelectedKeys();
			try{
				if(modalResult.getValue()!=null){
					String query="UPDATE VIDDIL_JS SET VIDDIL="+modalResult.getValue()+" WHERE ul1=? AND dm1=? AND lt1=?";
					PreparedStatement statement=this.connection.prepareStatement(query);
					statement.setObject(1, list.get(0).get(0));
					statement.setObject(2, list.get(0).get(1));
					statement.setObject(3, list.get(0).get(2));
					statement.executeUpdate();
					System.out.println(query);
					this.connection.commit();
				}else{
					String query="UPDATE VIDDIL_JS SET VIDDIL=null WHERE ul1=? AND dm1=? AND lt1=?";
					PreparedStatement statement=this.connection.prepareStatement(query);
					statement.setObject(1, list.get(0).get(0));
					statement.setObject(2, list.get(0).get(1));
					statement.setObject(3, list.get(0).get(2));
					statement.executeUpdate();
					System.out.println(query);
					this.connection.commit();
				}
				this.table.updateData(connection.createStatement().executeQuery("select ul1,dm1,lt1,naiu from viddil_js"));
			}catch(Exception ex){
				System.err.println("ViddilEdit#onEditViddil: "+ex.getMessage());
			}
		}
	}
	
	private String getAddressFromSelected(ArrayList<ArrayList<Object>> arrayList){
		if(arrayList.size()>0){
			return arrayList.get(0).get(0).toString()+arrayList.get(0).get(1).toString()+arrayList.get(0).get(2).toString();
		}else{
			return null;
		}
	}
	
}

