package com.bemychef.chefs.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

	void sendEmail(SimpleMailMessage mailMessage);

}
