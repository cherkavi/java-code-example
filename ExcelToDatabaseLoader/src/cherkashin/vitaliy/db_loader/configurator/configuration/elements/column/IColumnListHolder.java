package cherkashin.vitaliy.db_loader.configurator.configuration.elements.column;

import java.util.List;

import cherkashin.vitaliy.db_loader.exception.EDbLoaderException;

public interface IColumnListHolder {
	/**  
	 * @return columns 
	 * @throws EDbLoaderException
	 */
	public List<Column> getColumns() throws EDbLoaderException;
}
