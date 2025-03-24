package jdbc_copy.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DifferentField {
	public enum Cause{
		unique,
		type,
		size
	}

	private String name;
	private String typeName;
	private int size;
	private Cause cause;
	private String description;
	
	private DifferentField(String name, String typeName, int size, Cause uniquefirst, String description) {
		this.name=name;
		this.typeName=typeName;
		this.size=size;
		this.cause=uniquefirst;
		this.description=description;
	}

	public static Collection<? extends DifferentField> createDifferent(List<Field> unique1, Cause uniquefirst, String description) {
		ArrayList<DifferentField> returnValue=new ArrayList<DifferentField>();
		for(Field field: unique1){
			returnValue.add(new DifferentField(field.getName(), field.getTypeName(), field.getSize(), uniquefirst, description));
		}
		return returnValue;
	}

	public static DifferentField createDifferent(Field field, Cause type, String description) {
		return new DifferentField(field.getName(), field.getTypeName(), field.getSize(), type, description);
	}

	@Override
	public String toString() {
		return "cause=" + cause + ", description="+ description + ", name=" + name + ", size=" + size+ ", typeName=" + typeName;
	}
	
	
}
