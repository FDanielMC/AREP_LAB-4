package com.escuelaing.edu.co.arep_lab4;

import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Logger;

public class MovieClient {

    private static final MovieClient _instance = new MovieClient();
    protected static HashMap<String, Method> components = new HashMap<>();
    private static final Logger LOGGER = Logger.getLogger(MovieClient.class.getName());
    private static String directory = "src/resources/public";
    private static String contentType = "text/html";


    private MovieClient() {
    }

    public static void main(String[] args) throws URISyntaxException, InvocationTargetException, IllegalAccessException {
        Reflections reflections = new Reflections("com.escuelaing.edu.co.arep_lab4");
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Component.class);
        for (Class<?> c : classes) {
            for (Method method : c.getDeclaredMethods()) {
                System.out.println("Métodos :" + method.getName());
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    components.put(method.getAnnotation(RequestMapping.class).value(), method);
                }
            }
        }
    }
    
    public static void responseType(String responseType) {
        contentType = responseType;
    }

}
