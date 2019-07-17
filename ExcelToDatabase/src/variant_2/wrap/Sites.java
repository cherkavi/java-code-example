package variant_2.wrap;

import java.io.Serializable;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sites")
public class Sites implements Serializable {

    @Transient
    private final static long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;

    @Column(name = "address")
    private String address;

    public Sites(){
    }
    
    public Sites(String address) {
		this.address = address;
	}

	public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
