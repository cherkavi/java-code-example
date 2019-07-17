package com.cherkashin.vitaliy.rfid.web_server.client.view.show;

import java.util.Date;

import com.cherkashin.vitaliy.rfid.web_server.client.utility.RootComposite;
import com.cherkashin.vitaliy.rfid.web_server.client.view.MainMenu;
import com.cherkashin.vitaliy.rfid.web_server.client.view.show.result.ResultWindow;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.gwtext.client.core.Function;
import com.gwtext.client.widgets.DatePicker;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Tool;
import com.gwtext.client.widgets.event.DatePickerListenerAdapter;
import com.gwtext.client.widgets.layout.VerticalLayout;

public class Show extends Composite{
	private Show_const constant=GWT.create(Show_const.class);
	
	public Show(){
		Panel panel=new Panel();
		panel.setLayout(new VerticalLayout());
		panel.addTool(new Tool(Tool.CLOSE,new Function(){
			@Override
			public void execute() {
				onClose();
			}
		}));
		panel.setTitle(constant.title());

		HorizontalPanel panelDate=new HorizontalPanel();
		panelDate.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panelDate.setWidth(RootComposite.getWindowWidth()+"px");
		panel.add(panelDate);
		
		DatePicker dateBegin=new DatePicker();
		dateBegin.setTodayText(constant.dateBegin());
		dateBegin.addListener(new DatePickerListenerAdapter(){
			@Override
			public void onSelect(DatePicker dataPicker, Date date) {
				onSetDateBegin(date);
			}
		});
		panelDate.add(dateBegin);
		
		DatePicker dateEnd=new DatePicker();
		dateEnd.setTodayText(constant.dateEnd());
		dateEnd.addListener(new DatePickerListenerAdapter(){
			@Override
			public void onSelect(DatePicker dataPicker, Date date) {
				onSetDateEnd(date);
			}
		});
		panelDate.add(dateEnd);
		
		Button buttonShow=new Button(constant.buttonShow());
		buttonShow.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				onButtonShow();
			}
		});
		buttonShow.setWidth("100%");
		panel.add(buttonShow);
		this.initWidget(panel);
		this.setSize(RootComposite.getWindowWidth()+"px", RootComposite.getWindowHeight()+"px");
	}
	
	
	private Date dateBeginValue=new Date();
	private Date dateEndValue=dateBeginValue;

	private void onSetDateBegin(Date dateBegin){
		//System.out.println(dateBegin.toString());
		dateBeginValue=dateBegin;
	}

	private void onSetDateEnd(Date dateEnd){
		//System.out.println(dateEnd.toString());
		dateEndValue=dateEnd;
	}
	private void onButtonShow(){
		new ResultWindow(this.dateBeginValue, this.dateEndValue);
	}

	private void onClose(){
		RootComposite.showView(new MainMenu());
	}
	
}
