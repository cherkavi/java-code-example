package example;

import java.awt.Container;
import java.awt.FlowLayout;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

public class MonthSpinner extends JFrame {

  public MonthSpinner() {
    super("Month Spinner");
    setSize(200, 100);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    Container c = getContentPane();
    c.setLayout(new FlowLayout(FlowLayout.LEFT, 4, 4));

    c.add(new JLabel("Expiration Date:"));
    Date today = new Date();
    // Start the spinner today, but don't set a min or max date
    // The increment should be a month
    JSpinner s = new JSpinner(new SpinnerDateModel(today, null, null,
        Calendar.MONTH));
    //JSpinner.DateEditor de = new JSpinner.DateEditor(s, "MM/yy");
    //s.setEditor(de);
    c.add(s);

    setVisible(true);
  }

  public static void main(String args[]) {
    new MonthSpinner();
  }
}