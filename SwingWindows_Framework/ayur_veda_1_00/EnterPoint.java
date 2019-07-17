package ayur_veda;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import ayur_veda.questionary.AddQuestionary;

import swing_framework.FrameMain;
import swing_framework.Position;

/** основное окно программы */
public class EnterPoint extends FrameMain{
	private final static long serialVersionUID=1L;
	
	/** точка входа в программу */
	public static void main(String[] args){
		System.out.println("Begin");
		new EnterPoint();
		System.out.println("End");
	}
	
	/** главное окно программы, которое отображает рабочее место доктора */
	public EnterPoint(){
		super("Рабочее место доктора");
		Position.set_frame_by_dimension(this, 600, 400);
		this.setVisible(true);
	}
	
	/** получить меню пользователя для быстрого доступа */
	protected  JMenuBar getUserMenuBar(){
		JMenuBar menuBar=new JMenuBar();
		JMenu menuQuestionary=new JMenu("Анкеты");
		menuBar.add(menuQuestionary);
		
		JMenuItem showQuestionary=new JMenuItem("Просмотреть анкеты");
		showQuestionary.addActionListener(this.getActionListener(this, "onButtonShowQuestionary"));
		menuQuestionary.add(showQuestionary);
		
		JMenuItem addQuestionary=new JMenuItem("Добавить анкету");
		addQuestionary.addActionListener(this.getActionListener(this, "onButtonAddQuestionary"));
		menuQuestionary.add(addQuestionary);
		
		return menuBar;
	}
	
	/** реакция на выбор отображения всех анкет */
	public void onButtonShowQuestionary(){
		System.out.println("onButtonShowQuestionary ");

	}
	/** реакция на выбор отображения режима добавления анкет */
	public void onButtonAddQuestionary(){
		new AddQuestionary(this, 300,200);
	}
}
