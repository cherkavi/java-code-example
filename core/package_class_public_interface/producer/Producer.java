package private_class_public_interface.producer;

import private_class_public_interface.consumer.IGetString;

public class Producer {
	public IGetString getHolder(){
		return new TempGetString();
		/*return new IGetString(){
			@Override
			public String getString() {
				return "hello from inner class";
			}
			
		};*/
	}
	
	class TempGetString implements IGetString{
		@Override
		public String getString() {
			return this.getClass().getName()+this.getClass().getModifiers();
		}
		
	}
}
