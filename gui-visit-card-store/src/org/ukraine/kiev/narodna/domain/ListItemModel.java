package org.ukraine.kiev.narodna.domain;

import java.io.Serializable;

public class ListItemModel implements Serializable {
	private static final long	serialVersionUID	= 1L;
	
	private int id;
	private String name;
	private String surname;
	private String fathername;
	private String phone;
	
	
	public ListItemModel(int id, String name, String surname, String fathername, String phone) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.fathername = fathername;
		this.phone=phone;
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
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getFathername() {
		return fathername;
	}
	public void setFathername(String fathername) {
		this.fathername = fathername;
	}

	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fathername == null) ? 0 : fathername.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ListItemModel other = (ListItemModel) obj;
		if (fathername == null) {
			if (other.fathername != null)
				return false;
		} else if (!fathername.equals(other.fathername))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return name + " " + surname+ " " + fathername ;
	}

	
	
}
