package ar.com.utn.dto;

import ar.com.utn.form.PublicacionFotoForm;
import ar.com.utn.models.*;
import ar.com.utn.services.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Currency;

public class PostulacionDTO {
    private Long id;
    private String descripcion;
    private BigDecimal presupAprox;
    private Currency currency;
    private BigDecimal duracionAprox;
    private String comentarios;
    private EstadoPostulacion estado;
    private Boolean elegida;
    private PublicacionDTO publicacion;
    private UsuarioDTO usuarioPrestador;

    public PostulacionDTO(Postulacion postulacion, PublicacionFotoForm primaryImage, Usuario usuario) {
        this.id = postulacion.getId();
        this.descripcion = postulacion.getDescripcion();
        this.presupAprox = postulacion.getPresupAprox();
        this.currency = postulacion.getCurrency();
        this.duracionAprox = postulacion.getDuracionAprox();
        this.comentarios = postulacion.getComentarios();
        this.estado = postulacion.getEstadoPostulacion();
        this.elegida = postulacion.getElegida();
        this.publicacion = new PublicacionDTO(postulacion.getPublicacion(), primaryImage);
        this.usuarioPrestador = new UsuarioDTO(usuario, true);
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

    public PublicacionDTO getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(PublicacionDTO publicacion) {
        this.publicacion = publicacion;
    }

    public UsuarioDTO getUsuarioPrestador() {
        return usuarioPrestador;
    }

    public void setUsuarioPrestador(UsuarioDTO usuarioPrestador) {
        this.usuarioPrestador = usuarioPrestador;
    }
}

