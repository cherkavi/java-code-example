package com.cherkashyn.vitalii.tools.database.diff.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.apache.commons.lang.StringUtils;

import com.cherkashyn.vitalii.tools.database.diff.gui.ExecutorsPanel.DiffListener;

public class MainFrame extends JFrame implements DiffListener, ClipboardOwner {
	
	private static final long serialVersionUID = 1L;
	private JTextArea text=new JTextArea();
	
	public MainFrame(ButtonModel ... buttonModels){
		super();
		init(buttonModels);
		this.setVisible(true);
	}

	private final static String BUTTON_COPY_TITLE="copy to clipboard";
	
	private void init(ButtonModel[] buttonModels) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(400, 600);
		
		this.add(new ExecutorsPanel(this, buttonModels), BorderLayout.NORTH);
		
		JPanel panelText=new JPanel();
		panelText.setLayout(new GridLayout(1,1));
		panelText.add(text);
		this.add(panelText, BorderLayout.CENTER);
		
		JButton buttonCopy=new JButton(BUTTON_COPY_TITLE);
		buttonCopy.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			    clipboard.setContents(new StringSelection(text.getText()), MainFrame.this);
			}
		});
		this.add(buttonCopy, BorderLayout.SOUTH);
	}

	@Override
	public void diffChanges(List<String> sqlCommands) {
		this.text.setText(StringUtils.EMPTY);
		this.text.setText(StringUtils.join(sqlCommands, "\n"));
	}

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
	}
	
	
}
