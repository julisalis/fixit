package ar.com.utn.mercadopago;

import ar.com.utn.mercadopago.model.ClientCredentials;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by julian on 14/10/17.
 */
public interface MercadoPagoClient{

    @Headers({
            "accept: application/json",
            "content-type: application/x-www-form-urlencoded"
    })
    @FormUrlEncoded
    @POST("/oauth/token")
    Call<ClientCredentials> getClientCredentials(@Field("client_secret") String clientSecret,
                                                 @Field("grant_type") String grantType, @Field("code") String code,
                                                 @Field("redirect_uri") String redirectUri);


    @Headers({
            "accept: application/json",
            "content-type: application/x-www-form-urlencoded"
    })
    @FormUrlEncoded
    @POST("/oauth/token")
    Call<ClientCredentials> renewClientCredentials(@Field("client_secret") String clientSecret,
                                                   @Field("grant_type") String grantType,@Field("refresh_token") String refreshToken);

}
