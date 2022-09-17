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

}
