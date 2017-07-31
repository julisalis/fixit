package ar.com.utn.services.implementation;

import ar.com.utn.form.TomadorForm;
import ar.com.utn.repositories.TomadorRepository;
import ar.com.utn.repositories.UsuarioRepository;
import ar.com.utn.services.TomadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TomadorServiceImpl implements TomadorService {

    @Autowired
    private TomadorRepository tomadorRepository;

    @Override
    public void registrarTomador(TomadorForm tomadorForm) {

    }
}
