package ar.com.utn.services;

import ar.com.utn.models.ActividadAfip;
import ar.com.utn.models.TipoTrabajo;

import java.util.List;

public interface PrestadorService {

    boolean cuitUnique(Long cuit);

    List<TipoTrabajo> getTiposTrabajos();

    List<ActividadAfip> getActividadesAfip();
}
