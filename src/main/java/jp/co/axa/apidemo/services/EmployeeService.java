package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.model.request.EmployeeRequestModel;
import jp.co.axa.apidemo.model.response.ApiResponseModel;
import jp.co.axa.apidemo.model.response.EmployeeResponseModel;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    public List<EmployeeResponseModel> retrieveEmployees();

    public Optional<EmployeeResponseModel> getEmployee(Long employeeId);

    public ApiResponseModel saveEmployee(EmployeeRequestModel employeeRequestModel);

    public ApiResponseModel deleteEmployee(Long employeeId);

    public ApiResponseModel updateEmployee(EmployeeRequestModel employeeRequestModel, Long employeeId);
}