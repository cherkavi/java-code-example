package com.cherkashyn.vitalii.ldap;

import com.beust.jcommander.DynamicParameter;
import com.beust.jcommander.Parameter;

import java.util.HashMap;
import java.util.Map;

public class Arguments {

	    @Parameter(names = {"--help"}, description = "list of arguments for ldap authentication", help = true)
	        boolean isHelp;

	        @Parameter(names = {"-u", "--url"}, description = "url to ldap", required = true)
		    String url="ldaps://ubs000001.addp.com:636";

		    @Parameter(names = {"-p", "--password"}, description = "password of user", required = true)
		        String password="my_password";

		        @DynamicParameter(names = {"-d"}, description = "parameters for ldap connection: -dkey=value ", required = true)
			    Map<String, String> properties=new HashMap<String, String>();


    public static void main( String[] args ) {
	            System.out.println( "--- begin ---" );

		            Arguments arguments = new Arguments();
			            JCommander.newBuilder()
					                    .addObject(arguments)
							                    .build()
									                    .parse(args);
				            if(arguments.isHelp){
						                new JCommander(arguments, args).usage();
								            System.exit(0);
									            }

					            // contextFactory.createContext(getContextEnvironment(arguments.url, arguments.password, arguments.properties));
						    //         System.out.println(arguments.url);
						    //                 System.out.println(arguments.password);
						    //                         System.out.println(arguments.properties);
						    //                                 System.out.println("---  end  ---");
						    //                                     }
						    //
}

