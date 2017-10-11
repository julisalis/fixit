package ar.com.utn.dto;

import ar.com.utn.models.EstadoPostulacion;
import ar.com.utn.models.Postulacion;
import ar.com.utn.models.Prestador;

import java.math.BigDecimal;
import java.util.Currency;

public class PostulacionDTO {

    private Long id;
    private String descripcion;
    private BigDecimal presupAprox;
    private Currency currency;
    private BigDecimal duracionAprox;
    private String comentarios;
    private Prestador prestador;
    private EstadoPostulacion estado;
    private Boolean elegida;

    public PostulacionDTO(Postulacion postulacion) {
        this.id = postulacion.getId();
        this.descripcion = postulacion.getDescripcion();
        this.presupAprox = postulacion.getPresupAprox();
        this.currency = postulacion.getCurrency();
        this.duracionAprox = postulacion.getDuracionAprox();
        this.comentarios = postulacion.getComentarios();
        this.prestador = postulacion.getPrestador();
        this.estado = postulacion.getEstadoPostulacion();
        this.elegida = postulacion.getElegida();
    }

    public PostulacionDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getDuracionAprox() {
        return duracionAprox;
    }

    public void setDuracionAprox(BigDecimal duracionAprox) {
        this.duracionAprox = duracionAprox;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Prestador getPrestador() {
        return prestador;
    }

    public void setPrestador(Prestador prestador) {
        this.prestador = prestador;
    }

    public EstadoPostulacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoPostulacion estado) {
        this.estado = estado;
    }

    public Boolean getElegida() {
        return elegida;
    }

    public void setElegida(Boolean elegida) {
        this.elegida = elegida;
    }
}

