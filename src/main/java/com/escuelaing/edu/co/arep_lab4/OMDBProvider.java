/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.escuelaing.edu.co.arep_lab4;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Fachada para realizar solicitudes a la API REST para obtener toda la información de las películas
 * 
 * @author Daniel Fernando Moreno Cerón
 */
public class OMDBProvider {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String GET_URL = "https://www.omdbapi.com/?apikey=2d61ae7e&t=";
    private static final Logger LOGGER = Logger.getLogger(OMDBProvider.class.getName());

    public String fetchMovieData(String titleValue) {
        String outputLine = null;
        try {
            LOGGER.info(titleValue);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(GET_URL + titleValue)).header("User-Agent", USER_AGENT).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int responseCode = response.statusCode();
            LOGGER.log(Level.INFO, "GET Response Code :: {0}", responseCode);

            if (responseCode == 200) { // success
                String movieInformation = response.body();
                LOGGER.log(Level.INFO, "Response body: {0}", movieInformation);
                outputLine = movieInformation;
            } else {
                LOGGER.info("GET request not worked");
            }
            LOGGER.info("GET DONE");
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error fetching movie data: {0}", e.getMessage());
            Thread.currentThread().interrupt();
        }
        return outputLine;
    }
    
}
