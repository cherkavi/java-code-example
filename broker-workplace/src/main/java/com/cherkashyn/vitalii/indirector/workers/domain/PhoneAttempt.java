package com.cherkashyn.vitalii.indirector.workers.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="phone_attempt")
public class PhoneAttempt {
	
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = true)
    private Integer id;

    
    @OneToOne()
    @JoinColumn(name="id_phone_dirty")
    private PhoneDirty phoneDirty;
    
    @OneToOne()
    @JoinColumn(name="id_result")
    private PhoneAttemptResult result;
    
    @Column(name="description")
    private String description;

    @Column(name="createdat")
    private Date createdat=new Date();


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public PhoneDirty getPhoneDirty() {
		return phoneDirty;
	}


	public void setPhoneDirty(PhoneDirty phoneDirty) {
		this.phoneDirty = phoneDirty;
	}


	public PhoneAttemptResult getResult() {
		return result;
	}


	public void setResult(PhoneAttemptResult result) {
		this.result = result;
	}


	public Date getCreatedat() {
		return createdat;
	}


	public void setCreatedat(Date createdat) {
		this.createdat = createdat;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	private final static SimpleDateFormat SDF=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	@Override
	public String toString() {
		if(this.description==null || this.description.trim().length()==0){
			return SDF.format(this.createdat)+"   "+this.result.getName()+" ( "+this.description+" )";
		}else{
			return SDF.format(this.createdat)+"   "+this.result.getName()+" ( "+this.description+" )";
		}
	}
}
