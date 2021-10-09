package com.id.userproductservice.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorRestEx {

    private String errorCode;
    private String statusMessage;
    private String errorMessage;
    private HttpStatus status;
}
