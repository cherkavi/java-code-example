package variant_2.wrap;

import java.io.Serializable;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "users")
public class Users implements Serializable {
    @Transient
    private final static long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Integer id;
    @Column(name="user_name")
    private String userName;
    @Column(name="user_password")
    private String userPassword;
    @Column(name="user_nick")
    private String userNick;

    public Users(){
    }

    public Users(Integer id, String userName, String userPassword, String userNick) {
        this.id = id;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userNick = userNick;
    }

    public Users(String userName, String userPassword, String userNick) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userNick = userNick;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public String toString(){
        StringBuilder returnValue=new StringBuilder();
        returnValue.append("-- Users --\n")
                .append("Id:")
                .append(this.getId())
                .append("\n")
                .append("Nick:").append(this.userNick).append("\n")
                .append("Name:").append(this.userName).append("\n")
                .append("Password:").append(this.userPassword).append("\n")
        .append("===========\n");
        return returnValue.toString();
    }
}
