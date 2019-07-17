package gui.EnterPoint;
import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

/**
 * класс, который является главным внутренним фреймом для приложения
 * @author cherkashinv
 */
public class JInternalFrameMain extends JInternalFrame implements InternalFrameListener{
	/** рабочий стол, на котором происходит отображение всех фреймов*/
	private JDesktopPane field_desktop;
	
	/**
	 * Главный внутренний фрейм
	 * @param desktop - рабочий стол внутреннего фрейма
	 * @param caption - заголовок для внутреннего фрейма
	 * @param width - ширина внутреннего фрейма
	 * @param height - высота внутреннего фрейма
	 */
	public JInternalFrameMain(JDesktopPane desktop,
							  String caption,
							  int width,
							  int height){
		super(caption,true,true,true,true);
		this.field_desktop=desktop;
		this.addInternalFrameListener(this);
		this.setSize(width,height);
		this.setVisible(true);
		this.field_desktop.add(this);
		Position.set_frame_to_center(this, field_desktop, 20, 20);
	}

	@Override
	public void internalFrameActivated(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameClosed(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		System.exit(0);
	}

	@Override
	public void internalFrameClosing(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameDeactivated(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameDeiconified(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameIconified(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void internalFrameOpened(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}
}
