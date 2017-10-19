package ar.com.utn.models;

import ar.com.utn.form.PostulacionForm;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Currency;

/**
 * Created by julis on 13/7/2017.
 */
@Entity
@Table(name="postulaciones")
public class Postulacion extends PersistentEntity {

    private String descripcion;
    private BigDecimal presupAprox;
    private Currency currency;
    private BigDecimal duracionAprox;
    //private String recursos;
    private String comentarios;
    @Column(columnDefinition="boolean default false", nullable = false)
    private Boolean elegida = false;

    @Enumerated(EnumType.STRING)
    private EstadoPostulacion estadoPostulacion = EstadoPostulacion.NUEVA;

    @ManyToOne
    @JoinColumn(name = "publicacion", nullable = false)
    private Publicacion publicacion;

    @ManyToOne
    @JoinColumn(name = "usuario", nullable = false)
    private Usuario usuario;

    public Postulacion() {
    }

    public Postulacion(PostulacionForm postulacionForm, Usuario usuario, Publicacion publicacion) {
        this.descripcion = postulacionForm.getDescripcion();
        this.presupAprox = postulacionForm.getPresupAprox();
        this.currency = Currency.getInstance(postulacionForm.getCurrencyCode())!=null? Currency.getInstance(postulacionForm.getCurrencyCode()) : Currency.getInstance("ARS") ;
        this.duracionAprox = postulacionForm.getDuracionAprox();
        this.publicacion = publicacion;
        this.usuario = usuario;
        this.comentarios = postulacionForm.getComentarios();
    }

    public void update(PostulacionForm postulacionForm) {
        this.descripcion = postulacionForm.getDescripcion();
        this.presupAprox = postulacionForm.getPresupAprox();
        this.currency = Currency.getInstance(postulacionForm.getCurrencyCode())!=null? Currency.getInstance(postulacionForm.getCurrencyCode()) : Currency.getInstance("ARS") ;
        this.duracionAprox = postulacionForm.getDuracionAprox();
        this.comentarios = postulacionForm.getComentarios();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPresupAprox() {
        return presupAprox;
    }

    public void setPresupAprox(BigDecimal presupAprox) {
        this.presupAprox = presupAprox;
    }

    public BigDecimal getDuracionAprox() {
        return duracionAprox;
    }

    public void setDuracionAprox(BigDecimal duracionAprox) {
        this.duracionAprox = duracionAprox;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Boolean getElegida() {
        return elegida;
    }

    public void setElegida(Boolean elegida) {
        this.elegida = elegida;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setPrestador(Usuario usuario) {
        this.usuario = usuario;
    }

    public EstadoPostulacion getEstadoPostulacion() {
        return estadoPostulacion;
    }

    public void setEstadoPostulacion(EstadoPostulacion estadoPostulacion) {
        this.estadoPostulacion = estadoPostulacion;
    }
}
