package poly.edu.ka.services;

import jakarta.servlet.ServletContext;
import poly.edu.ka.domain.Account;

public interface EmailService {

	void sendEmail(ServletContext context, Account recipient, String type);

}
