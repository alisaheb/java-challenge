package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.model.response.EmployeeResponseModel;

import java.util.List;

public interface EmployeeService {

    public List<EmployeeResponseModel> retrieveEmployees();

    public EmployeeResponseModel getEmployee(Long employeeId);

    public void saveEmployee(Employee employee);

    public void deleteEmployee(Long employeeId);

    public void updateEmployee(Employee employee);
}