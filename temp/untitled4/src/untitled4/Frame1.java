package untitled4;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// объявляем подкласс Exception
class my_exception extends Exception
{
  my_exception(String s)
  {
    super(s);
  }
}

// объявляем класс, который содержит только один абстрактный метод, который должен быть Override в подклассах
// при чем объявляем класс так же Abstract, так как у него не может быть объектов - могут быть только подкласса, а уже у них объекты
//class begin_hello_world extends Object
abstract class begin_hello_world extends Object// по умолчанию данная запись равна записи "class hello_world extends Object"
{
  abstract String get_message();
}
// интерфейс, который будет добавлен в hello_world
interface begin_hello_world_add
{
  Integer variable_one=new Integer(10); // константа
  String information=new String("Information"); // константа
  public String get_message(); // метод, который должен быть реализован в классах, где будет применен данный интерфейсы
}

class hello_world extends begin_hello_world implements begin_hello_world_add
{
  public static int counter=0;// переменная класса, а не объекта
  String message="hello world";
  // объявление массива
  int[] array_int=new int[10];
  int[] temp_array_int={1,2,3,4,5};
  int temp_array_int2[]={1,2,3,4,5};
  Integer[] array_Integer=new Integer[10];
  // constructor
  hello_world()
  {
    counter++; // счетчик обращений к данному классу (кол-во созданных объектов)
    try
    {
      this.message="Hello world constructor set";
      for(int index=0;index<this.array_int.length;index++)
      {
        this.array_int[index]=index;
        this.array_Integer[index]=new Integer(index);
      }
    }
    catch (ArrayIndexOutOfBoundsException e)// поймать Exception Index of Bounds
    {
      System.out.println("Array Index of bounds: "+e);
    }
    catch (Exception e)// поймать все остальные Exception
    {
      System.out.println("Detected Exception in constructor:"+e);
    }

  }
  // constructor overloading - whith arguments
  hello_world(String s)
  {
    this.message=s;
  }
  //метод, который выводит на консоль данные обо всех массивах
  void put_arrays()
  {
    cykle_1:for(int index=0;index<this.array_int.length;index++)
    {
     if(index>6){break cykle_1;};
      //if((index % 3)==0)System.out.println(this.array_int[index]);
      if((index%3)!=0){
        continue cykle_1;// задействовать оператор "continue" для оператора цикла на метке "cykle_1"
      }
      else{
        System.out.println(this.array_int[index]);
      };
    }
  }
  // метод, устанавливающий поле данного класса
  void set_message(String s)
  {
    this.message=s;
  }
  // метод, возвращающий поле данного класса
  public String get_message()
  {
    return this.message;
  }
  // метод, который выводит на консоль поле данного класса
  void print_message()
  {
    System.out.println(this.message);
  }
  // при System.out.println - все аргументы данного метода обращаются к методу toString() каждого объекта
  // override Object.toString()
  public String toString()
  {
    return "Override method toString: "+this.message+" : "+this.information;
  }
}
/*end class hello_world extends Object*/
// создать интерфейс
interface put_method // extended <add_interface1>,<add_interface2>,<add_interface3>
{
  void print_variable(); // данный интерфейс содержит информацию о необходимом методе в классах, которые будут его содержать
  // можно сказать что метод интерфейса это абстрактный метод для класса, где он должен быть Overrided
}
// создаем класс, один из методов которого возвращает ссылку на объект класса hello_world
class hello_world_dop
{
  // объявляем public метод, который возвращает тип объекта hello_world_add и имя этого метода get_hello_world, как аргумент - тип String
  public hello_world_add get_hello_world(String s)
  {
    return  new hello_world_add(s);
  }
}

// объявляем класс hello_world_add, который является подклассам hello_world и включает в себя интерфейс put_method
class hello_world_add extends hello_world implements put_method
{
  public void print_variable()
  {
    try
    {
      System.out.println("Hello_world_add implements put_method: "+super.message);
      throw new Exception("hello");// create Exception
    }
    catch(Exception e)// catch Exception
    {
      System.out.println("catch exception, Text of Exception:"+e); // print Exception
    }
    finally// finally block
    {
      System.out.println(" end of Exception");
    }
  }
  hello_world_add(String s)
  {
    super(s);// обращаемся к конструктору суперкласса, который в качестве аргументов принимает данные
  }
}
public class Frame1 extends JFrame {
  JPanel contentPane;
  BorderLayout borderLayout1 = new BorderLayout();

  /**Construct the frame*/
  public Frame1() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  /**/

  /**Component initialization*/
  private void jbInit() throws Exception  {
    //setIconImage(Toolkit.getDefaultToolkit().createImage(Frame1.class.getResource("[Your Icon]")));
    contentPane = (JPanel) this.getContentPane();
    contentPane.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        contentPane_mouseClicked(e);
      }
    });
    contentPane.setLayout(borderLayout1);
    this.setSize(new Dimension(400, 300));
    this.setTitle("Frame Title");
  }
  /**/

  /**Overridden so we can exit when window is closed*/
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      System.exit(0);
    }
  }
  /**/

  /* Event mouse click on the Panel*/
  void contentPane_mouseClicked(MouseEvent e){
  Object[] array_of_object=new Object[10];
  String[] array_of_string={"one","two","three"}; // объявлен массив, и сразу же инициализированы 3 переменные
  String[] array_of_string2=new String[3]; // объявлен массив, и зарезервирована память под 3 элемента String
  String[] array_of_string3;// объявлен массив, но не зарезервирована память под размер массива

  hello_world temp  =new hello_world();
  hello_world temp2 =new hello_world();
  hello_world_dop temp4=new hello_world_dop();

  hello_world_add temp3;
  temp3=temp4.get_hello_world("hello_world_add_message_string");
  //hello_world_add temp3=new hello_world_add("hello_world_add_message_string");

  // array_of_object[0]==null
  array_of_object[1]=temp;//  hello_world      to object
  array_of_object[2]=temp2;// hello_world      to object
  array_of_object[3]=temp3;// hello_world_add  to object
  array_of_object[4]=temp4;// hello_world_dop  to object


  temp.set_message("This is my string");
  temp.print_message();
  System.out.println("call method toString: "+temp2);// call method "toString"
  System.out.println("counter of class hello_world: "+hello_world.counter); // output count of object of "hello_world" - STATIC hello_world.counter
  temp.put_arrays();
  //temp3.print_variable();
  //<class "sub-class">  <class "super">  <method of "sub-class">
  ( (hello_world_add) (array_of_object[3]) ).print_variable(); // hello_world from object
  // вывод текущего времени
  java.util.Date now=new java.util.Date();
  System.out.println("Time:"+now);

  }
}