
public class Test {
	public static void main(String[] args){
		System.out.println("begin");
		PoolConnect connect=null;
		try{
			connect=new PoolConnect("org.firebirdsql.jdbc.FBDriver",
									"jdbc:firebirdsql://localhost:3050/D:/eclipse_workspace/ParkingServer/DataBase/parking.gdb?sql_dialect=3",
									"SYSDBA",
									"masterkey");
			for(int counter=0;counter<10;counter++){
				System.out.println("get Connection:"+counter);
				connect.getConnection();
			}
		}catch(Exception ex){
			System.out.println("Exception:"+ex.getMessage());
		}
	}
}
