package ar.com.utn.repositories;

import ar.com.utn.models.Postulacion;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by julis on 15/7/2017.
 */
public interface PostulacionRepository extends CrudRepository<Postulacion, Long> {
}
