package variant_2.wrap;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "site_state")
public class SiteState implements Serializable {
    @Transient
    private final static long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;
    @Column(name="id_site")
    private Integer idSite;
    @Column(name="is_last")
    private Integer isLast;
    @Column(name="date_write")
    private Date dateWrite=null;
    @Column(name="id_kind_descriptor")
    private Integer idKindDescriptor;

	@OneToOne(targetEntity=KindDescription.class,fetch=FetchType.EAGER)
	@JoinColumn(name="id_kind_descriptor",nullable=true,columnDefinition="id",insertable=false, updatable=false)
	private KindDescription kindDescription;
    
    public SiteState(){
    }
    
    public SiteState(Integer idSite, Integer isLast, Integer idKindDescriptor) {
		this.idSite = idSite;
		this.isLast = isLast;
		this.idKindDescriptor = idKindDescriptor;
		this.dateWrite=new Date();
	}

	public Date getDateWrite() {
        return dateWrite;
    }

    public void setDateWrite(Date dateWrite) {
        this.dateWrite = dateWrite;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdKindDescriptor() {
        return idKindDescriptor;
    }

    public void setIdKindDescriptor(Integer idKindDescriptor) {
        this.idKindDescriptor = idKindDescriptor;
    }

    public Integer getIdSite() {
        return idSite;
    }

    public void setIdSite(Integer idSite) {
        this.idSite = idSite;
    }

    public Integer getIsLast() {
        return isLast;
    }

    public void setIsLast(Integer isLast) {
        this.isLast = isLast;
    }

	public KindDescription getKindDescription() {
		return kindDescription;
	}

	public void setKindDescription(KindDescription kindDescription) {
		this.kindDescription = kindDescription;
	}
    
}
