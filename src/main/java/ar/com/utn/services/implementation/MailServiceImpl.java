package ar.com.utn.services.implementation;

import ar.com.utn.form.TomadorForm;
import ar.com.utn.services.MailService;
import ar.com.utn.utils.Mailer;
import ar.com.utn.utils.MailsModelMapper;
import ar.com.utn.utils.URLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.logging.Logger;

import static org.apache.axis.i18n.MessagesConstants.locale;

/**
 * Created by mjarabroviski on 12/08/2017.
 */
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private Mailer mailer;
    @Autowired
    private URLBuilder urlBuilder;
//    @Autowired
//    private MailsModelMapper mailsModelMapper;
    @Autowired
    private Environment environment;

    @Override
    public void sendRegistrationMailTomador(TomadorForm user, String link) {
//        Map<String, Object> model = mailsModelMapper.genericModelSetter();
//        model.put("user", user);
//        model.put("domain", link);
//        sendMailWithHeaderAndFooter(user.getEmail(), "register-mail.html", model, "Bienvenido a FixIT","Hola como estas","register");

    }

    public void sendMailWithHeaderAndFooter(String to, String templateFile, Map<String, Object> model, String title,String footer,String property){
        model.put("title",title);
        model.put("header_img_file", environment.getProperty("mail."+property+".header-img-file"));
        model.put("footer_msg",footer);
        mailer.sendMail(to,title,templateFile,model);
    }
}
