package ar.com.utn.controllers;

import ar.com.utn.form.CurrencyCode;
import ar.com.utn.form.PrestadorForm;
import ar.com.utn.form.PublicacionForm;
import ar.com.utn.form.SelectorForm;
import ar.com.utn.models.*;
import ar.com.utn.services.PublicacionService;
import ar.com.utn.services.UsuarioService;
import ar.com.utn.utils.CurrentSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.EnumSet;
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
    public String listPublicaciones(WebRequest request, Model model) {
        //todo add en security permisos solo para el tomaddor
        Usuario user = currentSession.getUser();
        model.addAttribute("publicaciones",user.getTomador().getPublicaciones());
        return "publicacion-list";
    }

    @GetMapping(value="/new")
    public String newPublicacion(WebRequest request, Model model) {
        addModelAttributes(model,new PublicacionForm());
        return "publicacion-new-edit";
    }

    @PostMapping(path="/new")
    public String newPublicacion(@Valid @ModelAttribute("publicacion") PublicacionForm publicacionForm, BindingResult result, Model model, WebRequest webRequest, HttpServletRequest request, HttpServletResponse response) {
        try {

            if(publicacionForm.getUrgencia().equals(Urgencia.FECHA) && publicacionForm.getFecha()==null){
                result.rejectValue("Fecha","Fecha","es requerída");
            }

            if(!result.hasErrors()){
                publicacionService.createPublicacion(publicacionForm);
                return "redirect:/publicacion/list";
            }else{
                addModelAttributes(model,publicacionForm);
                return "publicacion-new-edit";
            }
        }catch (Exception e) {
            return "publicacion-new-edit";
        }

    }
    private List<SelectorForm> generarProvicias() {
        return usuarioService.getProvincias().stream().map(provincia -> new SelectorForm(provincia.getId(),provincia.getNombre())).collect(Collectors.toList());
    }


    public void addModelAttributes(Model model, PublicacionForm form){
        model.addAttribute("provincias",generarProvicias());
        model.addAttribute("publicacion",form);
        model.addAttribute("tipos", publicacionService.getTipostrabajos());
        model.addAttribute("tiempos", TiempoPublicacion.values());
        model.addAttribute("urgencias", Urgencia.values());
        model.addAttribute("currencies", CurrencyCode.values());
        model.addAttribute("form_action","new");
    }
}

