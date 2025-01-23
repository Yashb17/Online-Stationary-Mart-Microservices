package com.onlinestationarymart.email_service.kafka.service;

import com.onlinestationarymart.domain_service.dto.SendEmailDTO;
import com.onlinestationarymart.email_service.constants.EmailConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service(SendEmailOrderReceived.NAME)
public class SendEmailOrderReceived implements ISendEmail{

    @Autowired
    JavaMailSender javaMailSender;
    @Value("${from.email.name}")
    private String fromEmail;
    public static final String NAME = "SendEmailOrderReceived";
    private static final Logger LOGGER = LoggerFactory.getLogger(SendEmailOrderReceived.class);

    @Override
    public void sendEmail(SendEmailDTO sendEmailDTO) {
        LOGGER.info("@@@@ SendEmailOrderReceived: Inside sendEmail");
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(sendEmailDTO.getToEmail());
            simpleMailMessage.setSubject(EmailConstants.ORDER_RECEIVED_EMAIL_SUBJECT);
            simpleMailMessage.setText(String.format(EmailConstants.ORDER_RECEIVED_EMAIL_TEMPLATE,
                    sendEmailDTO.getFullName(),
                    sendEmailDTO.getOrderId(),
                    sendEmailDTO.getTotalAmount(),
                    sendEmailDTO.getProductsCodeMap(),
                    fromEmail)
            );
            javaMailSender.send(simpleMailMessage);
        } catch (MailException e) {
            throw new RuntimeException(e);
        }
        LOGGER.info("@@@@ SendEmailOrderReceived: Send Email Completed!!");
    }
}
