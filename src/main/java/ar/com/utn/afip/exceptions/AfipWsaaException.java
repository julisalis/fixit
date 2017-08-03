package ar.com.utn.afip.exceptions;

/**
 * Created by jsalischiker on 14/07/17.
 */
public class AfipWsaaException extends RuntimeException {

    private String code;
    private String description;

    public AfipWsaaException(String code, String desc){
        super("Error en autenticación de AFIP.\n Codigo: "+code+"\n Descripción: "+desc);
        this.code = code;
        this.description = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
