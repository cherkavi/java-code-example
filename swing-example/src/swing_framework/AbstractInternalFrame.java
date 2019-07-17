package swing_framework;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import swing_framework.messages.IMessageListener;
import swing_framework.messages.IMessageSender;
import swing_framework.messages.WindowMessage;
import swing_framework.messages.WindowMessageType;
import swing_framework.window.RegisteredWindow;
import swing_framework.window.WindowIdentifier;

/** окно на рабочем столе */
public abstract class AbstractInternalFrame extends JInternalFrame implements InternalFrameListener, 
																			  IMessageListener,
																			  IMessageSender{
	private static final long serialVersionUID=1L;
	protected IMessageSender messageSender;
	protected int width;
	protected int height;
	protected ArrayList<RegisteredWindow> listOfRegisteredWindow=new ArrayList<RegisteredWindow>();

	protected void error(Object object){
		System.err.print("ERROR AbstractinternalFrame ");
		System.err.println(object);
	}
	protected void debug(Object object){
		System.out.print("DEBUG AbstractinternalFrame ");
		System.out.println(object);
	}
	
	/** окно на рабочем столе 
	 * @param caption - заголовок окна 
	 * @param messageSender - объект, которому следует отправлять все сообщения 
	 * @param width - ширина окна 
	 * @param height - высота окна
	 * @param isModal - является ли окно модальным
	 * @param isSingleton - является ли данная копия окна уникальной в контексте родителя 
	 */
	public AbstractInternalFrame(String caption,
								IMessageSender messageSender,
								int width,
								int height,
								boolean isModal,
								boolean isSingleton){
		super(caption);
		this.singleton=isSingleton;
		this.messageSender=messageSender;
		this.width=width;
		this.height=height;
		this.addInternalFrameListener(this);
		//this.setPreferredSize(new Dimension(this.width,this.height));
		this.setBounds(1,1,this.width,this.height);
		this.setVisible(true);
		this.setClosable(true);
		this.setIconifiable(true);
		this.setResizable(true);
		this.setMaximizable(true);
		messageSender.sendMessage(this, new WindowMessage(WindowMessageType.REGISTER_WINDOW,
				new WindowIdentifier(this.getTitle(),false),
				this
				));
		messageSender.sendMessage(this,new WindowMessage(WindowMessageType.MOVE_TO_CENTER,null,this));
		if(isModal==true){
			this.setModal(true);
		}
		this.toFront();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		try{
			this.setSelected(true);
		}catch(Exception ex){};
		
	}

	private boolean modal=false;
	
	/** установить данное окно как модальное */
	public void setModal(boolean value){
		if(value){
			messageSender.sendMessage(this, new WindowMessage(WindowMessageType.CHILD_SET_MODAL));
		}
		this.modal=value;
	}
	
	/** получить значение модального окна */
	public boolean isModal(){
		return this.modal;
	}
	
	private boolean singleton;
	public boolean isSingleton() {
		return singleton;
	}

	public void setSingleton(boolean singleton) {
		this.singleton = singleton;
	}

	
	@Override
	public void internalFrameClosed(InternalFrameEvent e) {
		this.beforeClose();
		// разослать закрытие окна для всех дочерних
		WindowMessage closeMessage=new WindowMessage(WindowMessageType.CLOSE_WINDOW);
		while(this.listOfRegisteredWindow.size()>0){
			this.listOfRegisteredWindow.get(this.listOfRegisteredWindow.size()-1).getFrame().notifyMessage(closeMessage);
		}
		messageSender.sendMessage(this, new WindowMessage(WindowMessageType.UNREGISTER_WINDOW,null, this));
	}
	
	/** переопределение обязывает наличие super().notifyMessage()*/
	@Override
	public boolean notifyMessage(WindowMessage message){
		boolean returnValue=false;
		switch(message.getMessageId()){
			case WindowMessageType.MOVE_ON_TOP:{
				this.moveToFront();
				this.childOnTop();
				returnValue=true;
			};break;
			case WindowMessageType.SELECTED_SET:{
				try{
					this.setSelected(true);
				}catch(Exception ex){};
				returnValue=true;
			};break;
			case WindowMessageType.UNSELECTED_SET:{
				try{
					this.setSelected(false);
				}catch(Exception ex){};
				returnValue=true;
			};break;
			case WindowMessageType.CLOSE_WINDOW:{
				if(this.getModalWindowIndex()<0){
					this.internalFrameClosed(null);
				}
				returnValue=true;
			};break;
			case WindowMessageType.ICONIFY_WINDOW:{
				if(this.getModalWindowIndex()<0){
					try{
						this.setIcon(true);
					}catch(Exception ex){};
				}
				returnValue=true;
			};break;
			
			case WindowMessageType.DEICONIFY_WINDOW:{
				try{
					this.setIcon(false);
				}catch(Exception ex){};
				returnValue=true;
			};break;

			default:;
		}
		return returnValue;
	}
	
	/** переместить все дочерние окна на верхний уровень */
	private void childOnTop(){
		for(RegisteredWindow window:this.listOfRegisteredWindow){
			window.getFrame().toFront();
		}
		checkModal();
	}

	/** получить индекс модального окна */
	private int getModalWindowIndex(){
		int returnValue=(-1);
		for(int counter=0;counter<this.listOfRegisteredWindow.size();counter++){
			if(this.listOfRegisteredWindow.get(counter).getFrame().isModal()){
				returnValue=counter;
				break;
			}
		}
		return returnValue;
	}
	
	/** проверить данное окно на наличие в дочерних окнах модальности , и если есть - передать фокус самому верхнему модальному окну*/
	private void checkModal(){
		int modalIndex=this.getModalWindowIndex();
		if(modalIndex>=0){
			this.listOfRegisteredWindow.get(modalIndex).getFrame().notifyMessage(new WindowMessage(WindowMessageType.SELECTED_SET));
		}
	}
	
	@Override
	public void internalFrameActivated(InternalFrameEvent e) {
		this.messageSender.sendMessage(this, new WindowMessage(WindowMessageType.FOCUS_WAS_GOT));
		this.childOnTop();
	}

	@Override
	public void internalFrameClosing(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameDeactivated(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameDeiconified(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameIconified(InternalFrameEvent e) {
	}

	@Override
	public void internalFrameOpened(InternalFrameEvent e) {
	}

	private MouseListener[] titleMouseListener;
	private MouseMotionListener[] titleMouseMotionListener;
	private MouseWheelListener[] titleMouseWheelListener;
	private boolean initIconifiable;
	private boolean initClosable;
	private boolean initMaximizable;
	private boolean initResizable;

	/** забрать и сохранить все слушатели событий мыши */
	private void getMouseListenerFromComponent(JComponent component){
		titleMouseListener=component.getMouseListeners();
		titleMouseMotionListener=component.getMouseMotionListeners();
		titleMouseWheelListener=component.getMouseWheelListeners();
		if(titleMouseListener!=null){
			for(int counter=0;counter<titleMouseListener.length;counter++){
				component.removeMouseListener(titleMouseListener[counter]);
			}
		}
		if(titleMouseMotionListener!=null){
			for(int counter=0;counter<titleMouseMotionListener.length;counter++){
				component.removeMouseMotionListener(titleMouseMotionListener[counter]);
			}
		}
		if(titleMouseWheelListener!=null){
			for(int counter=0;counter<titleMouseWheelListener.length;counter++){
				component.removeMouseWheelListener(titleMouseWheelListener[counter]);
			}
		}
		initIconifiable=this.isIconifiable();
		initClosable=this.isClosable();
		initMaximizable=this.isMaximizable();
		initResizable=this.isResizable();
		this.setIconifiable(false);
		this.setClosable(false);
		this.setMaximizable(false);
		this.setResizable(false);
	}
	
	
	/** вернуть на место все слушатели событий мыши для компонента */
	private void setMouseListenerToComponent(JComponent component){
		if(titleMouseListener!=null){
			for(int counter=0;counter<titleMouseListener.length;counter++){
				component.addMouseListener(titleMouseListener[counter]);
			}
		}
		if(titleMouseMotionListener!=null){
			for(int counter=0;counter<titleMouseMotionListener.length;counter++){
				component.addMouseMotionListener(titleMouseMotionListener[counter]);
			}
		}
		if(titleMouseWheelListener!=null){
			for(int counter=0;counter<titleMouseWheelListener.length;counter++){
				component.addMouseWheelListener(titleMouseWheelListener[counter]);
			}
		}
		this.setIconifiable(initIconifiable);
		this.setClosable(initClosable);
		this.setMaximizable(initMaximizable);
		this.setResizable(initResizable);
	}
	
	
	/** отправка сообщения на объект, который рассылает слушателям 
	 * @param owner - собственник данного сообщения ( или null)
	 * @param message - сообщение, которое будет послано 
	 * @return
	 */
	@Override
	public boolean sendMessage(AbstractInternalFrame owner, WindowMessage message){
		/** проанализировать тип сообщения, который нужно послать - тип для данного родительского окна, либо же нужно послать дечерним */
		if(isMessageForMainWindow(message.getMessageId())){
			// сообщение для родительского окна 
			if(message.getMessageId()==WindowMessageType.UNREGISTER_CHILD_WINDOW){
				// послать сообщение родительскому окну, с просьбой убрать из рабочего стола добавленное окно
				messageSender.sendMessage(this, message);
			}
			// сообщение для родительского окна 
			if(message.getMessageId()==WindowMessageType.UNREGISTER_WINDOW){
				// в данном контексте интерпретируется как удаление зарегестрированого дочернего окна, с удалением окна с рабочего стола 
				// послать сообщение родительскому окну, с просьбой убрать из рабочего стола добавленное окно 
				messageSender.sendMessage(this, new WindowMessage(WindowMessageType.UNREGISTER_CHILD_WINDOW,null,message.getArgument()));
				// удалить окно зарегестрированное в данном окне
				int removeIndex=this.getIndexOfRegisterWindow((AbstractInternalFrame)message.getArgument());
				if(removeIndex>=0){
					if(this.listOfRegisteredWindow.get(removeIndex).getFrame().isModal()){
						this.repaint();
						JComponent component=((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).getNorthPane();
						this.setMouseListenerToComponent(component);
					}else{
						this.repaint();
					}
					
					this.listOfRegisteredWindow.remove(removeIndex);
				}else{
					error("#sendMessage index of Child window was not found ");
				}
				
			}
			if(message.getMessageId()==WindowMessageType.CHILD_SET_MODAL){
				JComponent component=((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).getNorthPane();
				this.getMouseListenerFromComponent(component);

				//this.titleComponentDimension=((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).getNorthPane().getPreferredSize();
				//((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).getNorthPane().setPreferredSize(new Dimension(0,0));
				//this.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
			}
			if(message.getMessageId()==WindowMessageType.REGISTER_CHILD_WINDOW){
				messageSender.sendMessage(this, message);
			}
			if(message.getMessageId()==WindowMessageType.REGISTER_WINDOW){
				// в данном контексте интерпретируется как добавление дочернего окна к данному окну, и добавление окна на рабочий стол
					// поиск в списке дочерних окон такого же идентификатора 
				int index=this.getExistingIdentifierIndex((WindowIdentifier)message.getAdditionalIdentifier());
				if((index<0)||(this.listOfRegisteredWindow.get(index).getFrame().isSingleton()==false)){
					// добавить окно на рабочий стол
					messageSender.sendMessage(this, new WindowMessage(WindowMessageType.REGISTER_CHILD_WINDOW,message.getAdditionalIdentifier(),message.getArgument()));
					 	// зарегестрировать окно в данном окне 
					this.listOfRegisteredWindow.add(new RegisteredWindow((AbstractInternalFrame)message.getArgument(),(WindowIdentifier)message.getAdditionalIdentifier()));
				}
			}
			if(message.getMessageId()==WindowMessageType.MOVE_TO_CENTER){
				AbstractInternalFrame source=(AbstractInternalFrame)message.getArgument();
				source.setBounds(
						(this.getWidth()-source.getWidth())/2+this.getX(),
						(this.getHeight()-source.getHeight())/2+this.getY(),
						source.getWidth(),
						source.getHeight()
						);
				// переместить данное окно в центр, относительно текущего
					// получить координаты данного окна, относительно рабочего стола
			}
			if(message.getMessageId()==WindowMessageType.FOCUS_WAS_GOT){
				// сообщение окном о приобретении фокуса 
				this.messageSender.sendMessage(this, new WindowMessage(WindowMessageType.FOCUS_WAS_GOT));
			}
			if(message.getMessageId()==WindowMessageType.PARENT_NOTIFIER){
				this.messageSender.sendMessage(this,new WindowMessage(WindowMessageType.THIS_NOTIFIER,message.getAdditionalIdentifier(), message.getArgument()));
			}
			if(message.getMessageId()==WindowMessageType.THIS_NOTIFIER){
				this.parentNotifier(message);
			}
		}else{
			// сообщение для родительского класса 
			this.messageSender.sendMessage(this,message);
		}
		return false;
	}
	
	/** оповещение одним из дочерних компонентов родительского компонента сообщением */
	protected abstract void parentNotifier(WindowMessage message);
	
	/** просмотреть в дочерних окнах окно с таким же идентификатором 
	 * @return (-1) если не найден 
	 * */
	private int getExistingIdentifierIndex(WindowIdentifier additionalIdentifier) {
		int returnValue=(-1);
		for(int counter=0;counter<this.listOfRegisteredWindow.size();counter++){
			try{
				if(this.listOfRegisteredWindow.get(counter).getWindowIdentifiers().equals(additionalIdentifier)){
					returnValue=counter;
					break;
				}
			}catch(NullPointerException ex){};
		}
		return returnValue;
	}
	/** получить индекс окна по зарегестрированному Frame*/
	private int getIndexOfRegisterWindow(AbstractInternalFrame frame){
		int returnValue=(-1);
		for(int counter=0;counter<this.listOfRegisteredWindow.size();counter++){
			try{
				if(this.listOfRegisteredWindow.get(counter).getFrame().equals(frame)){
					returnValue=counter;
					break;
				}
			}catch(Exception ex){
				System.err.println("FrameMain#countRegisterWindow Exception: "+ex.getMessage());
			}
		}
		return returnValue;
	}

	
	/** является ли данное сообщение типом для данного окна, а не для дочернего */
	private boolean isMessageForMainWindow(int messageId){
		if(
			 (messageId==WindowMessageType.UNREGISTER_WINDOW)
		   ||(messageId==WindowMessageType.UNREGISTER_CHILD_WINDOW)
		   ||(messageId==WindowMessageType.REGISTER_WINDOW)
		   ||(messageId==WindowMessageType.REGISTER_CHILD_WINDOW)
		   ||(messageId==WindowMessageType.MOVE_TO_CENTER)
		   ||(messageId==WindowMessageType.FOCUS_WAS_GOT)
		   ||(messageId==WindowMessageType.CHILD_SET_MODAL)
		   ||(messageId==WindowMessageType.PARENT_NOTIFIER)
		   ||(messageId==WindowMessageType.THIS_NOTIFIER)
				){
			return true;
		}else{
			return false;
		}
	}
	
	
	
	/** зарегестрировать окно в системе */
	@Override
	public boolean addWindowListener(AbstractInternalFrame listener,WindowIdentifier identifier){
		
		return false;
	}
	
	/** удалить зарегестрированное окно из системы */
	@Override
	public boolean removeWindowListener(AbstractInternalFrame listener){
		
		return false;
	}

	/** получить ActionListener на основании объекта и метода 
	 * @param object - объект, у которого вызывается метод
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
					}catch(Exception ex){
						System.out.println("getActionListener Exception "+ex.getMessage());
					};
				}
			};
		}catch(Exception ex){
			error("getActionListener Exception:"+ex.getMessage());
		}
		return returnValue;
	}

	/** закрыть данное окно */
	public void close(){
		this.internalFrameClosed(null);		
	}
	
	/** вызывается перед закрытием окна для освобождения ресурсов */
	protected void beforeClose(){
	}
}
