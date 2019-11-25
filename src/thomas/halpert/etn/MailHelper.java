package thomas.halpert.etn;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**************************** JAVA MAIL INFO ****************************

Username: noys.noreply@gmail.com
Password: n!ghtmar3

Properties props = new Properties();
props.put("mail.transport.protocol", "smtps");
props.put("mail.smtps.host", "smtp.gmail.com");
props.put("mail.smtps.port", 465);
props.put("mail.smtps.auth", "true");
props.put("mail.smtps.quitwait", "false");
Session session = Session.getDefaultInstance(props);

**************************************************************************/

public class MailHelper {
	public static void sendMail(String to, String from, String subject, String body) throws MessagingException
	{
		Properties p = new Properties();
		p.put("mail.transport.protocol", "smtps");
		p.put("mail.smtps.host", "smtp.gmail.com");
		p.put("mail.smtps.port", 465);
		p.put("mail.smtps.auth", "true");
		p.put("mail.smtps.quitwait", "false");
		Session sess = Session.getDefaultInstance(p);
//		sess.setDebug(true);
		
		Message msg = new MimeMessage(sess);
		msg.setSubject(subject);
		msg.setText(body);
		
		Address fromAddr = new InternetAddress(from);
		Address toAddr = new InternetAddress(to);
		msg.setFrom(fromAddr);
		msg.setRecipient(Message.RecipientType.TO, toAddr);
		
		Transport transport = sess.getTransport();
		transport.connect("noys.noreply@gmail.com", "n!ghtmar3");
		transport.sendMessage(msg, msg.getAllRecipients());
		transport.close();
	}
}
