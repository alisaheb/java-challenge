package jp.co.axa.apidemo.model.response;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "ApiResponseModel is used for returning the error status of the request.")
public class ApiExceptionModel {

    private String errorCode;
    private String errorMessage;
}
