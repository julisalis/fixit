package ar.com.utn.repositories;

import ar.com.utn.afip.TicketAcceso;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by julis on 15/7/2017.
 */
public interface TicketAccesoRepository extends CrudRepository<TicketAcceso, Long> {

    TicketAcceso findTicketAccesoByCuitRepresentada(Long cuit);
}
