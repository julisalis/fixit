package ar.com.utn.controllers;

import ar.com.utn.dto.PostulacionDTO;
import ar.com.utn.exception.MercadoPagoException;
import ar.com.utn.form.PublicacionFotoForm;
import ar.com.utn.mercadopago.MoneyFlowService;
import ar.com.utn.models.*;
import ar.com.utn.repositories.implementation.PublicacionSearch;
import ar.com.utn.services.*;
import ar.com.utn.utils.CurrentSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

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
    private PrestadorService prestadorService;
    @Autowired
    private CurrentSession currentSession;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private MailService mailService;

    @Autowired
    private PublicacionSearch publicacionSearch;
    private MoneyFlowService moneyFlowService;

    @GetMapping(value = "/{postulacionId}")
    public String contratar(@PathVariable(value = "postulacionId") Long postulacionId,WebRequest request, Model model) {
        Postulacion postulacion = postulacionService.findById(postulacionId);
        if (postulacion!=null){
            Usuario usuario = null;
            try {
                usuario = prestadorService.findByPrestadorRenewMP(postulacion.getPrestador());
            } catch (MercadoPagoException e) {
                return "redirect:/";
            }
            PostulacionDTO postulacionDTO = new PostulacionDTO(postulacion,getCover(postulacion.getPublicacion()),usuario);
            model.addAttribute("MPPublicKey", postulacion.getPrestador().getMpPrestador().getAccessToken());

            model.addAttribute("postulacion",postulacionDTO);
            return "contratacion-postulacion";
        }
        return "redirect:/";
    }

    @PreAuthorize("hasAuthority('TOMADOR')")
    @PostMapping
    @ResponseBody
    @Transactional
    public Map<String, Object> contratarPostulacion(
                                                    @RequestParam(required=false) String tokenMP,
                                                    @RequestParam(required=false) String paymentMethodId,
                                                    @RequestParam(value = "postulacionId") Postulacion postulacion
                                                    ,Model model) {

        Usuario usuario = currentSession.getUser();
        Publicacion publicacion = postulacion.getPublicacion();
        HashMap<String, Object> map = new HashMap<>();
        Usuario usuarioPostulacion = usuarioService.findByPrestador(postulacion.getPrestador());
        try {

            if (usuario.getTomador() != publicacion.getTomador()) {
                map.put("success", false);
                map.put("msg", "La publicación no es del usuario o está iniciado como profesional.");
                return map;
            }

            publicacion = publicacionService.setContratada(publicacion);
            postulacion = postulacionService.setContratada(postulacion);

            moneyFlowService.makePaymentMP(postulacion, tokenMP, paymentMethodId, usuario);

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
