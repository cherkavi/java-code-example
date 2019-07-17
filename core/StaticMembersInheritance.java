public class StaticMembersInheritance {

    public static void main(String args[]){
        One value = new Two();
        System.out.println(value.field1);

        Two value2 = new Two();
        System.out.println(value2.field2);
    }
    // One.field1
    // One.field2

}

class One{
    static String field1 = "One.field1";
    static String field2 = "One.field2";
}

class Two extends One{
    static String field1 = "Two.field1";
}
