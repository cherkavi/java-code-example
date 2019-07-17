package untitled5;

import java.awt.*;
import javax.swing.JFrame;
import java.awt.event.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2007
 * Company:
 * @author
 * @version 1.0
 */

public class Frame1 extends JFrame {

  public Frame1() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    this.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        this_mouseClicked(e);
      }
    });
  }

  void this_mouseClicked(MouseEvent e) {

  }
}