package com.test.database.db_beans;

import javax.persistence.*;

@Entity
@Table(name="CAR_NUMBER")
public class CarNumber {
	@Id
	@SequenceGenerator(name="generator", sequenceName="GEN_CAR_NUMBER_ID")
	@GeneratedValue(generator="generator", strategy=GenerationType.AUTO)
	@Column(name="ID")
	private int id;

	@Column(name="CAR_NUMBER")
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
	
	
}
