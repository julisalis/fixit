package ar.com.utn.services.implementation;

import ar.com.utn.form.PrestadorForm;
import ar.com.utn.repositories.PrestadorRepository;
import ar.com.utn.repositories.UsuarioRepository;
import ar.com.utn.services.PrestadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrestadorServiceImpl implements PrestadorService {

    @Autowired
    private PrestadorRepository prestadorRepository;

    @Override
    public void registrarPrestador(PrestadorForm prestadorForm) {

    }
}
