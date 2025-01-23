package com.onlinestationarymart.email_service.kafka.service;

import com.onlinestationarymart.domain_service.dto.SendEmailDTO;
import com.onlinestationarymart.email_service.constants.EmailConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailInventoryRefill implements ISendEmail{
    @Autowired
    JavaMailSender javaMailSender;
    public static final String NAME = "SendEmailInventoryRefill";

    private static final Logger LOGGER = LoggerFactory.getLogger(SendEmailInventoryRefill.class);
    @Override
    public void sendEmail(SendEmailDTO sendEmailDTO) {
        LOGGER.info("@@@@ SendEmailInventoryRefill: Inside sendEmail");
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(sendEmailDTO.getToEmail());
            simpleMailMessage.setSubject(EmailConstants.REFILLING_REQUIRED_EMAIL_SUBJECT);
            simpleMailMessage.setText(String.format(EmailConstants.REFILLING_REQUIRED_EMAIL_TEMPLATE,
                    sendEmailDTO.getProductDTOList())
            );
            javaMailSender.send(simpleMailMessage);
        } catch (MailException e) {
            throw new RuntimeException(e);
        }
        LOGGER.info("@@@@ SendEmailInventoryRefill: Send Email Completed!!");
    }
}
