package com.desafiojoke.joke.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
* @method RetrofitClient é nosso cliente para requisições, vai converter o formato Json obtido
* da API atraves do método addConverterFactory
* @param baseUrl é onde contem o endereço URL para as requisições na API que será utilizada no web service
* o endereço da URL é informado na classe Common, BASE_URL
* */
public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
