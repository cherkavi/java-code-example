package com.cherkashyn.vitalii.testtask.immobilienscout.dto;

public class PageDescription {
	private final String label;
	private final String value;
	
	public PageDescription(String title, String description) {
		super();
		this.label = title;
		this.value = description;
	}
	
	public String getLabel() {
		return label;
	}
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "PageDescription [label=" + label + ", value=" + value + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
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
		PageDescription other = (PageDescription) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}
	
}
