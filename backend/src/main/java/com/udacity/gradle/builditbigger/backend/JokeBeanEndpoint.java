package com.udacity.gradle.builditbigger.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.udacity.gradle.javajoke.Jokes;

import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "jokeBeanApi",
        version = "v1",
        resource = "jokeBean",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.gradle.udacity.com",
                ownerName = "backend.builditbigger.gradle.udacity.com",
                packagePath = ""
        )
)
public class JokeBeanEndpoint {

    @ApiMethod(name = "showJoke")
    public JokeBean showJoke() {
        Jokes joke = new Jokes();
        JokeBean response = new JokeBean();
        response.setData(joke.getJoke());

        return response;
    }
}