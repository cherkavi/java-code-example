package com.cherkashin.vitaliy.rfid.web_server.client;

import com.cherkashin.vitaliy.rfid.web_server.client.utility.RootComposite;

import com.cherkashin.vitaliy.rfid.web_server.client.view.MainMenu;
import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class RFID_WebServer implements EntryPoint {
	public void onModuleLoad() {
		RootComposite.setMain("main");
		RootComposite.showView(new MainMenu());
	}
}
