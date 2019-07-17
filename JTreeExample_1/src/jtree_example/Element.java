package jtree_example;

public class Element {
	private Integer kodClass;
	private String nameClass;
	private Integer kodSection;
	private String nameSection;
	
	/** элемент из таблицы для отображения на JTree */
	public Element(Integer kodClass, String nameClass, Integer kodSection, String nameSection ){
		this.kodClass=kodClass;
		this.nameClass=nameClass;
		this.kodSection=kodSection;
		this.nameSection=nameSection;
	}
	
	public Element(String nameClass, String nameSection){
		this.nameClass=nameClass;
		this.nameSection=nameSection;
	}

	public Integer getKodClass() {
		return kodClass;
	}
	public void setKodClass(Integer kodClass) {
		this.kodClass = kodClass;
	}
	public Integer getKodSection() {
		return kodSection;
	}
	public void setKodSection(Integer kodSection) {
		this.kodSection = kodSection;
	}

	public String getNameClass() {
		return nameClass;
	}

	public void setNameClass(String nameClass) {
		this.nameClass = nameClass;
	}

	public String getNameSection() {
		return nameSection;
	}

	public void setNameSection(String nameSection) {
		this.nameSection = nameSection;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean returnValue=false;
		if(obj instanceof Element){
			Element compareElement=(Element)obj;
			boolean nameEquals=false;
			try{
				nameEquals=(compareElement.nameClass.equals(this.nameClass)&&compareElement.nameSection.equals(this.nameSection));
			}catch(NullPointerException npe){};
			boolean kodEquals=false;
			try{
				kodEquals=(compareElement.kodClass.intValue()==this.kodClass.intValue()&&compareElement.kodSection.intValue()==this.kodSection.intValue());
			}catch(NullPointerException npe){};
			boolean classEquals=false;
			try{
				//classEquals=(compareElement.nameClass.equals(this.nameClass));
			}catch(NullPointerException npe){};
			returnValue=(  nameEquals || kodEquals || classEquals);
		}
		return returnValue;
	}
	
	
}
