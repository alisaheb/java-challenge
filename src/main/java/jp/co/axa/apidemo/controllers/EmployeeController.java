package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.exception.EntityNotFoundException;
import jp.co.axa.apidemo.exception.RequestInvalidParameterException;
import jp.co.axa.apidemo.model.request.EmployeeRequestModel;
import jp.co.axa.apidemo.model.response.EmployeeResponseModel;
import jp.co.axa.apidemo.services.EmployeeService;
import jp.co.axa.apidemo.services.RequestParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RequestParameterService requestParameterService;

    public void EmployeeController(EmployeeService employeeService, RequestParameterService requestParameterService)
    {
        this.employeeService = employeeService;
        this.requestParameterService = requestParameterService;
    }

    @GetMapping(value = "/employees", produces = "application/json")
    public ResponseEntity<Object> getEmployees() {
        List<EmployeeResponseModel> employees = employeeService.retrieveEmployees();
        if(employees.isEmpty()){
            throw new EntityNotFoundException();
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping(value = "/employees/{employeeId}", produces = "application/json")
    public ResponseEntity<Object> getEmployee(@PathVariable(name="employeeId") String employeeId) {
        if(!requestParameterService.isIdValid(employeeId)){
            throw new RequestInvalidParameterException();
        }
        Long id = requestParameterService.extractId(employeeId);
        Optional<EmployeeResponseModel> employee = employeeService.getEmployee(id);
        if(employee.isPresent()){
            return new ResponseEntity<>(employee.get(), HttpStatus.OK);
        }else {
            throw new EntityNotFoundException();
        }
    }

    @PostMapping(value = "/employees", produces = "application/json")
    public ResponseEntity<Object> saveEmployee(@RequestBody EmployeeRequestModel employeeRequestModel){
        if(!requestParameterService.isEmployeeRequestModelValid(employeeRequestModel)){
            throw new RequestInvalidParameterException();
        }
        return new ResponseEntity<>(employeeService.saveEmployee(employeeRequestModel), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/employees/{employeeId}", produces = "application/json")
    public ResponseEntity<Object> deleteEmployee(@PathVariable(name="employeeId")Long employeeId){
        Optional<EmployeeResponseModel> employee = employeeService.getEmployee(employeeId);
        if(employee.isPresent()){
            return new ResponseEntity<>(employeeService.deleteEmployee(employeeId), HttpStatus.OK);
        }else {
            throw new EntityNotFoundException();
        }
    }

    @PutMapping(value = "/employees/{employeeId}", produces = "application/json")
    public ResponseEntity<Object> updateEmployee(@RequestBody EmployeeRequestModel employeeRequestModel,
                               @PathVariable(name="employeeId") String employeeId){
        if(!requestParameterService.isIdValid(employeeId)){
            throw new RequestInvalidParameterException();
        }
        if(!requestParameterService.isEmployeeRequestModelValid(employeeRequestModel)){
            throw new RequestInvalidParameterException();
        }
        Long id = requestParameterService.extractId(employeeId);
        Optional<EmployeeResponseModel> employee = employeeService.getEmployee(id);
        if(employee.isPresent()){
            return new ResponseEntity<>(employeeService.updateEmployee(employeeRequestModel,id), HttpStatus.OK);
        }else {
            throw new EntityNotFoundException();
        }
    }
}
