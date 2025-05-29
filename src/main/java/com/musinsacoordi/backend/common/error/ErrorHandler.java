package com.musinsacoordi.backend.common.error;

import com.musinsacoordi.backend.common.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    /**
     * BaseException 공통 예외 처리
     */
    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<BaseResponse<Object>> handleBaseException(BaseException e) {
        log.error("BaseException", e);
        ErrorCode errorCode = e.getErrorCode();
        String message = e.getMessage();

        BaseResponse<Object> response = BaseResponse.error(errorCode, message);
        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
    }

    /**
     * 필수 파라미터가 없을 경우
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<BaseResponse<Object>> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e) {
        log.error("MissingServletRequestParameterException", e);
        String message = String.format("필수 파라미터가 누락되었습니다. 파라미터명: %s, 타입: %s",
                e.getParameterName(), e.getParameterType());

        BaseResponse<Object> response = BaseResponse.error(CommonErrorCode.BAD_REQUEST, message);
        return ResponseEntity.status(CommonErrorCode.BAD_REQUEST.getHttpStatus()).body(response);
    }

    /**
     * JSON 파싱 실패
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<BaseResponse<Object>> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {
        log.error("HttpMessageNotReadableException", e);
        BaseResponse<Object> response = BaseResponse.error(
            CommonErrorCode.BAD_REQUEST,
            "JSON 파싱에 실패했습니다. 요청 본문을 확인해주세요."
        );
        return ResponseEntity.status(CommonErrorCode.BAD_REQUEST.getHttpStatus()).body(response);
    }

    /**
     * 지원하지 않는 HTTP method 호출
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<BaseResponse<Object>> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        log.error("HttpRequestMethodNotSupportedException", e);
        BaseResponse<Object> response = BaseResponse.error(
            CommonErrorCode.METHOD_NOT_ALLOWED,
            String.format("지원하지 않는 HTTP Method입니다. 요청: %s", e.getMethod())
        );
        return ResponseEntity.status(CommonErrorCode.METHOD_NOT_ALLOWED.getHttpStatus()).body(response);
    }

    /**
     * 데이터 무결성 위반 (예: 유니크 제약조건 위반)
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<BaseResponse<Object>> handleDataIntegrityViolationException(
            DataIntegrityViolationException e) {
        log.error("DataIntegrityViolationException", e);
        BaseResponse<Object> response = BaseResponse.error(
            CommonErrorCode.ALREADY_EXIST_ENTITY,
            "데이터 무결성 제약조건을 위반했습니다."
        );
        return ResponseEntity.status(CommonErrorCode.ALREADY_EXIST_ENTITY.getHttpStatus()).body(response);
    }

    /**
     * 나머지 예외 처리
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<BaseResponse<Object>> handleException(Exception e) {
        log.error("Exception", e);
        BaseResponse<Object> response = BaseResponse.error(CommonErrorCode.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(CommonErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus()).body(response);
    }
}
