package ar.com.utn.controllers;

import ar.com.utn.dto.TomadorDTO;
import ar.com.utn.form.PerfilForm;
import ar.com.utn.form.SelectorForm;
import ar.com.utn.form.TelefonoForm;
import ar.com.utn.form.TomadorForm;
import ar.com.utn.models.TipoDoc;
import ar.com.utn.models.Usuario;
import ar.com.utn.repositories.TipoTrabajoRepository;
import ar.com.utn.repositories.UsuarioRepository;
import ar.com.utn.services.UsuarioService;
import ar.com.utn.utils.CurrentSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by julis on 17/5/2017.
 */
@Controller
@RequestMapping(path="/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

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
        //model.addAttribute("tomadorForm",new TomadorForm(currentSession.getUser()));
        //model.addAttribute("perfilForm",new PerfilForm(currentSession.getUser()));
        model.addAttribute("user",currentSession.getUser());
        model.addAttribute("provincias", signupController.generarProvicias());
        //model.addAttribute("telefono",new TelefonoForm());
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
