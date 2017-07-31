package ar.com.utn.services;

import ar.com.utn.form.PrestadorForm;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Created by julis on 15/7/2017.
 */

public interface PrestadorService{
    public void registrarPrestador(PrestadorForm prestadorForm);
}
