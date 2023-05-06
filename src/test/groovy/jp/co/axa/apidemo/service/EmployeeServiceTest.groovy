package jp.co.axa.apidemo.service

import jp.co.axa.apidemo.entities.Employee
import jp.co.axa.apidemo.exception.UnexpectedErrorException
import jp.co.axa.apidemo.model.request.EmployeeRequestModel
import jp.co.axa.apidemo.repositories.EmployeeRepository
import jp.co.axa.apidemo.services.EmployeeServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class EmployeeServiceTest extends Specification{
    @Autowired
    EmployeeServiceImpl systemUnderTest
    @Autowired
    EmployeeRepository employeeRepository

    def setup(){
        employeeRepository.flush()
    }

    def "retrieveEmployees return a EmployeeResponseModel list"(){

        given: "return the data from repository"
        def original = systemUnderTest
        def employee = new Employee()
        employee.setId(1)
        employee.setName("Ali")
        employee.setSalary(2000)
        employee.setDepartment("HR")
        def listOfEmployee = new ArrayList<Employee>()
        listOfEmployee.add(employee)
        def employeeRepositoryMock = Mock(EmployeeRepository)
        employeeRepositoryMock.findAll() >> {listOfEmployee}
        systemUnderTest = Spy(new EmployeeServiceImpl(employeeRepositoryMock))

        when: "Call the retrieveEmployees method and store the data into actualOutput"
        def actualOutput = systemUnderTest.retrieveEmployees()

        then: "Check with actual output and expected."
        actualOutput.size() == 1
        actualOutput.stream().findFirst().get().name == "Ali"
        actualOutput.stream().findFirst().get().salary == 2000
        actualOutput.stream().findFirst().get().department == "HR"

        cleanup: "Back to the original object."
        systemUnderTest = original
    }

    def "retrieveEmployees return the UnexpectedErrorException if there is any unexpected error happens."(){

        given: "set the Exception while retrieve the data from repository."
        def original = systemUnderTest
        def employeeRepositoryMock = Mock(EmployeeRepository)
        employeeRepositoryMock.findAll() >> { throw new Exception()}
        systemUnderTest = Spy(new EmployeeServiceImpl(employeeRepositoryMock))

        when: "Call the retrieveEmployees method and store the data into actualOutput"
        systemUnderTest.retrieveEmployees()

        then: "check the UnexpectedErrorException is return"
        thrown(UnexpectedErrorException)

        cleanup: "Back to the original object."
        systemUnderTest = original
    }

    def "getEmployee return a EmployeeResponseModel"(){

        given: "return the data from repository"
        def original = systemUnderTest
        def employee = new Employee()
        employee.setId(1)
        employee.setName("Ali")
        employee.setSalary(2000)
        employee.setDepartment("HR")
        def optEmployee = Optional.of(employee)
        def employeeRepositoryMock = Mock(EmployeeRepository)
        employeeRepositoryMock.findById(_ as Long) >> {optEmployee}
        systemUnderTest = Spy(new EmployeeServiceImpl(employeeRepositoryMock))

        when: "Call the getEmployee method and store the data into actualOutput"
        def actualOutput = systemUnderTest.getEmployee(1)

        then: "Check with actual output and expected."
        actualOutput.get().name == "Ali"
        actualOutput.get().salary == 2000
        actualOutput.get().department == "HR"

        cleanup: "Back to the original object."
        systemUnderTest = original
    }

    def "getEmployee return the UnexpectedErrorException if there is any unexpected error happens."(){

        given: "set the Exception while retrieve the data from repository."
        def original = systemUnderTest
        def employeeRepositoryMock = Mock(EmployeeRepository)
        employeeRepositoryMock.findById(_ as Long) >> { throw new Exception()}
        systemUnderTest = Spy(new EmployeeServiceImpl(employeeRepositoryMock))

        when: "Call the getEmployee method and store the data into actualOutput"
        systemUnderTest.getEmployee(1)

        then: "check the UnexpectedErrorException is return"
        thrown(UnexpectedErrorException)

        cleanup: "Back to the original object."
        systemUnderTest = original
    }

    def "saveEmployee return the success status and save the data into the database"(){

        given: "which set of data need to save is given"
        def employeeRequestModel = new EmployeeRequestModel()
        employeeRequestModel.setName("Ali")
        employeeRequestModel.setSalary(2000)
        employeeRequestModel.setDepartment("IT")
        Long id = 1

        when: "call the saveEmployee for saving the given data"
        def actualOutput =systemUnderTest.saveEmployee(employeeRequestModel)

        then: "Check the return value of the method and check it is saved on the database"
        actualOutput.getStatus() == "success"
        actualOutput.getData() == "The the entity has been saved."
        employeeRepository.findById(id).get().getName() == "Ali"
        employeeRepository.findById(id).get().getSalary() == 2000
        employeeRepository.findById(id).get().getDepartment() == "IT"
    }

    def "saveEmployee return UnexpectedErrorException if there is any unexpected error happens."(){

        given: "set the Exception while saving data into repository"
        def original = systemUnderTest
        def employeeRequestModel = new EmployeeRequestModel()
        employeeRequestModel.setName("Ali")
        employeeRequestModel.setSalary(2000)
        employeeRequestModel.setDepartment("IT")
        def employeeRepositoryMock = Mock(EmployeeRepository)
        employeeRepositoryMock.save(_ as Employee) >> { throw new Exception()}
        systemUnderTest = Spy(new EmployeeServiceImpl(employeeRepositoryMock))

        when: "Call the saveEmployee method and store the data into actualOutput"
        systemUnderTest.saveEmployee(employeeRequestModel)

        then: "check the UnexpectedErrorException is return"
        thrown(UnexpectedErrorException)

        cleanup: "Back to the original object."
        systemUnderTest = original
    }

    def "updateEmployee return the success status and update the data into the database"(){

        given: "which set of data need to save is given and for update"
        def employeeRequestModel = new EmployeeRequestModel()
        employeeRequestModel.setName("Ali")
        employeeRequestModel.setSalary(2000)
        employeeRequestModel.setDepartment("IT")
        Long id = 1
        systemUnderTest.saveEmployee(employeeRequestModel)

        def employeeRequestModelForUpdate = new EmployeeRequestModel()
        employeeRequestModelForUpdate.setName("Ali")
        employeeRequestModelForUpdate.setSalary(4000)
        employeeRequestModelForUpdate.setDepartment("HR")

        when: "call the deleteEmployee for deleting the given id"
        def actualOutput =systemUnderTest.updateEmployee(employeeRequestModelForUpdate, id)

        then: "Check the return value of the method and check it is deleted from the database"
        actualOutput.getStatus() == "success"
        actualOutput.getData() == "The the entity has been updated."
        employeeRepository.findById(id).get().getName() == "Ali"
        employeeRepository.findById(id).get().getSalary() == 4000
        employeeRepository.findById(id).get().getDepartment() == "HR"
    }

    def "updateEmployee return UnexpectedErrorException if there is any unexpected error happens."(){

        given: "set the Exception while saving data into repository"
        def original = systemUnderTest
        def employeeRequestModel = new EmployeeRequestModel()
        employeeRequestModel.setName("Ali")
        employeeRequestModel.setSalary(2000)
        employeeRequestModel.setDepartment("IT")
        Long id = 1
        def employeeRepositoryMock = Mock(EmployeeRepository)
        employeeRepositoryMock.save(_ as Employee) >> { throw new Exception()}
        systemUnderTest = Spy(new EmployeeServiceImpl(employeeRepositoryMock))

        when: "Call the saveEmployee method and store the data into actualOutput"
        systemUnderTest.updateEmployee(employeeRequestModel, id)

        then: "check the UnexpectedErrorException is return"
        thrown(UnexpectedErrorException)

        cleanup: "Back to the original object."
        systemUnderTest = original
    }

    def "deleteEmployee return the success status and delete the data from the database"(){

        given: "which set of data need to save is given"
        def employeeRequestModel = new EmployeeRequestModel()
        employeeRequestModel.setName("Ali")
        employeeRequestModel.setSalary(2000)
        employeeRequestModel.setDepartment("IT")
        Long id = 1
        systemUnderTest.saveEmployee(employeeRequestModel)

        when: "call the deleteEmployee for deleting the given id"
        def actualOutput =systemUnderTest.deleteEmployee(id)

        then: "Check the return value of the method and check it is deleted from the database"
        actualOutput.getStatus() == "success"
        actualOutput.getData() == "The the entity has been deleted."
    }

    def "deleteEmployee return the UnexpectedErrorException if there is any unexpected error happens."(){

        given: "set the Exception while retrieve the data from repository."
        def original = systemUnderTest
        def employeeRepositoryMock = Mock(EmployeeRepository)
        employeeRepositoryMock.deleteById(_ as Long) >> { throw new Exception()}
        systemUnderTest = Spy(new EmployeeServiceImpl(employeeRepositoryMock))

        when: "Call the deleteEmployee method and store the data into actualOutput"
        systemUnderTest.deleteEmployee(1)

        then: "check the UnexpectedErrorException is return"
        thrown(UnexpectedErrorException)

        cleanup: "Back to the original object."
        systemUnderTest = original
    }
}
