package bonpay.jobber.launcher.settings.storage.database_type;

public class FloatConverter extends DatabaseTypeConverter{

	public FloatConverter(){
		super("F");
	}
	
	@Override
	public Object getObjectFromString(String value) throws Exception {
		return new Float(Float.parseFloat(value)); 
	}

	@Override
	public String getStringFromObject(Object value) throws Exception {
		return Float.toString((Float)value);
	}

	@Override
	public String getTypeFromObject(Object object) {
		if(object instanceof Float){
			return this.getType();
		}else{
			return null;
		}
	}
	
}
