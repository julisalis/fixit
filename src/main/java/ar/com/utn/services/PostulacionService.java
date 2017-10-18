package ar.com.utn.services;

import ar.com.utn.form.PostulacionForm;
import ar.com.utn.models.Postulacion;
import ar.com.utn.models.Publicacion;

import java.util.List;

/**
 * Created by julis on 15/7/2017.
 */
public interface PostulacionService {

    Postulacion createPostulacion(Postulacion postulacionNueva);

    List<Postulacion> findByPublicacion(Publicacion mipublicacion);

    Postulacion findById(Long postulacionId);

    Postulacion setContratada(Postulacion postulacion);

    void editPostulacion(PostulacionForm postulacionForm);
}
