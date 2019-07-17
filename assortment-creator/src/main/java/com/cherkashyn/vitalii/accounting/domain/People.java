package com.cherkashyn.vitalii.accounting.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PEOPLE")
public class People {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = true)
	private Integer			id;

	@Column(name = "NAME")
	private String			name;

	@ManyToOne
	@JoinColumn(name = "ID_PEOPLE_POSITION")
	private PeoplePosition	position;

	@Column(name = "PASSWORD")
	private String			password;

	@Column(name = "IS_HIDE", columnDefinition = "TINYINT")
	private boolean			hide	= false;

	@Column(name = "PERCENT")
	private float			percent;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "PEOPLE2POINT", joinColumns = { @JoinColumn(name = "ID_PEOPLE", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "ID_POINT", nullable = false) })
	private Set<Point>		points;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PeoplePosition getPosition() {
		return position;
	}

	public void setPosition(PeoplePosition position) {
		this.position = position;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isHide() {
		return hide;
	}

	public void setHide(boolean hide) {
		this.hide = hide;
	}

	public float getPercent() {
		return percent;
	}

	public void setPercent(float percent) {
		this.percent = percent;
	}

}
