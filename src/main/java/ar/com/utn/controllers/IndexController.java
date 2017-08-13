package ar.com.utn.controllers;

import ar.com.utn.services.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by julis on 17/5/2017.
 */

@Controller
public class IndexController {

    @Autowired
    private PublicacionService publicacionService;

    @RequestMapping("/")
    String index(Model model) {
        model.addAttribute("tipotrabajos", publicacionService.getTipostrabajos());
        return "index";
    }
}
