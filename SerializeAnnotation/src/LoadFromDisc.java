import java.lang.annotation.Annotation;


public class LoadFromDisc {
	public static void main(String[] args){
		Object object=Utility.readObject_from_file("c:\\object.file");
		Value value=(Value)object;
		Class classValue=value.getClass();
		Annotation[] annotations=classValue.getAnnotations();
		for(int counter=0;counter<annotations.length;counter++){
			System.out.println("Annotation:"+annotations[counter].annotationType().toString());
			if(annotations[counter] instanceof SimpleAnnotation){
				System.out.println("SimpleAnnotation.Description:"+((SimpleAnnotation)annotations[counter]).description());
			}
		}
		System.out.println(object);
		
	}
}
