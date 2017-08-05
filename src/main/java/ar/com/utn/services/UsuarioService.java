package ar.com.utn.services;

import ar.com.utn.form.PrestadorForm;
import ar.com.utn.form.TomadorForm;
import ar.com.utn.models.Prestador;
import ar.com.utn.models.Telefono;
import ar.com.utn.models.Usuario;

/**
 * Created by julis on 26/5/2017.
 */
public interface UsuarioService {

    public Usuario findByUsername(String username);

    Usuario registrarPrestador(PrestadorForm prestadorForm, Prestador prestador, Telefono telefono);

    Usuario registrarTomador(TomadorForm tomadorForm,Telefono telefono);
}
