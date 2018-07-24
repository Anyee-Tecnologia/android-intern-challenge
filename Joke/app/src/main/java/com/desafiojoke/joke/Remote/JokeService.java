package com.desafiojoke.joke.Remote;

import com.desafiojoke.joke.Models.Joke;

import retrofit2.Call;
import retrofit2.http.GET;

/*
* @method GET vai pegar a ultima parte do caminho da URL sem a necessidade de adicionar toda a URL
* */
public interface JokeService {
    @GET("random")
    Call<Joke> getJoke();

}
