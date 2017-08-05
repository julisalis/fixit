package ar.com.utn.afip.enums;

/**
 * Created by jsalischiker on 20/07/17.
 */
public enum TipoPersona {
    FISICA("Fisica"),
    JURIDICA("Juridica");

    private final String nombre;

    private TipoPersona(final String nombre) {
        this.nombre = nombre;
    }

    public static TipoPersona getByNombre(String n) {
        for(TipoPersona tp : TipoPersona.values()){
            if(tp.getNombre().equalsIgnoreCase(n)){
                return tp;
            }
        }
        return null;
    }

    public String getNombre() {
        return nombre;
    }

    public String getName(){
        return name();
    }
}
