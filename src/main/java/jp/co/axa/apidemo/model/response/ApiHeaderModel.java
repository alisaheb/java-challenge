package jp.co.axa.apidemo.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiHeaderModel {
    private String errorCode;
    private String errorMessage;
}