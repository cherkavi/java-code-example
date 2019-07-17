package jdbc_copy.table;

import java.util.ArrayList;
import java.util.List;

/** ���� � ������� ���� ������ */
public class Field {
	private String name;
	private int type;
	private String typeName;
	private int size;
	
	public Field(String name, int type, String typeName, int size){
		this.name=name;
		this.type=type;
		this.typeName=typeName;
		this.size=size;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	
	
	/**
	 * get unique fields from first list ( which not found in second list )
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static List<Field> getUniqueFromFirstList(List<Field> list1, List<Field> list2){
		ArrayList<Field> returnValue=new ArrayList<Field>();
		for(Field field:list1){
			if(getFieldFromListByName(field.getName(), list2)==null){
				returnValue.add(field);
			}
		}
		return returnValue;
	}
	
	private static Field getFieldFromListByName(String name2, List<Field> list2) {
		for(Field field:list2){
			if(field.getName().equals(name2)){
				return field;
			}
		}
		return null;
	}

	private static List<String> getShareFields(List<Field> list1, 
											   List<Field> list2){
		ArrayList<String> returnValue=new ArrayList<String>();
		for(Field field:list1){
			if(getFieldFromListByName(field.getName(), list2)!=null){
				returnValue.add(field.getName());
			}
		}
		return returnValue;
	}
	
	/** 
	 * get difference between two list 
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static List<DifferentField> compareFields(List<Field> list1,
												List<Field> list2) {
		ArrayList<DifferentField> returnValue=new ArrayList<DifferentField>();
		// get not unique in first list
		List<Field> unique1=getUniqueFromFirstList(list1, list2);
		returnValue.addAll(DifferentField.createDifferent(unique1, DifferentField.Cause.unique,"first connection"));
		// get not unique in second list
		List<Field> unique2=getUniqueFromFirstList(list2, list1);
		returnValue.addAll(DifferentField.createDifferent(unique2, DifferentField.Cause.unique,"second connection"));
		
		// get equals in first and second
		List<String> shareFields=getShareFields(list1, list2);
		for(String fieldName:shareFields){
			Field fieldFirst=Field.getFieldFromListByName(fieldName, list1);
			Field fieldSecond=Field.getFieldFromListByName(fieldName, list2);
			// compare type
			if(fieldFirst.getType()!=fieldSecond.getType()){
				returnValue.add(DifferentField.createDifferent(fieldFirst, DifferentField.Cause.type,fieldFirst.getTypeName()+" <> "+fieldSecond.getTypeName()));
				continue;
			}
			// compare size
			if(fieldFirst.getSize()!=fieldSecond.getSize()){
				returnValue.add(DifferentField.createDifferent(fieldFirst, DifferentField.Cause.size, fieldFirst.getSize()+" <> "+fieldSecond.getSize()));
				continue;
			}
		}
		return returnValue;
	}
	
	
}
