package cherkashin.vitaliy.db_loader.writer;

import cherkashin.vitaliy.db_loader.configurator.configuration.elements.sheet.ALoaderSheet;
import cherkashin.vitaliy.db_loader.exception.EDbLoaderException;

public interface IWriter {
	/** init Writer for write data from Sheet 
	 * @param currentSheet - sheet for write data 
	 */
	public void init(ALoaderSheet<?> currentSheet) throws EDbLoaderException;
	
	/**
	 * write data from sheet to current Writer 
	 * @param nextData - data from sheet for write
	 * @throws EDbLoaderException - when write to database throw Exception
	 */
	public void writeData(ColumnDataAdapter[] nextData) throws EDbLoaderException;

	/**
	 * release all objects associated with current sheet
	 * @param currentSheet - sheet 
	 * @throws EDbLoaderException - when release object throw Exception
	 */
	public void deInit(ALoaderSheet<?> currentSheet) throws EDbLoaderException;

	/** 
	 * apply all changes
	 * @throws EDbLoaderException
	 */
	public void applyAllChanges() throws EDbLoaderException;
}
