package ar.com.utn.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.security.web.util.matcher.ELRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by julian on 09/09/17.
 */
@Component
public class RequestUtil {

    private static final Logger logger = Logger.getLogger(RequestUtil.class);

    private static final RequestMatcher REQUEST_MATCHER = new ELRequestMatcher(
            "hasHeader('X-Requested-With','XMLHttpRequest')");

    public Boolean isAjaxRequest(HttpServletRequest request) {
        return REQUEST_MATCHER.matches(request);
    }

    public void sendJsonResponse(HttpServletResponse response, Map<?, ?> m) {
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        try {
            ObjectMapper mapper = new ObjectMapper();
            response.setStatus(HttpServletResponse.SC_OK);
            mapper.writeValue(response.getWriter(), m);
        } catch (IOException e) {
            logger.error("error writing json to response", e);
            throw new RuntimeException("error writing json to response", e);
        }
    }
}
