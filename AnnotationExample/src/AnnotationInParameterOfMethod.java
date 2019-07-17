
/** 
 * Description for parameter of method 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Inherited
public @interface AMBeanOperationParameter {
	/** description of this element  */
	String description();
}

-------------------------------------------------------------------------------------------------------------

private static MBeanParameterInfo[] getMBeanParameters(Method method) {
		Annotation[][] annotations=method.getParameterAnnotations();
		Class<?>[] classes=method.getParameterTypes();
		MBeanParameterInfo[] returnValue=new MBeanParameterInfo[classes.length];
		try{
			for(int counter=0;counter<classes.length;counter++){
				returnValue[counter]=new MBeanParameterInfo("P"+counter, 
															classes[counter].getName(), 
															getDescriptionFromAnnotation(annotations, counter)
															);
			}
		}catch(Exception ex){
			Logger.getLogger(OperationProxy.class).error("getMBeanParameters Exception:"+ex.getMessage());
		}
		return returnValue;
	}
