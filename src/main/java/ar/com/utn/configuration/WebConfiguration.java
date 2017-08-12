package ar.com.utn.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by julis on 26/5/2017.
 */

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        registry.addViewController("/login").setViewName("login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

//	@Bean
//    public JavaMailSender javaMailSender(){
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost(host);
//        mailSender.setPort(port);
//        //Set gmail email id
//        mailSender.setUsername(username);
//        mailSender.setDefaultEncoding("UTF-8");
//        //Set gmail email password
//        mailSender.setPassword(password);
//        Properties prop = mailSender.getJavaMailProperties();
//    //		prop.put("mail.transport.protocol", protocol);
//        prop.put("mail.smtp.auth", auth);
//        prop.put("mail.smtp.starttls.enable", ttlsEnable);
//        prop.put("mail.debug", debug);
//        return mailSender;
//    }
}
