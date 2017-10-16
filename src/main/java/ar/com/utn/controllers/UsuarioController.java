package ar.com.utn.controllers;

import ar.com.utn.form.SelectorForm;
import ar.com.utn.form.TelefonoForm;
import ar.com.utn.form.TomadorForm;
import ar.com.utn.models.*;
import ar.com.utn.repositories.PrestadorRepository;
import ar.com.utn.repositories.TipoTrabajoRepository;
import ar.com.utn.repositories.TomadorRepository;
import ar.com.utn.repositories.UsuarioRepository;
import ar.com.utn.services.UsuarioService;
import ar.com.utn.utils.CurrentSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
        model.addAttribute("app_id_mp",MP_APP_ID);
        model.addAttribute("redirect_uri",URL+"/signup/mercadoPagoToken");
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
                map.put("errors", result.getAllErrors());
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
                                            @RequestParam(value = "localidad") Localidad localidad){
        HashMap<String,Object> map = new HashMap<>();
        try{
            if(nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || documento.isEmpty() || codArea.isEmpty() || telefono.isEmpty() || localidad == null){
                map.put("success", false);
                map.put("errors", "Todos los campos son obilgatorios");
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
                map.put("errors", "Todos los campos son obligatorios.");
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
}
