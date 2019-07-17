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
@Table(name="district")
public class District {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = true)
    private Integer id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="parent_id")
    private District parent;

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

	public District getParent() {
		return parent;
	}

	public void setParent(District parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parent == null || parent.getName()==null) ? 0 : parent.getName().hashCode());
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
		District other = (District) obj;
		if(parent==null ^ other.parent==null){
			return false;
		}
		if(parent!=null){// other.parent!=null
			if(parent.getName()==null ^ other.parent.getName()==null){
				return false;
			}
			if(parent.getName()!=null){
				if(!parent.getName().equals(other.parent.getName())){
					return false;
				}
			}
		}
		if(name==null ^ other.name==null){
			return false;
		}
		if(name==null){
			return true;
		}
		return name.equals(other.name);
	}

	
}
