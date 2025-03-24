package com.auth.userAuth.advice;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ApiError {
    private String error;
    private HttpStatus httpStatus;
}
