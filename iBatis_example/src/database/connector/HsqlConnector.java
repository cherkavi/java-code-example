package database.connector;

public class HsqlConnector extends Connector{

	@Override
	protected String getFullClassName() {
		return "org.hsqldb.jdbcDriver";
	}

	@Override
	protected String getLogin() {
		return "sa";
	}

	@Override
	protected String getPassword() {
		return "";
	}

	@Override
	protected String getUrl() {
		return "jdbc:hsqldb:demo";
	}

}
