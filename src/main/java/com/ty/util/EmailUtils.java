package com.ty.util;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {
	
	@Autowired  
    private JavaMailSender mailSender; // it is interface provide by mail dependency
	
	public boolean sendEmail(String subject,String body,String to, File f) {
		
		try {
			
			MimeMessage mimeMsg = mailSender.createMimeMessage();
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMsg,true); // it is constructor injection bcz i am passing MimeMessage obj MimeMessageHelper class constructor 
			                                                   // if u want to send the multiple data u have to set true
			 
			helper.setSubject(subject);
			helper.setText(body, true); //text avlbl not body...if ur using html tag in body then keep it as true
			helper.setTo(to); // to which mail id u want to send the file
			
			
		    helper.addAttachment("Plans-Info",f);  // to send the attachment to email so somewhere excel file should store so i can take tht excel file from tht location
			
			mailSender.send(mimeMsg); // if u want to send simple email then u have to send simpeMaesage
			                    // if u want send email with html tag with some attachment then go for mimeMassage
			                    // purpose to send mail to given mail id
			
		} catch (Exception e) {
			e.printStackTrace(); // if any exception occure i want print on console for now
		}
		
		return true; // if mail send successfully then i want to return true
		
	}
}
