package ar.com.utn.services;

import ar.com.utn.form.PrestadorForm;
import ar.com.utn.form.TomadorForm;
import ar.com.utn.models.Prestador;
import ar.com.utn.models.Usuario;

/**
 * Created by julis on 26/5/2017.
 */
public interface UsuarioService {

    public Usuario findByUsername(String username);

    Usuario registrarPrestador(PrestadorForm prestadorForm, Prestador prestador);

    Usuario registrarTomador(TomadorForm tomadorForm);
}
