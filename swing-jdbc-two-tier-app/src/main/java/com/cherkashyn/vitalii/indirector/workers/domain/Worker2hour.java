package com.cherkashyn.vitalii.indirector.workers.domain;

import javax.persistence.*;

@Entity
@Table(name="worker2hour")
public class Worker2hour {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = true)
    private Integer id;

    @Column(name = "day", nullable = false)
    private int day;

    @Column(name = "hour", nullable = false)
    private int hour;

    public void setId(Integer aValue) {
        id = aValue;
    }

    public Integer getId() {
        return id;
    }

    public void setDay(int aValue) {
        day = aValue;
    }

    public int getDay() {
        return day;
    }

    public void setHour(int aValue) {
        hour = aValue;
    }

    public int getHour() {
        return hour;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + day;
		result = prime * result + hour;
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
		Worker2hour other = (Worker2hour) obj;
		if (day != other.day)
			return false;
		if (hour != other.hour)
			return false;
		return true;
	}
    
    
}

