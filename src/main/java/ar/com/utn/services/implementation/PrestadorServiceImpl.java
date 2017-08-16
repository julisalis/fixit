package ar.com.utn.services.implementation;

import ar.com.utn.models.Prestador;
import ar.com.utn.repositories.PrestadorRepository;
import ar.com.utn.services.PrestadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrestadorServiceImpl implements PrestadorService {

    @Autowired
    PrestadorRepository prestadorRepository;

    @Override
    public boolean cuitUnique(Long cuit){
        Prestador prestador = prestadorRepository.findByCuit(cuit);
        return prestador == null;
    }
}
