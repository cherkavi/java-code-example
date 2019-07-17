package ua.cetelem;

import ua.cetelem.helpers.sequence_generator.IUniqueId;

public class TestUserGeneratorObject implements java.io.Serializable, IUniqueId {
    private static final long serialVersionUID = 1376489315L;

    private String tin;
    private long id;
    private String point_id;
    private String user_id;
    private java.util.Date date_write;
    private long result_in_black_list;
    private String fio;
    
    public TestUserGeneratorObject() {}
    public String getTin() {
        return tin;
    }
    public void setTin(String tin) {
        this.tin = tin;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getPoint_id() {
        return point_id;
    }
    public void setPoint_id(String point_id) {
        this.point_id = point_id;
    }
    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public java.util.Date getDate_write() {
        return date_write;
    }
    public void setDate_write(java.util.Date date_write) {
        this.date_write = date_write;
    }
    public long getResult_in_black_list() {
        return result_in_black_list;
    }
    public void setResult_in_black_list(long result_in_black_list) {
        this.result_in_black_list = result_in_black_list;
    }
    public String getFio() {
        return fio;
    }
    public void setFio(String fio) {
        this.fio = fio;
    }
}
