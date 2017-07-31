package ar.com.utn.services;

import ar.com.utn.form.TomadorForm;
import org.springframework.stereotype.Service;

/**
 * Created by julis on 15/7/2017.
 */

public interface TomadorService {
    public void registrarTomador(TomadorForm tomadorForm);
}
