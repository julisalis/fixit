package ar.com.utn.controllers;

import ar.com.utn.form.PrestadorForm;
import ar.com.utn.form.PublicacionForm;
import ar.com.utn.form.SelectorForm;
import ar.com.utn.models.TipoPublicacion;
import ar.com.utn.models.TipoTrabajo;
import ar.com.utn.models.Usuario;
import ar.com.utn.services.PublicacionService;
import ar.com.utn.services.UsuarioService;
import ar.com.utn.utils.CurrentSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by julis on 15/7/2017.
 */
@Controller
@RequestMapping(path="/publicacion")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;
    @Autowired
    private CurrentSession currentSession;
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping(value="/list")
    public String signupPrestador(WebRequest request, Model model) {
        Usuario user = currentSession.getUser();
        if(user!=null && user.getTomador()!=null){
            model.addAttribute("publicaciones",user.getTomador().getPublicaciones());
            return "publicacion-list";
        }
        return "redirect:/";
    }

    @GetMapping(value="/new")
    public String newPublicacion(WebRequest request, Model model) {
        model.addAttribute("provincias",generarProvicias());
        model.addAttribute("publicacion",new PublicacionForm());
        model.addAttribute("tipoTrabajos", publicacionService.getTipostrabajos());
        model.addAttribute("form_action","new");
        return "publicacion-new-edit";
    }

    private List<SelectorForm> generarProvicias() {
        return usuarioService.getProvincias().stream().map(provincia -> new SelectorForm(provincia.getId(),provincia.getNombre())).collect(Collectors.toList());
    }
}
