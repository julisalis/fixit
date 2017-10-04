package ar.com.utn.services;

import ar.com.utn.form.PrestadorForm;
import ar.com.utn.form.SelectorForm;
import ar.com.utn.form.TomadorForm;
import ar.com.utn.models.Prestador;
import ar.com.utn.models.Provincia;
import ar.com.utn.models.Rol;
import ar.com.utn.models.Usuario;

import java.util.List;

/**
 * Created by julis on 26/5/2017.
 */
public interface UsuarioService {

    public Usuario findByUsername(String username);

    //Usuario registrarPrestador(PrestadorForm prestadorForm, Prestador prestador);

    Usuario registrarPrestador(PrestadorForm prestadorForm);

    Usuario registrarTomador(TomadorForm tomadorForm);

    List<Provincia> getProvincias();

    List<SelectorForm> findAllLocalidadByProvince(Long provinceId);

    boolean usernameUnique(String username);

    boolean emailUnique(String email);

    void activateUser(String token);

    void setAuthority(Rol rol, Usuario usuario);
}
