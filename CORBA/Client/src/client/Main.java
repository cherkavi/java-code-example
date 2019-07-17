/*
 * Main.java
 *
 * 
 */

package client;

import org.omg.CORBA.*;
import org.omg.CosNaming.*;

/**
 * Клиент технологии CORBA
 */
public class Main {
    /**
     * получить все сервисы, которые доступны для программы
     */
    public static void getServices(String[] args){
        try{
            /**   инициализация брокера*/
            ORB orb=ORB.init(args,null);
            /**  список доступных сервисов*/
            String[] services=orb.list_initial_services();
            /**  выводим на печать доступные сервисы*/
            for(int i=0;i<services.length;i++){
                System.out.println(i+" : "+services[i]);
            }
        }catch(Exception e){
            System.out.println("Error in trying get all Services");
        }
    }
    /**
     * точка входа в программу
     */
    public static void main(String[] args) {
        try{
            getServices(args);
            /**  Инициализация брокера ORB*/
            ORB orb=ORB.init(args,null);
            /**  получение ссылки на объект данного сервиса, 
             *   то есть на объект, который выполняет один из сервисов */
            System.out.println("get reference on object of service");
            org.omg.CORBA.Object namingContextObj=orb.resolve_initial_references("NameService");
            /** преобразование ссылки к типу NamingContext для предоставления возможности вызова методов интерфейса контекста наименования 
             * возвращает объект, который связан с текущим именем
             */
            System.out.println("returns object, which is connected with current name");
            NamingContext namingContext=NamingContextHelper.narrow(namingContextObj);
            /**  получение класса справки целевого интерфейса */
               // компонент-имя состоит из   идентификатора(id),вида(kind)                     id,kind
            System.out.println("reception of the class of the reference of the target interface");
            NameComponent[] path={//new NameComponent("corejava","Context"),
                                  new NameComponent("Env","Object")};
            /** получение объекта, связанного с указанным именем */
            System.out.println("reception of the object, connected with specified by name");
            org.omg.CORBA.Object envObj=namingContext.resolve(path);
            Env env=EnvHelper.narrow(envObj);
            System.out.println("The Reception of the result removed to functions:"+env.getenv("value of remote PATH"));
        }catch(Exception e){
            System.out.println("CORBA Client Exception: "+e.getMessage());
        }
    }
    
}
