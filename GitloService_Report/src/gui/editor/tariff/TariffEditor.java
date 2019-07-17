package gui.editor.tariff;
import gui.editor.database.HibernateConnection;
import gui.editor.database.wrap.PayKind;
import gui.editor.database.wrap.Tariff;

import javax.swing.*;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import database.Utility;

import java.awt.event.*;
import java.text.DecimalFormat;

public class TariffEditor extends JPanel{
	private final static long serialVersionUID=1L;
	private JDialog parent;
	private HibernateConnection connection;
	private Object uniqueId;
	
	/** панель для добавления данных 
	 * @param parent - родительская панель
	 * @param connection - соединение с базой данных 
	 */
	public TariffEditor(JDialog parent,HibernateConnection connection){
		super();
		this.parent=parent;
		this.connection=connection;
		this.initComponents(null);
	}
	
	/** панель для редактирования данных 
	 * @param parent - родительская панель 
	 * @param connection - соединение с базой данных 
	 * @param uniqueId - уникальный идентификатор объекта
	 */
	public TariffEditor(JDialog parent,HibernateConnection connection,Object uniqueId){
		super();
		this.uniqueId=uniqueId;
		this.parent=parent;
		this.connection=connection;
		this.initComponents(uniqueId);
	}
	
	public Tariff getTariffFromVisualComponents(){
		Tariff returnValue=null;
		Session session=null;
		if(uniqueId!=null){
			// edit
			try{
				session=this.connection.openSession();
				Object value=session.createCriteria(Tariff.class).add(Restrictions.eq("id", uniqueId)).uniqueResult();
				if(value==null){
					System.err.println("Object is not loaded from Base:"+uniqueId);
				}else{
					returnValue=(Tariff)value;
					returnValue.setIdPayKind(this.getPayKindFromComboBox(this.tariff).getId());
					returnValue.setIsActive((this.valid.isSelected()?1:0));
					returnValue.setMonth(0);
					returnValue.setPrice(Float.parseFloat(this.price.getText().replaceAll(",", ".")));
				}
			}catch(Exception ex){
				System.err.println("Tariff load Exception by unique Kod:"+uniqueId+" Exception ex:"+ex.getMessage());
			}finally{
				try{
					session.close();
				}catch(Exception ex){};
			}
		}else{
			// insert
			returnValue=new Tariff();
			returnValue.setIdPayKind(this.getPayKindFromComboBox(this.tariff).getId());
			returnValue.setIsActive((this.valid.isSelected()?1:0));
			returnValue.setMonth(0);
			returnValue.setPrice(Float.parseFloat(this.price.getText().replaceAll(",", ".")));
			returnValue.setYear(0);
		}
		return returnValue;
	}
	
	private void setPayKindToCombobox(JComboBox combobox,int payKod){
		combobox.setSelectedItem(Utility.getObjectFromTable(this.connection.getConnection(), "PAY_KIND", "KOD", new Integer(payKod), "NAME"));
	}

	private PayKind getPayKindFromComboBox(JComboBox combobox){
		PayKind returnValue=null;
		Session session=null;
		try{
			session=this.connection.openSession();
			returnValue=(PayKind)session.createCriteria(PayKind.class).add(Restrictions.eq("name", combobox.getSelectedItem())).uniqueResult();
		}catch(Exception ex){
			System.err.println("TariffEditor getPayKindFromComboBox Exception:"+ex.getMessage());
		}finally{
			try{
				session.close();
			}catch(Exception ex){};
		}
		return returnValue;
	}
	
	public void setTariffToVisualComponents(Tariff value){
		if(value!=null){
			this.setPayKindToCombobox(tariff, value.getIdPayKind());
			this.price.setValue(new Float(value.getPrice()));
			this.valid.setSelected(value.getIsActive()>0);
		}else{
			System.err.println("setTariffToVisualComponents Null pointer");
		}
	}
	
	private DecimalFormat priceFormat=new DecimalFormat("#.00");
	private JComboBox tariff;
	private JCheckBox valid;
	private JFormattedTextField price;
	
	private Tariff getTariffFromObject(Object uniqueId){
		Tariff returnValue=null;
		Session session=null;
		try{
			session=this.connection.openSession();
			Object object=session.createCriteria(Tariff.class).add(Restrictions.eq("id", (Integer)uniqueId)).uniqueResult();
			System.out.println("getTariffFromObject:"+object);
			returnValue=(Tariff)object;
		}catch(Exception ex){
			System.err.println("getTariffFromObject Exception:"+ex.getMessage());
		}finally{
			try{
				session.close();
			}catch(Exception ex){};
		}
		return returnValue; 
	}
	
	/** инициализация компонентов 
	 * @param - uniqueId - уникальный идентификатор объекта,
	 * <li> !=null - edit </li>
	 * <li> ==null - insert</li> 
	 * */
	private void initComponents(Object uniqueId){
		// create element
		tariff=new JComboBox();
		Utility.fill_combobox_from_table_from_field(this.connection.getConnection(), tariff, "PAY_KIND", "NAME","KOD");
		valid=new JCheckBox("Действующий");
		price=new JFormattedTextField(priceFormat);
		JButton buttonOk=new JButton("Сохранить");
		JButton buttonCancel=new JButton("Отменить");
		if(uniqueId==null){
			// insert value
			this.tariff.setSelectedIndex(0);
		}else{
			// edit value
			Tariff tariffValue=this.getTariffFromObject(uniqueId);
			this.setTariffToVisualComponents(tariffValue);
		}
		
		// add listener
		buttonOk.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonOk();
			}
		});
		buttonCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonCancel();
			}
		});
		// placing 
		GroupLayout groupLayout=new GroupLayout(this);
		this.setLayout(groupLayout);
		GroupLayout.SequentialGroup groupLayoutHorizontal=groupLayout.createSequentialGroup();
		GroupLayout.SequentialGroup groupLayoutVertical=groupLayout.createSequentialGroup();
		groupLayout.setVerticalGroup(groupLayoutVertical);
		groupLayout.setHorizontalGroup(groupLayoutHorizontal);
		
		
		groupLayoutHorizontal.addGroup(groupLayout.createParallelGroup()
									   .addComponent(this.tariff)
									   .addGroup(groupLayout.createSequentialGroup()
											   	 .addComponent(this.price)
											   	 .addComponent(this.valid)
											     )
									   .addGroup(groupLayout.createSequentialGroup()
											   	 .addComponent(buttonOk)
											   	 .addComponent(buttonCancel)
											     )
									   );
		groupLayoutVertical.addComponent(this.tariff);
		groupLayoutVertical.addGroup(groupLayout.createParallelGroup()
									 .addComponent(this.price)
									 .addComponent(this.valid)
									 );
		groupLayoutVertical.addGroup(groupLayout.createParallelGroup()
				 					 .addComponent(buttonOk)
				 					 .addComponent(buttonCancel)
				 					);
	}
	
	private boolean returnOk=false;
	
	public boolean isReturnOk(){
		return returnOk;
	}
	
	private void onButtonOk(){
		returnOk=true;
		parent.setVisible(false);
	}
	private void onButtonCancel(){
		returnOk=false;
		parent.setVisible(false);
	}
}
