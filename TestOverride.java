import java.sql.SQLException;

/** данный пример демонстрирует возможность переопределения методов в потомке
 * <li>
 * с погашением throws - <b>можно погашать в потомке</b>
 * </li>
 * <li> 
 * и расширением throws - <b>нельзя расширять в переопределенных методах</b>
 * </li>
 */
public class TestOverride {
	public static void main(String[] args){
		Parent parent=new Child();
		System.out.println("Value:"+parent.getValue());
		
		try{
			Child child=new Child();
			System.out.println("Value:"+child.getString());
		}catch(Exception ex){
			System.out.println("Exception "+ex.getMessage());
		}
	}
}


class Parent{
	public String getValue() {
		return "parent";
	}
	public String getString() throws Exception {
		return "parent";
	}
}

class Child extends Parent{
	/** нельзя применять расширение throws - предок не поддерживает */
/*	public String getValue() throws Exception {
		return "child";
	}
*/

	/** сужение из Exception на SQLException*/
	public String getString() throws SQLException{
		return "child";
	}
}
