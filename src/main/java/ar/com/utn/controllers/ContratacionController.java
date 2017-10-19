package ar.com.utn.controllers;

import ar.com.utn.dto.PostulacionDTO;
import ar.com.utn.dto.PublicacionDTO;
import ar.com.utn.form.CurrencyCode;
import ar.com.utn.form.PublicacionForm;
import ar.com.utn.form.PublicacionFotoForm;
import ar.com.utn.form.SelectorForm;
import ar.com.utn.models.*;
import ar.com.utn.repositories.implementation.PublicacionSearch;
import ar.com.utn.services.MailService;
import ar.com.utn.services.PostulacionService;
import ar.com.utn.services.PublicacionService;
import ar.com.utn.services.UsuarioService;
import ar.com.utn.utils.CurrentSession;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by iaruedel on 18/10/17.
 */
@Controller
@RequestMapping(path="/contratar")
public class ContratacionController {
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

    @GetMapping(value = "/{postulacionId}")
    public String contratar(@PathVariable(value = "postulacionId") Long postulacionId,WebRequest request, Model model) {
        Postulacion postulacion = postulacionService.findById(postulacionId);
        if (postulacion!=null){
            Usuario usuario = usuarioService.findByPrestador(postulacion.getPrestador());
            model.addAttribute("postulacion",new PostulacionDTO(postulacion,getCover(postulacion.getPublicacion()),usuario));
            return "contratacion-postulacion";
        }
        return "redirect:/";
    }

    @PreAuthorize("hasAuthority('TOMADOR')")
    @PostMapping
    @ResponseBody
    @Transactional
    public Map<String, Object> contratarPostulacion(@RequestParam(value = "postulacionId") Postulacion postulacion) {
        Usuario usuario = currentSession.getUser();
        Publicacion publicacion = postulacion.getPublicacion();

        Usuario usuarioPostulacion = usuarioService.findByPrestador(postulacion.getPrestador());

        HashMap<String, Object> map = new HashMap<>();
        try {
            if (usuario.getTomador() != publicacion.getTomador()) {
                map.put("success", false);
                map.put("msg", "La publicación no es del usuario o está iniciado como profesional.");
                return map;
            }

            publicacion = publicacionService.setContratada(publicacion);
            postulacion = postulacionService.setContratada(postulacion);

            mailService.sendPostulacionElegidaMail(usuario, usuarioPostulacion, postulacion);

            map.put("success", true);
            map.put("msg", "Ha contratado a " + usuarioPostulacion.getUsername() + " correctamente.");
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "Ha surgido un error, pruebe nuevamente más tarde.");
        }
        return map;
    }

    private PublicacionFotoForm getCover(Publicacion publicacion) {
        PublicacionPhoto publicacionPhoto = publicacionService.getCover(publicacion);
        if(publicacionPhoto!=null){
            return new PublicacionFotoForm(publicacionPhoto);
        }else return null;

    }
}
