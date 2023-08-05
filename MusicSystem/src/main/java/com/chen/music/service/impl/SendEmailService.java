package com.chen.music.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SendEmailService {
    @Autowired
    private JavaMailSenderImpl mailSender;

    @Value("${spring.mail.username}")
    private String from;
    @Value("${spring.mail.nickname}")
    private String nickname;

    public void sendEmailVerifyCode(String code,String emailAddress){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(nickname+'<'+from+'>');
        message.setTo(emailAddress);
        message.setSubject("邮件发送");
        message.setText("验证码为:"+code+",有效期为十分钟，若非本人操作，请忽略");
        try {
            mailSender.send(message);
            log.info("邮件发送成功");
        }catch (MailException e){
            log.info("邮件发送失败");
            e.printStackTrace();
        }
    }

}
