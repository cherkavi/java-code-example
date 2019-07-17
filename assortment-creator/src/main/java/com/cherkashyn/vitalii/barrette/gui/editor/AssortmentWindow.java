package com.cherkashyn.vitalii.barrette.gui.editor;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.accounting.domain.Assortment;
import com.cherkashyn.vitalii.accounting.domain.AssortmentType;
import com.cherkashyn.vitalii.accounting.domain.Photo;
import com.cherkashyn.vitalii.accounting.domain.Price;
import com.cherkashyn.vitalii.accounting.exception.GeneralServiceException;
import com.cherkashyn.vitalii.accounting.service.finder.TypeFinder;
import com.cherkashyn.vitalii.accounting.service.repository.AssortmentRepository;
import com.cherkashyn.vitalii.accounting.service.repository.PriceRepository;
import com.cherkashyn.vitalii.barrette.gui.swing_utility.ModalPanel;
import com.cherkashyn.vitalii.barrette.service.exception.TransferServiceException;
import com.cherkashyn.vitalii.barrette.service.impl.Ftp2LocalService;

@Component
@Scope("prototype")
public class AssortmentWindow extends ModalPanel{

	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH=900;
	public static final int HEIGHT=300;

	
	@Autowired
	TypeFinder finderType;
	
	@Autowired
	PriceRepository repositoryPrice;

	@Autowired
	AssortmentRepository repositoryAssortment;
	
	
	@Autowired
	Ftp2LocalService servicePhoto;
	
	private JTextField fieldQuantity;
	private JTextField fieldName;
	private JComboBox<AssortmentType> fieldType;
	private JTextField fieldDescription;
	private JTextField fieldBuy;
	private JTextField fieldSell;
	private PanelPhotoAware photoHolder;
	
	@PostConstruct
	public void init(){
		fieldQuantity=new JTextField();
		fieldQuantity.setBorder(BorderFactory.createTitledBorder("Количество:"));
		fieldName=new JTextField();
		fieldName.setBorder(BorderFactory.createTitledBorder("Наименование:"));
		fieldType=new JComboBox<AssortmentType>(new DefaultComboBoxModel<AssortmentType>(readAllTypes()));
		fieldType.setSelectedIndex(0);
		fieldType.setRenderer(new DefaultListCellRenderer());
		fieldType.setBorder(BorderFactory.createTitledBorder("Тип:"));
		fieldDescription=new JTextField();
		fieldDescription.setBorder(BorderFactory.createTitledBorder("Описание: "));
		fieldBuy=new JTextField();
		fieldBuy.setBorder(BorderFactory.createTitledBorder("Цена закупки $ :"));
		fieldSell=new JTextField();
		fieldSell.setBorder(BorderFactory.createTitledBorder("Цена продажи $ :"));
		
		this.setLayout(new BorderLayout());
		this.add(createDataPanel(), BorderLayout.CENTER);
		this.add(createManagerPanel(), BorderLayout.SOUTH);
	}

	private AssortmentType[] readAllTypes() {
		try {
			return finderType.findAll().toArray(new AssortmentType[]{});
		} catch (GeneralServiceException ex) {
			JOptionPane.showMessageDialog(this, "can't retrieve types from DB:"+ex.getMessage());
			return new AssortmentType[0];
		}
	}

	private JPanel createDataPanel() {
		JPanel panel=new JPanel(new GridLayout(1,2));
		// photo
		PanelPhotoAware panelPhoto=createPanelPhoto();
		panel.add(panelPhoto);
		
		// description
		JPanel panelDescription=new JPanel(new GridLayout(3,1));
		panel.add(panelDescription);
		panelDescription.add(oneLinePanel(fieldName, fieldType));
		panelDescription.add(fieldDescription);
		panelDescription.add(oneLinePanel(fieldBuy, fieldSell, fieldQuantity));
		return panel;
	}

	/**
	 * @return
	 */
	private PanelPhotoAware createPanelPhoto() {
		PanelPhotoAware panel=new PanelPhotoAware(this.servicePhoto);
		return panel;
	}

	/** utility method for creating panel with one-line accommodation of element(s) */
	private JPanel oneLinePanel(JComponent ... components){
		if(components==null || components.length==0){
			return new JPanel();
		}
		JPanel panel=new JPanel(new GridLayout(1, components.length));
		for(JComponent eachComponent:components){
			panel.add(eachComponent);
		}
		return panel;
	}
	
	private JPanel createManagerPanel() {
		JPanel panel=new JPanel(new GridLayout(1,3));
		
		JButton buttonSavePrint=createButtonAddToContainer("Сохранить и \n отпечатать", panel);
		buttonSavePrint.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				onButtonSavePrint();
			}
		});
		
		JButton buttonSave=createButtonAddToContainer("Сохранить", panel);
		buttonSave.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				onButtonSave();
			}
		});
		
		JButton buttonCancel=createButtonAddToContainer("Отменить", panel);
		buttonCancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				onButtonCancel();
			}
		});

		return panel;
	}
	
	
	private JButton createButtonAddToContainer(String title, JComponent component){
		JButton button=new JButton(title);
		button.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
		component.add(button);
		return button;
	}


	private void onButtonSavePrint(){
		// TODO save Assortment, show print
	}
	
	private void onButtonSave(){
		// check input fields
		String valueName=fieldName.getText();
		AssortmentType valueType=(AssortmentType)fieldType.getSelectedItem();
		String valueDescription=fieldDescription.getText();

		String valueBuyText=StringUtils.trimToNull(fieldBuy.getText());
		Float valueBuy=null;
		if(valueBuyText!=null){
			try{
				valueBuy=Float.parseFloat(valueBuyText);
			}catch(NumberFormatException ex){
				JOptionPane.showMessageDialog(this, "can't parse BUY PRICE: "+valueBuyText);
				return ;
			}
		}
		
		String valueSellText=StringUtils.trimToNull(fieldSell.getText());
		Float valueSell=null;
		if(valueSellText!=null){
			try{
				valueSell=Float.parseFloat(valueSellText);
			}catch(NumberFormatException ex){
				JOptionPane.showMessageDialog(this, "can't parse SELL PRICE: "+valueSellText);
				return ;
			}
		}
		
		String valueQuantityText=StringUtils.trimToNull(fieldQuantity.getText());
		Integer valueQuantity=null;
		if(valueQuantityText!=null){
			try{
				valueQuantity=Integer.getInteger(valueQuantityText);
			}catch(NumberFormatException ex){
				JOptionPane.showMessageDialog(this, "can't parse SELL PRICE: "+valueSellText);
				return ;
			}
		}
		
		Photo valuePhoto=photoHolder.getPhoto();
		if(valuePhoto==null){
			JOptionPane.showMessageDialog(this, "can't recognize Photo");
			return ;
		}
		
		Assortment newAssortment=new Assortment();
		newAssortment.setName(valueName);
		newAssortment.setDescription(valueDescription);
		newAssortment.setType(valueType);
		newAssortment.setPhoto(valuePhoto);
		// TODO 
		// newAssortment.setBarcode(repositoryBarcode.createNew());
		
		try {
			repositoryAssortment.create(newAssortment);
		} catch (GeneralServiceException e) {
			JOptionPane.showMessageDialog(this, "can't create new Assortment: "+e.getMessage());
			return;
		}
		
		if(valueBuy!=null || valueSell!=null){
			Price newPrice=new Price(newAssortment, valueBuy, valueSell);
			try {
				repositoryPrice.create(newPrice);
			} catch (GeneralServiceException e) {
				JOptionPane.showMessageDialog(this, "can't create new Price: "+e.getMessage());
				return;
			}
		}
		
		// create new Assortment
		if(valueQuantity!=null){
			// TODO create record into Commodity ( if exists quantity ) or update existing 
			System.out.println(valueQuantity);
		}
		
	}
	
	private void onButtonCancel(){
		this.closeModal(); 
	}
	
}


interface PhotoAware{
	Photo getPhoto();
	void setPhoto(Photo photo);
}

/**
 * state object, with images 
 * @author technik
 *
 */
class PanelPhotoAware extends JPanel implements PhotoAware{
	private static final long serialVersionUID = 1L;
	private Photo photo;
	
	private JButton buttonLeft, buttonRight, buttonRemove;
	
	private Ftp2LocalService servicePhoto;
	private File fileImage;
	
	private JPanel panelImage=new JPanel(){
		
		private static final long serialVersionUID = 1L;
		
		@Override
	    public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if(PanelPhotoAware.this.fileImage!=null){
				try {
					BufferedImage image=ImageIO.read(PanelPhotoAware.this.fileImage);
					Rectangle panelBounds=this.getBounds();
					Point point=calculatePoint( image, panelBounds );
					g.drawImage(image, 0, 0, (int)point.getX() , (int)point.getY(), null);
				} catch (IOException e) {
					// TODO Logger
					System.err.println("image draw error : "+e.getMessage());
				}  
			}
	    }		
	};
	
	public PanelPhotoAware(Ftp2LocalService service){
		servicePhoto=service;
		init();
	}
	
	
	protected Point calculatePoint(BufferedImage image, Rectangle panelBounds) {
		double panelKoef=panelBounds.getWidth()/panelBounds.getHeight();
		double imageKoef=(double)image.getWidth()/(double)image.getHeight();
		if(panelKoef<=imageKoef){
			double k=panelBounds.getWidth()/image.getWidth();
			int width=(int) panelBounds.getWidth();
			int height=(int) (image.getHeight()*k);
			return new Point(width, height);
		}else{
			// image and panel has different rotation
			double k=panelBounds.getHeight()/image.getHeight();
			int height=(int) panelBounds.getHeight();
			int width=(int) (image.getWidth()*k);
			return new Point(width, height);
		}
	}

	private void init(){
		this.setLayout(new BorderLayout());
		buttonLeft=new JButton("<");
		this.add(buttonLeft, BorderLayout.WEST);
		buttonLeft.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				showPrevious();
			}
		});
		
		buttonRight=new JButton(">");
		this.add(buttonRight, BorderLayout.EAST);
		buttonRight.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				showNext();
			}
		});
		
		buttonRemove=new JButton("удалить");
		this.add(buttonRemove, BorderLayout.SOUTH);
		buttonRemove.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				removeCurrent();
			}
		});
		this.add(panelImage, BorderLayout.CENTER);
	}
	
	private void showPrevious(){
		File imageFile=this.servicePhoto.readPrevious();
		this.showImage(imageFile);
	}
	
	private void showImage(File imageFile) {
		this.fileImage=imageFile;
		this.panelImage.repaint();
	}

	private void showNext(){
		this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		File imageFile;
		try {
			imageFile = this.servicePhoto.readNext();
		} catch (TransferServiceException e) {
			JOptionPane.showMessageDialog(this, "can't read data from Remote FTP server");
			return;
		}
		this.showImage(imageFile);
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
	
	private void removeCurrent(){
		if(JOptionPane.showConfirmDialog(this, "Файл будет окончательно удален ?", "Предупреждение", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE)==JOptionPane.YES_OPTION){
			if(this.servicePhoto.removeCurrent()){
				this.showImage(null);
			}
		}
	}
	
	@Override
	public Photo getPhoto() {
		return photo;
	}

	@Override
	public void setPhoto(Photo photo) {
		this.photo = photo;
	}
	
}