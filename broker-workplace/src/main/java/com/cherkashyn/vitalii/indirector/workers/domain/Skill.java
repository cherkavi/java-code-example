package com.cherkashyn.vitalii.indirector.workers.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="skill")
public class Skill {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = true)
    private Integer id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="category_id")
    private Category category;

    public void setId(Integer aValue) {
        id = aValue;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String aValue) {
        name = aValue;
    }

    public String getName() {
        return name;
    }

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((category == null || category.getName()==null) ? 0 : category.getName().hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Skill other = (Skill) obj;
		if(category==null && other.category!=null){
			return false;
		}
		if(category!=null && other.category==null){
			return false;
		}
		if(category!=null){
			if(category.getName()==null && other.category.getName()!=null){
				return false;
			}
			if(!category.getName().equals(other.category.getName())){
				return false;
			}
		}
		if(name==null && other.name==null){
			return true;
		}
		if(name!=null && other.name==null){
			return false;
		}
		if(name==null && other.name!=null){
			return false;
		}
		return name.equals(other.name);
	}

    
}
