package com.cherkashyn.vitalii.accounting.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ASSORTMENT_TYPE")
public class AssortmentType {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID", nullable = true)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="ID_PARENT")
    private AssortmentType parent;
    
    @Column(name="NAME")
    private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AssortmentType getParent() {
		return parent;
	}

	public void setParent(AssortmentType parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
