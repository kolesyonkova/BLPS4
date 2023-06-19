package itmo.blps.mommy.exception;

import itmo.blps.mommy.controller.AuthController;
import itmo.blps.mommy.controller.OrderPurchaseController;
import itmo.blps.mommy.controller.ProductController;
import itmo.blps.mommy.controller.PurchaseController;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(assignableTypes = {AuthController.class, OrderPurchaseController.class,
        ProductController.class, PurchaseController.class})
public class ProcessExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<?> handleException(HttpMessageNotReadableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Invalid body");
    }


    @ExceptionHandler({ConstraintViolationException.class, BindException.class, MethodArgumentNotValidException.class,
            MissingServletRequestParameterException.class, IllegalArgumentException.class})
    public ResponseEntity<?> handleValidationException(Exception ex) {
        String message = ex.getMessage();
        if (ex instanceof MethodArgumentNotValidException) {
            message = "Некорректные параметры " + getValidationError((MethodArgumentNotValidException) ex);
        }
        return ResponseEntity.badRequest()
                .body(message);
    }

    private String getValidationError(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .sorted()
                .collect(Collectors.joining(";"));
    }
}
