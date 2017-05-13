package ar.com.utn.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by julis on 13/5/2017.
 */
@RestController
public class HomeController {

    @RequestMapping("/")
    public String home(){
        return "Hello World";
    }
}
