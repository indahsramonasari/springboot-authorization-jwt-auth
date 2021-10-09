package com.id.userproductservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenericException extends RuntimeException {
    private final String errorCode;
    private final String statusMessage;
    private final String errorMessage;
}
