package com.escuelaing.edu.co.arep_lab4;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class HelloController {

    @RequestMapping("/Mensaje")
    public static String mensaje(Map<String, String> p) {
        MovieClient.responseType("text/html");
        return "<h1>" + p.get("mensaje") + "</h1>";
    }
}
