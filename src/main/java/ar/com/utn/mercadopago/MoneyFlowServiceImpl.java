package ar.com.utn.mercadopago;

import ar.com.utn.mercadopago.model.UserMP;
import ar.com.utn.models.Postulacion;
import ar.com.utn.models.Prestador;
import ar.com.utn.models.Usuario;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MoneyFlowServiceImpl implements MoneyFlowService {

    @Value("${app.mercadopago.application_fee}")
    private double commission;

    @Autowired
    private MercadoPagoApi mercadoPagoApi;

    @Override
    public JSONObject efectuarPago(PaymentMP paymentMP, Prestador prestador) throws Exception {
        mercadoPagoApi.setAccessToken(prestador.getMpPrestador().getAccessToken());
        return mercadoPagoApi.makePayment(paymentMP);
    }
    @Override
    public PaymentMP completePayment(Postulacion postulacion, String tokenMP, String paymentMethodId, Usuario tomador) {
        UserMP userMP = mercadoPagoApi.buildUserMP(tomador);
        AdditionalInfoMP additionalInfoMP = mercadoPagoApi.buildAdditionalInfoMP(tomador);
        PaymentMP paymentMP = mercadoPagoApi.buildPaymentMP(postulacion.getPresupAprox(), tokenMP,"Contrato profesional FixIT",1, paymentMethodId, userMP,additionalInfoMP,commission);
        return paymentMP;
    }

    @Override
    public String getPaymentId(JSONObject payment) throws JSONException {
       return mercadoPagoApi.getPaymentMPId(payment);
    }

    @Override
    public String getDescription(JSONObject payment) throws JSONException {
        return mercadoPagoApi.getDescription(payment);
    }
}
