# Parcial Backend - Sistema de Detección de ADN Mutante 

Este proyecto implementa un servicio backend en Java que permite la detección de ADN mutante a partir de secuencias genéticas proporcionadas en formato de cadenas de ADN. El sistema valida si una secuencia de ADN pertenece a un humano o a un mutante, basándose en ciertos patrones genéticos específicos. 

## Características del Proyecto

- **Detección de ADN Mutante**: Utiliza algoritmos para verificar si las secuencias de ADN contienen patrones mutantes definidos (por ejemplo, secuencias repetitivas de 4 caracteres iguales en horizontal, vertical o diagonal).
  
- **Base de Datos**: Los registros de ADN procesados se almacenan en una base de datos. Las secuencias de ADN ya procesadas no se repiten, y se pueden consultar los resultados anteriores.
  
- **Estadísticas de Secuencias**: El servicio mantiene un contador de cuántos ADN mutantes y humanos han sido procesados, permitiendo calcular una relación entre estos dos tipos de ADN.
  
- **Validación de Formato de ADN**: Se realiza una validación previa del formato de la secuencia genética para asegurar que el ADN sea válido antes de procesarlo.

- **Interfaz RESTful**: El servicio se expone a través de una API RESTful para facilitar su integración con otros servicios y aplicaciones. Permite recibir secuencias de ADN en formato JSON, procesarlas y devolver si son mutantes o no.

## Tecnologías Utilizadas

- **Java 17**: Lenguaje de programación utilizado para el desarrollo del servicio.
- **Spring Boot**: Framework utilizado para la creación de la API RESTful y la lógica de backend.
- **JUnit**: Framework de testing utilizado para la prueba de las funcionalidades del servicio.
- **Mockito**: Biblioteca para el manejo de mocks en las pruebas unitarias.
- **H2 Database**: Base de datos en memoria utilizada para almacenar las secuencias de ADN procesadas.
- **Maven** o **Gradle**: Herramienta de construcción y gestión de dependencias.

## Funcionalidades

**La ruta del dominio para probar el proyecto con postman es : https://desarrollo-de-software-mutantes.onrender.com**
1. **POST /dna**: Recibe una secuencia de ADN en formato JSON, la procesa y determina si pertenece a un humano o a un mutante.
   - Request Body: Un objeto JSON con una lista de cadenas que representan las secuencias de ADN.
   - Response: Un JSON indicando si la secuencia es mutante (`true`) o humano (`false`).
  
2. **GET /stats**: Devuelve las estadísticas de las secuencias procesadas, incluyendo el conteo de ADN mutante y humano, así como la relación entre ambos.
   - Response: Un objeto JSON con los contadores y la relación entre los tipos de ADN.

- *Controladores*: Lógica para manejar las peticiones HTTP y responder con los resultados correspondientes.
- *Servicios*: Implementación de la lógica de negocio para determinar si una secuencia de ADN es mutante o humana.
- *Repositorios*: Interacción con la base de datos para almacenar y consultar las secuencias de ADN procesadas.
- *Entidades*: Modelos que representan las tablas en la base de datos, como DNAMuestra.

## Instalación y Ejecución

### Requisitos

- *Java 17 o superior*
- *Maven o Gradle* (dependiendo de tu elección de construcción)
- *H2 Database* (para la base de datos en memoria)

### Pasos para ejecutar

1. Clona este repositorio:

   ```bash
   git clone <URL del repositorio>
   cd <nombre_del_repositorio>
