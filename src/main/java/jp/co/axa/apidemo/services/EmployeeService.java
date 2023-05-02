package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.model.request.EmployeeRequestModel;
import jp.co.axa.apidemo.model.response.EmployeeResponseModel;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    public List<EmployeeResponseModel> retrieveEmployees();

    public Optional<EmployeeResponseModel> getEmployee(Long employeeId);

    public void saveEmployee(Employee employee);

    public void deleteEmployee(Long employeeId);

    public void updateEmployee(EmployeeRequestModel employeeRequestModel, Long employeeId);
}