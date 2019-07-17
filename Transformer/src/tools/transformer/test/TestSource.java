package tools.transformer.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

import tools.transformer.core.common_model.IModel;
import tools.transformer.core.destination.INextInstanceListener;
import tools.transformer.core.exceptions.ETransformerException;
import tools.transformer.core.source.ISourceIterator;

public class TestSource implements ISourceIterator{
	private Logger logger=Logger.getLogger(this.getClass());
	
	public void deInit() throws ETransformerException {
		logger.debug("source deInit");
	}

	public IModel getNext() throws ETransformerException {
		return this.model.get(++this.currentIndex);
	}
	
	public boolean hasNext() throws ETransformerException {
		return this.currentIndex<(this.model.size()-1);
	}

	private List<TempModel> model=null;
	private int currentIndex=-1;
	
	public void init() throws ETransformerException {
		logger.debug("source init ");
		model=new ArrayList<TempModel>();
		model.add(new TempModel(new String[]{"one", "two", "three", "four"}));
		model.add(new TempModel(new String[]{"1", "2", "3", "4"}));
		model.add(new TempModel(new String[]{"five", "six", "seven", "eight"}));
		model.add(new TempModel(new String[]{"5", "6", "7", "8"}));
	}

	private HashSet<INextInstanceListener> listeners=new HashSet<INextInstanceListener>();
	
	public void setNextInstanceListener(INextInstanceListener destination) {
		listeners.add(destination);
	}

}

class TempModel implements IModel{
	private Object[] values;
	
	public TempModel(Object[] values){
		this.values=values;
	}
	
	public Object[] getObjects() {
		return this.values;
	}

	public String toString(){
		return Arrays.toString(values);
	}
}
