package com.cherkashyn.vitalii.ldap;

import com.beust.jcommander.JCommander;
import com.sun.jndi.ldap.LdapCtxFactory;

import javax.naming.AuthenticationException;
import javax.naming.AuthenticationNotSupportedException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import static com.cherkashyn.vitalii.ldap.Operations.printResults;

public class Connect {
    public static void main(String[] args) {
        System.out.println("--- begin ---");

        Arguments arguments = new Arguments();
        JCommander.newBuilder()
                .addObject(arguments)
                .build()
                .parse(args);
        if (arguments.isHelp) {
            new JCommander(arguments, args).usage();
            System.exit(1);
        }

        connectToLdap(getContextEnvironment(arguments.url, arguments.password, arguments.properties), new Operations.SearchByName("VCherkashyn"));

        System.out.println("---  end  ---");
    }

    private static void connectToLdap(Map<String, String> environment, Operations.Operation ... operations) {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.putAll(environment);
        DirContext context = null;
        try {
            context = new InitialDirContext(env);
            System.out.println("Connected... "+environment);
            if(operations.length>0){
                for(Operations.Operation eachOperation : operations){
                    printResults(eachOperation.execute(context));
                }
            }
        } catch (AuthenticationNotSupportedException ex) {
            System.out.println("The authentication is not supported by the server:"+ex.getMessage());
            System.exit(2);
        } catch (AuthenticationException ex) {
            System.out.println("Incorrect password or username:"+ex.getMessage());
            System.exit(3);
        } catch (NamingException ex) {
            System.out.println("Error when trying to create the context:"+ex.getMessage());
            System.exit(4);
        }finally {
            try {
                context.close();
            } catch (NamingException e) {
            } catch (NullPointerException e){
            }
        }

    }
    private static Map<String, String> getContextEnvironment(String url, String userPassword, Map<String, String> properties) {
        final Map<String, String> env = new HashMap<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, LdapCtxFactory.class.getName());
        env.put(Context.PROVIDER_URL, url);
        env.put(Context.SECURITY_PROTOCOL, "ssl");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_CREDENTIALS, userPassword);
        // env.put(Context.SECURITY_PRINCIPAL, "uid=admin,ou=system");
	// "java.naming.security.principal","uid=vitalii.cherkashyn,ou=users,ou=rbac,dc=ubs,dc=com"
        env.putAll(properties);
        return env;
    }

    private static Map<String, String> convertToMap(List<String> elements) {
        Map<String, String> returnValue = new HashMap<String, String>();
        String key = null;
        for (int index = 0; index < elements.size(); index++) {
            if (key == null) {
                key = elements.get(index);
            } else {
                returnValue.put(key, elements.get(index));
                key = null;
            }
        }
        return returnValue;
    }

}
