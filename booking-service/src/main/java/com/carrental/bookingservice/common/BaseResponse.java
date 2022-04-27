package com.carrental.bookingservice.common;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BaseResponse<T> {
    private String code = Code.SUCCESS.value;
    private String errMsg = "";
    private T data;

    public static enum Code {
        SUCCESS("0"),
        ERROR("1"),
        ;

        private String value;

        private Code(String value) {
            this.value = value;
        }
    }

    public BaseResponse(T data) {
        this.data = data;
    }

    public static BaseResponse success() {
        return new BaseResponse<>();
    }

    public static BaseResponse error(String errMsg) {
        BaseResponse resp = new BaseResponse<>();
        resp.setCode(Code.ERROR.value);
        resp.setErrMsg(errMsg);
        return resp;
    }
}
