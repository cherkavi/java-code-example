package com.test.database.db_beans;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="CAR_BY_NAME")
public class CarByName {
	@Id
	@SequenceGenerator(name="generator", sequenceName="GEN_CAR_BY_NAME_ID")
	@GeneratedValue(generator="generator", strategy=GenerationType.AUTO)
	@Column(name="ID")
	private int id;
	@Column(name="ID_PLACE")
	private Integer idPlace;
	@Column(name="ID_CAR_NUMBER")
	private Integer IdCarNumber;
	@Column(name="DETECT_DATE")
	private Date DetectDate;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getIdPlace() {
		return idPlace;
	}
	public void setIdPlace(Integer idPlace) {
		this.idPlace = idPlace;
	}
	public Integer getIdCarNumber() {
		return IdCarNumber;
	}
	public void setIdCarNumber(Integer idCarNumber) {
		IdCarNumber = idCarNumber;
	}
	public Date getDetectDate() {
		return DetectDate;
	}
	public void setDetectDate(Date detectDate) {
		DetectDate = detectDate;
	}
	
	
}
