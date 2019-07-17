
class class_parent {
    int field_a=10;
    int field_b=20;
    
    {System.out.println("class_parent: определение внутри экземпляра класса");
    }

    static{
    	System.out.println("class_parent: статический инициализатор в классе");
    }
    
    public static void method_a(){
        System.out.println("class_parent: статический метод класса");
    }
    
    public class_parent(){
        System.out.println("class_parent: конструктор");
    }
}

class class_child extends class_parent{
    int field_a=10;
    int field_b=20;
    
    {System.out.println("class_child: определение внутри экземпляра класса");
    }

    static{
    	System.out.println("class_child: статический инициализатор в классе");
    }
    
    public static void method_a(){
        System.out.println("class_child: статический метод класса");
    }
    
    public class_child(){
        super();
	System.out.println("class_child: конструктор");
    }
    
}

public class parent{
    public static void main(String[] args){
        System.out.println("begin");
	new class_child();
	System.out.println("end");
    }
}