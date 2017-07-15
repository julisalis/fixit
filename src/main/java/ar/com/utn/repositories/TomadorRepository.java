package ar.com.utn.repositories;

import ar.com.utn.models.Prestador;
import ar.com.utn.models.Tomador;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by julis on 15/7/2017.
 */
public interface TomadorRepository extends CrudRepository<Tomador, Long> {
}
