package ar.com.utn.mercadopago;

import ar.com.utn.exception.MercadoPagoException;
import ar.com.utn.mercadopago.model.ClientCredentials;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

/**
 * Created by iara on 14/10/17.
 */

@Service
@Scope("prototype")
public class MercadoPagoAdapterImpl implements MercadoPagoAdapter {
    static final Logger logger = Logger.getLogger(MercadoPagoAdapterImpl.class);
    private static final String GRANT_TYPE_AUTH = "authorization_code";
    private static final String GRANT_TYPE_REFRESH = "refresh_token";
    private MercadoPagoClient mercadoPagoClient = (MercadoPagoClient)FactoryClient.buildClient(MercadoPagoClient.class);
    @Value("${app.mercadopago.access_token}")
    private String clientSecret;

    protected MercadoPagoAdapterImpl() {
    }

    public ClientCredentials getClientCredentials(String code, String redirectUri) throws MercadoPagoException {
        Call call = this.mercadoPagoClient.getClientCredentials(this.clientSecret, GRANT_TYPE_AUTH, code, redirectUri);

        try {
            Response<ClientCredentials> response = call.execute();
            if(response.code() != 200) {
                logger.error(response.errorBody().string());
                throw new MercadoPagoException();
            } else {
                return (ClientCredentials)response.body();
            }
        } catch (IOException var5) {
            logger.error("ERROR", var5);
            throw new MercadoPagoException();
        }
    }

    public ClientCredentials renewClientCredentials(String refreshToken) throws MercadoPagoException {
        Call call = this.mercadoPagoClient.renewClientCredentials(this.clientSecret, GRANT_TYPE_REFRESH, refreshToken);

        try {
            Response<ClientCredentials> response = call.execute();
            if(response.code() != 200) {
                logger.error(response.errorBody());
                throw new MercadoPagoException();
            } else {
                return (ClientCredentials)response.body();
            }
        } catch (IOException var4) {
            logger.error("ERROR", var4);
            throw new MercadoPagoException();
        }
    }
}

