# API REST de solicitudes de soporte técnico

Evaluación parcial de Desarrollo de los Componentes del Negocio. La API registra, consulta, actualiza y elimina solicitudes. Los registros se guardan durante la ejecución y se pierden al reiniciar.

## Requisitos y arranque

1. Instala JDK 21 y Maven 3.6.3 o superior. Comprueba la instalación con `java -version` y `mvn -version`.
2. Abre una terminal en la carpeta que contiene `pom.xml`. La primera ejecución requiere acceso a Maven Central para descargar las dependencias.
3. Ejecuta `mvn spring-boot:run`. La URL local es `http://localhost:8080`.

Para generar un archivo ejecutable, usa `mvn clean package` y luego `java -jar target/soporte-api-1.0.0.jar`.

## Endpoints

| Método | Ruta | Resultado | Errores previstos |
|---|---|---|---|
| POST | `/api/solicitudes` | 201 y encabezado `Location` | 400 |
| GET | `/api/solicitudes` | 200 y lista ordenada por creación | |
| GET | `/api/solicitudes/{id}` | 200 y solicitud | 400, 404 |
| PUT | `/api/solicitudes/{id}` | 200 y solicitud actualizada | 400, 404 |
| DELETE | `/api/solicitudes/{id}` | 204 sin cuerpo | 400, 404 |

## Cuerpo JSON para POST y PUT

Envía `Content-Type: application/json`. Ejemplo de registro:

```json
{
  "titulo": "Impresora sin conexión",
  "descripcion": "El equipo de recepción no puede imprimir.",
  "cliente": {
    "nombre": "Ana Pérez",
    "correo": "ana@example.com"
  },
  "estado": "PENDIENTE"
}
```

El título y la descripción son obligatorios y admiten 120 y 2000 caracteres respectivamente. El cliente debe incluir nombre y correo válido. El técnico es opcional y usa la misma estructura de nombre y correo. El estado es obligatorio y acepta `PENDIENTE`, `EN_PROCESO` o `RESUELTA`.

La API genera el `id` y la `fechaCreacion`. PUT reemplaza los campos editables y conserva el ID y la fecha de creación. Enviar `tecnico: null` u omitirlo en PUT quita la asignación. Para asignar un técnico en PUT, incluye por ejemplo `"tecnico": {"nombre": "Luis Torres", "correo": "luis@example.com"}` en el cuerpo completo.

Ejemplo de respuesta de validación:

```json
{
  "estado": 400,
  "mensaje": "Revise los campos indicados",
  "campos": {
    "titulo": "El título es obligatorio"
  }
}
```

## Estructura

- `controller`: recibe JSON y traduce las operaciones del servicio a respuestas HTTP.
- `service`: define el contrato CRUD y lo implementa con un mapa en memoria sincronizado.
- `model`: define solicitudes, clientes, técnicos y estados.
- `dto`: define los cuerpos de entrada con validaciones y el formato de salida.
- `exception`: centraliza errores de validación, formato e identificadores inexistentes con `@RestControllerAdvice`.

## Pruebas en Postman

1. Con la aplicación en ejecución, importa `postman/Soporte_API.postman_collection.json`.
2. Ejecuta **Run collection** en el orden original. La primera petición guarda el ID generado para las siguientes. Si cambias el puerto, ajusta la variable `baseUrl`.
3. Guarda una captura del resumen de ejecución y capturas de las respuestas que evidencien el CRUD y los errores HTTP 400 y 404.

La carpeta `postman/evidencia` incluye el resultado real de Newman y las respuestas HTTP observadas en una segunda ejecución.

## Publicación y entrega

Crea un repositorio en GitHub, sube el contenido de esta carpeta (incluido `pom.xml`) y comprueba el acceso de lectura del docente. Si el repositorio es privado, concede acceso a su cuenta. Copia la URL en el informe PDF, completa los nombres y funciones de los integrantes, adjunta las capturas y entrega el informe en EVA desde una sola cuenta.
