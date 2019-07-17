class sample_Float{
	double d=10.25;
	Float f_object;
	float f;
	sample_Float(){
		try{
			f_object=new Float("1.5");
		}
		// error in convert String to Float
		catch(NumberFormatException e){
			System.out.println("Exception:"+e.getMessage());
		}
		System.out.println("Convert String to Float:"+f_object);
		if("Float".equalsIgnoreCase(f_object.TYPE.toString())){
			System.out.println(" data is float");
		}
		System.out.println("Float Type:"+f_object.TYPE);
	}
}
