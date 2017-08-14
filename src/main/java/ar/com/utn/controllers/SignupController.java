package ar.com.utn.controllers;

import ar.com.utn.form.PrestadorForm;
import ar.com.utn.form.SelectorForm;
import ar.com.utn.form.TelefonoForm;
import ar.com.utn.form.TomadorForm;
import ar.com.utn.models.Prestador;
import ar.com.utn.models.Telefono;
import ar.com.utn.models.TipoDoc;
import ar.com.utn.models.Usuario;
import ar.com.utn.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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



    @GetMapping(value="/prestador")
    public String signupPrestador(WebRequest request,Model model) {
        model.addAttribute("prestadorForm",new PrestadorForm());
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

    private List<SelectorForm> generarProvicias() {
       return usuarioService.getProvincias().stream().map(provincia -> new SelectorForm(provincia.getId(),provincia.getNombre())).collect(Collectors.toList());
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
                Prestador prestador = new Prestador(prestadorForm.getCuit(),validationResult);
                usuarioService.registrarPrestador(prestadorForm,prestador);
                return "/";
            }else{
                return "signup-prestador";
            }
        }catch (Exception e) {
            return "signup-prestador";
        }

    }

    private boolean validarTelefono(TelefonoForm telefono) {
        return true;
    }

    @PostMapping(path="/tomador")
    public String signupTomador(@Valid @ModelAttribute("tomadorForm") TomadorForm tomadorForm, BindingResult result, Model model, WebRequest webRequest, HttpServletRequest request, HttpServletResponse response){
        try{
            validarTelefono(tomadorForm.getTelefono());
            boolean userUnique = usuarioService.usernameUnique(tomadorForm.getUsername());
            boolean mailUnique = usuarioService.emailUnique(tomadorForm.getEmail());
            if(!userUnique){
                result.addError(new ObjectError("username","El nombre de usuario ingresado ya existe"));
            }
            if (!mailUnique){
                result.addError(new ObjectError("email","El email ingresado ya existe"));
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

    @PostMapping(path="/tomador-json")
    public  @ResponseBody Map<String,Object> signupTomadorJson(@Valid @ModelAttribute("tomadorForm") TomadorForm tomadorForm, BindingResult result, Model model, WebRequest webRequest, HttpServletRequest request, HttpServletResponse response){
        HashMap<String,Object> map = new HashMap<>();
        try{
            validarTelefono(tomadorForm.getTelefono());
            boolean userUnique = usuarioService.usernameUnique(tomadorForm.getUsername());
            boolean mailUnique = usuarioService.emailUnique(tomadorForm.getEmail());
            if(!userUnique){
                result.addError(new ObjectError("username","El nombre de usuario ingresado ya existe"));
            }
            if (!mailUnique){
                result.addError(new ObjectError("email","El email ingresado ya existe"));
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
