package tools.transformer.core.source;

import tools.transformer.core.common_model.IModel;
import tools.transformer.core.destination.IDestinationIterator;
import tools.transformer.core.destination.INextInstanceListener;
import tools.transformer.core.exceptions.ETransformerException;


public interface ISourceIterator {
	/**
	 * init source
	 */
	void init() throws ETransformerException;

	/**
	 * check for get next value for save to {@link  IDestinationIterator}
	 * @return
	 */
	boolean hasNext() throws ETransformerException;

	/**
	 * get next object from source for save to {@link IDestinationIterator}
	 * @return
	 */
	IModel getNext() throws ETransformerException;

	/**
	 * deinitialize source
	 */
	void deInit() throws ETransformerException;

	void setNextInstanceListener(INextInstanceListener destination);	

}
