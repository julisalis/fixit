package ar.com.utn.afip.exceptions;

/**
 * Created by jsalischiker on 14/07/17.
 */
public class AfipCmsException extends RuntimeException {

    public AfipCmsException(String msgError) {super("Error al crear el CMS. Mensaje de error: "+msgError);}
}
