package poly.edu.ka.service.Impl;

import jakarta.servlet.ServletContext;
import poly.edu.ka.domain.Account;
import poly.edu.ka.services.EmailService;
import poly.edu.ka.utils.MailUtil;

public class EmailServiceImpl implements EmailService {

	private static final String EMAIL_WELCOME_SUBJECT = "Welcome to  KAJewelry Shop";
	private static final String EMAIL_FORGOT_PASSWORD = " KAJewelry Shop - New Password";

	@Override
	public void sendEmail(ServletContext context, Account recipient, String type) {
		String host = context.getInitParameter("host");
		String port = context.getInitParameter("port");
		String user = context.getInitParameter("user");
		String pass = context.getInitParameter("pass");

		try {
			String content = null;
			String subject = null;
			switch (type) {
			case "welcome":
				subject = EMAIL_WELCOME_SUBJECT;
				content = "Dear " + recipient.getUsername() + "!,welcome to KAJewelry Shop. We hope you have a good time!";
				break;
			case "forgot":
				subject = EMAIL_FORGOT_PASSWORD;
				content = "Dear " + recipient.getUsername() + "!, your new password here: " + recipient.getPassword();
				break;
			default:
				subject = " KAJewelry Shop";
				content = "Maybe this email is wrong, don't care about it";
				break;
			}
			MailUtil.sendEmail(host, port, user, pass, recipient.getEmail(), subject, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
