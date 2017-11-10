package ar.com.utn.controllers;

import ar.com.utn.dto.ContratacionDTO;
import ar.com.utn.dto.PostulacionDTO;
import ar.com.utn.dto.PublicacionDTO;
import ar.com.utn.exception.MercadoPagoException;
import ar.com.utn.form.PublicacionFotoForm;
import ar.com.utn.mercadopago.MercadoPagoApi;
import ar.com.utn.mercadopago.MoneyFlowService;
import ar.com.utn.mercadopago.PaymentMP;
import ar.com.utn.mercadopago.PaymentMPRepository;
import ar.com.utn.models.*;
import ar.com.utn.repositories.ContratacionRepository;
import ar.com.utn.services.*;
import ar.com.utn.utils.CurrentSession;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
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
    private PrestadorService prestadorService;
    @Autowired
    private CurrentSession currentSession;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private MailService mailService;
    @Autowired
    private ContratacionRepository contratacionRepository;
    @Autowired
    private ContratacionService contratacionService;
    @Autowired
    PaymentMPRepository paymentMPRepository;
    @Autowired
    private MoneyFlowService moneyFlowService;
    @Value("${app.mercadopago.public_key}")
    private String publicKey;


    @GetMapping(value = "/{postulacionId}")
    public String contratar(@PathVariable(value = "postulacionId") Long postulacionId, WebRequest request, Model model) {
        Postulacion postulacion = postulacionService.findById(postulacionId);
        if (postulacion != null) {
            if (postulacion.getEstadoPostulacion().equals(EstadoPostulacion.CONTRATADA)) {
                return "redirect:/";
            }
            Usuario usuario = null;
            try {
                usuario = prestadorService.findByPrestadorRenewMP(postulacion.getPrestador());
            } catch (MercadoPagoException e) {
                return "redirect:/";
            }
            PostulacionDTO postulacionDTO = new PostulacionDTO(postulacion, getCover(postulacion.getPublicacion()), usuario);
            model.addAttribute("MPPublicKey", publicKey);
            model.addAttribute("postulacion", postulacionDTO);
            return "contratacion-postulacion";
        }
        return "redirect:/";
    }

    @PreAuthorize("hasAuthority('TOMADOR')")
    @PostMapping
    @Transactional(rollbackFor = {Exception.class})
    public @ResponseBody
    Map<String, Object> contratarPostulacion(
            @RequestParam(required = false) String tokenMP,
            @RequestParam(required = false) String paymentMethodId,
            @RequestParam(value = "postulacionId") Postulacion postulacion
            , Model model) {
        return contratarPostu(postulacion, PayMethod.CREDIT_CARD, tokenMP, paymentMethodId);
    }

    @PreAuthorize("hasAuthority('TOMADOR')")
    @PostMapping(value = "/contratarCash")
    @Transactional(rollbackFor = {Exception.class})
    public @ResponseBody
    Map<String, Object> contratarPostulacionCash(
            @RequestParam(value = "postulacionId") Postulacion postulacion
            , Model model) {

        return contratarPostu(postulacion, PayMethod.CASH, null, null);
    }

    private PublicacionFotoForm getCover(Publicacion publicacion) {
        PublicacionPhoto publicacionPhoto = publicacionService.getCover(publicacion);
        if (publicacionPhoto != null) {
            return new PublicacionFotoForm(publicacionPhoto);
        } else return null;

    }

    private HashMap<String, Object> contratarPostu(Postulacion postulacion, PayMethod payMethod, String tokenMP, String paymentMethodId) {
        Publicacion publicacion = postulacion.getPublicacion();
        HashMap<String, Object> map = new HashMap<>();
        Contratacion contratacion;
        Usuario prestador = usuarioService.findByPrestador(postulacion.getPrestador());
        Usuario tomador = usuarioService.findByTomador(publicacion.getTomador());
        try {
            if (payMethod.equals(PayMethod.CREDIT_CARD)) {
                PaymentMP paymentMP = moneyFlowService.completePayment(postulacion, tokenMP, paymentMethodId,tomador);
                paymentMPRepository.save(paymentMP);
                contratacion = new Contratacion(postulacion, payMethod, paymentMP);
            } else {
                contratacion = new Contratacion(postulacion, payMethod);
            }
            publicacionService.setContratada(publicacion);
            postulacionService.setContratada(postulacion);
            contratacionRepository.save(contratacion);
            mailService.sendPostulacionElegidaMail(tomador, prestador, postulacion);
            map.put("success", true);
            map.put("msg", "Ha contratado a " + prestador.getUsername() + " correctamente.");
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "Ha surgido un error, pruebe nuevamente más tarde.");
        }
        return map;
    }


    @PreAuthorize("hasAuthority('TOMADOR')")
    @PostMapping(value = "calificarPagarTomador")
    @Transactional(rollbackFor = {Exception.class})
    public @ResponseBody Map<String, Object> calificarPagarTomador(
            @RequestParam(value = "publicacionId") Publicacion publicacion,
            @RequestParam(value = "calificacion") Double calificacion
            , Model model) {
        HashMap<String, Object> map = new HashMap<>();
        Usuario usuario = currentSession.getUser();
        Postulacion postulacion = postulacionService.findByPublicacionAndEstadoPostulacion(publicacion,EstadoPostulacion.CONTRATADA);
        String description = "La calificación se ha generado con éxito.";
        if (postulacion != null) {
            Contratacion contratacion = contratacionService.findByPostulacion(postulacion);
            if(contratacion.getCalificacionTomador() == null){
                    if(contratacion.getCalificacionPrestador()!=null){
                        try {
                            if (contratacion.getPayMethod().equals(PayMethod.CREDIT_CARD)) {
                                JSONObject payment =  moneyFlowService.efectuarPago(contratacion.getPaymentMP(),postulacion.getPrestador());
                                String paymentId = moneyFlowService.getPaymentId(payment);
                                description = moneyFlowService.getDescription(payment);
                                contratacion.setPaymentId(paymentId);
                            }

                        } catch (Exception e) {
                            map.put("success", false);
                            map.put("msg", "Ha surgido un error, pruebe nuevamente más tarde.");
                        }
                        publicacionService.setFinalizada(publicacion);
                        postulacionService.setFinalizada(postulacion);
                    }else{
                        if (contratacion.getPayMethod().equals(PayMethod.CREDIT_CARD)) {
                            description = "La calificación se ha generado con éxito, el pago se efectuará cuando el Profesional califique.";
                        }
                    }
                    contratacion.setCalificacionTomador(calificacion);
                    Usuario prof = usuarioService.findByPrestador(postulacion.getPrestador());
                    mailService.sendCalificacionMailToProfesional(contratacion,usuario,prof);
                    map.put("success", true);
                    map.put("msg",description);
            } else {
                map.put("success", false);
                map.put("msg", "Usted ya ha calificado al profesional.");
            }
        } else {
            map.put("success", false);
            map.put("msg", "Ha surgido un error, pruebe nuevamente más tarde.");
        }
        return map;
    }

    @PreAuthorize("hasAuthority('PRESTADOR')")
    @PostMapping(value = "calificarPagarPrestador")
    @Transactional(rollbackFor = {Exception.class})
    public @ResponseBody Map<String, Object> calificarPagarPrestador(
            @RequestParam(value = "postulacionId") Postulacion postulacion,
            @RequestParam(value = "calificacion") Double calificacion
            , Model model) {
        HashMap<String, Object> map = new HashMap<>();
        Usuario usuario = currentSession.getUser();
        String description = "";
        if (postulacion != null) {
            Publicacion publicacion = postulacion.getPublicacion();
            Contratacion contratacion = contratacionService.findByPostulacion(postulacion);
            if(contratacion.getCalificacionPrestador() == null) {
                    if(contratacion.getCalificacionTomador()!=null){
                        try {
                            if (contratacion.getPayMethod().equals(PayMethod.CREDIT_CARD)) {
                                JSONObject payment =  moneyFlowService.efectuarPago(contratacion.getPaymentMP(),postulacion.getPrestador());
                                String paymentId = moneyFlowService.getPaymentId(payment);
                                contratacion.setPaymentId(paymentId);
                            }
                        } catch (Exception e) {
                            map.put("success", false);
                            map.put("msg", "Ha surgido un error, pruebe nuevamente más tarde.");
                            return map;
                        }
                        publicacionService.setFinalizada(publicacion);
                        postulacionService.setFinalizada(postulacion);
                    }
                    contratacion.setCalificacionPrestador(calificacion);
                    Usuario prof = usuarioService.findByPrestador(postulacion.getPrestador());
                    mailService.sendCalificacionMailToProfesional(contratacion, usuario, prof);
                    map.put("success", true);
                    map.put("msg", "El trabajo ha finalizado con éxito, gracias por confiar en FixIT.");
            } else {
                map.put("success", false);
                map.put("msg", "Usted ya ha calificado al cliente.");
            }
        } else {
            map.put("success", false);
            map.put("msg", "Ha surgido un error, pruebe nuevamente más tarde.");

        }


        return map;

    }

    @GetMapping(value = "calificar/{publicacionId}")
    public String calificar(@PathVariable(value = "publicacionId") Long publicacionId, WebRequest request, Model model) {
        Publicacion publicacion = publicacionService.findById(publicacionId);
        Postulacion postulacion = postulacionService.findByPublicacionAndEstadoPostulacion(publicacion,EstadoPostulacion.CONTRATADA);
        if(postulacion.getEstadoPostulacion().equals(EstadoPostulacion.CONTRATADA)){
            Contratacion contratacion = contratacionService.findByPostulacion(postulacion);
            if(contratacion!=null) {
                model.addAttribute("contratacion",contratacion);
                return "calificar-postulacion";
            }
        }

        return "redirect:/";
    }

    @GetMapping(value="/detalle/{publicacionId}")
    public String detalleContratacion(@PathVariable Long publicacionId, WebRequest request, Model model) {
        Publicacion mipublicacion = publicacionService.findById(publicacionId);
        Postulacion mipostulacion = postulacionService.findByPublicacionAndEstadoPostulacion(mipublicacion, EstadoPostulacion.CONTRATADA);

        if(mipostulacion.getEstadoPostulacion().equals(EstadoPostulacion.CONTRATADA)){
            Contratacion contratacion = contratacionService.findByPostulacion(mipostulacion);
            if(contratacion!=null) {
                model.addAttribute("postulacion",new PostulacionDTO(mipostulacion,getCover(mipublicacion),usuarioService.findByPrestador(mipostulacion.getPrestador())));
                model.addAttribute("publicacion",new PublicacionDTO(mipublicacion,getCover(mipublicacion)));
                model.addAttribute("contratacion",new ContratacionDTO(contratacion));
                return "contratacion-detalle";
            }
        }

        return "redirect:/";
    }

}
