package ar.com.utn.controllers;

import ar.com.utn.models.Mensaje;
import ar.com.utn.models.Postulacion;
import ar.com.utn.models.Usuario;
import ar.com.utn.services.MailService;
import ar.com.utn.services.MensajeService;
import ar.com.utn.services.PostulacionService;
import ar.com.utn.services.UsuarioService;
import ar.com.utn.utils.CurrentSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by julis on 15/7/2017.
 */
@Controller
@RequestMapping(path="/mensaje")
public class MensajeController {
    @Autowired
    private MensajeService mensajeService;

    @Autowired
    private CurrentSession currentSession;

    @Autowired
    private MailService mailService;

    @PostMapping(value = "/new")
    @Transactional
    public @ResponseBody Map<String,Object> newMensaje(@RequestParam(value = "postulacionId") Postulacion postulacion,@RequestParam(value = "new-message") String message ) {
        HashMap<String,Object> map = new HashMap<>();
        Boolean enviaTomador = currentSession.getActualRol().stream().anyMatch(o -> o.getAuthority().equalsIgnoreCase("TOMADOR"));
        if(postulacion!=null && message!=null && !message.isEmpty()) {
            mensajeService.createMensaje(postulacion,message,enviaTomador);
    //      mailService.sendPostulacionNuevaMail(cliente,prof,publicacion,postulacion);
            map.put("success", true);
            map.put("msg","La postulación ha sido creada con éxito!");
        }else{
                map.put("success", false);
                map.put("msg","Complete todos los campos obligatorios.");
        }

        return map;
    }
}
