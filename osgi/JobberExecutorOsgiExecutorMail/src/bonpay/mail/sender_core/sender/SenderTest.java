package bonpay.mail.sender_core.sender;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Random;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import bonpay.mail.sender_core.letter.Letter;
import bonpay.mail.sender_core.letter.LetterAttachment;
import bonpay.mail.sender_core.letter.Translit;

/** Тестирование отправки HTML содержимого  */
public class SenderTest {
	public static void main(String[] args){
		System.out.println("begin");
		try{
			new SenderTest();
		}catch(Exception ex){
			System.err.println("Exception: "+ex.getMessage());
		}
		
		System.out.println("end");
	}

	private Logger logger=Logger.getLogger(this.getClass());
	
	static {
		BasicConfigurator.configure();
	}
	
	private Session getTransport() throws Exception {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", "smtp.mail.ru");
        props.setProperty("mail.smtp.port", "2525");
        props.setProperty("mail.smtp.auth", "true");
        
        final String userLogin="technik7_job";
        final String password="sokol7fenics";
        props.setProperty("mail.user", userLogin);
        props.setProperty("mail.password", password);

        Authenticator auth = new Authenticator(){
        	@Override
        	protected PasswordAuthentication getPasswordAuthentication() {
        		return new PasswordAuthentication(userLogin, password);
        	}
        };
        Session mailSession = Session.getDefaultInstance(props, auth);
        mailSession.setDebug(false);
        // mailSession.setPasswordAuthentication(new URLName("smtp.mail.ru"), new PasswordAuthentication(userLogin, password));
        return mailSession;
	}
	
	/** Тестирование отправки HTML содержимого  */
	public SenderTest() throws Exception {
		System.out.println("Sending mail...");
		Session mailSession=getTransport();
		Transport transport=mailSession.getTransport();
		String from="technik7_job@mail.ru";
		
        MimeMessage message = new MimeMessage(mailSession);
        message.setSubject("HTML  mail with images");
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO,new InternetAddress("technik7@mail.ru"));
        /*
        //
        // This HTML mail have to 2 part, the BODY and the embedded image
        //
        MimeMultipart multipart = new MimeMultipart("related");

        // first part  (the html)
        BodyPart messageBodyPart = new MimeBodyPart();
        String htmlText = "<table border=\"1\"> <tr><td><H1>Hello</H1></td><td><img src=\"cid:image@mail.ru\"></td></tr></table>";
        messageBodyPart.setContent(htmlText, "text/html");

        // add it
        multipart.addBodyPart(messageBodyPart);
        
        // second part (the image)
        messageBodyPart = new MimeBodyPart();
        DataSource fds = new FileDataSource("C:\\webhp.png");
        messageBodyPart.setDataHandler(new DataHandler(fds));
        messageBodyPart.setHeader("Content-ID","<image@mail.ru>");

        // add it
        multipart.addBodyPart(messageBodyPart);
*/
        // put everything together
        message.setContent(this.getLetterContent(createLetter("D:\\eclipse_workspace\\JobberExecutorOsgiExecutorMail\\test_letter\\letter\\"), from));

        transport.connect();
        transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
        transport.close();
	}
	

	private Letter createLetter(String directory) throws Exception{
		Letter returnValue=new Letter();
		File file=new File(directory);
		if(file.isDirectory()){
			File[] files=file.listFiles();
			for(int counter=0;counter<files.length;counter++){
				try{
					if(files[counter].getName().endsWith(".html")){
						// html file
						LetterAttachment attachment=new LetterAttachment(files[counter].getName(), files[counter].getAbsolutePath());
						returnValue.setAttachmentHtmlText(attachment);
						returnValue.setHtml(true);
					}else{
						// attachment file 
						LetterAttachment attachment=new LetterAttachment(files[counter].getName(), files[counter].getAbsolutePath());
						returnValue.addAttachmentFile(attachment);
					}
				}catch(Exception ex){
					logger.error("createLetter");
				}
			}
		}else{
			// simple Text letter
			returnValue.setText("this is simple Text for send letter ");
		}
		return returnValue;
	}
	
	
// ------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Попытка создать 
	 * 	Alternative
	 * 		Plain text
	 * 		attachment-file(Content-type: related;)
	 * 		attachment-html-text(Content-type: related;)
	 */
	private Multipart getLetterContent(Letter letter, String from){
		// выбор ветки составления письма - либо же HTML либо же просто текст и/или файлы
		if((letter.getAttachmentHtmlText()!=null)&&(letter.getAttachmentHtmlText().getPathToFile()!=null)){
			// HTML письмо 
			MimeMultipart multipart=new MimeMultipart("alternative"); // new MimeMultipart("related");// new MimeMultipart("alternative");// new MimeMultipart("mixed");
			try{
				MimeBodyPart textPart=new MimeBodyPart();
				textPart.setText("This is HTML content");
				multipart.addBodyPart(textPart);
			}catch(Exception ex){
				logger.error("#getLetterContent is HTML Text, add the part of text:"+ex.getMessage());
			}
			// MimeMultipart returnValue=new MimeMultipart("related"); // new MimeMultipart("related");// new MimeMultipart("alternative");// new MimeMultipart("mixed");
			HashMap<String, String> replaceNames=new HashMap<String, String>();

			ArrayList<MimeBodyPart> listOfParts=new ArrayList<MimeBodyPart>();
			
			// INFO letter add attachment 
			if(letter.getAttachmentSize()!=0){
				logger.error("добавить файлы в письмо "); 
				for(int counter=0;counter<letter.getAttachmentSize();counter++){
					LetterAttachment attachment=letter.getAttachment(counter);
					logger.debug(counter+" : "+attachment.getOriginalFileName());
					try{
						File file=new File(attachment.getPathToFile());
			            MimeBodyPart filePart=new MimeBodyPart();
			            filePart.addHeader("Content-Type", "multipart/related");
			            if(file.exists()==true){
				            DataSource fileDataSource=new FileDataSource(file);
				            filePart.setDataHandler(new DataHandler(fileDataSource));
				            filePart.setFileName(replaceRussianLetters(attachment.getOriginalFileName()));
				            // filePart.setHeader("MIME-Version", "1.0");
				            // filePart.setHeader("Content-Type", "image/jpeg; name=\""+file.getName()+"\"");
				            listOfParts.add(filePart);
				            // returnValue.addBodyPart(filePart);
				            String contentFileName=replaceRussianLetters(attachment.getOriginalFileName())+this.getRandomString(4); // +"@"+getDomain(from);
				            filePart.setHeader("Content-ID", "<"+contentFileName+">");
				            replaceNames.put(attachment.getOriginalFileName(), contentFileName);
			            }else{
			            	logger.error("add attachment to file, File is not Exists: ");
			            }
					}catch(Exception ex){
						logger.error("add Attachment part to letter Exception: "+ex.getMessage(),ex);
					}
				}
			}else{
				logger.error("нет прикрепленных файлов"); 
			}
			
			// INFO letter set Text
			if((letter.getAttachmentHtmlText()!=null)&&(letter.getAttachmentHtmlText().getPathToFile()!=null)){
				logger.info(" содержимое письма HTML файл ");
				try{
					// прочесть файл и полученный текст обработать
					// MimeBodyPart textPart=new MimeBodyPart();
					// textPart.setText("This is HTML content");
					// textPart.addHeader("Content-Type", "text/plain; charset=UTF-8; format=flowed");
					// returnValue.addBodyPart(textPart);

					MimeBodyPart htmlPart=new MimeBodyPart();
					// прочесть файл и полученный текст обработать
					// logger.debug("------------------------");
					// logger.debug(readTextFromFile(letter.getAttachmentHtmlText().getPathToFile(),"UTF-8"));
					// logger.debug("------------------------");
					htmlPart.setText(replaceAllValues(readTextFromFile(letter.getAttachmentHtmlText().getPathToFile(),
																	   "UTF-8"),
													  replaceNames));
					// htmlPart.setContent(replaceAllValues(readTextFromFile(letter.getAttachmentHtmlText().getPathToFile(),"UTF-8"),replaceNames), "text/html");
					// htmlPart.addHeader("Content-Type", "multipart/related");
					htmlPart.addHeader("Content-Type", "text/html"); // ;charset=UTF-8
					multipart.addBodyPart(htmlPart);
				}catch(Exception ex){
					logger.error("get path to Html Exception:"+ex.getMessage());
				}
			}else{
				logger.debug("возможно текстовое письмо"); 
				if(letter.getText()!=null){
					logger.info(" содержимое письма - текст ");
					MimeBodyPart textPart=new MimeBodyPart();
					try{
						if(letter.isHtml()==true){
							logger.debug("HTML текст");
							// textPart.setText(letter.getText(),"text/html");
							textPart.setText(replaceAllValues(letter.getText(),replaceNames));
							// textPart.setHeader("MIME-Version", "1.0");
							textPart.addHeader("Content-Type", "text/html; charset=UTF-8");
							textPart.addHeader("Content-Type", "multipart/related");
							// textPart.setHeader("Content-Type", textPart.getContentType());
						}else{
							logger.debug("Plain текст");
							textPart.addHeader("Content-Type", "text/plain; charset=UTF-8");
							textPart.addHeader("Content-Type", "multipart/related");
							textPart.setText(letter.getText());
						}
						multipart.addBodyPart(textPart);
					}catch(Exception ex){
						logger.error("add text part to letter Exception: "+ex.getMessage(), ex);
					}
				}else{
					logger.info("письмо не имеет текста");
				}
			}
			// добавить все возможные варианты вложений после текста 
			for(int counter=0;counter<listOfParts.size();counter++){
				try{
					multipart.addBodyPart(listOfParts.get(counter));
				}catch(Exception ex){
					logger.error("add attachment to Letter Body Exception:"+ex.getMessage());
				}
			}
			return multipart;
		}else{
			// не HTML письмо 
			MimeMultipart returnValue=new MimeMultipart("mixed"); // new MimeMultipart("related");// new MimeMultipart("alternative");// new MimeMultipart("mixed");
			ArrayList<MimeBodyPart> listOfParts=new ArrayList<MimeBodyPart>();
			
			// INFO letter set Text
			if(letter.getText()!=null){
				logger.info(" содержимое письма - текст ");
				MimeBodyPart textPart=new MimeBodyPart();
				try{
					if(letter.isHtml()==true){
						logger.debug("HTML text");
						// textPart.setText(letter.getText(),"text/html");
						textPart.setText(letter.getText());
						// textPart.setHeader("MIME-Version", "1.0");
						textPart.setHeader("Content-Type", "text/html; charset=UTF-8");
						// textPart.setHeader("Content-Type", textPart.getContentType());
					}else{
						logger.debug("Plain текст");
						textPart.setHeader("Content-Type", "text/plain; charset=UTF-8");
						textPart.setText(letter.getText());
					}
					returnValue.addBodyPart(textPart);
				}catch(Exception ex){
					logger.error("add text part to letter Exception: "+ex.getMessage(), ex);
				}
			}else{
				logger.info("письмо не имеет текста");
			}
			// INFO letter add attachment 
			if(letter.getAttachmentSize()!=0){
				logger.error("добавить файлы в письмо "); 
				for(int counter=0;counter<letter.getAttachmentSize();counter++){
					LetterAttachment attachment=letter.getAttachment(counter);
					logger.debug(counter+" : "+attachment.getOriginalFileName());
					try{
						File file=new File(attachment.getPathToFile());
			            MimeBodyPart filePart=new MimeBodyPart();
			            if(file.exists()==true){
				            DataSource fileDataSource=new FileDataSource(file);
				            filePart.setDataHandler(new DataHandler(fileDataSource));
				            filePart.setFileName(replaceRussianLetters(attachment.getOriginalFileName()));
				            listOfParts.add(filePart);
			            }else{
			            	logger.error("add attachment to file, File is not Exists: ");
			            }
					}catch(Exception ex){
						logger.error("add Attachment part to letter Exception: "+ex.getMessage(),ex);
					}
				}
			}else{
				logger.error("нет прикрепленных файлов"); 
			}
			// добавить все возможные варианты вложений после текста 
			for(int counter=0;counter<listOfParts.size();counter++){
				try{
					returnValue.addBodyPart(listOfParts.get(counter));
				}catch(Exception ex){
					logger.error("add attachment to Letter Body Exception:"+ex.getMessage());
				}
			}
			return returnValue;
		}
	}

// ------------------------------------------------------------------------------------------------------------------------------------------------
	
	/** прочесть из файла весь текст и вернуть как строку  */
	private String readTextFromFile(String pathToFile, String encoding){
		File file=new File(pathToFile);
		BufferedReader reader=null;
		StringBuffer returnValue=new StringBuffer();
		try{
			if(encoding!=null){
				reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),encoding));
			}else{
				reader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			}
			String currentString=null;
			while( (currentString=reader.readLine())!=null ){
				System.out.println(currentString);
				returnValue.append(currentString);
			}
		}catch(Exception ex){
			System.err.println("readTextFromFile Exception:"+ex.getMessage());
		}finally{
			try{
				reader.close();
			}catch(Exception ex){};
		}
		return returnValue.toString();
	}
	
	
	/** заменить в указанном тексте все вхождения имен файлов на их синонимы, и прибавить "cid"
	 * @param value - полный текст письма
	 * @param replaceNames - пары [оригинальное имя]=[значения]
	 * @return полный текст письма 
	 */
	private String replaceAllValues(String value, HashMap<String,String> replaceNames){
		Iterator<String> keys=replaceNames.keySet().iterator();
		while(keys.hasNext()){
			String currentValue=keys.next();
			String replaceValue=replaceNames.get(currentValue);
			// System.out.println("Replace All: "+currentValue+"   >>> "+replaceValue);
			// INFO возможно, нужно будет добавить следующее: "src=\""+currentValue, "src=\"cid:"+replaceValue 
			value=value.replaceAll("src=\""+currentValue, "src=\"cid:"+replaceValue);
			value=value.replaceAll("SRC=\""+currentValue, "SRC=\"cid:"+replaceValue);
		}
		return value;
	}
	
	
	/** замена русских букв на английские аналоги  */
	private String replaceRussianLetters(String russianFileNames){
		return Translit.toTranslit(russianFileNames);
	}


	// получить имя домена на основании отправителя
	@SuppressWarnings("unused")
	private String getDomain(String value){
		int index=value.indexOf("@");
		if(index>0){
			return value.substring(index+1);
		}else{
			return value;
		}
	}

	
	private Random random=new Random();
	private String getRandomString(int count){
		StringBuffer returnValue=new StringBuffer();
		for(int counter=0;counter<count;counter++){
			returnValue.append(Integer.toHexString(random.nextInt(16)) );
		}
		return returnValue.toString();
	}
}


/**

	private Multipart getLetterContent(Letter letter, String from){
		MimeMultipart returnValue=new MimeMultipart("related"); // new MimeMultipart("related");// new MimeMultipart("alternative");// new MimeMultipart("mixed");
		HashMap<String, String> replaceNames=new HashMap<String, String>();

		ArrayList<MimeBodyPart> listOfParts=new ArrayList<MimeBodyPart>();
		
		// INFO letter add attachment 
		if(letter.getAttachmentSize()!=0){
			logger.error("добавить файлы в письмо "); 
			for(int counter=0;counter<letter.getAttachmentSize();counter++){
				LetterAttachment attachment=letter.getAttachment(counter);
				logger.debug(counter+" : "+attachment.getOriginalFileName());
				try{
					File file=new File(attachment.getPathToFile());
		            MimeBodyPart filePart=new MimeBodyPart();
		            filePart.addHeader("Content-Type", "multipart/related");
		            if(file.exists()==true){
			            DataSource fileDataSource=new FileDataSource(file);
			            filePart.setDataHandler(new DataHandler(fileDataSource));
			            filePart.setFileName(replaceRussianLetters(attachment.getOriginalFileName()));
			            // filePart.setHeader("MIME-Version", "1.0");
			            // filePart.setHeader("Content-Type", "image/jpeg; name=\""+file.getName()+"\"");
			            listOfParts.add(filePart);
			            // returnValue.addBodyPart(filePart);
			            String contentFileName=replaceRussianLetters(attachment.getOriginalFileName())+this.getRandomString(4); // +"@"+getDomain(from);
			            filePart.setHeader("Content-ID", "<"+contentFileName+">");
			            replaceNames.put(attachment.getOriginalFileName(), contentFileName);
		            }else{
		            	logger.error("add attachment to file, File is not Exists: ");
		            }
				}catch(Exception ex){
					logger.error("add Attachment part to letter Exception: "+ex.getMessage(),ex);
				}
			}
		}else{
			logger.error("нет прикрепленных файлов"); 
		}
		
		// INFO letter set Text
		if((letter.getAttachmentHtmlText()!=null)&&(letter.getAttachmentHtmlText().getPathToFile()!=null)){
			logger.info(" содержимое письма HTML файл ");
			try{
				// прочесть файл и полученный текст обработать
				// MimeBodyPart textPart=new MimeBodyPart();
				// textPart.setText("This is HTML content");
				// textPart.addHeader("Content-Type", "text/plain; charset=UTF-8; format=flowed");
				// returnValue.addBodyPart(textPart);

				MimeBodyPart htmlPart=new MimeBodyPart();
				// прочесть файл и полученный текст обработать
				// logger.debug("------------------------");
				// logger.debug(readTextFromFile(letter.getAttachmentHtmlText().getPathToFile(),"UTF-8"));
				// logger.debug("------------------------");
				htmlPart.setText(replaceAllValues(readTextFromFile(letter.getAttachmentHtmlText().getPathToFile(),
																   "UTF-8"),
												  replaceNames));
				// htmlPart.setContent(replaceAllValues(readTextFromFile(letter.getAttachmentHtmlText().getPathToFile(),"UTF-8"),replaceNames), "text/html");
				// htmlPart.addHeader("Content-Type", "multipart/related");
				htmlPart.addHeader("Content-Type", "text/html"); // ;charset=UTF-8
				returnValue.addBodyPart(htmlPart);
				
			}catch(Exception ex){
				logger.error("get path to Html Exception:"+ex.getMessage());
			}
		}else{
			logger.debug("возможно текстовое письмо"); 
			if(letter.getText()!=null){
				logger.info(" содержимое письма - текст ");
				MimeBodyPart textPart=new MimeBodyPart();
				try{
					if(letter.isHtml()==true){
						logger.debug("HTML текст");
						// textPart.setText(letter.getText(),"text/html");
						textPart.setText(replaceAllValues(letter.getText(),replaceNames));
						// textPart.setHeader("MIME-Version", "1.0");
						textPart.setHeader("Content-Type", "text/html; charset=WIN-1251");
						// textPart.setHeader("Content-Type", textPart.getContentType());
					}else{
						logger.debug("Plain текст");
						textPart.setHeader("Content-Type", "text/plain; charset=UTF-8");
						textPart.setText(letter.getText());
					}
					returnValue.addBodyPart(textPart);
				}catch(Exception ex){
					logger.error("add text part to letter Exception: "+ex.getMessage(), ex);
				}
			}else{
				logger.info("письмо не имеет текста");
			}
		}
		// добавить все возможные варианты вложений после текста 
		for(int counter=0;counter<listOfParts.size();counter++){
			try{
				returnValue.addBodyPart(listOfParts.get(counter));
			}catch(Exception ex){
				logger.error("add attachment to Letter Body Exception:"+ex.getMessage());
			}
			
		}
		return returnValue;
	}

*/