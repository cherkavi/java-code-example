package copy_properties;

class ParentBean {
	private int id;
	private String name;
	private int excludeProperties;
	
	ParentBean(){
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getExcludeProperties() {
		return excludeProperties;
	}

	public void setExcludeProperties(int excludeProperties) {
		this.excludeProperties = excludeProperties;
	}
	
}
