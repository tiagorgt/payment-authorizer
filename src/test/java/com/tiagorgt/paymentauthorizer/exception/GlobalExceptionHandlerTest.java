package com.tiagorgt.paymentauthorizer.exception;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import com.tiagorgt.paymentauthorizer.dto.ErrorResponseDTO;
import com.tiagorgt.paymentauthorizer.enums.ErrorCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    public void testHandleValidationExceptions() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        WebRequest request = mock(WebRequest.class);
        BindingResult bindingResult = mock(BindingResult.class);

        Map<String, String> errors = new HashMap<>();
        errors.put("field", "error message");

        when(bindingResult.getFieldErrors()).thenReturn(List.of());
        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(request.getDescription(false)).thenReturn("description");

        ResponseEntity<ErrorResponseDTO> response = handler.handleValidationExceptions(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ErrorCode.VALIDATION_FAILED, response.getBody().getErrorCode());
    }

    @Test
    public void testHandleGlobalException() {
        Exception ex = new Exception("Unexpected error");
        WebRequest request = mock(WebRequest.class);

        when(request.getDescription(false)).thenReturn("description");

        ResponseEntity<ErrorResponseDTO> response = handler.handleGlobalException(ex, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(ErrorCode.UNEXPECTED_ERROR, response.getBody().getErrorCode());
    }
}