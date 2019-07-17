package interface_fields;
/** интерфейс для теста */
public interface ITest {
	/** 	public static final by default */
	String determinant=new String(" determinant ");
	/** public static by default <br />
	 * ITest$DynamicTest.class
	 * */
	class DynamicTest{
		/** ITest$DynamicTest$DynamicTestInner.class */
		class DynamicTestInner{
			public int getTestInner(){
				return 0;
			}
		}
		
		int intValue;
		private String dynamicString;
		
		public int getIntValue(){
			/** ITest$DynamicTest$1.class*/
			ITest test=new ITest(){
				@Override
				public Object getObject() {
					/** ITest$DynamicTest$1$1.class */
					ITest testInner=new ITest(){
						@Override
						public Object getObject() {
							return "implementation into inner class";
						}
					};
					return testInner.getObject();
				}
			};
			System.out.println("DynamicTest#getIntValue:"+test.getObject());
			return intValue;
		}
		public String getStringValue(){
			return this.dynamicString;
		}
	}
	/** public static as default  */
	static class StaticTest{
		String staticValue;
		public String getStringValue(){
			return this.staticValue;
		}
	}
	
	static class StaticTest2{
		String staticValue2;
		
		public String getStringValue(){
			return this.staticValue2;
		}
	}
	Object getObject();
	
}
