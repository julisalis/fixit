package ar.com.utn.controllers;

import javax.servlet.ServletContext;
import javax.servlet.ServletResponse;

import ar.com.utn.form.PublicacionFotoForm;
import ar.com.utn.services.MultimediaService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/multimedia")
public class MultimediaController{

    static final Logger logger = Logger.getLogger(MultimediaController.class);

    @Autowired
    private MultimediaService multimediaService;

    @RequestMapping(value="/image/{imageId}", method = RequestMethod.GET)
    public void getProfileImage(@PathVariable Long imageId, ServletResponse servletResponse){
        try{
            PublicacionFotoForm foto = multimediaService.findEcommerceImage(imageId);
            if(foto!=null){
                servletResponse.setContentType("image/"+foto.getExtension());
                servletResponse.getOutputStream().write(foto.getContent());
            }
        }catch(Exception e){
            logger.error("No se encuentra la imagen");

        }
    }

}
