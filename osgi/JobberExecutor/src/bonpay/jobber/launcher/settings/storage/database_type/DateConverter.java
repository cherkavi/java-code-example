package bonpay.jobber.launcher.settings.storage.database_type;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter extends DatabaseTypeConverter{
	private String dateFormat="yyyy.MM.dd HH:mm:ss";
	
	public DateConverter(){
		super("D");
	}
	
	@Override
	public String getType(){
		return super.getType()+" "+dateFormat; 
	}
	
	@Override
	public Object getObjectFromString(String value) throws Exception {
		SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
		return sdf.parse(value);
	}

	@Override
	public String getStringFromObject(Object value) throws Exception {
		SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
		return sdf.format((Date)value);
	}

	@Override
	public String getTypeFromObject(Object object) {
		if(object instanceof Date){
			return this.getType();
		}else{
			return null;
		}
	}
	
}
