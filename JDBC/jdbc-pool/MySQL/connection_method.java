	/**
	 * get connection with Database MySQL
	 * @param scheme
	 * @param login
	 * @param password
	 * @return
	 * null, if error connection
	 */
	private Connection getConnection(String scheme, String login, String password){
		// String scheme="test";
		// String login="root";
		// String password="";
		try{
			String driver_name="org.gjt.mm.mysql.Driver";
            Class.forName(driver_name);
            return java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/"+scheme,login,password);
		}catch(Exception ex){
			System.err.println("#getConnection: "+ex.getMessage());
			return null;
		}
	}
