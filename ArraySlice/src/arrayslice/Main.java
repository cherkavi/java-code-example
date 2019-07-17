/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package arrayslice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author cherkashinv
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // check String.equalsIgnoreCase
        //String value="true";
        //System.out.println(value.equalsIgnoreCase("True"));
     
        /*
        byte[] test=new byte[]{10,20,30};
        print_array(test);
        //print_array(appendBytesToBytes(test,test,test));
        check_array(test);
        print_array(test);
         */
        
        /*
        byte[] test=new byte[]{10,20,30,40,50,60,70};
        print_array(Arrays.copyOf(test, 3));
        print_array(test);
        print_array(Arrays.copyOfRange(test, 3, test.length));
        */
        //volatile_class_example();
LinkedList<Integer> list=new LinkedList<Integer>();
for(int counter=0;counter<10;counter++){
	list.add(new Integer(counter));
}
list=new LinkedList(list.subList(4+1, 10));
for(int counter=0;counter<list.size();counter++){
	System.out.println(list.get(counter));
}        
    }
    
    private static void volatile_class_example(){
        class Temp{
                private String field_name;
                public Temp(){
                        this.field_name="";
                }
                public Temp(String value){
                        this.field_name=value;
                }
                public String getValue(){
                        return this.field_name;
                }
        }
        Temp temp_value=new Temp(">>> value ");
        System.out.println(temp_value.getValue());        
    }
    
    private static void print_array(byte[] array){
        System.out.println(array+":");
        for(int counter=0;counter<array.length;counter++){
            System.out.print(array[counter]+"; ");
        }
        System.out.println();
    }
    
    public static void check_array(byte[] array){
        System.out.println(">>>>before:");
        print_array(array);
        array=appendBytesToBytes(array,array);
        System.out.println(">>>>after:");        
        print_array(array);
    }
    
	/** получить массив, который является результатом сложения всех массивов */
	public static byte[] appendBytesToBytes(byte[] ...elements){
		int position=0;
		int array_length=0;
		// create return array
		for(int index=0;index<elements.length;index++){
			if(elements[index]!=null){
				array_length+=elements[index].length;
			}else{
				// current element is null
			}
		}
		byte[] return_value=new byte[array_length];
		// copy
		for(int index=0;index<elements.length;index++){
			if(elements[index]!=null){
				for(int counter=0;counter<elements[index].length;counter++){
					return_value[position]=elements[index][counter];
					position++;
				}
			}else{
				// current element is null
			}
		}
		return return_value; 
	}

}
