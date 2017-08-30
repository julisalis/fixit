package ar.com.utn.models;

/**
 * Created by julian on 27/08/17.
 */
public enum Urgencia {
    URGENTE,FLEXIBLE,FECHA;

    public String getNameUrgencia(String code){
        code = code.trim();
        switch (code){
            case "URGENTE":return "Urgente";
            case "FLEXIBLE":return "Flexible";
            case "FECHA":return "Fecha Específica";
        }
        return code;
    }
}
