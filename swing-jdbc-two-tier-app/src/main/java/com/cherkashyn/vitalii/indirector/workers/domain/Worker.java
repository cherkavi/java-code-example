package com.cherkashyn.vitalii.indirector.workers.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="worker")
public class Worker {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = true)
    private Integer id;

    @Column(name = "surname", length = 100, nullable = false)
    private String surname;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "fathername", length = 100, nullable = true)
    private String fathername;

    @Column(name = "description", length = 255, nullable = true)
    private String description;

    @Column(name = "age", nullable = true)
    private Integer age;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, targetEntity=Skill.class) 
	@JoinTable(name = "worker2skill", 
			   joinColumns =  @JoinColumn(name = "id_worker", nullable=false), 
			   inverseJoinColumns =  @JoinColumn(name = "id_skill", nullable=false) 
			   )    
    private Set<Skill> skills;

    @ManyToMany(cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, targetEntity=Asset.class)
	@JoinTable(name = "worker2asset", 
			   joinColumns = { 
					@JoinColumn(name = "id_worker", nullable = false) 
				}, 
				inverseJoinColumns = { 
					@JoinColumn(name = "id_asset", nullable = false) 
				})    
    private Set<Asset> assets;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }, targetEntity=District.class)
	@JoinTable(name = "worker2district", 
			   joinColumns = { 
					@JoinColumn(name = "id_worker", nullable = false) 
				}, 
				inverseJoinColumns = { 
					@JoinColumn(name = "id_district", nullable = false) 
				})    
    private Set<District> districts;

    @OneToMany()
    @JoinColumn(name="id_worker")
    private Set<Worker2hour> hours;

    @OneToMany()
    @JoinColumn(name="id_worker")
    private Set<Phone> phones;

    @OneToOne()
    @JoinColumn(name="id_live_district", nullable=true)
	private District	liveDistrict;

    public void setId(Integer aValue) {
        id = aValue;
    }

    public Integer getId() {
        return id;
    }

    public void setSurname(String aValue) {
        surname = aValue;
    }

    public String getSurname() {
        return surname;
    }

    public void setName(String aValue) {
        name = aValue;
    }

    public String getName() {
        return name;
    }

    public void setFathername(String aValue) {
        fathername = aValue;
    }

    public String getFathername() {
        return fathername;
    }

    public void setDescription(String aValue) {
        description = aValue;
    }

    public String getDescription() {
        return description;
    }

	public Set<Skill> getSkills() {
		return skills;
	}

	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
	}

	public Set<Asset> getAssets() {
		return assets;
	}

	public void setAssets(Set<Asset> assets) {
		this.assets = assets;
	}

	public Set<District> getDistricts() {
		return districts;
	}

	public void setDistricts(Set<District> districts) {
		this.districts = districts;
	}

	public Set<Worker2hour> getHours() {
		return hours;
	}

	public void setHours(Set<Worker2hour> hours) {
		this.hours = hours;
	}

	public Set<Phone> getPhones() {
		return phones;
	}

	public void setPhones(Set<Phone> phones) {
		this.phones = phones;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer newAge) {
		this.age=newAge;
	}

	public District getLiveDistrict() {
		return this.liveDistrict;
	}
	
	public void setLiveDistrict(District newDistrict){
		this.liveDistrict=newDistrict;
	}


}
