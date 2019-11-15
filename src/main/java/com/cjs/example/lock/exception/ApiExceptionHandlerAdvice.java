package com.cjs.example.lock.exception;


import com.cjs.example.lock.object.ServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import com.cjs.example.lock.exception.ApiErrorEnum.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jpcui
 */
@ControllerAdvice
@ResponseBody
public class ApiExceptionHandlerAdvice {

    protected Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    /**
     * Bean 校验.
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseStatus(HttpStatus.OK)
    public ServerResponse exception(BindException exception, WebRequest request) {
        logger.warn("参数错误", exception);
        return ServerResponse.fail(ApiErrorEnum.Request.InvalidArguments.getCode(),
            exception.getFieldError().getDefaultMessage());
    }

    /**
     * Bean校验.
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public ServerResponse exception(MethodArgumentNotValidException exception, WebRequest request) {
        logger.warn("参数错误", exception);
        return ServerResponse.fail(ApiErrorEnum.Request.InvalidArguments.getCode(),
                                   exception.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * Handle API HttpRequestMethodNotSupportedException thrown by handlers.
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ServerResponse exception(HttpRequestMethodNotSupportedException exception,
                                    WebRequest request) {
        logger.warn("BAD_REQUEST", exception);
        ApiErrorEnum.Request e = ApiErrorEnum.Request.MethodNotSupported;
        return ServerResponse.fail(e.getCode(), e.getMessage());
    }

    /**
     * Handle API TypeMismatchException thrown by handlers.
     */
    @ExceptionHandler(value = TypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ServerResponse exception(TypeMismatchException exception, WebRequest request) {
        logger.warn("BAD_REQUEST", exception);
        return ServerResponse.fail(ApiErrorEnum.Request.InvalidArguments.getCode()
            , exception.getMessage());
    }

    /**
     * Handle API MissingServletRequestParameterException thrown by handlers.
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ServerResponse exception(MissingServletRequestParameterException exception,
                                    WebRequest request) {
        logger.warn("BAD_REQUEST", exception);
        return ServerResponse.fail(ApiErrorEnum.Request.MissingRequiredArguments.getCode(),
            exception.getMessage());
    }

    /**
     * Handle API ApiUnauthorizedException thrown by handlers.
     */
    @ExceptionHandler(value = ApiUnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ServerResponse exception(ApiUnauthorizedException exception, WebRequest request) {
        logger.warn("UNAUTHORIZED", exception);
        ApiErrorEnum.Request e = ApiErrorEnum.Request.Unauthorized;
        return ServerResponse.fail(e.getCode(), e.getMessage());
    }

    /**
     * Handle ApiException thrown by handlers.
     */
    @ExceptionHandler(value = ApiException.class)
    @ResponseStatus(HttpStatus.OK)
    public ServerResponse exception(ApiException exception, WebRequest request) {

        logger.error("INTERNAL_SERVER_ERROR", exception);
        return ServerResponse
            .fail(exception.getCode(), exception.getMessage(), exception.getData());
    }

    /**
     * 系统内部错误 Handle exceptions thrown by handlers.
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ServerResponse exception(Exception exception, WebRequest request) {

        Map<String, Object> data = this.getExceptionData(exception);
        logger.error("INTERNAL_SERVER_ERROR", exception);

        Internal r = Internal.ServiceError;
        return ServerResponse.fail(r.getCode(), r.getMessage(), data);
    }

    /**
     * 参数错误.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ServerResponse exception(IllegalArgumentException e) {

        logger.error("--------->接口调用异常!", e);
        Request r = Request.InvalidArguments;
        return ServerResponse.fail(r.getCode(), r.getMessage());
    }

    private Map<String, Object> getExceptionData(Exception exception) {
        Map<String, Object> data = new HashMap<>();

        //不返回给客户端异常堆栈
//        Object firstStackTrace = Array.get(exception.getStackTrace(), 0);
        data.put("service_message", exception.getMessage());
//        data.put("first_stack_trace", firstStackTrace);

        logger.error("api_internal_error:", exception);

        return data;
    }
}
