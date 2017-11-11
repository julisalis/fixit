package ar.com.utn.repositories;

import ar.com.utn.models.EstadoPostulacion;
import ar.com.utn.models.Postulacion;
import ar.com.utn.models.Prestador;
import ar.com.utn.models.Publicacion;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by julis on 15/7/2017.
 */
public interface PostulacionRepository extends CrudRepository<Postulacion, Long> {

    List<Postulacion> findByPublicacion(Publicacion publicacion);

    Postulacion findByPublicacionAndEstadoPostulacion(Publicacion publicacion, EstadoPostulacion e);

    List<Postulacion> findByPrestadorAndEstadoPostulacionIsIn(Prestador prestador, List<EstadoPostulacion> e);

    List<Postulacion> findByPublicacionIsInAndEstadoPostulacionIsIn(List<Publicacion> p, List<EstadoPostulacion> e);
}
