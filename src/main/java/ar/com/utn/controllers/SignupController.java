package ar.com.utn.controllers;

import ar.com.utn.form.PrestadorForm;
import ar.com.utn.form.TomadorForm;
import ar.com.utn.models.Prestador;
import ar.com.utn.services.UsuarioService;
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
import java.util.Locale;
import java.util.Map;

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
        model.addAttribute("tomadorForm",new TomadorForm());
        return "signup-tomador";
    }



    @PostMapping(path="/prestador")
    public String signupPrestador(@Valid @ModelAttribute("prestadorForm") PrestadorForm prestadorForm, BindingResult result, Model model){
        HashMap<String,Object> map = new HashMap<>();
        try{
            if(!result.hasErrors()){
                //validar cuit si completo ese campo PORQUE ES DOUBLE?
                boolean validationResult=false;
                Prestador prestador = new Prestador(prestadorForm.getCuit(),validationResult);
                usuarioService.registrarPrestador(prestadorForm,prestador);
            }else{
                return "signup-prestador";
            }
        }catch (Exception e) {

        }
        return "signup-prestador";
    }

    @PostMapping(path="/tomador")
    public String signupTomador(@Valid @ModelAttribute("tomadorForm") TomadorForm tomadorForm, BindingResult result, Model model, WebRequest webRequest, HttpServletRequest request, HttpServletResponse response){
        HashMap<String,Object> map = new HashMap<>();
        try{
            if(!result.hasErrors()){
                usuarioService.registrarTomador(tomadorForm);
            }else{
                return "signup-tomador";
            }
        }catch (Exception e) {

        }
        return "signup-tomador";
    }
}
