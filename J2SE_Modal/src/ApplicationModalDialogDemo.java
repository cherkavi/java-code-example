import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class ApplicationModalDialogDemo {

  public static void main(String[] args) {
    final JFrame parent1 = new JFrame("Parent Frame 1");
    parent1.setLayout(new FlowLayout());
    parent1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JButton button = new JButton("Application modal dialog");
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JDialog dialog = new JDialog(parent1, "Application-Modal Dialog",
            Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setBounds(200, 150, 200, 150);
        dialog.setVisible(true);
      }
    });
    parent1.add(button);
    parent1.setBounds(100, 100, 200, 150);
    parent1.setVisible(true);

    JFrame parent2 = new JFrame("Parent Frame 2");
    parent2.setBounds(500, 100, 200, 150);
    parent2.setVisible(true);
  }
}