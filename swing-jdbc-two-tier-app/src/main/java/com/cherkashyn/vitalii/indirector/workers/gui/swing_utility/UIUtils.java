package com.cherkashyn.vitalii.indirector.workers.gui.swing_utility;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.apache.commons.lang3.StringUtils;

public class UIUtils {
	private final static String FIELDNAME_WIDTH="width";
	private final static String FIELDNAME_HEIGHT="height";
	
	
	/**
	 * move Window to center
	 * @param window
	 */
	public static void setToCenterOfScreen(Window window){
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation((dim.width-window.getWidth())/2, (dim.height-window.getHeight())/2);
	}
	

	/**
	 * move Window to center of another Window 
	 * @param parent - etalon window  
	 * @param dialog - window for move 
	 */
	public static void setToCenterOfAnother(Window parent, JDialog dialog) {
		Rectangle rectangle=parent.getBounds();
		
		int centerX=rectangle.x+rectangle.width/2;
		int centerY=rectangle.y+rectangle.height/2;
		
		dialog.setBounds(centerX-dialog.getWidth()/2, centerY-dialog.getHeight()/2, dialog.getWidth(), dialog.getHeight());
	}

	
	private static Window getContainerFromPanel(JPanel panel){
		Component parentComponent=panel;
		while( (parentComponent=parentComponent.getParent())!=null){
			if(parentComponent instanceof Window){
				return (Window)parentComponent;
			}
		}
		throw new IllegalArgumentException("check Parent component for injected into Window/Dialog");
	}
	
	
	private static Dimension getDimensionFromField(JPanel panel, String widthFieldName, String heightFieldName){
		int width=-1;
		int height=-1;
		for(Field field:panel.getClass().getDeclaredFields()){
			field.setAccessible(true);
			if(StringUtils.equalsIgnoreCase(field.getName(), widthFieldName)){
				try {
					width=field.getInt(panel);
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				}
			}
			if(StringUtils.equalsIgnoreCase(field.getName(), heightFieldName)){
				try {
					height=field.getInt(panel);
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				}
			}
		}
		
		if(width<0 && height<0){
			throw new IllegalArgumentException("can't read field with name 'width'/'height' from panel: "+panel);
		}
		return new Dimension(width, height);
	}

	
	/**
	 * show modal window 
	 * @param parent
	 * @param panel - {@link JPanel} - panel with width/height fields of Integer type 
	 * height/width - will try to detect as field of object 
	 */
	public static void showModal(final ModalPanel parent, final ModalPanel panel){
		showModal( parent, panel, getDimensionFromField(panel, FIELDNAME_WIDTH, FIELDNAME_HEIGHT), false);
	}
	
	/**
	 * show modal window 
	 * @param parent
	 * @param panel - {@link JPanel} - panel with width/height fields of Integer type 
	 * height/width - will try to detect as field of object 
	 */
	public static void showModal(final ModalPanel parent, final ModalPanel panel, boolean addRemoveBorder){
		showModal( parent, panel, getDimensionFromField(panel, FIELDNAME_WIDTH, FIELDNAME_HEIGHT), addRemoveBorder);
	}
	
	/**
	 * show modal window 
	 * @param parent
	 * @param panel
	 * @param dimension 
	 */
	public static void showModal(final ModalPanel parent, final ModalPanel panel, Dimension dimension){
		showModal(parent, panel, dimension, false);
	}

	/**
	 * 
	 * @param parent
	 * @param panel
	 * @param dimension
	 * @param addRemoveBorder
	 */
	public static void showModal(final ModalPanel parentPanel, final ModalPanel panel, Dimension dimension, boolean addRemoveBorder ){
		Window parentContainer=getContainerFromPanel(parentPanel);
		final JDialog dialog=new JDialog( parentContainer,ModalityType.DOCUMENT_MODAL);
		dialog.getContentPane().setLayout(new BorderLayout());
		// add source panel to center
		dialog.getContentPane().add(panel, BorderLayout.CENTER);
		
		if( addRemoveBorder ){
			// add panel with button close to 
			dialog.getContentPane().add(createBorderWithButtonClose(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					dialog.dispose();
				}
			}), BorderLayout.SOUTH);
		}
		
		dialog.setSize(dimension);
		dialog.setUndecorated(false);
		// dialog.setSize(dialog.getPreferredSize());
		// dialog.pack();
		
		if( parentPanel instanceof ModalResultListener ){
			((ModalPanel)panel).setModalResultListener((ModalResultListener)parentPanel);
		}
		panel.setParentDialog(dialog);
		
		// move dialog to center 
		// setToCenterOfScreen(dialog);
		setToCenterOfAnother(parentContainer, dialog);
		dialog.setVisible(true);
	}
	


	/**
	 * create border with button close
	 * @param listener
	 * @return
	 */
	private static JPanel createBorderWithButtonClose(ActionListener listener) {
		JPanel panel=new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JButton button=new JButton("X");
		button.addActionListener(listener);
		panel.add(button);
		return panel;
	}

	
}
