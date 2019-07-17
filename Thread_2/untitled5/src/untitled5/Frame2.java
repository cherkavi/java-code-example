package untitled5;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class resource
{
  // �����, ��� ��������� � �������� �� ������� ����� ����������� �������,
  //�� ���� ���� ������ ����� ��� ���������� ���� �� �������, �� ������ ������ ����� ������� ���� ������ �� �������� ������ � ���
  synchronized void put_data(String s)
  {
    System.out.println(s);
  }
}

class print_to_out implements Runnable
{
  resource object_resource=new resource();
  public String message;
  public boolean flag;
  // ����������� ��������� ������ � ��������� �����
  print_to_out(String s,int priority)
  {
    Thread t;
    this.message=s;
    // �������� ������ �� ������ ������� ������, ������� ��������� ����� Runnable, ��� ��� ��� �� �� � �����������
    this.flag=true;
    t=new Thread(this,"print_to_out");
    t.setPriority(priority);// ������������� ���������
    t.start();
  }
  // ��� �����, ������� ����� ��������� � ������
  public void run()
  {
    long counter=0;
    /*for(int i=0;i<10;i++)
    {
      System.out.println(this.message+"   "+i);
      try
      {
        Thread.sleep(1000);
      }
      catch(Exception e)
      {
        System.out.println("Error in sleep");
      };*/
    while(flag)
    {
      object_resource.put_data(this.message);
      counter++;
    }
    System.out.println("end thread: "+this.message+"  "+counter);
  }
  public void stop()
  {
     flag=false;
  }

}

public class Frame2 extends JFrame {
  private JPanel contentPane;
  private BorderLayout borderLayout1 = new BorderLayout();
  private JButton jButton1 = new JButton();

  /**Construct the frame*/
  public Frame2() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  /**Component initialization*/
  private void jbInit() throws Exception  {
    //setIconImage(Toolkit.getDefaultToolkit().createImage(Frame2.class.getResource("[Your Icon]")));
    contentPane = (JPanel) this.getContentPane();
    jButton1.setText("jButton1");
    jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        jButton1_mouseClicked(e);
      }
    });
    contentPane.setLayout(borderLayout1);
    this.setSize(new Dimension(400, 300));
    this.setTitle("Frame Title");
    contentPane.add(jButton1, BorderLayout.CENTER);
  }
  /**Overridden so we can exit when window is closed*/
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      System.exit(0);
    }
  }

  void jButton1_mouseClicked(MouseEvent e) {
       print_to_out thread_1,thread_2;

       // ������� ������ � ������� ���������� ��������� Runnable
       thread_1=new print_to_out("first thread",Thread.NORM_PRIORITY);
       thread_2=new print_to_out("second thread",Thread.MAX_PRIORITY);
       for(int i=1;i<=2;i++)
       {
          try
          {
            System.out.println("Main "+i);
            Thread.sleep(500);
          }
          catch(Exception e2)
          {
            System.out.println("Error in main, in sleep");
          }
       }
       thread_1.stop();
       thread_2.stop();
  }
}