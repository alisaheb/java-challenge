package jp.co.axa.apidemo.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class EmployeeRequestModel {

    @NotEmpty
    @Size(max = 50)
    private String name;

    @Digits(integer = 10, fraction = 0)
    private Integer salary;

    @NotEmpty
    @Size(max = 50)
    private String department;
}
