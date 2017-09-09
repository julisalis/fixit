package ar.com.utn.repositories;

import ar.com.utn.models.Publicacion;
import ar.com.utn.models.TipoTrabajo;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by julis on 15/7/2017.
 */
public interface PublicacionRepository extends CrudRepository<Publicacion, Long> {
    Integer countByTiposTrabajo(TipoTrabajo tipoTrabajo);
}
