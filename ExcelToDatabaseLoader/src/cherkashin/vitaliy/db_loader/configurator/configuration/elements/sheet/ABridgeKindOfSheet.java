package cherkashin.vitaliy.db_loader.configurator.configuration.elements.sheet;

import java.util.List;

import cherkashin.vitaliy.db_loader.configurator.configuration.elements.column.Column;
import cherkashin.vitaliy.db_loader.writer.ColumnDataAdapter;

/**
 * abstraction for another kind of sheet (add functionality to Existing sheet) - use Bridge Pattern
 */
public abstract class ABridgeKindOfSheet {
	/** get collection of column for add to list  
	 * @param returnValue */
	public abstract List<ColumnDataAdapter> getColumnDataAdapters(List<ColumnDataAdapter> returnValue);

	/** get Property from Object 
	 * @param property - name of property 
	 * @return
	 * <ul>
	 * 	<li> <b>null</b> -  property does not found in this object </li>
	 * 	<li> <b>value</b> -  property from this object </li>
	 * </ul>
	 */
	public abstract Object getProperty(String property);

	/**
	 * get list of column's
	 * @param columns - column's of sheet
	 * @return columns of sheet, maybe something else
	 */
	public abstract List<Column> getColumns(List<Column> columns);

}
