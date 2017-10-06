package ar.com.utn.controllers;

import ar.com.utn.dto.PublicacionDTO;
import ar.com.utn.form.CurrencyCode;
import ar.com.utn.form.PostulacionForm;
import ar.com.utn.form.PublicacionForm;
import ar.com.utn.form.PublicacionFotoForm;
import ar.com.utn.models.*;
import ar.com.utn.services.PostulacionService;
import ar.com.utn.services.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by julis on 15/7/2017.
 */
@Controller
@RequestMapping(path="/postulacion")
public class PostulacionController {

    @Autowired
    private PublicacionService publicacionService;

    /*@Autowired
    private PostulacionService postulacionService;*/

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String newPostulacion(@RequestParam(value = "publicacionId") Publicacion publicacion, WebRequest request, Model model) {
        addModelAttributes(model,new PostulacionForm(),"new");
        model.addAttribute("publicacion", new PublicacionDTO(publicacion,getCover(publicacion)));
        return "postulacion";
    }

    public void addModelAttributes(Model model, PostulacionForm form, String formAction){
        model.addAttribute("postulacion",form);
        model.addAttribute("currencies", CurrencyCode.values());
        model.addAttribute("form_action",formAction);
    }

    private PublicacionFotoForm getCover(Publicacion publicacion) {
        PublicacionPhoto publicacionPhoto = publicacionService.getCover(publicacion);
        if(publicacionPhoto!=null){
            return new PublicacionFotoForm(publicacionPhoto);
        }else return null;
    }

    /*@RequestMapping(value = "/createPostulacion", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> newPostulacion() {
        HashMap<String,Object> map = new HashMap<>();
        try {
            if(!result.hasErrors()){
                Postulacion postulacion = postulacionService.createPublicacion(postulacionForm);
                map.put("success", true);
                map.put("postulacion", new PostulacionForm(postulacion));
                map.put("msg","La postulación ha sido creada con éxito!");
            }else{
                map.put("success", false);
                map.put("errors", result.getAllErrors());
            }
        }catch (Exception e) {
            map.put("success", false);
            map.put("msg","Ha surgido un error, pruebe nuevamente más tarde");
        }
        return map;
    }*/
}
