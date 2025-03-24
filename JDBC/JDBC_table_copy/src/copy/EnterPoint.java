package copy;

import java.sql.Connection;

import database.Table;
import database.connector;

public class EnterPoint {
	public static void main(String[] args){
		if(args.length<5){
			System.out.println(" [path to database] [login] [password] [table_source] [table_destination]");
			System.out.println(" V:\\Computer_shop\\Program\\Delphi7\\Server\\DataBase\\server_data.gdb SYSDBA masterkey commodity(kod,show_paper_warranty,show_paper_commodity) commodity_paper(id_commodity,SHOW_PAPER_WARRANTY,SHOW_PAPER_COMMODITY)");
			System.out.println(" [table_source] and [table_destination] must not consists space symbol ");
			System.exit(1);
		}
		// подключиться к базе данных
		Connection connection=connector.get_connection_to_firebird("", args[0], 3050, args[1], args[2]);
		Worker worker=new Worker(connection);
		/*Table source=new Table("commodity",new Field("KOD",java.sql.Types.INTEGER), 
										   new Field("SHOW_PAPER_WARRANTY"), 
										   new Field("SHOW_PAPER_COMMODITY"));
		Table destination=new Table("commodity_paper",new Field("ID_COMMODITY"), 
				   								new Field("SHOW_PAPER_WARRANTY"), 
				   								new Field("SHOW_PAPER_COMMODITY"));
		*/
		Table source=Worker.getTableFromString(args[3]);
		Table destination=Worker.getTableFromString(args[4]);
		worker.fullCopy(source, destination, true);
		try{
			connection.close();
		}catch(Exception ex){
		}
	}
	
	
}
