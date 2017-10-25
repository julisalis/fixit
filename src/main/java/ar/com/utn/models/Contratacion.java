package ar.com.utn.models;

import ar.com.utn.mercadopago.PaymentMP;

import javax.persistence.*;

@Entity
@Table(name="contratacion")
public class Contratacion extends PersistentEntity {

    @OneToOne
    private PaymentMP paymentMP;

    @OneToOne
    private Postulacion postulacion;

    @Enumerated(EnumType.STRING)
    private PayMethod payMethod;

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
}
