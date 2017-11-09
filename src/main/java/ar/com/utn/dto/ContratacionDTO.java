package ar.com.utn.dto;

import ar.com.utn.models.Contratacion;
import ar.com.utn.models.PayMethod;
import ar.com.utn.models.Postulacion;

import java.time.LocalDate;

public class ContratacionDTO {

    private Postulacion postulacion;
    private PayMethod payMethod;
    private String paymentId;
    private LocalDate fechaCreacion;
    private Double calificacionTomador;
    private Double calificacionPrestador;

    public ContratacionDTO(Contratacion contratacion) {
        this.postulacion = contratacion.getPostulacion();
        this.payMethod = contratacion.getPayMethod();
        this.fechaCreacion = contratacion.getFechaCreacion().toLocalDate();
        this.calificacionTomador = contratacion.getCalificacionTomador();
        this.calificacionPrestador = contratacion.getCalificacionPrestador();
    }

    public ContratacionDTO() {
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

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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
}
