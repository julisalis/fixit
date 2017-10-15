package ar.com.utn.mercadopago;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by iara on 14/10/17.
 */
public class FactoryClient {

    public static <T> T buildClient(Class<T> clazz) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getBaseUrl(clazz))
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        T service = retrofit.create(clazz);

        return service;
    }

    private static <T> String getBaseUrl(Class<T> clazz) {
        String baseUrl = null;
        if (clazz.isAssignableFrom(MercadoPagoClient.class)) {
            baseUrl = "https://api.mercadopago.com";
        }

        return baseUrl;
    }
}
