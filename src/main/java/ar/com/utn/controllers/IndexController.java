package ar.com.utn.controllers;

import ar.com.utn.dto.PublicacionDTO;
import ar.com.utn.dto.TipoTrabajoDTO;
import ar.com.utn.form.PublicacionFotoForm;
import ar.com.utn.models.*;
import ar.com.utn.repositories.implementation.PublicacionSearch;
import ar.com.utn.services.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import sun.misc.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by julis on 17/5/2017.
 */

@Controller
public class IndexController {

    @Autowired
    private PublicacionService publicacionService;

    @Autowired
    private PublicacionSearch publicacionSearch;

    @RequestMapping("/")
    String index(Model model) {
        model.addAttribute("tipotrabajos", getTiposTrabajos(publicacionService.getTipostrabajos()));
        return "index";
    }

    public List<TipoTrabajoDTO> getTiposTrabajos (List<TipoTrabajo> tiposTrabajos) {
        return tiposTrabajos.stream().map(t -> new TipoTrabajoDTO(t,publicacionService.countPublicaciones(t))).collect(Collectors.toList());
    }

}
