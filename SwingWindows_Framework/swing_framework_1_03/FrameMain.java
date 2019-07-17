package swing_framework;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import swing_framework.messages.IMessageListener;
import swing_framework.messages.IMessageSender;
import swing_framework.messages.WindowMessage;
import swing_framework.messages.WindowMessageType;
import swing_framework.window.RegisteredWindow;
import swing_framework.window.WindowIdentifier;

/** главный фрейм, который отображает окно с рабочим столом */
public abstract class FrameMain extends JFrame implements IMessageSender{
	private final static long serialVersionUID=1L;
	private JDesktopPane desktop;
	private JPanel panelIcon;
	private ButtonPopupMenu popupMenuButton; 
	
	private void error(Object object){
		System.err.print("swing_framework.FrameMain#"+object);
	}
	
	/** главный фрейм, который отображает окно с рабочим столом */
	public FrameMain(String title){
		super(title);
		this.initComponents();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	private void initComponents(){
		this.desktop=new JDesktopPane();
		//this.desktop.setBackground(Color.cyan);
		this.panelIcon=new JPanel(new FlowLayout(FlowLayout.LEFT));

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(this.desktop, BorderLayout.CENTER);
		this.getContentPane().add(panelIcon,BorderLayout.SOUTH);
		
		this.popupMenuButton=new ButtonPopupMenu(this);
		
		
		this.setJMenuBar(this.getUserMenuBar());
	}
	
	/** получить меню, которое будет добавлено в качестве JMenuBar */
	protected  abstract JMenuBar getUserMenuBar();
	
	/** получить ActionListener на основании объекта и метода 
	 * @param object - объект, у которого вызываетс€ метод
	 * @param methodName - public(must be) метод
	 * @return
	 */
	protected ActionListener getActionListener(final Object object, String methodName){
		ActionListener returnValue=null;
		try{
			final Method method=object.getClass().getMethod(methodName, new Class[]{});
			returnValue=new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent event) {
					try{
						method.invoke(object, new Object[]{});				
					}catch(Exception ex){};
				}
			};
		}catch(Exception ex){
			error("getActionListener Exception:"+ex.getMessage());
		}
		return returnValue;
	}
	/** зарегестрированные окна в системе */
	private ArrayList<RegisteredWindow> registeredWindow=new ArrayList<RegisteredWindow>();
	/** кнопки на панели окон */
	private ArrayList<JToggleButton> buttonIcons=new ArrayList<JToggleButton>();
	
	@Override
	public boolean sendMessage(AbstractInternalFrame owner, WindowMessage message) {
		if(isMessageForMainWindow(message.getMessageId())){
			// сообщение дл€ главного окна
			if(message.getMessageId()==WindowMessageType.UNREGISTER_WINDOW){
				// удалить окно, которое послало данное сообщение из списка зарегестрированных дл€ отображени€ окон
				try{
					AbstractInternalFrame internalFrame=(AbstractInternalFrame)message.getArgument();
					internalFrame.setVisible(false);
					this.desktop.remove(internalFrame);
					int index=this.getIndexRegisterWindowByListener(owner);
					if(index>=0){
						this.panelIcon.remove(this.buttonIcons.get(index));
						this.panelIcon.repaint();
						this.buttonIcons.remove(index);
					}
					this.removeWindowListener(owner);
					return true;
				}catch(Exception ex){
					System.err.println("FrameMain#sendMessage Exception:"+ex.getMessage());
					return false;
				}
			}
			if(message.getMessageId()==WindowMessageType.REGISTER_WINDOW){
				try{
					WindowIdentifier windowIdentifier=(WindowIdentifier)message.getAdditionalIdentifier();
					AbstractInternalFrame internalFrame=(AbstractInternalFrame)message.getArgument();
					// check singelton
					int windowIndex=getIndexOfRegisterWindow(windowIdentifier);
					if(( internalFrame.isSingleton()==false)||
					   ((internalFrame.isSingleton()==true)&&(windowIndex<0))
					   ){
						// unselect all buttons
						for(int counter=0;counter<this.buttonIcons.size();counter++){
							this.buttonIcons.get(counter).setSelected(false);
						}
						// unselect all window
						WindowMessage messageDeselected=new WindowMessage(WindowMessageType.UNSELECTED_SET);
						for(int counter=0;counter<this.registeredWindow.size();counter++){
							this.registeredWindow.get(counter).getFrame().notifyMessage(messageDeselected);
						}
						// create Window
						this.desktop.add(internalFrame);
						internalFrame.setVisible(true);
						internalFrame.toFront();
						try{
							internalFrame.setSelected(true);
						}catch(Exception ex){};
						
						this.addWindowListener(internalFrame,windowIdentifier);
						// create ToggleButton and add it
						JToggleButton button=new JToggleButton(windowIdentifier.getCaption(),true);
						button.addActionListener(new ActionListener(){
							@Override
							public void actionPerformed(ActionEvent event){
								FrameMain.this.onIconButton(event);
							}
						});
						button.addMouseListener(new MouseListener(){
							@Override
							public void mouseClicked(MouseEvent event) {
								if(event.getButton()==MouseEvent.BUTTON3){
									FrameMain.this.onButtonPopup(event);
								}
							}
							@Override
							public void mouseEntered(MouseEvent event) {
							}
							@Override
							public void mouseExited(MouseEvent event) {
							}
							@Override
							public void mousePressed(MouseEvent event) {
							}
							@Override
							public void mouseReleased(MouseEvent event) {
							}
						});
						this.buttonIcons.add(button);
						this.panelIcon.add(button);
						this.moveToCenterOfDesktop(internalFrame);
						return true;
					}else{
						// установить окно как выделенное, которое уже было создано
						// set windows as selected, wich has been created
						this.registeredWindow.get(windowIndex).getFrame().notifyMessage(new WindowMessage(WindowMessageType.SELECTED_SET));
						return false;
					}
				}catch(Exception ex){
					System.err.println("FrameMain#sendMessage Exception:"+ex.getMessage());
					return false;
				}
				
			}
			if(message.getMessageId()==WindowMessageType.MOVE_TO_CENTER){
				this.moveToCenterOfDesktop((AbstractInternalFrame)message.getArgument());
				return true;
			}
			if(message.getMessageId()==WindowMessageType.FOCUS_WAS_GOT){
				// отжать все кнопки, кроме относ€щейс€ к выделенному объекту
				int index=getIndexRegisterWindowByListener(owner);
				if(index>=0){
					for(int counter=0;counter<this.buttonIcons.size();counter++){
						if(index!=counter){
							this.buttonIcons.get(counter).setSelected(false);
						}else{
							this.buttonIcons.get(counter).setSelected(true);
						}
					}
				}
				return true;
			}
			if(message.getMessageId()==WindowMessageType.REGISTER_CHILD_WINDOW){
				if(message.getArgument() instanceof AbstractInternalFrame){
					this.desktop.add((AbstractInternalFrame)message.getArgument());
					return true;
				}else{
					return false;
				}
			}
			if(message.getMessageId()==WindowMessageType.UNREGISTER_CHILD_WINDOW){
				if(message.getArgument() instanceof AbstractInternalFrame){
					this.desktop.remove((AbstractInternalFrame)message.getArgument());
					this.desktop.repaint();
					owner.notifyMessage(new WindowMessage(WindowMessageType.SELECTED_SET));
					return true;
				}else{
					return false;
				}
			}
			if(message.getMessageId()==WindowMessageType.GET_DESKTOP_DIMENSION){
				message.setArgument(new Dimension(this.desktop.getWidth(), this.desktop.getHeight()));
				return true;
			}
			return false;
		}else{
			// сообщение не дл€ главного окна, а дл€ передачи искомым окнам
			if(isMessageForSingleton(message.getMessageId())){
				// сообщение дл€ одного конкретного окна 
				AbstractInternalFrame internalFrame=(AbstractInternalFrame)message.getArgument();
				int index=this.getIndexOfRegisterWindow(internalFrame);
				if(index>=0){
					this.registeredWindow.get(index).getFrame().notifyMessage(message);
					return true;
				}else{
					return false;
				}
			}else{
				// сообщение дл€ разных окон, возможно дл€ группы, возможно дл€ всех  
				try{
					for(RegisteredWindow listener:this.registeredWindow){
						if((owner!=null)&&(owner.equals(listener))){
							listener.getFrame().notifyMessage(message);
						}
					}
					return true;
				}catch(Exception ex){
					return false;
				}
			}
		}
	}
	
	/** €вл€етс€ ли данное сообщение типом дл€ главного окна, а не дл€ дочернего */
	private boolean isMessageForMainWindow(int messageId){
		if(
			 (messageId==WindowMessageType.UNREGISTER_WINDOW)
		   ||(messageId==WindowMessageType.REGISTER_WINDOW)
		   ||(messageId==WindowMessageType.MOVE_TO_CENTER)
		   ||(messageId==WindowMessageType.FOCUS_WAS_GOT)
		   ||(messageId==WindowMessageType.REGISTER_CHILD_WINDOW)
		   ||(messageId==WindowMessageType.UNREGISTER_CHILD_WINDOW)
		   ||(messageId==WindowMessageType.GET_DESKTOP_DIMENSION)
				){
			return true;
		}else{
			return false;
		}
	}
	
	/** €вл€етс€ ли данное сообщение сообщением дл€ конкретного окна */
	private boolean isMessageForSingleton(int messageId){
		if(
		     (messageId==WindowMessageType.CLOSE_WINDOW)
		   ||(messageId==WindowMessageType.ICONIFY_WINDOW)
		   ||(messageId==WindowMessageType.DEICONIFY_WINDOW)
				){
			return true;
		}else{
			return false;
		}
	}

	/** получить индекс окна по зарегестрированному Frame*/
	private int getIndexOfRegisterWindow(AbstractInternalFrame frame){
		int returnValue=(-1);
		for(int counter=0;counter<this.registeredWindow.size();counter++){
			try{
				if(this.registeredWindow.get(counter).getFrame().equals(frame)){
					returnValue=counter;
					break;
				}
			}catch(Exception ex){
				System.err.println("FrameMain#countRegisterWindow Exception: "+ex.getMessage());
			}
		}
		return returnValue;
	}
	
	/** получить индекс окна по его Caption */
	private int getIndexOfRegisterWindow(WindowIdentifier identifier){
		int returnValue=(-1);
		for(int counter=0;counter<this.registeredWindow.size();counter++){
			try{
				if(this.registeredWindow.get(counter).getWindowIdentifiers().equals(identifier)){
					returnValue=counter;
					break;
				}
			}catch(Exception ex){
				System.err.println("FrameMain#countRegisterWindow Exception: "+ex.getMessage());
			}
		}
		return returnValue;
	}
	
	/* подсчитать кол-во элементов, с указанным названием - подсчитать кол-во открытых окон 
	private int countRegisterWindow(String caption){
		int returnValue=0;
		for(int counter=0;counter<this.registeredWindow.size();counter++){
			try{
				if(this.registeredWindow.get(counter).getWindowIdentifiers().getCaption().equals(caption)){
					returnValue++;
				}
			}catch(Exception ex){
				System.err.println("FrameMain#countRegisterWindow Exception: "+ex.getMessage());
			}
		}
		return returnValue;
	}*/
	
	
	@Override
	public boolean addWindowListener(AbstractInternalFrame listener, WindowIdentifier identifier) {
		this.registeredWindow.add(new RegisteredWindow(listener,identifier));
		return true;
	}

	@Override
	public boolean removeWindowListener(AbstractInternalFrame listener) {
		// remove ToggleButton
		int index=this.getIndexRegisterWindowByListener(listener);
		if(index>=0){
			this.registeredWindow.remove(index);
			return true;
		}else{
			return false;
		}
	}
	
	/** получить индекс зарегестрированного окна по его слушателю */
	private int getIndexRegisterWindowByListener(IMessageListener listener){
		int index=(-1);
		for(int counter=0;counter<this.registeredWindow.size();counter++){
			if(this.registeredWindow.get(counter).getFrame().equals(listener)){
				index=counter;
			}
		}
		return index;		
	}

	private void onButtonPopup(MouseEvent event){
		if(event.getSource() instanceof JToggleButton){
			for(int counter=0;counter<this.registeredWindow.size();counter++){
				if(this.buttonIcons.get(counter).equals(event.getSource())){
					this.popupMenuButton.setSelectedInternalFrame(this.registeredWindow.get(counter).getFrame());
					this.popupMenuButton.show((JToggleButton)event.getSource(), event.getX(), event.getY());
				}
			}
		}
	}
	
	/** реакци€ на нажатие клавиши активации окна на панели кнопок, которые соответствуют запущенным окнам высокого уровн€*/
	private void onIconButton(ActionEvent e){
		if(e.getSource() instanceof JToggleButton){
			int index=(-1);
			// отжать все кнопки кроме активированной 
			for(int counter=0;counter<this.registeredWindow.size();counter++){
				if(this.buttonIcons.get(counter).equals(e.getSource())){
					this.buttonIcons.get(counter).setSelected(true);
					this.registeredWindow.get(counter).getFrame().notifyMessage(new WindowMessage(WindowMessageType.SELECTED_SET));
					index=counter;
				}else{
					this.buttonIcons.get(counter).setSelected(false);
				}
			}
			// послать сообщение о переводе окна в активное  
			this.registeredWindow.get(index).getFrame().notifyMessage(new WindowMessage(WindowMessageType.MOVE_ON_TOP));
		}else{
			// объект-источник событи€ не JToggleButton
		}
	}

	
	/** установить данное окно в центра */
	private void moveToCenterOfDesktop(JInternalFrame internalFrame){
		internalFrame.setBounds((this.desktop.getWidth()-internalFrame.getWidth())/2,
					   		   (this.desktop.getHeight()-internalFrame.getHeight())/2,	
					   		    internalFrame.getWidth(),
					   		    internalFrame.getHeight());
		
	}

	
	/** хранилище дл€ посто€нных объектов - аналог IoC Container (only for Singleton) */
	private static HashMap<String,Object> singletonProperties=new HashMap<String,Object>();
	
	/** установить объект по его имени */
	public static void setObject(String name, Object object){
		singletonProperties.put(name, object);
	}
	
	/** получить объект, по его имени */ 
	public static Object getObject(String name){
		return singletonProperties.get(name);
	}
}
