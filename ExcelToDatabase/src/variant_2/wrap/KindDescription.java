package variant_2.wrap;

import java.io.Serializable;

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
@Table(name = "kind_description")
/**
 * <table border="1">
 * 	<tr>
 * 		<th>DataBase</th>
 * 		<th>POJO</th>
 * 	</tr>
 * 	<tr>
 * 		<td>id</td>
 * 		<td>id</td>
 * 	</tr>
 * 	<tr>
 * 		<td>id_kind</td>
 * 		<td>idKind</td>
 * 	</tr>
 * 	<tr>
 * 		<td>description</td>
 * 		<td>description</td>
 * 	</tr>
 * </table>
 */
public class KindDescription implements Serializable {

    @Transient
    private final static long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;

    @Column(name="id_kind")
    private Integer idKind;

	@OneToOne(targetEntity=Kind.class,fetch=FetchType.EAGER)
	@JoinColumn(name="id_kind",nullable=true,columnDefinition="id",insertable=false, updatable=false)
	private Kind kind;
    
    @Column(name = "description")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdKind() {
        return idKind;
    }

    public void setIdKind(Integer idKind) {
        this.idKind = idKind;
    }

	public Kind getKind() {
		return kind;
	}

	public void setKind(Kind kind) {
		this.kind = kind;
	}
    
}
