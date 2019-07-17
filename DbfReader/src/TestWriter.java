import java.io.FileOutputStream;
import java.io.IOException;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFWriter;


public class TestWriter {

	public static void main(String[] args)throws DBFException, IOException {

	    // let us create field definitions first
	    // we will go for 3 fields
	    //
	    DBFField fields[] = new DBFField[2];

	    fields[0] = new DBFField();
	    fields[0].setName( "emp_code");
	    fields[0].setDataType( DBFField.FIELD_TYPE_C);
	    fields[0].setFieldLength( 50);

	    fields[1] = new DBFField();
	    fields[1].setName( "emp_code");
	    fields[1].setDataType( DBFField.FIELD_TYPE_C);
	    fields[1].setFieldLength( 50);

	    Object[] rowData=new Object[]{"Value1","Value2"};

	    DBFWriter writer = new DBFWriter();
	    writer.setFields( fields);
	    writer.addRecord(rowData);

	    FileOutputStream fos = new FileOutputStream("c:\\craeting.dbf");
	    writer.write( fos);
	    fos.close();
	    System.out.println("OK");
	}
}
