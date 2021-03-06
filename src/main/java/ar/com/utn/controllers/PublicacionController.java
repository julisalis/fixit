package ar.com.utn.controllers;

import ar.com.utn.dto.PostulacionDTO;
import ar.com.utn.dto.PublicacionDTO;
import ar.com.utn.form.*;
import ar.com.utn.models.*;
import ar.com.utn.repositories.implementation.PublicacionSearch;
import ar.com.utn.services.MailService;
import ar.com.utn.services.PostulacionService;
import ar.com.utn.services.PublicacionService;
import ar.com.utn.services.UsuarioService;
import ar.com.utn.utils.CurrentSession;
import org.hibernate.mapping.UnionSubclass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
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
    private Environment environment;
    @Autowired
    private PublicacionService publicacionService;
    @Autowired
    private PostulacionService postulacionService;
    @Autowired
    private CurrentSession currentSession;
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MailService mailService;

    @Autowired
    private PublicacionSearch publicacionSearch;

    @PreAuthorize("hasAuthority('TOMADOR')")
    @GetMapping(value="/list")
    public String listPublicaciones(WebRequest request, Model model) {
        Usuario user = currentSession.getUser();
        List<PublicacionDTO> misPublicaciones = user.getTomador().getPublicaciones().stream().map(publicacion -> new PublicacionDTO(publicacion,getCover(publicacion),publicacionService.findContratacionForPublicacion(publicacion))).collect(Collectors.toList());
        model.addAttribute("publicacionesNuevas", misPublicaciones.stream().filter(publicacion -> publicacion.getEstado()!= null && publicacion.getEstado().equals(EstadoPublicacion.NUEVA)).collect(Collectors.toList()));
        model.addAttribute("publicacionesContratadas", misPublicaciones.stream().filter(publicacion -> publicacion.getEstado()!= null && (publicacion.getEstado().equals(EstadoPublicacion.CONTRATADA) || publicacion.getEstado().equals(EstadoPublicacion.REVISION))).collect(Collectors.toList()));
        model.addAttribute("publicacionesFinalizadas", misPublicaciones.stream().filter(publicacion -> publicacion.getEstado()!= null && publicacion.getEstado().equals(EstadoPublicacion.FINALIZADA)).collect(Collectors.toList()));
        return "publicacion-list";
    }

    @PreAuthorize("hasAuthority('TOMADOR')")
    @GetMapping(value="/new")
    public String newPublicacion(WebRequest request, Model model) {
        addModelAttributes(model,new PublicacionForm(),"new");
        return "publicacion-new-edit";
    }

    @PreAuthorize("hasAuthority('TOMADOR')")
    @GetMapping(value="/edit/{publicacionId}")
    public String editPublicacion(@PathVariable Long publicacionId, WebRequest request, Model model) {
        Publicacion publicacion = publicacionService.findById(publicacionId);
        addModelAttributes(model,new PublicacionForm(publicacion),"edit");
        return "publicacion-new-edit";
    }


    private PublicacionFotoForm getCover(Publicacion publicacion) {
        PublicacionPhoto publicacionPhoto = publicacionService.getCover(publicacion);
        if(publicacionPhoto!=null){
            return new PublicacionFotoForm(publicacionPhoto);
        }else return null;

    }

    @PreAuthorize("hasAuthority('TOMADOR')")
    @PostMapping(path="/new")
    public @ResponseBody Map<String,Object> newPublicacion(@Valid @ModelAttribute("publicacion") PublicacionForm publicacionForm, BindingResult result, Model model) {
        HashMap<String,Object> map = new HashMap<>();
        try {

            if(publicacionForm.getUrgencia().equals(Urgencia.FECHA) && publicacionForm.getFecha()==null){
                result.rejectValue("Fecha","Fecha","es requerída");
            }
            if(!result.hasErrors()){
                Publicacion publicacion = publicacionService.createPublicacion(publicacionForm);
                map.put("success", true);
                map.put("publicacion", new PublicacionForm(publicacion));
                map.put("msg","La publicación ha sido creada con éxito!");
            }else{
                map.put("success", false);
                map.put("errors", result.getAllErrors());
            }
        }catch (Exception e) {
            map.put("success", false);
            map.put("msg","Ha surgido un error, pruebe nuevamente más tarde");
        }
        return map;
    }

    @PreAuthorize("hasAuthority('TOMADOR')")
    @PostMapping(path="/edit")
    public String editPublicacion(@Valid @ModelAttribute("publicacion") PublicacionForm publicacionForm,@RequestParam Long primaryImage, BindingResult result, Model model, WebRequest webRequest, HttpServletRequest request, HttpServletResponse response) {
        try {

            if(publicacionForm.getUrgencia().equals(Urgencia.FECHA) && publicacionForm.getFecha()==null){
                result.rejectValue("Fecha","Fecha","es requerída");
            }

            if(!result.hasErrors()){
                Publicacion  publicacion = publicacionService.findById(publicacionForm.getId());
                if(publicacion!=null){
                    publicacionService.editPublicacion(publicacionForm,publicacion,primaryImage);
                    return "redirect:/publicacion/list";
                }else return "redirect:/publicacion/list";
            }else{
                addModelAttributes(model,publicacionForm,"edit");
                return "publicacion-new-edit";
            }
        }catch (Exception e) {
            addModelAttributes(model,publicacionForm,"edit");
            return "publicacion-new-edit";
        }

    }

    private List<SelectorForm> generarProvicias() {
        return usuarioService.getProvincias().stream().map(provincia -> new SelectorForm(provincia.getId(),provincia.getNombre())).collect(Collectors.toList());
    }


    public void addModelAttributes(Model model, PublicacionForm form, String formAction){
        model.addAttribute("provincias",generarProvicias());
        model.addAttribute("publicacion",form);
        model.addAttribute("tipos", publicacionService.getTipostrabajos());
        model.addAttribute("tiempos", TiempoPublicacion.values());
        model.addAttribute("urgencias", Urgencia.values());
        model.addAttribute("currencies", CurrencyCode.values());
        model.addAttribute("form_action",formAction);
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value="/uploadImage",method = RequestMethod.POST)
    public ResponseEntity uploadImage(MultipartHttpServletRequest request, @RequestParam long publicacionId){
        try{
            MultiValueMap<String, MultipartFile> inputFiles = request.getMultiFileMap();
            List<MultipartFile> mpfs = inputFiles.get("inputFiles");
            if(mpfs != null){
                for(MultipartFile file : mpfs){
                    publicacionService.saveImage(file, publicacionId);
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body("{}");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{error:"+e.getMessage()+"}");
        }

    }

    @PreAuthorize("hasAuthority('TOMADOR')")
    @PostMapping(path="/delete/{publicacionId}")
    public @ResponseBody Map<String,Object> deletePublicacion(@PathVariable Long publicacionId,Model model) {
        HashMap<String,Object> map = new HashMap<>();
        try {
            Publicacion publicacion = publicacionService.findById(publicacionId);
            publicacionService.deletePublicacion(publicacion);
            map.put("success", true);
        }catch (Exception e) {
            map.put("success", false);
        }
        return map;
    }

    @PreAuthorize("hasAuthority('TOMADOR')")
    @GetMapping(value="/mas/{publicacionId}")
    public String masPublicacion(@PathVariable Long publicacionId, WebRequest request, Model model) {
        Usuario user = currentSession.getUser();
        Publicacion mipublicacion = publicacionService.findById(publicacionId);
        if(mipublicacion!=null){
            if (user != null && user.getPrestador() != null) {
                model.addAttribute("publicacion", new PublicacionDTO(mipublicacion, getCover(mipublicacion), user));
            }
            else {
                model.addAttribute("publicacion", new PublicacionDTO(mipublicacion, getCover(mipublicacion)));
            }
            return "publicacion-mas";
        }
        return "redirect:/";

    }

    @PreAuthorize("hasAuthority('TOMADOR')")
    @GetMapping(value="/postulaciones/{publicacionId}")
    public String verPostulaciones(@PathVariable Long publicacionId, WebRequest request, Model model) {
        Publicacion mipublicacion = publicacionService.findById(publicacionId);
        if(mipublicacion!=null){
            List<Postulacion> postulaciones = postulacionService.findByPublicacion(mipublicacion);
            List<PostulacionDTO> postulacionDTOS = postulaciones.stream().map(postulacion -> new PostulacionDTO(postulacion, getCover(publicacionService.findById(postulacion.getPublicacion().getId())), usuarioService.findByPrestador(postulacion.getPrestador()), usuarioService.calcularCalificacionPromedio(postulacion.getPrestador()))).collect(Collectors.toList());
            model.addAttribute("publicacion",new PublicacionDTO(mipublicacion,getCover(mipublicacion)));
            model.addAttribute("postulaciones",postulacionDTOS);
            return "publicacion-postulaciones";
        }
        return "redirect:/";
    }

    @PreAuthorize("hasAuthority('PRESTADOR')")
    @GetMapping(value="/recomendados")
    public String listRecomendados(WebRequest request, Model model) {
        Usuario usuario = currentSession.getUser();
        if(usuario!=null && usuario.getPrestador()!=null){
            List<PublicacionDTO> publicacionDTOS = publicacionService.getTrabajosRecomendados(usuario.getPrestador().getTipos()).stream()
                    .map(publicacion -> new PublicacionDTO(publicacion,getCover(publicacion),usuario)).collect(Collectors.toList());
            model.addAttribute("trabajosRecomendados", publicacionDTOS);
        }
        return "trabajos-recomendados";
    }
}

