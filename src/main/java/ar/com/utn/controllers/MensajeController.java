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
    private PostulacionService postulacionService;
    @Autowired
    private CurrentSession currentSession;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private MailService mailService;

    @PostMapping(value = "/new")
    @Transactional
    public @ResponseBody Map<String,Object> newMensaje(@RequestParam(value = "postulacionId") Long postulacionId,@RequestParam(value = "new-message") String message ) {
        HashMap<String,Object> map = new HashMap<>();

        Boolean enviaTomador = currentSession.getActualRol().stream().anyMatch(o -> o.getAuthority().equalsIgnoreCase("TOMADOR"));
        Postulacion postulacion = postulacionService.findById(postulacionId);

        Usuario usuarioOrigen = currentSession.getUser();
        Usuario usuarioDestino;
        if(enviaTomador){
            usuarioDestino = usuarioService.findByPrestador(postulacion.getPrestador());
        }else{
            usuarioDestino = usuarioService.findByTomador(postulacion.getPublicacion().getTomador());
        }

        if(postulacion!=null && message!=null && !message.isEmpty()) {
            mensajeService.createMensaje(postulacion,message,enviaTomador);
            mailService.sendConsultaPostulacion(usuarioOrigen,usuarioDestino,postulacion,message);
            map.put("success", true);
        }else{
                map.put("success", false);
        }

        return map;
    }
}
