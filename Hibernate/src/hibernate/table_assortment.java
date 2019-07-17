/*
 * table_assortment.java
 *
 * Created on 11 червня 2008, 15:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package hibernate;

import java.io.Serializable;
import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
//import org.hibernate.id.*;
import javax.persistence.*;
/**
 * Contact bean.
 * 
 * @author Siarhei Berdachuk
 */
@Entity
@Table(name = "ASSORTMENT")
public class table_assortment implements Serializable{

  private Long kod;

  private long kod_class;

  private String bar_code;


  @Id
  //@TransactionAttribute(TransactionAttributeType.REQUIRED)// - not work
  
  //@SequenceGenerator(name="GEN_ASSORTMENT_KOD",sequenceName="GEN_ASSORTMENT_KOD")
  //@GeneratedValue(generator="GEN_ASSORTMENT_KOD",strategy=GenerationType.AUTO)
  @Column(name = "KOD",unique=true,nullable=false,insertable=true,updatable=true,scale=0)
  public Long getKod() {
    return kod;
  }

  public void setKod(Long kod) {
    this.kod = kod;
  }

  @Column(name = "KOD_CLASS")
  public Long getKod_class() {
    return kod_class;
  }

  public void setKod_class(Long kod_class) {
    this.kod_class = kod_class;
  }

  @Column(name = "BAR_CODE")
  public String getBar_code() {
    return bar_code;
  }

  public void setBar_code(String bar_code) {
    this.bar_code = bar_code;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)//
        .append("KOD:", getKod())//
        .append("KOD_CLASS", getKod_class())//
        .append("BAR_CODE", getBar_code())//
        .toString();
  }

}