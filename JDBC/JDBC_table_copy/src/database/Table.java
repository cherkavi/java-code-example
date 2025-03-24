package database;

import java.util.ArrayList;

/** обертка для таблицы */
public class Table {
	private String name;
	private ArrayList<Field> listOfField=new ArrayList<Field>();
	private ArrayList<Field> listOfOrderField=new ArrayList<Field>();
	
	/** обертка для таблицы */
	public Table(String name){
		this.name=name;
	}

	/** обертка для таблицы */
	public Table(String name,Field ... fields){
		this.name=name;
		for(int counter=0;counter<fields.length;counter++){
			this.listOfField.add(fields[counter]);
		}
	}
	
	public void addField(Field field){
		this.listOfField.add(field);
	}
	
	public Field getField(int index){
		return this.listOfField.get(index);
	}
	
	public void addOrderField(Field field){
		this.listOfOrderField.add(field);
	}
	
	public int getFieldSize(){
		return this.listOfField.size();
	}
	
	public int getOrderSize(){
		return this.listOfOrderField.size();
	}
	
	public Field getOrderField(int index){
		return this.listOfOrderField.get(index);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
