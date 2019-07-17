package com.cherkashyn.vitalii.barrette.gui.swing_utility;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 *  ModalPanel 
 */
public class ModalPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private ModalResultListener listener;
	
	private JDialog parentContainer;

	public ModalPanel(){
	}

	public ModalPanel(ModalResultListener listener){
		this.listener=listener;
	}
	
	public void setModalResultListener(ModalResultListener listener){
		this.listener=listener;
	}

	/**
	 * notification about Modal windows action 
	 * @param result
	 */
	protected void sendModalResultToParent(Object result){
		if(this.listener!=null){
			this.listener.childWindowModalResult(result);
		}
	}
	
	/**
	 * 
	 * @param dialog
	 */
	public void setParentDialog(JDialog dialog){
		this.parentContainer=dialog;
	}
	
	protected void closeModal(Object modalResult){
		this.sendModalResultToParent(modalResult);
		this.closeModal();
	}

	protected void closeModal(){
		if(this.parentContainer!=null){
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						parentContainer.dispose();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});		
		}
	}

}
