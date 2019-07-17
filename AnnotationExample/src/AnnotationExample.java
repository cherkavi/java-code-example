import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/** Example of annotation's */
@SimpleAnnotation(description="method getDescription",parent=StringBuffer.class)
public class AnnotationExample {
	public static void main(String[] args){
		AnnotationExample example=new AnnotationExample();
		SimpleAnnotation annotationClass=example.getClass().getAnnotation(SimpleAnnotation.class);
		if(annotationClass!=null){
			System.out.println("Annotation class is not null: ");
			if(annotationClass instanceof SimpleAnnotation ){
				System.out.println("Value:"+annotationClass.value());
				System.out.println("Description:"+annotationClass.description());
				System.out.println("ClassName:"+annotationClass.parent());
			}
		}else{
			System.out.println("Annotation class is null");
		}
	}
	
	
	/** this is Annotation Example */
	 
	public AnnotationExample(){
		
	}
	
	public int getValue(){
		return 5;
	}
	
	public String getDescription(){
		return "";
	}
}


enum Types{
	TYPE_INTEGER, TYPE_STRING, TYPE_FLOAT;
}


@Retention(RetentionPolicy.RUNTIME) // CLASS - default value, into *.class; SOURCE - smallest prior, only in *.java code
// @Target({ElementType.FIELD , ElementType.TYPE})
// @Inherited
// public 
@interface SimpleAnnotation{
	/** описание данного элемента */
	String description();
	/** primitive values for this element */
	int value() default 1;
	/** class for description of element*/
	Class<? extends Object> parent() default String.class;
	/** possible type of this element */
	Types type() default Types.TYPE_STRING;
}


