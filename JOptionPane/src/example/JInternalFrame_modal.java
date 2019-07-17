package example;

import java.awt.Component;
import java.awt.Container;
import java.awt.KeyboardFocusManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.swing.Icon;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.PopupFactory;
/*
 * JInternalFrame_modal.java
 *
 * Created on 5 травня 2008, 22:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Technik
 */
public class JInternalFrame_modal {
    
    /** Creates a new instance of JInternalFrame_modal */
    public static int get_option_dialog(Component parentComponent,
                                       Object message,
                                       String title, 
                                       int optionType,
                                       int messageType, 
                                       Icon icon,
                                       Object[] options, 
                                       Object initialValue) {
        /*JOptionPane pane = new JOptionPane(message, 
                                           messageType,
                                           optionType, 
                                           icon, 
                                           options, 
                                           initialValue);*/
        JOptionPane pane=new JOptionPane();
        JInternalFrame dialog=pane.createInternalFrame(parentComponent,title);
        pane.add(new JPanel());
        //;
        //pane.putClientProperty(PopupFactory.forceHeavyWeightPopupKey,
        //      Boolean.TRUE);
        pane.putClientProperty(new StringBuffer("__force_heavy_weight_popup__"),
              Boolean.TRUE);
        
        Component fo = KeyboardFocusManager.getCurrentKeyboardFocusManager().
                getFocusOwner();
                
        //pane.setInitialValue(initialValue);

        //JInternalFrame dialog =pane.createInternalFrame(parentComponent, title);
        //pane.selectInitialValue();
        dialog.setVisible(true);

	/* Since all input will be blocked until this dialog is dismissed,
	 * make sure its parent containers are visible first (this component
	 * is tested below).  This is necessary for JApplets, because
	 * because an applet normally isn't made visible until after its
	 * start() method returns -- if this method is called from start(),
	 * the applet will appear to hang while an invisible modal frame
	 * waits for input.
	 */
	if (dialog.isVisible() && !dialog.isShowing()) {
	    Container parent = dialog.getParent();
	    while (parent != null) {
		if (parent.isVisible() == false) {
		    parent.setVisible(true);
		}
		parent = parent.getParent();
	    }
	}

        // Use reflection to get Container.startLWModal.
        try {
            Object obj;
            obj = AccessController.doPrivileged(new ModalPrivilegedAction(
                    Container.class, "startLWModal"));
            if (obj != null) {
                ((Method)obj).invoke(dialog, (Object[])null);
            }
        } catch (IllegalAccessException ex) {
        } catch (IllegalArgumentException ex) {
        } catch (InvocationTargetException ex) {
        }

        if (parentComponent instanceof JInternalFrame) {
            try {
                ((JInternalFrame)parentComponent).setSelected(true);
            } catch (java.beans.PropertyVetoException e) {
            }
        }

        Object selectedValue = pane.getValue();

        if (fo != null && fo.isShowing()) {
            fo.requestFocus();
        }
        if (selectedValue == null) {
            return -1;
        }
        if (options == null) {
            if (selectedValue instanceof Integer) {
                return ((Integer)selectedValue).intValue();
            }
            return -1;
        }
        for(int counter = 0, maxCounter = options.length;
            counter < maxCounter; counter++) {
            if (options[counter].equals(selectedValue)) {
                return counter;
            }
        }
        return -1;
    }
    private static class ModalPrivilegedAction implements PrivilegedAction {
        private Class clazz;
        private String methodName;

        public ModalPrivilegedAction(Class clazz, String methodName) {
            this.clazz = clazz;
            this.methodName = methodName;
        }

        public Object run() {
            Method method = null;
            try {
                method = clazz.getDeclaredMethod(methodName, (Class[])null);
            } catch (NoSuchMethodException ex) {
            }
            if (method != null) {
                method.setAccessible(true);
            }
            return method;
        }
    }
    
}
