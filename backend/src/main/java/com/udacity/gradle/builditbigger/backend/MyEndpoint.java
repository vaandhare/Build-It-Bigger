package com.udacity.gradle.builditbigger.backend;

import com.example.android.jokelib.JokeCreator;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.Random;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.gradle.udacity.com",
                ownerName = "backend.builditbigger.gradle.udacity.com"))
public class MyEndpoint {

    @ApiMethod(name = "getJokes")
    public MyBean getJokes() {
        JokeCreator joke = new JokeCreator();
        MyBean response = new MyBean();
        response.setData(joke.getJokes().get(new Random().nextInt(joke.getJokes().size())));
        return response;
  }
}
