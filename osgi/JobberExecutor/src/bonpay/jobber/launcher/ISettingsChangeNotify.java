package bonpay.jobber.launcher;

/** осведомитель об изменениях в настройках модуля */
public interface ISettingsChangeNotify {
	/** изменились настройки модуля  */
	public void notifySettingsChanged();
}
