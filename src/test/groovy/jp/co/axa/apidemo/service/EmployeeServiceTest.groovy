package jp.co.axa.apidemo.service

import jp.co.axa.apidemo.services.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class EmployeeServiceTest extends Specification{
    @Autowired
    EmployeeService systemUnderTest

    def "retrieveEmployees return a EmployeeResponseModel list"(){
        given:

        when:
        then:
    }
}
