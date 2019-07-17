import domain.HelloBean;
import domain.Name;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EnterPoint {
	public static void main(String[] args) {
		try {
			System.out.println("TestClient started");
			// Загружаем конфигурационный файл через ApplicationContext
			ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
					new String[] { "Description.xml" });
			System.out.println("Classpath loaded");
			// Доступ ко спрингу через getBean() метод
			HelloBean helloBean = (HelloBean) appContext.getBean("helloBean");
			// Тут все понятно и не сложно догадаться, что происходит
			Name name = new Name();
			name.setFirstName("Tony");
			name.setLastName("Greg");
			String str = helloBean.wishMe(name);
			System.out.println(str);
			System.out.println("TestClient end");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}