package connector;

public class FirebirdConnect extends PoolConnect{

	public FirebirdConnect(String userName, String password, Integer poolSize) {
		super(userName, password, poolSize);
	}

	@Override
	protected String geDriverName() {
		return "org.firebirdsql.jdbc.FBDriver";
	}

	@Override
	protected String getDataBasePath() {
		return "jdbc:firebirdsql://localhost:3050/D:/eclipse_workspace/JTableDatabase/src/temp.gdb?sql_dialect=3";
	}

}
