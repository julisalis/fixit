package ar.com.utn.models;

import ar.com.utn.form.PostulacionForm;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

/**
 * Created by julis on 13/7/2017.
 */
@Entity
@Table(name="postulaciones")
public class Postulacion extends PersistentEntity {
    @Type(type="text")
    private String descripcion;
    private BigDecimal presupAprox;
    private BigDecimal presupTrabajo;
    private Currency currency;
    private BigDecimal duracionAprox;
    //private String recursos;
    @Type(type="text")
    private String comentarios;
    @Column(columnDefinition="boolean default false", nullable = false)
    private Boolean elegida = false;

    @Enumerated(EnumType.STRING)
    private EstadoPostulacion estadoPostulacion = EstadoPostulacion.NUEVA;

    @ManyToOne
    @JoinColumn(name = "publicacion", nullable = false)
    private Publicacion publicacion;

    @ManyToOne
    @JoinColumn(name = "prestador", nullable = false)
    private Prestador prestador;

    @OneToMany(mappedBy = "postulacion")
    private List<Mensaje> mensajes = new ArrayList<>();

    public Postulacion() {
    }

    public Postulacion(PostulacionForm postulacionForm, Prestador prestador, Publicacion publicacion) {
        this.descripcion = postulacionForm.getDescripcion();
        this.presupAprox = postulacionForm.getPresupAprox();
        this.presupTrabajo = postulacionForm.getPresupTrabajo();
        this.currency = Currency.getInstance(postulacionForm.getCurrencyCode())!=null? Currency.getInstance(postulacionForm.getCurrencyCode()) : Currency.getInstance("ARS") ;
        this.duracionAprox = postulacionForm.getDuracionAprox();
        this.publicacion = publicacion;
        this.prestador = prestador;
        this.comentarios = postulacionForm.getComentarios();
    }

    public void update(PostulacionForm postulacionForm) {
        this.descripcion = postulacionForm.getDescripcion();
        this.presupAprox = postulacionForm.getPresupAprox();
        this.presupTrabajo = postulacionForm.getPresupTrabajo();
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

    public Prestador getPrestador() {
        return prestador;
    }

    public void setPrestador(Prestador prestador) {
        this.prestador = prestador;
    }

    public EstadoPostulacion getEstadoPostulacion() {
        return estadoPostulacion;
    }

    public void setEstadoPostulacion(EstadoPostulacion estadoPostulacion) {
        this.estadoPostulacion = estadoPostulacion;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    public BigDecimal getPresupTrabajo() {
        return presupTrabajo;
    }

    public void setPresupTrabajo(BigDecimal presupTrabajo) {
        this.presupTrabajo = presupTrabajo;
    }
}
