package ar.com.utn.dto;

import ar.com.utn.form.PublicacionFotoForm;
import ar.com.utn.models.*;
import ar.com.utn.services.PublicacionService;
import ar.com.utn.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class PostulacionDTO {
    private Long id;
    private String descripcion;
    private BigDecimal presupAprox;
    private BigDecimal presupTrabajo;
    private Currency currency;
    private BigDecimal duracionAprox;
    private String comentarios;
    private EstadoPostulacion estado;
    private Boolean elegida;
    private PublicacionDTO publicacion;
    private UsuarioDTO usuarioPrestador;
    private List<MensajeDTO> mensajes = new ArrayList<>();

    private boolean prestadorPuedeCalificar = true;

    private Contratacion contratacion;

    private Double calificacionPrestador;

    //prestador
    public PostulacionDTO(Postulacion postulacion, PublicacionFotoForm primaryImage, Usuario usuario) {
        this.id = postulacion.getId();
        this.descripcion = postulacion.getDescripcion();
        this.presupAprox = postulacion.getPresupAprox();
        this.presupTrabajo = postulacion.getPresupTrabajo();
        this.currency = postulacion.getCurrency();
        this.duracionAprox = postulacion.getDuracionAprox();
        this.comentarios = postulacion.getComentarios();
        this.estado = postulacion.getEstadoPostulacion();
        this.elegida = postulacion.getElegida();
        this.publicacion = new PublicacionDTO(postulacion.getPublicacion(), primaryImage);
        this.usuarioPrestador = new UsuarioDTO(usuario, true);
    }

    //calificacion
    public PostulacionDTO(Postulacion postulacion, PublicacionFotoForm primaryImage, Usuario usuario, Double calificacion) {
        this(postulacion, primaryImage, usuario);
        this.calificacionPrestador = calificacion;
    }

    //generico
    public PostulacionDTO(Postulacion postulacion, PublicacionFotoForm primaryImage, Boolean isTomador) {
        this.id = postulacion.getId();
        this.descripcion = postulacion.getDescripcion();
        this.presupAprox = postulacion.getPresupAprox();
        this.presupTrabajo = postulacion.getPresupTrabajo();
        this.currency = postulacion.getCurrency();
        this.duracionAprox = postulacion.getDuracionAprox();
        this.comentarios = postulacion.getComentarios();
        this.estado = postulacion.getEstadoPostulacion();
        this.elegida = postulacion.getElegida();
        this.publicacion = new PublicacionDTO(postulacion.getPublicacion(), primaryImage);
        this.mensajes = buildMensajes(postulacion.getMensajes(),isTomador);
    }


    private List<MensajeDTO> buildMensajes(List<Mensaje> mensajes, Boolean isTomador) {
        return mensajes.stream().map(mensaje ->
                new MensajeDTO(mensaje.getMensaje(),
                        mensaje.getFecha(),
                        (isTomador && mensaje.getEnviaTomador()) || (!isTomador && !mensaje.getEnviaTomador()))).collect(Collectors.toList());
    }

    public PostulacionDTO(Postulacion postulacion, PublicacionFotoForm primaryImage, Usuario usuario, Contratacion contratacion) {
        this(postulacion,primaryImage,usuario);
        this.contratacion = contratacion;
        if(contratacion != null) {
            prestadorPuedeCalificar = contratacion.getCalificacionPrestador() == null;
        }
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

    public List<MensajeDTO> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<MensajeDTO> mensajes) {
        this.mensajes = mensajes;
    }

    public boolean isPrestadorPuedeCalificar() {
        return prestadorPuedeCalificar;
    }

    public void setPrestadorPuedeCalificar(boolean prestadorPuedeCalificar) {
        this.prestadorPuedeCalificar = prestadorPuedeCalificar;
    }

    public Contratacion getContratacion() {
        return contratacion;
    }

    public void setContratacion(Contratacion contratacion) {
        this.contratacion = contratacion;
    }

    public Double getCalificacionPrestador() {
        return calificacionPrestador;
    }

    public void setCalificacionPrestador(Double calificacionPrestador) {
        this.calificacionPrestador = calificacionPrestador;
    }

    public BigDecimal getPresupTrabajo() {
        return presupTrabajo;
    }

    public void setPresupTrabajo(BigDecimal presupTrabajo) {
        this.presupTrabajo = presupTrabajo;
    }
}

