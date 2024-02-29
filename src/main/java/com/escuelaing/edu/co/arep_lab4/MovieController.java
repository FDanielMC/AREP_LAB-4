package com.escuelaing.edu.co.arep_lab4;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MovieController {

    private static final ConcurrentHashMap<String, String> CACHE = new ConcurrentHashMap<>();
    private static final OMDBProvider movieDataProvider = new OMDBProvider();

    @RequestMapping("/movies")
    public static String movies(Map<String, String> p) {
        MovieClient.responseType("application/json");
        return CACHE.computeIfAbsent(p.get("title"), movieDataProvider::fetchMovieData);
    }
}
