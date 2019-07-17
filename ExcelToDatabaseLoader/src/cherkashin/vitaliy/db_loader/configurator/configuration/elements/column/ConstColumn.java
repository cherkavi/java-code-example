package cherkashin.vitaliy.db_loader.configurator.configuration.elements.column;

/** column with const value */
public class ConstColumn extends Column{

	/**
	 * @param tableField - name of DataBase table field
	 * @param value - const value
	 */
	public ConstColumn(String tableField) {
		super(-1, tableField);
	}

}
