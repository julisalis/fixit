package ar.com.utn.repositories;

import ar.com.utn.models.Mensaje;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by iaruedel on 10/11/17.
 */
public interface MensajeRepository extends CrudRepository<Mensaje, Long> {
}
