package ar.com.utn.models;

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
    @JoinColumn(name = "prestador", nullable = false)
    private Prestador prestador;

    public Postulacion() {
    }

    public Postulacion(String currency, String descripcion, BigDecimal presupAprox, BigDecimal duracionAprox, Publicacion publicacion, Prestador prestador, String comentarios) {
        this.descripcion = descripcion;
        this.presupAprox = presupAprox;
        this.currency = Currency.getInstance(currency)!=null? Currency.getInstance(currency) : Currency.getInstance("ARS") ;
        this.duracionAprox = duracionAprox;
        this.publicacion = publicacion;
        this.prestador = prestador;
        this.comentarios = comentarios;
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
}
