package com.escuelaing.edu.co.arep_lab4;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MovieController {


    private static final OMDBProvider omdbProvider = new OMDBProvider();

    @RequestMapping("/movies")
    public static String movies(Map<String, String> p) {
        String bodyFile = "";
        try {
            MovieClient.responseType("text/html");
            JsonObject resp = omdbProvider.searchMovie(p.get("name"));
            JsonElement poster, title, released, genre, director, actors,
                    language, plot;
            poster = resp.get("Poster");
            title = resp.get("Title");
            released = resp.get("Released");
            genre = resp.get("Genre");
            director = resp.get("Director");
            actors = resp.get("Actors");
            language = resp.get("Language");
            plot = resp.get("Plot");
            bodyFile = MovieClient.httpClientFiles("/MoviePage.html").replace("{Poster}", poster.getAsString())
                    .replace("{Title}", title.getAsString()).replace("{Released}", released.getAsString())
                    .replace("{Genre}", genre.getAsString()).replace("{Director}", director.getAsString())
                    .replace("{Actors}", actors.getAsString()).replace("{Language", language.getAsString())
                    .replace("{Plot", plot.getAsString());

        } catch (Exception e) {
            System.err.println("Error al procesar la película:");
            bodyFile = MovieClient.httpClientError();
            e.printStackTrace();
        }
        return bodyFile;
    }
}