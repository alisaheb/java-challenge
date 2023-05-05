package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.model.request.EmployeeRequestModel;

public interface RequestParameterService {

    public Boolean isIdValid(String id);

    public Long extractId(String id);

    public Boolean isEmployeeRequestModelValid(EmployeeRequestModel employeeRequestModel);
}
