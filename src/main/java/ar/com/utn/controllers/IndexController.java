package ar.com.utn.controllers;

import ar.com.utn.dto.PublicacionDTO;
import ar.com.utn.dto.TipoTrabajoDTO;
import ar.com.utn.form.PublicacionFotoForm;
import ar.com.utn.models.*;
import ar.com.utn.repositories.implementation.PublicacionSearch;
import ar.com.utn.services.PublicacionService;
import ar.com.utn.services.UsuarioService;
import ar.com.utn.utils.CurrentSession;
import jdk.management.resource.ResourceRequestDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import sun.misc.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by julis on 17/5/2017.
 */

@Controller
public class IndexController {

    @Autowired
    private PublicacionService publicacionService;

    @Autowired
    private PublicacionSearch publicacionSearch;
    @Autowired
    private CurrentSession currentSession;

    @RequestMapping("/")
    String index(Model model) {
        model.addAttribute("tipotrabajos", getTiposTrabajos(publicacionService.getTipostrabajos()));
        model.addAttribute("destacados", publicacionService.getDestacados().stream().map(publicacion -> new PublicacionDTO(publicacion,getCover(publicacion))).collect(Collectors.toList()));
        Usuario usuario = currentSession.getUser();
        if(usuario!=null && usuario.getPrestador()!=null){
            List<PublicacionDTO> publicacionDTOS = publicacionService.getTrabajosRecomendados(usuario.getPrestador().getTipos()).stream()
                    .map(publicacion -> new PublicacionDTO(publicacion,getCover(publicacion),usuario)).collect(Collectors.toList());
            model.addAttribute("trabajosRecomendados", publicacionDTOS);
        }
        return "index";
    }

    public List<TipoTrabajoDTO> getTiposTrabajos (List<TipoTrabajo> tiposTrabajos) {
        return tiposTrabajos.stream().map(t -> new TipoTrabajoDTO(t,publicacionService.countPublicaciones(t))).collect(Collectors.toList());
    }

    private PublicacionFotoForm getCover(Publicacion publicacion) {
        PublicacionPhoto publicacionPhoto = publicacionService.getCover(publicacion);
        if(publicacionPhoto!=null){
            return new PublicacionFotoForm(publicacionPhoto);
        }else return null;
    }

    @RequestMapping(value="/pageNotFound",method=RequestMethod.GET)
    public String pageNotFound(){
        return "404-not-found";
    }
}
