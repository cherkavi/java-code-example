package com.cherkashin.vitaliy.rfid.web_server.client.view.edit.people_add;

import com.cherkashin.vitaliy.rfid.web_server.client.utility.ITableDataSelected;
import com.cherkashin.vitaliy.rfid.web_server.client.utility.RootComposite;
import com.cherkashin.vitaliy.rfid.web_server.client.view.edit.Card;
import com.cherkashin.vitaliy.rfid.web_server.client.view.edit.Edit;
import com.cherkashin.vitaliy.rfid.web_server.client.view.edit.IPeopleEditManagerAsync;
import com.cherkashin.vitaliy.rfid.web_server.client.view.edit.People;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.Function;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.MessageBoxConfig;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Tool;
import com.gwtext.client.widgets.WaitConfig;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.layout.VerticalLayout;

public class PeopleAdd extends Composite implements ITableDataSelected{
	private PeopleAdd_const constant=GWT.create(PeopleAdd_const.class);
	private TextField textSurname;
	private TextField textName;
	private TextField textFatherName;
	private TextField textCardNumber;
	
	private IPeopleEditManagerAsync manager;
	private TableCard table;
	/** объект, который подлежит редактированию */
	private People people=null;
	
	/** получить горизонтальную панель с элементом, выровненным по центру */
	private HorizontalPanel getHorizontalPanelWithElementOnCenter(Widget ... widgets){
		HorizontalPanel panelSurname=new HorizontalPanel();
		panelSurname.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		for(int counter=0;counter<widgets.length;counter++){
			panelSurname.add(widgets[counter]);
		}
		panelSurname.setWidth(RootComposite.getWindowWidth()+"px");
		return panelSurname;
	}
	
	public PeopleAdd(Integer peopleId, IPeopleEditManagerAsync manager){
		this.manager=manager;
		Panel panel=new Panel();
		panel.setLayout(new VerticalLayout());
		panel.addTool(new Tool(Tool.CLOSE,new Function(){
			@Override
			public void execute() {
				onClose();
			}
		}));
		panel.setTitle(constant.title());

		Label labelSurname=new Label(constant.surname());
		panel.add(getHorizontalPanelWithElementOnCenter(labelSurname));
		textSurname=new TextField();
		panel.add(getHorizontalPanelWithElementOnCenter(textSurname));
		
		Label labelName=new Label(constant.name());
		panel.add(getHorizontalPanelWithElementOnCenter(labelName));
		
		textName=new TextField();
		panel.add(getHorizontalPanelWithElementOnCenter(textName));

		Label labelFatherName=new Label(constant.fatherName());
		panel.add(getHorizontalPanelWithElementOnCenter(labelFatherName));
		
		textFatherName=new TextField();
		panel.add(getHorizontalPanelWithElementOnCenter(textFatherName));
		
		Label labelCardNumber=new Label(constant.cardNumber());
		panel.add(getHorizontalPanelWithElementOnCenter(labelCardNumber));

		textCardNumber=new TextField();
		textCardNumber.setReadOnly(true);
		panel.add(getHorizontalPanelWithElementOnCenter(textCardNumber));
		
		// таблица, которая содержит последние проведенные карты без привязки к пользователю
		if(this.people==null){
			table=new TableCard(this.constant,this);
			table.setHeight(140);
			panel.add(table);
		}
		
		Button buttonSave=new Button(constant.buttonSave());
		buttonSave.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				onButtonSave();
			}
		});
		Button buttonCancel=new Button(constant.buttonCancel());
		buttonCancel.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event){
				onButtonCancel();
			}
		});
		panel.add(getHorizontalPanelWithElementOnCenter(buttonSave, buttonCancel));
		
		this.initWidget(panel);
		this.setSize(RootComposite.getWindowWidth()+"px", RootComposite.getWindowHeight()+"px");

		if(peopleId!=null){
			MessageBox.show(new MessageBoxConfig(){
				{
					this.setWidth(200);
					this.setProgressText("Server Exchange");
					this.setWait(true);
					this.setWaitConfig(new WaitConfig(){
						{
							this.setInterval(200);
						}
					});
				}
			});
			this.manager.getPeople(peopleId, new AsyncCallback<People>(){
				@Override
				public void onFailure(Throwable caught) {
					MessageBox.hide();
					MessageBox.alert("Server exchange Error");
				}

				@Override
				public void onSuccess(People result) {
					MessageBox.hide();
					if(result==null){
						MessageBox.alert("Server not response ");
					}else{
						PeopleAdd.this.people=result;
						PeopleAdd.this.textName.setValue(result.getName());
						PeopleAdd.this.textSurname.setValue(result.getSurname());
						PeopleAdd.this.textFatherName.setValue(result.getFatherName());
						PeopleAdd.this.textCardNumber.setValue(result.getCardNumber());
						PeopleAdd.this.table.setVisible(false);
					}
				}
			});
		}else{
			MessageBox.show(new MessageBoxConfig(){
				{
					this.setWait(true);
					this.setProgressText("Server exchange");
					this.setWidth(200);
					this.setWaitConfig(new WaitConfig(){
						{
							this.setIncrement(200);
						}
					});
				}
			});
			this.manager.getLastCardWithoutUser(new AsyncCallback<Card[]>(){
				@Override
				public void onFailure(Throwable caught) {
					MessageBox.hide();
					MessageBox.alert("Server exchange Error ");
				}

				@Override
				public void onSuccess(Card[] result) {
					MessageBox.hide();
					if(result==null){
						MessageBox.alert("server does not response");
					}else{
						PeopleAdd.this.table.updateModel(result);
					}
				}
			});
			
		}
		
	}
	
	private void onButtonSave(){
		while(true){
			if((this.textSurname.getText()==null)||(this.textSurname.getText().trim().equals(""))){
				MessageBox.alert(constant.alertEmptySurname());
				this.textSurname.focus();
				break;
			}
			if((this.textName.getText()==null)||(this.textName.getText().trim().equals(""))){
				MessageBox.alert(constant.alertEmptyName());
				this.textName.focus();
				break;
			}
			if((this.textCardNumber.getText()==null)||(this.textCardNumber.getText().trim().equals(""))){
				MessageBox.alert(constant.alertEmptyCardNumber());
				break;
			}
			
			MessageBox.show(new MessageBoxConfig(){
				{
					this.setWait(true);
					this.setProgressText("Server exchange");
					this.setWaitConfig(new WaitConfig(){
						{
							this.setInterval(200);
						}
					});
				}
			});
			
			this.manager.savePeople((this.people==null)?null:this.people.getId(), 
									this.textName.getText(), 
									this.textSurname.getText(), 
									this.textFatherName.getText(), 
									this.textCardNumber.getText(), 
									new AsyncCallback<String>(){
				@Override
				public void onFailure(Throwable caught) {
					MessageBox.hide();
					MessageBox.alert("Server Error");
				}

				@Override
				public void onSuccess(String result) {
					MessageBox.hide();
					if(result==null){
						onClose();
					}else{
						MessageBox.alert("Save Error",result);
					}
				}
			});
			break;
		}
	}

	private void onButtonCancel(){
		onClose(); 
	}
	
	private void onClose(){
		RootComposite.showView(new Edit());
	}

	@Override
	public void selectedValue(String value) {
		this.textCardNumber.setValue(value);
	}
}
