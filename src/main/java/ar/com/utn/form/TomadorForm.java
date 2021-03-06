package ar.com.utn.form;

import ar.com.utn.models.Telefono;
import ar.com.utn.models.TipoDoc;
import ar.com.utn.models.Tomador;
import ar.com.utn.models.Usuario;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by julian on 30/07/17.
 */
public class TomadorForm {
    @NotBlank
    private String username;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String repeatPassword;
    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido;
    @NotNull
    private TipoDoc tipoDoc;
    @NotBlank
    private String documento;
    @Valid
    private TelefonoForm telefono;
    @NotNull
    private Long provincia;
    @NotNull
    private Long localidad;

    public TomadorForm(Usuario user) {
        this.nombre = user.getNombre();
        this.apellido = user.getApellido();
        this.documento = user.getDocumento();
        this.email = user.getEmail();
        this.tipoDoc = user.getTipoDoc();
        if(user.getUbicacion()!=null && user.getUbicacion().getLocalidad()!=null && user.getUbicacion().getLocalidad().getProvincia()!=null){
            this.provincia = user.getUbicacion().getLocalidad().getProvincia().getId();
            this.localidad = user.getUbicacion().getLocalidad().getId();
        }
        this.telefono = new TelefonoForm(user.getTelefono());
        this.username = user.getUsername();
    }

    public TomadorForm() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public TipoDoc getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(TipoDoc tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public TelefonoForm getTelefono() {
        return telefono;
    }

    public void setTelefono(TelefonoForm telefono) {
        this.telefono = telefono;
    }

    public Long getProvincia() {
        return provincia;
    }

    public void setProvincia(Long provincia) {
        this.provincia = provincia;
    }

    public Long getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Long localidad) {
        this.localidad = localidad;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
