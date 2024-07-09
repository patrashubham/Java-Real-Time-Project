package in.subham.util;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtil {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public boolean sendMail(String subject,String body,String to,File f) {
		
		try {
			
			MimeMessage mimeMsg = mailSender.createMimeMessage();
			
			MimeMessageHelper helper=new MimeMessageHelper(mimeMsg,true);
			helper.setSubject(subject);
			helper.setText(to, true);
			helper.addAttachment("Plans-Info", f);
			helper.setTo(to);
			
			
			mailSender.send(mimeMsg);
			
			
			
			
		} catch (Exception e) {

		
		}
		
		
		
		return true;
	}
		
}
  
		
	
		
		
	

