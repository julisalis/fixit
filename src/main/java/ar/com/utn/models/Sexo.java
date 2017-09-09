package ar.com.utn.models;

/**
 * Created by julis on 14/7/2017.
 */
public enum Sexo {
    MASCULINO("Masculino"),
    FEMENINO("Femenino");

    private final String texto;

    private Sexo(final String texto) {this.texto = texto; }

    public String getName(){
        return name();
    }

    public String getTexto() {
        return texto;
    }

    public Boolean equalsAfip(ar.com.utn.afip.enums.Sexo sAfip) {
        switch (sAfip) {
            case FEMENINO:
                if(this.texto == "Femenino") {return true;}
                break;
            case MASCULINO:
                if(this.texto == "Masculino") {return true;}
                break;
            case DESCONOCIDO:
                return true;
        }
        return false;
    }
}
