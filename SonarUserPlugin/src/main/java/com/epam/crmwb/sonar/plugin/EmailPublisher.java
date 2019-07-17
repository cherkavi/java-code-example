package com.epam.crmwb.sonar.plugin;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.sonar.api.batch.PostJob;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.platform.Server;
import org.sonar.api.resources.Project;
import org.sonar.api.utils.SonarException;

public class EmailPublisher implements PostJob {

  public static final String ENABLED_PROPERTY = "sonar.email.enabled";
  public static final boolean ENABLED_DEFAULT_VALUE = false;

  public static final String HOST_PROPERTY = "sonar.email.smtp_host.secured";
  public static final String SMTP_HOST_DEFAULT_VALUE = "localhost";

  public static final String PORT_PROPERTY = "sonar.email.smtp_port.secured";
  public static final String PORT_DEFAULT_VALUE = "25";

  public static final String TLS_PROPERTY = "sonar.email.password.secured";
  public static final boolean TLS_DEFAULT_VALUE = false;

  public static final String USERNAME_PROPERTY = "sonar.email.smtp_username.secured";

  public static final String PASSWORD_PROPERTY = "sonar.email.smtp_password.secured";

  public static final String FROM_PROPERTY = "sonar.email.from.secured";

  public static final String TO_PROPERTY = "sonar.email.to";

  private static final String PROJECT_INDEX_URI = "/project/index/";

  private Server server;

  public EmailPublisher(Server server) {
    this.server = server;
  }

  Email getEmail(Project project) throws EmailException {
    Configuration configuration = project.getConfiguration();
    SonarEmail email = new SonarEmail();
    String host = configuration.getString(HOST_PROPERTY, SMTP_HOST_DEFAULT_VALUE);
    String port = configuration.getString(PORT_PROPERTY, PORT_DEFAULT_VALUE);
    String username = configuration.getString(USERNAME_PROPERTY);
    String password = configuration.getString(PASSWORD_PROPERTY);
    boolean withTLS = configuration.getBoolean(TLS_PROPERTY, TLS_DEFAULT_VALUE);
    String from = configuration.getString(FROM_PROPERTY, "");
    String to = configuration.getString(TO_PROPERTY, "");

    email.setHostName(host);
    email.setSmtpPort(port);
    if (!StringUtils.isBlank(username) || !StringUtils.isBlank(password)) {
      email.setAuthentication(username, password);
    }
    email.setTLS(withTLS);
    email.setFrom(from);

    String[] addrs = StringUtils.split(to, "\t\r\n;, ");
    for (String addr : addrs) {
      email.addTo(addr);
    }

    email.setSubject(getSubject(project));
    email.setMsg(getMessage(project));

    return email;
  }

  
  
  private void writeToLog(String projectName, Date date, String analysysType){
	  BufferedWriter writer=null;
	  try{
		  String staticFileName="c:\\temp\\plugin_info.txt";
		  writer=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(staticFileName,true)));
		  writer.flush();
	  }catch(Exception ex){
		  // nothing to do 
	  }finally{
			IOUtils.closeQuietly(writer);  
	  }
  }
  
  public void executeOn(Project project, SensorContext context) {
	writeToLog(project.getName(), project.getAnalysisDate(), project.getAnalysisType().name());
	
    if (!project.getConfiguration().getBoolean(ENABLED_PROPERTY, ENABLED_DEFAULT_VALUE)) {
      return;
    }
    try {
      getEmail(project).send();
    } catch (EmailException e) {
      throw new SonarException("Unable to send email", e);
    }
  }

  String getSubject(Project project) {
    return String.format("Sonar analysis of %s", project.getName());
  }

  String getMessage(Project project) {
    StringBuilder url = new StringBuilder(server.getURL()).append(PROJECT_INDEX_URI).append(project.getKey());
    return String.format("Sonar analysis of %s available %s", project.getName(), url);
  }

  static class SonarEmail extends SimpleEmail {
    public void setSmtpPort(String smtpPort) {
      this.smtpPort = smtpPort;
    }
  }

}
