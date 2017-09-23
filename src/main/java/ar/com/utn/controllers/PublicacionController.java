package ar.com.utn.controllers;

import ar.com.utn.dto.PublicacionDTO;
import ar.com.utn.form.CurrencyCode;
import ar.com.utn.form.PrestadorForm;
import ar.com.utn.form.PublicacionForm;
import ar.com.utn.form.SelectorForm;
import ar.com.utn.models.*;
import ar.com.utn.services.PublicacionService;
import ar.com.utn.services.UsuarioService;
import ar.com.utn.utils.CurrentSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        List<PublicacionDTO> misPublicaciones = user.getTomador().getPublicaciones().stream().map(publicacion -> new PublicacionDTO(publicacion)).collect(Collectors.toList());
        model.addAttribute("publicacionesNuevas", misPublicaciones.stream().filter(publicacion -> publicacion.getEstado()!= null && publicacion.getEstado().equals(EstadoPublicacion.NUEVA)).collect(Collectors.toList()));
        model.addAttribute("publicacioneContratadas", misPublicaciones.stream().filter(publicacion -> publicacion.getEstado()!= null && publicacion.getEstado().equals(EstadoPublicacion.CONTRATADA)).collect(Collectors.toList()));
        model.addAttribute("publicacionesFinalizadas", misPublicaciones.stream().filter(publicacion -> publicacion.getEstado()!= null && publicacion.getEstado().equals(EstadoPublicacion.FINALIZADA)).collect(Collectors.toList()));
        return "publicacion-list";
    }

    @GetMapping(value="/new")
    public String newPublicacion(WebRequest request, Model model) {
        addModelAttributes(model,new PublicacionForm());
        return "publicacion-new-edit";
    }

    @GetMapping(value="/edit/{publicacionId}")
    public String editPublicacion(@PathVariable Long publicacionId, WebRequest request, Model model) {
        addModelAttributes(model,new PublicacionForm(publicacionService.findById(publicacionId)));
        return "publicacion-new-edit";
    }

    @RequestMapping(path="/detalle")
    public  @ResponseBody Map<String,Object> detallePublicacion(Long publicacionId, WebRequest request, Model model) {
        HashMap<String,Object> map = new HashMap<>();
        Publicacion mipublicacion = publicacionService.findById(publicacionId);
        map.put("publicacion",new PublicacionDTO(mipublicacion));
        return map;
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
            addModelAttributes(model,publicacionForm);
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

