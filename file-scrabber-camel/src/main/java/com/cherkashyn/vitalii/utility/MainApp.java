package com.cherkashyn.vitalii.utility;

import org.apache.camel.main.Main;

public class MainApp {

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {
    	Args arguments=parseArgs(args);
        Main main = new Main();
        main.enableHangupSupport();
        main.addRouteBuilder(new CopyRoute(arguments.source, arguments.destination));
        main.run(); // main.run(args);
    }
    
    
    private static Args parseArgs(String[] args) {
    	return new Args(args);
	}

    
	private static class Args{
    	public Args(String[] args) {
        	if(args==null || args.length<2){
        		throw new IllegalArgumentException(" need to have 'source' and 'destination' forlders");
        	}
			this.source=args[0];
			this.destination=args[1];
		}
		String source;
    	String destination;
    }

}

