package bonpay.jobber.launcher.settings.storage;

/** сохранение объектов во внешнем хранилище */
public interface IStorage {
	public boolean loadAllRecords();
	public boolean saveAllRecords();
	public Object getRecord(String name);
	public boolean putRecord(String name, Object value);
	public void removeRecord(String name);
	public boolean replaceRecord(String name, Object value);
}
