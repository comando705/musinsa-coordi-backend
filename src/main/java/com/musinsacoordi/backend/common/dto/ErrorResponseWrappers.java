package com.musinsacoordi.backend.common.dto;

import com.musinsacoordi.backend.common.error.ErrorResponse;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response wrapper classes for Swagger documentation.
 * These classes are used only for OpenAPI documentation purposes.
 */
public class ErrorResponseWrappers {

    @Schema(description = "ERROR 공통 응답")
    public static class ErrorResponseWrapper extends BaseResponse<ErrorResponse.ErrorResponseBuilder> {
        public ErrorResponseWrapper() {
            super(null);
        }
    }
}
