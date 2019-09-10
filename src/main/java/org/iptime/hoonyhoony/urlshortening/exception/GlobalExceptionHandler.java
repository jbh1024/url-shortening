package org.iptime.hoonyhoony.urlshortening.exception;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    //custom exception 처리
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Map handleCustomException(CustomException e, HttpServletResponse response, HttpServletRequest request) {
        response.setStatus(e.getErrorCode().getStatus().value());
        return ImmutableMap.of("message", e.getErrorCode().getErrorMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Map handleException(Exception e, HttpServletResponse response, HttpServletRequest request) {
        return ImmutableMap.of("message", ErrorCode.INTERNAL_ERROR.getErrorMessage());
    }
}
