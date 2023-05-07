package jp.co.axa.apidemo.model.response;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "ApiResponseModel is used for returning the status of the request.")
public class ApiResponseModel {

    private String status;
    private String data;
}
