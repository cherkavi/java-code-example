package bonpay.osgi.launcher.settings;

import java.util.ArrayList;

import bonpay.osgi.launcher.ISettingsChangeNotify;


public class SettingsAware implements ISettingsAware{
	private Settings settings;

	public SettingsAware(Settings settings){
		this.settings=settings;
	}
	
	@Override
	public Settings getSettings() {
		return settings;
	}
	
	/** установить новый объект {@link Settings}*/
	public void setSettings(Settings settings){
		this.settings=settings;
		for(ISettingsChangeNotify changeNotify : listOfChangeNotify){
			changeNotify.notifySettingsChanged();
		}
	}
	
	private ArrayList<ISettingsChangeNotify> listOfChangeNotify=new ArrayList<ISettingsChangeNotify>();
	
	/** добавить слушателя для оповещения об изменившемся объекте Settings */
	public void addSettingsChangeNotify(ISettingsChangeNotify changeNotify){
		this.listOfChangeNotify.add(changeNotify);
	}

}
