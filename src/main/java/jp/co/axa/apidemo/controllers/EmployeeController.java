package jp.co.axa.apidemo.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jp.co.axa.apidemo.exception.EntityNotFoundException;
import jp.co.axa.apidemo.exception.RequestInvalidParameterException;
import jp.co.axa.apidemo.model.request.EmployeeRequestModel;
import jp.co.axa.apidemo.model.response.ApiResponseModel;
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
@Api(tags = "Employee API")
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
    @ApiOperation(value = "This endpoint return a list employee.",
            notes = "This endpoint return all the employee are reside into the database if there is no entity into the database then it returns 404 entity not found.",
            response = EmployeeResponseModel.class)
    public ResponseEntity<Object> getEmployees() {
        List<EmployeeResponseModel> employees = employeeService.retrieveEmployees();
        if(employees.isEmpty()){
            throw new EntityNotFoundException();
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping(value = "/employees/{employeeId}", produces = "application/json")
    @ApiOperation(value = "This endpoint return a single entity of employee.",
            notes = "This endpoint return a single entity of employee if not then return a 404 not found.",
            response = EmployeeResponseModel.class)
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
    @ApiOperation(value = "This endpoint save a single entity.",
            notes = "This endpoint save a given entity and also validate the incoming request with a certain condition.",
            response = ApiResponseModel.class)
    public ResponseEntity<Object> saveEmployee(@RequestBody EmployeeRequestModel employeeRequestModel){
        if(!requestParameterService.isEmployeeRequestModelValid(employeeRequestModel)){
            throw new RequestInvalidParameterException();
        }
        return new ResponseEntity<>(employeeService.saveEmployee(employeeRequestModel), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/employees/{employeeId}", produces = "application/json")
    @ApiOperation(value = "This endpoint delete a single entity.",
            notes = "This endpoint delete a single entity from database if does not found the entity then return not found 404.",
            response = ApiResponseModel.class)
    public ResponseEntity<Object> deleteEmployee(@PathVariable(name="employeeId")Long employeeId){
        Optional<EmployeeResponseModel> employee = employeeService.getEmployee(employeeId);
        if(employee.isPresent()){
            return new ResponseEntity<>(employeeService.deleteEmployee(employeeId), HttpStatus.OK);
        }else {
            throw new EntityNotFoundException();
        }
    }

    @PutMapping(value = "/employees/{employeeId}", produces = "application/json")
    @ApiOperation(value = "This endpoint update a single entity.",
            notes = "This endpoint update a single entity from database if does not found the entity then return not found 404.",
            response = ApiResponseModel.class)
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
