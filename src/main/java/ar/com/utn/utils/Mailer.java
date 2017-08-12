package ar.com.utn.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

/**
 * Created by mjarabroviski on 12/08/2017.
 */
@Component
public class Mailer {
    private static Logger logger =  Logger.getLogger(Mailer.class);
    @Autowired
    private Environment environment;
    @Autowired
    private JavaMailSender javaMailSender;
//    @Autowired
//    private Configuration freemarkerConfiguration;


    public void sendMail(String to, String subject, String templateFile, Map<String, Object> model){
        try{
            MimeMessagePreparator preparator = new MimeMessagePreparator() {
                @Override
                public void prepare(MimeMessage mimeMessage) throws Exception {
                    MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

                    message.setFrom(environment.getProperty("mail.from"),environment.getProperty("mail.from.name"));
                    message.setSubject(subject);

//                    Template template = freemarkerConfiguration.getTemplate(templateFile,"UTF-8");

                    if(environment.acceptsProfiles("dev") || environment.acceptsProfiles("test")){
                        message.setTo(environment.getProperty("mail.info"));
                        message.setBcc(environment.getProperty("mail.support"));
                    }else if(environment.acceptsProfiles("prod")){
                        message.setTo(to);
                    }else{
                        Writer out = new OutputStreamWriter(System.out);
//                        template.process(model, out);
                        out.flush();
                    }
//                    String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
                    message.setText("hola",true);
                }
            };
            logger.trace("Enviando mail a: "+ to);
            javaMailSender.send(preparator);
            logger.info("Se envió mail a: "+ to);

        }catch (Exception e) {
            logger.error("No pudo enviarse el mail",e);
        }
    }

}
