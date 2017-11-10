package ar.com.utn.services;

import ar.com.utn.models.Postulacion;

/**
 * Created by iaruedel on 10/11/17.
 */
public interface MensajeService {
    void createMensaje(Postulacion postulacion, String message, Boolean enviaTomador);
}
