package ar.com.utn.form;

import ar.com.utn.models.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Created by julian on 30/07/17.
 */
public class PrestadorForm {

    @NotBlank
    private String username;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
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
    @NotEmpty
    private List<TipoTrabajo> tipos;

    private Boolean validar;

    private Long cuit;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,pattern="dd/MM/yyyy")
    private LocalDate nacimiento;

    private Sexo sexo;


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

    public Long getCuit() {
        return cuit;
    }

    public void setCuit(Long cuit) {
        this.cuit = cuit;
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

    public Boolean getValidar() {
        return validar;
    }

    public void setValidar(Boolean validar) {
        this.validar = validar;
    }

    public TelefonoForm getTelefono() {
        return telefono;
    }

    public void setTelefono(TelefonoForm telefono) {
        this.telefono = telefono;
    }

    public List<TipoTrabajo> getTipos() {
        return tipos;
    }

    public void setTipos(List<TipoTrabajo> tipos) {
        this.tipos = tipos;
    }

    public LocalDate getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(LocalDate nacimiento) {
        this.nacimiento = nacimiento;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }
}
