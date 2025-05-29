package com.musinsacoordi.backend.common.dto;

import com.musinsacoordi.backend.common.error.ErrorCode;
import lombok.Getter;

/**
 * 표준 API 응답 형식을 제공하는 클래스
 * 성공 응답과 오류 응답을 일관된 형식으로 처리합니다.
 * 
 * @param <T> 응답 데이터의 타입
 */
@Getter
public class BaseResponse<T> {
    private final T data;
    private final ErrorDetail error;

    /**
     * 성공 응답을 위한 생성자
     * 
     * @param data 응답 데이터
     */
    public BaseResponse(T data) {
        this.data = data;
        this.error = null;
    }

    /**
     * 오류 응답을 위한 생성자
     * 
     * @param data 응답 데이터 (일반적으로 null)
     * @param error 오류 상세 정보
     */
    private BaseResponse(T data, ErrorDetail error) {
        this.data = data;
        this.error = error;
    }

    /**
     * 데이터와 함께 성공 응답을 생성하는 정적 메소드
     * 
     * @param data 응답 데이터
     * @return 성공 응답 객체
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(data);
    }

    /**
     * 에러 응답을 생성하는 정적 메소드
     * 
     * @param errorCode 에러 코드
     * @param message 사용자 정의 에러 메시지 (null인 경우 errorCode의 기본 메시지 사용)
     * @return 에러 응답 객체
     */
    public static <T> BaseResponse<T> error(ErrorCode errorCode, String message) {
        ErrorDetail errorDetail = new ErrorDetail(
                errorCode.getHttpStatus().value(),
                errorCode.name(),
                message != null ? message : errorCode.getMessage()
        );
        return new BaseResponse<>(null, errorDetail);
    }

    /**
     * 에러 코드만 사용하는 경우의 에러 응답 생성 메소드
     * 
     * @param errorCode 에러 코드
     * @return 에러 응답 객체
     */
    public static <T> BaseResponse<T> error(ErrorCode errorCode) {
        return error(errorCode, null);
    }

    /**
     * API 오류 응답의 상세 정보를 담는 내부 클래스
     */
    @Getter
    public static class ErrorDetail {
        private final int status;    // HTTP 상태 코드
        private final String code;    // 에러 코드 문자열
        private final String message; // 에러 메시지

        /**
         * 에러 상세 정보 생성자
         * 
         * @param status HTTP 상태 코드
         * @param code 에러 코드 문자열
         * @param message 에러 메시지
         */
        public ErrorDetail(int status, String code, String message) {
            this.status = status;
            this.code = code;
            this.message = message;
        }
    }
}
