package ar.com.utn.configuration;

import ar.com.utn.utils.RequestUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by julian on 10/09/17.
 */
@Component
public class LoginAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private static Logger logger = Logger.getLogger(LoginAuthenticationFailureHandler.class);

    @Autowired
    private RequestUtil requestUtil;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        logger.info("Error en el login " + exception.getMessage());
        if (requestUtil.isAjaxRequest(request)) {
            Map<String, Object> m = new HashMap<>();
            m.put("success", false);
            requestUtil.sendJsonResponse(response, m);
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
