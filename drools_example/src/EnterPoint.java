
  
import java.io.File;  
import java.util.Collection;  
  
import org.drools.KnowledgeBase;  
import org.drools.KnowledgeBaseFactory;  
import org.drools.builder.KnowledgeBuilder;  
import org.drools.builder.KnowledgeBuilderFactory;  
import org.drools.builder.ResourceType;  
import org.drools.io.ResourceFactory;  
import org.drools.runtime.StatefulKnowledgeSession;  
import org.drools.runtime.StatelessKnowledgeSession;  
import org.drools.runtime.rule.WorkingMemoryEntryPoint;  

public class EnterPoint {
	public static void main(String[] args){
		System.out.println("begin");
		 //Create KnowledgeBase...  
        KnowledgeBase knowledgeBase = createKnowledgeBase();  
        //Create a statefull session  
        StatefulKnowledgeSession session = knowledgeBase.newStatefulKnowledgeSession();
        try {  
            //Create Facts and insert them  
			Account account = new Account();  
			account.setBalance(10);  
			account.setName("N1");  
			
			Account account2 = new Account();  
			account2.setBalance(20);  
			account2.setName("N2");  
			
			            //Insert fact  
			session.insert(account);  
			session.insert(account2);  
			
			            //Fire rules  
			session.fireAllRules();  
			
			            //Insert new bonus fact  
			Bonus b = new Bonus();  
			b.setAmmount(90);  
			
			            //insert fact  
			session.insert(b);  
			            //Fire rules  
			session.fireAllRules();  
			
			           //Create new fact  
			b = new Bonus();  
			b.setAmmount(100);  
			            //insert new fact  
			session.insert(b);  
			
			b = new Bonus();  
			b.setAmmount(200);  
			session.insert(b);  
			
			            //Fire rules  
			session.fireAllRules();  
		} finally {  
			session.dispose();  
		}  
        
		System.out.println("-end-");
	}
	
	/** 
	 * Create new knowledge base 
	 */  
	private static KnowledgeBase createKnowledgeBase() {  
	     KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();  
	             //Add drl file into builder  
	     File drl = new File("C:\\basicRule.drl");  
	     builder.add(ResourceFactory.newFileResource(drl), 
	    		 	 ResourceType.DRL);  
	     if (builder.hasErrors()) {  
	         throw new RuntimeException(builder.getErrors().toString());  
	     }  
	
	     KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();  
	             //Add to Knowledge Base packages from the builder which are actually the rules from the drl file.  
	     knowledgeBase.addKnowledgePackages(builder.getKnowledgePackages());  
	     return knowledgeBase;  
	}
	
}
