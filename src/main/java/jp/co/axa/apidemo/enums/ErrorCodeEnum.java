package jp.co.axa.apidemo.enums;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {

    REQUEST_PARAMETER_ERROR_CODE("400-00", "The request has invalid parameter."),
    REQUEST_ENTITY_NOT_FOUND("404-00", "The requested entity not found."),
    UNEXPECTED_ERROR_EXCEPTION("500-00", "There has been some unexpected error happens please check the log.");

    private String errorCode;
    private String errorMessage;

    ErrorCodeEnum(String errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
