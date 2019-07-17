package com.cherkashin.vitaliy.rfid.web_server.client.view;

import com.cherkashin.vitaliy.rfid.web_server.client.utility.RootComposite;
import com.cherkashin.vitaliy.rfid.web_server.client.view.edit.Edit;
import com.cherkashin.vitaliy.rfid.web_server.client.view.show.Show;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MainMenu extends Composite{
	private MainMenu_const constant=GWT.create(MainMenu_const.class);
	
	public MainMenu(){
		VerticalPanel panel=new VerticalPanel();
		panel.setSpacing(10);
		panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.setTitle(constant.title());
		
		Button buttonShow=new Button(constant.buttonShow());
		buttonShow.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				onButtonShow();
			}
		});
		panel.add(buttonShow);
		
		Button buttonEdit=new Button(constant.buttonEdit());
		buttonEdit.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				onButtonEdit();
			}
		});
		panel.add(buttonEdit);
		// panel.setSize(RootComposite.getWindowWidth(), RootComposite.getWindowHeight());
		this.initWidget(panel);
	}
	
	private void onButtonShow(){
		RootComposite.showView(new Show());
	}
	
	private void onButtonEdit(){
		RootComposite.showView(new Edit()); 
	}
	
}
