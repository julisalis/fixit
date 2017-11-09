package ar.com.utn.services;

import ar.com.utn.mercadopago.PaymentMP;
import ar.com.utn.models.Contratacion;
import ar.com.utn.models.Postulacion;
import ar.com.utn.models.Prestador;
import ar.com.utn.models.Usuario;

public interface ContratacionService {
    Contratacion findByPostulacion(Postulacion mipostulacion);
}
