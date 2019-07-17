package jmx.utility.server.interfaces;

public class NotificationAware<T extends INotification> {
	private T object;
	public NotificationAware(T object){
		this.object=object;
	}
	public T getNotificationProxyObject(){
		return this.object;
	}
	public void setNotificationProxyObject(T notification){
		this.object=notification;
	}
	
}
