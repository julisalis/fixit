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

    public String getNombre() {
        return nombre;
    }

    public String getName(){
        return name();
    }
}
