package ar.com.utn.services.implementation;

import ar.com.utn.form.PrestadorForm;
import ar.com.utn.form.TomadorForm;
import ar.com.utn.models.Contratacion;
import ar.com.utn.models.Postulacion;
import ar.com.utn.models.Publicacion;
import ar.com.utn.models.Usuario;
import ar.com.utn.services.MailService;
import ar.com.utn.utils.EmailApi;
import ar.com.utn.utils.URLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by mjarabroviski on 12/08/2017.
 */
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private EmailApi emailApi;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private Environment environment;

    @Autowired
    private URLBuilder urlBuilder;

    public String getDate(){
        Calendar calendar = Calendar.getInstance();
        String year = Integer.toString(calendar.get(Calendar.YEAR));
        String month = Integer.toString(calendar.get(Calendar.MONTH)+1);
        String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        return day+"/"+month+"/"+year;
    }

    @Override
    @Transactional(readOnly=true)
    public void sendRegistrationMailTomador(TomadorForm user, String link) {

        final Context ctx = new Context(new Locale("es","AR"));
        ctx.setVariable("name", user.getUsername());
        ctx.setVariable("linkConfirm", link);
        ctx.setVariable("title", "Bienvenido a FixIT");

        String dest=user.getEmail();
        if(environment.acceptsProfiles("dev") || environment.acceptsProfiles("test")){
            dest =(environment.getProperty("mail.info"));
        }

        sendBasicMail("Bienvenido a FixIT", dest, "email/confirm-tomador",ctx);
    }

    @Override
    @Transactional(readOnly=true)
    public void sendRegistrationMailPrestador(PrestadorForm user, String link) {

        final Context ctx = new Context(new Locale("es","AR"));
        ctx.setVariable("name", user.getUsername());
        ctx.setVariable("linkConfirm", link);
        ctx.setVariable("title", "Bienvenido a FixIT");

        String dest=user.getEmail();
        if(environment.acceptsProfiles("dev") || environment.acceptsProfiles("test")){
            dest =(environment.getProperty("mail.info"));
        }

        sendBasicMail("Bienvenido a FixIT", dest, "email/confirm-prestador",ctx);
    }

    @Override
    @Transactional(readOnly=true)
    public void sendPostulacionElegidaMail(Usuario cliente, Usuario profesional, Postulacion postulacion) {
        final Context ctx = new Context(new Locale("es","AR"));
        ctx.setVariable("name", profesional.getUsername());
        //ctx.setVariable("linkConfirm", link);
        ctx.setVariable("title", "Postulación elegida");

        String dest= profesional.getEmail();
        if(environment.acceptsProfiles("dev") || environment.acceptsProfiles("test")){
            dest =(environment.getProperty("mail.info"));
        }

        sendBasicMail("FixIT - Tu postulación ha sido elegida", dest, "email/postulacion-elegida",ctx);
    }

    @Override
    @Transactional(readOnly=true)
    public void sendPostulacionNuevaMail(Usuario cliente, Usuario profesional, Publicacion publicacion, Postulacion postulacion) {
        final Context ctx = new Context(new Locale("es","AR"));
        ctx.setVariable("name", cliente.getUsername());
        //ctx.setVariable("linkConfirm", link);
        ctx.setVariable("title", "Postulación elegida");

        String dest= cliente.getEmail();
        if(environment.acceptsProfiles("dev") || environment.acceptsProfiles("test")){
            dest =(environment.getProperty("mail.info"));
        }

        sendBasicMail("FixIT - Nueva postulación", dest, "email/postulacion-nueva",ctx);
    }

    @Override
    public void sendCalificacionMailToProfesional(Contratacion contratacion, Usuario usuario, Usuario profesional) {
        final Context ctx = new Context(new Locale("es","AR"));
        ctx.setVariable("name", profesional.getUsername());
        ctx.setVariable("calificacion", contratacion.getCalificacionTomador());
        ctx.setVariable("title", "Postulación finalizada");
        String dest= profesional.getEmail();
        if(environment.acceptsProfiles("dev") || environment.acceptsProfiles("test")){
            dest =(environment.getProperty("mail.info"));
        }
        sendBasicMail("FixIT - Trabajo finalizado con éxito", dest, "email/calificacion-to-prestador",ctx);
    }

    @Override
    public void sendConsultaPostulacion(Usuario usuarioOrigen, Usuario usuarioDestino, Postulacion postulacion, String message) {
        final Context ctx = new Context(new Locale("es","AR"));
        ctx.setVariable("name", usuarioDestino.getUsername());
        ctx.setVariable("nameOrigen", usuarioOrigen.getUsername());
        ctx.setVariable("message", message);
        ctx.setVariable("title", "Nuevo Mensaje");
        String email= usuarioDestino.getEmail();
        if(environment.acceptsProfiles("dev") || environment.acceptsProfiles("test")){
            email =(environment.getProperty("mail.info"));
        }
        sendBasicMail("FixIT - Nuevo Mensaje", email, "email/nuevo-mensaje",ctx);

    }

    @Override
    public void sendCodigoSeguridad(Contratacion contratacion, Publicacion publicacion, Usuario currentUser, Usuario profesional) {
        Context ctx = new Context(new Locale("es","AR"));
        ctx.setVariable("name", currentUser.getUsername());
        ctx.setVariable("codigo", contratacion.getCodigoSeguridad());
        ctx.setVariable("title", "Codigo de Seguridad");
        String dest= currentUser.getEmail();
        if(environment.acceptsProfiles("dev") || environment.acceptsProfiles("test")){
            dest =(environment.getProperty("mail.info"));
        }
        sendBasicMail("Código de seguridad", dest, "email/codigo-seguridad-tomador",ctx);

        ctx = new Context(new Locale("es","AR"));
        ctx.setVariable("name", profesional.getUsername());
        ctx.setVariable("codigo", contratacion.getCodigoSeguridad());
        ctx.setVariable("title", "Codigo de Seguridad");
        dest = profesional.getEmail();
        if(environment.acceptsProfiles("dev") || environment.acceptsProfiles("test")){
            dest =(environment.getProperty("mail.info"));
        }
        sendBasicMail("Código de seguridad", dest, "email/codigo-seguridad-prestador",ctx);
    }

    public void sendBasicMail(String subject,String dest,String html,Context ctx) {
        ctx.setVariable("principal_url", environment.getProperty("application.url"));
        ctx.setVariable("facebook_url", environment.getProperty("facebook.url"));
        ctx.setVariable("instagram_url", environment.getProperty("instagram.url"));
        ctx.setVariable("images_url", environment.getProperty("application.url.multimedia"));
        ctx.setVariable("terms_cond_url", urlBuilder.makeOfflineAbsolutePathLink(environment.getProperty("termscond.url")));
        final String htmlContent = this.templateEngine.process(html, ctx);
        String from = environment.getProperty("mail.from");
        emailApi.sendMessage(subject, dest, from, htmlContent);
    }

}
