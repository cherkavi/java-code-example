package insurance_test;

import java.util.Arrays;
import java.util.List;

import insurance_test.exists.AxiomaFields;
import insurance_test.exists.AxiomaFields.XmlOutputVarType;

public interface ISpecificationSource {
	public class Record{
		/** Text name of a field */
		private String name;
		/** xml tag name in output XML object */
		private String xmlTag;
		/** required/mandatory  field */
		private boolean required;
		/** data type of field */
		private XmlOutputVarType dataType;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getXmlTag() {
			return xmlTag;
		}
		public void setXmlTag(String xmlTag) {
			this.xmlTag = xmlTag;
		}
		public boolean isRequired() {
			return required;
		}
		public void setRequired(boolean required) {
			this.required = required;
		}
		public XmlOutputVarType getDataType() {
			return dataType;
		}
		public void setDataType(XmlOutputVarType dataType) {
			this.dataType = dataType;
		}
		
	}
	/** the difference between WORD specification and {@link AxiomaFields } */
	public class Difference{
		public enum Cause{
			/** unique field in AxiomaFields*/
			unique_field,
			/** unique record in Specification  */
			unique_record,
			/** different between specification and code in Required field  */
			required, 
			/** different between specification and code in Datatype */
			data_type
		}

		private Cause[] cause;
		private String name;
		private String description;
		 
		public Difference(Cause uniqueField, 
						  String xmlOutputElementName,
						  String description) {
			this(new Cause[]{uniqueField}, xmlOutputElementName, description);
		}

		public Difference(Cause[] causes, String xmlTag, String description2) {
			this.cause=causes;
			this.name=xmlTag;
			this.description=description2;
		}

		public String toString() {
			return "Difference [cause=" + Arrays.toString(cause)
					+ ", description=" + description + ", name=" + name + "]";
		}

		
	}
	
	
	/** get all records from source  */
	public List<Record> getCascoRecords() throws Exception;
	/** get all records from source  */
	public List<Record> getOsagoRecords() throws Exception;
}
