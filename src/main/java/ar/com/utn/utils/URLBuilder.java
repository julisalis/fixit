package ar.com.utn.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by mjarabroviski on 12/08/2017.
 */
@Component
public class URLBuilder {

    @Value("${application.url}")
    private String host;
    @Value("${application.url.multimedia}")
    private String imagesHost;

    public String getHost() {
        return host;
    }

    public String makeOfflineAbsolutePathLink(String relativeLink){
        return makeAbsolutePathLink(host,relativeLink);
    }

    public String makeAbsolutePathLink(String host, String relativeLink){

        if(host != null && host.endsWith("/")){
            host = host.substring(0,host.length()-1);
        }
        if(relativeLink != null && relativeLink.startsWith("/") && relativeLink.length() > 1){
            relativeLink = relativeLink.substring(1);
        }
        return host+"/"+relativeLink;
    }
}
