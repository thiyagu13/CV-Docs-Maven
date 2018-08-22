package com.eDocs.email;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class SendMail

{
	@Parameters({"EmailReport"}) // pass name & value to the parameter in testNG.xml file
	@Test
	public static void execute(String reportFileName) throws Exception
	{
		String path = "C:\\Users\\Easy solutions\\git\\CV-Docs\\eResidue_CV_eDocs\\test-output\\customized-emailable-report.html";

		String[] to = { "thiyagu@easysolutions.in" };
		String[] cc = { "thiyagu@easysolutions.in" };
		String[] bcc = { "thiyagu@easysolutions.in" };

		SendMail.sendMail("thiyagu@easysolutions.in", "thiyagu13",
				"smtp.gmail.com", "465", "true", "true", true,
				"javax.net.ssl.SSLSocketFactory", "false", to, cc, bcc,
				"Selenium TestCase Report for eDocs",
				"Please find the selenium test case status", path,
				reportFileName);
	}

	public static boolean sendMail(String userName, String passWord,
			String host, String port, String starttls, String auth,
			boolean debug, String socketFactoryClass, String fallback,
			String[] to, String[] cc, String[] bcc, String subject,
			String text, String attachmentPath, String attachmentName) {

		// Object Instantiation of a properties file.
		Properties props = new Properties();

		props.put("mail.smtp.user", userName);

		props.put("mail.smtp.host", host);

		if (!"".equals(port)) {
			props.put("mail.smtp.port", port);
		}

		if (!"".equals(starttls)) {
			props.put("mail.smtp.starttls.enable", starttls);
			props.put("mail.smtp.auth", auth);
		}

		if (debug) {

			props.put("mail.smtp.debug", "true");

		} else {

			props.put("mail.smtp.debug", "false");

		}

		if (!"".equals(port)) {
			props.put("mail.smtp.socketFactory.port", port);
		}
		if (!"".equals(socketFactoryClass)) {
			props.put("mail.smtp.socketFactory.class", socketFactoryClass);
		}
		if (!"".equals(fallback)) {
			props.put("mail.smtp.socketFactory.fallback", fallback);
		}

		try {

			Session session = Session.getDefaultInstance(props, null);

			session.setDebug(debug);

			MimeMessage msg = new MimeMessage(session);

			
			// Set Subject: header field
			msg.setSubject(subject);
			
			//Set Email body
			msg.setText(text);
			
			Multipart multipart = new MimeMultipart();
			// Create the message part
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			
			DataSource source = new FileDataSource(attachmentPath);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(attachmentName);
			multipart.addBodyPart(messageBodyPart);

			msg.setContent(multipart);
			msg.setFrom(new InternetAddress(userName));

			for (int i = 0; i < to.length; i++) {
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
						to[i]));
			}

			for (int i = 0; i < cc.length; i++) {
				msg.addRecipient(Message.RecipientType.CC, new InternetAddress(
						cc[i]));
			}

			for (int i = 0; i < bcc.length; i++) {
				msg.addRecipient(Message.RecipientType.BCC,
						new InternetAddress(bcc[i]));
			}

			msg.saveChanges();

			Transport transport = session.getTransport("smtp");

			transport.connect(host, userName, passWord);

			transport.sendMessage(msg, msg.getAllRecipients());
			System.out.println("Sent email successfully....");
			transport.close();

			return true;

		} catch (Exception mex) {	
			mex.printStackTrace();
			return false;
		}
		
	}
}

// SendMail.execute("index.html"); // define this in to ur test case
