package ar.com.utn.services;

import ar.com.utn.form.PublicacionForm;
import ar.com.utn.models.TipoTrabajo;

import java.util.List;

/**
 * Created by julis on 15/7/2017.
 */
public interface PublicacionService {
    List<TipoTrabajo> getTipostrabajos();

    void createPublicacion(PublicacionForm publicacionForm);
}
