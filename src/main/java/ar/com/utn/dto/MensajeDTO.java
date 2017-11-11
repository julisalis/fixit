package ar.com.utn.dto;

import java.time.LocalDateTime;

/**
 * Created by iaruedel on 10/11/17.
 */
public class MensajeDTO{
    private String mensaje;
    private LocalDateTime fecha;
    private Boolean isMine;

    public MensajeDTO() {
    }

    public MensajeDTO(String mensaje, LocalDateTime fecha, Boolean isMine) {
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.isMine = isMine;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFecha() {

        return fecha.toLocalDate().toString() +" "+ fecha.toLocalTime().toString();
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Boolean getMine() {
        return isMine;
    }

    public void setMine(Boolean mine) {
        isMine = mine;
    }
}
