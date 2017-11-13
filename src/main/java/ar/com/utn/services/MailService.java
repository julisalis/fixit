package ar.com.utn.services;

import ar.com.utn.dto.PostulacionDTO;
import ar.com.utn.form.PrestadorForm;
import ar.com.utn.form.TomadorForm;
import ar.com.utn.models.Contratacion;
import ar.com.utn.models.Postulacion;
import ar.com.utn.models.Publicacion;
import ar.com.utn.models.Usuario;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mjarabroviski on 12/08/2017.
 */
public interface MailService {
    @Transactional(readOnly=true)
    void sendRegistrationMailTomador(TomadorForm user, String link);

    void sendRegistrationMailPrestador(PrestadorForm user, String link);

    void sendPostulacionElegidaMail(Usuario cliente, Usuario prestador, PostulacionDTO postulacion);

    void sendPostulacionNuevaMail(Usuario cliente, Usuario prestador, PostulacionDTO postulacion);

    void sendCalificacionMailToProfesional(Contratacion contratacion, Usuario usuario, Usuario prof);

    void sendConsultaPostulacion(Usuario usuarioOrigen, Usuario usuarioDestino, Postulacion postulacion, String message);

    void sendCodigoSeguridad(Contratacion contratacion, Publicacion publicacion, Usuario currentUser, Usuario profesional);

    void sendCalificacionMailToTomador(Contratacion contratacion, Usuario usuario, Usuario prof);
}
