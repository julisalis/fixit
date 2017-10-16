package ar.com.utn.controllers;

import ar.com.utn.dto.PublicacionDTO;
import ar.com.utn.form.PublicacionFotoForm;
import ar.com.utn.models.EstadoPublicacion;
import ar.com.utn.models.Publicacion;
import ar.com.utn.models.PublicacionPhoto;
import ar.com.utn.repositories.implementation.PublicacionSearch;
import ar.com.utn.services.PublicacionService;
import ar.com.utn.services.UsuarioService;
import ar.com.utn.utils.CurrentSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
@RequestMapping(path="/publicaciones")
public class PublicacionPublicController {

    @Autowired
    private Environment environment;
    @Autowired
    private PublicacionService publicacionService;
    @Autowired
    private CurrentSession currentSession;
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PublicacionSearch publicacionSearch;


    @RequestMapping(path="/detalle")
    public  @ResponseBody
    Map<String,Object> detallePublicacion(Long publicacionId, WebRequest request, Model model) {
        HashMap<String,Object> map = new HashMap<>();
        Publicacion mipublicacion = publicacionService.findById(publicacionId);
        map.put("publicacion",new PublicacionDTO(mipublicacion,getCover(mipublicacion)));
        return map;
    }

    @GetMapping(value="/{slug}")
    public String listPublicaciones(@PathVariable("slug") String slug, WebRequest request, Model model) {
        model.addAttribute("nombre_categoria",publicacionService.findTipoTrabajoBySlug(slug).getNombre());
        model.addAttribute("publicaciones", getPublicacionesPorCatregoria(publicacionService.findAllByEstadoPublicacionEquals(EstadoPublicacion.NUEVA), slug));
        return "publicaciones-por-categoria";
    }

    @GetMapping(value = "/search")
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
        List<PublicacionDTO> publicacionDTOS = searchResults.stream().filter(publicacion -> publicacion.getEstadoPublicacion() == EstadoPublicacion.NUEVA)
                                                                     .map(publicacion -> new PublicacionDTO(publicacion,getCover(publicacion))).collect(Collectors.toList());
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
