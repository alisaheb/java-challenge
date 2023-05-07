package jp.co.axa.apidemo.model.response;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "EmployeeResponseModel is used for returning the employee entity.")
public class EmployeeResponseModel {

    private String name;
    private Integer salary;
    private String department;
}
