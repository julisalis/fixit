package ar.com.utn.services;

import ar.com.utn.form.PublicacionForm;
import ar.com.utn.models.Publicacion;
import ar.com.utn.models.TipoTrabajo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by julis on 15/7/2017.
 */
public interface PublicacionService {
    List<TipoTrabajo> getTipostrabajos();

    void createPublicacion(PublicacionForm publicacionForm);

    Integer countPublicaciones (TipoTrabajo tipoTrabajo);

    List<Publicacion> findAll();

    Publicacion findById(Long publicacionId);

    TipoTrabajo findTipoTrabajoBySlug(String slug);

    void saveImage(MultipartFile file, long publicacionId);
}
