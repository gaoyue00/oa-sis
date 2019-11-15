package com.cjs.example.lock.exception;

@SuppressWarnings("serial")
public class ApiUnauthorizedException extends ApiException {

    public ApiUnauthorizedException(int errorCode, String message) {
        super(errorCode, message);
    }

    public ApiUnauthorizedException(ApiErrorEnum apiErrorEnum) {
        super(apiErrorEnum);
    }
}
