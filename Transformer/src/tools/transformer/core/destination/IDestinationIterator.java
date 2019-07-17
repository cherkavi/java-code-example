package tools.transformer.core.destination;

import tools.transformer.core.common_model.IModel;
import tools.transformer.core.exceptions.ETransformerException;

/**
 * 
 * destination interface 
 *
 */
public interface IDestinationIterator extends INextInstanceListener{
	/** *
	 * init destination
	 */
	void init() throws ETransformerException;

	/** put next object to destination */
	void nextIteration(IModel next) throws ETransformerException;

	/**
	 * deinitialize 
	 */
	void deInit() throws ETransformerException;

}
