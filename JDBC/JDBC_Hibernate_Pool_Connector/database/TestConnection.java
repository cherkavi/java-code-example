package database;

public class TestConnection {
	public static void main(String[] args) throws Exception{
		System.out.println(" - begin - ");
		Connector.init(EConnectionType.mysql, "127.0.0.1", 0, "shop_list_reestr","technik", "technik", 20, null);
		System.out.println(Connector.getInstance().getConnection());
		System.out.println("  - end - ");
	}
}
