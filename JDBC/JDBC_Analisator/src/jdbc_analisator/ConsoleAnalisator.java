package jdbc_analisator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import jdbc_analisator.DatabaseAnalisator.Field;

public class ConsoleAnalisator {
	public static void main(String[] args){
		if((args==null)||(args.length<6)){
			System.out.println("JDBC Analisator ( needed six parameters) 1 2 3 4 5 6");
			System.out.println("1 - First database URL");
			System.out.println("2 - First database LOGIN");
			System.out.println("3 - First database PASSWORD ");
			System.out.println("4 - Second database URL");
			System.out.println("5 - Second database LOGIN");
			System.out.println("6 - Second database PASSWORD ");
			System.exit(0);
		}
		
		Connection one=null;
		Connection two=null;
		try{
			String firstUrl=args[0];
			String firstLogin=args[1];
			String firstPassword=args[2];
			
			String secondUrl=args[3];
			String secondLogin=args[4];
			String secondPassword=args[5];
			
			one=getConnection(DatabaseDrivers.Firebird, firstUrl, firstLogin, firstPassword);
			two=getConnection(DatabaseDrivers.Firebird, secondUrl, secondLogin, secondPassword);
			
			DatabaseAnalisator analisator=new DatabaseAnalisator();
			
			System.out.println("--------------------");			
			System.out.println("Все таблицы, которые есть в "+firstUrl+" но их нет в "+secondUrl);
			ArrayList<String> left=analisator.getOneUniqueTables(one, two);
			printArrayList(left);
			System.out.println("---");
			for(int counter=0;counter<left.size();counter++){
				ArrayList<DatabaseAnalisator.Field> list=analisator.getFieldsList(one, left.get(counter));
				System.out.println("Table: "+left.get(counter));
				printFieldList(list);
				System.out.println("---");
			}
			System.out.println("--------------------");
			
			System.out.println("--------------------");			
			System.out.println("Все таблицы, которые есть в "+secondUrl+" но их нет в "+firstUrl);
			ArrayList<String> right=analisator.getOneUniqueTables(two, one);
			printArrayList(right);
			System.out.println("---");
			for(int counter=0;counter<right.size();counter++){
				ArrayList<DatabaseAnalisator.Field> list=analisator.getFieldsList(one, right.get(counter));
				System.out.println("Table: "+right.get(counter));
				printFieldList(list);
				System.out.println("---");
			}
			System.out.println("--------------------");
			
			System.out.println("--------------------");			
			System.out.println("Общие таблицы для обоих баз данных ");
			ArrayList<String> center=analisator.getEqualsTables(one, two);
			// printArrayList(center);
			for(int counter=0;counter<center.size();counter++){
				ArrayList<Field> oneFields=analisator.getFieldsList(one, center.get(counter));
				ArrayList<Field> twoFields=analisator.getFieldsList(two, center.get(counter));
				printCompareFields(counter+" : "+center.get(counter),oneFields, twoFields);
				System.out.println("---");
			}
			System.out.println("--------------------");			
			
		}catch(Exception ex){
			System.err.println("ConsoleAnalisator Exception:"+ex.getMessage());
		}finally{
			try{
				one.close();
			}catch(Exception ex){};
			try{
				two.close();
			}catch(Exception ex){};
		}
	}
	
	/** вывести на печать поля, для анализа  */
	private static void printCompareFields(String tableName, ArrayList<Field> oneFields, ArrayList<Field> twoFields) {
		ArrayList<Field> left=new ArrayList<Field>();
		ArrayList<Field> right=new ArrayList<Field>();
		
		for(int counter=0;counter<oneFields.size();counter++){
			if( twoFields.indexOf(oneFields.get(counter))<0){
				left.add(oneFields.get(counter));
			}
		}
		for(int counter=0;counter<twoFields.size();counter++){
			if( oneFields.indexOf(twoFields.get(counter))<0){
				right.add(twoFields.get(counter));
			}
		}
		
		if((left.size()==0)&&(right.size()==0)){
			System.out.println(tableName+" : equals");
			return ;
		}
		
		if(left.size()>0){
			System.out.println("-f--");
			for(int counter=0;counter<left.size();counter++){
				System.out.println(left.get(counter)+"  _________________ ");
			}
			System.out.println("-/f-");
		}
		if(right.size()>0){
			System.out.println("-f--");
			for(int counter=0;counter<right.size();counter++){
				System.out.println("  _________________ "+right.get(counter));
			}
			System.out.println("-/f-");
		}
	}

	/** вывести на консоль список полей */
	private static void printFieldList(ArrayList<Field> list) {
		for(int counter=0;counter<list.size();counter++){
			System.out.println(counter+" : "+list.get(counter).getName()+"  "+list.get(counter).getSize());
		}
	}

	private static void printArrayList(ArrayList<String> list){
		for(int counter=0;counter<list.size();counter++){
			System.out.println(counter+" : "+list.get(counter));
		}
	}

	/**
	 * получить соединение с базой данных 
	 * @param driver - полное имя класса-драйвера 
	 * @param url - URL 
	 * @param login - логин 
	 * @param password - пароль 
	 * @return
	 * @throws Exception
	 */
	private static Connection getConnection(DatabaseDrivers driver, String url, String login, String password) throws Exception {
		Class.forName(driver.getDriverName());
		return DriverManager.getConnection(url, login, password);
	}
	
}
