package com.escuelaing.edu.co.arep_lab4;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MovieClient {

    private static final MovieClient _instance = new MovieClient();
    protected static HashMap<String, Method> components = new HashMap<>();
    private static final Logger LOGGER = Logger.getLogger(MovieClient.class.getName());
    private static String directory = "src/resources/public";
    private static String contentType = "text/html";
    private static String currentPath = "/";

    private MovieClient() {
    }

    public static void main(String[] args) throws URISyntaxException, InvocationTargetException, IllegalAccessException, IOException {
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
        try (ServerSocket serverSocket = new ServerSocket(35000)) {
            while (true) {
                LOGGER.info("Listo para recibir ...");
                HTTPResponse(serverSocket.accept());
            }
        } catch (IOException e) {
            LOGGER.info("Could not listen on port: 35000.");
            System.exit(1);
        }
    }

    public static void HTTPResponse(Socket clientSocket) throws URISyntaxException, InvocationTargetException, IllegalAccessException {
        try (OutputStream outputStream = clientSocket.getOutputStream();
                PrintWriter out = new PrintWriter(outputStream, true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String requestLine = in.readLine();
            LOGGER.log(Level.INFO, "Received:{0}", requestLine);
            if (requestLine != null) {
                // Analiza la línea de solicitud para obtener la URI y los parámetros
                URI fileUrl = new URI(requestLine.split(" ")[1]);
                String params = fileUrl.getRawQuery();
                String path = fileUrl.getPath();
                LOGGER.log(Level.INFO, "Path: {0}", path);
                String outputLine;
                // Procesa la solicitud dependiendo del tipo de ruta
                if (path.startsWith("/activity")) {
                    String webUri = path.replace("/activity", "");
                    if (components.containsKey(webUri)) {
                        Map<String, String> parameters = parseParams(params);
                        if (components.get(webUri).getParameterCount() == 0) {
                            outputLine = (String) components.get(webUri).invoke(null);
                        } else {
                            outputLine = (String) components.get(webUri).invoke(null, parameters);
                        }
                        out.println(httpHeader(contentType).append(outputLine));
                    }
                } else if (path.contains(".")) {
                    String contentType = contentType(path);
                    if (contentType != null && contentType.contains("image")) {
                        outputStream.write(httpClientImage(path));
                    } else {
                        out.println(httpClientFiles(path));
                    }
                } else {
                    out.println(httpClientError());
                }
            }
            clientSocket.close();
        } catch (IOException e) {
            LOGGER.info("Accept failed.");
            System.exit(1);
        }
    }

    public static String httpClientError() {
        StringBuilder outputLine = new StringBuilder();
        outputLine.append("HTTP/1.1 404 Not Found\r\n");
        outputLine.append("Content-Type:text/html\r\n\r\n");
        Path file = Paths.get(directory + "/Error.html");
        Charset charset = StandardCharsets.UTF_8;
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            String line;
            while ((line = reader.readLine()) != null) {
                outputLine.append(line);
            }
        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
        }
        return outputLine.toString();
    }

    public static Map<String, String> parseParams(String queryString) {
        if (queryString != null) {
            Map<String, String> params = new HashMap<>();
            for (String param : queryString.split("&")) {
                String[] nameValue = param.split("=");
                params.put(nameValue[0], nameValue[1]);
            }
            return params;
        } else {
            return Collections.emptyMap();
        }
    }

    public static String httpClientFiles(String path) {
        StringBuilder outputLine = httpHeader(contentType(path));
        Path file = Paths.get(directory + path);
        Charset charset = StandardCharsets.UTF_8;
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            String line;
            while ((line = reader.readLine()) != null) {
                outputLine.append(line);
            }
        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
        }
        return outputLine.toString();
    }

    public static byte[] httpClientImage(String path) {
        Path file = Paths.get(directory + path);
        byte[] imageData = null;
        try {
            imageData = Files.readAllBytes(file);
        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
        }
        byte[] headerBytes = httpHeader(contentType(path)).toString().getBytes();
        assert imageData != null;
        int totalLength = headerBytes.length + imageData.length;
        byte[] combinedBytes = new byte[totalLength];
        System.arraycopy(headerBytes, 0, combinedBytes, 0, headerBytes.length);
        System.arraycopy(imageData, 0, combinedBytes, headerBytes.length, imageData.length);
        return combinedBytes;
    }

    public static StringBuilder httpHeader(String contentType) {
        StringBuilder header = new StringBuilder();
        header.append("HTTP/1.1 200 OK\r\n");
        header.append("Content-Type:");
        header.append(contentType);
        header.append("\r\n");
        header.append("\r\n");
        return header;
    }

    public static String contentType(String path) {
        File file = new File(path);
        String contentType = "";
        try {
            contentType = Files.probeContentType(file.toPath());
        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
        }
        return contentType;
    }

    public static void staticDirectory(String directoryPath) {
        directory = "src/resources/" + directoryPath;
    }

    public static void responseType(String responseType) {
        contentType = responseType;
    }

    public static void setCurrentPath(String path) {
        currentPath = path;
    }

    public static String getDirectory() {
        return directory;
    }

    public static String getContentType() {
        return contentType;
    }

    public static String getCurrentPath() {
        return currentPath;
    }
}
