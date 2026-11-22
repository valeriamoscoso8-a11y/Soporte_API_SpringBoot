package pe.edu.soporte.exception;

import java.util.Map;
import java.util.TreeMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ManejadorApi {

    public record ErrorApi(int estado, String mensaje, Map<String, String> campos) {
    }

    @ExceptionHandler(SolicitudNoEncontradaException.class)
    public ResponseEntity<ErrorApi> noEncontrada(SolicitudNoEncontradaException error) {
        return responder(HttpStatus.NOT_FOUND, error.getMessage(), Map.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorApi> validacion(MethodArgumentNotValidException error) {
        Map<String, String> campos = new TreeMap<>();
        error.getBindingResult().getFieldErrors().forEach(campo ->
                campos.putIfAbsent(campo.getField(), campo.getDefaultMessage()));
        return responder(HttpStatus.BAD_REQUEST, "Revise los campos indicados", campos);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorApi> cuerpoInvalido() {
        return responder(HttpStatus.BAD_REQUEST,
                "Envíe un JSON válido con los tipos y valores de estado documentados", Map.of());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorApi> identificadorInvalido() {
        return responder(HttpStatus.BAD_REQUEST, "El identificador debe ser un número entero", Map.of());
    }

    private ResponseEntity<ErrorApi> responder(HttpStatus estado, String mensaje,
            Map<String, String> campos) {
        return ResponseEntity.status(estado).body(new ErrorApi(estado.value(), mensaje, campos));
    }
}
