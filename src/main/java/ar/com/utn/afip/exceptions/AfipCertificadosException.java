package ar.com.utn.afip.exceptions;

/**
 * Created by jsalischiker on 14/07/17.
 */
public class AfipCertificadosException extends RuntimeException {

    public AfipCertificadosException(String certError) {super("Error al crear certificados de AFIP. Mensaje de errror: "+certError);}
}
