	private static <T> T[] addArrays(T[] one, T[] two){
		if((one!=null)&&(two!=null)){
			one.getClass();
			T[] returnValue=null;
			if(one.length>0){
				returnValue=(T[])Array.newInstance((one[0]).getClass(), one.length+two.length);
			}else{
				if(two.length>0){
					returnValue=(T[])Array.newInstance((two[0]).getClass(), one.length+two.length);
				}else{
					return null; 
				}
			}
			for(int counter=0;counter<one.length;counter++){
				returnValue[counter]=one[counter];
			}
			for(int counter=0;counter<two.length;counter++){
				returnValue[counter+one.length]=two[counter];
			}
			return returnValue;
		}else{
			if((one==null)&&(two==null)){
				return null;
			}else{
				if(one==null){
					return two;
				}else{
					// only two has null
					return one;
				}
			}
		}
	}

	public static void main(String[] args){
		Integer[] one=new Integer[]{1,2,3,4};
		Integer[] two=new Integer[]{5,6,7,8};
		Integer[] three=addArrays(one, two);
		for(int counter=0;counter<three.length;counter++){
			System.out.print(counter+":"+three[counter]+"    ");
		}
		System.out.println();
	}
