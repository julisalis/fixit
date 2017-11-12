package ar.com.utn.controllers;

import ar.com.utn.afip.AfipHandler;
import ar.com.utn.afip.AutenticadorConfig;
import ar.com.utn.afip.domain.Persona;
import ar.com.utn.afip.enums.AfipWs;
import ar.com.utn.form.PrestadorForm;
import ar.com.utn.form.SelectorForm;
import ar.com.utn.form.TelefonoForm;
import ar.com.utn.form.TomadorForm;
import ar.com.utn.models.*;
import ar.com.utn.services.PrestadorService;
import ar.com.utn.services.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import sr.puc.server.ws.soap.a4.Actividad;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.Normalizer;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

    /**
     * Created by julian on 30/07/17.
     */
    @Controller
    @RequestMapping(path="/signup")
    public class SignupController {

        @Autowired
        private UsuarioService usuarioService;
        @Autowired
        private PrestadorService prestadorService;


        //AFIP//
        @Value("${app.afip.ws.endpoint}")
        private String endpoint;
        @Value("${app.afip.ws.dstdn}")
        private String dstdn;
        @Value("${app.afip.ws.p12file}")
        private String p12file;
        @Value("${app.afip.ws.signer}")
        private String signer;
        @Value("${app.afip.ws.p12pass}")
        private String p12pass;
        @Value("${app.afip.ws.ticketTime}")
        private Long ticketTime;

        private final Logger logger = LoggerFactory.getLogger("afip-log");
        //AFIP//

        @GetMapping(value="/prestador")
        public String signupPrestador(WebRequest request,Model model) {
            model.addAttribute("prestadorForm",new PrestadorForm());
            model.addAttribute("provincias",generarProvicias());
            model.addAttribute("telefono",new TelefonoForm());
            model.addAttribute("documentos", TipoDoc.values());
            model.addAttribute("tiposTrabajos", generarTiposTrabajos());
            model.addAttribute("sexos", Sexo.values());
            return "signup-prestador";
        }

        @GetMapping(value="/tomador")
        public String signupTomador(WebRequest request,Model model) {
            model.addAttribute("provincias",generarProvicias());
            model.addAttribute("tomadorForm",new TomadorForm());
            model.addAttribute("telefono",new TelefonoForm());
            model.addAttribute("documentos", TipoDoc.values());
            return "signup-tomador";
        }

        public List<SelectorForm> generarProvicias() {
            return usuarioService.getProvincias().stream().map(provincia -> new SelectorForm(provincia.getId(),provincia.getNombre())).collect(Collectors.toList());
        }

        @RequestMapping("/ajax/tiposTrabajo")
        @ResponseBody
        public List<SelectorForm> generarTiposTrabajos() {
            return prestadorService.getTiposTrabajos().stream().map(tipoTrabajo -> new SelectorForm(tipoTrabajo.getId(),tipoTrabajo.getNombre())).collect(Collectors.toList());
        }

        @RequestMapping("/ajax/localidad")
        public String ajaxBrands(@RequestParam("provincia") String provinceId, Model model) {
            List<SelectorForm> models = usuarioService.findAllLocalidadByProvince(Long.parseLong(provinceId));
            model.addAttribute("localidades", models);
            return "signup-tomador :: localidad-fragment ";
        }

        @PostMapping(path="/prestador")
        public String signupPrestador(@Valid @ModelAttribute("prestadorForm") PrestadorForm prestadorForm, BindingResult result, Model model){
            validarTelefono(prestadorForm.getTelefono());

            try{
                if(!result.hasErrors()){
                    boolean validationResult=false;
                    //Prestador prestador = new Prestador(prestadorForm.getCuit(),validationResult);
                    usuarioService.registrarPrestador(prestadorForm);
                    return "/";
                }else{
                    return "signup-prestador";
                }
            }catch (Exception e) {
                return "signup-prestador";
            }
        }

        @RequestMapping(path="/prestador-json")
        public  @ResponseBody Map<String,Object> signupPrestadorJson(@Valid @ModelAttribute("prestadorForm") PrestadorForm prestadorForm, BindingResult result, Model model, WebRequest webRequest, HttpServletRequest request, HttpServletResponse response){
            HashMap<String,Object> map = new HashMap<>();
            try{
                validarTelefono(prestadorForm.getTelefono());
                boolean userUnique = usuarioService.usernameUnique(prestadorForm.getUsername());
                boolean mailUnique = usuarioService.emailUnique(prestadorForm.getEmail());
                if(!userUnique){
                    result.rejectValue("username","username.repeat","El nombre de usuario ingresado ya existe");
                }
                if (!mailUnique){
                    result.rejectValue("email","email.repeat","El email ingresado ya existe");
                }
                if (!prestadorForm.getPassword().equals(prestadorForm.getRepeatPassword())){
                    result.rejectValue("repeatPassword","repeatPassword.repeat","Las contraseñas no coinciden");
                    result.rejectValue("password","password.repeat","Las contraseñas no coinciden");
                }

                if(prestadorForm.getValidar()) {
                    if(prestadorForm.getCuit()==null || prestadorForm.getNacimiento() == null || prestadorForm.getSexo() == null) {
                        result.rejectValue("validar","validar.campos", "Todos los campos obligatorios");
                    } else {

                        if (!prestadorService.cuitUnique(prestadorForm.getCuit())) {
                            result.rejectValue("cuit", "cuit.repeat", "El cuit ingresado ya existe");
                        }
                    }
                }

                if(!result.hasErrors()){
                    if(prestadorForm.getValidar()){
                        logger.info("Usuario quiere validar con AFIP. CUIT ingresada: {}",prestadorForm.getCuit().toString());
                        AutenticadorConfig autConfig =
                                new AutenticadorConfig(p12file, p12pass,
                                        signer, dstdn, AfipWs.PADRON_CUATRO.getText(), ticketTime, endpoint);
                        AfipHandler afip = new AfipHandler(AfipWs.PADRON_CUATRO,20389962237l, prestadorService, autConfig);
                        Persona personaAfip = afip.getPersona(prestadorForm.getCuit());
                        if(personaAfip == null) {
                            logger.info("AFIP no encuentra persona con CUIT: {}",prestadorForm.getCuit().toString());
                            map.put("success", false);
                            map.put("msg","AFIP no ha encontrado una persona con ese CUIT.");
                            return map;
                        }
                        String actividades = !personaAfip.getActividades().isEmpty()?personaAfip.getActividades().get(0).getDescripcionActividad():"Ninguna";
                        logger.info("Datos encontrados:"+System.lineSeparator()+"CUIT: {}."+System.lineSeparator()+"Nombre y apellido: {}."+System.lineSeparator()+"Fecha nacimiento: {}."+System.lineSeparator()+"Actividad: {}."+System.lineSeparator()+"Direccion: {}.",personaAfip.getIdPersona().toString(),personaAfip.getNombreCompleto(),personaAfip.getNacimiento().toString(),actividades,(personaAfip.getDomicilio().get(0)==null)?"Ninguno":personaAfip.getDomicilio().get(0).getDireccion());
                        if(!validarPersonaConAfip(personaAfip,prestadorForm)) {
                            map.put("success", false);
                            map.put("msg","Los datos de AFIP no coinciden. Por favor, revise los datos ingresados o deseleccione la validación.\n" +
                                    "Nombre: "+personaAfip.getNombreCompleto()+"\n" +
                                    "Cuit: "+personaAfip.getIdPersona().toString()+"\n"+
                                    "Fecha Nac: "+personaAfip.getNacimiento().toString()+"\n" +
                                    "Sexo: "+personaAfip.getSexo().getName()+"\n" +
                                    "Actividad Principal: "+actividades+"\n"+
                                    "Domicilio: "+personaAfip.getDomicilio().get(0).getDireccion());
                        }else{
                            usuarioService.registrarPrestador(prestadorForm);
                            map.put("success", true);
                            map.put("msg","El usuario ha sido creado con éxito! Se ha enviado un correo electrónico a su cuenta con el link de activación.\n" +
                                    "Nombre: "+personaAfip.getNombreCompleto()+"\n" +
                                    "Fecha Nac: "+personaAfip.getNacimiento().toString()+"\n" +
                                    "Sexo: "+personaAfip.getSexo().getName()+"\n" +
                                    "Actividad Principal: "+actividades+"\n"+
                                    "Domicilio: "+personaAfip.getDomicilio().get(0).getDireccion());
                        }
                    }else{
                        usuarioService.registrarPrestador(prestadorForm);
                        map.put("success", true);
                        map.put("msg","El usuario ha sido creado con éxito! Se ha enviado un correo electrónico a su cuenta con el link de activación.");
                    }
                }else{
                    map.put("success", false);
                    map.put("errors", result.getAllErrors());
                }
            }catch (Exception e) {
                map.put("success", false);
                map.put("msg","Ha surgido un error, pruebe nuevamente más tarde");
                e.printStackTrace();
            }

            return map;
        }

        private boolean validarPersonaConAfip(Persona personaAfip, PrestadorForm pf) {
            if(!normalizarTexto(personaAfip.getNombreCompleto()).equalsIgnoreCase(normalizarTexto(pf.getApellido().trim() + " " +pf.getNombre().trim()))) {
                logger.error("ERROR: Nombre y apellido no coinciden. Ingresado: {}. Obtenido AFIP: {}",pf.getApellido().trim() + " " +pf.getNombre().trim(),personaAfip.getNombreCompleto());
                return false;
            }

            if(!personaAfip.getNacimiento().equals(pf.getNacimiento())) {
                logger.error("ERROR: Fecha nacimiento no coincide. Ingresado: {}. Obtenido AFIP: {}",pf.getNacimiento().toString(),personaAfip.getNacimiento().toString());
                return false;
            }

            if(!pf.getSexo().equalsAfip(personaAfip.getSexo())) {
                logger.error("ERROR: Sexo no coincide. Ingresado: {}. Obtenido AFIP: {}",pf.getSexo().getName(),personaAfip.getSexo().getName());
                return false;
            }

            if(!actividadValida(personaAfip.getActividades())) {
                logger.error("ERROR: Tipos de trabajo ingresados no coinciden con actividades obtenidas.");
                return false;
            }
            logger.info("Todos los datos correctos. Validación aceptada.");
            return true;
        }

        private String normalizarTexto(String s) {
            s = Normalizer.normalize(s, Normalizer.Form.NFD);
            s = s.replaceAll("[^\\p{ASCII}]", "");
            s = s.replaceAll("[^-a-zA-Z0-9]", "");
            s = s.replaceAll("[AEIOUaeiou]", "");
            return s;
        }

        private boolean actividadValida(List<Actividad> actividades) {
            Boolean valido = false;
            for (Actividad a : actividades) {
                if (prestadorService.getActividadesAfip().stream().anyMatch(af -> af.getDescripcion().trim().equalsIgnoreCase(a.getDescripcionActividad().trim()))) {
                    valido = true;
                }
            }
            return valido;
        }

            private boolean validarTelefono(@Valid TelefonoForm telefono) {
            return true;
        }

        @PostMapping(path="/tomador")
        public String signupTomador(@Valid @ModelAttribute("tomadorForm") TomadorForm tomadorForm, BindingResult result, Model model, WebRequest webRequest, HttpServletRequest request, HttpServletResponse response){
            try{
                validarTelefono(tomadorForm.getTelefono());
                boolean userUnique = usuarioService.usernameUnique(tomadorForm.getUsername());
                boolean mailUnique = usuarioService.emailUnique(tomadorForm.getEmail());
                if(!userUnique){
                    result.rejectValue("username","username.repeat","El nombre de usuario ingresado ya existe");
                }
                if (!mailUnique){
                    result.rejectValue("email","email.repeat","El email ingresado ya existe");
                }
                if(!result.hasErrors()){
                    usuarioService.registrarTomador(tomadorForm);
                    return "index";
                }else{
                    model.addAttribute("provincias",generarProvicias());
                    model.addAttribute("documentos", TipoDoc.values());
                    return "signup-tomador";
                }
            }catch (Exception e) {
                model.addAttribute("provincias",generarProvicias());
                model.addAttribute("documentos", TipoDoc.values());
                return "signup-tomador";
            }
        }

        @RequestMapping(path="/tomador-json")
        public  @ResponseBody Map<String,Object> signupTomadorJson(@Valid @ModelAttribute("tomadorForm") TomadorForm tomadorForm, BindingResult result, Model model, WebRequest webRequest, HttpServletRequest request, HttpServletResponse response){
            HashMap<String,Object> map = new HashMap<>();
            try{
                validarTelefono(tomadorForm.getTelefono());
                boolean userUnique = usuarioService.usernameUnique(tomadorForm.getUsername());
                boolean mailUnique = usuarioService.emailUnique(tomadorForm.getEmail());
                if(!userUnique){
                    result.rejectValue("username","username.repeat","El nombre de usuario ingresado ya existe");
                }
                if (!mailUnique){
                    result.rejectValue("email","email.repeat","El email ingresado ya existe");
                }
                if (!tomadorForm.getPassword().equals(tomadorForm.getRepeatPassword())){
                    result.rejectValue("repeatPassword","repeatPassword.repeat","Las contraseñas no coinciden");
                    result.rejectValue("password","password.repeat","Las contraseñas no coinciden");
                }
                if(!result.hasErrors()){
                    usuarioService.registrarTomador(tomadorForm);
                    map.put("success", true);
                    map.put("msg","El usuario ha sido creado con éxito! Se ha enviado un correo electrónico a su cuenta con el link de activación.");
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


        @GetMapping(value="/activate/{token}")
        public String activateAccount(@PathVariable("token") String token){
            try{
                usuarioService.activateUser(token);
            }catch (Exception e) {
            }
            return "redirect:/";
        }


}
