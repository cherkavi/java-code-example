package com.cherkashyn.vitalii.indirector.workers.gui.swing_utility;

public interface ModalResultListener {
	public static enum Result{
		/** */
		Ok, 
		/** */
		Cancel;
	}
	
	/** child window send ModalResult */
	public void childWindowModalResult(Object value);
}
