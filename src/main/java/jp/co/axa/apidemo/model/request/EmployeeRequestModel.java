package jp.co.axa.apidemo.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRequestModel {

    private String name;
    private Integer salary;
    private String department;
}
