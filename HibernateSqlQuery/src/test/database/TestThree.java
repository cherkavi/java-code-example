package test.database;

// Generated 23.06.2011 21:40:40 by Hibernate Tools 3.4.0.CR1

/**
 * TestThree generated by hbm2java
 */
public class TestThree implements java.io.Serializable {

	private Integer id;
	private String name;

	public TestThree() {
	}

	public TestThree(String name) {
		this.name = name;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
