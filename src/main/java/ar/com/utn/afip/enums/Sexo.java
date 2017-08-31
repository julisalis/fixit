package ar.com.utn.afip.enums;

/**
 * Created by jsalischiker on 20/07/17.
 */
public enum Sexo {
    MASCULINO,
    FEMENINO,
    DESCONOCIDO;

    private Sexo() { }

    public String getName(){
        return name();
    }
}
