package ar.com.utn.mercadopago;

import ar.com.utn.models.Postulacion;
import ar.com.utn.models.Prestador;
import ar.com.utn.models.Usuario;

/**
 * Created by julian on 22/10/17.
 */
public interface MoneyFlowService {

    String efectuarPago(PaymentMP paymentMP, Prestador prestador) throws Exception;

    PaymentMP completePayment(Postulacion postulacion, String tokenMP, String paymentMethodId, Usuario tomador);
}
