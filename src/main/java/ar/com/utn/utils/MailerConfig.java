package ar.com.utn.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Created by mjarabroviski on 12/08/2017.
 */
@Configuration
public class MailerConfig {

    @Value("${mail.smtp.port}")
    private Integer port;
    @Value("${mail.smtp.host}")
    private String host;
    @Value("${mail.transport.protocol}")
    private String mailProtocol;
    @Value("${mail.smtp.auth}")
    private String auth;
    @Value("${mail.smtp.starttls.enable}")
    private String starttls;
    @Value("${mail.support.username}")
    private String userName;
    @Value("${mail.support.password}")
    private String password;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setDefaultEncoding("UTF-8");
        javaMailSender.setProtocol(mailProtocol);
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(userName);
        javaMailSender.setPassword(password);
        javaMailSender.setJavaMailProperties(getMailProperties());
        return javaMailSender;
    }
    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", auth);
        properties.setProperty("mail.smtp.starttls.enable", starttls);
        properties.setProperty("mail.debug", "false");
        return properties;
    }
}
