package ar.com.utn.services;

import ar.com.utn.mercadopago.PaymentMP;
import ar.com.utn.models.Contratacion;
import ar.com.utn.models.Postulacion;
import ar.com.utn.models.Prestador;
import ar.com.utn.models.Usuario;

public interface ContratacionService {
    String efectuarPago(PaymentMP paymentMP, Prestador prestador) throws Exception;

    Contratacion findByPostulacion(Postulacion mipostulacion);

    PaymentMP completePayment(Postulacion postulacion, String tokenMP, String paymentMethodId, Usuario tomador);
}
