package com.epam.crmwb.sonar.plugin;

import org.sonar.api.Plugin;
import org.sonar.api.Properties;
import org.sonar.api.Property;

import java.util.Arrays;
import java.util.List;

@Properties({
    @Property(
        key = EmailPublisher.ENABLED_PROPERTY,
        name = "Enabled",
        defaultValue = EmailPublisher.ENABLED_DEFAULT_VALUE + "",
        global = true, project = true, module = false),
    @Property(
        key = EmailPublisher.HOST_PROPERTY,
        name = "SMTP host",
        defaultValue = EmailPublisher.SMTP_HOST_DEFAULT_VALUE,
        global = true, project = false, module = false),
    @Property(
        key = EmailPublisher.PORT_PROPERTY,
        name = "SMTP port",
        defaultValue = EmailPublisher.PORT_DEFAULT_VALUE,
        global = true, project = false, module = false),
    @Property(
        key = EmailPublisher.TLS_PROPERTY,
        name = "TLS",
        defaultValue = EmailPublisher.TLS_DEFAULT_VALUE + "",
        global = true, project = false, module = false),
    @Property(
        key = EmailPublisher.USERNAME_PROPERTY,
        name = "SMTP username",
        global = true, project = false, module = false),
    @Property(
        key = EmailPublisher.PASSWORD_PROPERTY,
        name = "SMTP password",
        global = true, project = false, module = false),
    @Property(
        key = EmailPublisher.FROM_PROPERTY,
        name = "From",
        global = true, project = false, module = false),
    @Property(
        key = EmailPublisher.TO_PROPERTY,
        name = "To",
        global = true, project = true, module = false) })
public class EmailPlugin implements Plugin {

  public String getKey() {
    return "email";
  }

  public String getName() {
    return "Email";
  }

  public String getDescription() {
    return "Email";
  }

  public List getExtensions() {
    return Arrays.asList(EmailPublisher.class);
  }

}
