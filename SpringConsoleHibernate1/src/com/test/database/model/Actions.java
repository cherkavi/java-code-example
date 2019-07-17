package com.test.database.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="actions")
public class Actions {
	@Id
	@Column(name="id")
	private int id;
	@Column(name="date_write")
	private Date dateWrite;
	@Column(name="id_action_state")
	private Integer idActionState;
	@Column(name="device_id")
	private String deviceId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDateWrite() {
		return dateWrite;
	}
	public void setDateWrite(Date dateWrite) {
		this.dateWrite = dateWrite;
	}
	public Integer getIdActionState() {
		return idActionState;
	}
	public void setIdActionState(Integer idActionState) {
		this.idActionState = idActionState;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	@Override
	public String toString() {
		return "Actions [dateWrite=" + dateWrite + ", deviceId=" + deviceId
				+ ", id=" + id + ", idActionState=" + idActionState + "]";
	}
	
	
	
}
