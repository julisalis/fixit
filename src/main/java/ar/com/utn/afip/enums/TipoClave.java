package ar.com.utn.afip.enums;

/**
 * Created by jsalischiker on 20/07/17.
 */
public enum TipoClave {
    CUIT,
    CUIL,
    CDI;

    private TipoClave() { }

    public String getName(){
        return name();
    }
}
