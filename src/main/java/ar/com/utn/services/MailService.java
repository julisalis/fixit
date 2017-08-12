package ar.com.utn.services;

import ar.com.utn.form.TomadorForm;
import ar.com.utn.models.Usuario;

/**
 * Created by mjarabroviski on 12/08/2017.
 */
public interface MailService {
    void sendRegistrationMailTomador(TomadorForm user, String link);
}
