package com.cjs.example.lock.exception;


import com.cjs.example.lock.object.ServerResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("serial")
public class ApiException extends RuntimeException {

    private int code;
    private Object data;

    public ApiException(ApiErrorEnum apiErrorEnum) {
        super((apiErrorEnum.getMessage()));
        this.setCode(apiErrorEnum.getCode());
    }

    public ApiException(String message) {
        super(message);
        this.setCode(ApiErrorEnum.Internal.ServiceError.getCode());
    }

    public ApiException(ServerResponse serverResponse) {
        this(serverResponse.getCode(), serverResponse.getMessage());
    }

    public ApiException(int errorCode, String message) {
        super(message);
        this.setCode(errorCode);
    }

    public ApiException(int errorCode, String message, Object data) {
        super(message);
        this.setCode(errorCode);
        this.setData(data);
    }

}
