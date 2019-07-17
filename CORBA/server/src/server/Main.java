/*
 * Main.java
 *
 * Created on 14 лютого 2008, 19:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package server;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;

/**
 * класс, который реализует интерфейс для удаленного вызова
 * создание кода для объекта сервера
 */
class EnvPOAImpl extends EnvPOA{
    /** реализация интерфейса CORBA - расширение класса EnvPOA(org.omg.PortableServer.Servant.)
     * @param name получение параметра от удаленного объекта
     * @return возвращение параметра типа строка удаленному объекту
     */
    public String getenv(String name) {
        System.out.println("server get argument:"+name);
        return "remote method say: "+name;
    }
}

public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            // старт и инициализация ORB брокера
            System.out.println("Creating and initializing the ORB...");
            org.omg.CORBA.ORB orb=org.omg.CORBA.ORB.init(args,null);
            // 
            System.out.println("Registering server implementation with the ORB...");
            POA rootpoa=(POA)orb.resolve_initial_references("RootPOA");
            rootpoa.the_POAManager().activate();
            // создание объекта-сервера
            EnvPOAImpl impl=new EnvPOAImpl();
            // преобразование объекта-сервера в CORBA объект
            org.omg.CORBA.Object ref=rootpoa.servant_to_reference(impl);
            // выводим IOR(уникальный идентификатор объекта) ссылку с помощью метода object_to_string
            System.out.println(orb.object_to_string(ref));
            // получение ссылки на сервис именования
            org.omg.CORBA.Object namingContextObj=orb.resolve_initial_references("NameService");
            NamingContext namingContext=NamingContextHelper.narrow(namingContextObj);
            // создается нужное имя объекта 
            NameComponent[] path={new NameComponent("Env","Object")};
            // связывание объекта с его именем
            System.out.println("Binding server implementation to name service...");
            namingContext.rebind(path,ref);
            // переходим в состояние ожидания со стороны клиентов
            System.out.println("Waiting for invocations from clients...");
            orb.run();
        }catch(Exception e){
            System.out.println("error in create or work CORBA Server "+e.getMessage());
        }
    }
    
}
