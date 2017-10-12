package ar.com.utn.repositories;

import ar.com.utn.models.Postulacion;
import ar.com.utn.models.Publicacion;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by julis on 15/7/2017.
 */
public interface PostulacionRepository extends CrudRepository<Postulacion, Long> {

    List<Postulacion> findByPublicacion(Publicacion publicacion);
}
