package install;

import install.show_password.Password;
import install.temp_directory.TempDirectory;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class EnterPoint extends JFrame {
	private final static long serialVersionUID=1L;
	
	public static void main(String[] args){
		new EnterPoint();
	}
	
	private JDesktopPane desktop;
	
	public EnterPoint(){
		super("Программа инсталлирования комплекса ПрАТ УкрЕСКА");
		this.setState(JFrame.NORMAL);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimension = toolkit.getScreenSize();
		this.setSize(dimension);
		desktop=new JDesktopPane();
		this.getContentPane().setLayout(new BorderLayout());
		
		this.add(desktop,BorderLayout.CENTER);
		this.showTempDirectory();
		
		JPanel panelTop=new JPanel();
		panelTop.setLayout(new GridLayout(3,1));
		
		JLabel top1=new JLabel("Система Финансово-Аналитического учета",SwingConstants.CENTER);
		Font newLabelFont=new Font(top1.getFont().getName(),top1.getFont().getStyle(),22);
		top1.setFont(newLabelFont);
		panelTop.add(top1);
		
		JLabel top2=new JLabel("маркетинговой деятельности",SwingConstants.CENTER);
		top2.setFont(newLabelFont);
		panelTop.add(top2);
		
		JLabel top3=new JLabel("покупателей Украины",SwingConstants.CENTER);
		top3.setFont(newLabelFont);
		panelTop.add(top3);
		// JLabel labelTop=new JLabel("temp");
		this.add(panelTop, BorderLayout.NORTH);
		
		JPanel panelBottom=new JPanel();
		panelBottom.setLayout(new GridLayout(1,1));
		
		JLabel labelBottom=new JLabel("<html>Автор идеи: <b><i>Сытник Альберт Викторович</i></b></html>");
		newLabelFont=new Font(top1.getFont().getName(),top1.getFont().getStyle(),18);
		labelBottom.setFont(newLabelFont);
		labelBottom.setHorizontalAlignment(SwingConstants.CENTER);
		panelBottom.add(labelBottom);
		this.add(panelBottom, BorderLayout.SOUTH);
		this.setVisible(true);
	}
	
	public void showTempDirectory(){
		this.desktop.removeAll();
		this.desktop.repaint();
		TempDirectory directory=new TempDirectory(this);
		this.desktop.add(directory);
		position.set_frame_to_center(directory, this);
	}
	
	public void showPassword(String tempDirectory){
		this.desktop.removeAll();
		this.desktop.repaint();
		Password directory=new Password(this, tempDirectory);
		this.desktop.add(directory);
		position.set_frame_to_center(directory, this);
	}
}
