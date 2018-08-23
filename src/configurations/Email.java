package configurations;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {
	
	static {
		Properties props = System.getProperties();

		props.put("mail.smtp.password", Configurations.getConfig().retreive("emailp"));
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "stmp.gmail.com");		
		props.put("mail.smtp.user", "smtp.gmail.com");

	}
	
	private final String from;
	private final String password;
	private final String to;
	
	private final String title;
	private final String message;
	
	public Email(final String title, final String message) {
		this(Configurations.getConfig().retreive("emailreceiver"), title, message);
		
	}
	
	public Email(final String toEmail, final String title, final String message) {
		this(Configurations.getConfig().retreive("email"), Configurations.getConfig().retreive("emailp"), toEmail, title, message);
	}
	
	public Email(final String from, final String password, final String to, final String title, final String message) {
		this.message = message;
		this.title = title;
		this.from = from;
		this.password = password;
		this.to = to;
	}

	
	//credits to java mail example
    //dispatches an email to the receiver from the sender
	public void dispatch() {


		try {
            //new io session (javax.mail.Session)
			Session session = Session.getInstance(System.getProperties(), new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
				    return new PasswordAuthentication(from, password);
				}
			});  
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(this.from));
			message.addRecipients(Message.RecipientType.TO,
					to);
			
			message.setSubject(title);
			message.setContent(this.message, "text/html; charset=utf-8");
			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", from,
					password);
			transport.sendMessage(message, message.getAllRecipients());
			System.out.println("email sent");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}