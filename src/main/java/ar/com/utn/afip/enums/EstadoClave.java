package ar.com.utn.afip.enums;

/**
 * Created by jsalischiker on 20/07/17.
 */
public enum EstadoClave {
    ACTIVO,
    INACTIVO;

    private EstadoClave() { }

    public String getName(){
        return name();
    }
}
