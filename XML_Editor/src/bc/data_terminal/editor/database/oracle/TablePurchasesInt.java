package bc.data_terminal.editor.database.oracle;

import javax.persistence.*;


@Entity
@Table(name="F_DT_PURCHASES_INT")
public class TablePurchasesInt {
	private Integer start_value=new Integer(0);
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_TERM", unique=true, nullable=false)
	public Integer field_id_term;
	
	@Column(name="ID_FILE")
	public Integer field_id_file;

	public TablePurchasesInt(){
		clear();
	}
	
	public void clear(){
		this.field_id_term=start_value;
		this.field_id_file=start_value;
	}
	
	public Integer getField_id_term() {
		return field_id_term;
	}

	public void setField_id_term(Integer field_id_term) {
		this.field_id_term = field_id_term;
	}

	public Integer getField_id_file() {
		return field_id_file;
	}

	public void setField_id_file(Integer field_id_file) {
		this.field_id_file = field_id_file;
	}

}
