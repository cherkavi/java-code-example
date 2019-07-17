package variant_2.wrap;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "user_history")
public class UserHistory implements Serializable {

    @Transient
    private final static long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue
    private int id;

    @Column(name="id_user")
    private int idUser;
    @Column(name="date_write")
    private Date dateWrite;

    public Date getDateWrite() {
        return dateWrite;
    }

    public void setDateWrite(Date dateWrite) {
        this.dateWrite = dateWrite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

}
