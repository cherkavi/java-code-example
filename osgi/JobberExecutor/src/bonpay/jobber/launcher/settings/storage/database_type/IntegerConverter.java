package bonpay.jobber.launcher.settings.storage.database_type;

public class IntegerConverter extends DatabaseTypeConverter{

	public IntegerConverter(){
		super("I");
	}
	
	@Override
	public Object getObjectFromString(String value) throws Exception {
		return new Integer(Integer.parseInt(value));
	}

	@Override
	public String getStringFromObject(Object value) throws Exception {
		return Integer.toString((Integer)value);
	}

	@Override
	public String getTypeFromObject(Object object) {
		if(object instanceof Integer){
			return this.getType();
		}else{
			return null;
		}
	}
	
}
