package ar.com.utn.configuration;

import ar.com.utn.models.*;
import ar.com.utn.utils.CurrentSession;
import ar.com.utn.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by julian on 09/09/17.
 */
@Component
public class LoginAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private RequestUtil requestUtil;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private CurrentSession currentSession;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {

        if(requestUtil.isAjaxRequest(request)) {
            String returnURL = request.getParameter("returnURL");
            Usuario user = currentSession.getUser();

            Map<String, Object> m = new HashMap<>();
            if(user.getRoles().size()>1){
                m.put("select_role", true);
            }else{
                m.put("select_role", false);
            }
            m.put("success", true);
            m.put("url", (returnURL!= null) ? returnURL : "/");
            requestUtil.sendJsonResponse(response, m);
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

    public RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
}
