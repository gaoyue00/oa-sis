package com.cjs.example.lock.exception;

public interface ApiErrorEnum {

    int getCode();

    String getMessage();

    int SUCCESS = 0;

    /**
     * 客户端请求导致的错误.
     */
    enum Request implements ApiErrorEnum {
        InvalidArguments(-330001, "无效参数"),
        MissingRequiredArguments(-330002, "缺少参数"),
        ResourceNotFound(-330003, "资源不存在"),
        Unauthorized(-330004, "未登录"),
        MethodNotSupported(-330005, "请求类型不支持"),
        IllegalOperation(-330006, "非法操作"),
        UnRegister(-330007, "未注册");

        private int errorCode;
        private String message;

        Request(int errorCode, String message) {
            this.errorCode = errorCode;
            this.message = message;
        }

        @Override
        public int getCode() {
            return this.errorCode;
        }

        @Override
        public String getMessage() {
            return this.message;
        }
    }


    /**
     * 服务端错误.
     */
    enum Internal implements ApiErrorEnum {
        ServiceError(-440001, "服务内部错误"),
        RpcError(-440002, "服务内部错误");

        private int errorCode;
        private String message;

        Internal(int errorCode, String message) {
            this.errorCode = errorCode;
            this.message = message;
        }

        @Override
        public int getCode() {
            return this.errorCode;
        }

        @Override
        public String getMessage() {
            return this.message;
        }
    }
}
