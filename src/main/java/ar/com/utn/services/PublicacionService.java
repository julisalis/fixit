package ar.com.utn.services;

import ar.com.utn.dto.PublicacionDTO;
import ar.com.utn.form.PublicacionForm;
import ar.com.utn.models.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by julis on 15/7/2017.
 */
public interface PublicacionService {
    List<TipoTrabajo> getTipostrabajos();

    Publicacion createPublicacion(PublicacionForm publicacionForm);

    Integer countPublicaciones (TipoTrabajo tipoTrabajo);

    List<Publicacion> findAll();

    Publicacion findById(Long publicacionId);

    TipoTrabajo findTipoTrabajoBySlug(String slug);

    void saveImage(MultipartFile file, long publicacionId) throws IOException;

    List<Publicacion> findAllByEstadoPublicacionEquals(EstadoPublicacion estadoPublicacion);

    PublicacionPhoto getCover(Publicacion publicacion);

    void editPublicacion(PublicacionForm publicacionForm, Publicacion publicacion, Long primaryImage);

    void deletePublicacion(Publicacion publicacion);

    List<Publicacion> getTrabajosRecomendados(List<TipoTrabajo> tipos);

    Publicacion setContratada(Publicacion publicacion);

    List<Publicacion> getDestacados();

    void setFinalizada(Publicacion publicacion);

    Contratacion findContratacionForPublicacion(Publicacion publicacion);
}
