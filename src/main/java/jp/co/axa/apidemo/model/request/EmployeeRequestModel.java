package jp.co.axa.apidemo.model.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@ApiModel(description = "EmployeeRequestModel is used for save and update of the employee entity.")
public class EmployeeRequestModel {

    @NotEmpty
    @Size(max = 50)
    private String name;

    @Max(Integer.MAX_VALUE)
    private Integer salary;

    @NotEmpty
    @Size(max = 50)
    private String department;
}
