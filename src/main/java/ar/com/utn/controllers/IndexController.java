package ar.com.utn.controllers;

import ar.com.utn.dto.PublicacionDTO;
import ar.com.utn.dto.TipoTrabajoDTO;
import ar.com.utn.form.PublicacionFotoForm;
import ar.com.utn.models.*;
import ar.com.utn.repositories.implementation.PublicacionSearch;
import ar.com.utn.services.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import sun.misc.Request;

import java.util.ArrayList;
import java.util.List;
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

    @RequestMapping("/")
    String index(Model model) {
        model.addAttribute("tipotrabajos", getTiposTrabajos(publicacionService.getTipostrabajos()));
        return "index";
    }

    public List<TipoTrabajoDTO> getTiposTrabajos (List<TipoTrabajo> tiposTrabajos) {
        return tiposTrabajos.stream().map(t -> new TipoTrabajoDTO(t,publicacionService.countPublicaciones(t))).collect(Collectors.toList());
    }

    @GetMapping(value="/publicaciones/{slug}")
    public String listPublicaciones(@PathVariable("slug") String slug, WebRequest request, Model model) {
        model.addAttribute("nombre_categoria",publicacionService.findTipoTrabajoBySlug(slug).getNombre());
        model.addAttribute("publicaciones", getPublicacionesPorCatregoria(publicacionService.findAllByEstadoPublicacionEquals(EstadoPublicacion.NUEVA), slug));
        return "publicaciones-por-categoria";
    }

    @GetMapping(value = "/publicaciones/search")
    public String listPublicacionesSearch(@RequestParam(value="searchTerm") String searchTerm, Model model) {
        if(searchTerm.isEmpty()) {
            model.addAttribute("publicaciones",new ArrayList<PublicacionDTO>());
            model.addAttribute("searchTerm",searchTerm);
            return "publicaciones-busqueda";
        }
        List<Publicacion> searchResults = null;
        try {
            searchResults = publicacionSearch.search(searchTerm);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        List<PublicacionDTO> publicacionDTOS = searchResults.stream().map(publicacion -> new PublicacionDTO(publicacion,getCover(publicacion))).collect(Collectors.toList());
        model.addAttribute("publicaciones", publicacionDTOS);
        model.addAttribute("searchTerm",searchTerm);
        return "publicaciones-busqueda";
    }

    public List<PublicacionDTO> getPublicacionesPorCatregoria (List<Publicacion> publicaciones, String slug) {
        return publicaciones.stream().filter(p -> p.getTipoTrabajo().getSlug().equals(slug)).map(publicacion -> new PublicacionDTO(publicacion,getCover(publicacion))).collect(Collectors.toList());
    }

    private PublicacionFotoForm getCover(Publicacion publicacion) {
        PublicacionPhoto publicacionPhoto = publicacionService.getCover(publicacion);
        if(publicacionPhoto!=null){
            return new PublicacionFotoForm(publicacionPhoto);
        }else return null;
    }

}
