package ar.com.utn.dto;

import ar.com.utn.models.TipoTrabajo;

public class TipoTrabajoDTO {
    private TipoTrabajo tipoTrabajo;
    private Integer cantidad;

    public TipoTrabajoDTO(TipoTrabajo tipoTrabajo, Integer cantidad) {
        this.tipoTrabajo = tipoTrabajo;
        this.cantidad = cantidad;
    }

    public TipoTrabajo getTipoTrabajo() {
        return tipoTrabajo;
    }

    public void setTipoTrabajo(TipoTrabajo tipoTrabajo) {
        this.tipoTrabajo = tipoTrabajo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
