package com.test.database.db_beans;

import javax.persistence.*;

@Entity
@Table(name="PLACE")
public class Place {
	@Id
	@SequenceGenerator(name="generator", sequenceName="GEN_PLACE_ID_2")
	@GeneratedValue(generator="generator", strategy=GenerationType.AUTO)
	@Column(name="ID")
	private int id;

	@Column(name="NAME", length=50)
	private String carNumber;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	@Override
	public String toString() {
		return "Place [carNumber=" + carNumber + ", id=" + id + "]";
	}
	
}
