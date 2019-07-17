/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package object_and_string;

/**
 *
 * @author First
 */
/*
public class Main {

    public static void main(String[] args) {
        // Object
        Object first_object=new Object();
        System.out.println("new object:"+first_object.hashCode());
        // String
            // Declaration
        String first_string="first_string";
        String second_string="second_string";
        String third_string="first_string";
        System.out.println(" first_string:"+first_string.hashCode());
        System.out.println(" second_string:"+second_string.hashCode());
        System.out.println(" third_string:"+third_string.hashCode() );
            // Compare
        if(first_string==third_string){
            System.out.println("object \" first_string and object \" third_string equalent ");
        }
        if(first_string.equals(third_string)){
            System.out.println("object \" first_string and object \" third_string equalent ");
        }
        
         // Попытатся определить кол-во пробелов в строке, используя операцию сравнения 
        String find_string="hello from netbean enviroment";
        int amount=0;
        String empty_string=" ";
        for(int counter=0;counter<find_string.length();counter++){
            //System.out.print("current char:<"+find_string.substring(counter,counter+1)+">");
            //System.out.println(find_string.substring(counter,counter+1).hashCode()+" : "+empty_string.hashCode());
            //if(find_string.substring(counter, counter+1)==empty_string){            - not working
            if(find_string.substring(counter, counter+1).hashCode()==empty_string.hashCode()){
                amount++;
            }
        }
        System.out.println("Amount:"+amount);
        System.out.println(String.valueOf('\n')+String.valueOf('\r'));
        Point p1=new Point(3,4);
        Point p2=p1;
        p1.field_x=5;
        p1=new Point(4,4);
        System.out.println( p2.field_x-p1.field_x);
        int[] mas=new int[]{1,4,5};
        if(mas instanceof Object){
            System.out.println("is object");
        }
        System.out.println(mas.getClass().getName().toString());
    }

}

class Point{
    public int field_x, field_y;
    public Point(int x, int y){
        this.field_x=x;
        this.field_y=y;
    }
}

*/

// Пример notify
/*
public abstract class Main implements Runnable { 
    private Object lock = new Object(); 
 
    public void lock() { 
        synchronized (lock) { 
            try { 
                lock.wait(); 
                System.out.println("1"); 
            } catch (InterruptedException e) { 
            } 
        } 
    } 
 
    public void unlock() { 
        synchronized (lock) { 
            lock.notify(); 
            System.out.println("2"); 
        } 
    } 
 
    public static void main(String s[]) { 
        new Thread(new Main() { 
            public void run() { 
                lock(); 
            } 
        }).start(); 
        new Thread(new Main() { 
            public void run() { 
                unlock(); 
            } 
        }).start(); 
        System.out.println("stop");
    } 
}
 */
/*
 public class Main{
     public static void main(String[] args){
         System.out.println("hello"+'!');
         System.out.println('!'+'!');
         //String x=null;
         //if (x.toString() instanceof String){
         //    System.out.println("Error");
         //}
         byte b=(byte)(100+100);
         
		char c=65;
		System.out.println(c);
		System.out.println(+c);
		System.out.println("="+c);
         
     }
 }
 */
// пример полиморфизма методов, но не полиморфизма полей
/*
class Parent {
    int x = 2;
    public void print() {
        System.out.println(x);
    }
}
class Main extends Parent {
    int x = 3;
    public static void main(String s[]) {
        new Main().print();
    }
} 
 */

// пример Finally метода
public class Main{
    public static boolean check_finally(String value){
        System.out.println("check_finally in action");
        try{
            Float.parseFloat(value);
            return true;
        }catch(Exception ex){
            
        }finally{
            value+=1;
        }
        return false;
    }
    public static void main(String[] args){
        System.out.println("check_finally:"+check_finally("0,124"));
    }
}