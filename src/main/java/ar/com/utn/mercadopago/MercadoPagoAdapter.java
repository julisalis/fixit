package ar.com.utn.mercadopago;

import ar.com.utn.exception.MercadoPagoException;
import ar.com.utn.mercadopago.model.ClientCredentials;

/**
 * Created by julian on 14/10/17.
 */
public interface MercadoPagoAdapter {
    ClientCredentials getClientCredentials(String var1, String var2) throws MercadoPagoException;

    ClientCredentials renewClientCredentials(String var1) throws MercadoPagoException;
}