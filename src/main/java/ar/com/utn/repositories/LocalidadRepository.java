package ar.com.utn.repositories;

import ar.com.utn.models.Localidad;
import ar.com.utn.models.Provincia;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by iaruedel on 05/08/17.
 */
public interface LocalidadRepository  extends CrudRepository<Localidad, Long> {
    List<Localidad> findAllByProvinciaOrderByNombre(Provincia provincia);
}
