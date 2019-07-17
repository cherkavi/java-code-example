package variant_2;

import java.io.File;
import java.sql.Connection;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import variant_2.wrap.*;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import database.Connector;
import database.EConnectionType;

public class CopyToDb {
	public static void main(String[] args) throws Exception {
		System.out.println("begin");

		Connector.init(EConnectionType.mysql, "127.0.0.1", 0, "shop_list_reestr","technik", "technik", 20, new Class<?>[]{Kind.class, KindDescription.class, Sites.class, SiteState.class, UserHistory.class, Users.class});
		Connection connection=Connector.getInstance().getConnection();
		Session session=Connector.getInstance().openSession(connection);
		Workbook workbook=Workbook.getWorkbook(new File("D:\\eclipse_workspace\\TempParser\\Information\\List_of_shops\\List_shop_for_DB.xls"));
		
		Sheet sheet=workbook.getSheet(0);
		int rowCount=sheet.getRows();
		for(int rowCounter=1;rowCounter<rowCount;rowCounter++){
			try{
				process(connection, session, sheet.getRow(rowCounter));
			}catch(Exception ex){
				System.err.println("RowProcess ("+rowCounter+") Exception:"+ex.getMessage());
			}
		}
		connection.close();
		System.out.println("end");
	}
	
	/** получить "чистое" имя сайта */
	private static String getSiteClearValue(String value){
        if(value!=null){
            String returnValue=value.toLowerCase().trim().replaceAll("^http://", "").replaceAll("^www.", "");
            int index=returnValue.indexOf('/');
            if(index>0){
                returnValue=returnValue.substring(0,index);
            }
            return returnValue;
        }else{
            return null;
        }
	}
	
	
	/** обработчик для строки  */
	private static void process(Connection connection, Session session, Cell[] cells){
		String site=cells[0].getContents();
		String kindValue=cells[1].getContents();
		String descriptor=cells[2].getContents();

		if(site==null||site.equals(""))return;
		if(kindValue==null||kindValue.equals(""))return;

		site=getSiteClearValue(site);
		// есть ли данный сайт в списке ?
		System.out.println("Site:"+site);
		if(isSitePresentIntoDatabase(session, site)==false){
			try{
				session.beginTransaction();
				Sites sites=new Sites(site);
				session.save(sites);
				
				Kind kind=(Kind)session.createCriteria(Kind.class).add(Restrictions.eq("name", kindValue)).uniqueResult();
				if(kind==null){
					kind=new Kind();
					kind.setName(kindValue);
					session.save(kind);
				}
				KindDescription kindDescriptor=(KindDescription)session.createCriteria(KindDescription.class)
																	   .add(Restrictions.eq("idKind", kind.getId()))
																	   .add(Restrictions.eq("description", descriptor))
																	   .uniqueResult()
																	   ;
				if(kindDescriptor==null){
					kindDescriptor=new KindDescription();
					kindDescriptor.setIdKind(kind.getId());
					kindDescriptor.setDescription(descriptor);
					session.save(kindDescriptor);
				}
				SiteState siteState=new SiteState(sites.getId(), 1, kindDescriptor.getId());
				session.save(siteState);
				session.getTransaction().commit();
			}catch(Exception ex){
				System.err.println("Process Exception:"+ex.getMessage());
				try{
					session.getTransaction().rollback();
				}catch(Exception exInner){};
			}
			
			
		}else{
			System.out.println("ресурс уже в базе:"+site);
		}
	}

	private static boolean isSitePresentIntoDatabase(Session session, String site) {
		if(session.createCriteria(Sites.class).add(Restrictions.eq("address", site)).uniqueResult()!=null){
			// данный сайт уже есть в списке
			return true;
		}else{
			// данного сайта нет в списке
			return false;
		}
	}
}
