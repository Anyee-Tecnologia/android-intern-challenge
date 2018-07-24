package com.desafiojoke.joke;

import com.desafiojoke.joke.Remote.JokeService;
import com.desafiojoke.joke.Remote.RetrofitClient;

import retrofit2.Retrofit;

public class Common {
    private static final String BASE_URL = "https://api.chucknorris.io/jokes/";

    /*
     * @method getValueService vai retornar a URL obtida na API
     * */

    public static JokeService getValueService(){
        return RetrofitClient.getClient(BASE_URL).create(JokeService.class);
    }
}
