# LABORATORIO #4:  TALLER DE ARQUITECTURAS DE SERVIDORES DE APLICACIONES, META PROTOCOLOS DE OBJETOS, PATRÓN IOC, REFLEXIÓN

Para este taller los estudiantes deberán construir un servidor Web (tipo Apache) en Java. El servidor debe ser capaz de entregar páginas html e imágenes tipo PNG. Igualmente el servidor debe proveer un framework IoC para la construcción de aplicaciones web a partir de POJOS. Usando el servidor se debe construir una aplicación Web de ejemplo. El servidor debe atender múltiples solicitudes no concurrentes.

Para este taller desarrolle un prototipo mínimo que demuestre capcidades reflexivas de JAVA y permita por lo menos cargar un bean (POJO) y derivar una aplicación Web a partir de él. 

Debe entregar su trabajo al final del laboratorio. Luego puede complementar para entregarlo en 8 días. Se verificara y compararán el commit del día de inicio del laboratorio y el dela entrega final.

### PREREQUISITOS

* [Java (desde la 15 para delante)](https://www.oracle.com/co/java/technologies/downloads/) 
* [Maven](https://maven.apache.org/download.cgi) 
* [Git](https://git-scm.com/downloads) 

### REQUISITOS

1. Contar con IDE para la ejecución del proyecto o línea de comandos.
2. Contar con los prerequisitos.
3. Al tenerlos, ejecutar el siguiente comando en la maquina

```bash
git clone https://github.com/FDanielMC/AREP_LAB-4.git
```

### JAVADOC
Usando el siguiente comando se genera la documentación del proyecto y para acceder a ella se encuentra en la carpeta targe/site/apidocs en el archivo index.html: 
```
mvn site
```
En caso que no aparezcan el JAVADOC luego de haber ejecutado ese comando se puede hacer mediante NETBEANS:

![image](https://github.com/FDanielMC/AREP_LAB-2/assets/123689924/c8aee78f-38c4-4a63-ad28-016ee38f8598)
![image](https://github.com/FDanielMC/AREP_LAB-2/assets/123689924/839db2ca-8927-4eb3-b217-057808a54ed0)

### DESCRIPCION DEL PROYECTO

* Servidor Web Java: Este servidor implementado en Java responde a las peticiones HTTP de los usuarios y se encarga de servir archivos estáticos como páginas HTML e imágenes, brindando así una experiencia interactiva y visual.

* Framework de Inversión de Control (IoC): Desarrollado a medida, este framework permite la elegante carga de objetos POJO (Plain Old Java Objects) anotados con la etiqueta @Component. A través de reflexión, identifica métodos marcados con @RequestMapping, los cuales actúan como controladores web, gestionando de manera dinámica las solicitudes entrantes.

* Controladores Web POJO: Estas clases son los protagonistas de la interacción con los usuarios. Anotadas con @Component, contienen métodos específicos marcados con @RequestMapping que manejan las solicitudes HTTP de manera eficiente y generan respuestas personalizadas, desde páginas HTML hasta datos en formato JSON, creando así una experiencia web fluida y atractiva.

* Proveedor de Datos de Películas OMDb: La clase OMDBProvider, se conecta a la API OMDb para obtener detallada información sobre películas. Este proveedor garantiza datos precisos y actualizados para enriquecer la experiencia cinematográfica de los usuarios.

## INICIANDO EL PROYECTO

```
1. En un IDE de desarrollo o en la línea de comandos se ejecuta la clase MovieClient.java, se recomienda hacerlo desde NetBeans. 
```

![image](https://github.com/FDanielMC/AREP_LAB-4/assets/123689924/f2384356-da1e-49fe-b8af-4aa734395b65)

## EJECUTAR PRUEBAS

Para ejecutar las pruebas ingrese el siguiente comando en la línea de comandos:
```
mvn test
```

## Casos de Prueba

  ### Caso de Prueba: Buscar archivos estáticos
  * ingresar a la URL http://localhost:35000/Browser.html

![image](https://github.com/FDanielMC/AREP_LAB-4/assets/123689924/2f8d5ec1-eda4-4cbc-9d40-42cbabcad319)

![image](https://github.com/FDanielMC/AREP_LAB-4/assets/123689924/4e4794d8-63b7-4429-a9f6-d55df1664012)

  * En caso que no esté el recurso o no se busque ningún recurso saldrá la página de error 404:

![image](https://github.com/FDanielMC/AREP_LAB-3/assets/123689924/5d3c45d0-0b91-4224-b69f-1517fffa42e7)

![image](https://github.com/FDanielMC/AREP_LAB-3/assets/123689924/8f0825fe-113e-4f59-bf11-d69044f5ebe7)

  ## Caso de Prueba: Buscar recursos REST con consultas
  * A su vez se puede ingresar parámetros en los recursos para poder interactuar con la página de cada recurso: /activity/movies?name=(nombre película) y /activity/Mensaje?mensaje=(mensaje que aparezca en pantalla)

![image](https://github.com/FDanielMC/AREP_LAB-4/assets/123689924/30715997-b35a-4e50-a0ab-4ef3df6e9ef3)

![image](https://github.com/FDanielMC/AREP_LAB-4/assets/123689924/b97701dc-d27c-483f-91e8-a7998f186229)

## DESARROLLADO CON

* [Java version 15 (Netbeans JDK 15)](https://www.oracle.com/co/java/technologies/downloads/)
* [Maven](https://maven.apache.org/download.cgi)
* [Git](https://git-scm.com/downloads)
* [omdbapi](https://www.omdbapi.com)

## Autor

* **Daniel Fernando Moreno Cerón** - [FDanielMC](https://github.com/FDanielMC)

### Licencia

This project is licensed under the MIT License - see the LICENSE.md file for details

### Agradecimientos

Escuela Colombiana de Ingeniería

