package ar.com.utn.form;

import ar.com.utn.models.TipoDoc;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by julian on 30/07/17.
 */
public class PrestadorForm {

    private Long cuit;
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
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,pattern="dd-MM-yyyy")
    @NotNull
    private Date fechaNacimiento;
    @NotNull
    private TipoDoc tipoDoc;
    @NotBlank
    private String documento;
    @NotBlank
    private String telefono;



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

        public Date getFechaNacimiento() {
            return fechaNacimiento;
        }

        public void setFechaNacimiento(Date fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
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

        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

        public Long getCuit() {
            return cuit;
        }

        public void setCuit(Long cuit) {
            this.cuit = cuit;
        }

}
