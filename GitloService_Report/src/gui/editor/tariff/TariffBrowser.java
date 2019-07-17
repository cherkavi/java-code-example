package gui.editor.tariff;
import gui.Position;
import gui.editor.Editor;
import gui.editor.database.HibernateConnection;
import gui.editor.database.gui.JTableWrap;
import gui.editor.database.wrap.Tariff;
import gui.table_column_render.ColumnSimple;
import gui.table_column_render.ICellValue;

import javax.swing.*;

import org.hibernate.Session;

import database.Utility;

import java.io.Serializable;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.*;

/** панель, которая отвечает за редактирование тарифов по указанному месяцу и году 
 * <li> добавление </li>
 * <li> редактирование </li>
 * <li> удаление </li>
 * */
public class TariffBrowser extends JPanel{
	private final static long serialVersionUID=1L;

	private HibernateConnection hibernateConnection;
	private int month;
	private int year;
	private JDialog parent;
	private JTableWrap table;
	
	public TariffBrowser(JDialog parent, HibernateConnection hibernateConnection, int month, int year){
		super();
		this.parent=parent;
		this.hibernateConnection=hibernateConnection;
		this.month=month;
		this.year=year;
		try{
			this.table=new JTableWrap(this.hibernateConnection.getConnection(),
					  "SELECT ID FROM TARIFF WHERE MONTH_VALUE="+this.month+" AND YEAR_VALUE="+this.year,
					  "SELECT NAME, PRICE, IS_ACTIVE FROM TARIFF INNER JOIN PAY_KIND ON TARIFF.ID_PAY_KIND=PAY_KIND.KOD WHERE MONTH_VALUE="+this.month+" AND YEAR_VALUE="+this.year+" AND ID=? ",
					  "SELECT count(ID) FROM TARIFF WHERE MONTH_VALUE="+this.month+" AND YEAR_VALUE="+this.year,
					  new String[]{"Наименование","Цена","Валидность"},
					  new int[]{250,60,80},
					  new ICellValue[]{new ColumnSimple("NAME"),new ColumnSimple("PRICE"),new ColumnSimple("IS_ACTIVE")}
					  );
			this.table.refreshData();
		}catch(Exception ex){
			System.err.println("Price constructor JTableWrap Exception:"+ex.getMessage());
		};
		this.initComponents();
	}
	
	/** первоначальная инициализация всех компонентов */
	private void initComponents(){
		// create element
		JLabel label=new JLabel("<html><b>"+Utility.monthName[month-1]+"  "+year+"</b></html>");
		
		JScrollPane tableScroll=new JScrollPane(this.table);
		JButton buttonEdit=new JButton("Редактировать"); 
		JButton buttonInsert=new JButton("Добавить"); 
		JButton buttonRemove=new JButton("Удалить"); 
		// add listener's
		this.table.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if((arg0.getButton()==MouseEvent.BUTTON1)&&(arg0.getClickCount()>1)){
					onButtonEdit();
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
		buttonEdit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonEdit();
			}
		});
		buttonInsert.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonInsert();
			}
		});
		buttonRemove.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonRemove();
			}
		});
		// placing
		JPanel panelManager=Editor.getTitledPanel(new JComponent[]{buttonEdit,buttonInsert,buttonRemove}, new int[]{140,140,140}, "Редактирование");
		JPanel panelLabel=new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelLabel.add(label);
		panelLabel.setBorder(javax.swing.BorderFactory.createTitledBorder("Период"));
		this.setLayout(new BorderLayout());
		this.add(panelLabel,BorderLayout.NORTH);
		this.add(tableScroll,BorderLayout.CENTER);
		this.add(panelManager,BorderLayout.SOUTH);
	}
	
	/** редактирование */
	private void onButtonEdit(){
		if(this.table.getSelectedObject()!=null){
			JDialog dialog=new JDialog(parent,"Добавление данных",Dialog.ModalityType.APPLICATION_MODAL);
			TariffEditor editor=new TariffEditor(dialog,this.hibernateConnection,this.table.getSelectedObject());
			dialog.add(editor);
			Position.setDialogToCenterBySize(dialog, 250, 300);
			//dialog.setSize(250,300);
			dialog.pack();
			dialog.setVisible(true);
			if(editor.isReturnOk()){
				// user commit edit
				Tariff tariff=editor.getTariffFromVisualComponents();
				tariff.setMonth(this.month);
				tariff.setYear(this.year);
				String saveTariff=this.saveTariff(tariff);
				if(saveTariff!=null){
					JOptionPane.showMessageDialog(this, "Save Error"+saveTariff, "Error", JOptionPane.ERROR_MESSAGE);
				}else{
					// OK;
				}
				this.table.refreshData();
				//this.getParent().repaint();
			}
			
		}else{
			JOptionPane.showMessageDialog(this, "Сделайте свой выбор","Не выбран объект",JOptionPane.WARNING_MESSAGE);
		}
	}
	
	/** добавление */
	private void onButtonInsert(){
		JDialog dialog=new JDialog(parent,"Добавление данных",Dialog.ModalityType.APPLICATION_MODAL);
		TariffEditor editor=new TariffEditor(dialog,this.hibernateConnection);
		dialog.add(editor);
		Position.setDialogToCenterBySize(dialog, 250, 300);
		//dialog.setSize(250,300);
		dialog.pack();
		dialog.setVisible(true);
		if(editor.isReturnOk()){
			// user commit edit
			Tariff tariff=editor.getTariffFromVisualComponents();
			tariff.setMonth(this.month);
			tariff.setYear(this.year);
			String saveTariff=this.saveTariff(tariff);
			if(saveTariff!=null){
				JOptionPane.showMessageDialog(this, "Save Error"+saveTariff, "Error", JOptionPane.ERROR_MESSAGE);
			}else{
				// OK;
			}
			this.table.refreshData();
		}else{
			// user cancel edit
		}
	}
	
	/** удаление */
	private void onButtonRemove(){
		if(this.table.getSelectedObject()!=null){
			 if(JOptionPane.showConfirmDialog(this, "Уверены в удалении ?", "Предупреждение", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				 // удаление данных 
				 String removeTariff=removeTariff(this.table.getSelectedObject());
				 if(removeTariff==null){
					 
				 }else{
					 JOptionPane.showMessageDialog(this, "Remove Error"+removeTariff, "Error", JOptionPane.ERROR_MESSAGE);
				 }
				 this.table.refreshData();				 
			 }else{
				 // отмена удаления 
			 }
		}
	}
	
	private String removeTariff(Object selectedId){
		String returnValue=null;
		Session session=null;
		try{
			session=this.hibernateConnection.openSession();
			session.beginTransaction();
			session.delete(session.get(Tariff.class, (Serializable)selectedId));
			session.getTransaction().commit();
		}catch(Exception ex){
			returnValue=ex.getMessage();
		}finally{
			try{
				session.close();
			}catch(Exception ex2){};
		}
		return returnValue;
	}
	
	private String saveTariff(Tariff tariff){
		String returnValue=null;
		Session session=null;
		try{
			session=this.hibernateConnection.openSession();
			session.beginTransaction();
			session.saveOrUpdate(tariff);
			session.getTransaction().commit();
		}catch(Exception ex){
			returnValue=ex.getMessage();
		}finally{
			try{
				session.close();
			}catch(Exception ex2){};
		}
		return returnValue;
	}
}
