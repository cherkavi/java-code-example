import java.awt.Dialog;
import java.awt.FlowLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class DocumentModalDialogDemo {
  public static void main(String[] args) {
    final JFrame parent = new JFrame("Parent Frame");
    parent.setLayout(new FlowLayout());
    parent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    parent.setBounds(100, 100, 300, 200);
    parent.setVisible(true);
    JDialog dialog1 = new JDialog(parent, "Dialog1 - Modeless Dialog");
    dialog1.setBounds(200, 200, 300, 200);
    dialog1.setVisible(true);

    JDialog dialog2 = new JDialog(parent, "Dialog2 - Document-Modal Dialog",
        Dialog.ModalityType.DOCUMENT_MODAL);
    dialog2.setBounds(300, 300, 300, 200);

    JDialog dialog3 = new JDialog(dialog2, "Dialog3 - Modeless Dialog");
    dialog3.setBounds(400, 400, 300, 200);
    dialog3.setVisible(true);

    dialog2.setVisible(true);

  }
}