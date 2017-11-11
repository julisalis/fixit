package ar.com.utn.models;

import ar.com.utn.mercadopago.PaymentMP;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="contratacion")
public class Contratacion extends PersistentEntity {
    @OneToOne
    private PaymentMP paymentMP;
    @OneToOne
    private Postulacion postulacion;
    @Enumerated(EnumType.STRING)
    private PayMethod payMethod;
    private LocalDateTime fechaCreacion;
    private String paymentId;
    private Double calificacionTomador;
    private Double calificacionPrestador;

    private LocalDate fechaCodigo;
    private Long codigoSeguridad;
    @Column(columnDefinition="boolean default false", nullable = false)
    private Boolean codigoEnviado = false;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
    }

    public Contratacion() {
    }

    public Contratacion(Postulacion postulacion, PayMethod payMethod,PaymentMP paymentMP) {
        this.postulacion = postulacion;
        this.payMethod = payMethod;
        this.paymentMP = paymentMP;
    }

    public Contratacion(Postulacion postulacion, PayMethod payMethod) {
        this.postulacion = postulacion;
        this.payMethod = payMethod;
    }

    public Postulacion getPostulacion() {
        return postulacion;
    }

    public void setPostulacion(Postulacion postulacion) {
        this.postulacion = postulacion;
    }

    public PayMethod getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(PayMethod payMethod) {
        this.payMethod = payMethod;
    }

    public PaymentMP getPaymentMP() {
        return paymentMP;
    }

    public void setPaymentMP(PaymentMP paymentMP) {
        this.paymentMP = paymentMP;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Double getCalificacionTomador() {
        return calificacionTomador;
    }

    public void setCalificacionTomador(Double calificacionTomador) {
        this.calificacionTomador = calificacionTomador;
    }

    public Double getCalificacionPrestador() {
        return calificacionPrestador;
    }

    public void setCalificacionPrestador(Double calificacionPrestador) {
        this.calificacionPrestador = calificacionPrestador;
    }

    public LocalDate getFechaCodigo() {
        return fechaCodigo;
    }

    public void setFechaCodigo(LocalDate fechaCodigo) {
        this.fechaCodigo = fechaCodigo;
    }

    public Long getCodigoSeguridad() {
        return codigoSeguridad;
    }

    public void setCodigoSeguridad(Long codigoSeguridad) {
        this.codigoSeguridad = codigoSeguridad;
    }

    public Boolean getCodigoEnviado() {
        return codigoEnviado;
    }

    public void setCodigoEnviado(Boolean codigoEnviado) {
        this.codigoEnviado = codigoEnviado;
    }
}
