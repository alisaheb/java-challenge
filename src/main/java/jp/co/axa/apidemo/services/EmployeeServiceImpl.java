package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.model.response.EmployeeResponseModel;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private EmployeeRepository employeeRepository;

    public Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    public void EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    @Override
    public List<EmployeeResponseModel> retrieveEmployees() {
        try {
            List<Employee> employees = employeeRepository.findAll();
            List<EmployeeResponseModel> employeeResponseModels = employees.stream().map(employee -> {
                EmployeeResponseModel employeeResponseModel = new EmployeeResponseModel();
                employeeResponseModel.setName(employee.getName());
                employeeResponseModel.setDepartment(employee.getDepartment());
                employeeResponseModel.setSalary(employee.getSalary());
                return employeeResponseModel;
            }).collect(Collectors.toList());
            return employeeResponseModels;
        }catch (Exception databaseException){
            log.info("Check the database connections.");
            log.error(databaseException.toString());
            throw databaseException;
        }
    }
    @Override
    public EmployeeResponseModel getEmployee(Long employeeId) {
        try {
            Optional<Employee> optEmp = employeeRepository.findById(employeeId);
            EmployeeResponseModel employeeResponseModel = new EmployeeResponseModel();
            employeeResponseModel.setName(optEmp.get().getName());
            employeeResponseModel.setSalary(optEmp.get().getSalary());
            employeeResponseModel.setDepartment(optEmp.get().getDepartment());
            return employeeResponseModel;
        }catch (Exception databaseException){
            log.info("Check the database connections.");
            log.error(databaseException.toString());
            throw databaseException;
        }
    }

    public void saveEmployee(Employee employee){
        employeeRepository.save(employee);
    }

    public void deleteEmployee(Long employeeId){
        employeeRepository.deleteById(employeeId);
    }

    public void updateEmployee(Employee employee) {
        employeeRepository.save(employee);
    }
}