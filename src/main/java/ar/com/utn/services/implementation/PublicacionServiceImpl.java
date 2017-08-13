package ar.com.utn.services.implementation;

import ar.com.utn.models.TipoTrabajo;
import ar.com.utn.repositories.TipoTrabajoRepository;
import ar.com.utn.services.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by iaruedel on 13/08/17.
 */
@Service
public class PublicacionServiceImpl implements PublicacionService {
    @Autowired
    private TipoTrabajoRepository tipoTrabajoRepository;

    @Override
    public List<TipoTrabajo> getTipostrabajos() {
        return tipoTrabajoRepository.findAll();
    }
}
