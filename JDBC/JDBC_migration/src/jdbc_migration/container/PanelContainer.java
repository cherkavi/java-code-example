package jdbc_migration.container;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;

/** �������, ������� �������� ����������� ������ � SQL ��������� */
public class PanelContainer extends JPanel implements IPanelContainerManager{
	private final static long serialVersionUID=1L;
	private ArrayList<PanelElement> listOfElement=new ArrayList<PanelElement>();
	private GroupLayout groupLayout;
	private PrintWriter outputWriter;
	private IExportImport dataAware;
	
	public PanelContainer(IExportImport dataAware){
		groupLayout=new GroupLayout(this);
		this.dataAware=dataAware;
		this.setLayout(groupLayout);

	}
	
	/** ������� ��� ���� �������, � �������� ��� � ��������� */
	public void addElement(){
		this.listOfElement.add(0,new PanelElement(this));
		refreshVisualElement();
	}
	
	/** ������� ��� ���� �������, � �������� ��� � ��������� */
	public void addElement(String query,boolean isDeleteBeforeInsert){
		this.listOfElement.add(0,new PanelElement(this,query,isDeleteBeforeInsert));
		refreshVisualElement();
	}
	
	/** ���������� ��� �������� � ������������� ������ �������� � ���� */
	public void exportToFile(){
		boolean check=true;
		for(int counter=0;counter<this.listOfElement.size();counter++){
			String currentValue=this.listOfElement.get(counter).check();
			if(currentValue!=null){
				JOptionPane.showMessageDialog(this, 
											  currentValue,
											  "������",
											  JOptionPane.ERROR_MESSAGE);
				check=false;
				this.listOfElement.get(counter).setAlert();
			}else{
				this.listOfElement.get(counter).clearAlert();
			}
		}
		if(check==true){
			try {
				this.getExportWriterNew();
				// ��� ������, ���������� 
				for(int counter=0;counter<this.listOfElement.size();counter++){
					this.listOfElement.get(counter).export();
				}
				this.closeExportWriter();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "������ �� ��������\n"+e.getMessage(), "������",JOptionPane.ERROR_MESSAGE);
			}
		}else{
			// ���� ������ 
		}
	}
	

	/** ���������� ��� �������� � ������������� ������ �������� � ���� */
	public void toDestination(){
		boolean check=true;
		for(int counter=0;counter<this.listOfElement.size();counter++){
			String currentValue=this.listOfElement.get(counter).check();
			if(currentValue!=null){
				JOptionPane.showMessageDialog(this, 
											  currentValue,
											  "������",
											  JOptionPane.ERROR_MESSAGE);
				check=false;
				this.listOfElement.get(counter).setAlert();
			}else{
				this.listOfElement.get(counter).clearAlert();
			}
		}
		if(check==true){
			try {
				for(int counter=0;counter<this.listOfElement.size();counter++){
					this.listOfElement.get(counter).destination();
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "������ �� ��������\n"+e.getMessage(), "������",JOptionPane.ERROR_MESSAGE);
			}
		}else{
			// ���� ������ 
		}
	}
	
	
	/** �������� ��� ���������� ���������� */
	private void refreshVisualElement(){
		this.removeAll();
		
		GroupLayout.SequentialGroup groupLayoutHorizontal=groupLayout.createSequentialGroup();
		GroupLayout.SequentialGroup groupLayoutVertical=groupLayout.createSequentialGroup();
		groupLayout.setVerticalGroup(groupLayoutVertical);
		groupLayout.setHorizontalGroup(groupLayoutHorizontal);
		
		for(int counter=0;counter<this.listOfElement.size();counter++){
			groupLayoutVertical.addComponent(this.listOfElement.get(counter),GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE);
		};

		ParallelGroup horizontal=groupLayout.createParallelGroup(Alignment.CENTER);
		for(int counter=0;counter<this.listOfElement.size();counter++){
			horizontal.addComponent(this.listOfElement.get(counter));
		};
		groupLayoutHorizontal.addGroup(horizontal);

		this.revalidate();
		//this.repaint();
	}

	@Override
	public void removeElement(PanelElement element) {
		this.listOfElement.remove(element);
		this.refreshVisualElement();
	}

	@Override
	public PrintWriter getExportWriter() {
		return this.outputWriter;
	}

	@Override
	public Connection getImportConnection() {
		return this.dataAware.getImportConnection();
	}
	
	/** �������� ���-�� ��������� */
	public int getElementCount(){
		return this.listOfElement.size();
	}
	
	/** �������� ��������� �� ������� ������� */
	public PanelElement getPanelElement(int index){
		return this.listOfElement.get(index);
	}

	@Override
	public void closeExportWriter() {
		try{
			this.outputWriter.close();
			this.outputWriter=null;
		}catch(Exception ex){};
	}

	@Override
	public PrintWriter getExportWriterNew() {
		try{
			this.outputWriter=new PrintWriter(this.dataAware.getExportFile());
		}catch(Exception ex){
			System.out.println("PanelgetExportWriterNew");
		}
		return this.outputWriter;
	}

	@Override
	public Connection getExportConnection() {
		return this.dataAware.getExportConnection();
	}

}
