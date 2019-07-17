package tools.transformer.core.destination;

import tools.transformer.core.instance.IInstance;

/** *
 * interface for notify about new instance ( begin, end )
 */
public interface INextInstanceListener {
	/**
	 * notify about the instance is begin 
	 * @param instance 
	 */
	public void nextInstanceBegin(IInstance instance);
	
	/**
	 * notify about the instance is end 
	 * @param instance
	 */
	public void nextInstanceEnd(IInstance instance);
}
