package ar.com.utn.controllers;

import ar.com.utn.form.PrestadorForm;
import ar.com.utn.form.TelefonoForm;
import ar.com.utn.models.TipoDoc;
import ar.com.utn.models.Usuario;
import ar.com.utn.repositories.UsuarioRepository;
import ar.com.utn.utils.CurrentSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by julis on 17/5/2017.
 */
@Controller
@RequestMapping(path="/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

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
        model.addAttribute("usuario",currentSession.getUser());
        model.addAttribute("provincias", signupController.generarProvicias());
        return "perfil-usuario";

    }
}
