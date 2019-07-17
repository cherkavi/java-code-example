package com.cherkashin.vitaliy.rfid.web_server.client.view.edit;

import com.cherkashin.vitaliy.rfid.web_server.client.utility.ITableDataSelected;
import com.cherkashin.vitaliy.rfid.web_server.client.utility.RootComposite;
import com.cherkashin.vitaliy.rfid.web_server.client.view.MainMenu;
import com.cherkashin.vitaliy.rfid.web_server.client.view.edit.people_add.PeopleAdd;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.gwtext.client.core.Function;
import com.gwtext.client.core.NameValuePair;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.MessageBoxConfig;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Tool;
import com.gwtext.client.widgets.WaitConfig;
import com.gwtext.client.widgets.layout.VerticalLayout;

public class Edit extends Composite implements ITableDataSelected{
	private Edit_const constant=GWT.create(Edit_const.class);
	private IPeopleEditManagerAsync manager=GWT.create(IPeopleEditManager.class);
	private TablePeople table;
	
	public Edit(){
		Panel panel=new Panel();
		panel.setLayout(new VerticalLayout());
		panel.addTool(new Tool(Tool.CLOSE,new Function(){
			@Override
			public void execute() {
				onClose();
			}
		}));
		panel.setTitle(constant.title());

		this.table=new TablePeople(this.constant, this);
		this.table.setHeight(350);
		panel.add(this.table);

		Button buttonAdd=new Button(constant.buttonAdd());
		buttonAdd.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				onButtonAdd();
			}
		});

		Button buttonEdit=new Button(constant.buttonEdit());
		buttonEdit.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				onButtonEdit();
			}
		});

		Button buttonRemove=new Button(constant.buttonRemove());
		buttonRemove.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				onButtonRemove();
			}
		});
		
		HorizontalPanel panelAdd=new HorizontalPanel();
		panelAdd.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panelAdd.add(buttonAdd);
		panelAdd.add(buttonEdit);
		panelAdd.add(buttonRemove);
		panelAdd.setWidth(RootComposite.getWindowWidth()+"px");
		panel.add(panelAdd);

		/*Button buttonEdit=new Button(constant.buttonEdit());
		buttonEdit.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				onButtonEdit();
			}
		});
		
		HorizontalPanel panelEdit=new HorizontalPanel();
		panelEdit.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panelEdit.setWidth(RootComposite.getWindowWidth()+"px");
		panelEdit.add(buttonEdit);
		panel.add(panelEdit);*/
		
		this.initWidget(panel);
		this.setSize(RootComposite.getWindowWidth()+"px", RootComposite.getWindowHeight()+"px");
		this.updateList();
	}
	
	private void updateList(){
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
		this.manager.getAllPeople(new AsyncCallback<People[]>(){
			@Override
			public void onFailure(Throwable caught) {
				MessageBox.hide();
				MessageBox.alert("Server exchange Error");
			}

			@Override
			public void onSuccess(People[] result) {
				MessageBox.hide();
				if(result==null){
					MessageBox.alert("Server not response ");
				}else{
					Edit.this.table.updateModel(result);
				}
			}
		});
		
	}
	
	/** добавить */
	private void onButtonAdd(){
		 RootComposite.showView(new PeopleAdd(null, manager));
	}
	
	/** редактировать */
	private void onButtonEdit(){
		if(this.currentPeopleId!=null){
			RootComposite.showView(new PeopleAdd(Edit.this.currentPeopleId, Edit.this.manager));
		}
	}
	
	/** удалить */
	private void onButtonRemove(){
		if(this.currentPeopleId!=null){
			MessageBox.show(new MessageBoxConfig(){
				{
					this.setTitle(constant.titleRemove());
					this.setMsg(constant.messageRemove());
					this.setWidth(300);
					this.setButtons(new NameValuePair[]{new NameValuePair("yes",constant.buttonRemove()),
														new NameValuePair("no",constant.buttonCancel())});
					this.setCallback(new MessageBox.PromptCallback(){
						@Override
						public void execute(String btnID, String text) {
							if(btnID.equals("yes")){
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
								Edit.this.manager.removePeople((Integer)Edit.this.currentPeopleId,new AsyncCallback<String>() {
									@Override
									public void onFailure(Throwable caught) {
										MessageBox.hide();
										MessageBox.alert("Server Exchange Error");
									}
									@Override
									public void onSuccess(String result) {
										MessageBox.hide();
										Edit.this.updateList();
									}
								});
							}
							if(btnID.equals("no")){
								// user choose cancel case 
							}
						}
					});
				}
			});
		}
	}
	
	private void onClose(){
		RootComposite.showView(new MainMenu());
	}

	private Integer currentPeopleId=null;
	
	@Override
	public void selectedValue(String value) {
		this.currentPeopleId=Integer.parseInt(value);
	}
	
}
