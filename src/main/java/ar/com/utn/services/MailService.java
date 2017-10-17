package ar.com.utn.services;

import ar.com.utn.form.PrestadorForm;
import ar.com.utn.form.TomadorForm;
import ar.com.utn.models.Postulacion;
import ar.com.utn.models.Usuario;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mjarabroviski on 12/08/2017.
 */
public interface MailService {
    @Transactional(readOnly=true)
    void sendRegistrationMailTomador(TomadorForm user, String link);

    void sendRegistrationMailPrestador(PrestadorForm user, String link);

    void sendPostulacionElegidaMail(Usuario cliente, Usuario prestador, Postulacion postulacion);
}
