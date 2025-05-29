package com.musinsacoordi.backend.common.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
@Schema(description = "ERROR 공통 응답")
public class ErrorResponse {
    private final int status;
    private final String code;
    private final String message;

    @Builder
    public ErrorResponse(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}