package com.cherkashyn.vitalii.ldap;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;

public class Operations {
    static interface Operation {
        NamingEnumeration<SearchResult> execute(DirContext context) throws NamingException;
    }

    static void printResults(NamingEnumeration<SearchResult> results) throws NamingException {
        while(results.hasMoreElements()){
            System.out.println("-----");
            SearchResult result = results.next();
            Attributes attrs = result.getAttributes();
            NamingEnumeration<? extends Attribute> attributes = attrs.getAll();
            while(attributes.hasMoreElements()){
                Attribute attribute = attributes.next();
                System.out.println(attribute.toString());
                System.out.println(attribute.get());
            }
        }
    }

    static class SearchByName implements Operation {

        private final String userName;

        SearchByName(String userName){
            this.userName = userName;
        }

        public NamingEnumeration<SearchResult> execute(DirContext context) throws NamingException {
            // Specify the search filter
            String filterExpression = "(&(objectClass=Person) ((sAMAccountName=" + userName + ")))";

            // limit returned attributes to those we care about
            String[] attrIDs = {"sn", "givenName"};

            SearchControls constraints = new SearchControls();
            constraints.setReturningAttributes(attrIDs);
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);

            // Search for objects using filter and controls
            String contextName = "DC=org";
            return context.search(contextName, filterExpression, constraints);
        }
    }
}


// -------------------------------------
// LDAP operations
// -------------------------------------
// Specify the search filter
// String FILTER = "(&(objectClass=Person) ((sAMAccountName=" + user.getUsername() + ")))";
//
//    // limit returned attributes to those we care about
//    String[] attrIDs = { "sn", "givenName" };
//
//    SearchControls ctls = new SearchControls();
//ctls.setReturningAttributes(attrIDs);
//ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
//
//    // Search for objects using filter and controls
//    NamingEnumeration answer = ctx.search(searchBase, FILTER, ctls);
//
//...
//
//    SearchResult sr = (SearchResult) answer.next();
//    Attributes attrs = sr.getAttributes();
//    surName = attrs.get("sn").toString();
//    givenName = attrs.get("givenName").toString();

// ------------------------------------
