package insurance_test;

import insurance_test.ISpecificationSource.Difference;
import insurance_test.ISpecificationSource.Record;
import insurance_test.ISpecificationSource.Difference.Cause;
import insurance_test.exists.AxiomaFields;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.FilterIterator;
import org.apache.commons.collections.Predicate;

public class Test_AxiomaSpecification {
	public static void main(String[] args){
		try{
			ISpecificationSource source=new MsWordSpecificationSource("resource/specification.doc");
			
			// check Casco fields 
			List<ISpecificationSource.Difference> listCasco=checkCasco(source);
			if(!listCasco.isEmpty()){
				System.out.println("----- Casco difference ----- ");
				for(int counter=0;counter<listCasco.size();counter++){
					System.out.println(counter+" : "+listCasco.get(counter));
				}
				System.out.println("----- ------- ------ ------- ");
			}
			// check Osago fields 
			List<ISpecificationSource.Difference> listOsago=checkOsago(source);
			if(!listOsago.isEmpty()){
				System.out.println("----- Osago difference ----- ");
				for(int counter=0;counter<listOsago.size();counter++){
					System.out.println(counter+" : "+listOsago.get(counter));
				}
				System.out.println("----- ------- ------ ------- ");
			}
		}catch(Exception ex){
			System.err.println("Exception:"+ex.getMessage());
			ex.printStackTrace(System.err);
		}
//		System.out.println("Is Static:"+ Modifier.isStatic(ISpecificationSource.Difference.Cause.class.getModifiers()));
//		System.out.println("Is Final :"+ Modifier.isFinal(ISpecificationSource.Difference.Cause.class.getModifiers()));
	}

	/** get Osago difference  */
	private static List<Difference> checkOsago(ISpecificationSource source) throws Exception{
		return check(source.getOsagoRecords(), Arrays.asList(AxiomaFields.getOsagoOnly()), "Osago");
	}

	/** get Casco difference  */
	private static List<Difference> checkCasco(ISpecificationSource source) throws Exception{
		return check(source.getCascoRecords(), Arrays.asList(AxiomaFields.getCascoOnly()), "Casco");
	}
	

	private static List<Difference> check(List<Record> recordsList, 
										  List<AxiomaFields> fields, 
										  String description){
		// CollectionUtils.filter(fields, axiomaXmlPredicate);
		fields=filterFields(fields, axiomaXmlPredicate);
		List<Difference> returnValue=new ArrayList<Difference>();
		// check for unique in specification
		returnValue.addAll(getUniqueInRecord(recordsList, fields, description));
		// check for unique in fields
		returnValue.addAll(getUniqueInFields(recordsList, fields, description));
		// check for difference 
		returnValue.addAll(getDifference(recordsList, fields, description));
		return returnValue;
	}
	
	private static List<AxiomaFields> filterFields(List<AxiomaFields> fields, Predicate predicate){
		ArrayList<AxiomaFields> returnValue=new ArrayList<AxiomaFields>(fields.size());
		for(AxiomaFields field:fields){
			if(predicate.evaluate(field)){
				returnValue.add(field);
			}
		}
		return returnValue;
	}
	

	private static Collection<? extends Difference> getDifference(List<Record> recordsList, 
																  List<AxiomaFields> fields, 
																  String description) {
		ArrayList<Difference> returnValue=new ArrayList<Difference>();
		for(Record record:recordsList){
			AxiomaFields field=getAxiomaFieldByName(fields, record.getXmlTag());
			if(field!=null){
				Difference diff=compareAxiomaFieldsAndRecord(record, field, description);
				if(diff!=null){
					returnValue.add(diff);
				}
			}
		}
		return returnValue;
	}

	private static Difference compareAxiomaFieldsAndRecord(Record record,
														   AxiomaFields field,
														   String description) {
		List<Difference.Cause> causes=new ArrayList<Difference.Cause>();
		if(record.isRequired()!=field.isRequired()){
			causes.add(Cause.required);
		}
		if(!record.getDataType().equals(field.getXmlOutputVarType())){
			causes.add(Cause.data_type);
		}
		if(causes.size()>0){
			return new Difference(causes.toArray(new Cause[]{}), record.getXmlTag(), description);
		}else{
			return null;
		}
	}

	private static Collection<? extends Difference> getUniqueInFields(List<Record> recordsList, 
																	  List<AxiomaFields> fields, 
																	  String description) {
		ArrayList<Difference> returnValue=new ArrayList<Difference>();
		for(AxiomaFields field:fields){
			if(getRecordByXmlTag(recordsList, 
									 field.getXmlOutputElementName())==null){
				returnValue.add(new Difference(Difference.Cause.unique_field, field.getXmlOutputElementName(), description));
			}
		}
		return returnValue;
	}

	/**
	 * get index of Record from list by XML tag name
	 * @param recordsList
	 * @param xmlOutputElementName
	 * @return
	 */
	private static Record getRecordByXmlTag(List<Record> recordsList,
											 String xmlOutputElementName) {
		for(int counter=0;counter<recordsList.size();counter++){
			Record currentRecord=recordsList.get(counter);
			if(currentRecord.getXmlTag().trim().equalsIgnoreCase(xmlOutputElementName)){
				return currentRecord;
			}
		}
		return null;
	}

	private static Collection<? extends Difference> getUniqueInRecord(List<Record> recordsList, 
																	  List<AxiomaFields> fields, 
																	  String description) {
		ArrayList<Difference> returnValue=new ArrayList<Difference>();
		for(Record record:recordsList){
			if(getAxiomaFieldByName(fields, record.getXmlTag())==null){
				returnValue.add(new Difference(Cause.unique_record, record.getXmlTag(), description));
			}
		}
		return returnValue;
	}


	private static AxiomaFields getAxiomaFieldByName(List<AxiomaFields> fields,
											   String xmlTag) {
		xmlTag=xmlTag.trim();
		for(AxiomaFields field: fields){
			if(field.getXmlOutputElementName().equalsIgnoreCase(xmlTag)){
				return field;
			}
		}
		return null;
	}


	/** predicate for check output XML value */
	private static final Predicate axiomaXmlPredicate=new Predicate(){
		public boolean evaluate(Object value) {
			if(value instanceof AxiomaFields){
				return ((AxiomaFields)value).getXmlOutputVarType()!=null;
			}
			return false;
		}
	};
	
	
}
