package order_class;

import java.awt.BorderLayout;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class JTreeViewPanel extends JPanel{
	private final static long serialVersionUID=1L;
	private JTreeView treeView;
	private JTreeModel treeModel;
	private Connection connection;
	
	public JTreeViewPanel(Connection connection){
		this.connection=connection;
		initComponents(connection);
	}
	
	private void initComponents(Connection connection){
		// create component's
		this.treeModel=new JTreeModel();
		this.treeView=new JTreeView(this.treeModel, connection);
		this.treeView.updateModel();
		JButton buttonSaveIntoDataBase=new JButton("<html>Сохранить <br> в базе</html>");
		JButton buttonLoadFromDataBase=new JButton("<html>Загрузить <br>из базы </html>");
		JButton buttonResetDataBase=new JButton("<html>Удалить <br>из базы</html>");
		JButton buttonUp=new JButton("Вверх");
		JButton buttonDown=new JButton("Вниз");
		// add listeners
		buttonSaveIntoDataBase.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonSaveIntoDataBase();
			}
		});
		buttonLoadFromDataBase.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonLoadFromDataBase();
			}
		});
		buttonResetDataBase.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonResetDataBase();
			}
		});
		buttonUp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonUp();
			}
		});
		buttonDown.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonDown();
			}
		});
		// placing components
		JPanel panelChange=new JPanel(new GridLayout(1,3));
		panelChange.add(buttonSaveIntoDataBase);
		panelChange.add(buttonLoadFromDataBase);
		panelChange.add(buttonResetDataBase);
		JPanel panelManager=new JPanel(new GridLayout(1,2));
		panelManager.add(buttonUp);
		panelManager.add(buttonDown);
		
		this.setLayout(new BorderLayout());
		this.add(new JScrollPane(this.treeView),BorderLayout.CENTER);
		this.add(panelChange,BorderLayout.NORTH);
		this.add(panelManager,BorderLayout.SOUTH);
		
	}
	
	private void onButtonSaveIntoDataBase(){
		try{
			this.treeModel.save(this.connection);
		}catch(Exception ex){
			System.err.println("JTreeViewPanel#onButtonSaveIntoDataBase");
		}
		//this.treeView.updateModel();
	}
	private void onButtonLoadFromDataBase(){
		main_loop:
		while(true){
			if(this.treeModel.isChanged()){
				if(JOptionPane.showInternalConfirmDialog(this, "Изменения будут удалены, уверены ?","Были изменения в данных",JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION){
					break main_loop;
				};
			};
			try{
				this.treeModel.load(this.connection);
			}catch(Exception ex){
				System.err.println("JTreeViewPanel#onButtonSaveIntoDataBase");
			}
			this.treeView.updateModel();
			break main_loop;
		}
	}
	private void onButtonResetDataBase(){
		if(JOptionPane.showInternalConfirmDialog(this, 
												 "Данные будут полностью обновлены, уверены ?",
												 "Перезапись данных из базы ",
												 JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION){
			this.treeView.resetDataIntoDataBase(this.connection);
		}else{
			
		}
	}
	private void onButtonUp(){
		this.treeView.selectionMoveUp();
	}
	private void onButtonDown(){
		this.treeView.selectionMoveDown();
	}
	
	@Override
	public void finalize(){
		try{
			this.connection.close();
		}catch(Exception ex){};
	}
	
	public boolean isChanged(){
		return this.treeModel.isChanged();
	}
}
