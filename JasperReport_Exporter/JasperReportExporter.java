package com.gtwm.jasperexecute;

//Copyright 2007 Oliver Kohll
//modified by Charly Clairmont

//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at

//http://www.apache.org/licenses/LICENSE-2.0

//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.

import com.gtwm.jasperexecute.OptionValues.DatabaseType;
import com.gtwm.jasperexecute.OptionValues.OutputType;
import com.gtwm.jasperexecute.OptionValues.ParamType;
import com.gtwm.jasperexecute.beans.JobsReports;
import com.gtwm.jasperexecute.beans.Parameters;
import com.gtwm.jasperexecute.beans.SmtpServer;
import com.gtwm.jasperexecute.utils.ReportConfig;

import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRGraphics2DExporter;
import net.sf.jasperreports.engine.export.JRGraphics2DExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperExportManager;

import java.lang.IllegalArgumentException;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.Address;
import javax.mail.Transport;
import javax.mail.BodyPart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.Multipart;
import javax.mail.internet.MimeMultipart;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Timestamp;

import javax.activation.FileDataSource;
import javax.activation.DataSource;
import javax.activation.DataHandler;
import javax.ejb.Local;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.Properties;
import java.sql.SQLException;

import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Command line application to generate reports from JasperReports XML report
 * definition files, e.g. those designed with iReport
 *
 * One or more report definitions can be specified on the command line and a PDF
 * a HTML, XLS file will be generated from each. If email details are specified,
 * then they'll be attached to an email and sent. If not, they'll just be
 * written to disk
 *
 * Example Usage: java -jar RunJasperReports.jar -dbtype postgresql -dbname
 * accountsdb -dbuser fd -dbpass secret -reports p_and_l.jrxml,sales.jrxml
 * -folder /var/www/financial/ -emailto directors@gtwm.co.uk -emailfrom
 * accounts@gtwm.co.uk -subject FinancialReports -output pdf
 */
public class RunJasperReports {

       
       
        public RunJasperReports() {
        }
       
        /**
         *  generate pdf file
         *  
         * @param reportDefinitionFile
         * @param outputFileName
         * @param dbType
         * @param databaseHostName
         * @param port
         * @param databaseName
         * @param databaseUsername
         * @param databasePassword
         * @param parameters
         * @throws FileNotFoundException
         * @throws JRException
         * @throws SQLException
         * @throws IOException
         * @throws ClassNotFoundException
         */
        public void generatePdfReport(String reportDefinitionFile, String outputFileName,
                        DatabaseType dbType,
                        // CCH20071129-02
                        String databaseHostName,
                        // CCH20080108-14
                        String port,
                        String databaseName, String databaseUsername,
                        String databasePassword, Map parameters) throws FileNotFoundException, JRException,
                        SQLException, IOException, ClassNotFoundException {
                JasperPrint print = generateReport(reportDefinitionFile, dbType,
                                // CCH20071129-02
                                databaseHostName,
                                // CCH20080108-14
                                port,
                                databaseName,
                                databaseUsername, databasePassword, parameters);
                       
                JRPdfExporter exporter = new JRPdfExporter();
               
                List jasperPrintList = new ArrayList();
                jasperPrintList.add(print);
               
                exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,  jasperPrintList);
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
                                outputFileName          
                );
                exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
               
                exporter.exportReport();
               
                System.out.println("Output PDF file written: " + outputFileName);
        }
       
        /**
         * generate HTML
         *
         * @param reportDefinitionFile
         * @param outputFileName
         * @param dbType
         * @param databaseHostName
         * @param port
         * @param databaseName
         * @param databaseUsername
         * @param databasePassword
         * @param parameters
         * @throws FileNotFoundException
         * @throws JRException
         * @throws SQLException
         * @throws IOException
         * @throws ClassNotFoundException
         */
        public void generateHtmlReport(String reportDefinitionFile, String outputFileName,
                        DatabaseType dbType,
                        // CCH20071129-02
                        String databaseHostName,
                        // CCH20080108-14
                        String port,
                        String databaseName, String databaseUsername,
                        String databasePassword, Map parameters) throws FileNotFoundException, JRException,
                        SQLException, IOException, ClassNotFoundException {
                JasperPrint print = generateReport(reportDefinitionFile, dbType,
                                // CCH20071129-02
                                databaseHostName,
                                // CCH20080108-14
                                port,
                                databaseName,
                                databaseUsername, databasePassword, parameters);
                JasperExportManager.exportReportToHtmlFile(print, outputFileName);
                System.out.println("Output HTML file written: " + outputFileName);
        }
       
        /**
         * generate excel file from JXL API
         *
         * @param reportDefinitionFile
         * @param outputFileName
         * @param dbType
         * @param databaseHostName
         * @param port
         * @param databaseName
         * @param databaseUsername
         * @param databasePassword
         * @param parameters
         * @throws FileNotFoundException
         * @throws JRException
         * @throws SQLException
         * @throws IOException
         * @throws ClassNotFoundException
         */
        //CCH20071129-01
        public void generateJxlsReport(String reportDefinitionFile, String outputFileName,
                        DatabaseType dbType,
                        // CCH20071129-02
                        String databaseHostName,
                        // CCH20080108-14
                        String port,
                        String databaseName, String databaseUsername,
                        String databasePassword, Map parameters) throws FileNotFoundException, JRException,
                        SQLException, IOException, ClassNotFoundException {
               
                // ignorer la pagination
                parameters.put(net.sf.jasperreports.engine.JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
               
                JasperPrint print = generateReport(reportDefinitionFile, dbType,
                                // CCH20071129-02
                                databaseHostName,
                                // CCH20080108-14
                                port,
                                databaseName,
                                databaseUsername, databasePassword, parameters);
               
                JExcelApiExporter exporter = new JExcelApiExporter();
               
                List jasperPrintList = new ArrayList();
                jasperPrintList.add(print);
               
                exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,  jasperPrintList);
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFileName);
                //exporter.setParameter(JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
               
                exporter.exportReport();
               
                System.out.println("Output XLS file written: " + outputFileName);
        }
        //CCH20071129-01
       
       
        //CCH20071208-06
        public void generateJpgReport(String reportDefinitionFile, String outputFileName,
                        DatabaseType dbType,
                        // CCH20071129-02
                        String databaseHostName,
                        // CCH20080108-14
                        String port,
                        String databaseName, String databaseUsername,
                        String databasePassword, Map parameters) throws FileNotFoundException, JRException,
                        SQLException, IOException, ClassNotFoundException {
               
       
                JasperPrint print = generateReport(reportDefinitionFile, dbType,
                                // CCH20071129-02
                                databaseHostName,
                                // CCH20080108-14
                                port,
                                databaseName,
                                databaseUsername, databasePassword, parameters);
               
                int height = print.getPageHeight();
                int width  = print.getPageWidth();
               
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D gr2 = image.createGraphics();
       
        JRGraphics2DExporter exporter = new JRGraphics2DExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
        exporter.setParameter(JRGraphics2DExporterParameter.GRAPHICS_2D, gr2 );
                //exporter.setParameter(JRGraphics2DExporterParameter.PAGE_INDEX, new Integer(0));
                exporter.setParameter(JRGraphics2DExporterParameter.OUTPUT_FILE_NAME, outputFileName);
               
                exporter.exportReport();
               
                //gr2.
               
                System.out.println("Output JPG file written: " + outputFileName);
        }
        //CCH20071208-06
       
        /**
         * Perform a liberal check of boolean representation. Any string that starts
         * with 't', 'y' or '1', case-insensitively, will return true - anything
         * else, false, including null. Better than using Boolean.valueOf() because
         * it is less stringent and will never throw an Exception
         */
        private boolean valueRepresentsBooleanTrue(String value) {
                if (value == null) {
                        return false;
                }
                if (value.toLowerCase(Locale.UK).startsWith("t")
                                || value.toLowerCase(Locale.UK).startsWith("y") || value.startsWith("1")) {
                        return true;
                } else {
                        return false;
                }
        }
       
        /**
         * Parser the string parameter, like that fromDistrib=integer:401,toDistrib=integer:440
         * in order to create a map as JasperReport Engine hope it
         * @param parametersString
         * @return
         */
        private Map prepareParameters(String parametersString) {
                Map parameters = new HashMap();
                //CCH20071213-08
                //if (parametersString == null ) {
                if (parametersString == null || ("").equals(parametersString) ||  parametersString.length() < 5 ) {
                        System.out.println("No Report parameters");
                        return parameters;
                }
                //CCH20071213-08
                List<String> parameterList = Arrays.asList(parametersString.split(","));
                for (String parameter : parameterList) {
                        String paramName = parameter.split("=", 2)[0].trim();
                        String paramTypeAndValue = parameter.split("=", 2)[1].trim();
                        String paramTypeString = paramTypeAndValue.split(":", 2)[0].trim();
                        ParamType paramType = ParamType.valueOf(paramTypeString.toUpperCase());
                        String paramValueString = paramTypeAndValue.split(":", 2)[1];
                        switch (paramType) {
                        case BOOLEAN:
                                parameters.put(paramName, valueRepresentsBooleanTrue(paramValueString));
                                break;
                        case STRING:
                                parameters.put(paramName, paramValueString);
                                break;
                        case DOUBLE:
                                parameters.put(paramName, Double.valueOf(paramValueString));
                                break;
                        case INTEGER:
                                parameters.put(paramName, Integer.valueOf(paramValueString));
                                break;
                        //CCH20071237-10
                        case DATE:
                                try{
                                        SimpleDateFormat spfd = new SimpleDateFormat("yyyy-MM-dd");
                                        parameters.put(paramName, spfd.parse(paramValueString));
                                }catch(ParseException e){
                                        System.out.println("The parmeter \"" + paramName + "\" have an invalid date format. You must use this format : YYYY-MM-dd");
                                }
                                break;
                        case TIME:
                                try{
                                        SimpleDateFormat spfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        parameters.put(paramName, new Timestamp(spfd.parse(paramValueString).getTime()));
                                }catch(ParseException e){
                                        System.out.println("The parmeter \"" + paramName + "\" have an invalid date time format. You must use this format : yyyy-MM-dd HH:mm:ss");
                                }
                                break;
                        //CCH20071237-10
                        //CCH20080313-20
                        case LOCALE:
                                String lang = paramValueString.split("_", 2)[0].trim();
                                String country = paramValueString.split("_", 2)[1].trim();
                                if(lang != null){
                                        parameters.put(paramName, new Locale(lang));
                                        if(country != null){
                                                parameters.put(paramName, new Locale(lang, country));
                                        }
                                }
                               
                                break;
                        }
                }
                System.out.println("Report parameters are: " + parameters);
                return parameters;
        }
       
        /**
         * Create a map parameter for JasperReports with an object that come from
         * the xml configuraiton file
         * @param params
         * @return
         */
        //CCH20071227-11
        private Map prepareParameters(Parameters params) {
                Map parameters = new HashMap();
               
                if (params == null || params.getParameter() == null) {
                        System.out.println("No Report parameters");

                        return parameters;
                }
               
                for (int i = 0; i < params.getParameter().size(); i++) {
                       
                        String paramName = params.getParameter().get(i).getName();
                        String paramTypeString = params.getParameter().get(i).getType();
                        ParamType paramType = ParamType.valueOf(paramTypeString.toUpperCase());
                        String paramValueString = params.getParameter().get(i).getValue();
                        switch (paramType) {
                        case BOOLEAN:
                                parameters.put(paramName, valueRepresentsBooleanTrue(paramValueString));
                                break;
                        case STRING:
                                parameters.put(paramName, paramValueString);
                                break;
                        case DOUBLE:
                                parameters.put(paramName, Double.valueOf(paramValueString));
                                break;
                        case INTEGER:
                                parameters.put(paramName, Integer.valueOf(paramValueString));
                                break;
                        //CCH20071237-10
                        case DATE:
                                try{
                                        SimpleDateFormat spfd = new SimpleDateFormat("yyyy-MM-dd");
                                        parameters.put(paramName, spfd.parse(paramValueString));
                                }catch(ParseException e){
                                        System.out.println("The parmeter \"" + paramName + "\" have an invalid date format. You must use this format : YYYY-MM-dd");
                                }
                                break;
                        case TIME:
                                try{
                                        SimpleDateFormat spfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        parameters.put(paramName, new Timestamp(spfd.parse(paramValueString).getTime()));
                                }catch(ParseException e){
                                        System.out.println("The parmeter \"" + paramName + "\" have an invalid date time format. You must use this format : yyyy-MM-dd HH:mm:ss");
                                }
                                break;
                        //CCH20071237-10
                        }
                }
                System.out.println("Report parameters are: " + parameters);
                return parameters;
        }
        //CCH20071227-11
       
        private JasperPrint generateReport(String reportDefinitionFile, DatabaseType dbType,
                        // CCH20071129-02
                        String databaseHostName,
                        // CCH20080108-14
                        String port,
                        String databaseName, String databaseUsername, String databasePassword, Map parameters)
                        throws FileNotFoundException, JRException, SQLException, ClassNotFoundException {
                // parse JasperReport XML file
                InputStream input = new FileInputStream(new File(reportDefinitionFile));
                JasperDesign design = JRXmlLoader.load(input);
                JasperReport report = JasperCompileManager.compileReport(design);
                // connect to db
                Class.forName(dbType.getDriverName());
                // CCH20071129-02
                // String connectionStatement = "jdbc:" + dbType.toString().toLowerCase() + "://localhost/"
                //String connectionStatement = "jdbc:" + dbType.toString().toLowerCase() + "://" + databaseHostName + "/"+ databaseName;
               
                port = port != null ? port : dbType.getDefaultPort();
                String connectionStatement = String.format(dbType.getUrl(), databaseHostName, port, databaseName);
               
                Connection conn = DriverManager.getConnection(connectionStatement, databaseUsername,
                                databasePassword);
                conn.setAutoCommit(false);
                // run report and write output
                JasperPrint print = JasperFillManager.fillReport(report, parameters, conn);
                conn.close();
                return print;
        }

        public void emailReport(Set<String> emailRecipients, String emailSender, String emailSubject,
                        List<String> attachmentFileNames, String content, SmtpServer smtpserver) throws MessagingException {
                Properties props = new Properties();
               
                final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
               
                final String SMTP_HOST_NAME;
                final String SMTP_PORT;
                final String SMTP_LOGIN;
                final String SMTP_PASS;

                if(smtpserver != null){
                        SMTP_HOST_NAME = smtpserver.getHostname();
                        SMTP_PORT = smtpserver.getPort().toString();
                        SMTP_LOGIN = smtpserver.getLogin() ;
                        SMTP_PASS =  smtpserver.getPassword();
                }else{
                        SMTP_HOST_NAME =  "smtp.gmail.com";
                        SMTP_PORT = "465";
                        SMTP_LOGIN = "charly.clairmont@wonderbox.fr";
                        SMTP_PASS =  "tichouk14";
                }
               
                props.put("mail.smtp.host", SMTP_HOST_NAME);
                props.put("mail.debug", "false");
                props.put("mail.smtp.port", SMTP_PORT);
                props.put("mail.smtp.socketFactory.port", SMTP_PORT);
                props.put("mail.smtp.socketFactory.fallback", "false");
               
                Session mailSession;
               

                //mailSession = Session.getDefaultInstance(props);
                mailSession = Session.getInstance(props);
                if(SMTP_LOGIN != null && !"".equals(SMTP_LOGIN) &&
                        SMTP_PASS != null && !"".equals(SMTP_PASS)
                                ) {
                       
                        props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
                        props.put("mail.smtp.auth", "true");
                        mailSession = Session.getDefaultInstance(props,
                                        new javax.mail.Authenticator() {
       
                                        protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                                        return new javax.mail.PasswordAuthentication(SMTP_LOGIN, SMTP_PASS);
                                        }
                                }
                        );
                }
                mailSession.setDebug(false);
               
                MimeMessage message = new MimeMessage(mailSession);
                message.setSubject(emailSubject);
                for (String emailRecipient : emailRecipients) {
                        Address toAddress = new InternetAddress(emailRecipient);
                        message.addRecipient(Message.RecipientType.TO, toAddress);
                }
                Address fromAddress = new InternetAddress(emailSender);
                message.setFrom(fromAddress);
                // Message text
                Multipart multipart = new MimeMultipart();
                BodyPart textBodyPart = new MimeBodyPart();
                //
                textBodyPart.setText(content);
                multipart.addBodyPart(textBodyPart);
                // Attachements
                for (String attachmentFileName : attachmentFileNames) {
                        BodyPart attachmentBodyPart = new MimeBodyPart();
                        DataSource source = new FileDataSource(attachmentFileName);
                        attachmentBodyPart.setDataHandler(new DataHandler(source));
                        String fileNameWithoutPath = attachmentFileName.replaceAll("^.*\\/", "");
                        fileNameWithoutPath = fileNameWithoutPath.replaceAll("^.*\\\\", "");
                        attachmentBodyPart.setFileName(fileNameWithoutPath);
                        multipart.addBodyPart(attachmentBodyPart);
                }
                // add parts to message
                message.setContent(multipart);
                // send via SMTP
                //Transport transport = mailSession.getTransport("smtp");
                //transport.connect("smtp.gmail.com", "charly.clairmont@wonderbox.fr", "tichouk14");
                Transport.send(message, message.getAllRecipients());
                //Transport.close();
        }

        private String builOutputReportPath(String outputPath){
                Calendar today = Calendar.getInstance();
                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
       
                String outputPathDirectory = new String(outputPath);
                if(outputPathDirectory.lastIndexOf(File.separator) == outputPathDirectory.length()-1)
                        outputPathDirectory = outputPathDirectory + dateFormatter.format(today.getTime())+ File.separator ;
                else
                        outputPathDirectory = outputPathDirectory + File.separator + dateFormatter.format(today.getTime())+ File.separator ;
               
                try {
                        File outputDirectory = new File (outputPathDirectory);
                       
                        if(!outputDirectory.isDirectory())
                        // check if the directory is already created
                                if(outputDirectory.mkdir()){
                                        System.out.println("Directory: " + outputPathDirectory + " created");
                                }else{
                                        System.out.println("Directory: " + outputPathDirectory + " not created a problem occurs");
                                        return null;
                                }
                        return outputPathDirectory;
                       
                }catch(Exception e){
                        System.out.println("Directory: " + outputPathDirectory + " not created a problem occurs");
                        return null;
                }
        }
       
        private String buildOutputReportName(String outputName){
                Calendar today = Calendar.getInstance();
                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
       
                return outputName + "_" + dateFormatter.format(today.getTime());
                 
        }
       
        public static void main(String[] args) throws Exception {
                RunJasperReports runJasperReports = new RunJasperReports();
                // Set up command line parser
                Options options = new Options();
               
                //CCH20071237-11
                Option xmlconfig = OptionBuilder.withArgName("xmlconfig").hasArg().withDescription(
                "XML Configuration File").create("xmlconfig");
                options.addOption(xmlconfig);
                //CCH20071237-11
               
                Option reports = OptionBuilder.withArgName("reportlist").hasArg().withDescription(
                                "Comma separated list of JasperReport XML input files").create("reports");
                options.addOption(reports);
                Option emailTo = OptionBuilder.withArgName("emailaddress").hasArg().withDescription(
                                "Email address to send generated reports to").create("emailto");
                options.addOption(emailTo);
                Option emailFrom = OptionBuilder.withArgName("emailaddress").hasArg().withDescription(
                                "Sender email address").create("emailfrom");
                options.addOption(emailFrom);
                Option emailSubjectLine = OptionBuilder.withArgName("emailsubject").hasArg()
                                .withDescription("Subject line of email").create("subject");
                options.addOption(emailSubjectLine);
                Option outputFolder = OptionBuilder
                                .withArgName("foldername")
                                .hasArg()
                                .withDescription(
                                                "Folder to write generated reports to, with trailing separator (slash or backslash)")
                                .create("folder");
                options.addOption(outputFolder);
                Option dbTypeOption = OptionBuilder.withArgName("databasetype").hasArg().withDescription(
                                "Currently 'postgresql' and 'mysql' are supported").create("dbtype");
                options.addOption(dbTypeOption);
               
                Option dbNameOption = OptionBuilder.withArgName("databasename").hasArg().withDescription(
                "Name of the database to run reports against").create("dbname");
                options.addOption(dbNameOption);
               
                // CCH20071129-02
                Option dbHostNameOption = OptionBuilder.withArgName("hostname").hasArg().withDescription(
                                "Name or ip address of the host of the database to run reports against").create("hostname");
                options.addOption(dbHostNameOption);
                // CCH20071129-02
               
                // CCH20080108-14
                Option dbPortOption = OptionBuilder.withArgName("dbport").hasArg().withDescription(
                                "Port of the database instance to run reports against").create("dbport");
                options.addOption(dbPortOption);
                // CCH20080108-14
               
                Option dbUserOption = OptionBuilder.withArgName("username").hasArg().withDescription(
                                "Username to connect to databasewith").create("dbuser");
                options.addOption(dbUserOption);
                Option dbPassOption = OptionBuilder.withArgName("password").hasArg().withDescription(
                                "Database password").create("dbpass");
                options.addOption(dbPassOption);
                Option outputTypeOption = OptionBuilder.withArgName("outputtype").hasArg().withDescription(
                                "Output type, 'pdf' or 'html'").create("output");
                options.addOption(outputTypeOption);
               
                //CCH20071213-08
                /*Option paramsOption = OptionBuilder.withArgName("parameters").hasArg().withDescription(
                                "Parameters, e.g. param1=boolean:true,param2=string:ABC|param3=double:134.2,param4=integer:85"+
                                "\n the pipe \"|\" is to separate report parameters")
                                .create("params");*/
                Option paramsOption = OptionBuilder.withArgName("parameters").hasArg().withDescription(
                                "Parameters, e.g. param1=boolean:true,param2=string:ABC|param3=double:134.2,param4=integer:85"+
                                "\n the pipe \"|\" is to separate report parameters")
                                .create("params");
                //CCH20071213-08
                options.addOption(paramsOption);
               
               
                //CCH20071213-07
                Option fileOutputNameOption = OptionBuilder.withArgName("reportname").hasArg().withDescription(
                "Name of each output report").create("reportname");
                options.addOption(fileOutputNameOption);
                //CCH20071213-07
               
               
               
                // Parse command line
                CommandLineParser parser = new GnuParser();
                CommandLine commandLine = parser.parse(options, args);
               
                //CCH20071237-11
               
               
                String outputPath = null;
                List<String> reportDefinitionFileNames = new ArrayList<String>();
                List<String> outputFileNames = new ArrayList<String>();
                DatabaseType databaseType = null;
                String databaseHostName = null;
                String databasePort = null;
                List<String> reportFileNames = new ArrayList<String>();
                String databaseName = null;
                String databaseUsername = null;
                String databasePassword = null;
                String allReportOutputTypes[] = null;
                String parametersString = null;
                String allReportParameters[] = null;
                String emailRecipientList = null;
                String emailSender = null;
                String emailSubject = null;
               
                SmtpServer smtpSetting = null;
               
                // message in the mail
                StringBuffer MESSAGE_CONTENT_MAIL = new StringBuffer(
                "Bonjour,\n\nDate : %1$te %1$tm %1$tY \n\n Liste de(s) rapport(s) joint(s) :\n") ;
               
                String reportParameters;
                List<Parameters> xmlReportParameters = new ArrayList<Parameters>();
                Map parameters;
                int reportCount = 0;
               
                OutputType outputType;
               
                String xmlConfigFilePath = commandLine.getOptionValue("xmlconfig");
               
                if(xmlConfigFilePath != null && !("").equals(xmlConfigFilePath)){
                       
                        ReportConfig config = new ReportConfig(xmlConfigFilePath);
                        JobsReports jobConfig = new JobsReports();
                        config.readConfig();
                       
                        jobConfig = config.getJobReportConf();
                       
                        outputPath = jobConfig.getOutputFolder();
                       
                        MESSAGE_CONTENT_MAIL = new StringBuffer(
                                        jobConfig.getEmailTemplate().getTemplate());
                       
                        if(jobConfig.getSources() == null ||
                                        jobConfig.getSources().getSource() == null){
                                throw new IllegalArgumentException(
                                                "No source specified in the xml configuration file \n"
                                                + xmlConfigFilePath + ":\n" + config.toString());
                        }
                        databaseType = DatabaseType.valueOf(jobConfig.getSources().getSource().get(0).getDbprovider().toUpperCase());
                        databaseHostName = jobConfig.getSources().getSource().get(0).getDbhostname();
                        //CCH20080108-14
                        databasePort = jobConfig.getSources().getSource().get(0).getDbPort();
                        databaseName = jobConfig.getSources().getSource().get(0).getDbname();
                        databaseUsername = jobConfig.getSources().getSource().get(0).getDbuser();
                        databasePassword = jobConfig.getSources().getSource().get(0).getDbpassword();
                       
                        if(jobConfig.getReports() == null ||
                                        jobConfig.getReports().getReport() == null){
                                throw new IllegalArgumentException(
                                                "No report specified in the xml configuration file \n"
                                                + xmlConfigFilePath + ":\n" + config.toString());
                        }
                        reportDefinitionFileNames = new ArrayList<String>();
                        reportFileNames = new ArrayList<String>();
                       
                        allReportOutputTypes = null;
                        ArrayList<String> reportOutputTypes = new ArrayList<String>();
                       
                        parametersString = null;
                        allReportParameters = null;
                       
                        for(int i=0; i < jobConfig.getReports().getReport().size(); i++){
                               
                                reportDefinitionFileNames.add(jobConfig.getReports().getReport().get(i).getJrxmlFile());
                               
                                reportFileNames.add(jobConfig.getReports().getReport().get(i).getOutputName());
                               
                                reportOutputTypes.add(jobConfig.getReports().getReport().get(i).getOutputFormat());
                               
                                //TODO support different source
                               
                                xmlReportParameters.add(jobConfig.getReports().getReport().get(i).getParameters());
                               
                        }
                       
                        allReportOutputTypes = new String[reportOutputTypes.size()];
                        allReportOutputTypes = reportOutputTypes.toArray(allReportOutputTypes);
                       
                        emailRecipientList = jobConfig.getEmailToList();
                        emailSender =  jobConfig.getEmailFrom();
                        emailSubject = jobConfig.getEmailSubject();
                       
                        smtpSetting = jobConfig.getMailSmtp();
                       
               
                }else{
                        String reportsDefinitionFileNamesCvs = null;
                        reportsDefinitionFileNamesCvs = commandLine.getOptionValue("reports");
                        if (reportsDefinitionFileNamesCvs == null) {
                                HelpFormatter formatter = new HelpFormatter();
                                formatter.printHelp("java -jar RunJasperReports.jar", options);
                                System.out.println();
                                throw new IllegalArgumentException("No reports specified");
                        }
                        outputPath = commandLine.getOptionValue("folder");
                        reportDefinitionFileNames = Arrays.asList(reportsDefinitionFileNamesCvs
                                        .split(","));
                        outputFileNames = new ArrayList<String>();
                        databaseType = DatabaseType.valueOf(commandLine.getOptionValue("dbtype").toUpperCase());
                       
                        // CCH20071129-02
                        databaseHostName = commandLine.getOptionValue("hostname");

                        if(databaseHostName == null || "".equals(databaseHostName) )
                                databaseHostName = "localhost";
                        // CCH20071129-02
                       
                        //CCH20080108-14
                        databasePort = commandLine.getOptionValue("dbport");
                        //CCH20080108-14
                       
                        //CCH20071213-07
                        reportFileNames = Arrays.asList(commandLine.getOptionValue("reportname").split(","));
                        //CCH20071213-07
                                       
                        databaseName = commandLine.getOptionValue("dbname");
                        databaseUsername = commandLine.getOptionValue("dbuser");
                        databasePassword = commandLine.getOptionValue("dbpass");
                       
                        //CCH20071213-09
                        /*OutputType outputType = OutputType.valueOf(commandLine.getOptionValue("output")
                                        .toUpperCase());*/
                        allReportOutputTypes = commandLine.getOptionValue("output").split(",");
                        //CCH20071213-09
                       
                        parametersString = commandLine.getOptionValue("params");
                        //CCH20071213-08
                        //Map parameters = runJasperReports.prepareParameters(parametersString);
                        allReportParameters = parametersString.split("\\|");
                        //CCH20071213-08
                       
                        emailRecipientList = commandLine.getOptionValue("emailto");
                        emailSender = commandLine.getOptionValue("emailfrom");
                        emailSubject = commandLine.getOptionValue("subject");
                }
                //CCH20071237-11
               
               
                outputPath = runJasperReports.builOutputReportPath(outputPath);
               
                // Iterate over reports, generating output for each
                System.out.println("RunJasperRereport go to generate " + reportDefinitionFileNames.size() + " reports ");
                for (String reportsDefinitionFileName : reportDefinitionFileNames) {
                       
                        System.out.println("Generating report for template: " + reportsDefinitionFileName);
                        //CCH20071213-08
                       
                        parameters = null;
                        reportParameters = null;
                       
                        //CCH20071237-11
                        if(xmlConfigFilePath != null && !("").equals(xmlConfigFilePath)){
                                if(xmlReportParameters.get(reportCount) != null )
                                        parameters = runJasperReports.prepareParameters(xmlReportParameters.get(reportCount));
                        }else if(allReportParameters.length > reportCount){
                                reportParameters = allReportParameters[reportCount];
                                if(reportParameters != null )
                                        parameters = runJasperReports.prepareParameters(reportParameters);
                        }
                        //CCH20071213-08
                               
                        //String outputFileName = reportsDefinitionFileName.replaceAll("\\..*$", "");
                        //outputFileName = outputFileName.replaceAll("^.*\\/", "");
                        /*String outputFileName = reportsDefinitionFileName.substring(
                                        reportsDefinitionFileName.lastIndexOf(File.separator)+1,
                                        reportsDefinitionFileName.lastIndexOf(".")
                                        );
                        */
                        String outputFileName = runJasperReports.buildOutputReportName(reportFileNames.get(reportCount));
                        MESSAGE_CONTENT_MAIL.append("\n  * " + outputFileName);
                       
                        //CCH20071213-09
                        outputType = OutputType.valueOf(allReportOutputTypes[reportCount].toUpperCase());
                        //CCH20071213-09
                       
                        if (outputType.equals(OutputType.PDF)) {
                               
                                //outputFileName = outputFileName.replaceAll("\\W", "").toLowerCase() + "."
                                //+ outputType.toString().toLowerCase();
                               
                                outputFileName = outputFileName.toLowerCase() + "."    
                                + outputType.toString().toLowerCase();
                               
                                if (outputPath != null) {
                                        outputFileName = outputPath + outputFileName;
                                }
                                System.out.println("Generating report " + outputFileName);
                                runJasperReports.generatePdfReport(reportsDefinitionFileName, outputFileName,
                                                databaseType,
                                                // CCH20071129-02
                                                databaseHostName,
                                                // CCH20080108-14
                                                databasePort,
                                                databaseName, databaseUsername, databasePassword, parameters);
                        // CCH20071129-01
                        } else if(outputType.equals(OutputType.JXLS)) {
                                //outputFileName = outputFileName.replaceAll("\\W", "").toLowerCase() + ".xls";
                                outputFileName = outputFileName.toLowerCase() + ".xls";
                               
                                if (outputPath != null) {
                                        outputFileName = outputPath + outputFileName;
                                }
                                System.out.println("Generating report " + outputFileName);
                                runJasperReports.generateJxlsReport(reportsDefinitionFileName, outputFileName,
                                                databaseType,
                                                // CCH20071129-02
                                                databaseHostName,
                                                // CCH20080108-14
                                                databasePort,
                                                databaseName, databaseUsername, databasePassword, parameters);
                        // CCH20071129-01
                        // CCH20071208-06
                        } else if(outputType.equals(OutputType.JPG)) {
                                outputFileName = outputFileName.toLowerCase() + "."    
                                + outputType.toString().toLowerCase();
                               
                                if (outputPath != null) {
                                        outputFileName = outputPath + outputFileName;
                                }
                                System.out.println("Generating report " + outputFileName);
                                runJasperReports.generateJpgReport(reportsDefinitionFileName, outputFileName,
                                                databaseType,
                                                // CCH20071129-02
                                                databaseHostName,
                                                // CCH20080108-14
                                                databasePort,
                                                databaseName, databaseUsername, databasePassword, parameters);
                        // CCH20071208-06
                        }else {
                               
                                //outputFileName = outputFileName.replaceAll("\\W", "").toLowerCase() + "."
                                //+ outputType.toString().toLowerCase();
                               
                                outputFileName = outputFileName.toLowerCase() + "."
                                + outputType.toString().toLowerCase();
                               
                                if (outputPath != null) {
                                        outputFileName = outputPath + outputFileName;
                                }
                                System.out.println("Generating report " + outputFileName);
                                runJasperReports.generateHtmlReport(reportsDefinitionFileName, outputFileName,
                                                databaseType,
                                                // CCH20071129-02
                                                databaseHostName,
                                                // CCH20080108-14
                                                databasePort,
                                                databaseName, databaseUsername, databasePassword, parameters);
                        }
                        outputFileNames.add(outputFileName);
                       
                        reportCount++;
                }// end For
               
                // send the mail with all file
                if (emailRecipientList != null) {
                        Set<String> emailRecipients = new HashSet<String>(Arrays.asList(emailRecipientList
                                        .split(",")));
                        System.out.println("Emailing reports to " + emailRecipients);
                        runJasperReports.emailReport(emailRecipients, emailSender, String.format(emailSubject, Calendar.getInstance().getTime()),
                                        outputFileNames, String.format(MESSAGE_CONTENT_MAIL.toString(), Calendar.getInstance().getTime()), smtpSetting);
                }
       
        }

}
