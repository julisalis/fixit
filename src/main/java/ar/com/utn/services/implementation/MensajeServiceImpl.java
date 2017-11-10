package ar.com.utn.services.implementation;

import ar.com.utn.models.Mensaje;
import ar.com.utn.models.Postulacion;
import ar.com.utn.repositories.MensajeRepository;
import ar.com.utn.services.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by iaruedel on 10/11/17.
 */
@Service
public class MensajeServiceImpl implements MensajeService {
    @Autowired
    MensajeRepository mensajeRepository;


    @Override
    public void createMensaje(Postulacion postulacion, String message, Boolean enviaTomador) {
       Mensaje mensaje = new Mensaje(message,enviaTomador,postulacion);
       mensajeRepository.save(mensaje);
    }
}
