package ar.com.utn.controllers;

import ar.com.utn.afip.AfipHandler;
import ar.com.utn.afip.AutenticadorConfig;
import ar.com.utn.afip.domain.Persona;
import ar.com.utn.afip.enums.AfipWs;
import ar.com.utn.exception.MercadoPagoException;
import ar.com.utn.form.PrestadorForm;
import ar.com.utn.form.SelectorForm;
import ar.com.utn.form.TelefonoForm;
import ar.com.utn.form.TomadorForm;
import ar.com.utn.mercadopago.MercadoPagoAdapter;
import ar.com.utn.mercadopago.model.ClientCredentials;
import ar.com.utn.models.*;
import ar.com.utn.repositories.PrestadorRepository;
import ar.com.utn.repositories.TipoTrabajoRepository;
import ar.com.utn.repositories.TomadorRepository;
import ar.com.utn.repositories.UsuarioRepository;
import ar.com.utn.services.PrestadorService;
import ar.com.utn.services.UsuarioService;
import ar.com.utn.utils.CurrentSession;
import com.mercadopago.MP;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sr.puc.server.ws.soap.a4.Actividad;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.Normalizer;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by julis on 17/5/2017.
 */
@Controller
@RequestMapping(path="/usuario")
public class UsuarioController {

    @Value("${app.mercadopago.app_id}")
    private String MP_APP_ID;
    @Value("${application.url}")
    private String URL;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PrestadorRepository prestadorRepository;

    @Autowired
    private TomadorRepository tomadorRepository;

    @Autowired
    private TipoTrabajoRepository tipoTrabajoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private SignupController signupController;

    @Autowired
    private CurrentSession currentSession;

    @Autowired
    private MercadoPagoAdapter mercadoPagoAdapter;
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

    @GetMapping(path="/add") // Map ONLY GET Requests
    public @ResponseBody String addNewUser (@RequestParam String name
            , @RequestParam String email) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Usuario n = new Usuario();
        n.setNombre(name);
        n.setEmail(email);
        usuarioRepository.save(n);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody List<Usuario> getAllUsers() {
        // This returns a JSON or XML with the users
        return (List<Usuario>) usuarioRepository.findAll();
    }

    @GetMapping(value="/perfil")
    public String perfilUsuario(WebRequest request, Model model) {

        if(currentSession.getUser()!=null && currentSession.getUser().getPrestador()!=null){
            model.addAttribute("app_id_mp",MP_APP_ID);
            model.addAttribute("redirect_uri",URL+"/usuario/mercadoPagoToken");

            if(currentSession.getUser().getPrestador().getMpPrestador()!=null && currentSession.getActualRol().stream().anyMatch(o -> o.getAuthority().equalsIgnoreCase("PRESTADOR"))){
                MP mp = new MP (currentSession.getUser().getPrestador().getMpPrestador().getAccessToken());
                try {
                    JSONObject response = mp.get ("/users/"+currentSession.getUser().getPrestador().getMpPrestador().getUserId());
                    JSONObject userMP = (JSONObject)response.get("response");
                    if(userMP!=null){
                        model.addAttribute("mp_name",userMP.get("first_name"));
                        model.addAttribute("mp_lastname",userMP.get("last_name"));
                        model.addAttribute("mp_username",userMP.get("nickname"));
                        model.addAttribute("mp_email",userMP.get("email"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        model.addAttribute("user",currentSession.getUser());
        model.addAttribute("provincias", signupController.generarProvicias());
        model.addAttribute("documentos", TipoDoc.values());
        model.addAttribute("tiposTrabajo",tipoTrabajoRepository.findAll());
        return "perfil-usuario";
    }

    @RequestMapping("/ajax/localidad")
    public String ajaxBrands(@RequestParam("provincia") String provinceId, Model model) {
        List<SelectorForm> models = usuarioService.findAllLocalidadByProvince(Long.parseLong(provinceId));
        model.addAttribute("user",currentSession.getUser());
        model.addAttribute("localidades", models);
        return "perfil-usuario :: localidad-fragment ";
    }

    private boolean validarTelefono(@Valid TelefonoForm telefono) {
        return true;
    }

    @RequestMapping(path="/perfil-tomador-json")
    public  @ResponseBody Map<String,Object> perfilTomadorJson(@Valid @ModelAttribute("tomadorForm") TomadorForm tomadorForm, BindingResult result, Model model, WebRequest webRequest, HttpServletRequest request, HttpServletResponse response){
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
            if(!result.hasErrors()){
                //ACTUALIZAR INFORMACION DEL USUARIO

                //usuarioService.registrarTomador(tomadorForm);
                map.put("success", true);
                map.put("msg","Los cambios han sido guardados correctamente.");
            }else{
                map.put("success", false);
                map.put("msg", result.getAllErrors());
            }
        }catch (Exception e) {
            map.put("success", false);
            map.put("msg","Ha surgido un error, pruebe nuevamente más tarde");
        }

        return map;
    }

    @RequestMapping(value = "/editar", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> editarUsuario(HttpSession session,
                                            @RequestParam(value = "id") Usuario usuario,
                                            @RequestParam(value = "nombre") String nombre,
                                            @RequestParam(value = "apellido") String apellido,
                                            @RequestParam(value = "email") String email,
                                            @RequestParam(value = "documento") String documento,
                                            @RequestParam(value = "codArea") String codArea,
                                            @RequestParam(value = "telefono") String telefono,
                                            @RequestParam(value = "localidad") Localidad localidad,
                                            @RequestParam(value = "tipos", required = false) List<TipoTrabajo> tipos){
        HashMap<String,Object> map = new HashMap<>();
        try{
            if(nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || documento.isEmpty() || codArea.isEmpty() || telefono.isEmpty() || localidad == null){
                map.put("success", false);
                map.put("msg", "Todos los campos son obilgatorios");
            }else{
                if(!usuarioService.emailUnique(email)) {
                    map.put("success", false);
                    map.put("msg","La dirección de Email ya está en uso.");
                    return map;
                }
                usuario.setNombre(nombre);
                usuario.setApellido(apellido);
                usuario.setDocumento(documento);
                usuario.setEmail(email);
                Telefono tel = usuario.getTelefono();
                tel.setCodArea(codArea);
                tel.setTelefono(telefono);
                Ubicacion u = usuario.getUbicacion();
                u.setLocalidad(localidad);
                if(tipos!=null){
                    if(tipos.size() > 0){
                        Prestador p = currentSession.getUser().getPrestador();
                        if(p!=null){
                            p.setTipos(tipos);
                            prestadorRepository.save(p);
                        }
                    }else{
                        map.put("success", false);
                        map.put("msg", "Todos los campos son obilgatorios.");
                        return map;
                    }
                }
                usuarioRepository.save(usuario);
                map.put("success", true);
                map.put("msg","Los cambios han sido guardados correctamente.");
            }
        }catch (Exception e) {
            map.put("success", false);
            map.put("msg","Ha surgido un error, pruebe nuevamente más tarde");
        }
        return map;
    }

    @RequestMapping(value = "/crearPerfilPrestador", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public Map<String,Object> crearPerfilPrestador(HttpSession session,
                                                   @RequestParam(value = "id") Usuario usuario,
                                                   @RequestParam(value = "tiposTrabajo[]") List<TipoTrabajo> tipos){
        HashMap<String,Object> map = new HashMap<>();
        try{
            if(tipos.isEmpty() || tipos==null){
                map.put("success", false);
                map.put("msg", "Todos los campos son obligatorios.");
            }else{
                Prestador p = new Prestador(tipos);
                p=prestadorRepository.save(p);
                usuario.setPrestador(p);
                usuario.addRole(Rol.PRESTADOR);
                usuarioRepository.save(usuario);

                usuarioService.setAuthority(Rol.PRESTADOR, usuario);

                map.put("success", true);
                map.put("msg","Los cambios han sido guardados correctamente.");
            }
        }catch (Exception e) {
            map.put("success", false);
            map.put("msg","Ha surgido un error, pruebe nuevamente más tarde.");
        }
        return map;
    }

    @RequestMapping(value = "/validarPerfilAfip", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> validarPerfilAfip(HttpSession session,
                                                @RequestParam(value = "id") Usuario usuario,
                                                @RequestParam(value = "cuit") String cuit,
                                                @RequestParam(value = "fecha_nac") @DateTimeFormat(pattern="dd/MM/yyyy") LocalDate fecha_nac,
                                                @RequestParam(value = "sexo") Sexo sexo){
        HashMap<String,Object> map = new HashMap<>();
        try{
            Long cuitL = Long.parseLong(cuit);
            //boolean cuitUnique = prestadorService.cuitUnique(cuitL);
            boolean cuitUnique = true;
            if(!cuitUnique){
                map.put("success", false);
                map.put("msg","El CUIT ingresado ya existe.");
                return map;
            }

            logger.info("Usuario quiere validar con AFIP. CUIT ingresada: {}",cuit);
            AutenticadorConfig autConfig =
                    new AutenticadorConfig(p12file, p12pass,
                            signer, dstdn, AfipWs.PADRON_CUATRO.getText(), ticketTime, endpoint);
            AfipHandler afip = new AfipHandler(AfipWs.PADRON_CUATRO,20389962237l, prestadorService, autConfig);
            Persona personaAfip = afip.getPersona(cuitL);
            if(personaAfip == null) {
                logger.info("AFIP no encuentra persona con CUIT: {}",cuit);
                map.put("success", false);
                map.put("msg","AFIP no ha encontrado una persona con ese CUIT.");
                return map;
            }
            String actividades = !personaAfip.getActividades().isEmpty()?personaAfip.getActividades().get(0).getDescripcionActividad():"Ninguna";
            logger.info("Datos encontrados:"+System.lineSeparator()+"CUIT: {}."+System.lineSeparator()+"Nombre y apellido: {}."+System.lineSeparator()+"Fecha nacimiento: {}."+System.lineSeparator()+"Actividad: {}."+System.lineSeparator()+"Direccion: {}.",personaAfip.getIdPersona().toString(),personaAfip.getNombreCompleto(),personaAfip.getNacimiento().toString(),actividades,(personaAfip.getDomicilio().get(0)==null)?"Ninguno":personaAfip.getDomicilio().get(0).getDireccion());
            if(!validarPersonaConAfip(personaAfip,usuario,cuitL,sexo,fecha_nac)) {
                map.put("success", false);
                map.put("msg","Los datos de AFIP no coinciden. Por favor, revise los datos ingresados.\n" +
                        "Nombre: "+personaAfip.getNombreCompleto()+"\n" +
                        "Cuit: "+personaAfip.getIdPersona().toString()+"\n"+
                        "Fecha Nac: "+personaAfip.getNacimiento().toString()+"\n" +
                        "Sexo: "+personaAfip.getSexo().getName()+"\n" +
                        "Actividad Principal: "+actividades+"\n"+
                        "Domicilio: "+personaAfip.getDomicilio().get(0).getDireccion());
            }else{
                prestadorService.validarDatosAfip(usuario.getPrestador(),cuitL,fecha_nac,sexo);
                map.put("success", true);
                map.put("msg","El usuario ha sido validado con exito!.\n" +
                        "Nombre: "+personaAfip.getNombreCompleto()+"\n" +
                        "Fecha Nac: "+personaAfip.getNacimiento().toString()+"\n" +
                        "Sexo: "+personaAfip.getSexo().getName()+"\n" +
                        "Actividad Principal: "+actividades+"\n"+
                        "Domicilio: "+personaAfip.getDomicilio().get(0).getDireccion());
            }
        }catch (Exception e){
            map.put("success", false);
            map.put("msg","Ha surgido un error, pruebe nuevamente más tarde");
            e.printStackTrace();
        }

        return map;
    }

    private boolean validarPersonaConAfip(Persona personaAfip, Usuario usuario, Long cuit, Sexo sexo, LocalDate fechaNac) {
        if(!normalizarTexto(personaAfip.getNombreCompleto()).equalsIgnoreCase(normalizarTexto(usuario.getApellido().trim() + " " +usuario.getNombre().trim()))) {
            logger.error("ERROR: Nombre y apellido no coinciden. Ingresado: {}. Obtenido AFIP: {}",usuario.getApellido().trim() + " " +usuario.getNombre().trim(),personaAfip.getNombreCompleto());
            return false;
        }

        if(!personaAfip.getNacimiento().equals(fechaNac)) {
            logger.error("ERROR: Fecha nacimiento no coincide. Ingresado: {}. Obtenido AFIP: {}",fechaNac,personaAfip.getNacimiento().toString());
            return false;
        }

        if(!sexo.equalsAfip(personaAfip.getSexo())) {
            logger.error("ERROR: Sexo no coincide. Ingresado: {}. Obtenido AFIP: {}",sexo.getName(),personaAfip.getSexo().getName());
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

    @RequestMapping(value = "/crearPerfilTomador", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> crearPerfilTomador(HttpSession session,
                                                   @RequestParam(value = "id") Usuario usuario){
        HashMap<String,Object> map = new HashMap<>();
        try{
            Tomador t = new Tomador();
            t=tomadorRepository.save(t);
            usuario.setTomador(t);
            usuario.addRole(Rol.TOMADOR);
            usuarioRepository.save(usuario);
            usuarioService.setAuthority(Rol.TOMADOR, usuario);
            map.put("success", true);
            map.put("msg","Los cambios han sido guardados correctamente.");
        }catch (Exception e) {
            map.put("success", false);
            map.put("msg","Ha surgido un error, pruebe nuevamente más tarde.");
        }
        return map;
    }

    @RequestMapping(value = "/cambiarRol", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> cambiarRol(@RequestParam(value = "id") Usuario usuario,
                                         @RequestParam(value = "rol") Rol rol){
        HashMap<String,Object> map = new HashMap<>();
        try{
            usuarioService.setAuthority(rol, usuario);
            map.put("success", true);
            map.put("msg","Se ha cambiado el rol correctamente.");
        }catch (Exception e) {
            map.put("success", false);
            map.put("msg","Ha surgido un error, pruebe nuevamente más tarde.");
        }
        return map;
    }

    public List<SelectorForm> generarProvicias() {
        return usuarioService.getProvincias().stream().map(provincia -> new SelectorForm(provincia.getId(),provincia.getNombre())).collect(Collectors.toList());
    }

    @PostMapping(path="/tomador")
    public String perfilTomador(@Valid @ModelAttribute("tomadorForm") TomadorForm tomadorForm, BindingResult result, Model model, WebRequest webRequest, HttpServletRequest request, HttpServletResponse response){
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
                //ACTUALIZAR INFO DE USUARIO

                //usuarioService.registrarTomador(tomadorForm);
                return "index";
            }else{
                model.addAttribute("provincias",generarProvicias());
                model.addAttribute("documentos", TipoDoc.values());
                return "perfil-usuario";
            }
        }catch (Exception e) {
            model.addAttribute("provincias",generarProvicias());
            model.addAttribute("documentos", TipoDoc.values());
            return "perfil-usuario";
        }
    }

    /**
     * use the token (code) given by mercadoPago to ask for the user credentials
     * @param code given by MercadoPago.
     * @param error
     * @param redirectAttributes
     * @param request
     * @return
     */
    @RequestMapping(value="/mercadoPagoToken")
    public String mercadoPagoToken(@RequestParam(required=false) String code,
                                   @RequestParam(required=false) String error,RedirectAttributes redirectAttributes,
                                   HttpServletRequest request){
        if(error!=null){
            if(error.equals("access-denied")){
                error = "Debes darnos permisos a tu cuenta de MercadoPago para poder continuar";
            }
        }else if(code != null){
            //permissions conceded
            try {
                String contextPath = request.getContextPath();
                ClientCredentials clientCredentials = mercadoPagoAdapter.getClientCredentials(code, this.URL+contextPath+"/usuario/mercadoPagoToken");
                prestadorService.completeCredentials(clientCredentials);
            } catch (MercadoPagoException e) {
                e.printStackTrace();
                error = "Se ha producido un error al obtener datos de MercadoPago";
            }
        }
        if(error != null){

            redirectAttributes.addFlashAttribute("errorMsg", error);
        }

        return "redirect:/usuario/perfil";
    }

    @GetMapping(path="/validateMercadoPago")
    public @ResponseBody Map<String,Object> validateMercadoPago () {
        HashMap<String,Object> map = new HashMap<>();
        Usuario usuario = currentSession.getUser();
        if( usuario!=null && usuario.getPrestador()!=null && usuario.getPrestador().getMpPrestador()!=null){
            map.put("success", true);
        }else{
            map.put("success", false);
        }

        return map;
    }

}
