package ar.com.utn.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mjarabroviski on 12/08/2017.
 */
public class MailsModelMapper {

    @Autowired
    private Environment environment;
    @Autowired
    private URLBuilder urlBuilder;

    public Map<String,Object> genericModelSetter() {
        Map<String, Object> model = new HashMap<String, Object>();
        String offlineHost = urlBuilder.getHost();
        model.put("principal_url",offlineHost);
        model.put("multimedia_url", environment.getProperty("application.url.multimedia"));
        model.put("images_url", environment.getProperty("mail.images.url"));
        model.put("terms_cond_url", urlBuilder.makeOfflineAbsolutePathLink(environment.getProperty("url.terms-conditions")));
        model.put("trust_and_safety", urlBuilder.makeOfflineAbsolutePathLink(environment.getProperty("url.trust-and-safety")));
        model.put("contact_us_url", urlBuilder.makeOfflineAbsolutePathLink(environment.getProperty("url.contact-us")));
        model.put("facebook_url",environment.getProperty("url.facebook"));
        model.put("instagram_url",environment.getProperty("url.instagram"));

        return model;
    }
}
