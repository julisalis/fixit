package ar.com.utn.repositories;

import ar.com.utn.models.Contratacion;
import ar.com.utn.models.Postulacion;
import org.springframework.data.repository.CrudRepository;

public interface ContratacionRepository extends CrudRepository<Contratacion, Long> {
    Contratacion findByPostulacion(Postulacion mipostulacion);
}
