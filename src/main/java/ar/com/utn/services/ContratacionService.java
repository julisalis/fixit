package ar.com.utn.services;

import ar.com.utn.models.Contratacion;
import ar.com.utn.models.Postulacion;

public interface ContratacionService {
    String efectuarPago(Contratacion contratacion) throws Exception;

    Contratacion findByPostulacion(Postulacion mipostulacion);
}
