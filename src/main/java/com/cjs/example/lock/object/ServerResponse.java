package com.cjs.example.lock.object;

import com.cjs.example.lock.exception.ApiErrorEnum;
import com.cjs.example.lock.exception.ApiErrorEnum.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class ServerResponse<T> {

    private int code;

    private String message;

    private T data;

    public boolean success() {
        return this.code == ApiErrorEnum.SUCCESS;
    }

    public static ServerResponse SUCCESS;

    static {
        SUCCESS = new ServerResponse();
        SUCCESS.setCode(ApiErrorEnum.SUCCESS);
    }

    public static <T> ServerResponse<T> success(T t) {
        ServerResponse<T> r = new ServerResponse<>();
        r.code = ApiErrorEnum.SUCCESS;
        r.data = t;
        return r;
    }

    public static <T> ServerResponse<T> success(T t, String message) {
        ServerResponse<T> r = new ServerResponse<>();
        r.code = ApiErrorEnum.SUCCESS;
        r.message = message;
        r.data = t;
        return r;
    }

    public static ServerResponse fail(String message) {
        return fail(Internal.ServiceError.getCode(), message, null);
    }

    public static ServerResponse fail(int code, String message) {
        return fail(code, message, null);
    }

    public static <T> ServerResponse<T> fail(int code, String message, T data) {
        ServerResponse errorResponse = new ServerResponse();
        errorResponse.setCode(code);
        errorResponse.setMessage(message);
        errorResponse.setData(data);
        return errorResponse;
    }

}
