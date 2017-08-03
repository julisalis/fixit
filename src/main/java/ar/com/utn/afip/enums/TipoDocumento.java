package ar.com.utn.afip.enums;

/**
 * Created by jsalischiker on 20/07/17.
 */
public enum TipoDocumento {
    LIBRETA_CIVICA("LC","Libreta Cívica"),
    LIBRETA_ENROLAMIENTO("LE","Libreta de Enrolamiento"),
    CEDULA_IDENTIDAD("CI","Cedula de Identidad"),
    TRAMITE("TRAM","En Tramite"),
    ACTA("ACTA","Acta de Nacimiento"),
    PASAPORTE("PAS","Pasaporte"),
    DNI("DNI","Doc. Nacional de Identidad"),
    INDETERMINADO("INDET","Indeterminado"),
    CERT("CERT","Certificado de Migración"),
    DOC_EXT("DIEXT","Documento Identidad Extranjero"),
    DNI_MULTIPLE("DNI M","DNI (N° Multiple)"),
    INDOC("INDOC","ANSES INDOCUMENTADO");

    private final String codigo;
    private final String nombre;

    private TipoDocumento(final String codigo, final String nombre) {
        this.codigo = codigo;this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getName(){
        return name();
    }
}