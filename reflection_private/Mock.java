package reflection_private;

public class Mock {

	public String getPublicValue(){
		return "public #getPublicValue";
	}

	protected String getProtectedValue(){
		return "public #getProtectedValue";
	}
	
	String getDefaultValue(){
		return "public #getDefaultValue";
	}

	private String getPrivateValue(){
		return "public #getDefaultValue";
	}
}
