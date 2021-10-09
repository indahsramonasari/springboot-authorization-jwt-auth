package com.id.userproductservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {GenericException.class})
    public ResponseEntity genericException(GenericException ex, WebRequest webRequest) {
        ApiErrorRestEx apiErrorRestEx = new ApiErrorRestEx();
        apiErrorRestEx.setStatus(HttpStatus.BAD_REQUEST);
        apiErrorRestEx.setErrorCode(ex.getErrorCode());
        apiErrorRestEx.setStatusMessage(ex.getStatusMessage());
        apiErrorRestEx.setErrorMessage(ex.getErrorMessage());
        return buildResponseEntity(apiErrorRestEx);
    }

    public ResponseEntity<Object> buildResponseEntity(ApiErrorRestEx apiErrorRestEx) {
        return new ResponseEntity(apiErrorRestEx, apiErrorRestEx.getStatus());
    }

}
