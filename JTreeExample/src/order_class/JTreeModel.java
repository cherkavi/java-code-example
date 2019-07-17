package order_class;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class JTreeModel implements Model{
	private ArrayList<Element> list=new ArrayList<Element>();

	/** таблица, в которую данные нужно будет положить - таблица с порядками данных */
	private final static String tableName="A_CLASS_ORDER";
	/** уникальное поле порядка в таблице последовательности */
	private final static String columnIdName="KOD";
	/** поле класса, которое соответствует коду из таблицы {@link="#sourceTableName" }sourceTableName */
	private final static String columnAClassKodName="A_CLASS_KOD";
	private final static String columnAClassName="A_CLASS_NAME";
	private final static String columnASectionKodName="A_SECTION_KOD";
	private final static String columnASectionName="A_SECTION_NAME";
	private boolean flagChanged=false;

	/** модель содержащая данные из таблицы "A_CLASS_ORDER"*/
	public JTreeModel(){
	}
	
	
	private void loadToListFromDatabase(Connection connection) throws Exception {
		if(tableIsEmpty(connection)==true){
			rewriteFromSource(connection);
		}else{
			loadFromSource(connection);
		}
	}
	
	
	/** перезаписать данные из {@link="#sourceTableName"} в приемник {@link="#tableName"} и заполнить объект с данными */
	private void rewriteFromSource(Connection connection) throws Exception{
		ResultSet rs=null;
		list.clear();
		try{
			StringBuffer query=new StringBuffer();
			query.append("SELECT A_CLASS.KOD A_CLASS_KOD, \n");
			query.append("		 A_CLASS.NAME A_CLASS_NAME, \n");
			query.append("		 A_CLASS.KOD_SECTION A_SECTION_KOD, \n");
			query.append("		 A_SECTION.NAME A_SECTION_NAME \n");
			query.append("FROM A_CLASS \n");
			query.append("INNER JOIN A_SECTION ON A_SECTION.KOD=A_CLASS.KOD_SECTION \n");
			query.append("ORDER BY A_SECTION.KOD, A_CLASS.NAME");
			rs=connection.createStatement().executeQuery(query.toString());
			// read to object-data
			while(rs.next()){
				list.add(new Element(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getString(4)));
			}
			// write to object-data
			connection.setAutoCommit(false);
				// remove all data
			connection.createStatement().executeUpdate("delete from "+tableName);
			connection.commit();
			String preparedQuery="insert into "+tableName+" ("+columnIdName+","+columnAClassKodName+","+columnAClassName+","+columnASectionKodName+","+columnASectionName+") values (?,?,?,?,?)";
			PreparedStatement preparedStatement=connection.prepareStatement(preparedQuery);
			for(int counter=0;counter<list.size();counter++){
				preparedStatement.setInt(1, counter);
				preparedStatement.setInt(2, list.get(counter).getKodClass());
				preparedStatement.setString(3, list.get(counter).getNameClass());
				preparedStatement.setInt(4, list.get(counter).getKodSection());
				preparedStatement.setString(5, list.get(counter).getNameSection());
				preparedStatement.executeUpdate();
			}
			connection.commit();
			this.flagChanged=false;
		}catch(Exception ex){
			try{
				connection.rollback();
			}catch(Exception exInner){};
			err("#rewriteFromSource Exception:"+ex.getMessage());
			throw ex;
		}finally{
			try{
				rs.getStatement().close();
			}catch(Exception ex){};
		}
	}
	
	/** прочесть данные из {@link="#tableName"}*/
	private void loadFromSource(Connection connection) throws Exception {
		this.list.clear();
		ResultSet rs=null;
		try{
			rs=connection.createStatement().executeQuery("select * from "+tableName);
			while(rs.next()){
				list.add(new Element(rs.getInt(columnAClassKodName),
									 rs.getString(columnAClassName),
									 rs.getInt(columnASectionKodName),
									 rs.getString(columnASectionName)
									 )
						 );
			}
			this.flagChanged=false;
		}catch(Exception ex){
			err("#loadFromSource "+ex.getMessage());
			throw ex;
		}finally{
			try{
				rs.getStatement().close();
			}catch(Exception ex){}
		}
	}
	
	
	/** проверяет пуста ли таблица с данными */
	private boolean tableIsEmpty(Connection connection){
		boolean returnValue=true;
		ResultSet rs=null;
		try{
			rs=connection.createStatement().executeQuery("select * from "+tableName);
			if(rs.next()){
				returnValue=false;
			}
		}catch(Exception ex){
			// попытка создания таблицы в базе данных
			try{
				connection.rollback();
			}catch(Exception exInner){};
			createTableIntoDataBase(connection);
			err("tableIsEmpty: "+ex.getMessage());
		}finally{
			try{
				rs.getStatement().close();
			}catch(Exception ex){};
		}
		return returnValue;
	}
	
	private void createTableIntoDataBase(Connection connection){
		try{
			StringBuffer query=new StringBuffer();
			query.append("CREATE TABLE A_CLASS_ORDER (\n");
			query.append("KOD             INTEGER NOT NULL,\n");
			query.append("A_CLASS_KOD     INTEGER,\n");
			query.append("A_CLASS_NAME    VARCHAR(100),\n");
			query.append("A_SECTION_KOD   INTEGER,\n");
			query.append("A_SECTION_NAME  VARCHAR(100)); \n");
			connection.createStatement().executeUpdate(query.toString());
			if(connection.getAutoCommit()==false){
				connection.commit();
			}
		}catch(Exception ex){
			System.err.println("Try create Table Error: "+ex.getMessage());
		}
	}
	
	/** Output error message */
	private void err(Object information){
		System.err.print("JTreeModel ");
		System.err.print(" ERROR ");
		System.err.println(information);
	}
	/** Output log message */
	@SuppressWarnings("unused")
	private void out(Object information){
		System.out.print("JTreeModel ");
		System.out.print(" ERROR ");
		System.out.println(information);
	}

	@Override
	public void load(Connection connection) throws Exception {
		loadToListFromDatabase(connection);
	}
	
	@Override
	public Element getElement(int index) {
		return this.list.get(index);
	}

	@Override
	public int getSize() {
		return this.list.size();
	}

	@Override
	public void moveDownElement(int index) {
		if(index<(this.list.size()-1)){
			Element removedElement=list.remove(index);
			list.add(index+1,removedElement);
			this.flagChanged=true;
		}else{
			// it is last element 
		}
	}

	@Override
	public void moveUpElement(int index) {
		if(index>0){
			Element removedElement=list.remove(index);
			list.add(index-1,removedElement);
			this.flagChanged=true;
		}else{
			// it is first element
		}
	}

	@Override
	public void save(Connection connection) throws Exception {
		PreparedStatement preparedStatement=null;
		try{
			connection.commit();
			connection.createStatement().executeUpdate("delete from "+tableName);
			connection.commit();
			connection.setAutoCommit(false);
			
			String preparedQuery="insert into "+tableName+" ("+columnIdName+","+columnAClassKodName+","+columnAClassName+","+columnASectionKodName+","+columnASectionName+") values (?,?,?,?,?)";
			preparedStatement=connection.prepareStatement(preparedQuery);
			
			for(int counter=0;counter<list.size();counter++){
				preparedStatement.setInt(1, counter);
				preparedStatement.setInt(2, list.get(counter).getKodClass());
				preparedStatement.setString(3, list.get(counter).getNameClass());
				preparedStatement.setInt(4, list.get(counter).getKodSection());
				preparedStatement.setString(5, list.get(counter).getNameSection());
				preparedStatement.executeUpdate();
			}
			connection.commit();
			this.flagChanged=false;
		}catch(Exception ex){
			try{
				connection.rollback();
			}catch(Exception innerEx){};
			err("#save Exception: ");
		}finally{
			try{
				preparedStatement.close();
			}catch(Exception ex){};
		}
	}


	@Override
	public int getIndex(Element element) {
		return this.list.indexOf(element);
	}

	@Override
	public void moveUpSection(Element element) {
		int targetSectionBegin=this.getSectionFirstPosition(element.getNameSection());
		Element firstElement=this.list.get(targetSectionBegin);
		if(targetSectionBegin>0){
			Element priorSectionFirstElement=this.getPriorSectionFirstElement(firstElement);
			if(priorSectionFirstElement==null){
				// section is last can't move down
			}else{
				Element lastElement=this.getLastElementInSection(firstElement);
				int targetSectionEnd=this.list.indexOf(lastElement);
				// section is not last - is move down ok
				
				// first element; targetSectionBegin 
				// next section first element
				// next section last element
				ArrayList<Element> removedElement=new ArrayList<Element>(targetSectionEnd-targetSectionBegin+1);
				for(int counter=0;counter<(targetSectionEnd-targetSectionBegin+1);counter++){
					removedElement.add(this.list.remove(targetSectionBegin));
				}
				int positionPriorSectionFirstElement=this.list.indexOf(priorSectionFirstElement);
				this.list.addAll(positionPriorSectionFirstElement,removedElement);
				this.flagChanged=true;
			}
		}else{
			System.err.println("Target Section: "+element.getNameSection()+" is not finded");
		}
	}

	/** найти начало данной секции и получить начало предыдущей, вернуть найденный элемент */
	private Element getPriorSectionFirstElement(Element element){
		Element returnValue=this.list.get(0);
		int position=this.list.indexOf(element);
		int sectionKod=element.getKodSection();
		for(int counter=position;counter>=0;counter--){
			if(this.list.get(counter).getKodSection().intValue()!=sectionKod){
				// найдено начало предыдушего раздела 
				returnValue=getFirstElementInSection(this.list.get(counter));
				break;
			}
		}
		return returnValue;
	}
	
	@Override
	public void moveDownSection(Element element) {
		int targetSectionBegin=this.getSectionFirstPosition(element.getNameSection());
		Element firstElement=this.list.get(targetSectionBegin);
		if(targetSectionBegin>=0){
			Element nextSectionFirstElement=this.getNextSectionFirstElement(firstElement);
			if(nextSectionFirstElement==null){
				// section is last can't move down
			}else{
				int indexNextSectionFirstElement=this.list.indexOf(nextSectionFirstElement);
				// section is not last - is move down ok
				Element nextSectionLastElement=this.getLastElementInSection(nextSectionFirstElement);
				// first element; targetSectionBegin 
				// next section first element
				// next section last element
				ArrayList<Element> removedElement=new ArrayList<Element>(indexNextSectionFirstElement-targetSectionBegin);
				for(int counter=0;counter<(indexNextSectionFirstElement-targetSectionBegin);counter++){
					removedElement.add(this.list.remove(targetSectionBegin));
				}
				int positionNextSectionLastElement=this.list.indexOf(nextSectionLastElement);
				this.list.addAll(positionNextSectionLastElement+1,removedElement);
				this.flagChanged=true;
			}
		}else{
			System.err.println("Target Section: "+element.getNameSection()+" is not finded");
		}
	}

	/** возвращает первый элемент, который принадлежит другой секции после заданной в искомом элементе  */
	private Element getNextSectionFirstElement(Element firstElementInSection){
		Element returnValue=null;
		int beginPosition=this.list.indexOf(firstElementInSection);
		int controlSectionKod=firstElementInSection.getKodSection();
		for(int counter=beginPosition;counter<this.list.size();counter++){
			if(this.list.get(counter).getKodSection().intValue()!=controlSectionKod){
				returnValue=this.list.get(counter);
				break;
			}
		}
		return returnValue;
	}

	private Element getLastElementInSection(Element firstElementInSection){
		Element returnValue=this.list.get(this.list.size()-1);
		int beginPosition=this.list.indexOf(firstElementInSection);
		int controlSectionKod=firstElementInSection.getKodSection();
		for(int counter=beginPosition;counter<this.list.size();counter++){
			if(this.list.get(counter).getKodSection().intValue()!=controlSectionKod){
				returnValue=this.list.get(counter-1);
				break;
			}
		}
		return returnValue;
	}

	private Element getFirstElementInSection(Element firstElementInSection){
		Element returnValue=this.list.get(0);
		int beginPosition=this.list.indexOf(firstElementInSection);
		int controlSectionKod=firstElementInSection.getKodSection();
		for(int counter=beginPosition;counter>=0;counter--){
			if(this.list.get(counter).getKodSection().intValue()!=controlSectionKod){
				returnValue=this.list.get(counter+1);
				break;
			}
		}
		return returnValue;
	}
	
	/** получить первую позицию элемента с заданной секцией */
	private int getSectionFirstPosition(String sectionName){
		int returnValue=(0);
		for(int counter=0;counter<this.list.size();counter++){
			String currentSectionName=this.list.get(counter).getNameSection();
			if(currentSectionName.equals(sectionName)){
				returnValue=counter;
				break;
			}
		}
		return returnValue;
	}


	@Override
	public void resetDataIntoDataBase(Connection connection) {
		try{
			connection.commit();
			connection.createStatement().executeUpdate("delete from "+tableName);
			connection.commit();
		}catch(Exception ex){
			try{
				connection.close();
			}catch(Exception ex2){};
			System.err.println("JTreeModel#resetDataIntoDataBase: "+ex.getMessage());
		}
	}


	
	@Override
	public boolean isChanged() {
		return flagChanged;
	}
	
	
	

}
