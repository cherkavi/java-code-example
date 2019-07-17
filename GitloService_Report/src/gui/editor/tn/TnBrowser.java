package gui.editor.tn;
import gui.Position;
import gui.editor.Editor;
import gui.editor.database.HibernateConnection;
import gui.editor.database.gui.JTableWrap;
import gui.editor.database.wrap.Tn;
import gui.table_column_render.ColumnSimple;
import gui.table_column_render.ICellValue;

import javax.swing.*;

import org.hibernate.Session;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.event.*;


/** */
public class TnBrowser extends JPanel{
	private final static long serialVersionUID=1L;
	private JDialog parent;
	private HibernateConnection connection;
	private Integer month;
	private Integer year;
	private Object idNpom;
	private JTableWrap table;
	/** запрос к базе для отбора уникальных номеров [month, year, npom.id]*/
	private String queryUniqueId="select tn.id from tn inner join pay_kind on pay_kind.kod=tn.id_pay_kind where tn.month_value=? and tn.year_value=? and tn.id_npom=?"; 
	/** запрос к базе для получения кол-ва */
	private String queryCount="select count(tn.id) from tn inner join pay_kind on pay_kind.kod=tn.id_pay_kind where tn.month_value=? and tn.year_value=? and tn.id_npom=?";
	/** запрос к базе для получения текущего значения [month, year, npom.id, tn.id]*/
	private String queryRow="select pay_kind.name, tn.price,tn.quantity,tn.amount from tn inner join pay_kind on pay_kind.kod=tn.id_pay_kind where tn.month_value=? and tn.year_value=? and tn.id_npom=? and tn.id=?";
	
	/**
	 * Броузер, который отображает возможность ввода дополнительной информации по договорам 
	 * @param parent - родительский объект
	 * @param connection - соединение с базой данных 
	 * @param month - месяц, по которому происходит отбор
	 * @param year - год, по которому происходит отбор
	 * @param idNpom - код из таблицы NPOM 
	 */
	public TnBrowser(JDialog parent, HibernateConnection connection, int month, int year, Object idNpom){
		super();
		this.connection=connection;
		this.month=month;
		this.year=year;
		this.idNpom=idNpom;
		initComponents();
	}
	
	/** первоначальная инициализация компонентов */
	private void initComponents(){
		// create component's
		JButton buttonInsert=new JButton("Добавить");
		JButton buttonEdit=new JButton("Редактировать");
		try{
			this.table=new JTableWrap(this.connection.getConnection(),
					  this.queryUniqueId,
					  this.queryRow,
					  this.queryCount,
					  new String[]{"Вид","Цена","Кол-во","Сумма"},
					  new int[]{250,70,50,100},
					  new ICellValue[]{new ColumnSimple("NAME"),new ColumnSimple("PRICE"),new ColumnSimple("QUANTITY"),new ColumnSimple("AMOUNT")}
					  );
			this.table.refreshData(month,year,idNpom);
		}catch(Exception ex){
			System.err.println("TnBrowser#initComponents Exception:"+ex.getMessage());
		}
		// add listener's
		buttonInsert.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonInsert();
			}
		});
		buttonEdit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonEdit();
			}
		});
		// placing component's
		Information information=new Information(this.connection,this.idNpom);
		JPanel panelInformation=information.getPanel(this.month,this.year);
		//JPanel panelLabel=new JPanel(new FlowLayout(FlowLayout.CENTER));
		//panelLabel.add(new JLabel(Utility.monthName[this.month-1]+"  "+this.year));
		JPanel panelManager=Editor.getTitledPanel(new JComponent[]{buttonInsert, buttonEdit}, new int[]{150,150}, "Редактирование");

		this.setLayout(new BorderLayout());
		this.add(panelInformation,BorderLayout.NORTH);
		this.add(new JScrollPane(this.table),BorderLayout.CENTER);
		this.add(panelManager,BorderLayout.SOUTH);
	}
	
	/** reaction on striking button insert */
	private void onButtonInsert(){
		System.out.println("Insert");
		JDialog dialog=new JDialog(parent,"Добавление данных",Dialog.ModalityType.APPLICATION_MODAL);
		TnEditor editor=new TnEditor(dialog,this.connection, this.month, this.year,this.idNpom);
		dialog.add(editor);
		Position.setDialogToCenterBySize(dialog,350,500);
		//dialog.setSize(350,500);
		dialog.pack();
		dialog.setVisible(true);
		if(editor.isReturnOk()){
			// user commit edit
			Tn tn=editor.getTnFromVisualComponents();
			String saveTn=this.saveTn(tn);
			if(saveTn!=null){
				JOptionPane.showMessageDialog(this, "Save Error"+saveTn, "Error", JOptionPane.ERROR_MESSAGE);
			}else{
				// OK;
			}
			this.table.refreshData(month,year,idNpom);
		}else{
			// user cancel edit
		}
	}
	
	private String saveTn(Tn tn){
		String returnValue=null;
		Session session=null;
		try{
			session=this.connection.openSession();
			session.beginTransaction();
			session.saveOrUpdate(tn);
			session.getTransaction().commit();
		}catch(Exception ex){
			System.err.println("TnBrowser#saveTn Exception:"+ex.getMessage());
			returnValue=ex.getMessage();
		}finally{
			try{
				session.close();
			}catch(Exception ex){};
		}
		return returnValue;
	}
	
	/** reaction on striking button edit */
	private void onButtonEdit(){
		if(this.table.getSelectedObject()!=null){
			System.out.println("Edit");
			JDialog dialog=new JDialog(parent,"Редактирование данных",Dialog.ModalityType.APPLICATION_MODAL);
			TnEditor editor=new TnEditor(dialog,this.connection, this.month, this.year,this.idNpom,this.table.getSelectedObject());
			dialog.add(editor);
			Position.setDialogToCenterBySize(dialog, 350, 500);
			//dialog.setSize(350,500);
			dialog.pack();
			if(editor.setTnToVisualComponents(this.table.getSelectedObject())){
				dialog.setVisible(true);
				if(editor.isReturnOk()){
					// user commit edit
					Tn tn=editor.getTnFromVisualComponents();
					String saveTn=this.saveTn(tn);
					if(saveTn!=null){
						JOptionPane.showMessageDialog(this, "Save Error"+saveTn, "Error", JOptionPane.ERROR_MESSAGE);
					}else{
						// OK;
					}
					this.table.refreshData(month,year,idNpom);
				}else{
					// user cancel edit
				}
			}else{
				JOptionPane.showMessageDialog(this, "Ошибка редактирования");
			}
		}else{
			JOptionPane.showMessageDialog(this, "Сделайте свой выбор");
		}
	}
	
}
