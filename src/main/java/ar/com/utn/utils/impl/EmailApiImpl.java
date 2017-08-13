package ar.com.utn.utils.impl;

import ar.com.utn.services.implementation.MailServiceImpl;
import ar.com.utn.utils.EmailApi;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by julian on 13/08/17.
 */
@Component
public class EmailApiImpl implements EmailApi {

    static final Logger logger = Logger.getLogger(MailServiceImpl.class);

    @Autowired
    private JavaMailSender javaMailSender;


    @Override
    @Async
    public void sendMessage(String subject,String emailDestination,String emailFrom,String content) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mailMsg = new MimeMessageHelper(mimeMessage);
        try {
            mailMsg.setFrom(emailFrom);
            mailMsg.setTo(emailDestination);
            mailMsg.setSubject(subject);

            mailMsg.setText(content, true /* isHtml */);

        } catch (MessagingException e) {
            logger.error("ERROR", e);
        }

        javaMailSender.send(mimeMessage);
    }
}
