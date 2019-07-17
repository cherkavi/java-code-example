package bonpay.osgi.launcher.settings;

/** методы по получению текущих настроек */
public interface ISettingsAware {
	/** получить текущие настройки */
	public Settings getSettings();
}
