package ar.com.utn.mercadopago;

import ar.com.utn.exception.MercadoPagoException;
import ar.com.utn.mercadopago.model.UserMP;
import ar.com.utn.models.Usuario;
import com.google.gson.Gson;
import com.mercadopago.MP;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
//import org.codehaus.jettison.json.JSONException;
//import org.codehaus.jettison.json.JSONObject;

/**
 * Created by julian on 12/10/17.
 */
@Component
public class MercadoPagoApiImpl implements MercadoPagoApi {
    static final Logger logger = Logger.getLogger(MercadoPagoApiImpl.class);

    private final String PAYMENTS_URL = "/v1/payments";

    private String accessToken;

    private MP getMPInstance(){
        MP mp = new MP(accessToken);
        return mp;
    }

    @Override
    public UserMP buildUserMP(Usuario user){
        UserMP userMP = new UserMP(user.getEmail());
        return userMP;
    }

    @Override
    public AdditionalInfoMP buildAdditionalInfoMP(Usuario usuario) {
        return new AdditionalInfoMP(usuario);
    }

    @Override
    public PaymentMP buildPaymentMP(BigDecimal presupAprox, String tokenMP, String title, int installments, String paymentMethodId, UserMP userMP, AdditionalInfoMP additionalInfoMP, double commission) {
        PaymentMP paymentMP = new PaymentMP(presupAprox.doubleValue(), tokenMP, title,installments, paymentMethodId, userMP,additionalInfoMP,commission);
        return paymentMP;
    }


    @Override
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


    private String getPaymentMPId(JSONObject jsonObject) throws JSONException {
        return jsonObject.getJSONObject("response").getString("id");
    }

    @Override
    public String makePayment(PaymentMP paymentMP) throws Exception{
        MP mp = getMPInstance();
        Gson gson = new Gson();
        Map<String,Object> map = new HashMap<>();
        map.put("transaction_amount",paymentMP.getTransaction_amount());
        map.put("token",paymentMP.getToken());
        map.put("description",paymentMP.getDescription());
        map.put("installments",paymentMP.getInstallments());
        map.put("payment_method_id",paymentMP.getPayment_method_id());
        map.put("binary_mode",paymentMP.isBinary_mode());
        map.put("payer",paymentMP.getPayer());
        map.put("additional_info",paymentMP.getAdditional_info());
        String paymentMPJson = gson.toJson(map);
        JSONObject payment = mp.post(PAYMENTS_URL, paymentMPJson);
        controlResponse(payment);
        controlResponsePayment(payment);
        return getPaymentMPId(payment);
    }

    private void controlResponsePayment(JSONObject jsonObject)throws Exception{
        if(!jsonObject.getJSONObject("response").getString("status").equals("approved")){
            logger.error(jsonObject);
            throw new MercadoPagoException();
        }
    }


    private void controlResponse(JSONObject jsonObject)throws Exception{
        if(jsonObject.getInt("status") == 400 || jsonObject.getInt("status") == 401 ||
                jsonObject.getInt("status") == 404){
            logger.error(jsonObject);
            throw new MercadoPagoException();
        }
    }


}
