package ar.com.utn.services;

import ar.com.utn.models.Contratacion;

public interface ContratacionService {
    String efectuarPago(Contratacion contratacion) throws Exception;
}
