package ar.com.utn.controllers;

import ar.com.utn.configuration.ThreadPoolTaskSchedulerConfig;
import ar.com.utn.dto.ContratacionDTO;
import ar.com.utn.dto.PostulacionDTO;
import ar.com.utn.dto.PublicacionDTO;
import ar.com.utn.dto.UsuarioDTO;
import ar.com.utn.exception.MercadoPagoException;
import ar.com.utn.form.PublicacionFotoForm;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.util.*;
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
    @Autowired
    private ThreadPoolTaskSchedulerConfig taskScheduler;
    @Value("${app.mercadopago.public_key}")
    private String publicKey;

    @PreAuthorize("hasAuthority('TOMADOR')")
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

    @PreAuthorize("hasAuthority('TOMADOR')")
    @PostMapping(value = "/generarCodigoSeguridad")
    public @ResponseBody Map<String, Object> generarCodigoSeguridad(
            @RequestParam(value = "publicacionId") Publicacion publicacion,
            @RequestParam(value = "fecha") @DateTimeFormat(pattern="dd/MM/yyyy") LocalDate fecha,
            Model model) {
        HashMap<String, Object> map = new HashMap<>();

        Postulacion postulacion = postulacionService.findByPublicacionAndEstadoPostulacion(publicacion,EstadoPostulacion.CONTRATADA);
        Contratacion contratacion = contratacionService.findByPostulacion(postulacion);
        if(fecha.isAfter(LocalDate.now())) {
            contratacion.setFechaCodigo(fecha);
            contratacionRepository.save(contratacion);
            Usuario usuario = currentSession.getUser();
            //System.CurrentTimeInMilis() es ahora. se le suman milisegundos. 1000 ms = 1 s.
            taskScheduler.threadPoolTaskScheduler().schedule(new RunnableTask(contratacion,publicacion,usuario),new Date(System.currentTimeMillis()+(1000*60*2))); //Enviar codigo en 2 minutos
            map.put("success", true);
            map.put("msg", "Su codigo será enviado en la fecha solicitada.");
        }else{
            map.put("success", false);
            map.put("msg", "La fecha tiene que ser posterior a hoy.");
            return map;
        }

        return map;
        //return contratarPostu(postulacion, PayMethod.CASH, null, null);
    }

    //MAIL PARA ENVIAR CODIGO DE SEGURIDAD
    class RunnableTask implements Runnable{
        private Contratacion contratacion;
        private Publicacion publicacion;
        private Usuario currentUser;

        public RunnableTask(Contratacion contratacion, Publicacion publicacion, Usuario currentUser){
            this.contratacion = contratacion;
            this.publicacion = publicacion;
            this.currentUser = currentUser;
        }

        @Override
        public void run() {
            generarCodigoYEnviar(contratacion, publicacion, currentUser);
            System.out.println("Enviando mails de codigo de seguridad. Codigo: "+contratacion.getCodigoSeguridad());
        }
    }

    private void generarCodigoYEnviar(Contratacion contratacion, Publicacion publicacion, Usuario currentUser) {
        Long leftLimit = 1L;
        Long rightLimit = 999999L;
        Long generatedLong = leftLimit +  (long)(Math.random() * (rightLimit - leftLimit));
        contratacion.setCodigoSeguridad(generatedLong);
        Usuario prestador = usuarioService.findByPrestador(contratacion.getPostulacion().getPrestador());
        mailService.sendCodigoSeguridad(contratacion, publicacion, currentUser, prestador);
        contratacion.setCodigoEnviado(true);
        contratacionRepository.save(contratacion);
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
            PostulacionDTO postulacionDTO =  new PostulacionDTO(postulacion,getCover(publicacion),true);
            mailService.sendPostulacionElegidaMail(tomador, prestador, postulacionDTO);
            map.put("success", true);
            map.put("msg", "Ha contratado a " + prestador.getUsername() + " correctamente.");
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "Ha surgido un error, pruebe nuevamente más tarde.");
        }
        return map;
    }

    @PreAuthorize("hasAuthority('TOMADOR')")
    @PostMapping(value = "marcarNoRealizadoTomador")
    @Transactional(rollbackFor = {Exception.class})
    public @ResponseBody Map<String, Object> marcarNoRealizadoTomador(
            @RequestParam(value = "publicacionId") Publicacion publicacion, Model model){
        HashMap<String, Object> map = new HashMap<>();
        Usuario usuario = currentSession.getUser();
        try {
            Postulacion postulacion = postulacionService.findByPublicacionAndEstadoPostulacion(publicacion,EstadoPostulacion.CONTRATADA);
            if(postulacion != null){
                publicacion.setEstadoPublicacion(EstadoPublicacion.REVISION);
                postulacion.setEstadoPostulacion(EstadoPostulacion.REVISION);
                map.put("success", true);
                map.put("msg", "Has marcado el trabajo como no realizado. Revisaremos lo sucedido.");
            }
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "Ha surgido un error, pruebe nuevamente más tarde.");
        }
        return map;
    }

    @PreAuthorize("hasAuthority('PRESTADOR')")
    @PostMapping(value = "marcarNoRealizadoPrestador")
    @Transactional(rollbackFor = {Exception.class})
    public @ResponseBody Map<String, Object> marcarNoRealizadoPrestador(
            @RequestParam(value = "postulacionId") Postulacion postulacion, Model model){
        HashMap<String, Object> map = new HashMap<>();
        Usuario usuario = currentSession.getUser();
        try {
            Publicacion publicacion = postulacion.getPublicacion();
            if(publicacion != null){
                publicacion.setEstadoPublicacion(EstadoPublicacion.REVISION);
                postulacion.setEstadoPostulacion(EstadoPostulacion.REVISION);
                map.put("success", true);
                map.put("msg", "Has marcado el trabajo como no realizado. Revisaremos lo sucedido.");
            }
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
                    Usuario cli = usuarioService.findByTomador(publicacion.getTomador());
                    mailService.sendCalificacionMailToTomador(contratacion, cli, usuario);
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
        List<Postulacion> postulaciones = postulacionService.findByPublicacion(mipublicacion);
        if (postulaciones != null) {
            Postulacion mipostulacion = postulaciones.stream().filter(postulacion -> postulacion.getEstadoPostulacion() == EstadoPostulacion.CONTRATADA || postulacion.getEstadoPostulacion() == EstadoPostulacion.FINALIZADA).collect(Collectors.toList()).get(0);

            Boolean usuarioEsPrestador = currentSession.getActualRol().stream().anyMatch(o -> o.getAuthority().equalsIgnoreCase("PRESTADOR"));
            UsuarioDTO usuario;

            if (!usuarioEsPrestador) {
                usuario = new UsuarioDTO(usuarioService.findByPrestador(mipostulacion.getPrestador()), true);
            } else {
                usuario = new UsuarioDTO(usuarioService.findByTomador(mipublicacion.getTomador()), false);
            }

                Contratacion contratacion = contratacionService.findByPostulacion(mipostulacion);
                if (contratacion != null) {
                    model.addAttribute("postulacion", new PostulacionDTO(mipostulacion, getCover(mipublicacion), usuarioService.findByPrestador(mipostulacion.getPrestador())));
                    model.addAttribute("publicacion", new PublicacionDTO(mipublicacion, getCover(mipublicacion)));
                    model.addAttribute("contratacion", new ContratacionDTO(contratacion));
                    model.addAttribute("usuario", usuario);
                    return "contratacion-detalle";
                }
        }

        return "redirect:/";
    }

}
