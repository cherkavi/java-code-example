package com.cherkashyn.vitalii.tools.database.diff.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.cherkashyn.vitalii.tools.database.diff.utils.SqlFilter;

public class ExecutorsPanel extends JPanel{
	public static interface DiffListener{
		void diffChanges(List<String> sqlCommands);
	}
	
	private static final long serialVersionUID = 1L;
	private final static String TITLE_DIFF="diff";
	private final DiffListener listener;
	
	public ExecutorsPanel(DiffListener listener, ButtonModel ...buttonModels ){
		this.listener=listener;
		this.setLayout(new GridLayout(buttonModels.length, 1));
		for(ButtonModel eachButton:buttonModels){
			JButton button=new JButton(eachButton.getTitle());
			button.setToolTipText(eachButton.getCommand());
			if(eachButton.getTitle().equals(TITLE_DIFF)){
				button.addActionListener(craeteActionListenerDiff(eachButton.getCommand()));
			}else{
				button.addActionListener(craeteActionListener(eachButton.getCommand()));
			}
			this.add(button);
		}
	}

	
	/**
	 * command example:
	 * diff /tmp/opencart_source.sql /tmp/opencart_dest.sql > /tmp/opencart.diff
	 * @param command
	 * @return
	 */
	private ActionListener craeteActionListenerDiff(final String command) {
		return new ActionListener(){
			private final static String MARKER=">";
			
			@Override
			public void actionPerformed(ActionEvent e) {
				executeCommand(command);
				// detect file
				String filePath=getFilePath(command);
				// read content of the file
				FileInputStream fis=null;
				try{
					fis=new FileInputStream(filePath);
					List<String> lines=IOUtils.readLines(fis);
					List<String> sqlCommands=SqlFilter.filter(lines);
					notifyAboutLines(sqlCommands);
				}catch(IOException ex){
					
				}finally{
					IOUtils.closeQuietly(fis);
				}
			}
			
			private String getFilePath(String command){
				return StringUtils.trim(StringUtils.substringAfterLast(command, MARKER));
			}
			
		};
	}

	private void notifyAboutLines(List<String> sqlCommands) {
		if(this.listener!=null){
			this.listener.diffChanges(sqlCommands);
		}
	}


	private ActionListener craeteActionListener(final String command) {
		return new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				executeCommand(command);
			}
		};
	}
	
	private void executeCommand(final String command){
		Process p;
		try {
			System.out.println("execute command: "+command);
			p = Runtime.getRuntime().exec(new String[]{"bash","-c",command});
		} catch (IOException e1) {
			e1.printStackTrace();
			showError(e1.getMessage(), "execute command error");
			return;
		}
	    try {
			p.waitFor();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			showError(e1.getMessage(), "wait for end command Error");
			return;
		}
	    // BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
	}

	
	private void showError(String errorMessage, String title){
		JOptionPane.showMessageDialog(null, errorMessage, title, JOptionPane.ERROR_MESSAGE);
	}
}
