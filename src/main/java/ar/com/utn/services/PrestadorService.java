package ar.com.utn.services;

import ar.com.utn.afip.TicketAcceso;
import ar.com.utn.exception.MercadoPagoException;
import ar.com.utn.mercadopago.model.ClientCredentials;
import ar.com.utn.models.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface PrestadorService {

    boolean cuitUnique(Long cuit);

    List<TipoTrabajo> getTiposTrabajos();

    List<ActividadAfip> getActividadesAfip();

    TicketAcceso getLastTicketAcceso();

    void saveTicketAcceso(TicketAcceso ta);

    void completeCredentials(ClientCredentials clientCredentials);

    Usuario findByPrestadorRenewMP(Prestador prestador) throws MercadoPagoException;

    void validarDatosAfip(Prestador prestador, Long cuitL, LocalDate fecha_nac, Sexo sexo);
}
