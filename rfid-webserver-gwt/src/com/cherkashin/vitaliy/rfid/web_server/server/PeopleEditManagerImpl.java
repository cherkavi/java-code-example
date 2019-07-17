package com.cherkashin.vitaliy.rfid.web_server.server;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cherkashin.vitaliy.rfid.web_server.client.view.edit.Card;
import com.cherkashin.vitaliy.rfid.web_server.client.view.edit.IPeopleEditManager;
import com.cherkashin.vitaliy.rfid.web_server.client.view.edit.People;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import database.ConnectWrap;
import database.StaticConnector;
import database.wrap.Rfid;

public class PeopleEditManagerImpl extends RemoteServiceServlet implements IPeopleEditManager{
	private final static long serialVersionUID=1L;

	@SuppressWarnings("unchecked")
	@Override
	public com.cherkashin.vitaliy.rfid.web_server.client.view.edit.People[] getAllPeople() {
		com.cherkashin.vitaliy.rfid.web_server.client.view.edit.People[] returnValue=new com.cherkashin.vitaliy.rfid.web_server.client.view.edit.People[]{};
		ConnectWrap connector=StaticConnector.getConnectWrap();
		try{
			Session session=connector.getSession();
			List<database.wrap.People> list=(List<database.wrap.People>)session.createCriteria(database.wrap.People.class).addOrder(Order.asc("id")).list();
			ArrayList<com.cherkashin.vitaliy.rfid.web_server.client.view.edit.People> listOfReturnValue=new ArrayList<com.cherkashin.vitaliy.rfid.web_server.client.view.edit.People>();
			for(int counter=0;counter<list.size();counter++){
				com.cherkashin.vitaliy.rfid.web_server.client.view.edit.People people=new com.cherkashin.vitaliy.rfid.web_server.client.view.edit.People();
				people.setId(list.get(counter).getId());
				people.setName(list.get(counter).getName());
				people.setSurname(list.get(counter).getSurname());
				people.setFatherName(list.get(counter).getFatherName());
				people.setCardNumber(list.get(counter).getCardNumber());
				listOfReturnValue.add(people);
			}
			returnValue=listOfReturnValue.toArray(returnValue);
		}catch(Exception ex){
			returnValue=null;
			System.err.println("PeopleEditManagerImpl#getAllPeople Exception:"+ex.getMessage());
		}finally{
			connector.close();
		}
		return returnValue;
	}

	@Override
	public String removePeople(int kod) {
		String returnValue=null;
		ConnectWrap connector=StaticConnector.getConnectWrap();
		try{
			Session session=connector.getSession();
			database.wrap.People people=(database.wrap.People)session.get(database.wrap.People.class, new Integer(kod));
			if(people!=null){
				session.beginTransaction();
				session.delete(people);
				session.getTransaction().commit();
				returnValue=null;
			}else{
				returnValue="Пользователь не найден";
			}
		}catch(Exception ex){
			returnValue=ex.getMessage();
			System.err.println("PeopleEditManagerImpl#removePeople Exception:"+ex.getMessage());
		}finally{
			connector.close();
		}
		return returnValue;
	}

	@Override
	public String savePeople(Integer kod, String name, String surname, String fatherName, String cardNumber) {
		String returnValue=null;
		ConnectWrap connector=StaticConnector.getConnectWrap();
		try{
			if((kod==null)||(kod.intValue()==0)){
				// проверить на наличие подобного человека в системе
				Session session=connector.getSession();
				if(session.createCriteria(database.wrap.People.class).add(Restrictions.eq("cardNumber", cardNumber)).list().size()>0){
					returnValue="номер карты уже записан в базу"; 
				}else{
					database.wrap.People people=new database.wrap.People();
					people.setName(name);
					people.setSurname(surname);
					people.setFatherName(fatherName);
					people.setCardNumber(cardNumber);
					session.beginTransaction();
					session.save(people);
					session.getTransaction().commit();
					returnValue=null;
				}
			}else{
				// обновить пользователя 
				Session session=connector.getSession();
				database.wrap.People people=(database.wrap.People)session.get(database.wrap.People.class, kod);
				if(people==null){
					returnValue="Пользователь не найден";
				}else{
					people.setName(name);
					people.setSurname(surname);
					people.setFatherName(fatherName);
					people.setCardNumber(cardNumber);
					session.beginTransaction();
					session.update(people);
					session.getTransaction().commit();
					returnValue=null;
				}
			}
		}catch(Exception ex){
			returnValue=ex.getMessage();
			System.err.println("PeopleEditManagerImpl#savePeople Exception:"+ex.getMessage());
		}finally{
			connector.close();
		}
		return returnValue;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Card[] getLastCardWithoutUser() {
		Card[] returnValue=new Card[]{};
		ConnectWrap connector=StaticConnector.getConnectWrap();
		try{
			Session session=connector.getSession();
			List<Rfid> list=(List<Rfid>)session.createCriteria(Rfid.class).add(Restrictions.isNull("idUser")).addOrder(Order.desc("id")).setMaxResults(5).list();
			returnValue=new Card[list.size()];
			for(int counter=0;counter<list.size();counter++){
				Card card=new Card();
				card.setId(list.get(counter).getId());
				card.setIdUser(list.get(counter).getIdUser());
				card.setCardNumber(list.get(counter).getCardNumber());
				card.setDate(list.get(counter).getTimeWrite());
				returnValue[counter]=card;
			}
		}catch(Exception ex){
			returnValue=null;
			System.err.println("PeopleEditManagerImpl#getLastCardWithoutUser Exception:"+ex.getMessage());
		}finally{
			connector.close();
		}
		return returnValue;
	}

	@Override
	public People getPeople(Integer kod) {
		com.cherkashin.vitaliy.rfid.web_server.client.view.edit.People returnValue=null;
		ConnectWrap connector=StaticConnector.getConnectWrap();
		try{
			Session session=connector.getSession();
			database.wrap.People people=(database.wrap.People)session.get(database.wrap.People.class, kod);
			returnValue=new com.cherkashin.vitaliy.rfid.web_server.client.view.edit.People();
			returnValue.setId(people.getId());
			returnValue.setName(people.getName());
			returnValue.setSurname(people.getSurname());
			returnValue.setFatherName(people.getFatherName());
			returnValue.setCardNumber(people.getCardNumber());
		}catch(Exception ex){
			System.err.println("PeopleEditManagerImpl#getPeople Exception:"+ex.getMessage());
		}finally{
			connector.close();
		}
		return returnValue;
	}

	// public String savePeople(String name, String surname, String fatherName, String сardNumber) { return null; }
}
