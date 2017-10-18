package ar.com.utn.controllers;

import ar.com.utn.dto.PostulacionDTO;
import ar.com.utn.dto.PublicacionDTO;
import ar.com.utn.form.CurrencyCode;
import ar.com.utn.form.PostulacionForm;
import ar.com.utn.form.PublicacionForm;
import ar.com.utn.form.PublicacionFotoForm;
import ar.com.utn.models.*;
import ar.com.utn.services.PostulacionService;
import ar.com.utn.services.PublicacionService;
import ar.com.utn.services.UsuarioService;
import ar.com.utn.utils.CurrentSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by julis on 15/7/2017.
 */
@Controller
@RequestMapping(path="/postulacion")
public class PostulacionController {

    @Autowired
    private PublicacionService publicacionService;

    @Autowired
    private PostulacionService postulacionService;

    @Autowired
    private CurrentSession currentSession;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping(value = "/new")
    public String newPostulacion(@RequestParam(value = "publicacionId") Publicacion publicacion, WebRequest request, Model model) {
        addModelAttributes(model,new PostulacionForm(),"new?publicacionId="+publicacion.getId());
        model.addAttribute("currencies", CurrencyCode.values());
        model.addAttribute("publicacion", new PublicacionDTO(publicacion,getCover(publicacion)));
        return "postulacion";
    }

    @GetMapping(value = "/edit/{postulacionId}")
    public String editPostulacion(@PathVariable Long postulacionId, WebRequest request, Model model) {
        Postulacion postulacion = postulacionService.findById(postulacionId);
        if (postulacion!=null){
            addModelAttributes(model,new PostulacionForm(postulacion),"edit");
            model.addAttribute("currencies", CurrencyCode.values());
            Publicacion publicacion = postulacion.getPublicacion();
            model.addAttribute("publicacion", new PublicacionDTO(publicacion,getCover(publicacion)));
            return "postulacion";
        }
        return "redirect:/postulacion/list";
    }

    public void addModelAttributes(Model model, PostulacionForm form, String formAction){
        model.addAttribute("postulacion",form);
        model.addAttribute("currencies", CurrencyCode.values());
        model.addAttribute("form_action",formAction);
    }

    private PublicacionFotoForm getCover(Publicacion publicacion) {
        PublicacionPhoto publicacionPhoto = publicacionService.getCover(publicacion);
        if(publicacionPhoto!=null){
            return new PublicacionFotoForm(publicacionPhoto);
        }else return null;
    }

    @PostMapping(value = "/new")
    public @ResponseBody Map<String,Object> newPostulacion(@RequestParam(value = "publicacionId") Publicacion publicacion,@Valid @ModelAttribute("postulacion") PostulacionForm postulacionForm,BindingResult result) {
        HashMap<String,Object> map = new HashMap<>();
        Prestador prestador = currentSession.getUser().getPrestador();
        try {
            if(prestador.getPostulaciones().stream().anyMatch(p -> p.getPublicacion()==publicacion)) {
                map.put("success", false);
                map.put("msg", "Usted ya está postulado en esta publicación.");
            }else if(prestador.getMpPrestador()==null){
                map.put("success", false);
                map.put("msg", "Ingrese a su perfil para Iniciar Sesión con Mercado Pago.");
            }else{
                if(!result.hasErrors()){
                    if(postulacionForm.getPresupAprox().doubleValue() > 0
                            && postulacionForm.getDuracionAprox().doubleValue() > 0){
                        Postulacion postulacion = new Postulacion(postulacionForm,prestador,publicacion);
                        postulacionService.createPostulacion(postulacion);
                        map.put("success", true);
                        map.put("msg","La postulación ha sido creada con éxito!");
                    }else{
                        map.put("success", false);
                        map.put("msg","La duracion y el presupuesto deben ser positivos.");
                    }
                }else{
                    map.put("success", false);
                    map.put("msg","Complete todos los campos obligatorios.");
                }
            }
        }catch (Exception e) {
            map.put("success", false);
            map.put("msg","Ha surgido un error, pruebe nuevamente más tarde");
        }
        return map;
    }

    @PostMapping(value = "/edit")
    public @ResponseBody Map<String,Object> editPostulacion(@Valid @ModelAttribute("postulacion") PostulacionForm postulacionForm,BindingResult result) {
        HashMap<String,Object> map = new HashMap<>();
        Prestador prestador = currentSession.getUser().getPrestador();
        try {
            if(prestador.getMpPrestador()==null){
                map.put("success", false);
                map.put("msg", "Ingrese a su perfil para Iniciar Sesión con Mercado Pago.");
            }else{
                if(!result.hasErrors()){
                    if(postulacionForm.getPresupAprox().doubleValue() > 0
                            && postulacionForm.getDuracionAprox().doubleValue() > 0){
                        postulacionService.editPostulacion(postulacionForm);
                        map.put("success", true);
                        map.put("msg","La postulación ha sido editada con éxito!");
                    }else{
                        map.put("success", false);
                        map.put("msg","La duracion y el presupuesto deben ser positivos.");
                    }
                }else{
                    map.put("success", false);
                    map.put("msg","Complete todos los campos obligatorios.");
                }
            }
        }catch (Exception e) {
            map.put("success", false);
            map.put("msg","Ha surgido un error, pruebe nuevamente más tarde");
        }
        return map;
    }


    @GetMapping(value="/list")
    public String listPostulaciones(WebRequest request, Model model) {
        Usuario user = currentSession.getUser();
        List<PostulacionDTO> misPostulaciones = user.getPrestador().getPostulaciones().stream().map(postulacion -> new PostulacionDTO(postulacion, getCover(publicacionService.findById(postulacion.getPublicacion().getId())), user)).collect(Collectors.toList());
        model.addAttribute("postulacionesNuevas", misPostulaciones.stream().filter(postulacion -> postulacion.getEstado()!= null && postulacion.getEstado().equals(EstadoPostulacion.NUEVA)).collect(Collectors.toList()));
        model.addAttribute("postulacionesContratadas", misPostulaciones.stream().filter(postulacion -> postulacion.getEstado()!= null && postulacion.getEstado().equals(EstadoPostulacion.CONTRATADA)).collect(Collectors.toList()));
        model.addAttribute("postulacionesFinalizadas", misPostulaciones.stream().filter(postulacion -> postulacion.getEstado()!= null && postulacion.getEstado().equals(EstadoPostulacion.FINALIZADA)).collect(Collectors.toList()));
        model.addAttribute("postulacionesRechazadas", misPostulaciones.stream().filter(postulacion -> postulacion.getEstado()!= null && postulacion.getEstado().equals(EstadoPostulacion.RECHAZADA)).collect(Collectors.toList()));
        return "postulacion-list";
    }

    @GetMapping(value="/detalle/{postulacionId}")
    public String detallePostulacion(@PathVariable Long postulacionId, WebRequest request, Model model) {
        Postulacion mipostulacion = postulacionService.findById(postulacionId);
        Publicacion mipublicacion = publicacionService.findById(mipostulacion.getPublicacion().getId());
        if(mipostulacion!=null){
            model.addAttribute("postulacion",new PostulacionDTO(mipostulacion,getCover(mipublicacion),usuarioService.findByPrestador(mipostulacion.getPrestador())));
            model.addAttribute("publicacion",new PublicacionDTO(mipublicacion,getCover(mipublicacion)));
            return "postulacion-detalle";
        }
        return "redirect:/";
    }
}
