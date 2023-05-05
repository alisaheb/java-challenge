package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.model.request.EmployeeRequestModel;
import jp.co.axa.apidemo.model.response.ApiResponseModel;
import jp.co.axa.apidemo.model.response.EmployeeResponseModel;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    @Autowired
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
    public Optional<EmployeeResponseModel> getEmployee(Long employeeId) {
        try {
            Optional<Employee> optEmp = employeeRepository.findById(employeeId);

            if(optEmp.isPresent()){
                EmployeeResponseModel employeeResponseModel = new EmployeeResponseModel();
                employeeResponseModel.setName(optEmp.get().getName());
                employeeResponseModel.setSalary(optEmp.get().getSalary());
                employeeResponseModel.setDepartment(optEmp.get().getDepartment());
                return Optional.of(employeeResponseModel);
            }else {
             return Optional.empty();
            }
        }catch (Exception databaseException){
            log.info("Check the database connections.");
            log.error(databaseException.toString());
            throw databaseException;
        }
    }

    @Override
    public ApiResponseModel saveEmployee(EmployeeRequestModel employeeRequestModel){
        try {
            Employee employee = new Employee();
            employee.setName(employeeRequestModel.getName());
            employee.setSalary(employeeRequestModel.getSalary());
            employee.setDepartment(employeeRequestModel.getDepartment());
            employeeRepository.save(employee);
            ApiResponseModel apiResponseModel = new ApiResponseModel();
            apiResponseModel.setStatus("success");
            apiResponseModel.setData("The the entity has been saved.");
            return apiResponseModel;
        }catch (Exception databaseException){
            log.info("Check the database connections.");
            log.error(databaseException.toString());
            throw databaseException;
        }
    }

    @Override
    public ApiResponseModel deleteEmployee(Long employeeId){
        try {
            employeeRepository.deleteById(employeeId);
            ApiResponseModel apiResponseModel = new ApiResponseModel();
            apiResponseModel.setStatus("success");
            apiResponseModel.setData("The the entity has been deleted.");
            return apiResponseModel;
        }catch (Exception databaseException){
            log.info("Check the database connections.");
            log.error(databaseException.toString());
            throw databaseException;
        }
    }

    @Override
    public ApiResponseModel updateEmployee(EmployeeRequestModel employeeRequestModel, Long employeeId) {
        try {
            Optional<Employee> employee = employeeRepository.findById(employeeId);
            employee.get().setName(employeeRequestModel.getName());
            employee.get().setSalary(employeeRequestModel.getSalary());
            employee.get().setDepartment(employeeRequestModel.getDepartment());
            employeeRepository.save(employee.get());
            ApiResponseModel apiResponseModel = new ApiResponseModel();
            apiResponseModel.setStatus("success");
            apiResponseModel.setData("The the entity has been updated.");
            return apiResponseModel;
        }catch (Exception databaseException){
            log.info("Check the database connections.");
            log.error(databaseException.toString());
            throw databaseException;
        }
    }
}