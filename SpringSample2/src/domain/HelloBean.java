package domain;

import java.util.Calendar;
import domain.Name;
import service.ByeService;
import service.HelloService;
public class HelloBean {
    public Name name;
    public HelloService helloService;
    public ByeService byeService;
    public void setHelloService(HelloService helloService) {
        this.helloService = helloService;
    }
         //  Геттеры сеттеры 
    public void setByeService(ByeService byeService) {
        this.byeService = byeService;
    }
    public Name getName() {
        return name;
    }
    public void setName(Name name) {
        this.name = name;
    }
    // Главный метод, собственно он и будет генерировать вызов методов, в данном случае в зависимости от текущего времени 
         public String wishMe(Name name) {
        Calendar calendar = Calendar.getInstance();
        if(calendar.get(Calendar.HOUR_OF_DAY) <  12){
            return helloService.sayHello(name);
        } else {
            return byeService.sayBye(name);
        }
    }
}