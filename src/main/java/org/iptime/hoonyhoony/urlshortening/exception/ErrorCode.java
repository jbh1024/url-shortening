package org.iptime.hoonyhoony.urlshortening.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    URL_ERROR("잘못된 URL 입니다.", HttpStatus.BAD_REQUEST),
    INTERNAL_ERROR("일시적인 에러입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    CREATION_ERROR("생성에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    ErrorCode(String errorMessage, HttpStatus status) {
        this.errorMessage = errorMessage;
        this.status = status;
    }

    private String errorMessage;

    private HttpStatus status;

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
