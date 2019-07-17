package com.cherkashyn.vitalii.indirector.workers.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.indirector.workers.domain.Asset;
import com.cherkashyn.vitalii.indirector.workers.domain.Category;
import com.cherkashyn.vitalii.indirector.workers.domain.District;
import com.cherkashyn.vitalii.indirector.workers.domain.Phone;
import com.cherkashyn.vitalii.indirector.workers.domain.Skill;
import com.cherkashyn.vitalii.indirector.workers.domain.Worker;
import com.cherkashyn.vitalii.indirector.workers.domain.Worker2hour;
import com.cherkashyn.vitalii.indirector.workers.gui.swing_utility.ModalPanel;
import com.cherkashyn.vitalii.indirector.workers.gui.swing_utility.ModalResultListener;
import com.cherkashyn.vitalii.indirector.workers.gui.swing_utility.UIUtils;
import com.cherkashyn.vitalii.indirector.workers.service.exception.ServiceException;
import com.cherkashyn.vitalii.indirector.workers.service.interf.asset.AssetFinder;
import com.cherkashyn.vitalii.indirector.workers.service.interf.category.CategoryFinder;
import com.cherkashyn.vitalii.indirector.workers.service.interf.district.DistrictFinder;
import com.cherkashyn.vitalii.indirector.workers.service.interf.skill.SkillFinder;
import com.cherkashyn.vitalii.indirector.workers.service.interf.worker.WorkerFinder;
import com.cherkashyn.vitalii.indirector.workers.service.interf.worker.WorkerRepository;

@Component
@Scope("prototype")
public class WorkerWindow extends ModalPanel implements ModalResultListener {
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 900;
	public static final int HEIGHT = 700;

	@Autowired
	SkillFinder finderSkill;

	@Autowired
	CategoryFinder finderCategory;

	@Autowired
	AssetFinder finderAsset;

	@Autowired
	DistrictFinder finderDistrict;
	
	@Autowired
	WorkerRepository repositoryWorker;

	@Autowired
	WorkerFinder finderWorker;

	private Worker worker;
	
	public WorkerWindow() {
	}
	
	public WorkerWindow(Worker workerForUpdate){
	}
	
	public void setWorker(Worker workerForUpdate) throws ServiceException{
		// this.worker=finderWorker.findById(workerForUpdate.getId());
		this.worker=workerForUpdate;
		finderWorker.refresh(workerForUpdate);
		fromWorkerToUi(this.worker);
	}

	private JTextField name = new JTextField();
	{
		name.setBorder(BorderFactory.createTitledBorder("Имя"));
	}
	private JTextField surname = new JTextField();
	{
		surname.setBorder(BorderFactory.createTitledBorder("Фамилия"));
	}
	private JTextField fathername = new JTextField();
	{
		fathername.setBorder(BorderFactory.createTitledBorder("Отчество"));
	}
	private JTextField description = new JTextField();
	{
		description.setBorder(BorderFactory.createTitledBorder("Описание"));
	}
	
	private JList<Phone> phones = new JList<Phone>(new DefaultListModel<Phone>());
	{
		phones.setCellRenderer(new ListCellRenderer<Phone>() {

			@Override
			public java.awt.Component getListCellRendererComponent(
						JList<? extends Phone> list, 
						Phone phone, 
						int index,
						boolean isSelected, 
						boolean cellHasFocus) {
				
				JLabel returnValue = new JLabel();
				returnValue.setOpaque(true);
				returnValue.setText(formatText(phone.getPhone()));
				returnValue.setForeground(Color.BLACK);
				returnValue.setBackground(Color.WHITE);
				if (isSelected) {
					returnValue.setBackground(Color.LIGHT_GRAY);
				}
				if (cellHasFocus) {
					returnValue.setBackground(Color.BLUE);
					returnValue.setForeground(Color.YELLOW);
				}
				return returnValue;
			}

			private final int[] PATTERN = new int[] { 3, 3, 2, 2 };

			private String formatText(String value) {
				String clearValue = value.replaceAll("([^0-9])",
						StringUtils.EMPTY);

				String returnValue = StringUtils.EMPTY;
				int position = clearValue.length();
				for (int patternIndex = PATTERN.length - 1; patternIndex >= 0; patternIndex--) {
					int nextPosition = position - PATTERN[patternIndex];
					if (nextPosition >= 0) {
						returnValue = clearValue.substring(nextPosition,
								position) + " " + returnValue;
						position = nextPosition;
					} else {
						break;
					}
				}
				if (position > 0) {
					returnValue = clearValue.substring(0, position)
							+ returnValue;
				}
				return returnValue.toString();
			}

		});
	}

	private Map<Skill, JCheckBox> skills = new HashMap<Skill, JCheckBox>();
	private Map<Asset, JCheckBox> assets = new HashMap<Asset, JCheckBox>();

	private final static int WEEK_DAYS_COUNT = 7;
	private final static String[] WEEK_DAYS = new String[] { "Понедельник",
			"Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье" };
	private final static int DAY_HOURS = 24;
	private final static int WORKING_HOURS_BEGIN = 10;
	private final static int WORKING_HOURS_END = 19;

	private List<List<JCheckBox>> time = new ArrayList<List<JCheckBox>>(
			WEEK_DAYS_COUNT);
	/** each element - Pair<District, JCheckBox> */
	private JTree districtsTree;
	
	private JTextField age;
	
	private JComboBox<District> liveDistrict;
	
	@PostConstruct
	private void createPanels() {
		this.setLayout(new BorderLayout());
		/** name, surname, fathername, description, phones */
		JPanel panelTop = new JPanel(new BorderLayout());
		this.add(panelTop, BorderLayout.NORTH);
		
		/** name, surname, fathername, description */
		JPanel panelNameDescription = new JPanel(new GridLayout(2, 1));
		panelTop.add(panelNameDescription, BorderLayout.CENTER);
		JPanel panelName = new JPanel(new GridLayout(1, 4));
		panelName.add(name);
		panelName.add(surname);
		panelName.add(fathername);
		panelName.add(createPanelLiveDistrict());
		panelNameDescription.add(panelName);
		
		JPanel panelDescription=new JPanel(new BorderLayout());
		panelDescription.add(description);
		panelDescription.add(createPanelAge(), BorderLayout.WEST);
		panelNameDescription.add(panelDescription);

		/** phones */
		final JPanel panelPhones = createPanelPhones();
		panelTop.add(panelPhones, BorderLayout.EAST);

		
		JPanel panelSkillTimeDistrict = new JPanel(new GridLayout(1, 2));
		this.add(panelSkillTimeDistrict, BorderLayout.CENTER);

		/** Skills */
		final JComponent panelSkills = createPanelSkills();
		panelSkills.setBorder(BorderFactory.createTitledBorder("Навыки"));
		
		/** Time */
		JComponent panelTime = createPanelTime();
		panelTime.setBorder(BorderFactory.createTitledBorder("Время"));

		JPanel panelTimeSkills = new JPanel(new GridLayout(2, 1));
		panelTimeSkills.add(panelSkills);
		panelTimeSkills.add(panelTime);
		panelSkillTimeDistrict.add(panelTimeSkills);

		
		/** District & Save */
		JPanel panelDistrictAssetsSave=new JPanel(new BorderLayout());
		panelSkillTimeDistrict.add(panelDistrictAssetsSave);
		
		/** Assets */
		JComponent panelAssets=createPanelAssets();
		
		/**     District */
		JComponent panelDistrict = createPanelDistrict();
		
		JPanel panelAssetDistrict=new JPanel(new GridLayout(2,1));
		panelAssetDistrict.add(panelAssets);
		panelAssetDistrict.add(panelDistrict);
		panelDistrictAssetsSave.add(panelAssetDistrict, BorderLayout.CENTER);
		
		/**     Save */
		JPanel panelSave=createPanelSave();
		panelDistrictAssetsSave.add(panelSave, BorderLayout.SOUTH);
	}

	private java.awt.Component createPanelAge() {
		JPanel returnValue=new JPanel(new BorderLayout());
		returnValue.setBorder(BorderFactory.createTitledBorder("Возраст"));
		age=new JTextField(6);
		returnValue.add(age);
		return returnValue;
	}

	private java.awt.Component createPanelLiveDistrict() {
		liveDistrict=new JComboBox<District>();
		liveDistrict.setBorder(BorderFactory.createTitledBorder("Район проживания:"));
		try {
			List<District> rootList = finderDistrict.findAllLeaf();
			District empty=new District();
			empty.setName("");
			rootList.add(empty);
			liveDistrict.setModel(new DefaultComboBoxModel<District>(rootList.toArray(new District[rootList.size()])));
			liveDistrict.setSelectedItem(empty);
		} catch (ServiceException e) {
			JOptionPane.showMessageDialog(this, "can't retrieve Districts: "
					+ e.getMessage());
		}
		return liveDistrict;
		
	}

	private JComponent createPanelAssets()  {
		List<Asset> listOfAssets=null;
		try {
			listOfAssets = finderAsset.findAll();
		} catch (ServiceException e) {
			JOptionPane.showMessageDialog(this, "не могу найти Доп.Оборудование :"+e.getMessage());
			return new JLabel();
		}
		JPanel panelAssets=new JPanel(new GridLayout(listOfAssets.size(),1));
		for(Asset eachAsset:listOfAssets){
			JCheckBox checkbox=new JCheckBox(eachAsset.getName());
			this.assets.put(eachAsset, checkbox);
			panelAssets.add(checkbox);
		}
		JComponent returnValue=new JScrollPane(panelAssets);
		returnValue.setBorder(BorderFactory.createTitledBorder("Дополнительное оборудование"));
		return returnValue;
	}
	
	
	private JPanel createPanelPhones() {
		final JPanel panelPhones=	new JPanel(new BorderLayout());
		
		panelPhones.setBorder(BorderFactory.createTitledBorder("Телефоны"));
		panelPhones.add(phones, BorderLayout.CENTER);
		
		
		JPanel panelPhonesManager = new JPanel(new GridLayout(2, 1));
		panelPhones.add(panelPhonesManager, BorderLayout.EAST);

		/** phones button Add */
		JButton buttonPhonesAdd = new JButton("+");
		buttonPhonesAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UIUtils.showModal(WorkerWindow.this, new PanelEditPhone());
			}
		});
		panelPhonesManager.add(buttonPhonesAdd);

		/** phones button Remove */
		JButton buttonPhonesRemove = new JButton("-");
		buttonPhonesRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (phones.getSelectedIndex() >= 0) {
					if (JOptionPane.showConfirmDialog(
							panelPhones,
							"Будет удален:"
									+ phones.getModel().getElementAt(phones.getSelectedIndex()).getPhone(),
							"Внимание, удаление", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						((DefaultListModel<Phone>)phones.getModel()).removeElementAt(phones.getSelectedIndex());
					}
				}
			}
		});
		panelPhonesManager.add(buttonPhonesRemove);
		return panelPhones;
	}
	
	private JPanel createPanelSave() {
		JPanel panelSave=new JPanel(new GridLayout(1, 2));
		JButton buttonSave=new JButton("Сохранить");
		panelSave.add(buttonSave);
		buttonSave.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onButtonSave();
			}
		});
		JButton buttonCancel=new JButton("Отменить");
		buttonCancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onButtonCancel();
			}
		});
		panelSave.add(buttonCancel);
		return panelSave; 
	}
	/**
	 * from Worker object to User interface 
	 * @param worker2
	 */
	private void fromWorkerToUi(Worker value) {
		this.name.setText(value.getName());
		this.fathername.setText(value.getFathername());
		this.surname.setText(value.getSurname());
		this.description.setText(value.getDescription());
		if(value.getAge()!=null && value.getAge()>0){
			this.age.setText(Integer.toString(value.getAge()));
		}
		
		// set Phones
		DefaultListModel<Phone>  phoneModel=new DefaultListModel<Phone>();
		for(Phone eachPhone: value.getPhones()){
			phoneModel.addElement(eachPhone);
		}
		this.phones.setModel(phoneModel);

		// set Skills
		for(Skill workerSkill:value.getSkills()){
			JCheckBox checkbox=this.skills.get(workerSkill);
			if(checkbox!=null){
				checkbox.setSelected(true);
			}
		}
		
		// set Districts
		checkDistrictForCheckedLeaf(value.getDistricts(), this.districtsTree.getModel(), (DefaultMutableTreeNode)this.districtsTree.getModel().getRoot());
		// set Live District
		if(value.getLiveDistrict()!=null){
			for(int index=0; index<this.liveDistrict.getItemCount(); index++){
				if(this.liveDistrict.getItemAt(index).equals(value.getLiveDistrict())){
					this.liveDistrict.setSelectedItem(this.liveDistrict.getItemAt(index));
					break;
				}
			}
		}
		

		// clear hours
		for(int dayIndex=0;dayIndex<WEEK_DAYS_COUNT;dayIndex++){
			for(int hourIndex=0;hourIndex<DAY_HOURS;hourIndex++){
				time.get(dayIndex).get(hourIndex).setSelected(false);
			}
		}
		// set Hour
		for(Worker2hour eachHour:value.getHours()){
			time.get(eachHour.getDay()).get(eachHour.getHour()).setSelected(true);
		}
		
		// set Asset
		for(Asset eachAsset:value.getAssets()){
			JCheckBox checkbox=this.assets.get(eachAsset);
			if(checkbox!=null){
				checkbox.setSelected(true);
			}
		}
	}

	private void checkDistrictForCheckedLeaf(Set<District> districts,
											 TreeModel districtModel, 
											 DefaultMutableTreeNode root) {
		@SuppressWarnings("unchecked")
		ImmutablePair<District, JCheckBox> rootUserObject=(ImmutablePair<District, JCheckBox>)root.getUserObject();
		if(rootUserObject!=null){
			if(districts.contains(rootUserObject.getKey())){
				rootUserObject.getValue().setSelected(true);
			}
		}
		if(root.getChildCount()==0){
			return;
		}
		for(int childIndex=0;childIndex<root.getChildCount();childIndex++){
			DefaultMutableTreeNode nextNode=(DefaultMutableTreeNode)root.getChildAt(childIndex);
			checkDistrictForCheckedLeaf(districts, districtModel, nextNode);
		}
	}
	/**
	 * from UserInterface to worker object 
	 * @param value
	 */
	private void fromUiToWorker(Worker value) {
		value.setName(this.name.getText());
		value.setFathername(this.fathername.getText());
		value.setSurname(this.surname.getText());
		value.setDescription(this.description.getText());
		value.setAge(readAge(this.age.getText()));
		
		Set<Phone> workerPhones=new HashSet<Phone>();
		for(int index=0;index<this.phones.getModel().getSize();index++){
			workerPhones.add(this.phones.getModel().getElementAt(index));
		}
		value.setPhones(workerPhones);
		
		Set<Skill> workerSkills=new HashSet<Skill>();
		for(Entry<Skill, JCheckBox> eachSkill:skills.entrySet()){
			if(eachSkill.getValue().isSelected()){
				workerSkills.add(eachSkill.getKey());
			}
		}
		value.setSkills(workerSkills);
		
		TreeModel districtModel=this.districtsTree.getModel();
		Set<District> workerDistricts=new HashSet<District>();
		addDistrictFromLeaf(workerDistricts, districtModel, districtModel.getRoot());
		value.setDistricts(workerDistricts);
		
		District liveDistrict=(District)this.liveDistrict.getSelectedItem();
		if(liveDistrict.getId()!=null){
			value.setLiveDistrict(liveDistrict);
		}
		
		Set<Worker2hour> workerHours=new HashSet<Worker2hour>();
		for(int dayIndex=0;dayIndex<WEEK_DAYS_COUNT;dayIndex++){
			List<JCheckBox> dayHours=time.get(dayIndex);
			for(int hourIndex=0;hourIndex<DAY_HOURS;hourIndex++){
				if(dayHours.get(hourIndex).isSelected()){
					Worker2hour newHour=new Worker2hour();
					newHour.setDay(dayIndex);
					newHour.setHour(hourIndex);
					workerHours.add(newHour);
				}
			}
		}
		value.setHours(workerHours);
		
		Set<Asset> workerAssets=new HashSet<Asset>();
		for(Entry<Asset, JCheckBox> assetEntry:this.assets.entrySet()){
			if(assetEntry.getValue().isSelected()){
				workerAssets.add(assetEntry.getKey());
			}
		}
		value.setAssets(workerAssets);
	}
	

	private Integer readAge(String textWithDigits) {
		String clearValue=StringUtils.trimToNull(textWithDigits.replaceAll("[^0-9]", ""));
		if(clearValue==null){
			return null;
		}
		return Integer.parseInt(clearValue);
	}

	private void addDistrictFromLeaf(Set<District> workerDistricts, TreeModel model, Object node) {
		if(model.getChildCount(node)==0){
			Pair<District, JCheckBox> leaf=getUserObject((TreeNode)node);
			if(leaf.getValue().isSelected()){
				workerDistricts.add(leaf.getKey());
			}
		}else{
			for(int index=0;index<model.getChildCount(node);index++){
				addDistrictFromLeaf(workerDistricts, model, model.getChild(node, index));
			}
		}
	}
	
	private void onButtonSave(){
		if(this.worker==null){
			Worker value=new Worker();
			fromUiToWorker(value);
			try {
				repositoryWorker.create(value);
				this.closeModal(ModalResultListener.Result.Ok);
			} catch (ServiceException e) {
				JOptionPane.showMessageDialog(this, "can't save data: "+e.getMessage());
			}
		}else{
			fromUiToWorker(worker);
			try {
				repositoryWorker.update(worker);
				this.closeModal(ModalResultListener.Result.Ok);
			} catch (ServiceException e) {
				JOptionPane.showMessageDialog(this, "can't save data: "+e.getMessage());
			}
		}
	}
	
	private void onButtonCancel(){
		if(JOptionPane.showConfirmDialog(this, "Все изменения будут потеряны ", "Предупреждение", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
			this.closeModal(ModalResultListener.Result.Cancel);			
		}
	}

	private JComponent createPanelDistrict() {
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode();
		districtsTree = new JTree(rootNode);
		// districtsTree.setEditable(true);
		
		// districtsTree.setCellEditor(new CheckBoxNodeEditor(districtsTree));
		districtsTree.setCellRenderer(new CheckBoxNodeRenderer());
		districtsTree.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				if(MouseEvent.BUTTON3!=mouseEvent.getButton()){
					return;
				}
				
				TreePath path = districtsTree.getPathForLocation(mouseEvent.getX(),
						mouseEvent.getY());
				if (path == null) {
					return ;
				}
				Object node = path.getLastPathComponent();
				if (node == null) {
					return ;
				}
				
				Pair<District, JCheckBox> userObject = getUserObject((TreeNode)node);
				if (userObject == null || userObject.getRight() == null) {
					return ;
				}
				changeSelectedForTreeNode((TreeNode)node);
			}
		});

		// districtsTree.setCellEditor(new DefaultCellEditor(new JCheckBox()));
		fillDistricts(rootNode, null);

		// expand all
		for (int i = 0; i < districtsTree.getRowCount(); i++) {
			districtsTree.expandRow(i);
		}
		JComponent returnValue=new JScrollPane(districtsTree);
		returnValue.setBorder(BorderFactory.createTitledBorder("Место работы"));
		return returnValue;
	}

	protected void changeSelectedForTreeNode(TreeNode treeNode) {
		Pair<District, JCheckBox> userObject=getUserObject(treeNode);
		boolean newState=!userObject.getRight().isSelected();
		// set new state to element
		userObject.getRight().setSelected(newState);
		
		// set new status to children
		if(treeNode.getChildCount()>0){
			setStatusForChildren((DefaultMutableTreeNode)treeNode, newState);
		}
		
		// check parent when all brothers selected/unselected
		Pair<District, JCheckBox> parentUserObject=getUserObject(treeNode.getParent());
		if(parentUserObject!=null){ // for root only 
			if(isChildrenInSameState(treeNode.getParent())){
				parentUserObject.getRight().setSelected(newState);
			}else{
				parentUserObject.getRight().setSelected(false);
			}
		}
		districtsTree.repaint();
	}

	private boolean isChildrenInSameState(TreeNode treeNode) {
		boolean state = getUserObject(treeNode.getChildAt(0)).getRight().isSelected();
		for(int index=0;index<treeNode.getChildCount();index++){
			if(state!=getUserObject(treeNode.getChildAt(index)).getRight().isSelected()){
				return false;
			}
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	Pair<District, JCheckBox> getUserObject(TreeNode node){
		if(!(node instanceof DefaultMutableTreeNode)){
			return null;
		}
		Object userObject=((DefaultMutableTreeNode)node).getUserObject();
		if(userObject==null){
			return null;
		}
		return (Pair<District, JCheckBox>)(userObject);
	}
	
	
	

	private void setStatusForChildren(DefaultMutableTreeNode treeNode, boolean newState) {
		for(int index=0;index<treeNode.getChildCount();index++){
			DefaultMutableTreeNode childNode=(DefaultMutableTreeNode)treeNode.getChildAt(index);
			if(childNode==null){
				continue;
			}
			@SuppressWarnings("unchecked")
			Pair<District, JCheckBox> userObject=(Pair<District, JCheckBox>)(childNode).getUserObject();
			if(userObject==null){
				continue;
			}
			userObject.getRight().setSelected(newState);
			if(childNode.getChildCount()>0){
				setStatusForChildren(childNode, newState);
			}
		}
		
	}

	private void fillDistricts(DefaultMutableTreeNode rootNode,
			District parentElement) {
		List<District> rootList = null;
		try {
			rootList = finderDistrict.findAll(parentElement);
		} catch (ServiceException e) {
			JOptionPane.showMessageDialog(this, "can't retrieve Districts: "
					+ e.getMessage());
			return;
		}
		if (rootList == null || rootList.size() == 0) {
			return;
		}

		for (District childElement : rootList) {
			JCheckBox checkbox = new JCheckBox(childElement.getName());
			final DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(
					new ImmutablePair<District, JCheckBox>(childElement,
							checkbox));
			rootNode.add(childNode);
			fillDistricts(childNode, childElement);
		}
	}

	private JComponent createPanelTime() {
		JPanel returnValue = new JPanel(new GridLayout(WEEK_DAYS_COUNT, 1));

		for (int daysIndex = 0; daysIndex < WEEK_DAYS_COUNT; daysIndex++) {
			JPanel dayPanel = new JPanel(new GridLayout(1, DAY_HOURS));
			dayPanel.setBorder(BorderFactory
					.createTitledBorder(WEEK_DAYS[daysIndex]));
			returnValue.add(dayPanel);

			List<JCheckBox> day = new ArrayList<JCheckBox>(DAY_HOURS);
			for (int eachHour = 0; eachHour < DAY_HOURS; eachHour++) {
				JCheckBox nextHour = new JCheckBox();
				nextHour.setToolTipText(Integer.toString(eachHour));
				day.add(nextHour);
				dayPanel.add(nextHour);
			}
			setWorkingHour(day);
			time.add(day);
		}
		return returnValue;
	}

	private void setWorkingHour(List<JCheckBox> day) {
		for (int index = 0; index < day.size(); index++) {
			if (index < WORKING_HOURS_BEGIN) {
				day.get(index).setSelected(false);
			} else if (index > WORKING_HOURS_END) {
				day.get(index).setSelected(false);
			} else {
				day.get(index).setSelected(true);
			}

		}
	}

	private JComponent createPanelSkills() {
		final JTabbedPane tabPane = new JTabbedPane();
		List<Category> categories = null;
		// fill elements
		try {
			categories = finderCategory.findAll();
		} catch (ServiceException e) {
			JOptionPane.showMessageDialog(this,
					"can't load data: " + e.getMessage());
			return null;
		}
		
		
		
		for (Category eachCategory : categories) {
			List<Skill> skills = null;
			try {
				skills = finderSkill.findAll(eachCategory);
			} catch (ServiceException e) {
				JOptionPane.showMessageDialog(this,
						"can't load data: " + e.getMessage());
				return null;
			}
			JPanel panel = new JPanel(new GridLayout(skills.size(), 1));
			Map<Skill, JCheckBox> values = new HashMap<Skill, JCheckBox>();
			for (Skill eachSkill : skills) {
				JCheckBox checkbox = new JCheckBox(eachSkill.getName());
				values.put(eachSkill, checkbox);
				panel.add(checkbox);
			}
			this.skills.putAll(values);

			tabPane.addTab(eachCategory.getName(), new JScrollPane(panel));
		}

		JButton buttonSelectAll=new JButton("выделить все элементы в текущей категории");
		buttonSelectAll.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JScrollPane scrollPane=(JScrollPane)(tabPane).getSelectedComponent();
				JPanel rootPanel=(JPanel)(((javax.swing.JViewport)scrollPane.getComponents()[0])).getComponents()[0];
				for(java.awt.Component eachComponent:rootPanel.getComponents()){
					((JCheckBox)eachComponent).setSelected(true);
				}
			}
		});

		JButton buttonDeselectAll=new JButton("снять выделение со всех элементов в текущей категории");
		buttonDeselectAll.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JScrollPane scrollPane=(JScrollPane)(tabPane).getSelectedComponent();
				JPanel rootPanel=(JPanel)(((javax.swing.JViewport)scrollPane.getComponents()[0])).getComponents()[0];
				for(java.awt.Component eachComponent:rootPanel.getComponents()){
					((JCheckBox)eachComponent).setSelected(false);
				}
			}
		});
		JPanel panelSelector=new JPanel(new GridLayout(2, 1));
		panelSelector.add(buttonSelectAll);
		panelSelector.add(buttonDeselectAll);
		
		JPanel returnValue=new JPanel(new BorderLayout());
		returnValue.add(tabPane, BorderLayout.CENTER);
		returnValue.add(panelSelector, BorderLayout.NORTH);
		
		return returnValue;
	}
	
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void childWindowModalResult(Object value) {
		if (value instanceof String) {
			// Phones 
			String valueForAdd = (String) value;
			// check for repeat
			for (int index = 0; index < this.phones.getModel().getSize(); index++) {
				if (valueForAdd.equals((this.phones.getModel().getElementAt(index).getPhone()).replaceAll("([^0-9])",
						StringUtils.EMPTY))) {
					return;
				}
			}
			Phone newPhone=new Phone();
			newPhone.setPhone(valueForAdd);
			((DefaultListModel)this.phones.getModel()).addElement(newPhone);
		}

	}

	private static class PanelEditPhone extends ModalPanel {
		private static final long serialVersionUID = 1L;
		@SuppressWarnings("unused")
		public static final int WIDTH = 200;
		@SuppressWarnings("unused")
		public static final int HEIGHT = 150;

		public PanelEditPhone() {
			super();
			init();
		}

		JTextField field;
		
		private final static int KIEV_CITY_LENGHT=7;
		private final static String KIEV_CITY_CODE="044";

		private void init() {
			this.setLayout(new GridLayout(2, 1));
			field = new JTextField();
			field.setBorder(BorderFactory.createTitledBorder("телефон"));
			this.add(field);

			JPanel panelManager = new JPanel();
			this.add(panelManager);
			panelManager.setLayout(new GridLayout(1, 2));

			JButton buttonAdd = new JButton("Save");
			buttonAdd.setBorder(new EmptyBorder(5, 5, 5, 5));
			buttonAdd.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String valueFromField = StringUtils.trimToNull(field
							.getText());
					if (valueFromField == null) {
						PanelEditPhone.this.closeModal();
					}
					valueFromField = valueFromField.replaceAll("([^0-9])", "");
					// add Kiev city code as default 
					if(valueFromField.length()==KIEV_CITY_LENGHT){ 
						valueFromField=KIEV_CITY_CODE+valueFromField;
					}
					PanelEditPhone.this.closeModal(valueFromField);
				}
			});
			panelManager.add(buttonAdd);

			JButton buttonRemove = new JButton("Cancel");
			buttonRemove.setBorder(new EmptyBorder(5, 5, 5, 5));
			buttonRemove.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					PanelEditPhone.this.closeModal(null);
				}
			});
			panelManager.add(buttonRemove);
		}
	}

}
/*
class CheckBoxNodeEditor extends AbstractCellEditor implements TreeCellEditor {
	private static final long serialVersionUID = 1L;

	JCheckBox currentEditElement;
	JTree tree;
	
	public CheckBoxNodeEditor(JTree tree) {
		this.tree=tree;
	}

	public Object getCellEditorValue() {
		return currentEditElement;
	}

	public boolean isCellEditable(EventObject event) {
		if (!(event instanceof MouseEvent)) {
			return false;
		}
		MouseEvent mouseEvent = (MouseEvent) event;
		if (mouseEvent.getClickCount() <= 1) {
			return false;
		}
		TreePath path = tree.getPathForLocation(mouseEvent.getX(),
				mouseEvent.getY());
		if (path == null) {
			return false;
		}
		Object node = path.getLastPathComponent();
		if (node == null) {
			return false;
		}
		if (!(node instanceof DefaultMutableTreeNode)) {
			return false;
		}
		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) node;
		@SuppressWarnings("unchecked")
		Pair<District, JCheckBox> userObject = (Pair<District, JCheckBox>) treeNode
				.getUserObject();
		if (userObject == null || userObject.getRight() == null) {
			return false;
		}
		this.currentEditElement = userObject.getRight();
		return true;
	}

	public java.awt.Component getTreeCellEditorComponent(JTree tree,
			Object value, boolean selected, boolean expanded, boolean leaf,
			int row) {
		System.out.println("getTreeCellEditorComponent : ");
		if (!(value instanceof DefaultMutableTreeNode)) {
			return null;
		}
		@SuppressWarnings("unchecked")
		Pair<District, JCheckBox> realValue = (Pair<District, JCheckBox>) ((DefaultMutableTreeNode) value)
				.getUserObject();
		JCheckBox returnValue = realValue.getRight();
		return returnValue;
	}

}
*/

class CheckBoxNodeRenderer implements TreeCellRenderer{
	@Override
	public java.awt.Component getTreeCellRendererComponent(JTree tree,
														   Object value, 
														   boolean isSelected, 
														   boolean expanded,
														   boolean leaf, 
														   int row, 
														   boolean hasFocus) {
		
		Object userObject=((javax.swing.tree.DefaultMutableTreeNode) value).getUserObject();
		if(userObject==null){
			return new JLabel();
		}
		@SuppressWarnings("unchecked")
		
		Pair<District, JCheckBox> realValue = (Pair<District, JCheckBox>) userObject;
		JCheckBox returnValue = realValue.getRight();
		returnValue.setOpaque(true);

		returnValue.setOpaque(true);
		returnValue.setForeground(Color.BLACK);
		returnValue.setBackground(Color.WHITE);
		if (isSelected) {
			returnValue.setBackground(Color.LIGHT_GRAY);
		}
		if (hasFocus) {
			returnValue.setBackground(Color.BLUE);
			returnValue.setForeground(Color.YELLOW);
		}
		return returnValue;
	}
}
