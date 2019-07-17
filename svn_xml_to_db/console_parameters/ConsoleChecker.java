package svn_xml_to_db.console_parameters;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;


public class ConsoleChecker {
	
	/** input parameters for check  */
	public enum EParameters{
		/** XML file from SVN */
		svn_xml_file("XML file from SVN"),
		/** URL to Database */
		db_url("URL to Oracle Database"),
		/** Database Login */
		db_login("Database Login"),
		/** Database password */
		db_password("Database password"),
		/** Level of Logger */
		log_level("Level of Logger: trace, debug, info, warn, error");
		
		private String information;
		EParameters(String information){
			this.information=information;
		}
		/** get description of file */
		public String getDescription(){
			return this.information;
		}
	}
	
	public CommandLine checkArguments(String[] args) throws ParseException{
		return new GnuParser().parse(createOptions(), args);
	}
	
	@SuppressWarnings("static-access")
	private Options createOptions(){
		Options returnValue=new Options();
        returnValue.addOption(OptionBuilder
                .withArgName(EParameters.svn_xml_file.name()) // command line argument
                .hasArg()
                .isRequired()
                .withDescription(EParameters.svn_xml_file.getDescription()) // description
                .create(EParameters.svn_xml_file.name()) // name of created argument in object Options
                );
        returnValue.addOption(OptionBuilder
                .withArgName(EParameters.db_url.name()) // command line argument
                .hasArg()
                .isRequired()
                .withDescription(EParameters.db_url.getDescription()) // description
                .create(EParameters.db_url.name()) // name of created argument in object Options
                );
        returnValue.addOption(OptionBuilder
                .withArgName(EParameters.db_login.name()) // command line argument
                .hasArg()
                .isRequired()
                .withDescription(EParameters.db_login.getDescription()) // description
                .create(EParameters.db_login.name()) // name of created argument in object Options
                );
        returnValue.addOption(OptionBuilder
                .withArgName(EParameters.db_password.name()) // command line argument
                .hasArg()
                .isRequired()
                .withDescription(EParameters.db_password.getDescription()) // description
                .create(EParameters.db_password.name()) // name of created argument in object Options
                );
        returnValue.addOption(OptionBuilder
                .withArgName(EParameters.log_level.name()) // command line argument
                .hasArg()
                .withDescription(EParameters.log_level.getDescription()) // description
                .create(EParameters.log_level.name()) // name of created argument in object Options
                );
        
        return returnValue;
	}
}
