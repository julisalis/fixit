package ar.com.utn.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by julis on 17/5/2017.
 */

@Controller
public class IndexController {

    @RequestMapping("/")
    String index() {
        return "index";
    }
}
