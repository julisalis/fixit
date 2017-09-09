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

    public static Sexo afipValue(String afip) {
        if(afip.equalsIgnoreCase("MASCULINO")) {
            return MASCULINO;
        }
        if(afip.equalsIgnoreCase("FEMENINO")) {
            return FEMENINO;
        }
        if(afip.equalsIgnoreCase("DESCONOCIDO")) {
            return DESCONOCIDO;
        }
        return null;
    }
}
