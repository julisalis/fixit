package ar.com.utn.models;

/**
 * Created by iaruedel on 16/08/17.
 */
public enum TiempoPublicacion {
    SEMANA,MES,SEISMESES,ANUAL,ILIMITADO;

    public String getName(String code){
        code = code.trim();
        switch (code){
            case "SEMANA":return "Una Semana";
            case "MES":return "Un Mes";
            case "SEISMESES":return "Seis Meses";
            case "ANUAL":return "Un Año";
            case "ILIMITADO":return "Ilimitado";
        }
        return code;
    }
}
