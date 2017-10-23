package ar.com.utn.controllers;
import ar.com.utn.dto.UsuarioDTO;
import ar.com.utn.models.Usuario;
import ar.com.utn.utils.CurrentSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jws.WebResult;

@Controller
@RequestMapping(path="/reputacion")
public class ReputacionController {

    @Autowired
    private CurrentSession currentSession;

    @GetMapping
    public String showReputacion(Model model) {
        Usuario user = currentSession.getUser();
        if (currentSession.getActualRol() != null && currentSession.getActualRol().equals("PRESTADOR")){
            model.addAttribute("user", new UsuarioDTO(user, true));
        }
        else {
            model.addAttribute("user", new UsuarioDTO(user, false));
        }

        return "reputacion-usuario";
    }
}
