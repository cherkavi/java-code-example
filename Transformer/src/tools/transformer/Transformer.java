package tools.transformer;

import tools.transformer.core.destination.IDestinationIterator;
import tools.transformer.core.exceptions.ETransformerException;
import tools.transformer.core.source.ISourceIterator;

public class Transformer {
	private ISourceIterator source;
	private IDestinationIterator destination;
	
	public void setSource(ISourceIterator source){
		this.source=source;
	}
	
	public void setDestination(IDestinationIterator destination ){
		this.destination=destination;
	}
	
	public void tranform(){
		try{
			source.setNextInstanceListener(destination);
			
			source.init();
			destination.init();
			
			while(source.hasNext()){
				destination.nextIteration(source.getNext());
			}
			
			destination.deInit();
			source.deInit();
			
		}catch(ETransformerException ex){
			
		}
	}
}
