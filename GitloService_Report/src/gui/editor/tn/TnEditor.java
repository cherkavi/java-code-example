package gui.editor.tn;
import gui.Position;
import gui.editor.Editor;
import gui.editor.database.HibernateConnection;
import gui.editor.database.wrap.PayKind;
import gui.editor.database.wrap.Tariff;
import gui.editor.database.wrap.Tn;
import gui.editor.tariff.TariffBrowser;

import javax.swing.*;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import database.Utility;

import java.awt.Dialog;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class TnEditor extends JPanel{
	private final static long serialVersionUID=1L;
	private JDialog parent;
	private HibernateConnection connection;
	private Integer month;
	private Integer year;
	private Object npomId;
	private Object tnId;
	/** вид платежа */
	private JComboBox kind;
	/** ед. измерения */
	private JLabel unit;
	/** кол-во */
	private JFormattedTextField quantity;
	/** цена */
	private JComboBox price;
	/** query for fill JComboBox */
	private PreparedStatement statementForPrice;
	/** query for get Tariff from Kind and Price */
	private PreparedStatement statementForTariff;
	/** query for get AREN from NPOM  */
	private PreparedStatement statementForNpom;
	
	private DecimalFormat amountFormat=new DecimalFormat("#.00");
	private DecimalFormat quantityFormat=new DecimalFormat("#.00");
	/** сумма */
	private JFormattedTextField amount;
	/** показания на начало */
	private JFormattedTextField markBegin;
	/** показания на конец */
	private JFormattedTextField markEnd;
	/** отображать предупреждение о пустом Price */
	private boolean showPriceWarnings=true;
	
	/** edit */
	public TnEditor(JDialog parent, HibernateConnection connection, int month, int year, Object npomId,Object tnId){
		this.parent=parent;
		this.connection=connection;
		this.npomId=npomId;
		this.tnId=tnId;
		this.month=month;
		this.year=year;
		try{
			this.statementForPrice=connection.getConnection().prepareStatement("select PRICE from tariff where month_value="+month+" and year_value="+year+" and id_pay_kind=?");
			this.statementForTariff=connection.getConnection().prepareStatement("select id from tariff where is_active>0 and month_value="+month+" and year_value="+year+" and id_pay_kind=? and PRICE=?");
			this.statementForNpom=connection.getConnection().prepareStatement("select * from npom where npom.kod_pom=?");
		}catch(Exception ex){
			System.err.println("TnEditor constructor Exception:"+ex.getMessage());
		}
		this.initComponents(npomId);
	}

	/** insert */
	public TnEditor(JDialog parent, HibernateConnection connection, int month, int year, Object npomId){
		this.parent=parent;
		this.connection=connection;
		this.npomId=npomId;
		this.month=month;
		this.year=year;
		try{
			this.statementForPrice=connection.getConnection().prepareStatement("select PRICE from tariff where month_value="+month+" and year_value="+year+" and id_pay_kind=?");
			this.statementForTariff=connection.getConnection().prepareStatement("select id from tariff where is_active>0 and month_value="+month+" and year_value="+year+" and id_pay_kind=? and PRICE=?");
			this.statementForNpom=connection.getConnection().prepareStatement("select * from npom where npom.kod_pom=?");
		}catch(Exception ex){
			System.err.println("TnEditor constructor Exception:"+ex.getMessage());
		}
		this.initComponents(npomId);
		this.onChangeKind();
	}
	
	private void initComponents(Object npomId){
		// create element
		JButton buttonSave=new JButton("Сохранить");
		JButton buttonCancel=new JButton("Отменить");
		JButton buttonEditKind=new JButton("...");
		JButton buttonCalculate=new JButton(" ");
		this.kind=new JComboBox();
		JPanel panelKind=Editor.getTitledPanel(this.kind, "Вид платежа");
		this.price=new JComboBox();
		JPanel panelPrice=Editor.getTitledPanel(this.price, "Цена");
		this.unit=new JLabel();
		JPanel panelUnit=Editor.getTitledPanel(this.unit, "Ед.Изм.");

		DecimalFormatSymbols symbols=new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');

		quantityFormat.setGroupingUsed(false);
		quantityFormat.setDecimalFormatSymbols(symbols);
		this.quantity=new JFormattedTextField(quantityFormat);
		JPanel panelQuantity=Editor.getTitledPanel(this.quantity, "Кол-во");

		amountFormat.setGroupingUsed(false);
		amountFormat.setDecimalFormatSymbols(symbols);
		this.amount=new JFormattedTextField(amountFormat);
		JPanel panelAmount=Editor.getTitledPanel(this.amount, "Сумма");
		
		this.markBegin=new JFormattedTextField(quantityFormat);
		JPanel panelMarkBegin=Editor.getTitledPanel(this.markBegin, "Показатель в начале периода");
		this.markEnd=new JFormattedTextField(quantityFormat);
		JPanel panelMarkEnd=Editor.getTitledPanel(this.markEnd, "Показатель в конце периода");
		Utility.fill_combobox_from_table_from_field(this.connection.getConnection(), this.kind, "PAY_KIND", "NAME","KOD");
		// add listener
		buttonSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonSave();
			}
		});
		buttonCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonCancel();
			}
		});
		buttonEditKind.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonEditKind();
			}
		});
		this.kind.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onChangeKind();
			}
		});
		this.quantity.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("changed");
			}
		});
		buttonCalculate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				onButtonCalculate();
			}
		});
		// placing components
		Information information=new Information(this.connection,npomId);
		JPanel panelInformation=information.getPanel(this.month,this.year);
		panelInformation.setBorder(javax.swing.BorderFactory.createTitledBorder("Текущая запись:"));
		GroupLayout groupLayout=new GroupLayout(this);
		this.setLayout(groupLayout);
		GroupLayout.SequentialGroup groupLayoutHorizontal=groupLayout.createSequentialGroup();
		GroupLayout.SequentialGroup groupLayoutVertical=groupLayout.createSequentialGroup();
		groupLayout.setVerticalGroup(groupLayoutVertical);
		groupLayout.setHorizontalGroup(groupLayoutHorizontal);
		
		groupLayoutHorizontal.addGroup(groupLayout.createParallelGroup()
									   .addComponent(panelInformation)
									   .addGroup(groupLayout.createSequentialGroup()
											   	 .addComponent(panelKind)
											   	 .addComponent(buttonEditKind,20,20,20)
									   			 )
									   .addGroup(groupLayout.createSequentialGroup()
											   	 .addComponent(panelPrice)
											   	 .addComponent(panelUnit)
											   	 .addComponent(panelQuantity)
											   	 .addComponent(buttonCalculate)
											   	 .addComponent(panelAmount)
											     )
									   .addGroup(groupLayout.createSequentialGroup()
											   	 .addComponent(panelMarkBegin)
											   	 .addComponent(panelMarkEnd)
											   	 )
									   .addGroup(groupLayout.createSequentialGroup()
											   	 .addGap(70)
											   	 .addComponent(buttonSave)
											   	 .addGap(10)
											   	 .addComponent(buttonCancel)
											   	 )
									   );
		groupLayoutVertical.addComponent(panelInformation);
		groupLayoutVertical.addGroup(groupLayout.createParallelGroup()
									 .addComponent(panelKind)
									 .addComponent(buttonEditKind)
									 );
		groupLayoutVertical.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
			   	 					 .addComponent(panelPrice)
			   	 					 .addComponent(panelUnit)
			   	 					 .addComponent(panelQuantity)
			   	 					 .addComponent(buttonCalculate)
			   	 					 .addComponent(panelAmount)
								     );
		groupLayoutVertical.addGroup(groupLayout.createParallelGroup()
			   	 					 .addComponent(panelMarkBegin)
			   	 					 .addComponent(panelMarkEnd)
				 					 );
		groupLayoutVertical.addGroup(groupLayout.createParallelGroup()
			   	 					 .addComponent(buttonSave)
			   	 					 .addComponent(buttonCancel)
	     							 );
		
	}
	
	/** получить объект из визуальных компонентов */
	public Tn getTnFromVisualComponents(){
		PayKind payKind=this.getSelectedPayKind();
		Float price=Float.parseFloat(((String)this.price.getSelectedItem()).replaceAll(",", "."));
		Tariff tariff=this.getSelectedTariff(payKind,price);
		int idArn=this.getArnIdFromNpom(this.npomId);
		Tn returnValue=new Tn();
		if(this.tnId!=null){
			if(this.tnId instanceof Double){
				returnValue.setId( ((Double)this.tnId).intValue()  );
			}
			if(this.tnId instanceof Float){
				returnValue.setId( ((Float)this.tnId).intValue()  );
			}
			if(this.tnId instanceof Integer){
				returnValue.setId( ((Integer)this.tnId));
			}
			if(this.tnId instanceof Long){
				returnValue.setId( ((Integer)this.tnId));
			}
			
		}
		returnValue.setIdNpom( ((Double)this.npomId).intValue());
		returnValue.setIdArn(idArn);
		returnValue.setIdPayKind(payKind.getId());
		returnValue.setIdTariff(tariff.getId());
		returnValue.setPrice(price);
		returnValue.setQuantity(Float.parseFloat(this.quantity.getText().replaceAll(",", ".")));
		returnValue.setAmount(Float.parseFloat(this.amount.getText().replaceAll(",", ".")));
		returnValue.setMonthValue(this.month);
		returnValue.setYearValue(this.year);
		try{
			returnValue.setMarkBegin( new Float(Float.parseFloat(this.markBegin.getText().replaceAll(",", "."))).intValue());
		}catch(Exception ex){};
		try{
			returnValue.setMarkEnd( new Float(Float.parseFloat(this.markEnd.getText().replaceAll(",", "."))).intValue());
		}catch(Exception ex){};
		return returnValue;
	}
	
	/** Установить на основании Tn необходимые визуальные компоненты 
	 * @return true - успешная установка в визуальные компоненты на основании TN.ID
	 * false - ошибка установки параметров  
	 * */
	public boolean setTnToVisualComponents(Object tnId){
		boolean returnValue=false;
		Session session=null;
		try{
			session=this.connection.openSession();
			Tn tn=(Tn)session.createCriteria(Tn.class).add(Restrictions.eq("id", tnId)).uniqueResult();
			if(tn!=null){
				this.quantity.setValue((Float)tn.getQuantity());
				this.amount.setValue((Float)tn.getAmount());
				this.markBegin.setValue((Integer)tn.getMarkBegin());
				this.markEnd.setValue((Integer)tn.getMarkEnd());
				// установка PayKind
				PayKind payKind=(PayKind)session.createCriteria(PayKind.class).add(Restrictions.eq("id", tn.getIdPayKind())).uniqueResult();
				if(payKind!=null){
					this.showPriceWarnings=false;
					this.kind.setSelectedItem(payKind.getName());
					this.onChangeKind();
					for(int counter=0;counter<this.price.getItemCount();counter++){
						try{
							if(  Float.parseFloat(this.price.getItemAt(counter).toString())==tn.getPrice()){
								this.price.setSelectedIndex(counter);
								returnValue=true;
								break;
							}
						}catch(Exception ex){
						}
					}
					this.showPriceWarnings=true;
					if(this.price.getSelectedIndex()<0){
						throw new Exception("Error set Price");
					}
				}else{
					throw new Exception("PayKind is null");
				}
			}else{
				throw new Exception("Tn is null");
			}
		}catch(Exception ex){
			System.err.println("TnEditor setTnToVisualComponents Exception:"+ex.getMessage());
		}finally{
			try{
				session.close();
			}catch(Exception ex2){};
		}
		return returnValue;
	}
	
	private Integer getArnIdFromNpom(Object npomId){
		Integer returnValue=null;
		ResultSet rs=null;
		try{
			this.statementForNpom.setObject(1, npomId);
			rs=this.statementForNpom.executeQuery();
			if(rs.next()){
				Double value=rs.getDouble("AREN");
				returnValue=value.intValue();
			}
		}catch(Exception ex){
			System.err.println("TnEditor#getArnIdFromNpom Exception:"+ex.getMessage());
		}finally{
			try{
				rs.close();
			}catch(Exception ex){};
		}
		return returnValue;
	}
	
	private void onButtonEditKind(){
		JDialog modal=new JDialog(this.parent,"Таблица тарифов",Dialog.ModalityType.APPLICATION_MODAL);
		modal.add(new TariffBrowser(modal, this.connection,this.month,this.year));
		Position.setDialogToCenterBySize(modal, 450, 370);
		//modal.setSize(450,370);
		modal.setVisible(true);
		Utility.fill_combobox_from_table_from_field(this.connection.getConnection(), this.kind, "PAY_KIND", "NAME","KOD");
	}
	
	private void onButtonSave(){
		if(    !((String)this.price.getSelectedItem()).trim().equals("")
			&& !(this.amount.getText().trim().equals(""))
			&& !(this.quantity.getText().trim().equals(""))
		   ){
			this.buttonOk=true;
			System.out.println("Button Save");
			this.parent.setVisible(false);
		}else{
			JOptionPane.showMessageDialog(this, "Объект не заполнен");
		}
	}
	
	private void onButtonCancel(){
		this.buttonOk=false;
		System.out.println("Button Cancel");
		this.parent.setVisible(false);
	}

	private void onChangeKind(){
		PayKind currentKind=this.getSelectedPayKind();
		this.price.removeAllItems();
		if(currentKind!=null){
			this.unit.setText(currentKind.getUnit());
			ResultSet rs=null;
			try{
				this.statementForPrice.setObject(1, new Integer(currentKind.getId()));
				rs=this.statementForPrice.executeQuery();
				Utility.fill_combobox_from_resultset(price, rs, "PRICE", true);
				if(price.getModel().getSize()==1){
					if(this.showPriceWarnings==true){
						JOptionPane.showMessageDialog(this, "По данному критерию \n"+this.kind.getSelectedItem()+"\n"+Utility.monthName[this.month-1]+" "+this.year+"\n нет данных по цене");
					}
				}
			}catch(Exception ex){
				JOptionPane.showMessageDialog(this, "Error fill data");
			}finally{
				try{
					rs.close();
				}catch(Exception ex){};
			}
			
		}else{
			System.err.println("getSelectedPayKind Exception: ");
		}

	}
	
	private boolean buttonOk=false;

	public boolean isReturnOk(){
		return this.buttonOk;
	}
	
	/** получить объект с выделенным Kind*/
	public PayKind getSelectedPayKind(){
		PayKind returnValue=null;
		Session session=null;
		try{
			session=this.connection.openSession();
			returnValue=(PayKind)session.createCriteria(PayKind.class).add(Restrictions.eq("name", (String)this.kind.getSelectedItem())).uniqueResult();
		}catch(Exception ex){
			System.err.println("Selected Pay Kind:"+ex.getMessage());
		}finally{
			try{
				session.close();
			}catch(Exception ex){};
		}
		return returnValue;
	}
	/** получить объект с выделенным Tariff на основании Kind и Price*/
	public Tariff getSelectedTariff(PayKind payKind,float price){
		Tariff returnValue=null;
		Session session=null;
		try{
			int tariffId=0;
			this.statementForTariff.setObject(1, payKind.getId());
			this.statementForTariff.setObject(2, price);
			ResultSet rs=this.statementForTariff.executeQuery();
			if(rs.next()){
				tariffId=rs.getInt(1);
			}
			rs.close();
			session=this.connection.openSession();
			returnValue=(Tariff)session.createCriteria(Tariff.class)
			                            .add(Restrictions.eq("id", new Integer(tariffId)))
			                            .uniqueResult();
		}catch(Exception ex){
			System.err.println("Selected Pay Kind:"+ex.getMessage());
		}finally{
			try{
				session.close();
			}catch(Exception ex){};
		}
		return returnValue;
	}
	
	private void onButtonCalculate(){
		try{
			this.amount.setValue(new Float(Float.parseFloat(this.price.getSelectedItem().toString())*Float.parseFloat(this.quantity.getText())));
		}catch(Exception ex){
		}
	}
}
