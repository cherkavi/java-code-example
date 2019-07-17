package database.connector;

public class FirebirdConnector extends Connector{

	@Override
	protected String getFullClassName() {
		return "org.firebirdsql.jdbc.FBDriver";
	}

	@Override
	protected String getLogin() {
		return "SYSDBA";
	}

	@Override
	protected String getPassword() {
		return "masterkey";
	}

	@Override
	protected String getUrl() {
		return "jdbc:firebirdsql://localhost:3050/c:/temp_data.GDB?sql_dialect=3";
	}

}
