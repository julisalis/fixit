package ar.com.utn.mercadopago;

import ar.com.utn.models.Postulacion;
import ar.com.utn.models.Usuario;

/**
 * Created by julian on 22/10/17.
 */
public interface MoneyFlowService {
    void makePaymentMP(Postulacion postulacion, String tokenMP, String paymentMethodId, Usuario usuario);
}
