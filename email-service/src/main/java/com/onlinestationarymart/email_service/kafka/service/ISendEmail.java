package com.onlinestationarymart.email_service.kafka.service;

import com.onlinestationarymart.domain_service.dto.SendEmailDTO;

public interface ISendEmail {

    void sendEmail(SendEmailDTO sendEmailDTO);
}
