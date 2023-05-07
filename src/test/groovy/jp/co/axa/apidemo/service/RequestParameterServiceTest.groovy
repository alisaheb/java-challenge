package jp.co.axa.apidemo.service

import jp.co.axa.apidemo.exception.RequestInvalidParameterException
import jp.co.axa.apidemo.model.request.EmployeeRequestModel
import jp.co.axa.apidemo.services.RequestParameterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class RequestParameterServiceTest extends Specification{

    @Autowired
    RequestParameterService systemUnderTest

    def "isIdValid returns true false according to number and non number"(){

        expect:
        systemUnderTest.isIdValid(input) == expected

        where:
        input      | expected
        "5555"     | true
        "00000000" | true
        "this000"  | false
    }

    def "extractId extract the long id form the given string"(){

        expect:
        systemUnderTest.extractId(input) == expected

        where:
        input                     | expected
        "5555"                    | 5555
        Long.MAX_VALUE.toString() | Long.MAX_VALUE
    }

    def "extractId throw RequestInvalidParameterException if you send more than Long value"(){

        given:
        def input = "92233720368547758078787 "

        when:
        systemUnderTest.extractId(input)

        then:
        thrown(RequestInvalidParameterException)
    }

    def "isEmployeeRequestModelValid returns true false according to input validation violation"(){

        given:
        def input1 = new EmployeeRequestModel()
        input1.setName("Ali")
        input1.setSalary(2000)
        input1.setDepartment("HR")
        def input2 = new EmployeeRequestModel()
        def input3 = new EmployeeRequestModel()
        input3.setName()
        input3.setSalary(999999)
        input3.setDepartment("IT")

        when:
        def actualOutput1 =  systemUnderTest.isEmployeeRequestModelValid(input1)
        def actualOutput2 =  systemUnderTest.isEmployeeRequestModelValid(input2)
        def actualOutput3 =  systemUnderTest.isEmployeeRequestModelValid(input3)

        then:
        actualOutput1 == true
        actualOutput2 == false
        actualOutput3 == false
    }
}
