package bc.data_terminal.editor.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/** получить XML файл, который отображает отвечает за визуальное отображение интерфейса для указанного клиента 
 * */
public class VisualXml {
	private Logger field_logger=Logger.getLogger("VisualXml");
	{
		field_logger.setLevel(Level.DEBUG);
		
	}
	
	private String field_user_name;
	
	public VisualXml(String user_name){
		this.field_user_name=user_name;
	}
	
	/** получить всех потомков для данного parent_name
	 * @param connection
	 * @param terminal_id
	 * @param parent_name
	 * @return ArrayList<VisualElement>
	 * */
	private ArrayList<VisualElement> getChildFromParent(Document document, Connection connection,String terminal_id,String parent_name ){
		ArrayList<VisualElement> return_value=new ArrayList<VisualElement>();
		VisualElement element;
		try{
			PreparedStatement ps;
			if(parent_name!=null){
				ps=connection.prepareStatement("select * from v_dt_term_task where id_term=? and id_task_parent=?");
				ps.setString(1, terminal_id);
				ps.setString(2, parent_name);
			}else{
				ps=connection.prepareStatement("select * from v_dt_term_task where id_term=? and id_task_parent is null");
				ps.setString(1, terminal_id);
			}
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				element=new VisualElement();
				element.loadFromResultSet(rs);
				return_value.add(element);
			}
			rs.close();
			field_logger.debug("getChildFromParent done:"+terminal_id+"   parent:"+parent_name);
		}catch(SQLException ex){
			field_logger.error("getChildFromParent SQLException:"+ex.getMessage());
		}catch(Exception ex){
			field_logger.error("getChildFromParent Exception:"+ex.getMessage());
		}
		return return_value;
	}
	
	
	/** возвращает True, если у элемента есть потомки */
	private boolean isVisualElementHasChild(Connection connection,String terminal_id, String parent_id){
		boolean return_value=false;
		try{
			PreparedStatement ps;
			if(parent_id!=null){
				ps=connection.prepareStatement("select * from v_dt_term_task where id_term=? and id_task_parent=?");
				ps.setString(1, terminal_id);
				ps.setString(2, parent_id);
			}else{
				ps=connection.prepareStatement("select * from v_dt_term_task where id_term=? and id_task_parent is null");
				ps.setString(1, terminal_id);
			}
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				return_value=true;
			}
			rs.close();
		}catch(Exception ex){
			field_logger.error("Exception: "+ex.getMessage());
		}
		return return_value;
	}
	
	
	public Document getVisualXml(){
		return this.getVisualXmlByUser(this.field_user_name);
	}
	
	
	/** получить NODE на основании кода*/
	private Element getElementFromTable(Document document, Connection connection, String terminal_id, String task_id){
		Element return_value=null;
		try{
			field_logger.debug("getElementFromTable: prepared statement");
			PreparedStatement ps;
			if(task_id==null){
				ps=connection.prepareStatement("select * from v_dt_term_task where id_term=? and id_task_parent is null");
				ps.setString(1, terminal_id);
			}else{
				ps=connection.prepareStatement("select * from v_dt_term_task where id_term=? and id_task=?");
				ps.setString(1, terminal_id);
				ps.setString(2, task_id);
			}
			field_logger.debug("getElementFromTable: before execute query");
			ResultSet rs=ps.executeQuery();
			VisualElement element=new VisualElement();
			field_logger.debug("getElementFromTable: before get result");
			if(rs.next()){
				element.loadFromResultSet(rs);
			}
			field_logger.debug(element);
			rs.close();
			/* 
			field_logger.debug("getElementFromTable: document:"+document);
			field_logger.debug("getElementFromTable: connection:"+connection);
			field_logger.debug("getElementFromTable: terminal_id:"+terminal_id);
			field_logger.debug("getElementFromTable: task_id:"+task_id);
			*/
			return_value=document.createElement(element.getNameTask());
			field_logger.debug("setAttribute: visible");
			return_value.setAttribute("visible", (element.isVisible())?"true":"false");
			return_value.setAttribute("task_id",element.getIdTask());
		}catch(Exception ex){
			field_logger.error("Exception: "+ex.getMessage());
		}
		return return_value;
	}
	
	private Element getChildFromNode(Document document, Connection connection, String user_name, String parent_id){
		field_logger.debug("check has child ");
		if(isVisualElementHasChild(connection, user_name,parent_id)){
			field_logger.debug("child has exists");
			Element parent_element=getElementFromTable(document, connection, user_name, parent_id);
			ArrayList<VisualElement> list=getChildFromParent(document, connection, user_name, parent_id);
			for(int counter=0;counter<list.size();counter++){
				field_logger.debug("getChildFromNode:"+list.get(counter));
				field_logger.debug("getChildFromNode: counter:"+counter+"   list:"+list.get(counter).getNameTask());
				Element root=getChildFromNode(document, 
	   											connection, 
	   											list.get(counter).getIdTerm(), 
	   											list.get(counter).getIdTask()
												);
				field_logger.debug("parent_element is null");
				parent_element.appendChild(root);
			}
			return parent_element;
		}else{
			field_logger.debug("child no have:  user_name:"+user_name+"   parent_id:"+parent_id);
			return getElementFromTable(document, connection,user_name, parent_id);
		}
	}
	
	private String getIdTaskMain(Connection connection, String user_main){
		String return_value="";
		try{
			field_logger.debug("getElementFromTable: prepared statement");
			PreparedStatement ps;
			ps=connection.prepareStatement("select * from v_dt_term_task where id_term=? and id_task_parent is null");
			ps.setString(1, user_main);
			ResultSet rs=ps.executeQuery();
			field_logger.debug("getElementFromTable: before get result");
			if(rs.next()){
				return_value=rs.getString("ID_TASK");
			}
			field_logger.debug(return_value);
			rs.close();
		}catch(Exception ex){
			field_logger.error("getIdTaskMain: "+ex.getMessage());
		}
		return return_value;
	}
	
	public Document getVisualXmlByUser(String user_name){
		field_logger.debug("begin");
		Document destination_document=null;
		Connection connection=Connector.getConnection();
		try{
			javax.xml.parsers.DocumentBuilderFactory document_builder_factory=javax.xml.parsers.DocumentBuilderFactory.newInstance();
	        document_builder_factory.setValidating(false);
	        document_builder_factory.setIgnoringComments(true);
	        javax.xml.parsers.DocumentBuilder document_builder=document_builder_factory.newDocumentBuilder();
	        destination_document=document_builder.newDocument();
	        field_logger.debug("getVisualXmlByUser:   Document:"+destination_document);
	        Element element=getChildFromNode(destination_document, connection, user_name,getIdTaskMain(connection,user_name));
	        field_logger.debug("getVisualXmlByUser addChild:"+element);
			destination_document.appendChild(element);
		}catch(Exception ex){
			field_logger.error("getVisualXmlbyUser Exception:"+ex.getMessage());
		}
		Connector.closeConnection(connection);
		field_logger.debug("end");
		return destination_document;
	}
	

}


class VisualElement{
	private String field_id_term;
	private String field_id_task;
	private String field_name_task;
	private String field_id_parent;
	private String field_visible;
	
	public VisualElement(){
		this.field_id_term="";
		this.field_id_task="";
		this.field_name_task="";
		this.field_id_parent="";
		this.field_visible="";
	}
	
	public String getIdTerm() {
		return field_id_term;
	}
	public void setIdTerm(String field_id_term) {
		this.field_id_term = field_id_term;
	}
	public String getIdTask() {
		return field_id_task;
	}
	public void setIdTask(String field_id_task) {
		this.field_id_task = field_id_task;
	}
	public String getNameTask() {
		return field_name_task;
	}
	public void setNameTask(String field_id_name) {
		this.field_name_task = field_id_name;
	}
	public String getIdParent() {
		return field_id_parent;
	}
	public void setIdParent(String field_id_parent) {
		this.field_id_parent = field_id_parent;
	}
	
	public void loadFromResultSet(ResultSet rs) throws SQLException{
		this.field_id_term=rs.getString("ID_TERM");
		this.field_id_task=rs.getString("ID_TASK");
		this.field_name_task=rs.getString("NAME_TASK");
		this.field_id_parent=rs.getString("ID_TASK_PARENT");
		this.field_visible=rs.getString("IS_VISIBLE");
	}
	
	public boolean isVisible(){
		return this.field_visible.equals("Y");
	}
	
	public String toString(){
		return "n:"+this.field_name_task+"    v:"+this.isVisible();
	}
	
}