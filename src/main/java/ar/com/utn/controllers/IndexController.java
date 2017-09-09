package ar.com.utn.controllers;

import ar.com.utn.dto.TipoTrabajoDTO;
import ar.com.utn.models.Publicacion;
import ar.com.utn.models.TipoTrabajo;
import ar.com.utn.models.Usuario;
import ar.com.utn.services.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import sun.misc.Request;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by julis on 17/5/2017.
 */

@Controller
public class IndexController {

    @Autowired
    private PublicacionService publicacionService;

    @RequestMapping("/")
    String index(Model model) {
        model.addAttribute("tipotrabajos", getTiposTrabajos(publicacionService.getTipostrabajos()));
        return "index";
    }

    public List<TipoTrabajoDTO> getTiposTrabajos (List<TipoTrabajo> tiposTrabajos) {
        return tiposTrabajos.stream().map(t -> new TipoTrabajoDTO(t,publicacionService.countPublicaciones(t))).collect(Collectors.toList());
    }

    @GetMapping(value="/publicaciones/plomeria")
    public String listPublicaciones(WebRequest request, Model model) {
       // model.addAttribute("publicaciones", );
        return "publicaciones-por-categoria";
    }

//    @GetMapping(value="/publicaciones/{slug}")
//    public String listPublicaciones(@RequestParam String slug, WebRequest request, Model model) {
//       model.addAttribute("publicaciones", );
//        return "publicaciones-por-categoria";
//    }

//    public List<Publicacion> getPublicacionesPorCatregoria (List<Publicacion> publicaciones, slug) {
//        return publicaciones.stream().filter(p -> p.getTiposTrabajo().f)
//                .map(p -> ).collect(Collectors.toList());
//    }
}
