package jp.co.axa.apidemo.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonSlurper
import jp.co.axa.apidemo.entities.Employee
import jp.co.axa.apidemo.model.request.EmployeeRequestModel
import jp.co.axa.apidemo.repositories.EmployeeRepository
import jp.co.axa.apidemo.services.EmployeeService
import jp.co.axa.apidemo.services.RequestParameterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Shared
import spock.lang.Specification

@AutoConfigureMockMvc
@SpringBootTest
class EmployeeControllerTest extends Specification{

    @Autowired
    EmployeeRepository employeeRepository

    @Autowired
    EmployeeService employeeService

    @Autowired
    RequestParameterService requestParameterService

    @Autowired
    MockMvc mockMvc

    @Shared
    JsonSlurper jsonSlurper = new JsonSlurper()

    @Shared
    ObjectMapper objectMapper = new ObjectMapper()

    def setup(){
        employeeRepository.flush()
    }

    def "getEmployees return not found if there is no employee exist in database"(){

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders
                .get('/api/v1/employees')
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
        def content = jsonSlurper.parseText(response.getContentAsString())

        then:
        response.getStatus() == HttpStatus.NOT_FOUND.value()
        content == ['errorCode':'404-00', 'errorMessage':'The requested entity not found.']
    }

    def "getEmployee return not found if there is no employee exist in database"(){

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders
                .get('/api/v1/employees/{employeeId}','1')
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
        def content = jsonSlurper.parseText(response.getContentAsString())

        then:
        response.getStatus() == HttpStatus.NOT_FOUND.value()
        content == ['errorCode':'404-00', 'errorMessage':'The requested entity not found.']
    }

    def "updateEmployee return the success for a valid request."(){

        given:
        def employee = new Employee()
        employee.setId(1)
        employee.setName("Ali")
        employee.setSalary(2000)
        employee.setDepartment("HR")
        employeeRepository.save(employee)

        def employeeRequestModel = new EmployeeRequestModel()
        employeeRequestModel.setName("Ali")
        employeeRequestModel.setSalary(4000)
        employeeRequestModel.setDepartment("HR")

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders
                .put('/api/v1/employees/{employeeId}','1')
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeRequestModel)))
                .andReturn()
                .getResponse()
        def content = jsonSlurper.parseText(response.getContentAsString())
        then:
        response.getStatus() == HttpStatus.OK.value()
        content == ['status':'success', 'data':'The the entity has been updated.']
    }

    def "getEmployees return a list of employee"(){

        given:
        def employee = new Employee()
        employee.setId(1)
        employee.setName("Ali")
        employee.setSalary(2000)
        employee.setDepartment("HR")
        employeeRepository.save(employee)

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders
                .get('/api/v1/employees')
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
        def content = jsonSlurper.parseText(response.getContentAsString())

        then:
        response.getStatus() == HttpStatus.OK.value()
        content == [['name':'Ali', 'salary':2000, 'department':'HR']]
    }

    def "getEmployee return a employee"(){

        given:
        def employee = new Employee()
        employee.setId(1)
        employee.setName("Ali")
        employee.setSalary(2000)
        employee.setDepartment("HR")
        employeeRepository.save(employee)

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders
                .get('/api/v1/employees/{employeeId}','1')
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
        def content = jsonSlurper.parseText(response.getContentAsString())

        then:
        response.getStatus() == HttpStatus.OK.value()
        content == ['name':'Ali', 'salary':2000, 'department':'HR']
    }

    def "saveEmployee return the success and http status created in a valid request"(){

        given:
        def employeeRequestModel = new EmployeeRequestModel()
        employeeRequestModel.setName("Ali")
        employeeRequestModel.setSalary(2000)
        employeeRequestModel.setDepartment("HR")

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders
                .post('/api/v1/employees')
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeRequestModel)))
                .andReturn()
                .getResponse()
        def content = jsonSlurper.parseText(response.getContentAsString())
        then:
        response.getStatus() == HttpStatus.CREATED.value()
        content == ['status':'success', 'data':'The the entity has been saved.']
    }

    def "deleteEmployee return the success after deleting the entity"(){

        given:
        def employee = new Employee()
        employee.setId(1)
        employee.setName("Ali")
        employee.setSalary(2000)
        employee.setDepartment("HR")
        employeeRepository.save(employee)

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders
                .delete('/api/v1/employees/{employeeId}','1')
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
        def content = jsonSlurper.parseText(response.getContentAsString())

        then:
        response.getStatus() == HttpStatus.OK.value()
        content == ['status':'success', 'data':'The the entity has been deleted.']
    }

    def "deleteEmployee return not found if there is no employee exist in database"(){

        when:
        def response = mockMvc.perform(MockMvcRequestBuilders
                .delete('/api/v1/employees/{employeeId}','1')
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
        def content = jsonSlurper.parseText(response.getContentAsString())

        then:
        response.getStatus() == HttpStatus.NOT_FOUND.value()
        content == ['errorCode':'404-00', 'errorMessage':'The requested entity not found.']
    }

    def "updateEmployee return not found if there is no employee exist in database"(){

        given:
        def employeeRequestModel = new EmployeeRequestModel()
        employeeRequestModel.setName("Ali")
        employeeRequestModel.setSalary(4000)
        employeeRequestModel.setDepartment("HR")
        when:
        def response = mockMvc.perform(MockMvcRequestBuilders
                .put('/api/v1/employees/{employeeId}','1')
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeRequestModel)))
                .andReturn()
                .getResponse()
        def content = jsonSlurper.parseText(response.getContentAsString())

        then:
        response.getStatus() == HttpStatus.NOT_FOUND.value()
        content == ['errorCode':'404-00', 'errorMessage':'The requested entity not found.']
    }
}
