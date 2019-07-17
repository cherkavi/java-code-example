package com.cherkashin.vitaliy.rfid.web_server.client.view.show.result;

import java.util.Date;

import com.cherkashin.vitaliy.rfid.web_server.client.view.show.IShowResult;
import com.cherkashin.vitaliy.rfid.web_server.client.view.show.IShowResultAsync;
import com.cherkashin.vitaliy.rfid.web_server.client.view.show.Result;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.MessageBoxConfig;
import com.gwtext.client.widgets.WaitConfig;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;

public class ResultWindow extends Window{
	private Result_const constants=GWT.create(Result_const.class);
	private IShowResultAsync manager=GWT.create(IShowResult.class);
	private ResultTable table;
	

	public ResultWindow(Date dateBegin, Date dateEnd){
		this.setTitle(constants.title()+": ");  
        this.setClosable(true);  
        this.setWidth(800);  
        this.setHeight(500);  
        this.setPlain(true);  
        this.setLayout(new BorderLayout());
        BorderLayoutData centerData = new BorderLayoutData(RegionPosition.CENTER);  
        // centerData.setMargins(3, 0, 3, 3);  
        table=new ResultTable(constants);
        this.add(table, centerData);
        this.setCloseAction(Window.HIDE);
        this.show();
        
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
		manager.getResult(dateBegin, dateEnd, new AsyncCallback<Result[]>(){
			@Override
			public void onFailure(Throwable caught) {
				MessageBox.hide();
				MessageBox.alert("Server exchange Error");
			}

			@Override
			public void onSuccess(Result[] result) {
				MessageBox.hide();
				if(result==null){
					MessageBox.alert("Server answer ERROR");
				}else{
					ResultWindow.this.table.updateModel(result);
				}
			}
		});
	}
	
}
