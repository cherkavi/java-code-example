import java.io.Serializable;
import java.lang.annotation.Retention;

@SimpleAnnotation(description = "this is Value class, annotation text")
public class Value implements Serializable{
	private static final long serialVersionUID=1L;
	
	private String description="Class Value";
	public Value(){
	}
	
	public Value(String value){
		this.description=value;
	}
	
	public String toString(){
		return this.description;
	}
}


@Retention(java.lang.annotation.RetentionPolicy.RUNTIME) // CLASS - default value, into *.class; SOURCE - smallest prior, only in *.java code
@interface SimpleAnnotation{
	/** описание данного элемента */
	String description();
	/** primitive values for this element */
	int intValue() default 1;
}


