package ar.com.utn.models;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by julis on 17/5/2017.
 */
@Entity
@Table(name = "usuarios")
public class Usuario extends PersistentEntity {

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String nombre;
    private String apellido;
    private Date fechaNacimiento;
    private Date fechaCreacion;

    @Enumerated (EnumType.STRING)
    private TipoDoc tipoDoc;

    private String documento;
    private String telefono;
    private Double calificacionPromedio;

    @OneToOne
    private Tomador tomador;

    @OneToOne
    private Prestador prestador;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuarios_roles",
            joinColumns = {@JoinColumn(name = "usuario_id")},
            inverseJoinColumns = {@JoinColumn(name = "rol_id")}
    )
    private Set<Rol> roles = new HashSet<Rol>();

    public Usuario(){

    }

    public Usuario(String username, String nombre, String apellido, String documento, TipoDoc tipoDoc, String password, Date fechaNacimiento, String telefono, Long cuit, Prestador prestador) {
        super();
        this.username = username;
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.tipoDoc = tipoDoc;
        this.password= password;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.prestador = prestador;
        this.fechaCreacion = new Date();
        //falta el rol
    }

    public Usuario(String username, String nombre, String apellido, String documento, TipoDoc tipoDoc, String password, Date fechaNacimiento, String telefono) {
        super();
        this.username = username;
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.tipoDoc = tipoDoc;
        this.password= password;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.tomador = new Tomador();
        this.fechaCreacion = new Date();
        //falta el rol
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() { return email; }

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

    public Set<Rol> getRoles() { return roles; }

    public void setRoles(Set<Rol> roles) { this.roles = roles; }

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

    public Double getCalificacionPromedio() {
        return calificacionPromedio;
    }

    public void setCalificacionPromedio(Double calificacionPromedio) {
        this.calificacionPromedio = calificacionPromedio;
    }
}
